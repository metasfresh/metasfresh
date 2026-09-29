package de.metas.contracts.compensationGroup.contract;

import de.metas.contracts.FlatrateTermId;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
 * Repository Tables: C_Order_CompensationGroup, C_OrderLine
 * <p>
 * Persistence primitives for a contract-created {@code C_Order_CompensationGroup}: stamping the term it came
 * from, and the plumbing {@link ContractCompensationGroupService} needs to remove one again.
 */
@Repository
public class ContractCompensationGroupRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/** Stamps the group header with the contract term it was (re)created from. */
	public void setFlatrateTerm(@NonNull final GroupId groupId, @NonNull final FlatrateTermId termId)
	{
		final I_C_Order_CompensationGroup groupRecord = load(groupId.getOrderCompensationGroupId(), I_C_Order_CompensationGroup.class);
		groupRecord.setC_Flatrate_Term_ID(termId.getRepoId());
		saveRecord(groupRecord);
	}

	/** @return the {@code C_Order_CompensationGroup_ID}s of {@code orderId}'s contract-created groups (those with a {@code C_Flatrate_Term_ID} set) */
	public List<Integer> retrieveContractGroupOrderCompensationGroupIds(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_Order_CompensationGroup.class)
				.addEqualsFilter(I_C_Order_CompensationGroup.COLUMNNAME_C_Order_ID, orderId)
				.addCompareFilter(I_C_Order_CompensationGroup.COLUMNNAME_C_Flatrate_Term_ID, Operator.GREATER, 0)
				.create()
				.listIds();
	}

	/** Deletes the given group's compensation (discount) lines, leaving its regular lines ungrouped so {@link OrderGroupRepository#retrieveGroupIfExists} can rebuild it. */
	public void deleteCompensationLines(@NonNull final OrderId orderId, final int orderCompensationGroupId)
	{
		queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_CompensationGroup_ID, orderCompensationGroupId)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, true)
				.create()
				.delete();
	}

	/** @return this order's not-(yet-)grouped, active, non-compensation regular lines — candidates for a new group. */
	public List<I_C_OrderLine> retrieveUngroupedActiveOrderLines(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, false)
				.create()
				.list(I_C_OrderLine.class);
	}
}
