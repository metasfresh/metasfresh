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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.Adempiere;
import org.compiere.model.MTask;
import org.compiere.swing.CFrame;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Task;


/**
 *  Application Task
 *
 *  @author     Jorg Janke
 *  @version    $Id: ATask.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class ATask extends CFrame 
	implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8998998120736876682L;

	/**
	 *  Start Application Task
	 *  @param task task model
	 */
	static public void start (final String title, final MTask task)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				new ATask(title, task);
			}
		}.start();
	}   //  start

	
	/**************************************************************************
	 *  Full Constructor
	 *  @param title title
	 *  @param task task
	 */
	public ATask (String title, MTask task)
	{
		super (title);
		this.setIconImage(Adempiere.getProductIconSmall());
		try
		{
			jbInit();
			AEnv.showCenterScreen(this);
			//
			if (task.isServerProcess())
				info.setText("Executing on Server ...");
			else
				info.setText("Executing locally ...");
			String result = task.execute();
			info.setText(result);
			confirmPanel.getCancelButton().setEnabled(false);
			confirmPanel.getOKButton().setEnabled(true);
		}
		catch(Exception e)
		{
			log.error(task.toString(), e);
		}
	}   //  ATask

	private Task    m_task = null;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(ATask.class);

	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
	private JScrollPane infoScrollPane = new JScrollPane();
	private JTextArea info = new JTextArea();

	/**
	 *  Static Layout
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		info.setEditable(false);
		info.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		infoScrollPane.getViewport().add(info, null);
		infoScrollPane.setPreferredSize(new Dimension(500,300));
		this.getContentPane().add(infoScrollPane, BorderLayout.CENTER);
		this.getContentPane().add(confirmPanel,  BorderLayout.SOUTH);
		//
		confirmPanel.setActionListener(this);
		confirmPanel.getOKButton().setEnabled(false);
	}   //  jbInit


	/**
	 *  Action Listener
	 *  @param e
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (m_task != null && m_task.isAlive())
			m_task.interrupt();
		dispose();
	}   //  actionPerformed

}   //  ATask
