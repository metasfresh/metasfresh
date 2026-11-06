package de.metas.handlingunits.rest_api.createfromlabel;

import com.google.common.collect.ImmutableMap;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Everything a scanned product label yields, as computed by {@link LabelValuesService}: the product, the values the label carries
 * (lot, best-before, production date) and the quantity / weight of the new CU. Shared by the proposal and the create command.
 */
@Value
@Builder
class LabelValues
{
	@NonNull ProductId productId;
	/** {@code M_Product.Value} */
	@NonNull String productValue;
	/** {@code M_Product.Name} */
	@NonNull String productName;

	@Nullable String lotNo;
	@Nullable LocalDate bestBeforeDate;
	@Nullable LocalDate productionDate;

	@NonNull UomId stockUomId;
	/** {@link #stockUomId} as code; not for display (the JSON shows the translated symbol) */
	@NonNull X12DE355 stockUom;
	/** the quantity the scanned code carries (the label weight of a product stocked in kg), in {@link #stockUomId}; null: the code carries none, the user types it */
	@Nullable Quantity qty;
	/** set only when the label weight becomes the CU's net weight (catch-weight product) */
	@Nullable Quantity weightKgForCU;
	/** the label carries a weight that this product does not use */
	boolean isWeightIgnored;

	/**
	 * The attribute values the label carries, in display order (lot, best-before, production date), without the attributes for which
	 * {@code isActiveAttribute} is false: the preview does not show them and the booking does not store them (one rule for both steps,
	 * so OK never fails on a value the preview left out).
	 */
	@NonNull
	ImmutableMap<AttributeCode, Object> getAttributeValues(@NonNull final Predicate<AttributeCode> isActiveAttribute)
	{
		final ImmutableMap.Builder<AttributeCode, Object> result = ImmutableMap.builder();
		putIfActive(result, AttributeConstants.ATTR_LotNumber, lotNo, isActiveAttribute);
		putIfActive(result, AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate, isActiveAttribute);
		putIfActive(result, AttributeConstants.ProductionDate, productionDate, isActiveAttribute);
		return result.build();
	}

	private static void putIfActive(
			@NonNull final ImmutableMap.Builder<AttributeCode, Object> result,
			@NonNull final AttributeCode attributeCode,
			@Nullable final Object value,
			@NonNull final Predicate<AttributeCode> isActiveAttribute)
	{
		if (value != null && isActiveAttribute.test(attributeCode))
		{
			result.put(attributeCode, value);
		}
	}
}
