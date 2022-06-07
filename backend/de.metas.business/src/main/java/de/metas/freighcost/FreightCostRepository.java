package de.metas.freighcost;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.location.CountryAreaId;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.I_M_FreightCost;
import org.adempiere.model.I_M_FreightCostDetail;
import org.adempiere.model.I_M_FreightCostShipper;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class FreightCostRepository
{
	private static final Logger logger = LogManager.getLogger(FreightCostRepository.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final AdMessageKey MSG_NO_FREIGHT_COST_DETAIL = AdMessageKey.of("freightCost.Order.noFreightCostDetail");

	private final CCache<Integer, IndexedFreightCosts> freightCostsCache = CCache.<Integer, IndexedFreightCosts> builder()
			.additionalTableNameToResetFor(I_M_FreightCost.Table_Name)
			.additionalTableNameToResetFor(I_M_FreightCostShipper.Table_Name)
			.additionalTableNameToResetFor(I_M_FreightCostDetail.Table_Name)
			.build();

	public FreightCost getById(@NonNull final FreightCostId id)
	{
		return getIndexedFreightCosts().getById(id);
	}

	public boolean existsByFreightCostProductId(final ProductId freightCostProductId)
	{
		return getIndexedFreightCosts().isFreightCostProduct(freightCostProductId);
	}

	public Optional<FreightCost> getDefaultFreightCost()
	{
		return getIndexedFreightCosts().getDefaultFreightCost();
	}

	private IndexedFreightCosts getIndexedFreightCosts()
	{
		return freightCostsCache.getOrLoad(0, () -> retrieveIndexedFreightCosts());
	}

	private IndexedFreightCosts retrieveIndexedFreightCosts()
	{
		final List<I_M_FreightCost> records = queryBL.createQueryBuilderOutOfTrx(I_M_FreightCost.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableList.toImmutableList());

		final Set<FreightCostId> freightCostIds = records.stream()
				.map(record -> FreightCostId.ofRepoId(record.getM_FreightCost_ID()))
				.collect(ImmutableSet.toImmutableSet());

		final ListMultimap<FreightCostId, FreightCostShipper> shippers = retrieveShippers(freightCostIds);

		final ImmutableList.Builder<FreightCost> freightCosts = ImmutableList.builder();
		for (final I_M_FreightCost record : records)
		{
			final FreightCostId freightCostId = FreightCostId.ofRepoId(record.getM_FreightCost_ID());
			final FreightCost freightCost = toFreightCost(record, shippers.get(freightCostId));
			freightCosts.add(freightCost);
		}

		return new IndexedFreightCosts(freightCosts.build());
	}

	private static FreightCost toFreightCost(final I_M_FreightCost record, final Collection<FreightCostShipper> shippers)
	{
		return FreightCost.builder()
				.id(FreightCostId.ofRepoId(record.getM_FreightCost_ID()))
				.name(record.getName())
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.freightCostProductId(ProductId.ofRepoId(record.getM_Product_ID()))
				.defaultFreightCost(record.isDefault())
				.shippers(shippers)
				.build();
	}

	private ListMultimap<FreightCostId, FreightCostShipper> retrieveShippers(@NonNull final Collection<FreightCostId> freightCostIds)
	{
		if (freightCostIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		final List<I_M_FreightCostShipper> shipperRecords = queryBL.createQueryBuilderOutOfTrx(I_M_FreightCostShipper.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_FreightCostShipper.COLUMNNAME_M_FreightCost_ID, freightCostIds)
				.create()
				.list();

		final ImmutableSet<FreightCostShipperId> fcShipperIds = shipperRecords.stream()
				.map(record -> FreightCostShipperId.ofRepoId(record.getM_FreightCostShipper_ID()))
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableListMultimap<FreightCostShipperId, FreightCostBreak> breaks = retrieveBreaks(fcShipperIds);

		if (Check.isEmpty(breaks))
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);

			final ITranslatableString translatableMsgText = msgBL.getTranslatableMsgText(MSG_NO_FREIGHT_COST_DETAIL);

			throw new AdempiereException(translatableMsgText);
		}

		final ListMultimap<FreightCostId, FreightCostShipper> result = ArrayListMultimap.create();
		for (final I_M_FreightCostShipper shipperRecord : shipperRecords)
		{
			final FreightCostId freightCostId = FreightCostId.ofRepoId(shipperRecord.getM_FreightCost_ID());
			final FreightCostShipperId fcShipperId = FreightCostShipperId.ofRepoId(shipperRecord.getM_FreightCostShipper_ID());

			final FreightCostShipper shipper = toFreightCostShipper(shipperRecord, breaks.get(fcShipperId));
			result.put(freightCostId, shipper);
		}

		return result;
	}

	private static FreightCostShipper toFreightCostShipper(
			@NonNull final I_M_FreightCostShipper record,
			@NonNull final Collection<FreightCostBreak> breaks)
	{
		return FreightCostShipper.builder()
				.id(FreightCostShipperId.ofRepoId(record.getM_FreightCostShipper_ID()))
				.shipperId(ShipperId.ofRepoId(record.getM_Shipper_ID()))
				.validFrom(TimeUtil.asLocalDate(record.getValidFrom()))
				.breaks(breaks)
				.build();
	}

	private ImmutableListMultimap<FreightCostShipperId, FreightCostBreak> retrieveBreaks(
			@NonNull final Collection<FreightCostShipperId> freightCostShipperIds)
	{
		if (freightCostShipperIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		return queryBL
				.createQueryBuilderOutOfTrx(I_M_FreightCostDetail.class)
				.addInArrayFilter(I_M_FreightCostDetail.COLUMNNAME_M_FreightCostShipper_ID, freightCostShipperIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> FreightCostShipperId.ofRepoId(record.getM_FreightCostShipper_ID()), // keyFunction
						record -> toFreightCostBreak(record)) // valueFunction
		);
	}

	private FreightCostBreak toFreightCostBreak(final I_M_FreightCostDetail record)
	{
		final CountryId countryId = CountryId.ofRepoIdOrNull(record.getC_Country_ID());
		final CountryAreaId countryAreaId = CountryAreaId.ofRepoIdOrNull(record.getC_CountryArea_ID());
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());

		return FreightCostBreak.builder()
				.freightCostShipperId(FreightCostShipperId.ofRepoId(record.getM_FreightCostShipper_ID()))
				.countryId(countryId)
				.countryAreaId(countryAreaId)
				.shipmentValueAmtMax(Money.of(record.getShipmentValueAmt(), currencyId))
				.freightRate(Money.of(record.getFreightAmt(), currencyId))
				.seqNo(record.getSeqNo())
				.build();
	}

	@ToString
	private static class IndexedFreightCosts
	{
		private final ImmutableMap<FreightCostId, FreightCost> freightCosts;
		@Getter
		private final Optional<FreightCostId> defaultFreightCostId;
		private final ImmutableSet<ProductId> freightCostProductIds;

		private IndexedFreightCosts(final Collection<FreightCost> freightCosts)
		{
			this.freightCosts = Maps.uniqueIndex(freightCosts, FreightCost::getId);

			defaultFreightCostId = extractDefaultFreightCost(freightCosts);
			freightCostProductIds = extractFreightCostProductIds(freightCosts);
		}

		private static Optional<FreightCostId> extractDefaultFreightCost(final Collection<FreightCost> freightCosts)
		{
			if (freightCosts.isEmpty())
			{
				return Optional.empty();
			}

			final ImmutableList<FreightCost> defaults = freightCosts.stream()
					.filter(FreightCost::isDefaultFreightCost)
					.sorted(Comparator.comparing(FreightCost::getId)) // just to have a predictible order
					.collect(ImmutableList.toImmutableList());
			if (defaults.isEmpty())
			{
				return Optional.empty();
			}
			else if (defaults.size() == 1)
			{
				return Optional.of(defaults.get(0).getId());
			}
			else
			{
				logger.warn("More than one default freight costs found. Considering the first one: {}", defaults);
				return Optional.of(defaults.get(0).getId());
			}
		}

		private static ImmutableSet<ProductId> extractFreightCostProductIds(final Collection<FreightCost> freightCosts)
		{
			return freightCosts.stream()
					.map(FreightCost::getFreightCostProductId)
					.collect(ImmutableSet.toImmutableSet());
		}

		public boolean isFreightCostProduct(@NonNull final ProductId productId)
		{
			return freightCostProductIds.contains(productId);
		}

		public FreightCost getById(@NonNull final FreightCostId id)
		{
			final FreightCost freightCost = freightCosts.get(id);
			if (freightCost == null)
			{
				throw new AdempiereException("@NotFound@ @M_FreightCost_ID@: " + id);
			}
			return freightCost;
		}

		public Optional<FreightCost> getDefaultFreightCost()
		{
			return getDefaultFreightCostId().map(this::getById);
		}

	}
}
