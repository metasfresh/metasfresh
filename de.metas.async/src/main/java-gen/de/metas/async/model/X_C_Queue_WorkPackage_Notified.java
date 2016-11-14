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

/** Generated Model for C_Queue_WorkPackage_Notified
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Queue_WorkPackage_Notified extends org.compiere.model.PO implements I_C_Queue_WorkPackage_Notified, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -742795785L;

    /** Standard Constructor */
    public X_C_Queue_WorkPackage_Notified (Properties ctx, int C_Queue_WorkPackage_Notified_ID, String trxName)
    {
      super (ctx, C_Queue_WorkPackage_Notified_ID, trxName);
      /** if (C_Queue_WorkPackage_Notified_ID == 0)
        {
			setC_Queue_WorkPackage_Notified_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Queue_WorkPackage_Notified (Properties ctx, ResultSet rs, String trxName)
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
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_Queue_WorkPackage_Notified[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Bach Workpackage SeqNo.
		@param BachWorkpackageSeqNo Bach Workpackage SeqNo	  */
	@Override
	public void setBachWorkpackageSeqNo (int BachWorkpackageSeqNo)
	{
		set_Value (COLUMNNAME_BachWorkpackageSeqNo, Integer.valueOf(BachWorkpackageSeqNo));
	}

	/** Get Bach Workpackage SeqNo.
		@return Bach Workpackage SeqNo	  */
	@Override
	public int getBachWorkpackageSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BachWorkpackageSeqNo);
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
			set_Value (COLUMNNAME_C_Async_Batch_ID, null);
		else 
			set_Value (COLUMNNAME_C_Async_Batch_ID, Integer.valueOf(C_Async_Batch_ID));
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

	@Override
	public de.metas.async.model.I_C_Queue_WorkPackage getC_Queue_WorkPackage() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Queue_WorkPackage_ID, de.metas.async.model.I_C_Queue_WorkPackage.class);
	}

	@Override
	public void setC_Queue_WorkPackage(de.metas.async.model.I_C_Queue_WorkPackage C_Queue_WorkPackage)
	{
		set_ValueFromPO(COLUMNNAME_C_Queue_WorkPackage_ID, de.metas.async.model.I_C_Queue_WorkPackage.class, C_Queue_WorkPackage);
	}

	/** Set WorkPackage Queue.
		@param C_Queue_WorkPackage_ID WorkPackage Queue	  */
	@Override
	public void setC_Queue_WorkPackage_ID (int C_Queue_WorkPackage_ID)
	{
		if (C_Queue_WorkPackage_ID < 1) 
			set_Value (COLUMNNAME_C_Queue_WorkPackage_ID, null);
		else 
			set_Value (COLUMNNAME_C_Queue_WorkPackage_ID, Integer.valueOf(C_Queue_WorkPackage_ID));
	}

	/** Get WorkPackage Queue.
		@return WorkPackage Queue	  */
	@Override
	public int getC_Queue_WorkPackage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_WorkPackage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set WorkPackage Notified.
		@param C_Queue_WorkPackage_Notified_ID WorkPackage Notified	  */
	@Override
	public void setC_Queue_WorkPackage_Notified_ID (int C_Queue_WorkPackage_Notified_ID)
	{
		if (C_Queue_WorkPackage_Notified_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_Notified_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_Notified_ID, Integer.valueOf(C_Queue_WorkPackage_Notified_ID));
	}

	/** Get WorkPackage Notified.
		@return WorkPackage Notified	  */
	@Override
	public int getC_Queue_WorkPackage_Notified_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_WorkPackage_Notified_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Notified.
		@param IsNotified Notified	  */
	@Override
	public void setIsNotified (boolean IsNotified)
	{
		set_Value (COLUMNNAME_IsNotified, Boolean.valueOf(IsNotified));
	}

	/** Get Notified.
		@return Notified	  */
	@Override
	public boolean isNotified () 
	{
		Object oo = get_Value(COLUMNNAME_IsNotified);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

}