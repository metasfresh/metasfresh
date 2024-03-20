package org.eevolution.callout;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.exception.MrpException;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPRoutingRepository;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.CalloutEngine;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.ProductBOMId;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.api.impl.ProductBOMVersionsDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Product_BOM;

import java.util.Optional;

/**
 * Manufacturing order callout
 *
 * @author metas-dev <dev@metasfresh.com>
 * @author based on initial version developed by Victor Perez, Teo Sarca under ADempiere project
 */
@Callout(I_PP_Order.class)
public class PP_Order extends CalloutEngine
{
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IProductBOMDAO bomsRepo = Services.get(IProductBOMDAO.class);
	private final ProductBOMVersionsDAO bomVersionsRepo;
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	private final IPPRoutingRepository routingRepo = Services.get(IPPRoutingRepository.class);
	private final IDocumentNoBuilderFactory documentNoBuilderFactory;

	public PP_Order(
			@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory,
			@NonNull final ProductBOMVersionsDAO bomVersionsRepo)
	{
		this.documentNoBuilderFactory = documentNoBuilderFactory;
		this.bomVersionsRepo = bomVersionsRepo;
	}

	/**
	 * When document type (target) is changed, update the DocumentNo.
	 */
	@CalloutMethod(columnNames = I_PP_Order.COLUMNNAME_C_DocTypeTarget_ID)
	public void onC_DocTypeTarget_ID(final I_PP_Order ppOrder)
	{
		final I_C_DocType docTypeTarget = DocTypeId.optionalOfRepoId(ppOrder.getC_DocTypeTarget_ID())
				.map(docTypeBL::getById)
				.orElse(null);

		final IDocumentNoInfo documentNoInfo = documentNoBuilderFactory
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(docTypeTarget)
				.setOldDocType_ID(ppOrder.getC_DocType_ID())
				.setOldDocumentNo(ppOrder.getDocumentNo())
				.setDocumentModel(ppOrder)
				.buildOrNull();
		if (documentNoInfo == null)
		{
			return;
		}

		// DocumentNo
		if (documentNoInfo.isDocNoControlled())
		{
			ppOrder.setDocumentNo(documentNoInfo.getDocumentNo());
		}
	}

	@CalloutMethod(columnNames = { I_PP_Order.COLUMNNAME_M_Product_ID, I_PP_Order.COLUMNNAME_C_DocTypeTarget_ID })
	public void onProductChanged(final I_PP_Order ppOrder)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(ppOrder.getM_Product_ID());
		if (productId == null)
		{
			return;
		}

		final UomId uomId = productBL.getStockUOMId(productId);
		ppOrder.setC_UOM_ID(uomId.getRepoId());

		final ProductPlanning pp = findPP_Product_Planning(ppOrder);
		ppOrder.setAD_Workflow_ID(PPRoutingId.toRepoId(pp.getWorkflowId()));

		final ProductBOMVersionsId productBOMVersionsId = Optional.ofNullable(pp.getBomVersionsId())
				.orElseThrow(() -> new MrpException("@FillMandatory@ @PP_Product_BOMVersions_ID@ ( @M_Product_ID@=" + productId.getRepoId() + ")"));

		final PPOrderDocBaseType docBaseType = ppOrderBL.getPPOrderDocBaseType(ppOrder);
		final ProductBOMId productBOMId = bomsRepo.getLatestBOMIdByVersionAndType(productBOMVersionsId, docBaseType.getBOMTypes())
				.orElseThrow(() -> new MrpException("@FillMandatory@ @PP_Product_BOM_ID@ ( @M_Product_ID@=" + productId.getRepoId() + ")"));

		ppOrder.setPP_Product_BOM_ID(productBOMId.getRepoId());

		final I_PP_Product_BOM bom = bomsRepo.getById(productBOMId);
		ppOrder.setC_UOM_ID(bom.getC_UOM_ID());

