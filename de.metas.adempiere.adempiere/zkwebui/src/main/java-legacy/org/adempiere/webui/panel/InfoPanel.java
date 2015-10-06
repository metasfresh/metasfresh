/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
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
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.panel;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.service.IADInfoWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.apps.BusyDialog;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.WListItemRenderer;
import org.adempiere.webui.component.WListbox;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.event.WTableModelEvent;
import org.adempiere.webui.event.WTableModelListener;
import org.adempiere.webui.part.ITabOnSelectHandler;
import org.adempiere.webui.session.SessionManager;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.MTable;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zk.au.out.AuEcho;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelExt;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.ZulEvents;

/**
 * Search Information and return selection - Base Class. Based on Info written by Jorg Janke
 * 
 * @author Sendy Yagambrum
 * 
 *         Zk Port
 * @author Elaine
 * @version Info.java Adempiere Swing UI 3.4.1
 */
public abstract class InfoPanel extends Window implements EventListener, WTableModelListener, ListModelExt, IInfoPanel
{

	/**
	 * generated serial version ID
	 */
	private static final long serialVersionUID = 325050327514511004L;
	private final static int PAGE_SIZE = 100;

	/**
	 * metas: c.ghita@metas.ro create info
	 * 
	 * @param WindowNo
	 * @param tableName
	 * @param keyColumn
	 * @param value
	 * @param multiSelection
	 * @param whereClause
	 * @return
	 */
	public static IInfoPanel create(int WindowNo,
			String tableName, String keyColumn, String value,
			boolean multiSelection, String whereClause)
	{
		return create(WindowNo, tableName, keyColumn, value, multiSelection, whereClause, null);
	}

	/**
	 * metas: c.ghita@metas.ro create info
	 * 
	 * @param WindowNo
	 * @param tableName
	 * @param attributes
	 * @return
	 */
	public static IInfoPanel create(int WindowNo, String tableName, Map<String, Object> attributes)
	{
		return create(WindowNo, tableName, tableName + "_ID", "", false, "", attributes);
	}

	/**
	 * metas: c.ghita@metas.ro create info
	 * 
	 * @param WindowNo
	 * @param tableName
	 * @param keyColumn
	 * @param value
	 * @param multiSelection
	 * @param whereClause
	 * @param attributes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static IInfoPanel create(int WindowNo,
			String tableName, String keyColumn, String value,
			boolean multiSelection, String whereClause, Map<String, Object> attributes)
	{
		IInfoPanel info = null;

		// metas: c.ghita@metas.ro : start
		Class<? extends IInfoPanel> infoPanelClass = s_infoPanelClasses == null ? null : s_infoPanelClasses.get(tableName);
		if (infoPanelClass != null)
		{
			try
			{
				info = infoPanelClass.newInstance();
				return info;
			}
			catch (Exception e)
			{
				throw new AdempiereException("Cannot instantiate " + infoPanelClass + " for table " + tableName, e);
			}
		}
		
		final I_AD_InfoWindow infoWindow = Services.get(IADInfoWindowDAO.class).retrieveInfoWindowByTableName(Env.getCtx(), tableName);
		if (info == null && infoWindow != null && infoWindow.isActive())
		{
			final String className = infoWindow.getClassname();
			try
			{
				final IInfoPanel infoPanel;
				if (Check.isEmpty(className, true))
				{
					infoPanel = new InfoSimplePanel();
				}
				else
				{
					infoPanel = InfoPanelClassLoader.get().newInstance(className, IInfoPanel.class);
				}
				
				if (infoPanel instanceof InfoSimplePanel)
				{
					final InfoSimplePanel infoSimple = (InfoSimplePanel)infoPanel;
					infoSimple.init(WindowNo, infoWindow, keyColumn, value, multiSelection, whereClause, true);

					if (attributes != null)
					{
						for (Entry<String, Object> e : attributes.entrySet())
						{
							infoSimple.setCtxAttribute(e.getKey(), e.getValue());
						}
					}
				}
				else
				{
					// TODO: handle the simple case
				}
				info = infoPanel;
			}
			catch (Exception e)
			{
				throw new AdempiereException(e);
			}
		}
		// metas: c.ghita@metas.ro : start
		else if (tableName.equals("C_BPartner"))
			info = new InfoBPartnerPanel(value, WindowNo, !Env.getContext(Env.getCtx(), "IsSOTrx").equals("N"),
					multiSelection, whereClause);
		else if (tableName.equals("M_Product"))
			info = new InfoProductPanel();
		else if (tableName.equals("C_Invoice"))
			info = new InfoInvoicePanel(WindowNo, value,
					multiSelection, whereClause);
		else if (tableName.equals("A_Asset"))
			info = new InfoAssetPanel(WindowNo, 0, value,
					multiSelection, whereClause);
		else if (tableName.equals("C_Order"))
			info = new InfoOrderPanel(WindowNo, value,
					multiSelection, whereClause);
		else if (tableName.equals("M_InOut"))
			info = new InfoInOutPanel(WindowNo, value,
					multiSelection, whereClause);
		else if (tableName.equals("C_Payment"))
			info = new InfoPaymentPanel(WindowNo, value, multiSelection, whereClause);
		else if (tableName.equals("C_CashLine"))
			info = new InfoCashLinePanel(WindowNo, value,
					multiSelection, whereClause);
		else if (tableName.equals("S_ResourceAssigment"))
			info = new InfoAssignmentPanel(WindowNo, value,
					multiSelection, whereClause);
		else
			info = new InfoGeneralPanel(value, WindowNo,
					tableName, keyColumn,
					multiSelection, whereClause);
		//
		return info;

	}

	/**
	 * Show BPartner Info (non modal)
	 * 
	 * @param WindowNo window no
	 */
	public static void showBPartner(int WindowNo)
	{
		IInfoPanel info = create(WindowNo, "C_BPartner", "C_BPartner_ID", "", false, "");
		AEnv.showWindow(info);
	} // showBPartner

