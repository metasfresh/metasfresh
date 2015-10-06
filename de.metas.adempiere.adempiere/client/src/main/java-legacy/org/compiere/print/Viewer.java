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
 * Contributor: phib [ 1566335 ] Report View scrolling (Bug 1566328)          *
 *              Teo Sarca [ 1619449 ] Minor typo problem                      *
 *****************************************************************************/
package org.compiere.print;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.asp.IASPFiltersFactory;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.images.Images;
import org.adempiere.util.Services;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.AMenu;
import org.compiere.apps.AWindow;
import org.compiere.apps.AWindowListener;
import org.compiere.apps.AppsAction;
import org.compiere.apps.EMailDialog;
import org.compiere.apps.StatusBar;
import org.compiere.apps.WindowMenu;
import org.compiere.apps.search.Find;
import org.compiere.model.GridField;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.MQuery;
import org.compiere.model.MUser;
import org.compiere.print.layout.Page;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CFrame;
import org.compiere.swing.CLabel;
import org.compiere.swing.CMenuItem;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.ExtensionFileFilter;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;
import org.compiere.util.Msg;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

import de.metas.adempiere.form.IClientUI;

/**
 *	Print View Frame
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: Viewer.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 * globalqss: integrate phib contribution from 
 *   http://sourceforge.net/tracker/index.php?func=detail&aid=1566335&group_id=176962&atid=879334
 * globalqss: integrate Teo Sarca bug fixing
 * Colin Rooney 2007/03/20 RFE#1670185 & BUG#1684142
 *                         Extend security to Info queries
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 				<li>FR [ 1762466 ] Add "Window" menu to report viewer.
 * 				<li>FR [ 1894640 ] Report Engine: Excel Export support
 */
public class Viewer extends CFrame
	implements ActionListener, ChangeListener, WindowStateListener
{
	/**
	 *	Viewer Constructor
	 *	@param re report engine
	 */
	public Viewer (ReportEngine re)
	{
		super();
		log.info("");
		m_WindowNo = Env.createWindowNo(this);
		m_reportEngine = re;
		m_AD_Table_ID = re.getPrintFormat().getAD_Table_ID();
		if (!Env.getUserRolePermissions().isCanReport(m_AD_Table_ID))
		{
			ADialog.error(m_WindowNo, this, "AccessCannotReport", m_reportEngine.getName());
			this.dispose();
		}
		m_isCanExport = Env.getUserRolePermissions().isCanExport(m_AD_Table_ID);
		try
		{
			m_viewPanel = re.getView();
			m_ctx = m_reportEngine.getCtx();
			jbInit();
			dynInit();
			if (!m_viewPanel.isArchivable())
				log.warning("Cannot archive Document");
			AEnv.showCenterScreen(this);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
			ADialog.error(m_WindowNo, this, "LoadError", e.getLocalizedMessage());
			this.dispose();
		}
	}	//	Viewer

	/** Window No					*/
	private int                 m_WindowNo;
	/**	Print Context				*/
	private Properties			m_ctx;
	/** Page No						*/
	private int					m_pageNo = 1;
	/**	Max Page Number				*/
	private int 				m_pageMax = 1;
	/** View Pane					*/
	private View 				m_viewPanel;
	/**	Setting Values				*/
	private boolean				m_setting = false;
	/**	Report Engine				*/
	private ReportEngine 		m_reportEngine;
	/** Drill Down/Across			*/
	private boolean				m_drillDown = true;
	/** Table ID					*/
	private int					m_AD_Table_ID = 0;
	private boolean				m_isCanExport;
	
	private MQuery 		m_ddQ = null;
	private MQuery 		m_daQ = null;
	private CMenuItem 	m_ddM = null;
	private CMenuItem 	m_daM = null;

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(Viewer.class);

	//
	private CPanel northPanel = new CPanel();
	private JScrollPane centerScrollPane = new JScrollPane();
	private StatusBar statusBar = new StatusBar(false);
	private JMenuBar menuBar = new JMenuBar();
	private JToolBar toolBar = new JToolBar();
	private CButton bPrint = new CButton();
	private CButton bSendMail = new CButton();
	private CButton bPageSetup = new CButton();
	private CButton bArchive = new CButton();
	private BorderLayout northLayout = new BorderLayout();
	private CButton bCustomize = new CButton();
	private CButton bEnd = new CButton();
	private CButton bFind = new CButton();
	private CButton bExport = new CButton();
	private CComboBox comboReport = new CComboBox();
	private CButton bPrevious = new CButton();
	private CButton bNext = new CButton();
	private SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1,1,100,1);
	private JSpinner spinner = new JSpinner(spinnerModel);
	private CLabel labelDrill = new CLabel();
	private CComboBox comboDrill = new CComboBox();
