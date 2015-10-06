/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.apps.wf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import org.compiere.apps.wf.WFIcon;
import org.compiere.wf.MWFNode;

/**
 * 
 * @author Low Heng Sin
 *
 */
public class WFNode {

	/**	Size of the Node				*/
	private static Dimension	s_size = new Dimension (120, 50);
	private MWFNode m_node;
	private WFIcon m_icon;
	private Rectangle m_bounds;
	
	/**
	 * 	Create WF Node
	 * 	@param node model
	 */
	public WFNode (MWFNode node) {
		m_node = node;
		m_icon = new WFIcon(node.getAction());		
		m_bounds = new Rectangle(m_node.getXPosition(), m_node.getYPosition(), s_size.width,
				s_size.height);
	}
	
	public void paint(Graphics2D g2D) {
		m_icon.paintIcon(null, g2D, 0, 0);
		//	Paint Text
		g2D.setPaint(Color.BLACK);
		Font base = new Font(null);
		Font font = new Font(base.getName(), Font.ITALIC | Font.BOLD, base.getSize());
		//
		AttributedString aString = new AttributedString(m_node.getName(true));
		aString.addAttribute(TextAttribute.FONT, font);
		aString.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
		AttributedCharacterIterator iter = aString.getIterator();
		//
		LineBreakMeasurer measurer = new LineBreakMeasurer(iter, g2D.getFontRenderContext());
		float width = s_size.width - m_icon.getIconWidth() - 2;
		TextLayout layout = measurer.nextLayout(width);
		float xPos = m_icon.getIconWidth();
		float yPos = layout.getAscent() + 2;
		//
		layout.draw(g2D, xPos, yPos);
		width = s_size.width - 4;	//	2 pt 
		while (measurer.getPosition() < iter.getEndIndex())
		{
			layout = measurer.nextLayout(width);
			yPos += layout.getAscent() + layout.getDescent() + layout.getLeading();
			layout.draw(g2D, 2, yPos);
		}
	}

	/**
	 * 
	 * @return AD_WF_Node_ID
	 */
	public int getAD_WF_Node_ID() {
		return m_node.getAD_WF_Node_ID();
	}

	public Rectangle getBounds() {
		return m_bounds;
	}
	
	public MWFNode getNode() {
		return m_node;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setBounds(int x, int y, int width, int height) {
		m_bounds = new Rectangle(x, y, width, height);
	}
}
