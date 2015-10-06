/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package de.metas.async.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for RV_Async_batch_statistics
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_RV_Async_batch_statistics extends org.compiere.model.PO implements I_RV_Async_batch_statistics, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 704152327L;

    /** Standard Constructor */
    public X_RV_Async_batch_statistics (Properties ctx, int RV_Async_batch_statistics_ID, String trxName)
    {
      super (ctx, RV_Async_batch_statistics_ID, trxName);
      /** if (RV_Async_batch_statistics_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_RV_Async_batch_statistics (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.async.model.I_C_Async_Batch getC_Async_Batch() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Async_Batch_ID, de.metas.async.model.I_C_Async_Batch.class);
	}

	@Override
	public void setC_Async_Batch(de.metas.async.model.I_C_Async_Batch C_Async_Batch)
	{
		set_ValueFromPO(COLUMNNAME_C_Async_Batch_ID, de.metas.async.model.I_C_Async_Batch.class, C_Async_Batch);
	}

	/** Set Async Batch.
		@param C_Async_Batch_ID Async Batch	  */
	@Override
	public void setC_Async_Batch_ID (int C_Async_Batch_ID)
	{
		if (C_Async_Batch_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Async_Batch_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Async_Batch_ID, Integer.valueOf(C_Async_Batch_ID));
	}

	/** Get Async Batch.
		@return Async Batch	  */
	@Override
	public int getC_Async_Batch_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Async_Batch_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Enqueued.
		@param CountEnqueued Enqueued	  */
	@Override
	public void setCountEnqueued (int CountEnqueued)
	{
		set_ValueNoCheck (COLUMNNAME_CountEnqueued, Integer.valueOf(CountEnqueued));
	}

	/** Get Enqueued.
		@return Enqueued	  */
	@Override
	public int getCountEnqueued () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CountEnqueued);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeitet.
		@param CountProcessed Verarbeitet	  */
	@Override
	public void setCountProcessed (int CountProcessed)
	{
		set_ValueNoCheck (COLUMNNAME_CountProcessed, Integer.valueOf(CountProcessed));
	}

	/** Get Verarbeitet.
		@return Verarbeitet	  */
	@Override
	public int getCountProcessed () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CountProcessed);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.async.model.I_C_Queue_PackageProcessor getC_Queue_PackageProcessor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Queue_PackageProcessor_ID, de.metas.async.model.I_C_Queue_PackageProcessor.class);
	}

	@Override
	public void setC_Queue_PackageProcessor(de.metas.async.model.I_C_Queue_PackageProcessor C_Queue_PackageProcessor)
	{
		set_ValueFromPO(COLUMNNAME_C_Queue_PackageProcessor_ID, de.metas.async.model.I_C_Queue_PackageProcessor.class, C_Queue_PackageProcessor);
	}

	/** Set WorkPackage Processor.
		@param C_Queue_PackageProcessor_ID WorkPackage Processor	  */
	@Override
	public void setC_Queue_PackageProcessor_ID (int C_Queue_PackageProcessor_ID)
	{
		if (C_Queue_PackageProcessor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_PackageProcessor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_PackageProcessor_ID, Integer.valueOf(C_Queue_PackageProcessor_ID));
	}

	/** Get WorkPackage Processor.
		@return WorkPackage Processor	  */
	@Override
	public int getC_Queue_PackageProcessor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_PackageProcessor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}