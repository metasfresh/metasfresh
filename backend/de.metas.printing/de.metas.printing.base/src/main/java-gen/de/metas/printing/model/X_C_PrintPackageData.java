/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_PrintPackageData
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_PrintPackageData extends org.compiere.model.PO implements I_C_PrintPackageData, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 273970225L;

    /** Standard Constructor */
    public X_C_PrintPackageData (Properties ctx, int C_PrintPackageData_ID, String trxName)
    {
      super (ctx, C_PrintPackageData_ID, trxName);
    }

    /** Load Constructor */
    public X_C_PrintPackageData (Properties ctx, ResultSet rs, String trxName)
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
	public void setC_PrintPackageData_ID (int C_PrintPackageData_ID)
	{
		if (C_PrintPackageData_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PrintPackageData_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PrintPackageData_ID, Integer.valueOf(C_PrintPackageData_ID));
	}

	@Override
	public int getC_PrintPackageData_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PrintPackageData_ID);
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
	public void setPrintData (byte[] PrintData)
	{
		set_Value (COLUMNNAME_PrintData, PrintData);
	}

	@Override
	public byte[] getPrintData() 
	{
		return (byte[])get_Value(COLUMNNAME_PrintData);
	}
}