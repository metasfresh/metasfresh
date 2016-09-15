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


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.grid.ed.RichTextEditor;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.Lookup;
import org.compiere.model.MLookupFactory;
import org.compiere.swing.CCheckBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.letters.model.I_AD_BoilerPlate;
import de.metas.letters.model.MADBoilerPlate;

public class LetterDialog
		extends CDialog
		implements ActionListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = 5185869427506396349L;

	// private static final transient Logger log = CLogMgt.getLogger(LetterDialog.class);

	public LetterDialog(final Frame owner, final String title, final Map<String, Object> attributes)
	{
		super(owner, title, true);
		this.attributes = attributes;
		init(owner);
	}

	public LetterDialog(final Dialog owner, final String title, final Map<String, Object> attributes)
	{
		super(owner, title, true);
		this.attributes = attributes;
		init(owner);
	}

	private String m_message;

	private VLookup fBPartner = null;
	private VLookup fBoilerPlate = null;
	private CCheckBox fResolveVariables = null;

	private final CPanel mainPanel = new CPanel();
	private final CPanel headerPanel = new CPanel();
	private RichTextEditor fMessage;
	private final ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
	private final StatusBar statusBar = new StatusBar();

	@Override
	public void dispose()
	{
		fMessage.dispose();
		super.dispose();
	}

	private void init(final Container owner)
	{
		try
		{
			jbInit();
			fBPartner.setValue(attributes.get("C_BPartner_ID"));
		}
		catch (final Exception e)
		{
			ADialog.error(0, owner, "Error", e.getLocalizedMessage());
			dispose();
		}
	}

	/**
	 * Static Init
	 */
	private void jbInit() throws Exception
	{
		final Properties ctx = Env.getCtx();

		final Lookup lookupBPartner = MLookupFactory.get(ctx,
				getWindowNo(),
				0, // Column_ID
				DisplayType.TableDir,
				I_C_BPartner.COLUMNNAME_C_BPartner_ID,
				0, // AD_Reference_Value_ID,
				false, // IsParent,
				IValidationRule.AD_Val_Rule_ID_Null); // ValidationCode

		fBPartner = new VLookup(lookupBPartner.getColumnName(), false, true, false, lookupBPartner);

		fBoilerPlate = new VLookup(I_AD_BoilerPlate.COLUMNNAME_AD_BoilerPlate_ID, false, false, true, MADBoilerPlate.getAllLookup(getWindowNo()));
		fBoilerPlate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{

				final Integer id = (Integer)fBoilerPlate.getValue();
				if (id == null)
				{
					return;
				}

				final Cursor currentCursor = LetterDialog.this.getCursor();
				LetterDialog.this.setCursor(Cursor
						.getPredefinedCursor(Cursor.WAIT_CURSOR));

				final MADBoilerPlate boilerPlate = MADBoilerPlate.get(ctx,
						id);

				final String textSnippetParsed;
				try
				{
					textSnippetParsed = boilerPlate
							.getTextSnippetParsed(attributes);
				}
				catch (final RuntimeException e1)
				{
					ADialog.error(0, LetterDialog.this, "Error", e1
							.getLocalizedMessage());
					return;
				}
				finally
				{
					LetterDialog.this.setCursor(currentCursor);
				}
				setMessage(textSnippetParsed);
			}
		});

		fMessage = new RichTextEditor(attributes); // metas

		fResolveVariables = new CCheckBox(Services.get(IMsgBL.class).getMsg(ctx, "de.metas.letter.ResolveVariables"), fMessage.isResolveVariables());
		fResolveVariables.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				fMessage.setResolveVariables(fResolveVariables.isSelected());
			}
		});

		final BorderLayout mainLayout = new BorderLayout();
		final GridBagLayout headerLayout = new GridBagLayout();
		mainPanel.setLayout(mainLayout);
		headerPanel.setLayout(headerLayout);
		mainLayout.setHgap(5);
		mainLayout.setVgap(5);
		getContentPane().add(mainPanel);
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		//
		addHeaderLine(I_C_BPartner.COLUMNNAME_C_BPartner_ID, fBPartner);
		addHeaderLine(I_AD_BoilerPlate.COLUMNNAME_AD_BoilerPlate_ID, fBoilerPlate);
		addHeaderLine(null, fResolveVariables);
		//
		fMessage.setPreferredSize(new Dimension(700, 400));
		mainPanel.add(fMessage, BorderLayout.CENTER);
		//
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		getContentPane().add(statusBar, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
		statusBar.setStatusDB(null);
		//
		fMessage.requestFocusInWindow();
	}	// jbInit

	private final int getWindowNo()
	{
		return Env.getWindowNo(getParent());
	}

	private void addHeaderLine(final String labelText, final Component field)
	{
		if (labelText != null)
		{
			final Properties ctx = Env.getCtx();
			final CLabel label = new CLabel(Services.get(IMsgBL.class).translate(ctx, labelText));
			headerPanel.add(label, new GridBagConstraints(0, m_headerLine, 1, 1, 0.0,
					0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 10, 0, 5), 0, 0));
		}
		headerPanel.add(field, new GridBagConstraints(1, m_headerLine, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 10), 1, 0));
		//
		m_headerLine++;
	}

	private int m_headerLine = 0;
	private Map<String, Object> attributes;
	private boolean m_isPrinted = false;
	private boolean m_isPressedOK = false;
	private boolean m_isPrintOnOK = false;

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

	public void setAttributes(final Map<String, Object> attributes)
	{
		this.attributes = attributes;
		fMessage.setAttributes(attributes);
	}

	public Map<String, Object> getAttributes()
	{
		return attributes;
	}

	/**
	 * Action Listener - Send email
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			try
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				confirmPanel.getOKButton().setEnabled(false);
				//
				if (m_isPrintOnOK)
				{
					m_isPrinted = fMessage.print();
				}
			}
			finally
			{
				confirmPanel.getOKButton().setEnabled(false);
				setCursor(Cursor.getDefaultCursor());
			}
			// m_isPrinted = true;
			m_isPressedOK = true;
			dispose();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
		}

	}	// actionPerformed

	public void setPrintOnOK(final boolean value)
	{
		m_isPrintOnOK = value;
	}

	public boolean isPrintOnOK()
	{
		return m_isPrintOnOK;
	}

	public boolean isPressedOK()
	{
		return m_isPressedOK;
	}

	public boolean isPrinted()
	{
		return m_isPrinted;
	}

	public File getPDF()
	{
		return fMessage.getPDF(getTitle());
	}

	public RichTextEditor getRichTextEditor()
	{
		return fMessage;
	}
}	// Letter
