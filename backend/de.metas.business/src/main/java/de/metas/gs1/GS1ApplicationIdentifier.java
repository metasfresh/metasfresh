package de.metas.gs1;

import de.metas.bpartner.GLN;
import lombok.Getter;
import lombok.NonNull;

/**
 * Based on GS1 General Specifications, Release 17.0.1.
 * <p>
 *
 * @see <a href="http://www.gs1.org/sites/default/files/docs/barcodes/GS1_General_Specifications.pdf">GS1 General Specifications</a>
 */
@Getter
public enum GS1ApplicationIdentifier
{
	/**
	 * Identification of a logistic unit (SSCC).
	 */
	SSCC("00", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, 18),

	/**
	 * Identification of a trade item (GTIN).
	 */
	GTIN("01", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, 14),

	/**
	 * Identification of trade items contained in a logistic unit. The GTIN of the trade items contained is the GTIN of
	 * the highest level of trade item contained in the logistic unit.
	 *
	 * @see GS1ApplicationIdentifier#COUNT_OF_TRADE_ITEMS
	 */
	CONTAINED_GTIN("02", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, 14),

	/**
	 * Batch or lot number, associates an item with information the manufacturer considers relevant for traceability of
	 * the trade item to which the element string is applied. The data may refer to the trade item itself or to items
	 * contained.
	 */
	BATCH_OR_LOT_NUMBER("10", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 20),

	/**
	 * Production date, the production or assembly date determined by the manufacturer. The date may refer to the trade
	 * item itself or to items contained.
	 */
	PRODUCTION_DATE("11", GS1ApplicationIdentifierFormat.DATE),

	/**
	 * Due date for amount on payment slip.
	 *
	 * @see GS1ApplicationIdentifier#PAYMENT_SLIP_REFERENCE_NUMBER
	 * @see GS1ApplicationIdentifier#INVOICING_PARTY
	 */
	DUE_DATE("12", GS1ApplicationIdentifierFormat.DATE),

	/**
	 * Packaging date, the date when the goods were packed as determined by the packager. The date may refer to the
	 * trade item itself or to items contained.
	 */
	PACKAGING_DATE("13", GS1ApplicationIdentifierFormat.DATE),

	/**
	 * Best before date on the label or package signifies the end of the period under which the product will retain
	 * specific quality attributes or claims even though the product may continue to retain positive quality attributes
	 * after this date.
	 */
	BEST_BEFORE_DATE("15", GS1ApplicationIdentifierFormat.DATE),

	/**
	 * Sell by date, specified by the manufacturer as the last date the retailer is to offer the product for sale to the
	 * consumer.
	 */
	SELL_BY_DATE("16", GS1ApplicationIdentifierFormat.DATE),

	/**
	 * Expiration date, determines the limit of consumption or use of a product / coupon.
	 */
	EXPIRATION_DATE("17", GS1ApplicationIdentifierFormat.DATE),

	/**
	 * Product variant, distinguished a variant from the usual item if the variation is not sufficiently significant to
	 * require a separate Global Trade Item Number (GTIN) and the variation is relevant only to the brand owner and any
	 * third party acting on its behalf.
	 */
	VARIANT_NUMBER("20", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, 2),

	/**
	 * Serial number, assigned to an entity for its lifetime. When combined with a GTIN, a serial number uniquely
	 * identifies an individual item.
	 */
	SERIAL_NUMBER("21", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 20),

	/**
	 * Additional product identification assigned by the manufacturer.
	 */
	ADDITIONAL_ITEM_ID("240", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 30),

	/**
	 * Customer part number.
	 */
	CUSTOMER_PART_NUMBER("241", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 30),

	/**
	 * Made-to-Order variation number, provides the additional data needed to uniquely identify a custom trade item.
	 */
	MADE_TO_ORDER_VARIATION_NUMBER("242", GS1ApplicationIdentifierFormat.NUMERIC_VARIABLE, 6),

	/**
	 * Packaging component number, assigned to the packaging component for its lifetime. When associated with a GTIN, a
	 * PCN uniquely identifies the relationship between a finished consumer trade item and one of its packaging
	 * components.
	 */
	PACKAGING_COMPONENT_NUMBER("243", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 20),

