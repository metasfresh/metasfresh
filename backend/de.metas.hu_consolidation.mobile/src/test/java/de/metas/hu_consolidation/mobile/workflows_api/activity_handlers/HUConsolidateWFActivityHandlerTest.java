package de.metas.hu_consolidation.mobile.workflows_api.activity_handlers;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.handlingunits.grai.HUGraiService;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobRepository;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobReference;
import de.metas.picking.api.PickingSlotId;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.X_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link HUConsolidateWFActivityHandler#resolveGraiScanEnabled} —
 * verifies that the handler reads {@code GRAIRequired} from the BPartner via
 * {@link IBPartnerDAO} and maps it correctly to the graiScanEnabled boolean.
 */
@ExtendWith(AdempiereTestWatcher.class)
class HUConsolidateWFActivityHandlerTest
{
	private static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(1);
	private static final UserId USER_ID = UserId.ofRepoId(1);

	private HUConsolidateWFActivityHandler handler;
	private IBPartnerDAO bpartnerDAO;
	private HUConsolidationJob job;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		bpartnerDAO = mock(IBPartnerDAO.class);
		Services.registerService(IBPartnerDAO.class, bpartnerDAO);

		handler = new HUConsolidateWFActivityHandler(
				mock(PickingSlotService.class),
				mock(IDocumentLocationBL.class),
				mock(HUGraiService.class));

		final HUConsolidationJobRepository jobRepository = new HUConsolidationJobRepository();
		final HUConsolidationJobReference reference = HUConsolidationJobReference.builder()
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(BPARTNER_ID, 2))
				.pickingSlotId(PickingSlotId.ofRepoId(3))
				.build();
		job = jobRepository.create(reference, USER_ID);
	}

	@Test
	void resolveGraiScanEnabled_whenGRAIRequiredIsYes()
	{
		// GIVEN
		final I_C_BPartner bpartner = mock(I_C_BPartner.class);
		when(bpartner.getGRAIRequired()).thenReturn(X_C_BPartner.GRAIREQUIRED_Yes);
		when(bpartnerDAO.getById(BPARTNER_ID)).thenReturn(bpartner);

		// WHEN
		final boolean result = handler.resolveGraiScanEnabled(job);

		// THEN
		assertThat(result).isTrue();
	}

	@Test
	void resolveGraiScanEnabled_whenGRAIRequiredIsNo()
	{
		// GIVEN
		final I_C_BPartner bpartner = mock(I_C_BPartner.class);
		when(bpartner.getGRAIRequired()).thenReturn(X_C_BPartner.GRAIREQUIRED_No);
		when(bpartnerDAO.getById(BPARTNER_ID)).thenReturn(bpartner);

		// WHEN
		final boolean result = handler.resolveGraiScanEnabled(job);

		// THEN
		assertThat(result).isFalse();
	}

	@Test
	void resolveGraiScanEnabled_whenGRAIRequiredIsYesWithDummyGRAIs()
	{
		// GIVEN — YesWithDummyGRAIs is treated the same as Yes (not No)
		final I_C_BPartner bpartner = mock(I_C_BPartner.class);
		when(bpartner.getGRAIRequired()).thenReturn(X_C_BPartner.GRAIREQUIRED_YesWithDummyGRAIs);
		when(bpartnerDAO.getById(BPARTNER_ID)).thenReturn(bpartner);

		// WHEN
		final boolean result = handler.resolveGraiScanEnabled(job);

		// THEN
		assertThat(result).isTrue();
	}
}
