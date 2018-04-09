package org.eevolution.event;

import static de.metas.document.engine.IDocument.STATUS_Completed;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Product_BOMLine;
import org.eevolution.model.validator.PP_Order;
import org.eevolution.mrp.spi.impl.pporder.PPOrderProducer;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_M_Product;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderPojoConverter;

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

public class PPOrderRequestedEventHandlerTests
{
	private static final int PPORDER_POJO_GROUPID = 33;

	private I_PP_Product_Planning productPlanning;

	private I_AD_Org org;

	private I_M_Product bomMainProduct;

	private I_M_Product bomCoProduct;

	private I_M_Product bomComponentProduct;

	private I_M_Warehouse warehouse;

	private I_C_DocType docType;

	private PPOrder ppOrderPojo;

	private I_C_OrderLine orderLine;

	private PPOrderRequestedEventHandler ppOrderRequestedEventHandler;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_BPartner_ID(120);
		save(orderLine);

		final I_AD_Workflow workflow = newInstance(I_AD_Workflow.class);
		workflow.setIsValid(true);
		save(workflow);

		bomMainProduct = newInstance(I_M_Product.class);
		bomMainProduct.setIsVerified(true);
		save(bomMainProduct);

		final I_PP_Product_BOM productBom = newInstance(I_PP_Product_BOM.class);
		productBom.setM_Product_ID(bomMainProduct.getM_Product_ID());
		save(productBom);

		productPlanning = newInstance(I_PP_Product_Planning.class);
		productPlanning.setAD_Workflow(workflow);
		productPlanning.setPP_Product_BOM(productBom);
		productPlanning.setIsDocComplete(true);
		save(productPlanning);

		org = newInstance(I_AD_Org.class);
		save(org);

		warehouse = newInstance(I_M_Warehouse.class);
		save(warehouse);

		docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(X_C_DocType.DOCBASETYPE_ManufacturingOrder);
		save(docType);

		final I_PP_Product_BOMLine bomCoProductLine;
		{
			bomCoProduct = newInstance(I_M_Product.class);
			bomCoProduct.setIsVerified(true);
			save(bomCoProduct);

			bomCoProductLine = newInstance(I_PP_Product_BOMLine.class);
			bomCoProductLine.setComponentType(X_PP_Product_BOMLine.COMPONENTTYPE_Co_Product);
			bomCoProductLine.setPP_Product_BOM(productBom);
			bomCoProductLine.setM_Product(bomCoProduct);
			bomCoProductLine.setDescription("supposed to become the co-product line");
			save(bomCoProductLine);
		}
		final I_PP_Product_BOMLine bomComponentLine;
		{
			bomComponentProduct = newInstance(I_M_Product.class);
			bomComponentProduct.setIsVerified(true);
			save(bomComponentProduct);

			bomComponentLine = newInstance(I_PP_Product_BOMLine.class);
			bomComponentLine.setComponentType(X_PP_Product_BOMLine.COMPONENTTYPE_Component);
			bomComponentLine.setPP_Product_BOM(productBom);
			bomComponentLine.setM_Product(bomComponentProduct);
			bomComponentLine.setDescription("supposed to become the component line");
			save(bomComponentLine);
		}

		final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(bomMainProduct.getM_Product_ID(),
				AttributesKey.ofAttributeValueIds(12345),
				bomMainProduct.getM_AttributeSetInstance_ID());

		ppOrderPojo = PPOrder.builder()
				.materialDispoGroupId(PPORDER_POJO_GROUPID)
				.datePromised(SystemTime.asDate())
				.dateStartSchedule(SystemTime.asDate())
				.orgId(org.getAD_Org_ID())
				.plantId(110)
				.orderLineId(orderLine.getC_OrderLine_ID())
				.productDescriptor(productDescriptor)
				.productPlanningId(productPlanning.getPP_Product_Planning_ID())
				.qtyRequired(TEN)
				.qtyDelivered(ONE)
				.warehouseId(warehouse.getM_Warehouse_ID())
				.build();

