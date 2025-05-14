package de.metas.frontend_testing.masterdata.dd_order;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.lowlevel.model.DDOrderLineHUPackingAware;
import de.metas.distribution.workflows_api.DDOrderReference;
import de.metas.distribution.workflows_api.DDOrderReferenceCollector;
import de.metas.distribution.workflows_api.DistributionLauncherCaptionProvider;
import de.metas.distribution.workflows_api.facets.DistributionFacetId;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.IHUDocumentHandlerFactory;
import de.metas.handlingunits.QtyTU;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Builder
public class DDOrderCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IHUDocumentHandlerFactory huDocumentHandlerFactory = Services.get(IHUDocumentHandlerFactory.class);
	@NonNull private final IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final DDOrderService ddOrderService;

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonDDOrderRequest request;
	@NonNull private final Identifier identifier;
	@NonNull private final OrgId orgId = MasterdataContext.ORG_ID;

	public JsonDDOrderResponse execute()
	{
		return trxManager.callInThreadInheritedTrx(this::execute0);
	}

	private JsonDDOrderResponse execute0()
	{
		final BPartnerId bpartnerId = bpartnerOrgBL.retrieveLinkedBPartnerId(orgId).orElse(null);
		final BPartnerLocationId bpartnerLocationId = bpartnerId != null
				? bpartnerDAO.retrieveBPartnerLocationId(BPartnerLocationQuery.builder().bpartnerId(bpartnerId).type(BPartnerLocationQuery.Type.SHIP_TO).build())
				: null;

		final WarehouseId fromWarehouseId = context.getId(request.getWarehouseFrom(), WarehouseId.class);
		final WarehouseId toWarehouseId = context.getId(request.getWarehouseTo(), WarehouseId.class);
		final WarehouseId transitWarehouseId = context.getId(request.getWarehouseInTransit(), WarehouseId.class);

		final ResourceId plantId = request.getPlant() != null
				? context.getId(request.getPlant(), ResourceId.class)
				: MasterdataContext.DEFAULT_PLANT_ID;

		final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
		ddOrder.setAD_Org_ID(orgId.getRepoId());
		ddOrder.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		ddOrder.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(bpartnerLocationId));
		ddOrder.setM_Warehouse_From_ID(fromWarehouseId.getRepoId());
		ddOrder.setM_Warehouse_To_ID(toWarehouseId.getRepoId());
		ddOrder.setM_Warehouse_ID(transitWarehouseId.getRepoId());
		ddOrder.setPP_Plant_ID(plantId.getRepoId());
		ddOrder.setIsInDispute(false);
		ddOrder.setIsSOTrx(false);
		ddOrder.setIsInTransit(false);
		ddOrder.setDeliveryRule(X_DD_Order.DELIVERYRULE_Availability);

		final DocTypeId docTypeId = findDocTypeId();
		ddOrder.setC_DocType_ID(docTypeId.getRepoId());
		ddOrderService.save(ddOrder);

		request.getLines().forEach(line -> createLine(line, ddOrder));

		InterfaceWrapperHelper.refresh(ddOrder);
		ddOrder.setDocAction(IDocument.ACTION_Complete); // we need this because otherwise MOrder.completeIt() won't complete it
		documentBL.processEx(ddOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		final DDOrderReference ddOrderReference = toDDOrderReference(ddOrder);
		return JsonDDOrderResponse.builder()
				.documentNo(ddOrder.getDocumentNo())
				.launcherCaption(computeLauncherCaption(ddOrderReference).translate(context.getAdLanguage()))
				.launcherTestId(ddOrderReference.getTestId())
				.warehouseFromFacetId(DistributionFacetId.ofWarehouseFromId(fromWarehouseId).toWorkflowLaunchersFacetId().toJsonString())
				.warehouseToFacetId(DistributionFacetId.ofWarehouseFromId(toWarehouseId).toWorkflowLaunchersFacetId().toJsonString())
				.plantFacetId(DistributionFacetId.ofPlantId(plantId).toWorkflowLaunchersFacetId().toJsonString())
				.build();
	}

	private void createLine(final JsonDDOrderRequest.Line line, final I_DD_Order ddOrder)
	{
		final ProductId productId = context.getId(line.getProduct(), ProductId.class);
		final WarehouseId fromWarehouseId = context.getId(request.getWarehouseFrom(), WarehouseId.class);
		final LocatorId fromLocatorId = warehouseBL.getOrCreateDefaultLocatorId(fromWarehouseId);
		final WarehouseId toWarehouseId = context.getId(request.getWarehouseTo(), WarehouseId.class);
		final LocatorId toLocatorId = warehouseBL.getOrCreateDefaultLocatorId(toWarehouseId);
		final BigDecimal qtyEntered = line.getQtyEntered();

		final I_DD_OrderLine ddOrderLine = newInstance(I_DD_OrderLine.class);
		ddOrderLine.setDD_Order_ID(ddOrder.getDD_Order_ID());
		ddOrderLine.setAD_Org_ID(ddOrder.getAD_Org_ID());
		ddOrderLine.setM_Product_ID(productId.getRepoId());
		ddOrderLine.setQtyEntered(qtyEntered);
		ddOrderLine.setQtyOrdered(qtyEntered);
		ddOrderLine.setM_Locator_ID(fromLocatorId.getRepoId());
		ddOrderLine.setM_LocatorTo_ID(toLocatorId.getRepoId());
		ddOrderLine.setIsInvoiced(false);
		saveRecord(ddOrderLine);

		final IHUDocumentHandler handler = huDocumentHandlerFactory.createHandler(I_DD_OrderLine.Table_Name);
		handler.applyChangesFor(ddOrderLine);

		final IHUPackingAware packingAware = new DDOrderLineHUPackingAware(InterfaceWrapperHelper.create(ddOrderLine, de.metas.distribution.ddorder.lowlevel.model.I_DD_OrderLine.class));
		huPackingAwareBL.setQtyTU(packingAware);

		final QtyTU qtyPacks = QtyTU.ofBigDecimal(packingAware.getQtyTU());
		huPackingAwareBL.setQtyCUFromQtyTU(packingAware, qtyPacks.toInt());

		saveRecord(ddOrderLine);
	}

	private DocTypeId findDocTypeId()
	{
		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.DistributionOrder)
				.clientAndOrgId(MasterdataContext.CLIENT_ID, orgId)
				.build());
	}

	private DDOrderReference toDDOrderReference(final I_DD_Order ddOrder)
	{
		final DDOrderReferenceCollector collector = DDOrderReferenceCollector.builder()
				.ddOrderService(ddOrderService)
				.build();
		collector.collect(ddOrder, false);
		return CollectionUtils.singleElement(collector.getCollectedItems());
	}

	private static WorkflowLauncherCaption computeLauncherCaption(final DDOrderReference ddOrderReference)
	{
		final DistributionLauncherCaptionProvider captionProvider = new DistributionLauncherCaptionProvider();
		return captionProvider.compute(ddOrderReference);
	}

}
