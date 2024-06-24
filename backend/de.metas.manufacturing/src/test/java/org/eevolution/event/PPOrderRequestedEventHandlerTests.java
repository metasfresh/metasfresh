package org.eevolution.event;

import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_Product;
import de.metas.common.util.time.SystemTime;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoBuilderFactory;
import de.metas.event.impl.PlainEventBusFactory;
import de.metas.material.event.MaterialEventObserver;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.eventbus.MaterialEventConverter;
import de.metas.material.event.eventbus.MetasfreshEventBusService;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.material.planning.pporder.impl.PPOrderBOMBL;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.mm.attributes.api.impl.ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_AD_Workflow;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.ProductBOMId;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.api.impl.ProductBOMVersionsDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.eevolution.model.X_PP_Product_BOM;
import org.eevolution.model.validator.PP_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.metas.document.engine.IDocument.STATUS_Completed;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2017 metas GmbH
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
@ExtendWith(AdempiereTestWatcher.class)
public class PPOrderRequestedEventHandlerTests
{

	private static final MaterialDispoGroupId PPORDER_POJO_GROUPID = MaterialDispoGroupId.ofInt(33);

	private ProductPlanning productPlanning;

	private final ClientId adClientId = ClientId.ofRepoId(123);
	private OrgId orgId;

	private I_M_Product bomMainProduct;

	private I_M_Product bomCoProduct;

	private I_M_Product bomComponentProduct;

	private I_M_Warehouse warehouse;

	private I_C_DocType docType;

	private PPOrder ppOrderPojo;

	private I_C_OrderLine orderLine;

