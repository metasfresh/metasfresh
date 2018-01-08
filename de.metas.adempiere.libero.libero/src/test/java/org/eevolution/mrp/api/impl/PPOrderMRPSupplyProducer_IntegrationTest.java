package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import java.sql.Timestamp;

import org.adempiere.mm.attributes.api.impl.ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.X_C_DocType;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Product_Planning;
import org.eevolution.mrp.AbstractMRPTestBase;
import org.eevolution.mrp.spi.impl.PPOrderMRPSupplyProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import de.metas.order.compensationGroup.OrderGroupRepository;

/**
 * Integration test for {@link PPOrderMRPSupplyProducer} (it is tested indirectly).
 *
 * @author tsa
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class,
		ShutdownListener.class,
		ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory.class,

		PPOrderPojoConverter.class,

		OrderGroupRepository.class
})
public class PPOrderMRPSupplyProducer_IntegrationTest
		extends AbstractMRPTestBase
{
	@MockBean
	private PostMaterialEventService postMaterialEventService;

	// services
	private IPPOrderBOMDAO ppOrderBOMDAO;

	// Master data
	private MRPTestDataSimple masterData;

	@Override
	protected void afterInit()
	{
		masterData = new MRPTestDataSimple(helper);

		// Services
		this.ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
		this.docActionBL = Services.get(IDocumentBL.class);

		// Product Planning: pSalad_2xTomato_1xOnion
		helper.newProductPlanning()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.setIsManufactured(X_PP_Product_Planning.ISMANUFACTURED_Yes)
				.setPP_Product_BOM(masterData.pSalad_2xTomato_1xOnion_BOM)
				.setAD_Workflow(masterData.workflow_Standard)
				.setDeliveryTime_Promised(1)
				.build();
		// Product Planning: pTomato
		helper.newProductPlanning()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pTomato)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(masterData.ddNetwork)
				.build();
		// Product Planning: pOnion
		helper.newProductPlanning()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pOnion)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(masterData.ddNetwork)
				.build();

		// MRP error exception: we don't care for BOM product "Salad_2xTomato_1xOnion"
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Product(masterData.pSalad_2xTomato_1xOnion);
		// MRP error exception: we don't care about balancing raw materials warehouses
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setMRPCode(MRPExecutor.MRP_ERROR_120_NoProductPlanning)
				.setM_Warehouse(masterData.warehouse_rawMaterials01);
	}

	/**
	 * Test case:
	 * <ul>
	 * <li>create a manufacturing order
	 * <li>assert initial MRP demand quantity
	 * <li>do an over-receipt on manufacturing order
	 * <li>assert MRP demand does not become negative
	 * </ul>
	 */
	@Test
	public void test_OverReceipt()
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class, helper.contextProvider);
		ppOrder.setAD_Org(masterData.adOrg01);

		setCommonProperties(ppOrder);

		ppOrder.setM_Product(masterData.pSalad_2xTomato_1xOnion);
		ppOrder.setPP_Product_BOM(masterData.pSalad_2xTomato_1xOnion_BOM);
		ppOrder.setAD_Workflow(masterData.workflow_Standard);
		ppOrder.setM_Warehouse(masterData.warehouse_plant01);
		ppOrder.setS_Resource(masterData.plant01);
		ppOrder.setQtyOrdered(new BigDecimal("100"));
		ppOrder.setDatePromised(helper.getToday());
		ppOrder.setDocStatus(IDocument.STATUS_Drafted);
		ppOrder.setDocAction(IDocument.ACTION_Complete);
		ppOrder.setDescription("Triggering manufacturing order");

		InterfaceWrapperHelper.save(ppOrder);

		// Check initial Demand
		helper.newMRPExpectation()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(BigDecimal.ZERO)
				.qtySupply("100")
				.assertExpected();

		// Simulate an over-receipt
		ppOrder.setQtyDelivered(new BigDecimal("110"));
		InterfaceWrapperHelper.save(ppOrder);

		// Check demand after over-receipt
		// Make sure we don't get a negative Demand
		helper.newMRPExpectation()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(BigDecimal.ZERO)
				.qtySupply(BigDecimal.ZERO)
				.assertExpected();
	}

	private void setCommonProperties(final I_PP_Order ppOrder)
	{
		Services.get(IPPOrderBL.class).setDocType(ppOrder, X_C_DocType.DOCBASETYPE_ManufacturingOrder, null);

		// required to avoid an NPE when building the lightweight PPOrder pojo
		final Timestamp t1 = SystemTime.asTimestamp();
		ppOrder.setDateOrdered(t1);
		ppOrder.setDateStartSchedule(t1);
	}

	/**
	 * Test case:
	 * <ul>
	 * <li>create a manufacturing order
	 * <li>assert initial MRP supply quantity
	 * <li>do an over-issue on manufacturing order's BOM line
	 * <li>assert MRP supply does not become negative
	 * </ul>
	 */
	@Test
	public void test_OverIssue()
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class, helper.contextProvider);
		ppOrder.setAD_Org(masterData.adOrg01);

		setCommonProperties(ppOrder);

		ppOrder.setM_Product(masterData.pSalad_2xTomato_1xOnion);
		ppOrder.setPP_Product_BOM(masterData.pSalad_2xTomato_1xOnion_BOM);
		ppOrder.setAD_Workflow(masterData.workflow_Standard);
		ppOrder.setM_Warehouse(masterData.warehouse_plant01);
		ppOrder.setS_Resource(masterData.plant01);
		ppOrder.setQtyOrdered(new BigDecimal("100"));
		ppOrder.setDatePromised(helper.getToday());
		ppOrder.setDocStatus(IDocument.STATUS_Drafted);
		ppOrder.setDocAction(IDocument.ACTION_Complete);
		ppOrder.setDescription("Triggering manufacturing order");
		InterfaceWrapperHelper.save(ppOrder);

		// Check initial Demand
		helper.newMRPExpectation()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pTomato)
				.addPPOrderToInclude(ppOrder)
				.qtyDemand(200)
				.qtySupply(0)
				.assertExpected();

		// Simulate an over-issue
		final I_PP_Order_BOMLine ppOrderBOMLine_Tomato = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, masterData.pTomato);
		ppOrderBOMLine_Tomato.setQtyDelivered(new BigDecimal("300"));
		InterfaceWrapperHelper.save(ppOrderBOMLine_Tomato);

		// Check demand after over-receipt
		// Make sure we don't get a negative Demand
		helper.newMRPExpectation()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pTomato)
				.addPPOrderToInclude(ppOrder)
				.qtyDemand(0)
				.qtySupply(0)
				.assertExpected();
	}
}
