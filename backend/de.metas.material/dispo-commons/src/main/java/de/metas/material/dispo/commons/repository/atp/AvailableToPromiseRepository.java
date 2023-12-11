package de.metas.material.dispo.commons.repository.atp;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import org.adempiere.mm.attributes.keys.AttributesKeyPattern;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import de.metas.material.commons.attributes.clasifiers.BPartnerClassifier;
import de.metas.material.dispo.model.I_MD_Candidate_ATP_QueryResult;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

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
	private static final String SYSCONFIG_AVAILABILITY_INFO_ATTRIBUTES_KEYS = "de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.AvailabilityInfo.AttributesKeys";

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
		final boolean addToPredefinedBuckets = multiQuery.isAddToPredefinedBuckets();
		final AvailableToPromiseResultBuilder result = addToPredefinedBuckets
				? AvailableToPromiseResultBuilder.createEmptyWithPredefinedBuckets(multiQuery)
				: AvailableToPromiseResultBuilder.createEmpty();

		final IQuery<I_MD_Candidate_ATP_QueryResult> dbQuery = createDBQueryForMaterialQueryOrNull(multiQuery);
		if (dbQuery == null)
		{
			return result.build();
		}

		final Function<I_MD_Candidate_ATP_QueryResult, Boolean> compareByWhetherRecordHasBPartnerId = record -> record.getC_BPartner_Customer_ID() > 0;

		final List<I_MD_Candidate_ATP_QueryResult> atpRecords = dbQuery.list()
				.stream()
				// records with dedicated bPartnerId first
				// latest date first
				// biggest seqNo first
				.sorted(Comparator
						.comparing(compareByWhetherRecordHasBPartnerId)
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
			if (addToPredefinedBuckets)
			{
				result.addQtyToAllMatchingGroups(request);
			}
			else
			{
				result.addToNewGroupIfFeasible(request);
			}
		}

		return result.build();
	}

	public AvailableToPromiseResult retrieveAvailableStock(@NonNull final AvailableToPromiseQuery query)
	{
		return retrieveAvailableStock(AvailableToPromiseMultiQuery.of(query));
	}

	@Nullable
	private IQuery<I_MD_Candidate_ATP_QueryResult> createDBQueryForMaterialQueryOrNull(
			@NonNull final AvailableToPromiseMultiQuery multiQuery)
	{
		return multiQuery.getQueries()
				.stream()
				.filter(Objects::nonNull)
				.map(AvailableToPromiseSqlHelper::createDBQueryForStockQuery)
				.reduce(IQuery.unionDistict())
				.orElse(null);
	}

	@VisibleForTesting
	static AddToResultGroupRequest createAddToResultGroupRequest(final I_MD_Candidate_ATP_QueryResult stockRecord)
	{
		final BPartnerId customerId = BPartnerId.ofRepoIdOrNull(stockRecord.getC_BPartner_Customer_ID());

		return AddToResultGroupRequest.builder()
				.productId(ProductId.ofRepoId(stockRecord.getM_Product_ID()))
				.bpartner(BPartnerClassifier.specificOrAny(customerId)) // records that have no bPartner-ID are applicable to any bpartner
				.warehouseId(WarehouseId.ofRepoId(stockRecord.getM_Warehouse_ID()))
				.storageAttributesKey(AttributesKey.ofString(stockRecord.getStorageAttributesKey()))
				.qty(stockRecord.getQty())
				.date(TimeUtil.asInstant(stockRecord.getDateProjected()))
				.seqNo(stockRecord.getSeqNo())
				.build();
	}

	public Set<AttributesKeyPattern> getPredefinedStorageAttributeKeys()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final int clientId = Env.getAD_Client_ID(Env.getCtx());
		final int orgId = Env.getAD_Org_ID(Env.getCtx());

		final String storageAttributesKeys = sysConfigBL.getValue(
				SYSCONFIG_AVAILABILITY_INFO_ATTRIBUTES_KEYS,
				AttributesKey.ALL.getAsString(),
				clientId, orgId);

		return AttributesKeyPatternsUtil.parseCommaSeparatedString(storageAttributesKeys);
	}
}
