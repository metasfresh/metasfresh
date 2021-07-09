/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.compiere.model.I_C_BP_SupplierApproval;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class BPartnerSupplierApprovalRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public I_C_BP_SupplierApproval getById(@NonNull final BPSupplierApprovalId bpSupplierApprovalId)
	{
		return load(bpSupplierApprovalId, I_C_BP_SupplierApproval.class);
	}

	public ImmutableList<I_C_BP_SupplierApproval> retrieveBPartnerSupplierApprovals(@NonNull final BPartnerId partnerId)
	{
		return queryBL.createQueryBuilder(I_C_BP_SupplierApproval.class)
				.addEqualsFilter(I_C_BP_SupplierApproval.COLUMNNAME_C_BPartner_ID, partnerId)
				.addOnlyActiveRecordsFilter()
				.create()
				.listImmutable(I_C_BP_SupplierApproval.class);
	}

	public void updateBPSupplierApproval(@NonNull final BPSupplierApprovalId bpSupplierApprovalId,
			@Nullable final String supplierApproval,
			@Nullable final Instant supplierApprovalDateParameter)
	{
		final I_C_BP_SupplierApproval bpSupplierApprovalRecord = getById(bpSupplierApprovalId);

		bpSupplierApprovalRecord.setSupplierApproval(supplierApproval);

		final Timestamp supplierApprovalDate = supplierApproval == null ? null : TimeUtil.asTimestamp(supplierApprovalDateParameter);

		bpSupplierApprovalRecord.setSupplierApproval_Date(supplierApprovalDate);

		save(bpSupplierApprovalRecord);

	}

	public ImmutableList<I_C_BP_SupplierApproval> retrieveBPSupplierApprovalsAboutToExpire(final int maxMonthsUntilExpirationDate)
	{
		final LocalDate today = SystemTime.asLocalDate();
		final LocalDate maxExpirationDate = today.plusMonths(maxMonthsUntilExpirationDate);

		final IQueryFilter<I_C_BP_SupplierApproval> filterThreeYears =
				queryBL.createCompositeQueryFilter(I_C_BP_SupplierApproval.class)
						.addEqualsFilter(I_C_BP_SupplierApproval.COLUMNNAME_SupplierApproval, SupplierApproval.ThreeYears)
						.addCompareFilter(I_C_BP_SupplierApproval.COLUMNNAME_SupplierApproval_Date,
										  CompareQueryFilter.Operator.LESS_OR_EQUAL,
										  maxExpirationDate.minusYears(3));

		final IQueryFilter<I_C_BP_SupplierApproval> filterTwoYears =
				queryBL.createCompositeQueryFilter(I_C_BP_SupplierApproval.class)
						.addEqualsFilter(I_C_BP_SupplierApproval.COLUMNNAME_SupplierApproval, SupplierApproval.TwoYears)
						.addCompareFilter(I_C_BP_SupplierApproval.COLUMNNAME_SupplierApproval_Date,
										  CompareQueryFilter.Operator.LESS_OR_EQUAL,
										  maxExpirationDate.minusYears(2));

		final IQueryFilter<I_C_BP_SupplierApproval> filterOneYear =
				queryBL.createCompositeQueryFilter(I_C_BP_SupplierApproval.class)
						.addEqualsFilter(I_C_BP_SupplierApproval.COLUMNNAME_SupplierApproval, SupplierApproval.OneYear)
						.addCompareFilter(I_C_BP_SupplierApproval.COLUMNNAME_SupplierApproval_Date,
										  CompareQueryFilter.Operator.LESS_OR_EQUAL,
										  maxExpirationDate.minusYears(1));

		final IQueryFilter<I_C_BP_SupplierApproval> supplierApprovalOptionFilter
				= queryBL.createCompositeQueryFilter(I_C_BP_SupplierApproval.class)
				.setJoinOr()
				.addFilter(filterThreeYears)
				.addFilter(filterTwoYears)
				.addFilter(filterOneYear);

		return queryBL.createQueryBuilder(I_C_BP_SupplierApproval.class)
				.filter(supplierApprovalOptionFilter)
				.create()
				.listImmutable(I_C_BP_SupplierApproval.class)
				;

	}
}
