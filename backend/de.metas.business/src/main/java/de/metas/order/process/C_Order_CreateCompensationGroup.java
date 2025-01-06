package de.metas.order.process;

import com.google.common.collect.ImmutableList;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateCompensationLine;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;

/*
 * #%L
 * de.metas.business
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

public class C_Order_CreateCompensationGroup extends OrderCompensationGroupProcess
{
	@Param(parameterName = I_M_Product.COLUMNNAME_M_Product_ID, mandatory = true)
	private int compensationProductId;

	@Param(parameterName = I_M_Product_Category.COLUMNNAME_M_Product_Category_ID)
	private I_M_Product_Category productCategory;

	@Param(parameterName = "Name")
	private String groupName;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		return acceptIfEligibleOrder(context)
				.and(() -> acceptIfOrderLinesNotInGroup(context));
	}

	@Override
	protected String doIt()
	{
		try (final OrderGroupRepository.OrderIdsToRenumber orderIdsToRenumber = groupsRepo.delayOrderLinesRenumbering())
		{
			final Group group = groupsRepo.prepareNewGroup()
					.groupTemplate(createNewGroupTemplate())
					.createGroup(getSelectedOrderLineIds());

			final OrderId orderId = OrderGroupRepository.extractOrderIdFromGroupId(group.getGroupId());
			orderIdsToRenumber.addOrderId(orderId);
		}

		return MSG_OK;
	}

	private GroupTemplate createNewGroupTemplate()
	{
		String groupNameEffective = this.groupName;
		if (Check.isEmpty(groupNameEffective, true) && productCategory != null)
		{
			groupNameEffective = productCategory.getName();
		}
		if (Check.isEmpty(groupNameEffective, true))
		{
			throw new FillMandatoryException("Name");
		}

		return GroupTemplate.builder()
				.name(groupNameEffective)
				.productCategoryId(productCategory != null ? ProductCategoryId.ofRepoId(productCategory.getM_Product_Category_ID()) : null)
				.compensationLine(GroupTemplateCompensationLine.ofProductId(ProductId.ofRepoId(compensationProductId)))
				.regularLinesToAdd(ImmutableList.of())
				.build();
	}
}
