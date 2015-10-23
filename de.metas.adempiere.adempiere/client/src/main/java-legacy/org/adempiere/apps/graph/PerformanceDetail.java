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

import org.compiere.Adempiere;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.model.MGoal;
import org.compiere.swing.CFrame;


/**
 * 	Performance Detail Frame.
 * 	BarPanel for Drill-Down
 *	
 *  @author Jorg Janke
 *  @version $Id: PerformanceDetail.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class PerformanceDetail extends CFrame
	implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5994488373513922522L;

	/**
	 * 	Constructor.
	 * 	Called from PAPanel, ViewPI (Performance Indicator)
	 *	@param goal goal
	 */
	public PerformanceDetail (MGoal goal)
	{
		super (goal.getName());
		setIconImage(Adempiere.getProductIconSmall());
		barPanel = new Graph(goal, true);
		init();
		AEnv.addToWindowManager(this);
		AEnv.showCenterScreen(this);
	}	//	PerformanceDetail

	Graph barPanel = null;
	ConfirmPanel confirmPanel = ConfirmPanel.newWithOK();

	/**
	 * 	Static init
	 */
	private void init()
	{
		getContentPane().add(barPanel, BorderLayout.CENTER);
		getContentPane().add(confirmPanel, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
	}	//	init
	
	/**
	 * 	Action Listener
	 *	@param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
			dispose();
	}	//	actionPerformed
	
}	//	PerformanceDetail
