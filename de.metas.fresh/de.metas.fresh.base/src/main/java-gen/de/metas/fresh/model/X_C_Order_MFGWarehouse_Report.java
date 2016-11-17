/** Generated Model - DO NOT CHANGE */
package de.metas.fresh.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Order_MFGWarehouse_Report
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Order_MFGWarehouse_Report extends org.compiere.model.PO implements I_C_Order_MFGWarehouse_Report, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1080673917L;

    /** Standard Constructor */
    public X_C_Order_MFGWarehouse_Report (Properties ctx, int C_Order_MFGWarehouse_Report_ID, String trxName)
    {
      super (ctx, C_Order_MFGWarehouse_Report_ID, trxName);
      /** if (C_Order_MFGWarehouse_Report_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Order_ID (0);
			setC_Order_MFGWarehouse_Report_ID (0);
			setDocumentType (null);
			setProcessed (false);
// N
        } */
    }

    /** Load Constructor */
    public X_C_Order_MFGWarehouse_Report (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_User getAD_User_Responsible() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_Responsible_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User_Responsible(org.compiere.model.I_AD_User AD_User_Responsible)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_Responsible_ID, org.compiere.model.I_AD_User.class, AD_User_Responsible);
	}

	/** Set Verantwortlicher Benutzer.
		@param AD_User_Responsible_ID Verantwortlicher Benutzer	  */
	@Override
	public void setAD_User_Responsible_ID (int AD_User_Responsible_ID)
	{
		if (AD_User_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_User_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_Responsible_ID, Integer.valueOf(AD_User_Responsible_ID));
	}

	/** Get Verantwortlicher Benutzer.
		@return Verantwortlicher Benutzer	  */
	@Override
	public int getAD_User_Responsible_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_Responsible_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	/** Set Auftrag.
		@param C_Order_ID 
		Auftrag
	  */
	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Auftrag.
		@return Auftrag
	  */
	@Override
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** 
	 * DocumentType AD_Reference_ID=540574
	 * Reference name: C_Order_MFGWarehouse_Report_DocumentType
	 */
	public static final int DOCUMENTTYPE_AD_Reference_ID=540574;
	/** Warehouse = WH */
	public static final String DOCUMENTTYPE_Warehouse = "WH";
	/** Plant = PL */
	public static final String DOCUMENTTYPE_Plant = "PL";
	/** Set Belegart.
		@param DocumentType 
		Belegart
	  */
	@Override
	public void setDocumentType (java.lang.String DocumentType)
	{

		set_Value (COLUMNNAME_DocumentType, DocumentType);
	}

	/** Get Belegart.
		@return Belegart
	  */
	@Override
	public java.lang.String getDocumentType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentType);
	}

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class, M_Warehouse);
	}

	/** Set Lager.
		@param M_Warehouse_ID 
		Lager oder Ort für Dienstleistung
	  */
	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Lager.
		@return Lager oder Ort für Dienstleistung
	  */
	@Override
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_S_Resource getPP_Plant() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setPP_Plant(org.compiere.model.I_S_Resource PP_Plant)
	{
		set_ValueFromPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class, PP_Plant);
	}

	/** Set Produktionsstätte.
		@param PP_Plant_ID Produktionsstätte	  */
	@Override
	public void setPP_Plant_ID (int PP_Plant_ID)
	{
		if (PP_Plant_ID < 1) 
			set_Value (COLUMNNAME_PP_Plant_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Plant_ID, Integer.valueOf(PP_Plant_ID));
	}

	/** Get Produktionsstätte.
		@return Produktionsstätte	  */
	@Override
	public int getPP_Plant_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Plant_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}