/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.apps.search;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.font.TextLayout;
import java.sql.Timestamp;

import javax.swing.JComponent;

import org.compiere.model.MAssignmentSlot;
import org.compiere.plaf.CompiereUtils;
import org.compiere.util.SwingUtils;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *	Schedule Time Panel.
 *  Manages slot layout
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: VScheduleTimePanel.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class VScheduleTimePanel extends JComponent
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2498994787352243360L;

	public VScheduleTimePanel()
	{
		setOpaque(false);
		setSize();
	}

	/**	Time Slots					*/
	private MAssignmentSlot[] 	m_timeSlots = null;
	private String[]			m_lines = new String [] {""};


	/** Line Height					*/
	public static int			LINE_HEIGHT = 35;
	/**	Heading Space				*/
	public static int			HEADING = 25;
	/**	Used Font					*/
	private Font				m_font = new Font("serif", Font.PLAIN, 12);
	/** Line Width					*/
	private int					m_width = 120;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(VScheduleTimePanel.class);

	/**
	 * 	Set Time Slots and calculate width
	 * 	@param timeSlots time slots
	 */
	public void setTimeSlots (MAssignmentSlot[] timeSlots)
	{
		log.debug( "VScheduleTimePanel.setTimeSlots");
		m_timeSlots = timeSlots;
		m_lines = new String[m_timeSlots.length];
		//
		FontMetrics fm = null;
		Graphics g = getGraphics();
		if (g == null)
			g = SwingUtils.getGraphics(this);
		if (g != null)
			fm = g.getFontMetrics(m_font);		//	the "correct" way
		else
		{
			log.error("No Graphics");
		//	fm = getToolkit().getFontMetrics(m_font);
		}
		m_width = 0;
		for (int i = 0; i < m_lines.length; i++)
		{
			m_lines[i] = m_timeSlots[i].getInfoTimeFrom();
			int width = 0;
			if (fm != null)
				width = fm.stringWidth(m_lines[i]);
			if (width > m_width)
				m_width = width;
		}
		setSize();
	//	repaint();
	}	//	setTimeSlots

	/**
	 * 	Calculate & Set Size
	 */
	private void setSize()
	{
		//	Width
		int width = m_width + 10;	//	slack
		if (width <= 10)
			width = 120;			//	default size

		//	Height
		int height = LINE_HEIGHT;
		int lines = m_lines.length;
		if (lines < 2)
			height *= 10;		//	default
		else
			height *= lines;
		height += HEADING;
		//
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
	}	//	setSize

	/**
	 * 	Get the height of the Header
	 * 	@return height of the header
	 */
	public int getHeaderHeight()
	{
		return HEADING;
	}	//	getHeaderHeight

	/**
	 * 	Get the height of a slot
	 * 	@return height of a slot
	 */
	public int getSlotHeight()
	{
		int height = getPreferredSize().height;
		int part = (height-HEADING) / m_lines.length;
		return part;
	}	//	getSlotHeight

	/**
	 * 	Get the height of a slot
	 * 	@return height of a slot
	 */
	public int getSlotCount()
	{
		return m_lines.length;
	}	//	getSlotCount

	/**
	 * 	Get Y start position of Slot
	 *  @param slot slot index
	 * 	@return y start position
	 */
	public int getSlotYStart (int slot)
	{
		int part = getSlotHeight();
		int y = HEADING + (slot * part);
		return y;
	}	//	getSlotYStart

	/**
	 * 	Get Y end position of Slot
	 *  @param slot slot index
	 * 	@return y end position
	 */
	public int getSlotYEnd (int slot)
	{
		int part = getSlotHeight();
		int y = HEADING + ((slot+1) * part);
		return y;
	}	//	getSlotYEnd

	/**
	 * 	Return the Time Slot index for the time.
	 *  Based on start time and not including end time
	 * 	@param time time (day is ignored)
	 *  @param endTime if true, the end time is included
	 * 	@return slot index
	 */
	public int getTimeSlotIndex (Timestamp time, boolean endTime)
	{
		//	Just one slot
		if (m_timeSlots.length <= 1)
			return 0;
		//	search for it
		for (int i = 0; i < m_timeSlots.length; i++)
		{
			if (m_timeSlots[i].inSlot (time, endTime))
				return i;
		}
		log.error("VScheduleTimePanel.getSlotIndex - did not find Slot for " + time + " end=" + endTime);
		return 0;
	}	//	getTimeSlotIndex

	/**
	 * 	Get Time Slot
	 * 	@param index time index
	 * 	@return Assignment Slot
	 */
	public MAssignmentSlot getTimeSlot (int index)
	{
		if (index < 0 || index > m_timeSlots.length)
			return null;
		return m_timeSlots[index];
	}	//	getTimeSlot

	/**
	 * 	Get Time Slot Index
	 * 	@param yPos Y position
	 * 	@return Assignment Slot Index
	 */
	public int getTimeSlotIndex (int yPos)
	{
		int index = yPos - getHeaderHeight();
		index /= getSlotHeight();
		if (index < 0)
			return 0;
		if (index >= m_timeSlots.length)
			return m_timeSlots.length-1;
		return index;
	}	//	getTimeSlotIndex

	/*************************************************************************/

	/**
	 *	Paint it
	 * 	@param g the <code>Graphics</code> object
	 */
	@Override
	public void paint (Graphics g)
	{
	//	log.debug( "VScheduleTimePanel.paint", g.getClip());
		Graphics2D g2D = (Graphics2D)g;
		g2D.setFont(m_font);
		Dimension size = getPreferredSize();
		int w = size.width;
		int h = size.height;

		//	Paint Background
		g2D.setPaint(Color.white);
		g2D.fill3DRect(1, 1,  w-2, h-2, true);

		//	Header Background
		Rectangle where = new Rectangle(0, 0, w, getHeaderHeight());
		CompiereUtils.paint3Deffect(g2D, where, false, true);

		//	heading
		TextLayout layout = null;
	//	layout = new TextLayout ("Heading", m_font, g2D.getFontRenderContext());
	//	float hh = layout.getAscent() + layout.getDescent();
	//	layout.draw (g2D, (w - layout.getAdvance())/2,		//	center
	//		((HEADING - hh)/2) + layout.getAscent());		//	center

		//	horizontal lines & text
		g2D.setStroke(getStroke(true));
		for (int i = 0; i < m_lines.length; i++)
		{
			int yy = getSlotYStart(i);
			if (m_lines[i] != null && m_lines[i].length() > 0)
			{
				layout = new TextLayout (m_lines[i], m_font, g2D.getFontRenderContext());
				g2D.setPaint(Color.blue);
				layout.draw (g2D, w - layout.getAdvance() - 3,	//	right aligned with 2 pt space
					yy + layout.getAscent() + layout.getLeading());	//	top aligned with leading space
			}
			//
			g2D.setPaint(Color.gray);
			g2D.drawLine(2, yy,  w-2, yy);	//	top horiz line
		}

		//	Paint Borders
		g2D.setPaint(Color.black);
		g2D.setStroke(getStroke(false));
		g2D.drawLine(1, 1,  	1, h-1);	//	left
		g2D.drawLine(w-1, 1,  	w-1, h-1);	//	right
		g2D.drawLine(1, 1,		w-1, 1);	//	top
		g2D.drawLine(1, getHeaderHeight(),	w-1, getHeaderHeight());	//	header
		g2D.drawLine(1, h-1,		w-1, h-1);	//	bottom line
	}	//	paintComponent

	/**
	 * 	Get Stroke
	 * 	@param slotLine if true return dashed line
	 * 	@return Stroke
	 */
	public static Stroke getStroke (boolean slotLine)
	{
		if (slotLine)
			return new BasicStroke (1.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 1.0f,
				new float[] {2.0f, 0.5f}, 0.0f);
		return new BasicStroke(1.0f);
	}	//	getStroke

}	//	VScheduleTimePanel
