package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermStatus;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.DocStatus;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

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
 * Queries {@code CompensationGroup}-type contract terms.
 */
@Repository
public class ContractCompensationGroupTermRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * @return every active {@code CompensationGroup}-type term of {@code billPartnerId} whose {@code DocStatus} is
	 * completed/closed, whose {@code ContractStatus} is not voided, and whose {@code [StartDate, EndDate]} period
	 * (inclusive) covers {@code date} — ordered by {@code C_Flatrate_Term_ID} for a deterministic result.
	 * Doc-type membership is not filtered here (it needs the settings repository); the caller filters it.
	 */
	public List<I_C_Flatrate_Term> findActiveTerms(@NonNull final BPartnerId billPartnerId, @NonNull final LocalDate date)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, billPartnerId)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, TypeConditions.COMPENSATION_GROUP)
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, ImmutableList.of(DocStatus.Completed, DocStatus.Closed))
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Voided)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, TimeUtil.asTimestamp(date))
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, TimeUtil.asTimestamp(date))
				.orderBy()
				.addColumn(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
				.endOrderBy()
				.create()
				.list(I_C_Flatrate_Term.class);
	}
}
