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
package org.compiere.grid;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.images.Images;
import org.compiere.apps.ADialog;
import org.compiere.apps.APanel;
import org.compiere.model.DataStatusEvent;
import org.compiere.model.GridTab;
import org.compiere.swing.CTabbedPane;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *  Tabbed Pane - either Workbench or Window Tab
 *
 *  @author Jorg Janke
 *  @version $Id: VTabbedPane.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 */
public class VTabbedPane extends CTabbedPane
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 716455372513747084L;

	/**
	 *  Constructor
	 *  @param isWorkbench is this a workbench tab (tabs on the left side)
	 */
	public VTabbedPane (final boolean isWorkbench)
	{
		super();
		setTabLayoutPolicy (JTabbedPane.SCROLL_TAB_LAYOUT);
		setWorkbench (isWorkbench);
		setFocusable(false);
	}   //  VTabbedPane

	/**	Logger					*/
	private static final Logger log = LogManager.getLogger(VTabbedPane.class);
	/** Workbench 				*/
	private boolean			m_workbenchTab = false;
	/** List of dependent Variables		*/
	private Set<String>	m_dependents = new HashSet<>();
	/** Disabled Icon			*/
	private static final Icon s_disabledIcon = Images.getImageIcon2("Cancel10");
	
	private List<Component> components = new ArrayList<>();
	private List<GridTab> gTabs = new ArrayList<>();
	private List<String> tabNames = new ArrayList<>();
	
	/**
	 *  toString
	 *  @return info
	 */
	@Override
	public String toString()
	{
		return (m_workbenchTab ? "WorkbenchTab" : "WindowTab") + " - selected " + getSelectedIndex() + " of " + getTabCount();
	}   //  toString

	/**
	 * 	Add Tab
	 *	@param tabName name
	 *	@param gTab grid tab model
	 *	@param tabComponent GridController or VSortTab
	 */
	public void addTab(final String tabName, final GridTab gTab, final Component tabComponent)
	{
		final int index = getTabCount();
		tabNames.add(tabName);
		gTabs.add(gTab);
		components.add(tabComponent);
		m_dependents.addAll(gTab.getDependentOn());
		
		super.addTab (tabName, gTab.getIcon(), tabComponent, gTab.getDescription());
		
		setDisabledIconAt(index, s_disabledIcon);
	}	//	addTab
	
	private void hideTab(final String tabName)
	{	
		int tabIndex = indexOfTab(tabName);
		
		if ( tabIndex != -1 )
			removeTabAt(tabIndex);
	}

	private void showTab(String tabName)
	{
		int insertAt = 0;
		
		if ( indexOfTab(tabName) != -1 )  // already visible
			return;
		
		for ( int i = 0; i < tabNames.size(); i++ )
		{
			String name = tabNames.get(i);
			if ( name.equals(tabName))
			{
				insertTab (tabName, gTabs.get(i).getIcon(), 
						components.get(i), gTabs.get(i).getDescription(), insertAt);
				break;
			}
			
			if ( indexOfTab(name) != -1 )  // tab is visible, insert after
				insertAt ++;
		}
		
	}

	/**
	 * @param gridTab
	 * @return tab index or -1 if not found
	 */
	public int findTabindex(final GridTab gridTab) 
	{
		for (int i = 0; i < gTabs.size(); i++) 
		{
			if (gTabs.get(i) == gridTab)
			{
				return indexOfTab(tabNames.get(i));
			}
		}
		return -1;
	}
	
	/**
	 *  Set Workbench - or Window
	 *  @param isWorkbench
	 */
	public void setWorkbench (final boolean isWorkbench)
	{
		m_workbenchTab = isWorkbench;
		if (m_workbenchTab)
		{
			super.setTabPlacement(JTabbedPane.BOTTOM);
		}
		else
		{
			final Language language = Env.getLanguage(Env.getCtx());
			final boolean isLeftToRight = language == null ? true : language.isLeftToRight();
			super.setTabPlacement(isLeftToRight  ? JTabbedPane.LEFT : JTabbedPane.RIGHT);
		}
	}   //  setWorkbench

	/**
	 *  Tab is Workbench (not Window)
	 *  @return true if Workbench
	 */
	public boolean isWorkbench()
	{
		return m_workbenchTab;
	}   //  toString

	/**
	 * Set Tab Placement. Do not use - set via {@link #setWorkbench(boolean)}.
	 * 
	 * @param notUsed
	 */
	@Override
	public final void setTabPlacement (final int notUsed)
	{
		new java.lang.IllegalAccessError("Do not use VTabbedPane.setTabPlacement directly");
	}   //  setTabPlacement

	/**
	 *  Dispose all contained VTabbedPanes and GridControllers
	 *  @param aPanel
	 */
	public void dispose (final APanel aPanel)
	{
		final Component[] comp = getComponents();
		for (int i = 0; i < comp.length; i++)
		{
			if (comp[i] instanceof VTabbedPane)
			{
				final VTabbedPane tp = (VTabbedPane)comp[i];
				tp.removeChangeListener(aPanel);
				
				if (tp != this)
				{
					tp.dispose(aPanel);
				}
			}
			else if (comp[i] instanceof GridController)
			{
				final GridController gc = (GridController)comp[i];
				gc.removeDataStatusListener(aPanel);
				gc.dispose();
			}
		}
		removeAll();
	}   //  dispose

	@Override
	//hengsin, bug [ 1637763 ]
	public boolean isEnabledAt(final int index)
	{
		boolean enabled = super.isEnabledAt(index); 
		if (!enabled) return enabled;
		Component comp = getComponentAt(index);
		GridController gc = null;
		if (comp instanceof GridController)
			gc = (GridController)comp;
		//	Display
		if (gc != null)
		{
			enabled = isDisplay(gc);
		}
		return enabled;
	}

	//hengsin, bug [ 1637763 ]
	private static boolean isDisplay(GridController gc)
	{
		final ILogicExpression displayLogic = gc.getDisplayLogic();
		final boolean display = displayLogic.evaluate(gc, true); // ignoreUnparsable=true
		if (!display)
		{
			log.info("Not displayed: {}", displayLogic);
			return false;
		}
		return true;
	}
	
	/**
	 * 	Set Selected Index.
	 * 	Register/Unregister Mnemonics
	 *	@param index index
	 */
	@Override
	public void setSelectedIndex (final int index)
	{
		final Component newComp = getComponentAt(index);
		final GridController newGC;
		final Integer newTabLevel;
		if (newComp instanceof GridController)
		{
			newGC = (GridController)newComp;
			newTabLevel = ((GridController)newComp).getTabLevel();
		}
		else if (newComp instanceof VSortTab)
		{
			newGC = null;
			newTabLevel = ((VSortTab)newComp).getTabLevel();
		}
		else
		{
			newGC = null;
			newTabLevel = null;
		}
		
		//	Display
		if (newGC != null)
		{
			//hengsin, bug [ 1637763 ]
			if(isDisplay(newGC) == false)
			{
				return;
			}
		}

		//
		final int oldIndex = getSelectedIndex();
		if (newTabLevel != null && oldIndex >= 0 && index != oldIndex)
		{
			final Component oldC = getComponentAt(oldIndex);
			if (oldC != null && oldC instanceof GridController)
			{
				final GridController oldGC = (GridController)oldC;
				if (newTabLevel > oldGC.getTabLevel()+1)
				{
					//  validate
					//	Search for right tab
					GridController rightGC = null;
					boolean canJump = true;
					int currentLevel = newTabLevel;
					for (int i = index-1; i >=0; i--)
					{
						final Component rightC = getComponentAt(i);
						if (rightC instanceof GridController)
						{
							GridController gc = (GridController)rightC;
							//can only skip level if all parent data are not stale
							if (gc.getTabLevel() < currentLevel)
							{
								if (gc.getTabLevel() == oldGC.getTabLevel()+1)
								{
									rightGC = gc;
								}							
								if (!gc.isCurrent())
									canJump = false;
								currentLevel = gc.getTabLevel();
							}
						}
					}
					if (canJump == false)
					{
						if (rightGC != null )
							ADialog.warn(0, this, "TabSwitchJumpGo", rightGC.getTitle());
						else
							ADialog.warn(0, this, "TabSwitchJump");
						return;
					}
				}
				oldGC.setMnemonics(false);
			}
		}
		//	Switch
		super.setSelectedIndex (index);
		if (newGC != null)
		{
			newGC.setMnemonics(true);
		}
	}	//	setSelectedIndex

	/**
	 * 	Evaluate Tab Logic
	 *	@param e event
	 */
	public void evaluate (final DataStatusEvent e)
	{
		boolean process = e == null;
		String columnName = null;
		if (!process)
		{
			columnName = e.getColumnName();
			if (columnName != null)
				process = m_dependents.contains(columnName);
			else
				process = true;
		}
			
		if (process)
		{
			log.info(columnName == null ? "" : columnName);
			for (int i = 0; i < components.size(); i++)
			{
				Component c = components.get(i);
				if (c instanceof GridController)
				{
					GridController gc = (GridController)c;
					if (!gc.isDetailGrid())  //ignore included tabs
					{
						boolean display = isDisplay(gc);
						if ( display )
							showTab(tabNames.get(i));
						else
							hideTab(tabNames.get(i));
					}
				}
			}
		}
	}	//	evaluate
	
}   //  VTabbdPane
