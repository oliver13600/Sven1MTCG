package Game;

public class Stats {

    static float Probability(float rating1, float rating2){
        return 1.0f * 1.0f / (1 + 1.0f * (float)(Math.pow(10, 1.0f * (rating1 - rating2) / 400)));
    }

    public void EloRating(User user1, User user2, boolean user1won)
    {
        // To calculate the Winning
        // Probability of Player B
        float Pb = Probability(user1.getElo(), user2.getElo());

        // To calculate the Winning
        // Probability of Player A
        float Pa = Probability(user2.getElo(), user1.getElo());

        // Case -1 When User1 wins
        // Updating the Elo Ratings
        if (user1won) {
            user1.setElo(user1.getElo() + 25 * (1 - Pa));
            user2.setElo(user2.getElo() + 25 * (0 - Pb));
        }

        // Case -2 When User2 wins
        // Updating the Elo Ratings
        else {
            user1.setElo(user1.getElo() + 25 * (0 - Pa));
            user2.setElo(user2.getElo() + 25 * (1 - Pb));
        }
    }
}


