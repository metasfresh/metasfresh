package de.metas.handlingunits.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IReference;
import org.adempiere.util.lang.ImmutableReference;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentBuilder;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUAssignmentListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;

public class HUAssignmentBL implements IHUAssignmentBL
{
	private final CompositeHUAssignmentListener listeners = new CompositeHUAssignmentListener();

	/** Marker used to specify that we don't want to change the IsTransferPackingMaterials flag */
	private static final Boolean IsTransferPackingMaterials_DoNotChange = null;

	public HUAssignmentBL()
	{
		super();

		//
		// Register Standard listener, which will be applied first, in ALL cases
		registerHUAssignmentListener(StandardHUAssignmentListener.instance);
	}

	@Override
	public void registerHUAssignmentListener(final IHUAssignmentListener listener)
	{
		listeners.addHUAssignmentListener(listener);
	}

	@Override
	public IHUAssignmentListener getHUAssignmentListeners()
	{
		return listeners;
	}

	@Override
	public void assignHUs(final Object model, final Collection<I_M_HU> huList)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(model);
		assignHUs(model, huList, trxName);
	}

	@Override
	public void assignHUs(final Object model, final Collection<I_M_HU> huList, final String trxName)
	{
		Check.assumeNotNull(model, "model not null");
		Check.assumeNotNull(huList, "huList not null");
		if (huList.isEmpty())
		{
			// Nothing to do.
			return;
		}

		huList.forEach(hu -> assignHU(model, hu, trxName));
	}

	@Override
	public I_M_HU_Assignment assignHU(final Object model, final I_M_HU hu, final String trxName)
	{
		return assignHU0(model, hu, IsTransferPackingMaterials_DoNotChange, trxName);
	}

	@Override
	public I_M_HU_Assignment assignHU(final Object model, final I_M_HU hu, final boolean isTransferPackingMaterials, final String trxName)
	{
		return assignHU0(model, hu, isTransferPackingMaterials, trxName);
	}

	private I_M_HU_Assignment assignHU0(final Object model, final I_M_HU hu, final Boolean isTransferPackingMaterials, final String trxName)
	{
		Check.assumeNotNull(model, "model not null");
		Check.assumeNotNull(hu, "hu not null");

		//
		// Validate the HU
		final int huId = hu.getM_HU_ID();
		Check.assume(huId > 0, "huId > 0");

		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);

		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

		final IHUAssignmentBuilder builder = createHUAssignmentBuilder();

		final I_M_HU_Assignment assignment = huAssignmentDAO.retrieveHUAssignmentOrNull(ctx, huId, adTableId, recordId, trxName);
		if (assignment == null)
		{
			builder.initializeAssignment(ctx, trxName); // create a new I_M_HU_Assignment
		}
		else
		{
			builder.initializeAssignment(assignment); // update the existing I_M_HU_Assignment
		}

		updateHUAssignment(builder, hu, model, isTransferPackingMaterials, trxName);

		return builder.build();
	}

	@Override
	public IHUAssignmentBuilder createTradingUnitDerivedAssignmentBuilder(final Properties ctx, final Object model, final I_M_HU topLevelHU, final I_M_HU luHU, final I_M_HU tuHU, final String trxName)
	{
		//
		// Create & initialize builder
		final IHUAssignmentBuilder builder = createHUAssignmentBuilder();
		builder.initializeAssignment(ctx, trxName);

		builder.setIsActive(true);
		builder.setIsTransferPackingMaterials(false);

		builder.setModel(model);

		builder.setTopLevelHU(topLevelHU);

		builder.setM_LU_HU(luHU);
		builder.setM_TU_HU(tuHU);

		return builder;
	}

	private void updateHUAssignment(final IHUAssignmentBuilder builder, final I_M_HU hu, final Object model, final Boolean isTransferPackingMaterials, final String trxName)
	{
		//
		// Make sure HU is assignable to given model
		listeners.assertAssignable(hu, model, trxName);

		final boolean isNewAssignment = builder.isNewAssignment();
		final boolean isActiveOld = builder.isActive();

		//
		// Update Assignment fields and save it
		updateHUAssignmentAndSave(builder, model, hu, isTransferPackingMaterials);

		//
		// Check if this update was an assignment or an unassignment and fire listeners accordingly
		final boolean isActiveNew = builder.isActive();
		final Boolean isAssignment;
		if (isNewAssignment)
		{
			isAssignment = isActiveNew;
		}
		else if (isActiveOld != isActiveNew)
		{
			isAssignment = isActiveNew;
		}
		else
		{
			// not a new record, IsActive flag did not changed => not an assignment nor an unassignment
			isAssignment = null;
		}

		//
		// Fire assign/unassign events
		if (isAssignment == null)
		{
			// not an assignment nor an unassignment => no event to trigger
		}
		else if (isAssignment)
		{
			listeners.onHUAssigned(hu, model, trxName);
		}
		else
		{
			final IReference<I_M_HU> huRef = ImmutableReference.valueOf(hu);
			final IReference<Object> modelRef = ImmutableReference.valueOf(model);
			listeners.onHUUnassigned(huRef, modelRef, trxName);
		}
	}

	/**
	 * @param builder
	 * @param model
	 * @param topLevelHU
	 * @param isTransferPackingMaterials
	 * @return assignment which was built / saved
	 */
	private I_M_HU_Assignment updateHUAssignmentAndSave(final IHUAssignmentBuilder builder, final Object model, final I_M_HU topLevelHU, final Boolean isTransferPackingMaterials)
	{
		builder.setModel(model);
		builder.setIsActive(true);
		if (isTransferPackingMaterials != null)
		{
			builder.setIsTransferPackingMaterials(isTransferPackingMaterials);
		}

		builder.setTopLevelHU(topLevelHU);

		return builder.build();
	}

	@Override
	public void unassignAllHUs(final Object model, final String trxName)
	{
		final List<I_M_HU> handlingUnits = Collections.emptyList();
		final boolean deleteOld = true; // delete all which are not present in handlingUnits list
		setAssignedHandlingUnits(model, handlingUnits, deleteOld, trxName);
	}

	@Override
	public void unassignAllHUs(final Object model)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(model);
		unassignAllHUs(model, trxName);
	}

	@Override
	public void setAssignedHandlingUnits(final Object model, final Collection<I_M_HU> handlingUnits)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(model);
		setAssignedHandlingUnits(model, handlingUnits, trxName);
	}

	@Override
	public void setAssignedHandlingUnits(final Object model, final Collection<I_M_HU> handlingUnits, final String trxName)
	{
		final boolean deleteOld = true; // delete all which are not present in handlingUnits list
		setAssignedHandlingUnits(model, handlingUnits, deleteOld, trxName);
	}

	/**
	 *
	 * @param model
	 * @param handlingUnits handling units to assign
	 * @param deleteOld if true, assigned HUs which are not found in <code>handlingUnits</code> will be deleted
	 * @param trxName transaction name
	 */
	private void setAssignedHandlingUnits(final Object model, final Collection<I_M_HU> handlingUnits, final boolean deleteOld, final String trxName)
	{
		Check.assumeNotNull(model, "model not null");
		Check.assumeNotNull(handlingUnits, "handlingUnits not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);

		//
		// Build the HU to assign map (M_HU_ID -> M_HU)
		final Map<Integer, I_M_HU> huId2hu = new HashMap<Integer, I_M_HU>(handlingUnits.size());
		for (final I_M_HU hu : handlingUnits)
		{
			final int huId = hu.getM_HU_ID();
			huId2hu.put(huId, hu);
		}

		//
		// Iterate current assignments and:
		// * delete those which are not assignments anymore
		// * update those which are still assignments
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		final List<I_M_HU_Assignment> assignmentsExisting = huAssignmentDAO.retrieveHUAssignmentsForModel(ctx, adTableId, recordId, trxName);
		for (final I_M_HU_Assignment assignment : assignmentsExisting)
		{
			final int huId = assignment.getM_HU_ID();

			// NOTE: we are deleting the HU from map because at the end we want to have in that map only those HUs which we still need to assign
			final I_M_HU hu = huId2hu.remove(huId);

			if (hu != null)
			{
				// existing assignment, keep it but update it (just in case)
				final IHUAssignmentBuilder builder = createHUAssignmentBuilder();
				builder.initializeAssignment(assignment);

				updateHUAssignment(builder, hu, model, IsTransferPackingMaterials_DoNotChange, trxName);
			}
			else
			{
				// not an assignment anymore, delete it
				InterfaceWrapperHelper.delete(assignment);

				// delete included assignments too
				final List<I_M_HU_Assignment> includedHUAssignments = huAssignmentDAO.retrieveIncludedHUAssignments(assignment);
				for (final I_M_HU_Assignment includedHUAssignment : includedHUAssignments)
				{
					InterfaceWrapperHelper.delete(includedHUAssignment);
				}
			}
		}

		//
		// Create assignments for remaining HUs
		for (final I_M_HU hu : huId2hu.values())
		{
			// Create a new empty assignment
			final IHUAssignmentBuilder builder = createHUAssignmentBuilder();
			builder.initializeAssignment(ctx, trxName);

			builder.setIsActive(true);
			builder.setIsTransferPackingMaterials(true); // backward compatibility

			// Ask "assignHU" to update it, save it and trigger events
			updateHUAssignment(builder, hu, model, IsTransferPackingMaterials_DoNotChange, trxName);
		}
	}

	@Override
	public void unassignHUs(final Object model, final Collection<I_M_HU> husToUnassign, final String trxName)
	{
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		huAssignmentDAO.deleteHUAssignments(model, husToUnassign, trxName);
	}

	@Override
	public void unassignHUs(final Object model, final Collection<I_M_HU> husToUnassign)
	{
		Check.assumeNotNull(model, "model not null");
		final String trxName = InterfaceWrapperHelper.getTrxName(model);
		unassignHUs(model, husToUnassign, trxName);
	}

	@Override
	public IHUAssignmentBuilder createHUAssignmentBuilder()
	{
		return new HUAssignmentBuilder();
	}
}
