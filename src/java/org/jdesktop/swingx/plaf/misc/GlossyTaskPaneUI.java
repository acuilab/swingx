/*
 * $Id$
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */
package org.jdesktop.swingx.plaf.misc;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.plaf.basic.BasicTaskPaneUI;

/**
 * Paints the JXTaskPane with a gradient in the title bar.
 * 
 * @author <a href="mailto:fred@L2FProd.com">Frederic Lavigne</a>
 */
public class GlossyTaskPaneUI extends BasicTaskPaneUI {

  public static ComponentUI createUI(JComponent c) {
    return new GlossyTaskPaneUI();
  }

  protected Border createPaneBorder() {
    return new GlossyPaneBorder();
  }
  
  /**
   * Overriden to paint the background of the component but keeping the rounded
   * corners.
   */
  public void update(Graphics g, JComponent c) {
    if (c.isOpaque()) {
      g.setColor(c.getParent().getBackground());
      g.fillRect(0, 0, c.getWidth(), c.getHeight());
      g.setColor(c.getBackground());
      g.fillRect(0, ROUND_HEIGHT, c.getWidth(), c.getHeight() - ROUND_HEIGHT);
    }
    paint(g, c);
  }

  /**
   * The border of the taskpane group paints the "text", the "icon", the
   * "expanded" status and the "special" type.
   *  
   */
  class GlossyPaneBorder extends PaneBorder {
    
    protected void paintTitleBackground(JXTaskPane group, Graphics g) {
      if (group.isSpecial()) {
        g.setColor(specialTitleBackground);
        g.fillRoundRect(
          0,
          0,
          group.getWidth(),
          ROUND_HEIGHT * 2,
          ROUND_HEIGHT,
          ROUND_HEIGHT);
        g.fillRect(
          0,
          ROUND_HEIGHT,
          group.getWidth(),
          TITLE_HEIGHT - ROUND_HEIGHT);
      } else {
        Paint oldPaint = ((Graphics2D)g).getPaint();
        GradientPaint gradient =
          new GradientPaint(
            0f,
            0f, //group.getWidth() / 2,
            titleBackgroundGradientStart,
            0f, //group.getWidth(),
            TITLE_HEIGHT,
            titleBackgroundGradientEnd);
                
        ((Graphics2D)g).setRenderingHint(
          RenderingHints.KEY_COLOR_RENDERING,
          RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        ((Graphics2D)g).setRenderingHint(
          RenderingHints.KEY_INTERPOLATION,
          RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        ((Graphics2D)g).setRenderingHint(
          RenderingHints.KEY_RENDERING,
          RenderingHints.VALUE_RENDER_QUALITY);
        ((Graphics2D)g).setPaint(gradient);
        
        g.fillRoundRect(
          0,
          0,
          group.getWidth(),
          ROUND_HEIGHT * 2,
          ROUND_HEIGHT,
          ROUND_HEIGHT);
        g.fillRect(
          0,
          ROUND_HEIGHT,
          group.getWidth(),
          TITLE_HEIGHT - ROUND_HEIGHT);
        ((Graphics2D)g).setPaint(oldPaint);
      }
      
      Rectangle oldRect = g.getClipBounds();
      g.setClip(0, 0, group.getWidth(), TITLE_HEIGHT);
      g.setColor(borderColor);
      g.drawRoundRect(
        0,
        0,
        group.getWidth() - 1,
        TITLE_HEIGHT + ROUND_HEIGHT,
        ROUND_HEIGHT,
        ROUND_HEIGHT);
      g.drawLine(0, TITLE_HEIGHT - 1, group.getWidth(), TITLE_HEIGHT - 1);
      g.setClip(oldRect);      
    }

    protected void paintExpandedControls(JXTaskPane group, Graphics g, int x,
      int y, int width, int height) {
      ((Graphics2D)g).setRenderingHint(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
      
      paintOvalAroundControls(group, g, x, y, width, height);
      g.setColor(getPaintColor(group));
      paintChevronControls(group, g, x, y, width, height);
      
      ((Graphics2D)g).setRenderingHint(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    @Override
    protected boolean isMouseOverBorder() {
      return true;
    }

  }

}
