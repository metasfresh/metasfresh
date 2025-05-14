/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.material.planning.ppordercandidate;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_M_Product;
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.PPOrderCandidate;
import de.metas.material.event.pporder.PPOrderCandidateAdvisedEvent;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.event.MaterialRequest;
import de.metas.material.planning.pporder.PPOrderCandidateDemandMatcher;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.createSupplyRequiredDescriptorWithProductId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class PPOrderCandidateAdvisedEventCreatorTest
{
	private UomId uomId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uomId = UomId.ofRepoId(BusinessTestHelper.createUOM("uom").getC_UOM_ID());
	}

	@SuppressWarnings("SameParameterValue")
	private MaterialRequest materialRequest(final String qtyToSupply)
	{
		return MaterialRequest.builder()
				.context(newDummyMaterialPlanningContext())
				.qtyToSupply(qty(qtyToSupply))
				.demandDate(SystemTime.asInstant())
				.build();
	}

	private static MaterialPlanningContext newDummyMaterialPlanningContext()
	{
		return MaterialPlanningContext.builder()
				.productId(ProductId.ofRepoId(1))
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.warehouseId(WarehouseId.MAIN)
				.productPlanning(ProductPlanning.builder().build())
				.plantId(ResourceId.ofRepoId(2))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(3, 4))
				.build();
	}

	private Quantity qty(final String value)
	{
		return Quantitys.of(value, uomId);
	}

	@Nested
	class createMaterialRequests
	{
		@Test
		void total31_15perOrder()
		{
			final ImmutableList<MaterialRequest> materialRequests = PPOrderCandidateAdvisedEventCreator.createMaterialRequests(
					materialRequest("31"),
					qty("15")
			);

			assertThat(materialRequests)
					.extracting(l -> l.getQtyToSupply().toBigDecimal().toString())
					.containsExactly("15", "15", "1");
		}

		@Test
		void total31_null_perOrder()
		{
			final ImmutableList<MaterialRequest> materialRequests = PPOrderCandidateAdvisedEventCreator.createMaterialRequests(
					materialRequest("31"),
					null
			);

			assertThat(materialRequests)
					.extracting(l -> l.getQtyToSupply().toBigDecimal().toString())
					.containsExactly("31");
		}

		@Test
		void total31_31perOrder()
		{
			final ImmutableList<MaterialRequest> materialRequests = PPOrderCandidateAdvisedEventCreator.createMaterialRequests(
					materialRequest("31"),
					qty("31")
			);

			assertThat(materialRequests)
					.extracting(l -> l.getQtyToSupply().toBigDecimal().toString())
					.containsExactly("31");
		}

		@Test
		void total31_32perOrder()
		{
			final ImmutableList<MaterialRequest> materialRequests = PPOrderCandidateAdvisedEventCreator.createMaterialRequests(
					materialRequest("31"),
					qty("32")
			);

			assertThat(materialRequests)
					.extracting(l -> l.getQtyToSupply().toBigDecimal().toString())
					.containsExactly("31");
		}

		@Test
		void total31_0perOrder()
		{
			final ImmutableList<MaterialRequest> materialRequests = PPOrderCandidateAdvisedEventCreator.createMaterialRequests(
					materialRequest("31"),
					qty("0")
			);

			assertThat(materialRequests)
					.extracting(l -> l.getQtyToSupply().toBigDecimal().toString())
					.containsExactly("31");
		}
	}

	@Nested
	class createAdvisedEvents
	{
		PPOrderCandidateDemandMatcher ppOrderCandidateDemandMatcher;
		PPOrderCandidatePojoSupplier ppOrderCandidatePojoSupplier;
		private PPOrderCandidateAdvisedEventCreator ppOrderCandidateAdvisedCreator;

		I_M_Product product;

		@BeforeEach
		void beforeEach()
		{
			ppOrderCandidateDemandMatcher = Mockito.mock(PPOrderCandidateDemandMatcher.class);
			ppOrderCandidatePojoSupplier = Mockito.mock(PPOrderCandidatePojoSupplier.class);
			ppOrderCandidateAdvisedCreator = new PPOrderCandidateAdvisedEventCreator(ppOrderCandidateDemandMatcher, ppOrderCandidatePojoSupplier);

			product = newInstance(I_M_Product.class);
			product.setC_UOM_ID(uomId.getRepoId());
			saveRecord(product);
		}

		private PPOrderCandidate newDummyPPOrderCandidate()
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

		@Test
		public void returns_same_supplyRequiredDescriptor()
		{
			final MaterialPlanningContext context = MaterialPlanningContext.builder()
					.productId(ProductId.ofRepoId(1))
					.attributeSetInstanceId(AttributeSetInstanceId.NONE)
					.warehouseId(WarehouseId.MAIN)
					.productPlanning(ProductPlanning.builder().build())
					.plantId(ResourceId.ofRepoId(2))
					.clientAndOrgId(CLIENT_AND_ORG_ID)
					.build();

			Mockito.when(ppOrderCandidateDemandMatcher.matches(Mockito.any(MaterialPlanningContext.class))).thenReturn(true);
			Mockito.when(ppOrderCandidatePojoSupplier.supplyPPOrderCandidatePojoWithoutLines(Mockito.any(MaterialRequest.class))).thenReturn(newDummyPPOrderCandidate());

			final SupplyRequiredDescriptor supplyRequiredDescriptor = createSupplyRequiredDescriptorWithProductId(product.getM_Product_ID());

			final List<PPOrderCandidateAdvisedEvent> events = ppOrderCandidateAdvisedCreator.createAdvisedEvents(supplyRequiredDescriptor, context);
			assertThat(events).hasSize(1);
			assertThat(events.get(0).getSupplyRequiredDescriptor()).isSameAs(supplyRequiredDescriptor);
		}
	}
}