//	private CComboBox comboZoom = new CComboBox(View.ZOOM_OPTIONS);


	/**
	 * 	Static Layout
	 * 	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.setIconImage(Images.getImage2("mReport"));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//
		northPanel.setLayout(northLayout);
		this.getContentPane().add(northPanel, BorderLayout.NORTH);
		northPanel.add(toolBar,  BorderLayout.EAST);
		this.getContentPane().add(centerScrollPane, BorderLayout.CENTER);
		centerScrollPane.getViewport().add(m_viewPanel, null);
		// pb add: set scrolling with scrollbar buttons to move by 20 pixels
		// each press
		centerScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		centerScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
		// end pb
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);

		//	ToolBar
		this.setJMenuBar(menuBar);
		//	Page Control
		toolBar.add(bPrevious);
		toolBar.add(spinner);
		spinner.setToolTipText(Msg.getMsg(m_ctx, "GoToPage"));
		toolBar.add(bNext);
		//	Zoom Level
		toolBar.addSeparator();
//		toolBar.add(comboZoom, null);
//		comboZoom.setToolTipText(Msg.getMsg(m_ctx, "Zoom"));
		//	Drill
		toolBar.addSeparator();
		labelDrill.setText(Msg.getMsg(m_ctx, "Drill") + ": ");
		toolBar.add(labelDrill);
		toolBar.add(comboDrill);
		comboDrill.setToolTipText(Msg.getMsg(m_ctx, "Drill"));
		//	Format, Customize, Find
		toolBar.addSeparator();
		toolBar.add(comboReport);
		comboReport.setToolTipText(Msg.translate(m_ctx, "AD_PrintFormat_ID"));
		toolBar.add(bCustomize);
		bCustomize.setToolTipText(Msg.getMsg(m_ctx, "PrintCustomize"));
		toolBar.add(bFind);
		bFind.setToolTipText(Msg.getMsg(m_ctx, "Find"));
		toolBar.addSeparator();
		//	Print/Export
		toolBar.add(bPrint);
		toolBar.addSeparator();
		toolBar.add(bPageSetup);
		bPageSetup.setToolTipText(Msg.getMsg(m_ctx, "PageSetup"));
		toolBar.add(bSendMail);
		toolBar.add(bArchive);
		if (m_isCanExport)
		{
			bExport.setToolTipText(Msg.getMsg(m_ctx, "Export"));
			toolBar.add(bExport);
		}
		// 	End
		toolBar.addSeparator();
		toolBar.add(bEnd, null);
		bEnd.setToolTipText(Msg.getMsg(m_ctx, "End"));
	}	//	jbInit

	/**
	 * 	Dynamic Init
	 */
	private void dynInit()
	{
		createMenu();
//		comboZoom.addActionListener(this);
		//	Change Listener to set Page no
		//pb comment this out so that scrolling works normally
		//centerScrollPane.getViewport().addChangeListener(this);
		// end pb

		//	Max Page
		m_pageMax = m_viewPanel.getPageCount();
		spinnerModel.setMaximum(new Integer(m_pageMax));
		spinner.addChangeListener(this);

		fillComboReport(m_reportEngine.getPrintFormat().get_ID());

		//	View Panel Mouse Listener
		m_viewPanel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (SwingUtilities.isRightMouseButton(e))
					mouse_clicked(e,true);
				else if (e.getClickCount() > 1)
					mouse_clicked(e,false);
			}
		});

		//	fill Drill Options (Name, TableName)
		comboDrill.addItem(new ValueNamePair (null,""));
		String sql = "SELECT t.AD_Table_ID, t.TableName, e.PrintName, NULLIF(e.PO_PrintName,e.PrintName) "
			+ "FROM AD_Column c "
			+ " INNER JOIN AD_Column used ON (c.ColumnName=used.ColumnName)"
			+ " INNER JOIN AD_Table t ON (used.AD_Table_ID=t.AD_Table_ID AND t.IsView='N' AND t.AD_Table_ID <> c.AD_Table_ID)"
			+ " INNER JOIN AD_Column cKey ON (t.AD_Table_ID=cKey.AD_Table_ID AND cKey.IsKey='Y')"
			+ " INNER JOIN AD_Element e ON (cKey.ColumnName=e.ColumnName) "
			+ "WHERE c.AD_Table_ID=? AND c.IsKey='Y' "
			+ "ORDER BY 3";
		boolean trl = !Env.isBaseLanguage(Env.getCtx(), "AD_Element");
		if (trl)
			sql = "SELECT t.AD_Table_ID, t.TableName, et.PrintName, NULLIF(et.PO_PrintName,et.PrintName) "
				+ "FROM AD_Column c"
				+ " INNER JOIN AD_Column used ON (c.ColumnName=used.ColumnName)"
				+ " INNER JOIN AD_Table t ON (used.AD_Table_ID=t.AD_Table_ID AND t.IsView='N' AND t.AD_Table_ID <> c.AD_Table_ID)"
				+ " INNER JOIN AD_Column cKey ON (t.AD_Table_ID=cKey.AD_Table_ID AND cKey.IsKey='Y')"
				+ " INNER JOIN AD_Element e ON (cKey.ColumnName=e.ColumnName)"
				+ " INNER JOIN AD_Element_Trl et ON (e.AD_Element_ID=et.AD_Element_ID) "
				+ "WHERE c.AD_Table_ID=? AND c.IsKey='Y'"
				+ " AND et.AD_Language=? "
				+ "ORDER BY 3";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_reportEngine.getPrintFormat().getAD_Table_ID());
			if (trl)
				pstmt.setString(2, Env.getAD_Language(Env.getCtx()));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				String tableName = rs.getString(2);
				String name = rs.getString(3);
				String poName = rs.getString(4);
				if (poName != null)
					name += "/" + poName;
				comboDrill.addItem(new ValueNamePair (tableName, name));
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		if (comboDrill.getItemCount() == 1)
		{
			labelDrill.setVisible(false);
			comboDrill.setVisible(false);
		}
		else
			comboDrill.addActionListener(this);

		revalidateViewer();
	}	//	dynInit

	/**
	 * 	Fill ComboBox comboReport (report options)
	 *  @param AD_PrintFormat_ID item to be selected
	 */
	private void fillComboReport(int AD_PrintFormat_ID)
	{
		comboReport.removeActionListener(this);
		comboReport.removeAllItems();
		KeyNamePair selectValue = null;
		//	fill Report Options
		String sql = Env.getUserRolePermissions().addAccessSQL(
			"SELECT AD_PrintFormat_ID, Name, Description "
				+ "FROM AD_PrintFormat "
				+ "WHERE AD_Table_ID=? "
				//Added Lines by Armen
				+ "AND IsActive='Y' "
				//End of Added Lines
				+ "ORDER BY Name",
			"AD_PrintFormat", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		int AD_Table_ID = m_reportEngine.getPrintFormat().getAD_Table_ID();
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Table_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				KeyNamePair pp = new KeyNamePair(rs.getInt(1), rs.getString(2));
				comboReport.addItem(pp);
				if (rs.getInt(1) == AD_PrintFormat_ID)
					selectValue = pp;
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		StringBuffer sb = new StringBuffer("** ").append(Msg.getMsg(m_ctx, "NewReport")).append(" **");
		KeyNamePair pp = new KeyNamePair(-1, sb.toString());
		comboReport.addItem(pp);
		if (selectValue != null)
			comboReport.setSelectedItem(selectValue);
		comboReport.addActionListener(this);
	}	//	fillComboReport

	/**
	 * 	Revalidate settings after change of environment
	 */
	private void revalidateViewer()
	{
		m_pageMax = m_viewPanel.getPageCount();
		spinnerModel.setMaximum(new Integer(m_pageMax));

		//	scroll area (page size dependent)
		centerScrollPane.setPreferredSize(new Dimension
			(m_viewPanel.getPaperWidth()+30, m_viewPanel.getPaperHeight()+15));
		centerScrollPane.getViewport().setViewSize(new Dimension
			(m_viewPanel.getPaperWidth()+2*View.MARGIN,
			m_viewPanel.getPaperHeight()+2*View.MARGIN));

		//	Report Info
		setTitle(Msg.getMsg(m_ctx, "Report") + ": " + m_reportEngine.getName() + "  " + Env.getHeader(m_ctx, 0));
		StringBuffer sb = new StringBuffer ();
		sb.append(m_viewPanel.getPaper().toString(m_ctx))
			.append(" - ").append(Msg.getMsg(m_ctx, "DataCols")).append("=")
			.append(m_reportEngine.getColumnCount())
			.append(", ").append(Msg.getMsg(m_ctx, "DataRows")).append("=")
			.append(m_reportEngine.getRowCount());
		statusBar.setStatusLine(sb.toString());
		//
		setPage(m_pageNo);
	}	//	revalidate


	/**
	 *  Create Menu
	 */
	private void createMenu()
	{
		//      File
		JMenu mFile = AEnv.getMenu("File");
		menuBar.add(mFile);
		AEnv.addMenuItem("PrintScreen", null, KeyStroke.getKeyStroke(KeyEvent.VK_PRINTSCREEN, 0), mFile, this);
		AEnv.addMenuItem("ScreenShot", null, KeyStroke.getKeyStroke(KeyEvent.VK_PRINTSCREEN, Event.SHIFT_MASK), mFile, this);
		AEnv.addMenuItem("Report", null, KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.ALT_MASK), mFile, this);
		mFile.addSeparator();
		AEnv.addMenuItem("PrintCustomize", "Preference", null, mFile, this);
		AEnv.addMenuItem("Translate", null, null, mFile, this);
		AEnv.addMenuItem("Find", null, KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK), mFile, this);
		mFile.addSeparator();
		AEnv.addMenuItem("PageSetup", null, null, mFile, this);
		AEnv.addMenuItem("Print", null, KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK), mFile, this);
		if (m_isCanExport)
			AEnv.addMenuItem("Export", null, null, mFile, this);
		mFile.addSeparator();
		AEnv.addMenuItem("End", null, KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.ALT_MASK), mFile, this);
		AEnv.addMenuItem("Logout", null, KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.SHIFT_MASK+Event.ALT_MASK), mFile, this);
		AEnv.addMenuItem("Exit", null, KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.SHIFT_MASK+Event.ALT_MASK), mFile, this);

		//      View
		JMenu mView = AEnv.getMenu("View");
		menuBar.add(mView);

		if (Env.getUserRolePermissions().isAllow_Info_Product())
		{
			AEnv.addMenuItem("InfoProduct", null, KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.ALT_MASK), mView, this);			
		}
		if (Env.getUserRolePermissions().isAllow_Info_BPartner())
		{
			AEnv.addMenuItem("InfoBPartner", null, KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.ALT_MASK+Event.CTRL_MASK), mView, this);
		}
		if (Env.getUserRolePermissions().hasPermission(IUserRolePermissions.PERMISSION_ShowAcct) && Env.getUserRolePermissions().isAllow_Info_Account())
		{
			AEnv.addMenuItem("InfoAccount", null, KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.ALT_MASK+Event.CTRL_MASK), mView, this);
		}
		if (Env.getUserRolePermissions().isAllow_Info_Schedule())
		{
			AEnv.addMenuItem("InfoSchedule", null, null, mView, this);			
		}
		mView.addSeparator();
		if (Env.getUserRolePermissions().isAllow_Info_Order())
		{
			AEnv.addMenuItem("InfoOrder", "Info", null, mView, this);	
		}
		if (Env.getUserRolePermissions().isAllow_Info_Invoice())
		{
			AEnv.addMenuItem("InfoInvoice", "Info", null, mView, this);			
		}
		if (Env.getUserRolePermissions().isAllow_Info_InOut())
		{
			AEnv.addMenuItem("InfoInOut", "Info", null, mView, this);	
		}
		if (Env.getUserRolePermissions().isAllow_Info_Payment())
		{
			AEnv.addMenuItem("InfoPayment", "Info", null, mView, this);	
		}
		if (Env.getUserRolePermissions().isAllow_Info_CashJournal())
		{
			AEnv.addMenuItem("InfoCashLine", "Info", null, mView, this);	
		}
		if (Env.getUserRolePermissions().isAllow_Info_Resource())
		{
			AEnv.addMenuItem("InfoAssignment", "Info", null, mView, this);	
		}
		if (Env.getUserRolePermissions().isAllow_Info_Asset())
		{
			AEnv.addMenuItem("InfoAsset", "Info", null, mView, this);	
		}
		//		Go
		JMenu mGo = AEnv.getMenu("Go");
		menuBar.add(mGo);
		AEnv.addMenuItem("First", "First", KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, Event.ALT_MASK),	mGo, this);
		AEnv.addMenuItem("PreviousPage", "Previous", KeyStroke.getKeyStroke(KeyEvent.VK_UP, Event.ALT_MASK), mGo, this);
		AEnv.addMenuItem("NextPage", "Next", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, Event.ALT_MASK), mGo, this);
		AEnv.addMenuItem("Last", "Last", KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, Event.ALT_MASK), mGo, this);

		//      Tools
		JMenu mTools = AEnv.getMenu("Tools");
		menuBar.add(mTools);
		// metas-tsa: Drop unneeded menu items (09271)
		//@formatter:off
