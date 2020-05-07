/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_PackingMaterial
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_PackingMaterial extends org.compiere.model.PO implements I_M_HU_PackingMaterial, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 227179655L;

    /** Standard Constructor */
    public X_M_HU_PackingMaterial (Properties ctx, int M_HU_PackingMaterial_ID, String trxName)
    {
      super (ctx, M_HU_PackingMaterial_ID, trxName);
      /** if (M_HU_PackingMaterial_ID == 0)
        {
			setIsClosed (false); // N
			setM_HU_PackingMaterial_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_M_HU_PackingMaterial (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Zulässiges Verpackungsvolumen.
		@param AllowedPackingVolume 
		In diesem Feld kann ein maximales Ladungsvolumen für eine Handling Unit eingegeben werden. Wird das zulässige Volumen beim Verpacken erreicht, wird der weitere Verpackungsvorgang ggf. in einer neuen Handling Unit durchgeführt.
	  */
	@Override
	public void setAllowedPackingVolume (java.math.BigDecimal AllowedPackingVolume)
	{
		set_Value (COLUMNNAME_AllowedPackingVolume, AllowedPackingVolume);
	}

	/** Get Zulässiges Verpackungsvolumen.
		@return In diesem Feld kann ein maximales Ladungsvolumen für eine Handling Unit eingegeben werden. Wird das zulässige Volumen beim Verpacken erreicht, wird der weitere Verpackungsvorgang ggf. in einer neuen Handling Unit durchgeführt.
	  */
	@Override
	public java.math.BigDecimal getAllowedPackingVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AllowedPackingVolume);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Zulässiges Verpackungsgewicht.
		@param AllowedPackingWeight 
		In diesem Feld kann ein maximales Ladungsgewicht für eine Handling Unit eingegeben werden. Wird das zulässige Gewicht beim Verpacken erreicht wird der weitere Verpackungsvorgang ggf. mit einer neuen Handling Unit durchgeführt.
	  */
	@Override
	public void setAllowedPackingWeight (java.math.BigDecimal AllowedPackingWeight)
	{
		set_Value (COLUMNNAME_AllowedPackingWeight, AllowedPackingWeight);
	}

	/** Get Zulässiges Verpackungsgewicht.
		@return In diesem Feld kann ein maximales Ladungsgewicht für eine Handling Unit eingegeben werden. Wird das zulässige Gewicht beim Verpacken erreicht wird der weitere Verpackungsvorgang ggf. mit einer neuen Handling Unit durchgeführt.
	  */
	@Override
	public java.math.BigDecimal getAllowedPackingWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AllowedPackingWeight);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM_Dimension() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_Dimension_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM_Dimension(org.compiere.model.I_C_UOM C_UOM_Dimension)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_Dimension_ID, org.compiere.model.I_C_UOM.class, C_UOM_Dimension);
	}

	/** Set Einheit Abessungen.
		@param C_UOM_Dimension_ID 
		Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.
	  */
	@Override
	public void setC_UOM_Dimension_ID (int C_UOM_Dimension_ID)
	{
		if (C_UOM_Dimension_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Dimension_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Dimension_ID, Integer.valueOf(C_UOM_Dimension_ID));
	}

	/** Get Einheit Abessungen.
		@return Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.
	  */
	@Override
	public int getC_UOM_Dimension_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_Dimension_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM_Weight() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_Weight_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM_Weight(org.compiere.model.I_C_UOM C_UOM_Weight)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_Weight_ID, org.compiere.model.I_C_UOM.class, C_UOM_Weight);
	}

	/** Set Einheit Gewicht.
		@param C_UOM_Weight_ID Einheit Gewicht	  */
	@Override
	public void setC_UOM_Weight_ID (int C_UOM_Weight_ID)
	{
		if (C_UOM_Weight_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Weight_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Weight_ID, Integer.valueOf(C_UOM_Weight_ID));
	}

	/** Get Einheit Gewicht.
		@return Einheit Gewicht	  */
	@Override
	public int getC_UOM_Weight_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_Weight_ID);
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

	/** Set Übervolumentoleranz.
		@param ExcessVolumeTolerance 
		In diesem Feld kann eine prozentuale Toleranzgrenze eingegeben werden, bis zu der das zulässige Verpackungsvolumen einer Handling Unit überschritten werden kann.
	  */
	@Override
	public void setExcessVolumeTolerance (java.math.BigDecimal ExcessVolumeTolerance)
	{
		set_Value (COLUMNNAME_ExcessVolumeTolerance, ExcessVolumeTolerance);
	}

	/** Get Übervolumentoleranz.
		@return In diesem Feld kann eine prozentuale Toleranzgrenze eingegeben werden, bis zu der das zulässige Verpackungsvolumen einer Handling Unit überschritten werden kann.
	  */
	@Override
	public java.math.BigDecimal getExcessVolumeTolerance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ExcessVolumeTolerance);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Übergewichtstoleranz.
		@param ExcessWeightTolerance 
		In diesem Feld kann eine prozentuale Toleranzgrenze eingegeben werden, bis zu der das zulässige Verpackungsgewicht einer Handling Unit überschritten werden kann.
	  */
	@Override
	public void setExcessWeightTolerance (java.math.BigDecimal ExcessWeightTolerance)
	{
		set_Value (COLUMNNAME_ExcessWeightTolerance, ExcessWeightTolerance);
	}

	/** Get Übergewichtstoleranz.
		@return In diesem Feld kann eine prozentuale Toleranzgrenze eingegeben werden, bis zu der das zulässige Verpackungsgewicht einer Handling Unit überschritten werden kann.
	  */
	@Override
	public java.math.BigDecimal getExcessWeightTolerance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ExcessWeightTolerance);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Füllgrad.
		@param FillingLevel 
		Der Füllgrad gibt prozentual an, bis zu welchem Grad eine Handling Unit gefüllt werden soll. Die Angabe hat keinen steuernden Charakter und dient nur zu Informationszwecken.
	  */
	@Override
	public void setFillingLevel (java.math.BigDecimal FillingLevel)
	{
		set_Value (COLUMNNAME_FillingLevel, FillingLevel);
	}

	/** Get Füllgrad.
		@return Der Füllgrad gibt prozentual an, bis zu welchem Grad eine Handling Unit gefüllt werden soll. Die Angabe hat keinen steuernden Charakter und dient nur zu Informationszwecken.
	  */
	@Override
	public java.math.BigDecimal getFillingLevel () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FillingLevel);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Höhe.
		@param Height Höhe	  */
	@Override
	public void setHeight (java.math.BigDecimal Height)
	{
		set_Value (COLUMNNAME_Height, Height);
	}

	/** Get Höhe.
		@return Höhe	  */
	@Override
	public java.math.BigDecimal getHeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Height);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Geschlossen.
		@param IsClosed Geschlossen	  */
	@Override
	public void setIsClosed (boolean IsClosed)
	{
		set_Value (COLUMNNAME_IsClosed, Boolean.valueOf(IsClosed));
	}

	/** Get Geschlossen.
		@return Geschlossen	  */
	@Override
	public boolean isClosed () 
	{
		Object oo = get_Value(COLUMNNAME_IsClosed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Länge.
		@param Length Länge	  */
	@Override
	public void setLength (java.math.BigDecimal Length)
	{
		set_Value (COLUMNNAME_Length, Length);
	}

	/** Get Länge.
		@return Länge	  */
	@Override
	public java.math.BigDecimal getLength () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Length);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Packmittel.
		@param M_HU_PackingMaterial_ID Packmittel	  */
	@Override
	public void setM_HU_PackingMaterial_ID (int M_HU_PackingMaterial_ID)
	{
		if (M_HU_PackingMaterial_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PackingMaterial_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PackingMaterial_ID, Integer.valueOf(M_HU_PackingMaterial_ID));
	}

	/** Get Packmittel.
		@return Packmittel	  */
	@Override
	public int getM_HU_PackingMaterial_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PackingMaterial_ID);
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

	/** Set Stapelbarkeitsfaktor.
		@param StackabilityFactor 
		Der Stapelbarkeitsfaktor gibt an, wie viele Packmittel (z.B. Paletten) aufeinander gestapelt werden können. Die Angabe hat keinen steuernden Charakter und dient nur zu Informationszwecken.
	  */
	@Override
	public void setStackabilityFactor (int StackabilityFactor)
	{
		set_Value (COLUMNNAME_StackabilityFactor, Integer.valueOf(StackabilityFactor));
	}

	/** Get Stapelbarkeitsfaktor.
		@return Der Stapelbarkeitsfaktor gibt an, wie viele Packmittel (z.B. Paletten) aufeinander gestapelt werden können. Die Angabe hat keinen steuernden Charakter und dient nur zu Informationszwecken.
	  */
	@Override
	public int getStackabilityFactor () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_StackabilityFactor);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Breite.
		@param Width Breite	  */
	@Override
	public void setWidth (java.math.BigDecimal Width)
	{
		set_Value (COLUMNNAME_Width, Width);
	}

	/** Get Breite.
		@return Breite	  */
	@Override
	public java.math.BigDecimal getWidth () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Width);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}