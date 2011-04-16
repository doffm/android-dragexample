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
import android.widget.ImageView;
import android.graphics.Point;

import android.content.Context;
import android.util.AttributeSet;

import com.doffman.dragarea.*;

class DraggableDot extends ImageView
{
  private void initDraggableDot()
  {
    // TODO Nothing for now.
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

  public void setDragArea(final DragArea dragArea)
  {
    this.setOnTouchListener(new View.OnTouchListener()
    {
      @Override
      public boolean onTouch(View view, MotionEvent event)
      {
        if (event.getAction() == MotionEvent.ACTION_DOWN) { 
          dragArea.startDrag(DraggableDot.this.getDrawable(),
            new Point((int)event.getX(), (int)event.getY()));
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
            DraggableDot.this.setImageResource(R.drawable.white_dot);
            break;
          case DragEvent.ACTION_DRAG_ENTERED:
            DraggableDot.this.setImageResource(R.drawable.green_dot);
            break;
          case DragEvent.ACTION_DRAG_EXITED:
            DraggableDot.this.setImageResource(R.drawable.white_dot);
            break;
          case DragEvent.ACTION_DROP:
          case DragEvent.ACTION_DRAG_ENDED:
            DraggableDot.this.setImageResource(R.drawable.red_dot);
            break;
          default:
            break;
        }
      }
    });
  }
}
