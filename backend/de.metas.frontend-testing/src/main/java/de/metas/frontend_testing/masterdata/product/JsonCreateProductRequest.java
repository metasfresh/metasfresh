package de.metas.frontend_testing.masterdata.product;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.gs1.GTIN;
import de.metas.gs1.ean13.EAN13;
import de.metas.gs1.ean13.EAN13ProductCode;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.uom.X12DE355;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.eevolution.api.BOMComponentIssueMethod;
import org.eevolution.api.BOMComponentType;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonCreateProductRequest
{
	// Allow exact value/name (if both null, use valuePrefix or timestamp)
	@Nullable String value;
	@Nullable String name;

	/**
	 * Product type — either the enum name ({@code "Item"}, {@code "Service"}, …) or the
	 * AD ref-list code ({@code "I"}, {@code "S"}, …). Defaults to {@code Item} when omitted.
	 * Non-item types (Service, Resource, ExpenseType, …) are NOT stocked by default — useful
	 * for "bracket" / "header" products that carry a price but are not physically shipped.
	 */
	@Nullable String type;

	/**
	 * Explicit {@code M_Product.IsStocked} override. When {@code null} the value is derived
	 * from {@link #type} ({@code Item} → stocked, anything else → not stocked).
	 * Set to {@code false} on an {@code Item} product when you want a "bracket" / bundle
	 * line that participates in the order flow but should not be tracked as stock.
	 */
	@Nullable Boolean isStocked;

	/**
	 * Explicit {@code M_Product.IsSelfPacked} override. When {@code null} the column keeps its
	 * default ({@code false}). Set to {@code true} to mark the product as self-packed — required by
	 * the mobileUI mass-printing flow, which only packs self-packed products and skips the rest.
	 */
	@Nullable Boolean isSelfPacked;

	@Nullable String valuePrefix;
	@Nullable RandomValueSpec randomValue;
	@Nullable GTIN gtin;
	@Nullable EAN13ProductCode ean13ProductCode;
	@Nullable X12DE355 uom;
	@Nullable List<UOMConversion> uomConversions;

	@Nullable BigDecimal price;
	@Nullable List<Price> prices;

	@Nullable List<BPartner> bpartners;

	@Nullable BOM bom;

	/**
	 * Attribute Set name to associate with the product.
	 * If set, the product will have this M_AttributeSet_ID assigned,
	 * enabling the Attributes button in the Test Window.
	 * Example values: "Lot", "Serial", "LotSerial"
	 */
	@Nullable String attributeSetName;

	/**
	 * Identifier of a {@link de.metas.frontend_testing.masterdata.compensation_group.JsonCompensationGroupSchemaRequest}
	 * created in the same request. When set, the product is linked via
	 * {@code M_Product.C_CompensationGroup_Schema_ID} after the schema is created — this turns the
	 * product into a "trigger product" that materialises the schema's template lines on an order.
	 */
	@Nullable Identifier compensationGroupSchema;

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class RandomValueSpec
	{
		int size;
		boolean isIncludeDigits;
		boolean isIncludeLetters;
	}

	@Value
	@Builder
	@Jacksonized
	public static class UOMConversion
	{
		@NonNull X12DE355 from;
		@NonNull X12DE355 to;
		@NonNull BigDecimal multiplyRate;
		boolean isCatchUOMForProduct;
	}

	@Value
	@Builder
	@Jacksonized
	public static class Price
	{
		@NonNull BigDecimal price;
		@Nullable X12DE355 uom;
		@Nullable InvoicableQtyBasedOn invoicableQtyBasedOn;
	}

	@Value
	@Builder
	@Jacksonized
	public static class BPartner
	{
		@NonNull Identifier bpartner;
		@Nullable EAN13 ean13;
	}

	@Value
	@Builder
	@Jacksonized
	public static class BOM
	{
		@NonNull List<BOMLine> lines;
	}

	@Value
	@Builder
	@Jacksonized
	public static class BOMLine
	{
		@NonNull Identifier product;
		@NonNull BigDecimal qty;
		boolean percentage;
		@Nullable X12DE355 uom;
		@Nullable BOMComponentType componentType;
		@Nullable BOMComponentIssueMethod issueMethod;
		@Nullable String pickingInstruction;
		@Nullable BigDecimal issuingTolerancePerc;
	}
}
