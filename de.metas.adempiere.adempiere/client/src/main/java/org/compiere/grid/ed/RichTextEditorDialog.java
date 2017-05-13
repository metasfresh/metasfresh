/**
 * 
 */
package org.compiere.grid.ed;

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


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;

import org.compiere.apps.ConfirmPanel;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextPane;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;

import de.metas.i18n.Msg;
import de.metas.letters.model.MADBoilerPlate;

/**
 * @author teo_sarca
 *
 */
public class RichTextEditorDialog extends CDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7306050007969338986L;
	
	private Component field = null;

	public RichTextEditorDialog(Component field, Frame owner, String title, String htmlText, boolean editable)
	{
		super(owner, "", true);
		init(owner, title, htmlText, editable);
		this.field = field;
	}
	
	public RichTextEditorDialog(Dialog owner, String title, String htmlText, boolean editable)
	{
		super(owner, "", true);
		init(owner, title, htmlText, editable);
	}

	private void init(Window owner, String title, String htmlText, boolean editable)
	{
		if (title == null)
			setTitle(Msg.getMsg(Env.getCtx(), "Editor"));
		else
			setTitle(title);
		
		// General Layout
		final CPanel mainPanel = new CPanel();
		mainPanel.setLayout(new BorderLayout());
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.add(editor, BorderLayout.CENTER);
		editor.setPreferredSize(new Dimension(600, 600));
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
		//
		setHtmlText(htmlText);
	} // init

	/** Logger */
	private Logger log = LogManager.getLogger(getClass());
	/** The HTML Text */
	private String m_text;

	private final RichTextEditor editor = new RichTextEditor();
	private final ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
	private boolean m_isPressedOK = false;
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		log.debug("actionPerformed - Text:" + getHtmlText());
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			m_text = editor.getHtmlText();
			m_isPressedOK = true;
			dispose();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
		}
	}

	public String getHtmlText()
	{
		String text = m_text;
		// us315: check if is plain rext
		if (field instanceof CTextPane)
		{
			if ("text/plain".equals(((CTextPane)field).getContentType()))
			{
				text = MADBoilerPlate.getPlainText(m_text);
				log.info("Converted html to plain text: "+text);
			}
		}
		return text;
	}

	public void setHtmlText(String htmlText)
	{
		m_text = htmlText;
		editor.setHtmlText(htmlText);
	}
	
	public boolean isPressedOK()
	{
		return m_isPressedOK ;
	}
}
