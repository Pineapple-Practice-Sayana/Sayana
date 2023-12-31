package site.pnpl.mira.ui.check_in.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import site.pnpl.mira.R

class BubbleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    constructor(context: Context, type: Type, message: String) : this(context) {

        inflate(
            context,
            when (type) {
                Type.LEFT_HIGH -> R.layout.item_bubble_left_high
                Type.LEFT_SMALL -> R.layout.item_bubble_left_small
                Type.RIGHT_HIGH -> R.layout.item_bubble_right_high
                Type.RIGHT_SMALL -> R.layout.item_bubble_right_small
            },
            this
        )
        this.type = type
        this.message = message

    }

    var type : Type = Type.LEFT_HIGH
        private set
    var message : String = ""
        set(value) {
            findViewById<TextView>(R.id.message).text = value
            field = value
        }

    enum class Type {
        LEFT_HIGH,
        LEFT_SMALL,
        RIGHT_HIGH,
        RIGHT_SMALL
    }

}

