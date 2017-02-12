/** Generated Model - DO NOT CHANGE */
package de.metas.rfq.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for RV_C_RfQResponse
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_RV_C_RfQResponse extends org.compiere.model.PO implements I_RV_C_RfQResponse, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1463987480L;

    /** Standard Constructor */
    public X_RV_C_RfQResponse (Properties ctx, int RV_C_RfQResponse_ID, String trxName)
    {
      super (ctx, RV_C_RfQResponse_ID, trxName);
      /** if (RV_C_RfQResponse_ID == 0)
        {
			setBenchmarkPrice (Env.ZERO);
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_Currency_ID (0);
			setC_RfQ_ID (0);
			setC_RfQ_Topic_ID (0);
			setC_RfQResponse_ID (0);
			setC_UOM_ID (0);
			setIsSelfService (false);
			setLine (0);
			setPrice (Env.ZERO);
			setQty (Env.ZERO);
			setQtyPrice (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_RV_C_RfQResponse (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User(org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Benchmark Difference.
		@param BenchmarkDifference 
		Difference between Response Price and Benchmark Price
	  */
	@Override
	public void setBenchmarkDifference (java.math.BigDecimal BenchmarkDifference)
	{
		set_ValueNoCheck (COLUMNNAME_BenchmarkDifference, BenchmarkDifference);
	}

	/** Get Benchmark Difference.
		@return Difference between Response Price and Benchmark Price
	  */
	@Override
	public java.math.BigDecimal getBenchmarkDifference () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BenchmarkDifference);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Benchmark Price.
		@param BenchmarkPrice 
		Price to compare responses to
	  */
	@Override
	public void setBenchmarkPrice (java.math.BigDecimal BenchmarkPrice)
	{
		set_ValueNoCheck (COLUMNNAME_BenchmarkPrice, BenchmarkPrice);
	}

	/** Get Benchmark Price.
		@return Price to compare responses to
	  */
	@Override
	public java.math.BigDecimal getBenchmarkPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BenchmarkPrice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
		Identifies a Business Partner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Identifies a Business Partner
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
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifies the (ship to) address for this Business Partner
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
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
		The Currency for this record
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return The Currency for this record
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
	public de.metas.rfq.model.I_C_RfQ getC_RfQ() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RfQ_ID, de.metas.rfq.model.I_C_RfQ.class);
	}

	@Override
	public void setC_RfQ(de.metas.rfq.model.I_C_RfQ C_RfQ)
	{
		set_ValueFromPO(COLUMNNAME_C_RfQ_ID, de.metas.rfq.model.I_C_RfQ.class, C_RfQ);
	}

	/** Set Ausschreibung.
		@param C_RfQ_ID 
		Request for Quotation
	  */
	@Override
	public void setC_RfQ_ID (int C_RfQ_ID)
	{
		if (C_RfQ_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_ID, Integer.valueOf(C_RfQ_ID));
	}

	/** Get Ausschreibung.
		@return Request for Quotation
	  */
	@Override
	public int getC_RfQ_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQ_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.rfq.model.I_C_RfQ_Topic getC_RfQ_Topic() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RfQ_Topic_ID, de.metas.rfq.model.I_C_RfQ_Topic.class);
	}

	@Override
	public void setC_RfQ_Topic(de.metas.rfq.model.I_C_RfQ_Topic C_RfQ_Topic)
	{
		set_ValueFromPO(COLUMNNAME_C_RfQ_Topic_ID, de.metas.rfq.model.I_C_RfQ_Topic.class, C_RfQ_Topic);
	}

	/** Set Ausschreibungs-Thema.
		@param C_RfQ_Topic_ID 
		Topic for Request for Quotations
	  */
	@Override
	public void setC_RfQ_Topic_ID (int C_RfQ_Topic_ID)
	{
		if (C_RfQ_Topic_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_Topic_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_Topic_ID, Integer.valueOf(C_RfQ_Topic_ID));
	}

	/** Get Ausschreibungs-Thema.
		@return Topic for Request for Quotations
	  */
	@Override
	public int getC_RfQ_Topic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQ_Topic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.rfq.model.I_C_RfQResponse getC_RfQResponse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RfQResponse_ID, de.metas.rfq.model.I_C_RfQResponse.class);
	}

	@Override
	public void setC_RfQResponse(de.metas.rfq.model.I_C_RfQResponse C_RfQResponse)
	{
		set_ValueFromPO(COLUMNNAME_C_RfQResponse_ID, de.metas.rfq.model.I_C_RfQResponse.class, C_RfQResponse);
	}

	/** Set Ausschreibungs-Antwort.
		@param C_RfQResponse_ID 
		Request for Quotation Response from a potential Vendor
	  */
	@Override
	public void setC_RfQResponse_ID (int C_RfQResponse_ID)
	{
		if (C_RfQResponse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQResponse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQResponse_ID, Integer.valueOf(C_RfQResponse_ID));
	}

	/** Get Ausschreibungs-Antwort.
		@return Request for Quotation Response from a potential Vendor
	  */
	@Override
	public int getC_RfQResponse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQResponse_ID);
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
		Unit of Measure
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
		@return Unit of Measure
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Antwort-datum.
		@param DateResponse 
		Date of the Response
	  */
	@Override
	public void setDateResponse (java.sql.Timestamp DateResponse)
	{
		set_ValueNoCheck (COLUMNNAME_DateResponse, DateResponse);
	}

	/** Get Antwort-datum.
		@return Date of the Response
	  */
	@Override
	public java.sql.Timestamp getDateResponse () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateResponse);
	}

	/** Set Arbeit fertiggestellt.
		@param DateWorkComplete 
		Date when work is (planned to be) complete
	  */
	@Override
	public void setDateWorkComplete (java.sql.Timestamp DateWorkComplete)
	{
		set_ValueNoCheck (COLUMNNAME_DateWorkComplete, DateWorkComplete);
	}

	/** Get Arbeit fertiggestellt.
		@return Date when work is (planned to be) complete
	  */
	@Override
	public java.sql.Timestamp getDateWorkComplete () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateWorkComplete);
	}

	/** Set Arbeitsbeginn.
		@param DateWorkStart 
		Date when work is (planned to be) started
	  */
	@Override
	public void setDateWorkStart (java.sql.Timestamp DateWorkStart)
	{
		set_ValueNoCheck (COLUMNNAME_DateWorkStart, DateWorkStart);
	}

	/** Get Arbeitsbeginn.
		@return Date when work is (planned to be) started
	  */
	@Override
	public java.sql.Timestamp getDateWorkStart () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateWorkStart);
	}

	/** Set Auslieferungstage.
		@param DeliveryDays 
		Number of Days (planned) until Delivery
	  */
	@Override
	public void setDeliveryDays (int DeliveryDays)
	{
		set_ValueNoCheck (COLUMNNAME_DeliveryDays, Integer.valueOf(DeliveryDays));
	}

	/** Get Auslieferungstage.
		@return Number of Days (planned) until Delivery
	  */
	@Override
	public int getDeliveryDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DeliveryDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_ValueNoCheck (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Rabatt %.
		@param Discount 
		Discount in percent
	  */
	@Override
	public void setDiscount (java.math.BigDecimal Discount)
	{
		set_ValueNoCheck (COLUMNNAME_Discount, Discount);
	}

	/** Get Rabatt %.
		@return Discount in percent
	  */
	@Override
	public java.math.BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Kommentar/Hilfe.
		@param Help 
		Comment or Hint
	  */
	@Override
	public void setHelp (java.lang.String Help)
	{
		set_ValueNoCheck (COLUMNNAME_Help, Help);
	}

	/** Get Kommentar/Hilfe.
		@return Comment or Hint
	  */
	@Override
	public java.lang.String getHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	/** Set Selbstbedienung.
		@param IsSelfService 
		This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	@Override
	public void setIsSelfService (boolean IsSelfService)
	{
		set_ValueNoCheck (COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	/** Get Selbstbedienung.
		@return This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	@Override
	public boolean isSelfService () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelfService);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zeile Nr..
		@param Line 
		Unique line for this document
	  */
	@Override
	public void setLine (int Line)
	{
		set_ValueNoCheck (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Zeile Nr..
		@return Unique line for this document
	  */
	@Override
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Line Work Complete.
		@param LineDateWorkComplete 
		Date when line work is (planned to be) complete
	  */
	@Override
	public void setLineDateWorkComplete (java.sql.Timestamp LineDateWorkComplete)
	{
		set_ValueNoCheck (COLUMNNAME_LineDateWorkComplete, LineDateWorkComplete);
	}

	/** Get Line Work Complete.
		@return Date when line work is (planned to be) complete
	  */
	@Override
	public java.sql.Timestamp getLineDateWorkComplete () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_LineDateWorkComplete);
	}

	/** Set Line Work Start.
		@param LineDateWorkStart 
		Date when line work is (planned to be) started
	  */
	@Override
	public void setLineDateWorkStart (java.sql.Timestamp LineDateWorkStart)
	{
		set_ValueNoCheck (COLUMNNAME_LineDateWorkStart, LineDateWorkStart);
	}

	/** Get Line Work Start.
		@return Date when line work is (planned to be) started
	  */
	@Override
	public java.sql.Timestamp getLineDateWorkStart () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_LineDateWorkStart);
	}

	/** Set Line Delivery Days.
		@param LineDeliveryDays Line Delivery Days	  */
	@Override
	public void setLineDeliveryDays (int LineDeliveryDays)
	{
		set_ValueNoCheck (COLUMNNAME_LineDeliveryDays, Integer.valueOf(LineDeliveryDays));
	}

	/** Get Line Delivery Days.
		@return Line Delivery Days	  */
	@Override
	public int getLineDeliveryDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LineDeliveryDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Positions-Beschreibung.
		@param LineDescription 
		Description of the Line
	  */
	@Override
	public void setLineDescription (java.lang.String LineDescription)
	{
		set_ValueNoCheck (COLUMNNAME_LineDescription, LineDescription);
	}

	/** Get Positions-Beschreibung.
		@return Description of the Line
	  */
	@Override
	public java.lang.String getLineDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LineDescription);
	}

	/** Set Line Help/Comment.
		@param LineHelp Line Help/Comment	  */
	@Override
	public void setLineHelp (java.lang.String LineHelp)
	{
		set_ValueNoCheck (COLUMNNAME_LineHelp, LineHelp);
	}

	/** Get Line Help/Comment.
		@return Line Help/Comment	  */
	@Override
	public java.lang.String getLineHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LineHelp);
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

	/** Set Ausprägung Merkmals-Satz.
		@param M_AttributeSetInstance_ID 
		Product Attribute Set Instance
	  */
	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Ausprägung Merkmals-Satz.
		@return Product Attribute Set Instance
	  */
	@Override
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
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

	/** Set Preis.
		@param Price 
		Price
	  */
	@Override
	public void setPrice (java.math.BigDecimal Price)
	{
		set_ValueNoCheck (COLUMNNAME_Price, Price);
	}

	/** Get Preis.
		@return Price
	  */
	@Override
	public java.math.BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Menge.
		@param Qty 
		Quantity
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_ValueNoCheck (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Quantity
	  */
	@Override
	public java.math.BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Price.
		@param QtyPrice Quantity Price	  */
	@Override
	public void setQtyPrice (java.math.BigDecimal QtyPrice)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPrice, QtyPrice);
	}

	/** Get Quantity Price.
		@return Quantity Price	  */
	@Override
	public java.math.BigDecimal getQtyPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPrice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Ranking.
		@param QtyRanking Quantity Ranking	  */
	@Override
	public void setQtyRanking (int QtyRanking)
	{
		set_ValueNoCheck (COLUMNNAME_QtyRanking, Integer.valueOf(QtyRanking));
	}

	/** Get Quantity Ranking.
		@return Quantity Ranking	  */
	@Override
	public int getQtyRanking () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyRanking);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ranking.
		@param Ranking 
		Relative Rank Number
	  */
	@Override
	public void setRanking (int Ranking)
	{
		set_ValueNoCheck (COLUMNNAME_Ranking, Integer.valueOf(Ranking));
	}

	/** Get Ranking.
		@return Relative Rank Number
	  */
	@Override
	public int getRanking () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ranking);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}