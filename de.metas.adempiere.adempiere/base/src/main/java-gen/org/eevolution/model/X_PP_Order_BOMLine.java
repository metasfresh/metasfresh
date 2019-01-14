/** Generated Model - DO NOT CHANGE */
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_Order_BOMLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PP_Order_BOMLine extends org.compiere.model.PO implements I_PP_Order_BOMLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1183984800L;

    /** Standard Constructor */
    public X_PP_Order_BOMLine (Properties ctx, int PP_Order_BOMLine_ID, String trxName)
    {
      super (ctx, PP_Order_BOMLine_ID, trxName);
      /** if (PP_Order_BOMLine_ID == 0)
        {
			setC_UOM_ID (0);
			setIsCritical (false);
			setLine (0); // @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM PP_Order_BOMLine WHERE PP_Order_ID=@PP_Order_ID@
			setM_Product_ID (0);
			setM_Warehouse_ID (0);
			setPP_Order_BOM_ID (0);
			setPP_Order_BOMLine_ID (0);
			setPP_Order_ID (0);
			setProcessed (false); // N
			setQtyBatch (BigDecimal.ZERO);
			setQtyBeforeClose (BigDecimal.ZERO); // 0
			setQtyBOM (BigDecimal.ZERO);
			setQtyDelivered (BigDecimal.ZERO);
			setQtyDeliveredActual (BigDecimal.ZERO); // 0
			setQtyPost (BigDecimal.ZERO);
			setQtyReject (BigDecimal.ZERO);
			setQtyRequiered (BigDecimal.ZERO);
			setQtyReserved (BigDecimal.ZERO);
			setQtyScrap (BigDecimal.ZERO);
			setQtyUsageVariance (BigDecimal.ZERO); // 0
			setValidFrom (new Timestamp( System.currentTimeMillis() )); // @#Date@
        } */
    }

    /** Load Constructor */
    public X_PP_Order_BOMLine (Properties ctx, ResultSet rs, String trxName)
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
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
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

	/** Set Mengen Probe.
		@param Assay 
		Indicated the Quantity Assay to use into Quality Order
	  */
	@Override
	public void setAssay (java.math.BigDecimal Assay)
	{
		set_ValueNoCheck (COLUMNNAME_Assay, Assay);
	}

	/** Get Mengen Probe.
		@return Indicated the Quantity Assay to use into Quality Order
	  */
	@Override
	public java.math.BigDecimal getAssay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Assay);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Retrograde Gruppe.
		@param BackflushGroup 
		The Grouping Components to the Backflush
	  */
	@Override
	public void setBackflushGroup (java.lang.String BackflushGroup)
	{
		set_ValueNoCheck (COLUMNNAME_BackflushGroup, BackflushGroup);
	}

	/** Get Retrograde Gruppe.
		@return The Grouping Components to the Backflush
	  */
	@Override
	public java.lang.String getBackflushGroup () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BackflushGroup);
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

