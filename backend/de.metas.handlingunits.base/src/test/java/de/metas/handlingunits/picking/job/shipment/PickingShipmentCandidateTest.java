package de.metas.handlingunits.picking.job.shipment;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Regression coverage for the contract that {@link PickingShipmentCandidate} carries the
 * customer-configured {@link CreateShipmentPolicy}. The earlier
 * bug was that the service layer was forcing {@code CREATE_COMPLETE_CLOSE}
 * regardless of the configured policy.
 */
class PickingShipmentCandidateTest
{
	private static final PickingShipmentCandidateKey KEY = new PickingShipmentCandidateKey(BPartnerId.ofRepoId(1));

	@Test
	void createShipmentPolicy_isPreserved_whenConfigured()
	{
		final PickingShipmentCandidate candidate = PickingShipmentCandidate.builder()
				.key(KEY)
				.createShipmentPolicy(CreateShipmentPolicy.CREATE_AND_COMPLETE)
				.build();

		assertThat(candidate.getCreateShipmentPolicy()).isEqualTo(CreateShipmentPolicy.CREATE_AND_COMPLETE);
	}

	@Test
	void createShipmentPolicy_defaultsTo_CREATE_COMPLETE_CLOSE_whenNull()
	{
		final PickingShipmentCandidate candidate = PickingShipmentCandidate.builder()
				.key(KEY)
				.build();

		assertThat(candidate.getCreateShipmentPolicy()).isEqualTo(CreateShipmentPolicy.CREATE_COMPLETE_CLOSE);
	}

	@Test
	void createShipmentPolicy_DO_NOT_CREATE_isRejected()
	{
		assertThatThrownBy(() -> PickingShipmentCandidate.builder()
				.key(KEY)
				.createShipmentPolicy(CreateShipmentPolicy.DO_NOT_CREATE)
				.build())
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Invalid create shipment policy");
	}
}
