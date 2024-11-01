package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Array<Button>>
    private lateinit var statusTextView: TextView
    private var currentPlayer = "X"
    private var board = Array(3) { arrayOfNulls<String>(3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)
        buttons = Array(3) { row ->
            Array(3) { col ->
                val buttonId = resources.getIdentifier("button${3 * row + col + 1}", "id", packageName)
                findViewById<Button>(buttonId).apply {
                    setOnClickListener { onCellClicked(this, row, col) }
                }
            }
        }

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            resetGame()
        }
    }

    private fun onCellClicked(button: Button, row: Int, col: Int) {
        if (button.text.isNotEmpty()) return
        button.text = currentPlayer
        board[row][col] = currentPlayer
        if (checkForWin()) {
            statusTextView.text = "Jogador $currentPlayer venceu!"
            disableBoard()
        } else if (isBoardFull()) {
            statusTextView.text = "Empate!"
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            statusTextView.text = "Vez do jogador $currentPlayer"
        }
    }

    private fun checkForWin(): Boolean {
        // Verifica linhas, colunas e diagonais
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true
        return false
    }

    private fun isBoardFull(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j].isNullOrEmpty()) return false
            }
        }
        return true
    }

    private fun disableBoard() {
        for (row in buttons) {
            for (button in row) {
                button.isEnabled = false
            }
        }
    }

    private fun resetGame() {
        currentPlayer = "X"
        statusTextView.text = "Jogador X começa"
        board = Array(3) { arrayOfNulls<String>(3) }
        for (row in buttons) {
            for (button in row) {
                button.text = ""
                button.isEnabled = true
            }
        }
    }
}
