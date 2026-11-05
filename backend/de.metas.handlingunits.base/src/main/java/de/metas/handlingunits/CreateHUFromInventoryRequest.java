package de.metas.handlingunits;

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
	/** the movement date of the inventory document (mandatory, no default) */
	@NonNull ZonedDateTime date;
	@NonNull ProductId productId;
	@NonNull Quantity qty;
	@NonNull LocatorId locatorId;
	@NonNull @Builder.Default ImmutableAttributeSet attributes = ImmutableAttributeSet.EMPTY;
	/** the description of the inventory document */
	@Nullable String description;
	/** the net weight (kg) to set on the new CU after the inventory completed; {@code null}: the weight is not touched */
	@Nullable Quantity weightInKg;
	/** the scanned label string to store in the new HU's {@code HU_QRCode} attribute, so picking finds the HU by that label; {@code null}: not stored */
	@Nullable String huQRCodeAttributeValue;
}
