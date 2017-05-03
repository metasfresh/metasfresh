package de.metas.material.planning.pporder.impl;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.X_PP_Order_BOMLine;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.event.EventDescr;
import de.metas.material.event.ProductionOrderRequested;
import de.metas.material.event.ProductionPlanEvent;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.planning.pporder.PPOrderPojoConverter;

/*
 * #%L
 * metasfresh-material-planning
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

public class PPOrderPojoConverterTests
{
	private final Date t0 = SystemTime.asDate();
	private final Date t1 = TimeUtil.addMinutes(t0, 10);
	private final Date t2 = TimeUtil.addMinutes(t0, 20);
	private final Date t3 = TimeUtil.addMinutes(t0, 30);
	private final Date t4 = TimeUtil.addMinutes(t0, 40);

	private I_M_Product endProduct;
	private I_C_UOM uom;

	private I_M_Product componentProduct;

	private I_M_Product coProduct;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);

		endProduct = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		endProduct.setC_UOM(uom);
		InterfaceWrapperHelper.save(endProduct);

		componentProduct = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		componentProduct.setC_UOM(uom);
		InterfaceWrapperHelper.save(componentProduct);

		coProduct = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		coProduct.setC_UOM(uom);
		InterfaceWrapperHelper.save(coProduct);
	}

	@Test
	public void testPProductBOMStuff()
	{
		final I_PP_Product_BOM ppProductBom = InterfaceWrapperHelper.newInstance(I_PP_Product_BOM.class);

		InterfaceWrapperHelper.save(ppProductBom);
	}

	private void createProductBomLine()
	{
		final I_PP_Product_BOMLine ppProductBomLine = InterfaceWrapperHelper.newInstance(I_PP_Product_BOMLine.class);

		ppProductBomLine.setAssay(BigDecimal.valueOf(20));
		ppProductBomLine.setBackflushGroup("backflushGroup");
		ppProductBomLine.setComponentType(X_PP_Order_BOMLine.COMPONENTTYPE_Component);
		ppProductBomLine.setIsCritical(true);
		ppProductBomLine.setForecast(BigDecimal.valueOf(30));
		ppProductBomLine.setHelp("help");
		ppProductBomLine.setIssueMethod("issueMethod");
		ppProductBomLine.setLeadTimeOffset(40);
		ppProductBomLine.setQtyBatch(BigDecimal.valueOf(50));
		ppProductBomLine.setQtyBOM(BigDecimal.valueOf(60));
		ppProductBomLine.setIsQtyPercentage(true);
		ppProductBomLine.setScrap(BigDecimal.valueOf(80));
		ppProductBomLine.setC_UOM_ID(uom.getC_UOM_ID());
		ppProductBomLine.setValidFrom(new Timestamp(t3.getTime()));
		ppProductBomLine.setValidTo(new Timestamp(t4.getTime()));
		ppProductBomLine.setVariantGroup("variantGroup");
		ppProductBomLine.setM_ChangeNotice_ID(120);

		InterfaceWrapperHelper.save(ppProductBomLine);
	}

	@Test
	public void test()
	{
		final PPOrderLine componentLinePojo = PPOrderLine.builder()
				.productBomLineId(100)
				.attributeSetInstanceId(110)
				.description("description")
				.qtyRequired(BigDecimal.valueOf(70))
				.productId(componentProduct.getM_Product_ID())
				.receipt(false)
				.build();

		final PPOrder ppOrderPojo = PPOrder.builder()
				.orgId(20)
				.datePromised(t1)
				.dateStartSchedule(t2)
				.productPlanningId(30)
				.plantId(40)
				.productId(endProduct.getM_Product_ID())
				.quantity(BigDecimal.TEN)
				.uomId(uom.getC_UOM_ID())
				.warehouseId(90)
				.line(componentLinePojo)
				.line(componentLinePojo
						.withProductId(coProduct.getM_Product_ID())
						.withReceipt(true)) // this means that we have *two* outputs
				.build();

		final PPOrderPojoConverter ppOrderPojoConverter = new PPOrderPojoConverter();
		final ProductionPlanEvent productionPlanEvent = ppOrderPojoConverter.asProductionPlanEvent(ppOrderPojo, TableRecordReference.of("sometable", 23));

		assertThat(productionPlanEvent, notNullValue());

		final ProductionOrderRequested productionOrderEvent = ProductionOrderRequested.builder()
				.eventDescr(new EventDescr())
				.ppOrder(productionPlanEvent.getPpOrder())
				.reference(productionPlanEvent.getReference())
				.build();

		final PPOrder ppOrderPojoAfterConversion = ppOrderPojoConverter.asPPOrderPojo(productionOrderEvent);
		assertThat(ppOrderPojoAfterConversion, notNullValue());

		assertThat(ppOrderPojoAfterConversion, is(ppOrderPojo));
	}
}
