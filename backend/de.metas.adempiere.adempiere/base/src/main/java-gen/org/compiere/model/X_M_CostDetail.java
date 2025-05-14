/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_CostDetail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_CostDetail extends org.compiere.model.PO implements I_M_CostDetail, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1642738087L;

    /** Standard Constructor */
    public X_M_CostDetail (Properties ctx, int M_CostDetail_ID, String trxName)
    {
      super (ctx, M_CostDetail_ID, trxName);
      /** if (M_CostDetail_ID == 0)
        {
			setAmt (BigDecimal.ZERO);
			setC_AcctSchema_ID (0);
			setC_Currency_ID (0);
			setC_UOM_ID (0);
			setIsChangingCosts (false); // N
			setIsSOTrx (false);
			setM_AttributeSetInstance_ID (0);
			setM_CostDetail_ID (0);
			setM_Product_ID (0);
			setPrev_CumulatedAmt (BigDecimal.ZERO); // 0
			setPrev_CumulatedQty (BigDecimal.ZERO); // 0
			setPrev_CurrentCostPrice (BigDecimal.ZERO); // 0
			setPrev_CurrentCostPriceLL (BigDecimal.ZERO); // 0
			setPrev_CurrentQty (BigDecimal.ZERO); // 0
			setProcessed (false);
			setQty (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_CostDetail (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Betrag.
		@param Amt 
		Amount
	  */
	@Override
	public void setAmt (java.math.BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Betrag.
		@return Amount
	  */
	@Override
	public java.math.BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	/** Set Buchführungs-Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	@Override
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Buchführungs-Schema.
		@return Rules for accounting
	  */
	@Override
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException
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
		Invoice Detail Line
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
		@return Invoice Detail Line
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
		Sales Order Line
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
		@return Sales Order Line
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
	public org.compiere.model.I_C_ProjectIssue getC_ProjectIssue() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_ProjectIssue_ID, org.compiere.model.I_C_ProjectIssue.class);
	}

	@Override
	public void setC_ProjectIssue(org.compiere.model.I_C_ProjectIssue C_ProjectIssue)
	{
		set_ValueFromPO(COLUMNNAME_C_ProjectIssue_ID, org.compiere.model.I_C_ProjectIssue.class, C_ProjectIssue);
	}

	/** Set Projekt-Problem.
		@param C_ProjectIssue_ID 
		Project Issues (Material, Labor)
	  */
	@Override
	public void setC_ProjectIssue_ID (int C_ProjectIssue_ID)
	{
		if (C_ProjectIssue_ID < 1) 
			set_Value (COLUMNNAME_C_ProjectIssue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ProjectIssue_ID, Integer.valueOf(C_ProjectIssue_ID));
	}

	/** Get Projekt-Problem.
		@return Project Issues (Material, Labor)
	  */
	@Override
	public int getC_ProjectIssue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ProjectIssue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
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

	@Override
	public void setDateAcct (final java.sql.Timestamp DateAcct)
	{
		set_ValueNoCheck (COLUMNNAME_DateAcct, DateAcct);
	}

	@Override
	public java.sql.Timestamp getDateAcct()
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcct);
	}

	/** Set Delta Amount.
		@param DeltaAmt 
		Difference Amount
	  */
	@Override
	public void setDeltaAmt (java.math.BigDecimal DeltaAmt)
	{
		set_Value (COLUMNNAME_DeltaAmt, DeltaAmt);
	}

	/** Get Delta Amount.
		@return Difference Amount
	  */
	@Override
	public java.math.BigDecimal getDeltaAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DeltaAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Delta Quantity.
		@param DeltaQty 
		Quantity Difference
	  */
	@Override
	public void setDeltaQty (java.math.BigDecimal DeltaQty)
	{
		set_Value (COLUMNNAME_DeltaQty, DeltaQty);
	}

	/** Get Delta Quantity.
		@return Quantity Difference
	  */
	@Override
	public java.math.BigDecimal getDeltaQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DeltaQty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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

	/** Set Changing costs.
		@param IsChangingCosts 
		Set if this record is changing the costs.
	  */
	@Override
	public void setIsChangingCosts (boolean IsChangingCosts)
	{
		set_Value (COLUMNNAME_IsChangingCosts, Boolean.valueOf(IsChangingCosts));
	}

	/** Get Changing costs.
		@return Set if this record is changing the costs.
	  */
	@Override
	public boolean isChangingCosts () 
	{
		Object oo = get_Value(COLUMNNAME_IsChangingCosts);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verkaufs-Transaktion.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	@Override
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Verkaufs-Transaktion.
		@return This is a Sales Transaction
	  */
	@Override
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	/** Set Merkmale.
		@param M_AttributeSetInstance_ID 
		Merkmals Ausprägungen zum Produkt
	  */
	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Merkmale.
		@return Merkmals Ausprägungen zum Produkt
	  */
	@Override
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kosten-Detail.
		@param M_CostDetail_ID 
		Cost Detail Information
	  */
	@Override
	public void setM_CostDetail_ID (int M_CostDetail_ID)
	{
		if (M_CostDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostDetail_ID, Integer.valueOf(M_CostDetail_ID));
	}

	/** Get Kosten-Detail.
		@return Cost Detail Information
	  */
	@Override
	public int getM_CostDetail_ID () 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostDetail_ID);
	}

	/** 
	 * M_CostDetail_Type AD_Reference_ID=541722
	 * Reference name: M_CostDetail_Type
	 */
	public static final int M_COSTDETAIL_TYPE_AD_Reference_ID=541722;
	/** Main = M */
	public static final String M_COSTDETAIL_TYPE_Main = "M";
	/** Cost Adjustment = A */
	public static final String M_COSTDETAIL_TYPE_CostAdjustment = "A";
	/** Already Shipped = S */
	public static final String M_COSTDETAIL_TYPE_AlreadyShipped = "S";
	@Override
	public void setM_CostDetail_Type (final java.lang.String M_CostDetail_Type)
	{
		set_Value (COLUMNNAME_M_CostDetail_Type, M_CostDetail_Type);
	}

	@Override
	public java.lang.String getM_CostDetail_Type() 
	{
		return get_ValueAsString(COLUMNNAME_M_CostDetail_Type);
	}

	@Override
	public org.compiere.model.I_M_CostElement getM_CostElement() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class);
	}

	@Override
	public void setM_CostElement(org.compiere.model.I_M_CostElement M_CostElement)
	{
		set_ValueFromPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class, M_CostElement);
	}

	/** Set Kostenart.
		@param M_CostElement_ID 
		Product Cost Element
	  */
	@Override
	public void setM_CostElement_ID (int M_CostElement_ID)
	{
		if (M_CostElement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, Integer.valueOf(M_CostElement_ID));
	}

	/** Get Kostenart.
		@return Product Cost Element
	  */
	@Override
	public int getM_CostElement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_CostElement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}


	@Override
	public org.compiere.model.I_M_CostRevaluation getM_CostRevaluation()
	{
		return get_ValueAsPO(COLUMNNAME_M_CostRevaluation_ID, org.compiere.model.I_M_CostRevaluation.class);
	}

	@Override
	public void setM_CostRevaluation(final org.compiere.model.I_M_CostRevaluation M_CostRevaluation)
	{
		set_ValueFromPO(COLUMNNAME_M_CostRevaluation_ID, org.compiere.model.I_M_CostRevaluation.class, M_CostRevaluation);
	}

	@Override
	public void setM_CostRevaluation_ID (final int M_CostRevaluation_ID)
	{
		if (M_CostRevaluation_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_CostRevaluation_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_CostRevaluation_ID, M_CostRevaluation_ID);
	}

	@Override
	public int getM_CostRevaluation_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_CostRevaluation_ID);
	}

	@Override
	public org.compiere.model.I_M_CostRevaluationLine getM_CostRevaluationLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_CostRevaluationLine_ID, org.compiere.model.I_M_CostRevaluationLine.class);
	}

	@Override
	public void setM_CostRevaluationLine(final org.compiere.model.I_M_CostRevaluationLine M_CostRevaluationLine)
	{
		set_ValueFromPO(COLUMNNAME_M_CostRevaluationLine_ID, org.compiere.model.I_M_CostRevaluationLine.class, M_CostRevaluationLine);
	}

	@Override
	public void setM_CostRevaluationLine_ID (final int M_CostRevaluationLine_ID)
	{
		if (M_CostRevaluationLine_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_CostRevaluationLine_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_CostRevaluationLine_ID, M_CostRevaluationLine_ID);
	}

	@Override
	public int getM_CostRevaluationLine_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_CostRevaluationLine_ID);
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	/** Set Versand-/Wareneingangsposition.
		@param M_InOutLine_ID 
		Line on Shipment or Receipt document
	  */
	@Override
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Versand-/Wareneingangsposition.
		@return Line on Shipment or Receipt document
	  */
	@Override
	public int getM_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_InventoryLine getM_InventoryLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_InventoryLine_ID, org.compiere.model.I_M_InventoryLine.class);
	}

	@Override
	public void setM_InventoryLine(org.compiere.model.I_M_InventoryLine M_InventoryLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InventoryLine_ID, org.compiere.model.I_M_InventoryLine.class, M_InventoryLine);
	}

	/** Set Inventur-Position.
		@param M_InventoryLine_ID 
		Unique line in an Inventory document
	  */
	@Override
	public void setM_InventoryLine_ID (int M_InventoryLine_ID)
	{
		if (M_InventoryLine_ID < 1) 
			set_Value (COLUMNNAME_M_InventoryLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InventoryLine_ID, Integer.valueOf(M_InventoryLine_ID));
	}

	/** Get Inventur-Position.
		@return Unique line in an Inventory document
	  */
	@Override
	public int getM_InventoryLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InventoryLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_MatchInv getM_MatchInv() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_MatchInv_ID, org.compiere.model.I_M_MatchInv.class);
	}

	@Override
	public void setM_MatchInv(org.compiere.model.I_M_MatchInv M_MatchInv)
	{
		set_ValueFromPO(COLUMNNAME_M_MatchInv_ID, org.compiere.model.I_M_MatchInv.class, M_MatchInv);
	}

	/** Set Abgleich Rechnung.
		@param M_MatchInv_ID 
		Match Shipment/Receipt to Invoice
	  */
	@Override
	public void setM_MatchInv_ID (int M_MatchInv_ID)
	{
		if (M_MatchInv_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_MatchInv_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_MatchInv_ID, Integer.valueOf(M_MatchInv_ID));
	}

	/** Get Abgleich Rechnung.
		@return Match Shipment/Receipt to Invoice
	  */
	@Override
	public int getM_MatchInv_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_MatchInv_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_MatchPO getM_MatchPO() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_MatchPO_ID, org.compiere.model.I_M_MatchPO.class);
	}

	@Override
	public void setM_MatchPO(org.compiere.model.I_M_MatchPO M_MatchPO)
	{
		set_ValueFromPO(COLUMNNAME_M_MatchPO_ID, org.compiere.model.I_M_MatchPO.class, M_MatchPO);
	}

	/** Set Abgleich Bestellung.
		@param M_MatchPO_ID 
		Match Purchase Order to Shipment/Receipt and Invoice
	  */
	@Override
	public void setM_MatchPO_ID (int M_MatchPO_ID)
	{
		if (M_MatchPO_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_MatchPO_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_MatchPO_ID, Integer.valueOf(M_MatchPO_ID));
	}

	/** Get Abgleich Bestellung.
		@return Match Purchase Order to Shipment/Receipt and Invoice
	  */
	@Override
	public int getM_MatchPO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_MatchPO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_MovementLine getM_MovementLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_MovementLine_ID, org.compiere.model.I_M_MovementLine.class);
	}

	@Override
	public void setM_MovementLine(org.compiere.model.I_M_MovementLine M_MovementLine)
	{
		set_ValueFromPO(COLUMNNAME_M_MovementLine_ID, org.compiere.model.I_M_MovementLine.class, M_MovementLine);
	}

	/** Set Warenbewegungs- Position.
		@param M_MovementLine_ID 
		Inventory Move document Line
	  */
	@Override
	public void setM_MovementLine_ID (int M_MovementLine_ID)
	{
		if (M_MovementLine_ID < 1) 
			set_Value (COLUMNNAME_M_MovementLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_MovementLine_ID, Integer.valueOf(M_MovementLine_ID));
	}

	/** Get Warenbewegungs- Position.
		@return Inventory Move document Line
	  */
	@Override
	public int getM_MovementLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_MovementLine_ID);
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

	@Override
	public org.eevolution.model.I_PP_Cost_Collector getPP_Cost_Collector() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Cost_Collector_ID, org.eevolution.model.I_PP_Cost_Collector.class);
	}

	@Override
	public void setPP_Cost_Collector(org.eevolution.model.I_PP_Cost_Collector PP_Cost_Collector)
	{
		set_ValueFromPO(COLUMNNAME_PP_Cost_Collector_ID, org.eevolution.model.I_PP_Cost_Collector.class, PP_Cost_Collector);
	}

	/** Set Manufacturing Cost Collector.
		@param PP_Cost_Collector_ID Manufacturing Cost Collector	  */
	@Override
	public void setPP_Cost_Collector_ID (int PP_Cost_Collector_ID)
	{
		if (PP_Cost_Collector_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Cost_Collector_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Cost_Collector_ID, Integer.valueOf(PP_Cost_Collector_ID));
	}

	/** Get Manufacturing Cost Collector.
		@return Manufacturing Cost Collector	  */
	@Override
	public int getPP_Cost_Collector_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Cost_Collector_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Previous Cumulated Amount.
		@param Prev_CumulatedAmt Previous Cumulated Amount	  */
	@Override
	public void setPrev_CumulatedAmt (java.math.BigDecimal Prev_CumulatedAmt)
	{
		set_Value (COLUMNNAME_Prev_CumulatedAmt, Prev_CumulatedAmt);
	}

	/** Get Previous Cumulated Amount.
		@return Previous Cumulated Amount	  */
	@Override
	public java.math.BigDecimal getPrev_CumulatedAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_CumulatedAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Previous Cumulated Quantity.
		@param Prev_CumulatedQty Previous Cumulated Quantity	  */
	@Override
	public void setPrev_CumulatedQty (java.math.BigDecimal Prev_CumulatedQty)
	{
		set_Value (COLUMNNAME_Prev_CumulatedQty, Prev_CumulatedQty);
	}

	/** Get Previous Cumulated Quantity.
		@return Previous Cumulated Quantity	  */
	@Override
	public java.math.BigDecimal getPrev_CumulatedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_CumulatedQty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Previous Current Cost Price.
		@param Prev_CurrentCostPrice Previous Current Cost Price	  */
	@Override
	public void setPrev_CurrentCostPrice (java.math.BigDecimal Prev_CurrentCostPrice)
	{
		set_Value (COLUMNNAME_Prev_CurrentCostPrice, Prev_CurrentCostPrice);
	}

	/** Get Previous Current Cost Price.
		@return Previous Current Cost Price	  */
	@Override
	public java.math.BigDecimal getPrev_CurrentCostPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_CurrentCostPrice);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Previous Current Cost Price LL.
		@param Prev_CurrentCostPriceLL Previous Current Cost Price LL	  */
	@Override
	public void setPrev_CurrentCostPriceLL (java.math.BigDecimal Prev_CurrentCostPriceLL)
	{
		set_Value (COLUMNNAME_Prev_CurrentCostPriceLL, Prev_CurrentCostPriceLL);
	}

	/** Get Previous Current Cost Price LL.
		@return Previous Current Cost Price LL	  */
	@Override
	public java.math.BigDecimal getPrev_CurrentCostPriceLL () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_CurrentCostPriceLL);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Previous Current Qty.
		@param Prev_CurrentQty Previous Current Qty	  */
	@Override
	public void setPrev_CurrentQty (java.math.BigDecimal Prev_CurrentQty)
	{
		set_Value (COLUMNNAME_Prev_CurrentQty, Prev_CurrentQty);
	}

	/** Get Previous Current Qty.
		@return Previous Current Qty	  */
	@Override
	public java.math.BigDecimal getPrev_CurrentQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_CurrentQty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Preis.
		@param Price 
		Price
	  */
	@Override
	public void setPrice (java.math.BigDecimal Price)
	{
		throw new IllegalArgumentException ("Price is virtual column");	}

	/** Get Preis.
		@return Price
	  */
	@Override
	public java.math.BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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

	/** Set Menge.
		@param Qty 
		Quantity
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Quantity
	  */
	@Override
	public java.math.BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public void setSourceAmt (final @Nullable BigDecimal SourceAmt)
	{
		set_Value (COLUMNNAME_SourceAmt, SourceAmt);
	}

	@Override
	public BigDecimal getSourceAmt()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_SourceAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSource_Currency_ID (final int Source_Currency_ID)
	{
		if (Source_Currency_ID < 1)
			set_Value (COLUMNNAME_Source_Currency_ID, null);
		else
			set_Value (COLUMNNAME_Source_Currency_ID, Source_Currency_ID);
	}

	@Override
	public int getSource_Currency_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Source_Currency_ID);
	}
}