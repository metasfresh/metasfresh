package de.metas.handlingunits.picking.candidate.commands;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.expectations.HUStorageExpectation;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(AdempiereTestWatcher.class)
public class ProcessAndUnProcessPickingCandidatesCommand_PickFromHU_Test
{
	private ProcessPickingCandidatesCommandTestHelper helper;
	private PickingCandidateRepository pickingCandidateRepository;
	private InventoryService inventoryService;
	private I_C_UOM uomEach;
	private ProductId productId;

	@BeforeEach
	public void beforeEach()
	{
		this.helper = new ProcessPickingCandidatesCommandTestHelper();
		this.pickingCandidateRepository = helper.pickingCandidateRepository;
		this.inventoryService = helper.inventoryService;
		this.uomEach = helper.uomEach;
		this.productId = helper.createProduct("P1", uomEach);
	}

	@NonNull
	private PickingCandidateId createPickingCandidate(final HuId pickFromHUId, final Quantity qtyPicked, PackToSpec packToSpec)
	{
		final PickingCandidate pickingCandidate = PickingCandidate.builder()
				.shipmentScheduleId(helper.createShipmentSchedule(productId))
				.pickFrom(PickFrom.ofHuId(pickFromHUId))
				.qtyPicked(qtyPicked)
				.packToSpec(packToSpec)
				.build();
		pickingCandidateRepository.save(pickingCandidate);
		return Objects.requireNonNull(pickingCandidate.getId());
	}

	@Test
	public void packTo_lessQtyThanPickFrom()
	{
		final HuId pickFromVHUId = helper.createVHU(productId, Quantity.of("100", uomEach));
		final PickingCandidateId pickingCandidateId = createPickingCandidate(pickFromVHUId, Quantity.of("10", uomEach), PackToSpec.VIRTUAL);

		newProcessPickingCandidatesCommand()
				.request(ProcessPickingCandidatesRequest.builder()
						.pickingCandidateId(pickingCandidateId)
						.build())
				.build()
				.execute();
		final HuId packedToHUId;
		{
			final PickingCandidate pickingCandidate = pickingCandidateRepository.getById(pickingCandidateId);
			assertThat(pickingCandidate.getQtyPicked()).isEqualTo(Quantity.of("10", uomEach));
			assertThat(pickingCandidate.getPickFrom()).isEqualTo(PickFrom.ofHuId(pickFromVHUId));
			assertThat(packedToHUId = pickingCandidate.getPackedToHuId()).isNotNull().isNotEqualTo(pickFromVHUId);
			HUStorageExpectation.newExpectation().product(productId).qty(Quantity.of("90", uomEach)).assertExpected(pickFromVHUId);
			HUStorageExpectation.newExpectation().product(productId).qty(Quantity.of("10", uomEach)).assertExpected(pickingCandidate.getPackedToHuId());
		}

		UnProcessPickingCandidatesCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidateId(pickingCandidateId)
				.build()
				.execute();
		{
			final PickingCandidate pickingCandidate = pickingCandidateRepository.getById(pickingCandidateId);
			assertThat(pickingCandidate.getQtyPicked()).isEqualTo(Quantity.of("10", uomEach));
			assertThat(pickingCandidate.getPickFrom()).isEqualTo(PickFrom.ofHuId(pickFromVHUId));
			assertThat(pickingCandidate.getPackedToHuId()).isNull();
			HUStorageExpectation.newExpectation().product(productId).qty(Quantity.of("90", uomEach)).assertExpected(pickFromVHUId);
			HUStorageExpectation.newExpectation().product(productId).qty(Quantity.of("10", uomEach)).assertExpected(packedToHUId);
		}
	}

	private ProcessPickingCandidatesCommand.ProcessPickingCandidatesCommandBuilder newProcessPickingCandidatesCommand()
	{
		return ProcessPickingCandidatesCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.inventoryService(inventoryService);
	}

	@Test
	public void packTo_sameQtyAsPickFrom_samePackingInstructions()
	{
		final HuId pickFromVHUId = helper.createVHU(productId, Quantity.of("100", uomEach));
		final PickingCandidateId pickingCandidateId = createPickingCandidate(pickFromVHUId, Quantity.of("100", uomEach), PackToSpec.VIRTUAL);

		newProcessPickingCandidatesCommand()
				.request(ProcessPickingCandidatesRequest.builder()
						.pickingCandidateId(pickingCandidateId)
						.build())
				.build()
				.execute();
		{
			final PickingCandidate pickingCandidate = pickingCandidateRepository.getById(pickingCandidateId);
			assertThat(pickingCandidate.getQtyPicked()).isEqualTo(Quantity.of("100", uomEach));
			assertThat(pickingCandidate.getPickFrom()).isEqualTo(PickFrom.ofHuId(pickFromVHUId));
			assertThat(pickingCandidate.getPackedToHuId()).isEqualTo(pickFromVHUId);
		}

		UnProcessPickingCandidatesCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidateId(pickingCandidateId)
				.build()
				.execute();
		{
			final PickingCandidate pickingCandidate = pickingCandidateRepository.getById(pickingCandidateId);
			assertThat(pickingCandidate.getQtyPicked()).isEqualTo(Quantity.of("100", uomEach));
			assertThat(pickingCandidate.getPickFrom()).isEqualTo(PickFrom.ofHuId(pickFromVHUId));
			assertThat(pickingCandidate.getPackedToHuId()).isNull();
		}
	}

	@Test
	public void packTo_sameQtyAsPickFrom_but_DifferentPackingInstructions()
	{
		final HuId pickFromVHUId = helper.createVHU(productId, Quantity.of("100", uomEach));
		final PackToSpec packToSpec = helper.createTUPackingInstructions(productId, Quantity.of("100", uomEach));
		final PickingCandidateId pickingCandidateId = createPickingCandidate(pickFromVHUId, Quantity.of("100", uomEach), packToSpec);

		newProcessPickingCandidatesCommand()
				.request(ProcessPickingCandidatesRequest.builder()
						.pickingCandidateId(pickingCandidateId)
						.build())
				.build()
				.execute();
		final HuId packedToHUId;
		{
			final PickingCandidate pickingCandidate = pickingCandidateRepository.getById(pickingCandidateId);
			assertThat(pickingCandidate.getQtyPicked()).isEqualTo(Quantity.of("100", uomEach));
			assertThat(pickingCandidate.getPickFrom()).isEqualTo(PickFrom.ofHuId(pickFromVHUId));
			assertThat(packedToHUId = pickingCandidate.getPackedToHuId()).isNotNull().isNotEqualTo(pickFromVHUId);
			HUStorageExpectation.newExpectation().product(productId).qty(Quantity.of("0", uomEach)).assertExpected(pickFromVHUId);
			HUStorageExpectation.newExpectation().product(productId).qty(Quantity.of("100", uomEach)).assertExpected(pickingCandidate.getPackedToHuId());
		}

		UnProcessPickingCandidatesCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidateId(pickingCandidateId)
				.build()
				.execute();
		{
			final PickingCandidate pickingCandidate = pickingCandidateRepository.getById(pickingCandidateId);
			assertThat(pickingCandidate.getQtyPicked()).isEqualTo(Quantity.of("100", uomEach));
			assertThat(pickingCandidate.getPickFrom()).isEqualTo(PickFrom.ofHuId(pickFromVHUId));
			assertThat(pickingCandidate.getPackedToHuId()).isNull();
			HUStorageExpectation.newExpectation().product(productId).qty(Quantity.of("100", uomEach)).assertExpected(packedToHUId);
		}
	}

}
