import Game.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserTest {
    @Test
    public void testGetInfo() {
        User user = new User("username", "John Doe", "Bio", "image.jpg", 10, 0, 0, 1500);
        String expectedResult = "{\"Image:\":\"image.jpg\",\"Name:\":\"John Doe\",\"Coins:\":\"10\",\"Bio:\":\"Bio\"}";
        assertEquals(expectedResult, user.getInfo());
    }

    @Test
    public void testGetStats() {
        User user = new User("username", "John Doe", "Bio", "image.jpg", 10, 0, 0, 1500);
        String expectedResult = "{\"Wins:\":0,\"Games:\":0}";
        assertEquals(expectedResult, user.getStats());
    }

    @Test
    public void testBuyPackage_success() {
        User user = new User("username", "John Doe", "Bio", "image.jpg", 10, 0, 0, 1500);
        assertTrue(user.buyPackage());
    }

    @Test
    public void testBuyPackage_failure() {
        User user = new User("username", "John Doe", "Bio", "image.jpg", 4, 0, 0, 1500);
        assertFalse(user.buyPackage());
    }

    @Test
    public void testBattleWon() {
        User user = new User("username", "John Doe", "Bio", "image.jpg", 10, 0, 0, 1500);
        assertTrue(user.battleWon());
        assertEquals(1, user.getWins());
        assertEquals(1, user.getGames());
        assertEquals(1503, user.getElo(), 0.1);
    }

    @Test
    public void testBattleLost() {
        User user = new User("username", "John Doe", "Bio", "image.jpg", 10, 0, 0, 1500);
        assertTrue(user.battleLost());
        assertEquals(0, user.getWins());
        assertEquals(1, user.getGames());
        assertEquals(1495, user.getElo(), 0.1);
    }

    @Test
    public void testBattleDraw() {
        User user = new User("username", "John Doe", "Bio", "image.jpg", 10, 0, 0, 1500);
        assertTrue(user.battleDraw());
        assertEquals(0, user.getWins());
        assertEquals(1, user.getGames());
        assertEquals(1500, user.getElo(), 0.1);
    }
}
