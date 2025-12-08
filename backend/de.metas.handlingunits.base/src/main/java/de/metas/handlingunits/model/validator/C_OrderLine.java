/**
 *
 */
package de.metas.handlingunits.model.validator;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.OrderLineHUPackingAware;
import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.IHUDocumentHandlerFactory;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.order.api.IHUOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.OrderLinePriceUpdateRequest.ResultUOM;
import de.metas.order.impl.OrderLineBL;
import de.metas.util.Services;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

/**
 * @author cg
 *
 */
@Validator(I_C_OrderLine.class)
public class C_OrderLine
{
	@Init
	public void setupCallouts()
	{
		final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);
		calloutProvider.registerAnnotatedCallout(new de.metas.handlingunits.callout.C_OrderLine());
	}

	@ModelChange(
			timings = {
					ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE
			}
			, ifColumnsChanged = {
			I_C_OrderLine.COLUMNNAME_C_BPartner_ID
			, I_C_OrderLine.COLUMNNAME_M_Product_ID
			, I_C_OrderLine.COLUMNNAME_QtyEntered
			, de.metas.handlingunits.model.I_C_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID
	})
	public void add_M_HU_PI_Item_Product(final I_C_OrderLine olPO)
	{
		// 09445: do not recompute price if also the price entered is changed
		if ((!InterfaceWrapperHelper.isNew(olPO) || InterfaceWrapperHelper.isCopy(olPO))
				&& InterfaceWrapperHelper.isValueChanged(olPO, I_C_OrderLine.COLUMNNAME_PriceEntered))
		{
			InterfaceWrapperHelper.setDynAttribute(olPO, OrderLineBL.DYNATTR_DoNotRecalculatePrices, Boolean.TRUE);
			return;
		}

		// avoid price recalculation in some specific cases (e.g. on reactivation)
		final Boolean doNotRecalculatePrices = InterfaceWrapperHelper.getDynAttribute(olPO, OrderLineBL.DYNATTR_DoNotRecalculatePrices);
		if (doNotRecalculatePrices != null && doNotRecalculatePrices)
		{
			return;
		}

		final IHUOrderBL huOrderBL = Services.get(IHUOrderBL.class);
		final IHUDocumentHandlerFactory huDocumentHandlerFactory = Services.get(IHUDocumentHandlerFactory.class);
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

		// We call with changedColumnName = null because we get the changed values from the object.
		huOrderBL.updateOrderLine(olPO, null);

		final IHUDocumentHandler handler = huDocumentHandlerFactory.createHandler(org.compiere.model.I_C_OrderLine.Table_Name);
		if (null != handler)
		{
			final de.metas.handlingunits.model.I_C_OrderLine olEx = InterfaceWrapperHelper.create(olPO, de.metas.handlingunits.model.I_C_OrderLine.class);
			handler.applyChangesFor(olEx);

			// First, we update the quantity packs (aka QtyTU)
			updateQtyPacks(olEx);

			// Finally, update prices
			orderLineBL.updatePrices(OrderLinePriceUpdateRequest.builder()
					.orderLine(olEx)
					.resultUOM(ResultUOM.PRICE_UOM_IF_ORDERLINE_IS_NEW)
					.updatePriceEnteredAndDiscountOnlyIfNotAlreadySet(true)
					.updateLineNetAmt(true)
					.build());
		}
	}

	private void updateQtyPacks(final de.metas.handlingunits.model.I_C_OrderLine orderLine)
	{
		final IHUPackingAware packingAware = new OrderLineHUPackingAware(orderLine);
		Services.get(IHUPackingAwareBL.class).setQtyTU(packingAware);
	}
}
