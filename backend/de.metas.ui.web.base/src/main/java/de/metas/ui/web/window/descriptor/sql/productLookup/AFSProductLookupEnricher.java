/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.window.descriptor.sql.productLookup;

import com.google.common.collect.ImmutableList;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfig;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfigRepo;
import de.metas.material.cockpit.availableforsales.AvailableForSalesMultiQuery;
import de.metas.material.cockpit.availableforsales.AvailableForSalesQuery;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.ui.web.material.adapter.AvailabilityInfoResultForWebui;
import de.metas.ui.web.material.adapter.AvailableForSaleAdapter;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.mm.attributes.keys.AttributesKeyPattern;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Note: AFS stands for "available for sales" and basically means "on-hand qty minus pending shipments"
 */
public class AFSProductLookupEnricher
{
	@NonNull
	final ZonedDateTime dateOrNull;
	@Nullable
	final WarehouseId warehouseId;
	@NonNull
	final ClientId clientId;
	@NonNull
	final OrgId orgId;
	private final AvailableForSaleAdapter availableForSaleAdapter;
	private final AvailableForSalesConfigRepo availableForSalesConfigRepo;

	@Builder
	public AFSProductLookupEnricher(
			@NonNull final ZonedDateTime dateOrNull,
			@Nullable final WarehouseId warehouseId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final AvailableForSaleAdapter availableForSaleAdapter,
			@NonNull final AvailableForSalesConfigRepo availableForSalesConfigRepo)

	{
		this.dateOrNull = dateOrNull;
		this.warehouseId = warehouseId;
		this.clientId = clientId;
		this.orgId = orgId;
		this.availableForSaleAdapter = availableForSaleAdapter;
		this.availableForSalesConfigRepo = availableForSalesConfigRepo;
	}

	public List<AvailabilityInfoResultForWebui.Group> getAvailabilityInfoGroups(@NonNull final LookupValuesList productLookupValues)
	{
		final AvailableForSalesConfig config = availableForSalesConfigRepo.getConfig(
				AvailableForSalesConfigRepo.ConfigQuery.builder()
						.clientId(clientId)
						.orgId(orgId)
						.build());
		final List<AvailableForSalesQuery> collect = productLookupValues.getKeysAsInt().stream()
				.map(ProductId::ofRepoId)
				.map(id -> createAvailableForSalesMultiQuery(id, dateOrNull.toInstant(), config.getSalesOrderLookBehindHours(), config.getShipmentDateLookAheadHours(), warehouseId))
				.map(AvailableForSalesMultiQuery::getAvailableForSalesQueries)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
		final AvailableForSalesMultiQuery afsMultiQuery = AvailableForSalesMultiQuery.builder()
				.availableForSalesQueries(collect)
				.build();

		final AvailabilityInfoResultForWebui availableStock = availableForSaleAdapter.retrieveAvailableStock(afsMultiQuery);
		return availableStock.getGroups();

	}

	private AvailableForSalesMultiQuery createAvailableForSalesMultiQuery(
			@NonNull final ProductId productId,
			@NonNull final Instant dateOfInterest,
			final int salesOrderLookBehindHours,
			final int shipmentDateLookAheadHours,
			@Nullable final WarehouseId warehouseId)
	{
		final AvailableForSalesMultiQuery.AvailableForSalesMultiQueryBuilder result = AvailableForSalesMultiQuery.builder();

		final AvailableForSalesQuery.AvailableForSalesQueryBuilder queryBuilder = AvailableForSalesQuery.builder()
				.productId(productId)
				.dateOfInterest(dateOfInterest)
				.salesOrderLookBehindHours(salesOrderLookBehindHours)
				.warehouseId(warehouseId)
				.shipmentDateLookAheadHours(shipmentDateLookAheadHours);

		boolean containsAll = false;
		for (final AttributesKeyPattern attributesKey : availableForSaleAdapter.getPredefinedStorageAttributeKeys())
		{
			final AvailableForSalesQuery query = queryBuilder.storageAttributesKeyPattern(attributesKey).build();
			result.availableForSalesQuery(query);
			if (attributesKey.isAll())
			{
				containsAll = true;
			}
		}

		if (!containsAll)
		{
			// we don't just want the different attributes' quantities, but also the "sum"
			final AvailableForSalesQuery query = queryBuilder.storageAttributesKeyPattern(AttributesKeyPatternsUtil.ofAttributeKey(AttributesKey.ALL)).build();
			result.availableForSalesQuery(query);
		}
		return result.build();
	}

	private ImmutableList<AttributesKey> retrieveAttributesKeys(@Nullable final WarehouseId warehouseId, @NonNull final ProductId productId)
	{
		final IQueryBuilder<I_MD_Stock> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Stock.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, productId.getRepoId())
				.addCompareFilter(I_MD_Stock.COLUMN_QtyOnHand, CompareQueryFilter.Operator.GREATER, BigDecimal.ZERO);
		if (warehouseId != null)
		{
			queryBuilder.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Warehouse_ID, warehouseId.getRepoId());
		}
		return queryBuilder
				.create()
				.stream().map(s -> AttributesKey.ofString(s.getAttributesKey()))
				.collect(ImmutableList.toImmutableList());
	}
}
