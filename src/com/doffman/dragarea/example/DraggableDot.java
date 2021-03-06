/*
 * Copyright (C) 2011 by Mark Doffman
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE
 */
package com.doffman.dragarea.example;

import android.view.View;
import android.view.MotionEvent;
import android.widget.TextView;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

import android.os.Bundle;

import com.doffman.dragarea.*;

class DraggableDot extends TextView
{
  private Drawable mWhiteDot;
  private Drawable mRedDot;
  private Drawable mGreenDot;
  private Drawable mTranslucentDot;

  private void initDraggableDot()
  {
    Resources res = getContext().getResources();

    mWhiteDot       = res.getDrawable(R.drawable.white_dot);
    mRedDot         = res.getDrawable(R.drawable.red_dot);
    mGreenDot       = res.getDrawable(R.drawable.green_dot);
    mTranslucentDot = res.getDrawable(R.drawable.translucent_dot);
  }

  public DraggableDot(Context context)
  {
    super(context);
    initDraggableDot();
  }

  public DraggableDot(Context context, AttributeSet attrs)
  {
    super(context, attrs);
    initDraggableDot();
  }

  public DraggableDot(Context context, AttributeSet attrs, int defStyle)
  {
    super(context, attrs, defStyle);
    initDraggableDot();
  }

  public void setDragArea(final DragArea dragArea, final TextView reportView)
  {
    this.setOnTouchListener(new View.OnTouchListener()
    {
      @Override
      public boolean onTouch(View view, MotionEvent event)
      {
        if (event.getAction() == MotionEvent.ACTION_DOWN) { 
          Bundle data = new Bundle();
          data.putCharSequence("number", getText());
          dragArea.startDrag(data,
                             new DrawableDragShadowBuilder(
                                                  DraggableDot.this,
                                                  mTranslucentDot,
                                                  new Point((int)event.getX() - getPaddingLeft(),
                                                            (int)event.getY() - getPaddingTop())));
          return true;
        } else {
          return false;
        }
      }
    });

    dragArea.addDragListener(DraggableDot.this, new OnDragListener()
    {
      @Override
      public void onDrag(View view, DragEvent dragEvent)
      {
        switch (dragEvent.getAction())
        {
          case DragEvent.ACTION_DRAG_STARTED:
            DraggableDot.this.setBackgroundDrawable(mGreenDot);
            break;
          case DragEvent.ACTION_DRAG_ENTERED:
            DraggableDot.this.setBackgroundDrawable(mWhiteDot);
            break;
          case DragEvent.ACTION_DRAG_EXITED:
            DraggableDot.this.setBackgroundDrawable(mGreenDot);
            break;
          case DragEvent.ACTION_DROP:
            final Bundle data = dragEvent.getBundle();
            final CharSequence dropText = data.getCharSequence("number");

            int a = Integer.parseInt(dropText.toString());
            int b = Integer.parseInt(DraggableDot.this.getText().toString());
            reportView.setText(dropText + " + " + DraggableDot.this.getText() + " = " + Integer.toString(a + b));
            DraggableDot.this.setBackgroundDrawable(mRedDot);
            break;
          case DragEvent.ACTION_DRAG_ENDED:
            DraggableDot.this.setBackgroundDrawable(mRedDot);
            break;
          default:
            break;
        }
      }
    });
  }
}
