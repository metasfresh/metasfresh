package de.metas.handlingunits.inout.impl;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.inout.impl.ShipmentPackingUnitProjectConflictDetector.Conflict;
import de.metas.handlingunits.inout.impl.ShipmentPackingUnitProjectConflictDetector.Usage;
import de.metas.inout.InOutLineId;
import de.metas.project.ProjectId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_InOut;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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

class ShipmentPackingUnitProjectConflictDetectorTest
{
	private static final InOutLineId LINE_1 = InOutLineId.ofRepoId(1000001);
	private static final InOutLineId LINE_2 = InOutLineId.ofRepoId(1000002);
	private static final InOutLineId LINE_3 = InOutLineId.ofRepoId(1000003);

	private static final ProjectId PROJECT_1 = ProjectId.ofRepoId(2000001);
	private static final ProjectId PROJECT_2 = ProjectId.ofRepoId(2000002);

	private ch.qos.logback.classic.Logger logbackLogger;
	private ListAppender<ILoggingEvent> listAppender;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		logbackLogger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(ShipmentPackingUnitProjectConflictDetector.class);
		listAppender = new ListAppender<>();
		listAppender.start();
		logbackLogger.addAppender(listAppender);
	}

	@AfterEach
	void tearDown()
	{
		logbackLogger.detachAppender(listAppender);
	}

	private static I_M_InOut createShipment(final String documentNo)
	{
		final I_M_InOut shipment = newInstance(I_M_InOut.class);
		shipment.setDocumentNo(documentNo);
		saveRecord(shipment);
		return shipment;
	}

	@Test
	void huSharedByTwoProjects_conflictAndWarning()
	{
		final Usage usage1 = new Usage("HU:1", LINE_1, PROJECT_1, true);
		final Usage usage2 = new Usage("HU:1", LINE_2, PROJECT_2, false);

		final ImmutableList<Conflict> conflicts = ShipmentPackingUnitProjectConflictDetector.detect(ImmutableList.of(usage1, usage2));

		assertThat(conflicts).hasSize(1);
		final Conflict conflict = conflicts.get(0);
		assertThat(conflict.getUnitKey()).isEqualTo("HU:1");
		assertThat(conflict.getProjectIds()).containsExactlyInAnyOrder(PROJECT_1, PROJECT_2);
		assertThat(conflict.getBookedProjectId()).isEqualTo(PROJECT_1);

		final I_M_InOut shipment = createShipment("SHIP-1");
		ShipmentPackingUnitProjectConflictDetector.logWarnings(shipment, conflicts);

		assertThat(listAppender.list).hasSize(1);
		final String message = listAppender.list.get(0).getFormattedMessage();
		assertThat(message).contains("SHIP-1");
		assertThat(message).contains(String.valueOf(shipment.getM_InOut_ID()));
		assertThat(message).contains("HU:1");
		assertThat(message).contains(String.valueOf(PROJECT_1.getRepoId()));
		assertThat(message).contains(String.valueOf(PROJECT_2.getRepoId()));
		assertThat(message).contains("booked to " + PROJECT_1.getRepoId());
	}

	@Test
	void huOnTwoLinesOfSameProject_noConflict()
	{
		final Usage usage1 = new Usage("HU:1", LINE_1, PROJECT_1, false);
		final Usage usage2 = new Usage("HU:1", LINE_2, PROJECT_1, false);

		final ImmutableList<Conflict> conflicts = ShipmentPackingUnitProjectConflictDetector.detect(ImmutableList.of(usage1, usage2));

		assertThat(conflicts).isEmpty();

		final I_M_InOut shipment = createShipment("SHIP-2");
		ShipmentPackingUnitProjectConflictDetector.logWarnings(shipment, conflicts);

		assertThat(listAppender.list).isEmpty();
	}

	@Test
	void defaultPallet_sharedByTwoProjects_conflictAndWarning()
	{
		final Usage usage1 = new Usage("DefaultLU-PI:1000006", LINE_1, PROJECT_1, true);
		final Usage usage2 = new Usage("DefaultLU-PI:1000006", LINE_2, PROJECT_2, false);

		final ImmutableList<Conflict> conflicts = ShipmentPackingUnitProjectConflictDetector.detect(ImmutableList.of(usage1, usage2));

		assertThat(conflicts).hasSize(1);
		final Conflict conflict = conflicts.get(0);
		assertThat(conflict.getUnitKey()).isEqualTo("DefaultLU-PI:1000006");
		assertThat(conflict.getProjectIds()).containsExactlyInAnyOrder(PROJECT_1, PROJECT_2);
		assertThat(conflict.getBookedProjectId()).isEqualTo(PROJECT_1);

		final I_M_InOut shipment = createShipment("SHIP-3");
		ShipmentPackingUnitProjectConflictDetector.logWarnings(shipment, conflicts);

		assertThat(listAppender.list).hasSize(1);
		final String message = listAppender.list.get(0).getFormattedMessage();
		assertThat(message).contains("SHIP-3");
		assertThat(message).contains("DefaultLU-PI:1000006");
		assertThat(message).contains("booked to " + PROJECT_1.getRepoId());
	}

	@Test
	void defaultPallet_oneLineWithNoProject_noConflict()
	{
		final Usage usage1 = new Usage("DefaultLU-PI:1000006", LINE_1, PROJECT_1, false);
		final Usage usage2 = new Usage("DefaultLU-PI:1000006", LINE_2, null, false);

		final ImmutableList<Conflict> conflicts = ShipmentPackingUnitProjectConflictDetector.detect(ImmutableList.of(usage1, usage2));

		assertThat(conflicts).isEmpty();

		final I_M_InOut shipment = createShipment("SHIP-4");
		ShipmentPackingUnitProjectConflictDetector.logWarnings(shipment, conflicts);

		assertThat(listAppender.list).isEmpty();
	}

	@Test
	void huFirstLineNotBooked_secondLineBooked_bookedIsSecondProject()
	{
		final Usage usage1 = new Usage("HU:1", LINE_1, PROJECT_1, false);
		final Usage usage2 = new Usage("HU:1", LINE_2, PROJECT_2, true);

		final ImmutableList<Conflict> conflicts = ShipmentPackingUnitProjectConflictDetector.detect(ImmutableList.of(usage1, usage2));

		assertThat(conflicts).hasSize(1);
		final Conflict conflict = conflicts.get(0);
		assertThat(conflict.getBookedProjectId()).isEqualTo(PROJECT_2);

		final I_M_InOut shipment = createShipment("SHIP-5");
		ShipmentPackingUnitProjectConflictDetector.logWarnings(shipment, conflicts);

		assertThat(listAppender.list).hasSize(1);
		final String message = listAppender.list.get(0).getFormattedMessage();
		assertThat(message).contains("booked to " + PROJECT_2.getRepoId());
	}

	@Test
	void defaultPallet_bookedManualLineHasNoProject_bookedIsNone()
	{
		final Usage usage1 = new Usage("DefaultLU-PI:1000006", LINE_1, null, true);
		final Usage usage2 = new Usage("DefaultLU-PI:1000006", LINE_2, PROJECT_1, false);
		final Usage usage3 = new Usage("DefaultLU-PI:1000006", LINE_3, PROJECT_2, false);

		final ImmutableList<Conflict> conflicts = ShipmentPackingUnitProjectConflictDetector.detect(ImmutableList.of(usage1, usage2, usage3));

		assertThat(conflicts).hasSize(1);
		final Conflict conflict = conflicts.get(0);
		assertThat(conflict.getProjectIds()).containsExactlyInAnyOrder(PROJECT_1, PROJECT_2);
		assertThat(conflict.getBookedProjectId()).isNull();

		final I_M_InOut shipment = createShipment("SHIP-6");
		ShipmentPackingUnitProjectConflictDetector.logWarnings(shipment, conflicts);

		assertThat(listAppender.list).hasSize(1);
		final String message = listAppender.list.get(0).getFormattedMessage();
		assertThat(message).contains("booked to none");
	}
}