	/**
	 * Secondary serial number. A secondary serial number represents the serial number of a component of an item.
	 *
	 * @see GS1ApplicationIdentifier#SERIAL_NUMBER
	 */
	SECONDARY_SERIAL_NUMBER("250", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 30),

	/**
	 * Reference to source entity, an attribute of a trade item used to refer to the original item from which the trade
	 * item was derived.
	 */
	REFERENCE_TO_SOURCE_ENTITY("251", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 30),

	/**
	 * Global Document Type Identifier. The GDTI is used to identify a document type with an optional serial number.
	 */
	GDTI("253", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 13, 17),

	/**
	 * GLN extension component.
	 *
	 * @see GS1ApplicationIdentifier#PHYSICAL_LOCATION
	 */
	GLN_EXTENSION_COMPONENT("254", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 20),

	/**
	 * Global Coupon Number, provides a globally unique identification for a coupon, with an optional serial number.
	 */
	GCN("255", GS1ApplicationIdentifierFormat.NUMERIC_VARIABLE, 13, 25),

	/**
	 * Variable count, number of items contained in a variable measure trade item.
	 */
	VARIABLE_COUNT("30", GS1ApplicationIdentifierFormat.NUMERIC_VARIABLE, 8),

	ITEM_NET_WEIGHT_KG("310", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_LENGTH_METRES("311", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_WIDTH_METRES("312", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_HEIGHT_METRES("313", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_AREA_SQUARE_METRES("314", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_NET_VOLUME_LITRES("315", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_NET_VOLUME_CUBIC_METRES("316", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_NET_WEIGHT_POUNDS("320", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_LENGTH_INCHES("321", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_LENGTH_FEET("322", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_LENGTH_YARDS("323", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_WIDTH_INCHES("324", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_WIDTH_FEET("325", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_WIDTH_YARDS("326", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_HEIGHT_INCHES("327", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_HEIGHT_FEET("328", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_HEIGHT_YARDS("329", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_AREA_SQUARE_INCHES("350", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_AREA_SQUARE_FEET("351", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_AREA_SQUARE_YARDS("352", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_NET_WEIGHT_TROY_OUNCES("356", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_NET_VOLUME_OUNCES("357", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_NET_VOLUME_QUARTS("360", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_NET_VOLUME_GALLONS("361", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_NET_VOLUME_CUBIC_INCHES("364", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_NET_VOLUME_CUBIC_FEET("365", GS1ApplicationIdentifierFormat.DECIMAL),
	ITEM_NET_VOLUME_CUBIC_YARDS("366", GS1ApplicationIdentifierFormat.DECIMAL),

	CONTAINER_GROSS_WEIGHT_KG("330", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_LENGTH_METRES("331", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_WIDTH_METRES("332", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_HEIGHT_METRES("333", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_AREA_SQUARE_METRES("334", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_VOLUME_LITRES("335", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_VOLUME_CUBIC_METRES("336", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_GROSS_WEIGHT_POUNDS("340", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_LENGTH_INCHES("341", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_LENGTH_FEET("342", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_LENGTH_YARDS("343", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_WIDTH_INCHES("344", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_WIDTH_FEET("345", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_WIDTH_YARDS("346", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_HEIGHT_INCHES("347", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_HEIGHT_FEET("348", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_HEIGHT_YARDS("349", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_AREA_SQUARE_INCHES("353", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_AREA_SQUARE_FEET("354", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_AREA_SQUARE_YARDS("355", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_VOLUME_QUARTS("362", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_VOLUME_GALLONS("363", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_VOLUME_CUBIC_INCHES("367", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_VOLUME_CUBIC_FEET("368", GS1ApplicationIdentifierFormat.DECIMAL),
	CONTAINER_VOLUME_CUBIC_YARDS("369", GS1ApplicationIdentifierFormat.DECIMAL),

	/**
	 * Kilograms per square metre, of a particular trade item
	 */
	KILOGRAMS_PER_SQUARE_METRE("337", GS1ApplicationIdentifierFormat.DECIMAL),

	/**
	 * Count of trade items contained in a logistic unit.
	 *
	 * @see GS1ApplicationIdentifier#CONTAINED_GTIN
	 */
	COUNT_OF_TRADE_ITEMS("37", GS1ApplicationIdentifierFormat.NUMERIC_VARIABLE, 8),

	/**
	 * The amount payable of a payment slip or the coupon value.
	 *
	 * @see GS1ApplicationIdentifier#PAYMENT_SLIP_REFERENCE_NUMBER
	 * @see GS1ApplicationIdentifier#INVOICING_PARTY
	 * @see GS1ApplicationIdentifier#GCN
	 */
	AMOUNT_PAYABLE("390", GS1ApplicationIdentifierFormat.DECIMAL, 15),

	/**
	 * Amount payable and ISO currency code.
	 *
	 * @see GS1ApplicationIdentifier#PAYMENT_SLIP_REFERENCE_NUMBER
	 * @see GS1ApplicationIdentifier#INVOICING_PARTY
	 */
	AMOUNT_PAYABLE_WITH_CURRENCY("391", GS1ApplicationIdentifierFormat.CUSTOM),

	/**
	 * Amount payable for a variable measure trade item. Refers to an item identified by the Global Trade Item Number
	 * (GTIN) of a variable measure trade item and is expressed in local currency.
	 */
	AMOUNT_PAYABLE_PER_SINGLE_ITEM("392", GS1ApplicationIdentifierFormat.DECIMAL, 15),

	/**
	 * Amount payable for a variable measure trade item and ISO currency code. Refers to an item
	 * identified with the Global Trade Item Number (GTIN) of a variable measure trade item and is
	 * expressed in the indicated currency.
	 */
	AMOUNT_PAYABLE_PER_SINGLE_ITEM_WITH_CURRENCY("393", GS1ApplicationIdentifierFormat.CUSTOM),

	/**
	 * Percentage discount of a coupon.
	 */
	COUPON_DISCOUNT_PERCENTAGE("394", GS1ApplicationIdentifierFormat.DECIMAL, 4, 4),

	/**
	 * Customer’s purchase order number.  It contains the number of the purchase order assigned by the company
	 * that issued the order. The composition and content of the order number is left to the discretion of
	 * the customer.
	 */
	CUSTOMER_PURCHASE_ORDER_NUMBER("400", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 30),

	/**
	 * Global Identification Number for Consignment. Identifies a logical grouping of goods (one or more physical
	 * entities) that has been consigned to a freight forwarder and is intended to be transported as a whole.
	 */
	CONSIGNMENT_NUMBER("401", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 30),

	/**
	 * Global Shipment Identification Number.
	 */
	SHIPMENT_NUMBER("402", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, 17),

	/**
	 * Routing code, assigned by the parcel carrier and is an attribute of the SSCC (Serial Shipping Container Code).
	 */
	ROUTING_CODE("403", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 30),

	/**
	 * Ship to - Deliver to Global Location Number.
	 */
	SHIP_TO_LOCATION("410", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, GLN.LENGTH),

	/**
	 * Bill to - Invoice to Global Location Number.
	 */
	BILL_TO_LOCATION("411", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, GLN.LENGTH),

	/**
	 * Purchased from Global Location Number.
	 */
	PURCHASED_FROM_LOCATION("412", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, GLN.LENGTH),

	/**
	 * Ship for - Deliver for - Forward to Global Location Number.
	 */
	SHIP_FOR_LOCATION("413", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, GLN.LENGTH),

	/**
	 * Identification of a physical location - Global Location Number.
	 */
	PHYSICAL_LOCATION("414", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, GLN.LENGTH),

	/**
	 * Global Location Number of the invoicing party.
	 */
	INVOICING_PARTY("415", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, GLN.LENGTH),

	/**
	 * GLN of the production or service location.
	 */
	PRODUCTION_OR_SERVICE_LOCATION("416", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, GLN.LENGTH),

	/**
	 * Ship to - Deliver to postal code within a single postal authority.
	 */
	SHIP_TO_POSTAL_CODE("420", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 20),

	/**
	 * Ship to - Deliver to postal code with three-digit ISO country code.
	 */
	SHIP_TO_POSTAL_CODE_WITH_COUNTRY("421", GS1ApplicationIdentifierFormat.CUSTOM),

	/**
	 * Country of origin of a trade item. ISO-3166.
	 */
	COUNTRY_OF_ORIGIN("422", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, 3),

	/**
	 * Country or countries of initial processing.
	 */
	COUNTRY_OF_INITIAL_PROCESSING("423", GS1ApplicationIdentifierFormat.CUSTOM),

	/**
	 * Country of processing. ISO-3166.
	 */
	COUNTRY_OF_PROCESSING("424", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, 3),

	/**
	 * Country or countries of disassembly.
	 */
	COUNTRY_OF_DISASSEMBLY("425", GS1ApplicationIdentifierFormat.CUSTOM),

	/**
	 * Country covering full process chain. ISO-3166.
	 */
	COUNTRY_OF_FULL_PROCESS_CHAIN("426", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, 3),

	/**
	 * Country subdivision of origin code for a trade item. ISO based country subdivision code (e.g. provinces, states,
	 * cantons, etc.) of a country’s local region origin of the trade item. The ISO country subdivision code field
	 * contains up to three alphanumeric characters after separator of ISO 3166-2:2007 that is the principal subdivision
	 * of origin.
	 *
	 * @see GS1ApplicationIdentifier#COUNTRY_OF_ORIGIN
	 */
	COUNTRY_SUBDIVISION_OF_ORIGIN("427", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 3),

	/**
	 * NATO Stock Number (NSN).
	 */
	NATO_STOCK_NUMBER("7001", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, 13),

	/**
	 * UN/ECE meat carcasses and cuts classification.
	 */
	MEAT_CUT("7002", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 30),

	/**
	 * Expiration date and time.
	 */
	EXPIRATION_DATE_AND_TIME("7003", GS1ApplicationIdentifierFormat.CUSTOM),

	/**
	 * Active potency.
	 */
	ACTIVE_POTENCY("7004", GS1ApplicationIdentifierFormat.NUMERIC_VARIABLE, 4),

	/**
	 * Catch area, identifies where the fisheries product was caught using the international fishing areas and subareas
	 * as defined by the United Nations Fisheries and Aquaculture Department of the Food and Agricultural Organization.
	 */
	CATCH_AREA("7005", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 12),

	/**
	 * First freeze date.
	 */
	FIRST_FREEZE_DATE("7006", GS1ApplicationIdentifierFormat.DATE),

	/**
	 * Harvest date. A harvest date or date range.
	 */
	HARVEST_DATE("7007", GS1ApplicationIdentifierFormat.CUSTOM, 6, 12),

	/**
	 * Species for fishery purposes, according to the 3-alpha Aquatic Sciences and Fisheries Information System (ASFIS)
	 * list of species.
	 */
	AQUATIC_SPECIES("7008", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 3),

	/**
	 * Fishing gear type, as defined by the United Nations Fisheries and Aquaculture Department of the Food and
	 * Agricultural Organization (FAO).
	 */
	FISHING_GEAR_TYPE("7009", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 10),

	/**
	 * Production method, provides the production method for fish and seafood as specified by the Fisheries and Aquaculture
	 * Department of the Food and Agricultural Organization (FAO) of the United Nations.
	 */
	PRODUCTION_METHOD("7010", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 2),

	/**
	 * Refurbishment lot ID, identifies a batch of items that were remanufactured to the original specifications using a
	 * combination of reused, repaired and new parts.
	 */
	REFURBISHMENT_LOT_ID("7020", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 20),

	/**
	 * Functional status.
	 */
	FUNCTIONAL_STATUS("7021", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 20),

	/**
	 * Revision status.
	 */
	REVISION_STATUS("7021", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 20),

	/**
	 * Global Individual Asset Identifier of an assembly.
	 */
	ASSEMBLY_GIAI("7023", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 30),

	// No constants defined for 703s Number of processor with three-digit ISO country code. Query parse result using strings.

	// No constants defined for 710-719 National Healthcare Reimbursement Number (NHRN). Query parse result using strings.

	/**
	 * Roll products - width, length, core diameter, direction, splices.
	 */
	ROLL_PRODUCT_DIMENSIONS("8001", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, 14),

	/**
	 * Cellular mobile telephone identifier.
	 */
	MOBILE_PHONE_IDENTIFIER("8002", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 20),

	/**
	 * Global Returnable Asset Identifier.
	 */
	GRAI("8003", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 14, 30),

	/**
	 * Global Individual Asset Identifier (GIAI).
	 */
	GIAI("8004", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 30),

	/**
	 * Price per unit of measure.
	 */
	PRICE_PER_UNIT("8005", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, 6),

	/**
	 * Identification of the components of a trade item.
	 */
	GCTIN("8006", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, 18),

	/**
	 * International Bank Account Number (IBAN).
	 */
	IBAN("8007", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 34),

	/**
	 * Date and time of production.
	 */
	PRODUCTION_DATE_AND_TIME("8008", GS1ApplicationIdentifierFormat.CUSTOM, 8, 12),

	/**
	 * Component / Part Identifier.
	 */
	COMPONENT_OR_PART_IDENTIFIER("8010", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 30),

	/**
	 * Component / Part Identifier serial number.
	 */
	COMPONENT_OR_PART_IDENTIFIER_SERIAL_NUMBER("8011", GS1ApplicationIdentifierFormat.NUMERIC_VARIABLE, 12),

	/**
	 * Software version.
	 */
	SOFTWARE_VERSION("8012", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 20),

	/**
	 * Global Service Relation Number of provider.
	 */
	GSRN_PROVIDER("8017", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, 18),

	/**
	 * Global Service Relation Number of recipient.
	 */
	GSRN_RECIPIENT("8018", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, 18),

	/**
	 * Service Relation Instance Number.
	 */
	SRIN("8019", GS1ApplicationIdentifierFormat.NUMERIC_VARIABLE, 10),

	/**
	 * Payment slip reference number. Assigned by the invoicing party, identifies a payment slip within a given Global
	 * Location Number (GLN) of an invoicing party. Together with the GLN of the invoicing party, the payment slip
	 * reference number uniquely identifies a payment slip.
	 */
	PAYMENT_SLIP_REFERENCE_NUMBER("8020", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 25),

	/**
	 * Coupon code identification for use in North America.
	 */
	COUPON_CODE_IDENTIFICATION_NORTH_AMERICA("8110", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 70),

	/**
	 * Loyalty points of a coupon.
	 *
	 * @see GS1ApplicationIdentifier#GCN
	 */
	COUPON_LOYALTY_POINTS("8111", GS1ApplicationIdentifierFormat.NUMERIC_FIXED, 4),

	/**
	 * Paperless coupon code identification for use in North America.
	 */
	PAPERLESS_COUPON_CODE_IDENTIFICATION_NORTH_AMERICA("8112", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 70),

	/**
	 * Extended packaging URL.
	 */
	EXTENDED_PACKAGING_URL("8200", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 70),

	/**
	 * Information mutually agreed between trading partners.
	 */
	MUTUALLY_AGREED_INFORMATION("90", GS1ApplicationIdentifierFormat.ALPHANUMERIC_VARIABLE, 30);

	// No constants defined for 91-99 Company internal information. Query parse result using strings.

	@NonNull private final String key;
	@NonNull private final GS1ApplicationIdentifierFormat format;
	private final int minLength;
	private final int maxLength;

	GS1ApplicationIdentifier(@NonNull String key, @NonNull GS1ApplicationIdentifierFormat format, int maxLength)
	{
		this.key = key;
		this.format = format;
		this.minLength = 1;
		this.maxLength = maxLength;
	}

	GS1ApplicationIdentifier(@NonNull String key, @NonNull GS1ApplicationIdentifierFormat format, int minLength, int maxLength)
	{
		this.key = key;
		this.format = format;
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	GS1ApplicationIdentifier(@NonNull String key, @NonNull GS1ApplicationIdentifierFormat format)
	{
		this.key = key;
		this.format = format;
		if (format == GS1ApplicationIdentifierFormat.DECIMAL || format == GS1ApplicationIdentifierFormat.DATE)
		{
			this.minLength = 6;
			this.maxLength = 6;
		}
		else
		{
			this.minLength = -1;
			this.maxLength = -1;
		}
	}
}