	private PPOrderRequestedEventHandler ppOrderRequestedEventHandler;
	private IProductBOMDAO productBOMsRepo;
	private IPPOrderBOMDAO ppOrderBOMDAO;
	private IModelInterceptorRegistry modelInterceptorRegistry;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
		this.productBOMsRepo = Services.get(IProductBOMDAO.class);
		this.ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
		this.modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);

		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Client_ID, adClientId.getRepoId());

		SpringContextHolder.registerJUnitBean(new ProductBOMVersionsDAO());

		final I_C_Order order = newInstance(I_C_Order.class);
		save(order);

		orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setC_BPartner_ID(120);
		save(orderLine);

		final PPRoutingId routingId = createRouting();

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		save(uom);

		bomMainProduct = newInstance(I_M_Product.class);
		bomMainProduct.setIsVerified(true);
		bomMainProduct.setC_UOM_ID(uom.getC_UOM_ID());
		save(bomMainProduct);

		final I_PP_Product_BOMVersions productBomVersions = newInstance(I_PP_Product_BOMVersions.class);
		productBomVersions.setM_Product_ID(bomMainProduct.getM_Product_ID());
		productBomVersions.setName("Name");
		save(productBomVersions);

		final I_PP_Product_BOM productBom = newInstance(I_PP_Product_BOM.class);
		productBom.setM_Product_ID(bomMainProduct.getM_Product_ID());
		productBom.setC_UOM_ID(uom.getC_UOM_ID());
		productBom.setValidFrom(TimeUtil.asTimestamp(Instant.now().minus(1, ChronoUnit.HOURS)));
		productBom.setDocStatus(X_PP_Product_BOM.DOCSTATUS_Completed);
		productBom.setPP_Product_BOMVersions_ID(productBomVersions.getPP_Product_BOMVersions_ID());
		save(productBom);

		productPlanning = productPlanningDAO.save(ProductPlanning.builder()
				.workflowId(routingId)
				.bomVersionsId(ProductBOMVersionsId.ofRepoId(productBomVersions.getPP_Product_BOMVersions_ID()))
				.isDocComplete(true)
				.build());

		final I_AD_Org orgRecord = newInstance(I_AD_Org.class);
		save(orgRecord);
		orgId = OrgId.ofRepoId(orgRecord.getAD_Org_ID());

		warehouse = newInstance(I_M_Warehouse.class);
		save(warehouse);

		docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(PPOrderDocBaseType.MANUFACTURING_ORDER.getCode());
		save(docType);

		final I_PP_Product_BOMLine bomCoProductLine;
		{
			bomCoProduct = newInstance(I_M_Product.class);
			bomCoProduct.setIsVerified(true);
			bomCoProduct.setC_UOM_ID(uom.getC_UOM_ID());
			save(bomCoProduct);

			bomCoProductLine = newInstance(I_PP_Product_BOMLine.class);
			bomCoProductLine.setComponentType(BOMComponentType.CoProduct.getCode());
			bomCoProductLine.setPP_Product_BOM(productBom);
			bomCoProductLine.setM_Product_ID(bomCoProduct.getM_Product_ID());
			bomCoProductLine.setDescription("supposed to become the co-product line");
			bomCoProductLine.setC_UOM_ID(uom.getC_UOM_ID());
			save(bomCoProductLine);
		}
		final I_PP_Product_BOMLine bomComponentLine;
		{
			bomComponentProduct = newInstance(I_M_Product.class);
			bomComponentProduct.setIsVerified(true);
			bomComponentProduct.setC_UOM_ID(uom.getC_UOM_ID());
			save(bomComponentProduct);

			bomComponentLine = newInstance(I_PP_Product_BOMLine.class);
			bomComponentLine.setComponentType(BOMComponentType.Component.getCode());
			bomComponentLine.setPP_Product_BOM(productBom);
			bomComponentLine.setM_Product_ID(bomComponentProduct.getM_Product_ID());
			bomComponentLine.setDescription("supposed to become the component line");
			bomComponentLine.setC_UOM_ID(uom.getC_UOM_ID());
			save(bomComponentLine);
		}

		final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(bomMainProduct.getM_Product_ID(),
				AttributesKey.ofAttributeValueIds(12345),
				bomMainProduct.getM_AttributeSetInstance_ID());

		ppOrderPojo = PPOrder.builder()
				.ppOrderData(PPOrderData.builder()
						.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(adClientId, orgId))
						.materialDispoGroupId(PPORDER_POJO_GROUPID)
						.datePromised(SystemTime.asInstant())
						.dateStartSchedule(SystemTime.asInstant())
						.plantId(ResourceId.ofRepoId(110))
						.workstationId(ResourceId.ofRepoId(112))
						.orderLineId(orderLine.getC_OrderLine_ID())
						.productDescriptor(productDescriptor)
						.productPlanningId(productPlanning.getIdNotNull().getRepoId())
						.qtyRequired(TEN)
						.qtyDelivered(ONE)
						.warehouseId(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()))
						.build())
				.build();

		ppOrderRequestedEventHandler = new PPOrderRequestedEventHandler();
	}

	private PPRoutingId createRouting()
	{
		final I_AD_Workflow routingRecord = newInstance(I_AD_Workflow.class);
		routingRecord.setValue("dummy");
		routingRecord.setName("dummy");
		routingRecord.setIsValid(true);
		routingRecord.setDurationUnit(X_AD_Workflow.DURATIONUNIT_Hour);
		save(routingRecord);

		final I_AD_WF_Node activityRecord = newInstance(I_AD_WF_Node.class);
		activityRecord.setAD_Workflow_ID(routingRecord.getAD_Workflow_ID());
		activityRecord.setValue("dummy");
		activityRecord.setName("dummy");
		activityRecord.setS_Resource_ID(createResource().getRepoId());
		save(activityRecord);

		routingRecord.setAD_WF_Node_ID(activityRecord.getAD_WF_Node_ID());
		save(routingRecord);

		return PPRoutingId.ofRepoId(routingRecord.getAD_Workflow_ID());
	}

	private ResourceId createResource()
	{
		final I_S_Resource resource = newInstance(I_S_Resource.class);
		saveRecord(resource);

		return ResourceId.ofRepoId(resource.getS_Resource_ID());
	}

	@Test
	public void testOnlyPPOrder()
	{
		final PPOrderRequestedEvent ppOrderRequestedEvent = PPOrderRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(0, 10))
				.dateOrdered(SystemTime.asInstant())
				.ppOrder(ppOrderPojo)
				.build();

		final I_PP_Order ppOrder = ppOrderRequestedEventHandler.createProductionOrder(ppOrderRequestedEvent);
		verifyPPOrder(ppOrder);
	}

	private void verifyPPOrder(final I_PP_Order ppOrder)
	{
		assertThat(ppOrder).isNotNull();
		assertThat(ppOrder.getAD_Org_ID()).isEqualTo(orgId.getRepoId());

		final ProductBOMVersionsId productBOMVersionsId = productPlanning.getBomVersionsId();
		final ProductBOMId productBOMId = productBOMsRepo.getLatestBOMByVersion(productBOMVersionsId).orElse(null);

		assertThat(ppOrder.getPP_Product_BOM_ID()).isEqualTo(ProductBOMId.toRepoId(productBOMId));

		final I_PP_Product_BOM productBOM = productBOMsRepo.getById(ProductBOMId.ofRepoId(ppOrder.getPP_Product_BOM_ID()));
		assertThat(ppOrder.getM_Product_ID()).isEqualTo(productBOM.getM_Product_ID());

		assertThat(ppOrder.getPP_Product_Planning_ID()).isEqualTo(productPlanning.getIdNotNull().getRepoId());
		assertThat(ppOrder.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
		assertThat(ppOrder.getC_BPartner_ID()).isEqualTo(120);

		assertThat(ppOrder.getM_Product_ID()).isEqualTo(bomMainProduct.getM_Product_ID());
		assertThat(ppOrder.getM_Warehouse_ID()).isEqualTo(warehouse.getM_Warehouse_ID());
		assertThat(ppOrder.getC_DocType_ID()).isEqualTo(docType.getC_DocType_ID());
		assertThat(PPRoutingId.ofRepoIdOrNull(ppOrder.getAD_Workflow_ID())).isEqualTo(productPlanning.getWorkflowId());

		assertThat(ResourceId.ofRepoId(ppOrder.getS_Resource_ID())).isEqualTo(ppOrderPojo.getPpOrderData().getPlantId());
		assertThat(ResourceId.ofRepoIdOrNull(ppOrder.getWorkStation_ID())).isEqualTo(ppOrderPojo.getPpOrderData().getWorkstationId());

		if (productPlanning.isDocComplete())
		{
			assertThat(ppOrder.getDocStatus()).isEqualTo(STATUS_Completed);
		}

		final MaterialDispoGroupId groupId = PPOrderPojoConverter.getMaterialDispoGroupIdOrNull(ppOrder);
		assertThat(groupId).isEqualTo(PPORDER_POJO_GROUPID);
	}

	@Test
	public void testPPOrderWithLines()
	{
		registerPP_Order_Interceptor();  // enable the MI supposed to supplement lines

		final PPOrderRequestedEvent ppOrderRequestedEvent = PPOrderRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(0, 10))
				.dateOrdered(SystemTime.asInstant())
				.ppOrder(ppOrderPojo)
				.build();

		final I_PP_Order ppOrder = ppOrderRequestedEventHandler.createProductionOrder(ppOrderRequestedEvent);
		verifyPPOrder(ppOrder);

		final List<I_PP_Order_BOMLine> orderBOMLines = ppOrderBOMDAO.retrieveOrderBOMLines(ppOrder);
		assertThat(orderBOMLines).hasSize(2);

		assertThat(filter(ppOrder, BOMComponentType.Component)).hasSize(1);
		final I_PP_Order_BOMLine componentLine = filter(ppOrder, BOMComponentType.Component).get(0);
		assertThat(componentLine.getDescription()).isEqualTo("supposed to become the component line");
		assertThat(componentLine.getM_Product_ID()).isEqualTo(bomComponentProduct.getM_Product_ID());

		assertThat(filter(ppOrder, BOMComponentType.CoProduct)).hasSize(1);
		final I_PP_Order_BOMLine coProductLine = filter(ppOrder, BOMComponentType.CoProduct).get(0);
		assertThat(coProductLine.getDescription()).isEqualTo("supposed to become the co-product line");
		assertThat(coProductLine.getM_Product_ID()).isEqualTo(bomCoProduct.getM_Product_ID());
	}

	private void registerPP_Order_Interceptor()
	{
		SpringContextHolder.registerJUnitBean(IDocumentNoBuilderFactory.class, new DocumentNoBuilderFactory(Optional.empty()));

		final ModelProductDescriptorExtractor productDescriptorFactory = new ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory();
		final ReplenishInfoRepository replenishInfoRepository = new ReplenishInfoRepository();
		final PPOrderPojoConverter ppOrderConverter = new PPOrderPojoConverter(productDescriptorFactory, replenishInfoRepository);

		final MaterialEventConverter materialEventConverter = new MaterialEventConverter();
		final MetasfreshEventBusService materialEventService = MetasfreshEventBusService.createLocalServiceThatIsReadyToUse(
				materialEventConverter,
				PlainEventBusFactory.newInstance(),
				new MaterialEventObserver());
		final PostMaterialEventService postMaterialEventService = new PostMaterialEventService(materialEventService);

		modelInterceptorRegistry.addModelInterceptor(new PP_Order(
				ppOrderConverter,
				postMaterialEventService,
				new DocumentNoBuilderFactory(Optional.empty()),
				new PPOrderBOMBL(),
				new ProductBOMVersionsDAO()));
	}

	private List<I_PP_Order_BOMLine> filter(final I_PP_Order ppOrder, final BOMComponentType componentType)
	{
		final List<I_PP_Order_BOMLine> allBomLines = ppOrderBOMDAO.retrieveOrderBOMLines(ppOrder);

		return allBomLines.stream()
				.filter(l -> componentType.equals(BOMComponentType.ofCode(l.getComponentType())))
				.collect(Collectors.toList());
	}

}
