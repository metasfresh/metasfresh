package de.metas.order.process;

import org.adempiere.util.Check;
import org.compiere.Adempiere;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.adempiere.model.I_C_Order;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateLine;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
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

public class C_Order_CreateCompensationGroup extends JavaProcess implements IProcessPrecondition
{
	@Autowired
	private OrderGroupRepository groupsRepo;

	@Param(parameterName = I_M_Product.COLUMNNAME_M_Product_ID, mandatory = true)
	private int compensationProductId;

	@Param(parameterName = I_M_Product_Category.COLUMNNAME_M_Product_Category_ID, mandatory = false)
	private I_M_Product_Category productCategory;

	@Param(parameterName = "Name", mandatory = false)
	private String groupName;

	public C_Order_CreateCompensationGroup()
	{
		Adempiere.autowire(this);
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("one and only one order shall be selected");
		}

		// Only draft orders
		final I_C_Order order = context.getSelectedModel(I_C_Order.class);
		if (order.isProcessed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only draft orders are allowed");
		}

		// Only sales orders
		if (!order.isSOTrx())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only sales orders are allowed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		groupsRepo.prepareNewGroup()
				.linesToGroup(getSelectedIncludedRecordIds(I_C_OrderLine.class))
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
