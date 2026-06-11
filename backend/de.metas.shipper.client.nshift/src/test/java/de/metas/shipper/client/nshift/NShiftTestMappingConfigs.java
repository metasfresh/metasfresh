package de.metas.shipper.client.nshift;

import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.common.delivery.v1.json.request.JsonMappingConfig;
import de.metas.common.delivery.v1.json.request.JsonMappingConfigList;
import lombok.experimental.UtilityClass;

/**
 * Shared mapping configs used by both {@link NShiftShipmentServiceTest} and {@link NShiftShipAdvisorServiceTest}.
 * Mirrors the full nShift mapping config as it exists in the system DB.
 * Using the full config in the advisor test also verifies that configs not applicable at advise time
 * (e.g. LineDetailGroup values like ShippedQuantity) are silently skipped via the null fallback in
 * JsonDeliveryAdvisorRequest.getValue() rather than causing failures.
 */
@UtilityClass
public class NShiftTestMappingConfigs
{
	// see https://helpcenter.nshift.com/hc/en-us/articles/16926110939292-Objects-and-Fields ReferenceKind
	public static final String REFERENCE_KIND_PICKUP_START = "108";
	public static final String REFERENCE_KIND_PICKUP_END = "109";
	public static final String REFERENCE_KIND_DELIVERY_DATE = "9";
	public static final String REFERENCE_KIND_CUSTOMER_REFERENCE = "7";
	public static final String LINE_REFERENCE_KIND_CONTENTS = "23";
	public static final String LINE_REFERENCE_KIND_CUSTOM_FIELD_1 = "129";
	public static final String LINE_REFERENCE_KIND_CUSTOM_FIELD_2 = "130";
	public static final String LINE_REFERENCE_KIND_CUSTOM_FIELD_3 = "131";
	public static final String LINE_REFERENCE_KIND_CUSTOM_FIELD_4 = "132";
	public static final String LINE_REFERENCE_KIND_CUSTOM_FIELD_5 = "133";
	public static final String LINE_REFERENCE_KIND_CUSTOM_FIELD_6 = "134";

	// see https://helpcenter.nshift.com/hc/en-us/articles/16926110939292-Objects-and-Fields DetailGroupKind
	public static final String DETAIL_GROUP_KEY_CUSTOMS_ARTICLE = "1";
	public static final String DETAIL_GROUP_KEY_CUSTOMS_INFO = "2";

	// see https://helpcenter.nshift.com/hc/en-us/articles/16926110939292-Objects-and-Fields DetailKind
	public static final String DETAIL_KIND_ARTICLE_NO = "1";
	public static final String DETAIL_KIND_UNIT_VALUE = "2";
	public static final String DETAIL_KIND_COUNTRY_OF_ORIGIN = "4";
	public static final String DETAIL_KIND_QUANTITY = "5";
	public static final String DETAIL_KIND_UNIT_WEIGHT = "6";
	public static final String DETAIL_KIND_DESCRIPTION_OF_GOODS = "7";
	public static final String DETAIL_KIND_UNIT_OF_MEASURE = "8";
	public static final String DETAIL_KIND_TOTAL_VALUE = "10";
	public static final String DETAIL_KIND_CUSTOMS_ARTICLE_CURRENCY = "17";
	public static final String DETAIL_KIND_SHIPPER_EORI = "182";

