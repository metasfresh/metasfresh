/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.inoutcandidate.modelvalidator;

import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleUpdater;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.invalidation.impl.ShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Recompute;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that deleting an {@link I_M_ShipmentSchedule} through the model layer also deletes every
 * {@code M_ShipmentSchedule_Recompute} marker of that schedule -- tagged ones included, per the deliberate
 * design decision in {@link IShipmentScheduleInvalidateRepository#deleteRecomputeMarkers}. A test that only
 * covers the untagged row would not guard that decision.
 */
class M_ShipmentSchedule_DeleteRecomputeMarkersTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void deletingScheduleThroughModelLayer_deletesUntaggedAndTaggedRecomputeMarkers()
	{
		// given: the schedule + its markers, created with the interceptor NOT YET registered -- so only the
		// TYPE_BEFORE_DELETE methods under test fire later, not the unrelated TYPE_BEFORE_NEW defaulting chain
		// (which needs a full Spring context this plain unit test doesn't have).
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		saveRecord(schedule);

		final I_M_ShipmentSchedule_Recompute untaggedMarker = newInstance(I_M_ShipmentSchedule_Recompute.class);
		untaggedMarker.setM_ShipmentSchedule_ID(schedule.getM_ShipmentSchedule_ID());
		saveRecord(untaggedMarker);

		final I_M_ShipmentSchedule_Recompute taggedMarker = newInstance(I_M_ShipmentSchedule_Recompute.class);
		taggedMarker.setM_ShipmentSchedule_ID(schedule.getM_ShipmentSchedule_ID());
		taggedMarker.setAD_PInstance_ID(1000001); // tagged: AD_PInstance_ID set (vs. the untagged marker's NULL)
		saveRecord(taggedMarker);

		registerInterceptorUnderTest();

		// when: deleted through the model layer, so the @ModelChange(TYPE_BEFORE_DELETE) interceptor fires
		InterfaceWrapperHelper.delete(schedule);

		// then: both the untagged AND the tagged marker are gone
		final List<I_M_ShipmentSchedule_Recompute> remainingMarkers = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule_Recompute.class)
				.addEqualsFilter(I_M_ShipmentSchedule_Recompute.COLUMNNAME_M_ShipmentSchedule_ID, schedule.getM_ShipmentSchedule_ID())
				.create()
				.list();

		assertThat(remainingMarkers).isEmpty();
	}

	/**
	 * Registers the interceptor's collaborators via {@link Services#registerService}, which writes straight into
	 * the service cache, and NOT via {@code SpringContextHolder.registerJUnitBean}.
	 * <p>
	 * Both are Spring beans with constructor-injected collaborators, so {@code Services.get(...)} cannot
	 * reflectively default-construct them here. The JUnit-bean fallback would normally cover that, but it is
	 * reached only through Services' external provider -- and any {@code @SpringBootTest} elsewhere in this
	 * module permanently replaces that provider with one bound to its own throwaway context, which
	 * {@code AdempiereTestHelper.init()} does not restore. So the fallback works when this class runs alone and
	 * silently stops working once the full module suite runs it after such a test. Registering the instances
	 * directly bypasses the provider entirely and behaves the same either way.
	 */
	private static void registerInterceptorUnderTest()
	{
		Services.registerService(IShipmentScheduleInvalidateBL.class, new ShipmentScheduleInvalidateBL(new PickingBOMService()));
		Services.registerService(IShipmentScheduleUpdater.class, ShipmentScheduleUpdater.newInstanceForUnitTesting());

		POJOLookupMap.get().addModelValidator(new M_ShipmentSchedule());
	}
}
