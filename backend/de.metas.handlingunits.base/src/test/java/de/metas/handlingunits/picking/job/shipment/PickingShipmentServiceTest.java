package de.metas.handlingunits.picking.job.shipment;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.handlingunits.shipmentschedule.api.IShipmentService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Service-level regression coverage for me03 issue 27448.
 * <p>
 * Original bug: {@link PickingShipmentService#createShipmentForLUs} hardcoded the
 * {@link CreateShipmentPolicy} to {@link CreateShipmentPolicy#CREATE_COMPLETE_CLOSE} regardless
 * of what the customer's mobile-picking profile had configured. The fix removes that override
 * so the customer-configured policy is honoured.
 * <p>
 * The DTO checks in {@link PickingShipmentCandidateTest} would have passed even with the bug
 * in place — only an assertion on the policy resolution itself catches the regression.
 */
class PickingShipmentServiceTest
{
	private static final BPartnerId CUSTOMER_ID = BPartnerId.ofRepoId(1);

	private static PickingJobOptions optionsWith(final CreateShipmentPolicy policy)
	{
		return PickingJobOptions.builder()
				.createShipmentPolicy(policy)
				.build();
	}

	private static PickingShipmentService newServiceWithConfiguredPolicy(final CreateShipmentPolicy configuredPolicy)
	{
		final MobileUIPickingUserProfileRepository profileRepo = mock(MobileUIPickingUserProfileRepository.class);
		when(profileRepo.getPickingJobOptions(CUSTOMER_ID)).thenReturn(optionsWith(configuredPolicy));
		return new PickingShipmentService(profileRepo, mock(IShipmentService.class));
	}

	@Test
	void resolveCreateShipmentPolicy_returnsConfiguredPolicy_whenOverrideIsNull()
	{
		final PickingShipmentService service = newServiceWithConfiguredPolicy(CreateShipmentPolicy.CREATE_AND_COMPLETE);

		final CreateShipmentPolicy resolved = service.resolveCreateShipmentPolicy(CUSTOMER_ID, null);

		assertThat(resolved)
				.as("close-LU path passes override=null and must therefore use the customer-configured policy — me03#27448 regression guard")
				.isEqualTo(CreateShipmentPolicy.CREATE_AND_COMPLETE);
	}

	@Test
	void resolveCreateShipmentPolicy_returnsCREATE_COMPLETE_CLOSE_whenCustomerConfiguredSo()
	{
		final PickingShipmentService service = newServiceWithConfiguredPolicy(CreateShipmentPolicy.CREATE_COMPLETE_CLOSE);

		final CreateShipmentPolicy resolved = service.resolveCreateShipmentPolicy(CUSTOMER_ID, null);

		assertThat(resolved).isEqualTo(CreateShipmentPolicy.CREATE_COMPLETE_CLOSE);
	}

	@Test
	void resolveCreateShipmentPolicy_returnsCREATE_DRAFT_whenCustomerConfiguredSo()
	{
		final PickingShipmentService service = newServiceWithConfiguredPolicy(CreateShipmentPolicy.CREATE_DRAFT);

		final CreateShipmentPolicy resolved = service.resolveCreateShipmentPolicy(CUSTOMER_ID, null);

		assertThat(resolved).isEqualTo(CreateShipmentPolicy.CREATE_DRAFT);
	}

	@Test
	void resolveCreateShipmentPolicy_returnsDO_NOT_CREATE_whenCustomerConfiguredSo()
	{
		final PickingShipmentService service = newServiceWithConfiguredPolicy(CreateShipmentPolicy.DO_NOT_CREATE);

		final CreateShipmentPolicy resolved = service.resolveCreateShipmentPolicy(CUSTOMER_ID, null);

		assertThat(resolved).isEqualTo(CreateShipmentPolicy.DO_NOT_CREATE);
	}

	@Test
	void resolveCreateShipmentPolicy_overrideTakesPrecedence_overConfiguredPolicy()
	{
		final PickingShipmentService service = newServiceWithConfiguredPolicy(CreateShipmentPolicy.CREATE_AND_COMPLETE);

		final CreateShipmentPolicy resolved = service.resolveCreateShipmentPolicy(CUSTOMER_ID, CreateShipmentPolicy.CREATE_COMPLETE_CLOSE);

		assertThat(resolved)
				.as("an explicit override must win over the customer profile (the contract that makes the override parameter useful)")
				.isEqualTo(CreateShipmentPolicy.CREATE_COMPLETE_CLOSE);
	}
}
