/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
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
package org.compiere.pos;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Properties;

import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.compiere.apps.AppsAction;
import org.compiere.apps.ConfirmPanel;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.MPOS;
import org.compiere.swing.CButton;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

public abstract class PosQuery extends CDialog implements MouseListener, ListSelectionListener, ActionListener {

	protected Properties p_ctx;
	/** POS Panel							*/
	protected PosBasePanel p_posPanel = null;
	/**	Underlying POS Model				*/
	protected MPOS p_pos = null;
	/** The Table					*/
	protected PosTable m_table;
	protected CPanel northPanel;
	protected CScrollPane centerScroll;
	protected ConfirmPanel confirm;
	protected CButton f_up;
	protected CButton f_down;
	/**	Logger			*/
	protected static CLogger log = CLogger.getCLogger(QueryProduct.class);

	public PosQuery() throws HeadlessException {
		super();
	}

	protected abstract void close();

	public abstract void reset();

	public abstract void actionPerformed(ActionEvent e);

	public void dispose() {
		removeAll();
		northPanel = null;
		centerScroll = null;
		confirm = null;
		m_table = null;
		super.dispose();
	}

	protected abstract void init();
	protected abstract void enableButtons();

	/**
	 * 	Constructor
	 */
	public PosQuery (PosBasePanel posPanel)
	{
		super(Env.getFrame(posPanel), true);
		p_posPanel = posPanel;
		p_pos = posPanel.p_pos;
		p_ctx = p_pos.getCtx();
		init();
		pack();
		setLocationByPlatform(true);
	}	//	PosQueryBPartner

	/**
	 *  Mouse Clicked
	 *  @param e event
	 */
	public void mouseClicked(MouseEvent e)
	{
		//  Single click with selected row => exit
		if (e.getClickCount() > 0 && m_table.getSelectedRow() != -1)
		{
			enableButtons();
			close();
		}
	}   //  mouseClicked

	public void mouseEntered (MouseEvent e) {}
	public void mouseExited (MouseEvent e) {}
	public void mousePressed (MouseEvent e) {}
	public void mouseReleased (MouseEvent e) {}
	
	/**
	 * 	Table selection changed
	 *	@param e event
	 */
	public void valueChanged (ListSelectionEvent e)
	{
		if (e.getValueIsAdjusting())
			return;
		enableButtons();
	}	//	valueChanged
	
	/**
	 * 	Create Action Button
	 *	@param action action 
	 *	@return button
	 */
	protected CButton createButtonAction(String action, KeyStroke accelerator) {
		AppsAction act = new AppsAction(action, accelerator, false);
		act.setDelegate(this);
		CButton button = (CButton)act.getButton();
		button.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		button.setMinimumSize(getPreferredSize());
		button.setMaximumSize(getPreferredSize());
		button.setFocusable(false);
		return button;
	}	//	getButtonAction

}