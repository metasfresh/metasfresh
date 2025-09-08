package de.metas.handlingunits.shipmentschedule.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.JsonObjectMapperHolder;
import de.metas.inout.ShipmentScheduleId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class QtyToDeliverMap
{
	public static final QtyToDeliverMap EMPTY = new QtyToDeliverMap(ImmutableMap.of());

	@NonNull Map<ShipmentScheduleId, StockQtyAndUOMQty> map;

	@Builder
	@Jacksonized
	private QtyToDeliverMap(@NonNull final Map<ShipmentScheduleId, StockQtyAndUOMQty> map)
	{
		this.map = ImmutableMap.copyOf(map);
	}

	public static QtyToDeliverMap ofMap(final Map<ShipmentScheduleId, StockQtyAndUOMQty> map)
	{
		return map.isEmpty() ? EMPTY : new QtyToDeliverMap(ImmutableMap.copyOf(map));
	}

	public static QtyToDeliverMap of(@NonNull final ShipmentScheduleId shipmentScheduleId, @NonNull StockQtyAndUOMQty qtyToDeliver)
	{
		return ofMap(ImmutableMap.of(shipmentScheduleId, qtyToDeliver));
	}

	@NonNull
	public static QtyToDeliverMap fromJson(@Nullable final String json)
	{
		if (json == null || Check.isBlank(json))
		{
			return QtyToDeliverMap.EMPTY;
		}

		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(json, QtyToDeliverMap.class);
		}
		catch (JsonProcessingException e)
		{
			throw new AdempiereException("Failed deserializing " + QtyToDeliverMap.class.getSimpleName() + " from json: " + json, e);
		}
	}

	public String toJsonString()
	{
		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(this);
		}
		catch (JsonProcessingException e)
		{
			throw new AdempiereException("Failed serializing " + this, e);
		}
	}

	public boolean isEmpty() {return map.isEmpty();}

	public ImmutableSet<ShipmentScheduleId> getShipmentScheduleIds() {return ImmutableSet.copyOf(map.keySet());}

	@Nullable
	public StockQtyAndUOMQty getQtyToDeliver(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return map.get(shipmentScheduleId);
	}
}
