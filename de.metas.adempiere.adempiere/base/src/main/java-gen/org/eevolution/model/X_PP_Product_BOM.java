/** Generated Model - DO NOT CHANGE */
package org.eevolution.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_Product_BOM
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PP_Product_BOM extends org.compiere.model.PO implements I_PP_Product_BOM, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 863762944L;

    /** Standard Constructor */
    public X_PP_Product_BOM (Properties ctx, int PP_Product_BOM_ID, String trxName)
    {
      super (ctx, PP_Product_BOM_ID, trxName);
      /** if (PP_Product_BOM_ID == 0)
        {
			setC_UOM_ID (0);
			setM_Product_ID (0);
			setName (null);
			setPP_Product_BOM_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() )); // @#Date@
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_PP_Product_BOM (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * BOMType AD_Reference_ID=347
	 * Reference name: M_BOM Type
	 */
	public static final int BOMTYPE_AD_Reference_ID=347;
	/** Current Active = A */
	public static final String BOMTYPE_CurrentActive = "A";
	/** Make-To-Order = O */
	public static final String BOMTYPE_Make_To_Order = "O";
	/** Previous = P */
	public static final String BOMTYPE_Previous = "P";
	/** Previous, Spare = S */
	public static final String BOMTYPE_PreviousSpare = "S";
	/** Future = F */
	public static final String BOMTYPE_Future = "F";
	/** Verwaltung = M */
	public static final String BOMTYPE_Verwaltung = "M";
	/** Repair = R */
	public static final String BOMTYPE_Repair = "R";
	/** Product Configure = C */
	public static final String BOMTYPE_ProductConfigure = "C";
	/** Make-To-Kit = K */
	public static final String BOMTYPE_Make_To_Kit = "K";
	/** Set Stücklisten-Zugehörigkeit.
		@param BOMType 
		Type of BOM
	  */
	@Override
	public void setBOMType (java.lang.String BOMType)
	{

		set_Value (COLUMNNAME_BOMType, BOMType);
	}

	/** Get Stücklisten-Zugehörigkeit.
		@return Type of BOM
	  */
	@Override
	public java.lang.String getBOMType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BOMType);
	}

	/** 
	 * BOMUse AD_Reference_ID=348
	 * Reference name: PP_Product_BOM_BOMUse
	 */
	public static final int BOMUSE_AD_Reference_ID=348;
	/** Master = A */
	public static final String BOMUSE_Master = "A";
	/** Engineering = E */
	public static final String BOMUSE_Engineering = "E";
	/** Manufacturing = M */
	public static final String BOMUSE_Manufacturing = "M";
	/** Planning = P */
	public static final String BOMUSE_Planning = "P";
	/** Quality = Q */
	public static final String BOMUSE_Quality = "Q";
	/** Trading = T */
	public static final String BOMUSE_Trading = "T";
	/** Set BOM Use.
		@param BOMUse 
		The use of the Bill of Material
	  */
	@Override
	public void setBOMUse (java.lang.String BOMUse)
	{

		set_Value (COLUMNNAME_BOMUse, BOMUse);
	}

	/** Get BOM Use.
		@return The use of the Bill of Material
	  */
	@Override
	public java.lang.String getBOMUse () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BOMUse);
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

	/** Set Copy From.
		@param CopyFrom 
		Copy From Record
	  */
	@Override
	public void setCopyFrom (java.lang.String CopyFrom)
	{
		set_Value (COLUMNNAME_CopyFrom, CopyFrom);
	}

	/** Get Copy From.
		@return Copy From Record
	  */
	@Override
	public java.lang.String getCopyFrom () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CopyFrom);
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

	/** Set Nr..
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
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

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
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
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
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
	public org.compiere.model.I_M_ChangeNotice getM_ChangeNotice()
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

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set BOM & Formula.
		@param PP_Product_BOM_ID 
		BOM & Formula
	  */
	@Override
	public void setPP_Product_BOM_ID (int PP_Product_BOM_ID)
	{
		if (PP_Product_BOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOM_ID, Integer.valueOf(PP_Product_BOM_ID));
	}

	/** Get BOM & Formula.
		@return BOM & Formula
	  */
	@Override
	public int getPP_Product_BOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Product_BOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Revision.
		@param Revision Revision	  */
	@Override
	public void setRevision (java.lang.String Revision)
	{
		set_Value (COLUMNNAME_Revision, Revision);
	}

	/** Get Revision.
		@return Revision	  */
	@Override
	public java.lang.String getRevision () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Revision);
	}

	@Override
	public org.compiere.model.I_AD_Sequence getSerialNo_Sequence()
	{
		return get_ValueAsPO(COLUMNNAME_SerialNo_Sequence_ID, org.compiere.model.I_AD_Sequence.class);
	}

	@Override
	public void setSerialNo_Sequence(org.compiere.model.I_AD_Sequence SerialNo_Sequence)
	{
		set_ValueFromPO(COLUMNNAME_SerialNo_Sequence_ID, org.compiere.model.I_AD_Sequence.class, SerialNo_Sequence);
	}

	/** Set Nummernfolge für Seriennummer.
		@param SerialNo_Sequence_ID Nummernfolge für Seriennummer	  */
	@Override
	public void setSerialNo_Sequence_ID (int SerialNo_Sequence_ID)
	{
		if (SerialNo_Sequence_ID < 1) 
			set_Value (COLUMNNAME_SerialNo_Sequence_ID, null);
		else 
			set_Value (COLUMNNAME_SerialNo_Sequence_ID, Integer.valueOf(SerialNo_Sequence_ID));
	}

	/** Get Nummernfolge für Seriennummer.
		@return Nummernfolge für Seriennummer	  */
	@Override
	public int getSerialNo_Sequence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SerialNo_Sequence_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}