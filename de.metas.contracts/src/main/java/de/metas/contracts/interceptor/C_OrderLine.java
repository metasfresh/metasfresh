package de.metas.contracts.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.contracts.subscription.model.I_C_OrderLine;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupCompensationChangesHandler;
import de.metas.order.compensationGroup.OrderGroupRepository;

@Interceptor(I_C_OrderLine.class)
public class C_OrderLine
{
	private static final ModelDynAttributeAccessor<I_C_OrderLine, Boolean> DYNATTR_SkipUpdatingGroupFlatrateConditions = new ModelDynAttributeAccessor<>("SkipUpdatingGroupFlatrateConditions", Boolean.class);

	@Autowired
	private OrderGroupCompensationChangesHandler groupChangesHandler;

	public C_OrderLine()
	{
		Adempiere.autowire(this);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW }, skipIfCopying = true)
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
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_C_OrderLine.COLUMNNAME_C_Flatrate_Conditions_ID, skipIfCopying = true)
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
		final int excludeOrderLineId = orderLine.getC_OrderLine_ID();
		setFlatrateConditionsIdToCompensationGroup(flatrateConditionsId, groupId, excludeOrderLineId);

		groupChangesHandler.recreateGroupOnOrderLineChanged(orderLine);
	}

	private int retrieveFirstFlatrateConditionsIdForCompensationGroup(final GroupId groupId)
	{
		final Integer flatrateConditionsId = groupChangesHandler.retrieveGroupOrderLinesQuery(groupId)
				.addNotNull(I_C_OrderLine.COLUMNNAME_C_Flatrate_Conditions_ID)
				.orderBy(I_C_OrderLine.COLUMNNAME_Line)
				.orderBy(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID)
				.create()
				.first(I_C_OrderLine.COLUMNNAME_C_Flatrate_Conditions_ID, Integer.class);
		return flatrateConditionsId != null && flatrateConditionsId > 0 ? flatrateConditionsId : -1;
	}

	private void setFlatrateConditionsIdToCompensationGroup(final int flatrateConditionsId, final GroupId groupId, final int excludeOrderLineId)
	{
		groupChangesHandler.retrieveGroupOrderLinesQuery(groupId)
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
}
