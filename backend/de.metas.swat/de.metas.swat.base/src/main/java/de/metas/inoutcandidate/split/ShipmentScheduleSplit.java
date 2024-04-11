package de.metas.inoutcandidate.split;

import de.metas.document.dimension.Dimension;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;

@Data
@Builder
public class ShipmentScheduleSplit
{
	@Nullable private ShipmentScheduleSplitId id;

	@NonNull private final ShipmentScheduleId shipmentScheduleId;
	@NonNull private LocalDate deliveryDate;
	@NonNull private Quantity qtyToDeliver;
	@NonNull @Builder.Default private Dimension dimension = Dimension.EMPTY;

	@Nullable @Setter(AccessLevel.NONE) private InOutAndLineId shipmentLineId;
	@Setter(AccessLevel.NONE) private boolean processed;

	@NonNull
	public ShipmentScheduleSplitId getIdNotNull()
	{
		return Check.assumeNotNull(id, "id of `{}` shall be set", this);
	}

	public Optional<ShipmentScheduleSplitId> getIdIfSet() {return Optional.ofNullable(id);}

	public void assertNotProcessed()
	{
		if (processed)
		{
			throw new AdempiereException("@Processed@");
		}
	}

	public void markAsProcessed(@NonNull final InOutAndLineId shipmentLineId)
	{
		this.processed = true;
		this.shipmentLineId = shipmentLineId;
	}
}
