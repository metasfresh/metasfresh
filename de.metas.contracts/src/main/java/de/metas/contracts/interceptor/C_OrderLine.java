package de.metas.contracts.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;

/*
 * #%L
 * de.metas.contracts
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

import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.contracts.subscription.model.I_C_OrderLine;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupRepository;

/**
 *
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Abonnement_Auftragsverwaltung_(2009_0015_G36)'>(2009_0015_G36)</a>"
 */
// code used to be located in /sw01_swat_it/src/java/org/adempiere/order/subscription/modelvalidator/OrderValidator.java
@Interceptor(I_C_OrderLine.class)
public class C_OrderLine
{
	private static final ModelDynAttributeAccessor<I_C_OrderLine, Boolean> DYNATTR_SkipUpdatingGroupFlatrateConditions = new ModelDynAttributeAccessor<>("SkipUpdatingGroupFlatrateConditions", Boolean.class);

	@Autowired
	private OrderGroupRepository orderGroupsRepo;

	public C_OrderLine()
	{
		Adempiere.autowire(this);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void setSameFlatrateConditionsForWholeCompensationGroupWhenGroupIsCreated(final I_C_OrderLine orderLine)
	{
		if (!orderLine.isGroupCompensationLine())
		{
			return;
		}

		final GroupId groupId = OrderGroupRepository.extractGroupId(orderLine);
		final int flatrateConditionsId = retrieveFirstFlatrateConditionsIdForCompensationGroup(groupId);

		orderLine.setC_Flatrate_Conditions_ID(flatrateConditionsId);

		int excludeOrderLineId = orderLine.getC_OrderLine_ID();
		setFlatrateConditionsIdToCompensationGroup(flatrateConditionsId, groupId, excludeOrderLineId);
	}

	/**
	 * In case the flatrate conditions for an order line is updated and that line is part of an compensation group,
	 * then set the same flatrate conditions to all other lines from the same compensation group.
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/3150
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_C_OrderLine.COLUMNNAME_C_Flatrate_Conditions_ID)
	public void setSameFlatrateConditionsForWholeCompensationGroupWhenOneGroupLineChanged(final I_C_OrderLine orderLine)
	{
		if (DYNATTR_SkipUpdatingGroupFlatrateConditions.getValue(orderLine, Boolean.FALSE))
		{
			return;
		}

		final GroupId groupId = OrderGroupRepository.extractGroupIdOrNull(orderLine);
		if (groupId == null)
		{
			return;
		}

		final int flatrateConditionsId = orderLine.getC_Flatrate_Conditions_ID();
		int excludeOrderLineId = orderLine.getC_OrderLine_ID();
		setFlatrateConditionsIdToCompensationGroup(flatrateConditionsId, groupId, excludeOrderLineId);
	}

	private int retrieveFirstFlatrateConditionsIdForCompensationGroup(final GroupId groupId)
	{
		return orderGroupsRepo.retrieveGroupOrderLinesQuery(groupId)
				.addNotNull(I_C_OrderLine.COLUMNNAME_C_Flatrate_Conditions_ID)
				.orderBy(I_C_OrderLine.COLUMNNAME_Line)
				.orderBy(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID)
				.create()
				.first(I_C_OrderLine.COLUMNNAME_C_Flatrate_Conditions_ID, Integer.class);
	}

	private void setFlatrateConditionsIdToCompensationGroup(final int flatrateConditionsId, final GroupId groupId, final int excludeOrderLineId)
	{
		orderGroupsRepo.retrieveGroupOrderLinesQuery(groupId)
				.addNotEqualsFilter(I_C_OrderLine.COLUMN_C_OrderLine_ID, excludeOrderLineId)
				.addNotEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Flatrate_Conditions_ID, flatrateConditionsId > 0 ? flatrateConditionsId : null)
				.create()
				.list(I_C_OrderLine.class)
				.forEach(otherOrderLine -> {
					otherOrderLine.setC_Flatrate_Conditions_ID(flatrateConditionsId);
					DYNATTR_SkipUpdatingGroupFlatrateConditions.setValue(otherOrderLine, Boolean.TRUE);
					save(otherOrderLine);
				});
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void updateDataEntry(final I_C_OrderLine ol)
	{
		if (ol.isProcessed())
		{
			final I_C_Order order = ol.getC_Order();
			final int subscriptionId = ol.getC_Flatrate_Conditions_ID();
			if (subscriptionId <= 0)
			{
				// TODO figure out wtf this check and this error message mean
				throw new AdempiereException("OrderLine " + ol.getLine()
						+ " of order " + order.getDocumentNo()
						+ " is processed, but I don't know why");
			}
		}
	}
}
