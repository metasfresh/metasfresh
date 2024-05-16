package de.metas.manufacturing.job.model;

import de.metas.i18n.ITranslatableString;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderId;

import java.time.ZonedDateTime;

@Value
@Builder
public class ManufacturingJobReference
{
	@NonNull PPOrderId ppOrderId;
	@NonNull String documentNo;
	@NonNull ZonedDateTime datePromised;
	@NonNull ITranslatableString productName;
	@NonNull Quantity qtyRequiredToProduce;
	boolean isJobStarted;
}
