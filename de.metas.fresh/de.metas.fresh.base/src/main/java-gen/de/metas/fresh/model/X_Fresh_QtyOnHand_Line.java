/** Generated Model - DO NOT CHANGE */
package de.metas.fresh.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for Fresh_QtyOnHand_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_Fresh_QtyOnHand_Line extends org.compiere.model.PO implements I_Fresh_QtyOnHand_Line, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1624532669L;

    /** Standard Constructor */
    public X_Fresh_QtyOnHand_Line (Properties ctx, int Fresh_QtyOnHand_Line_ID, String trxName)
    {
      super (ctx, Fresh_QtyOnHand_Line_ID, trxName);
      /** if (Fresh_QtyOnHand_Line_ID == 0)
        {
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @DateDoc@
			setFresh_QtyOnHand_ID (0);
			setFresh_QtyOnHand_Line_ID (0);
			setM_Product_ID (0);
			setPP_Plant_ID (0);
			setQtyCount (Env.ZERO);
			setSeqNo (0);
// 0
        } */
    }

    /** Load Constructor */
    public X_Fresh_QtyOnHand_Line (Properties ctx, ResultSet rs, String trxName)
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

	/** Set ASI Key.
		@param ASIKey ASI Key	  */
	@Override
	public void setASIKey (java.lang.String ASIKey)
	{
		set_Value (COLUMNNAME_ASIKey, ASIKey);
	}

	/** Get ASI Key.
		@return ASI Key	  */
	@Override
	public java.lang.String getASIKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ASIKey);
	}

	/** Set Belegdatum.
		@param DateDoc 
		Datum des Belegs
	  */
	@Override
	public void setDateDoc (java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Belegdatum.
		@return Datum des Belegs
	  */
	@Override
	public java.sql.Timestamp getDateDoc () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	@Override
	public de.metas.fresh.model.I_Fresh_QtyOnHand getFresh_QtyOnHand() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Fresh_QtyOnHand_ID, de.metas.fresh.model.I_Fresh_QtyOnHand.class);
	}

	@Override
	public void setFresh_QtyOnHand(de.metas.fresh.model.I_Fresh_QtyOnHand Fresh_QtyOnHand)
	{
		set_ValueFromPO(COLUMNNAME_Fresh_QtyOnHand_ID, de.metas.fresh.model.I_Fresh_QtyOnHand.class, Fresh_QtyOnHand);
	}

	/** Set Zählbestand Einkauf (fresh).
		@param Fresh_QtyOnHand_ID Zählbestand Einkauf (fresh)	  */
	@Override
	public void setFresh_QtyOnHand_ID (int Fresh_QtyOnHand_ID)
	{
		if (Fresh_QtyOnHand_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Fresh_QtyOnHand_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Fresh_QtyOnHand_ID, Integer.valueOf(Fresh_QtyOnHand_ID));
	}

	/** Get Zählbestand Einkauf (fresh).
		@return Zählbestand Einkauf (fresh)	  */
	@Override
	public int getFresh_QtyOnHand_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Fresh_QtyOnHand_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Einkauf-Zählbestand Datensatz.
		@param Fresh_QtyOnHand_Line_ID Einkauf-Zählbestand Datensatz	  */
	@Override
	public void setFresh_QtyOnHand_Line_ID (int Fresh_QtyOnHand_Line_ID)
	{
		if (Fresh_QtyOnHand_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Fresh_QtyOnHand_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Fresh_QtyOnHand_Line_ID, Integer.valueOf(Fresh_QtyOnHand_Line_ID));
	}

	/** Get Einkauf-Zählbestand Datensatz.
		@return Einkauf-Zählbestand Datensatz	  */
	@Override
	public int getFresh_QtyOnHand_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Fresh_QtyOnHand_Line_ID);
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

	/** Set Ausprägung Merkmals-Satz.
		@param M_AttributeSetInstance_ID 
		Instanz des Merkmals-Satzes zum Produkt
	  */
	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Ausprägung Merkmals-Satz.
		@return Instanz des Merkmals-Satzes zum Produkt
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

	/** Set Produktgruppe.
		@param ProductGroup 
		This SQL-column is supposed to be used by the code, so pls don't set Lazy loading to true!
	  */
	@Override
	public void setProductGroup (java.lang.String ProductGroup)
	{
		throw new IllegalArgumentException ("ProductGroup is virtual column");	}

	/** Get Produktgruppe.
		@return This SQL-column is supposed to be used by the code, so pls don't set Lazy loading to true!
	  */
	@Override
	public java.lang.String getProductGroup () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductGroup);
	}

	/** Set Produktname.
		@param ProductName 
		This SQL-column is supposed to be used by the code, so pls don't set Lazy loading to true!
	  */
	@Override
	public void setProductName (java.lang.String ProductName)
	{
		throw new IllegalArgumentException ("ProductName is virtual column");	}

	/** Get Produktname.
		@return This SQL-column is supposed to be used by the code, so pls don't set Lazy loading to true!
	  */
	@Override
	public java.lang.String getProductName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductName);
	}

	/** Set Zählmenge.
		@param QtyCount 
		Gezählte Menge
	  */
	@Override
	public void setQtyCount (java.math.BigDecimal QtyCount)
	{
		set_Value (COLUMNNAME_QtyCount, QtyCount);
	}

	/** Get Zählmenge.
		@return Gezählte Menge
	  */
	@Override
	public java.math.BigDecimal getQtyCount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyCount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}