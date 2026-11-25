package de.metas.frontend_testing.masterdata.mobile_configuration;

import com.google.common.collect.ImmutableSet;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.config.mobileui.PickToStructure;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The picking profile is one shared record and some of its fields keep their value until a request sets them again:
 * the response carries the values from before the update, so a test can write back what it changed.
 */
class MobileConfigPickingCommandTest
{
	private MobileUIPickingUserProfileService profileService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		profileService = MobileUIPickingUserProfileService.newInstanceForUnitTesting();
	}

	private JsonMobileConfigResponse.Picking update(final Boolean alwaysSplitHUsEnabled)
	{
		return MobileConfigPickingCommand.builder()
				.mobilePickingConfigService(profileService)
				.context(new MasterdataContext())
				.request(JsonMobileConfigRequest.Picking.builder()
						.pickTo(ImmutableSet.of(PickToStructure.TU))
						.alwaysSplitHUsEnabled(alwaysSplitHUsEnabled)
						.build())
				.build()
				.execute();
	}

	@Test
	void responseCarriesTheValuesFromBeforeTheUpdate()
	{
		update(false);

		final JsonMobileConfigResponse.Picking response = update(true);

		assertThat(response.getAlwaysSplitHUsEnabled()).isTrue();
		assertThat(response.getPrevious()).isNotNull();
		assertThat(response.getPrevious().getAlwaysSplitHUsEnabled()).isFalse();
		assertThat(response.getPrevious().getPrevious()).isNull();
	}
}
