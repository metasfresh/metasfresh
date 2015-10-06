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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.KeyboardFocusManager;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Properties;
import java.util.Timer;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

import org.compiere.Adempiere;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.AKeyboardFocusManager;
import org.compiere.apps.ALogin;
import org.compiere.apps.AMenu;
import org.compiere.apps.StatusBar;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.apps.wf.WFActivity;
import org.compiere.apps.wf.WFPanel;
import org.compiere.grid.ed.VLocationDialog;
import org.compiere.grid.tree.VTreePanel;
import org.compiere.model.MBPartner;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrderTax;
import org.compiere.model.MPOS;
import org.compiere.model.MSession;
import org.compiere.swing.CDialog;
import org.compiere.swing.CFrame;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Msg;
import org.compiere.util.Splash;

/**
 *	Point of Sales Main Window.
 *
 *  @author Comunidad de Desarrollo OpenXpertya 
 *         *Basado en Codigo Original Modificado, Revisado y Optimizado de:
 *         *Copyright (c) Jorg Janke
 *  @version $Id: PosPanel.java,v 1.10 2004/07/12 04:10:04 jjanke Exp $
 */
public class PosBasePanel extends CPanel
	//implements FormPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3010214392188209281L;

	/**
	 * 	Constructor - see init 
	 */
	public PosBasePanel()
	{
		
		super (new MigLayout(" fill","[500!]10[300:350:, fill]",""));
		originalKeyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		m_focusMgr = new PosKeyboardFocusManager();
		KeyboardFocusManager.setCurrentKeyboardFocusManager(m_focusMgr);
	}	//	PosPanel

	/**	Window No			*/
	private int         	m_WindowNo = 0;
	/**	FormFrame			*/
	private CFrame 		m_frame;
	/**	Logger				*/
	private CLogger			log = CLogger.getCLogger(getClass());
	/** Context				*/
	private Properties		m_ctx = Env.getCtx();
	/** Sales Rep 			*/
	private int				m_SalesRep_ID = 0;
	/** POS Model			*/
	protected MPOS			p_pos = null;
	/** Keyoard Focus Manager		*/
	private PosKeyboardFocusManager	m_focusMgr = null;

	/** Order Panel				*/
	protected SubOrder 		f_order;
	/** Current Line				*/
	protected SubCurrentLine 	f_curLine;
	/** Function Keys				*/
	protected SubFunctionKeys 	f_functionKeys;
	
	protected CashSubFunctions 	f_cashfunctions;
	
	private javax.swing.Timer logoutTimer;


	PosOrderModel m_order = null;
	
	//	Today's (login) date		*/
	private Timestamp			m_today = Env.getContextAsDate(m_ctx, "#Date");
	
	private KeyboardFocusManager originalKeyboardFocusManager;
	private boolean debug = true;
	private CFrame frame;
	private HashMap<Integer, POSKeyboard> keyboards = new HashMap<Integer, POSKeyboard>();
	
	/**
	 *	Initialize Panel
	 *  @param WindowNo window
	 *  @param frame parent frame
	 */
	public void init (int WindowNo, CFrame frame)
	{
		this.frame = frame;
		if ( debug )
			frame.setPreferredSize(new Dimension(1024,768));
		else
		{
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setResizable(false);
		}
		
		m_SalesRep_ID = Env.getAD_User_ID(m_ctx);
		log.info("init - SalesRep_ID=" + m_SalesRep_ID);
		m_WindowNo = WindowNo;
		m_frame = frame;
		frame.setJMenuBar(null);
		//
		try
		{
			if (!dynInit())
			{
				dispose();
				frame.dispose();
				return;
			}
			frame.getContentPane().add(this, BorderLayout.CENTER);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "init", e);
		}
		log.config( "PosPanel.init - " + getPreferredSize());
		
		if ( p_pos.getAutoLogoutDelay() > 0 && logoutTimer == null )
		{
			logoutTimer = new javax.swing.Timer(1000,
					new ActionListener() {

				PointerInfo pi = null;
				long lastMouseMove  = System.currentTimeMillis();
				long lastKeyboardEvent = System.currentTimeMillis();
				public void actionPerformed(ActionEvent e) {
					long now = e.getWhen();
					PointerInfo newPi = MouseInfo.getPointerInfo();
					// mouse moved
					if ( newPi != null && pi != null 
							&& !pi.getLocation().equals(newPi.getLocation()) )
					{
						lastMouseMove = now;
					}
					pi = newPi;

					lastKeyboardEvent = m_focusMgr.getLastWhen();

					if ( p_pos.getAutoLogoutDelay()*1000 < now - Math.max(lastKeyboardEvent, lastMouseMove) )
					{
					//	new PosLogin(this);
					}
				}
			});
			logoutTimer.start();
		}
		m_focusMgr.start();
		
	}	//	init

	/**
	 * 	Dispose - Free Resources
	 */
	public void dispose()
	{
		keyboards.clear();
		keyboards = null;
		if ( logoutTimer != null )
			logoutTimer.stop();
		logoutTimer = null;
		
		if (m_focusMgr != null)
			m_focusMgr.stop();
		m_focusMgr = null;
		KeyboardFocusManager.setCurrentKeyboardFocusManager(originalKeyboardFocusManager);
		//
		if (f_order != null)
			f_order.dispose();
		f_order = null;
		if (f_curLine != null)
		{
			// if ( m_order != null )
			// 	m_order.deleteOrder();
			f_curLine.dispose();
		}
		f_curLine = null;
		if (f_functionKeys != null)
			f_functionKeys.dispose();
		f_functionKeys = null;

		if (f_cashfunctions != null)
			f_cashfunctions.dispose();
		f_cashfunctions = null;
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
		m_ctx = null;
	}	//	dispose

	
	/**************************************************************************
	 * 	Dynamic Init.
	 * 	PosPanel has a GridBagLayout.
	 * 	The Sub Panels return their position
	 */
	private boolean dynInit()
	{
		if (!setMPOS())
			return false;
		frame.setTitle("Adempiere POS: " + p_pos.getName());
		//	Create Sub Panels
		f_order = new SubOrder (this);
		add (f_order, "split 2, flowy, growx, spany");
		//
		f_curLine = new SubCurrentLine (this);
		add (f_curLine, "h 300, growx, growy, gaptop 30");
		
		f_functionKeys = new SubFunctionKeys (this);
		add (f_functionKeys, "aligny top, h 500, growx, growy, flowy, split 2");
		
		return true;
	}	//	dynInit

	/**
	 * 	Set MPOS
	 *	@return true if found/set
	 */
	private boolean setMPOS()
	{
		MPOS[] poss = null;
		if (m_SalesRep_ID == 100)	//	superUser
			poss = getPOSs (0);
		else
			poss = getPOSs (m_SalesRep_ID);
		//
		if (poss.length == 0)
		{
			ADialog.error(m_WindowNo, m_frame, "NoPOSForUser");
			return false;
		}
		else if (poss.length == 1)
		{
			p_pos = poss[0];
			return true;
		}

		//	Select POS
		String msg = Msg.getMsg(m_ctx, "SelectPOS");
		String title = Env.getHeader(m_ctx, m_WindowNo);
		Object selection = JOptionPane.showInputDialog(m_frame, msg, title, 
			JOptionPane.QUESTION_MESSAGE, null, poss, poss[0]);
		if (selection != null)
		{
			p_pos = (MPOS)selection;
			return true;
		}
		return false;
	}	//	setMPOS
	
	/**
	 * 	Get POSs for specific Sales Rep or all
	 *	@param SalesRep_ID
	 *	@return array of POS
	 */
	private MPOS[] getPOSs (int SalesRep_ID)
	{
		String pass_field = "SalesRep_ID";
		int pass_ID = SalesRep_ID;
		if (SalesRep_ID==0)
			{
			pass_field = "AD_Client_ID";
			pass_ID = Env.getAD_Client_ID(m_ctx);
			}
		return MPOS.getAll(m_ctx, pass_field, pass_ID);
	}	//	getPOSs

	/**************************************************************************
	 * 	Get Today's date
	 *	@return date
	 */
	public Timestamp getToday()
	{
		return m_today;
	}	//	getToday
	
	/**
	 * 	New Order
	 *   
	 */
	public void newOrder()
	{
		log.info( "PosPanel.newOrder");
		f_order.setC_BPartner_ID(0);
		m_order = null;
		m_order = PosOrderModel.createOrder(p_pos, f_order.getBPartner());
		f_curLine.newLine();
		f_curLine.f_name.requestFocusInWindow();
		updateInfo();
	}	//	newOrder
	
	/**
	 * Get the number of the window for the function calls that it needs 
	 * 
	 * @return the window number
	 */
	public int getWindowNo()
	{
		return m_WindowNo;
	}
	
	/**
	 * Get the properties for the process calls that it needs
	 * 
	 * @return getProperties m_ctx
	 */
	public Properties getCtx()
	{
		return m_ctx;
	}
	
	public void updateInfo()
	{
		// reload order
		if ( m_order != null )
		{
			m_order.reload();
		}
		if ( f_curLine != null )
			f_curLine.updateTable(m_order);
		if (f_order != null)
		{
			f_order.updateOrder();
		}
	}

	/**
	 * @param m_c_order_id
	 */
	public void setOldOrder(int m_c_order_id) 
	{
		if ( m_order != null ) 
			m_order.deleteOrder();
		
		if ( m_c_order_id == 0 )
			m_order = null;
		else 
			m_order = new PosOrderModel(m_ctx , m_c_order_id, null, p_pos);
		updateInfo();
	}
	
	/**
	 * @param m_c_order_id
	 */
	public void setOrder(int m_c_order_id) 
	{
		if ( m_c_order_id == 0 )
			m_order = null;
		else
			m_order = new PosOrderModel(m_ctx , m_c_order_id, null, p_pos);
	}

	public POSKeyboard getKeyboard(int keyLayoutId) {
		if ( keyboards.containsKey(keyLayoutId) )
			return keyboards.get(keyLayoutId);
		else
		{
			POSKeyboard keyboard = new POSKeyboard(this, keyLayoutId);
			keyboards.put(keyLayoutId, keyboard);
			return keyboard;
		}
	}
	
}	//	PosPanel

