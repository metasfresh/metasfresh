package de.metas.inventory.mobileui.job;

import de.metas.i18n.ITranslatableString;
import de.metas.inventory.InventoryLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

@Value
@Builder
public class InventoryJobLine
{
	@NonNull InventoryLineId id;
	
	@NonNull ProductId productId;
	@NonNull String productNo;
	@NonNull ITranslatableString productName;

	@NonNull LocatorId locatorId;
	@NonNull String locatorName;


	@NonNull Quantity qtyBooked;
	@NonNull Quantity qtyCount;

	public UomId getUomId()
	{
		return Quantity.getCommonUomIdOfAll(qtyBooked, qtyCount);
	}

	public String getUOMSymbol()
	{
		Quantity.getCommonUomIdOfAll(qtyBooked, qtyCount);
		return qtyBooked.getUOMSymbol();
	}

}