	/**
	 * Show Asset Info (non modal)
	 * 
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 */
	public static void showAsset(int WindowNo)
	{
		IInfoPanel info = create(WindowNo, "A_Asset", "A_Asset_ID", "", false, "");
		AEnv.showWindow(info);
	} // showBPartner

	/**
	 * Show Product Info (non modal)
	 * 
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 */
	public static void showProduct(int WindowNo)
	{
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("M_Warehouse_ID", Env.getContextAsInt(Env.getCtx(), WindowNo, "M_Warehouse_ID"));
		attributes.put("M_PriceList_ID", Env.getContextAsInt(Env.getCtx(), WindowNo, "M_PriceList_ID"));

		IInfoPanel info = create(WindowNo, "M_Product", "M_Product_ID", "", false, "");
		AEnv.showWindow(info);
	} // showProduct

	/**
	 * Show Order Info (non modal)
	 * 
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 * @param value query value
	 */
	public static void showOrder(int WindowNo, String value)
	{
		IInfoPanel info = create(WindowNo, "C_Order", "C_Order_ID", value, false, "");
		AEnv.showWindow(info);
	} // showOrder

	/**
	 * Show Invoice Info (non modal)
	 * 
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 * @param value query value
	 */
	public static void showInvoice(int WindowNo, String value)
	{
		IInfoPanel info = create(WindowNo, "C_Invoice", "C_Invoice_ID", value, false, "");
		AEnv.showWindow(info);
	} // showInvoice

	/**
	 * Show Shipment Info (non modal)
	 * 
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 * @param value query value
	 */
	public static void showInOut(int WindowNo, String value)
	{
		IInfoPanel info = create(WindowNo, "M_InOut", "M_InOut_ID", value, false, "");
		AEnv.showWindow(info);
	} // showInOut

	/**
	 * Show Payment Info (non modal)
	 * 
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 * @param value query value
	 */
	public static void showPayment(int WindowNo, String value)
	{
		IInfoPanel info = create(WindowNo, "C_Payment", "C_Payment_ID", value, false, "");
		AEnv.showWindow(info);
	} // showPayment

	/**
	 * Show Cash Line Info (non modal)
	 * 
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 * @param value query value
	 */
	public static void showCashLine(int WindowNo, String value)
	{
		IInfoPanel info = create(WindowNo, "C_CashLine", "C_CashLine_ID", value, false, "");
		AEnv.showWindow(info);
	} // showCashLine

	/**
	 * Show Assignment Info (non modal)
	 * 
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 * @param value query value
	 */
	public static void showAssignment(int WindowNo, String value)
	{
		IInfoPanel info = new InfoAssignmentPanel(WindowNo, value,
				false, "", false);
		AEnv.showWindow(info);
	} // showAssignment

	/** Window Width */
	static final int INFO_WIDTH = 800;
	protected boolean m_lookup;

	/**************************************************
	 * Detail Constructor
	 * 
	 * @param WindowNo WindowNo
	 * @param tableName tableName
	 * @param keyColumn keyColumn
	 * @param whereClause whereClause
	 */
	protected InfoPanel(int WindowNo,
			String tableName, String keyColumn, boolean multipleSelection,
			String whereClause)
	{
		super();
		init(WindowNo, tableName, keyColumn, multipleSelection, whereClause, true);
	}

	public InfoPanel()
	{
		super();
	}

