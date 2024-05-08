package de.metas.handlingunits.expectations;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.test.ErrorMessage;
import org.compiere.model.IQuery;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Expect to have a {@link I_M_HU_Assignment} record.
 *
 * @author tsa
 *
 * @param <ParentExpectationType>
 */
public class HUAssignmentExpectation<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	public static HUAssignmentExpectation<Object> newExpectation()
	{
		return new HUAssignmentExpectation<>();
	}

	private int tableId = -1;
	private int recordId = -1;
	private I_M_HU hu;
	private I_M_HU luHU;
	private I_M_HU tuHU;
	private int huAssignmentsCount = 1;

	public HUAssignmentExpectation()
	{
		super();
	}

	public HUAssignmentExpectation(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	/**
	 * Assert that an {@link I_M_HU_Assignment} exist for given criterias.
	 *
	 * @param message
	 * @return this
	 */
	public HUAssignmentExpectation<ParentExpectationType> assertExpected(final ErrorMessage message)
	{
		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Assignment.class, getContext());

		// Table/Record
		queryBuilder.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, tableId);
		queryBuilder.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, recordId);

		Check.assumeNotNull(hu, "hu not null");
		final Integer huId = hu == null ? null : hu.getM_HU_ID();
		queryBuilder.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_HU_ID, huId);

		final Integer luHUId = luHU == null ? null : luHU.getM_HU_ID();
		queryBuilder.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_LU_HU_ID, luHUId);

		final Integer tuHUId = tuHU == null ? null : tuHU.getM_HU_ID();
		queryBuilder.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_TU_HU_ID, tuHUId);

		//
		// Query database and retrieve the HU assignments for our criterias
		final IQuery<I_M_HU_Assignment> huAssignmentsQuery = queryBuilder.create();
		final List<I_M_HU_Assignment> huAssignments = huAssignmentsQuery.list(I_M_HU_Assignment.class);

		final ErrorMessage messageToUse = derive(message)
				.addContextInfo("HU Assignments Query", huAssignmentsQuery)
				.addContextInfo("HU Assignments Count", huAssignments.size())
				.addContextInfo("HU Assignments", huAssignments);

		//
		// Make sure we got the expected number of assignments
		assertEquals(messageToUse.expect("Invalid HU Assignments count"), huAssignmentsCount, huAssignments.size());

		return this;
	}

	public HUAssignmentExpectation<ParentExpectationType> assertExpected(final String message)
	{
		return assertExpected(newErrorMessage(message));
	}

	public HUAssignmentExpectation<ParentExpectationType> tableId(final String tableName)
	{
		this.tableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		return this;
	}

	public HUAssignmentExpectation<ParentExpectationType> recordId(final int recordId)
	{
		this.recordId = recordId;
		return this;
	}

	public HUAssignmentExpectation<ParentExpectationType> tableAndRecordIdFromModel(final Object model)
	{
		Check.assumeNotNull(model, "model not null");
		this.tableId = InterfaceWrapperHelper.getModelTableId(model);
		this.recordId = InterfaceWrapperHelper.getId(model);
		return this;
	}

	public HUAssignmentExpectation<ParentExpectationType> hu(final I_M_HU hu)
	{
		this.hu = hu;
		return this;
	}

	public HUAssignmentExpectation<ParentExpectationType> luHU(final I_M_HU luHU)
	{
		this.luHU = luHU;
		return this;
	}

	public HUAssignmentExpectation<ParentExpectationType> tuHU(final I_M_HU tuHU)
	{
		this.tuHU = tuHU;
		return this;
	}

	public void huAssignmentsCount(final int huAssignmentsCount)
	{
		this.huAssignmentsCount = huAssignmentsCount;
	}
}
