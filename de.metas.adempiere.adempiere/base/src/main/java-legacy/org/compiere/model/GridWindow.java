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
package org.compiere.model;

import java.awt.Dimension;
import java.awt.Image;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.Icon;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IdentityHashSet;
import org.apache.ecs.xhtml.a;
import org.apache.ecs.xhtml.h2;
import org.apache.ecs.xhtml.h3;
import org.apache.ecs.xhtml.h4;
import org.apache.ecs.xhtml.i;
import org.apache.ecs.xhtml.p;
import org.apache.ecs.xhtml.strong;
import org.apache.ecs.xhtml.table;
import org.apache.ecs.xhtml.td;
import org.apache.ecs.xhtml.th;
import org.apache.ecs.xhtml.tr;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.WebDoc;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;
import de.metas.util.IColorRepository;
import de.metas.util.MFColor;

/**
 *	Window Model
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: GridWindow.java,v 1.4 2006/07/30 00:51:02 jjanke Exp $
 */
public class GridWindow implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3342733142743698614L;

	/**
	 * 	Get Grid Window
	 *  @param ctx context
	 *  @param WindowNo window no for ctx
	 *  @param AD_Window_ID window id
	 *	@return window or null if not found
	 */
	public static GridWindow get (Properties ctx, int WindowNo, int AD_Window_ID)
	{
		return get(ctx, WindowNo, AD_Window_ID, false);
	}
	
	/**
	 * 	Get Grid Window
	 *  @param ctx context
	 *  @param WindowNo window no for ctx
	 *  @param AD_Window_ID window id
	 *  @param virtual
	 *	@return window or null if not found
	 */
	public static GridWindow get (Properties ctx, int WindowNo, int AD_Window_ID, boolean virtual)
	{
		log.debug("Window={}, AD_Window_ID={}", WindowNo, AD_Window_ID);
		GridWindowVO mWindowVO = GridWindowVO.create (ctx, WindowNo, AD_Window_ID);
		if (mWindowVO == null)
			return null;
		return new GridWindow(mWindowVO, virtual);
	}	//	get

	/**************************************************************************
	 *	Constructor
	 *  @param vo value object
	 */
	public GridWindow (GridWindowVO vo)
	{
		this(vo, false);
	}
	
	/**************************************************************************
	 *	Constructor
	 *  @param vo value object
	 *  @param virtual
	 */
	public GridWindow (GridWindowVO vo, boolean virtual)
	{
		m_vo = vo;
		m_virtual = virtual;
		if (loadTabData())
			enableEvents();
	}	//	MWindow

	/** Value Object                */
	private GridWindowVO   	m_vo;
	/** use virtual table			*/
	private boolean m_virtual;
	/**	Tabs						*/
	private ArrayList<GridTab>	m_tabs = new ArrayList<>();
	/** Model last updated			*/
	private Timestamp		m_modelUpdated = null;

	/**
	 * Set of {@link GridTab}s which were already initialized by {@link #initTab(int)} methods.
	 */
	private Set<GridTab> initTabs = new IdentityHashSet<>();
	
	/**	Logger			*/
	private static final Logger log = LogManager.getLogger(GridWindow.class);
	
	/**************************************************************************
	 *	Dispose
	 */
	public void dispose()
	{
		log.debug("AD_Window_ID={}", m_vo.getAD_Window_ID());
		for (int i = 0; i < getTabCount(); i++)
			getTab(i).dispose();
		m_tabs.clear();
		m_tabs = null;
	}	//	dispose

	/**
	 *	Get Tab data and create MTab(s)
	 *  @return true if tab loaded
	 */
	private boolean loadTabData()
	{
		final List<GridTabVO> tabVOs = m_vo.getTabs();
		if (tabVOs == null || tabVOs.isEmpty())
		{
			return false;
		}

		for (final GridTabVO mTabVO : tabVOs)
		{
			if(mTabVO == null)
			{
				// shall not happen
				continue;
			}
			
			final GridTab mTab = new GridTab(mTabVO, this, m_virtual);
			m_tabs.add(mTab);
		}	//  for all tabs
		return true;
	}	//	loadTabData

	/**
	 * Is tab initialize
	 * @param index
	 * @return boolean
	 */
	public boolean isTabInitialized(int index)
	{
		GridTab mTab = m_tabs.get(index);
		return initTabs.contains(mTab);
	}
	
	/**
	 * Initialise tab and all it's included tabs.
	 * 
	 * If tab was already initialized (see {@link #isTabInitialized(int)}), it won't be initialized again.
	 * 
	 * @param index tab index
	 */
	public void initTab(final int index)
	{
		//
		// Check if tab was already initialized
		final GridTab mTab = m_tabs.get(index);
		if (initTabs.contains(mTab))
		{
			return;		
		}
		
		//
		// Init tab
		mTab.initTab(false); // async=false
		
		//
		// Set Link Column
		if (mTab.getLinkColumnName().length() == 0)
		{
			final List<String> parents = mTab.getParentColumnNames();
			//	No Parent - no link
			if (parents.size() == 0)
			{
				;
			}
			//	Standard case
			else if (parents.size() == 1)
			{
				mTab.setLinkColumnName(parents.get(0));
			}
			//	More than one parent.
			else
			{
				//	Search prior tabs for the "right parent"
				//	for all previous tabs
				for (int i = 0; i < index; i++)
				{
					//	we have a tab
					final GridTab tab = m_tabs.get(i);
					String tabKey = tab.getKeyColumnName();		//	may be ""
					//	look, if one of our parents is the key of that tab
					for (int j = 0; j < parents.size(); j++)
					{
						String parent = parents.get(j);
						if (parent.equals(tabKey))
						{
							mTab.setLinkColumnName(parent);
							break;
						}
						//	The tab could have more than one key, look into their parents
						if (tabKey.equals(""))
							for (int k = 0; k < tab.getParentColumnNames().size(); k++)
								if (parent.equals(tab.getParentColumnNames().get(k)))
								{
									mTab.setLinkColumnName(parent);
									break;
								}
					}	//	for all parents
				}	//	for all previous tabs
			}	//	parents.size > 1
		}	//	set Link column
		
		// FIXME (metas-tsa): why we do this?
		mTab.setLinkColumnName(null);	//	overwrites, if AD_Column_ID exists
		
		//
		// Add this tab to the list of initialized tabs
		initTabs.add(mTab);
		
		//
		// Recursivelly init all included tabs
		final List<GridTab> includedTabs = mTab.getIncludedTabs();
		for(final GridTab includedTab : includedTabs)
		{
			final int includedTabIndex = m_tabs.indexOf(includedTab);
			if (includedTabIndex < 0)
			{
				// shall not happen
				final AdempiereException ex = new AdempiereException("Included tab not found in the list of window tabs."
						+"\n Included tab: "+includedTab
						+"\n Parent tab: "+mTab
						+"\n Window: "+this
						+"\n Window tabs: "+m_tabs);
				log.warn(ex.getLocalizedMessage(), ex);
				continue;
			}
			
			initTab(includedTabIndex);
		}
	}
	
	/**
	 *  Get Window Icon
	 *  @return Icon for Window
	 */
	public Image getImage()
	{
		if (m_vo.getAD_Image_ID() <= 0)
			return null;
		//
		MImage mImage = MImage.get(Env.getCtx(), m_vo.getAD_Image_ID());
		return mImage.getImage();
	}   //  getImage

	/**
	 *  Get Window Icon
	 *  @return Icon for Window
	 */
	public Icon getIcon()
	{
		if (m_vo.getAD_Image_ID() <= 0)
			return null;
		//
		MImage mImage = MImage.get(Env.getCtx(), m_vo.getAD_Image_ID());
		return mImage.getIcon();
	}   //  getIcon

	/**
	 *  Get Color
	 *  @return AdempiereColor or null
	 */
	public MFColor getColor()
	{
		final int adColorId = m_vo.getAD_Color_ID();
		return adColorId > 0 ? Services.get(IColorRepository.class).getColorById(adColorId) : null;
	}   //  getColor

	/**
	 * 	SO Trx Window
	 *	@return true if SO Trx
	 */
	public boolean isSOTrx()
	{
		return m_vo.isSOTrx();
	}	//	isSOTrx
	
	
	/**
	 *  Open and query first Tab (events should be enabled) and get first row.
	 */
	public void query()
	{
		log.info("query");
		GridTab tab = getTab(0);
		tab.query(false, 0, GridTabMaxRows.NO_RESTRICTION);
		if (tab.getRowCount() > 0)
			tab.navigate(0);
	}   //  open

	/**
	 *  Enable Events - enable data events of tabs (add listeners)
	 */
	private void enableEvents()
	{
		for (int i = 0; i < getTabCount(); i++)
			getTab(i).enableEvents();
	}   //  enableEvents

	/**
	 *	Get number of Tabs
	 *  @return number of tabs
	 */
	public int getTabCount()
	{
		return m_tabs.size();
	}	//	getTabCount

	/**
	 *	Get i-th MTab - null if not valid
	 *  @param i index
	 *  @return MTab
	 */
	public GridTab getTab (int i)
	{
		if (i < 0 || i+1 > m_tabs.size())
			return null;
		return m_tabs.get(i);
	}	//	getTab
	
	public int getTabIndex(GridTab tab)
	{
		return m_tabs.indexOf(tab);
	}

	/**
	 *	Get Window_ID
	 *  @return AD_Window_ID
	 */
	public int getAD_Window_ID()
	{
		return m_vo.getAD_Window_ID();
	}	//	getAD_Window_ID

	/**
	 *	Get WindowNo
	 *  @return WindowNo
	 */
	public int getWindowNo()
	{
		return m_vo.getWindowNo();
	}	//	getWindowNo

	/**
	 *	Get Name
	 *  @return name
	 */
	public String getName()
	{
		return m_vo.getName();
	}	//	getName

	/**
	 *	Get Description
	 *  @return Description
	 */
	public String getDescription()
	{
		return m_vo.getDescription();
	}	//	getDescription

	/**
	 *	Get Help
	 *  @return Help
	 */
	public String getHelp()
	{
		return m_vo.getHelp();
	}	//	getHelp

	/**
	 *	Get Window Type
	 *  @return Window Type see WindowType_*
	 */
	public String getWindowType()
	{
		return m_vo.getWindowType();
	}	//	getWindowType

	/**
	 *	Is Transaction Window
	 *  @return true if transaction
	 */
	public boolean isTransaction()
	{
		return GridWindowVO.WINDOWTYPE_TRX.equals(m_vo.getWindowType());
	}   //	isTransaction

	/**
	 * 	Get Window Size
	 *	@return window size or null if not set
	 */
	public Dimension getWindowSize()
	{
		final int winWidth = m_vo.getWinWidth();
		final int winHeight = m_vo.getWinHeight();
		if (winWidth != 0 && winHeight != 0)
			return new Dimension (winWidth, winHeight);
		return null;
	}	//	getWindowSize

	/**
	 *  To String
	 *  @return String representation
	 */
	@Override
	public String toString()
	{
		return "MWindow[" + m_vo.getWindowNo() + "," + m_vo.getName() + " (" + m_vo.getAD_Window_ID() + ")]";
	}   //  toString

	/**
	 * Get Help HTML Document
	 *
	 * @return help doc
	 */
	public WebDoc getHelpDoc()
	{
		final String title = Msg.getMsg(Env.getCtx(), "Window") + ": " + getName();
		final WebDoc doc = WebDoc.create (false, title, true);
		
	//	body.addElement("&copy;&nbsp;Adempiere &nbsp; ");
	//	body.addElement(new a("http://www.adempiere.org/help/", "Online Help"));
		final td center  = doc.addPopupCenter(false);
		
		//
		// Window
		if (getDescription().length() != 0)
			center.addElement(new p().addElement(new i(getDescription())));
		if (getHelp().length() != 0)
			center.addElement(new p().addElement(getHelp()));


		//
		// List of all Tabs in current window
		final int tabsCount = getTabCount();
		{
			center.addElement(new a().setName("Tabs")).addElement(new h3("Tabs").addAttribute("ALIGN", "left"));
			p p = new p();
			for (int tabIndex = 0; tabIndex < tabsCount; tabIndex++)
			{
				GridTab tab = getTab(tabIndex);
				if (tabIndex > 0)
					p.addElement(" | ");
				p.addElement(new a("#Tab" + tabIndex).addElement(tab.getName()));
			}
			center.addElement(p)
					.addElement(new p().addElement(WebDoc.NBSP));
		}

		//	For all Tabs
		for (int tabIndex = 0; tabIndex < tabsCount; tabIndex++)
		{
			final GridTab tab = getTab(tabIndex);

			final table table = new table("1", "5", "5", "100%", null);
			table.setBorder("1px").setCellSpacing(0);
			
			final table tabHeader = new table();
			tabHeader.setBorder("0").setCellPadding(0).setCellSpacing(0);
			tabHeader.addElement(new tr()
					.addElement(new td()
							.addElement(new a().setName("Tab" + tabIndex))
							.addElement(new h2(Msg.getMsg(Env.getCtx(), "Tab") + ": " + tab.getName())))
					.addElement(new td()
							.addElement(WebDoc.NBSP)
							.addElement(WebDoc.NBSP)
							.addElement(new a("#Tabs")
									.addElement("..")
									.addAttribute("title", "Up one level"))));

			// Tab description
			{
				final tr tr = new tr()
						.addElement(new th()
								.addElement(tabHeader));

				final String tabDescription = tab.getDescription();
				if (!Check.isEmpty(tabDescription, true))
					tr.addElement(new th()
							.addElement(new i(tabDescription)));
				else
					tr.addElement(new th()
							.addElement(WebDoc.NBSP));
				table.addElement(tr);
			}
			
			// Tab help
			final td tdTabContent = new td().setColSpan(2);
			table.addElement(new tr().addElement(tdTabContent));
			{
				final String tabHelp = tab.getHelp();
				if (!Check.isEmpty(tabHelp, true))
					tdTabContent.addElement(new p().addElement(tabHelp));
			}
			
			//
			//	Links to Fields
			{
				tdTabContent.addElement(new a().setName("Fields" + tabIndex));
				tdTabContent.addElement(new h4("Fields").addAttribute("ALIGN", "left"));
				final p p = new p();
				if (!tab.isLoadComplete())
					this.initTab(tabIndex);
				for (int fieldIndex = 0; fieldIndex < tab.getFieldCount(); fieldIndex++)
				{
					final GridField field = tab.getField(fieldIndex);
					// hidden fields should not be displayed - teo_sarca, [ 1667073 ]
					if (!field.isDisplayed(false))
					{
						continue;
					}
					
					String hdr = field.getHeader();
					if (hdr != null && hdr.length() > 0)
					{
						if (fieldIndex > 0)
							p.addElement(" | ");
						p.addElement(new a("#Field" + tabIndex + "-" + fieldIndex, hdr));
					}
				}
				tdTabContent.addElement(p);
			}

			//
			//	For all Fields
			for (int j = 0; j < tab.getFieldCount(); j++)
			{
				GridField field = tab.getField(j);
				// hidden fields should not be displayed - teo_sarca, [ 1667073 ] 
				if (!field.isDisplayed(false)) {
					continue;
				}
				String hdr = field.getHeader();
				if (hdr != null && hdr.length() > 0)
				{
					table fieldHeader = new table();
					fieldHeader.setBorder("0").setCellPadding(0).setCellSpacing(0);
					fieldHeader.addElement(new tr()
					.addElement(new td().addElement(new a().setName("Field" + tabIndex + "-" + j))
						.addElement(new h3(Msg.getMsg(Env.getCtx(), "Field") + ": " + hdr)))
					.addElement(new td().addElement(WebDoc.NBSP).addElement(WebDoc.NBSP)
						.addElement(new strong().addElement(new a("#Fields"+tabIndex).addElement("..").addAttribute("title", "Up one level")))));
					
					final td td = new td().setColSpan(2).addElement(fieldHeader);
						
					if (field.getDescription().length() != 0)
						td.addElement(new i(field.getDescription()));
					//
					if (field.getHelp().length() != 0)
						td.addElement(new p().addElement(field.getHelp()));
					table.addElement(new tr().addElement(td));
				}
			}	//	for all Fields
			
			center.addElement(table);
			center.addElement(new p().addElement(WebDoc.NBSP));
		}	//	for all Tabs
		
		return doc;
	}	//	getHelpDoc

	/**
	 * 	Get Model last Updated
	 * 	@param recalc recalculate again
	 *	@return date
	 */
	public Timestamp getModelUpdated (boolean recalc)
	{
		if (recalc || m_modelUpdated == null)
		{
			String sql = "SELECT MAX(w.Updated), MAX(t.Updated), MAX(tt.Updated), MAX(f.Updated), MAX(c.Updated) "
				+ "FROM AD_Window w"
				+ " INNER JOIN AD_Tab t ON (w.AD_Window_ID=t.AD_Window_ID)"
				+ " INNER JOIN AD_Table tt ON (t.AD_Table_ID=tt.AD_Table_ID)"
				+ " INNER JOIN AD_Field f ON (t.AD_Tab_ID=f.AD_Tab_ID)"
				+ " INNER JOIN AD_Column c ON (f.AD_Column_ID=c.AD_Column_ID) "
				+ "WHERE w.AD_Window_ID=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement (sql, null);
				pstmt.setInt (1, getAD_Window_ID());
				rs = pstmt.executeQuery ();
				if (rs.next ())
				{
					m_modelUpdated = rs.getTimestamp(1);	//	Window
					Timestamp ts = rs.getTimestamp(2);		//	Tab
					if (ts.after(m_modelUpdated))
						m_modelUpdated = ts;
					ts = rs.getTimestamp(3);				//	Table
					if (ts.after(m_modelUpdated))
						m_modelUpdated = ts;
					ts = rs.getTimestamp(4);				//	Field
					if (ts.after(m_modelUpdated))
						m_modelUpdated = ts;
					ts = rs.getTimestamp(5);				//	Column
					if (ts.after(m_modelUpdated))
						m_modelUpdated = ts;
				}
			}
			catch (Exception e)
			{
				log.error(sql, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}
		}
		return m_modelUpdated;
	}	//	getModelUpdated

// metas: begin:
	public GridWindowVO getVO()
	{
		return m_vo;
	}
// metas: end
}	//	MWindow
