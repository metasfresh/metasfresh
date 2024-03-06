package de.metas.manufacturing.job.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.i18n.ITranslatableString;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.OrderBOMLineQuantities;
import de.metas.material.planning.pporder.PPOrderQuantities;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Builder
public class ManufacturingJobLoaderAndSaverSupportingServices
{
	@NonNull IOrgDAO orgDAO;
	@NonNull IWarehouseBL warehouseBL;
	@NonNull IProductBL productBL;
	@NonNull IAttributeDAO attributeDAO;
	@NonNull IHUPPOrderBL ppOrderBL;
	@NonNull IPPOrderBOMBL ppOrderBOMBL;
	@NonNull final IHandlingUnitsBL handlingUnitsBL;
	@NonNull IPPOrderRoutingRepository ppOrderRoutingRepository;
	@NonNull PPOrderIssueScheduleService ppOrderIssueScheduleService;
	@NonNull HUQRCodesService huQRCodeService;

	public ZoneId getTimeZone(final OrgId orgId) {return orgDAO.getTimeZone(orgId);}

	public String getLocatorName(@NonNull final LocatorId locatorId) {return warehouseBL.getLocatorNameById(locatorId);}

	public ITranslatableString getProductName(@NonNull final ProductId productId) {return productBL.getProductNameTrl(productId);}

	public I_PP_Order getPPOrderRecordById(@NonNull final PPOrderId ppOrderId) {return ppOrderBL.getById(ppOrderId);}

	public PPOrderRouting getOrderRouting(@NonNull final PPOrderId ppOrderId) {return ppOrderRoutingRepository.getByOrderId(ppOrderId);}

	public void saveOrderRouting(@NonNull final PPOrderRouting routing) {ppOrderRoutingRepository.save(routing);}

	public ImmutableList<I_PP_Order_BOMLine> getOrderBOMLines(@NonNull final PPOrderId ppOrderId) {return ImmutableList.copyOf(ppOrderBOMBL.retrieveOrderBOMLines(ppOrderId, I_PP_Order_BOMLine.class));}

	public ZonedDateTime getDatePromised(final I_PP_Order ppOrder)
	{
		return InstantAndOrgId.ofTimestamp(ppOrder.getDatePromised(), ppOrder.getAD_Org_ID()).toZonedDateTime(orgDAO::getTimeZone);
	}

	public PPOrderQuantities getQuantities(@NonNull final I_PP_Order order) {return ppOrderBOMBL.getQuantities(order);}

	public OrderBOMLineQuantities getQuantities(@NonNull final I_PP_Order_BOMLine orderBOMLine) {return ppOrderBOMBL.getQuantities(orderBOMLine);}

	public ImmutableListMultimap<PPOrderBOMLineId, PPOrderIssueSchedule> getIssueSchedules(@NonNull final PPOrderId ppOrderId)
	{
		return Multimaps.index(ppOrderIssueScheduleService.getByOrderId(ppOrderId), PPOrderIssueSchedule::getPpOrderBOMLineId);
	}

	public ImmutableAttributeSet getImmutableAttributeSet(final AttributeSetInstanceId asiId)
	{
		return attributeDAO.getImmutableAttributeSetById(asiId);
	}

	public HUQRCode getQRCodeByHuId(@NonNull final HuId huId)
	{
		return huQRCodeService.getQRCodeByHuId(huId);
	}

	public Optional<HuId> getHuIdByQRCodeIfExists(@NonNull final HUQRCode qrCode)
	{
		return huQRCodeService.getHuIdByQRCodeIfExists(qrCode);
	}

	public void assignQRCode(@NonNull HUQRCode qrCode, @NonNull HuId huId)
	{
		huQRCodeService.assign(qrCode, huId);
	}

	public Quantity getHUCapacity(
			@NonNull final HuId huId,
			@NonNull final ProductId productId,
			@NonNull final I_C_UOM uom)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		return handlingUnitsBL
				.getStorageFactory()
				.getStorage(hu)
				.getQuantity(productId, uom);
	}
}
