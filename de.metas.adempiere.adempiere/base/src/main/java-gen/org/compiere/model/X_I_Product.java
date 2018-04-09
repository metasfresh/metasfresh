/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_Product
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_Product extends org.compiere.model.PO implements I_I_Product, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1009256563L;

    /** Standard Constructor */
    public X_I_Product (Properties ctx, int I_Product_ID, String trxName)
    {
      super (ctx, I_Product_ID, trxName);
      /** if (I_Product_ID == 0)
        {
			setI_IsImported (false);
			setI_Product_ID (0);
			setIsSold (false); // N
			setIsStocked (false); // N
        } */
    }

    /** Load Constructor */
    public X_I_Product (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Geschäftspartner-Schlüssel.
		@param BPartner_Value 
		The Key of the Business Partner
	  */
	@Override
	public void setBPartner_Value (java.lang.String BPartner_Value)
	{
		set_Value (COLUMNNAME_BPartner_Value, BPartner_Value);
	}

	/** Get Geschäftspartner-Schlüssel.
		@return The Key of the Business Partner
	  */
	@Override
	public java.lang.String getBPartner_Value () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartner_Value);
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
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
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
	public org.compiere.model.I_C_DataImport getC_DataImport() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	/** Set Data import.
		@param C_DataImport_ID Data import	  */
	@Override
	public void setC_DataImport_ID (int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DataImport_ID, Integer.valueOf(C_DataImport_ID));
	}

	/** Get Data import.
		@return Data import	  */
	@Override
	public int getC_DataImport_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DataImport_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_TaxCategory getC_TaxCategory() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_TaxCategory_ID, org.compiere.model.I_C_TaxCategory.class);
	}

	@Override
	public void setC_TaxCategory(org.compiere.model.I_C_TaxCategory C_TaxCategory)
	{
		set_ValueFromPO(COLUMNNAME_C_TaxCategory_ID, org.compiere.model.I_C_TaxCategory.class, C_TaxCategory);
	}

	/** Set Steuerkategorie.
		@param C_TaxCategory_ID 
		Steuerkategorie
	  */
	@Override
	public void setC_TaxCategory_ID (int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, Integer.valueOf(C_TaxCategory_ID));
	}

	/** Get Steuerkategorie.
		@return Steuerkategorie
	  */
	@Override
	public int getC_TaxCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MwSt-Kategorie.
		@param C_TaxCategory_Name MwSt-Kategorie	  */
	@Override
	public void setC_TaxCategory_Name (java.lang.String C_TaxCategory_Name)
	{
		set_Value (COLUMNNAME_C_TaxCategory_Name, C_TaxCategory_Name);
	}

	/** Get MwSt-Kategorie.
		@return MwSt-Kategorie	  */
	@Override
	public java.lang.String getC_TaxCategory_Name () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_C_TaxCategory_Name);
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
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
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

	/** Set Klassifizierung.
		@param Classification 
		Classification for grouping
	  */
	@Override
	public void setClassification (java.lang.String Classification)
	{
		set_Value (COLUMNNAME_Classification, Classification);
	}

	/** Get Klassifizierung.
		@return Classification for grouping
	  */
	@Override
	public java.lang.String getClassification () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Classification);
	}

	/** Set Bestellkosten.
		@param CostPerOrder 
		Fixed Cost Per Order
	  */
	@Override
	public void setCostPerOrder (java.math.BigDecimal CostPerOrder)
	{
		set_Value (COLUMNNAME_CostPerOrder, CostPerOrder);
	}

	/** Get Bestellkosten.
		@return Fixed Cost Per Order
	  */
	@Override
	public java.math.BigDecimal getCostPerOrder () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostPerOrder);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Zugesicherte Lieferzeit.
		@param DeliveryTime_Promised 
		Promised days between order and delivery
	  */
	@Override
	public void setDeliveryTime_Promised (int DeliveryTime_Promised)
	{
		set_Value (COLUMNNAME_DeliveryTime_Promised, Integer.valueOf(DeliveryTime_Promised));
	}

	/** Get Zugesicherte Lieferzeit.
		@return Promised days between order and delivery
	  */
	@Override
	public int getDeliveryTime_Promised () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DeliveryTime_Promised);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Beschreibungs-URL.
		@param DescriptionURL 
		URL for the description
	  */
	@Override
	public void setDescriptionURL (java.lang.String DescriptionURL)
	{
		set_Value (COLUMNNAME_DescriptionURL, DescriptionURL);
	}

	/** Get Beschreibungs-URL.
		@return URL for the description
	  */
	@Override
	public java.lang.String getDescriptionURL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DescriptionURL);
	}

	/** Set Eingestellt.
		@param Discontinued 
		This product is no longer available
	  */
	@Override
	public void setDiscontinued (boolean Discontinued)
	{
		set_Value (COLUMNNAME_Discontinued, Boolean.valueOf(Discontinued));
	}

	/** Get Eingestellt.
		@return This product is no longer available
	  */
	@Override
	public boolean isDiscontinued () 
	{
		Object oo = get_Value(COLUMNNAME_Discontinued);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Eingestellt durch.
		@param DiscontinuedBy 
		Discontinued By
	  */
	@Override
	public void setDiscontinuedBy (java.sql.Timestamp DiscontinuedBy)
	{
		set_Value (COLUMNNAME_DiscontinuedBy, DiscontinuedBy);
	}

	/** Get Eingestellt durch.
		@return Discontinued By
	  */
	@Override
	public java.sql.Timestamp getDiscontinuedBy () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DiscontinuedBy);
	}

	/** Set Notiz / Zeilentext.
		@param DocumentNote 
		Additional information for a Document
	  */
	@Override
	public void setDocumentNote (java.lang.String DocumentNote)
	{
		set_Value (COLUMNNAME_DocumentNote, DocumentNote);
	}

	/** Get Notiz / Zeilentext.
		@return Additional information for a Document
	  */
	@Override
	public java.lang.String getDocumentNote () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNote);
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

	/** Set Import-Fehlermeldung.
		@param I_ErrorMsg 
		Messages generated from import process
	  */
	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_ValueNoCheck (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import-Fehlermeldung.
		@return Messages generated from import process
	  */
	@Override
	public java.lang.String getI_ErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** Set Importiert.
		@param I_IsImported 
		Has this import been processed
	  */
	@Override
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Importiert.
		@return Has this import been processed
	  */
	@Override
	public boolean isI_IsImported () 
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Import - Produkt.
		@param I_Product_ID 
		Import Item or Service
	  */
	@Override
	public void setI_Product_ID (int I_Product_ID)
	{
		if (I_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_Product_ID, Integer.valueOf(I_Product_ID));
	}

	/** Get Import - Produkt.
		@return Import Item or Service
	  */
	@Override
	public int getI_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bild-URL.
		@param ImageURL 
		URL of  image
	  */
	@Override
	public void setImageURL (java.lang.String ImageURL)
	{
		set_Value (COLUMNNAME_ImageURL, ImageURL);
	}

	/** Get Bild-URL.
		@return URL of  image
	  */
	@Override
	public java.lang.String getImageURL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ImageURL);
	}

	/** Set ISO Währungscode.
		@param ISO_Code 
		Three letter ISO 4217 Code of the Currency
	  */
	@Override
	public void setISO_Code (java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	/** Get ISO Währungscode.
		@return Three letter ISO 4217 Code of the Currency
	  */
	@Override
	public java.lang.String getISO_Code () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ISO_Code);
	}

	/** Set Verkauft.
		@param IsSold 
		Die Organisation verkauft dieses Produkt
	  */
	@Override
	public void setIsSold (boolean IsSold)
	{
		set_Value (COLUMNNAME_IsSold, Boolean.valueOf(IsSold));
	}

	/** Get Verkauft.
		@return Die Organisation verkauft dieses Produkt
	  */
	@Override
	public boolean isSold () 
	{
		Object oo = get_Value(COLUMNNAME_IsSold);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lagerhaltig.
		@param IsStocked 
		Die Organisation hat dieses Produkt auf Lager
	  */
	@Override
	public void setIsStocked (boolean IsStocked)
	{
		set_Value (COLUMNNAME_IsStocked, Boolean.valueOf(IsStocked));
	}

	/** Get Lagerhaltig.
		@return Die Organisation hat dieses Produkt auf Lager
	  */
	@Override
	public boolean isStocked () 
	{
		Object oo = get_Value(COLUMNNAME_IsStocked);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_PriceList_Version getM_PriceList_Version() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_PriceList_Version_ID, org.compiere.model.I_M_PriceList_Version.class);
	}

	@Override
	public void setM_PriceList_Version(org.compiere.model.I_M_PriceList_Version M_PriceList_Version)
	{
		set_ValueFromPO(COLUMNNAME_M_PriceList_Version_ID, org.compiere.model.I_M_PriceList_Version.class, M_PriceList_Version);
	}

	/** Set Version Preisliste.
		@param M_PriceList_Version_ID 
		Bezeichnet eine einzelne Version der Preisliste
	  */
	@Override
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, Integer.valueOf(M_PriceList_Version_ID));
	}

	/** Get Version Preisliste.
		@return Bezeichnet eine einzelne Version der Preisliste
	  */
	@Override
	public int getM_PriceList_Version_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_Version_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Version Preisliste.
		@param M_PriceList_Version_Name 
		Bezeichnet eine einzelne Version der Preisliste
	  */
	@Override
	public void setM_PriceList_Version_Name (java.lang.String M_PriceList_Version_Name)
	{
		set_Value (COLUMNNAME_M_PriceList_Version_Name, M_PriceList_Version_Name);
	}

	/** Get Version Preisliste.
		@return Bezeichnet eine einzelne Version der Preisliste
	  */
	@Override
	public java.lang.String getM_PriceList_Version_Name () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_M_PriceList_Version_Name);
	}

	@Override
	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_Category_ID, org.compiere.model.I_M_Product_Category.class);
	}

	@Override
	public void setM_Product_Category(org.compiere.model.I_M_Product_Category M_Product_Category)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_Category_ID, org.compiere.model.I_M_Product_Category.class, M_Product_Category);
	}

	/** Set Produkt Kategorie.
		@param M_Product_Category_ID 
		Kategorie eines Produktes
	  */
	@Override
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Produkt Kategorie.
		@return Kategorie eines Produktes
	  */
	@Override
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
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

	/** 
	 * M_ProductPlanningSchema_Selector AD_Reference_ID=540829
	 * Reference name: M_ProductPlanningSchema_Selector_List
	 */
	public static final int M_PRODUCTPLANNINGSCHEMA_SELECTOR_AD_Reference_ID=540829;
	/** Normal = N */
	public static final String M_PRODUCTPLANNINGSCHEMA_SELECTOR_Normal = "N";
	/** Set M_ProductPlanningSchema_Selector.
		@param M_ProductPlanningSchema_Selector M_ProductPlanningSchema_Selector	  */
	@Override
	public void setM_ProductPlanningSchema_Selector (java.lang.String M_ProductPlanningSchema_Selector)
	{

		set_Value (COLUMNNAME_M_ProductPlanningSchema_Selector, M_ProductPlanningSchema_Selector);
	}

	/** Get M_ProductPlanningSchema_Selector.
		@return M_ProductPlanningSchema_Selector	  */
	@Override
	public java.lang.String getM_ProductPlanningSchema_Selector () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_M_ProductPlanningSchema_Selector);
	}

	/** Set Hersteller.
		@param Manufacturer 
		Manufacturer of the Product
	  */
	@Override
	public void setManufacturer (java.lang.String Manufacturer)
	{
		set_Value (COLUMNNAME_Manufacturer, Manufacturer);
	}

	/** Get Hersteller.
		@return Manufacturer of the Product
	  */
	@Override
	public java.lang.String getManufacturer () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Manufacturer);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Mindestbestellmenge.
		@param Order_Min 
		Minimum order quantity in UOM
	  */
	@Override
	public void setOrder_Min (int Order_Min)
	{
		set_Value (COLUMNNAME_Order_Min, Integer.valueOf(Order_Min));
	}

	/** Get Mindestbestellmenge.
		@return Minimum order quantity in UOM
	  */
	@Override
	public int getOrder_Min () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Order_Min);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Packungsgröße.
		@param Order_Pack 
		Package order size in UOM (e.g. order set of 5 units)
	  */
	@Override
	public void setOrder_Pack (int Order_Pack)
	{
		set_Value (COLUMNNAME_Order_Pack, Integer.valueOf(Order_Pack));
	}

	/** Get Packungsgröße.
		@return Package order size in UOM (e.g. order set of 5 units)
	  */
	@Override
	public int getOrder_Pack () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Order_Pack);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getPackage_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Package_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setPackage_UOM(org.compiere.model.I_C_UOM Package_UOM)
	{
		set_ValueFromPO(COLUMNNAME_Package_UOM_ID, org.compiere.model.I_C_UOM.class, Package_UOM);
	}

	/** Set Package UOM.
		@param Package_UOM_ID 
		UOM of the package
	  */
	@Override
	public void setPackage_UOM_ID (int Package_UOM_ID)
	{
		if (Package_UOM_ID < 1) 
			set_Value (COLUMNNAME_Package_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_Package_UOM_ID, Integer.valueOf(Package_UOM_ID));
	}

	/** Get Package UOM.
		@return UOM of the package
	  */
	@Override
	public int getPackage_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Package_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Package Size.
		@param PackageSize 
		Size of a package
	  */
	@Override
	public void setPackageSize (java.lang.String PackageSize)
	{
		set_Value (COLUMNNAME_PackageSize, PackageSize);
	}

	/** Get Package Size.
		@return Size of a package
	  */
	@Override
	public java.lang.String getPackageSize () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PackageSize);
	}

	/** Set Preis gültig.
		@param PriceEffective 
		Effective Date of Price
	  */
	@Override
	public void setPriceEffective (java.sql.Timestamp PriceEffective)
	{
		set_Value (COLUMNNAME_PriceEffective, PriceEffective);
	}

	/** Get Preis gültig.
		@return Effective Date of Price
	  */
	@Override
	public java.sql.Timestamp getPriceEffective () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PriceEffective);
	}

	/** Set Mindestpreis.
		@param PriceLimit 
		Lowest price for a product
	  */
	@Override
	public void setPriceLimit (java.math.BigDecimal PriceLimit)
	{
		set_Value (COLUMNNAME_PriceLimit, PriceLimit);
	}

	/** Get Mindestpreis.
		@return Lowest price for a product
	  */
	@Override
	public java.math.BigDecimal getPriceLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceLimit);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Auszeichnungspreis.
		@param PriceList 
		Auszeichnungspreis
	  */
	@Override
	public void setPriceList (java.math.BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	/** Get Auszeichnungspreis.
		@return Auszeichnungspreis
	  */
	@Override
	public java.math.BigDecimal getPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceList);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Einkaufspreis.
		@param PricePO 
		Price based on a purchase order
	  */
	@Override
	public void setPricePO (java.math.BigDecimal PricePO)
	{
		set_Value (COLUMNNAME_PricePO, PricePO);
	}

	/** Get Einkaufspreis.
		@return Price based on a purchase order
	  */
	@Override
	public java.math.BigDecimal getPricePO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PricePO);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Standardpreis.
		@param PriceStd 
		Standard Price
	  */
	@Override
	public void setPriceStd (java.math.BigDecimal PriceStd)
	{
		set_Value (COLUMNNAME_PriceStd, PriceStd);
	}

	/** Get Standardpreis.
		@return Standard Price
	  */
	@Override
	public java.math.BigDecimal getPriceStd () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceStd);
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

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Produktkategorie-Schlüssel.
		@param ProductCategory_Value Produktkategorie-Schlüssel	  */
	@Override
	public void setProductCategory_Value (java.lang.String ProductCategory_Value)
	{
		set_Value (COLUMNNAME_ProductCategory_Value, ProductCategory_Value);
	}

	/** Get Produktkategorie-Schlüssel.
		@return Produktkategorie-Schlüssel	  */
	@Override
	public java.lang.String getProductCategory_Value () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductCategory_Value);
	}

	/** Set Product Manufacturer.
		@param ProductManufacturer Product Manufacturer	  */
	@Override
	public void setProductManufacturer (java.lang.String ProductManufacturer)
	{
		set_Value (COLUMNNAME_ProductManufacturer, ProductManufacturer);
	}

	/** Get Product Manufacturer.
		@return Product Manufacturer	  */
	@Override
	public java.lang.String getProductManufacturer () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductManufacturer);
	}

	/** 
	 * ProductType AD_Reference_ID=270
	 * Reference name: M_Product_ProductType
	 */
	public static final int PRODUCTTYPE_AD_Reference_ID=270;
	/** Item = I */
	public static final String PRODUCTTYPE_Item = "I";
	/** Service = S */
	public static final String PRODUCTTYPE_Service = "S";
	/** Resource = R */
	public static final String PRODUCTTYPE_Resource = "R";
	/** ExpenseType = E */
	public static final String PRODUCTTYPE_ExpenseType = "E";
	/** Online = O */
	public static final String PRODUCTTYPE_Online = "O";
	/** Set Produktart.
		@param ProductType 
		Type of product
	  */
	@Override
	public void setProductType (java.lang.String ProductType)
	{

		set_Value (COLUMNNAME_ProductType, ProductType);
	}

	/** Get Produktart.
		@return Type of product
	  */
	@Override
	public java.lang.String getProductType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductType);
	}

	/** Set Lizenzbetrag.
		@param RoyaltyAmt 
		(Included) Amount for copyright, etc.
	  */
	@Override
	public void setRoyaltyAmt (java.math.BigDecimal RoyaltyAmt)
	{
		set_Value (COLUMNNAME_RoyaltyAmt, RoyaltyAmt);
	}

	/** Get Lizenzbetrag.
		@return (Included) Amount for copyright, etc.
	  */
	@Override
	public java.math.BigDecimal getRoyaltyAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RoyaltyAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Regaltiefe.
		@param ShelfDepth 
		Shelf depth required
	  */
	@Override
	public void setShelfDepth (int ShelfDepth)
	{
		set_Value (COLUMNNAME_ShelfDepth, Integer.valueOf(ShelfDepth));
	}

	/** Get Regaltiefe.
		@return Shelf depth required
	  */
	@Override
	public int getShelfDepth () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShelfDepth);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Regalhöhe.
		@param ShelfHeight 
		Shelf height required
	  */
	@Override
	public void setShelfHeight (int ShelfHeight)
	{
		set_Value (COLUMNNAME_ShelfHeight, Integer.valueOf(ShelfHeight));
	}

	/** Get Regalhöhe.
		@return Shelf height required
	  */
	@Override
	public int getShelfHeight () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShelfHeight);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Regalbreite.
		@param ShelfWidth 
		Shelf width required
	  */
	@Override
	public void setShelfWidth (int ShelfWidth)
	{
		set_Value (COLUMNNAME_ShelfWidth, Integer.valueOf(ShelfWidth));
	}

	/** Get Regalbreite.
		@return Shelf width required
	  */
	@Override
	public int getShelfWidth () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShelfWidth);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SKU.
		@param SKU 
		Stock Keeping Unit
	  */
	@Override
	public void setSKU (java.lang.String SKU)
	{
		set_Value (COLUMNNAME_SKU, SKU);
	}

	/** Get SKU.
		@return Stock Keeping Unit
	  */
	@Override
	public java.lang.String getSKU () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SKU);
	}

	/** Set Einheiten pro Palette.
		@param UnitsPerPallet 
		Units Per Pallet
	  */
	@Override
	public void setUnitsPerPallet (int UnitsPerPallet)
	{
		set_Value (COLUMNNAME_UnitsPerPallet, Integer.valueOf(UnitsPerPallet));
	}

	/** Get Einheiten pro Palette.
		@return Units Per Pallet
	  */
	@Override
	public int getUnitsPerPallet () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UnitsPerPallet);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UPC/EAN.
		@param UPC 
		Bar Code (Universal Product Code or its superset European Article Number)
	  */
	@Override
	public void setUPC (java.lang.String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	/** Get UPC/EAN.
		@return Bar Code (Universal Product Code or its superset European Article Number)
	  */
	@Override
	public java.lang.String getUPC () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UPC);
	}

	/** Set Suchschlüssel.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	/** Set Produkt-Kategorie Geschäftspartner.
		@param VendorCategory 
		Product Category of the Business Partner
	  */
	@Override
	public void setVendorCategory (java.lang.String VendorCategory)
	{
		set_Value (COLUMNNAME_VendorCategory, VendorCategory);
	}

	/** Get Produkt-Kategorie Geschäftspartner.
		@return Product Category of the Business Partner
	  */
	@Override
	public java.lang.String getVendorCategory () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VendorCategory);
	}

	/** Set Produkt-Nr. Geschäftspartner.
		@param VendorProductNo 
		Product Key of the Business Partner
	  */
	@Override
	public void setVendorProductNo (java.lang.String VendorProductNo)
	{
		set_Value (COLUMNNAME_VendorProductNo, VendorProductNo);
	}

	/** Get Produkt-Nr. Geschäftspartner.
		@return Product Key of the Business Partner
	  */
	@Override
	public java.lang.String getVendorProductNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VendorProductNo);
	}

	/** Set Volumen.
		@param Volume 
		Volume of a product
	  */
	@Override
	public void setVolume (int Volume)
	{
		set_Value (COLUMNNAME_Volume, Integer.valueOf(Volume));
	}

	/** Get Volumen.
		@return Volume of a product
	  */
	@Override
	public int getVolume () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Volume);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Gewicht.
		@param Weight 
		Weight of a product
	  */
	@Override
	public void setWeight (int Weight)
	{
		set_Value (COLUMNNAME_Weight, Integer.valueOf(Weight));
	}

	/** Get Gewicht.
		@return Weight of a product
	  */
	@Override
	public int getWeight () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Weight);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kodierung der Mengeneinheit.
		@param X12DE355 
		UOM EDI X12 Code
	  */
	@Override
	public void setX12DE355 (java.lang.String X12DE355)
	{
		set_Value (COLUMNNAME_X12DE355, X12DE355);
	}

	/** Get Kodierung der Mengeneinheit.
		@return UOM EDI X12 Code
	  */
	@Override
	public java.lang.String getX12DE355 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_X12DE355);
	}
}