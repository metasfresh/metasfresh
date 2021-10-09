package de.metas.order.process;

import lombok.NonNull;
import org.compiere.model.I_M_Product;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequest;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.GroupTemplateCompensationLine;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * Add compensation order line to existing group.
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
public class C_Order_AddDiscountCompensationLine extends OrderCompensationGroupProcess
{
	@Autowired
	private GroupCompensationLineCreateRequestFactory compensationLineCreateRequestFactory;

	@Param(parameterName = I_M_Product.COLUMNNAME_M_Product_ID, mandatory = true)
	private int compensationProductId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		return acceptIfEligibleOrder(context)
				.and(() -> acceptIfOrderLinesHaveSameGroupId(context));
	}

	@Override
	protected String doIt()
	{
		final GroupId groupId = groupsRepo.extractSingleGroupIdFromOrderLineIds(getSelectedOrderLineIds());
		final Group group = groupsRepo.retrieveGroup(groupId);
		group.addNewCompensationLine(createGroupCompensationLineCreateRequest(group));
		groupsRepo.saveGroup(group);
		groupsRepo.renumberOrderLinesForOrderId(OrderGroupRepository.extractOrderIdFromGroupId(groupId));
		return MSG_OK;
	}

	private GroupCompensationLineCreateRequest createGroupCompensationLineCreateRequest(final Group group)
	{
		final GroupTemplateCompensationLine templateLine = GroupTemplateCompensationLine.ofProductId(ProductId.ofRepoId(compensationProductId));
		return compensationLineCreateRequestFactory.createGroupCompensationLineCreateRequest(templateLine, group);
	}
}
