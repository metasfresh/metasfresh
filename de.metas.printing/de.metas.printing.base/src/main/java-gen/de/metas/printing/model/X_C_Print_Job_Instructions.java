/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Print_Job_Instructions
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Print_Job_Instructions extends org.compiere.model.PO implements I_C_Print_Job_Instructions, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 854993614L;

    /** Standard Constructor */
    public X_C_Print_Job_Instructions (Properties ctx, int C_Print_Job_Instructions_ID, String trxName)
    {
      super (ctx, C_Print_Job_Instructions_ID, trxName);
      /** if (C_Print_Job_Instructions_ID == 0)
        {
			setAD_User_ToPrint_ID (0);
			setCopies (0); // 1
			setC_Print_Job_ID (0);
			setC_Print_Job_Instructions_ID (0);
			setC_PrintJob_Line_From_ID (0);
			setC_PrintJob_Line_To_ID (0);
			setStatus (null); // P
        } */
    }

    /** Load Constructor */
    public X_C_Print_Job_Instructions (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.printing.model.I_AD_PrinterHW getAD_PrinterHW() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrinterHW_ID, de.metas.printing.model.I_AD_PrinterHW.class);
	}

	@Override
	public void setAD_PrinterHW(de.metas.printing.model.I_AD_PrinterHW AD_PrinterHW)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrinterHW_ID, de.metas.printing.model.I_AD_PrinterHW.class, AD_PrinterHW);
	}

	/** Set Hardware-Drucker.
		@param AD_PrinterHW_ID Hardware-Drucker	  */
	@Override
	public void setAD_PrinterHW_ID (int AD_PrinterHW_ID)
	{
		if (AD_PrinterHW_ID < 1) 
			set_Value (COLUMNNAME_AD_PrinterHW_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrinterHW_ID, Integer.valueOf(AD_PrinterHW_ID));
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

	@Override
	public org.compiere.model.I_AD_User getAD_User_ToPrint() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ToPrint_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User_ToPrint(org.compiere.model.I_AD_User AD_User_ToPrint)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ToPrint_ID, org.compiere.model.I_AD_User.class, AD_User_ToPrint);
	}

	/** Set Ausdruck für.
		@param AD_User_ToPrint_ID Ausdruck für	  */
	@Override
	public void setAD_User_ToPrint_ID (int AD_User_ToPrint_ID)
	{
		if (AD_User_ToPrint_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ToPrint_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ToPrint_ID, Integer.valueOf(AD_User_ToPrint_ID));
	}

	/** Get Ausdruck für.
		@return Ausdruck für	  */
	@Override
	public int getAD_User_ToPrint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ToPrint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	public de.metas.printing.model.I_C_Print_Job getC_Print_Job() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_Job_ID, de.metas.printing.model.I_C_Print_Job.class);
	}

	@Override
	public void setC_Print_Job(de.metas.printing.model.I_C_Print_Job C_Print_Job)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_Job_ID, de.metas.printing.model.I_C_Print_Job.class, C_Print_Job);
	}

	/** Set Druck-Job.
		@param C_Print_Job_ID Druck-Job	  */
	@Override
	public void setC_Print_Job_ID (int C_Print_Job_ID)
	{
		if (C_Print_Job_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_ID, Integer.valueOf(C_Print_Job_ID));
	}

	/** Get Druck-Job.
		@return Druck-Job	  */
	@Override
	public int getC_Print_Job_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Print_Job_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Druck-Job Anweisung.
		@param C_Print_Job_Instructions_ID Druck-Job Anweisung	  */
	@Override
	public void setC_Print_Job_Instructions_ID (int C_Print_Job_Instructions_ID)
	{
		if (C_Print_Job_Instructions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_Instructions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_Instructions_ID, Integer.valueOf(C_Print_Job_Instructions_ID));
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

	@Override
	public de.metas.printing.model.I_C_Print_Job_Line getC_PrintJob_Line_From() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_PrintJob_Line_From_ID, de.metas.printing.model.I_C_Print_Job_Line.class);
	}

	@Override
	public void setC_PrintJob_Line_From(de.metas.printing.model.I_C_Print_Job_Line C_PrintJob_Line_From)
	{
		set_ValueFromPO(COLUMNNAME_C_PrintJob_Line_From_ID, de.metas.printing.model.I_C_Print_Job_Line.class, C_PrintJob_Line_From);
	}

	/** Set Print job line from.
		@param C_PrintJob_Line_From_ID Print job line from	  */
	@Override
	public void setC_PrintJob_Line_From_ID (int C_PrintJob_Line_From_ID)
	{
		if (C_PrintJob_Line_From_ID < 1) 
			set_Value (COLUMNNAME_C_PrintJob_Line_From_ID, null);
		else 
			set_Value (COLUMNNAME_C_PrintJob_Line_From_ID, Integer.valueOf(C_PrintJob_Line_From_ID));
	}

	/** Get Print job line from.
		@return Print job line from	  */
	@Override
	public int getC_PrintJob_Line_From_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PrintJob_Line_From_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.printing.model.I_C_Print_Job_Line getC_PrintJob_Line_To() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_PrintJob_Line_To_ID, de.metas.printing.model.I_C_Print_Job_Line.class);
	}

	@Override
	public void setC_PrintJob_Line_To(de.metas.printing.model.I_C_Print_Job_Line C_PrintJob_Line_To)
	{
		set_ValueFromPO(COLUMNNAME_C_PrintJob_Line_To_ID, de.metas.printing.model.I_C_Print_Job_Line.class, C_PrintJob_Line_To);
	}

	/** Set Print job line to.
		@param C_PrintJob_Line_To_ID Print job line to	  */
	@Override
	public void setC_PrintJob_Line_To_ID (int C_PrintJob_Line_To_ID)
	{
		if (C_PrintJob_Line_To_ID < 1) 
			set_Value (COLUMNNAME_C_PrintJob_Line_To_ID, null);
		else 
			set_Value (COLUMNNAME_C_PrintJob_Line_To_ID, Integer.valueOf(C_PrintJob_Line_To_ID));
	}

	/** Get Print job line to.
		@return Print job line to	  */
	@Override
	public int getC_PrintJob_Line_To_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PrintJob_Line_To_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Fehlermeldung.
		@param ErrorMsg Fehlermeldung	  */
	@Override
	public void setErrorMsg (java.lang.String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Fehlermeldung.
		@return Fehlermeldung	  */
	@Override
	public java.lang.String getErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set Host key.
		@param HostKey 
		Unique identifier of a host
	  */
	@Override
	public void setHostKey (java.lang.String HostKey)
	{
		set_Value (COLUMNNAME_HostKey, HostKey);
	}

	/** Get Host key.
		@return Unique identifier of a host
	  */
	@Override
	public java.lang.String getHostKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HostKey);
	}

	/** 
	 * Status AD_Reference_ID=540384
	 * Reference name: C_Print_Job_Instructions_Status
	 */
	public static final int STATUS_AD_Reference_ID=540384;
	/** Pending = P */
	public static final String STATUS_Pending = "P";
	/** Send = S */
	public static final String STATUS_Send = "S";
	/** Done = D */
	public static final String STATUS_Done = "D";
	/** Error = E */
	public static final String STATUS_Error = "E";
	/** Set Status.
		@param Status 
		Status of the currently running check
	  */
	@Override
	public void setStatus (java.lang.String Status)
	{

		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	@Override
	public java.lang.String getStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}
}