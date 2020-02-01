package com.shreyaspatil.material.navigationview

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.navigation.NavigationView
import com.shreyaspatil.MaterialNavigationView.R

/**
 * It creates Material navigation menu for application. The menu contents can be populated by a
 * menu resource file.
 */
class MaterialNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = R.style.Widget_NavigationView
) : NavigationView(context, attrs, defStyleAttr) {

    private var itemStyle: Int = ITEM_STYLE_DEFAULT

    init {
        // Init itemStyle
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MaterialNavigationView,
            0, 0
        )

        itemStyle = a.getInteger(
            R.styleable.MaterialNavigationView_itemStyle,
            ITEM_STYLE_DEFAULT
        )

        // Recycler it.
        a.recycle()

        // Set the Background to the Navigation Item
        itemBackground = navigationItemBackground()
    }

    /**
     * Method is used to get item style
     * @return Int value of Item style. It will be one of the below
     * @see ITEM_STYLE_ROUND_RIGHT (1)
     * @see ITEM_STYLE_ROUND_RECTANGLE (2)
     */
    fun getItemStyle(): Int {
        return itemStyle
    }

    /**
     * Method is used to set item style
     * @param itemStyle is value of Item style. It will be one of the below
     * @see ITEM_STYLE_ROUND_RIGHT (1)
     * @see ITEM_STYLE_ROUND_RECTANGLE (2)
     */
    fun setItemStyle(itemStyle: Int) {
        if (itemStyle == ITEM_STYLE_ROUND_RIGHT ||
            itemStyle == ITEM_STYLE_ROUND_RECTANGLE ||
            itemStyle == ITEM_STYLE_DEFAULT
        ) {
            this.itemStyle = itemStyle
            itemBackground = navigationItemBackground()
        } else {
            Log.e(javaClass.name, context.getString(R.string.illegal_arg_message))
        }
    }

    private fun navigationItemBackground(): Drawable? {
        // Set Resource
        val resource = when (itemStyle) {
            ITEM_STYLE_DEFAULT -> R.drawable.navigation_item_background_default
            ITEM_STYLE_ROUND_RIGHT -> R.drawable.navigation_item_background_rounded_right
            else -> R.drawable.navigation_item_background_rounded_rect
        }
        var background = AppCompatResources.getDrawable(context, resource)
        if (background != null) {
            val tint = AppCompatResources.getColorStateList(
                context, R.color.navigation_item_background_tint
            )

            background = DrawableCompat.wrap(background.mutate())

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                background.setTintList(tint)
            } else {
                DrawableCompat.setTintList(background, tint)
            }
        }
        return background
    }

    companion object {
        const val ITEM_STYLE_DEFAULT = 1
        const val ITEM_STYLE_ROUND_RIGHT = 2
        const val ITEM_STYLE_ROUND_RECTANGLE = 3
    }
}
