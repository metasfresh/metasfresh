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

import java.util.TreeMap;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.AutoComplete;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.util.DocumentSearch;
import org.adempiere.webui.util.TreeItemAction;
import org.adempiere.webui.util.TreeNodeAction;
import org.adempiere.webui.util.TreeUtils;
import org.compiere.model.MTreeNode;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Div;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.event.TreeDataEvent;
import org.zkoss.zul.event.TreeDataListener;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Mar 3, 2007
 * @version $Revision: 0.10 $
 */
public class TreeSearchPanel extends Panel implements EventListener, TreeDataListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1788127438140771622L;
	private TreeMap<String, Object> treeNodeItemMap = new TreeMap<String, Object>();
    private String[] treeValues;
    private String[] treeDescription;

    private Label lblSearch;
    private AutoComplete cmbSearch;

	private Tree tree;

	private String eventToFire;

	private static final String PREFIX_DOCUMENT_SEARCH = "/";

	/**
     * @param tree
     */
    public TreeSearchPanel(Tree tree)
    {
    	this(tree, Events.ON_CLICK);
    }

    /**
     * @param tree
     * @param event
     */
    public TreeSearchPanel(Tree tree, String event)
    {
        super();
        this.tree = tree;
        this.eventToFire = event;
        init();
    }

    private void init()
    {
    	Div div = new Div();
        lblSearch = new Label();
        lblSearch.setValue(Msg.getMsg(Env.getCtx(),"TreeSearch").replaceAll("&", "") + ":");
        lblSearch.setTooltiptext(Msg.getMsg(Env.getCtx(),"TreeSearchText"));
        div.appendChild(lblSearch);
        String divStyle = "height: 20px; vertical-align: middle;";
        if (!AEnv.isInternetExplorer())
        {
        	divStyle += "margin-bottom: 10px; display: inline-block;";
        }
        div.setStyle(divStyle);

        cmbSearch = new AutoComplete();
        cmbSearch.setAutodrop(true);
        cmbSearch.addEventListener(Events.ON_CHANGE, this);
        if (AEnv.isInternetExplorer())
        {
        	cmbSearch.setWidth("200px");
        }

        this.appendChild(div);
        this.appendChild(cmbSearch);
        if (!AEnv.isInternetExplorer())
        {
        	this.setStyle("height: 20px;");
    	}
    }

    private void addTreeItem(Treeitem treeItem)
    {
        String key = treeItem.getLabel();
        treeNodeItemMap.put(key, treeItem);
    }

    private void addTreeItem(MTreeNode node) {
    	Object data = node;
    	if (data instanceof MTreeNode) {
    		MTreeNode mNode = (MTreeNode) data;
    		treeNodeItemMap.put(mNode.getName(), node);
    	}
	}

    /**
     * populate the searchable list
     */
    public void initialise()
    {
    	refreshSearchList();

        if (tree.getModel() != null)
        {
        	tree.getModel().addTreeDataListener(this);
        }
    }

	private void refreshSearchList() {
		treeNodeItemMap.clear();
		if (tree.getModel() == null) {
	    	TreeUtils.traverse(tree, new TreeItemAction() {
				public void run(Treeitem treeItem) {
					addTreeItem(treeItem);
				}
	    	});
		} else {
			TreeUtils.traverse(tree.getModel(), new TreeNodeAction() {
				public void run(MTreeNode treeNode) {
					addTreeItem(treeNode);
				}
	    	});
		}

    	treeValues = new String[treeNodeItemMap.size()];
    	treeDescription = new String[treeNodeItemMap.size()];

    	int i = -1;

        for (Object value : treeNodeItemMap.values())
        {
        	i++;
        	if (value instanceof Treeitem)
        	{
        		Treeitem treeItem = (Treeitem) value;
        		treeValues[i] = treeItem.getLabel();
        		treeDescription[i] = treeItem.getTooltiptext();
        	}
        	else if (value instanceof MTreeNode)
        	{
        		MTreeNode mNode = (MTreeNode)value;
        		treeValues[i] = mNode.getName();
        		treeDescription[i] = mNode.getDescription();
        	}
        }

        cmbSearch.setDescription(treeDescription);
        cmbSearch.setDict(treeValues);
	}

	/**
     * @param event
     * @see EventListener#onEvent(Event)
     */
    public void onEvent(Event event)
    {
        if (cmbSearch.equals(event.getTarget()) && (event.getName().equals(Events.ON_CHANGE)))
        {
            String value = cmbSearch.getValue();

            if (value != null && value.trim().length() > 0
            		&& value.substring(0, 1).equals(PREFIX_DOCUMENT_SEARCH))
            {
            	DocumentSearch search = new DocumentSearch();
            	if (search.openDocumentsByDocumentNo(value.substring(1)))
    				cmbSearch.setText(null);
            	return;
            }

            Object node = treeNodeItemMap.get(value);
            Treeitem treeItem = null;
            if (node == null) {
            	return;
            } else if (node instanceof Treeitem) {
	            treeItem = (Treeitem) node;
            } else {
            	MTreeNode sNode = (MTreeNode) node;
            	int[] path = tree.getModel().getPath(tree.getModel().getRoot(), sNode);
    			treeItem = tree.renderItemByPath(path);
    			tree.setSelectedItem(treeItem);
            }
            if (treeItem != null)
            {
                select(treeItem);
                Clients.showBusy(Msg.getMsg(Env.getCtx(), "Loading"), true);
                Events.echoEvent("onPostSelect", this, null);
            }
        }
    }

    /**
     * don't call this directly, use internally for post selection event
     */
    public void onPostSelect() {
    	Clients.showBusy(null, false);
    	Event event = null;
    	if (eventToFire.equals(Events.ON_CLICK))
    		event = new Event(Events.ON_CLICK, tree.getSelectedItem().getTreerow());
    	else
    		event = new Event(eventToFire, tree);
    	Events.postEvent(event);
    }

	private void select(Treeitem selectedItem) {
		Treeitem parent = selectedItem.getParentItem();
		while (parent != null) {
			if (!parent.isOpen())
				parent.setOpen(true);

			parent = parent.getParentItem();
		}
		selectedItem.getTree().setSelectedItem(selectedItem);
	}

	/**
	 * @param event
	 * @see TreeDataListener#onChange(TreeDataEvent)
	 */
	public void onChange(TreeDataEvent event) {
		refreshSearchList();
	}
}