//	/** 
//	 * ComponentType AD_Reference_ID=53225
//	 * Reference name: PP_ComponentType
//	 */
//	public static final int COMPONENTTYPE_AD_Reference_ID=53225;
//	/** By-Product = BY */
//	public static final String COMPONENTTYPE_By_Product = "BY";
//	/** Component = CO */
//	public static final String COMPONENTTYPE_Component = "CO";
//	/** Phantom = PH */
//	public static final String COMPONENTTYPE_Phantom = "PH";
//	/** Packing = PK */
//	public static final String COMPONENTTYPE_Packing = "PK";
//	/** Planning = PL */
//	public static final String COMPONENTTYPE_Planning = "PL";
//	/** Tools = TL */
//	public static final String COMPONENTTYPE_Tools = "TL";
//	/** Option = OP */
//	public static final String COMPONENTTYPE_Option = "OP";
//	/** Variant = VA */
//	public static final String COMPONENTTYPE_Variant = "VA";
//	/** Co-Product = CP */
//	public static final String COMPONENTTYPE_Co_Product = "CP";
//	/** Scrap = SC */
//	public static final String COMPONENTTYPE_Scrap = "SC";
//	/** Product = PR */
//	public static final String COMPONENTTYPE_Product = "PR";
	/** Set Component Type.
		@param ComponentType 
		Component Type for a Bill of Material or Formula
	  */
	@Override
	public void setComponentType (java.lang.String ComponentType)
	{

		set_Value (COLUMNNAME_ComponentType, ComponentType);
	}

	/** Get Component Type.
		@return Component Type for a Bill of Material or Formula
	  */
	@Override
	public java.lang.String getComponentType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ComponentType);
	}

	/** Set CU Label Qty.
		@param CULabelQuanitity CU Label Qty	  */
	@Override
	public void setCULabelQuanitity (java.lang.String CULabelQuanitity)
	{
		set_Value (COLUMNNAME_CULabelQuanitity, CULabelQuanitity);
	}

	/** Get CU Label Qty.
		@return CU Label Qty	  */
	@Override
	public java.lang.String getCULabelQuanitity () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CULabelQuanitity);
	}

	/** Set Lieferdatum.
		@param DateDelivered 
		Date when the product was delivered
	  */
	@Override
	public void setDateDelivered (java.sql.Timestamp DateDelivered)
	{
		set_Value (COLUMNNAME_DateDelivered, DateDelivered);
	}

	/** Get Lieferdatum.
		@return Date when the product was delivered
	  */
	@Override
	public java.sql.Timestamp getDateDelivered () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateDelivered);
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

	/** Set Feature.
		@param Feature 
		Indicated the Feature for Product Configure
	  */
	@Override
	public void setFeature (java.lang.String Feature)
	{
		set_Value (COLUMNNAME_Feature, Feature);
	}

	/** Get Feature.
		@return Indicated the Feature for Product Configure
	  */
	@Override
	public java.lang.String getFeature () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Feature);
	}

	/** Set Forecast.
		@param Forecast 
		Indicated the % of participation this component into a of the BOM Planning
	  */
	@Override
	public void setForecast (java.math.BigDecimal Forecast)
	{
		set_ValueNoCheck (COLUMNNAME_Forecast, Forecast);
	}

	/** Get Forecast.
		@return Indicated the % of participation this component into a of the BOM Planning
	  */
	@Override
	public java.math.BigDecimal getForecast () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Forecast);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Kommentar/Hilfe.
		@param Help 
		Comment or Hint
	  */
	@Override
	public void setHelp (java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Kommentar/Hilfe.
		@return Comment or Hint
	  */
	@Override
	public java.lang.String getHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	/** Set Is Critical Component.
		@param IsCritical 
		Indicate that a Manufacturing Order can not begin without have this component
	  */
	@Override
	public void setIsCritical (boolean IsCritical)
	{
		set_Value (COLUMNNAME_IsCritical, Boolean.valueOf(IsCritical));
	}

	/** Get Is Critical Component.
		@return Indicate that a Manufacturing Order can not begin without have this component
	  */
	@Override
	public boolean isCritical () 
	{
		Object oo = get_Value(COLUMNNAME_IsCritical);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Qty Percentage.
		@param IsQtyPercentage 
		Indicate that this component is based in % Quantity
	  */
	@Override
	public void setIsQtyPercentage (boolean IsQtyPercentage)
	{
		set_ValueNoCheck (COLUMNNAME_IsQtyPercentage, Boolean.valueOf(IsQtyPercentage));
	}

	/** Get Is Qty Percentage.
		@return Indicate that this component is based in % Quantity
	  */
	@Override
	public boolean isQtyPercentage () 
	{
		Object oo = get_Value(COLUMNNAME_IsQtyPercentage);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * IssueMethod AD_Reference_ID=53226
	 * Reference name: PP_Product_BOM IssueMethod
	 */
	public static final int ISSUEMETHOD_AD_Reference_ID=53226;
	/** Issue = 0 */
	public static final String ISSUEMETHOD_Issue = "0";
	/** Backflush = 1 */
	public static final String ISSUEMETHOD_Backflush = "1";
	/** Floor Stock = 2 */
	public static final String ISSUEMETHOD_FloorStock = "2";
	/** IssueOnlyForReceived = 9 */
	public static final String ISSUEMETHOD_IssueOnlyForReceived = "9";
	/** Set Zuteil Methode.
		@param IssueMethod 
		There are two methods for issue the components to Manufacturing Order
	  */
	@Override
	public void setIssueMethod (java.lang.String IssueMethod)
	{

		set_Value (COLUMNNAME_IssueMethod, IssueMethod);
	}

	/** Get Zuteil Methode.
		@return There are two methods for issue the components to Manufacturing Order
	  */
	@Override
	public java.lang.String getIssueMethod () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IssueMethod);
	}

	/** Set Lead Time Offset.
		@param LeadTimeOffset 
		Optional Lead Time offest before starting production
	  */
	@Override
	public void setLeadTimeOffset (int LeadTimeOffset)
	{
		set_Value (COLUMNNAME_LeadTimeOffset, Integer.valueOf(LeadTimeOffset));
	}

	/** Get Lead Time Offset.
		@return Optional Lead Time offest before starting production
	  */
	@Override
	public int getLeadTimeOffset () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LeadTimeOffset);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zeile Nr..
		@param Line 
		Unique line for this document
	  */
	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
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

	@Override
	public org.compiere.model.I_M_ChangeNotice getM_ChangeNotice() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_ChangeNotice_ID, org.compiere.model.I_M_ChangeNotice.class);
	}

	@Override
	public void setM_ChangeNotice(org.compiere.model.I_M_ChangeNotice M_ChangeNotice)
	{
		set_ValueFromPO(COLUMNNAME_M_ChangeNotice_ID, org.compiere.model.I_M_ChangeNotice.class, M_ChangeNotice);
	}

	/** Set Änderungsmeldung.
		@param M_ChangeNotice_ID 
		Bill of Materials (Engineering) Change Notice (Version)
	  */
	@Override
	public void setM_ChangeNotice_ID (int M_ChangeNotice_ID)
	{
		if (M_ChangeNotice_ID < 1) 
			set_Value (COLUMNNAME_M_ChangeNotice_ID, null);
		else 
			set_Value (COLUMNNAME_M_ChangeNotice_ID, Integer.valueOf(M_ChangeNotice_ID));
	}

	/** Get Änderungsmeldung.
		@return Bill of Materials (Engineering) Change Notice (Version)
	  */
	@Override
	public int getM_ChangeNotice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ChangeNotice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Locator_ID, org.compiere.model.I_M_Locator.class);
	}

	@Override
	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator)
	{
		set_ValueFromPO(COLUMNNAME_M_Locator_ID, org.compiere.model.I_M_Locator.class, M_Locator);
	}

	/** Set Lagerort.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	@Override
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Lagerort.
		@return Warehouse Locator
	  */
	@Override
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
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
		Storage Warehouse and Service Point
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
		@return Storage Warehouse and Service Point
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
	public org.eevolution.model.I_PP_Order_BOM getPP_Order_BOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_BOM_ID, org.eevolution.model.I_PP_Order_BOM.class);
	}

	@Override
	public void setPP_Order_BOM(org.eevolution.model.I_PP_Order_BOM PP_Order_BOM)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_BOM_ID, org.eevolution.model.I_PP_Order_BOM.class, PP_Order_BOM);
	}

	/** Set Manufacturing Order BOM.
		@param PP_Order_BOM_ID Manufacturing Order BOM	  */
	@Override
	public void setPP_Order_BOM_ID (int PP_Order_BOM_ID)
	{
		if (PP_Order_BOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_BOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_BOM_ID, Integer.valueOf(PP_Order_BOM_ID));
	}

	/** Get Manufacturing Order BOM.
		@return Manufacturing Order BOM	  */
	@Override
	public int getPP_Order_BOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_BOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Manufacturing Order BOM Line.
		@param PP_Order_BOMLine_ID Manufacturing Order BOM Line	  */
	@Override
	public void setPP_Order_BOMLine_ID (int PP_Order_BOMLine_ID)
	{
		if (PP_Order_BOMLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_BOMLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_BOMLine_ID, Integer.valueOf(PP_Order_BOMLine_ID));
	}

	/** Get Manufacturing Order BOM Line.
		@return Manufacturing Order BOM Line	  */
	@Override
	public int getPP_Order_BOMLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_BOMLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	/** Set Produktionsauftrag.
		@param PP_Order_ID Produktionsauftrag	  */
	@Override
	public void setPP_Order_ID (int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, Integer.valueOf(PP_Order_ID));
	}

	/** Get Produktionsauftrag.
		@return Produktionsauftrag	  */
	@Override
	public int getPP_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Product_BOMLine getPP_Product_BOMLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Product_BOMLine_ID, org.eevolution.model.I_PP_Product_BOMLine.class);
	}

	@Override
	public void setPP_Product_BOMLine(org.eevolution.model.I_PP_Product_BOMLine PP_Product_BOMLine)
	{
		set_ValueFromPO(COLUMNNAME_PP_Product_BOMLine_ID, org.eevolution.model.I_PP_Product_BOMLine.class, PP_Product_BOMLine);
	}

	/** Set BOM Line.
		@param PP_Product_BOMLine_ID 
		BOM Line
	  */
	@Override
	public void setPP_Product_BOMLine_ID (int PP_Product_BOMLine_ID)
	{
		if (PP_Product_BOMLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOMLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOMLine_ID, Integer.valueOf(PP_Product_BOMLine_ID));
	}

	/** Get BOM Line.
		@return BOM Line
	  */
	@Override
	public int getPP_Product_BOMLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Product_BOMLine_ID);
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

	/** Set Quantity in %.
		@param QtyBatch 
		Indicate the Quantity % use in this Formula
	  */
	@Override
	public void setQtyBatch (java.math.BigDecimal QtyBatch)
	{
		set_ValueNoCheck (COLUMNNAME_QtyBatch, QtyBatch);
	}

	/** Get Quantity in %.
		@return Indicate the Quantity % use in this Formula
	  */
	@Override
	public java.math.BigDecimal getQtyBatch () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBatch);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Qty before close.
		@param QtyBeforeClose Qty before close	  */
	@Override
	public void setQtyBeforeClose (java.math.BigDecimal QtyBeforeClose)
	{
		set_Value (COLUMNNAME_QtyBeforeClose, QtyBeforeClose);
	}

	/** Get Qty before close.
		@return Qty before close	  */
	@Override
	public java.math.BigDecimal getQtyBeforeClose () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBeforeClose);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param QtyBOM 
		Indicate the Quantity  use in this BOM
	  */
	@Override
	public void setQtyBOM (java.math.BigDecimal QtyBOM)
	{
		set_ValueNoCheck (COLUMNNAME_QtyBOM, QtyBOM);
	}

	/** Get Quantity.
		@return Indicate the Quantity  use in this BOM
	  */
	@Override
	public java.math.BigDecimal getQtyBOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBOM);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Gelieferte Menge.
		@param QtyDelivered 
		Delivered Quantity
	  */
	@Override
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	/** Get Gelieferte Menge.
		@return Delivered Quantity
	  */
	@Override
	public java.math.BigDecimal getQtyDelivered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Tatsächlich gelieferte Menge.
		@param QtyDeliveredActual 
		Tatsächlich gelieferte Menge
	  */
	@Override
	public void setQtyDeliveredActual (java.math.BigDecimal QtyDeliveredActual)
	{
		set_ValueNoCheck (COLUMNNAME_QtyDeliveredActual, QtyDeliveredActual);
	}

	/** Get Tatsächlich gelieferte Menge.
		@return Tatsächlich gelieferte Menge
	  */
	@Override
	public java.math.BigDecimal getQtyDeliveredActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDeliveredActual);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Menge.
		@param QtyEntered 
		The Quantity Entered is based on the selected UoM
	  */
	@Override
	public void setQtyEntered (java.math.BigDecimal QtyEntered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Menge.
		@return The Quantity Entered is based on the selected UoM
	  */
	@Override
	public java.math.BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Qty Post.
		@param QtyPost Qty Post	  */
	@Override
	public void setQtyPost (java.math.BigDecimal QtyPost)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPost, QtyPost);
	}

	/** Get Qty Post.
		@return Qty Post	  */
	@Override
	public java.math.BigDecimal getQtyPost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPost);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Qty Reject.
		@param QtyReject Qty Reject	  */
	@Override
	public void setQtyReject (java.math.BigDecimal QtyReject)
	{
		set_ValueNoCheck (COLUMNNAME_QtyReject, QtyReject);
	}

	/** Get Qty Reject.
		@return Qty Reject	  */
	@Override
	public java.math.BigDecimal getQtyReject () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReject);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Menge angefragt.
		@param QtyRequiered Menge angefragt	  */
	@Override
	public void setQtyRequiered (java.math.BigDecimal QtyRequiered)
	{
		set_Value (COLUMNNAME_QtyRequiered, QtyRequiered);
	}

	/** Get Menge angefragt.
		@return Menge angefragt	  */
	@Override
	public java.math.BigDecimal getQtyRequiered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyRequiered);
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
		set_ValueNoCheck (COLUMNNAME_QtyReserved, QtyReserved);
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

	/** Set QtyScrap.
		@param QtyScrap 
		Scrap Quantity for this componet
	  */
	@Override
	public void setQtyScrap (java.math.BigDecimal QtyScrap)
	{
		set_ValueNoCheck (COLUMNNAME_QtyScrap, QtyScrap);
	}

	/** Get QtyScrap.
		@return Scrap Quantity for this componet
	  */
	@Override
	public java.math.BigDecimal getQtyScrap () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyScrap);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set abweichende menge.
		@param QtyUsageVariance abweichende menge	  */
	@Override
	public void setQtyUsageVariance (java.math.BigDecimal QtyUsageVariance)
	{
		set_Value (COLUMNNAME_QtyUsageVariance, QtyUsageVariance);
	}

	/** Get abweichende menge.
		@return abweichende menge	  */
	@Override
	public java.math.BigDecimal getQtyUsageVariance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUsageVariance);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set % Scrap.
		@param Scrap 
		Indicate the % Scrap  for calculate the Scrap Quantity
	  */
	@Override
	public void setScrap (java.math.BigDecimal Scrap)
	{
		set_ValueNoCheck (COLUMNNAME_Scrap, Scrap);
	}

	/** Get % Scrap.
		@return Indicate the % Scrap  for calculate the Scrap Quantity
	  */
	@Override
	public java.math.BigDecimal getScrap () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Scrap);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Show Sub BOM Ingredients.
		@param ShowSubBOMIngredients Show Sub BOM Ingredients	  */
	@Override
	public void setShowSubBOMIngredients (boolean ShowSubBOMIngredients)
	{
		set_Value (COLUMNNAME_ShowSubBOMIngredients, Boolean.valueOf(ShowSubBOMIngredients));
	}

	/** Get Show Sub BOM Ingredients.
		@return Show Sub BOM Ingredients	  */
	@Override
	public boolean isShowSubBOMIngredients () 
	{
		Object oo = get_Value(COLUMNNAME_ShowSubBOMIngredients);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Gültig ab.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gültig ab.
		@return Valid from including this date (first day)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Gültig bis.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	@Override
	public void setValidTo (java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Gültig bis.
		@return Valid to including this date (last day)
	  */
	@Override
	public java.sql.Timestamp getValidTo () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidTo);
	}

	/** 
	 * VariantGroup AD_Reference_ID=540490
	 * Reference name: VariantGroup
	 */
	public static final int VARIANTGROUP_AD_Reference_ID=540490;
	/** 01 = 01 */
	public static final String VARIANTGROUP_01 = "01";
	/** 02 = 02 */
	public static final String VARIANTGROUP_02 = "02";
	/** 03 = 03 */
	public static final String VARIANTGROUP_03 = "03";
	/** 04 = 04 */
	public static final String VARIANTGROUP_04 = "04";
	/** 05 = 05 */
	public static final String VARIANTGROUP_05 = "05";
	/** 06 = 06 */
	public static final String VARIANTGROUP_06 = "06";
	/** 07 = 07 */
	public static final String VARIANTGROUP_07 = "07";
	/** 08 = 08 */
	public static final String VARIANTGROUP_08 = "08";
	/** 09 = 09 */
	public static final String VARIANTGROUP_09 = "09";
	/** Set Varianten Gruppe.
		@param VariantGroup Varianten Gruppe	  */
	@Override
	public void setVariantGroup (java.lang.String VariantGroup)
	{

		set_Value (COLUMNNAME_VariantGroup, VariantGroup);
	}

	/** Get Varianten Gruppe.
		@return Varianten Gruppe	  */
	@Override
	public java.lang.String getVariantGroup () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VariantGroup);
	}
}