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

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Properties;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JProgressBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.apps.graph.PAPanel;
import org.adempiere.images.Images;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.ui.notifications.SwingEventNotifierService;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.NullRunnable;
import org.compiere.Adempiere;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.search.InfoWindowMenuBuilder;
import org.compiere.apps.wf.WFPanel;
import org.compiere.grid.tree.VTreePanel;
import org.compiere.model.MSession;
import org.compiere.model.MTreeNode;
import org.compiere.swing.CButton;
import org.compiere.swing.CFrame;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTabbedPane;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Language;
import org.compiere.util.Splash;
import org.compiere.wf.IADWorkflowBL;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.swing.SwingClientUI;
import de.metas.adempiere.model.I_AD_Form;

/**
 * Application Menu Controller
 * 
 * @author Jorg Janke
 * @version $Id: AMenu.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 * 
 * @author Colin Rooney (croo) RFE#1670185 restrict access to info queries
 * @author victor.perez@e-evolution.com
 * @see FR [ 1966328 ] New Window Info to MRP and CRP into View http://sourceforge.net/tracker/index.php?func=detail&aid=1966328&group_id=176962&atid=879335
 * 
 */
public final class AMenu extends CFrame
		implements ActionListener, PropertyChangeListener
{
	private static final long serialVersionUID = 5255914306969824011L;

	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	
	/**
	 * Application Start and Menu
	 */
	public AMenu()
	{
		super();
		log.info("CodeBase=" + Adempiere.getCodeBase());
		Splash splash = Splash.getSplash();
		//
		m_WindowNo = Env.createWindowNo(this);
		// Login
		initSystem(splash);        // login
		splash.setText(msgBL.getMsg(m_ctx, "Loading"));
		splash.toFront();
		splash.paint(splash.getGraphics());

		//
		if (!Adempiere.startupEnvironment(true)) // Load Environment
		{
			System.exit(1);
		}
		MSession.get(m_ctx, true);		// Start Session

		// Preparation
		treePanel = new VTreePanel(m_WindowNo, true, false);	// !editable & hasBar
		treePanel.setName("main.treePanel"); // me16
		try
		{
			jbInit();
			createMenu();
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "AMenu", ex);
		}

		m_AD_User_ID = Env.getAD_User_ID(m_ctx);
		m_AD_Role_ID = Env.getAD_Role_ID(m_ctx);

		// initialize & load tree
		final int AD_Tree_ID = retrieveMenuTreeId(m_ctx);
		treePanel.initTree(AD_Tree_ID);

		// Translate
		Env.setContext(m_ctx, m_WindowNo, "WindowName", msgBL.getMsg(m_ctx, "Menu"));
		setTitle(Env.getHeader(m_ctx, m_WindowNo));

		progressBar.setString(msgBL.getMsg(m_ctx, "SelectProgram"));

		//
		// Setup Update Info
		infoUpdater = new InfoUpdater();
		infoUpdaterThread = new Thread(infoUpdater, getClass().getName() + "-InfoUpdater");
		infoUpdaterThread.setDaemon(true);
		infoUpdaterThread.start();
		
		//
		// Start notifications UI
		SwingEventNotifierService.getInstance().start();
			
		//
		// Start Main Window
		final IUserRolePermissions role = Env.getUserRolePermissions();
		final int roleFormId = role.getStartup_AD_Form_ID();
		if (roleFormId > 0)
		{
			this.setVisible(false);
			mainWindow = startForm(roleFormId);
			if (mainWindow == null)
			{
				logout();
				return;
			}
		}
		else
		{
			packAndShow();
			mainWindow = this;
		}

		// Setting close operation/listener - teo_sarca [ 1684168 ]
		mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainWindow.addWindowListener(mainWindowListener);

		//
		// Hide slash
		splash.dispose();
		splash = null;
	}	// AMenu
	
	/**
	 * Sets window position, window dimension and shows it.
	 */
	private final void packAndShow()
	{
		boolean openMaximized = Ini.isPropertyBool(Ini.P_OPEN_WINDOW_MAXIMIZED);
		
		//
		// Set window position
		{
			Point windowLocation = Ini.getWindowLocation(0);
			if (windowLocation == null)
				windowLocation = new Point(0, 0);
			// Make sure the position is not out of the screen
			if (windowLocation.x < 0 || windowLocation.y < 0)
			{
				windowLocation = new Point(windowLocation.x < 0 ? 0 : windowLocation.x, windowLocation.y < 0 ? 0 : windowLocation.y);
			}
			this.setLocation(windowLocation);
		}

		//
		// Set window dimension
		{
			Dimension windowSize = Ini.getWindowDimension(0);
			AEnv.setMaximumSizeAsScreenSize(this);
			if (windowSize == null || windowSize.width <= 0 || windowSize.height <= 0)
			{
				this.setPreferredSize(this.getMaximumSize());
				openMaximized = true;
			}
			else
			{
				this.setPreferredSize(windowSize);
			}
			this.pack();
		}

		//
		// Show the window
		this.setVisible(true);
		if (openMaximized)
			this.setExtendedState(Frame.MAXIMIZED_BOTH);
		else
			this.setState(Frame.NORMAL);
	}

	private final int m_WindowNo;
	private final Properties m_ctx = Env.getCtx();
	private boolean m_startingItem = false;
	/** The User */
	private int m_AD_User_ID;
	/** The Role */
	private int m_AD_Role_ID;

	// Links
	private int m_request_Menu_ID = 0;
	private int m_note_Menu_ID = 0;
	private String m_requestSQL = null;
	// private DecimalFormat m_memoryFormat = DisplayType.getNumberFormat(DisplayType.Integer);
	/** Logger */
	private static CLogger log = CLogger.getCLogger(AMenu.class);

	/** The Info Update instance **/
	private InfoUpdater infoUpdater = null;
	/** The Info Update thread **/
	private Thread infoUpdaterThread = null;

	private WindowManager windowManager = new WindowManager();

	private JFrame mainWindow;
	private final WindowListener mainWindowListener = new WindowAdapter()
	{
		@Override
		public void windowClosing(WindowEvent e)
		{
			if (mainWindow == null || e.getWindow() != mainWindow)
			{
				return;
			}
			if (!ADialog.ask(0, null, "ExitApplication?"))
			{
				return;
			}
			dispose();
		}

		@Override
		public void windowClosed(WindowEvent e)
		{
			// NOTE: here we are handling the case when user closes the window from a programatically button
			if (mainWindow == null || e.getWindow() != mainWindow)
			{
				return;
			}
			dispose();
		}
	};

	/**************************************************************************
	 * Init System. -- do not get Msg as environment not initialized yet --
	 * 
	 * <pre>
	 * - Login - in not successful, exit
	 * </pre>
	 * 
	 * @param splash splash window
	 */
	private void initSystem(Splash splash)
	{
		// Default Image
		this.setIconImage(Adempiere.getProductIconSmall());

		// Focus Traversal
		KeyboardFocusManager.setCurrentKeyboardFocusManager(AKeyboardFocusManager.get());
		// FocusManager.getCurrentManager().setDefaultFocusTraversalPolicy(AFocusTraversalPolicy.get());
		// this.setFocusTraversalPolicy(AFocusTraversalPolicy.get());

		// Register Swing ClientUI service
		Services.registerService(IClientUI.class, new SwingClientUI());

		/**
		 * Show Login Screen - if not successful - exit
		 */
		log.finer("Login");

		final ALogin login = new ALogin(splash, m_ctx);
		if (!login.initLogin())		// no automatic login
		{
			// Center the window
			try
			{
				AEnv.showCenterScreen(login);	// HTML load errors
			}
			catch (Exception ex)
			{
				log.severe(ex.toString());
			}
			if (!login.isConnected() || !login.isOKpressed())
				AEnv.exit(1);
		}

		// Check Build
		if (!DB.isBuildOK(m_ctx))
			AEnv.exit(1);

		// Check DB (AppsServer Version checked in Login)
		DB.isDatabaseOK(m_ctx);
	}	// initSystem

	// UI
	private JMenuBar menuBar = new JMenuBar();
	protected JProgressBar progressBar = new JProgressBar(0, 100);
	private CButton bNotes = new CButton();
	private CButton bRequests = new CButton();
	// Tabs
	private PAPanel paPanel = null;
	private VTreePanel treePanel = null;
	private WindowMenu m_WindowMenu;

	/**
	 * Static Init.
	 * 
	 * <pre>
	 * -mainPanel
	 * 		- centerPane
	 * 		- treePanel
	 * 		- southPanel
	 * 		- infoPanel
	 * 		- bNotes
	 * 		- bTask
	 * 		- progressBar
	 * </pre>
	 * 
	 * @throws Exception
	 */
	void jbInit() throws Exception
	{
		this.setName("Menu");
		this.setLocale(Language.getLoginLanguage().getLocale());
		this.setJMenuBar(menuBar);
		
		//
		final CPanel mainPanel = new CPanel();
		final BorderLayout mainLayout = new BorderLayout();
		mainPanel.setLayout(mainLayout);
		mainLayout.setHgap(0);
		mainLayout.setVgap(2);
		//
		getContentPane().add(mainPanel);
		mainPanel.add(Box.createHorizontalStrut(3), BorderLayout.EAST);
		mainPanel.add(Box.createHorizontalStrut(3), BorderLayout.WEST);
		
		//
		// Center panel
		{
			final CTabbedPane centerPane = new CTabbedPane();
			centerPane.setFont(centerPane.getFont().deriveFont(centerPane.getFont().getSize2D() + 1));
			mainPanel.add(centerPane, BorderLayout.CENTER);

			// Tab: Performance Analysis Panel / Dashboard
			{
				paPanel = PAPanel.get();
				if (paPanel != null)
				{
					centerPane.addTab(msgBL.getMsg(m_ctx, "PAPanel"), Images.getImageIcon2("InfoAccount16"), paPanel);
				}
			}
			
			//
			// Tab: Menu tree
			{
				treePanel.setBorder(BorderFactory.createEmptyBorder()); // (2, 3, 2, 3));
				centerPane.addTab(msgBL.getMsg(m_ctx, "Menu"), Images.getImageIcon2("Home16"), treePanel);
				treePanel.addPropertyChangeListener(VTreePanel.NODE_SELECTION, this);
				
				//
				final int dividerLocation = Ini.getDividerLocation();
				if (dividerLocation > 0)
					treePanel.setDividerLocation(dividerLocation);
				else
					treePanel.setDividerLocation(400);
			}
		}
		
		//
		// South panel
		{
			final BorderLayout southLayout = new BorderLayout();
			southLayout.setHgap(0);
			southLayout.setVgap(1);
			final CPanel southPanel = new CPanel();
			southPanel.setLayout(southLayout);
			mainPanel.add(southPanel, BorderLayout.SOUTH);
			
			//
			// Bottom buttons panel
			{
				// bNotes.setRequestFocusEnabled(false);
				bNotes.setToolTipText("");
				bNotes.setActionCommand("Notes");
				bNotes.addActionListener(this);
				bNotes.setIcon(Images.getImageIcon2("GetMail24"));
				bNotes.setMargin(new Insets(0, 0, 0, 0));
				//
				// bRequests.setRequestFocusEnabled(false);
				bRequests.setActionCommand("Requests");
				bRequests.addActionListener(this);
				bRequests.setIcon(Images.getImageIcon2("Request24"));
				bRequests.setMargin(new Insets(0, 0, 0, 0));
				//
				final GridLayout infoLayout = new GridLayout();
				infoLayout.setColumns(2);
				infoLayout.setHgap(4);
				infoLayout.setVgap(0);
				final CPanel infoPanel = new CPanel();
				infoPanel.setLayout(infoLayout);
				infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4)); // left=right=same space as between components (task 09387)
				infoPanel.add(bNotes, null);
				infoPanel.add(bRequests, null);				
				southPanel.add(infoPanel, BorderLayout.NORTH);
			}
			
			//
			// Progress bar
			{
				progressBar.setStringPainted(true);
				progressBar.setOpaque(false);
				southPanel.add(progressBar, BorderLayout.SOUTH);
			}
		}
	} // jbInit

	/**
	 * Create Menu
	 */
	private void createMenu()
	{
		// File
		JMenu mFile = AEnv.getMenu("File");
		menuBar.add(mFile);
		AEnv.addMenuItem("Logout", null, KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.SHIFT_MASK + Event.ALT_MASK), mFile, this);
		AEnv.addMenuItem("Exit", null, KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.SHIFT_MASK + Event.ALT_MASK), mFile, this);

		// View
		JMenu mView = AEnv.getMenu("View");
		menuBar.add(mView);
		
		InfoWindowMenuBuilder.newBuilder()
				.setCtx(m_ctx)
				.setParentWindowNo(m_WindowNo)
				.setMenu(mView)
				.build();

		// Tools
		JMenu mTools = AEnv.getMenu("Tools");
		menuBar.add(mTools);
		
		// metas-tsa: Drop unneeded menu items (09271)
		//@formatter:off
