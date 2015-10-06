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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.compiere.model.MGoal;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

/**
 * 	Performance Analysis Panel.
 * 	Key Performace Indicators
 *	
 *  @author Jorg Janke
 *  @version $Id: PAPanel.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class PAPanel extends CPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4937417260772233929L;

	/**
	 * 	Get Panel if User has Perfpormance Goals
	 *	@return panel pr null
	 */
	public static PAPanel get()
	{
		int AD_User_ID = Env.getAD_User_ID(Env.getCtx());
		MGoal[] goals = MGoal.getUserGoals(Env.getCtx(), AD_User_ID);
		if (goals.length == 0)
			return null;
		return new PAPanel(goals);
	}	//	get
	
	
	/**************************************************************************
	 * 	Constructor
	 *	@param goals
	 */
	private PAPanel (MGoal[] goals)
	{
		super ();
		m_goals = goals;
		init();
	}	//	PAPanel
	
	/** Goals			*/
	private MGoal[] 	m_goals = null;
	
	/**	Logger	*/
	private static CLogger log = CLogger.getCLogger (PAPanel.class);
	
	/**
	 * 	Static/Dynamic Init
	 */
	private void init()
	{
		/* BOXES:
		 * 
		 *  boxV   Header
		 *        ********
		 *          boxH
		 *
		 *  boxH  *		  *       *
		 *  	  * boxV1 * boxV2 *  
		 *        *       *       *
		 *
		 *  boxV1   dial1
		 *        ********
		 *          dial2
		 *          
		 *  boxV2   HTML
		 *        ********
		 *          boxH1
		 *        
		 *  boxH1 *		  *       *
		 *  	  * bar1  * bar2  *  
		 *        *       *       *
		 *        
		 *        
		 *   V1 + HTML in scrollpane
		 */
		
		this.setLayout(new BorderLayout());
		
		// HEADER
		Box boxV = Box.createVerticalBox();
		// DIALS/HTML+Bars
		Box boxH =  Box.createHorizontalBox();
		// DIALS
		Box boxV1 = Box.createVerticalBox();
		// HTML/Bars
		Box boxV2 = Box.createVerticalBox();
		// barChart
		Box boxH1 = Box.createHorizontalBox();
		//boxH_V.setPreferredSize(new Dimension(180, 1500));
		//boxH1.setPreferredSize(new Dimension(400, 180));		
		boxV2.setPreferredSize(new Dimension(120, 120));
		
	    // DIALS below HEADER, LEFT
		for (int i = 0; i < m_goals.length; i++)
		{
			PerformanceIndicator pi = new PerformanceIndicator(m_goals[i]);
			pi.addActionListener(this);
			boxV1.add (pi, BorderLayout.NORTH);
		}
		boxV1.add(Box.createVerticalGlue(), BorderLayout.CENTER);
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    scrollPane.getViewport().add(boxV1, BorderLayout.CENTER );
	    scrollPane.setMinimumSize(new Dimension(190, 180));

		// RIGHT, HTML + Bars
	    HtmlDashboard contentHtml = new HtmlDashboard("http:///local/home", m_goals, true);
	    boxV2.add(contentHtml, BorderLayout.CENTER);
	    
		for (int i = 0; i < java.lang.Math.min(2, m_goals.length); i++)
		{
			if (m_goals[i].getMeasure() != null) //MGoal goal = pi.getGoal();
				boxH1.add ( new Graph(m_goals[i]), BorderLayout.SOUTH);
		}
	    boxV2.add(boxH1, BorderLayout.SOUTH);
	    
	    // below HEADER
	    boxH.add(scrollPane, BorderLayout.WEST );
		boxH.add(Box.createHorizontalStrut(5)); //space
		boxH.add(boxV2, BorderLayout.CENTER);
		
		// HEADER + below
		HtmlDashboard t = new HtmlDashboard("http:///local/logo", null, false);
		t.setMaximumSize(new Dimension(2000,80));
		//t.setPreferredSize(new Dimension(200,10));
		//t.setMaximumSize(new Dimension(2000,10));
		boxV.add(t, BorderLayout.NORTH);
		boxV.add(Box.createVerticalStrut(5)); //space		
		boxV.add(boxH, BorderLayout.CENTER);
		boxV.add(Box.createVerticalGlue());

		// WINDOW
		add(boxV, BorderLayout.CENTER);
	}	//	init

	/**
	 * 	Action Listener for Drill Down
	 *	@param e event
	 */
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource() instanceof PerformanceIndicator)
		{
			PerformanceIndicator pi = (PerformanceIndicator)e.getSource();
			log.info(pi.getName());
			MGoal goal = pi.getGoal();
			if (goal.getMeasure() != null)
				new PerformanceDetail(goal);
		}
	}	//	actionPerformed

}	//	PAPanel
