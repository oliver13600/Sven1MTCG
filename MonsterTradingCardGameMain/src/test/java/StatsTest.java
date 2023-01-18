import Game.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StatsTest {

    @Test
    public void testEloRating() {
        User user1 = new User("test", "test", "test", "test", 1, 1, 1, 1500);
        User user2 = new User("test", "test", "test", "test", 1, 1, 1, 1400);
        Stats stats = new Stats();

        // Case 1: User1 wins
        stats.EloRating(user1, user2, true);
        assertTrue(user1.getElo() > 1500 && user2.getElo() < 1400);

        // Case 2: User2 wins
        stats.EloRating(user1, user2, false);
        assertTrue(user1.getElo() < 1500 && user2.getElo() > 1400);
    }
}
