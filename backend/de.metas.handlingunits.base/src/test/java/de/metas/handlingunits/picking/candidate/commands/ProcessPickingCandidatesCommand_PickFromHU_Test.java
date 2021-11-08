package de.metas.handlingunits.picking.candidate.commands;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.expectations.HUStorageExpectation;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
public class ProcessPickingCandidatesCommand_PickFromHU_Test
{
	private ProcessPickingCandidatesCommandTestHelper helper;
	private PickingCandidateRepository pickingCandidateRepository;
	private I_C_UOM uomEach;
	private ProductId productId;

	@BeforeEach
	public void init()
	{
		this.helper = new ProcessPickingCandidatesCommandTestHelper();
		this.pickingCandidateRepository = helper.pickingCandidateRepository;
		this.uomEach = helper.uomEach;
		this.productId = helper.createProduct("P1", uomEach);
	}

	@NonNull
	private PickingCandidateId createPickingCandidate(final HuId pickFromHUId, final Quantity qtyPicked, HuPackingInstructionsId packToInstructionsId)
	{
		final PickingCandidate pickingCandidate = PickingCandidate.builder()
				.shipmentScheduleId(helper.createShipmentSchedule(productId))
				.pickFrom(PickFrom.ofHuId(pickFromHUId))
				.qtyPicked(qtyPicked)
				.packToInstructionsId(packToInstructionsId)
				.build();
		pickingCandidateRepository.save(pickingCandidate);
		return Objects.requireNonNull(pickingCandidate.getId());
	}

	@Test
	public void packTo_lessQtyThanPickFrom()
	{
		final HuId pickFromVHUId = helper.createVHU(productId, Quantity.of("100", uomEach));
		final PickingCandidateId pickingCandidateId = createPickingCandidate(pickFromVHUId, Quantity.of("10", uomEach), HuPackingInstructionsId.VIRTUAL);

		ProcessPickingCandidatesCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidateId(pickingCandidateId)
				.build()
				.execute();

		{
			final PickingCandidate pickingCandidate = pickingCandidateRepository.getById(pickingCandidateId);
			assertThat(pickingCandidate.getQtyPicked()).isEqualTo(Quantity.of("10", uomEach));
			assertThat(pickingCandidate.getPickFrom()).isEqualTo(PickFrom.ofHuId(pickFromVHUId));
			assertThat(pickingCandidate.getPackedToHuId())
					.isNotNull()
					.isNotEqualTo(pickFromVHUId);

			HUStorageExpectation.newExpectation()
					.product(productId)
					.qty(Quantity.of("90", uomEach))
					.assertExpected(pickFromVHUId);

			HUStorageExpectation.newExpectation()
					.product(productId)
					.qty(Quantity.of("10", uomEach))
					.assertExpected(pickingCandidate.getPackedToHuId());
		}
	}

	@Test
	public void packTo_sameQtyAsPickFrom_samePackingInstructions()
	{
		final HuId pickFromVHUId = helper.createVHU(productId, Quantity.of("100", uomEach));
		final PickingCandidateId pickingCandidateId = createPickingCandidate(pickFromVHUId, Quantity.of("100", uomEach), HuPackingInstructionsId.VIRTUAL);

		ProcessPickingCandidatesCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidateId(pickingCandidateId)
				.build()
				.execute();

		{
			final PickingCandidate pickingCandidate = pickingCandidateRepository.getById(pickingCandidateId);
			assertThat(pickingCandidate.getQtyPicked()).isEqualTo(Quantity.of("100", uomEach));
			assertThat(pickingCandidate.getPickFrom()).isEqualTo(PickFrom.ofHuId(pickFromVHUId));
			assertThat(pickingCandidate.getPackedToHuId()).isEqualTo(pickFromVHUId);
		}
	}

	@Test
	public void packTo_sameQtyAsPickFrom_but_DifferentPackingInstructions()
	{
		final HuId pickFromVHUId = helper.createVHU(productId, Quantity.of("100", uomEach));
		final HuPackingInstructionsId packToInstructionsId = helper.createTUPackingInstructions(productId, Quantity.of("100", uomEach));
		final PickingCandidateId pickingCandidateId = createPickingCandidate(pickFromVHUId, Quantity.of("100", uomEach), packToInstructionsId);

		ProcessPickingCandidatesCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidateId(pickingCandidateId)
				.build()
				.execute();

		{
			final PickingCandidate pickingCandidate = pickingCandidateRepository.getById(pickingCandidateId);
			assertThat(pickingCandidate.getQtyPicked()).isEqualTo(Quantity.of("100", uomEach));
			assertThat(pickingCandidate.getPickFrom()).isEqualTo(PickFrom.ofHuId(pickFromVHUId));
			assertThat(pickingCandidate.getPackedToHuId())
					.isNotNull()
					.isNotEqualTo(pickFromVHUId);

			HUStorageExpectation.newExpectation()
					.product(productId)
					.qty(Quantity.of("0", uomEach))
					.assertExpected(pickFromVHUId);

			HUStorageExpectation.newExpectation()
					.product(productId)
					.qty(Quantity.of("100", uomEach))
					.assertExpected(pickingCandidate.getPackedToHuId());
		}
	}

}
