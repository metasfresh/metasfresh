/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_Inventory
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_Inventory extends org.compiere.model.PO implements I_I_Inventory, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 308810635L;

    /** Standard Constructor */
    public X_I_Inventory (Properties ctx, int I_Inventory_ID, String trxName)
    {
      super (ctx, I_Inventory_ID, trxName);
      /** if (I_Inventory_ID == 0)
        {
			setI_Inventory_ID (0);
			setI_IsImported (false);
			setIsLotBlocked (false); // N
        } */
    }

    /** Load Constructor */
    public X_I_Inventory (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Datum der letzten Inventur.
		@param DateLastInventory 
		Datum der letzten Inventur
	  */
	@Override
	public void setDateLastInventory (java.sql.Timestamp DateLastInventory)
	{
		set_Value (COLUMNNAME_DateLastInventory, DateLastInventory);
	}

	/** Get Datum der letzten Inventur.
		@return Datum der letzten Inventur
	  */
	@Override
	public java.sql.Timestamp getDateLastInventory () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateLastInventory);
	}

	/** Set Eingangsdatum.
		@param DateReceived 
		Datum, zu dem ein Produkt empfangen wurde
	  */
	@Override
	public void setDateReceived (java.sql.Timestamp DateReceived)
	{
		set_Value (COLUMNNAME_DateReceived, DateReceived);
	}

	/** Get Eingangsdatum.
		@return Datum, zu dem ein Produkt empfangen wurde
	  */
	@Override
	public java.sql.Timestamp getDateReceived () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateReceived);
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

	/** Set Import-Fehlermeldung.
		@param I_ErrorMsg 
		Messages generated from import process
	  */
	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import-Fehlermeldung.
		@return Messages generated from import process
	  */
	@Override
	public java.lang.String getI_ErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** Set Import - Warenbestand.
		@param I_Inventory_ID 
		Import Inventory Transactions
	  */
	@Override
	public void setI_Inventory_ID (int I_Inventory_ID)
	{
		if (I_Inventory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_Inventory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_Inventory_ID, Integer.valueOf(I_Inventory_ID));
	}

	/** Get Import - Warenbestand.
		@return Import Inventory Transactions
	  */
	@Override
	public int getI_Inventory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_Inventory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Lot Blocked.
		@param IsLotBlocked Lot Blocked	  */
	@Override
	public void setIsLotBlocked (boolean IsLotBlocked)
	{
		set_Value (COLUMNNAME_IsLotBlocked, Boolean.valueOf(IsLotBlocked));
	}

	/** Get Lot Blocked.
		@return Lot Blocked	  */
	@Override
	public boolean isLotBlocked () 
	{
		Object oo = get_Value(COLUMNNAME_IsLotBlocked);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lagerort-Schlüssel.
		@param LocatorValue 
		Key of the Warehouse Locator
	  */
	@Override
	public void setLocatorValue (java.lang.String LocatorValue)
	{
		set_Value (COLUMNNAME_LocatorValue, LocatorValue);
	}

	/** Get Lagerort-Schlüssel.
		@return Key of the Warehouse Locator
	  */
	@Override
	public java.lang.String getLocatorValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LocatorValue);
	}

	/** Set Los-Nr..
		@param Lot 
		Los-Nummer (alphanumerisch)
	  */
	@Override
	public void setLot (java.lang.String Lot)
	{
		set_Value (COLUMNNAME_Lot, Lot);
	}

	/** Get Los-Nr..
		@return Los-Nummer (alphanumerisch)
	  */
	@Override
	public java.lang.String getLot () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Lot);
	}

	@Override
	public org.compiere.model.I_M_Inventory getM_Inventory() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Inventory_ID, org.compiere.model.I_M_Inventory.class);
	}

	@Override
	public void setM_Inventory(org.compiere.model.I_M_Inventory M_Inventory)
	{
		set_ValueFromPO(COLUMNNAME_M_Inventory_ID, org.compiere.model.I_M_Inventory.class, M_Inventory);
	}

	/** Set Inventur.
		@param M_Inventory_ID 
		Parameters for a Physical Inventory
	  */
	@Override
	public void setM_Inventory_ID (int M_Inventory_ID)
	{
		if (M_Inventory_ID < 1) 
			set_Value (COLUMNNAME_M_Inventory_ID, null);
		else 
			set_Value (COLUMNNAME_M_Inventory_ID, Integer.valueOf(M_Inventory_ID));
	}

	/** Get Inventur.
		@return Parameters for a Physical Inventory
	  */
	@Override
	public int getM_Inventory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Inventory_ID);
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

	/** Set Bewegungsdatum.
		@param MovementDate 
		Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	  */
	@Override
	public void setMovementDate (java.sql.Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	/** Get Bewegungsdatum.
		@return Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	  */
	@Override
	public java.sql.Timestamp getMovementDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_MovementDate);
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

	/** Set Internal Use Qty.
		@param QtyInternalUse 
		Internal Use Quantity removed from Inventory
	  */
	@Override
	public void setQtyInternalUse (java.math.BigDecimal QtyInternalUse)
	{
		set_Value (COLUMNNAME_QtyInternalUse, QtyInternalUse);
	}

	/** Get Internal Use Qty.
		@return Internal Use Quantity removed from Inventory
	  */
	@Override
	public java.math.BigDecimal getQtyInternalUse () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInternalUse);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Serien-Nr..
		@param SerNo 
		Product Serial Number 
	  */
	@Override
	public void setSerNo (java.lang.String SerNo)
	{
		set_Value (COLUMNNAME_SerNo, SerNo);
	}

	/** Get Serien-Nr..
		@return Product Serial Number 
	  */
	@Override
	public java.lang.String getSerNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SerNo);
	}

	/** Set TE.
		@param TE TE	  */
	@Override
	public void setTE (java.lang.String TE)
	{
		set_Value (COLUMNNAME_TE, TE);
	}

	/** Get TE.
		@return TE	  */
	@Override
	public java.lang.String getTE () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TE);
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

	/** Set Lager-Schlüssel.
		@param WarehouseValue 
		Key of the Warehouse
	  */
	@Override
	public void setWarehouseValue (java.lang.String WarehouseValue)
	{
		set_Value (COLUMNNAME_WarehouseValue, WarehouseValue);
	}

	/** Get Lager-Schlüssel.
		@return Key of the Warehouse
	  */
	@Override
	public java.lang.String getWarehouseValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WarehouseValue);
	}

	/** Set Gang.
		@param X 
		X-Dimension, z.B. Gang
	  */
	@Override
	public void setX (java.lang.String X)
	{
		set_Value (COLUMNNAME_X, X);
	}

	/** Get Gang.
		@return X-Dimension, z.B. Gang
	  */
	@Override
	public java.lang.String getX () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_X);
	}

	/** Set Regal.
		@param X1 Regal	  */
	@Override
	public void setX1 (java.lang.String X1)
	{
		set_Value (COLUMNNAME_X1, X1);
	}

	/** Get Regal.
		@return Regal	  */
	@Override
	public java.lang.String getX1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_X1);
	}

	/** Set Fach.
		@param Y 
		Y-Dimension, z.B. Fach
	  */
	@Override
	public void setY (java.lang.String Y)
	{
		set_Value (COLUMNNAME_Y, Y);
	}

	/** Get Fach.
		@return Y-Dimension, z.B. Fach
	  */
	@Override
	public java.lang.String getY () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Y);
	}

	/** Set Ebene.
		@param Z 
		Z-Dimension, z.B. Ebene
	  */
	@Override
	public void setZ (java.lang.String Z)
	{
		set_Value (COLUMNNAME_Z, Z);
	}

	/** Get Ebene.
		@return Z-Dimension, z.B. Ebene
	  */
	@Override
	public java.lang.String getZ () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Z);
	}
}