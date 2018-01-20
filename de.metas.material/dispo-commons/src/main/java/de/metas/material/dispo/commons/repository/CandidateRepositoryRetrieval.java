package de.metas.material.dispo.commons.repository;

import static org.adempiere.model.InterfaceWrapperHelper.isNew;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.commons.candidate.DistributionDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Transaction_Detail;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import lombok.NonNull;

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
public class CandidateRepositoryRetrieval
{
	/**
	 * Load and return <b>the</b> single record this has the given {@code id} as parentId.
	 *
	 * @param parentId
	 * @return
	 */
	public Optional<Candidate> retrieveSingleChild(@NonNull final Integer parentId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final I_MD_Candidate candidateRecord = queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_Parent_ID, parentId)
				.create()
				.firstOnly(I_MD_Candidate.class);

		if (candidateRecord == null)
		{
			return Optional.empty();
		}

		return fromCandidateRecord(candidateRecord);
	}

	/**
	 *
	 * @param groupId
	 * @return
	 */
	public List<Candidate> retrieveGroup(final Integer groupId)
	{
		if (groupId == null)
		{
			return ImmutableList.of();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_GroupId, groupId)
				.orderBy().addColumn(I_MD_Candidate.COLUMN_MD_Candidate_ID).endOrderBy()
				.create()
				.stream().map(r -> fromCandidateRecord(r).get())
				.collect(Collectors.toList());
	}

	@VisibleForTesting
	Optional<Candidate> fromCandidateRecord(final I_MD_Candidate candidateRecordOrNull)
	{
		if (candidateRecordOrNull == null || isNew(candidateRecordOrNull) || candidateRecordOrNull.getMD_Candidate_ID() <= 0)
		{
			return Optional.empty();
		}

		final CandidateBuilder builder = createAndInitializeBuilder(candidateRecordOrNull);

		final CandidateBusinessCase businessCase = getBusinesCaseOrNull(candidateRecordOrNull);
		builder.businessCase(businessCase);

		builder.productionDetail(createProductionDetailOrNull(candidateRecordOrNull));
		builder.distributionDetail(createDistributionDetailOrNull(candidateRecordOrNull));
		builder.demandDetail(createDemandDetailOrNull(candidateRecordOrNull));

		builder.transactionDetails(createTransactionDetails(candidateRecordOrNull));

		return Optional.of(builder.build());
	}

	private CandidateBusinessCase getBusinesCaseOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		CandidateBusinessCase subType = null;
		if (!Check.isEmpty(candidateRecord.getMD_Candidate_BusinessCase()))
		{
			subType = CandidateBusinessCase.valueOf(candidateRecord.getMD_Candidate_BusinessCase());
		}
		return subType;
	}

	private CandidateBuilder createAndInitializeBuilder(@NonNull final I_MD_Candidate candidateRecord)
	{
		final Timestamp dateProjected = Preconditions.checkNotNull(candidateRecord.getDateProjected(),
				"Given parameter candidateRecord needs to have a not-null dateProjected; candidateRecord=%s",
				candidateRecord);
		final String md_candidate_type = Preconditions.checkNotNull(candidateRecord.getMD_Candidate_Type(),
				"Given parameter candidateRecord needs to have a not-null MD_Candidate_Type; candidateRecord=%s",
				candidateRecord);

		final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(
				candidateRecord.getM_Product_ID(),
				getEfferciveStorageAttributesKey(candidateRecord),
				candidateRecord.getM_AttributeSetInstance_ID());

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(productDescriptor)
				.quantity(candidateRecord.getQty())
				.warehouseId(candidateRecord.getM_Warehouse_ID())
				.bPartnerId(candidateRecord.getC_BPartner_ID())
				// make sure to add a Date and not a Timestamp to avoid confusing Candidate's equals() and hashCode() methods
				.date(new Date(dateProjected.getTime()))
				.build();

		final CandidateBuilder candidateBuilder = Candidate.builder()
				.id(candidateRecord.getMD_Candidate_ID())
				.clientId(candidateRecord.getAD_Client_ID())
				.orgId(candidateRecord.getAD_Org_ID())
				.seqNo(candidateRecord.getSeqNo())
				.type(CandidateType.valueOf(md_candidate_type))

				// if the record has a group id, then set it.
				.groupId(candidateRecord.getMD_Candidate_GroupId())
				.materialDescriptor(materialDescriptor);

		if (candidateRecord.getMD_Candidate_Parent_ID() > 0)
		{
			candidateBuilder.parentId(candidateRecord.getMD_Candidate_Parent_ID());
		}
		return candidateBuilder;
	}

	private AttributesKey getEfferciveStorageAttributesKey(@NonNull final I_MD_Candidate candidateRecord)
	{
		final AttributesKey attributesKey;
		if (Check.isEmpty(candidateRecord.getStorageAttributesKey(), true))
		{
			attributesKey = AttributesKey.ALL;
		}
		else
		{
			attributesKey = AttributesKey.ofString(candidateRecord.getStorageAttributesKey());
		}
		return attributesKey;
	}

	private ProductionDetail createProductionDetailOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		final I_MD_Candidate_Prod_Detail productionDetail = //
				RepositoryCommons.retrieveSingleCandidateDetail(candidateRecord, I_MD_Candidate_Prod_Detail.class);
		if (productionDetail == null)
		{
			return null;
		}

		return ProductionDetail.forProductionDetailRecord(productionDetail);
	}

	private DistributionDetail createDistributionDetailOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		final I_MD_Candidate_Dist_Detail distributionDetail = //
				RepositoryCommons.retrieveSingleCandidateDetail(candidateRecord, I_MD_Candidate_Dist_Detail.class);
		if (distributionDetail == null)
		{
			return null;
		}

		return DistributionDetail.forDistributionDetailRecord(distributionDetail);
	}

	private static DemandDetail createDemandDetailOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		final I_MD_Candidate_Demand_Detail demandDetailRecord = //
				RepositoryCommons.retrieveSingleCandidateDetail(candidateRecord, I_MD_Candidate_Demand_Detail.class);
		if (demandDetailRecord == null)
		{
			return null;
		}

		return DemandDetail.forDemandDetailRecord(demandDetailRecord);
	}

	private static List<TransactionDetail> createTransactionDetails(@NonNull final I_MD_Candidate candidateRecord)
	{
		final List<I_MD_Candidate_Transaction_Detail> transactionDetailRecords = //
				RepositoryCommons.createCandidateDetailQueryBuilder(candidateRecord, I_MD_Candidate_Transaction_Detail.class)
						.list();

		final ImmutableList.Builder<TransactionDetail> result = ImmutableList.builder();
		for (final I_MD_Candidate_Transaction_Detail transactionDetailRecord : transactionDetailRecords)
		{
			result.add(TransactionDetail.forTransactionDetailRecord(transactionDetailRecord));
		}
		return result.build();
	}

	public Candidate retrieveLatestMatchOrNull(@NonNull final CandidatesQuery query)
	{
		final IQueryBuilder<I_MD_Candidate> queryBuilderWithoutOrdering = RepositoryCommons.mkQueryBuilder(query);

		final I_MD_Candidate candidateRecordOrNull = addOrderingLatestFirst(queryBuilderWithoutOrdering)
				.create()
				.first();

		return fromCandidateRecord(candidateRecordOrNull).orElse(null);
	}

	private IQueryBuilder<I_MD_Candidate> addOrderingLatestFirst(
			@NonNull final IQueryBuilder<I_MD_Candidate> queryBuilderWithoutOrdering)
	{
		return queryBuilderWithoutOrdering
				.orderBy()
				.addColumnDescending(I_MD_Candidate.COLUMNNAME_DateProjected)
				.addColumnAscending(I_MD_Candidate.COLUMNNAME_SeqNo)
				.addColumnAscending(I_MD_Candidate.COLUMNNAME_MD_Candidate_ID)
				.endOrderBy();
	}

	public List<Candidate> retrieveOrderedByDateAndSeqNo(@NonNull final CandidatesQuery query)
	{
		final IQueryBuilder<I_MD_Candidate> queryBuilderWithoutOrdering = RepositoryCommons.mkQueryBuilder(query);

		final Stream<I_MD_Candidate> candidateRecords = addOrderingYoungestFirst(queryBuilderWithoutOrdering)
				.create()
				.stream();

		return candidateRecords
				.map(record -> fromCandidateRecord(record).get())
				.collect(Collectors.toList());
	}

	private IQueryBuilder<I_MD_Candidate> addOrderingYoungestFirst(final IQueryBuilder<I_MD_Candidate> queryBuilderWithoutOrdering)
	{
		return queryBuilderWithoutOrdering
				.orderBy()
				.addColumnAscending(I_MD_Candidate.COLUMNNAME_DateProjected)
				.addColumnAscending(I_MD_Candidate.COLUMNNAME_SeqNo)
				.addColumnAscending(I_MD_Candidate.COLUMNNAME_MD_Candidate_ID)
				.endOrderBy();
	}

}
