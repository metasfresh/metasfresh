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

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.GridTabExcelExporter;
import org.adempiere.webui.WArchive;
import org.adempiere.webui.WProcess;
import org.adempiere.webui.WRequest;
import org.adempiere.webui.WZoomAcross;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.apps.ProcessModalDialog;
import org.adempiere.webui.apps.WReport;
import org.adempiere.webui.apps.form.WCreateFromFactory;
import org.adempiere.webui.apps.form.WPayment;
import org.adempiere.webui.component.CWindowToolbar;
import org.adempiere.webui.component.IADTab;
import org.adempiere.webui.component.IADTabList;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.editor.WButtonEditor;
import org.adempiere.webui.event.ActionEvent;
import org.adempiere.webui.event.ActionListener;
import org.adempiere.webui.event.ToolbarListener;
import org.adempiere.webui.exception.ApplicationException;
import org.adempiere.webui.part.AbstractUIPart;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.window.FDialog;
import org.adempiere.webui.window.FindWindow;
import org.adempiere.webui.window.WRecordAccessDialog;
import org.compiere.grid.ICreateFrom;
import org.compiere.model.DataStatusEvent;
import org.compiere.model.DataStatusListener;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridTab.DataNewCopyMode;
import org.compiere.model.GridTabMaxRows;
import org.compiere.model.GridTabMaxRowsRestrictionChecker;
import org.compiere.model.GridTable;
import org.compiere.model.GridWindow;
import org.compiere.model.GridWindowVO;
import org.compiere.model.Lookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MProcess;
import org.compiere.model.MQuery;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoUtil;
import org.compiere.util.ASyncProcess;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.MimeType;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.compiere.util.WebDoc;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;

/**
 *
 * This class is based on org.compiere.apps.APanel written by Jorg Janke.
 * @author Jorg Janke
 *
 * @author <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @author <a href="mailto:hengsin@gmail.com">Low Heng Sin</a>
 * @date Feb 25, 2007
 * @version $Revision: 0.10 $
 *
 * @author Cristina Ghita, www.arhipac.ro
 * @see FR [ 2877111 ] See identifiers columns when delete records https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2877111&group_id=176962
 *
 * @author hengsin, hengsin.low@idalica.com
 * @see FR [2887701] https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2887701&group_id=176962
 * @sponsor www.metas.de
 *
 * @author Teo Sarca, teo.sarca@gmail.com
 *  	<li>BF [ 2992540 ] Grid/Panel not refreshed after process run
 *  		https://sourceforge.net/tracker/?func=detail&aid=2992540&group_id=176962&atid=955896
 *  	<li>BF [ 2985892 ] Opening a window using a new record query is not working
 *  		https://sourceforge.net/tracker/?func=detail&aid=2985892&group_id=176962&atid=955896
 */
