/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_UOM
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_UOM extends org.compiere.model.PO implements I_C_UOM, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -241043055L;

    /** Standard Constructor */
    public X_C_UOM (final Properties ctx, final int C_UOM_ID, final String trxName)
    {
      super (ctx, C_UOM_ID, trxName);
    }

    /** Load Constructor */
    public X_C_UOM (final Properties ctx, final ResultSet rs, final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setCostingPrecision (final int CostingPrecision)
	{
		set_Value (COLUMNNAME_CostingPrecision, CostingPrecision);
	}

	@Override
	public int getCostingPrecision() 
	{
		return get_ValueAsInt(COLUMNNAME_CostingPrecision);
	}

	@Override
	public void setDescription (final java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setHF134_IsImputedUnit (final boolean HF134_IsImputedUnit)
	{
		set_Value (COLUMNNAME_HF134_IsImputedUnit, HF134_IsImputedUnit);
	}

	@Override
	public boolean isHF134_IsImputedUnit() 
	{
		return get_ValueAsBoolean(COLUMNNAME_HF134_IsImputedUnit);
	}

	@Override
	public void setIsDefault (final boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, IsDefault);
	}

	@Override
	public boolean isDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setStdPrecision (final int StdPrecision)
	{
		set_Value (COLUMNNAME_StdPrecision, StdPrecision);
	}

	@Override
	public int getStdPrecision() 
	{
		return get_ValueAsInt(COLUMNNAME_StdPrecision);
	}

	@Override
	public void setUOMSymbol (final java.lang.String UOMSymbol)
	{
		set_Value (COLUMNNAME_UOMSymbol, UOMSymbol);
	}

	@Override
	public java.lang.String getUOMSymbol() 
	{
		return get_ValueAsString(COLUMNNAME_UOMSymbol);
	}

	/** 
	 * UOMType AD_Reference_ID=53323
	 * Reference name: UOM Type
	 */
	public static final int UOMTYPE_AD_Reference_ID=53323;
	/** Angle = AN */
	public static final String UOMTYPE_Angle = "AN";
	/** Area = AR */
	public static final String UOMTYPE_Area = "AR";
	/** Data Storage = DS */
	public static final String UOMTYPE_DataStorage = "DS";
	/** Density = DE */
	public static final String UOMTYPE_Density = "DE";
	/** Energy = EN */
	public static final String UOMTYPE_Energy = "EN";
	/** Force = FO */
	public static final String UOMTYPE_Force = "FO";
	/** Kitchen Measures = KI */
	public static final String UOMTYPE_KitchenMeasures = "KI";
	/** Length = LE */
	public static final String UOMTYPE_Length = "LE";
	/** Power = PO */
	public static final String UOMTYPE_Power = "PO";
	/** Pressure = PR */
	public static final String UOMTYPE_Pressure = "PR";
	/** Temperature = TE */
	public static final String UOMTYPE_Temperature = "TE";
	/** Time = TM */
	public static final String UOMTYPE_Time = "TM";
	/** Torque = TO */
	public static final String UOMTYPE_Torque = "TO";
	/** Velocity = VE */
	public static final String UOMTYPE_Velocity = "VE";
	/** Volume Liquid = VL */
	public static final String UOMTYPE_VolumeLiquid = "VL";
	/** Volume Dry = VD */
	public static final String UOMTYPE_VolumeDry = "VD";
	/** Weigth = WE */
	public static final String UOMTYPE_Weigth = "WE";
	/** Currency = CU */
	public static final String UOMTYPE_Currency = "CU";
	/** Data Speed = DV */
	public static final String UOMTYPE_DataSpeed = "DV";
	/** Frequency = FR */
	public static final String UOMTYPE_Frequency = "FR";
	/** Other = OT */
	public static final String UOMTYPE_Other = "OT";
	/** Gesundheitswesen = HC */
	public static final String UOMTYPE_Gesundheitswesen = "HC";
	/** Zeit (Erfassungsgenauigkeit) = TD */
	public static final String UOMTYPE_ZeitErfassungsgenauigkeit = "TD";
	@Override
	public void setUOMType (final java.lang.String UOMType)
	{
		set_Value (COLUMNNAME_UOMType, UOMType);
	}

	@Override
	public java.lang.String getUOMType() 
	{
		return get_ValueAsString(COLUMNNAME_UOMType);
	}

	@Override
	public void setX12DE355 (final java.lang.String X12DE355)
	{
		set_Value (COLUMNNAME_X12DE355, X12DE355);
	}

	@Override
	public java.lang.String getX12DE355() 
	{
		return get_ValueAsString(COLUMNNAME_X12DE355);
	}
}