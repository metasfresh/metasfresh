package de.metas.material.dispo.commons.repository.atp;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.adempiere.service.ISysConfigBL;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.material.dispo.model.I_MD_Candidate_ATP_QueryResult;
import de.metas.material.event.commons.AttributesKey;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Service
public class AvailableToPromiseRepository
{
	private static final String SYSCONFIG_ATP_ATTRIBUTES_KEYS = "de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.ATP.AttributesKeys";

	@NonNull
	public BigDecimal retrieveAvailableStockQtySum(@NonNull final AvailableToPromiseMultiQuery multiQuery)
	{
		return retrieveAvailableStock(multiQuery)
				.getResultGroups()
				.stream().map(AvailableToPromiseResultGroup::getQty).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@NonNull
	public BigDecimal retrieveAvailableStockQtySum(@NonNull final AvailableToPromiseQuery query)
	{
		return retrieveAvailableStockQtySum(AvailableToPromiseMultiQuery.of(query));
	}

	@NonNull
	public AvailableToPromiseResult retrieveAvailableStock(@NonNull final AvailableToPromiseMultiQuery multiQuery)
	{
		final AvailableToPromiseResult result = multiQuery.isAddToPredefinedBuckets()
				? AvailableToPromiseResult.createEmptyWithPredefinedBuckets(multiQuery)
				: AvailableToPromiseResult.createEmpty();

		final IQuery<I_MD_Candidate_ATP_QueryResult> dbQuery = createDBQueryForMaterialQueryOrNull(multiQuery);
		if (dbQuery == null)
		{
			return result;
		}

		final Function<I_MD_Candidate_ATP_QueryResult, Boolean> recordHasBPartnerId = record -> record.getC_BPartner_Customer_ID() > 0;

		final List<I_MD_Candidate_ATP_QueryResult> atpRecords = dbQuery
				.stream()
				.sorted(Comparator
						.comparing(recordHasBPartnerId) // note that true > false
						.thenComparing(I_MD_Candidate_ATP_QueryResult::getDateProjected)
						.thenComparing(I_MD_Candidate_ATP_QueryResult::getSeqNo) // if dateProjected is equal, then SeqNo makes the difference
						.reversed())
				.collect(ImmutableList.toImmutableList());

		// note: this is a dedicated step in order to ease debugging (i.e. have a chance to take a look at the atpRecords)
		final ImmutableList<AddToResultGroupRequest> requests = atpRecords
				.stream()
				.map(AvailableToPromiseRepository::createAddToResultGroupRequest)
				.collect(ImmutableList.toImmutableList());

		for (final AddToResultGroupRequest request : requests)
		{
			if (multiQuery.isAddToPredefinedBuckets())
			{
				result.addQtyToAllMatchingGroups(request);
			}
			else
			{
				result.addToNewGroupIfFeasible(request);
			}
		}
		return result;
	}

	public AvailableToPromiseResult retrieveAvailableStock(@NonNull AvailableToPromiseQuery query)
	{
		return retrieveAvailableStock(AvailableToPromiseMultiQuery.of(query));
	}

	private IQuery<I_MD_Candidate_ATP_QueryResult> createDBQueryForMaterialQueryOrNull(
			@NonNull final AvailableToPromiseMultiQuery multiQuery)
	{
		final Function<AvailableToPromiseQuery, IQuery<I_MD_Candidate_ATP_QueryResult>> createDbQueryForSingleStockQuery = //
				stockQuery -> AvailableToPromiseSqlHelper
						.createDBQueryForStockQuery(stockQuery);

		return multiQuery.getQueries()
				.stream()
				.filter(Predicates.notNull())
				.map(createDbQueryForSingleStockQuery)
				.reduce(IQuery.unionDistict())
				.orElse(null);
	}

	@VisibleForTesting
	static AddToResultGroupRequest createAddToResultGroupRequest(final I_MD_Candidate_ATP_QueryResult stockRecord)
	{
		final int bPpartnerIdForRequest = stockRecord.getC_BPartner_Customer_ID() > 0
				? stockRecord.getC_BPartner_Customer_ID()
				: AvailableToPromiseQuery.BPARTNER_ID_ANY // records that have no bPartner-ID are applicable to any bpartner
		;

		return AddToResultGroupRequest.builder()
				.productId(stockRecord.getM_Product_ID())
				.bpartnerId(bPpartnerIdForRequest)
				.warehouseId(stockRecord.getM_Warehouse_ID())
				.storageAttributesKey(AttributesKey.ofString(stockRecord.getStorageAttributesKey()))
				.qty(stockRecord.getQty())
				.date(TimeUtil.asLocalDateTime(stockRecord.getDateProjected()))
				.seqNo(stockRecord.getSeqNo())
				.build();
	}

	public Set<AttributesKey> getPredefinedStorageAttributeKeys()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final int clientId = Env.getAD_Client_ID(Env.getCtx());
		final int orgId = Env.getAD_Org_ID(Env.getCtx());

		final String storageAttributesKeys = sysConfigBL.getValue(
				SYSCONFIG_ATP_ATTRIBUTES_KEYS,
				AttributesKey.ALL.getAsString(),
				clientId, orgId);

		return Splitter.on(",")
				.trimResults()
				.omitEmptyStrings()
				.splitToList(storageAttributesKeys)
				.stream()
				.map(attributesKeyStr -> toAttributesKey(attributesKeyStr))
				.collect(ImmutableSet.toImmutableSet());
	}

	private static AttributesKey toAttributesKey(final String storageAttributesKey)
	{
		if ("<ALL_STORAGE_ATTRIBUTES_KEYS>".equals(storageAttributesKey))
		{
			return AttributesKey.ALL;
		}
		else if ("<OTHER_STORAGE_ATTRIBUTES_KEYS>".equals(storageAttributesKey))
		{
			return AttributesKey.OTHER;
		}
		else
		{
			return AttributesKey.ofString(storageAttributesKey);
		}
	}

	@Value
	private static class ProductAndAttributeKey
	{
		int productId;
		String attributeKey;
	}
}
