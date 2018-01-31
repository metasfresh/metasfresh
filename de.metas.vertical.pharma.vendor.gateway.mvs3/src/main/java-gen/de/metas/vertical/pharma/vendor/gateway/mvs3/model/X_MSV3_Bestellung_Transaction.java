/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_Bestellung_Transaction
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_Bestellung_Transaction extends org.compiere.model.PO implements I_MSV3_Bestellung_Transaction, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -253686264L;

    /** Standard Constructor */
    public X_MSV3_Bestellung_Transaction (Properties ctx, int MSV3_Bestellung_Transaction_ID, String trxName)
    {
      super (ctx, MSV3_Bestellung_Transaction_ID, trxName);
      /** if (MSV3_Bestellung_Transaction_ID == 0)
        {
			setMSV3_Bestellung_ID (0);
			setMSV3_Bestellung_Transaction_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MSV3_Bestellung_Transaction (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Issue getAD_Issue() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class);
	}

	@Override
	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue)
	{
		set_ValueFromPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class, AD_Issue);
	}

	/** Set System-Problem.
		@param AD_Issue_ID 
		Automatically created or manually entered System Issue
	  */
	@Override
	public void setAD_Issue_ID (int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, Integer.valueOf(AD_Issue_ID));
	}

	/** Get System-Problem.
		@return Automatically created or manually entered System Issue
	  */
	@Override
	public int getAD_Issue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Issue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Order getC_OrderPO() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderPO_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_OrderPO(org.compiere.model.I_C_Order C_OrderPO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderPO_ID, org.compiere.model.I_C_Order.class, C_OrderPO);
	}

	/** Set Bestellung.
		@param C_OrderPO_ID 
		Bestellung
	  */
	@Override
	public void setC_OrderPO_ID (int C_OrderPO_ID)
	{
		if (C_OrderPO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderPO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderPO_ID, Integer.valueOf(C_OrderPO_ID));
	}

	/** Get Bestellung.
		@return Bestellung
	  */
	@Override
	public int getC_OrderPO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderPO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAntwort getMSV3_BestellungAntwort() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_BestellungAntwort_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAntwort.class);
	}

	@Override
	public void setMSV3_BestellungAntwort(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAntwort MSV3_BestellungAntwort)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_BestellungAntwort_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAntwort.class, MSV3_BestellungAntwort);
	}

	/** Set MSV3_BestellungAntwort.
		@param MSV3_BestellungAntwort_ID MSV3_BestellungAntwort	  */
	@Override
	public void setMSV3_BestellungAntwort_ID (int MSV3_BestellungAntwort_ID)
	{
		if (MSV3_BestellungAntwort_ID < 1) 
			set_Value (COLUMNNAME_MSV3_BestellungAntwort_ID, null);
		else 
			set_Value (COLUMNNAME_MSV3_BestellungAntwort_ID, Integer.valueOf(MSV3_BestellungAntwort_ID));
	}

	/** Get MSV3_BestellungAntwort.
		@return MSV3_BestellungAntwort	  */
	@Override
	public int getMSV3_BestellungAntwort_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_BestellungAntwort_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Bestellung getMSV3_Bestellung() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_Bestellung_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Bestellung.class);
	}

	@Override
	public void setMSV3_Bestellung(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Bestellung MSV3_Bestellung)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_Bestellung_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Bestellung.class, MSV3_Bestellung);
	}

	/** Set MSV3_Bestellung.
		@param MSV3_Bestellung_ID MSV3_Bestellung	  */
	@Override
	public void setMSV3_Bestellung_ID (int MSV3_Bestellung_ID)
	{
		if (MSV3_Bestellung_ID < 1) 
			set_Value (COLUMNNAME_MSV3_Bestellung_ID, null);
		else 
			set_Value (COLUMNNAME_MSV3_Bestellung_ID, Integer.valueOf(MSV3_Bestellung_ID));
	}

	/** Get MSV3_Bestellung.
		@return MSV3_Bestellung	  */
	@Override
	public int getMSV3_Bestellung_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_Bestellung_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MSV3_Bestellung_Transaction.
		@param MSV3_Bestellung_Transaction_ID MSV3_Bestellung_Transaction	  */
	@Override
	public void setMSV3_Bestellung_Transaction_ID (int MSV3_Bestellung_Transaction_ID)
	{
		if (MSV3_Bestellung_Transaction_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_Bestellung_Transaction_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_Bestellung_Transaction_ID, Integer.valueOf(MSV3_Bestellung_Transaction_ID));
	}

	/** Get MSV3_Bestellung_Transaction.
		@return MSV3_Bestellung_Transaction	  */
	@Override
	public int getMSV3_Bestellung_Transaction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_Bestellung_Transaction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_FaultInfo getMSV3_FaultInfo() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_FaultInfo_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_FaultInfo.class);
	}

	@Override
	public void setMSV3_FaultInfo(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_FaultInfo MSV3_FaultInfo)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_FaultInfo_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_FaultInfo.class, MSV3_FaultInfo);
	}

	/** Set MSV3_FaultInfo.
		@param MSV3_FaultInfo_ID MSV3_FaultInfo	  */
	@Override
	public void setMSV3_FaultInfo_ID (int MSV3_FaultInfo_ID)
	{
		if (MSV3_FaultInfo_ID < 1) 
			set_Value (COLUMNNAME_MSV3_FaultInfo_ID, null);
		else 
			set_Value (COLUMNNAME_MSV3_FaultInfo_ID, Integer.valueOf(MSV3_FaultInfo_ID));
	}

	/** Get MSV3_FaultInfo.
		@return MSV3_FaultInfo	  */
	@Override
	public int getMSV3_FaultInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_FaultInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}