	/**
	 * init
	 * 
	 * @param WindowNo
	 * @param tableName
	 * @param keyColumn
	 * @param multipleSelection
	 * @param whereClause
	 * @param lookup
	 */
	public void init(int WindowNo,
			String tableName, String keyColumn, boolean multipleSelection,
			String whereClause, boolean lookup)
	{
		// WindowNo
		if (WindowNo <= 0)
		{
			p_WindowNo = SessionManager.getAppDesktop().registerWindow(this);
			localWindowNo = true;
		}
		else
		{
			p_WindowNo = WindowNo;
			localWindowNo = false;
		}
		
		p_tableName = tableName;
		p_keyColumn = keyColumn;
		p_multipleSelection = multipleSelection;
		m_lookup = lookup;

		if (whereClause == null || whereClause.indexOf('@') == -1)
			p_whereClause = whereClause;
		else
		{
			p_whereClause = Env.parseContext(Env.getCtx(), p_WindowNo, whereClause, false, false);
			if (p_whereClause.length() == 0)
				log.log(Level.SEVERE, "Cannot parse context= " + whereClause);
		}
		init();

		this.setAttribute(ITabOnSelectHandler.ATTRIBUTE_KEY, new ITabOnSelectHandler()
		{
			public void onSelect()
			{
				scrollToSelectedRow();
			}
		});
	}

	/**************************************************
	 * Detail Constructor
	 * 
	 * @param WindowNo WindowNo
	 * @param tableName tableName
	 * @param keyColumn keyColumn
	 * @param whereClause whereClause
	 */
	protected InfoPanel(int WindowNo,
			String tableName, String keyColumn, boolean multipleSelection,
			String whereClause, boolean lookup)
	{
		init(WindowNo, tableName, keyColumn, multipleSelection, whereClause, lookup);
	} // InfoPanel

	protected void init()
	{
		if (isLookup())
		{
			setAttribute(Window.MODE_KEY, Window.MODE_MODAL);
			setBorder("normal");
			setClosable(true);
			int height = SessionManager.getAppDesktop().getClientInfo().desktopHeight * 85 / 100;
			int width = SessionManager.getAppDesktop().getClientInfo().desktopWidth * 80 / 100;
			setWidth(width + "px");
			setHeight(height + "px");
			this.setContentStyle("overflow: auto");
		}
		else
		{
			setAttribute(Window.MODE_KEY, Window.MODE_EMBEDDED);
			setBorder("none");
			setWidth("100%");
			setHeight("100%");
			setStyle("position: absolute");
		}

		confirmPanel = new ConfirmPanel(true, true, false, true, true, true); // Elaine 2008/12/16
		confirmPanel.addActionListener(Events.ON_CLICK, this);
		confirmPanel.setStyle("border-top: 2px groove #444; padding-top: 4px");

		// Elaine 2008/12/16
		confirmPanel.getButton(ConfirmPanel.A_CUSTOMIZE).setVisible(hasCustomize());
		confirmPanel.getButton(ConfirmPanel.A_HISTORY).setVisible(hasHistory());
		confirmPanel.getButton(ConfirmPanel.A_ZOOM).setVisible(hasZoom());
		//
		if (!isLookup())
		{
			confirmPanel.getButton(ConfirmPanel.A_OK).setVisible(false);
		}

		this.setSizable(true);
		this.setMaximizable(true);

		this.addEventListener(Events.ON_OK, this);

		contentPanel.setOddRowSclass(null);
	} // init

	protected ConfirmPanel confirmPanel;
	/** Master (owning) Window */
	protected int p_WindowNo;
	/** true if WindowNo was created locally */
	private boolean localWindowNo = false;
	/** Table Name */
	protected String p_tableName;
	/** Key Column Name */
	protected String p_keyColumn;
	/** Enable more than one selection */
	protected boolean p_multipleSelection;
	/** Initial WHERE Clause */
	protected String p_whereClause = "";
	protected StatusBarPanel statusBar = new StatusBarPanel();
	/**                    */
	private List<Object> line;

	private boolean m_ok = false;
	/** Cancel pressed - need to differentiate between OK - Cancel - Exit */
	private boolean m_cancel = false;
	/** Result IDs */
	private ArrayList<Integer> m_results = new ArrayList<Integer>(3);

	private ListModelTable model;
	/** Layout of Grid */
	protected ColumnInfo[] p_layout;
	/** Main SQL Statement */
	protected String m_sqlMain;
	/** Count SQL Statement */
	protected String m_sqlCount;
	/** Order By Clause */
	protected String m_sqlOrder;
	protected String m_sqlUserOrder;
	/** ValueChange listeners */
	private ArrayList<ValueChangeListener> listeners = new ArrayList<ValueChangeListener>();
	/** Loading success indicator */
	protected boolean p_loadedOK = false;
	/** SO Zoom Window */
	private int m_SO_Window_ID = -1;
	/** PO Zoom Window */
	private int m_PO_Window_ID = -1;

	/** Logger */
	protected CLogger log = CLogger.getCLogger(getClass());

	protected WListbox contentPanel = new WListbox();
	protected Paging paging;
	protected int pageNo;
	private int m_count;
	private int cacheStart;
	private int cacheEnd;
	private boolean m_useDatabasePaging = false;
	private BusyDialog progressWindow;

	private static final String[] lISTENER_EVENTS = {};

	/**
	 * Loaded correctly
	 * 
	 * @return true if loaded OK
	 */
	public boolean loadedOK()
	{
		return p_loadedOK;
	} // loadedOK

