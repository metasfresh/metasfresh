package de.metas.material.dispo.commons;

import de.metas.document.dimension.DimensionService;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.CandidatesGroup;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.ddordercandidate.DDOrderCandidateData;
import de.metas.material.event.ddordercandidate.DDOrderCandidateRequestedEvent;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.order.OrderLineRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.shipping.ShipperId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptorWithProductId;
import static de.metas.material.event.EventTestHelper.newMaterialDescriptor;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-material-dispo
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

public class CandidateServiceTests
{
	private RequestMaterialOrderService requestMaterialOrderService;
	private ProductId productId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final DimensionService dimensionService = Mockito.mock(DimensionService.class);
		final PostMaterialEventService postMaterialEventService = Mockito.mock(PostMaterialEventService.class);
		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();
		requestMaterialOrderService = new RequestMaterialOrderService(
				new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo),
				postMaterialEventService,
				new OrderLineRepository());

		createMasterData();
	}

	private void createMasterData()
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);

		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		InterfaceWrapperHelper.save(product);
		this.productId = ProductId.ofRepoId(product.getM_Product_ID());
	}

	@Test
	public void testcreatePPOrderRequestEvent()
	{
		final Candidate candidate = Candidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(20, 30))
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.materialDescriptor(newMaterialDescriptor())
				.businessCaseDetail(ProductionDetail.builder()
						.plantId(ResourceId.ofRepoId(210))
						.productPlanningId(220)
						.advised(Flag.FALSE)
						.pickDirectlyIfFeasible(Flag.FALSE)
						.qty(TEN)
						.build())
				.build();

		final Candidate candidate2 = candidate
				.withType(CandidateType.DEMAND)
				.withMaterialDescriptor(candidate.getMaterialDescriptor()
						.withProductDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(310))
						.withQuantity(BigDecimal.valueOf(20)))
				.withDimension(candidate.getDimension())
				.withBusinessCaseDetail(ProductionDetail.builder()
						.plantId(ResourceId.ofRepoId(210))
						.productPlanningId(220)
						.productBomLineId(500)
						.advised(Flag.TRUE)
						.pickDirectlyIfFeasible(Flag.FALSE)
						.qty(TEN)
						.build());

		final Candidate candidate3 = candidate
				.withType(CandidateType.DEMAND)
				.withMaterialDescriptor(candidate.getMaterialDescriptor()
						.withProductDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(320))
						.withQuantity(BigDecimal.valueOf(10)))
				.withDimension(candidate.getDimension())
				.withBusinessCaseDetail(ProductionDetail.builder()
						.plantId(ResourceId.ofRepoId(210))
						.productPlanningId(220)
						.productBomLineId(600)
						.advised(Flag.FALSE)
						.pickDirectlyIfFeasible(Flag.TRUE)
						.qty(TEN)
						.build());

		final PPOrderRequestedEvent ppOrderRequestedEvent = requestMaterialOrderService
				.createPPOrderRequestedEvent(CandidatesGroup.of(candidate, candidate2, candidate3));

		assertThat(ppOrderRequestedEvent).isNotNull();
		assertThat(ppOrderRequestedEvent.getClientId().getRepoId()).isEqualTo(20);
		assertThat(ppOrderRequestedEvent.getOrgId().getRepoId()).isEqualTo(30);

		final PPOrder ppOrder = ppOrderRequestedEvent.getPpOrder();
		assertThat(ppOrder).isNotNull();
		assertThat(ppOrder.getPpOrderData().getClientAndOrgId().getOrgId().getRepoId()).isEqualTo(30);
		assertThat(ppOrder.getPpOrderData().getProductDescriptor().getProductId()).isEqualTo(PRODUCT_ID);

		assertThat(ppOrder.getLines()).hasSize(2);
	}

	@Test
	public void testcreateDDOrderRequestEvent()
	{
		final Candidate supplyCandidate = Candidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(20, 30))
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.DISTRIBUTION)
				.materialDescriptor(createMaterialDescriptorWithProductId(productId.getRepoId()))
				.businessCaseDetail(DistributionDetail.builder()
						.productPlanningId(ProductPlanningId.ofRepoId(220))
						.plantId(ResourceId.ofRepoId(230))
						.shipperId(ShipperId.ofRepoId(240))
						.qty(TEN)
						.build())
				.build();

		final Candidate demandCandidate = supplyCandidate
				.withType(CandidateType.DEMAND)
				.withMaterialDescriptor(supplyCandidate.getMaterialDescriptor()
						.withProductDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(productId.getRepoId()))
						.withQuantity(BigDecimal.valueOf(20)))
				.withDimension(supplyCandidate.getDimension())
				.withBusinessCaseDetail(DistributionDetail.builder()
						.productPlanningId(ProductPlanningId.ofRepoId(220))
						.plantId(ResourceId.ofRepoId(230))
						.shipperId(ShipperId.ofRepoId(240))
						.distributionNetworkAndLineId(DistributionNetworkAndLineId.ofRepoIds(500, 500))
						.qty(TEN)
						.build());

		final DDOrderCandidateRequestedEvent distributionOrderEvent = requestMaterialOrderService.createDDOrderCandidateRequestedEvent(CandidatesGroup.of(supplyCandidate, demandCandidate), null);
		assertThat(distributionOrderEvent).isNotNull();

		assertThat(distributionOrderEvent.getClientId().getRepoId()).isEqualTo(20);
		assertThat(distributionOrderEvent.getOrgId().getRepoId()).isEqualTo(30);

		final DDOrderCandidateData ddOrderCandidateData = distributionOrderEvent.getDdOrderCandidateData();
		assertThat(ddOrderCandidateData).isNotNull();
		assertThat(ddOrderCandidateData.getOrgId().getRepoId()).isEqualTo(30);
		assertThat(ddOrderCandidateData.getProductPlanningId().getRepoId()).isEqualTo(220);
		assertThat(ddOrderCandidateData.getTargetPlantId().getRepoId()).isEqualTo(230);
		assertThat(ddOrderCandidateData.getShipperId().getRepoId()).isEqualTo(240);

	}

}
