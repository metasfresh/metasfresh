package de.metas.distribution.ddordercandidate;

import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.order.OrderAndLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class DDOrderCandidateRepositoryTest
{
	private static final ClientId CLIENT_ID = ClientId.ofRepoId(9);
	private DDOrderCandidateRepository ddOrderCandidateRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), CLIENT_ID);

		this.ddOrderCandidateRepository = new DDOrderCandidateRepository();
	}

	static DDOrderCandidate newFullyFilled()
	{
		@NonNull final I_C_UOM uom = BusinessTestHelper.createUOM("uom", 2);

		return DDOrderCandidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(CLIENT_ID, OrgId.ofRepoId(10)))
				//
				.dateOrdered(Instant.parse("2024-07-01T23:59:59Z"))
				.supplyDate(Instant.parse("2024-08-05T02:03:04Z"))
				.demandDate(Instant.parse("2024-08-04T01:02:03Z"))
				//
				.productId(ProductId.ofRepoId(20))
				.hupiItemProductId(HUPIItemProductId.ofRepoId(30))
				.qty(Quantity.of("123.45", uom))
				.qtyTUs(3)
				//
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(50)) // TODO
				//
				.sourceWarehouseId(WarehouseId.ofRepoId(60))
				.targetWarehouseId(WarehouseId.ofRepoId(70))
				.targetPlantId(ResourceId.ofRepoId(80))
				.shipperId(ShipperId.ofRepoId(90))
				//
				.isSimulated(true)
				// isAllowPush;
				// isKeepTargetPlant;
				//
				.customerId(BPartnerId.ofRepoId(94))
				.salesOrderLineId(OrderAndLineId.ofRepoIds(95, 951))
				.forwardPPOrderRef(PPOrderRef.builder()
						.ppOrderCandidateId(96)
						.ppOrderLineCandidateId(97)
						.ppOrderId(PPOrderId.ofRepoId(98))
						.ppOrderBOMLineId(PPOrderBOMLineId.ofRepoId(99))
						.build())
				//
				.distributionNetworkAndLineId(DistributionNetworkAndLineId.ofRepoIds(100, 110))
				.productPlanningId(ProductPlanningId.ofRepoId(120))
				//
				// Don't set them because they are not persisted:
				// .traceId("traceId")
				// .materialDispoGroupId(MaterialDispoGroupId.ofInt(999))
				//
				.build();

	}

	@Test
	void save_load()
	{
		final DDOrderCandidate candidate = newFullyFilled();
		ddOrderCandidateRepository.save(candidate);

		final DDOrderCandidate candidate2 = ddOrderCandidateRepository.getById(candidate.getIdNotNull());
		assertThat(candidate2).usingRecursiveComparison().isEqualTo(candidate);
		assertThat(candidate2).isEqualTo(candidate);

	}
}