	/**
	 * Set Status Line
	 * 
	 * @param text text
	 * @param error error
	 */
	public void setStatusLine(String text, boolean error)
	{
		statusBar.setStatusLine(text, error);
	} // setStatusLine

	/**
	 * Set Status DB
	 * 
	 * @param text text
	 */
	public void setStatusDB(String text)
	{
		statusBar.setStatusDB(text);
	} // setStatusDB

	protected void prepareTable(ColumnInfo[] layout,
			String from,
			String where,
			String orderBy)
	{
		String sql = contentPanel.prepareTable(layout, from,
				where, p_multipleSelection && m_lookup,
				getTableName(), false);
		p_layout = contentPanel.getLayout();
		m_sqlMain = sql;
		m_sqlCount = "SELECT COUNT(*) FROM " + from + " WHERE " + where;
		//
		m_sqlOrder = "";
		m_sqlUserOrder = "";
		if (orderBy != null && orderBy.length() > 0)
			m_sqlOrder = " ORDER BY " + orderBy;
	} // prepareTable

	/**************************************************************************
	 * Execute Query
	 */
	// metas: changed from protected to public
	public void executeQuery()
	{
		line = new ArrayList<Object>();
		cacheStart = -1;
		cacheEnd = -1;

		testCount();
		m_useDatabasePaging = (m_count > 1000);
		if (m_useDatabasePaging)
		{
			return;
		}
		else
		{
			readLine(0, -1);
		}
	}

	private void readData(ResultSet rs) throws SQLException
	{
		int colOffset = 1; // columns start with 1
		List<Object> data = new ArrayList<Object>();
		for (int col = 0; col < p_layout.length; col++)
		{
			Object value = null;
			Class<?> c = p_layout[col].getColClass();
			int colIndex = col + colOffset;
			if (c == IDColumn.class)
			{
				value = new IDColumn(rs.getInt(colIndex));

			}
			else if (c == Boolean.class)
				value = new Boolean("Y".equals(rs.getString(colIndex)));
			else if (c == Timestamp.class)
				value = rs.getTimestamp(colIndex);
			else if (c == BigDecimal.class)
				value = rs.getBigDecimal(colIndex);
			else if (c == Double.class)
				value = new Double(rs.getDouble(colIndex));
			else if (c == Integer.class)
				value = new Integer(rs.getInt(colIndex));
			else if (c == KeyNamePair.class)
			{
				String display = rs.getString(colIndex);
				int key = rs.getInt(colIndex + 1);
				value = new KeyNamePair(key, display);

				colOffset++;
			}
			else
			{
				value = rs.getString(colIndex);
			}
			data.add(value);
		}
		line.add(data);
	}

	protected void renderItems()
	{
		// metas: cg: task 02798: refresh also if there is no match
		if (m_count > PAGE_SIZE)
		{
			if (paging == null)
			{
				paging = new Paging();
				paging.setPageSize(PAGE_SIZE);
				paging.setTotalSize(m_count);
				paging.setDetailed(true);
				paging.addEventListener(ZulEvents.ON_PAGING, this);
				insertPagingComponent();
			}
			else
			{
				paging.setTotalSize(m_count);
				paging.setActivePage(0);
			}
			List<Object> subList = readLine(0, PAGE_SIZE);
			model = new ListModelTable(subList);
			model.setSorter(this);
			model.addTableModelListener(this);
			contentPanel.setData(model, null);

			pageNo = 0;
		}
		else
		{
			if (paging != null)
			{
				paging.setTotalSize(m_count);
				paging.setActivePage(0);
				pageNo = 0;
			}
			model = new ListModelTable(line);
			model.setSorter(this);
			model.addTableModelListener(this);
			contentPanel.setData(model, null);
		}
		int no = m_count;
		setStatusLine(Integer.toString(no) + " " + Msg.getMsg(Env.getCtx(), "SearchRows_EnterQuery"), false);
		setStatusDB(Integer.toString(no));

		addDoubleClickListener();
	}

