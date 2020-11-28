/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Print_Job_Instructions_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Print_Job_Instructions_v extends org.compiere.model.PO implements I_C_Print_Job_Instructions_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1825686391L;

    /** Standard Constructor */
    public X_C_Print_Job_Instructions_v (Properties ctx, int C_Print_Job_Instructions_v_ID, String trxName)
    {
      super (ctx, C_Print_Job_Instructions_v_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Print_Job_Instructions_v (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Archive getAD_Archive()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class);
	}

	@Override
	public void setAD_Archive(org.compiere.model.I_AD_Archive AD_Archive)
	{
		set_ValueFromPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class, AD_Archive);
	}

	@Override
	public void setAD_Archive_ID (int AD_Archive_ID)
	{
		if (AD_Archive_ID < 1) 
			set_Value (COLUMNNAME_AD_Archive_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Archive_ID, Integer.valueOf(AD_Archive_ID));
	}

	@Override
	public int getAD_Archive_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Archive_ID);
	}

	@Override
	public org.compiere.model.I_AD_Session getAD_Session()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Session_ID, org.compiere.model.I_AD_Session.class);
	}

	@Override
	public void setAD_Session(org.compiere.model.I_AD_Session AD_Session)
	{
		set_ValueFromPO(COLUMNNAME_AD_Session_ID, org.compiere.model.I_AD_Session.class, AD_Session);
	}

	@Override
	public void setAD_Session_ID (int AD_Session_ID)
	{
		if (AD_Session_ID < 1) 
			set_Value (COLUMNNAME_AD_Session_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Session_ID, Integer.valueOf(AD_Session_ID));
	}

	@Override
	public int getAD_Session_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Session_ID);
	}

	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setC_Order_MFGWarehouse_Report_ID (int C_Order_MFGWarehouse_Report_ID)
	{
		if (C_Order_MFGWarehouse_Report_ID < 1) 
			set_Value (COLUMNNAME_C_Order_MFGWarehouse_Report_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_MFGWarehouse_Report_ID, Integer.valueOf(C_Order_MFGWarehouse_Report_ID));
	}

	@Override
	public int getC_Order_MFGWarehouse_Report_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_MFGWarehouse_Report_ID);
	}

	@Override
	public de.metas.printing.model.I_C_Print_Job_Instructions getC_Print_Job_Instructions()
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_Job_Instructions_ID, de.metas.printing.model.I_C_Print_Job_Instructions.class);
	}

	@Override
	public void setC_Print_Job_Instructions(de.metas.printing.model.I_C_Print_Job_Instructions C_Print_Job_Instructions)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_Job_Instructions_ID, de.metas.printing.model.I_C_Print_Job_Instructions.class, C_Print_Job_Instructions);
	}

	@Override
	public void setC_Print_Job_Instructions_ID (int C_Print_Job_Instructions_ID)
	{
		if (C_Print_Job_Instructions_ID < 1) 
			set_Value (COLUMNNAME_C_Print_Job_Instructions_ID, null);
		else 
			set_Value (COLUMNNAME_C_Print_Job_Instructions_ID, Integer.valueOf(C_Print_Job_Instructions_ID));
	}

	@Override
	public int getC_Print_Job_Instructions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Print_Job_Instructions_ID);
	}

	@Override
	public de.metas.printing.model.I_C_Print_Package getC_Print_Package()
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_Package_ID, de.metas.printing.model.I_C_Print_Package.class);
	}

	@Override
	public void setC_Print_Package(de.metas.printing.model.I_C_Print_Package C_Print_Package)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_Package_ID, de.metas.printing.model.I_C_Print_Package.class, C_Print_Package);
	}

	@Override
	public void setC_Print_Package_ID (int C_Print_Package_ID)
	{
		if (C_Print_Package_ID < 1) 
			set_Value (COLUMNNAME_C_Print_Package_ID, null);
		else 
			set_Value (COLUMNNAME_C_Print_Package_ID, Integer.valueOf(C_Print_Package_ID));
	}

	@Override
	public int getC_Print_Package_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Print_Package_ID);
	}

	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public org.compiere.model.I_S_Resource getS_Resource()
	{
		return get_ValueAsPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setS_Resource(org.compiere.model.I_S_Resource S_Resource)
	{
		set_ValueFromPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class, S_Resource);
	}

	@Override
	public void setS_Resource_ID (int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_Value (COLUMNNAME_S_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_S_Resource_ID, Integer.valueOf(S_Resource_ID));
	}

	@Override
	public int getS_Resource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_ID);
	}
}