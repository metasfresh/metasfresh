package de.metas.manufacturing.job.model;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
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
	@NonNull ZonedDateTime dateStartSchedule;
	@NonNull ITranslatableString productName;
	@NonNull String productValue;
	@NonNull Quantity qtyRequiredToProduce;
	boolean isJobStarted;

	@NonNull
	public ITranslatableString getProductValueAndProductName()
	{
		final TranslatableStringBuilder message = TranslatableStrings.builder()
				.append(getProductValue())
				.append(" ")
				.append(getProductName());

		return message.build();
	}
}
