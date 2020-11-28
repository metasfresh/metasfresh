/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
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
package org.compiere.grid.ed;

/*
 * #%L
 * de.metas.swat.base
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
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

import org.compiere.Adempiere;
import org.compiere.apps.ConfirmPanel;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;

import de.metas.logging.MetasfreshLastError;

/**
 * metas: Rich Text Editor, base on jj's HTML editor. <br>
 * Note: Right now it uses "text/plain" as format (so the text is not really that
 * rich).
 * 
 * (Thanks to Kim Topley "Core Swing Programming" for action menu organization)
 * 
 * @author Jorg Janke
 */
public class RichTextEditor_OLD extends CDialog {

	private static final long serialVersionUID = -6395140638056866507L;

	public static final String MSG_PICK_SNIPPET = "boilerplate.picksnippet";
	public static final String MSG_NO_SNIPPTES = "boilerplate.nosnippets";

	/**
	 * HTML Editor
	 * 
	 * @param owner
	 *            owner
	 * @param htmlText
	 *            text
	 */
	public RichTextEditor_OLD(Frame owner, String title, String htmlText,
			boolean editable) {
		super(owner,
				title == null ? Msg.getMsg(Env.getCtx(), "Editor") : title,
				true);
		init(owner, htmlText, editable);
	} // HTMLEditor

	/**
	 * HTML Editor
	 * 
	 * @param owner
	 *            owner
	 * @param htmlText
	 *            text
	 */
	public RichTextEditor_OLD(Dialog owner, String title, String htmlText,
			boolean editable) {
		super(owner,
				title == null ? Msg.getMsg(Env.getCtx(), "Editor") : title,
				true);
		init(owner, htmlText, editable);
	} // HTMLEditor

	/**
	 * Init
	 * 
	 * @param owner
	 *            owner
	 * @param htmlText
	 *            text
	 */
	private void init(Window owner, String htmlText, boolean editable) {
		try {
			jbInit();
		} catch (Exception e) {
			log.error("HTMLEditor", e);
		}
		setHtmlText(htmlText);
		editorPane.setEditable(editable);
	} // init

	/** Logger */
	private static final Logger log = LogManager.getLogger(RichTextEditor_OLD.class);
	/** The HTML Text */
	private String m_text;

	//
	private CPanel mainPanel = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	//
	private JScrollPane editorScrollPane = new JScrollPane();
	private JTextPane editorPane = new JTextPane();
	//
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();

	// Tool Bar
	private JToolBar toolBar = new JToolBar();

	// Menu Bar
	private JMenuBar menuBar = new JMenuBar();

	private final JComboBox snippetSelector = new JComboBox();;

	private Map<String, String> adempiereBoilerPlate;

	// Insert HTML Actions
	private static HTMLEditorKit.InsertHTMLTextAction[] extraActions = new HTMLEditorKit.InsertHTMLTextAction[] {
			new HTMLEditorKit.InsertHTMLTextAction("Heading 1", "<h1>h1</h1>",
					HTML.Tag.BODY, HTML.Tag.H1),
			new HTMLEditorKit.InsertHTMLTextAction("Heading 2", "<h2>h2</h2>",
					HTML.Tag.BODY, HTML.Tag.H2),
			new HTMLEditorKit.InsertHTMLTextAction("Heading 3", "<h2>h3</h2>",
					HTML.Tag.BODY, HTML.Tag.H3),
			new HTMLEditorKit.InsertHTMLTextAction("Heading 4", "<h2>h4</h2>",
					HTML.Tag.BODY, HTML.Tag.H4),
			new HTMLEditorKit.InsertHTMLTextAction("Heading 5", "<h2>h5</h2>",
					HTML.Tag.BODY, HTML.Tag.H5),
			//
			new HTMLEditorKit.InsertHTMLTextAction("Paragraph", "<p>p</p>",
					HTML.Tag.BODY, HTML.Tag.P) };

	/**
	 * Static Init
	 * 
	 * @throws Exception
	 */
	private void jbInit() throws Exception {
		// ToolBar
		toolBar.add(snippetSelector, null);
		toolBar.addSeparator();

		// Editor
		// editorPane.setContentType("text/html");
		editorPane.setContentType("text/plain");
		// Set Menu (content type must be set)
		setJMenuBar(menuBar);
		createMenuBar();

		// General Layout
		mainPanel.setLayout(mainLayout);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.add(toolBar, BorderLayout.NORTH);
		mainPanel.add(editorScrollPane, BorderLayout.CENTER);
		// Size 600x600
		editorScrollPane.setPreferredSize(new Dimension(600, 600));
		editorScrollPane.getViewport().add(editorPane, null);
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
	} // setHTMLText

