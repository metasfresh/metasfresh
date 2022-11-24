package de.metas.manufacturing.workflows_api;

import de.metas.i18n.ITranslatableString;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderId;

import java.time.ZonedDateTime;

@Value
@Builder
public class PPOrderReference
{
	@NonNull PPOrderId ppOrderId;
	@NonNull String documentNo;
	@NonNull ZonedDateTime datePromised;
	@NonNull ITranslatableString productName;
	@NonNull Quantity qtyRequiredToProduce;
}