//		AEnv.addMenuItem("Calculator", null, null, mTools, this);
//		AEnv.addMenuItem("Calendar", null, null, mTools, this);
//		AEnv.addMenuItem("Editor", null, null, mTools, this);
//		MUser user = MUser.get(Env.getCtx());
//		if (user.isAdministrator())
//			AEnv.addMenuItem("Script", null, null, mTools, this);
//		if (AEnv.isWorkflowProcess())
//			AEnv.addMenuItem("WorkFlow", null, null, mTools, this);
		//@formatter:on
		
		if (Env.getUserRolePermissions().isShowPreference())
		{
			if (mTools.getComponentCount() > 0)
				mTools.addSeparator();
			AEnv.addMenuItem("Preference", null, null, mTools, this);
		}

		// Window Menu
		m_WindowMenu = new WindowMenu(windowManager, this);
		menuBar.add(m_WindowMenu);
		if (m_WindowMenu.isDisplayShowAllAction())
		{
			final KeyStroke ks = WindowMenu.ShowAllWindows_KeyStroke;
			this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ks, WindowMenu.ShowAllWindows_ActionName);
			AppsAction action = new AppsAction(WindowMenu.ShowAllWindows_ActionName, ks, false);
			this.getRootPane().getActionMap().put(WindowMenu.ShowAllWindows_ActionName, action);
			action.setDelegate(this);
		}

		// Help
		JMenu mHelp = AEnv.getMenu("Help");
		menuBar.add(mHelp);
		AEnv.addMenuItem("Online", null, null, mHelp, this);
		AEnv.addMenuItem("EMailSupport", null, null, mHelp, this);
		AEnv.addMenuItem("About", null, null, mHelp, this);
	} // createMenu

	/**
	 * Dispose - end system
	 */
	@Override
	public void dispose()
	{
		preDispose();
		//
		super.dispose();
		AEnv.exit(0);
	}	// dispose

	private void preDispose()
	{
		// clean up - save window state only if current main window is this window
		if (mainWindow == this)
		{
			Ini.setWindowDimension(0, getSize());
			Ini.setDividerLocation(treePanel.getDividerLocation());
			Ini.setWindowLocation(0, getLocation());
			Ini.saveProperties();
		}
		//
		infoUpdater.stop = true;
		try
		{
			synchronized(infoUpdater)
			{
				infoUpdater.notify();
			}
		}
		finally
		{
			infoUpdaterThread = null;
			infoUpdater = null;
		}
		
		// Stop the notifications UI 
		SwingEventNotifierService.getInstance().stop();

		mainWindow = null;
	}

	public void logout()
	{
		windowManager.close();
		preDispose();
		super.dispose();
		AEnv.logout();
	}

	/**
	 * Window Events - requestFocus
	 * 
	 * @param e event
	 */
	@Override
	protected void processWindowEvent(WindowEvent e)
	{
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_OPENED)
		{
			treePanel.getSearchField().requestFocusInWindow();
			// this.toFront();
		}
	}   // processWindowEvent

	/**
	 * Set Busy
	 * 
	 * @param value true if busy
	 */
	protected void setBusy(boolean value)
	{
		m_startingItem = value;
		if (value)
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		else
			setCursor(Cursor.getDefaultCursor());
		// setEnabled (!value); // causes flicker
	}	// setBusy

	/**
	 * Selection in tree - launch Application
	 * 
	 * @param e PropertyChangeEvent
	 */
	@Override
	public void propertyChange(PropertyChangeEvent e)
	{

		MTreeNode nd = (MTreeNode)e.getNewValue();
		log.info(nd.getNode_ID() + " - " + nd.toString());

		// ignore summary items & when loading
		if (m_startingItem || nd.isSummary())
			return;

		String sta = nd.toString();
		progressBar.setString(sta);
		int cmd = nd.getNode_ID();

		(new AMenuStartItem(cmd, true, sta, this)).start();		// async load
		// hengsin, updateInfo is call again in AMenuStartItem
		// updateInfo();
	}	// propertyChange

	/**************************************************************************
	 * ActionListener
	 * 
	 * @param e ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// Buttons
		if (e.getSource() == bNotes)
			gotoNotes();
		else if (e.getSource() == bRequests)
			gotoRequests();
		else if (WindowMenu.ShowAllWindows_ActionName.equals(e.getActionCommand()))
			m_WindowMenu.expose();
		else if (!AEnv.actionPerformed(e.getActionCommand(), m_WindowNo, this))
			log.log(Level.SEVERE, "unknown action=" + e.getActionCommand());
		// updateInfo();
	}	// actionPerformed

	/**
	 * Get number of open Notes
	 * 
	 * @return number of notes
	 */
	private int getNotes()
	{
		String sql = "SELECT COUNT(1) FROM AD_Note "
				+ "WHERE AD_Client_ID=? AND AD_User_ID IN (0,?)"
				+ " AND Processed='N'";
		int retValue = DB.getSQLValue(null, sql, Env.getAD_Client_ID(m_ctx), m_AD_User_ID);
		return retValue;
	}	// getNotes

	/**
	 * Open Note Window
	 */
	private void gotoNotes()
	{
		// AD_Table_ID for AD_Note = 389 HARDCODED
		if (m_note_Menu_ID == 0)
			m_note_Menu_ID = DB.getSQLValue(null, "SELECT AD_Menu_ID "
					+ "FROM AD_Menu m"
					+ " INNER JOIN AD_TABLE t ON (t.AD_Window_ID=m.AD_Window_ID) "
					+ "WHERE t.AD_Table_ID=?", 389);
		if (m_note_Menu_ID == 0)
			m_note_Menu_ID = 233;	// fallback HARDCODED
		(new AMenuStartItem(m_note_Menu_ID, true, msgBL.translate(m_ctx, "AD_Note_ID"), this)).start();		// async load
	}   // gotoMessage

	/**
	 * Ger Number of open Requests
	 * 
	 * @return number of requests
	 */
	private int getRequests()
	{
		if (m_requestSQL == null)
			m_requestSQL = Env.getUserRolePermissions().addAccessSQL("SELECT COUNT(1) FROM R_Request "
					+ "WHERE (SalesRep_ID=? OR AD_Role_ID=?) AND Processed='N'"
					+ " AND (DateNextAction IS NULL OR TRUNC(DateNextAction) <= TRUNC(now()))"
					+ " AND (R_Status_ID IS NULL OR R_Status_ID IN (SELECT R_Status_ID FROM R_Status WHERE IsClosed='N'))",
					"R_Request", false, true);	// not qualified - RW
		int retValue = DB.getSQLValue(null, m_requestSQL, m_AD_User_ID, m_AD_Role_ID);
		return retValue;
	}	// getRequests

	/**
	 * Open Request Window
	 */
	private void gotoRequests()
	{
		// AD_Table_ID for R_Request = 417 HARDCODED
		// if (m_request_Menu_ID == 0) // Goes to Request (all)
		// m_request_Menu_ID = DB.getSQLValue (null, "SELECT AD_Menu_ID "
		// + "FROM AD_Menu m"
		// + " INNER JOIN AD_TABLE t ON (t.AD_Window_ID=m.AD_Window_ID) "
		// + "WHERE t.AD_Table_ID=?", 417);
		if (m_request_Menu_ID == 0)
			m_request_Menu_ID = 237;	// My Requests
		(new AMenuStartItem(m_request_Menu_ID, true, msgBL.translate(m_ctx, "R_Request_ID"), this)).start();		// async load
	}   // gotoRequests

	/**
	 * Show Memory Info - run GC if required - Update Requests/Memos/Activities
	 */
	public void updateInfo()
	{
		if (DB.isConnected())
		{
			// Requests
			final int requests = getRequests();
			bRequests.setText(msgBL.translate(m_ctx, "R_Request_ID") + ": " + requests);
			// Memo
			final int notes = getNotes();
			bNotes.setText(msgBL.translate(m_ctx, "AD_Note_ID") + ": " + notes);
			/*
			 * log.config(msg + ", Processors=" + Runtime.getRuntime().availableProcessors() + ", Requests=" + requests + ", Notes=" + notes + ", Activities=" + activities + "," +
			 * CConnection.get().getStatus() );
			 */
			// MSystem.get(m_ctx).info();
		}
	}	// updateInfo

	

	/*************************************************************************
	 * Start Workflow Activity
	 * 
	 * @param AD_Workflow_ID id
	 */
	protected void startWorkFlow(final int AD_Workflow_ID)
	{
		final WFPanel wfPanel = new WFPanel(this);
		// load panel
		wfPanel.load(AD_Workflow_ID, false);
		// set this on true in order use the simplified form
		wfPanel.setSimpleWorkflowWindow(true);
		
		// create the frame
		final FormFrame ff = new FormFrame();
		ff.setTitle(Services.get(IMsgBL.class).getMsg (m_ctx, "WorkflowPanel") + " - " +Services.get(IADWorkflowBL.class).getWorkflowName(AD_Workflow_ID));
		ff.setIconImage(Images.getImage2("mWorkFlow"));
		
		wfPanel.init(m_WindowNo, ff);
		
		windowManager.add(ff);

		ff.showFormWindow();
	}	// startWorkFlow

	public WindowManager getWindowManager()
	{
		return windowManager;
	}

	class InfoUpdater implements Runnable
	{
		boolean stop = false;

		@Override
		public void run()
		{
			final int sleep = Services.get(ISysConfigBL.class).getIntValue("MENU_INFOUPDATER_SLEEP_MS", 60000, Env.getAD_Client_ID(Env.getCtx()));
			while (stop == false)
			{
				updateInfo();

				try
				{
					synchronized (this)
					{
						this.wait(sleep);
					}
				}
				catch (InterruptedException ire)
				{
				}
			}
		}
	}

	private static int retrieveMenuTreeId(final Properties ctx)
	{
		final int AD_Role_ID = Env.getAD_Role_ID(ctx);
		int AD_Tree_ID = DB.getSQLValue(ITrx.TRXNAME_None,
				"SELECT COALESCE(r.AD_Tree_Menu_ID, ci.AD_Tree_Menu_ID)"
						+ "FROM AD_ClientInfo ci"
						+ " INNER JOIN AD_Role r ON (ci.AD_Client_ID=r.AD_Client_ID) "
						+ "WHERE AD_Role_ID=?", AD_Role_ID);
		if (AD_Tree_ID <= 0)
			AD_Tree_ID = 10;	// Menu
		return AD_Tree_ID;
	}

	public FormFrame startForm(final int AD_Form_ID)
	{
		final Runnable updateProgressBarRunnable = NullRunnable.instance;
		return startForm(AD_Form_ID, updateProgressBarRunnable);
	}

	public FormFrame startForm(final int AD_Form_ID, final Runnable updateProgressBarRunnable)
	{
		// metas: tsa: begin:  US831: Open one window per session per user (2010101810000044)
		final Properties ctx = Env.getCtx();
		final I_AD_Form form = InterfaceWrapperHelper.create(ctx, AD_Form_ID, I_AD_Form.class, ITrx.TRXNAME_None);
		if (form == null)
		{
			ADialog.warn(0, null, "Error", msgBL.parseTranslation(ctx, "@NotFound@ @AD_Form_ID@"));
			return null;
		}

		final WindowManager windowManager = getWindowManager();

		// metas: tsa: end:  US831: Open one window per session per user (2010101810000044)
		if (Ini.isPropertyBool(Ini.P_SINGLE_INSTANCE_PER_WINDOW) || form.isOneInstanceOnly()) // metas: tsa: us831
		{
			final FormFrame ffExisting = windowManager.findForm(AD_Form_ID);
			if (ffExisting != null)
			{
				AEnv.showWindow(ffExisting); // metas: tsa: use this method because toFront() is not working when window is minimized
				// ff.toFront(); // metas: tsa: commented original code
				return ffExisting;
			}
		}

		final FormFrame ff = new FormFrame();
		SwingUtilities.invokeLater(updateProgressBarRunnable);			// 1
		final boolean ok = ff.openForm(form);  // metas: tsa: us831
		if (!ok)
		{
			ff.dispose();
			return null;
		}
		windowManager.add(ff);
		SwingUtilities.invokeLater(updateProgressBarRunnable);			// 2

		// Center the window
		SwingUtilities.invokeLater(updateProgressBarRunnable);			// 3
		ff.showFormWindow();

		return ff;
	}	// startForm

}	// AMenu
