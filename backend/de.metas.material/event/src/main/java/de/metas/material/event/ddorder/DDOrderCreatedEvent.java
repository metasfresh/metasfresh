package de.metas.material.event.ddorder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DDOrderCreatedEvent extends AbstractDDOrderEvent
{
	public static final String TYPE = "DDOrderCreatedEvent";

	@JsonCreator
	@Builder
	public DDOrderCreatedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("ddOrder") @NonNull final DDOrder ddOrder,
			@JsonProperty("fromWarehouseId") final WarehouseId fromWarehouseId,
			@JsonProperty("toWarehouseId") final WarehouseId toWarehouseId,
			@JsonProperty("supplyRequiredDescriptor") @Nullable final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		super(
				eventDescriptor,
				ddOrder,
				fromWarehouseId,
				toWarehouseId,
				supplyRequiredDescriptor);
	}
}
