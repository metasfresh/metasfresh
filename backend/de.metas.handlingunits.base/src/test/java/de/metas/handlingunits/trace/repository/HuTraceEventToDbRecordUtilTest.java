package de.metas.handlingunits.trace.repository;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import de.metas.common.util.time.SystemTime;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.model.X_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceEvent;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class HuTraceEventToDbRecordUtilTest
{

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void fromDbRecord_no_id()
	{
		final I_M_HU_Trace dbRecord = createM_HU_Trace();

		final HUTraceEvent trace = HuTraceEventToDbRecordUtil.fromDbRecord(dbRecord);
		assertThat(trace.getHuTraceEventId().isPresent()).isFalse();
	}

	@Test
	public void fromDbRecord_id()
	{
		final I_M_HU_Trace dbRecord = createM_HU_Trace();
		save(dbRecord);

		final HUTraceEvent trace = HuTraceEventToDbRecordUtil.fromDbRecord(dbRecord);
		assertThat(trace.getHuTraceEventId().getAsInt()).isEqualTo(dbRecord.getM_HU_Trace_ID());
	}

	@Test
	public void fromDbRecord_no_doctype()
	{
		final I_M_HU_Trace dbRecord = createM_HU_Trace();

		final HUTraceEvent trace = HuTraceEventToDbRecordUtil.fromDbRecord(dbRecord);
		assertThat(trace.getDocTypeId().isPresent()).isFalse();
	}

	private I_M_HU_Trace createM_HU_Trace()
	{
		final I_M_HU_Trace dbRecord = newInstance(I_M_HU_Trace.class);
		dbRecord.setEventTime(SystemTime.asTimestamp());
		dbRecord.setHUTraceType(X_M_HU_Trace.HUTRACETYPE_MATERIAL_MOVEMENT);
		dbRecord.setVHUStatus(X_M_HU_Trace.VHUSTATUS_Active);
		dbRecord.setVHU_ID(1);
		dbRecord.setM_HU_ID(2);
		dbRecord.setM_Product_ID(3);
		// save(dbRecord); we might nor might not save in the individual tests

		return dbRecord;
	}

}
