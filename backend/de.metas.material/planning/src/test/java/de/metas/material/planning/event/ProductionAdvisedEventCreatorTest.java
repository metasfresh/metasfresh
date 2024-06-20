package de.metas.material.planning.event;

import de.metas.adempiere.model.I_M_Product;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.PPOrderCandidate;
import de.metas.material.event.pporder.PPOrderCandidateAdvisedEvent;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMaterialRequest;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.pporder.PPOrderCandidateDemandMatcher;
import de.metas.material.planning.ppordercandidate.PPOrderCandidateAdvisedEventCreator;
import de.metas.material.planning.ppordercandidate.PPOrderCandidatePojoSupplier;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ResourceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
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

public class ProductionAdvisedEventCreatorTest
{
	PPOrderCandidateDemandMatcher ppOrderCandidateDemandMatcher;
	PPOrderCandidatePojoSupplier ppOrderCandidatePojoSupplier;

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

		ppOrderCandidateDemandMatcher = Mockito.mock(PPOrderCandidateDemandMatcher.class);
		ppOrderCandidatePojoSupplier = Mockito.mock(PPOrderCandidatePojoSupplier.class);
	}

	@Test
	public void createProductionAdvisedEvents_returns_same_supplyRequiredDescriptor()
	{
		final IMutableMRPContext mrpContext = Mockito.mock(IMutableMRPContext.class);
		Mockito.when(mrpContext.getProductPlanning())
				.thenReturn(ProductPlanning.builder().build());

		Mockito.when(ppOrderCandidateDemandMatcher.matches(Mockito.any(IMaterialPlanningContext.class)))
				.thenReturn(true);

		Mockito.when(ppOrderCandidatePojoSupplier.supplyPPOrderCandidatePojoWithoutLines(Mockito.any(IMaterialRequest.class)))
				.thenReturn(createDummyPPOrderCandidate());

		final SupplyRequiredDescriptor supplyRequiredDescriptor = createSupplyRequiredDescriptorWithProductId(product.getM_Product_ID());

		final PPOrderCandidateAdvisedEventCreator pPOrderCandidateAdvisedCreator = new PPOrderCandidateAdvisedEventCreator(ppOrderCandidateDemandMatcher, ppOrderCandidatePojoSupplier);
		final List<PPOrderCandidateAdvisedEvent> events = pPOrderCandidateAdvisedCreator.createPPOrderCandidateAdvisedEvents(supplyRequiredDescriptor, mrpContext);
		assertThat(events).hasSize(1);
		assertThat(events.get(0).getSupplyRequiredDescriptor()).isSameAs(supplyRequiredDescriptor);
	}

	private static PPOrderCandidate createDummyPPOrderCandidate()
	{
		return PPOrderCandidate.builder()
				.ppOrderData(PPOrderData.builder()
									 .clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 2))
									 .plantId(ResourceId.ofRepoId(1))
									 .warehouseId(WarehouseId.ofRepoId(1))
									 .productDescriptor(ProductDescriptor.forProductAndAttributes(1, AttributesKey.ofString("1")))
									 .datePromised(Instant.now())
									 .dateStartSchedule(Instant.now())
									 .qtyRequired(new BigDecimal("100"))
									 .build())
				.build();
	}
}
