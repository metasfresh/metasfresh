/** Generated Model - DO NOT CHANGE */
package de.metas.fresh.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Order_MFGWarehouse_ReportLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Order_MFGWarehouse_ReportLine extends org.compiere.model.PO implements I_C_Order_MFGWarehouse_ReportLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -477617748L;

    /** Standard Constructor */
    public X_C_Order_MFGWarehouse_ReportLine (Properties ctx, int C_Order_MFGWarehouse_ReportLine_ID, String trxName)
    {
      super (ctx, C_Order_MFGWarehouse_ReportLine_ID, trxName);
      /** if (C_Order_MFGWarehouse_ReportLine_ID == 0)
        {
			setC_OrderLine_ID (0);
			setC_Order_MFGWarehouse_Report_ID (0);
			setC_Order_MFGWarehouse_ReportLine_ID (0);
			setM_Product_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Order_MFGWarehouse_ReportLine (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	/** Set Auftragsposition.
		@param C_OrderLine_ID 
		Auftragsposition
	  */
	@Override
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Auftragsposition.
		@return Auftragsposition
	  */
	@Override
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.fresh.model.I_C_Order_MFGWarehouse_Report getC_Order_MFGWarehouse_Report() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_MFGWarehouse_Report_ID, de.metas.fresh.model.I_C_Order_MFGWarehouse_Report.class);
	}

	@Override
	public void setC_Order_MFGWarehouse_Report(de.metas.fresh.model.I_C_Order_MFGWarehouse_Report C_Order_MFGWarehouse_Report)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_MFGWarehouse_Report_ID, de.metas.fresh.model.I_C_Order_MFGWarehouse_Report.class, C_Order_MFGWarehouse_Report);
	}

	/** Set Order / MFG Warehouse report.
		@param C_Order_MFGWarehouse_Report_ID Order / MFG Warehouse report	  */
	@Override
	public void setC_Order_MFGWarehouse_Report_ID (int C_Order_MFGWarehouse_Report_ID)
	{
		if (C_Order_MFGWarehouse_Report_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_MFGWarehouse_Report_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_MFGWarehouse_Report_ID, Integer.valueOf(C_Order_MFGWarehouse_Report_ID));
	}

	/** Get Order / MFG Warehouse report.
		@return Order / MFG Warehouse report	  */
	@Override
	public int getC_Order_MFGWarehouse_Report_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_MFGWarehouse_Report_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Order / MFG Warehouse report line.
		@param C_Order_MFGWarehouse_ReportLine_ID Order / MFG Warehouse report line	  */
	@Override
	public void setC_Order_MFGWarehouse_ReportLine_ID (int C_Order_MFGWarehouse_ReportLine_ID)
	{
		if (C_Order_MFGWarehouse_ReportLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_MFGWarehouse_ReportLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_MFGWarehouse_ReportLine_ID, Integer.valueOf(C_Order_MFGWarehouse_ReportLine_ID));
	}

	/** Get Order / MFG Warehouse report line.
		@return Order / MFG Warehouse report line	  */
	@Override
	public int getC_Order_MFGWarehouse_ReportLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_MFGWarehouse_ReportLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}