package de.metas.order.model.interceptor;

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

import java.math.BigDecimal;
import java.util.Optional;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.CalloutOrder;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.order.compensationGroup.OrderGroupCompensationChangesHandler;
import de.metas.order.compensationGroup.OrderGroupRepository;

@Interceptor(I_C_OrderLine.class)
@Callout(I_C_OrderLine.class)
public class C_OrderLine
{
	public static final C_OrderLine INSTANCE = new C_OrderLine();

	private static final Logger logger = LogManager.getLogger(C_OrderLine.class);
	@Autowired
	private OrderGroupCompensationChangesHandler groupChangesHandler;

	public static final String ERR_NEGATIVE_QTY_RESERVED = "MSG_NegativeQtyReserved";

	private C_OrderLine()
	{
		Adempiere.autowire(this);

		// NOTE: in unit test mode and while running tools like model generators,
		// the groupsRepo is not Autowired because there is no spring context,
		// so we have to instantiate it directly
		if (groupChangesHandler == null && Adempiere.isUnitTestMode())
		{
			groupChangesHandler = new OrderGroupCompensationChangesHandler(
					new OrderGroupRepository(
							new GroupCompensationLineCreateRequestFactory(),
							Optional.empty()),
					new GroupTemplateRepository(Optional.empty()));
		}
	};