		ppOrderRequestedEventHandler = new PPOrderRequestedEventHandler(new PPOrderProducer());
	}

	@Test
	public void testOnlyPPOrder()
	{
		final PPOrderRequestedEvent ppOrderRequestedEvent = PPOrderRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(0, 10))
				.dateOrdered(SystemTime.asDate())
				.ppOrder(ppOrderPojo).build();

		final I_PP_Order ppOrder = ppOrderRequestedEventHandler.createProductionOrder(ppOrderRequestedEvent);
		verifyPPOrder(ppOrder);
	}

	private void verifyPPOrder(final I_PP_Order ppOrder)
	{
		assertThat(ppOrder).isNotNull();
		assertThat(ppOrder.getAD_Org_ID()).isEqualTo(org.getAD_Org_ID());
		assertThat(ppOrder.getM_Product_ID()).isEqualTo(productPlanning.getPP_Product_BOM().getM_Product_ID());
		assertThat(ppOrder.getPP_Product_BOM_ID()).isEqualTo(productPlanning.getPP_Product_BOM_ID());
		assertThat(ppOrder.getPP_Product_Planning_ID()).isEqualTo(productPlanning.getPP_Product_Planning_ID());
		assertThat(ppOrder.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
		assertThat(ppOrder.getC_BPartner_ID()).isEqualTo(120);

		assertThat(ppOrder.getM_Product_ID()).isEqualTo(bomMainProduct.getM_Product_ID());
		assertThat(ppOrder.getM_Warehouse_ID()).isEqualTo(warehouse.getM_Warehouse_ID());
		assertThat(ppOrder.getC_DocType_ID()).isEqualTo(docType.getC_DocType_ID());
		assertThat(ppOrder.getAD_Workflow_ID()).isEqualTo(productPlanning.getAD_Workflow_ID());

		if (productPlanning.isDocComplete())
		{
			assertThat(ppOrder.getDocStatus()).isEqualTo(STATUS_Completed);
		}

		final Integer groupId = PPOrderPojoConverter.ATTR_PPORDER_REQUESTED_EVENT_GROUP_ID.getValue(ppOrder);
		assertThat(groupId).isEqualTo(PPORDER_POJO_GROUPID);
	}

	@Test
	public void testPPOrderWithLines()
	{
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new PP_Order(), null); // enable the MI supposed to supplement lines

		final PPOrderRequestedEvent ppOrderRequestedEvent = PPOrderRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(0, 10))
				.dateOrdered(SystemTime.asDate())
				.ppOrder(ppOrderPojo).build();

		final I_PP_Order ppOrder = ppOrderRequestedEventHandler
				.createProductionOrder(ppOrderRequestedEvent);

		verifyPPOrder(ppOrder);

		final List<I_PP_Order_BOMLine> orderBOMLines = Services.get(IPPOrderBOMDAO.class).retrieveOrderBOMLines(ppOrder);
		assertThat(orderBOMLines.isEmpty()).isFalse();
		assertThat(orderBOMLines).hasSize(2);

		assertThat(filter(ppOrder, X_PP_Order_BOMLine.COMPONENTTYPE_Component).size(), is(1));
		final I_PP_Order_BOMLine componentLine = filter(ppOrder, X_PP_Order_BOMLine.COMPONENTTYPE_Component).get(0);
		assertThat(componentLine.getDescription()).isEqualTo("supposed to become the component line");
		assertThat(componentLine.getM_Product_ID()).isEqualTo(bomComponentProduct.getM_Product_ID());

		assertThat(filter(ppOrder, X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product).size(), is(1));
		final I_PP_Order_BOMLine coProductLine = filter(ppOrder, X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product).get(0);
		assertThat(coProductLine.getDescription()).isEqualTo("supposed to become the co-product line");
		assertThat(coProductLine.getM_Product_ID()).isEqualTo(bomCoProduct.getM_Product_ID());
	}

	private List<I_PP_Order_BOMLine> filter(final I_PP_Order ppOrder, final String componenttypeComponent)
	{
		final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);

		final List<I_PP_Order_BOMLine> allBomLines = ppOrderBOMDAO.retrieveOrderBOMLines(ppOrder);

		return allBomLines.stream()
				.filter(l -> {
					return componenttypeComponent.equals(l.getComponentType());
				})
				.collect(Collectors.toList());
	}

}
