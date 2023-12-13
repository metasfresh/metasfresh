package de.metas.handlingunits.attribute;

import de.metas.util.Check;
import lombok.experimental.UtilityClass;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.compiere.model.I_M_Attribute;

/**
 * HU Attributes constants.
 * IMPORTANT: before adding a constant here, please check if it was already added to {@link AttributeConstants}.
 */
@UtilityClass
public final class HUAttributeConstants
{
	/**
	 * Context name used to identify the map of initial default values to be used when creating HU attributes.
	 * <p>
	 * Type: Map of {@link I_M_Attribute} to value({@link Object}).
	 */
	public static String CTXATTR_DefaultAttributesValue = HUAttributeConstants.class.getName() + "#DefaultAttributesValue";

	public static final String ATTR_QualityDiscountPercent_Value = "QualityDiscountPercent";
	public static final String ATTR_QualityNotice_Value = "QualityNotice";
	public static final AttributeCode ATTR_SSCC18_Value = AttributeCode.ofString("SSCC18");
	public static final AttributeCode ATTR_SubProducerBPartner_Value = AttributeConstants.ATTR_SubProducerBPartner_Value;

	public static final AttributeCode ATTR_CostPrice = AttributeCode.ofString("HU_CostPrice");

	public static final AttributeCode ATTR_LotNumberDate = AttributeConstants.ATTR_LotNumberDate;

	/**
	 * The <code>M_Attribute.Value</code> for the HU-attribute referencing the purchase C_OrderLine_ID on which the given HU was ordered.
	 */
	public static final AttributeCode ATTR_PurchaseOrderLine_ID = AttributeCode.ofString("HU_PurchaseOrderLine_ID");

	/**
	 * The <code>M_Attribute.Value</code> for the HU-attribute referencing the receipt M_InOutLine_ID on which the given HU was received.
	 */
	public static final AttributeCode ATTR_ReceiptInOutLine_ID = AttributeCode.ofString("HU_ReceiptInOutLine_ID");
	public static final AttributeCode ATTR_PP_Order_ID = AttributeCode.ofString("PP_Order_ID");

	public static final AttributeCode ATTR_Expired = AttributeCode.ofString("HU_Expired");
	public static final String ATTR_Expired_Value_Expired = "expired";

	public static final AttributeCode ATTR_Age = AttributeCode.ofString("Age");

	public static final AttributeCode ATTR_AgeOffset = AttributeCode.ofString("AgeOffset");
	public static final AttributeCode ATTR_ProductionDate = AttributeCode.ofString("ProductionDate");

	public static final AttributeCode ATTR_Quarantine = AttributeCode.ofString("HU_Quarantine");
	public static final String ATTR_Quarantine_Value_Quarantine = "quarantine";

	public static String sqlBestBeforeDate(final String huIdColumnName)
	{
		Check.assumeNotEmpty(huIdColumnName, "huIdColumnName is not empty");
		return "\"de.metas.handlingunits\".huBestBeforeDate(" + huIdColumnName + ")";
	}
}
