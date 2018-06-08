/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_PrintPackageData
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_PrintPackageData extends org.compiere.model.PO implements I_C_PrintPackageData, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -301860268L;

    /** Standard Constructor */
    public X_C_PrintPackageData (Properties ctx, int C_PrintPackageData_ID, String trxName)
    {
      super (ctx, C_PrintPackageData_ID, trxName);
      /** if (C_PrintPackageData_ID == 0)
        {
			setC_PrintPackageData_ID (0);
			setC_Print_Package_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_PrintPackageData (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Print package data.
		@param C_PrintPackageData_ID Print package data	  */
	@Override
	public void setC_PrintPackageData_ID (int C_PrintPackageData_ID)
	{
		if (C_PrintPackageData_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PrintPackageData_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PrintPackageData_ID, Integer.valueOf(C_PrintPackageData_ID));
	}

	/** Get Print package data.
		@return Print package data	  */
	@Override
	public int getC_PrintPackageData_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PrintPackageData_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.printing.model.I_C_Print_Package getC_Print_Package() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_Package_ID, de.metas.printing.model.I_C_Print_Package.class);
	}

	@Override
	public void setC_Print_Package(de.metas.printing.model.I_C_Print_Package C_Print_Package)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_Package_ID, de.metas.printing.model.I_C_Print_Package.class, C_Print_Package);
	}

	/** Set Druckpaket.
		@param C_Print_Package_ID Druckpaket	  */
	@Override
	public void setC_Print_Package_ID (int C_Print_Package_ID)
	{
		if (C_Print_Package_ID < 1) 
			set_Value (COLUMNNAME_C_Print_Package_ID, null);
		else 
			set_Value (COLUMNNAME_C_Print_Package_ID, Integer.valueOf(C_Print_Package_ID));
	}

	/** Get Druckpaket.
		@return Druckpaket	  */
	@Override
	public int getC_Print_Package_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Print_Package_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Print data.
		@param PrintData Print data	  */
	@Override
	public void setPrintData (byte[] PrintData)
	{
		set_Value (COLUMNNAME_PrintData, PrintData);
	}

	/** Get Print data.
		@return Print data	  */
	@Override
	public byte[] getPrintData () 
	{
		return (byte[])get_Value(COLUMNNAME_PrintData);
	}
}