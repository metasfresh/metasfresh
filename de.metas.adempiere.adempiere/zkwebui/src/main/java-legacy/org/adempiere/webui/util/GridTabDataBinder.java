/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
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
package org.adempiere.webui.util;

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridTab.DataNewCopyMode;
import org.compiere.model.GridTable;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

/**
 * Transfer data from editor to GridTab
 * @author hengsin
 *
 */
public class GridTabDataBinder implements ValueChangeListener {

	private final static Logger logger = LogManager.getLogger(GridTabDataBinder.class);
	
	private GridTab gridTab;

	/**
	 * 
	 * @param gridTab
	 */
	public GridTabDataBinder(GridTab gridTab) {
		this.gridTab = gridTab;
	}
	
	/**
	 * @param e
	 */
	@Override
	public void valueChange(ValueChangeEvent e)
    {
        if (gridTab.isProcessed())       //  only active records
        {
            Object source = e.getSource();
            if (source instanceof WEditor)
            {
            	// Elaine 2009/05/06
            	WEditor editor = (WEditor) source;
            	GridField gridField = editor.getGridField();
            	
            	if(gridField != null)
            	{
            		if(!gridField.isEditable(true))
            		{
            			logger.info("(" + gridTab.toString() + ") " + e.getPropertyName());
            			return;
            		}
            	}
            	else if(!editor.isReadWrite())
            	{
            		logger.info("(" + gridTab.toString() + ") " + e.getPropertyName());
            		return;            		
            	}
            }
            else
            {
                logger.info("(" + gridTab.toString() + ") " + e.getPropertyName());
                return;
            }
        }   //  processed
        logger.info("(" + gridTab.toString() + ") "
            + e.getPropertyName() + "=" + e.getNewValue() + " (" + e.getOldValue() + ") "
            + (e.getOldValue() == null ? "" : e.getOldValue().getClass().getName()));
        

        //  Get Row/Col Info
        GridTable mTable = gridTab.getTableModel();
        int row = gridTab.getCurrentRow();
        int col = mTable.findColumn(e.getPropertyName());
        //
        if (e.getNewValue() == null && e.getOldValue() != null 
            && e.getOldValue().toString().length() > 0)     //  some editors return "" instead of null
//        	  this is the original code from GridController, don't know what it does there but it breaks ignore button for web ui        
//            mTable.setChanged (true);  
        	mTable.setValueAt (e.getNewValue(), row, col);
        else
        {
        	
        	Object newValue = e.getNewValue();
			Integer newValues[] = null;
			
			if (newValue instanceof Integer[])
			{
				newValues = ((Integer[])newValue);
				newValue = newValues[0];
				
				if (newValues.length > 1)
				{
					Integer valuesCopy[] = new Integer[newValues.length - 1];
					System.arraycopy(newValues, 1, valuesCopy, 0, valuesCopy.length);
					newValues = valuesCopy;
				}
				else
				{
					newValues = null;
				}
			}
			else if (newValue instanceof Object[])
			{
				logger.error("Multiple values can only be processed for IDs (Integer)");
				throw new IllegalArgumentException("Multiple Selection values not available for this field. " + e.getPropertyName());
			}
			
           	mTable.setValueAt (newValue, row, col);
            //  Force Callout
            if ( e.getPropertyName().equals("S_ResourceAssignment_ID") )
            {
                GridField mField = gridTab.getField(col);
                if (mField != null && gridTab.getCalloutExecutor().hasCallouts(mField))
                {
                    gridTab.processFieldChange(mField);     //  Dependencies & Callout
                }
            }
            
			if (newValues != null && newValues.length > 0)
			{
				// Save data, since record need to be used for generating clones.
				if (!gridTab.dataSave(false))
				{
					throw new AdempiereException("SaveError");
				}
				
				// Retrieve the current record ID
				int recordId = gridTab.getKeyID(gridTab.getCurrentRow());
				
				Trx trx = Trx.get(Trx.createTrxName(), true);
				trx.start();
				try
				{
					saveMultipleRecords(Env.getCtx(), gridTab.getTableName(), e.getPropertyName(), recordId, newValues, trx.getTrxName());
					trx.commit();
					gridTab.dataRefreshAll();
				}
				catch(Exception ex)
				{
					trx.rollback();
					logger.error(ex.getMessage());
					throw new AdempiereException("SaveError");
				}
				finally
				{
					trx.close();
				}
			}
        }

    } // ValueChange
	
	/**************************************************************************
	 * Save Multiple records - Clone a record and assign new values to each 
	 * clone for a specific column.
	 * @param ctx context
	 * @param tableName Table Name
	 * @param columnName Column for which value need to be changed
	 * @param recordId Record to clone
	 * @param values Values to be assigned to clones for the specified column
	 * @param trxName Transaction
	 * @throws Exception If error is occured when loading the PO or saving clones
	 * 
	 * @author ashley
	 */
	protected void saveMultipleRecords(Properties ctx, String tableName, 
			String columnName, int recordId, Integer[] values, 
			String trxName) throws Exception
	{
		if (values == null)
		{
			return ;
		}
		
		int oldRow = gridTab.getCurrentRow();
		GridField lineField = gridTab.getField("Line");	
		
		for (int i = 0; i < values.length; i++)
		{
			if (!gridTab.dataNew(DataNewCopyMode.Copy))
			{
				throw new IllegalStateException("Could not clone tab");
			}
			
			gridTab.setValue(columnName, values[i]);
			
			if (lineField != null)
			{
				gridTab.setValue(lineField, 0);
			}
			
			if (!gridTab.dataSave(false))
			{
				throw new IllegalStateException("Could not update tab");
			}
			
			gridTab.setCurrentRow(oldRow);
		}
	}
}
