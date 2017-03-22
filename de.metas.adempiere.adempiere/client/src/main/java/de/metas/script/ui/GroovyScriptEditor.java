package de.metas.script.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.adempiere.form.IClientUI;
import groovy.lang.GroovyShell;
import groovy.ui.ConsoleTextEditor;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Groovy script Editor
 *
 * @author author of initial version: Low Heng Sin
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
public class GroovyScriptEditor extends CDialog implements ActionListener
{
	public GroovyScriptEditor(final Frame owner, final String title, final String script, final int windowNo)
	{
		super(owner);

		this.windowNo = windowNo;
		this.scriptOrig = script;

		//
		// Setup window
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(title);
		if (owner != null)
		{
			setModal(true);
		}

		init();
		setScript(script);

		AEnv.showCenterScreen(this);
	}

	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	/** WindowNo */
	private final int windowNo;
	/** Original Script */
	private final String scriptOrig;
	/** The Script */
	private String script;

	//
	// UI
	private final ConsoleTextEditor editor = new ConsoleTextEditor();
	private final JButton bOK = ConfirmPanel.createOKButton(true);
	private final JButton bCancel = ConfirmPanel.createCancelButton(true);
	private final JButton bValidate = ConfirmPanel.createProcessButton(true);
	private final JTextField fResult = new JTextField();

	/**
	 * Static Layout
	 */
	void init()
	{
		//
		// Setup components
		{
			bOK.addActionListener(this);
			bCancel.addActionListener(this);
			bValidate.setText(msgBL.translate(Env.getCtx(), "Validate"));
			bValidate.addActionListener(this);
			fResult.setBackground(Color.lightGray);
			fResult.setEditable(false);
			fResult.setText("");
		}

		//
		// Layout
		{
			final CPanel mainPanel = new CPanel();
			mainPanel.setLayout(new BorderLayout());
			getContentPane().add(mainPanel);

			//
			// Center
			{
				final JScrollPane editorPane = new JScrollPane();
				mainPanel.add(editorPane, BorderLayout.CENTER);
				editorPane.setBorder(BorderFactory.createEmptyBorder());
				editorPane.setPreferredSize(new Dimension(500, 500));
				editorPane.getViewport().setView(editor);
			}    // Center

			//
			// South
			{
				final CPanel southPanel = new CPanel();
				mainPanel.add(southPanel, BorderLayout.SOUTH);
				southPanel.setLayout(new BorderLayout());

				//
				// Validate/Result panel (on left)
				{
					final CPanel resultPanel = new CPanel();
					southPanel.add(resultPanel, BorderLayout.CENTER);
					resultPanel.setLayout(new GridBagLayout());

					resultPanel.add(bValidate, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

					final JLabel lResult = new JLabel();
					lResult.setText(msgBL.getMsg(Env.getCtx(), "ScriptResult"));
					resultPanel.add(lResult, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

					resultPanel.add(fResult, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
				}

				//
				// OK/Cancel panel (on right)
				{
					final CPanel okPanel = new CPanel();
					okPanel.setLayout(new FlowLayout());
					southPanel.add(okPanel, BorderLayout.EAST);
					okPanel.add(bCancel, null);
					okPanel.add(bOK, null);
				}
			}    // South
		}
	}

	/**
	 * Set Script
	 *
	 * @param script The Script
	 */
	private void setScript(final String script)
	{
		this.script = Util.coalesce(script, "");
		editor.getTextEditor().setText(this.script);
	}

	public String getScript()
	{
		return script;
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		if (e.getSource() == bOK)
		{
			script = editor.getTextEditor().getText();
			dispose();
		}
		else if (e.getSource() == bCancel)
		{
			script = scriptOrig;
			dispose();
		}
		else if (e.getSource() == bValidate)
		{
			actionValidate();
		}
	}

	/**
	 * Validate script and update {@link #fResult}.
	 */
	private void actionValidate()
	{
		if (!Env.getUserRolePermissions().isSystemAdministrator())
		{
			fResult.setText("Not Administrator");
			return;
		}

		//
		Exception error = null;
		try
		{
			final GroovyShell sh = new GroovyShell();
			sh.parse(editor.getTextEditor().getText());
		}
		catch (final Exception ex)
		{
			error = ex;
		}
		if (error != null)
		{
			fResult.setText("Syntax errors detected.");
			Services.get(IClientUI.class).error(windowNo, error);
		}
		else
		{
			fResult.setText("No syntax errors detected.");
		}
	}
}
