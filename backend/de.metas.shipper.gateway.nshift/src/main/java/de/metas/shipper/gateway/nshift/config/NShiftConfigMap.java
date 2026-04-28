package de.metas.shipper.gateway.nshift.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.shipping.ShipperId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
class NShiftConfigMap
{
	public static final NShiftConfigMap EMPTY = new NShiftConfigMap(ImmutableList.of());

	private final ImmutableMap<ShipperId, NShiftConfig> byShipperId;

	private NShiftConfigMap(final List<NShiftConfig> list)
	{
		this.byShipperId = Maps.uniqueIndex(list, NShiftConfig::getShipperId);
	}

	public static NShiftConfigMap ofList(final List<NShiftConfig> list)
	{
		return list.isEmpty() ? null : new NShiftConfigMap(list);
	}

	public static Collector<NShiftConfig, ?, NShiftConfigMap> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(NShiftConfigMap::ofList);
	}

	public @NonNull NShiftConfig getByShipperId(final @NonNull ShipperId shipperId)
	{
		final NShiftConfig config = byShipperId.get(shipperId);
		if (config == null)
		{
			throw new AdempiereException("No config found for shipper: " + shipperId);
		}
		return config;
	}
}
