package de.metas.contracts.interceptor;

import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderLineBL;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.order.compensationGroup.OrderGroupCompensationChangesHandler;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.save;

@Interceptor(I_C_OrderLine.class)
@Component
public class C_OrderLine
{
	private static final ModelDynAttributeAccessor<I_C_OrderLine, Boolean> DYNATTR_SkipUpdatingGroupFlatrateConditions = new ModelDynAttributeAccessor<>("SkipUpdatingGroupFlatrateConditions", Boolean.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final ISubscriptionBL subscriptionBL = Services.get(ISubscriptionBL.class);
	private final OrderGroupCompensationChangesHandler groupChangesHandler;

	public C_OrderLine(@NonNull final OrderGroupCompensationChangesHandler groupChangesHandler)
	{
		this.groupChangesHandler = groupChangesHandler;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW }, skipIfCopying = true)
	public void setSameFlatrateConditionsForWholeCompensationGroupWhenGroupIsCreated(final I_C_OrderLine orderLine)
	{
		if (!orderLine.isGroupCompensationLine())
		{
			return;
		}

		final GroupId groupId = OrderGroupRepository.extractGroupIdOrNull(orderLine);
		final GroupTemplateId groupTemplateId = groupChangesHandler.getGroupTemplateId(groupId);

		if (groupChangesHandler.isProductExcludedFromFlatrateConditions(groupTemplateId, ProductId.ofRepoId(orderLine.getM_Product_ID())))
		{
			return;
		}

		final int flatrateConditionsId = retrieveFirstFlatrateConditionsIdForCompensationGroup(groupId);

		orderLine.setC_Flatrate_Conditions_ID(flatrateConditionsId);

		final int excludeOrderLineId = orderLine.getC_OrderLine_ID();
		setFlatrateConditionsIdToCompensationGroup(flatrateConditionsId, groupId, groupTemplateId, excludeOrderLineId);
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
		if (groupId != null)
		{
			final GroupTemplateId groupTemplateId = groupChangesHandler.getGroupTemplateId(groupId);

			if (groupChangesHandler.isProductExcludedFromFlatrateConditions(groupTemplateId, ProductId.ofRepoId(orderLine.getM_Product_ID())))
			{
				return;
			}
			final int flatrateConditionsId = orderLine.getC_Flatrate_Conditions_ID();
			final int excludeOrderLineId = orderLine.getC_OrderLine_ID();
			setFlatrateConditionsIdToCompensationGroup(flatrateConditionsId, groupId, groupTemplateId, excludeOrderLineId);
		}
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

	private void setFlatrateConditionsIdToCompensationGroup(final int flatrateConditionsId,
			final GroupId groupId,
			final GroupTemplateId groupTemplateId,
			final int excludeOrderLineId)
	{
		groupChangesHandler.retrieveGroupOrderLinesQuery(groupId)
				.addNotEqualsFilter(I_C_OrderLine.COLUMN_C_OrderLine_ID, excludeOrderLineId)
				.addNotEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Flatrate_Conditions_ID, flatrateConditionsId > 0 ? flatrateConditionsId : null)
				.create()
				.list(I_C_OrderLine.class)
				.stream()
				.filter(ol -> !groupChangesHandler.isProductExcludedFromFlatrateConditions(groupTemplateId, ProductId.ofRepoId(ol.getM_Product_ID())))
				.forEach(otherOrderLine -> {
					otherOrderLine.setC_Flatrate_Conditions_ID(flatrateConditionsId);
					DYNATTR_SkipUpdatingGroupFlatrateConditions.setValue(otherOrderLine, Boolean.TRUE);
					save(otherOrderLine);
				});
	}

	/**
	 * Set QtyOrderedInPriceUOM, just to make sure is up2date.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = { de.metas.interfaces.I_C_OrderLine.COLUMNNAME_QtyEntered,
			de.metas.interfaces.I_C_OrderLine.COLUMNNAME_Price_UOM_ID,
			de.metas.interfaces.I_C_OrderLine.COLUMNNAME_C_UOM_ID,
			de.metas.interfaces.I_C_OrderLine.COLUMNNAME_M_Product_ID
	})
	public void setQtyEnteredInPriceUOM(final I_C_OrderLine orderLine)
	{
		if (subscriptionBL.isSubscription(orderLine))
		{
			final org.compiere.model.I_C_Order order = orderLine.getC_Order();
			final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());

			if (soTrx.isPurchase())
			{
				return; // leave this job to the adempiere standard callouts
			}

			subscriptionBL.updateQtysAndPrices(orderLine, soTrx, true);
		}
		else
		{
			final BigDecimal qtyEnteredInPriceUOM = orderLineBL.convertQtyEnteredToPriceUOM(orderLine).toBigDecimal();
			orderLine.setQtyEnteredInPriceUOM(qtyEnteredInPriceUOM);
		}
	}
}
