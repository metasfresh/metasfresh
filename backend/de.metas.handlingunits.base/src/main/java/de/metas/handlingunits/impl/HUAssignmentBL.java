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

import com.google.common.collect.ImmutableSetMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentBuilder;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUAssignmentListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IReference;
import org.adempiere.util.lang.ImmutableReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class HUAssignmentBL implements IHUAssignmentBL
{
	private final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final CompositeHUAssignmentListener listeners = new CompositeHUAssignmentListener();

	/**
	 * Marker used to specify that we don't want to change the IsTransferPackingMaterials flag
	 */
	private static final Boolean IsTransferPackingMaterials_DoNotChange = null;

	public HUAssignmentBL()
	{
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

	private I_M_HU_Assignment assignHU0(
			@NonNull final Object model,
			@NonNull final I_M_HU hu,
			@Nullable final Boolean isTransferPackingMaterials,
			final String trxName)
	{
		// Validate the HU
		final int huId = Check.assumeGreaterThanZero(hu.getM_HU_ID(), "hu.getM_HU_ID()");

		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);

		final IHUAssignmentBuilder builder = createHUAssignmentBuilder();

		final I_M_HU_Assignment assignment = huAssignmentDAO.retrieveHUAssignmentOrNull(ctx, huId, adTableId, recordId, trxName);
		if (assignment == null)
		{
			builder.initializeAssignment(ctx, trxName); // create a new I_M_HU_Assignment
		}
		else
		{
			builder.setAssignmentRecordToUpdate(assignment); // going to update the existing I_M_HU_Assignment
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

	private void updateHUAssignment(final IHUAssignmentBuilder builder, final I_M_HU hu, final Object model, @Nullable final Boolean isTransferPackingMaterials, final String trxName)
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
	 *
	 */
	private void updateHUAssignmentAndSave(final IHUAssignmentBuilder builder, final Object model, final I_M_HU topLevelHU, @Nullable final Boolean isTransferPackingMaterials)
	{
		builder.setModel(model);
		builder.setIsActive(true);
		if (isTransferPackingMaterials != null)
		{
			builder.setIsTransferPackingMaterials(isTransferPackingMaterials);
		}

		builder.setTopLevelHU(topLevelHU);

		builder.build();
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
		final boolean deleteOld = true; // delete all which are not present in handlingUnits list
		setAssignedHandlingUnits(model, handlingUnits, deleteOld, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	public void addAssignedHandlingUnits(final Object model, final Collection<I_M_HU> handlingUnits)
	{
		final boolean deleteOld = false;
		setAssignedHandlingUnits(model, handlingUnits, deleteOld, ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * @param model         model
	 * @param handlingUnits handling units to assign
	 * @param deleteOld     if true, assigned HUs which are not found in <code>handlingUnits</code> will be deleted
	 * @param trxName       transaction name
	 */
	private void setAssignedHandlingUnits(
			@NonNull final Object model,
			@NonNull final Collection<I_M_HU> handlingUnits,
			final boolean deleteOld,
			final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);

		//
		// Build the HU to assign map (M_HU_ID -> M_HU)
		final Map<Integer, I_M_HU> huId2hu = new HashMap<>(handlingUnits.size());
		for (final I_M_HU hu : handlingUnits)
		{
			final int huId = hu.getM_HU_ID();
			huId2hu.put(huId, hu);
		}

		//
		// Iterate current assignments and:
		// * delete those which are not assignments anymore
		// * update those which are still assignments
		final List<I_M_HU_Assignment> assignmentsExisting = huAssignmentDAO.retrieveHUAssignmentsForModel(ctx, adTableId, recordId, trxName);
		for (final I_M_HU_Assignment assignment : assignmentsExisting)
		{
			final int huId = assignment.getM_HU_ID();

			// NOTE: we are deleting the HU from map because at the end we want to have in that map only those HUs which we still need to assign
			final I_M_HU hu = huId2hu.remove(huId);

			// Case: existing assignment, keep it but update it (just in case)
			if (hu != null)
			{
				final IHUAssignmentBuilder builder = createHUAssignmentBuilder();
				builder.setAssignmentRecordToUpdate(assignment);

				updateHUAssignment(builder, hu, model, IsTransferPackingMaterials_DoNotChange, trxName);
			}
			// Case: not an assignment anymore and deleteOld=true
			// => delete the assignment
			else if (deleteOld)
			{
				InterfaceWrapperHelper.delete(assignment);

				// delete included assignments too
				final List<I_M_HU_Assignment> includedHUAssignments = huAssignmentDAO.retrieveIncludedHUAssignments(assignment);
				for (final I_M_HU_Assignment includedHUAssignment : includedHUAssignments)
				{
					InterfaceWrapperHelper.delete(includedHUAssignment);
				}
			}
			// Case: not an assignment anymore and deleteOld=false
			// => do nothing, preserve the assignment
			// else
			// {
			// }
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
	public void unassignHUs(@NonNull final Object model, @NonNull final Collection<HuId> husToUnassign, final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final TableRecordReference modelRef = TableRecordReference.of(model);
		huAssignmentDAO.deleteHUAssignments(ctx, modelRef, husToUnassign, trxName);
	}

	@Override
	public void unassignHUs(@NonNull final TableRecordReference modelRef, @NonNull final Collection<HuId> husToUnassign)
	{
		huAssignmentDAO.deleteHUAssignments(Env.getCtx(), modelRef, husToUnassign, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	public IHUAssignmentBuilder createHUAssignmentBuilder()
	{
		return new HUAssignmentBuilder();
	}

	@Override
	public void copyHUAssignments(
			@NonNull final Object sourceModel,
			@NonNull final Object targetModel)
	{
		final List<I_M_HU_Assignment> //
				huAssignmentsForSource = huAssignmentDAO.retrieveTopLevelHUAssignmentsForModel(sourceModel);

		for (final I_M_HU_Assignment huAssignment : huAssignmentsForSource)
		{
			createHUAssignmentBuilder()
					.setTemplateForNewRecord(huAssignment)
					.setModel(targetModel)
					.build();
		}
	}

	@Override
	public ImmutableSetMultimap<TableRecordReference, HuId> getHUsByRecordRefs(@NonNull final Set<TableRecordReference> recordRefs)
	{
		return huAssignmentDAO.retrieveHUsByRecordRefs(recordRefs);
	}
}