	/**
	 * 09557: If a purchase order line is deleted, then all sales order lines need to un-reference it to avoid an FK-constraint-error
	 * FRESH-386: likewise, also make sure that counter document lines are unlinked as well.
	 *
	 * @param orderLine
	 * @task http://dewiki908/mediawiki/index.php/09557_Wrong_aggregation_on_OrderPOCreate_%28109614894753%29
	 * @task https://metasfresh.atlassian.net/browse/FRESH-386
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void unlinkReferencedOrderLines(final I_C_OrderLine orderLine)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		// 09557
		queryBL
				.createQueryBuilder(I_C_OrderLine.class, orderLine)
				.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMNNAME_Link_OrderLine_ID, orderLine.getC_OrderLine_ID())
				.create()
				.update(ol -> {
					ol.setLink_OrderLine(null);
					return IQueryUpdater.MODEL_UPDATED;
				});

		// FRESH-386
		queryBL
				.createQueryBuilder(I_C_OrderLine.class, orderLine)
				.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMNNAME_Ref_OrderLine_ID, orderLine.getC_OrderLine_ID()) // ref_orderline_id is used with counter docs
				.create()
				.update(ol -> {
					ol.setRef_OrderLine(null);
					return IQueryUpdater.MODEL_UPDATED;
				});

	}

	/**
	 * Set QtyOrderedInPriceUOM, just to make sure is up2date.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_QtyEntered,
			I_C_OrderLine.COLUMNNAME_Price_UOM_ID,
			I_C_OrderLine.COLUMNNAME_C_UOM_ID,
			I_C_OrderLine.COLUMNNAME_M_Product_ID
	})
	public void setQtyEnteredInPriceUOM(final I_C_OrderLine orderLine)
	{
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		final BigDecimal qtyEnteredInPriceUOM = orderLineBL.convertQtyEnteredToPriceUOM(orderLine);
		orderLine.setQtyEnteredInPriceUOM(qtyEnteredInPriceUOM);
	}

	/**
	 * Set qtyOrdered, to make sure is up2date. Note that this value is also set in
	 * {@link CalloutOrder#amt(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, Object)}.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_M_Product_ID,
			I_C_OrderLine.COLUMNNAME_QtyEntered,
			I_C_OrderLine.COLUMNNAME_C_UOM_ID
	})
	public void setQtyOrdered(final I_C_OrderLine orderLine)
	{
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		final BigDecimal qtyOrdered = orderLineBL.convertQtyEnteredToInternalUOM(orderLine);
		orderLine.setQtyOrdered(qtyOrdered);
	}

	/**
	 *
	 * @param orderLine
	 * @task http://dewiki908/mediawiki/index.php/09358_OrderLine-QtyReserved_sometimes_not_updated_%28108061810375%29
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_QtyOrdered,
					I_C_OrderLine.COLUMNNAME_QtyDelivered,
					I_C_OrderLine.COLUMNNAME_C_Order_ID })
	public void updateReserved(final I_C_OrderLine orderLine)
	{
		Services.get(IOrderLineBL.class).updateQtyReserved(orderLine);
	}

	/**
	 * Ported this method from de.metas.adempiere.modelvalidator.OrderLine
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = I_C_OrderLine.COLUMNNAME_QtyReserved)
	public void checkQtyReserved(final I_C_OrderLine ol)
	{
		if (ol.getQtyReserved().signum() >= 0)
		{
			return; // nothing to do
		}
		// task: 08665
		// replace the negative qty with 0, logging a warning message
		ol.setQtyReserved(BigDecimal.ZERO);

		// task: 08665 ts: but only log when in developer mode. i don't see how this warning should help the user
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			final AdempiereException ex = new AdempiereException("@" + ERR_NEGATIVE_QTY_RESERVED + "@. Setting QtyReserved to ZERO."
					+ "\nStorage: " + ol);
			logger.warn(ex.getLocalizedMessage(), ex);
		}
	}

	/**
	 * @task https://github.com/metasfresh/metasfresh/issues/3298
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = I_C_OrderLine.COLUMNNAME_QtyReserved)
	public void checkQtyOrdered(final I_C_OrderLine ol)
	{
		final boolean qtyOrderedLessThanZero = ol.getQtyOrdered().signum() < 0;

		Check.errorIf(qtyOrderedLessThanZero,
				"QtyOrdered needs to be >= 0, but the given ol has QtyOrdered={}; ol={}; C_Order_ID={}",
				ol.getQtyOrdered(), ol, ol.getC_Order_ID());
	}

	// task 06727
	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_C_OrderLine.COLUMNNAME_PriceEntered,
			I_C_OrderLine.COLUMNNAME_Discount
	})
	public void setIsManual(final I_C_OrderLine olPO)
	{
		// urgent-no-taskname available yet: commenting out the code that sets IsManual because where that, attribute changes don't update the price anymore (checking for InterfaceWrapperHelper.isUI
		// doesn't help, because it actually *was* a UI action..the action just didn't change one of these two)
		// olPO.setIsManualPrice(true);
		// olPO.setIsManualDiscount(true);
	}

	/**
	 *
	 * @param orderLine
	 * @task http://dewiki908/mediawiki/index.php/09285_add_deliver_and_invoice_status_to_order_window
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
			I_C_OrderLine.COLUMNNAME_QtyOrdered,
			I_C_OrderLine.COLUMNNAME_QtyInvoiced,
			I_C_OrderLine.COLUMNNAME_QtyDelivered
	})
	public void updateQuantities(final I_C_OrderLine orderLine)
	{
		Services.get(IOrderBL.class).updateOrderQtySums(orderLine.getC_Order());
	}

	@CalloutMethod(columnNames = I_C_OrderLine.COLUMNNAME_GroupCompensationPercentage)
	public void onGroupCompensationPercentageChanged(final I_C_OrderLine orderLine)
	{
		groupChangesHandler.updateCompensationLineNoSave(orderLine);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
			I_C_OrderLine.COLUMNNAME_LineNetAmt,
			I_C_OrderLine.COLUMNNAME_GroupCompensationPercentage
	}, skipIfCopying = true)
	public void handleCompensantionGroupChange(final I_C_OrderLine orderLine)
	{
		groupChangesHandler.onOrderLineChanged(orderLine);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void handleCompensantionGroupDelete(final I_C_OrderLine orderLine)
	{
		groupChangesHandler.onOrderLineDeleted(orderLine);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_OrderLine.COLUMNNAME_M_DiscountSchemaBreak_ID)
	public void updateNoPriceConditionsColor(final I_C_OrderLine orderLine)
	{

		Services.get(IOrderLineBL.class).updateNoPriceConditionsColor(orderLine);

	}

}
