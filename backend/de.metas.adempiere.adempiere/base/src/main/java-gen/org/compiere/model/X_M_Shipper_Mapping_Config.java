// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Shipper_Mapping_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Shipper_Mapping_Config extends org.compiere.model.PO implements I_M_Shipper_Mapping_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1970021698L;

    /** Standard Constructor */
    public X_M_Shipper_Mapping_Config (final Properties ctx, final int M_Shipper_Mapping_Config_ID, @Nullable final String trxName)
    {
      super (ctx, M_Shipper_Mapping_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Shipper_Mapping_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCarrier_Product_ID (final int Carrier_Product_ID)
	{
		if (Carrier_Product_ID < 1) 
			set_Value (COLUMNNAME_Carrier_Product_ID, null);
		else 
			set_Value (COLUMNNAME_Carrier_Product_ID, Carrier_Product_ID);
	}

	@Override
	public int getCarrier_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Carrier_Product_ID);
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
	public void setMappingAttributeKey (final @Nullable java.lang.String MappingAttributeKey)
	{
		set_Value (COLUMNNAME_MappingAttributeKey, MappingAttributeKey);
	}

	@Override
	public java.lang.String getMappingAttributeKey() 
	{
		return get_ValueAsString(COLUMNNAME_MappingAttributeKey);
	}

	/** 
	 * MappingAttributeType AD_Reference_ID=541999
	 * Reference name: Mapping Attribut Typ
	 */
	public static final int MAPPINGATTRIBUTETYPE_AD_Reference_ID=541999;
	/** SenderAttention = SenderAttention */
	public static final String MAPPINGATTRIBUTETYPE_SenderAttention = "SenderAttention";
	/** ReceiverAttention = ReceiverAttention */
	public static final String MAPPINGATTRIBUTETYPE_ReceiverAttention = "ReceiverAttention";
	/** Reference = Reference */
	public static final String MAPPINGATTRIBUTETYPE_Reference = "Reference";
	/** LineReference = LineReference */
	public static final String MAPPINGATTRIBUTETYPE_LineReference = "LineReference";
	/** DetailGroup = DetailGroup */
	public static final String MAPPINGATTRIBUTETYPE_DetailGroup = "DetailGroup";
	/** LineDetailGroup = LineDetailGroup */
	public static final String MAPPINGATTRIBUTETYPE_LineDetailGroup = "LineDetailGroup";
	@Override
	public void setMappingAttributeType (final java.lang.String MappingAttributeType)
	{
		set_Value (COLUMNNAME_MappingAttributeType, MappingAttributeType);
	}

	@Override
	public java.lang.String getMappingAttributeType() 
	{
		return get_ValueAsString(COLUMNNAME_MappingAttributeType);
	}

	/** 
	 * MappingAttributeValue AD_Reference_ID=542001
	 * Reference name: Mapping Attribut Wert
	 */
	public static final int MAPPINGATTRIBUTEVALUE_AD_Reference_ID=542001;
	/** PickupDateAndTimeStart = PickupDateAndTimeStart */
	public static final String MAPPINGATTRIBUTEVALUE_PickupDateAndTimeStart = "PickupDateAndTimeStart";
	/** PickupDateAndTimeEnd = PickupDateAndTimeEnd */
	public static final String MAPPINGATTRIBUTEVALUE_PickupDateAndTimeEnd = "PickupDateAndTimeEnd";
	/** DeliveryDate = DeliveryDate */
	public static final String MAPPINGATTRIBUTEVALUE_DeliveryDate = "DeliveryDate";
	/** CustomerReference = CustomerReference */
	public static final String MAPPINGATTRIBUTEVALUE_CustomerReference = "CustomerReference";
	/** ReceiverCountryCode = ReceiverCountryCode */
	public static final String MAPPINGATTRIBUTEVALUE_ReceiverCountryCode = "ReceiverCountryCode";
	/** SenderCountryCode = SenderCountryCode */
	public static final String MAPPINGATTRIBUTEVALUE_SenderCountryCode = "SenderCountryCode";
	/** ShipperProductExternalId = ShipperProductExternalId */
	public static final String MAPPINGATTRIBUTEVALUE_ShipperProductExternalId = "ShipperProductExternalId";
	/** SenderCompanyName = SenderCompanyName */
	public static final String MAPPINGATTRIBUTEVALUE_SenderCompanyName = "SenderCompanyName";
	/** SenderCompanyName2 = SenderCompanyName2 */
	public static final String MAPPINGATTRIBUTEVALUE_SenderCompanyName2 = "SenderCompanyName2";
	/** SenderDepartment = SenderDepartment */
	public static final String MAPPINGATTRIBUTEVALUE_SenderDepartment = "SenderDepartment";
	/** ReceiverCompanyName = ReceiverCompanyName */
	public static final String MAPPINGATTRIBUTEVALUE_ReceiverCompanyName = "ReceiverCompanyName";
	/** ReceiverDepartment = ReceiverDepartment */
	public static final String MAPPINGATTRIBUTEVALUE_ReceiverDepartment = "ReceiverDepartment";
	/** ReceiverContactLastnameAndFirstname = ReceiverContactLastnameAndFirstname */
	public static final String MAPPINGATTRIBUTEVALUE_ReceiverContactLastnameAndFirstname = "ReceiverContactLastnameAndFirstname";
	/** ShipperEORI = ShipperEORI */
	public static final String MAPPINGATTRIBUTEVALUE_ShipperEORI = "ShipperEORI";
	/** ParcelId = ParcelId */
	public static final String MAPPINGATTRIBUTEVALUE_ParcelId = "ParcelId";
	/** ShippedQuantity = ShippedQuantity */
	public static final String MAPPINGATTRIBUTEVALUE_ShippedQuantity = "ShippedQuantity";
	/** UomCode = UomCode */
	public static final String MAPPINGATTRIBUTEVALUE_UomCode = "UomCode";
	/** ProductName = ProductName */
	public static final String MAPPINGATTRIBUTEVALUE_ProductName = "ProductName";
	/** ShipmentOrderItemId = ShipmentOrderItemId */
	public static final String MAPPINGATTRIBUTEVALUE_ShipmentOrderItemId = "ShipmentOrderItemId";
	/** UnitPrice = UnitPrice */
	public static final String MAPPINGATTRIBUTEVALUE_UnitPrice = "UnitPrice";
	/** TotalValue = TotalValue */
	public static final String MAPPINGATTRIBUTEVALUE_TotalValue = "TotalValue";
	/** CurrencyCode = CurrencyCode */
	public static final String MAPPINGATTRIBUTEVALUE_CurrencyCode = "CurrencyCode";
	@Override
	public void setMappingAttributeValue (final java.lang.String MappingAttributeValue)
	{
		set_Value (COLUMNNAME_MappingAttributeValue, MappingAttributeValue);
	}

	@Override
	public java.lang.String getMappingAttributeValue() 
	{
		return get_ValueAsString(COLUMNNAME_MappingAttributeValue);
	}

	@Override
	public void setMappingGroupKey (final @Nullable java.lang.String MappingGroupKey)
	{
		set_Value (COLUMNNAME_MappingGroupKey, MappingGroupKey);
	}

	@Override
	public java.lang.String getMappingGroupKey() 
	{
		return get_ValueAsString(COLUMNNAME_MappingGroupKey);
	}

	/** 
	 * MappingRule AD_Reference_ID=542000
	 * Reference name: Mapping Regel
	 */
	public static final int MAPPINGRULE_AD_Reference_ID=542000;
	/** ReceiverCountryCode = ReceiverCountryCode */
	public static final String MAPPINGRULE_ReceiverCountryCode = "ReceiverCountryCode";
	@Override
	public void setMappingRule (final @Nullable java.lang.String MappingRule)
	{
		set_Value (COLUMNNAME_MappingRule, MappingRule);
	}

	@Override
	public java.lang.String getMappingRule() 
	{
		return get_ValueAsString(COLUMNNAME_MappingRule);
	}

	@Override
	public void setMappingRuleValue (final @Nullable java.lang.String MappingRuleValue)
	{
		set_Value (COLUMNNAME_MappingRuleValue, MappingRuleValue);
	}

	@Override
	public java.lang.String getMappingRuleValue() 
	{
		return get_ValueAsString(COLUMNNAME_MappingRuleValue);
	}

	@Override
	public void setM_Shipper_ID (final int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, M_Shipper_ID);
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
	}

	@Override
	public void setM_Shipper_Mapping_Config_ID (final int M_Shipper_Mapping_Config_ID)
	{
		if (M_Shipper_Mapping_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_Mapping_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_Mapping_Config_ID, M_Shipper_Mapping_Config_ID);
	}

	@Override
	public int getM_Shipper_Mapping_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_Mapping_Config_ID);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}