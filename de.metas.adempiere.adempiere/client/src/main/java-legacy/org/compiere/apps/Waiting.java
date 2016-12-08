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
package org.compiere.apps;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.adempiere.plaf.MetasFreshTheme;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.Adempiere;
import org.compiere.swing.CButton;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 *  Waiting Dialog
 *
 *  @author     Jorg Janke
 *  @version    $Id: Waiting.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class Waiting extends CDialog 
	implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2078889004176093095L;

	/**
	 *  Constructor - non nodal as otherwise process is blocked
	 *  @param owner
	 *  @param text     Message to be displayed
	 *  @param canNotWait user can continue with other work
	 *  @param timer    timer ticks (seconds) - if 0 then 10
	 */
	public Waiting (final Frame owner, final String text, final boolean canNotWait, final int timer)
	{
		super(owner, Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Processing"));
		init (text, canNotWait, timer);
	}   //  Waiting

	/**
	 *  Constructor - non modal as otherwise process is blocked
	 *  @param owner
	 *  @param text     Message to be displayed
	 *  @param canNotWait user can continue with other work
	 *  @param timer    timer ticks (seconds) - if 0 then 10
	 */
	public Waiting (Dialog owner, String text, boolean canNotWait, int timer)
	{
		super(owner, Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Processing"));
		init (text, canNotWait, timer);
	}   //  Waiting

	/**
	 *  Common Initialize routine - does not create if timer == 1
	 *  @param text     Message to be displayed
	 *  @param canNotWait user can continue with other work
	 *  @param timer    timer ticks (seconds) - if less than 5 then 10
	 */
	private void init (String text, boolean canNotWait, int timer)
	{
		log.debug(text + " - Sec=" + timer);
		//  don't show if 1 sec average
		if (timer == 1)
			return;

		try
		{
			jbInit();
			setText (text);
			if (!canNotWait)
				bDoNotWait.setVisible(false);
		}
		catch(Exception e)
		{
			log.error("Waiting", e);
		}
		//  set progress Bar
		progressBar.setMinimum(0);
		progressBar.setMaximum(timer < 5 ? 10 : timer); //  min 2 seconds

		//  Timer
		m_timer = new Timer (1000, this);     //  every second
		m_timer.start();
		AEnv.showCenterWindow(getOwner(), this);
	}   //  init

	private int     m_timervalue = 0;
	private Timer   m_timer;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(Waiting.class);

	private CButton bDoNotWait = new CButton();
	private CLabel infoLabel = new CLabel();
	private CPanel mainPanel = new CPanel();
	private JProgressBar progressBar = new JProgressBar();

	/**
	 *  Static Layout
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		this.getContentPane().add(Box.createVerticalStrut(8), BorderLayout.NORTH);
		this.getContentPane().add(Box.createHorizontalStrut(8), BorderLayout.WEST);
		this.getContentPane().add(Box.createVerticalStrut(8), BorderLayout.SOUTH);
		this.getContentPane().add(Box.createHorizontalStrut(8), BorderLayout.EAST);
		mainPanel.setLayout(new BorderLayout(5,5));
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		//
		infoLabel.setFont(UIManager.getFont(MetasFreshTheme.KEY_Logo_TextFont));
		infoLabel.setForeground(UIManager.getColor(MetasFreshTheme.KEY_Logo_TextColor));
		infoLabel.setHorizontalAlignment(JLabel.CENTER);
		infoLabel.setHorizontalTextPosition(JLabel.CENTER);
		infoLabel.setVerticalTextPosition(JLabel.BOTTOM);
		final Image productLogoImage = Adempiere.getProductLogoLarge();
		if (productLogoImage != null)
		{
			infoLabel.setIcon(new ImageIcon(productLogoImage));
		}
		infoLabel.setIconTextGap(10);
		mainPanel.add(infoLabel, BorderLayout.NORTH);
		mainPanel.add(progressBar,  BorderLayout.CENTER);
		//
//		bDoNotWait.setText(Msg.getMsg(Env.getCtx(), "DoNotWait"));
//		bDoNotWait.setToolTipText(Msg.getMsg(Env.getCtx(), "DoNotWaitInfo"));
//		bDoNotWait.addActionListener(this);
//		southPanel.setLayout(southLayout);
//		southPanel.add(bDoNotWait, null);
//		mainPanel.add(southPanel, BorderLayout.SOUTH);
	}   //  jbInit

	/**
	 *  Set Info Text
	 *  @param text
	 */
	public void setText (String text)
	{
		infoLabel.setText(text);
	}   //  setText

	/**
	 *  ActionListener
	 *  @param e
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource() == bDoNotWait)
			doNotWait();
		//
		progressBar.setValue(m_timervalue++);
		if (m_timervalue > progressBar.getMaximum())
			m_timervalue = progressBar.getMinimum();
	//	progressBar.setString(progressBar.getPercentComplete());
	}   //  actionPerformed

	/**
	 *  Set Timer Estimate
	 *  @param max Seconds
	 */
	public void setTimerEstimate (int max)
	{
		progressBar.setMaximum(max);
	}   //  setMaximum

	/**
	 *  User does not want to wait for result and continue with other worg
	 *  Callback & dispose
	 */
	public void doNotWait()
	{
		/** @todo callback */
		dispose();
	}   //  doNotWait

	/**
	 *  Dispose
	 */
	@Override
	public void dispose()
	{
		if (m_timer != null)
			m_timer.stop();
		m_timer = null;
		super.dispose();
	}   //  dispose
	
	/**
	 * Execute dispose in Event Dispatch Thread
	 */
	public void disposeInEDT()
	{
		final Runnable disposeRunnable = new Runnable()
		{
			
			@Override
			public void run()
			{
				dispose();
				setVisible(false);
			}
		};
		
		if(SwingUtilities.isEventDispatchThread())
		{
			disposeRunnable.run();
		}
		else
		{
			SwingUtilities.invokeLater(disposeRunnable);
		}
	}

}   //  Waiting