	private List<Object> readLine(int start, int end)
	{
		// cacheStart & cacheEnd - 1 based index, start & end - 0 based index
		if (cacheStart >= 1 && cacheEnd > cacheStart)
		{
			if (start + 1 >= cacheStart && end + 1 <= cacheEnd)
			{
				return end == -1 ? line : line.subList(start - cacheStart + 1, end - cacheStart + 2);
			}
		}

		cacheStart = start + 1 - (PAGE_SIZE * 4);
		if (cacheStart <= 0)
			cacheStart = 1;

		if (end == -1)
		{
			cacheEnd = m_count;
		}
		else
		{
			cacheEnd = end + 1 + (PAGE_SIZE * 4);
			if (cacheEnd > m_count)
				cacheEnd = m_count;
		}

		line = new ArrayList<Object>();

		PreparedStatement m_pstmt = null;
		ResultSet m_rs = null;

		long startTime = System.currentTimeMillis();
		//

		String dynWhere = getSQLWhere();
		StringBuffer sql = new StringBuffer(m_sqlMain);
		if (dynWhere.length() > 0)
			sql.append(dynWhere); // includes first AND
		if (m_sqlUserOrder != null && m_sqlUserOrder.trim().length() > 0)
			sql.append(m_sqlUserOrder);
		else
			sql.append(m_sqlOrder);
		String dataSql = Env.parseContext(Env.getCtx(), p_WindowNo, sql.toString(), false, true); // metas: cg: ignore unparsable so that info window to open
		// String dataSql = Msg.parseTranslation(Env.getCtx(),); // Variables
		dataSql = Env.getUserRolePermissions().addAccessSQL(dataSql, getTableName(),
				IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO);
		if (end > start && m_useDatabasePaging && DB.getDatabase().isPagingSupported())
		{
			dataSql = DB.getDatabase().addPagingSQL(dataSql, cacheStart, cacheEnd);
		}
		log.finer(dataSql);
		try
		{
			m_pstmt = DB.prepareStatement(dataSql, null);
			setParameters(m_pstmt, false); // no count
			log.fine("Start query - " + (System.currentTimeMillis() - startTime) + "ms");
			m_pstmt.setFetchSize(100);
			m_rs = m_pstmt.executeQuery();
			log.fine("End query - " + (System.currentTimeMillis() - startTime) + "ms");
			// skips the row that we dont need if we can't use native db paging
			if (end > start && m_useDatabasePaging && !DB.getDatabase().isPagingSupported())
			{
				for (int i = 0; i < cacheStart - 1; i++)
				{
					if (!m_rs.next())
						break;
				}
			}

			int rowPointer = cacheStart - 1;
			while (m_rs.next())
			{
				rowPointer++;
				readData(m_rs);
				// check now of rows loaded, break if we hit the suppose end
				if (m_useDatabasePaging && rowPointer >= cacheEnd)
				{
					break;
				}
			}
		}

		catch (SQLException e)
		{
			log.log(Level.SEVERE, dataSql, e);
		}

		finally
		{
			DB.close(m_rs, m_pstmt);
		}

		return line;
	}

	private void addDoubleClickListener()
	{
		Iterator<?> i = contentPanel.getListenerIterator(Events.ON_DOUBLE_CLICK);
		while (i.hasNext())
		{
			if (i.next() == this)
				return;
		}
		contentPanel.addEventListener(Events.ON_DOUBLE_CLICK, this);
	}

	protected void insertPagingComponent()
	{
		contentPanel.getParent().insertBefore(paging, contentPanel.getNextSibling());
	}

	public Vector<String> getColumnHeader(ColumnInfo[] p_layout)
	{
		Vector<String> columnHeader = new Vector<String>();

		for (ColumnInfo info : p_layout)
		{
			columnHeader.add(info.getColHeader());
		}
		return columnHeader;
	}

