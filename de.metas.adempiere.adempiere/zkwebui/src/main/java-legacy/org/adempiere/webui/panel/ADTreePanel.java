/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.panel;

import java.util.List;

import org.adempiere.model.tree.IADTreeBL;
import org.adempiere.util.Services;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.SimpleTreeModel;
import org.adempiere.webui.util.TreeUtils;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Tree;

/**
 * 
 * @author hengsin
 *
 */
public class ADTreePanel extends Panel implements EventListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5473705529310157142L;
	private TreeSearchPanel pnlSearch;
    private Tree tree;
    
    private Checkbox chkExpand; // Elaine 2009/02/27 - expand tree
    
    public ADTreePanel()
    {
        init();        
    }
    
    /**
     * @param AD_Tree_ID
     * @param windowNo
     */
    public void initTree(int AD_Tree_ID, int windowNo) 
    {
    	SimpleTreeModel.initADTree(tree, AD_Tree_ID, windowNo);
    	pnlSearch.initialise();
    }
    
    private void init()
    {
    	this.setWidth("100%");
    	this.setHeight("100%");
    	
        tree = new Tree();
        tree.setMultiple(false);
        tree.setWidth("100%");
        tree.setVflex(true);
        tree.setPageSize(-1); // Due to bug in the new paging functionality
        
        tree.setStyle("border: none");
        
        pnlSearch = new TreeSearchPanel(tree, Events.ON_SELECT);
        
        Toolbar toolbar = new Toolbar();
        toolbar.appendChild(pnlSearch);
        this.appendChild(toolbar);
        
        Panelchildren pc = new Panelchildren();
        this.appendChild(pc);
        pc.appendChild(tree);  
        
        // Elaine 2009/02/27 - expand tree
        toolbar = new Toolbar();
        chkExpand = new Checkbox();
        chkExpand.setText(Msg.getMsg(Env.getCtx(), "ExpandTree"));
        chkExpand.addEventListener(Events.ON_CHECK, this);
        toolbar.appendChild(chkExpand);
        this.appendChild(toolbar);
    }
    
    /**
     * @param event
     * @see EventListener#onEvent(Event)
     */
    public void onEvent(Event event)
    {
        String eventName = event.getName();
        
        // Elaine 2009/02/27 - expand tree
        if (eventName.equals(Events.ON_CHECK) && event.getTarget() == chkExpand)
        {
        	expandOnCheck();
        }
        //
    }
    
    /**
     * @return tree
     */
	public Tree getTree() 
	{
		return tree;
	}
	
	/**
	* expand all node
	*/
	public void expandAll()
	{
		if (!chkExpand.isChecked())
			chkExpand.setChecked(true);
	
		TreeUtils.expandAll(tree);
	}
	
	/**
	 * collapse all node
	 */
	public void collapseAll()
	{
		if (chkExpand.isChecked())
			chkExpand.setChecked(false);
	
		TreeUtils.collapseAll(tree);
	}
	
	/**
	 *  On check event for the expand checkbox
	 */
	private void expandOnCheck()
	{
		if (chkExpand.isChecked())
			expandAll();
		else
			collapseAll();
	}
	//
	
	public void filterIds(List<Integer> ids)
	{
		Tree tree = getTree();
		SimpleTreeModel model = (SimpleTreeModel)tree.getModel();
		
		Services.get(IADTreeBL.class).filterIds(model.getRoot(), ids);
		
		tree.setModel(null);
		tree.setModel(model);
		if (chkExpand.isChecked())
			expandAll();
	}
}