	/**
	 * Create Menu Bar
	 */
	private void createMenuBar() {
		// Build Lookup
		Action[] actionArray = editorPane.getActions();
		Hashtable<Object, Action> actions = new Hashtable<Object, Action>();
		for (int i = 0; i < actionArray.length; i++) {
			Object name = actionArray[i].getValue(Action.NAME);
			// System.out.println (name);
			actions.put(name, actionArray[i]);
		}
		for (int i = 0; i < extraActions.length; i++) {
			Object name = extraActions[i].getValue(Action.NAME);
			actions.put(name, extraActions[i]);
		}

	} // createMenuBar

	/**
	 * Build Menu
	 * 
	 * @param name
	 *            name
	 * @param menuActions
	 *            menu structure
	 * @param actions
	 *            lookup
	 * @return menu
	 */
	/**
	 * Action Listener
	 * 
	 * @param e
	 *            event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		log.debug("actionPerformed - Text:" + getHtmlText());

		if (e.getActionCommand().equals(ConfirmPanel.A_OK)) {
			m_text = editorPane.getText();
			dispose();
		} else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
			dispose();
	} // actionPerformed

	/***************************************************************************
	 * Get Html Text
	 * 
	 * @return text
	 */
	public String getHtmlText() {
		return m_text;
	} // getHTMLText

	/**
	 * Set Html Text
	 * 
	 * @param htmlText
	 */
	public void setHtmlText(String htmlText) {
		m_text = htmlText;
		editorPane.setText(htmlText);
	} // setHTMLText

	/**
	 * 
	 * @param boilerPlate
	 *            a map of text snippets together with their names.
	 */
	void setBoilerPlate(final Map<String, String> boilerPlate) {

		adempiereBoilerPlate = boilerPlate;

		if (snippetSelector == null) {
			return;
		}

		snippetSelector.setEnabled(false);
		snippetSelector.removeAllItems();
		if (boilerPlate.isEmpty()) {
			snippetSelector.addItem(Msg.getMsg(Env.getCtx(), MSG_NO_SNIPPTES));
		} else {
			snippetSelector.addItem(Msg.getMsg(Env.getCtx(), MSG_PICK_SNIPPET)
					+ "...");
		}

		for (final String name : boilerPlate.keySet()) {

			snippetSelector.addItem(name);
		}

		snippetSelector.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				final String snippetName = (String) ((JComboBox) e.getSource())
						.getSelectedItem();

				final String snippet = adempiereBoilerPlate.get(snippetName);
				if (snippet == null) {
					// true for the "adempiere.PickSnippet" item
					return;
				}

				final Document document = editorPane.getDocument();

				// find out the text position where the new text should be
				// inserted
				final int caretOffset = editorPane.getSelectionStart();

				// find out if the have selected text to be replaces with
				// the new snippet
				final String selText = editorPane.getSelectedText();
				//
				try {
					if (selText != null) {
						document.remove(caretOffset, selText.length());
					}
					document.insertString(caretOffset, snippet, null);

				} catch (BadLocationException e1) {

					MetasfreshLastError.saveError(log, "BadlocationException: " + e1.getMessage()
							+ "; Illegal offset: " + e1.offsetRequested(), e1);
				}

			}
		});

		snippetSelector.setEnabled(true);
	}

	/***************************************************************************
	 * Test
	 * 
	 * @param args
	 *            ignored
	 */
	public static void main(String[] args) {
		Adempiere.startupEnvironment(true);
		JFrame frame = new JFrame("test");
		frame.setVisible(true);
		String text = "<html><p>this is a line<br>with <b>bold</> info</html>";
		int i = 0;
		while (true) {
			RichTextEditor_OLD ed = new RichTextEditor_OLD(frame, "heading " + ++i,
					text, true);
			text = ed.getHtmlText();
		}
	} // main

} // HTMLEditor

/*******************************************************************************
 * HTML Editor Menu Action
 */
class HTMLEditor_MenuAction_OLD {
	public HTMLEditor_MenuAction_OLD(String name, HTMLEditor_MenuActionExperimental[] subMenus) {
		m_name = name;
		m_subMenus = subMenus;
	}

	public HTMLEditor_MenuAction_OLD(String name, String actionName) {
		m_name = name;
		m_actionName = actionName;
	}

	public HTMLEditor_MenuAction_OLD(String name, Action action) {
		m_name = name;
		m_action = action;
	}

	private String m_name;
	private String m_actionName;
	private Action m_action;
	private HTMLEditor_MenuActionExperimental[] m_subMenus;

	public boolean isSubMenu() {
		return m_subMenus != null;
	}

	public boolean isAction() {
		return m_action != null;
	}

	public String getName() {
		return m_name;
	}

	public HTMLEditor_MenuActionExperimental[] getSubMenus() {
		return m_subMenus;
	}

	public String getActionName() {
		return m_actionName;
	}

	public Action getAction() {
		return m_action;
	}

} // MenuAction
