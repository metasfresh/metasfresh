package de.metas.contracts.compensationGroup.contract;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermStatus;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.DocStatus;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Repository Tables: C_Flatrate_Term
 * <p>
 * Repository Cluster: ContractCompensationGroupTermRepository, {@code FlatrateDAO} — this one reads
 * {@code C_Flatrate_Term} (CompensationGroup terms only) and saves their contract status; {@code FlatrateDAO} reads and writes all terms.
 * <p>
 * Queries {@code CompensationGroup}-type contract terms.
 */
@Repository
public class ContractCompensationGroupTermRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@VisibleForTesting
	public static ContractCompensationGroupTermRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(ContractCompensationGroupTermRepository.class, ContractCompensationGroupTermRepository::new);
	}

	/**
	 * @return every active {@code CompensationGroup}-type term of {@code billPartnerId} whose {@code DocStatus} is
	 * completed/closed, whose {@code ContractStatus} is not voided, and whose {@code [StartDate, EndDate]} period
	 * (inclusive) covers {@code date} — ordered by {@code C_Flatrate_Term_ID} for a deterministic result.
	 * Doc-type membership is not filtered here (it needs the settings repository); the caller filters it.
	 */
	public List<I_C_Flatrate_Term> findActiveTerms(@NonNull final BPartnerId billPartnerId, @NonNull final LocalDate date)
	{
		return activeTermsQueryBuilder(billPartnerId)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, TimeUtil.asTimestamp(date))
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, TimeUtil.asTimestamp(date))
				.orderBy()
				.addColumn(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
				.endOrderBy()
				.create()
				.list(I_C_Flatrate_Term.class);
	}

	/**
	 * @return every OTHER active {@code CompensationGroup}-type term of {@code billPartnerId} — same active-term
	 * predicate as {@link #findActiveTerms} (DocStatus completed/closed, ContractStatus not voided) — whose
	 * {@code [StartDate, EndDate]} period (inclusive) overlaps {@code [start, end]}, excluding
	 * {@code excludeTermId}. Not scoped by org — a compensation-group contract matches "the same invoice
	 * partner", the same scope {@link #findActiveTerms} uses for order matching. Never cached.
	 */
	public List<I_C_Flatrate_Term> findActiveTermsOverlapping(
			@NonNull final BPartnerId billPartnerId,
			@NonNull final LocalDate start,
			@NonNull final LocalDate end,
			@NonNull final FlatrateTermId excludeTermId)
	{
		return activeTermsQueryBuilder(billPartnerId)
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID, excludeTermId)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, TimeUtil.asTimestamp(end))
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, TimeUtil.asTimestamp(start))
				.orderBy()
				.addColumn(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
				.endOrderBy()
				.create()
				.list(I_C_Flatrate_Term.class);
	}

	/**
	 * @return every active {@code CompensationGroup}-type term of the context client whose {@code DocStatus} is completed/closed
	 * and whose contract status the daily update may change on {@code today}, ordered by {@code C_Flatrate_Term_ID}:
	 * <ul>
	 *     <li>"not yet started" ({@code Wa}) and started on or before {@code today}, or</li>
	 *     <li>"running" ({@code Ru}), ended before {@code today} and not extended ({@code C_FlatrateTerm_Next_ID} empty)</li>
	 * </ul>
	 */
	public List<I_C_Flatrate_Term> getTermsDueForDailyContractStatusUpdate(@NonNull final LocalDate today)
	{
		final Timestamp startOfToday = TimeUtil.asTimestamp(today);
		final Timestamp startOfTomorrow = TimeUtil.asTimestamp(today.plusDays(1));

		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, TypeConditions.COMPENSATION_GROUP)
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, ImmutableList.of(DocStatus.Completed, DocStatus.Closed))
				.filter(queryBL.createCompositeQueryFilter(I_C_Flatrate_Term.class)
						.setJoinOr()
						.addFilter(queryBL.createCompositeQueryFilter(I_C_Flatrate_Term.class)
								.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Waiting)
								// "StartDate < tomorrow 00:00" rather than "<= today 00:00": a start date with a time of day still counts as today
								.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS, startOfTomorrow))
						.addFilter(queryBL.createCompositeQueryFilter(I_C_Flatrate_Term.class)
								.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Running)
								.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.LESS, startOfToday)
								.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_C_FlatrateTerm_Next_ID, null, 0)))
				.orderBy()
				.addColumn(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
				.endOrderBy()
				.create()
				.list(I_C_Flatrate_Term.class);
	}

	public void saveContractStatus(@NonNull final I_C_Flatrate_Term term, @NonNull final FlatrateTermStatus contractStatus)
	{
		term.setContractStatus(contractStatus.getCode());
		InterfaceWrapperHelper.saveRecord(term);
	}

	/** The active-term predicate shared by {@link #findActiveTerms} and {@link #findActiveTermsOverlapping}: active, {@code billPartnerId}, {@code CompensationGroup} type, DocStatus completed/closed, ContractStatus not voided. */
	private IQueryBuilder<I_C_Flatrate_Term> activeTermsQueryBuilder(@NonNull final BPartnerId billPartnerId)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, billPartnerId)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, TypeConditions.COMPENSATION_GROUP)
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, ImmutableList.of(DocStatus.Completed, DocStatus.Closed))
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Voided);
	}
}
