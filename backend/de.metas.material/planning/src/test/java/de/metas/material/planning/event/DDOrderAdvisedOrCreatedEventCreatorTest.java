package de.metas.material.planning.event;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_M_Product;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderAdvisedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ddorder.DDOrderAdvisedEventCreator;
import de.metas.material.planning.ddorder.DDOrderDemandMatcher;
import de.metas.material.planning.ddorder.DDOrderPojoSupplier;
import de.metas.organization.OrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static de.metas.material.event.EventTestHelper.createSupplyRequiredDescriptorWithProductId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

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

public class DDOrderAdvisedOrCreatedEventCreatorTest
{
	DDOrderDemandMatcher ddOrderDemandMatcher;
	DDOrderPojoSupplier ddOrderPojoSupplier;

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

		ddOrderDemandMatcher = Mockito.mock(DDOrderDemandMatcher.class);
		ddOrderPojoSupplier = Mockito.mock(DDOrderPojoSupplier.class);
	}

	@Test
	public void createProductionAdvisedEvents_returns_same_supplyRequiredDescriptor()
	{
		final IMutableMRPContext mrpContext = Mockito.mock(IMutableMRPContext.class);
		Mockito.when(mrpContext.getProductPlanning())
				.thenReturn(ProductPlanning.builder().build());

		Mockito.when(ddOrderDemandMatcher.matches(Mockito.any(IMaterialPlanningContext.class)))
				.thenReturn(true);

		Mockito.when(ddOrderPojoSupplier.supplyPojos(Mockito.any()))
				.thenReturn(ImmutableList.of(createDummyDDOrder()));

		final SupplyRequiredDescriptor supplyRequiredDescriptor = createSupplyRequiredDescriptorWithProductId(product.getM_Product_ID());

		final DDOrderAdvisedEventCreator productionAdvisedEventCreator = new DDOrderAdvisedEventCreator(ddOrderDemandMatcher, ddOrderPojoSupplier);
		final List<DDOrderAdvisedEvent> events = productionAdvisedEventCreator.createDDOrderAdvisedEvents(supplyRequiredDescriptor, mrpContext);

		assertThat(events).hasSize(1);
		assertThat(events.get(0).getSupplyRequiredDescriptor()).isSameAs(supplyRequiredDescriptor);
	}

	private DDOrder createDummyDDOrder()
	{
		final I_DD_NetworkDistributionLine networkDistributionLine = newInstance(I_DD_NetworkDistributionLine.class);
		networkDistributionLine.setM_WarehouseSource_ID(1);
		networkDistributionLine.setM_Warehouse_ID(2);
		saveRecord(networkDistributionLine);

		return DDOrder.builder()
				.orgId(OrgId.ofRepoId(1))
				.datePromised(Instant.now())
				.line(DDOrderLine.builder()
						.productDescriptor(ProductDescriptor.forProductAndAttributes(1, AttributesKey.ofString("1")))
						.qty(new BigDecimal("100"))
						.networkDistributionLineId(networkDistributionLine.getDD_NetworkDistributionLine_ID())
						.build())
				.build();
	}

}
