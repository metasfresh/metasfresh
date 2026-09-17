// Generated Model - DO NOT CHANGE
package de.metas.fresh.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Order_MFGWarehouse_Report
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Order_MFGWarehouse_Report extends org.compiere.model.PO implements I_C_Order_MFGWarehouse_Report, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1746349251L;

    /** Standard Constructor */
    public X_C_Order_MFGWarehouse_Report (final Properties ctx, final int C_Order_MFGWarehouse_Report_ID, @Nullable final String trxName)
    {
      super (ctx, C_Order_MFGWarehouse_Report_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Order_MFGWarehouse_Report (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_Responsible_ID (final int AD_User_Responsible_ID)
	{
		if (AD_User_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_User_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_Responsible_ID, AD_User_Responsible_ID);
	}

	@Override
	public int getAD_User_Responsible_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_Responsible_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setC_Order_MFGWarehouse_Report_ID (final int C_Order_MFGWarehouse_Report_ID)
	{
		if (C_Order_MFGWarehouse_Report_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_MFGWarehouse_Report_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_MFGWarehouse_Report_ID, C_Order_MFGWarehouse_Report_ID);
	}

	@Override
	public int getC_Order_MFGWarehouse_Report_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_MFGWarehouse_Report_ID);
	}

	/** 
	 * DocumentType AD_Reference_ID=540574
	 * Reference name: C_Order_MFGWarehouse_Report_DocumentType
	 */
	public static final int DOCUMENTTYPE_AD_Reference_ID=540574;
	/** Warehouse = WH */
	public static final String DOCUMENTTYPE_Warehouse = "WH";
	/** Plant = PL */
	public static final String DOCUMENTTYPE_Plant = "PL";
	@Override
	public void setDocumentType (final java.lang.String DocumentType)
	{
		set_Value (COLUMNNAME_DocumentType, DocumentType);
	}

	@Override
	public java.lang.String getDocumentType() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentType);
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
	public void setOrderCheckupGeneration (final int OrderCheckupGeneration)
	{
		set_Value (COLUMNNAME_OrderCheckupGeneration, OrderCheckupGeneration);
	}

	@Override
	public int getOrderCheckupGeneration() 
	{
		return get_ValueAsInt(COLUMNNAME_OrderCheckupGeneration);
	}

	@Override
	public org.compiere.model.I_S_Resource getPP_Plant()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setPP_Plant(final org.compiere.model.I_S_Resource PP_Plant)
	{
		set_ValueFromPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class, PP_Plant);
	}

	@Override
	public void setPP_Plant_ID (final int PP_Plant_ID)
	{
		if (PP_Plant_ID < 1) 
			set_Value (COLUMNNAME_PP_Plant_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Plant_ID, PP_Plant_ID);
	}

	@Override
	public int getPP_Plant_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Plant_ID);
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
}