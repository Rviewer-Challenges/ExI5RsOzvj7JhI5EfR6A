package com.fernan.game.memory.ui.adapters

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.graphics.Camera
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.fernan.game.memory.R
import com.fernan.game.memory.data.model.GameBean
import com.fernan.game.memory.data.model.State
import com.fernan.game.memory.databinding.ItemGameBinding
import com.fernan.game.memory.provider.Player
import com.fernan.game.memory.ui.events.AnimationListener
import com.fernan.game.memory.utils.Utils.loadFrom
import com.fernan.game.memory.utils.Utils.printLog
import com.fernan.game.memory.utils.Utils.setSize
import com.google.android.material.card.MaterialCardView

class GameAdapter(
    var listGame: MutableList<GameBean>,
    val context: Context,
    val sizeItem: Int,
    var listen: com.fernan.game.memory.ui.events.State? = null
) : RecyclerView.Adapter<GameAdapter.GameHolder>() {

    private var countFounds:Int = 0
    init {
        countFounds  = listGame.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGameBinding.inflate(layoutInflater, parent, false)
        return GameHolder(binding)

    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.bind(listGame[holder.adapterPosition])
    }
    private fun resetAlls(){
        listGame.forEach{
            it.show =  false
        }
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return listGame.size
    }

    private var countShow = 0
    private var selectOne = -1
    private var selectTwo = -1
    private var tapsUser = 0
    inner class GameHolder
        (private val bin: ItemGameBinding) : RecyclerView.ViewHolder(bin.root){
        init {
            bin.rootView.setSize(sizeItem,sizeItem)
        }

        fun bind(item: GameBean) {
            // Testing
            // bin.executePendingBindings()

            bin.image.loadFrom(item.image,200,200)

            // bindingXml
            bin.root.isVisible = item.state != State.FOUND

            bin.cardFront.isVisible = true
            bin.cardBack.isVisible = true
            itemView.setOnClickListener {

                countFounds.printLog("countFounds")

                tapsUser++
                if (item.show) {

                } else {

                    bin.root.isClickable = false
                    if(countShow==0){
                        selectOne = adapterPosition
                    }
                    if(countShow==1){
                        selectTwo = adapterPosition
                    }
                    countShow++

                    flip(itemView,bin.cardFront,bin.cardBack,){ finish ->
                        if(finish){
                            bin.root.isClickable = true
                            if(countShow == 2){
                                countShow = 0


                                Log.d("itemTesting","verificar 2 ${listGame[selectOne].image == listGame[selectTwo].image} >> pos ${selectOne} == ${selectTwo}")
                                if(listGame[selectOne].image == listGame[selectTwo].image){
                                    listGame[selectOne].state = State.FOUND
                                    listGame[selectTwo].state = State.FOUND

                                    // Play Music Correct
                                    Player.correct(context)

                                    notifyItemChanged(selectOne)
                                    notifyItemChanged(selectTwo)

                                    selectOne = -1
                                    selectTwo = -1

                                    countFounds--
                                    countFounds--
                                    if(countFounds==0){
                                        listen?.onFoundAlls(tapsUser,listGame.size)
                                    }

                                } else {
                                    selectOne = -1
                                    selectTwo = -1
                                    resetAlls()
                                }
                            }
                        }

                    }

                }
            }

        }

    }

    private fun flipCard(
        context: Context,
        visibleView: MaterialCardView,
        inVisibleView: MaterialCardView,
        listen: AnimationListener?,
    ) {
        try {
            listen?.onStart()
            /*
            listen?.onEnd()
            if(true){
                visibleView.visibility = View.VISIBLE
                inVisibleView.visibility= View.GONE
                return
            }

             */
            visibleView.visibility = View.VISIBLE
            val scale = context.resources.displayMetrics.density
            val cameraDist = 8000 * scale
            visibleView.cameraDistance = cameraDist
            inVisibleView.cameraDistance = cameraDist
            val flipOutAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_out
                ) as AnimatorSet
            flipOutAnimatorSet.setTarget(inVisibleView)
            val flipInAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_in
                ) as AnimatorSet
            flipInAnimatorSet.setTarget(visibleView)
            flipOutAnimatorSet.start()
            flipInAnimatorSet.start()
            flipInAnimatorSet.doOnEnd {
                flipOutAnimatorSet.setTarget(null)
                flipInAnimatorSet.setTarget(null)


                inVisibleView.visibility= View.GONE
                listen?.onEnd()
            }
        } catch (e: Exception) {
        }
    }

    private fun flip(ROOT: View,FRONT: MaterialCardView,
                     BACK: MaterialCardView, finish : (Boolean) -> Unit) {
        // val flipAnimation = FlipAnimation(FRONT, BACK)
        // visibleView: MaterialCardView,
        // inVisibleView: MaterialCardView,
        val flipAnimation = FlipAnimation(FRONT, BACK)

        if (FRONT.visibility == View.GONE) {
            // flipAnimation.reverse()
        }
        ROOT.startAnimation(flipAnimation)
        flipAnimation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationEnd(p0: Animation?) {
                ROOT.clearAnimation()
                ROOT.animation = null
                finish(true)
            }

            override fun onAnimationRepeat(p0: Animation?) {
                TODO("Not yet implemented")
            }

            override fun onAnimationStart(p0: Animation?) {
                finish(false)
            }
        })
    }


    class FlipAnimation(private var fromView: View, private var toView: View) :
        Animation() {
        private var camera: Camera? = null
        private var centerX = 0f
        private var centerY = 0f
        private var forward = true
        fun reverse() {
            forward = false
            val switchView = toView
            toView = fromView
            fromView = switchView
        }

        override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
            super.initialize(width, height, parentWidth, parentHeight)
            centerX = (width / 2).toFloat()
            centerY = (height / 2).toFloat()
            camera = Camera()
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            // Angle around the y-axis of the rotation at the given time
            // calculated both in radians and degrees.
            val radians = Math.PI * interpolatedTime
            var degrees = (180.0 * radians / Math.PI).toFloat()

            // Once we reach the midpoint in the animation, we need to hide the
            // source view and show the destination view. We also need to change
            // the angle by 180 degrees so that the destination does not come in
            // flipped around
            if (interpolatedTime >= 0.5f) {
                degrees -= 180f
                fromView.visibility = View.GONE
                toView.visibility = View.VISIBLE
            }
            if (forward) degrees = -degrees // determines direction of rotation when
            // flip begins
            val matrix = t.matrix
            camera!!.save()
            camera!!.rotateY(degrees)
            camera!!.getMatrix(matrix)
            camera!!.restore()
            matrix.preTranslate(-centerX, -centerY)
            matrix.postTranslate(centerX, centerY)
        }

        /**
         * Creates a 3D flip animation between two views.
         *
         * @param fromView
         * First view in the transition.
         * @param toView
         * Second view in the transition.
         */
        init {
            duration = 700
            fillAfter = false
            interpolator = AccelerateDecelerateInterpolator()
        }
    }


}