/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Print_Job_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Print_Job_Detail extends org.compiere.model.PO implements I_C_Print_Job_Detail, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -328508281L;

    /** Standard Constructor */
    public X_C_Print_Job_Detail (Properties ctx, int C_Print_Job_Detail_ID, String trxName)
    {
      super (ctx, C_Print_Job_Detail_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Print_Job_Detail (Properties ctx, ResultSet rs, String trxName)
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
	public void setAD_PrinterRouting_ID (int AD_PrinterRouting_ID)
	{
		if (AD_PrinterRouting_ID < 1) 
			set_Value (COLUMNNAME_AD_PrinterRouting_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrinterRouting_ID, Integer.valueOf(AD_PrinterRouting_ID));
	}

	@Override
	public int getAD_PrinterRouting_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrinterRouting_ID);
	}

	@Override
	public void setC_Print_Job_Detail_ID (int C_Print_Job_Detail_ID)
	{
		if (C_Print_Job_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_Detail_ID, Integer.valueOf(C_Print_Job_Detail_ID));
	}

	@Override
	public int getC_Print_Job_Detail_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Print_Job_Detail_ID);
	}

	@Override
	public de.metas.printing.model.I_C_Print_Job_Line getC_Print_Job_Line()
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_Job_Line_ID, de.metas.printing.model.I_C_Print_Job_Line.class);
	}

	@Override
	public void setC_Print_Job_Line(de.metas.printing.model.I_C_Print_Job_Line C_Print_Job_Line)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_Job_Line_ID, de.metas.printing.model.I_C_Print_Job_Line.class, C_Print_Job_Line);
	}

	@Override
	public void setC_Print_Job_Line_ID (int C_Print_Job_Line_ID)
	{
		if (C_Print_Job_Line_ID < 1) 
			set_Value (COLUMNNAME_C_Print_Job_Line_ID, null);
		else 
			set_Value (COLUMNNAME_C_Print_Job_Line_ID, Integer.valueOf(C_Print_Job_Line_ID));
	}

	@Override
	public int getC_Print_Job_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Print_Job_Line_ID);
	}
}