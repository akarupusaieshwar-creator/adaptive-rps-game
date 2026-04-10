async function play(userMove) {
    const response = await fetch('http://localhost:8080/api/game/play', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ move: userMove })
    });
    
    const data = await response.json();
    
    document.getElementById('displayResult').innerText = 
        `You: ${userMove} | System: ${data.systemMove} -> Result: ${data.result}`;
    
    // Visual feedback for "Learning" [cite: 8, 9]
    document.getElementById('stats').innerText = "System intelligence: Adapting to behavior...";
}