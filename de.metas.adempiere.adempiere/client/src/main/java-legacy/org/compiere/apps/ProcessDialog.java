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

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.images.Images;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.X_AD_Process;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoUtil;
import org.compiere.swing.CButton;
import org.compiere.swing.CFrame;
import org.compiere.swing.CPanel;
import org.compiere.util.ASyncProcess;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_AD_Process;
import de.metas.logging.LogManager;

/**
 *	Dialog to Start process.
 *	Displays information about the process
 *		and lets the user decide to start it
 *  	and displays results (optionally print them).
 *  Calls ProcessCtl to execute.
 *
 * @author Jorg Janke
 * @version $Id: ProcessDialog.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 *  @author		Low Heng Sin
 *  - Merge process parameter dialog into process dialog.
 *  @author     arboleda - globalqss
 *  - Implement ShowHelp option on processes and reports
 *  @author		Teo Sarca, SC ARHIPAC SERVICE SRL
 *  				<li>BF [ 1893525 ] ProcessDialog: Cannot select the text from text field
 *  				<li>BF [ 1963128 ] Running a process w/o trl should display an error
 */
public class ProcessDialog extends CFrame
		implements ActionListener, ASyncProcess
{
	private static final long serialVersionUID = 790447068287846414L;

	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	/**
	 * Dialog to start Process
	 *
	 * @param AD_Process_ID process
	 * @param isSOTrx is sales trx
	 */
	public ProcessDialog(final int AD_Process_ID, final boolean isSOTrx)
	{
		super();
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);

		final Properties ctx = Env.getCtx();
		this.m_AD_Process_ID = AD_Process_ID;
		this.m_WindowNo = Env.createWindowNo(this);
		Env.setContext(ctx, m_WindowNo, "IsSOTrx", isSOTrx);
		this._adClientId = Env.getAD_Client_ID(ctx);
		this._adUserId = Env.getAD_User_ID(ctx);
		try
		{
			jbInit();
		}
		catch (Exception ex)
		{
			log.error("", ex);
		}
	}	// ProcessDialog

	private final int m_AD_Process_ID;
	private String _processName;
	private String _processClassname;
	private final int m_WindowNo;
	private final int _adUserId;
	private final int _adClientId;
	private boolean m_IsReport = false;
	private boolean _allowProcessReRun = true;
	private boolean m_isLocked = false;
	private boolean _disposed = false;

	/**
	 * Determine if a Help Process Window is shown
	 */
	private String m_ShowHelp = null;
	/** Logger */
	private static final Logger log = LogManager.getLogger(ProcessDialog.class);
	//

	/** The main panel */
	private CPanel dialog = new CPanel()
	{
		/**
		 *
		 */
		private static final long serialVersionUID = 428410337428677817L;

		@Override
		public Dimension getPreferredSize()
		{
			Dimension d = super.getPreferredSize();
			Dimension m = getMinimumSize();
			if (d.height < m.height || d.width < m.width)
			{
				Dimension d1 = new Dimension();
				d1.height = Math.max(d.height, m.height);
				d1.width = Math.max(d.width, m.width);
				return d1;
			}
			else
				return d;
		}
	};

	private final MessagePanel messageTop = new MessagePanel();

	private final CPanel _centerPanel = new CPanel();
	private final CardLayout _centerPanelLayout = new CardLayout();
	private String _currentCardName = null;
	//
	private static final String CARDNAME_ProcessParameters = "ProcessParameters";
	private final CPanel parametersContainer = new CPanel();
	private ProcessParameterPanel parameterPanel = null;
	//
	private static final String CARDNAME_ProcessResult = "ProcessResult";
	private final CPanel resultContainer = new CPanel();
	private final MessagePanel resultMessagePane = new MessagePanel();

	private final CButton bOK = ConfirmPanel.createOKButton(true);
	private final CButton bBack = ConfirmPanel.createBackButton(true);

	/**
	 * Static Layout
	 *
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		setIconImage(Images.getImage2("mProcess"));

		//
		// Main panel:
		{
			final BorderLayout mainLayout = new BorderLayout();
			mainLayout.setVgap(2);
			//
			dialog.setLayout(mainLayout);
			dialog.setMinimumSize(new Dimension(500, 200));
			getContentPane().add(dialog);
		}

		//
		// NorthPanel: Process message
		{
			dialog.add(messageTop, BorderLayout.NORTH);
			messageTop.setMaximumSize(new Dimension(600, 300));
		}

		//
		// Center panel: process parameters / process result
		{
			_centerPanel.setLayout(_centerPanelLayout);
			_centerPanel.setBorder(BorderFactory.createEmptyBorder());
			dialog.add(_centerPanel, BorderLayout.CENTER);

			// Parameters panel:
			{
				parametersContainer.setLayout(new BorderLayout());
				_centerPanel.add(parametersContainer, CARDNAME_ProcessParameters);
			}

			// Result panel:
			{
				resultContainer.setLayout(new BorderLayout());
				resultContainer.add(resultMessagePane, BorderLayout.CENTER);
				_centerPanel.add(resultContainer, CARDNAME_ProcessResult);
			}

			showCard(CARDNAME_ProcessParameters); // initially process parameters (first card) shall be displayed
		}

		//
		// South panel: confirm panel buttons
		{
			bOK.setText(msgBL.getMsg(Env.getCtx(), "Start")); // make sure the text is set
			bOK.addActionListener(this);

			bBack.setText(msgBL.getMsg(Env.getCtx(), "Back")); // make sure the text is set
			bBack.addActionListener(this);

			final FlowLayout southLayout = new FlowLayout();
			final CPanel southPanel = new CPanel();
			southPanel.setLayout(southLayout);
			southLayout.setAlignment(FlowLayout.RIGHT);
			dialog.add(southPanel, BorderLayout.SOUTH);
			southPanel.add(bBack, null);
			southPanel.add(bOK, null);
		}

		//
		this.getRootPane().setDefaultButton(bOK);
	}	// jbInit

	private final void showCard(final String cardName)
	{
		_centerPanelLayout.show(_centerPanel, cardName);
		_currentCardName = cardName;

		updateUIStatus();
	}

	private final String getCurrentCardName()
	{
		return _currentCardName;
	}

	private final void updateUIStatus()
	{

		final String okButtonMessage;
		final boolean enableBackButton;

		final String cardName = getCurrentCardName();
		if (CARDNAME_ProcessParameters.equals(cardName))
		{
			okButtonMessage = "Start";
			enableBackButton = false;
		}
		else if (CARDNAME_ProcessResult.equals(cardName))
		{
			okButtonMessage = "Close";
			enableBackButton = true;
		}
		else
		{
			throw new IllegalStateException("Unknown card: " + cardName);
		}

		//
		// Update UI
		bOK.setText(msgBL.getMsg(Env.getCtx(), okButtonMessage));
		bBack.setVisible(_allowProcessReRun);
		bBack.setEnabled(_allowProcessReRun && enableBackButton);

	}

	/**
	 * Set Visible (set focus to OK if visible)
	 *
	 * @param visible true if visible
	 */
	@Override
	public void setVisible(boolean visible)
	{
		if (visible && _disposed)
		{
			log.warn("Marking visible an already disposed panel: {}", this, new Exception("trace"));
		}
		super.setVisible(visible);
		if (visible)
		{
			bOK.requestFocus();
		}
	}	// setVisible

	/**
	 * Dispose
	 */
	@Override
	public void dispose()
	{
		if (_disposed)
		{
			log.warn("Calling dispose again for {}", this, new Exception("trace"));
		}
		_disposed = true;

		if (parameterPanel != null)
		{
			parameterPanel.dispose();
		}

		Env.clearWinContext(m_WindowNo);
		super.dispose();
	}	// dispose

	/**
	 * Dynamic Init
	 *
	 * @return true, if there is something to process (start from menu)
	 */
	public boolean init()
	{
		log.info("");

		//
		// Load process definition from database
		final I_AD_Process process = InterfaceWrapperHelper.create(Env.getCtx(), m_AD_Process_ID, I_AD_Process.class, ITrx.TRXNAME_None);
		if (process == null)
		{
			throw new AdempiereException("@NotFound@ @AD_Process_ID@=" + m_AD_Process_ID);
		}
		final I_AD_Process processTrl = InterfaceWrapperHelper.translate(process, I_AD_Process.class, Env.getAD_Language(Env.getCtx()));
		this._processName = processTrl.getName();
		final String description = processTrl.getDescription();
		final String help = processTrl.getHelp();
		this.m_IsReport = processTrl.isReport();
		this.m_ShowHelp = processTrl.getShowHelp();
		this._allowProcessReRun = processTrl.isAllowProcessReRun();
		this._processClassname = processTrl.getClassname();

		//
		// Build the top message (Process description and help).
		final StringBuilder messageText = new StringBuilder();
		// Description
		messageText.append("<b>");
		if (Check.isEmpty(description))
			messageText.append(msgBL.getMsg(Env.getCtx(), "StartProcess?"));
		else
			messageText.append(description);
		messageText.append("</b>");
		// Help
		if (!Check.isEmpty(help))
		{
			messageText.append("<p>").append(help).append("</p>");
		}

		//
		// Update UI
		this.setTitle(_processName);
		messageTop.setText(messageText.toString());

		//
		// Create process parameters panel
		final ProcessInfo processInfo = createProcessInfo(); // NOTE: used only for loading
		parameterPanel = new ProcessParameterPanel(processInfo);
		parametersContainer.removeAll();
		if (parameterPanel.hasFields())
		{
			final JScrollPane parametersPanelScroll = new JScrollPane(parameterPanel);
			parametersPanelScroll.setBorder(BorderFactory.createEmptyBorder());

			parametersContainer.add(parametersPanelScroll, BorderLayout.CENTER);
		}

		//
		// Automatically execute the process (if possible)
		{
			if (!parameterPanel.hasFields() && X_AD_Process.SHOWHELP_DonTShowHelp.equals(m_ShowHelp))
			{
				bOK.doClick();    // don't ask first click, anyway show resulting window
			}
			// Check if the process is a silent one
			else if (X_AD_Process.SHOWHELP_RunSilently_TakeDefaults.equals(m_ShowHelp))
			{
				bOK.doClick();
			}
		}

		dialog.revalidate();
		return true;
	}	// init

	private final ProcessInfo createProcessInfo()
	{
		final ProcessInfo pi = new ProcessInfo(_processName, m_AD_Process_ID);
		pi.setWindowNo(m_WindowNo);
		pi.setAD_User_ID(_adUserId);
		pi.setAD_Client_ID(_adClientId);
		pi.setClassName(_processClassname);
		return pi;
	}

	/**
	 * ActionListener (Start)
	 *
	 * @param e ActionEvent
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		if (e.getSource() == bOK)
		{
			final String currentCardName = getCurrentCardName();
			if (CARDNAME_ProcessParameters.equals(currentCardName))
			{
				if (!parameterPanel.validateParameters())
				{
					return;
				}
				final ProcessInfo processInfo = createProcessInfo();
				ProcessCtl.process(this, m_WindowNo, parameterPanel, processInfo, ITrx.TRX_None);
			}
			else if (CARDNAME_ProcessResult.equals(currentCardName))
			{
				dispose();
			}
			else
			{
				log.warn("Unknown card: {}", currentCardName);
			}
		}
		else if (e.getSource() == bBack)
		{
			showCard(CARDNAME_ProcessParameters);
		}
	}	// actionPerformed

	/**
	 * Lock User Interface Called from the Worker before processing
	 *
	 * @param pi process info
	 */
	@Override
	public void lockUI(final ProcessInfo pi)
	{
		bOK.setEnabled(false);
		this.setEnabled(false);
		m_isLocked = true;
	}   // lockUI

	/**
	 * Unlock User Interface. Called from the Worker when processing is done
	 *
	 * @param pi process info
	 */
	@Override
	public void unlockUI(final ProcessInfo pi)
	{
		final StringBuilder m_messageText = new StringBuilder();

		// Show process logs if any
		if (pi.isShowProcessLogs())
		{
			//
			// Update message
			ProcessInfoUtil.setLogFromDB(pi);
			m_messageText.append("<p><font color=\"").append(pi.isError() ? "#FF0000" : "#0000FF").append("\">** ")
					.append(pi.getSummary())
					.append("</font></p>");
			m_messageText.append(pi.getLogInfo(true));
		}
		resultMessagePane.setText(m_messageText.toString());
		resultMessagePane.moveCaretToEnd(); // scroll down
		// message.setCaretPosition(message.getDocument().getLength()); // scroll down

		//
		// Fetch IDs from process logs... we will need them in afterProcessTask()

		//
		// Enable OK button, flag as not locked anymore.
		bOK.setEnabled(true);
		this.setEnabled(true);
		m_isLocked = false;

		//
		// Show the results panel
		showCard(CARDNAME_ProcessResult);

		//
		// Update UI
		this.validate();
		AEnv.showCenterScreen(this);

		//
		// Close automatically
		{
			// If it was a report which finished successfully and we don't allow process re-run
			if (m_IsReport && !pi.isError() && !_allowProcessReRun)
			{
				bOK.doClick();
			}

			// If the process is a silent one and no errors occurred, close the dialog
			if (X_AD_Process.SHOWHELP_RunSilently_TakeDefaults.equals(m_ShowHelp))
			{
				bOK.doClick();
			}
		}
	}   // unlockUI

	/**
	 * Is the UI locked (Internal method)
	 *
	 * @return true, if UI is locked
	 */
	@Override
	public boolean isUILocked()
	{
		return m_isLocked;
	}   // isLoacked

	/**
	 * Method to be executed async. Called from the ASyncProcess worker
	 *
	 * @param pi process info
	 */
	@Override
	public void executeASync(ProcessInfo pi)
	{
		log.info("-");
	}   // executeASync

	/**
	 * Message panel component
	 *
	 * @author tsa
	 *
	 */
	private static final class MessagePanel extends JScrollPane
	{
		private static final long serialVersionUID = 1L;

		private final StringBuffer messageBuf = new StringBuffer();
		private final JEditorPane message = new JEditorPane()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize()
			{
				Dimension d = super.getPreferredSize();
				Dimension m = getMaximumSize();
				if (d.height > m.height || d.width > m.width)
				{
					Dimension d1 = new Dimension();
					d1.height = Math.min(d.height, m.height);
					d1.width = Math.min(d.width, m.width);
					return d1;
				}
				else
					return d;
			}
		};

		public MessagePanel()
		{
			super();

			this.setBorder(null);
			this.setViewportView(message);

			message.setContentType("text/html");
			message.setEditable(false);
			message.setBackground(Color.white);
			message.setFocusable(true);

		}

		public void moveCaretToEnd()
		{
			message.setCaretPosition(message.getDocument().getLength());	// scroll down
		}

		public final void setText(final String text)
		{
			messageBuf.setLength(0);
			appendText(text);
		}

		public final void appendText(final String text)
		{
			messageBuf.append(text);
			message.setText(messageBuf.toString());
		}

		@Override
		public Dimension getPreferredSize()
		{
			Dimension d = super.getPreferredSize();
			Dimension m = getMaximumSize();
			if (d.height > m.height || d.width > m.width)
			{
				Dimension d1 = new Dimension();
				d1.height = Math.min(d.height, m.height);
				d1.width = Math.min(d.width, m.width);
				return d1;
			}
			else
			{
				return d;
			}
		}
	};

}	// ProcessDialog
