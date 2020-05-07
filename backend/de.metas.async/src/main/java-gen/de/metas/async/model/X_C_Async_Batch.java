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

/** Generated Model for C_Async_Batch
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Async_Batch extends org.compiere.model.PO implements I_C_Async_Batch, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1010076500L;

    /** Standard Constructor */
    public X_C_Async_Batch (Properties ctx, int C_Async_Batch_ID, String trxName)
    {
      super (ctx, C_Async_Batch_ID, trxName);
      /** if (C_Async_Batch_ID == 0)
        {
			setC_Async_Batch_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_Async_Batch (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_PInstance getAD_PInstance() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance(org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
	}

	/** Set Prozess-Instanz.
		@param AD_PInstance_ID 
		Instanz eines Prozesses
	  */
	@Override
	public void setAD_PInstance_ID (int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, Integer.valueOf(AD_PInstance_ID));
	}

	/** Get Prozess-Instanz.
		@return Instanz eines Prozesses
	  */
	@Override
	public int getAD_PInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	@Override
	public de.metas.async.model.I_C_Async_Batch_Type getC_Async_Batch_Type() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Async_Batch_Type_ID, de.metas.async.model.I_C_Async_Batch_Type.class);
	}

	@Override
	public void setC_Async_Batch_Type(de.metas.async.model.I_C_Async_Batch_Type C_Async_Batch_Type)
	{
		set_ValueFromPO(COLUMNNAME_C_Async_Batch_Type_ID, de.metas.async.model.I_C_Async_Batch_Type.class, C_Async_Batch_Type);
	}

	/** Set Async Batch Type.
		@param C_Async_Batch_Type_ID Async Batch Type	  */
	@Override
	public void setC_Async_Batch_Type_ID (int C_Async_Batch_Type_ID)
	{
		if (C_Async_Batch_Type_ID < 1) 
			set_Value (COLUMNNAME_C_Async_Batch_Type_ID, null);
		else 
			set_Value (COLUMNNAME_C_Async_Batch_Type_ID, Integer.valueOf(C_Async_Batch_Type_ID));
	}

	/** Get Async Batch Type.
		@return Async Batch Type	  */
	@Override
	public int getC_Async_Batch_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Async_Batch_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Enqueued.
		@param CountEnqueued Enqueued	  */
	@Override
	public void setCountEnqueued (int CountEnqueued)
	{
		set_Value (COLUMNNAME_CountEnqueued, Integer.valueOf(CountEnqueued));
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

	/** Set Expected.
		@param CountExpected Expected	  */
	@Override
	public void setCountExpected (int CountExpected)
	{
		set_Value (COLUMNNAME_CountExpected, Integer.valueOf(CountExpected));
	}

	/** Get Expected.
		@return Expected	  */
	@Override
	public int getCountExpected () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CountExpected);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeitet.
		@param CountProcessed Verarbeitet	  */
	@Override
	public void setCountProcessed (int CountProcessed)
	{
		set_Value (COLUMNNAME_CountProcessed, Integer.valueOf(CountProcessed));
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

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set First Enqueued.
		@param FirstEnqueued First Enqueued	  */
	@Override
	public void setFirstEnqueued (java.sql.Timestamp FirstEnqueued)
	{
		set_Value (COLUMNNAME_FirstEnqueued, FirstEnqueued);
	}

	/** Get First Enqueued.
		@return First Enqueued	  */
	@Override
	public java.sql.Timestamp getFirstEnqueued () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_FirstEnqueued);
	}

	/** Set Last Enqueued.
		@param LastEnqueued Last Enqueued	  */
	@Override
	public void setLastEnqueued (java.sql.Timestamp LastEnqueued)
	{
		set_Value (COLUMNNAME_LastEnqueued, LastEnqueued);
	}

	/** Get Last Enqueued.
		@return Last Enqueued	  */
	@Override
	public java.sql.Timestamp getLastEnqueued () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_LastEnqueued);
	}

	/** Set Last Processed.
		@param LastProcessed Last Processed	  */
	@Override
	public void setLastProcessed (java.sql.Timestamp LastProcessed)
	{
		set_Value (COLUMNNAME_LastProcessed, LastProcessed);
	}

	/** Get Last Processed.
		@return Last Processed	  */
	@Override
	public java.sql.Timestamp getLastProcessed () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_LastProcessed);
	}

	@Override
	public de.metas.async.model.I_C_Queue_WorkPackage getLastProcessed_WorkPackage() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_LastProcessed_WorkPackage_ID, de.metas.async.model.I_C_Queue_WorkPackage.class);
	}

	@Override
	public void setLastProcessed_WorkPackage(de.metas.async.model.I_C_Queue_WorkPackage LastProcessed_WorkPackage)
	{
		set_ValueFromPO(COLUMNNAME_LastProcessed_WorkPackage_ID, de.metas.async.model.I_C_Queue_WorkPackage.class, LastProcessed_WorkPackage);
	}

	/** Set LastProcessed WorkPackage.
		@param LastProcessed_WorkPackage_ID LastProcessed WorkPackage	  */
	@Override
	public void setLastProcessed_WorkPackage_ID (int LastProcessed_WorkPackage_ID)
	{
		if (LastProcessed_WorkPackage_ID < 1) 
			set_Value (COLUMNNAME_LastProcessed_WorkPackage_ID, null);
		else 
			set_Value (COLUMNNAME_LastProcessed_WorkPackage_ID, Integer.valueOf(LastProcessed_WorkPackage_ID));
	}

	/** Get LastProcessed WorkPackage.
		@return LastProcessed WorkPackage	  */
	@Override
	public int getLastProcessed_WorkPackage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LastProcessed_WorkPackage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	@Override
	public de.metas.async.model.I_C_Async_Batch getParent_Async_Batch() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Parent_Async_Batch_ID, de.metas.async.model.I_C_Async_Batch.class);
	}

	@Override
	public void setParent_Async_Batch(de.metas.async.model.I_C_Async_Batch Parent_Async_Batch)
	{
		set_ValueFromPO(COLUMNNAME_Parent_Async_Batch_ID, de.metas.async.model.I_C_Async_Batch.class, Parent_Async_Batch);
	}

	/** Set Parent_Async_Batch_ID.
		@param Parent_Async_Batch_ID Parent_Async_Batch_ID	  */
	@Override
	public void setParent_Async_Batch_ID (int Parent_Async_Batch_ID)
	{
		if (Parent_Async_Batch_ID < 1) 
			set_Value (COLUMNNAME_Parent_Async_Batch_ID, null);
		else 
			set_Value (COLUMNNAME_Parent_Async_Batch_ID, Integer.valueOf(Parent_Async_Batch_ID));
	}

	/** Get Parent_Async_Batch_ID.
		@return Parent_Async_Batch_ID	  */
	@Override
	public int getParent_Async_Batch_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Parent_Async_Batch_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}