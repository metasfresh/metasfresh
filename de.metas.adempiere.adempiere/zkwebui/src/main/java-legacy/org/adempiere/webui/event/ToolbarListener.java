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

package org.adempiere.webui.event;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Feb 25, 2007
 * @version $Revision: 0.10 $
 */
public interface ToolbarListener
{
	/**
	 * Ignore user changes
	 */
    public void onIgnore();
    
    /**
     * Create new record
     */
    public void onNew();
    
    /**
     * Navigate to first record
     */
    public void onFirst();
    
    /**
     * Navigate to last record
     */
    public void onLast();
    
    /**
     * Navigate to next record
     */
    public void onNext();
    
    /**
     * Navigate to previous record
     */
    public void onPrevious();
    
    /**
     * Navigate to parent record, i.e previous tab
     */
    public void onParentRecord();
    
    /**
     * Navigate to detail record, i.e next tab
     */
    public void onDetailRecord();
    
    /**
     * Refresh current record
     */
    public void onRefresh();
    
    /**
     * Print document
     */
    public void onPrint();
    
    /**
     * View available report for current tab
     */
    public void onReport();
    
    /**
     * Toggle record presentation between free form and tabular 
     */
    public void onToggle();
    
    /**
     * Open the help window
     */
    public void onHelp();
    
    /**
     * Delete current record
     */
    public void onDelete();
    
    /**
     * Save current record
     */
    public void onSave();
    
    /**
     * Open the find/search dialog
     */
    public void onFind();
    
    /**
     * Open the attachment dialog
     */
    public void onAttachment();
    
    /**
     * Open the history dialog
     */
    public void onHistoryRecords();
    
    /**
     * Open the archive dialog
     */
    public void onArchive();
    
    /**
     * Zoom to window where current record is use
     */
    public void onZoomAcross();
    
    /**
     * Show active workflow for current record
     */
    public void onActiveWorkflows();
    
    /**
     * Open the request menu where user can view available request or create a new request
     */
    public void onRequests();
    
    /**
     * View product info
     */
    public void onProductInfo();
    
    /**
     * Export current records
     */
    public void onExport();
}
