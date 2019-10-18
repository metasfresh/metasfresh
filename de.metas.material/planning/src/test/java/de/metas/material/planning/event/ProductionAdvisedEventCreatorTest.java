package de.metas.material.planning.event;

import static de.metas.material.event.EventTestHelper.createSupplyRequiredDescriptorWithProductId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.eevolution.model.I_PP_Product_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.metas.adempiere.model.I_M_Product;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderAdvisedEvent;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMaterialRequest;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.pporder.PPOrderAdvisedEventCreator;
import de.metas.material.planning.pporder.PPOrderDemandMatcher;
import de.metas.material.planning.pporder.PPOrderPojoSupplier;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ResourceId;

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

public class ProductionAdvisedEventCreatorTest
{
	PPOrderDemandMatcher ppOrderDemandMatcher;
	PPOrderPojoSupplier ppOrderPojoSupplier;

	private I_M_Product product;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);

		product = newInstance(I_M_Product.class);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(product);

		ppOrderDemandMatcher = Mockito.mock(PPOrderDemandMatcher.class);
		ppOrderPojoSupplier = Mockito.mock(PPOrderPojoSupplier.class);
	}

	@Test
	public void createProductionAdvisedEvents_returns_same_supplyRequiredDescriptor()
	{
		final IMutableMRPContext mrpContext = Mockito.mock(IMutableMRPContext.class);
		Mockito.when(mrpContext.getProductPlanning())
				.thenReturn(newInstance(I_PP_Product_Planning.class));

		Mockito.when(ppOrderDemandMatcher.matches(Mockito.any(IMaterialPlanningContext.class)))
				.thenReturn(true);

		Mockito.when(ppOrderPojoSupplier.supplyPPOrderPojoWithLines(Mockito.any(IMaterialRequest.class)))
				.thenReturn(createDummyPPOrder());

		final SupplyRequiredDescriptor supplyRequiredDescriptor = createSupplyRequiredDescriptorWithProductId(product.getM_Product_ID());

		final PPOrderAdvisedEventCreator pPOrderAdvisedCreator = new PPOrderAdvisedEventCreator(ppOrderDemandMatcher, ppOrderPojoSupplier);
		final List<PPOrderAdvisedEvent> events = pPOrderAdvisedCreator.createPPOrderAdvisedEvents(supplyRequiredDescriptor, mrpContext);
		assertThat(events).hasSize(1);
		assertThat(events.get(0).getSupplyRequiredDescriptor()).isSameAs(supplyRequiredDescriptor);
	}

	private static PPOrder createDummyPPOrder()
	{
		return PPOrder.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 2))
				.plantId(ResourceId.ofRepoId(1))
				.warehouseId(WarehouseId.ofRepoId(1))
				.productDescriptor(ProductDescriptor.forProductAndAttributes(1, AttributesKey.ofString("1")))
				.datePromised(Instant.now())
				.dateStartSchedule(Instant.now())
				.qtyRequired(new BigDecimal("100"))
				.build();
	}
}
