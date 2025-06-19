/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.distribution.ddordercandidate.material_dispo;

import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.distribution.ddordercandidate.DDOrderCandidateService;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddordercandidate.DDOrderCandidateAdvisedEvent;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.material.planning.ddordercandidate.DDOrderCandidateDataFactory;
import de.metas.material.planning.ddordercandidate.DDOrderCandidateDemandMatcher;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class DDOrderCandidateAdvisedEventCreatorTest
{
	public static final int ATTRIBUTE_SET_INSTANCE_ID = 28;
	public static final AttributesKey STORAGE_ATTRIBUTES_KEY = AttributesKey.ofString("1");
	public static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(25);

	private DDOrderCandidateDemandMatcher demandMatcher;
	private DDOrderCandidateDataFactory ddOrderCandidateDataFactory;
	private DDOrderCandidateService ddOrderCandidateService;
	private CandidateRepositoryWriteService candidateRepositoryWriteService;
	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	private DDOrderCandidateAdvisedEventCreator advisedEventCreator;

	private ProductId productId;
	private final ShipperId shipperId = ShipperId.ofRepoId(33);

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		demandMatcher = Mockito.mock(DDOrderCandidateDemandMatcher.class);
		ddOrderCandidateService = Mockito.mock(DDOrderCandidateService.class);
		candidateRepositoryWriteService = Mockito.mock(CandidateRepositoryWriteService.class);
		candidateRepositoryRetrieval = Mockito.mock(CandidateRepositoryRetrieval.class);
		ddOrderCandidateDataFactory = new DDOrderCandidateDataFactory(new DistributionNetworkRepository(), new ReplenishInfoRepository());
		advisedEventCreator = new DDOrderCandidateAdvisedEventCreator(demandMatcher, ddOrderCandidateDataFactory, ddOrderCandidateService, candidateRepositoryWriteService, candidateRepositoryRetrieval);

		createMasterData();
	}

	private void createMasterData()
	{
		final I_C_UOM uom = BusinessTestHelper.createUOM("uom");
		productId = BusinessTestHelper.createProductId("product", uom);

		createOrgBPartner(OrgId.MAIN);
		warehouse().name("inTransit").isInTransit(true).build();
	}

	@Builder(builderMethodName = "warehouse", builderClassName = "$WarehouseBuilder")
	private WarehouseId createWarehouse(
			@NonNull final String name,
			final boolean isInTransit)
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setAD_Org_ID(OrgId.MAIN.getRepoId());
		warehouse.setValue(name);
		warehouse.setName(name);
		warehouse.setIsInTransit(isInTransit);
		saveRecord(warehouse);

		return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
	}

	@SuppressWarnings("SameParameterValue")
	private void createOrgBPartner(final OrgId orgId)
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setValue("OrgBP");
		bpartner.setName("OrgBP");
		bpartner.setAD_Org_ID(orgId.getRepoId());
		bpartner.setAD_OrgBP_ID(orgId.getRepoId());
		InterfaceWrapperHelper.save(bpartner);
	}

	@Builder(builderMethodName = "distributionNetwork", builderClassName = "$DistributionNetwork")
	private DistributionNetworkAndLineId createDistributionNetwork(
			@NonNull final WarehouseId sourceWarehouseId,
			@NonNull final WarehouseId targetWarehouseId,
			final int durationInDays)
	{
		final I_DD_NetworkDistribution distributionNetwork = InterfaceWrapperHelper.newInstance(I_DD_NetworkDistribution.class);
		distributionNetwork.setName("dummy");
		InterfaceWrapperHelper.save(distributionNetwork);

		final I_DD_NetworkDistributionLine distributionNetworkLine = newInstance(I_DD_NetworkDistributionLine.class);
		distributionNetworkLine.setDD_NetworkDistribution_ID(distributionNetwork.getDD_NetworkDistribution_ID());
		distributionNetworkLine.setM_WarehouseSource_ID(sourceWarehouseId.getRepoId());
		distributionNetworkLine.setM_Warehouse_ID(targetWarehouseId.getRepoId());
		distributionNetworkLine.setM_Shipper_ID(shipperId.getRepoId());
		distributionNetworkLine.setPercent(BigDecimal.valueOf(100));
		distributionNetworkLine.setTransfertTime(durationInDays > 0 ? BigDecimal.valueOf(durationInDays) : null);
		saveRecord(distributionNetworkLine);

		return DistributionNetworkAndLineId.ofRepoIds(distributionNetworkLine.getDD_NetworkDistribution_ID(), distributionNetworkLine.getDD_NetworkDistributionLine_ID());
	}

	@Test
	public void createProductionAdvisedEvents_returns_same_supplyRequiredDescriptor()
	{
		final WarehouseId sourceWarehouseId;
		final WarehouseId targetWarehouseId;
		final DistributionNetworkAndLineId distributionNetworkAndLineId = distributionNetwork()
				.sourceWarehouseId(sourceWarehouseId = warehouse().name("source").build())
				.targetWarehouseId(targetWarehouseId = warehouse().name("target").build())
				.durationInDays(12)
				.build();

		final ProductPlanning productPlanning;
		final MaterialPlanningContext context = MaterialPlanningContext.builder()
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.warehouseId(targetWarehouseId)
				.productPlanning(productPlanning = ProductPlanning.builder()
						.id(ProductPlanningId.ofRepoId(1))
						.distributionNetworkId(distributionNetworkAndLineId.getNetworkId())
						.warehouseId(targetWarehouseId)
						.build())
				.plantId(ResourceId.ofRepoId(2))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.MAIN))
				.build();

		final int productId1 = productId.getRepoId();
		final SupplyRequiredDescriptor supplyRequiredDescriptor = SupplyRequiredDescriptor.builder()
				.shipmentScheduleId(21)
				.demandCandidateId(41)
				.eventDescriptor(EventDescriptor.ofClientAndOrg(ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.MAIN)))
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(createProductDescriptorWithProductId(productId1))
						.warehouseId(WarehouseId.ofRepoId(9999999)) // does not matter
						.customerId(BPARTNER_ID)
						.quantity(new BigDecimal("123"))
						.date(Instant.parse("2024-04-15T00:00:00Z"))
						.build())
				.build();

		Mockito.when(demandMatcher.matches(Mockito.any(MaterialPlanningContext.class))).thenReturn(true);
		final List<DDOrderCandidateAdvisedEvent> events = advisedEventCreator.createAdvisedEvents(supplyRequiredDescriptor, context);

		assertThat(events).hasSize(1);

		final DDOrderCandidateAdvisedEvent event = events.getFirst();

		final EventDescriptor eventDescriptor = event.getEventDescriptor();
		System.out.println(eventDescriptor);
		assertThat(eventDescriptor.getClientId()).isEqualTo(ClientId.METASFRESH);
		assertThat(eventDescriptor.getOrgId()).isEqualTo(OrgId.MAIN);

		System.out.println(event);
		assertThat(event.getProductPlanningId()).isEqualTo(productPlanning.getId());
		assertThat(event.getDistributionNetworkAndLineId()).isEqualTo(distributionNetworkAndLineId);
		assertThat(event.getOrgId()).isEqualTo(OrgId.MAIN);
		assertThat(event.getSourceWarehouseId()).isEqualTo(sourceWarehouseId);
		assertThat(event.getTargetWarehouseId()).isEqualTo(targetWarehouseId);
		assertThat(event.getTargetPlantId()).isEqualTo(context.getPlantId());
		assertThat(event.getShipperId()).isEqualTo(shipperId);
		assertThat(event.getProductId()).isEqualTo(productId.getRepoId());
		assertThat(event.getQty()).isEqualTo("123");
		assertThat(event.getSupplyDate()).isEqualTo(Instant.parse("2024-04-15T00:00:00Z"));
		assertThat(event.getDemandDate()).isEqualTo(Instant.parse("2024-04-03T00:00:00Z")); // -12 days
		// assertThat(event.).isEqualTo();

		assertThat(event.getSupplyRequiredDescriptor()).isSameAs(supplyRequiredDescriptor);
	}

	private ProductDescriptor createProductDescriptorWithProductId(final int productId)
	{
		return ProductDescriptor.forProductAndAttributes(
				productId,
				STORAGE_ATTRIBUTES_KEY,
				ATTRIBUTE_SET_INSTANCE_ID);
	}
}