package de.metas.handlingunits;

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
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.util.lang.IReference;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.Collection;
import java.util.Properties;
import java.util.Set;

public interface IHUAssignmentBL extends ISingletonService
{
	/**
	 * Register an {@link IHUAssignmentListener}.
	 */
	void registerHUAssignmentListener(IHUAssignmentListener listener);

	/**
	 * Gets current {@link IHUAssignmentListener}s.
	 * <p>
	 * NOTE: don't use it directly, it's used by API
	 *
	 * @return composite {@link IHUAssignmentListener}s
	 */
	IHUAssignmentListener getHUAssignmentListeners();

	/**
	 * Assign given HUs <b>as top level HUs</b>. This means, create {@link I_M_HU_Assignment} records and set their {@code M_HU_ID} to the given HUs' IDs.
	 * <p>
	 * NOTE: model's trxName will be used.
	 */
	void assignHUs(Object model, Collection<I_M_HU> huList);

	/**
	 * See {@link #assignHUs(Object, Collection)}.
	 */
	void assignHUs(Object model, Collection<I_M_HU> huList, final String trxName);

	/**
	 * Assign given HU <b>as top level HU</b>.
	 *
	 * @return created/updated {@link I_M_HU_Assignment}.
	 */
	I_M_HU_Assignment assignHU(Object model, I_M_HU hu, final String trxName);

	/**
	 * Assign given HU <b>as top level HU</b>.
	 */
	I_M_HU_Assignment assignHU(Object model, I_M_HU hu, boolean isTransferPackingMaterials, String trxName);

	/**
	 * Create handling unit assignment builder for given loading / trading unit(s) of the top-level HU
	 */
	IHUAssignmentBuilder createTradingUnitDerivedAssignmentBuilder(Properties ctx, Object model, I_M_HU topLevelHU, I_M_HU luHU, I_M_HU tuHU, String trxName);

	/**
	 * Deletes existing links between a specific <code>model</code> and it's existing handling units.
	 */
	void setAssignedHandlingUnits(Object model, Collection<I_M_HU> handlingUnits);

	/**
	 * Unassign all HUs which are currently assigned to given <code>model</code>.
	 */
	void unassignAllHUs(Object model, String trxName);

	/**
	 * Unassign all HUs which are currently assigned to given <code>model</code>.
	 * <p>
	 * Model's transaction will be used.
	 */
	void unassignAllHUs(Object model);

	/**
	 * Unassigns the given <code>hus</code> from the given <code>model</code> by deleting the respective {@link I_M_HU_Assignment} records and then calls
	 * {@link IHUAssignmentListener#onHUUnassigned(IReference, IReference, String)}  for all registered listeners. Note that for HUs with <code>M_HU_ID <= 0</code> no unassignment is attempted, and the
	 * listeners are not notified.
	 */
	void unassignHUs(Object model, Collection<I_M_HU> husToUnassign);

	void addAssignedHandlingUnits(Object model, Collection<I_M_HU> handlingUnits);

	/**
	 * Unassign given HUs.
	 */
	void unassignHUs(Object model, Collection<I_M_HU> husToUnassign, String trxName);

	/**
	 * Create an {@link IHUAssignmentBuilder} to easily decorate and create new {@link I_M_HU_Assignment}s.
	 *
	 * @return builder
	 */
	IHUAssignmentBuilder createHUAssignmentBuilder();

	void copyHUAssignments(Object sourceModel, Object targetModel);

	ImmutableSetMultimap<TableRecordReference, HuId> getHUsByRecordRefs(@NonNull Set<TableRecordReference> recordRefs);
}
