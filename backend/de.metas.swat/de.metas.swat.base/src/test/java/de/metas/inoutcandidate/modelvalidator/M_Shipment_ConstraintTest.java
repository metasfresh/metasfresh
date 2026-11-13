package de.metas.inoutcandidate.modelvalidator;

import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.invalidation.impl.ShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_Shipment_Constraint;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.inoutcandidate.shipmentconstraint.ShipmentConstraintRepository;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
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
 * Pins that saving a shipment constraint writes its storage-segment recompute flag inside the transaction of the
 * save (so it commits or rolls back with it), not out of transaction.
 */
class M_Shipment_ConstraintTest
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
	void savingActiveConstraint_flagsStorageSegmentInThreadInheritedTrx()
	{
		registerInterceptorUnderTest();

		// when: an active constraint is saved inside a transaction, so the AFTER_NEW interceptor fires
		trxManager.runInThreadInheritedTrx(() -> {
			final I_M_Shipment_Constraint constraint = newInstance(I_M_Shipment_Constraint.class);
			constraint.setBill_BPartner_ID(2000001);
			constraint.setIsActive(true);
			saveRecord(constraint);
		});

		// then: the segment flag was written in the caller's transaction, and not out of transaction
		verify(invalidateRepository, times(1)).invalidateStorageSegmentsInThreadInheritedTrx(any());
		verify(invalidateRepository, never()).invalidateStorageSegments(any(), any());
	}

	/**
	 * Registers the collaborators via {@link Services#registerService} -- NOT
	 * {@code SpringContextHolder.registerJUnitBean}, which is silently inert here once any
	 * {@code @SpringBootTest} has run earlier in the same fork.
	 */
	private void registerInterceptorUnderTest()
	{
		Services.registerService(IShipmentScheduleInvalidateRepository.class, invalidateRepository);
		final IShipmentScheduleInvalidateBL invalidateBL = new ShipmentScheduleInvalidateBL(new PickingBOMService());
		Services.registerService(IShipmentScheduleInvalidateBL.class, invalidateBL);

		POJOLookupMap.get().addModelValidator(new M_Shipment_Constraint(new ShipmentConstraintRepository(), invalidateBL));
	}
}
