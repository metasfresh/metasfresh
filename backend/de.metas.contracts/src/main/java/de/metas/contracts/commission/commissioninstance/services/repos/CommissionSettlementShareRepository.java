package de.metas.contracts.commission.commissioninstance.services.repos;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShareId;
import de.metas.contracts.commission.commissioninstance.businesslogic.settlement.CommissionSettlementFact;
import de.metas.contracts.commission.commissioninstance.businesslogic.settlement.CommissionSettlementShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.settlement.CommissionSettlementState;
import de.metas.contracts.commission.model.I_C_Commission_Fact;
import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util.ArrayKey;
import org.springframework.stereotype.Repository;

import java.util.Set;

import static de.metas.util.Check.assumeEquals;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.contracts
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
public class CommissionSettlementShareRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public CommissionSettlementShare getByInvoiceCandidateId(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final I_C_Invoice_Candidate icRecord = load(invoiceCandidateId, I_C_Invoice_Candidate.class);
		final TableRecordReference reference = TableRecordReference.ofReferenced(icRecord);
		assumeEquals(reference.getTableName(), I_C_Commission_Share.Table_Name, "C_Invoice_Candidate record with id={}, needs to reference {}, but references {} instead.", invoiceCandidateId, I_C_Commission_Share.Table_Name, reference.getTableName());

		final I_C_Commission_Share shareRecord = reference.getModel(I_C_Commission_Share.class);

		final ImmutableList<I_C_Commission_Fact> factRecords = retrieveFactRecords(shareRecord);

		final ImmutableList<CommissionSettlementFact> facts = createFacts(factRecords);

		return CommissionSettlementShare.builder()
				.salesCommissionShareId(CommissionShareId.ofRepoId(shareRecord.getC_Commission_Share_ID()))
				.facts(facts)
				.build();
	}

	private ImmutableList<CommissionSettlementFact> createFacts(@NonNull final ImmutableList<I_C_Commission_Fact> factRecords)
	{
		final ImmutableList.Builder<CommissionSettlementFact> facts = ImmutableList.builder();

		for (final I_C_Commission_Fact factRecord : factRecords)
		{
			final CommissionSettlementFact fact = CommissionSettlementFact.builder()
					.points(CommissionPoints.of(factRecord.getCommissionPoints()))
					.state(CommissionSettlementState.valueOf(factRecord.getCommission_Fact_State()))
					.timestamp(TimeUtil.deserializeInstant(factRecord.getCommissionFactTimestamp()))
					.settlementInvoiceCandidateId(InvoiceCandidateId.ofRepoId(factRecord.getC_Invoice_Candidate_Commission_ID()))
					.build();
			facts.add(fact);
		}
		return facts.build();
	}

	public void save(@NonNull final CommissionSettlementShare settlementShare)
	{
		final I_C_Commission_Share commissionShareRecord = load(settlementShare.getSalesCommissionShareId(), I_C_Commission_Share.class);
		commissionShareRecord.setPointsSum_ToSettle(settlementShare.getPointsToSettleSum().toBigDecimal());
		commissionShareRecord.setPointsSum_Settled(settlementShare.getSettledPointsSum().toBigDecimal());
		saveRecord(commissionShareRecord);

		final OrgId orgId = OrgId.ofRepoId(commissionShareRecord.getAD_Org_ID());

		final ImmutableList<I_C_Commission_Fact> factRecords = retrieveFactRecords(commissionShareRecord);
		createNewFactRecords(
				settlementShare.getFacts(),
				settlementShare.getSalesCommissionShareId().getRepoId(),
				orgId,
				factRecords);
	}

	@NonNull
	public Set<CommissionShareId> retrieveShareIdsForInstanceQuery(@NonNull final IQuery<I_C_Commission_Instance> commissionInstanceIQuery)
	{
		return queryBL.createQueryBuilder(I_C_Commission_Share.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_C_Commission_Share.COLUMNNAME_C_Commission_Instance_ID, I_C_Commission_Instance.COLUMNNAME_C_Commission_Instance_ID, commissionInstanceIQuery)
				.create()
				.stream()
				.map(I_C_Commission_Share::getC_Commission_Share_ID)
				.map(CommissionShareId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private ImmutableList<I_C_Commission_Fact> retrieveFactRecords(@NonNull final I_C_Commission_Share shareRecord)
	{
		final ImmutableList<I_C_Commission_Fact> factRecords = queryBL.createQueryBuilder(I_C_Commission_Fact.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Commission_Fact.COLUMN_C_Commission_Share_ID, shareRecord.getC_Commission_Share_ID())
				.addInArrayFilter(I_C_Commission_Fact.COLUMN_Commission_Fact_State, CommissionSettlementState.allRecordCodes())
				.orderBy(I_C_Commission_Fact.COLUMN_CommissionFactTimestamp)
				.create()
				.listImmutable(I_C_Commission_Fact.class);
		return factRecords;
	}

	private void createNewFactRecords(
			@NonNull final ImmutableList<CommissionSettlementFact> facts,
			final int commissionShareRecordId,
			@NonNull final OrgId orgId,
			@NonNull final ImmutableList<I_C_Commission_Fact> preexistingFactRecords)
	{
		final ImmutableMap<ArrayKey, I_C_Commission_Fact> idAndTypeAndTimestampToFactRecord = Maps.uniqueIndex(
				preexistingFactRecords,
				r -> ArrayKey.of(r.getC_Commission_Share_ID(), r.getCommission_Fact_State(), r.getCommissionFactTimestamp()));

		for (final CommissionSettlementFact fact : facts)
		{
			final I_C_Commission_Fact factRecordOrNull = idAndTypeAndTimestampToFactRecord.get(
					ArrayKey.of(commissionShareRecordId, fact.getState().toString(), TimeUtil.serializeInstant(fact.getTimestamp())));
			if (factRecordOrNull != null)
			{
				continue;
			}
			final I_C_Commission_Fact factRecord = newInstance(I_C_Commission_Fact.class);
			factRecord.setAD_Org_ID(orgId.getRepoId());
			factRecord.setC_Commission_Share_ID(commissionShareRecordId);
			factRecord.setC_Invoice_Candidate_Commission_ID(fact.getSettlementInvoiceCandidateId().getRepoId());
			factRecord.setCommissionPoints(fact.getPoints().toBigDecimal());
			factRecord.setCommission_Fact_State(fact.getState().toString());
			factRecord.setCommissionFactTimestamp(TimeUtil.serializeInstant(fact.getTimestamp()));
			saveRecord(factRecord);
		}
	}
}
