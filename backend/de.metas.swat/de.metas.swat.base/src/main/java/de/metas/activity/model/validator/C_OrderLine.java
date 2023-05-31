package de.metas.activity.model.validator;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.organization.OrgId;
import de.metas.product.IProductActivityProvider;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;

@Validator(I_C_OrderLine.class)
public class C_OrderLine
{
	private final DimensionService dimensionService = SpringContextHolder.instance.getBean(DimensionService.class);
	private final OrderGroupRepository orderGroupRepo = SpringContextHolder.instance.getBean(OrderGroupRepository.class);
	private final IProductActivityProvider productActivityProvider = Services.get(IProductActivityProvider.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_M_Product_ID,
					I_C_OrderLine.COLUMNNAME_C_Order_CompensationGroup_ID })
	public void updateActivity(final I_C_OrderLine orderLine)
	{

		if (InterfaceWrapperHelper.isCopy(orderLine))
		{
			// let the activity be copied from the source.

			return;
		}

		final ActivityId groupActivityId = getGroupActivityId(orderLine);

		orderLine.setC_Activity_ID(ActivityId.toRepoId(groupActivityId));

		if (orderLine.getC_Activity_ID() > 0)
		{
			return; // was already set, so don't try to auto-fill it
		}

		final ProductId productId = ProductId.ofRepoIdOrNull(orderLine.getM_Product_ID());
		if (productId == null)
		{
			return;
		}

		// IsDiverse flag
		orderLine.setIsDiverse(productBL.isDiverse(productId));

		// Activity
		final ActivityId productActivityId = productActivityProvider.getActivityForAcct(
				ClientId.ofRepoId(orderLine.getAD_Client_ID()),
				OrgId.ofRepoId(orderLine.getAD_Org_ID()),
				productId);
		if (productActivityId == null)
		{
			return;
		}

		final Dimension orderLineDimension = dimensionService.getFromRecord(orderLine);
		if (orderLineDimension == null)

		{
			//nothing to do
			return;
		}

		dimensionService.updateRecordIncludingUserElements(orderLine, orderLineDimension.withActivityId(productActivityId));
	}

	private ActivityId getGroupActivityId(final I_C_OrderLine orderLine)
	{
		if (orderLine.getC_Order_CompensationGroup_ID() <= 0)
		{
			return null;
		}

		final GroupId groupId = OrderGroupRepository.extractGroupIdOrNull(orderLine);

		if (groupId == null)
		{
			return null;
		}

		final Group group = orderGroupRepo.retrieveGroupIfExists(groupId);

		if (group == null)
		{
			return null;
		}

		return group.getActivityId();
	}
}
