/** Generated Model - DO NOT CHANGE */
package de.metas.material.cockpit.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Cockpit_DocumentDetail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_Cockpit_DocumentDetail extends org.compiere.model.PO implements I_MD_Cockpit_DocumentDetail, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 34082079L;

    /** Standard Constructor */
    public X_MD_Cockpit_DocumentDetail (Properties ctx, int MD_Cockpit_DocumentDetail_ID, String trxName)
    {
      super (ctx, MD_Cockpit_DocumentDetail_ID, trxName);
      /** if (MD_Cockpit_DocumentDetail_ID == 0)
        {
			setMD_Cockpit_DocumentDetail_ID (0);
			setMD_Cockpit_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MD_Cockpit_DocumentDetail (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType)
	{
		set_ValueFromPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class, C_DocType);
	}

	/** Set Belegart.
		@param C_DocType_ID 
		Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Belegart.
		@return Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Pauschale - Vertragsperiode.
		@param C_Flatrate_Term_ID Pauschale - Vertragsperiode	  */
	@Override
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, Integer.valueOf(C_Flatrate_Term_ID));
	}

	/** Get Pauschale - Vertragsperiode.
		@return Pauschale - Vertragsperiode	  */
	@Override
	public int getC_Flatrate_Term_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Term_ID);
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
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
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
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
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

	/** Set Abo-Verlauf.
		@param C_SubscriptionProgress_ID Abo-Verlauf	  */
	@Override
	public void setC_SubscriptionProgress_ID (int C_SubscriptionProgress_ID)
	{
		if (C_SubscriptionProgress_ID < 1) 
			set_Value (COLUMNNAME_C_SubscriptionProgress_ID, null);
		else 
			set_Value (COLUMNNAME_C_SubscriptionProgress_ID, Integer.valueOf(C_SubscriptionProgress_ID));
	}

	/** Get Abo-Verlauf.
		@return Abo-Verlauf	  */
	@Override
	public int getC_SubscriptionProgress_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_SubscriptionProgress_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Wareneingangsdisposition.
		@param M_ReceiptSchedule_ID Wareneingangsdisposition	  */
	@Override
	public void setM_ReceiptSchedule_ID (int M_ReceiptSchedule_ID)
	{
		if (M_ReceiptSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ReceiptSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ReceiptSchedule_ID, Integer.valueOf(M_ReceiptSchedule_ID));
	}

	/** Get Wareneingangsdisposition.
		@return Wareneingangsdisposition	  */
	@Override
	public int getM_ReceiptSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ReceiptSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lieferdisposition.
		@param M_ShipmentSchedule_ID Lieferdisposition	  */
	@Override
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, Integer.valueOf(M_ShipmentSchedule_ID));
	}

	/** Get Lieferdisposition.
		@return Lieferdisposition	  */
	@Override
	public int getM_ShipmentSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ShipmentSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DocumentDetail.
		@param MD_Cockpit_DocumentDetail_ID DocumentDetail	  */
	@Override
	public void setMD_Cockpit_DocumentDetail_ID (int MD_Cockpit_DocumentDetail_ID)
	{
		if (MD_Cockpit_DocumentDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Cockpit_DocumentDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Cockpit_DocumentDetail_ID, Integer.valueOf(MD_Cockpit_DocumentDetail_ID));
	}

	/** Get DocumentDetail.
		@return DocumentDetail	  */
	@Override
	public int getMD_Cockpit_DocumentDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_Cockpit_DocumentDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.material.cockpit.model.I_MD_Cockpit getMD_Cockpit() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MD_Cockpit_ID, de.metas.material.cockpit.model.I_MD_Cockpit.class);
	}

	@Override
	public void setMD_Cockpit(de.metas.material.cockpit.model.I_MD_Cockpit MD_Cockpit)
	{
		set_ValueFromPO(COLUMNNAME_MD_Cockpit_ID, de.metas.material.cockpit.model.I_MD_Cockpit.class, MD_Cockpit);
	}

	/** Set Materialcockpit.
		@param MD_Cockpit_ID Materialcockpit	  */
	@Override
	public void setMD_Cockpit_ID (int MD_Cockpit_ID)
	{
		if (MD_Cockpit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Cockpit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Cockpit_ID, Integer.valueOf(MD_Cockpit_ID));
	}

	/** Get Materialcockpit.
		@return Materialcockpit	  */
	@Override
	public int getMD_Cockpit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_Cockpit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bestellt/ Beauftragt.
		@param QtyOrdered 
		Bestellt/ Beauftragt
	  */
	@Override
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Bestellt/ Beauftragt.
		@return Bestellt/ Beauftragt
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Offen.
		@param QtyReserved 
		Offene Menge
	  */
	@Override
	public void setQtyReserved (java.math.BigDecimal QtyReserved)
	{
		set_Value (COLUMNNAME_QtyReserved, QtyReserved);
	}

	/** Get Offen.
		@return Offene Menge
	  */
	@Override
	public java.math.BigDecimal getQtyReserved () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReserved);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}