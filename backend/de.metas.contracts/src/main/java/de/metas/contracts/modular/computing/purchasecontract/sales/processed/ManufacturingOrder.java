package de.metas.contracts.modular.computing.purchasecontract.sales.processed;

import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderId;

/**
 * Manufacturing Order (in modular contracts ubiquitous language)
 */
@Value
@Builder
public class ManufacturingOrder
{
	@NonNull PPOrderId id;
	@NonNull ProductId processedProductId;
}
