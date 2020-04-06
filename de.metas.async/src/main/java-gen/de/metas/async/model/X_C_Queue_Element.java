/** Generated Model - DO NOT CHANGE */
package de.metas.async.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Queue_Element
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Queue_Element extends org.compiere.model.PO implements I_C_Queue_Element, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 437744889L;

    /** Standard Constructor */
    public X_C_Queue_Element (Properties ctx, int C_Queue_Element_ID, String trxName)
    {
      super (ctx, C_Queue_Element_ID, trxName);
      /** if (C_Queue_Element_ID == 0)
        {
			setAD_Table_ID (0);
			setC_Queue_Block_ID (0);
			setC_Queue_Element_ID (0);
			setC_Queue_WorkPackage_ID (0);
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Queue_Element (Properties ctx, ResultSet rs, String trxName)
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

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
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
	public de.metas.async.model.I_C_Queue_Block getC_Queue_Block()
	{
		return get_ValueAsPO(COLUMNNAME_C_Queue_Block_ID, de.metas.async.model.I_C_Queue_Block.class);
	}

	@Override
	public void setC_Queue_Block(de.metas.async.model.I_C_Queue_Block C_Queue_Block)
	{
		set_ValueFromPO(COLUMNNAME_C_Queue_Block_ID, de.metas.async.model.I_C_Queue_Block.class, C_Queue_Block);
	}

	/** Set Queue Block.
		@param C_Queue_Block_ID Queue Block	  */
	@Override
	public void setC_Queue_Block_ID (int C_Queue_Block_ID)
	{
		if (C_Queue_Block_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Block_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Block_ID, Integer.valueOf(C_Queue_Block_ID));
	}

	/** Get Queue Block.
		@return Queue Block	  */
	@Override
	public int getC_Queue_Block_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_Block_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Element Queue.
		@param C_Queue_Element_ID Element Queue	  */
	@Override
	public void setC_Queue_Element_ID (int C_Queue_Element_ID)
	{
		if (C_Queue_Element_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Element_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Element_ID, Integer.valueOf(C_Queue_Element_ID));
	}

	/** Get Element Queue.
		@return Element Queue	  */
	@Override
	public int getC_Queue_Element_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_Element_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.async.model.I_C_Queue_WorkPackage getC_Queue_WorkPackage()
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
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_ID, Integer.valueOf(C_Queue_WorkPackage_ID));
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

	@Override
	public de.metas.async.model.I_C_Queue_WorkPackage getC_Queue_Workpackage_Preceeding()
	{
		return get_ValueAsPO(COLUMNNAME_C_Queue_Workpackage_Preceeding_ID, de.metas.async.model.I_C_Queue_WorkPackage.class);
	}

	@Override
	public void setC_Queue_Workpackage_Preceeding(de.metas.async.model.I_C_Queue_WorkPackage C_Queue_Workpackage_Preceeding)
	{
		set_ValueFromPO(COLUMNNAME_C_Queue_Workpackage_Preceeding_ID, de.metas.async.model.I_C_Queue_WorkPackage.class, C_Queue_Workpackage_Preceeding);
	}

	/** Set Ältestes nicht verarb. Vorgänger-Paket.
		@param C_Queue_Workpackage_Preceeding_ID 
		Arbeitspaket mit einem Element, das den selben Datensatz referenziert, noch nicht verarbeitet wurde und somit die Verarbeitung dieses Elements verhindert
	  */
	@Override
	public void setC_Queue_Workpackage_Preceeding_ID (int C_Queue_Workpackage_Preceeding_ID)
	{
		throw new IllegalArgumentException ("C_Queue_Workpackage_Preceeding_ID is virtual column");	}

	/** Get Ältestes nicht verarb. Vorgänger-Paket.
		@return Arbeitspaket mit einem Element, das den selben Datensatz referenziert, noch nicht verarbeitet wurde und somit die Verarbeitung dieses Elements verhindert
	  */
	@Override
	public int getC_Queue_Workpackage_Preceeding_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_Workpackage_Preceeding_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}