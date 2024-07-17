package de.metas.material.planning.ddordercandidate;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddordercandidate.DDOrderCandidateAdvisedEvent;
import de.metas.material.event.ddordercandidate.DDOrderCandidateData;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.createSupplyRequiredDescriptorWithProductId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class DDOrderCandidateAdvisedEventCreatorTest
{
	DDOrderCandidateDemandMatcher demandMatcher;
	DDOrderCandidateDataFactory pojoSupplier;
	DDOrderCandidateAdvisedEventCreator advisedEventCreator;

	private ProductId productId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM uom = BusinessTestHelper.createUOM("uom");
		productId = BusinessTestHelper.createProductId("product", uom);

		demandMatcher = Mockito.mock(DDOrderCandidateDemandMatcher.class);
		pojoSupplier = Mockito.mock(DDOrderCandidateDataFactory.class);
		this.advisedEventCreator = new DDOrderCandidateAdvisedEventCreator(demandMatcher, pojoSupplier);
	}

	@Test
	public void createProductionAdvisedEvents_returns_same_supplyRequiredDescriptor()
	{
		final MaterialPlanningContext context = MaterialPlanningContext.builder()
				.productId(ProductId.ofRepoId(1))
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.warehouseId(WarehouseId.MAIN)
				.productPlanning(ProductPlanning.builder().build())
				.plantId(ResourceId.ofRepoId(2))
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.build();

		Mockito.when(demandMatcher.matches(Mockito.any(MaterialPlanningContext.class))).thenReturn(true);
		Mockito.when(pojoSupplier.create(Mockito.any(), Mockito.any())).thenReturn(ImmutableList.of(newDummyDDOrderCandidateData()));

		final SupplyRequiredDescriptor supplyRequiredDescriptor = createSupplyRequiredDescriptorWithProductId(productId.getRepoId());

		final List<DDOrderCandidateAdvisedEvent> events = advisedEventCreator.createDDOrderCandidateAdvisedEvents(supplyRequiredDescriptor, context);

		assertThat(events).hasSize(1);
		assertThat(events.get(0).getSupplyRequiredDescriptor()).isSameAs(supplyRequiredDescriptor);
	}

	private DDOrderCandidateData newDummyDDOrderCandidateData()
	{
		// final DistributionNetworkAndLineId distributionNetworkAndLineId = newDistributionNetwork();

		return DDOrderCandidateData.builder()
				.productPlanningId(ProductPlanningId.ofRepoId(1))
				// .distributionNetworkAndLineId(DistributionNetworkRepository)
				.build();
	}

	private static DistributionNetworkAndLineId newDistributionNetwork()
	{
		final I_DD_NetworkDistribution distributionNetwork = InterfaceWrapperHelper.newInstance(I_DD_NetworkDistribution.class);
		distributionNetwork.setName("dummy");
		InterfaceWrapperHelper.save(distributionNetwork);

		final I_DD_NetworkDistributionLine distributionNetworkLine = newInstance(I_DD_NetworkDistributionLine.class);
		distributionNetworkLine.setDD_NetworkDistribution_ID(distributionNetwork.getDD_NetworkDistribution_ID());
		distributionNetworkLine.setM_WarehouseSource_ID(1);
		distributionNetworkLine.setM_Warehouse_ID(2);
		distributionNetworkLine.setM_Shipper_ID(3);
		distributionNetworkLine.setPercent(BigDecimal.valueOf(100));
		saveRecord(distributionNetworkLine);

		return DistributionNetworkAndLineId.ofRepoIds(distributionNetworkLine.getDD_NetworkDistribution_ID(), distributionNetworkLine.getDD_NetworkDistributionLine_ID());
	}

}