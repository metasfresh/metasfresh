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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collection;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;

public interface IHUAssignmentBL extends ISingletonService
{
	/**
	 * Register an {@link IHUAssignmentListener}.
	 *
	 * @param listener
	 */
	void registerHUAssignmentListener(IHUAssignmentListener listener);

	/**
	 * Gets current {@link IHUAssignmentListener}s.
	 *
	 * NOTE: don't use it directly, it's used by API
	 *
	 * @return composite {@link IHUAssignmentListener}s
	 */
	IHUAssignmentListener getHUAssignmentListeners();

	/**
	 * Assign given HUs <b>as top level HUs</b>
	 *
	 * NOTE: old assignments will NOT be touched.
	 *
	 * NOTE: model's trxName will be used.
	 *
	 * @param model
	 * @param huList
	 */
	void assignHUs(Object model, Collection<I_M_HU> huList);

	/**
	 * See {@link #assignHUs(Object, Collection)}.
	 *
	 * @param model
	 * @param huList
	 * @param trxName
	 */
	void assignHUs(Object model, Collection<I_M_HU> huList, final String trxName);

	/**
	 * Assign given HU <b>as top level HU</b>.
	 *
	 * @param model
	 * @param hu
	 * @param trxName
	 * @return created/updated {@link I_M_HU_Assignment}.
	 */
	I_M_HU_Assignment assignHU(Object model, I_M_HU hu, final String trxName);

	/**
	 * Assign given HU <b>as top level HU</b>.
	 *
	 * @param model
	 * @param hu
	 * @param isTransferPackingMaterials
	 * @param trxName
	 * @return
	 */
	I_M_HU_Assignment assignHU(Object model, I_M_HU hu, boolean isTransferPackingMaterials, String trxName);
	
	/**
	 * Create handling unit assignment builder for given loading / trading unit(s) of the top-level HU
	 *
	 * @param ctx
	 * @param model
	 * @param topLevelHU
	 * @param luHU
	 * @param tuHU
	 * @param trxName
	 * @return builder
	 */
	IHUAssignmentBuilder createTradingUnitDerivedAssignmentBuilder(Properties ctx, Object model, I_M_HU topLevelHU, I_M_HU luHU, I_M_HU tuHU, String trxName);

	/**
	 * Deletes existing links between a specific <code>model</code> and it's existing handling units.
	 *
	 * Creates new assignments afterwards between <code>model</code> and the specified handling units.
	 *
	 * @param model
	 * @param handlingUnits
	 * @param trxName
	 */
	void setAssignedHandlingUnits(Object model, Collection<I_M_HU> handlingUnits, String trxName);

	/**
	 * Same as {@link #setAssignedHandlingUnits(Object, Collection, String)} but <code>trxName</code> will be fetched from <code>model</code>.
	 *
	 * @param model
	 * @param handlingUnits
	 */
	void setAssignedHandlingUnits(Object model, Collection<I_M_HU> handlingUnits);

	/**
	 * Unassign all HUs which are currently assigned to given <code>model</code>.
	 *
	 * @param model
	 * @param trxName
	 */
	void unassignAllHUs(Object model, String trxName);

	/**
	 * Unassign all HUs which are currently assigned to given <code>model</code>.
	 *
	 * Model's transaction will be used.
	 *
	 * @param model
	 */
	void unassignAllHUs(Object model);

	/**
	 * Unassigns the given <code>hus</code> from the given <code>model</code> by deleting the respective {@link I_M_HU_Assignment} records and then calls
	 * {@link IHUAssignmentListener#onHUUnassigned(I_M_HU, Object, String)} for all registered listeners. Note that for HUs with <code>M_HU_ID <= 0</code> no unassignment is attempted, and the
	 * listeners are not notified.
	 *
	 * @param model
	 * @param husToUnassign
	 */
	void unassignHUs(Object model, Collection<I_M_HU> husToUnassign);

	/**
	 * Unassign given HUs.
	 *
	 * @param model
	 * @param husToUnassign
	 * @param trxName
	 */
	void unassignHUs(Object model, Collection<I_M_HU> husToUnassign, String trxName);

	/**
	 * Create an {@link IHUAssignmentBuilder} to easily decorate and create new {@link I_M_HU_Assignment}s.
	 *
	 * @return builder
	 */
	IHUAssignmentBuilder createHUAssignmentBuilder();
}
