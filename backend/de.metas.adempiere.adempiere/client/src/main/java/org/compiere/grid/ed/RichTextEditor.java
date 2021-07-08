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
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.StringWriter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.adempiere.images.Images;
import org.compiere.apps.ADialogDialog;
import org.compiere.print.ReportEngine;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;

import de.metas.i18n.Msg;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;
import net.sf.memoranda.ui.htmleditor.AltHTMLWriter;
import net.sf.memoranda.ui.htmleditor.HTMLEditor;

/**
 * @author teo_sarca
 * 
 */
public class RichTextEditor extends CPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5543325281948291601L;

	// /** Logger */
	// private Logger log = CLogMgt.getLogger(getClass());

	private JButton butPrint;

	private final HTMLEditor editor = new HTMLEditor();

	private final BoilerPlateMenu boilerPlateMenu = new BoilerPlateMenu();

	/**
	 * Shall we add BoilderPlate menu in our right-click menu? Disabling it is useful for debugging/testing because the
	 * boilerplate menu requires database connection.
	 */
	public static boolean enableBoilerPlateMenu = true;

	public RichTextEditor()
	{
		init();
	}

	public RichTextEditor(final BoilerPlateContext attributes)
	{
		setAttributes(attributes);
		init();
	}

	private void init()
	{
		// Editor
		customizeHTMLEditor();

		// General Layout
		final CPanel mainPanel = this;
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(editor);
	}

	public void dispose()
	{
		this.editor.getEditorKeymap().removeBindings();
	}

	@Override
	public void requestFocus()
	{
		this.editor.requestFocus();
	}

	@Override
	public boolean requestFocus(boolean temporary)
	{
		return this.editor.requestFocus(temporary);
	}

	@Override
	public boolean requestFocusInWindow()
	{
		return this.editor.requestFocusInWindow();
	}

	private void customizeHTMLEditor()
	{
		mapEditorAction(KeyEvent.VK_B, KeyEvent.CTRL_MASK, this.editor.boldAction);
		mapEditorAction(KeyEvent.VK_I, KeyEvent.CTRL_MASK, this.editor.italicAction);
		mapEditorAction(KeyEvent.VK_U, KeyEvent.CTRL_MASK, this.editor.underAction);
		//
		mapEditorAction(KeyEvent.VK_Z, KeyEvent.CTRL_MASK, this.editor.undoAction);
		mapEditorAction(KeyEvent.VK_Y, KeyEvent.CTRL_MASK, this.editor.redoAction);
		//
		mapEditorAction(KeyEvent.VK_L, KeyEvent.CTRL_MASK, this.editor.lAlignAction);
		mapEditorAction(KeyEvent.VK_E, KeyEvent.CTRL_MASK, this.editor.cAlignAction);
		mapEditorAction(KeyEvent.VK_R, KeyEvent.CTRL_MASK, this.editor.rAlignAction);
		//
		//
		if (enableBoilerPlateMenu)
		{
			final JMenu menuSnippet = boilerPlateMenu.createInsertSnippetMenu(editor.getTextComponent());
			if (menuSnippet != null)
			{
				this.editor.jMenuAdditionList.add(menuSnippet);
			}
			//
			final JMenu menuVars = boilerPlateMenu.createInsertVariableMenu(editor.getTextComponent());
			if (menuVars != null)
			{
				this.editor.jMenuAdditionList.add(menuVars);
			}
		}

		//
		// Hide Actions that are not supported by JasperReports:
		final JComponent[] hiddenActions = new JComponent[] {
				this.editor.blockCB, // Hide Paragraph Drop down because is not supported by Jasper
				this.editor.propsActionB,
				this.editor.lAlignActionB,
				this.editor.cAlignActionB,
				this.editor.rAlignActionB,
				this.editor.jAlignActionB,
				this.editor.imageActionB,
				this.editor.tableActionB,
				this.editor.linkActionB,
//				this.editor.brActionB,
				this.editor.hrActionB,
//				this.editor.insCharActionB,
		};
		for (JComponent c : hiddenActions)
		{
			hideToolbarAction(c);
		}

		//
		// Toolbar Button: Preview:
		addToolbarButton("PrintPreview16", new AbstractAction()
		{
			private static final long serialVersionUID = 4942007238006608191L;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				actionPreview();
			}
		});
		//
		// Toolbar Button: Print:
		butPrint = addToolbarButton("Print16", new AbstractAction()
		{
			private static final long serialVersionUID = 2571639214828243909L;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				print();
			}
		});
	}

	private void hideToolbarAction(JComponent c)
	{
		c.setVisible(false);
		c.setEnabled(false);
	}

	private JButton addToolbarButton(String icon2, Action action)
	{
		final JButton button = new JButton();
		button.setAction(action);
		button.setMaximumSize(new Dimension(22, 22));
		button.setMinimumSize(new Dimension(22, 22));
		button.setPreferredSize(new Dimension(22, 22));
		button.setBorderPainted(false);
		button.setFocusable(false);
		button.setText("");
		button.setIcon(Images.getImageIcon2(icon2));
		this.editor.editToolbar.add(button);
		return button;
	}

	private void mapEditorAction(int keyCode, int modifiers, Action a)
	{
		final KeyStroke ks = KeyStroke.getKeyStroke(keyCode, modifiers);
		// editor.getKeymap().removeKeyStrokeBinding(ks);
		// editor.getKeymap().addActionForKeyStroke(ks, a);
		//
		String name = (String)a.getValue(Action.SHORT_DESCRIPTION);
		if (name == null)
			name = a.toString();
		editor.getEditorActionMap().put(name, a);
		editor.getEditorInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ks, name);
	}

	public void setHtmlText(String htmlText)
	{
		editor.setText(htmlText);
	}

	public String getHtmlText()
	{
		try
		{
			StringWriter sw = new StringWriter();
			new AltHTMLWriter(sw, editor.getDocument()).write();
			// new HTMLWriter(sw, editor.document).write();
			String html = sw.toString();
			return html;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Compatible with CTextArea
	 * 
	 * @param text
	 * @see #setHtmlText(String)
	 */
	public void setText(String text)
	{
		if (!isHtml(text))
		{
			setHtmlText(convertToHtml(text));
		}
		else
		{
			setHtmlText(text);
		}
	}

	/**
	 * Compatible with CTextArea
	 * 
	 * @return html text
	 * @see #getHtmlText()
	 */
	public String getText()
	{
		return getHtmlText();
	}

	/**
	 * Compatible with CTextArea
	 * 
	 * @param position
	 */
	public void setCaretPosition(int position)
	{
		this.editor.setCaretPosition(position);
	}

	public BoilerPlateContext getAttributes()
	{
		return boilerPlateMenu.getAttributes();
	}

	public void setAttributes(final BoilerPlateContext attributes)
	{
		boilerPlateMenu.setAttributes(attributes);
	}

	public File getPDF(String fileNamePrefix)
	{
		return MADBoilerPlate.getPDF(fileNamePrefix, getHtmlText(), getAttributes());
	}

	private void actionPreview()
	{
		// final ReportEngine re = getReportEngine();
		// ReportCtl.preview(re);
		File pdf = getPDF(null);
		if (pdf != null)
		{
			Env.startBrowser(pdf.toURI().toString());
		}
	}

	private Dialog getParentDialog()
	{
		Dialog parent = null;
		Container e = getParent();
		while (e != null)
		{
			if (e instanceof Dialog)
			{
				parent = (Dialog)e;
				break;
			}
			e = e.getParent();
		}
		return parent;
	}

	public boolean print()
	{
		ReportEngine re = MADBoilerPlate.getReportEngine(getHtmlText(), getAttributes());
		int retValue = ADialogDialog.A_CANCEL;
		do
		{
			re.print();
			ADialogDialog d = new ADialogDialog(getParentDialog(),
					"Print", // Env.getHeader(Env.getCtx(), windowNo),
					Msg.getMsg(Env.getCtx(), "PrintoutOK?"),
					JOptionPane.QUESTION_MESSAGE);
			retValue = d.getReturnCode();
		}
		while (retValue == ADialogDialog.A_CANCEL);
		return retValue == ADialogDialog.A_OK;
	}

	public static boolean isHtml(String s)
	{
		if (s == null)
			return false;
		String s2 = s.trim().toUpperCase();
		return s2.startsWith("<HTML>");
	}

	public static String convertToHtml(String plainText)
	{
		if (plainText == null)
			return null;
		return plainText.replaceAll("[\r]*\n", "<br/>\n");
	}

	public boolean isResolveVariables()
	{
		return boilerPlateMenu.isResolveVariables();
	}

	public void setResolveVariables(boolean isResolveVariables)
	{
		boilerPlateMenu.setResolveVariables(isResolveVariables);
	}

	public void setEnablePrint(boolean enabled)
	{
		butPrint.setEnabled(enabled);
		butPrint.setVisible(enabled);
	}
}
