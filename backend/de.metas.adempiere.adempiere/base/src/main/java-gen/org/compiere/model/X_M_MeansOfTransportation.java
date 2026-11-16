// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_MeansOfTransportation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_MeansOfTransportation extends org.compiere.model.PO implements I_M_MeansOfTransportation, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 175415084L;

    /** Standard Constructor */
    public X_M_MeansOfTransportation (final Properties ctx, final int M_MeansOfTransportation_ID, @Nullable final String trxName)
    {
      super (ctx, M_MeansOfTransportation_ID, trxName);
    }

    /** Load Constructor */
    public X_M_MeansOfTransportation (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setDescriptionURL (final @Nullable java.lang.String DescriptionURL)
	{
		set_Value (COLUMNNAME_DescriptionURL, DescriptionURL);
	}

	@Override
	public java.lang.String getDescriptionURL() 
	{
		return get_ValueAsString(COLUMNNAME_DescriptionURL);
	}

	@Override
	public void setIMO_MMSI_Number (final @Nullable java.lang.String IMO_MMSI_Number)
	{
		set_Value (COLUMNNAME_IMO_MMSI_Number, IMO_MMSI_Number);
	}

	@Override
	public java.lang.String getIMO_MMSI_Number() 
	{
		return get_ValueAsString(COLUMNNAME_IMO_MMSI_Number);
	}

	@Override
	public void setM_MeansOfTransportation_ID (final int M_MeansOfTransportation_ID)
	{
		if (M_MeansOfTransportation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_MeansOfTransportation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_MeansOfTransportation_ID, M_MeansOfTransportation_ID);
	}

	@Override
	public int getM_MeansOfTransportation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_MeansOfTransportation_ID);
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
	public void setPlane (final @Nullable java.lang.String Plane)
	{
		set_Value (COLUMNNAME_Plane, Plane);
	}

	@Override
	public java.lang.String getPlane() 
	{
		return get_ValueAsString(COLUMNNAME_Plane);
	}

	@Override
	public void setPlate_Number (final @Nullable java.lang.String Plate_Number)
	{
		set_Value (COLUMNNAME_Plate_Number, Plate_Number);
	}

	@Override
	public java.lang.String getPlate_Number() 
	{
		return get_ValueAsString(COLUMNNAME_Plate_Number);
	}

	@Override
	public void setRTC (final @Nullable java.lang.String RTC)
	{
		set_Value (COLUMNNAME_RTC, RTC);
	}

	@Override
	public java.lang.String getRTC() 
	{
		return get_ValueAsString(COLUMNNAME_RTC);
	}

	/** 
	 * Type_MoT AD_Reference_ID=541691
	 * Reference name: Means of Trasportation Type
	 */
	public static final int TYPE_MOT_AD_Reference_ID=541691;
	/** Truck = Truck */
	public static final String TYPE_MOT_Truck = "Truck";
	/** Vessel = Vessel */
	public static final String TYPE_MOT_Vessel = "Vessel";
	/** Train = Train */
	public static final String TYPE_MOT_Train = "Train";
	/** Plane = Plane */
	public static final String TYPE_MOT_Plane = "Plane";
	@Override
	public void setType_MoT (final @Nullable java.lang.String Type_MoT)
	{
		set_Value (COLUMNNAME_Type_MoT, Type_MoT);
	}

	@Override
	public java.lang.String getType_MoT() 
	{
		return get_ValueAsString(COLUMNNAME_Type_MoT);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}