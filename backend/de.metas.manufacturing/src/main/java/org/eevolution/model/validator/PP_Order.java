package org.eevolution.model.validator;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import de.metas.order.IOrderBL;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.ProductBOMId;
import org.eevolution.api.impl.ProductBOMVersionsDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_Order;

import java.sql.Timestamp;

@Interceptor(I_PP_Order.class)
public class PP_Order
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IPPOrderRoutingRepository ppOrderRoutingRepository = Services.get(IPPOrderRoutingRepository.class);
	private final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	private final IPPOrderCostBL orderCostsService = Services.get(IPPOrderCostBL.class);
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);

	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	private final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);
	private final PPOrderPojoConverter ppOrderConverter;
	private final PostMaterialEventService materialEventService;
	private final IDocumentNoBuilderFactory documentNoBuilderFactory;
	private final IPPOrderBOMBL ppOrderBOMBL;
	private final ProductBOMVersionsDAO bomVersionsDAO;

	public PP_Order(
			@NonNull final PPOrderPojoConverter ppOrderConverter,
			@NonNull final PostMaterialEventService materialEventService,
			@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory,
			@NonNull final IPPOrderBOMBL ppOrderBOMBL,
			@NonNull final ProductBOMVersionsDAO bomVersionsDAO)
	{
		this.ppOrderConverter = ppOrderConverter;
		this.materialEventService = materialEventService;
		this.documentNoBuilderFactory = documentNoBuilderFactory;
		this.ppOrderBOMBL = ppOrderBOMBL;
		this.bomVersionsDAO = bomVersionsDAO;
	}

	@Init
	public void registerCallouts()
	{
		final IProgramaticCalloutProvider calloutsRegistry = Services.get(IProgramaticCalloutProvider.class);
		calloutsRegistry.registerAnnotatedCallout(new org.eevolution.callout.PP_Order(documentNoBuilderFactory, bomVersionsDAO));

		final ITabCalloutFactory tabCalloutRegistry = Services.get(ITabCalloutFactory.class);
		tabCalloutRegistry.registerTabCalloutForTable(I_PP_Order.Table_Name, org.eevolution.callout.PP_Order_TabCallout.class);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(
			final I_PP_Order ppOrder,
			final ModelChangeType changeType)
	{
		//
		// If UOM not filled, get it from Product
		if (ppOrder.getC_UOM_ID() <= 0)
		{
			final ProductId productId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
			final UomId uomId = productBL.getStockUOMId(productId);
			ppOrder.setC_UOM_ID(uomId.getRepoId());
		}

		//
		// If DateFinishSchedule is not filled, use DatePromised
		if (ppOrder.getDateFinishSchedule() == null)
		{
			ppOrder.setDateFinishSchedule(ppOrder.getDatePromised());
		}

		//
		// If Warehouse changed or Locator was never set, set it now
		if (ppOrder.getM_Locator_ID() <= 0 || InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_M_Warehouse_ID))
		{
			final WarehouseId warehouseId = WarehouseId.ofRepoId(ppOrder.getM_Warehouse_ID());
			final LocatorId locatorId = warehouseBL.getDefaultLocatorId(warehouseId);
			ppOrder.setM_Locator_ID(locatorId.getRepoId());
		}

		//
		// Set QtyBatchSize and QtyBatchs
		ppOrderBL.updateQtyBatchs(ppOrder, false);

		//
		// Set BPartner if not set
		if (ppOrder.getC_OrderLine_ID() > 0 && ppOrder.getC_BPartner_ID() <= 0)
		{
			final int bpartnerId = ppOrder.getC_OrderLine().getC_BPartner_ID();
			ppOrder.setC_BPartner_ID(bpartnerId);
		}

		//
		// Set PreparationDate from linked Sales Order, if not set (08181)
		if (ppOrder.getPreparationDate() == null && !ppOrder.isProcessed() && ppOrder.getC_OrderLine_ID() > 0)
		{
			final Timestamp preparationDate = ppOrder.getC_OrderLine().getC_Order().getPreparationDate();
			ppOrder.setPreparationDate(preparationDate);
		}

		//
		// Set ASI from OrderLine if not set (08074)
		if (ppOrder.getM_AttributeSetInstance_ID() <= 0
				&& ppOrder.getC_OrderLine_ID() > 0
				&& ppOrder.getC_OrderLine().getM_AttributeSetInstance_ID() > 0)
		{
			final I_M_AttributeSetInstance asi = ppOrder.getC_OrderLine().getM_AttributeSetInstance();
			final I_M_AttributeSetInstance asiCopy = attributeDAO.copy(asi);
			ppOrder.setM_AttributeSetInstance(asiCopy);
		}

		//
		// Set project from OrderLine if not set
		if ((changeType.isNew() || InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_C_OrderLine_ID))
				&& ppOrder.getC_OrderLine_ID() > 0)
		{
			final OrderLineId orderLineId = OrderLineId.ofRepoId(ppOrder.getC_OrderLine_ID());
			final ProjectId projectId = orderBL.getProjectIdOrNull(orderLineId);
			ppOrder.setC_Project_ID(ProjectId.toRepoId(projectId));
		}

		//
		// Warehouse/Locator changed => update Order BOM Lines
		if (InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_M_Warehouse_ID)
				|| InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_M_Locator_ID)
				|| InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_AD_Org_ID))
		{
			if (ppOrder.getPP_Order_ID() > 0)
			{
				ppOrderBL.updateBOMOrderLinesWarehouseAndLocator(ppOrder);
			}
		}

		//
		// DocTypeTarget:
		if (ppOrder.getC_DocTypeTarget_ID() <= 0)
		{
			throw new FillMandatoryException(I_PP_Order.COLUMNNAME_C_DocTypeTarget_ID);
		}

		//
		// DocType: OrderType
		if (changeType.isNew()
				|| InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_C_DocType_ID))
		{
			final I_C_DocType docType = DocTypeId.optionalOfRepoId(ppOrder.getC_DocType_ID())
					.map(docTypeBL::getById)
					.orElse(null);
			if (docType != null)
			{
				ppOrder.setOrderType(docType.getDocSubType());
				ppOrder.setDocBaseType(docType.getDocBaseType());
			}
			else
			{
				ppOrder.setOrderType(null);
				ppOrder.setDocBaseType(null);
			}
		}

		if (changeType.isNew() || InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_CanBeExportedFrom))
		{
			ppOrderBL.updateCanBeExportedAfter(ppOrder);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void createWorkFlowAndBom(final I_PP_Order ppOrderRecord)
	{
		createWorkflowAndBOM(ppOrderRecord);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = { I_PP_Order.COLUMNNAME_QtyEntered, I_PP_Order.COLUMNNAME_AD_Workflow_ID })
	public void updateAndPostEventOnQtyEnteredChange(final I_PP_Order ppOrderRecord)
	{
		if (ppOrderBL.isSomethingProcessed(ppOrderRecord))
		{
			throw new LiberoException("Cannot quantity is not allowed because there is something already processed on this order"); // TODO: trl
		}

		final PPOrderChangedEventFactory eventFactory = PPOrderChangedEventFactory.newWithPPOrderBeforeChange(ppOrderConverter, ppOrderRecord);

		final PPOrderId orderId = PPOrderId.ofRepoId(ppOrderRecord.getPP_Order_ID());
		deleteWorkflowAndBOM(orderId);
		createWorkflowAndBOM(ppOrderRecord);

		final PPOrderChangedEvent event = eventFactory.inspectPPOrderAfterChange();

		materialEventService.postEventAfterNextCommit(event);
	}

	private void deleteWorkflowAndBOM(final PPOrderId orderId)
	{
		ppOrderRoutingRepository.deleteByOrderId(orderId);
		ppOrderBOMDAO.deleteByOrderId(orderId);
	}

	private void createWorkflowAndBOM(final I_PP_Order ppOrder)
	{
		ppOrderBL.createOrderRouting(ppOrder);
		ppOrderBOMBL.createOrderBOMAndLines(ppOrder);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(@NonNull final I_PP_Order ppOrder)
	{
		//
		// Delete depending records
		final String docStatus = ppOrder.getDocStatus();
		if (X_PP_Order.DOCSTATUS_Drafted.equals(docStatus)
				|| X_PP_Order.DOCSTATUS_InProgress.equals(docStatus))
		{
			final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());

			orderCostsService.deleteByOrderId(ppOrderId);
			deleteWorkflowAndBOM(ppOrderId);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW },
			ifColumnsChanged = { I_PP_Order.COLUMNNAME_M_Product_ID, I_PP_Order.COLUMNNAME_PP_Product_BOM_ID })
	public void validateBOMAndProduct(@NonNull final I_PP_Order ppOrder)
	{
		final ProductBOMId bomId = ProductBOMId.ofRepoId(ppOrder.getPP_Product_BOM_ID());

		final ProductId productIdOfBOM = productBOMDAO.getBOMProductId(bomId);

		if (ppOrder.getM_Product_ID() != productIdOfBOM.getRepoId())
		{
			throw new AdempiereException(AdMessageKey.of("PP_Order_BOM_Doesnt_Match"))
					.markAsUserValidationError()
					.appendParametersToMessage()
					.setParameter("PP_Order.M_Product_ID", ppOrder.getM_Product_ID())
					.setParameter("PP_Order.PP_Product_BOM_ID.M_Product_ID", productIdOfBOM.getRepoId());
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void setSeqNo(final I_PP_Order ppOrder)
	{
		if (ppOrder.getSeqNo() <= 0)
		{
			ppOrder.setSeqNo(ppOrderDAO.getLastSeqNoPerOrderDate(ppOrder));
		}
	}
}
