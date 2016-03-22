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
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.SwingUtilities;

import org.compiere.model.I_AD_Process;
import org.compiere.model.MProcess;
import org.compiere.model.X_AD_Process;
import org.compiere.process.ProcessInfo;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;

/**
 *	Parameter Dialog.
 *	- called from ProcessCtl
 *	- checks, if parameters exist and inquires and saves them
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: ProcessParameter.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class ProcessParameter extends CDialog
	implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3898982577949513358L;

	/**
	 *	Dynamic generated Parameter Dialog screen.
	 *	Called from ProcessCtl.process
	 *
	 *  @param frame frame
	 *  @param WindowNo window
	 *  @param pi process info
	 */
	public ProcessParameter (Frame frame, int WindowNo, ProcessInfo pi)
	{
		super(frame, pi.getTitle(), true);
		m_frame = frame;
		parametersPanel = new ProcessParameterPanel(pi);
		try
		{
			jbInit();
		}
		catch(Exception ex)
		{
			log.error(ex.getMessage());
		}
		//
		m_WindowNo = WindowNo;
		m_processInfo = pi;
		//
	}	//	ProcessParameter

	private Frame		m_frame;
	private int			m_WindowNo;
	private ProcessInfo m_processInfo;
	private boolean 	m_isOK = false;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(ProcessParameter.class);
	//
	private final ProcessParameterPanel parametersPanel;

	/**
	 *	Static Layout
	 *  @throws Exception
	 */
	void jbInit() throws Exception
	{
		final CPanel mainPanel = new CPanel();
		final BorderLayout mainLayout = new BorderLayout();
		final ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();

		mainPanel.setLayout(mainLayout);
//		centerPanel.setLayout(centerLayout);
		this.getContentPane().add(mainPanel);
		mainPanel.add(parametersPanel, BorderLayout.CENTER);
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
	}	//	jbInit

	/**
	 *  Dispose
	 */
	@Override
	public void dispose()
	{
		parametersPanel.dispose();
		this.removeAll();
		super.dispose();
	}   //  dispose

	/**
	 *	Read Fields to display
	 *  @return true if loaded OK
	 */
	public boolean initDialog()
	{
		final boolean hasFields = parametersPanel.hasFields();
		if (hasFields)
		{
			AEnv.positionCenterWindow(m_frame, this);
		}
		else
		{
			SwingUtilities.invokeLater(new Runnable()
			{
				
				@Override
				public void run()
				{
					dispose();
				}
			});
			
		}
		return hasFields;
	}	//	initDialog

	@Override
	public void actionPerformed(ActionEvent e)
	{
		m_isOK = false;
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			//	check if saving parameters is complete
			if (parametersPanel.saveParameters(m_processInfo.getAD_PInstance_ID()))
			{
				m_isOK = true;
				dispose();
			}
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
		}
	}	//	actionPerformed

	/**
	 *	Editor Listener
	 *	@param evt Event
	 * 	@exception PropertyVetoException if the recipient wishes to roll back.
	 */
	public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException
	{
	//	log.debug( "ProcessParameter.vetoableChange");
		String value = evt.getNewValue() == null ? "" : evt.getNewValue().toString();
		Env.setContext(Env.getCtx(), m_WindowNo, evt.getPropertyName(), value);
	}	//	vetoableChange

	/**
	 *	Is everything OK?
	 *  @return true if parameters saved correctly
	 */
	public boolean isOK()
	{
		return m_isOK;
	}	//	isOK

	@Override
	public void setVisible(boolean b)
	{
		final I_AD_Process process = MProcess.get(Env.getCtx(), m_processInfo.getAD_Process_ID());
		if(process != null && X_AD_Process.SHOWHELP_RunSilently_TakeDefaults.equals(process.getShowHelp()))
		{
			// It is defined as a silent process
			if(parametersPanel.saveParameters(m_processInfo.getAD_PInstance_ID()))
			{
				m_isOK = true;
				dispose();
			}
		}
		else
		{
			// Not a silent process
			super.setVisible(b);
		}
	}
}	//	ProcessParameter
