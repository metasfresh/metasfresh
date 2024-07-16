package de.metas.material.planning.ddorder;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.shipping.ShipperId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collector;

@Repository
public class DistributionNetworkRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, DistributionNetworksMap> cache = CCache.<Integer, DistributionNetworksMap>builder()
			.tableName(I_DD_NetworkDistribution.Table_Name)
			.additionalTableNameToResetFor(I_DD_NetworkDistributionLine.Table_Name)
			.build();

	public DistributionNetwork getById(@NonNull final DistributionNetworkId id)
	{
		return getMap().getById(id);
	}

	public DistributionNetworkLine getLineById(@NonNull final DistributionNetworkLineId lineId)
	{
		return getMap().getLineById(lineId);
	}

	public DistributionNetwork getEmptiesDistributionNetwork()
	{
		return getMap().getEmptiesDistributionNetwork();
	}

	private DistributionNetworksMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private DistributionNetworksMap retrieveMap()
	{
		final ImmutableListMultimap<DistributionNetworkId, I_DD_NetworkDistributionLine> lineRecords = queryBL.createQueryBuilder(I_DD_NetworkDistributionLine.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> DistributionNetworkId.ofRepoId(record.getDD_NetworkDistribution_ID()),
						record -> record
				));

		return queryBL.createQueryBuilder(I_DD_NetworkDistribution.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_DD_NetworkDistribution.COLUMNNAME_DD_NetworkDistribution_ID)
				.create()
				.stream()
				.map(headerRecord -> fromRecord(headerRecord, lineRecords.get(DistributionNetworkId.ofRepoId(headerRecord.getDD_NetworkDistribution_ID()))))
				.collect(DistributionNetworksMap.collect());

	}

	private static DistributionNetwork fromRecord(
			final I_DD_NetworkDistribution headerRecord,
			final List<I_DD_NetworkDistributionLine> lineRecords)
	{
		return DistributionNetwork.builder()
				.id(DistributionNetworkId.ofRepoId(headerRecord.getDD_NetworkDistribution_ID()))
				.name(headerRecord.getName())
				.lines(lineRecords.stream()
						.map(DistributionNetworkRepository::fromRecord)
						.collect(ImmutableList.toImmutableList()))
				.isEmptiesDistributionNetwork(headerRecord.isHUDestroyed())
				.build();
	}

	private static DistributionNetworkLine fromRecord(@NonNull final I_DD_NetworkDistributionLine lineRecord)
	{
		return DistributionNetworkLine.builder()
				.id(DistributionNetworkLineId.ofRepoId(lineRecord.getDD_NetworkDistributionLine_ID()))
				.sourceWarehouseId(WarehouseId.ofRepoId(lineRecord.getM_WarehouseSource_ID()))
				.targetWarehouseId(WarehouseId.ofRepoId(lineRecord.getM_Warehouse_ID()))
				.priorityNo(lineRecord.getPriorityNo())
				.shipperId(ShipperId.ofRepoId(lineRecord.getM_Shipper_ID()))
				.transferPercent(Percent.of(lineRecord.getPercent()))
				.transferDuration(Duration.ofDays(lineRecord.getTransfertTime().intValueExact()))
				.isAllowPush(lineRecord.isDD_AllowPush())
				.isKeepTargetPlant(lineRecord.isKeepTargetPlant())
				.build();
	}

	//
	//
	//
	//
	//

	@EqualsAndHashCode
	@ToString
	private static class DistributionNetworksMap
	{
		private final ImmutableMap<DistributionNetworkId, DistributionNetwork> byId;
		private final ImmutableList<DistributionNetwork> emptiesDistributionNetworks;
		private final ImmutableMap<DistributionNetworkLineId, ImmutablePair<DistributionNetwork, DistributionNetworkLine>> byLineId;

		private DistributionNetworksMap(final List<DistributionNetwork> list)
		{
			this.byId = Maps.uniqueIndex(list, DistributionNetwork::getId);
			this.byLineId = list.stream()
					.flatMap(network -> network.getLines().stream().map(line -> ImmutablePair.of(network, line)))
					.collect(ImmutableMap.toImmutableMap(
							pair -> pair.getRight().getId(),
							pair -> pair));
			this.emptiesDistributionNetworks = list.stream()
					.filter(DistributionNetwork::isEmptiesDistributionNetwork)
					.collect(ImmutableList.toImmutableList());
		}

		public static Collector<DistributionNetwork, ?, DistributionNetworksMap> collect()
		{
			return GuavaCollectors.collectUsingListAccumulator(DistributionNetworksMap::new);
		}

		public DistributionNetwork getById(@NonNull DistributionNetworkId id)
		{
			final DistributionNetwork distributionNetwork = byId.get(id);
			if (distributionNetwork == null)
			{
				throw new AdempiereException("No distribution network found with id " + id);
			}
			return distributionNetwork;
		}

		public DistributionNetwork getEmptiesDistributionNetwork()
		{
			if (emptiesDistributionNetworks.isEmpty())
			{
				throw new AdempiereException("No empties distribution network found");
			}
			else if (emptiesDistributionNetworks.size() > 1)
			{
				throw new AdempiereException("Multiple empties distribution networks found");
			}
			else
			{
				return emptiesDistributionNetworks.get(0);
			}
		}

		public DistributionNetworkLine getLineById(@NonNull final DistributionNetworkLineId lineId)
		{
			final ImmutablePair<DistributionNetwork, DistributionNetworkLine> pair = byLineId.get(lineId);
			if (pair == null)
			{
				throw new AdempiereException("No line found with id " + lineId);
			}
			return pair.getRight();
		}
	}
}
