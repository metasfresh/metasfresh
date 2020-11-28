/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Print_Package
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Print_Package extends org.compiere.model.PO implements I_C_Print_Package, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1881830811L;

    /** Standard Constructor */
    public X_C_Print_Package (Properties ctx, int C_Print_Package_ID, String trxName)
    {
      super (ctx, C_Print_Package_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Print_Package (Properties ctx, ResultSet rs, String trxName)
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
			set_ValueNoCheck (COLUMNNAME_AD_Session_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Session_ID, Integer.valueOf(AD_Session_ID));
	}

	@Override
	public int getAD_Session_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Session_ID);
	}

	@Override
	public void setBinaryFormat (java.lang.String BinaryFormat)
	{
		set_Value (COLUMNNAME_BinaryFormat, BinaryFormat);
	}

	@Override
	public java.lang.String getBinaryFormat() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BinaryFormat);
	}

	@Override
	public void setCopies (int Copies)
	{
		set_Value (COLUMNNAME_Copies, Integer.valueOf(Copies));
	}

	@Override
	public int getCopies() 
	{
		return get_ValueAsInt(COLUMNNAME_Copies);
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
	public void setC_Print_Package_ID (int C_Print_Package_ID)
	{
		if (C_Print_Package_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_Package_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Package_ID, Integer.valueOf(C_Print_Package_ID));
	}

	@Override
	public int getC_Print_Package_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Print_Package_ID);
	}

	@Override
	public void setPackageInfoCount (int PackageInfoCount)
	{
		throw new IllegalArgumentException ("PackageInfoCount is virtual column");	}

	@Override
	public int getPackageInfoCount() 
	{
		return get_ValueAsInt(COLUMNNAME_PackageInfoCount);
	}

	@Override
	public void setPageCount (int PageCount)
	{
		set_Value (COLUMNNAME_PageCount, Integer.valueOf(PageCount));
	}

	@Override
	public int getPageCount() 
	{
		return get_ValueAsInt(COLUMNNAME_PageCount);
	}

	@Override
	public void setTransactionID (java.lang.String TransactionID)
	{
		set_Value (COLUMNNAME_TransactionID, TransactionID);
	}

	@Override
	public java.lang.String getTransactionID() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TransactionID);
	}
}