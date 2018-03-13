package de.metas.order.process;

import org.adempiere.util.Check;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;

import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateLine;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;

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

	@Param(parameterName = I_M_Product_Category.COLUMNNAME_M_Product_Category_ID, mandatory = false)
	private I_M_Product_Category productCategory;

	@Param(parameterName = "Name", mandatory = false)
	private String groupName;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		return acceptIfEligibleOrder(context)
				.and(() -> acceptIfOrderLinesNotInGroup(context));
	}

	@Override
	protected String doIt()
	{
		groupsRepo.prepareNewGroup()
				.linesToGroup(getSelectedOrderLineIds())
				.groupTemplate(createNewGroupTemplate())
				.createGroup();

		return MSG_OK;
	}

	private GroupTemplate createNewGroupTemplate()
	{
		String groupNameEffective = this.groupName;
		if (Check.isEmpty(groupNameEffective, true) && productCategory != null)
		{
			groupNameEffective = productCategory.getName();
		}

		return GroupTemplate.builder()
				.name(groupNameEffective)
				.productCategoryId(productCategory != null ? productCategory.getM_Product_Category_ID() : 0)
				.line(GroupTemplateLine.builder()
						.productId(compensationProductId)
						.build())
				.build();
	}
}
