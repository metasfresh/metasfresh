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

package org.adempiere.webui.component;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.panel.IADTabpanel;
import org.compiere.model.DataStatusEvent;
import org.compiere.model.GridTab;
import org.compiere.util.CLogger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Div;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @author <a href="mailto:hengsin@gmail.com">Low Heng Sin</a>
 * @date    Feb 25, 2007
 * @version $Revision: 0.10 $
 */
public class CompositeADTab extends AbstractADTab
{
    /** Logger                  */
    @SuppressWarnings("unused")
	private static CLogger  log = CLogger.getCLogger (CompositeADTab.class);

    private List<ADTabListModel.ADTabLabel> tabLabelList = new ArrayList<ADTabListModel.ADTabLabel>();

    private Div div;

	protected ADButtonTabList tabList;

    public CompositeADTab()
    {
    	tabList = new ADButtonTabList();
    	tabList.setADTab(this);
    }

    protected Component doCreatePart(Component parent)
    {
    	div= new Div();
    	if (parent != null) {
    		div.setParent(parent);
    		LayoutUtils.addSclass("adtab-body", div);
    	} else {
    		div.setPage(page);
    	}
    	return div;
    }

    @Override
	protected void doAddTab(GridTab gTab, IADTabpanel tabPanel) {
    	tabPanel.setParent(div);
        tabPanel.setVisible(false);

        ADTabListModel.ADTabLabel tabLabel = new ADTabListModel.ADTabLabel(gTab.getName(), gTab.getTabLevel(),gTab.getDescription());
        tabLabelList.add(tabLabel);
	}

    /**
     *  Evaluate Tab Logic
     *  @param e event
     */
    public void evaluate (DataStatusEvent e)
    {
    	tabList.setItems(tabLabelList);

        super.evaluate(e);

    } //  evaluate

    @Override
	protected void updateTabState() {
    	tabList.refresh();
	}

    /**
     * Return the selected Tab Panel
     */
    public IADTabpanel getSelectedTabpanel()
    {
        for(int i = 0; i < tabPanelList.size(); i++) {
        	if (tabPanelList.get(i).isVisible())
        		return tabPanelList.get(i);
        }
        return null;
    }

    public int getSelectedIndex() {
    	for(int i = 0; i < tabPanelList.size(); i++) {
        	if (tabPanelList.get(i).isVisible())
        		return i;
        }
    	return -1;
    }

	public void addSelectionEventListener(EventListener listener) {
		tabList.addSelectionEventListener(listener);
	}

	public Component getTabSelectionComponent() {
		return tabList;
	}

	@Override
	protected void doTabSelectionChanged(int oldIndex, int newIndex) {
		IADTabpanel oldTabpanel = oldIndex >= 0 ? tabPanelList.get(oldIndex) : null;
        IADTabpanel newTabpanel = tabPanelList.get(newIndex);
        if (oldTabpanel != null) {
        	oldTabpanel.setVisible(false);
        }
        newTabpanel.createUI();
        newTabpanel.setVisible(true);

        if (tabList.getSelectedIndex() != newIndex)
        	tabList.setSelectedIndex(newIndex);
	}

	public boolean isUseExternalSelection() {
		return true;
	}

	public Component getComponent() {
		return div;
	}

	/**
	 * @param tabPlacement
	 */
	public void setTabplacement(int tabPlacement) {
		tabList.setTabplacement(tabPlacement);
	}

	@Override
	public IADTabpanel findADTabpanel(GridTab gTab) {
		for (IADTabpanel tabpanel : tabPanelList) {
			if (tabpanel.getGridTab() == gTab) {
				return tabpanel;
			}
		}
		return null;
	}
}
