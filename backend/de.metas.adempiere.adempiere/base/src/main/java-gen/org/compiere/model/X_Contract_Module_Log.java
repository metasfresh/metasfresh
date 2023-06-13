// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for Contract_Module_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Contract_Module_Log extends org.compiere.model.PO implements I_Contract_Module_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 991883089L;

    /** Standard Constructor */
    public X_Contract_Module_Log (final Properties ctx, final int Contract_Module_Log_ID, @Nullable final String trxName)
    {
      super (ctx, Contract_Module_Log_ID, trxName);
    }

    /** Load Constructor */
    public X_Contract_Module_Log (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setC_Flatrate_Term_ID (final int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, C_Flatrate_Term_ID);
	}

	@Override
	public int getC_Flatrate_Term_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Term_ID);
	}

	@Override
	public void setC_Invoice_Candidate_ID (final int C_Invoice_Candidate_ID)
	{
		if (C_Invoice_Candidate_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, C_Invoice_Candidate_ID);
	}

	@Override
	public int getC_Invoice_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_ID);
	}

	@Override
	public void setCollectionPoint_BPartner_ID (final int CollectionPoint_BPartner_ID)
	{
		if (CollectionPoint_BPartner_ID < 1) 
			set_Value (COLUMNNAME_CollectionPoint_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_CollectionPoint_BPartner_ID, CollectionPoint_BPartner_ID);
	}

	@Override
	public int getCollectionPoint_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_CollectionPoint_BPartner_ID);
	}

	@Override
	public void setContract_Module_Log_ID (final int Contract_Module_Log_ID)
	{
		if (Contract_Module_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Contract_Module_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Contract_Module_Log_ID, Contract_Module_Log_ID);
	}

	@Override
	public int getContract_Module_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Contract_Module_Log_ID);
	}

	@Override
	public org.compiere.model.I_Contract_Module_Type getContract_Module_Type()
	{
		return get_ValueAsPO(COLUMNNAME_Contract_Module_Type_ID, org.compiere.model.I_Contract_Module_Type.class);
	}

	@Override
	public void setContract_Module_Type(final org.compiere.model.I_Contract_Module_Type Contract_Module_Type)
	{
		set_ValueFromPO(COLUMNNAME_Contract_Module_Type_ID, org.compiere.model.I_Contract_Module_Type.class, Contract_Module_Type);
	}

	@Override
	public void setContract_Module_Type_ID (final int Contract_Module_Type_ID)
	{
		if (Contract_Module_Type_ID < 1) 
			set_Value (COLUMNNAME_Contract_Module_Type_ID, null);
		else 
			set_Value (COLUMNNAME_Contract_Module_Type_ID, Contract_Module_Type_ID);
	}

	@Override
	public int getContract_Module_Type_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Contract_Module_Type_ID);
	}

	@Override
	public void setDateTrx (final @Nullable java.sql.Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	@Override
	public java.sql.Timestamp getDateTrx() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateTrx);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setIsSOTrx (final boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public boolean isSOTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOTrx);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProducer_BPartner_ID (final int Producer_BPartner_ID)
	{
		if (Producer_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Producer_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Producer_BPartner_ID, Producer_BPartner_ID);
	}

	@Override
	public int getProducer_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Producer_BPartner_ID);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}
}