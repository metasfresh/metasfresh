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
package org.adempiere.apps.graph;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.BoxLayout;

import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.model.MGoal;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

/**
 * 	View Performance Indicators
 *	
 *  @author Jorg Janke
 *  @version $Id: ViewPI.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class ViewPI extends CPanel
implements FormPanel, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8022906851004145960L;


	/**
	 * 	Init
	 *	@param WindowNo
	 *	@param frame
	 */
	public void init (int WindowNo, FormFrame frame)
	{
		log.fine("");
		m_WindowNo = WindowNo;
		m_frame = frame;
		try
		{
			//	Top Selection Panel
			//	m_frame.getContentPane().add(selectionPanel, BorderLayout.NORTH);
			//	Center
			initPanel();
			CScrollPane scroll = new CScrollPane (this);
			m_frame.getContentPane().add(scroll, BorderLayout.CENTER);
			//	South
			confirmPanel.addActionListener(this);
			m_frame.getContentPane().add(confirmPanel, BorderLayout.SOUTH);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}
		sizeIt();
	}	//	init

	/**
	 * 	Size Window
	 */
	private void sizeIt()
	{
		//	Frame
		m_frame.pack();
		//	Dimension size = m_frame.getPreferredSize();
		//	size.width = WINDOW_WIDTH;
		//	m_frame.setSize(size);
	}	//	size

	/**
	 * 	Dispose
	 */
	public void dispose()
	{
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
		removeAll();
	}	//	dispose

	/**	Window No					*/
	private int         m_WindowNo = 0;
	/**	FormFrame					*/
	private FormFrame 	m_frame;
	/**	Logger	*/
	private static CLogger log = CLogger.getCLogger (ViewPI.class);
	/** Confirmation Panel			*/
	private ConfirmPanel confirmPanel = new ConfirmPanel();


	/**
	 * 	Init Panel
	 */
	private void initPanel()
	{
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		MGoal[] goals = MGoal.getGoals(Env.getCtx());
		for (int i = 0; i < goals.length; i++)
		{
			PerformanceIndicator pi = new PerformanceIndicator(goals[i]);
			pi.addActionListener(this);
			add (pi);
		}
	}	//	initPanel


	/**
	 * 	Action Listener for Drill Down
	 *	@param e event
	 */
	public void actionPerformed (ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
			dispose();
		else if (e.getSource() instanceof PerformanceIndicator)
		{
			PerformanceIndicator pi = (PerformanceIndicator)e.getSource();
			log.info(pi.getName());
			MGoal goal = pi.getGoal();
			if (goal.getMeasure() != null)
				new PerformanceDetail(goal);
		}
	}	//	actionPerformed

}	//	ViewPI
