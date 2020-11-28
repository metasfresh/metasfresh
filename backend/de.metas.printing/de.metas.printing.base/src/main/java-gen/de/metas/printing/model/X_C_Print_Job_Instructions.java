/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Print_Job_Instructions
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Print_Job_Instructions extends org.compiere.model.PO implements I_C_Print_Job_Instructions, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -491101471L;

    /** Standard Constructor */
    public X_C_Print_Job_Instructions (Properties ctx, int C_Print_Job_Instructions_ID, String trxName)
    {
      super (ctx, C_Print_Job_Instructions_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Print_Job_Instructions (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.printing.model.I_AD_PrinterHW getAD_PrinterHW()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrinterHW_ID, de.metas.printing.model.I_AD_PrinterHW.class);
	}

	@Override
	public void setAD_PrinterHW(de.metas.printing.model.I_AD_PrinterHW AD_PrinterHW)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrinterHW_ID, de.metas.printing.model.I_AD_PrinterHW.class, AD_PrinterHW);
	}

	@Override
	public void setAD_PrinterHW_ID (int AD_PrinterHW_ID)
	{
		if (AD_PrinterHW_ID < 1) 
			set_Value (COLUMNNAME_AD_PrinterHW_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrinterHW_ID, Integer.valueOf(AD_PrinterHW_ID));
	}

	@Override
	public int getAD_PrinterHW_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrinterHW_ID);
	}

	@Override
	public void setAD_User_ToPrint_ID (int AD_User_ToPrint_ID)
	{
		if (AD_User_ToPrint_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ToPrint_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ToPrint_ID, Integer.valueOf(AD_User_ToPrint_ID));
	}

	@Override
	public int getAD_User_ToPrint_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ToPrint_ID);
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
	public de.metas.printing.model.I_C_Print_Job getC_Print_Job()
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_Job_ID, de.metas.printing.model.I_C_Print_Job.class);
	}

	@Override
	public void setC_Print_Job(de.metas.printing.model.I_C_Print_Job C_Print_Job)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_Job_ID, de.metas.printing.model.I_C_Print_Job.class, C_Print_Job);
	}

	@Override
	public void setC_Print_Job_ID (int C_Print_Job_ID)
	{
		if (C_Print_Job_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_ID, Integer.valueOf(C_Print_Job_ID));
	}

	@Override
	public int getC_Print_Job_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Print_Job_ID);
	}

	@Override
	public void setC_Print_Job_Instructions_ID (int C_Print_Job_Instructions_ID)
	{
		if (C_Print_Job_Instructions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_Instructions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_Instructions_ID, Integer.valueOf(C_Print_Job_Instructions_ID));
	}

	@Override
	public int getC_Print_Job_Instructions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Print_Job_Instructions_ID);
	}

	@Override
	public de.metas.printing.model.I_C_Print_Job_Line getC_PrintJob_Line_From()
	{
		return get_ValueAsPO(COLUMNNAME_C_PrintJob_Line_From_ID, de.metas.printing.model.I_C_Print_Job_Line.class);
	}

	@Override
	public void setC_PrintJob_Line_From(de.metas.printing.model.I_C_Print_Job_Line C_PrintJob_Line_From)
	{
		set_ValueFromPO(COLUMNNAME_C_PrintJob_Line_From_ID, de.metas.printing.model.I_C_Print_Job_Line.class, C_PrintJob_Line_From);
	}

	@Override
	public void setC_PrintJob_Line_From_ID (int C_PrintJob_Line_From_ID)
	{
		if (C_PrintJob_Line_From_ID < 1) 
			set_Value (COLUMNNAME_C_PrintJob_Line_From_ID, null);
		else 
			set_Value (COLUMNNAME_C_PrintJob_Line_From_ID, Integer.valueOf(C_PrintJob_Line_From_ID));
	}

	@Override
	public int getC_PrintJob_Line_From_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PrintJob_Line_From_ID);
	}

	@Override
	public de.metas.printing.model.I_C_Print_Job_Line getC_PrintJob_Line_To()
	{
		return get_ValueAsPO(COLUMNNAME_C_PrintJob_Line_To_ID, de.metas.printing.model.I_C_Print_Job_Line.class);
	}

	@Override
	public void setC_PrintJob_Line_To(de.metas.printing.model.I_C_Print_Job_Line C_PrintJob_Line_To)
	{
		set_ValueFromPO(COLUMNNAME_C_PrintJob_Line_To_ID, de.metas.printing.model.I_C_Print_Job_Line.class, C_PrintJob_Line_To);
	}

	@Override
	public void setC_PrintJob_Line_To_ID (int C_PrintJob_Line_To_ID)
	{
		if (C_PrintJob_Line_To_ID < 1) 
			set_Value (COLUMNNAME_C_PrintJob_Line_To_ID, null);
		else 
			set_Value (COLUMNNAME_C_PrintJob_Line_To_ID, Integer.valueOf(C_PrintJob_Line_To_ID));
	}

	@Override
	public int getC_PrintJob_Line_To_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PrintJob_Line_To_ID);
	}

	@Override
	public void setErrorMsg (java.lang.String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	@Override
	public java.lang.String getErrorMsg() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ErrorMsg);
	}

	@Override
	public void setHostKey (java.lang.String HostKey)
	{
		set_Value (COLUMNNAME_HostKey, HostKey);
	}

	@Override
	public java.lang.String getHostKey() 
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
	@Override
	public void setStatus (java.lang.String Status)
	{

		set_Value (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}
}