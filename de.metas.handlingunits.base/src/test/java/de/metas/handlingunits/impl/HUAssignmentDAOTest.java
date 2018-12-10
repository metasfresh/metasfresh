package de.metas.handlingunits.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_InOutLine;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.IHUAssignmentDAO.HuAssignment;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;

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

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		huAssignmentDAO = new HUAssignmentDAO();
	}

	@Test
	public void retrieveHUAssignmentsForModel()
	{
		final I_M_InOutLine inoutLine = newInstance(I_M_InOutLine.class);
		saveRecord(inoutLine);

		final I_M_HU tu = newInstance(I_M_HU.class);
		saveRecord(tu);

		final I_M_HU vhu = newInstance(I_M_HU.class);
		saveRecord(vhu);

		final I_M_HU_Assignment vhuAssignment = newInstance(I_M_HU_Assignment.class);
		vhuAssignment.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_M_InOutLine.class));
		vhuAssignment.setRecord_ID(inoutLine.getM_InOutLine_ID());
		vhuAssignment.setM_TU_HU(tu);
		vhuAssignment.setVHU(vhu);
		saveRecord(vhuAssignment);

		final List<HuAssignment> results = huAssignmentDAO.retrieveLowLevelHUAssignmentsForModel(inoutLine);
		assertThat(results).hasSize(1);
		assertThat(results.get(0).getLowestLevelHU().getM_HU_ID()).isEqualTo(vhu.getM_HU_ID());
	}
}
