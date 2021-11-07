package de.metas.manufacturing.job;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.i18n.ITranslatableString;
import de.metas.manufacturing.order.PPOrderIssueSchedule;
import de.metas.manufacturing.order.PPOrderIssueScheduleRepository;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.OrderBOMLineQuantities;
import de.metas.material.planning.pporder.PPOrderQuantities;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

@Builder
public class ManufacturingJobLoaderSupportingServices
{
	@NonNull IWarehouseBL warehouseBL;
	@NonNull IProductBL productBL;
	@NonNull IPPOrderBL ppOrderBL;
	@NonNull IPPOrderBOMBL ppOrderBOMBL;
	@NonNull IPPOrderRoutingRepository ppOrderRoutingRepository;
	@NonNull PPOrderIssueScheduleRepository ppOrderIssueScheduleRepository;

	public String getLocatorName(@NonNull final LocatorId locatorId) {return warehouseBL.getLocatorNameById(locatorId);}
	public ITranslatableString getProductName(@NonNull final ProductId productId) {return productBL.getProductNameTrl(productId);}

	public I_PP_Order getPPOrderRecordById(@NonNull final PPOrderId ppOrderId) {return ppOrderBL.getById(ppOrderId);}

	public PPOrderRouting getOrderRouting(@NonNull final PPOrderId ppOrderId) {return ppOrderRoutingRepository.getByOrderId(ppOrderId);}

	public ImmutableList<I_PP_Order_BOMLine> getOrderBOMLines(@NonNull final PPOrderId ppOrderId) {return ImmutableList.copyOf(ppOrderBOMBL.retrieveOrderBOMLines(ppOrderId, I_PP_Order_BOMLine.class));}

	public PPOrderQuantities getQuantities(@NonNull final I_PP_Order order) {return ppOrderBOMBL.getQuantities(order);}

	public OrderBOMLineQuantities getQuantities(@NonNull final I_PP_Order_BOMLine orderBOMLine) {return ppOrderBOMBL.getQuantities(orderBOMLine);}

	public ImmutableListMultimap<PPOrderBOMLineId, PPOrderIssueSchedule> getIssueSchedules(@NonNull final PPOrderId ppOrderId)
	{
		return Multimaps.index(ppOrderIssueScheduleRepository.getByOrderId(ppOrderId), PPOrderIssueSchedule::getPpOrderBOMLineId);
	}
}
