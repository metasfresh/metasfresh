package org.adempiere.plaf;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalScrollBarUI;

/**
 * metas Fresh Scrollbar UI: it simply draws a rectangle.
 * 
 * @author tsa
 *
 */
public class MetasFreshScrollBarUI extends MetalScrollBarUI
{
	/** the UI Class ID to bind this UI to */
	public static final String uiClassID = AdempierePLAF.getUIClassID(JScrollBar.class, "ScrollBarUI");

	/** The width of the scrollbar */
	public static final String KEY_Width = "MetasFreshScrollBarUI.Width";
	public static final int DEFAULT_Width = 10;

	/** The background color of the whole scrollbar rectangle */
	public static final String KEY_Track_Color = "MetasFreshScrollBarUI.Track.Color";
	private static final ColorUIResource DEFAULT_Track_Color = new ColorUIResource(new Color(0, 0, 0, 0)); // transparent

	/** The background color of scrollbar's thumb rectangle (the one which user drags) */
	public static final String KEY_Thumb_Color = "MetasFreshScrollBarUI.Thumb.Color";
	private static final ColorUIResource DEFAULT_Thumb_Color = MetasFreshTheme.COLOR_LightGray;

	private static final String KEY_Thumb_MouseOver_Color = "MetasFreshScrollBarUI.Thumb.MouseOver.Color";
	private static final ColorUIResource DEFAULT_Thumb_MouseOver_Color = new ColorUIResource(149, 149, 149);
	private static final String KEY_Thumb_Dragging_Color = "MetasFreshScrollBarUI.Thumb.Dragging.Color";
	private static final ColorUIResource DEFAULT_Thumb_Dragging_Color = new ColorUIResource(125, 125, 125);

	public static ComponentUI createUI(final JComponent c)
	{
		return new MetasFreshScrollBarUI();
	}

	public static final Object[] getUIDefaults()
	{
		return new Object[] {
				uiClassID, MetasFreshScrollBarUI.class.getName()
				, KEY_Width, DEFAULT_Width
				, KEY_Track_Color, DEFAULT_Track_Color
				, KEY_Thumb_Color, DEFAULT_Thumb_Color
				, KEY_Thumb_MouseOver_Color, DEFAULT_Thumb_MouseOver_Color
				, KEY_Thumb_Dragging_Color, DEFAULT_Thumb_Dragging_Color
		};
	}

	private final JButton noButton = new JButton()
	{
		private static final long serialVersionUID = 1L;

		@Override
		public Dimension getPreferredSize()
		{
			return new Dimension(0, 0);
		}

	};

	private Color thumbColor;
	private Color thumbColorMouseOver;
	private Color thumbColorDragging;
	
	private boolean isMouseButtonPressed = false;

	MetasFreshScrollBarUI()
	{
		super();
		noButton.setVisible(false);
		noButton.setEnabled(false);
	}

	@Override
	protected void installDefaults()
	{
		super.installDefaults();

		trackColor = AdempierePLAF.getColor(KEY_Track_Color);
		
		thumbColor = AdempierePLAF.getColor(KEY_Thumb_Color, DEFAULT_Thumb_Color);
		thumbColorMouseOver = AdempierePLAF.getColor(KEY_Thumb_MouseOver_Color, DEFAULT_Thumb_MouseOver_Color);
		thumbColorDragging = AdempierePLAF.getColor(KEY_Thumb_Dragging_Color, DEFAULT_Thumb_Dragging_Color);

		scrollBarWidth = AdempierePLAF.getInt(KEY_Width, DEFAULT_Width);
	}

	@Override
	protected void paintThumb(final Graphics g, final JComponent c, final Rectangle r)
	{
		final Color color;
		if (!scrollbar.isEnabled())
		{
			color = thumbColor;
		}
		else if (isDragging || isMouseButtonPressed)
		{
			color = thumbColorDragging;
		}
		else if (isThumbRollover())
		{
			color = thumbColorMouseOver;
		}
		else
		{
			color = thumbColor;
		}
		
		g.setColor(color);
		g.fillRect(r.x, r.y, r.width, r.height);
	}

	@Override
	protected void paintTrack(final Graphics g, final JComponent c, final Rectangle r)
	{
		g.setColor(trackColor);
		g.fillRect(r.x, r.y, r.width, r.height);
	}

	@Override
	protected JButton createDecreaseButton(final int orientation)
	{
		return noButton;
	}

	@Override
	protected JButton createIncreaseButton(final int orientation)
	{
		return noButton;
	}
	
	@Override
	protected TrackListener createTrackListener()
	{
		return new MetasTrackListener();
	}
	
    private class MetasTrackListener extends TrackListener
    {
    	@Override
    	public void mousePressed(MouseEvent e)
    	{
    		isMouseButtonPressed = true;
    		super.mousePressed(e);
    		
    		scrollbar.repaint(getThumbBounds());
    	}
    	
    	@Override
    	public void mouseReleased(MouseEvent e)
    	{
    		isMouseButtonPressed = false;
    		super.mouseReleased(e);
    		
    		scrollbar.repaint(getThumbBounds());
    	}
    }

}