	/**
	 * Test Row Count
	 * 
	 * @return true if display
	 */
	private boolean testCount()
	{
		long start = System.currentTimeMillis();
		String dynWhere = getSQLWhere();
		StringBuffer sql = new StringBuffer(m_sqlCount);

		if (dynWhere.length() > 0)
			sql.append(dynWhere); // includes first AND

		String countSql = Msg.parseTranslation(Env.getCtx(), sql.toString()); // Variables
		countSql = Env.getUserRolePermissions().addAccessSQL(countSql, getTableName(),
				IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO);
		log.finer(countSql);
		m_count = -1;

		try
		{
			PreparedStatement pstmt = DB.prepareStatement(countSql, null);
			setParameters(pstmt, true);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next())
				m_count = rs.getInt(1);

			rs.close();
			pstmt.close();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, countSql, e);
			m_count = -2;
		}

		log.fine("#" + m_count + " - " + (System.currentTimeMillis() - start) + "ms");

		// Armen: add role checking (Patch #1694788 )
		// MRole role = MRole.getDefault();
		// if (role.isQueryMax(no))
		// return ADialog.ask(p_WindowNo, this, "InfoHighRecordCount", String.valueOf(no));

		return true;
	} // testCount

	/**
	 * Save Selection - Called by dispose
	 */
	protected void saveSelection()
	{
		// Already disposed
		if (contentPanel == null)
			return;

		log.config("OK=" + m_ok);

		if (!m_ok) // did not press OK
		{
			m_results.clear();
			contentPanel = null;
			this.detach();
			return;
		}

		// Multi Selection
		if (p_multipleSelection)
		{
			m_results.addAll(getSelectedRowKeys());
		}
		else
		// singleSelection
		{
			Integer data = getSelectedRowKey();
			if (data != null)
				m_results.add(data);
		}

		log.config(getSelectedSQL());

		// Save Settings of detail info screens
		saveSelectionDetail();

	} // saveSelection

	/**
	 * Get the key of currently selected row
	 * 
	 * @return selected key
	 */
	// metas: changed from protected to public
	public Integer getSelectedRowKey()
	{
		Integer key = contentPanel.getSelectedRowKey();

		return key;
	} // getSelectedRowKey

	/**
	 * Get the keys of selected row/s based on layout defined in prepareTable
	 * 
	 * @return IDs if selection present
	 * @author ashley
	 */
	protected ArrayList<Integer> getSelectedRowKeys()
	{
		ArrayList<Integer> selectedDataList = new ArrayList<Integer>();

		if (contentPanel.getKeyColumnIndex() == -1)
		{
			return selectedDataList;
		}

		if (p_multipleSelection)
		{
			int[] rows = contentPanel.getSelectedIndices();
			for (int row = 0; row < rows.length; row++)
			{
				Object data = contentPanel.getModel().getValueAt(rows[row], contentPanel.getKeyColumnIndex());
				if (data instanceof IDColumn)
				{
					IDColumn dataColumn = (IDColumn)data;
					selectedDataList.add(dataColumn.getRecord_ID());
				}
				else
				{
					log.severe("For multiple selection, IDColumn should be key column for selection");
				}
			}
		}

		if (selectedDataList.size() == 0)
		{
			int row = contentPanel.getSelectedRow();
			if (row != -1 && contentPanel.getKeyColumnIndex() != -1)
			{
				Object data = contentPanel.getModel().getValueAt(row, contentPanel.getKeyColumnIndex());
				if (data instanceof IDColumn)
					selectedDataList.add(((IDColumn)data).getRecord_ID());
				if (data instanceof Integer)
					selectedDataList.add((Integer)data);
			}
		}

		return selectedDataList;
	} // getSelectedRowKeys

	/**
	 * Get selected Keys
	 * 
	 * @return selected keys (Integers)
	 */
	public Object[] getSelectedKeys()
	{
		if (!m_ok || m_results.size() == 0)
			return null;
		return m_results.toArray(new Integer[0]);
	} // getSelectedKeys;

	/**
	 * Get (first) selected Key
	 * 
	 * @return selected key
	 */
	public Object getSelectedKey()
	{
		if (!m_ok || m_results.size() == 0)
			return null;
		return m_results.get(0);
	} // getSelectedKey

	/**
	 * Is cancelled? - if pressed Cancel = true - if pressed OK or window closed = false
	 * 
	 * @return true if cancelled
	 */
	public boolean isCancelled()
	{
		return m_cancel;
	} // isCancelled

	/**
	 * Get where clause for (first) selected key
	 * 
	 * @return WHERE Clause
	 */
	public String getSelectedSQL()
	{
		// No results
		Object[] keys = getSelectedKeys();
		if (keys == null || keys.length == 0)
		{
			log.config("No Results - OK="
					+ m_ok + ", Cancel=" + m_cancel);
			return "";
		}
		//
		StringBuffer sb = new StringBuffer(getKeyColumn());
		if (keys.length > 1)
			sb.append(" IN (");
		else
			sb.append("=");

		// Add elements
		for (int i = 0; i < keys.length; i++)
		{
			if (getKeyColumn().endsWith("_ID"))
				sb.append(keys[i].toString()).append(",");
			else
				sb.append("'").append(keys[i].toString()).append("',");
		}

		sb.replace(sb.length() - 1, sb.length(), "");
		if (keys.length > 1)
			sb.append(")");
		return sb.toString();
	} // getSelectedSQL;

	/**
	 * Get Table name Synonym
	 * 
	 * @return table name
	 */
	protected String getTableName()
	{
		return p_tableName;
	} // getTableName

	/**
	 * Get Key Column Name
	 * 
	 * @return column name
	 */
	protected String getKeyColumn()
	{
		return p_keyColumn;
	} // getKeyColumn

	public String[] getEvents()
	{
		return InfoPanel.lISTENER_EVENTS;
	}

	// Elaine 2008/11/28
	/**
	 * Enable OK, History, Zoom if row/s selected --- Changes: Changed the logic for accomodating multiple selection
	 * 
	 * @author ashley
	 */
	protected void enableButtons()
	{
		boolean enable = (contentPanel.getSelectedCount() == 1);
		confirmPanel.getOKButton().setEnabled(contentPanel.getSelectedCount() > 0);

		if (hasHistory())
			confirmPanel.getButton(ConfirmPanel.A_HISTORY).setEnabled(enable);
		if (hasZoom())
			confirmPanel.getButton(ConfirmPanel.A_ZOOM).setEnabled(enable);
	} // enableButtons

	//

	/**************************************************************************
	 * Get dynamic WHERE part of SQL To be overwritten by concrete classes
	 * 
	 * @return WHERE clause
	 */
	protected abstract String getSQLWhere();

	/**
	 * Set Parameters for Query To be overwritten by concrete classes
	 * 
	 * @param pstmt statement
	 * @param forCount for counting records
	 * @throws SQLException
	 */
	protected abstract void setParameters(PreparedStatement pstmt, boolean forCount)
			throws SQLException;

	/**
	 * notify to search editor of a value change in the selection info
	 * 
	 * @param event event
	 * 
	 */

	protected void showHistory()
	{
	}

	/**
	 * Has History (false) To be overwritten by concrete classes
	 * 
	 * @return true if it has history (default false)
	 */
	protected boolean hasHistory()
	{
		return false;
	}

	/**
	 * Customize dialog To be overwritten by concrete classes
	 */
	protected void customize()
	{
	}

	/**
	 * Has Customize (false) To be overwritten by concrete classes
	 * 
	 * @return true if it has customize (default false)
	 */
	protected boolean hasCustomize()
	{
		return false;
	}

	/**
	 * Has Zoom (false) To be overwritten by concrete classes
	 * 
	 * @return true if it has zoom (default false)
	 */
	protected boolean hasZoom()
	{
		return false;
	}

	/**
	 * Save Selection Details To be overwritten by concrete classes
	 */
	protected void saveSelectionDetail()
	{
	}

	/**
	 * Get Zoom Window
	 * 
	 * @param tableName table name
	 * @param isSOTrx sales trx
	 * @return AD_Window_ID
	 */
	protected int getAD_Window_ID(String tableName, boolean isSOTrx)
	{
		if (!isSOTrx && m_PO_Window_ID > 0)
			return m_PO_Window_ID;
		if (m_SO_Window_ID > 0)
			return m_SO_Window_ID;
		//
		String sql = "SELECT AD_Window_ID, PO_Window_ID FROM AD_Table WHERE TableName=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, tableName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				m_SO_Window_ID = rs.getInt(1);
				m_PO_Window_ID = rs.getInt(2);
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		//
		if (!isSOTrx && m_PO_Window_ID > 0)
			return m_PO_Window_ID;
		return m_SO_Window_ID;
	} // getAD_Window_ID

	public void onEvent(Event event)
	{
		if (event != null)
		{
			if (event.getTarget().equals(confirmPanel.getButton(ConfirmPanel.A_OK)))
			{
				onOk();
			}
			else if (event.getTarget() == contentPanel && event.getName().equals(Events.ON_DOUBLE_CLICK))
			{
				onDoubleClick();
			}
			else if (event.getTarget().equals(confirmPanel.getButton(ConfirmPanel.A_REFRESH)))
			{
				showBusyDialog();
				Clients.response(new AuEcho(this, "onQueryCallback", null));
			}
			else if (event.getTarget().equals(confirmPanel.getButton(ConfirmPanel.A_CANCEL)))
			{
				m_cancel = true;
				dispose(false);
			}
			// Elaine 2008/12/16
			else if (event.getTarget().equals(confirmPanel.getButton(ConfirmPanel.A_HISTORY)))
			{
				if (!contentPanel.getChildren().isEmpty() && contentPanel.getSelectedRowKey() != null)
				{
					showHistory();
				}
			}
			else if (event.getTarget().equals(confirmPanel.getButton(ConfirmPanel.A_CUSTOMIZE)))
			{
				if (!contentPanel.getChildren().isEmpty() && contentPanel.getSelectedRowKey() != null)
				{
					customize();
				}
			}
			//
			else if (event.getTarget().equals(confirmPanel.getButton(ConfirmPanel.A_ZOOM)))
			{
				if (!contentPanel.getChildren().isEmpty() && contentPanel.getSelectedRowKey() != null)
				{
					zoom();
					if (isLookup())
						this.detach();
				}
			}
			else if (event.getTarget() == paging)
			{
				int pgNo = paging.getActivePage();
				if (pageNo != pgNo)
				{

					contentPanel.clearSelection();

					pageNo = pgNo;
					int start = pageNo * PAGE_SIZE;
					int end = start + PAGE_SIZE;
					List<Object> subList = readLine(start, end);
					model = new ListModelTable(subList);
					model.setSorter(this);
					model.addTableModelListener(this);
					contentPanel.setData(model, null);

					contentPanel.setSelectedIndex(0);
				}
			}
			// default
			else
			{
				showBusyDialog();
				Clients.response(new AuEcho(this, "onQueryCallback", null));
			}
		}
	} // onEvent

	private void showBusyDialog()
	{
		progressWindow = new BusyDialog();
		progressWindow.setPage(this.getPage());
		progressWindow.doHighlighted();
	}

	private void hideBusyDialog()
	{
		progressWindow.dispose();
		progressWindow = null;
	}

	public void onQueryCallback()
	{
		try
		{
			executeQuery();
			renderItems();
		}
		finally
		{
			hideBusyDialog();
		}
	}

	private void onOk()
	{
		if (!contentPanel.getChildren().isEmpty() && contentPanel.getSelectedRowKey() != null)
		{
			dispose(true);
		}
	}

	private void onDoubleClick()
	{
		if (isLookup())
		{
			dispose(true);
		}
		else
		{
			zoom();
		}

	}

	public void tableChanged(WTableModelEvent event)
	{
		enableButtons();
	}

	public void zoom()
	{
		if (listeners != null && listeners.size() > 0)
		{
			ValueChangeEvent event = new ValueChangeEvent(this, "zoom",
					contentPanel.getSelectedRowKey(), contentPanel.getSelectedRowKey());
			fireValueChange(event);
		}
		else
		{
			Integer recordId = contentPanel.getSelectedRowKey();
			int AD_Table_ID = MTable.getTable_ID(p_tableName);
			if (AD_Table_ID <= 0)
			{
				if (p_keyColumn.endsWith("_ID"))
				{
					AD_Table_ID = MTable.getTable_ID(p_keyColumn.substring(0, p_keyColumn.length() - 3));
				}
			}
			if (AD_Table_ID > 0)
				AEnv.zoom(AD_Table_ID, recordId);
		}
	}

	public void addValueChangeListener(ValueChangeListener listener)
	{
		if (listener == null)
		{
			return;
		}

		listeners.add(listener);
	}

	public void fireValueChange(ValueChangeEvent event)
	{
		for (ValueChangeListener listener : listeners)
		{
			listener.valueChange(event);
		}
	}

	private boolean disposing = false;
	private boolean disposed = false;

	/**
	 * Dispose and save Selection
	 * 
	 * @param ok OK pressed
	 */
	public void dispose(boolean ok)
	{
		if (disposing || disposed)
		{
			// Info window is currently dispossing or it was already disposed
			return;
		}

		disposing = true;
		try
		{
			log.config("OK=" + ok);
			m_ok = ok;

			// End Worker
			if (isLookup())
			{
				saveSelection();
			}
			if (Window.MODE_EMBEDDED.equals(getAttribute(Window.MODE_KEY)))
				SessionManager.getAppDesktop().closeActiveWindow();
			else
				this.detach();

			// 05203: In case the windowNo was created locally, we need cleanup the context for it and to remove the window
			if (localWindowNo)
			{
				SessionManager.getAppDesktop().unregisterWindow(p_WindowNo);
			}
		}
		finally
		{
			disposing = false;
			disposed = true;
		}
	} // dispose	

	/**
	 * 
	 * @return true if this window is currently disposing or it was already disposed
	 */
	public final boolean isDisposed()
	{
		return disposing || disposed;
	}

	public void sort(Comparator cmpr, boolean ascending)
	{
		WListItemRenderer.ColumnComparator lsc = (WListItemRenderer.ColumnComparator)cmpr;
		if (m_useDatabasePaging)
		{
			int col = lsc.getColumnIndex();
			String colsql = p_layout[col].getColSQL().trim();
			int lastSpaceIdx = colsql.lastIndexOf(" ");
			if (lastSpaceIdx > 0)
			{
				String tmp = colsql.substring(0, lastSpaceIdx).trim();
				char last = tmp.charAt(tmp.length() - 1);
				if (tmp.toLowerCase().endsWith("as"))
				{
					colsql = colsql.substring(lastSpaceIdx).trim();
				}
				else if (!(last == '*' || last == '-' || last == '+' || last == '/' || last == '>' || last == '<' || last == '='))
				{
					tmp = colsql.substring(lastSpaceIdx).trim();
					if (tmp.startsWith("\"") && tmp.endsWith("\""))
					{
						colsql = colsql.substring(lastSpaceIdx).trim();
					}
					else
					{
						boolean hasAlias = true;
						for (int i = 0; i < tmp.length(); i++)
						{
							char c = tmp.charAt(i);
							if (Character.isLetterOrDigit(c))
							{
								continue;
							}
							else
							{
								hasAlias = false;
								break;
							}
						}
						if (hasAlias)
						{
							colsql = colsql.substring(lastSpaceIdx).trim();
						}
					}
				}
			}
			m_sqlUserOrder = " ORDER BY " + colsql;
			if (!ascending)
				m_sqlUserOrder += " DESC ";
			executeQuery();
			renderItems();
		}
		else
		{
			Collections.sort(line, lsc);
			renderItems();
		}
	}

	public boolean isLookup()
	{
		return m_lookup;
	}

	public void scrollToSelectedRow()
	{
		if (contentPanel != null && contentPanel.getSelectedIndex() >= 0)
		{
			Listitem selected = contentPanel.getItemAtIndex(contentPanel.getSelectedIndex());
			if (selected != null)
			{
				selected.focus();
			}
		}
	}
	
	private static Map<String, Class<? extends IInfoPanel>> s_infoPanelClasses;
	public static void registerInfoPanel(String tableName, Class<? extends IInfoPanel> infoPanelClass)
	{
		if (s_infoPanelClasses == null)
		{
			s_infoPanelClasses = new HashMap<String, Class<? extends IInfoPanel>>();
		}
		s_infoPanelClasses.put(tableName, infoPanelClass);
	}
}	//	Info
