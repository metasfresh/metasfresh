/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipping;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.i18n.ExplainedOptional;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;

/**
 * Repository Tables: M_Shipper
 * Repository Cluster: ShipperRepository, ShipperConfigRepository
 */
@Repository
public class ShipperRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final CCache<Integer, ShippersMap> cache = CCache.<Integer, ShippersMap>builder()
			.tableName(I_M_Shipper.Table_Name)
			.build();

	@NonNull
	public Shipper getById(@NonNull final ShipperId shipperId)
	{
		return getMap().getById(shipperId);
	}

	@NonNull
	public Map<ShipperId, Shipper> getByIds(@NonNull final Set<ShipperId> shipperIds)
	{
		return getMap().getByIds(shipperIds);
	}

	public boolean isApiCarrierAdvise(@NonNull final ShipperId shipperId)
	{
		return getMap().getById(shipperId).isApiCarrierAdvise();
	}

	/**
	 * {@code true} iff at least one active shipper has {@code IsApiCarrierAdvise}. Cache-backed (no DB hit on the hot
	 * path) — an early gate so carrier-advise work (e.g. the per-HU schedule resolution in the consistency guard) is
	 * skipped entirely on instances that do not use API carrier advise at all.
	 */
	public boolean isAnyApiCarrierAdvise()
	{
		return getMap().anyApiCarrierAdvise();
	}

	@NonNull
	public ExplainedOptional<ShipperGatewayId> getShipperGatewayId(@NonNull final ShipperId shipperId)
	{
		final Shipper shipper = getMap().getById(shipperId);
		final ShipperGatewayId shipperGatewayId = shipper.getShipperGatewayId();
		return shipperGatewayId != null
				? ExplainedOptional.of(shipperGatewayId)
				: ExplainedOptional.emptyBecause("Shipper " + shipper.getName() + " has no gateway configured");
	}

	@NonNull
	private ShippersMap getMap()
	{
		return cache.getOrLoadNonNull(0, this::retrieveMap);
	}

	@NonNull
	private ShippersMap retrieveMap()
	{
		final ImmutableList<Shipper> shippers = queryBL.createQueryBuilder(I_M_Shipper.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(ShipperRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
		return new ShippersMap(shippers);
	}

	@NonNull
	private static Shipper fromRecord(@NonNull final I_M_Shipper record)
	{
		return Shipper.builder()
				.id(ShipperId.ofRepoId(record.getM_Shipper_ID()))
				.name(record.getName())
				.apiCarrierAdvise(record.isApiCarrierAdvise())
				.createDeliveryPlanning(record.isCreateDeliveryPlanning())
				.trackingUrl(record.getTrackingURL())
				.pickupTimeFrom(TimeUtil.asLocalTime(record.getPickupTimeFrom()))
				.pickupTimeTo(TimeUtil.asLocalTime(record.getPickupTimeTo()))
				.shipperGatewayId(ShipperGatewayId.ofNullableString(record.getShipperGateway()))
				.build();
	}

	private static class ShippersMap
	{
		private final ImmutableMap<ShipperId, Shipper> byId;

		ShippersMap(final ImmutableList<Shipper> shippers)
		{
			this.byId = Maps.uniqueIndex(shippers, Shipper::getId);
		}

		@NonNull
		public Shipper getById(@NonNull final ShipperId shipperId)
		{
			final Shipper shipper = byId.get(shipperId);
			if (shipper == null)
			{
				throw new AdempiereException("No shipper found for " + shipperId);
			}
			return shipper;
		}

		@NonNull
		public Map<ShipperId, Shipper> getByIds(@NonNull final Set<ShipperId> shipperIds)
		{
			if (shipperIds.isEmpty())
			{
				return ImmutableMap.of();
			}
			return shipperIds.stream()
					.filter(byId::containsKey)
					.collect(ImmutableMap.toImmutableMap(id -> id, byId::get));
		}

		public boolean anyApiCarrierAdvise()
		{
			return byId.values().stream().anyMatch(Shipper::isApiCarrierAdvise);
		}
	}
}
