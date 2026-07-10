package de.metas.handlingunits.picking.job.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests persisting the picking consignee on the close-LU so the per-BPartner
 * {@code M_HU_Label_Config} matches and the SSCC label auto-prints.
 *
 * <p>Exercises the facade method
 * {@link PickingJobHUService#setBPartnerAndLocationIfNotSet(HuId, BPartnerLocationId)}:
 * <ul>
 *   <li>(a) a partner-less LU closed for a consignee gets that consignee's BPartner + delivery location stamped;</li>
 *   <li>(b) an LU that already carries a bpartner is left UNCHANGED (stamp-only-if-unset guard).</li>
 * </ul>
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobLabelConsigneeTest
{
	private static final BPartnerId CONSIGNEE = BPartnerId.ofRepoId(2810);
	private static final BPartnerLocationId CONSIGNEE_LOCATION = BPartnerLocationId.ofRepoId(CONSIGNEE, 3810);

	private static final BPartnerId OTHER_PARTNER = BPartnerId.ofRepoId(2402);
	private static final BPartnerLocationId OTHER_LOCATION = BPartnerLocationId.ofRepoId(OTHER_PARTNER, 3402);

	private HUTestHelper huTestHelper;
	private PickingJobHUService huService;

	@BeforeEach
	void setUp()
	{
		huTestHelper = HUTestHelper.newInstanceOutOfTrx();
		huService = PickingJobHUService.newInstanceForUnitTesting();
	}

	/** Creates a minimal saved LU; optionally pre-stamped with a bpartner + location. */
	private HuId createLU(final BPartnerLocationId preStampedLocation)
	{
		final I_M_HU_PI luPI = huTestHelper.createHUDefinition("LU-PI", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final I_M_HU lu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		lu.setM_HU_PI_Version_ID(Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(luPI).getM_HU_PI_Version_ID());
		if (preStampedLocation != null)
		{
			lu.setC_BPartner_ID(preStampedLocation.getBpartnerId().getRepoId());
			lu.setC_BPartner_Location_ID(preStampedLocation.getRepoId());
		}
		InterfaceWrapperHelper.save(lu);
		return HuId.ofRepoId(lu.getM_HU_ID());
	}

	private I_M_HU reload(final HuId huId)
	{
		return InterfaceWrapperHelper.load(huId.getRepoId(), I_M_HU.class);
	}

	@Test
	void partnerlessLU_getsConsigneeStamped()
	{
		final HuId luId = createLU(null);

		huService.setBPartnerAndLocationIfNotSet(luId, CONSIGNEE_LOCATION);

		final I_M_HU lu = reload(luId);
		assertThat(lu.getC_BPartner_ID())
				.as("partner-less LU must get the consignee's bpartner stamped")
				.isEqualTo(CONSIGNEE.getRepoId());
		assertThat(lu.getC_BPartner_Location_ID())
				.as("partner-less LU must get the consignee's delivery location stamped")
				.isEqualTo(CONSIGNEE_LOCATION.getRepoId());
	}

	@Test
	void luWithExistingBPartner_isLeftUnchanged()
	{
		final HuId luId = createLU(OTHER_LOCATION);

		huService.setBPartnerAndLocationIfNotSet(luId, CONSIGNEE_LOCATION);

		final I_M_HU lu = reload(luId);
		assertThat(lu.getC_BPartner_ID())
				.as("an LU that already carries a bpartner must be left unchanged (stamp-only-if-unset)")
				.isEqualTo(OTHER_PARTNER.getRepoId());
		assertThat(lu.getC_BPartner_Location_ID())
				.as("an LU that already carries a location must be left unchanged")
				.isEqualTo(OTHER_LOCATION.getRepoId());
	}
}
