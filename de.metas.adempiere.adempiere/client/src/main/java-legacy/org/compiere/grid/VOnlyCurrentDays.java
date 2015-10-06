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
package org.compiere.grid;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;

import javax.swing.WindowConstants;

import org.compiere.swing.CButton;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 *	Queries how many days back history is displayed as current
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: VOnlyCurrentDays.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 *  
 *  @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *  			<li>BF [ 1824621 ] History button can't be canceled
 */
public class VOnlyCurrentDays extends CDialog
	implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2619306709535014989L;

	/**
	 *	Constructor
	 *  @param parent parent frame
	 *  @param buttonLocation lower left corner of the button
	 */
	public VOnlyCurrentDays(Frame parent, Point buttonLocation)
	{
		//	How long back in History?
		super (parent, Msg.getMsg(Env.getCtx(), "VOnlyCurrentDays", true), true);
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "VOnlyCurrentDays", e);
		}
		this.pack();
		buttonLocation.x -= (int)getPreferredSize().getWidth()/2;
		this.setLocation(buttonLocation);
		this.setVisible(true);
	}	//	VOnlyCurrentDays

	private CPanel mainPanel = new CPanel();
	private CButton bShowAll = new CButton();
	private CButton bShowMonth = new CButton();
	private CButton bShowWeek = new CButton();
	private CButton bShowDay = new CButton();
	private CButton bShowYear = new CButton();

	/**	Days (0=all)			*/
	private int 	m_days = 0;
	/** Is cancel				*/
	private boolean m_isCancel = false;
	/**	Margin					*/
	private static Insets	s_margin = new Insets (2, 2, 2, 2);
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(VOnlyCurrentDays.class);

	/**
	 * 	Static Initializer
	 * 	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		bShowAll.setText(Msg.getMsg(Env.getCtx(), "All"));
		bShowAll.addActionListener(this);
		bShowAll.setMargin(s_margin);
		bShowYear.setText(Msg.getMsg(Env.getCtx(), "Year"));
		bShowYear.addActionListener(this);
		bShowYear.setMargin(s_margin);
		bShowMonth.setText(Msg.getMsg(Env.getCtx(), "Month"));
		bShowMonth.addActionListener(this);
		bShowMonth.setMargin(s_margin);
		bShowWeek.setText(Msg.getMsg(Env.getCtx(), "Week"));
		bShowWeek.addActionListener(this);
		bShowWeek.setMargin(s_margin);
		bShowDay.setText(Msg.getMsg(Env.getCtx(), "Day"));
		bShowDay.addActionListener(this);
		bShowDay.setMargin(s_margin);
		bShowDay.setDefaultCapable(true);
		//
		mainPanel.add(bShowDay, null);
		mainPanel.add(bShowWeek, null);
		mainPanel.add(bShowMonth, null);
		mainPanel.add(bShowYear, null);
		mainPanel.add(bShowAll, null);
		//
		mainPanel.setToolTipText(Msg.getMsg(Env.getCtx(), "VOnlyCurrentDays", false));
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		this.getRootPane().setDefaultButton(bShowDay);
		// 
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				m_isCancel = true;
			}
		});
	}	//	jbInit

	/**
	 * 	Action Listener
	 * 	@param e evant
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == bShowDay)
			m_days = 1;
		else if (e.getSource() == bShowWeek)
			m_days = 7;
		else if (e.getSource() == bShowMonth)
			m_days = 31;
		else if (e.getSource() == bShowYear)
			m_days = 365;
		else
			m_days = 0;		//	all
		dispose();
	}	//	actionPerformed

	/**
	 * 	Get selected number of days
	 * 	@return days or -1 for all
	 */
	public int getCurrentDays()
	{
		return m_days;
	}	//	getCurrentDays

	/**
	 * @return true if user has canceled this form
	 */
	public boolean isCancel() {
		return m_isCancel;
	}
}	//	VOnlyCurrentDays
