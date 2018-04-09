/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_PrinterHW
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_PrinterHW extends org.compiere.model.PO implements I_AD_PrinterHW, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2120510340L;

    /** Standard Constructor */
    public X_AD_PrinterHW (Properties ctx, int AD_PrinterHW_ID, String trxName)
    {
      super (ctx, AD_PrinterHW_ID, trxName);
      /** if (AD_PrinterHW_ID == 0)
        {
			setAD_PrinterHW_ID (0);
			setHostKey (null); // @#AD_Session.HostKey@
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_PrinterHW (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Hardware-Drucker.
		@param AD_PrinterHW_ID Hardware-Drucker	  */
	@Override
	public void setAD_PrinterHW_ID (int AD_PrinterHW_ID)
	{
		if (AD_PrinterHW_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_ID, Integer.valueOf(AD_PrinterHW_ID));
	}

	/** Get Hardware-Drucker.
		@return Hardware-Drucker	  */
	@Override
	public int getAD_PrinterHW_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrinterHW_ID);
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

	/** Set Host key.
		@param HostKey 
		Unique identifier of a host
	  */
	@Override
	public void setHostKey (java.lang.String HostKey)
	{
		set_ValueNoCheck (COLUMNNAME_HostKey, HostKey);
	}

	/** Get Host key.
		@return Unique identifier of a host
	  */
	@Override
	public java.lang.String getHostKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HostKey);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_ValueNoCheck (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** 
	 * OutputType AD_Reference_ID=540632
	 * Reference name: OutputType
	 */
	public static final int OUTPUTTYPE_AD_Reference_ID=540632;
	/** PDF = PDF */
	public static final String OUTPUTTYPE_PDF = "PDF";
	/** Set Ausgabe Art.
		@param OutputType Ausgabe Art	  */
	@Override
	public void setOutputType (java.lang.String OutputType)
	{

		set_Value (COLUMNNAME_OutputType, OutputType);
	}

	/** Get Ausgabe Art.
		@return Ausgabe Art	  */
	@Override
	public java.lang.String getOutputType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OutputType);
	}
}