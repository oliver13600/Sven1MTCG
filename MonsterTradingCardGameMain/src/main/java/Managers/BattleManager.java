package Managers;

import Database.DbService;
import Game.Card;
import Game.Stats;
import Game.User;
import Game.Deck;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BattleManager {

    private static BattleManager single_instance = null;

    private User user1;
    private User user2;
    private String response;
    private boolean working = false;
    final Object LOCK = new Object();

    public static BattleManager getInstance()
    {
        if (single_instance == null) {
            single_instance = new BattleManager();
        }
        return single_instance;
    }

    public String addUser(User user){
        if (user1 == null){
            user1 = user;
            response = null;
            working = true;
            synchronized (LOCK) {
                while (working) {
                    try { LOCK.wait(); }
                    catch (InterruptedException e) {
                        // treat interrupt as exit request
                        break;
                    }
                }
            }
            return response;
        } else if (user2 == null){
            user2 = user;
            CardManager manager = new CardManager();
            Deck deck1 = manager.getDeckUser(user1);
            Deck deck2 = manager.getDeckUser(user2);
            response = battle(user1,user2,deck1,deck2);
            working = false;
            synchronized (LOCK) {
                LOCK.notifyAll();
            }
            user1 = null;
            user2 = null;
            return response;
        }
        return null;
    }

    public String battle(User user1, User user2, Deck deck1, Deck deck2){
        if (user1 == null || user2 == null || deck1 == null || deck2 == null){
            this.user1 = null;
            this.user2 = null;
            this.response = null;
            return null;
        }
        int turns = 0;
        try {
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode arrayNode = mapper.createArrayNode();
            String log;
            while (!deck1.isEmpty() && !deck2.isEmpty() && ++turns <= 100){
                ObjectNode round = mapper.createObjectNode();
                Card card1 = deck1.getRandomCard();
                Card card2 = deck2.getRandomCard();
                float damage1 = card1.calculateDamage(card2);
                float damage2 = card2.calculateDamage(card1);
                // Logging
                round.put("Round",turns);
                round.put("User_1",user1.getName());
                round.put("User_2",user2.getName());
                round.put("DeckSizeBefore_1",deck1.getSize());
                round.put("DeckSizeBefore_2",deck2.getSize());
                round.put("CardID_1",card1.getId());
                round.put("CardID_2",card2.getId());
                round.put("CardName_1",card1.getName());
                round.put("CardName_2",card2.getName());
                round.put("CardDamage_1",damage1);
                round.put("CardDamage_2",damage2);

                if (damage1 > damage2 || card1.defeatsInstantly(card2)){
                    card1.levelUp();
                    card2.LevelDown();
                    deck2.removeCard(card2);
                    deck1.addCard(card2);
                    round.put("Won",user1.getName());
                } else if (damage1 < damage2 || card2.defeatsInstantly(card1)){
                    card2.levelUp();
                    card1.LevelDown();
                    deck2.addCard(card1);
                    deck1.removeCard(card1);
                    round.put("Won",user2.getName());
                } else {
                    round.put("Won","Draw");
                }
                round.put("DeckSizeAfter_1",deck1.getSize());
                round.put("DeckSizeAfter_2",deck2.getSize());
                arrayNode.add(round);
            }
            Stats stats = new Stats();
            log = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
            if (deck1.isEmpty()){
                stats.EloRating(user1, user2, false);
                user1.battleLost();
                user2.battleWon();
                return log;
            } else if (deck2.isEmpty()){
                stats.EloRating(user1, user2, true);
                user1.battleWon();
                user2.battleLost();
                return log;
            }
            user1.battleDraw();
            user2.battleDraw();
            return log;
        }  catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getScoreboard(){
        try {
            Connection conn = DbService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT name, wins, games, elo FROM users WHERE name IS NOT NULL ORDER BY elo DESC;");
            ResultSet rs = ps.executeQuery();
            ps.close();
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode arrayNode = mapper.createArrayNode();
            while (rs.next()){
                ObjectNode entry = mapper.createObjectNode();
                entry.put("Name",rs.getString(1));
                entry.put("Wins",rs.getString(2));
                entry.put("Games",rs.getString(3));
                entry.put("Elo",rs.getString(4));
                arrayNode.add(entry);
            }
            rs.close();
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
