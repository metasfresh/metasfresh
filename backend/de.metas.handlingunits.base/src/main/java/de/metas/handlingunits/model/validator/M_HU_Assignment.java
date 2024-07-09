package de.metas.handlingunits.model.validator;

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

import de.metas.cache.model.impl.TableRecordCacheLocal;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.IReference;
import org.adempiere.util.lang.LazyInitializer;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import java.util.HashMap;
import java.util.Map;

@Validator(I_M_HU_Assignment.class)
public class M_HU_Assignment
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void fireHUUnassigned(final I_M_HU_Assignment assignment)
	{
		final IReference<I_M_HU> huRef = new LazyInitializer<I_M_HU>()
		{

			@Override
			protected I_M_HU initialize()
			{
				return assignment.getM_HU();
			}
		};
		final IReference<Object> modelRef = new LazyInitializer<Object>()
		{

			@Override
			protected Object initialize()
			{
				return TableRecordCacheLocal.getReferencedValue(assignment, Object.class);
			}
		};

		final String trxName = InterfaceWrapperHelper.getTrxName(assignment);

		//
		// Notify listeners
		Services.get(IHUAssignmentBL.class)
				.getHUAssignmentListeners()
				.onHUUnassigned(huRef, modelRef, trxName);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void assertOnlyOneAssignmentPerInOut(final I_M_HU_Assignment assignment)
	{
		// Make sure we are dealing with M_InOutLine assignments
		if (!Services.get(IADTableDAO.class).isTableId(org.compiere.model.I_M_InOutLine.Table_Name, assignment.getAD_Table_ID()))
		{
			return;
		}
		final int inoutLineTableId = assignment.getAD_Table_ID(); // i.e. M_InOutLine's AD_Table_ID

		// Consider only those assignments which are about PM transfer too
		if (!assignment.isTransferPackingMaterials())
		{
			return;
		}

		//
		// Retrieve all inouts on which the HU from given assignment is assigned
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(assignment);
		final I_M_HU hu = assignment.getM_HU();
		final Map<Integer, I_M_InOut> inouts = Services.get(IHUAssignmentDAO.class)
				.retrieveTableHUAssignmentsQuery(contextProvider, inoutLineTableId, hu)
				// Consider only those assignments which are about PM transfer too,
				// because an HU can be assignment multiple shipments (partial assignments) but only one will get the packing material.
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_IsTransferPackingMaterials, true)
				.andCollect(I_M_HU_Assignment.COLUMN_Record_ID, I_M_InOutLine.class)
				.andCollect(org.compiere.model.I_M_InOutLine.COLUMN_M_InOut_ID)
				.create()
				.mapById(I_M_InOut.class);

		// If it's a new assignment, then it's not yet saved in database,
		// so we need to add its inout to our evaluation map
		final boolean isNewAssigment = InterfaceWrapperHelper.isNew(assignment);
		if (isNewAssigment)
		{
			final I_M_InOutLine inoutLine = TableRecordCacheLocal.getReferencedValue(assignment, I_M_InOutLine.class);
			final I_M_InOut inout = inoutLine.getM_InOut();
			inouts.put(inout.getM_InOut_ID(), inout);
		}

		//
		// Group retrieved inouts by their unique segment and make sure we have only one inout for each unique segment.
		final Map<ArrayKey, Map<Integer, I_M_InOut>> uniqueSegment2inouts = new HashMap<>();
		for (final I_M_InOut inout : inouts.values())
		{
			final I_C_DocType docTypeRecord = Services.get(IDocTypeDAO.class).getRecordById(inout.getC_DocType_ID());
			// Create the unique segment where current inout shall be placed
			final ArrayKey uniqueSegment = Util.mkKey(
					inout.isSOTrx(), // Validation shall only take place if both InOuts are shipments or receipts
					docTypeRecord.getDocBaseType() // ..and when both inouts have the same base type (e.g. an HU may be received with BaseType MMR and send again with MMS)
					);

			// Add current inout to its unique segment's list
			final Map<Integer, I_M_InOut> inoutsForSegment = uniqueSegment2inouts.computeIfAbsent(uniqueSegment, k -> new HashMap<>());
			inoutsForSegment.put(inout.getM_InOut_ID(), inout);

			// Make sure we have only one inout per segment
			Check.assume(inoutsForSegment.size() <= 1,
					"A Handling Unit can only be assigned to ONE shipment/receipt."
							+ "\n @M_HU_ID@: {}"
							+ "\n uniqueSegment: {}"
							+ "\n @M_InOut_ID@ / segment: {}",
					hu, uniqueSegment, inoutsForSegment);
		}
	}
}
