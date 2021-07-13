/** Generated Model - DO NOT CHANGE */
package de.metas.material.dispo.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Candidate_ATP_QueryResult
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_Candidate_ATP_QueryResult extends org.compiere.model.PO implements I_MD_Candidate_ATP_QueryResult, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 60721310L;

    /** Standard Constructor */
    public X_MD_Candidate_ATP_QueryResult (Properties ctx, int MD_Candidate_ATP_QueryResult_ID, String trxName)
    {
      super (ctx, MD_Candidate_ATP_QueryResult_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_Candidate_ATP_QueryResult (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_BPartner_Customer_ID (int C_BPartner_Customer_ID)
	{
		if (C_BPartner_Customer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Customer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Customer_ID, Integer.valueOf(C_BPartner_Customer_ID));
	}

	@Override
	public int getC_BPartner_Customer_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Customer_ID);
	}

	@Override
	public void setDateProjected (java.sql.Timestamp DateProjected)
	{
		set_ValueNoCheck (COLUMNNAME_DateProjected, DateProjected);
	}

	@Override
	public java.sql.Timestamp getDateProjected() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateProjected);
	}

	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_ValueNoCheck (COLUMNNAME_Qty, Qty);
	}

	@Override
	public java.math.BigDecimal getQty() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSeqNo (int SeqNo)
	{
		set_ValueNoCheck (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setStorageAttributesKey (java.lang.String StorageAttributesKey)
	{
		set_ValueNoCheck (COLUMNNAME_StorageAttributesKey, StorageAttributesKey);
	}

	@Override
	public java.lang.String getStorageAttributesKey() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StorageAttributesKey);
	}
}