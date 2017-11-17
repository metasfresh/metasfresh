package org.eevolution.model.validator;

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_PP_Order;

import de.metas.material.event.MaterialEventService;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrder.DDOrderBuilder;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.ddorder.DistributionAdvisedEvent;
import de.metas.material.planning.ddorder.DDOrderUtil;

/**
 * A dedicated model interceptor whose job it is to fire events on the {@link MaterialEventService}.<br>
 * I add this into a dedicated interceptor (as opposed to adding the method to {@link DD_Order}) because there is at least one test case where I want {@link PP_Order} to be invoked without events being fired.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Interceptor(I_DD_Order.class)
public class DD_OrderFireMaterialEvent
{

	@ModelChange(timings =
		{
				ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE
		}, ifColumnsChanged = I_PP_Order.COLUMNNAME_DocStatus)
	public void fireMaterialEvent(final I_DD_Order ddOrder, final int timing)
	{
		// when going with @DocAction, here the ppOrder's docStatus would still be "IP" even if we are invoked on afterComplete..
		// also, it might still be rolled back
		// those aren't show-stoppers, but we therefore rather work with @ModelChange
		final DDOrderBuilder ddOrderPojoBuilder = DDOrder.builder()
				.datePromised(ddOrder.getDatePromised())
				.ddOrderId(ddOrder.getDD_Order_ID())
				.docStatus(ddOrder.getDocStatus())
				.orgId(ddOrder.getAD_Org_ID())
				.plantId(ddOrder.getPP_Plant_ID())
				.productPlanningId(ddOrder.getPP_Product_Planning_ID())
				.shipperId(ddOrder.getM_Shipper_ID());

		final ModelProductDescriptorExtractor productDescriptorFactory = Adempiere.getBean(ModelProductDescriptorExtractor.class);

		final List<I_DD_OrderLine> ddOrderLines = Services.get(IDDOrderDAO.class).retrieveLines(ddOrder);
		for (final I_DD_OrderLine line : ddOrderLines)
		{
			final int durationDays = DDOrderUtil.calculateDurationDays(ddOrder.getPP_Product_Planning(), line.getDD_NetworkDistributionLine());

			ddOrderPojoBuilder
					.line(DDOrderLine.builder()
							.productDescriptor(productDescriptorFactory.createProductDescriptor(line))
							.ddOrderLineId(line.getDD_OrderLine_ID())
							.qty(line.getQtyDelivered())
							.networkDistributionLineId(line.getDD_NetworkDistributionLine_ID())
							.salesOrderLineId(line.getC_OrderLineSO_ID())
							.durationDays(durationDays)
							.build());

			final DistributionAdvisedEvent event = DistributionAdvisedEvent.builder()
					.eventDescriptor(EventDescriptor.createNew(ddOrder))
					.ddOrder(ddOrderPojoBuilder.build())
					.fromWarehouseId(line.getM_Locator().getM_Warehouse_ID())
					.toWarehouseId(line.getM_LocatorTo().getM_Warehouse_ID())
					.build();

			final MaterialEventService materialEventService = Adempiere.getBean(MaterialEventService.class);
			materialEventService.fireEventAfterNextCommit(event, InterfaceWrapperHelper.getTrxName(ddOrder));
		}
	}

}
