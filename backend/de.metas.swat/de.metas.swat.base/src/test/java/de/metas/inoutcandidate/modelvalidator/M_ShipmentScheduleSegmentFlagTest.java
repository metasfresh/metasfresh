package de.metas.inoutcandidate.modelvalidator;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.impl.DocumentLocationBL;
import de.metas.greeting.GreetingRepository;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleUpdater;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.invalidation.impl.ShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Pins that a business-partner change of a shipment schedule writes its storage-segment recompute flag inside the
 * transaction of the change (so it commits or rolls back with it), not out of transaction.
 */
class M_ShipmentScheduleSegmentFlagTest
{
	private ITrxManager trxManager;
	private IShipmentScheduleInvalidateRepository invalidateRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		trxManager = Services.get(ITrxManager.class);
		invalidateRepository = Mockito.mock(IShipmentScheduleInvalidateRepository.class);
	}

	@Test
	void bpartnerChange_flagsStorageSegmentInThreadInheritedTrx()
	{
		// given: a schedule with a business partner and its location (as every real schedule has), created with the interceptor NOT YET registered
		final BPartnerLocationId initialLocation = createBPartnerWithLocation("BP1");
		final BPartnerLocationId changedLocation = createBPartnerWithLocation("BP2");

		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_Product_ID(1000001);
		schedule.setC_BPartner_ID(initialLocation.getBpartnerId().getRepoId());
		schedule.setC_BPartner_Location_ID(initialLocation.getRepoId());
		schedule.setM_Warehouse_ID(3000001);
		saveRecord(schedule);

		registerInterceptorUnderTest();

		// when: the business partner is changed and saved inside a transaction, so the AFTER_CHANGE interceptor fires
		trxManager.runInThreadInheritedTrx(() -> {
			schedule.setC_BPartner_ID(changedLocation.getBpartnerId().getRepoId());
			schedule.setC_BPartner_Location_ID(changedLocation.getRepoId());
			saveRecord(schedule);
		});

		// then: the segment flag was written in the caller's transaction, and not out of transaction
		verify(invalidateRepository, times(1)).invalidateStorageSegmentsInThreadInheritedTrx(any());
		verify(invalidateRepository, never()).invalidateStorageSegments(any(), any());
	}

	private static BPartnerLocationId createBPartnerWithLocation(final String name)
	{
		final I_C_BPartner bpartner = BusinessTestHelper.createBPartner(name);
		final I_C_BPartner_Location bpLocation = BusinessTestHelper.createBPartnerLocation(bpartner);
		return BPartnerLocationId.ofRepoId(bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
	}

	/**
	 * Registers the collaborators via {@link Services#registerService}. {@code SpringContextHolder.registerJUnitBean}
	 * is used only for {@link IDocumentLocationBL} and {@link GreetingRepository}, Spring beans without a {@link Services}-registrable
	 * singleton; the other collaborators avoid it because it can be silently inert once any
	 * {@code @SpringBootTest} has run earlier in the same fork (not reproduced here, this class passes standalone).
	 */
	private void registerInterceptorUnderTest()
	{
		// needed by the schedule's BEFORE_CHANGE address-capturing interceptor, which also fires on this save
		SpringContextHolder.registerJUnitBean(IDocumentLocationBL.class, DocumentLocationBL.newInstanceForUnitTesting());
		SpringContextHolder.registerJUnitBean(new GreetingRepository()); // needed to render the schedule's address
		Services.registerService(IShipmentScheduleInvalidateRepository.class, invalidateRepository);
		Services.registerService(IShipmentScheduleInvalidateBL.class, new ShipmentScheduleInvalidateBL(new PickingBOMService()));
		Services.registerService(IShipmentScheduleUpdater.class, ShipmentScheduleUpdater.newInstanceForUnitTesting());

		POJOLookupMap.get().addModelValidator(new M_ShipmentSchedule());
	}
}
