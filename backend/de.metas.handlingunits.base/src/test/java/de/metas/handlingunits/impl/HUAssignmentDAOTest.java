package de.metas.handlingunits.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_InOutLine;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUAssignmentDAO.HuAssignment;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.util.collections.CollectionUtils;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class HUAssignmentDAOTest
{
	private HUAssignmentDAO huAssignmentDAO;
	private I_M_InOutLine inoutLine;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		huAssignmentDAO = new HUAssignmentDAO();

		inoutLine = newInstance(I_M_InOutLine.class);
		saveRecord(inoutLine);
	}

	@Test
	public void retrieveHUAssignmentsForModel()
	{
		final I_M_HU tu = newInstance(I_M_HU.class);
		saveRecord(tu);

		final I_M_HU vhu = newInstance(I_M_HU.class);
		saveRecord(vhu);

		final I_M_HU_Assignment vhuAssignment = newInstance(I_M_HU_Assignment.class);
		vhuAssignment.setAD_Table_ID(getTableId(I_M_InOutLine.class));
		vhuAssignment.setRecord_ID(inoutLine.getM_InOutLine_ID());
		vhuAssignment.setM_TU_HU(tu);
		vhuAssignment.setVHU(vhu);
		saveRecord(vhuAssignment);

		final List<HuAssignment> results = huAssignmentDAO.retrieveLowLevelHUAssignmentsForModel(inoutLine);
		assertThat(results).hasSize(1);
		assertThat(results.get(0).getLowestLevelHU().getM_HU_ID()).isEqualTo(vhu.getM_HU_ID());
	}

	@Test
	public void retrieveOrderedHUAssignmentRecords_2()
	{
		final List<I_M_HU_Assignment> huAssignments = createTopLevelAndTUAssignenments();
		final ImmutableList<Integer> expectedAssignmentIds = CollectionUtils.extractDistinctElements(huAssignments, I_M_HU_Assignment::getM_HU_Assignment_ID);
		//final int expectedTuId = huAssignments.get(0).getM_TU_HU_ID();
		// invoke the method under test
		final List<I_M_HU_Assignment> result = huAssignmentDAO.retrieveOrderedHUAssignmentRecords(inoutLine);

		final ImmutableList<Integer> resultAssignmentIds = CollectionUtils.extractDistinctElements(result, I_M_HU_Assignment::getM_HU_Assignment_ID);

		assertThat(resultAssignmentIds).containsExactlyElementsOf(expectedAssignmentIds);
	}

	@Test
	public void retrieveHUAssignmentsForModel_2()
	{
		final List<I_M_HU_Assignment> huAssignments = createTopLevelAndTUAssignenments();
		final I_M_HU tu = huAssignments.get(0).getM_TU_HU();

		// invoke the method under test
		final List<HuAssignment> results = huAssignmentDAO.retrieveLowLevelHUAssignmentsForModel(inoutLine);

		assertThat(results).hasSize(1);
		assertThat(results.get(0).getLowestLevelHU().getM_HU_ID()).isEqualTo(tu.getM_HU_ID());
	}

	/** @return assignments in the order expected by {@link HUAssignmentDAO#retrieveOrderedHUAssignmentRecords(Object)}. */
	private List<I_M_HU_Assignment> createTopLevelAndTUAssignenments()
	{
		final I_M_HU lu = newInstance(I_M_HU.class);
		saveRecord(lu);

		final I_M_HU tu = newInstance(I_M_HU.class);
		saveRecord(tu);

		final I_M_HU_Assignment assignment = newInstance(I_M_HU_Assignment.class);
		assignment.setAD_Table_ID(getTableId(I_M_InOutLine.class));
		assignment.setRecord_ID(inoutLine.getM_InOutLine_ID());
		assignment.setM_HU(lu);
		assignment.setM_LU_HU(lu);
		assignment.setM_TU_HU(tu);
		saveRecord(assignment);

		final I_M_HU_Assignment topLevelAssignment = newInstance(I_M_HU_Assignment.class);
		topLevelAssignment.setAD_Table_ID(getTableId(I_M_InOutLine.class));
		topLevelAssignment.setRecord_ID(inoutLine.getM_InOutLine_ID());
		topLevelAssignment.setM_HU(lu);
		saveRecord(topLevelAssignment);

		return ImmutableList.of(assignment, topLevelAssignment);
	}

	@Test
	public void retrieveHUAssignmentsForModel_3()
	{
		final I_M_HU lu = newInstance(I_M_HU.class);
		saveRecord(lu);

		final I_M_HU tu = newInstance(I_M_HU.class);
		saveRecord(tu);

		final I_M_HU_Assignment assignment = newInstance(I_M_HU_Assignment.class);
		assignment.setAD_Table_ID(getTableId(I_M_InOutLine.class));
		assignment.setRecord_ID(inoutLine.getM_InOutLine_ID());
		assignment.setM_HU(lu);
		assignment.setM_LU_HU(lu);
		assignment.setM_TU_HU(tu);
		saveRecord(assignment);

		final List<HuAssignment> results = huAssignmentDAO.retrieveLowLevelHUAssignmentsForModel(inoutLine);
		assertThat(results).hasSize(1);
		assertThat(results.get(0).getLowestLevelHU().getM_HU_ID()).isEqualTo(tu.getM_HU_ID());
	}

	/**
	 * Three M_HU_Assignments; one "top-level", two with LU, TU and VHU, where only the VHU differs.<br>
	 * Expectation: we get two HuAssignment
	 */
	@Test
	public void retrieveHUAssignmentsForModel_4()
	{
		final I_M_HU lu = newInstance(I_M_HU.class);
		saveRecord(lu);

		final I_M_HU tu = newInstance(I_M_HU.class);
		saveRecord(tu);

		final I_M_HU vhu1 = newInstance(I_M_HU.class);
		saveRecord(vhu1);

		final I_M_HU_Assignment assignment = newInstance(I_M_HU_Assignment.class);
		assignment.setAD_Table_ID(getTableId(I_M_InOutLine.class));
		assignment.setRecord_ID(inoutLine.getM_InOutLine_ID());
		assignment.setM_HU(lu);
		assignment.setM_LU_HU(lu);
		assignment.setM_TU_HU(tu);
		assignment.setVHU(vhu1);
		saveRecord(assignment);

		final I_M_HU vhu2 = newInstance(I_M_HU.class);
		saveRecord(vhu2);

		final I_M_HU_Assignment assignment2 = newInstance(I_M_HU_Assignment.class);
		assignment2.setAD_Table_ID(getTableId(I_M_InOutLine.class));
		assignment2.setRecord_ID(inoutLine.getM_InOutLine_ID());
		assignment2.setM_HU(lu);
		assignment2.setM_LU_HU(lu);
		assignment2.setM_TU_HU(tu);
		assignment2.setVHU(vhu2);
		saveRecord(assignment2);

		final I_M_HU_Assignment topLevelAssignment = newInstance(I_M_HU_Assignment.class);
		topLevelAssignment.setAD_Table_ID(getTableId(I_M_InOutLine.class));
		topLevelAssignment.setRecord_ID(inoutLine.getM_InOutLine_ID());
		topLevelAssignment.setM_HU(lu);
		saveRecord(topLevelAssignment);

		final List<HuAssignment> results = huAssignmentDAO.retrieveLowLevelHUAssignmentsForModel(inoutLine);
		assertThat(results).hasSize(2);
		assertThat(results.get(1).getLowestLevelHU().getM_HU_ID()).isEqualTo(vhu1.getM_HU_ID());
		assertThat(results.get(0).getLowestLevelHU().getM_HU_ID()).isEqualTo(vhu2.getM_HU_ID());
	}
}
