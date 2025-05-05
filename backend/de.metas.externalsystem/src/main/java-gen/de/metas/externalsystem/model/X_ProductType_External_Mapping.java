// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ProductType_External_Mapping
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ProductType_External_Mapping extends org.compiere.model.PO implements I_ProductType_External_Mapping, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -414255651L;

    /** Standard Constructor */
    public X_ProductType_External_Mapping (final Properties ctx, final int ProductType_External_Mapping_ID, @Nullable final String trxName)
    {
      super (ctx, ProductType_External_Mapping_ID, trxName);
    }

    /** Load Constructor */
    public X_ProductType_External_Mapping (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.externalsystem.model.I_ExternalSystem_Config getExternalSystem_Config()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Config_ID, de.metas.externalsystem.model.I_ExternalSystem_Config.class);
	}

	@Override
	public void setExternalSystem_Config(final de.metas.externalsystem.model.I_ExternalSystem_Config ExternalSystem_Config)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Config_ID, de.metas.externalsystem.model.I_ExternalSystem_Config.class, ExternalSystem_Config);
	}

	@Override
	public void setExternalSystem_Config_ID (final int ExternalSystem_Config_ID)
	{
		if (ExternalSystem_Config_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Config_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Config_ID, ExternalSystem_Config_ID);
	}

	@Override
	public int getExternalSystem_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ID);
	}

	@Override
	public void setExternalValue (final java.lang.String ExternalValue)
	{
		set_Value (COLUMNNAME_ExternalValue, ExternalValue);
	}

	@Override
	public java.lang.String getExternalValue() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalValue);
	}

	@Override
	public void setProductType_External_Mapping_ID (final int ProductType_External_Mapping_ID)
	{
		if (ProductType_External_Mapping_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ProductType_External_Mapping_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ProductType_External_Mapping_ID, ProductType_External_Mapping_ID);
	}

	@Override
	public int getProductType_External_Mapping_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ProductType_External_Mapping_ID);
	}

	/** 
	 * Value AD_Reference_ID=270
	 * Reference name: M_Product_ProductType
	 */
	public static final int VALUE_AD_Reference_ID=270;
	/** Item = I */
	public static final String VALUE_Item = "I";
	/** Service = S */
	public static final String VALUE_Service = "S";
	/** Resource = R */
	public static final String VALUE_Resource = "R";
	/** ExpenseType = E */
	public static final String VALUE_ExpenseType = "E";
	/** Online = O */
	public static final String VALUE_Online = "O";
	/** FreightCost = F */
	public static final String VALUE_FreightCost = "F";
	/** Nahrung = N */
	public static final String VALUE_Nahrung = "N";
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