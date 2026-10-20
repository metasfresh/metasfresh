/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.shipmentschedule.api;

import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.impl.ShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.impl.PlainLockManager;
import de.metas.lock.exceptions.LockFailedException;
import de.metas.lock.spi.impl.PlainLockDatabase;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ShipmentScheduleEnqueuerTest
{
	private static final AdMessageKey MSG_NO_VALID_RECORDS = AdMessageKey.of("de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.NoValidRecords");

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Services.registerService(IShipmentScheduleInvalidateBL.class, new ShipmentScheduleInvalidateBL(new PickingBOMService())
		{
			@Override
			public boolean isFlaggedForRecompute(@NonNull final ShipmentScheduleId shipmentScheduleId)
			{
				return false; // the real check is a plain SQL query, which the in-memory test database can't run
			}
		});
	}

	/**
	 * The selection matched no shipment schedule when it was locked, and one became eligible right afterwards
	 * (e.g. the recompute committed its quantity to deliver in between).
	 * Nothing was shippable when "generate shipments" locked its selection, so the user gets the "no valid records" message.
	 * <p>
	 * A lock manager double locks nothing for the selection filter. On a real database, holding the lock statement back while only
	 * not yet eligible schedules are selected needs a picking job schedule in the selection (the only other record that statement locks).
	 */
	@Test
	void emptyLock_failsWithNoValidRecords()
	{
		Services.registerService(ILockManager.class, new NothingMatchedAtLockTimeLockManager());

		final I_M_ShipmentSchedule eligibleAfterLocking = newInstance(I_M_ShipmentSchedule.class);
		eligibleAfterLocking.setHeaderAggregationKey("headerAggregationKey");
		saveRecord(eligibleAfterLocking);

		final ShipmentScheduleWorkPackageParameters workPackageParameters = ShipmentScheduleWorkPackageParameters.builder()
				.adPInstanceId(PInstanceId.ofRepoId(1))
				.queryFilters(Services.get(IQueryBL.class).createCompositeQueryFilter(I_M_ShipmentSchedule.class)
						.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, eligibleAfterLocking.getM_ShipmentSchedule_ID()))
				.quantityType(M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
				.completeShipments(true)
				.isShipmentDateToday(false)
				.build();

		final Throwable failure = catchThrowable(() -> ShipmentScheduleEnqueuer.newInstance().createWorkpackages(workPackageParameters));

		final String expectedMessage = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_NO_VALID_RECORDS).translate(Env.getAD_Language());
		assertThat(failure)
				.as("Outcome of generating shipments for a selection that matched nothing when it was locked")
				.isInstanceOf(AdempiereException.class)
				.isNotInstanceOf(LockFailedException.class)
				.hasMessage(expectedMessage);
	}

	/**
	 * Locks nothing for a selection filter, as if no record matched the filter at lock time.
	 */
	private static class NothingMatchedAtLockTimeLockManager extends PlainLockManager
	{
		private final PlainLockDatabase lockDatabase = new PlainLockDatabase()
		{
			@Override
			protected int lockByFilters(final ILockCommand lockCommand)
			{
				return 0;
			}
		};

		@Override
		public PlainLockDatabase getLockDatabase()
		{
			return lockDatabase;
		}
	}
}