	public static final JsonMappingConfigList SHARED = JsonMappingConfigList.ofList(ImmutableList.of(
			// Shipment-level references
			JsonMappingConfig.builder()
					.seqNo(10)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_REFERENCE)
					.attributeKey(REFERENCE_KIND_PICKUP_START)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PICKUP_DATE_AND_TIME_START)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(20)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_REFERENCE)
					.attributeKey(REFERENCE_KIND_PICKUP_END)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PICKUP_DATE_AND_TIME_END)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(30)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_REFERENCE)
					.attributeKey(REFERENCE_KIND_DELIVERY_DATE)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_DELIVERY_DATE)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(40)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_REFERENCE)
					.attributeKey(REFERENCE_KIND_CUSTOMER_REFERENCE)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_CUSTOMER_REFERENCE)
					.build(),
			// Sender attention
			JsonMappingConfig.builder()
					.seqNo(45)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_SENDER_ATTENTION)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_BPARTNER_ATTENTION)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(50)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_SENDER_ATTENTION)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_COMPANY_NAME)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(60)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_SENDER_ATTENTION)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_DEPARTMENT)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(70)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_SENDER_ATTENTION)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_COMPANY_NAME_2)
					.mappingRule(DeliveryMappingConstants.MAPPING_RULE_RECEIVER_COUNTRY_CODE)
					.mappingRuleValue("RO")
					.build(),
			JsonMappingConfig.builder()
					.seqNo(80)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_SENDER_ATTENTION)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_COMPANY_NAME_2)
					.mappingRule(DeliveryMappingConstants.MAPPING_RULE_RECEIVER_COUNTRY_CODE)
					.mappingRuleValue("DE")
					.build(),
			// Receiver attention
			JsonMappingConfig.builder()
					.seqNo(85)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_RECEIVER_ATTENTION)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_BPARTNER_ATTENTION)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(90)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_RECEIVER_ATTENTION)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_COMPANY_NAME)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(100)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_RECEIVER_ATTENTION)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_DEPARTMENT)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(110)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_RECEIVER_ATTENTION)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_CONTACT_LASTNAME_AND_FIRSTNAME)
					.build(),
			// Line detail groups (customs article) — produce no output at advise time; verify null fallback
			JsonMappingConfig.builder()
					.seqNo(120)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_DETAIL_GROUP)
					.groupKey(DETAIL_GROUP_KEY_CUSTOMS_ARTICLE)
					.attributeKey(DETAIL_KIND_QUANTITY)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_SHIPPED_QUANTITY)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(130)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_DETAIL_GROUP)
					.groupKey(DETAIL_GROUP_KEY_CUSTOMS_ARTICLE)
					.attributeKey(DETAIL_KIND_UNIT_OF_MEASURE)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UOM_CODE)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(140)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_DETAIL_GROUP)
					.groupKey(DETAIL_GROUP_KEY_CUSTOMS_ARTICLE)
					.attributeKey(DETAIL_KIND_DESCRIPTION_OF_GOODS)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_NAME)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(150)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_DETAIL_GROUP)
					.groupKey(DETAIL_GROUP_KEY_CUSTOMS_ARTICLE)
					.attributeKey(DETAIL_KIND_ARTICLE_NO)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_SHIPMENT_ORDER_ITEM_ID)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(160)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_DETAIL_GROUP)
					.groupKey(DETAIL_GROUP_KEY_CUSTOMS_ARTICLE)
					.attributeKey(DETAIL_KIND_UNIT_VALUE)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_PRICE)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(170)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_DETAIL_GROUP)
					.groupKey(DETAIL_GROUP_KEY_CUSTOMS_ARTICLE)
					.attributeKey(DETAIL_KIND_TOTAL_VALUE)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_VALUE)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(180)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_DETAIL_GROUP)
					.groupKey(DETAIL_GROUP_KEY_CUSTOMS_ARTICLE)
					.attributeKey(DETAIL_KIND_CUSTOMS_ARTICLE_CURRENCY)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_CURRENCY_CODE)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(190)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_DETAIL_GROUP)
					.groupKey(DETAIL_GROUP_KEY_CUSTOMS_ARTICLE)
					.attributeKey(DETAIL_KIND_COUNTRY_OF_ORIGIN)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_COUNTRY_CODE)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(195)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_DETAIL_GROUP)
					.groupKey(DETAIL_GROUP_KEY_CUSTOMS_ARTICLE)
					.attributeKey(DETAIL_KIND_COUNTRY_OF_ORIGIN)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_COUNTRY_OF_ORIGIN)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(200)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_DETAIL_GROUP)
					.groupKey(DETAIL_GROUP_KEY_CUSTOMS_ARTICLE)
					.attributeKey(DETAIL_KIND_COUNTRY_OF_ORIGIN)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_COUNTRY_CODE)
					.mappingRule(DeliveryMappingConstants.MAPPING_RULE_RECEIVER_COUNTRY_CODE)
					.mappingRuleValue("DE")
					.build(),
			JsonMappingConfig.builder()
					.seqNo(210)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_DETAIL_GROUP)
					.groupKey(DETAIL_GROUP_KEY_CUSTOMS_ARTICLE)
					.attributeKey(DETAIL_KIND_COUNTRY_OF_ORIGIN)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_COUNTRY_CODE)
					.mappingRule(DeliveryMappingConstants.MAPPING_RULE_RECEIVER_COUNTRY_CODE)
					.mappingRuleValue("RO")
					.build(),
			// Line references
			JsonMappingConfig.builder()
					.seqNo(220)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_REFERENCE)
					.attributeKey(LINE_REFERENCE_KIND_CUSTOM_FIELD_1)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PARCEL_ID)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(230)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_REFERENCE)
					.attributeKey(LINE_REFERENCE_KIND_CUSTOM_FIELD_1)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_COUNTRY_CODE)
					.mappingRule(DeliveryMappingConstants.MAPPING_RULE_RECEIVER_COUNTRY_CODE)
					.mappingRuleValue("DE")
					.build(),
			JsonMappingConfig.builder()
					.seqNo(240)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_REFERENCE)
					.attributeKey(LINE_REFERENCE_KIND_CUSTOM_FIELD_1)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_COUNTRY_CODE)
					.mappingRule(DeliveryMappingConstants.MAPPING_RULE_RECEIVER_COUNTRY_CODE)
					.mappingRuleValue("RO")
					.build(),
			// Shipment-level detail groups (customs info) — produce no output at advise time; verify null fallback
			JsonMappingConfig.builder()
					.seqNo(250)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_DETAIL_GROUP)
					.groupKey(DETAIL_GROUP_KEY_CUSTOMS_INFO)
					.attributeKey(DETAIL_KIND_SHIPPER_EORI)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_SHIPPER_EORI)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(260)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_DETAIL_GROUP)
					.groupKey(DETAIL_GROUP_KEY_CUSTOMS_INFO)
					.attributeKey(DETAIL_KIND_SHIPPER_EORI)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_COUNTRY_CODE)
					.mappingRule(DeliveryMappingConstants.MAPPING_RULE_RECEIVER_COUNTRY_CODE)
					.mappingRuleValue("DE")
					.build(),
			JsonMappingConfig.builder()
					.seqNo(270)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_DETAIL_GROUP)
					.groupKey(DETAIL_GROUP_KEY_CUSTOMS_INFO)
					.attributeKey(DETAIL_KIND_SHIPPER_EORI)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_COUNTRY_CODE)
					.mappingRule(DeliveryMappingConstants.MAPPING_RULE_RECEIVER_COUNTRY_CODE)
					.mappingRuleValue("RO")
					.build(),
			JsonMappingConfig.builder()
					.seqNo(280)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_REFERENCE)
					.attributeKey(LINE_REFERENCE_KIND_CONTENTS)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_NAME)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(290)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_REFERENCE)
					.attributeKey(LINE_REFERENCE_KIND_CUSTOM_FIELD_2)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_VALUE)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(300)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_REFERENCE)
					.attributeKey(LINE_REFERENCE_KIND_CUSTOM_FIELD_3)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_CURRENCY_CODE)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(310)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_REFERENCE)
					.attributeKey(LINE_REFERENCE_KIND_CUSTOM_FIELD_4)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_VALUE)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(320)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_REFERENCE)
					.attributeKey(LINE_REFERENCE_KIND_CUSTOM_FIELD_5)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_CUSTOMS_TARIFF)
					.build(),
			JsonMappingConfig.builder()
					.seqNo(330)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_DETAIL_GROUP)
					.groupKey(DETAIL_GROUP_KEY_CUSTOMS_ARTICLE)
					.attributeKey(DETAIL_KIND_UNIT_WEIGHT)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_WEIGHT_G)
					.build(),
			// Line reference for gross weight — available at advise time via JsonDeliveryAdvisorRequestItem.getValue()
			JsonMappingConfig.builder()
					.seqNo(340)
					.attributeType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_REFERENCE)
					.attributeKey(LINE_REFERENCE_KIND_CUSTOM_FIELD_6)
					.attributeValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_GROSS_WEIGHT_KG)
					.build()
	));
}