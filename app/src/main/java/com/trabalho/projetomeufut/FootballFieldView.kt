import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class FootballFieldView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val fieldPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.GREEN
    }

    private val linePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f
        color = Color.WHITE
    }

    private val circlePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f
        color = Color.WHITE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val fieldWidth = width * 0.8f
        val fieldHeight = height * 0.5f
        val fieldLeft = (width - fieldWidth) / 2f
        val fieldTop = (height - fieldHeight) / 2f
        val radius = fieldWidth / 10f
        val penaltyAreaWidth = fieldWidth * 0.18f
        val penaltyAreaHeight = fieldHeight * 0.44f
        val goalAreaWidth = fieldWidth * 0.1f
        val goalAreaHeight = fieldHeight * 0.22f
        val goalPostSize = fieldWidth * 0.01f
        val ballRadius = fieldWidth * 0.015f

        // Draw field
        canvas.drawColor(Color.parseColor("#00BFFF"))
        canvas.drawRect(
            fieldLeft,
            fieldTop,
            fieldLeft + fieldWidth,
            fieldTop + fieldHeight,
            fieldPaint
        )

        // Draw center line
        canvas.drawLine(
            fieldLeft + fieldWidth / 2f,
            fieldTop,
            fieldLeft + fieldWidth / 2f,
            fieldTop + fieldHeight,
            linePaint
        )

        // Draw center circle
        canvas.drawCircle(
            fieldLeft + fieldWidth / 2f,
            fieldTop + fieldHeight / 2f,
            radius,
            circlePaint
        )

        // Draw penalty areas
        val leftPenaltyAreaLeft = fieldLeft
        val rightPenaltyAreaLeft = fieldLeft + fieldWidth - penaltyAreaWidth
        val penaltyAreaTop = fieldTop + (fieldHeight - penaltyAreaHeight) / 2f
        canvas.drawRect(
            leftPenaltyAreaLeft,
            penaltyAreaTop,
            leftPenaltyAreaLeft + penaltyAreaWidth,
            penaltyAreaTop + penaltyAreaHeight,
            linePaint
        )
        canvas.drawRect(
            rightPenaltyAreaLeft,
            penaltyAreaTop,
            rightPenaltyAreaLeft + penaltyAreaWidth,
            penaltyAreaTop + penaltyAreaHeight,
            linePaint
        )

        // Draw goal areas
        val leftGoalAreaLeft = fieldLeft - goalAreaWidth
        val rightGoalAreaLeft = fieldLeft + fieldWidth
        val goalAreaTop = fieldTop + (fieldHeight - goalAreaHeight) / 2f
        canvas.drawRect(
            leftGoalAreaLeft,
            goalAreaTop,
            leftGoalAreaLeft + goalAreaWidth,
            goalAreaTop + goalAreaHeight,
            linePaint
        )
        canvas.drawRect(
            rightGoalAreaLeft,
            goalAreaTop,
            rightGoalAreaLeft + goalAreaWidth,
            goalAreaTop + goalAreaHeight,
            linePaint
        )

        // Draw goals
        val leftGoalPostLeft = fieldLeft - goalPostSize / 2f

    }
}