public abstract class AbstractADWindowPanel extends AbstractUIPart implements ToolbarListener,
        EventListener, DataStatusListener, ActionListener, ASyncProcess
{
    private static final CLogger logger;

    static
    {
        logger = CLogger.getCLogger(AbstractADWindowPanel.class);
    }
    
    private Properties           ctx;

    private GridWindow           gridWindow;

    protected StatusBarPanel     statusBar;

    protected IADTab          	 adTab;

    private int                  curWindowNo;

    private GridTab              curTab;

    private boolean              m_onlyCurrentRows = true;

    private IADTabpanel           curTabpanel;

    protected CWindowToolbar     toolbar;

    private int                  curTabIndex;

    protected String             title;

    private boolean              newRecord;

    private boolean 			 boolChanges = false;

	private int m_onlyCurrentDays = 0;

	private Component parent;

	private boolean m_uiLocked;

	private boolean m_findCancelled;

	private int embeddedTabindex = -1;

	protected Map<Integer, ADTabpanel> includedMap = new HashMap<Integer, ADTabpanel>();

	private IADTabpanel embeddedTabPanel;

	private boolean m_findCreateNew;

	/**
	 * Constructor for non-embedded mode
	 * @param ctx
	 * @param windowNo
	 */
    public AbstractADWindowPanel(Properties ctx, int windowNo)
    {
        this(ctx, windowNo, null, -1, null);
    }

    /**
     * Constructor for embedded mode
     * @param ctx
     * @param windowNo
     * @param gridWindow
     * @param tabIndex
     * @param tabPanel
     */
    public AbstractADWindowPanel(Properties ctx, int windowNo, GridWindow gridWindow, int tabIndex, IADTabpanel tabPanel)
    {
        this.ctx = ctx;
        this.curWindowNo = windowNo;
        this.gridWindow = gridWindow;
        this.embeddedTabindex = tabIndex;
        this.embeddedTabPanel = tabPanel;
        curTabpanel = tabPanel;
        if (gridWindow != null && tabIndex >= 0)
        	curTab = gridWindow.getTab(tabIndex);

        initComponents();
    }

    /**
     * @param parent
     * @return Component
     */
	@Override
	public Component createPart(Object parent)
    {
		if (parent instanceof Component)
			this.parent = (Component) parent;

		adTab = createADTab();
		adTab.addSelectionEventListener(this);

        return super.createPart(parent);
    }

	/**
	 * @return StatusBarPanel
	 */
	public StatusBarPanel getStatusBar()
    {
    	return statusBar;
    }

	/**
	 * @return boolean
	 */
	public boolean isEmbedded() {
		return embeddedTabindex >= 0;
	}

    private void initComponents()
    {
        /** Initalise toolbar */
        toolbar = new CWindowToolbar(isEmbedded());
        WProcess.createAction(this); // metas
        toolbar.addListener(this);

        statusBar = new StatusBarPanel(isEmbedded());
    }

    /**
     * @return IADTab
     */
    protected abstract IADTab createADTab();

    private void focusToActivePanel() {
    	IADTabpanel adTabPanel = adTab.getSelectedTabpanel();
		if (adTabPanel != null && adTabPanel instanceof HtmlBasedComponent) {
			((HtmlBasedComponent)adTabPanel).focus();
		}
	}

    /**
     * @param adWindowId
     * @param query
     * @return boolean
     */
	public boolean initPanel(int adWindowId, MQuery query)
    {
		// This temporary validation code is added to check the reported bug
		// [ adempiere-ZK Web Client-2832968 ] User context lost?
		// https://sourceforge.net/tracker/?func=detail&atid=955896&aid=2832968&group_id=176962
		// it's harmless, if there is no bug then this must never fail
		Session currSess = Executions.getCurrent().getDesktop().getSession();
		int checkad_user_id = -1;
		if (currSess != null && currSess.getAttribute("Check_AD_User_ID") != null)
			checkad_user_id = (Integer)currSess.getAttribute("Check_AD_User_ID");
		if (checkad_user_id!=Env.getAD_User_ID(ctx))
		{
			String msg = "Timestamp=" + new Date() 
					+ ", Bug 2832968 SessionUser="
					+ checkad_user_id
					+ ", ContextUser="
					+ Env.getAD_User_ID(ctx)
					+ ".  Please report conditions to your system administrator or in sf tracker 2832968";
			ApplicationException ex = new ApplicationException(msg);
			logger.log(Level.SEVERE, msg, ex);
			throw ex;
		}
		// End of temporary code for [ adempiere-ZK Web Client-2832968 ] User context lost?

		// Set AutoCommit for this Window
		if (embeddedTabindex < 0)
		{
			Env.setAutoCommit(ctx, curWindowNo, Env.isAutoCommit(ctx));
			boolean autoNew = Env.isAutoNew(ctx);
			Env.setAutoNew(ctx, curWindowNo, autoNew);

	        GridWindowVO gWindowVO = AEnv.getMWindowVO(curWindowNo, adWindowId, 0);
	        if (gWindowVO == null)
	        {
	            throw new ApplicationException(Msg.getMsg(ctx,
	                    "AccessTableNoView")
	                    + "(No Window Model Info)");
	        }
	        gridWindow = new GridWindow(gWindowVO);
	        title = gridWindow.getName();

	        // Set SO/AutoNew for Window
	        Env.setContext(ctx, curWindowNo, "IsSOTrx", gridWindow.isSOTrx());
	        if (!autoNew && gridWindow.isTransaction())
	        {
	            Env.setAutoNew(ctx, curWindowNo, true);
	        }
		}

        m_onlyCurrentRows =  embeddedTabindex < 0 && gridWindow.isTransaction();

        MQuery detailQuery = null;
        /**
         * Window Tabs
         */
        if (embeddedTabindex < 0)
        {
        	if (query != null && query.getZoomTableName() != null && query.getZoomColumnName() != null
					&& query.getZoomValue() instanceof Integer && (Integer)query.getZoomValue() > 0)
	    	{
	    		if (!query.getZoomTableName().equalsIgnoreCase(gridWindow.getTab(0).getTableName()))
	    		{
	    			detailQuery = query;
	    			query = new MQuery();
	    			query.addRestriction("1=2");
	    			query.setRecordCount(0);
	    		}
	    	}

	        int tabSize = gridWindow.getTabCount();

	        for (int tab = 0; tab < tabSize; tab++)
	        {
	            initTab(query, tab);
	            if (tab == 0 && curTab == null && m_findCancelled)
	            	return false;
	        }
	        Env.setContext(ctx, curWindowNo, "WindowName", gridWindow.getName());
        }
        else
        {
        	initEmbeddedTab(query, embeddedTabindex);
        }

        if (curTab != null)
        	curTab.getTableModel().setChanged(false);

        if (embeddedTabindex < 0)
        {
	        curTabIndex = 0;

	        adTab.setSelectedIndex(0);
	        toolbar.enableTabNavigation(adTab.getTabCount() > 1);
	        toolbar.enableFind(true);
	        adTab.evaluate(null);

	        if (gridWindow.isTransaction())
	        {
	        	toolbar.enableHistoryRecords(true);
	        }

	        if (detailQuery != null && zoomToDetailTab(detailQuery))
	        {
	        	return true;
	        }
        }
        else
        {
        	curTabIndex = embeddedTabindex;
        	toolbar.enableTabNavigation(false);
	        toolbar.enableFind(true);
	        toolbar.enableHistoryRecords(false);
        }

        updateToolbar();

        return true;
    }

	private boolean zoomToDetailTab(MQuery query) {
		//zoom to detail
        if (query != null && query.getZoomTableName() != null && query.getZoomColumnName() != null)
    	{
    		GridTab gTab = gridWindow.getTab(0);
    		if (!query.getZoomTableName().equalsIgnoreCase(gTab.getTableName()))
    		{
    			int tabSize = gridWindow.getTabCount();

    	        for (int tab = 0; tab < tabSize; tab++)
    	        {
    	        	gTab = gridWindow.getTab(tab);
    	        	if (gTab.isSortTab())
    	        		continue;
    	        	if (gTab.getTableName().equalsIgnoreCase(query.getZoomTableName()))
    	        	{
    	        		if (doZoomToDetail(gTab, query, tab)) {
	        				return true;
	        			}
    	        	}
    	        }
    		}
    	}
        return false;
	}

	private boolean doZoomToDetail(GridTab gTab, MQuery query, int tabIndex) {
		GridField[] fields = gTab.getFields();
		for (GridField field : fields)
		{
			if (field.getColumnName().equalsIgnoreCase(query.getZoomColumnName()))
			{
				gridWindow.initTab(tabIndex);
				int parentId = DB.getSQLValue(null, "SELECT " + gTab.getLinkColumnName() + " FROM " + gTab.getTableName() + " WHERE " + query.getWhereClause());
				if (parentId > 0)
				{
					Map<Integer, Object[]>parentMap = new TreeMap<Integer, Object[]>();
					int index = tabIndex;
					int oldpid = parentId;
					GridTab currentTab = gTab;
					while (index > 0)
					{
						index--;
						GridTab pTab = gridWindow.getTab(index);
						if (pTab.getTabLevel() < currentTab.getTabLevel())
						{
							gridWindow.initTab(index);
							if (index > 0)
							{
								if (pTab.getLinkColumnName() != null && pTab.getLinkColumnName().trim().length() > 0)
								{
									int pid = DB.getSQLValue(null, "SELECT " + pTab.getLinkColumnName() + " FROM " + pTab.getTableName() + " WHERE " + currentTab.getLinkColumnName() + " = ?", oldpid);
									if (pid > 0)
									{
										parentMap.put(index, new Object[]{currentTab.getLinkColumnName(), oldpid});
										oldpid = pid;
										currentTab = pTab;
									}
									else
									{
										parentMap.clear();
										break;
									}
								}
							}
							else
							{
								parentMap.put(index, new Object[]{currentTab.getLinkColumnName(), oldpid});
							}
						}
					}

					for(Map.Entry<Integer, Object[]> entry : parentMap.entrySet())
					{
						GridTab pTab = gridWindow.getTab(entry.getKey());
						Object[] value = entry.getValue();
						MQuery pquery = new MQuery(pTab.getAD_Table_ID());
						pquery.addRestriction((String)value[0], "=", value[1]);
						pTab.setQuery(pquery);
						IADTabpanel tp = adTab.findADTabpanel(pTab);
        				tp.createUI();
        				tp.query();
					}

					MQuery targetQuery = new MQuery(gTab.getAD_Table_ID());
					targetQuery.addRestriction(gTab.getLinkColumnName(), "=", parentId);
					gTab.setQuery(targetQuery);
					IADTabpanel gc = null;
					if (!includedMap.containsKey(gTab.getAD_Tab_ID()))
					{
						gc = adTab.findADTabpanel(gTab);
					}
					else
					{
						ADTabpanel parent = includedMap.get(gTab.getAD_Tab_ID());
						gc = parent.findEmbeddedPanel(gTab);
					}
					gc.createUI();
					gc.query(false, 0, GridTabMaxRows.NO_RESTRICTION);

					GridTable table = gTab.getTableModel();
    				int count = table.getRowCount();
    				for(int i = 0; i < count; i++)
    				{
    					int id = table.getKeyID(i);
    					if (id == ((Integer)query.getZoomValue()).intValue())
    					{
    						if (!includedMap.containsKey(gTab.getAD_Tab_ID()))
    						{
    							setActiveTab(gridWindow.getTabIndex(gTab));
    						}
    						else
    						{
    							IADTabpanel parent = includedMap.get(gTab.getAD_Tab_ID());
    							int pindex = gridWindow.getTabIndex(parent.getGridTab());
    							if (pindex >= 0)
    								setActiveTab(pindex);
    						}
    						gTab.setCurrentRow(i);
    						return true;
    					}
    				}
				}
			}
		}
		return false;
	}

	private void initEmbeddedTab(MQuery query, int tabIndex) {
		GridTab gTab = gridWindow.getTab(tabIndex);
		gTab.addDataStatusListener(this);
		adTab.addTab(gTab, embeddedTabPanel);
		if (gTab.isSortTab()) {
			((ADSortTab)embeddedTabPanel).registerAPanel(this);
		} else {
			((ADTabpanel)embeddedTabPanel).init(this, curWindowNo, gTab, gridWindow);
		}
	}

	/**
	 * @param query
	 * @param tabIndex
	 */
	protected void initTab(MQuery query, int tabIndex) {
		gridWindow.initTab(tabIndex);

		GridTab gTab = gridWindow.getTab(tabIndex);
		Env.setContext(ctx, curWindowNo, tabIndex, GridTab.CTX_TabLevel, Integer.toString(gTab.getTabLevel()));

		// Query first tab
		if (tabIndex == 0)
		{
		    query = initialQuery(query, gTab);
		    // metas: 02842: changed - we need to stop even if the tab is not HighVolume and user presses Cancel in find window
		    //if (gTab.isHighVolume() && m_findCancelled)
		    if (m_findCancelled)
		    {
		    	curTab = null; // metas: make sure current tab is set to null
		    	return;
		    }

		    if (query != null && query.getRecordCount() <= 1)
		    {
		        // goSingleRow = true;
		    }
		    // Set initial Query on first tab
		    if (query != null)
		    {
		        m_onlyCurrentRows = false;
		        gTab.setQuery(query);
		    }

		    curTab = gTab;
		    curTabIndex = tabIndex;
		}

		if (gTab.isSortTab())
		{
			ADSortTab sortTab = new ADSortTab(curWindowNo, gTab);
			if (includedMap.containsKey(gTab.getAD_Tab_ID()))
		    {
		    	includedMap.get(gTab.getAD_Tab_ID()).embed(ctx, curWindowNo, gridWindow, gTab.getAD_Tab_ID(), tabIndex, sortTab);
		    }
			else
			{
				adTab.addTab(gTab, sortTab);
				sortTab.registerAPanel(this);
				if (tabIndex == 0) {
					curTabpanel = sortTab;
					curTabpanel.createUI();
					curTabpanel.query(m_onlyCurrentRows, m_onlyCurrentDays, GridTabMaxRows.DEFAULT);
					curTabpanel.activate(true);
				}
				gTab.addDataStatusListener(this);
			}
		}
		else
		{
			//build embedded tab map
			ADTabpanel fTabPanel = new ADTabpanel();
			GridField[] fields = gTab.getTableModel().getFields();
		    for(int i = 0; i < fields.length; i++)
		    {
		    	if (fields[i].getIncluded_Tab_ID() > 0)
		    	{
		    		includedMap.put(fields[i].getIncluded_Tab_ID(), fTabPanel);
		    	}
		    }

		    if (includedMap.containsKey(gTab.getAD_Tab_ID()))
		    {
		    	includedMap.get(gTab.getAD_Tab_ID()).embed(ctx, curWindowNo, gridWindow, gTab.getAD_Tab_ID(), tabIndex, fTabPanel);
		    }
		    else
		    {
		    	gTab.addDataStatusListener(this);
		    	fTabPanel.init(this, curWindowNo, gTab, gridWindow);
		    	adTab.addTab(gTab, fTabPanel);
			    if (tabIndex == 0) {
			    	fTabPanel.createUI();
			    	curTabpanel = fTabPanel;
			    	curTabpanel.query(m_onlyCurrentRows, m_onlyCurrentDays, GridTabMaxRows.DEFAULT);
			        curTabpanel.activate(true);
			    }
		    }
		}

		if (tabIndex == 0) {
			if (curTab.isHighVolume() && m_findCreateNew)
				onNew();
		    else if (query == null && curTab.getRowCount() == 0 && Env.isAutoNew(ctx, curWindowNo))
		    	onNew();
		    else if (!curTab.isReadOnly() && curTab.isQueryNewRecord())
		    	onNew();
		}
	}

    /**
     * Initial Query
     *
     * @param query
     *            initial query
     * @param mTab
     *            tab
     * @return query or null
     */
    private MQuery initialQuery(MQuery query, GridTab mTab)
    {
        // We have a (Zoom) query
        if (query != null && query.isActive() && query.getRecordCount() < 10)
            return query;
        //
        StringBuffer where = new StringBuffer();
        {
            String wh1 = mTab.getWhereExtended();
            if (wh1 == null || wh1.length() == 0)
                wh1 = mTab.getWhereClause();
            if (wh1 != null && wh1.length() > 0)
                where.append(wh1);
            //
            if (query != null)
            {
                String wh2 = query.getWhereClause();
                if (wh2.length() > 0)
                {
                    if (where.length() > 0)
                        where.append(" AND ");
                    where.append(wh2);
                }
            }
        }
        
        // Query automatically if high volume and no query
		// metas: we need to check query count anyway, else when we zoom on a window, even if there are a few results the Find window will popup because table was marked as HighVolume
        boolean require = false; //mTab.isHighVolume();
        //if (query != null) // No Trx Window // metas: we need to check the count anyway, even if we don't have a query
        {
            StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM ")
                    .append(mTab.getTableName());
            if (where.length() > 0)
                sql.append(" WHERE ").append(where);
            // Does not consider security
            int no = DB.getSQLValue(null, sql.toString());
            //
			require = GridTabMaxRowsRestrictionChecker.builder()
					.setAD_Tab(mTab)
					.build()
					.isQueryRequire(no);
        }
        // Show Query
        if (require)
        {
        	m_findCancelled = false;
        	m_findCreateNew = false;
            GridField[] findFields = mTab.getFields();
            FindWindow find = new FindWindow(curWindowNo,
                    mTab.getName(), mTab.getAD_Table_ID(), mTab.getTableName(),
                    where.toString(),
                    null, // metas: query is null because we included everything in "where" 
                    findFields, 10, mTab.getAD_Tab_ID()); // no query below 10
            if (find.getTitle() != null && find.getTitle().length() > 0) {
            	// Title is not set when the number of rows is below the minRecords parameter (10)
                if (!find.isCancel())
                {
                	final MQuery queryOld = query; // metas
                	query = find.getQuery();
					if (queryOld != null)
					{
						query.addRestriction(queryOld.getWhereClause(), true); // metas: adding back old query
					}
                	m_findCreateNew = find.isCreateNew();
                }
                else
                {
                	m_findCancelled = true;
                }
                find = null;
            }
        }
        return query;
    } // initialQuery

    public String getTitle()
    {
        return title;
    }

    /**
     * @see ToolbarListener#onDetailRecord()
     */
    @Override
	public void onDetailRecord()
    {
        int maxInd = adTab.getTabCount() - 1;
        int curInd = adTab.getSelectedIndex();
        if (curInd < maxInd)
        {
            setActiveTab(curInd + 1);
        }

        focusToActivePanel();
    }

	/**
     * @see ToolbarListener#onParentRecord()
     */
    @Override
	public void onParentRecord()
    {
        int curInd = adTab.getSelectedIndex();
        if (curInd > 0)
        {
            setActiveTab(curInd - 1);
        }

        focusToActivePanel();
    }

    /**
     * @see ToolbarListener#onFirst()
     */
    @Override
	public void onFirst()
    {
        curTab.navigate(0);
        focusToActivePanel();
    }

    /**
     * @see ToolbarListener#onLast()
     */
    @Override
	public void onLast()
    {
        curTab.navigate(curTab.getRowCount() - 1);
        focusToActivePanel();
    }

    /**
     * @see ToolbarListener#onNext()
     */
    @Override
	public void onNext()
    {
        curTab.navigateRelative(+1);
        focusToActivePanel();
    }

    /**
     * @see ToolbarListener#onPrevious()
     */
    @Override
	public void onPrevious()
    {
        curTab.navigateRelative(-1);
        focusToActivePanel();
    }

    // Elaine 2008/12/04
	private Menupopup 	m_popup = null;
	private Menuitem 	m_lock = null;
	private Menuitem 	m_access = null;

	/**
	 *	@see ToolbarListener#onLock()
	 */
	public void onLock()
	{
		if (!toolbar.isPersonalLock)
			return;
		final int record_ID = curTab.getRecord_ID();
		if (record_ID == -1)	//	No Key
			return;

		if(m_popup == null)
		{
			m_popup = new Menupopup();

			m_lock = new Menuitem(Msg.translate(Env.getCtx(), "Lock"));
			m_popup.appendChild(m_lock);
			m_lock.addEventListener(Events.ON_CLICK, new EventListener()
			{
				@Override
				public void onEvent(Event event) throws Exception
				{
					curTab.lock(Env.getCtx(), record_ID, !toolbar.getButton("Lock").isPressed());
					curTab.loadAttachments();			//	reload

					toolbar.lock(curTab.isLocked());
				}
			});

			m_access = new Menuitem(Msg.translate(Env.getCtx(), "RecordAccessDialog"));
			m_popup.appendChild(m_access);
			m_access.addEventListener(Events.ON_CLICK, new EventListener()
			{
				@Override
				public void onEvent(Event event) throws Exception
				{
					new WRecordAccessDialog(null, curTab.getAD_Table_ID(), record_ID);

					toolbar.lock(curTab.isLocked());
				}
			});

			m_popup.setPage(toolbar.getButton("Lock").getPage());
		}
		m_popup.open(toolbar.getButton("Lock"));
	}	//	lock
	//

    /**
     * @see ToolbarListener#onHistoryRecords()
     */
    @Override
	public void onHistoryRecords()
    {
		logger.info("");

		if (gridWindow.isTransaction())
		{
			if (curTab.needSave(true, true) && !onSave(false))
				return;

			WOnlyCurrentDays ocd = new WOnlyCurrentDays();
			m_onlyCurrentDays = ocd.getCurrentDays();

			history(m_onlyCurrentDays);
		}
		focusToActivePanel();
    }

    private void history(int onlyCurrentDays)
    {
		if (onlyCurrentDays == 1)	//	Day
		{
			m_onlyCurrentRows = true;
			onlyCurrentDays = 0; 	//	no Created restriction
		}
		else
			m_onlyCurrentRows = false;

		curTab.setQuery(null);	//	reset previous queries

		logger.config("OnlyCurrent=" + m_onlyCurrentRows
			+ ", Days=" + m_onlyCurrentDays);

		curTab.query(m_onlyCurrentRows, onlyCurrentDays, GridTabMaxRows.DEFAULT);
    }

    /**
     * @see ToolbarListener#onAttachment()
     */
    @Override
	public void onAttachment()
    {
		int record_ID = curTab.getRecord_ID();
		logger.info("Record_ID=" + record_ID);

		if (record_ID == -1)	//	No Key
		{
			//aAttachment.setEnabled(false);
			return;
		}

		//	Attachment va =
		new WAttachment (	curWindowNo, curTab.getAD_AttachmentID(),
							curTab.getAD_Table_ID(), record_ID, null);

		curTab.loadAttachments();				//	reload
		toolbar.getButton("Attachment").setPressed(curTab.hasAttachment());
		focusToActivePanel();
	}

    /**
     * @see ToolbarListener#onToggle()
     */
    @Override
	public void onToggle()
    {
    	curTabpanel.switchRowPresentation();
    	focusToActivePanel();
    }

    /**
     * @return boolean
     */
    public boolean onExit()
    {
    	if (!boolChanges)
    	{
    		Events.postEvent(EVENT_OnWindowClose, getComponent(), null); // metas
    		return true;
    	}
    	
    	FDialog.info(this.curWindowNo, null, "SaveBeforeClose");
    	return false;
    }

    /**
     * @param event
     * @see EventListener#onEvent(Event)
     */
    @Override
	public void onEvent(Event event)
    {
    	if (!Events.ON_SELECT.equals(event.getName()))
    		return;

    	IADTabList tabList = null;
    	Component target = event.getTarget();
    	if (target instanceof IADTabList)
    	{
    		tabList = (IADTabList) target;
    	}
    	else
    	{
    		target = target.getParent();
    		while(target != null)
    		{
    			if (target instanceof IADTabList)
    	    	{
    	    		tabList = (IADTabList) target;
    	    		break;
    	    	}
    			target = target.getParent();
    		}
    	}

        if (tabList != null)
        {
        	int newTabIndex = tabList.getSelectedIndex();

            if (setActiveTab(newTabIndex))
            {
            	//force sync model
            	tabList.refresh();
            }
            else
            {
            	//reset to original
            	tabList.setSelectedIndex(curTabIndex);
            }
        }
    }

	private boolean setActiveTab(int newTabIndex) {
		boolean back = false;

		int oldTabIndex = curTabIndex;

		if (oldTabIndex == newTabIndex)
		{
		    return true;
		}

		if (curTab != null)
		{
			if (curTab.isSortTab())
			{
				if (curTabpanel instanceof ADSortTab)
				{
					((ADSortTab)curTabpanel).saveData();
					((ADSortTab)curTabpanel).unregisterPanel();
				}
			}
			else if (curTab.needSave(true, false))
		    {
		    	if (curTab.needSave(true, true))
				{
					//	Automatic Save
					if (Env.isAutoCommit(ctx, curWindowNo)
						&& (curTab.getCommitWarning() == null || curTab.getCommitWarning().trim().length() == 0))
					{
						if (!curTab.dataSave(true))
						{
							showLastError();
							//  there is a problem, stop here
							return false;
						}
					}
					//  explicitly ask when changing tabs
					else if (FDialog.ask(curWindowNo, this.getComponent(), "SaveChanges?", curTab.getCommitWarning()))
					{   //  yes we want to save
						if (!curTab.dataSave(true))
						{
							showLastError();
							//  there is a problem, stop here
							return false;
						}
					}
					else    //  Don't save
					{
						int newRecord= curTab.getTableModel().getNewRow();     //VOSS COM

						if( newRecord == -1)
							curTab.dataIgnore();
						else
						{
							return false;
						}
					}
				}
				else    //  new record, but nothing changed
					curTab.dataIgnore();
			}   //  there is a change
		}

		if (!adTab.updateSelectedIndex(oldTabIndex, newTabIndex))
		{
		    FDialog.warn(curWindowNo, "TabSwitchJumpGo", title);
		    return false;
		}

		IADTabpanel oldTabpanel = curTabpanel;
		IADTabpanel newTabpanel = adTab.getSelectedTabpanel();
		curTab = newTabpanel.getGridTab();

		if (oldTabpanel != null)
			oldTabpanel.activate(false);
		newTabpanel.activate(true);

		back = (newTabIndex < oldTabIndex);
		if (back)
		{
			if (newTabpanel.getTabLevel() >= oldTabpanel.getTabLevel())
				back = false;
			else if ((newTabIndex - oldTabIndex) > 1)
			{
				for (int i = oldTabIndex - 1; i > newTabIndex; i--)
				{
					IADTabpanel next = adTab.getADTabpanel(i);
					if (next != null && next.getTabLevel() <= newTabpanel.getTabLevel())
					{
						back = false;
						break;
					}
				}
			}
		}

		if (!back)
		{
		    newTabpanel.query();
		}
		else
		{
		    newTabpanel.refresh();
		}

		curTabIndex = newTabIndex;
		curTabpanel = newTabpanel;

		if (curTabpanel instanceof ADSortTab)
		{
			((ADSortTab)curTabpanel).registerAPanel(this);
		}
		else
		{
			if (curTab.getRowCount() == 0 && Env.isAutoNew(ctx, getWindowNo()))
			{
				onNew();
			}
		}

		updateToolbar();

		return true;
	}

	private void updateToolbar()
	{
		toolbar.enableChanges(curTab.isReadOnly());
		toolbar.enabledNew(curTab.isInsertRecord());
		toolbar.enableCopy(curTab.isInsertRecord());

		toolbar.enableTabNavigation(curTabIndex > 0,
		        curTabIndex < (adTab.getTabCount() - 1));

		toolbar.getButton("Attachment").setPressed(curTab.hasAttachment());
		if (isFirstTab())
        {
            toolbar.getButton("HistoryRecords").setPressed(!curTab.isOnlyCurrentRows());
        }
		toolbar.getButton("Find").setPressed(curTab.isQueryActive());

		if (toolbar.isPersonalLock)
		{
			toolbar.lock(curTab.isLocked());
		}

		toolbar.enablePrint(curTab.isPrinted());

        if (gridWindow.isTransaction() && isFirstTab())
        {
        	toolbar.enableHistoryRecords(true);
        }
        else
        {
        	toolbar.enableHistoryRecords(false);
        }

	}

	/**
	 * @param e
	 * @see DataStatusListener#dataStatusChanged(DataStatusEvent)
	 */
    @Override
	public void dataStatusChanged(DataStatusEvent e)
    {
    	//ignore non-ui thread event for now.
    	if (Executions.getCurrent() == null)
    		return;

        logger.info(e.getMessage());
        String dbInfo = e.getMessage();
        if (curTab != null && curTab.isQueryActive())
            dbInfo = "[ " + dbInfo + " ]";
        statusBar.setStatusDB(dbInfo, e);

        //  Set Message / Info
        if (e.getAD_Message() != null || e.getInfo() != null)
        {
            StringBuffer sb = new StringBuffer();
            String msg = e.getMessage();
            if (msg != null && msg.length() > 0)
            {
                sb.append(Msg.getMsg(Env.getCtx(), e.getAD_Message()));
            }
            String info = e.getInfo();
            if (info != null && info.length() > 0)
            {
                if (sb.length() > 0 && !sb.toString().trim().endsWith(":"))
                    sb.append(": ");
                sb.append(info);
            }
            if (sb.length() > 0)
            {
                int pos = sb.indexOf("\n");
                if (pos != -1 && pos+1 < sb.length())  // replace CR/NL
                {
                    sb.replace(pos, pos+1, " - ");
            	}
                boolean showPopup = e.isError() 
					|| (!GridTab.DEFAULT_STATUS_MESSAGE.equals(e.getAD_Message()));
                statusBar.setStatusLine (sb.toString (), e.isError (), showPopup);
            }
        }

        //  Confirm Error
        if (e.isError() && !e.isConfirmed())
        {
        	//focus to error field
        	ADTabpanel tabPanel = (ADTabpanel) getADTab().getSelectedTabpanel();
        	GridField[] fields = curTab.getFields();
        	for (GridField field : fields)
        	{
        		if (field.isError())
        		{
        			tabPanel.setFocusToField(field.getColumnName());
        			break;
        		}
        	}
            e.setConfirmed(true);   //  show just once - if MTable.setCurrentRow is involved the status event is re-issued
        }
        //  Confirm Warning
        else if (e.isWarning() && !e.isConfirmed())
        {
            FDialog.warn(curWindowNo, null, e.getAD_Message(), e.getInfo());
            e.setConfirmed(true);   //  show just once - if MTable.setCurrentRow is involved the status event is re-issued
        }

        //  update Navigation
        boolean firstRow = e.isFirstRow();
        boolean lastRow = e.isLastRow();
        toolbar.enableFirstNavigation(!firstRow && !curTab.isSortTab());
        toolbar.enableLastNavigation(!lastRow && !curTab.isSortTab());

        //  update Change
        boolean changed = e.isChanged() || e.isInserting();
        boolChanges = changed;
        boolean readOnly = curTab.isReadOnly();
        boolean insertRecord = !readOnly;

        if (insertRecord)
        {
            insertRecord = curTab.isInsertRecord();
        }
//        toolbar.enabledNew(!changed && insertRecord && !curTab.isSortTab());
        toolbar.enabledNew(insertRecord && !curTab.isSortTab()); // metas : cg: task 03494
        toolbar.enableCopy(!changed && insertRecord && !curTab.isSortTab());
        toolbar.enableRefresh(!changed);
        toolbar.enableDelete(!changed && !readOnly && !curTab.isSortTab());
        toolbar.enableDeleteSelection(!changed && !readOnly && !curTab.isSortTab());
        //
        if (readOnly && curTab.isAlwaysUpdateField())
        {
            readOnly = false;
        }
        toolbar.enableIgnore(changed && !readOnly);
        toolbar.enableSave(changed && !readOnly);
        
        //
        //  No Rows
        if (e.getTotalRows() == 0 && insertRecord)
        {
	    	toolbar.enableCopy(false); // metas: if there is no record, is nothing to copy
            toolbar.enableDelete(false);
            toolbar.enableDeleteSelection(false);
        }

        //  History (on first Tab only)
        if (isFirstTab())
        {
            toolbar.getButton("HistoryRecords").setPressed(!curTab.isOnlyCurrentRows());
        }

        //  Transaction info
        final String summaryInfoMessage = curTab.getSummaryInfoMessage();
        if (summaryInfoMessage != null)
        {
            statusBar.setInfo(summaryInfoMessage);
        }

        //  Check Attachment
        boolean canHaveAttachment = curTab.canHaveAttachment();       //  not single _ID column
        //
        if (canHaveAttachment && e.isLoading() &&
                curTab.getCurrentRow() > e.getLoadedRows())
        {
            canHaveAttachment = false;
        }
        if (canHaveAttachment && curTab.getRecord_ID() == -1)    //   No Key
        {
            canHaveAttachment = false;
        }
        if (canHaveAttachment)
        {
            toolbar.enableAttachment(true);
            toolbar.getButton("Attachment").setPressed(curTab.hasAttachment());
        }
        else
        {
            toolbar.enableAttachment(false);
        }

        toolbar.getButton("Find").setPressed(curTab.isQueryActive());

        // Elaine 2008/12/05
        //  Lock Indicator
        if (toolbar.isPersonalLock)
        {
			toolbar.lock(curTab.isLocked());
        }
        //

        adTab.evaluate(e);

        toolbar.enablePrint(curTab.isPrinted());
        toolbar.enableReport(true);
    }

    /**
     * @return boolean
     */
    public boolean isFirstTab()
    {
        int selTabIndex = adTab.getSelectedIndex();
        return (selTabIndex == 0);
    }

    /**
     * @see ToolbarListener#onRefresh()
     */
    @Override
	public void onRefresh()
    {
    	onSave(false);
        curTab.dataRefreshAll();
        curTabpanel.dynamicDisplay(0);
        focusToActivePanel();
    }

    /**
     * @see ToolbarListener#onHelp()
     */
    @Override
	public void onHelp()
    {
    	WebDoc doc = gridWindow.getHelpDoc(true);
		SessionManager.getAppDesktop().showURL(doc, "Help", true);
    }

    /**
     * @see ToolbarListener#onNew()
     */
	@Override
    public void onNew()
    {
        if (!curTab.isInsertRecord())
        {
			logger.warning("Insert Record disabled for Tab: " + curTab);
            return;
        }

        if (!autoSave()) {
        	return;
        }

		newRecord = curTab.dataNew(DataNewCopyMode.NoCopy);
        if (newRecord)
        {
            curTabpanel.dynamicDisplay(0);
            toolbar.enableChanges(false);
            toolbar.enableDelete(false);
    		toolbar.enableDeleteSelection(false);
            toolbar.enableNavigation(false);
            toolbar.enableTabNavigation(false);
            toolbar.enableIgnore(true);
            toolbar.enablePrint(curTab.isPrinted());
            toolbar.enableReport(true);
        }
        else
        {
            logger.severe("Could not create new record");
        }
        focusToActivePanel();
    }

    private boolean autoSave() {
    	//  has anything changed?
		if (curTab.needSave(true, false))
		{   //  do we have real change
			if (curTab.needSave(true, true))
			{
				if (!onSave(false))
				{
					return false;
				}
			}
			else    //  new record, but nothing changed
				curTab.dataIgnore();
		}   //  there is a change
		return true;
	}

	// Elaine 2008/11/19
    /**
     * @see ToolbarListener#onCopy()
     */
    public void onCopy()
    {
        if (!curTab.isInsertRecord())
        {
            logger.warning("Insert Record disabled for Tab");
            return;
        }

		newRecord = curTab.dataNew(DataNewCopyMode.Copy);
        if (newRecord)
        {
            curTabpanel.dynamicDisplay(0);
            toolbar.enableChanges(false);
            toolbar.enableDelete(false);
            toolbar.enableDeleteSelection(false); // Elaine 2008/12/02
            toolbar.enableNavigation(false);
            toolbar.enableTabNavigation(false);
            toolbar.enableIgnore(true);
            toolbar.enablePrint(curTab.isPrinted());
            toolbar.enableReport(true);
        }
        else
        {
            logger.severe("Could not create new record");
        }
        focusToActivePanel();
    }
    //

    /**
     * @see ToolbarListener#onFind()
     */
    @Override
	public void onFind()
    {
        if (curTab == null)
            return;
        
        if (!onSave(false))
        	return;

        //  Gets Fields from AD_Field_v
        //GridField[] findFields = GridField.createFields(ctx, curTab.getWindowNo(), 0,curTab.getAD_Tab_ID());
        final FindWindow find = new FindWindow (curTab.getWindowNo(), curTab.getName(),
            curTab.getAD_Table_ID(), curTab.getTableName(),
            curTab.getWhereExtended(),
            curTab.getQuery(), // metas
            curTab.getFields(), // metas: pass the grid tab fields directly; FindWindow will copy them
            1, // minRecords
            curTab.getAD_Tab_ID());

        if (!find.isCancel())
        {
	        MQuery query = find.getQuery();

	        //  Confirmed query
	        if (query != null)
	        {
	            m_onlyCurrentRows = false;          //  search history too
	            curTab.setQuery(query);
	            curTabpanel.query(m_onlyCurrentRows, m_onlyCurrentDays, GridTabMaxRows.DEFAULT);   //  autoSize
	        }

	        curTab.dataRefresh(); // Elaine 2008/07/25
        }
        focusToActivePanel();
    }

    /**
     * @see ToolbarListener#onIgnore()
     */
    @Override
	public void onIgnore()
    {
    	if (curTab.isSortTab())
    	{
    		curTabpanel.refresh();
    		toolbar.enableIgnore(false);
    	}
    	else
    	{
	        curTab.dataIgnore();
	        curTab.dataRefresh();
	        curTabpanel.dynamicDisplay(0);
	        toolbar.enableIgnore(false);
    	}
    	focusToActivePanel();
    }

    /**
     * @see ToolbarListener#onSave()
     */
    @Override
	public void onSave()
    {
    	onSave(true);
    	focusToActivePanel();
    }

    /**
     * @param onSaveEvent
     */
    private boolean onSave(boolean onSaveEvent)
    {
    	if (curTab.isSortTab())
    	{
    		((ADSortTab)curTabpanel).saveData();
    		toolbar.enableSave(true);	//	set explicitly
    		toolbar.enableIgnore(false);
    		return true;
    	}
    	else
    	{
    		if (onSaveEvent && curTab.getCommitWarning() != null && curTab.getCommitWarning().trim().length() > 0)
    		{
    			if (!FDialog.ask(curWindowNo, this.getComponent(), "SaveChanges?", curTab.getCommitWarning()))
    			{
    				return false;
    			}
    		}
	    	boolean retValue = curTab.dataSave(onSaveEvent);

	        if (!retValue)
	        {
	        	showLastError();
	            return false;
	        } 
	        else if (!onSaveEvent) //need manual refresh
	        {
	        	curTab.setCurrentRow(curTab.getCurrentRow());
	        }
	        curTabpanel.dynamicDisplay(0);
	        curTabpanel.afterSave(onSaveEvent);
	        return true;
    	}
    }

	private void showLastError() {
		String msg = CLogger.retrieveErrorString(null);
		if (msg != null)
		{
			statusBar.setStatusLine(Msg.getMsg(Env.getCtx(), msg), true, true);
		}
		//other error will be catch in the dataStatusChanged event
	}

    /**
     * @see ToolbarListener#onDelete()
     */
    @Override
	public void onDelete()
    {
        if (curTab.isReadOnly())
        {
            return;
        }

        if (FDialog.ask(curWindowNo, null, "DeleteRecord?"))
        {
        	//error will be catch in the dataStatusChanged event
            curTab.dataDelete();
        }
        curTabpanel.dynamicDisplay(0);
        focusToActivePanel();
    }

    // Elaine 2008/12/01
	/**
	 * @see ToolbarListener#onDelete()
	 */
    public void onDeleteSelection()
	{
		if (curTab.isReadOnly())
        {
            return;
        }

		//show table with deletion rows -> value, name...
		final Window messagePanel = new Window();
		messagePanel.setBorder("normal");
		messagePanel.setWidth("600px");
		messagePanel.setTitle(Msg.getMsg(Env.getCtx(), "Find").replaceAll("&", "") + ": " + title);
        messagePanel.setAttribute(Window.MODE_KEY, Window.MODE_MODAL);
        messagePanel.setClosable(true);
        messagePanel.setSizable(true);

		final Listbox listbox = new Listbox();
		listbox.setHeight("400px");

		Vector<String> data = new Vector<String>();
		// FR [ 2877111 ]
		final String keyColumnName = curTab.getKeyColumnName();
		String sql = null;
		if (! "".equals(keyColumnName)) {
			sql = MLookupFactory.getLookup_TableDirEmbed(Env.getLanguage(ctx), keyColumnName, "[?","?]")
			   .replace("[?.?]", "?");
		}
		int noOfRows = curTab.getRowCount();
		for(int i=0; i<noOfRows; i++)
		{
			StringBuffer displayValue = new StringBuffer();
			if ("".equals(keyColumnName))
			{
				final List<String> parentColumnNames = curTab.getParentColumnNames();
				for (Iterator<String> iter = parentColumnNames.iterator(); iter.hasNext();)
				{
					String columnName = iter.next();
					GridField field = curTab.getField(columnName);
					if(field.isLookup()){
						Lookup lookup = field.getLookup();
						if (lookup != null){
							displayValue = displayValue.append(lookup.getDisplay(curTab.getValue(i,columnName))).append(" | ");
						} else {
							displayValue = displayValue.append(curTab.getValue(i,columnName)).append(" | ");
						}
					} else {
						displayValue = displayValue.append(curTab.getValue(i,columnName)).append(" | ");
					}
				}
			} else {
				final int id = curTab.getKeyID(i);
				String value = DB.getSQLValueStringEx(null, sql, id);
				if (value != null)
					value = value.replace(" - ", " | ");
				displayValue.append(value);
				// Append ID
				if (displayValue.length() == 0 || CLogMgt.isLevelFine())
				{
					if (displayValue.length() > 0)
						displayValue.append(" | ");
					displayValue.append("<").append(id).append(">");
				}
			}
			//
			data.add(displayValue.toString());
		}
		// FR [ 2877111 ]

		for(int i = 0; i < data.size(); i++)
		{
			String record = data.get(i);
			listbox.appendItem(record, record);
		}

		listbox.setMultiple(true);
		messagePanel.appendChild(listbox);

		Div div = new Div();
		div.setAlign("center");
		messagePanel.appendChild(div);

		Hbox hbox = new Hbox();
		div.appendChild(hbox);

		Button btnOk = new Button();
		btnOk.setLabel(Util.cleanAmp(Msg.getMsg(Env.getCtx(), "OK")));
		btnOk.setImage("/images/Ok16.png");
		btnOk.addEventListener(Events.ON_CLICK, new EventListener()
		{
			@Override
			@SuppressWarnings("unchecked")
			public void onEvent(Event event) throws Exception
			{
				if (FDialog.ask(curWindowNo, messagePanel, "DeleteSelection"))
		        {
					logger.fine("ok");
					Set<Listitem> selectedValues = listbox.getSelectedItems();
					if(selectedValues != null)
					{
						for(Iterator<Listitem> iter = selectedValues.iterator(); iter.hasNext();)
						{
							Listitem li = iter.next();
							if(li != null)
								logger.fine((String) li.getValue());
						}
					}

					int[] indices = listbox.getSelectedIndices();
					Arrays.sort(indices);
					int offset = 0;
					for (int i = 0; i < indices.length; i++)
					{
						curTab.navigate(indices[i]-offset);
						if (curTab.dataDelete())
						{
							offset++;
						}
					}
					curTabpanel.dynamicDisplay(0);

		            messagePanel.dispose();
		        } else {
					logger.fine("cancel");
				}
			}
		});
		hbox.appendChild(btnOk);

		Button btnCancel = new Button();
		btnCancel.setLabel(Util.cleanAmp(Msg.getMsg(Env.getCtx(), "Cancel")));
		btnCancel.setImage("/images/Cancel16.png");
		btnCancel.addEventListener(Events.ON_CLICK, new EventListener()
		{
			@Override
			public void onEvent(Event event) throws Exception
			{
				messagePanel.dispose();
			}
		});
		hbox.appendChild(btnCancel);

		AEnv.showWindow(messagePanel);
		focusToActivePanel();
	}
	//

    /**
     * @see ToolbarListener#onPrint()
     */
	@Override
	public void onPrint() {
		//Get process defined for this tab
		int AD_Process_ID = curTab.getAD_Process_ID();
		//log.info("ID=" + AD_Process_ID);

		//	No report defined
		if (AD_Process_ID == 0)
		{
			onReport();

			return;
		}

		if (!onSave(false))
			return;
		//
		int table_ID = curTab.getAD_Table_ID();
		int record_ID = curTab.getRecord_ID();

		ProcessModalDialog dialog = new ProcessModalDialog(this,getWindowNo(),
				AD_Process_ID,table_ID, record_ID, true);
		if (dialog.isValid()) {
			dialog.setPosition("center");
			try {
				dialog.setPage(this.getComponent().getPage());
				dialog.doModal();
			}
			catch (InterruptedException e) {
			}
		}
	}

	/**
     * @see ToolbarListener#onReport()
     */
	@Override
	public void onReport() {
		if (!Env.getUserRolePermissions().isCanReport(curTab.getAD_Table_ID()))
		{
			FDialog.error(curWindowNo, parent, "AccessCannotReport");
			return;
		}

		if (!onSave(false))
			return;

		//	Query
		final MQuery query = curTab.createReportingQuery(); // 03917

		new WReport (curTab.getAD_Table_ID(), query, toolbar.getEvent().getTarget(), curWindowNo);
	}

	/**
     * @see ToolbarListener#onZoomAcross()
     */
	@Override
	public void onZoomAcross() {
		if (toolbar.getEvent() != null)
		{
			int record_ID = curTab.getRecord_ID();
			if (record_ID <= 0)
				return;

			//	Query
			MQuery query = new MQuery();
			//	Current row
			String link = curTab.getKeyColumnName();
			//	Link for detail records
			if (link.length() == 0)
				link = curTab.getLinkColumnName();
			if (link.length() != 0)
			{
				if (link.endsWith("_ID"))
					query.addRestriction(link, MQuery.EQUAL,
						new Integer(Env.getContextAsInt(ctx, curWindowNo, link)));
				else
					query.addRestriction(link, MQuery.EQUAL,
						Env.getContext(ctx, curWindowNo, link));
			}
			new WZoomAcross(toolbar.getEvent().getTarget(), curTab
					.getTableName(), curTab.getAD_Window_ID(), query);
		}
	}

	// Elaine 2008/07/17
	/**
     * @see ToolbarListener#onActiveWorkflows()
     */
	@Override
	public void onActiveWorkflows() {
		if (toolbar.getEvent() != null)
		{
			if (curTab.getRecord_ID() <= 0)
				return;
			else
				AEnv.startWorkflowProcess(curTab.getAD_Table_ID(), curTab.getRecord_ID());
		}
	}
	//

	// Elaine 2008/07/22
	/**
     * @see ToolbarListener#onRequests()
     */
	@Override
	public void onRequests()
	{
		if (toolbar.getEvent() != null)
		{
			if (curTab.getRecord_ID() <= 0)
				return;

			int C_BPartner_ID = 0;
			Object bpartner = curTab.getValue("C_BPartner_ID");
			if(bpartner != null)
				C_BPartner_ID = Integer.valueOf(bpartner.toString());

			new WRequest(toolbar.getEvent().getTarget(), curTab.getAD_Table_ID(), curTab.getRecord_ID(), C_BPartner_ID);
		}
	}
	//

	// Elaine 2008/07/22
	/**
     * @see ToolbarListener#onProductInfo()
     */
	@Override
	public void onProductInfo()
	{
		InfoPanel.showProduct(0);
	}
	//

	// Elaine 2008/07/28
	/**
     * @see ToolbarListener#onArchive()
     */
	@Override
	public void onArchive()
	{
		if (toolbar.getEvent() != null)
		{
			if (curTab.getRecord_ID() <= 0)
				return;

			new WArchive(toolbar.getEvent().getTarget(), curTab.getAD_Table_ID(), curTab.getRecord_ID());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.adempiere.webui.event.ToolbarListener#onExport()
	 */
	// 03917
    @Override
	public void onExport()
    {
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
		final GridTabExcelExporter exporter = new GridTabExcelExporter(ctx, curTab);
		
		try
		{
			exporter.export(out);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getLocalizedMessage(), e);
		}
		
		final String filename = curTab.getName() + ".xls";
		final String contentType = MimeType.getMimeType(filename);
		Filedownload.save(out.toByteArray(), contentType, filename);
    }

	//

	/**************************************************************************
	 *	Start Button Process
	 *  @param vButton button
	 */
	private void actionButton (WButtonEditor wButton)
	{
		if (curTab.hasChangedCurrentTabAndParents()) {
			String msg = CLogger.retrieveErrorString("Please ReQuery Window");
			FDialog.error(curWindowNo, parent, null, msg);
			return;
		}

		logger.info(wButton.toString());

		boolean startWOasking = false;
		String col = wButton.getColumnName();

		//  Zoom
		if (col.equals("Record_ID"))
		{
			int AD_Table_ID = Env.getContextAsInt (ctx, curWindowNo, "AD_Table_ID");
			int Record_ID = Env.getContextAsInt (ctx, curWindowNo, "Record_ID");

			AEnv.zoom(AD_Table_ID, Record_ID);
			return;
		} // Zoom

		//  save first	---------------

		if (curTab.needSave(true, false))
		{
			if (!onSave(false))
				return;
		}

		int table_ID = curTab.getAD_Table_ID();

		//	Record_ID

		int record_ID = curTab.getRecord_ID();

		//	Record_ID - Language Handling

		if (record_ID == -1 && curTab.getKeyColumnName().equals("AD_Language"))
			record_ID = Env.getContextAsInt (ctx, curWindowNo, "AD_Language_ID");

		//	Record_ID - Change Log ID

		if (record_ID == -1
			&& (wButton.getProcess_ID() == 306 || wButton.getProcess_ID() == 307))
		{
			Integer id = (Integer)curTab.getValue("AD_ChangeLog_ID");
			record_ID = id.intValue();
		}

		//	Ensure it's saved

		if (record_ID == -1 && curTab.getKeyColumnName().endsWith("_ID"))
		{
			FDialog.error(curWindowNo, parent, "SaveErrorRowNotFound");
			return;
		}

		boolean isProcessMandatory = false;

		//	Pop up Payment Rules

		if (col.equals("PaymentRule"))
		{
			WPayment vp = new WPayment(curWindowNo, curTab, wButton);


			if (vp.isInitOK())		//	may not be allowed
			{
				vp.setVisible(true);
				AEnv.showWindow(vp);
			}
			//vp.dispose();

			if (vp.needSave())
			{
				onSave(false);
				onRefresh();
			}
		} // PaymentRule

		//	Pop up Document Action (Workflow)

		else if (col.equals("DocAction"))
		{
			isProcessMandatory = true;
			WDocActionPanel win = new WDocActionPanel(curTab);
			if (win.getNumberOfOptions() == 0)
			{
				logger.info("DocAction - No Options");
				return;
			}
			else
			{
				AEnv.showWindow(win);

				if (!win.isStartProcess())
					return;

				//batch = win.isBatch();
				startWOasking = true;
				//vda.dispose();
			}
		} // DocAction

		//  Pop up Create From

		else if (col.equals("CreateFrom"))
		{
			ICreateFrom cf = WCreateFromFactory.create(curTab);

			if(cf != null)
			{
				if(cf.isInitOK())
				{
					cf.showWindow();
					curTab.dataRefresh();
				}
				return;
			}
			// else may start process
		} // CreateFrom

		//  Posting -----

		else if (col.equals("Posted") && Env.getUserRolePermissions().hasPermission(IUserRolePermissions.PERMISSION_ShowAcct))
		{
			//  Check Doc Status

			String processed = Env.getContext(ctx, curWindowNo, "Processed");

			if (!processed.equals("Y"))
			{
				String docStatus = Env.getContext(ctx, curWindowNo, "DocStatus");

				if (DocAction.STATUS_Completed.equals(docStatus)
					|| DocAction.STATUS_Closed.equals(docStatus)
					|| DocAction.STATUS_Reversed.equals(docStatus)
					|| DocAction.STATUS_Voided.equals(docStatus))
					;
				else
				{
					FDialog.error(curWindowNo, parent, "PostDocNotComplete");
					return;
				}
			}

			// try to get table and record id from context data (eg for unposted view)
			// otherwise use current table/record
			int tableId = Env.getContextAsInt(ctx, curWindowNo, "AD_Table_ID", true);
			int recordId = Env.getContextAsInt(ctx, curWindowNo, "Record_ID", true);
			if ( tableId == 0 || recordId == 0 )
			{
				tableId = curTab.getAD_Table_ID();
				recordId = curTab.getRecord_ID();
			}

			//  Check Post Status
			Object ps = curTab.getValue("Posted");

			if (ps != null && ps.equals("Y"))
			{
				new org.adempiere.webui.acct.WAcctViewer(Env.getContextAsInt (ctx, curWindowNo, "AD_Client_ID"),
						tableId, recordId);
			}
			else
			{
				if (FDialog.ask(curWindowNo, null, "PostImmediate?"))
				{
					boolean force = ps != null && !ps.equals ("N");		//	force when problems

					String error = AEnv.postImmediate (curWindowNo, Env.getAD_Client_ID(ctx),
						tableId, recordId, force);

					if (error != null)
						FDialog.error(curWindowNo, null, "PostingError-N", error);

					onRefresh();
				}
			}
			return;
		}   //  Posted

		/**
		 *  Start Process ----
		 */

		logger.config("Process_ID=" + wButton.getProcess_ID() + ", Record_ID=" + record_ID);

		if (wButton.getProcess_ID() == 0)
		{
			if (isProcessMandatory)
			{
				FDialog.error(curWindowNo, null, null, Msg.parseTranslation(ctx, "@NotFound@ @AD_Process_ID@"));
			}
			return;
		}

		//	Save item changed

		if (curTab.needSave(true, false))
		{
			if (!onSave(false))
				return;
		}

		// call form
		MProcess pr = new MProcess(ctx, wButton.getProcess_ID(), null);
		int adFormID = pr.getAD_Form_ID();
		if (adFormID != 0 )
		{
			String title = wButton.getDescription();
			if (title == null || title.length() == 0)
				title = wButton.getDisplay();
			ProcessInfo pi = new ProcessInfo (title, wButton.getProcess_ID(), table_ID, record_ID);
			pi.setAD_User_ID (Env.getAD_User_ID(ctx));
			pi.setAD_Client_ID (Env.getAD_Client_ID(ctx));
			ADForm form = ADForm.openForm(adFormID);
			form.setProcessInfo(pi);
			form.setAttribute(Window.MODE_KEY, Window.MODE_EMBEDDED);
			form.setAttribute(Window.INSERT_POSITION_KEY, Window.INSERT_NEXT);
			SessionManager.getAppDesktop().showWindow(form);
			onRefresh();
		}
		else
		{
			ProcessModalDialog dialog = new ProcessModalDialog(
					this, //aProcess,
					curWindowNo, //WindowNo,
					wButton.getProcess_ID(), //AD_Process_ID,
					table_ID, record_ID,
					startWOasking // autoStart
					);

			if (dialog.isValid())
			{
				dialog.setWidth("500px");
				dialog.setVisible(true);
				dialog.setPosition("center");
				AEnv.showWindow(dialog);
				onRefresh();
			}
		}
	} // actionButton

	/**
	 * @param event
	 * @see ActionListener#actionPerformed(ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event)
	{
		 if (event.getSource() instanceof WButtonEditor)
	     {
			String error = processButtonCallout((WButtonEditor)event.getSource());
			if (error != null && error.trim().length() > 0)
			{
				statusBar.setStatusLine(error, true);
				return;
			}
			actionButton((WButtonEditor)event.getSource());
	     }
	}

	/**************************************************************************
	 *  Process Callout(s).
	 *  <p>
	 *  The Callout is in the string of
	 *  "class.method;class.method;"
	 * If there is no class name, i.e. only a method name, the class is regarded
	 * as CalloutSystem.
	 * The class needs to comply with the Interface Callout.
	 *
	 * @param field field
	 * @return error message or ""
	 * @see org.compiere.model.Callout
	 */
	private String processButtonCallout (WButtonEditor button)
	{
		GridField field = curTab.getField(button.getColumnName());
		return curTab.processCallout(field);
	}	//	processButtonCallout

	/**
	 *
	 * @return IADTab
	 */
	public IADTab getADTab() {
		return adTab;
	}

	/**
	 * @param pi
	 */
	@Override
	public void executeASync(ProcessInfo pi) {
	}

	/**
	 * @return boolean
	 */
	@Override
	public boolean isUILocked() {
		return m_uiLocked;
	}

	/**
	 * @param pi
	 */
	@Override
	public void lockUI(ProcessInfo pi) {
		if (m_uiLocked) return;

		m_uiLocked = true;

		if (Executions.getCurrent() != null)
			Clients.showBusy(null, true);
		else
		{
			try {
				//get full control of desktop
				Executions.activate(getComponent().getDesktop(), 500);
				try {
					Clients.showBusy(null, true);
                } catch(Error ex){
                	throw ex;
                } finally{
                	//release full control of desktop
                	Executions.deactivate(getComponent().getDesktop());
                }
			} catch (Exception e) {
				logger.log(Level.WARNING, "Failed to lock UI.", e);
			}
		}
	}

	/**
	 * @param pi
	 */
	@Override
	public void unlockUI(ProcessInfo pi) {
		if (!m_uiLocked) return;

		m_uiLocked = false;
		boolean notPrint = pi != null
		&& pi.getAD_Process_ID() != curTab.getAD_Process_ID()
		&& pi.isReportingProcess() == false;
		//
		//  Process Result

		if (Executions.getCurrent() != null)
		{
			if (notPrint)		//	refresh if not print
			{
				updateUI(pi);
			}
			Clients.showBusy(null, false);
		}
		else
		{
			try {
				//get full control of desktop
				Executions.activate(getComponent().getDesktop(), 500);
				try {
					if (notPrint)		//	refresh if not print
					{
						updateUI(pi);
					}
                	Clients.showBusy(null, false);
                } catch(Error ex){
                	throw ex;
                } finally{
                	//release full control of desktop
                	Executions.deactivate(getComponent().getDesktop());
                }
			} catch (Exception e) {
				logger.log(Level.WARNING, "Failed to update UI upon unloc.", e);
			}
		}
	}

	private void updateUI(ProcessInfo pi) {
		//	Refresh data
		curTab.dataRefresh();
		//	Timeout
		if (pi.isTimeout())		//	set temporarily to R/O
			Env.setContext(ctx, curWindowNo, "Processed", "Y");
		curTabpanel.dynamicDisplay(0);
		//	Update Status Line
		String summary = pi.getSummary();
		if (summary != null && summary.indexOf('@') != -1)
			pi.setSummary(Msg.parseTranslation(Env.getCtx(), summary));
		statusBar.setStatusLine(pi.getSummary(), pi.isError(), true);
		//	Get Log Info
		ProcessInfoUtil.setLogFromDB(pi);
		String logInfo = pi.getLogInfo();
		if (logInfo.length() > 0)
			FDialog.info(curWindowNo, this.getComponent(), Env.getHeader(ctx, curWindowNo),
				pi.getTitle() + "<br>" + logInfo);
	}

	/**
	 *
	 * @return toolbar instance
	 */
	public CWindowToolbar getToolbar() {
		return toolbar;
	}

	/**
	 * @return active grid tab
	 */
	public GridTab getActiveGridTab() {
		return curTab;
	}

	/**
	 * @return windowNo
	 */
	public int getWindowNo() {
		return curWindowNo;
	}

// metas: begin
	public GridWindow getGridWindow()
	{
		return gridWindow;
	}

	public static final String EVENT_OnWindowClose = "onWindowClose"; // metas

	public void addEventListener(String evtnm, EventListener listener)
	{
		final Component c = getComponent();
		if (c == null)
		{
			logger.log(Level.WARNING, "Cannot register listener {0} on event {1}", new Object[] { listener, evtnm });
			return;
		}
		c.addEventListener(evtnm, listener);
	}
	// metas: end
}
