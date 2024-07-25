package de.metas.distribution.ddordercandidate;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class DDOrderCandidateRepositoryTest
{
	private DDOrderCandidateRepository ddOrderCandidateRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		this.ddOrderCandidateRepository = new DDOrderCandidateRepository();
	}

	private DDOrderCandidate newFullyFilled()
	{
		@NonNull final I_C_UOM uom = BusinessTestHelper.createUOM("uom", 2);

		return DDOrderCandidate.builder()
				.orgId(OrgId.ofRepoId(10))
				//
				.dateOrdered(Instant.parse("2024-07-04T01:02:03Z"))
				.datePromised(Instant.parse("2024-08-05T02:03:04Z"))
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
				.salesOrderLineId(OrderLineId.ofRepoId(95))
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