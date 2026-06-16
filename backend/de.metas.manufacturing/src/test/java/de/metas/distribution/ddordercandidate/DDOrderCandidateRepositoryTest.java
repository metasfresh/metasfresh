package de.metas.distribution.ddordercandidate;

import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.impexp.InputDataSourceId;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.order.OrderAndLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.CreateSelectionResponse;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

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
				.qtyEntered(Quantity.of("123.45", uom))
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
						.ppOrderCandidateId(PPOrderCandidateId.ofRepoId(96))
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

	@Test
	void inputDataSource_roundtrip_present()
	{
		final InputDataSourceId inputDataSourceId = InputDataSourceId.ofRepoId(200);

		final DDOrderCandidate candidate = newFullyFilled().toBuilder()
				.inputDataSourceId(inputDataSourceId)
				.build();
		ddOrderCandidateRepository.save(candidate);

		final DDOrderCandidate reloaded = ddOrderCandidateRepository.getById(candidate.getIdNotNull());
		assertThat(reloaded.getInputDataSourceId()).isEqualTo(inputDataSourceId);
	}

	@Test
	void inputDataSource_roundtrip_absent()
	{
		final DDOrderCandidate candidate = newFullyFilled().toBuilder()
				.inputDataSourceId(null)
				.build();
		ddOrderCandidateRepository.save(candidate);

		final DDOrderCandidate reloaded = ddOrderCandidateRepository.getById(candidate.getIdNotNull());
		assertThat(reloaded.getInputDataSourceId()).isNull();
	}

	@Test
	void list_filtersByInputDataSource()
	{
		final InputDataSourceId sourceA = InputDataSourceId.ofRepoId(301);
		final InputDataSourceId sourceB = InputDataSourceId.ofRepoId(302);

		final DDOrderCandidate candidateA = newFullyFilled().toBuilder()
				.inputDataSourceId(sourceA)
				.build();
		ddOrderCandidateRepository.save(candidateA);

		final DDOrderCandidate candidateB = newFullyFilled().toBuilder()
				.inputDataSourceId(sourceB)
				.build();
		ddOrderCandidateRepository.save(candidateB);

		final List<DDOrderCandidate> resultA = ddOrderCandidateRepository.list(
				DDOrderCandidateQuery.builder()
						.inputDataSourceId(sourceA)
						.build());
		assertThat(resultA).hasSize(1);
		assertThat(resultA.get(0).getInputDataSourceId()).isEqualTo(sourceA);

		final List<DDOrderCandidate> resultAll = ddOrderCandidateRepository.list(
				DDOrderCandidateQuery.builder()
						.build());
		assertThat(resultAll).hasSize(2);
	}

	@Nested
	class CreateSelection
	{
		@Test
		void filtersByInputDataSource()
		{
			final InputDataSourceId sourceA = InputDataSourceId.ofRepoId(301);
			final InputDataSourceId sourceB = InputDataSourceId.ofRepoId(302);

			final DDOrderCandidate candidateA = newFullyFilled().toBuilder().inputDataSourceId(sourceA).build();
			ddOrderCandidateRepository.save(candidateA);
			final DDOrderCandidate candidateB = newFullyFilled().toBuilder().inputDataSourceId(sourceB).build();
			ddOrderCandidateRepository.save(candidateB);

			final PInstanceId selectionId = ddOrderCandidateRepository.createSelection(DDOrderCandidateQuery.builder()
							.inputDataSourceId(sourceA)
							.processed(false)
							.onlyPositiveQtyToProcess(true)
							.build())
					.map(CreateSelectionResponse::getSelectionId)
					.orElseThrow(() -> new AdempiereException("No candidates found"));

			assertThat(ddOrderCandidateRepository.getBySelectionId(selectionId))
					.extracting(DDOrderCandidate::getIdNotNull)
					.containsExactly(candidateA.getIdNotNull());
		}

		@Test
		void noInputDataSource_returnsAllEligible()
		{
			final DDOrderCandidate a = newFullyFilled().toBuilder().inputDataSourceId(InputDataSourceId.ofRepoId(301)).build();
			ddOrderCandidateRepository.save(a);
			final DDOrderCandidate b = newFullyFilled().toBuilder().inputDataSourceId(InputDataSourceId.ofRepoId(302)).build();
			ddOrderCandidateRepository.save(b);

			final PInstanceId selectionId = ddOrderCandidateRepository.createSelection(DDOrderCandidateQuery.builder()
							.processed(false)
							.onlyPositiveQtyToProcess(true)
							.build())
					.map(CreateSelectionResponse::getSelectionId)
					.orElseThrow(() -> new AdempiereException("No candidates found"));

			assertThat(ddOrderCandidateRepository.getBySelectionId(selectionId))
					.extracting(DDOrderCandidate::getIdNotNull)
					.containsExactlyInAnyOrder(a.getIdNotNull(), b.getIdNotNull());
		}

		@Test
		void excludesIneligibleZeroQtyToProcess()
		{
			final InputDataSourceId source = InputDataSourceId.ofRepoId(301);
			final DDOrderCandidate eligible = newFullyFilled().toBuilder().inputDataSourceId(source).build();
			ddOrderCandidateRepository.save(eligible);

			final DDOrderCandidate base = newFullyFilled();
			final DDOrderCandidate ineligible = base.toBuilder().inputDataSourceId(source).qtyProcessed(base.getQtyEntered()).build();
			ddOrderCandidateRepository.save(ineligible);

			final PInstanceId selectionId = ddOrderCandidateRepository.createSelection(DDOrderCandidateQuery.builder()
							.inputDataSourceId(source)
							.processed(false)
							.onlyPositiveQtyToProcess(true)
							.build())
					.map(CreateSelectionResponse::getSelectionId)
					.orElseThrow(() -> new AdempiereException("No candidates found"));

			assertThat(ddOrderCandidateRepository.getBySelectionId(selectionId))
					.extracting(DDOrderCandidate::getIdNotNull)
					.containsExactly(eligible.getIdNotNull());
		}
	}
}
