package de.metas.handlingunits;

import de.metas.common.util.time.SystemTime;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/** Request to create one new HU (a CU) of a product by a single-HU inventory, see {@code HUQtyService#createHUFromInventory} */
@Value
@Builder
public class CreateHUFromInventoryRequest
{
	@NonNull @Builder.Default ZonedDateTime date = SystemTime.asZonedDateTime();
	@NonNull ProductId productId;
	@NonNull Quantity qty;
	@NonNull LocatorId locatorId;
	@NonNull @Builder.Default ImmutableAttributeSet attributes = ImmutableAttributeSet.EMPTY;
	/** the description of the inventory document */
	@Nullable String description;
}
