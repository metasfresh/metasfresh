/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Print_Package
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Print_Package extends org.compiere.model.PO implements I_C_Print_Package, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1025779941L;

    /** Standard Constructor */
    public X_C_Print_Package (Properties ctx, int C_Print_Package_ID, String trxName)
    {
      super (ctx, C_Print_Package_ID, trxName);
      /** if (C_Print_Package_ID == 0)
        {
			setCopies (0); // 1
			setC_Print_Package_ID (0);
			setPageCount (0); // 0
			setTransactionID (null);
        } */
    }

    /** Load Constructor */
    public X_C_Print_Package (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Session getAD_Session() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Session_ID, org.compiere.model.I_AD_Session.class);
	}

	@Override
	public void setAD_Session(org.compiere.model.I_AD_Session AD_Session)
	{
		set_ValueFromPO(COLUMNNAME_AD_Session_ID, org.compiere.model.I_AD_Session.class, AD_Session);
	}

	/** Set Nutzersitzung.
		@param AD_Session_ID 
		User Session Online or Web
	  */
	@Override
	public void setAD_Session_ID (int AD_Session_ID)
	{
		if (AD_Session_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Session_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Session_ID, Integer.valueOf(AD_Session_ID));
	}

	/** Get Nutzersitzung.
		@return User Session Online or Web
	  */
	@Override
	public int getAD_Session_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Session_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Binärformat.
		@param BinaryFormat 
		Binary format of the print package (e.g. postscript vs pdf)
	  */
	@Override
	public void setBinaryFormat (java.lang.String BinaryFormat)
	{
		set_Value (COLUMNNAME_BinaryFormat, BinaryFormat);
	}

	/** Get Binärformat.
		@return Binary format of the print package (e.g. postscript vs pdf)
	  */
	@Override
	public java.lang.String getBinaryFormat () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BinaryFormat);
	}

	/** Set Kopien.
		@param Copies 
		Anzahl der zu erstellenden/zu druckenden Exemplare
	  */
	@Override
	public void setCopies (int Copies)
	{
		set_Value (COLUMNNAME_Copies, Integer.valueOf(Copies));
	}

	/** Get Kopien.
		@return Anzahl der zu erstellenden/zu druckenden Exemplare
	  */
	@Override
	public int getCopies () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Copies);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.printing.model.I_C_Print_Job_Instructions getC_Print_Job_Instructions() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_Job_Instructions_ID, de.metas.printing.model.I_C_Print_Job_Instructions.class);
	}

	@Override
	public void setC_Print_Job_Instructions(de.metas.printing.model.I_C_Print_Job_Instructions C_Print_Job_Instructions)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_Job_Instructions_ID, de.metas.printing.model.I_C_Print_Job_Instructions.class, C_Print_Job_Instructions);
	}

	/** Set Druck-Job Anweisung.
		@param C_Print_Job_Instructions_ID Druck-Job Anweisung	  */
	@Override
	public void setC_Print_Job_Instructions_ID (int C_Print_Job_Instructions_ID)
	{
		if (C_Print_Job_Instructions_ID < 1) 
			set_Value (COLUMNNAME_C_Print_Job_Instructions_ID, null);
		else 
			set_Value (COLUMNNAME_C_Print_Job_Instructions_ID, Integer.valueOf(C_Print_Job_Instructions_ID));
	}

	/** Get Druck-Job Anweisung.
		@return Druck-Job Anweisung	  */
	@Override
	public int getC_Print_Job_Instructions_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Print_Job_Instructions_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Druckpaket.
		@param C_Print_Package_ID Druckpaket	  */
	@Override
	public void setC_Print_Package_ID (int C_Print_Package_ID)
	{
		if (C_Print_Package_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_Package_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Package_ID, Integer.valueOf(C_Print_Package_ID));
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

	/** Set Anz. Druckpaket-Infos.
		@param PackageInfoCount 
		Number of different package infos for a given print package.
	  */
	@Override
	public void setPackageInfoCount (int PackageInfoCount)
	{
		throw new IllegalArgumentException ("PackageInfoCount is virtual column");	}

	/** Get Anz. Druckpaket-Infos.
		@return Number of different package infos for a given print package.
	  */
	@Override
	public int getPackageInfoCount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PackageInfoCount);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Seitenzahl.
		@param PageCount Seitenzahl	  */
	@Override
	public void setPageCount (int PageCount)
	{
		set_Value (COLUMNNAME_PageCount, Integer.valueOf(PageCount));
	}

	/** Get Seitenzahl.
		@return Seitenzahl	  */
	@Override
	public int getPageCount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PageCount);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Transaktions-ID.
		@param TransactionID Transaktions-ID	  */
	@Override
	public void setTransactionID (java.lang.String TransactionID)
	{
		set_Value (COLUMNNAME_TransactionID, TransactionID);
	}

	/** Get Transaktions-ID.
		@return Transaktions-ID	  */
	@Override
	public java.lang.String getTransactionID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TransactionID);
	}
}