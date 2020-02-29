/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Commission_Overview_V
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Commission_Overview_V extends org.compiere.model.PO implements I_C_Commission_Overview_V, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -338560102L;

    /** Standard Constructor */
    public X_C_Commission_Overview_V (Properties ctx, int C_Commission_Overview_V_ID, String trxName)
    {
      super (ctx, C_Commission_Overview_V_ID, trxName);
      /** if (C_Commission_Overview_V_ID == 0)
        {
			setC_Commission_Overview_V_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Commission_Overview_V (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Rechnungspartner.
		@param Bill_BPartner_ID 
		Geschäftspartner für die Rechnungsstellung
	  */
	@Override
	public void setBill_BPartner_ID (int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Bill_BPartner_ID, Integer.valueOf(Bill_BPartner_ID));
	}

	/** Get Rechnungspartner.
		@return Geschäftspartner für die Rechnungsstellung
	  */
	@Override
	public int getBill_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zugeordneter Vertriebspartner.
		@param C_BPartner_SalesRep_ID Zugeordneter Vertriebspartner	  */
	@Override
	public void setC_BPartner_SalesRep_ID (int C_BPartner_SalesRep_ID)
	{
		if (C_BPartner_SalesRep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_SalesRep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_SalesRep_ID, Integer.valueOf(C_BPartner_SalesRep_ID));
	}

	/** Get Zugeordneter Vertriebspartner.
		@return Zugeordneter Vertriebspartner	  */
	@Override
	public int getC_BPartner_SalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.contracts.commission.model.I_C_Commission_Instance getC_Commission_Instance()
	{
		return get_ValueAsPO(COLUMNNAME_C_Commission_Instance_ID, de.metas.contracts.commission.model.I_C_Commission_Instance.class);
	}

	@Override
	public void setC_Commission_Instance(de.metas.contracts.commission.model.I_C_Commission_Instance C_Commission_Instance)
	{
		set_ValueFromPO(COLUMNNAME_C_Commission_Instance_ID, de.metas.contracts.commission.model.I_C_Commission_Instance.class, C_Commission_Instance);
	}

	/** Set Provisionsvorgang.
		@param C_Commission_Instance_ID Provisionsvorgang	  */
	@Override
	public void setC_Commission_Instance_ID (int C_Commission_Instance_ID)
	{
		if (C_Commission_Instance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Instance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Instance_ID, Integer.valueOf(C_Commission_Instance_ID));
	}

	/** Get Provisionsvorgang.
		@return Provisionsvorgang	  */
	@Override
	public int getC_Commission_Instance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Commission_Instance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Provision Übersicht.
		@param C_Commission_Overview_V_ID Provision Übersicht	  */
	@Override
	public void setC_Commission_Overview_V_ID (int C_Commission_Overview_V_ID)
	{
		if (C_Commission_Overview_V_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Overview_V_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Overview_V_ID, Integer.valueOf(C_Commission_Overview_V_ID));
	}

	/** Get Provision Übersicht.
		@return Provision Übersicht	  */
	@Override
	public int getC_Commission_Overview_V_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Commission_Overview_V_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.contracts.commission.model.I_C_Commission_Share getC_Commission_Share()
	{
		return get_ValueAsPO(COLUMNNAME_C_Commission_Share_ID, de.metas.contracts.commission.model.I_C_Commission_Share.class);
	}

	@Override
	public void setC_Commission_Share(de.metas.contracts.commission.model.I_C_Commission_Share C_Commission_Share)
	{
		set_ValueFromPO(COLUMNNAME_C_Commission_Share_ID, de.metas.contracts.commission.model.I_C_Commission_Share.class, C_Commission_Share);
	}

	/** Set Buchauszug.
		@param C_Commission_Share_ID Buchauszug	  */
	@Override
	public void setC_Commission_Share_ID (int C_Commission_Share_ID)
	{
		if (C_Commission_Share_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Share_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Share_ID, Integer.valueOf(C_Commission_Share_ID));
	}

	/** Get Buchauszug.
		@return Buchauszug	  */
	@Override
	public int getC_Commission_Share_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Commission_Share_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	/** Set Rechnung.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Rechnung.
		@return Invoice Identifier
	  */
	@Override
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceLine_ID, org.compiere.model.I_C_InvoiceLine.class);
	}

	@Override
	public void setC_InvoiceLine(org.compiere.model.I_C_InvoiceLine C_InvoiceLine)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceLine_ID, org.compiere.model.I_C_InvoiceLine.class, C_InvoiceLine);
	}

	/** Set Rechnungsposition.
		@param C_InvoiceLine_ID 
		Rechnungszeile
	  */
	@Override
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
	}

	/** Get Rechnungsposition.
		@return Rechnungszeile
	  */
	@Override
	public int getC_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
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

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine()
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

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Hierarchie-Ebene.
		@param LevelHierarchy Hierarchie-Ebene	  */
	@Override
	public void setLevelHierarchy (int LevelHierarchy)
	{
		set_ValueNoCheck (COLUMNNAME_LevelHierarchy, Integer.valueOf(LevelHierarchy));
	}

	/** Get Hierarchie-Ebene.
		@return Hierarchie-Ebene	  */
	@Override
	public int getLevelHierarchy () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LevelHierarchy);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Provisionsauslöser Zeitpunkt.
		@param MostRecentTriggerTimestamp Provisionsauslöser Zeitpunkt	  */
	@Override
	public void setMostRecentTriggerTimestamp (java.sql.Timestamp MostRecentTriggerTimestamp)
	{
		set_ValueNoCheck (COLUMNNAME_MostRecentTriggerTimestamp, MostRecentTriggerTimestamp);
	}

	/** Get Provisionsauslöser Zeitpunkt.
		@return Provisionsauslöser Zeitpunkt	  */
	@Override
	public java.sql.Timestamp getMostRecentTriggerTimestamp () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_MostRecentTriggerTimestamp);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	/** Set % der Basispunkte.
		@param PercentOfBasePoints % der Basispunkte	  */
	@Override
	public void setPercentOfBasePoints (java.math.BigDecimal PercentOfBasePoints)
	{
		set_ValueNoCheck (COLUMNNAME_PercentOfBasePoints, PercentOfBasePoints);
	}

	/** Get % der Basispunkte.
		@return % der Basispunkte	  */
	@Override
	public java.math.BigDecimal getPercentOfBasePoints () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PercentOfBasePoints);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Beauftragte Basispunktzahl.
		@param PointsBase_Forecasted Beauftragte Basispunktzahl	  */
	@Override
	public void setPointsBase_Forecasted (java.math.BigDecimal PointsBase_Forecasted)
	{
		set_ValueNoCheck (COLUMNNAME_PointsBase_Forecasted, PointsBase_Forecasted);
	}

	/** Get Beauftragte Basispunktzahl.
		@return Beauftragte Basispunktzahl	  */
	@Override
	public java.math.BigDecimal getPointsBase_Forecasted () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsBase_Forecasted);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Fakturierbare Basispunktzahl.
		@param PointsBase_Invoiceable Fakturierbare Basispunktzahl	  */
	@Override
	public void setPointsBase_Invoiceable (java.math.BigDecimal PointsBase_Invoiceable)
	{
		set_ValueNoCheck (COLUMNNAME_PointsBase_Invoiceable, PointsBase_Invoiceable);
	}

	/** Get Fakturierbare Basispunktzahl.
		@return Fakturierbare Basispunktzahl	  */
	@Override
	public java.math.BigDecimal getPointsBase_Invoiceable () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsBase_Invoiceable);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Fakturierte Basispunktzahl.
		@param PointsBase_Invoiced Fakturierte Basispunktzahl	  */
	@Override
	public void setPointsBase_Invoiced (java.math.BigDecimal PointsBase_Invoiced)
	{
		set_ValueNoCheck (COLUMNNAME_PointsBase_Invoiced, PointsBase_Invoiced);
	}

	/** Get Fakturierte Basispunktzahl.
		@return Fakturierte Basispunktzahl	  */
	@Override
	public java.math.BigDecimal getPointsBase_Invoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsBase_Invoiced);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Abgerechnete Punktzahl.
		@param PointsSum_Settled Abgerechnete Punktzahl	  */
	@Override
	public void setPointsSum_Settled (java.math.BigDecimal PointsSum_Settled)
	{
		set_ValueNoCheck (COLUMNNAME_PointsSum_Settled, PointsSum_Settled);
	}

	/** Get Abgerechnete Punktzahl.
		@return Abgerechnete Punktzahl	  */
	@Override
	public java.math.BigDecimal getPointsSum_Settled () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsSum_Settled);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Abzurechnende Punktzahl.
		@param PointsSum_ToSettle Abzurechnende Punktzahl	  */
	@Override
	public void setPointsSum_ToSettle (java.math.BigDecimal PointsSum_ToSettle)
	{
		set_ValueNoCheck (COLUMNNAME_PointsSum_ToSettle, PointsSum_ToSettle);
	}

	/** Get Abzurechnende Punktzahl.
		@return Abzurechnende Punktzahl	  */
	@Override
	public java.math.BigDecimal getPointsSum_ToSettle () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsSum_ToSettle);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Menge.
		@param QtyEntered 
		Die Eingegebene Menge basiert auf der gewählten Mengeneinheit
	  */
	@Override
	public void setQtyEntered (java.math.BigDecimal QtyEntered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Menge.
		@return Die Eingegebene Menge basiert auf der gewählten Mengeneinheit
	  */
	@Override
	public java.math.BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}