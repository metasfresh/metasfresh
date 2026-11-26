package de.metas.handlingunits.shipping;

import de.metas.handlingunits.HuUnitType;
import de.metas.product.PackageDimensions;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class PackedHUShippingInfo
{
	/**
	 * Null when the HU's weight cannot be derived (e.g. product has no gross weight defined and no WeightGross attribute set).
	 */
	@Nullable Quantity weightInKg;

	@NonNull PackageDimensions dimensions;

	@NonNull HuUnitType topLevelType;

	@Nullable String countryOfOrigin;
}