		updateQtyOrderedAndBatches(ppOrder);
	}

	@CalloutMethod(columnNames = I_PP_Order.COLUMNNAME_QtyEntered)
	public void onQtyEnteredChanged(final I_PP_Order ppOrder)
	{
		updateQtyOrderedAndBatches(ppOrder);
	}

	@CalloutMethod(columnNames = I_PP_Order.COLUMNNAME_C_UOM_ID)
	public void onC_UOM_ID(final I_PP_Order ppOrder)
	{
		updateQtyOrderedAndBatches(ppOrder);
	}

	@CalloutMethod(columnNames = I_PP_Order.COLUMNNAME_M_Warehouse_ID)
	public void onM_Warehouse_ID(final I_PP_Order ppOrder)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(ppOrder.getM_Warehouse_ID());
		if (warehouseId == null)
		{
			return;
		}

		final LocatorId locatorId = warehouseBL.getOrCreateDefaultLocatorId(warehouseId);
		ppOrder.setM_Locator_ID(locatorId.getRepoId());
	}

	/**
	 * Calculates and sets QtyOrdered from QtyEntered and UOM
	 */
	private void updateQtyOrderedAndBatches(@NonNull final I_PP_Order ppOrder)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(ppOrder.getM_Product_ID());
		if (productId == null)
		{
			return;
		}

		final UomId uomToId = UomId.ofRepoIdOrNull(ppOrder.getC_UOM_ID());
		if (uomToId == null)
		{
			return;
		}

		final I_C_UOM uom = uomDAO.getById(uomToId);
		final Quantity qtyEntered = Quantity.of(ppOrder.getQtyEntered(), uom);

		ppOrderBOMBL.changeQuantities(ppOrder, qtys -> qtys.withQtyRequiredToProduce(qtyEntered));
		ppOrderBL.updateQtyBatchs(ppOrder, true); // override
	}

	/**
	 * Find Product Planning Data for given manufacturing order. If not planning found, a new one is created and filled with default values.
	 */
	@NonNull
	private ProductPlanning findPP_Product_Planning(@NonNull final I_PP_Order ppOrderWithProductId)
	{
		final ProductPlanningQuery query = ProductPlanningQuery.builder()
				.orgId(OrgId.ofRepoIdOrAny(ppOrderWithProductId.getAD_Org_ID()))
				.warehouseId(WarehouseId.ofRepoIdOrNull(ppOrderWithProductId.getM_Warehouse_ID()))
				.plantId(ResourceId.ofRepoIdOrNull(ppOrderWithProductId.getS_Resource_ID()))
				.productId(ProductId.ofRepoId(ppOrderWithProductId.getM_Product_ID()))
				.includeWithNullProductId(false)
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(ppOrderWithProductId.getM_AttributeSetInstance_ID()))
				.build();
		final ProductPlanning productPlanningOrig = productPlanningDAO.find(query).orElse(null);

		final ProductPlanning.ProductPlanningBuilder builder;
		if (productPlanningOrig == null)
		{
			builder = ProductPlanning.builder()
					.orgId(OrgId.ofRepoId(ppOrderWithProductId.getAD_Org_ID()))
					.warehouseId(WarehouseId.ofRepoId(ppOrderWithProductId.getM_Warehouse_ID()))
					.plantId(ResourceId.ofRepoId(ppOrderWithProductId.getS_Resource_ID()))
					.productId(ProductId.ofRepoId(ppOrderWithProductId.getM_Product_ID()));
		}
		else
		{
			builder = productPlanningOrig.toBuilder();
		}
		builder.disallowSaving(true);

		final ProductId productId = ProductId.ofRepoId(ppOrderWithProductId.getM_Product_ID()); // pp itself might not have M_Product_ID>0, so we use the PP_Order's one
		if (productPlanningOrig == null || productPlanningOrig.getWorkflowId() == null)
		{
			final PPRoutingId routingId = routingRepo.getRoutingIdByProductId(productId);
			builder.workflowId(routingId);
		}

		if (productPlanningOrig == null || productPlanningOrig.getBomVersionsId() == null)
		{
			bomVersionsRepo.retrieveBOMVersionsId(productId).ifPresent(builder::bomVersionsId);
		}

		return builder.build();
	}
}
