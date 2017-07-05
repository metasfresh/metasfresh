package de.metas.handlingunits.trace;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Comparator;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.adempiere.util.time.TimeSource;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.model.I_AD_User;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceEvent.HUTraceEventBuilder;
import de.metas.logging.LogManager;

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

public class HUTraceUtilTests
{
	private HUTraceEventsCreateAndAdd huTraceEventsCreateAndAdd;

	// @Injectable
	// private HUTraceRepository huTraceRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		huTraceEventsCreateAndAdd = new HUTraceEventsCreateAndAdd(new HUTraceRepository());

		LogManager.setLoggerLevel(HUTraceRepository.class, Level.INFO);
	}

	/**
	 * Create two records (AD_Users, but doesn't matter) and different {@link I_M_HU_Assignment} that reference them.
	 */
	@Test
	public void testCreateAndAddEvents()
	{
		final I_AD_User user1 = newInstance(I_AD_User.class);
		user1.setLogin("user");
		user1.setName("we-just-need-some-record-as-a-reference");
		save(user1);

		final I_AD_User user2 = newInstance(I_AD_User.class);
		user2.setLogin("user2");
		user1.setName("we-just-need-some-record-as-a-reference");
		save(user2);

		final I_M_HU luHu11;
		final I_M_HU luHu12;
		final I_M_HU luHu21;
		final I_M_HU luHu22;
		{
			final TableRecordReference ref1 = TableRecordReference.of(user1);

			luHu11 = newInstance(I_M_HU.class);
			save(luHu11);
			final I_M_HU vhu11 = newInstance(I_M_HU.class);
			save(vhu11);
			final I_M_HU_Assignment huAssignment11 = newInstance(I_M_HU_Assignment.class);
			huAssignment11.setM_HU_ID(luHu11.getM_HU_ID());
			huAssignment11.setVHU(vhu11);
			huAssignment11.setAD_Table_ID(ref1.getAD_Table_ID());
			huAssignment11.setRecord_ID(ref1.getRecord_ID());
			save(huAssignment11);

			luHu12 = newInstance(I_M_HU.class);
			save(luHu12);
			final I_M_HU vhu12 = newInstance(I_M_HU.class);
			save(vhu12);
			final I_M_HU_Assignment huAssignment12 = newInstance(I_M_HU_Assignment.class);
			huAssignment12.setM_HU_ID(luHu12.getM_HU_ID());
			huAssignment12.setVHU(vhu12);
			huAssignment12.setAD_Table_ID(ref1.getAD_Table_ID());
			huAssignment12.setRecord_ID(ref1.getRecord_ID());
			save(huAssignment12);

			final TableRecordReference ref2 = TableRecordReference.of(user2);

			luHu21 = newInstance(I_M_HU.class);
			save(luHu21);
			final I_M_HU vhu21 = newInstance(I_M_HU.class);
			save(vhu21);
			final I_M_HU_Assignment huAssignment21 = newInstance(I_M_HU_Assignment.class);
			huAssignment21.setM_HU_ID(luHu21.getM_HU_ID());
			huAssignment21.setVHU(vhu21);
			huAssignment21.setAD_Table_ID(ref2.getAD_Table_ID());
			huAssignment21.setRecord_ID(ref2.getRecord_ID());
			save(huAssignment21);

			luHu22 = newInstance(I_M_HU.class);
			save(luHu22);
			final I_M_HU vhu22 = newInstance(I_M_HU.class);
			save(vhu22);
			final I_M_HU_Assignment huAssignment22 = newInstance(I_M_HU_Assignment.class);
			huAssignment22.setM_HU_ID(luHu22.getM_HU_ID());
			huAssignment22.setVHU(vhu22);
			huAssignment22.setAD_Table_ID(ref2.getAD_Table_ID());
			huAssignment22.setRecord_ID(ref2.getRecord_ID());
			save(huAssignment22);

			// create a 5th assignment that references user2 but has the same HU-ID *and* updated time! as huAssignment22
			SystemTime.setTimeSource(new TimeSource()
			{
				@Override
				public long millis()
				{
					return huAssignment22.getUpdated().getTime();
				}
			});
			final I_M_HU_Assignment huAssignment22double = newInstance(I_M_HU_Assignment.class);
			huAssignment22double.setM_HU_ID(luHu22.getM_HU_ID());
			huAssignment22double.setVHU(vhu22);
			huAssignment22double.setAD_Table_ID(ref1.getAD_Table_ID());
			huAssignment22double.setRecord_ID(ref1.getRecord_ID());
			save(huAssignment22double);
			
			SystemTime.resetTimeSource();
		}

		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.inOutId(12).type(HUTraceType.MATERIAL_SHIPMENT); // note: inOutId and type don't really matter for this test

		huTraceEventsCreateAndAdd.createAndAddEvents(builder, ImmutableList.of(user1, user2));

		final List<I_M_HU_Trace> allDBRecords = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Trace.class)
				.create().list();

		assertThat(allDBRecords.size(), is(4)); // there shall be no record for the 5th assignment.
		allDBRecords.sort(Comparator.comparing(I_M_HU_Trace::getM_HU_ID));
		assertThat(allDBRecords.get(0).getM_HU_ID(), is(luHu11.getM_HU_ID()));
		assertThat(allDBRecords.get(1).getM_HU_ID(), is(luHu12.getM_HU_ID()));
		assertThat(allDBRecords.get(2).getM_HU_ID(), is(luHu21.getM_HU_ID()));
		assertThat(allDBRecords.get(3).getM_HU_ID(), is(luHu22.getM_HU_ID()));
	}
}
