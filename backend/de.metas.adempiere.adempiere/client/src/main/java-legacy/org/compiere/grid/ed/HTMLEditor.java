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
package org.compiere.grid.ed;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Hashtable;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

import org.compiere.Adempiere;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.model.GridField;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;

/**
 *	HTML Editor.
 *	(Thanks to Kim Topley "Core Swing Programming" for action menu organization)
 *  @author Jorg Janke
 *  @version $Id: HTMLEditor.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class HTMLEditor extends CDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7502050590234610482L;


	/**
	 * 	HTML Editor
	 *	@param owner owner
	 *	@param htmlText text
	 */
	public HTMLEditor (Frame owner, String title, String htmlText, boolean editable)
	{
		super (owner, title == null ? Msg.getMsg(Env.getCtx(), "Editor") : title, true);
		init(owner, htmlText, editable);
	}	//	HTMLEditor


	/**
	 * 	HTML Editor
	 *	@param owner owner
	 *	@param htmlText text
	 */
	public HTMLEditor (Dialog owner, String title, String htmlText, boolean editable)
	{
		super (owner, title == null ? Msg.getMsg(Env.getCtx(), "Editor") : title, true);
		init(owner, htmlText, editable);
	}	//	HTMLEditor

	/**
	 * 	Init
	 *	@param owner owner
	 *	@param htmlText text
	 */
	private void init (Window owner, String htmlText, boolean editable)
	{
		try
		{
			jbInit();
		}
		catch (Exception e)
		{
			log.error("HTMLEditor", e);
		}
		setHtmlText(htmlText);
		editorPane.setEditable(editable);
		AEnv.showCenterWindow(owner, this);
	}	//	init

	/**	Logger				*/
	private Logger	log = LogManager.getLogger(getClass());
	/**	The HTML Text		*/
	private String	m_text;
	
	//
	private CPanel mainPanel = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	//
	private JScrollPane editorScrollPane = new JScrollPane();
	private JTextPane editorPane = new JTextPane();
	//
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
	
	//	Tool Bar
	private JToolBar toolBar = new JToolBar();
	private JButton bImport = AEnv.getButton("Import");
	private JButton bExport = AEnv.getButton("Export");
	private JButton bBold = AEnv.getButton("Bold");
	private JButton bItalic = AEnv.getButton("Italic");
	private JButton bUnderline = AEnv.getButton("Underline");
	
	//	Menu Bar
	private JMenuBar menuBar = new JMenuBar();
	
	private static String NAME_SIZE 	= Msg.getMsg(Env.getCtx(),"Size");
	private static String NAME_HEADING 	= Msg.getMsg(Env.getCtx(), "Heading"); 
	//	Font Size Sub-Menu
	private static HTMLEditor_MenuAction[] sizeMenu = new HTMLEditor_MenuAction[] {
		new HTMLEditor_MenuAction(NAME_SIZE + "  8",  "font-size-8"),
		new HTMLEditor_MenuAction(NAME_SIZE + " 10", "font-size-10"),
		new HTMLEditor_MenuAction(NAME_SIZE + " 12", "font-size-12"),
		new HTMLEditor_MenuAction(NAME_SIZE + " 14", "font-size-14"),
		new HTMLEditor_MenuAction(NAME_SIZE + " 16", "font-size-16"),
		new HTMLEditor_MenuAction(NAME_SIZE + " 18", "font-size-18"),
		new HTMLEditor_MenuAction(NAME_SIZE + " 24", "font-size-24"),
		new HTMLEditor_MenuAction(NAME_SIZE + " 36", "font-size-36"),
		new HTMLEditor_MenuAction(NAME_SIZE + " 48", "font-size-48")					
	};
	//	Font Family Sub-Menu
	private static HTMLEditor_MenuAction[] familyMenu = new HTMLEditor_MenuAction[] {
		new HTMLEditor_MenuAction("Sans Serif", "font-family-SansSerif"),
		new HTMLEditor_MenuAction("Monospaced", "font-family-Monospaced"),
		new HTMLEditor_MenuAction("Serif", "font-family-Serif")
	};
	//	Font Size Sub-Menu
	private static HTMLEditor_MenuAction[] styleMenu = new HTMLEditor_MenuAction[] {
		new HTMLEditor_MenuAction(Msg.getMsg(Env.getCtx(), "Bold"), "font-bold"),
		new HTMLEditor_MenuAction(Msg.getMsg(Env.getCtx(), "Italic"), "font-italic"),
		new HTMLEditor_MenuAction(Msg.getMsg(Env.getCtx(), "Underline"), "font-underline")
		// default-typed?
	};
	//	Heading Sub-Menu
	private static HTMLEditor_MenuAction[] headingMenu = new HTMLEditor_MenuAction[] {
		new HTMLEditor_MenuAction(NAME_HEADING + " 1", "Heading 1"),
		new HTMLEditor_MenuAction(NAME_HEADING + " 2", "Heading 2"),
		new HTMLEditor_MenuAction(NAME_HEADING + " 3", "Heading 3"),
		new HTMLEditor_MenuAction(NAME_HEADING + " 4", "Heading 4"),
		new HTMLEditor_MenuAction(NAME_HEADING + " 5", "Heading 5")
	};
	//	Font Menu
	private static HTMLEditor_MenuAction[] fontMenu = new HTMLEditor_MenuAction[] {
		new HTMLEditor_MenuAction(NAME_SIZE, sizeMenu),
		new HTMLEditor_MenuAction(Msg.getMsg(Env.getCtx(), "FontFamily"), familyMenu),
		new HTMLEditor_MenuAction(Msg.getMsg(Env.getCtx(), "FontStyle"), styleMenu),
		new HTMLEditor_MenuAction(NAME_HEADING, headingMenu)
	};
	// Alignment Menu
	private static HTMLEditor_MenuAction[] alignMenu = new HTMLEditor_MenuAction[] {
		new HTMLEditor_MenuAction(Msg.getMsg(Env.getCtx(), "Left"), "left-justify"),
		new HTMLEditor_MenuAction(Msg.getMsg(Env.getCtx(), "Center"), "center-justify"),
		new HTMLEditor_MenuAction(Msg.getMsg(Env.getCtx(), "Right"), "right-justify")
	};
	// Other HTML Menu
	private static HTMLEditor_MenuAction[] htmlMenu = new HTMLEditor_MenuAction[] {
		new HTMLEditor_MenuAction("Paragraph", "Paragraph"),
		new HTMLEditor_MenuAction("Table", "InsertTable"),
		new HTMLEditor_MenuAction("Table Row", "InsertTableRow"),
		new HTMLEditor_MenuAction("Table Cell", "InsertTableDataCell"),
		new HTMLEditor_MenuAction("Unordered List", "InsertUnorderedList"),
		new HTMLEditor_MenuAction("Unordered List Item", "InsertUnorderedListItem"),
		new HTMLEditor_MenuAction("Ordered List", "InsertOrderedList"),
		new HTMLEditor_MenuAction("Ordered List Item", "InsertOrderedListItem"),
		new HTMLEditor_MenuAction("Preformatted Paragraph", "InsertPre"),
		new HTMLEditor_MenuAction("Horizontal Rule", "InsertHR")
	};

	//	Insert HTML Actions
	private static HTMLEditorKit.InsertHTMLTextAction[] extraActions = 
		new HTMLEditorKit.InsertHTMLTextAction[] 
	{
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
				HTML.Tag.BODY, HTML.Tag.P)
	};


	/**
	 * 	Static Init
	 *	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		//	ToolBar
		bImport.setToolTipText(Msg.getMsg(Env.getCtx(), "Import"));
		bImport.addActionListener(this);
		bExport.setToolTipText(Msg.getMsg(Env.getCtx(), "Export"));
		bExport.addActionListener(this);
		//
		bBold.setToolTipText(Msg.getMsg(Env.getCtx(), "Bold"));
		bItalic.setToolTipText(Msg.getMsg(Env.getCtx(), "Italic"));
		bUnderline.setToolTipText(Msg.getMsg(Env.getCtx(), "Underline"));
		
		toolBar.add(bImport, null);
		toolBar.add(bExport, null);
		toolBar.addSeparator();
		toolBar.add(bBold, null);
		toolBar.add(bItalic, null);
		toolBar.add(bUnderline, null);
		toolBar.addSeparator();

		//	Editor
		editorPane.setContentType("text/html");
		//	Set Menu (content type must be set)
		setJMenuBar(menuBar);
		createMenuBar();

		//	General Layout
		mainPanel.setLayout(mainLayout);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.add(toolBar,  BorderLayout.NORTH);
		mainPanel.add(editorScrollPane, BorderLayout.CENTER);
		//	Size 600x600
		editorScrollPane.setPreferredSize(new Dimension(600,600));
		editorScrollPane.getViewport().add(editorPane, null);
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
	}	//	setHTMLText

	/**
	 * Create Menu Bar
	 */
	private void createMenuBar() 
	{
		//	Build Lookup
		Action[] actionArray = editorPane.getActions();
		Hashtable<Object,Action> actions = new Hashtable<Object,Action>();
		for (int i = 0; i < actionArray.length; i++)
		{
			Object name = actionArray[i].getValue(Action.NAME);
		//	System.out.println (name);
			actions.put(name, actionArray[i]);
		}
		for (int i = 0; i < extraActions.length; i++)
		{
			Object name = extraActions[i].getValue(Action.NAME);
			actions.put(name, extraActions[i]);
		}

		//	Add the font menu
		JMenu menu = buildMenu(Msg.getMsg(Env.getCtx(), "Font"), fontMenu, actions);
		if (menu != null)
			menuBar.add(menu);

		// Add the alignment menu
		menu = buildMenu(Msg.getMsg(Env.getCtx(), "Align"), alignMenu, actions);
		if (menu != null) 
			menuBar.add(menu);

		// Add the HTML menu
		menu = buildMenu("HTML", htmlMenu, actions);
		if (menu != null)
			menuBar.add(menu);
			
		//	Add to Button Actions
		Action targetAction = actions.get("font-bold");
		bBold.addActionListener(targetAction);
		targetAction = actions.get("font-italic");
		bItalic.addActionListener(targetAction);
		targetAction = actions.get("font-underline");
		bUnderline.addActionListener(targetAction);
		
	}	//	createMenuBar
	
	/**
	 * 	Build Menu
	 *	@param name name
	 *	@param menuActions menu structure
	 *	@param actions lookup
	 *	@return menu
	 */
	private JMenu buildMenu(String name, HTMLEditor_MenuAction[] menuActions, Hashtable actions) 
	{
		JMenu menu = new JMenu(name);
		for (int i = 0; i < menuActions.length; i++) 
		{
			HTMLEditor_MenuAction item = menuActions[i];
			if (item.isSubMenu()) 		// Recurse to handle a sub menu
			{
				JMenu subMenu = buildMenu(item.getName(), item.getSubMenus(), actions);
				if (subMenu != null)
					menu.add(subMenu);
			}
			else if (item.isAction())	//	direct action
			{
				menu.add(item.getAction());
			} 
			else 						//	find it
			{
				String actionName = item.getActionName();
				Action targetAction = (Action)actions.get(actionName);
				// Create the menu item
				JMenuItem menuItem = menu.add(item.getName());
				if (targetAction != null)
					menuItem.addActionListener(targetAction);
				else	// Action not known - disable the menu item
					menuItem.setEnabled(false);
			}
		}	//	for all actions

		// Return null if nothing was added to the menu.
		if (menu.getMenuComponentCount() == 0)
			menu = null;

		return menu;
	}	//	buildMenu

	/**
	 *	Action Listener
	 *	@param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
	//	log.debug("actionPerformed - Text:" + getHtmlText());
		//
		
		if (e.getSource() == bImport)
			cmd_import();
		else if (e.getSource() == bExport)
			cmd_export();
		//
		else if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			m_text = editorPane.getText();
			dispose();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
			dispose();
	}	//	actionPerformed

	/**
	 *	Import Text from File
	 */
	private void cmd_import()
	{
		JFileChooser jc = new JFileChooser();
		jc.setDialogTitle(Msg.getMsg(Env.getCtx(), "Import"));
		jc.setDialogType(JFileChooser.OPEN_DIALOG);
		jc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//
		if (jc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			return;

		StringBuffer sb = new StringBuffer();
		try
		{
			InputStreamReader in = new InputStreamReader (new FileInputStream (jc.getSelectedFile()));
			char[] cbuf = new char[1024];
			int count;
			while ((count = in.read(cbuf)) > 0)
				sb.append(cbuf, 0, count);
			in.close();
		}
		catch (Exception e)
		{
			log.error("HTMLEditor.import" + e.getMessage());
			return;
		}
		setHtmlText(sb.toString());
	}	//	cmd_import

	/**
	 *	Export Text to File
	 */
	private void cmd_export()
	{
		JFileChooser jc = new JFileChooser();
		jc.setDialogTitle(Msg.getMsg(Env.getCtx(), "Export"));
		jc.setDialogType(JFileChooser.SAVE_DIALOG);
		jc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//
		if (jc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
			return;

		try
		{
			EditorKit kit = editorPane.getEditorKit();
			OutputStreamWriter writer = new OutputStreamWriter
				(new FileOutputStream (jc.getSelectedFile()));
			editorPane.write(writer);
			writer.flush();
			writer.close();
		}
		catch (Exception e)
		{
			log.error("HTMLEditor.export" + e.getMessage());
		}
	}	//	cmd_export


	/*************************************************************************
	 * 	Get Html Text
	 *	@return text
	 */
	public String getHtmlText()
	{
		return m_text;
	}	//	getHTMLText

	/**
	 * 	Set Html Text
	 *	@param htmlText
	 */
	public void setHtmlText (String htmlText)
	{
		m_text = htmlText;
		editorPane.setText(htmlText);
	}	//	setHTMLText

	/**************************************************************************
	 * 	Test 
	 *	@param args ignored
	 */
	public static void main (String[] args)
	{
		Adempiere.startupEnvironment(true);
		JFrame frame = new JFrame("test");
		frame.setVisible(true);
		String text = "<html><p>this is a line<br>with <b>bold</> info</html>";
		int i = 0;
		while (true)
		{
			HTMLEditor ed = new HTMLEditor (frame, "heading " + ++i, text, true);
			text = ed.getHtmlText();
		}
	}	//	main

	// metas: begin
	public HTMLEditor (Frame owner, String title, String htmlText, boolean editable, GridField gridField)
	{
		super (owner, title == null ? Msg.getMsg(Env.getCtx(), "Editor") : title, true);
		BoilerPlateMenu.createFieldMenu(editorPane, null, gridField);
		init(owner, htmlText, editable);
	}
	// metas: end
}	//	HTMLEditor

/******************************************************************************
 * 	HTML Editor Menu Action
 */
class HTMLEditor_MenuAction 
{
	public HTMLEditor_MenuAction(String name, HTMLEditor_MenuAction[] subMenus) 
	{
		m_name = name;
		m_subMenus = subMenus;
	}

	public HTMLEditor_MenuAction(String name, String actionName) 
	{
		m_name = name;
		m_actionName = actionName;
	}

	public HTMLEditor_MenuAction(String name, Action action) 
	{
		m_name = name;
		m_action = action;
	}

	private String 			m_name;
	private String 			m_actionName;
	private Action 			m_action;
	private HTMLEditor_MenuAction[] 	m_subMenus;


	public boolean isSubMenu() 
	{
		return m_subMenus != null;
	}

	public boolean isAction() 
	{
		return m_action != null;
	}

	public String getName() 
	{
		return m_name;
	}

	public HTMLEditor_MenuAction[] getSubMenus() 
	{
		return m_subMenus;
	}

	public String getActionName() 
	{
		return m_actionName;
	}

	public Action getAction() 
	{
		return m_action;
	}

}	//	MenuAction
