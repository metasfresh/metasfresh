package org.compiere.apps;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import java.util.Properties;

import javax.swing.JPopupMenu;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.GridTab;
import org.compiere.model.I_R_Request;
import org.compiere.model.MUser;
import org.compiere.swing.CMenuItem;
import org.compiere.util.Env;

import de.metas.email.EMail;
import de.metas.letters.model.IEMailEditor;
import de.metas.letters.model.MADBoilerPlate;

public class AEMailLetter implements ActionListener
{
	// private static final transient Logger log = CLogMgt.getLogger(getClass());

	public static final String ACTION_Name = "EMailLetter";

	/**
	 * 
	 * @param parent
	 * @param small if <code>true</code>, than use a small icon.
	 * @return
	 */
	public static AppsAction createAppsAction(final APanel parent, final boolean small)
	{
		final AEMailLetter app = new AEMailLetter(parent, small);
		return app.action;
	}

	private final APanel parent;
	private final AppsAction action;

	private final JPopupMenu m_popup = new JPopupMenu("RequestMenu");
	private final CMenuItem m_menuEMail;
	private final CMenuItem m_menuLetter;

	private AEMailLetter(final APanel parent, final boolean small)
	{
		this.parent = parent;

		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final Properties ctx = Env.getCtx();

		m_menuEMail = new CMenuItem(msgBL.getMsg(ctx, "de.metas.letters.EMail"));
		m_menuLetter = new CMenuItem(msgBL.getMsg(ctx, "de.metas.letters.Letter"));

		action = AppsAction.builder()
				.setAction(ACTION_Name)
				.setSmallSize(small)
				.build();
		action.setDelegate(this);

		m_popup.add(m_menuEMail).addActionListener(this);
		m_popup.add(m_menuLetter).addActionListener(this);
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		try
		{
			actionPerformed0(e);
		}
		catch (final Exception ex)
		{
			ADialog.error(parent.getWindowNo(), parent, ex);
		}
	}

	public void actionPerformed0(final ActionEvent e)
	{
		boolean doRefresh = false;
		if (e.getSource() == action.getButton())
		{
			if (action.getButton().isShowing())
			{
				m_popup.show(action.getButton(), 0, action.getButton().getHeight());	// below button
			}
		}
		else if (e.getSource() == m_menuEMail)
		{
			actionEMail();
			doRefresh = true;
		}
		else if (e.getSource() == m_menuLetter)
		{
			actionLetter();
			doRefresh = true;
		}
		//
		// TODO: Workaround to following issue:
		// 1. open request window
		// 2. use letter feature to create a letter for a BP
		// 3. search for the new request
		// 4. look at attachment button
		// REsult: Attachment Button is indicating no attachment.
		// If you click it you see that there is no attachment in that little attachment window.
		// When you close that window the attachment button becomes orange, indicating that there is a file.
		// When you now open attachment window, letter is shown.
		final GridTab tab = parent.getCurrentTab();
		if (doRefresh && tab != null && tab.getTableName().equals(I_R_Request.Table_Name))
		{
			tab.loadAttachments();
		}
	}

	private void actionEMail()
	{
		MADBoilerPlate.sendEMail(new IEMailEditor()
		{
			@Override
			public Object getBaseObject()
			{
				return parent.getCurrentTab();
			}

			@Override
			public int getAD_Table_ID()
			{
				return parent.getCurrentTab().getAD_Table_ID();
			}

			@Override
			public int getRecord_ID()
			{
				return parent.getCurrentTab().getRecord_ID();
			}

			@Override
			public EMail sendEMail(final MUser from, final String toEmail, final String subject, final Map<String, Object> variables)
			{
				final Properties ctx = Env.getCtx();
				final EMailDialog dialog = new EMailDialog(
						Env.getWindow(parent.getWindowNo()),
						Services.get(IMsgBL.class).getMsg(ctx, "de.metas.letters.EMail"),
						from,
						toEmail,
						subject,
						"",			// message
						null,		// attachments
						null,		// textPreset
						null,		// I_AD_Archive
						variables,	// attributes
						getAD_Table_ID(), getRecord_ID() // Document Info
				);
				return dialog.getEMail();
			}
		});
	}

	private void actionLetter()
	{
		final Map<String, Object> variables = MADBoilerPlate.createEditorContext(parent.getCurrentTab());
		final LetterDialog dialog = new LetterDialog(
				Env.getWindow(parent.getWindowNo()),
				Services.get(IMsgBL.class).getMsg(Env.getCtx(), "de.metas.letters.Letter"),
				variables
				);
		dialog.setPrintOnOK(true);
		dialog.getRichTextEditor().setEnablePrint(false);
		AEnv.showCenterScreen(dialog);
		if (dialog.isPrinted())
		{
			final File pdf = dialog.getPDF();
			MADBoilerPlate.createRequest(pdf,
					parent.getCurrentTab().getAD_Table_ID(), parent.getCurrentTab().getRecord_ID(),
					variables);
		}
	}
}
