/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
package org.compiere.apps;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.email.EMail;
import de.metas.email.EMailAddress;
import de.metas.email.EMailSentStatus;
import de.metas.email.mailboxes.UserEMailConfig;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.letters.model.I_AD_BoilerPlate;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;
import de.metas.logging.LogManager;
import de.metas.user.api.IUserBL;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.ArchiveEmailSentStatus;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.archive.api.IPDFArchiveProvider;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.grid.ed.RichTextEditor;
import org.compiere.grid.ed.VLetterAttachment;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_User;
import org.compiere.model.Lookup;
import org.compiere.model.MClient;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MUserMail;
import org.compiere.model.PO;
import org.compiere.swing.CCheckBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.File;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * EMail Dialog
 *
 * @author Jorg Janke
 * @version $Id: EMailDialog.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 *
 *          globalqss: integrate phib fixing bug reported here http://sourceforge.net/tracker/index.php?func=detail&aid=1568765&group_id=176962&atid=879332
 *
 *          phib - fixing bug [ 1568765 ] Close email dialog button broken
 *
 *          globalqss - Carlos Ruiz - implement CC - FR [ 1754879 ] Enhancements on sending e-mail
 *
 */
// metas: synced with rev. 8996
public class EMailDialog
		extends CDialog
		implements ActionListener, VetoableChangeListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = -6898571372446841308L;

	/**
	 * EMail Dialog
	 *
	 * @param owner calling window
	 * @param title title
	 * @param to to
	 * @param subject subject
	 * @param message message
	 * @param attachment optional attachment
	 */
	public EMailDialog(final Dialog owner,
			final String title,
			final UserEMailConfig fromUserEmailConfig,
			final String to,
			final String subject,
			final String message,
			final File attachment)
	{
		super(owner, title, true);
		commonInit(fromUserEmailConfig, to, subject, message, attachment, null); // boilerPlate
	}	// EmailDialog

	public EMailDialog(final Frame owner,
			final String title,
			final UserEMailConfig fromUserEmailConfig,
			final String to,
			final String subject,
			final String message,
			final File attachment)
	{
		this(owner, title, fromUserEmailConfig, to, subject, message, attachment, null, null); // textPreset, archive
	}	// EmailDialog

	private EMailDialog(final Frame owner,
			final String title,
			final UserEMailConfig fromUserEmailConfig,
			final String to,
			final String subject,
			final String message,
			final File attachment,
			final MADBoilerPlate textPreset,
			final I_AD_Archive archive)
	{
		super(owner, title, true);

		this.archive = archive;

		commonInit(fromUserEmailConfig, to, subject, message, attachment, textPreset);
	}	// EmailDialog

	/**
	 * Common Init
	 *
	 * @param to to
	 * @param subject subject
	 * @param message message
	 * @param attachment optional attachment
	 */
	private void commonInit(
			final UserEMailConfig fromUserEmailConfig,
			final String to,
			final String subject,
			final String message,
			final File attachment,
			final MADBoilerPlate boilerPlate)
	{
		final Properties ctx = Env.getCtx();

		m_client = MClient.get(ctx);
		try
		{
			final int WindowNo = getWindowNo();
			final int AD_Column_ID = 0;
			final Lookup lookup = MLookupFactory.newInstance().get(ctx, WindowNo,
					AD_Column_ID, DisplayType.Search,
					null, // tableName
					"AD_User_ID",
					0,
					false,
					"EMail IS NOT NULL" // Validation
			);
			fUser = new VLookup("AD_User_ID", false, false, true, lookup);
			fUser.addVetoableChangeListener(this);
			fCcUser = new VLookup("AD_User_ID", false, false, true, lookup);
			fCcUser.addVetoableChangeListener(this);
			setDocumentInfo(); // metas
			jbInit();
		}
		catch (final Exception ex)
		{
			log.error("EMailDialog", ex);
		}
		set(fromUserEmailConfig, to, subject, message);
		setAttachment(attachment);

		// metas adding default bcc and text preset
		setBcc();
		selectTextPreset(boilerPlate);
		// metas end

		AEnv.showCenterScreen(this);
	}	// commonInit

	/** Client */
	private MClient m_client = null;
	/** Sender */
	private UserEMailConfig fromUserEmailConfig = null;
	/** Primary Recipient */
	private I_AD_User m_user = null;
	/** Cc Recipient */
	private I_AD_User m_ccuser = null;
	//
	private String m_to;
	private String m_cc;
	private String m_subject;
	private String m_message;
	/** File to be optionally attached */
	private File m_attachFile;
	/** Logger */
	private static Logger log = LogManager.getLogger(EMailDialog.class);

	// metas: additional fields
	private final CLabel lBoilerPlate = new CLabel();
	private VLookup fBoilerPlate = null;
	private CCheckBox fResolveVariables = null;
	private CCheckBox fAttachDocument = null;
	private String m_bcc;
	private final CTextField fBcc = new CTextField(20);
	private final CLabel lBcc = new CLabel();
	private I_AD_Archive archive = null; // 03743
	private EMail email = null;
	// metas end
	private final CPanel mainPanel = new CPanel();
	private final BorderLayout mainLayout = new BorderLayout();
	private final CPanel headerPanel = new CPanel();
	private final GridBagLayout headerLayout = new GridBagLayout();
	private final CTextField fFrom = new CTextField(20);
	private final CTextField fTo = new CTextField(20);
	private final CTextField fCc = new CTextField(20);
	private VLookup fUser = null;
	private VLookup fCcUser = null;
	private final CTextField fSubject = new CTextField(40);
	private final CLabel lFrom = new CLabel();
	private final CLabel lTo = new CLabel();
	private final CLabel lCc = new CLabel();
	private final CLabel lSubject = new CLabel();
	private final CLabel lAttachment = new CLabel();
	private final CTextField fAttachment = new CTextField(40);
	// private CTextArea fMessage = new CTextArea(); // metas: commented
	private RichTextEditor fMessage; // metas
	private final ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
	private final StatusBar statusBar = new StatusBar();

	/**
	 * Static Init
	 */
	void jbInit() throws Exception
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final Properties ctx = Env.getCtx();

		lFrom.setText(msgBL.getMsg(ctx, "From") + ":");
		fFrom.setReadWrite(false);

		lTo.setText(msgBL.getMsg(ctx, "To") + ":");
		lCc.setText(msgBL.getMsg(ctx, "Cc") + ":");

		// metas: new field Bcc
		lBcc.setText(msgBL.getMsg(ctx, "Bcc") + ":");
		lSubject.setText(msgBL.getMsg(ctx, "Subject") + ":");
		lAttachment.setText(msgBL.getMsg(ctx, "Attachment") + ":");

		// metas: new list box to select text snippet
		lBoilerPlate.setText(msgBL.translate(ctx, I_AD_BoilerPlate.COLUMNNAME_AD_BoilerPlate_ID) + ":");
		fBoilerPlate = new VLookup(I_AD_BoilerPlate.COLUMNNAME_AD_BoilerPlate_ID, false, false, true, MADBoilerPlate.getAllLookup(getWindowNo()));
		fBoilerPlate.addActionListener(e -> {
			final Integer id = (Integer)fBoilerPlate.getValue();
			if (id == null)
			{
				return;
			}
			final MADBoilerPlate boilerPlate = MADBoilerPlate.get(ctx, id);

			//
			// Set subject from boiler plate
			// if (Check.isEmpty(getSubject(), true))
			{
				final String subject = MADBoilerPlate.parseText(ctx, boilerPlate.getSubject(), true, attributes, ITrx.TRXNAME_None);
				setSubject(subject);
			}
			setMessage(boilerPlate.getTextSnippetParsed(attributes));
		});

		fMessage = new RichTextEditor(attributes); // metas

		fResolveVariables = new CCheckBox(msgBL.getMsg(ctx, "de.metas.letter.ResolveVariables"), fMessage.isResolveVariables());
		fResolveVariables.addActionListener(e -> fMessage.setResolveVariables(fResolveVariables.isSelected()));

		lLetter.setText(msgBL.translate(ctx, "de.metas.letters.AttachedLetter") + ":");

		fAttachDocument = new CCheckBox(msgBL.getMsg(ctx, "de.metas.letter.AttachDocument"), true);
		if (m_AD_Table_ID <= 0 || m_Record_ID <= 0)
		{
			fAttachDocument.setValue(false);
			fAttachDocument.setReadWrite(false);
			fAttachDocument.setVisible(false);
		}
		// metas end

		mainPanel.setLayout(mainLayout);
		headerPanel.setLayout(headerLayout);
		mainLayout.setHgap(5);
		mainLayout.setVgap(5);
		fMessage.setPreferredSize(new Dimension(700, 400)); // metas

		getContentPane().add(mainPanel);
		mainPanel.add(headerPanel, BorderLayout.NORTH);

		headerPanel.add(lFrom, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 10, 0, 5), 0, 0));
		headerPanel.add(fFrom, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 10), 0, 0));

		headerPanel.add(lTo, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 10, 0, 5), 0, 0));
		headerPanel.add(fUser, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
		headerPanel.add(fTo, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
		headerPanel.add(lCc, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 10, 0, 5), 0, 0));
		headerPanel.add(fCcUser, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
		headerPanel.add(fCc, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
		// metas: new field Bcc
		headerPanel.add(lBcc, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 10, 0, 5), 0, 0));
		headerPanel.add(fBcc, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
		// metas end
		headerPanel.add(lSubject, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 10, 0, 5), 0, 0));
		headerPanel.add(fSubject, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 10), 1, 0));

		headerPanel.add(lAttachment, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 10, 0, 5), 0, 0));
		headerPanel.add(fAttachment, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 10), 1, 0));
		// metas: new list box for text snippets
		headerPanel.add(lBoilerPlate, new GridBagConstraints(0, 8, 1, 1, 0.0,
				0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 10, 0, 5), 0, 0));
		headerPanel.add(fBoilerPlate, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 10), 1, 0));
		headerPanel.add(fResolveVariables, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 10), 1, 0));
		//
		headerPanel.add(lLetter, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 10), 1, 0));
		headerPanel.add(fLetter, new GridBagConstraints(1, 10, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 10), 1, 0));
		headerPanel.add(fAttachDocument, new GridBagConstraints(1, 11, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 10), 1, 0));
		// metas end

		mainPanel.add(fMessage, BorderLayout.CENTER);
		//
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		getContentPane().add(statusBar, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
		statusBar.setStatusDB(null);
	}	// jbInit

	/**
	 * Set all properties
	 */
	private void set(final UserEMailConfig fromUserEmailConfig, final String to, final String subject, final String message)
	{
		// Content
		setFrom(fromUserEmailConfig);
		setTo(to);
		setSubject(subject);
		setMessage(message);
		//
		statusBar.setStatusLine(m_client.getSMTPHost());
	}	// set

	/**
	 * Set Address
	 */
	public void setTo(final String newTo)
	{
		m_to = newTo;
		fTo.setText(m_to);
	}	// setTo

	/**
	 * Set CC Address
	 */
	public void setCc(final String newCc)
	{
		m_cc = newCc;
		fCc.setText(m_cc);
	}	// setCc

	/**
	 * Get Address
	 */
	public String getTo()
	{
		m_to = fTo.getText();
		return m_to;
	}	// getTo

	/**
	 * Get CC Address
	 */
	public String getCc()
	{
		m_cc = fCc.getText();
		return m_cc;
	}	// getCc

	/**
	 * Set Sender
	 */
	private void setFrom(final UserEMailConfig fromUserEmailConfig)
	{
		this.fromUserEmailConfig = fromUserEmailConfig;

		final IUserBL userBL = Services.get(IUserBL.class);

		if (fromUserEmailConfig == null)
		{
			confirmPanel.getOKButton().setEnabled(false);
			fFrom.setText("**No user**");
		}

		final ITranslatableString errmsg = userBL.checkCanSendEMail(fromUserEmailConfig);
		if (errmsg != null)
		{
			confirmPanel.getOKButton().setEnabled(false);
			fFrom.setText("** " + errmsg.translate(Env.getAD_Language()) + " **");
		}
		else
		{
			fFrom.setText(EMailAddress.toStringOrNull(fromUserEmailConfig.getEmail()));
		}
	}	// setFrom

	/**
	 * Get Sender
	 */
	private UserEMailConfig getFromUserEMailConfig()
	{
		return fromUserEmailConfig;
	}	// getFrom

	/**
	 * Set Subject
	 */
	public void setSubject(final String newSubject)
	{
		m_subject = newSubject;
		fSubject.setText(m_subject);
	}	// setSubject

	/**
	 * Get Subject
	 */
	public String getSubject()
	{
		m_subject = fSubject.getText();
		return m_subject;
	}	// getSubject

	/**
	 * Set Message
	 */
	public void setMessage(final String newMessage)
	{
		m_message = newMessage;
		fMessage.setText(m_message);
		fMessage.setCaretPosition(0);
	}   // setMessage

	/**
	 * Get Message
	 */
	public String getMessage()
	{
		m_message = fMessage.getText();
		return m_message;
	}   // getMessage

	/**
	 * Set Attachment
	 */
	public void setAttachment(final File attachment)
	{
		m_attachFile = attachment;
		if (attachment == null)
		{
			lAttachment.setVisible(false);
			fAttachment.setVisible(false);
		}
		else
		{
			lAttachment.setVisible(true);
			fAttachment.setVisible(true);
			fAttachment.setText(attachment.getName());
			fAttachment.setReadWrite(false);
		}
	}	// setAttachment

	/**
	 * Get Attachment
	 */
	public File getAttachment()
	{
		return m_attachFile;
	}	// getAttachment

	/**
	 * Action Listener - Send email
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{

		if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
		}

		if (getTo() == null || getTo().length() == 0)
		{
			return;
		}

		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			try
			{
				send();
			}
			catch (final Exception ex)
			{
				ADialog.error(0, this, "MessageNotSent", ex.getLocalizedMessage());
				return;
			}
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
		}
	}	// actionPerformed

	private void send() throws Exception
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final Properties ctx = Env.getCtx();

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		confirmPanel.getOKButton().setEnabled(false);

		final StringTokenizer st = new StringTokenizer(getTo(), " ,;", false);
		final EMailAddress to = EMailAddress.ofString(st.nextToken());
		email = m_client.createEMail(
				getFromUserEMailConfig(), 
				to, 
				getSubject(), 
				getMessage(), 
				true);
		String status = "Check Setup";
		if (email != null)
		{
			while (st.hasMoreTokens())
			{
				email.addTo(EMailAddress.ofString(st.nextToken()));
			}
			// cc
			final StringTokenizer stcc = new StringTokenizer(getCc(), " ,;", false);
			while (stcc.hasMoreTokens())
			{
				final String cc = stcc.nextToken();
				if (cc != null && cc.length() > 0)
				{
					email.addCc(EMailAddress.ofString(cc));
				}
			}
			addBcc(email); // metas

			// Attachment
			attachDocument(email); // metas
			if (m_attachFile != null && m_attachFile.exists())
			{
				email.addAttachment(m_attachFile);
			}
			// metas: begin
			final File pdf = fLetter.getPDF();
			if (pdf != null && pdf.exists())
			{
				email.addAttachment(pdf);
			}
			// metas: end

			final EMailSentStatus emailSentStatus = email.send();
			//
			if (m_user != null)
			{
				new MUserMail(Env.getCtx(), m_user.getAD_User_ID(), email, emailSentStatus).save();
			}
			if (emailSentStatus.isSentOK())
			{
				updateDocExchange(ArchiveEmailSentStatus.MESSAGE_SENT); // updating the status first, to make sure it's done when the user reads the message and clicks on OK
				ADialog.info(0, this, "MessageSent");
				dispose();
			}
			else
			{
				updateDocExchange(ArchiveEmailSentStatus.MESSAGE_NOT_SENT); // updating the status first, to make sure it's done when the user reads the message and clicks on OK
				ADialog.error(0, this, "MessageNotSent", status);
			}
		}
		else
		{
			updateDocExchange(ArchiveEmailSentStatus.MESSAGE_NOT_SENT); // updating the status first, to make sure it's done when the user reads the message and clicks on OK
			ADialog.error(0, this, "MessageNotSent", status);
		}
		//
		confirmPanel.getOKButton().setEnabled(true);
		setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * Vetoable Change - User selected
	 *
	 * @param evt
	 * @throws PropertyVetoException
	 */
	@Override
	public void vetoableChange(final PropertyChangeEvent evt) throws PropertyVetoException
	{
		final VLookup source = (VLookup)evt.getSource();
		final Object value = evt.getNewValue();
		log.info("Value=" + value);
		if (source.equals(fUser))
		{
			// fUser
			if (value == null)
			{
				fTo.setText("");
			}
			if (value instanceof Integer)
			{
				final int AD_User_ID = ((Integer)value).intValue();
				m_user = Services.get(IUserDAO.class).retrieveUserOrNull(Env.getCtx(), AD_User_ID);
				fTo.setValue(m_user.getEMail());
			}
		}
		else
		{
			// fCcUser
			if (value == null)
			{
				fCc.setText("");
			}
			if (value instanceof Integer)
			{
				final int AD_User_ID = ((Integer)value).intValue();
				m_ccuser = Services.get(IUserDAO.class).retrieveUserOrNull(Env.getCtx(), AD_User_ID);
				fCc.setValue(m_ccuser.getEMail());
			}
		}
	}	// vetoableChange

	// metas ------------------------------------------------------------------------------------------
	private BoilerPlateContext attributes = null;

	private final CLabel lLetter = new CLabel();
	private final VLetterAttachment fLetter = new VLetterAttachment(this);

	public EMailDialog(final Frame owner, final String title, 
			final UserEMailConfig fromUserEmailConfig,
			final String to, final String subject, final String message,
			final File attachment,
			final MADBoilerPlate textPreset, final I_AD_Archive archive,
			final BoilerPlateContext attributes,
			final int AD_Table_ID, final int Record_ID)
	{
		super(owner, title, true);
		this.archive = archive;
		this.attributes = attributes; // context attributes
		// this.fMessage.setAttributes(attributes);
		m_AD_Table_ID = AD_Table_ID;
		m_Record_ID = Record_ID;
		commonInit(fromUserEmailConfig, to, subject, message, attachment, textPreset);
	}	// EmailDialog

	private void updateDocExchange(final ArchiveEmailSentStatus status)
	{
		if (archive == null)
		{
			if (m_AD_Table_ID <= 0 || m_Record_ID <= 0)
			{
				return;
			}
			final TableRecordReference recordRef = TableRecordReference.of(m_AD_Table_ID, m_Record_ID);
			final IPDFArchiveProvider pdfArchiveProvider = SpringContextHolder.instance.getBean(IPDFArchiveProvider.class);
			archive = pdfArchiveProvider.getPDFArchiveForModel(recordRef, I_AD_Archive.class).orElse(null);
		}
		if (archive == null)
		{
			return;
		}
		final UserEMailConfig fromUserEMailConfig = getFromUserEMailConfig();
		Services.get(IArchiveEventManager.class).fireEmailSent(
				archive,
				fromUserEMailConfig,
				fromUserEMailConfig != null ? fromUserEMailConfig.getEmail() : null, 
				EMailAddress.ofNullableString(getTo()), 
				EMailAddress.ofNullableString(getCc()), 
				EMailAddress.ofNullableString(getBcc()), 
				status);
	}

	/**
	 * @param boilerPlate may be <code>null</code>. In that case, nothing is done.
	 */
	private boolean selectTextPreset(final MADBoilerPlate boilerPlate)
	{
		if (boilerPlate == null || boilerPlate.is_new())
		{
			return false;
		}
		fBoilerPlate.setValue(boilerPlate.getAD_BoilerPlate_ID());
		return true;
	}

	private void setBcc()
	{
		// NOTE: commented out because "DefaultBcc" column is not present
		// final MClientInfo clientInfo = MClientInfo.get(Env.getCtx(), Env.getAD_Client_ID(Env.getCtx()));
		// final Object defaultBcc = clientInfo.get_Value("DefaultBcc");
		// if (defaultBcc != null)
		// {
		// fBcc.setValue(defaultBcc);
		// }
	}

	public String getBcc()
	{
		m_bcc = fBcc.getText();
		return m_bcc;
	}	// getCc

	public void addBcc(final EMail email)
	{
		final StringTokenizer stBcc = new StringTokenizer(getBcc(), " ,;", false);
		while (stBcc.hasMoreTokens())
		{
			final String bcc = stBcc.nextToken();
			if (bcc != null && bcc.length() > 0)
			{
				email.addBcc(EMailAddress.ofString(bcc));
			}
		}
	}

	public EMail getEMail()
	{
		return email;
	}

	// Document Attachment:
	private int m_AD_Table_ID = -1;
	private int m_Record_ID = -1;
	private File m_documentFile = null;

	private void setDocumentInfo()
	{
		if (m_AD_Table_ID > 0 && m_Record_ID > 0)
		{
			return;
		}
		//
		final int windowNo = getWindowNo();
		if (windowNo < 0)
		{
			return;
		}
		//
		m_AD_Table_ID = Env.getContextAsInt(Env.getCtx(), windowNo, 0, GridTab.CTX_AD_Table_ID);
		if (m_AD_Table_ID <= 0)
		{
			return;
		}
		final String keyColumnName = Env.getContext(Env.getCtx(), windowNo, 0, GridTab.CTX_KeyColumnName);
		if (Check.isEmpty(keyColumnName))
		{
			return;
		}
		m_Record_ID = Env.getContextAsInt(Env.getCtx(), windowNo, keyColumnName);
		if (m_Record_ID <= 0)
		{
			return;
		}
	}

	/**
	 * Attach Document PDF (if requested)
	 *
	 * @param email
	 */
	private void attachDocument(final EMail email)
	{
		if (m_AD_Table_ID <= 0 || m_Record_ID <= 0)
		{
			return;
		}
		if (!fAttachDocument.isSelected())
		{
			return;
		}
		//
		if (m_documentFile == null || !m_documentFile.canRead())
		{
			final PO po = TableModelLoader.instance.getPO(Env.getCtx(), m_AD_Table_ID, m_Record_ID, ITrx.TRXNAME_None);
			final IDocument document = po != null ? Services.get(IDocumentBL.class).getDocumentOrNull(po) : null;
			if (document == null)
			{
				return;
			}
			m_documentFile = document.createPDF();
		}
		//
		email.addAttachment(m_documentFile);
	}

	int _windowNo = -1;

	private int getWindowNo()
	{
		if (_windowNo <= 0)
		{
			_windowNo = Env.getWindowNo(getParent());
		}
		if (_windowNo <= 0)
		{
			_windowNo = Env.createWindowNo(getParent());
		}
		return _windowNo;
	}

	@Override
	public void dispose()
	{
		super.dispose();

		Env.clearWinContext(getWindowNo());
	}
	// metas end
}
