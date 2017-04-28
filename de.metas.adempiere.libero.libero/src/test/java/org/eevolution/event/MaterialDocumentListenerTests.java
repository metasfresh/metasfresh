package org.eevolution.event;

import java.math.BigDecimal;

import org.adempiere.util.time.SystemTime;
import org.eevolution.model.I_PP_Order;
import org.eevolution.mrp.spi.impl.pporder.PPOrderProducer;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderLine;

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

public class MaterialDocumentListenerTests
{
	@Test
	public void test()
	{
		final PPOrder ppOrderPojo = PPOrder.builder()
				.datePromised(SystemTime.asDate())
				.dateStartSchedule(SystemTime.asDate())
				.orgId(100)
				.plantId(110)
				.productId(120)
				.productPlanningId(130)
				.quantity(BigDecimal.TEN)
				.uomId(140)
				.warehouseId(150)
				.warehouseId(160)
				.line(PPOrderLine.builder()
						.attributeSetInstanceId(270)
						.description("desc1")
						.productBomLineId(280)
						.productId(290)
						.qtyRequired(BigDecimal.valueOf(220))
						.receipt(true)
						.build())
				.line(PPOrderLine.builder()
						.attributeSetInstanceId(370)
						.description("desc2")
						.productBomLineId(380)
						.productId(390)
						.qtyRequired(BigDecimal.valueOf(320))
						.receipt(false)
						.build())
				.build();

		final I_PP_Order ppOrder = new MaterialDocumentListener(new PPOrderProducer())
				.createProductionOrder(ppOrderPojo, SystemTime.asDate());
		assertThat(ppOrder, notNullValue());

	}
}