//		AEnv.addMenuItem("Calculator", null, null, mTools, this);
//		AEnv.addMenuItem("Calendar", null, null, mTools, this);
//		MUser user = MUser.get(Env.getCtx());
//		if (user.isAdministrator())
//			AEnv.addMenuItem("Editor", null, null, mTools, this);
//		AEnv.addMenuItem("Script", null, null, mTools, this);
//		mTools.addSeparator();
		//@formatter:on
		if (Env.getUserRolePermissions().isShowPreference())
		{
			AEnv.addMenuItem("Preference", null, null, mTools, this);
		}
		
		//		Window
		AMenu aMenu = (AMenu)Env.getWindow(0);
		JMenu mWindow = new WindowMenu(aMenu.getWindowManager(), this);
		menuBar.add(mWindow);

		//      Help
		JMenu mHelp = AEnv.getMenu("Help");
		menuBar.add(mHelp);
		AEnv.addMenuItem("Online", null, null, mHelp, this);
		AEnv.addMenuItem("SendMail", null, null, mHelp, this);
		AEnv.addMenuItem("About", null, null, mHelp, this);

		//	---- ToolBar ----
		//
		setButton(bPrint, "Print", "Print");
		setButton(bSendMail, "SendMail", "SendMail");
		setButton(bPageSetup, "PageSetup", "PageSetup");
		setButton(bArchive, "Archive", "Archive");
		if (m_isCanExport)
			setButton(bExport, "Export", "Export");
		//
		setButton(bNext, "NextPage", "Next");
		setButton(bPrevious, "PreviousPage", "Previous");
		//
		setButton(bFind, "Find", "Find");
		setButton(bCustomize, "PrintCustomize", "Preference");
		//
		setButton(bEnd, "End", "End");
	}   //  createMenu

	/**
	 *	Set Button
	 *  @param button button
	 *  @param cmd command
	 *  @param file fine mame
	 */
	private void setButton (AbstractButton button, String cmd, String file)
	{
		String text = Msg.getMsg(m_ctx, cmd);
		button.setToolTipText(text);
		button.setActionCommand(cmd);
		//
		ImageIcon ii24 = Images.getImageIcon2(file + "24");
		if (ii24 != null)
			button.setIcon(ii24);
		button.setMargin(AppsAction.BUTTON_INSETS);
		button.setPreferredSize(AppsAction.BUTTON_SIZE);
		button.addActionListener(this);
	}	//	setButton

	/**
	 * 	Dispose
	 */
	@Override
	public void dispose()
	{
		Env.clearWinContext(m_WindowNo);
		m_reportEngine = null;
		m_viewPanel = null;
		m_ctx = null;
		super.dispose();
	}	//	dispose

	
	/**************************************************************************
	 * 	Action Listener
	 * 	@param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (m_setting)
			return;
		String cmd = e.getActionCommand();
		log.config(cmd);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		//
//		if (e.getSource() == comboZoom)
//			cmd_zoom();
//		else
		if (e.getSource() == comboReport)
			cmd_report();
		else if (e.getSource() == comboDrill)
			cmd_drill();
		else if (cmd.equals("First"))
			setPage(1);
		else if (cmd.equals("PreviousPage") || cmd.equals("Previous"))
			setPage(m_pageNo-1);
		else if (cmd.equals("NextPage") || cmd.equals("Next"))
			setPage(m_pageNo+1);
		else if (cmd.equals("Last"))
			setPage(m_pageMax);
		else if (cmd.equals("Find"))
			cmd_find();
		else if (cmd.equals("Export"))
			cmd_export();
		else if (cmd.equals("Print"))
			cmd_print();
		else if (cmd.equals("SendMail"))
			cmd_sendMail();
		else if (cmd.equals("Archive"))
			cmd_archive();
		else if (cmd.equals("PrintCustomize"))
			cmd_customize();
		else if (cmd.equals("PageSetup"))
			cmd_pageSetup();
		else if (cmd.equals("Translate"))
			cmd_translate();
		else if (cmd.equals("End"))
			dispose();
		//
		else if (e.getSource() == m_ddM)
			cmd_window(m_ddQ);
		else if (e.getSource() == m_daM)
			cmd_window(m_daQ);
		//
		else if (!AEnv.actionPerformed(e.getActionCommand(), m_WindowNo, this))
			log.log(Level.SEVERE, "unknown action=" + e.getActionCommand());
		//
		this.setCursor(Cursor.getDefaultCursor());
	}	//	actionPerformed

	/**
	 * 	Change Listener (spinner, viewpoint)
	 * 	@param e event
	 */
	@Override
	public void stateChanged (ChangeEvent e)
	{
		if (m_setting)
			return;
	//	log.config( "Viewer.stateChanged", e);
		m_setting = true;
		int newPage = 0;
		if (e.getSource() == spinner)
		{
			newPage = ((Integer)spinnerModel.getValue()).intValue();
		}
		// pb with the viewport change listener disabled the following is
		// superfluous and should be removed
		else	//	Viewpoint
		{
			Point p = centerScrollPane.getViewport().getViewPosition();
			newPage = Math.round(m_viewPanel.getPageNoAt(p));
		}
		setPage(newPage);
		m_setting = false;
	}	//	stateChanged


	/**
	 * 	Set Page No
	 * 	@param page page no
	 */
	private void setPage (int page)
	{
		m_setting = true;
		m_pageNo = page;
		if (m_pageNo < 1)
			m_pageNo = 1;
		if (page > m_pageMax)
			m_pageNo = m_pageMax;
		bPrevious.setEnabled (m_pageNo != 1);
		bNext.setEnabled (m_pageNo != m_pageMax);
		//
		Rectangle pageRectangle = m_viewPanel.getRectangleOfPage(m_pageNo);
		pageRectangle.x -= View.MARGIN;
		pageRectangle.y -= View.MARGIN;
		centerScrollPane.getViewport().setViewPosition(pageRectangle.getLocation());
	//	System.out.println("scrollTo " + pageRectangle);

		//	Set Page
		spinnerModel.setValue(new Integer(m_pageNo));
		
		final String pageInfo = m_viewPanel.updatePageInfo(m_pageNo);
		
	
		
		StringBuffer sb = new StringBuffer (Msg.getMsg(m_ctx, "Page"))
			.append(" ").append(pageInfo)
			.append(" ").append(Msg.getMsg(m_ctx, "slash")).append(" ")
			.append(m_viewPanel.getPageInfoMax());
		statusBar.setStatusDB(sb.toString());
		m_setting = false;
	}	//	setPage

	
	/**************************************************************************
	 * 	(Re)Set Drill Accross Cursor
	 */
	private void cmd_drill()
	{
		m_drillDown = comboDrill.getSelectedIndex() < 1;	//	-1 or 0
		if (m_drillDown)
			setCursor(Cursor.getDefaultCursor());
		else
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}	//	cmd_drill

	/**
	 * 	Mouse clicked
	 * 	@param e event
	 * 	@param rightClick true if right click
	 */
	private void mouse_clicked (MouseEvent e, boolean rightClick)
	{
		Point point = e.getPoint();
		log.info("Right=" + rightClick + " - " + point.toString());
		if (rightClick)
		{
			m_ddQ = m_viewPanel.getDrillDown(point);
			m_daQ = m_viewPanel.getDrillAcross(point);
			m_ddM = null;
			m_daM = null;
			if (m_ddQ == null && m_daQ == null)
				return;
			//	Create Menu
			JPopupMenu pop = new JPopupMenu();
			Icon wi = Images.getImageIcon2("mWindow");
			if (m_ddQ != null)
			{
				m_ddM = new CMenuItem(m_ddQ.getDisplayName(Env.getCtx()), wi);
				m_ddM.setToolTipText(m_ddQ.toString());
				m_ddM.addActionListener(this);
				pop.add(m_ddM);
			}
			if (m_daQ != null)
			{
				m_daM = new CMenuItem(m_daQ.getDisplayName(Env.getCtx()), wi);
				m_daM.setToolTipText(m_daQ.toString());
				m_daM.addActionListener(this);
				pop.add(m_daM);
			}
			Point pp = e.getPoint();
			pop.show((Component)e.getSource(), pp.x, pp.y);
			return;
		}
		
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		if (m_drillDown)
		{
			MQuery query = m_viewPanel.getDrillDown(point);
			if (query != null)
			{
				log.info("Drill Down: " + query.getWhereClause(true));
				executeDrill(query);
			}
		}
		else if (comboDrill.getSelectedItem() != null && comboDrill.getSelectedIndex() > 0)
		{
			MQuery query  = m_viewPanel.getDrillAcross(point);
			if (query != null)
			{
				NamePair pp = (NamePair)comboDrill.getSelectedItem();
				query.setTableName(pp.getID());
				log.info("Drill Accross: " + query.getWhereClause(true));
				executeDrill(query);
			}
		}
		cmd_drill();	//	setCursor
	}	//	mouse_clicked

	/**
	 * 	Execute Drill to Query
	 * 	@param query query
	 */
	private void executeDrill (MQuery query)
	{
		int AD_Table_ID = AReport.getAD_Table_ID(query.getTableName());
		if (!Env.getUserRolePermissions().isCanReport(AD_Table_ID))
		{
			ADialog.error(m_WindowNo, this, "AccessCannotReport", query.getTableName());
			return;
		}
		if (AD_Table_ID != 0)
			new AReport (AD_Table_ID, null, query);
		else
			log.warning("No Table found for " + query.getWhereClause(true));
	}	//	executeDrill

	/**
	 * 	Open Window
	 *	@param query query
	 */
	private void cmd_window (MQuery query)
	{
		if (query == null)
			return;
		AEnv.zoom(query);
	}	//	cmd_window
	
	/**************************************************************************
	 * 	Print Report
	 */
	private void cmd_print()
	{
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		m_reportEngine.getPrintInfo().setWithDialog(true);
		m_reportEngine.print();
		cmd_drill();	//	setCursor
	}	//	cmd_print

	/**
	 * 	Send Mail
	 */
	private void cmd_sendMail()
	{
		String to = "";
		MUser from = MUser.get(Env.getCtx(), Env.getAD_User_ID(Env.getCtx()));
		String subject = m_reportEngine.getName();
		String message = "";
		File attachment = null;
		
		try
		{
			attachment = File.createTempFile("mail", ".pdf");
			m_reportEngine.getPDF(attachment);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}

		EMailDialog emd = new EMailDialog (this,
			Msg.getMsg(Env.getCtx(), "SendMail"),
			from, to, subject, message, attachment);
		
	}	//	cmd_sendMail

	/**
	 * 	Archive Report directly
	 */
	private void cmd_archive ()
	{
		try
		{
			final I_AD_Archive archive = Services.get(IArchiveBL.class).archive(
					m_reportEngine.getLayout(),
					m_reportEngine.getPrintInfo(),
					true, // force
					ITrx.TRXNAME_None);
			if (archive != null)
			{
				Services.get(IClientUI.class).info(m_WindowNo, "Archived");
			}
			else
			{
				Services.get(IClientUI.class).error(m_WindowNo, "ArchiveError");
			}

		}
		catch (Exception e)
		{
			log.log(Level.WARNING, e.getLocalizedMessage(), e);
			Services.get(IClientUI.class).error(m_WindowNo, "ArchiveError", e.getLocalizedMessage());
		}
	}	//	cmd_archive

	/**
	 * 	Print Setup Dialog
	 */
	private void cmd_pageSetup()
	{
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		m_reportEngine.pageSetupDialog();
		revalidateViewer();
		cmd_drill();	//	setCursor
	}	//	cmd_pageSetup

	/**
	 * 	Export
	 */
	private void cmd_export()
	{
		log.config("");
		if (!m_isCanExport)
		{
			ADialog.error(m_WindowNo, this, "AccessCannotExport", getTitle());
			return;
		}
		
		//
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogTitle(Msg.getMsg(m_ctx, "Export") + ": " + getTitle());
		//
		chooser.addChoosableFileFilter(new ExtensionFileFilter("ps", Msg.getMsg(m_ctx, "FilePS")));
		chooser.addChoosableFileFilter(new ExtensionFileFilter("xml", Msg.getMsg(m_ctx, "FileXML")));
		chooser.addChoosableFileFilter(new ExtensionFileFilter("pdf", Msg.getMsg(m_ctx, "FilePDF")));
		chooser.addChoosableFileFilter(new ExtensionFileFilter("html", Msg.getMsg(m_ctx, "FileHTML")));
		chooser.addChoosableFileFilter(new ExtensionFileFilter("txt", Msg.getMsg(m_ctx, "FileTXT")));
		chooser.addChoosableFileFilter(new ExtensionFileFilter("ssv", Msg.getMsg(m_ctx, "FileSSV")));
		chooser.addChoosableFileFilter(new ExtensionFileFilter("csv", Msg.getMsg(m_ctx, "FileCSV")));
		chooser.addChoosableFileFilter(new ExtensionFileFilter("xls", Msg.getMsg(m_ctx, "FileXLS")));
		//
		if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
			return;

		//	Create File
		File outFile = ExtensionFileFilter.getFile(chooser.getSelectedFile(), chooser.getFileFilter());
		try
		{
			outFile.createNewFile();
		}
		catch (IOException e)
		{
			log.log(Level.SEVERE, "", e);
			ADialog.error(m_WindowNo, this, "FileCannotCreate", e.getLocalizedMessage());
			return;
		}

		String ext = outFile.getPath();
		//	no extension
		if (ext.lastIndexOf('.') == -1)
		{
			ADialog.error(m_WindowNo, this, "FileInvalidExtension");
			return;
		}
		ext = ext.substring(ext.lastIndexOf('.')+1).toLowerCase();
		log.config( "File=" + outFile.getPath() + "; Type=" + ext);

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try {
			if (ext.equals("pdf"))
				m_reportEngine.createPDF(outFile);
			else if (ext.equals("ps"))
				m_reportEngine.createPS(outFile);
			else if (ext.equals("xml"))
				m_reportEngine.createXML(outFile);
			else if (ext.equals("csv"))
				m_reportEngine.createCSV(outFile, ',', m_reportEngine.getPrintFormat().getLanguage());
			else if (ext.equals("ssv"))
				m_reportEngine.createCSV(outFile, ';', m_reportEngine.getPrintFormat().getLanguage());
			else if (ext.equals("txt"))
				m_reportEngine.createCSV(outFile, '\t', m_reportEngine.getPrintFormat().getLanguage());
			else if (ext.equals("html") || ext.equals("htm"))
				m_reportEngine.createHTML(outFile, false, m_reportEngine.getPrintFormat().getLanguage());
			else if (ext.equals("xls"))
				m_reportEngine.createXLS(outFile, m_reportEngine.getPrintFormat().getLanguage());
			else
				ADialog.error(m_WindowNo, this, "FileInvalidExtension");
		}
		catch (Exception e) {
			ADialog.error(m_WindowNo, this, "Error", e.getLocalizedMessage());
			if (CLogMgt.isLevelFinest())
				e.printStackTrace();
		}
		cmd_drill();	//	setCursor
	}	//	cmd_export

	
	/**
	 * 	Report Combo - Start other Report or create new one
	 */
	private void cmd_report()
	{
		KeyNamePair pp = (KeyNamePair)comboReport.getSelectedItem();
		if (pp == null)
			return;
		//
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		MPrintFormat pf = null;
		int AD_PrintFormat_ID = pp.getKey();

		//	create new
		if (AD_PrintFormat_ID == -1)
		{
			int AD_ReportView_ID = m_reportEngine.getPrintFormat().getAD_ReportView_ID();
			if (AD_ReportView_ID != 0)
			{
				String name = m_reportEngine.getName();
				int index = name.lastIndexOf('_');
				if (index != -1)
					name = name.substring(0,index);
				pf = MPrintFormat.createFromReportView(m_ctx, AD_ReportView_ID, name);
			}
			else
			{
				int AD_Table_ID = m_reportEngine.getPrintFormat().getAD_Table_ID();
				pf = MPrintFormat.createFromTable(m_ctx, AD_Table_ID);
			}
			if (pf != null)
				fillComboReport(pf.get_ID());
			else
				return;
		}
		else
			pf = MPrintFormat.get (Env.getCtx(), AD_PrintFormat_ID, true);
		
		//	Get Language from previous - thanks Gunther Hoppe 
		if (m_reportEngine.getPrintFormat() != null)
		{
			pf.setLanguage(m_reportEngine.getPrintFormat().getLanguage());		//	needs to be re-set - otherwise viewer will be blank
			pf.setTranslationLanguage(m_reportEngine.getPrintFormat().getLanguage());
		}
		m_reportEngine.setPrintFormat(pf);
		revalidateViewer();

		cmd_drill();	//	setCursor
	}	//	cmd_report

	/**
	 * 	Query Report
	 */
	private void cmd_find()
	{
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		final int AD_Table_ID = m_reportEngine.getPrintFormat().getAD_Table_ID();
		
		//	Get Find Tab Info
		String sql = "SELECT t.AD_Tab_ID "
			//	,w.Name, t.Name, w.IsDefault, t.SeqNo, ABS (tt.AD_Window_ID-t.AD_Window_ID)
			+ "FROM AD_Tab t"
			+ " INNER JOIN AD_Window w ON (t.AD_Window_ID=w.AD_Window_ID)"
			+ " INNER JOIN AD_Table tt ON (t.AD_Table_ID=tt.AD_Table_ID) "
			+ "WHERE tt.AD_Table_ID=? "
			+ "ORDER BY w.IsDefault DESC, t.SeqNo, ABS (tt.AD_Window_ID-t.AD_Window_ID)";
		final int AD_Tab_ID = DB.getSQLValue(ITrx.TRXNAME_None, sql, AD_Table_ID);

		// ASP
		final String ASPFilter = Services.get(IASPFiltersFactory.class)
				.getASPFiltersForClient(Env.getAD_Client_ID(Env.getCtx()))
				.getSQLWhereClause(I_AD_Tab.class);

		//
		sql = "SELECT Name, TableName"
				+ ", " + I_AD_Tab.COLUMNNAME_MaxQueryRecords
				+ " FROM AD_Tab_v WHERE AD_Tab_ID=? " + ASPFilter;
		if (!Env.isBaseLanguage(Env.getCtx(), "AD_Tab"))
		{
			sql = "SELECT Name, TableName"
					+ ", " + I_AD_Tab.COLUMNNAME_MaxQueryRecords
					+ " FROM AD_Tab_vt WHERE AD_Tab_ID=?"
					+ " AND AD_Language='" + Env.getAD_Language(Env.getCtx()) + "' " + ASPFilter;
		}

		String title = null; 
		String tableName = null;
		int maxQueryRecordsPerTab = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Tab_ID);
			rs = pstmt.executeQuery();
			//
			if (rs.next())
			{
				title = rs.getString(1);				
				tableName = rs.getString(2);
				maxQueryRecordsPerTab = rs.getInt(I_AD_Tab.COLUMNNAME_MaxQueryRecords);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		GridField[] findFields = null;
		if (tableName != null)
			findFields = GridField.createFields(m_ctx, m_WindowNo, 0, AD_Tab_ID);
		
		if (findFields == null)		//	No Tab for Table exists
			bFind.setEnabled(false);
		else
		{
			Find find = Find.builder()
					.setParentFrame(this)
					.setTargetWindowNo(m_WindowNo)
					.setTitle(title)
					.setAD_Tab_ID(AD_Tab_ID)
					.setMaxQueryRecordsPerTab(maxQueryRecordsPerTab)
					.setAD_Table_ID(AD_Table_ID)
					.setTableName(tableName)
					.setWhereExtended("")
					.setFindFields(findFields)
					.setMinRecords(1)
					.buildFindDialog();
			m_reportEngine.setQuery(find.getQuery());
			find.dispose();
			find = null;
			revalidateViewer();
		}
		cmd_drill();	//	setCursor
	}	//	cmd_find

	/**
	 * 	Call Customize
	 */
	private void cmd_customize()
	{
		AWindow win = new AWindow ();
		new AWindowListener (win, this);	//	forwards Window Events
		int AD_Window_ID = 240;		//	hardcoded
		int AD_PrintFormat_ID = m_reportEngine.getPrintFormat().get_ID();
		win.initWindow(AD_Window_ID, MQuery.getEqualQuery("AD_PrintFormat_ID", AD_PrintFormat_ID));
		AEnv.addToWindowManager(win);
		AEnv.showCenterScreen(win);
		//	see windowStateChanged for applying change
	}	//	cmd_customize

	/**
	 * 	Window State Listener for Customize Window
	 * 	@param e event
	 */
	@Override
	public void windowStateChanged (WindowEvent e)
	{
		//	The Customize Window was closed
		if (e.getID() == WindowEvent.WINDOW_CLOSED && m_reportEngine != null)
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			log.info("Re-read PrintFormat");
			int AD_PrintFormat_ID = m_reportEngine.getPrintFormat().get_ID();
			Language language = m_reportEngine.getPrintFormat().getLanguage();
			MPrintFormat pf = MPrintFormat.get (Env.getCtx(), AD_PrintFormat_ID, true);
			pf.setLanguage (language);		//	needs to be re-set - otherwise viewer will be blank
			pf.setTranslationLanguage (language);
			m_reportEngine.setPrintFormat(pf);
			revalidateViewer();
			cmd_drill();	//	setCursor
		}
	}	//	windowStateChanged

	/**
	 * 	Set Zoom Level
	 */
	private void cmd_zoom()
	{
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//		m_viewPanel.setZoomLevel(comboZoom.getSelectedIndex());
		revalidateViewer();
		cmd_drill();	//	setCursor
	}	//	cmd_zoom


	/**
	 * 	Show Translation Dialog.
	 *  Translate base table entry, will be copied to trl tables if not multi-lingual
	 */
	private void cmd_translate()
	{
		ArrayList<ValueNamePair> list = new ArrayList<ValueNamePair>();
		ValueNamePair pp = null;
		String sql = "SELECT Name, AD_Language FROM AD_Language WHERE IsSystemLanguage='Y' ORDER BY 1";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new ValueNamePair (rs.getString(2), rs.getString(1)));
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		if (list.size() == 0)
		{
			ADialog.warn(m_WindowNo, this, "NoTranslation");
			return;
		}

		//	Dialog
		String title = Msg.getMsg(Env.getCtx(), "PrintFormatTrl", true);
		String message = Msg.getMsg(Env.getCtx(), "PrintFormatTrl", false);
		int choice = JOptionPane.showOptionDialog
			(this, message, title,
			JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null,
			list.toArray(), null);
		if (choice == JOptionPane.CLOSED_OPTION)
			return;
		//
		pp = list.get(choice);
		String AD_Language = pp.getValue();
		int AD_PrintFormat_ID = m_reportEngine.getPrintFormat().get_ID();
		log.config(AD_Language + " - AD_PrintFormat_ID=" + AD_PrintFormat_ID);
		StringBuffer sb = new StringBuffer();
		//	English
		if (Language.isBaseLanguage (AD_Language))
		{
			sb.append("UPDATE AD_PrintFormatItem pfi "
				+ "SET Name = (SELECT e.Name FROM AD_Element e, AD_Column c"
				+ " WHERE e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=pfi.AD_Column_ID),"
				+ "PrintName = (SELECT e.PrintName FROM AD_Element e, AD_Column c"
				+ " WHERE e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=pfi.AD_Column_ID) "
				+ "WHERE AD_PrintFormat_ID=").append(AD_PrintFormat_ID).append(
				  " AND EXISTS (SELECT * FROM AD_Element e, AD_Column c"
				+ " WHERE e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=pfi.AD_Column_ID)");
		}
		else
		{
			AD_Language = "'" + AD_Language + "'";
			sb.append("UPDATE AD_PrintFormatItem pfi "
				+ "SET Name = (SELECT e.Name FROM AD_Element_Trl e, AD_Column c"
				+ " WHERE e.AD_Language=").append(AD_Language).append(
				  " AND e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=pfi.AD_Column_ID), "
				+ "PrintName = (SELECT e.PrintName FROM AD_Element_Trl e, AD_Column c"
				+ "	WHERE e.AD_Language=").append(AD_Language).append(
				  " AND e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=pfi.AD_Column_ID) "
				+ "WHERE AD_PrintFormat_ID=").append(AD_PrintFormat_ID).append(
				  " AND EXISTS (SELECT * FROM AD_Element_Trl e, AD_Column c"
				+ " WHERE e.AD_Language=").append(AD_Language).append(
				  " AND e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=pfi.AD_Column_ID)");
		}
		int count = DB.executeUpdate(sb.toString(), null);
		log.config("Count=" + count);
		//
		m_reportEngine.setPrintFormat(MPrintFormat.get (Env.getCtx(), AD_PrintFormat_ID, true));
		revalidateViewer();
	}	//	cmd_translate
}	//	Viewer

