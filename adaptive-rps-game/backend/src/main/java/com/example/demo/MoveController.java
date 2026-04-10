import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class MoveController {

    // Gameplay data collection [cite: 5]
    private List<String> playerHistory = new ArrayList<>();
    private Map<String, Integer> frequencyMap = new HashMap<>();

    public MoveController() {
        frequencyMap.put("ROCK", 0);
        frequencyMap.put("PAPER", 0);
        frequencyMap.put("SCISSORS", 0);
    }

    @PostMapping("/play")
    public Map<String, String> play(@RequestBody Map<String, String> request) {
        String userMove = request.get("move").toUpperCase();

        // 1. Predict user's move based on frequency patterns [cite: 6, 7]
        String predictedMove = predictUserMove();

        // 2. Select optimal counter strategy
        String systemMove = getCounter(predictedMove);

        // 3. Determine result
        String result = determineWinner(userMove, systemMove);

        // 4. Update dynamically generated dataset [cite: 5]
        playerHistory.add(userMove);
        frequencyMap.put(userMove, frequencyMap.get(userMove) + 1);

        Map<String, String> response = new HashMap<>();
        response.put("systemMove", systemMove);
        response.put("result", result);
        return response;
    }

    private String predictUserMove() {
        // If not enough data, move randomly
        if (playerHistory.size() < 3)
            return getRandomMove();

        // Return the move the user uses most (Probabilistic estimation)
        return frequencyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey();
    }

    private String getCounter(String move) {
        if (move.equals("ROCK"))
            return "PAPER";
        if (move.equals("PAPER"))
            return "SCISSORS";
        return "ROCK";
    }

    private String getRandomMove() {
        String[] moves = { "ROCK", "PAPER", "SCISSORS" };
        return moves[new Random().nextInt(3)];
    }

    private String determineWinner(String user, String system) {
        if (user.equals(system))
            return "TIE";
        if ((user.equals("ROCK") && system.equals("SCISSORS")) ||
                (user.equals("PAPER") && system.equals("ROCK")) ||
                (user.equals("SCISSORS") && system.equals("PAPER"))) {
            return "WIN";
        }
        return "LOSS";
    }
}