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
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;

public interface IHUAssignmentDAO extends ISingletonService
{
	/**
	 * Retrieve single top-level handling unit assignment. "Top-level" means that both {@code M_LU_HU_ID} and {@code M_TU_HU_ID} are null. If no such record is found, return null.
	 *
	 * @param ctx
	 * @param huId
	 * @param adTableId
	 * @param recordId
	 * @param trxName
	 * @return assignment
	 */
	I_M_HU_Assignment retrieveHUAssignmentOrNull(Properties ctx, int huId, int adTableId, int recordId, String trxName);

	/**
	 * Retrieve top-level handling unit assignments for given document record, identified by <code>adTableId</code> and <code>recordId</code>.
	 *
	 * @param ctx
	 * @param adTableId
	 * @param recordId
	 * @param trxName
	 * @return assignments
	 */
	List<I_M_HU_Assignment> retrieveHUAssignmentsForModel(Properties ctx, int adTableId, int recordId, String trxName);

	/**
	 * Retrieve <b>top-level</b> handling unit assignments for given document
	 *
	 * @param model
	 * @return assignments
	 */
	List<I_M_HU_Assignment> retrieveHUAssignmentsForModel(Object model);

	IQueryBuilder<I_M_HU_Assignment> retrieveHUAssignmentsForModelQuery(Object model);

	/**
	 * @param model
	 * @return
	 * @see #retrieveTopLevelHUsForModel(Object, String)
	 */
	List<I_M_HU> retrieveTopLevelHUsForModel(Object model);

	/**
	 * Retrieves HUs which are top level and assigned to given model.
	 *
	 * NOTE: this method will NOT exclude destroyed HUs.
	 *
	 * @param model
	 * @param trxName
	 * @return HUs which are top level and assigned to given model.
	 */
	List<I_M_HU> retrieveTopLevelHUsForModel(Object model, String trxName);

	/**
	 * Retrieves TUs assigned to <code>model</code>.
	 *
	 * NOTE: this method will NOT exclude destroyed HUs.
	 *
	 * @param model
	 * @return TUs assigned to <code>model</code>
	 * @see #retrieveTUHUAssignmentsForModelQuery(Object)
	 */
	List<I_M_HU> retrieveTUHUsForModel(Object model);

	/**
	 *
	 * @param model
	 * @return assignments which have M_TU_HU_ID set and are <code>model</code>
	 */
	IQueryBuilder<I_M_HU_Assignment> retrieveTUHUAssignmentsForModelQuery(Object model);

	/**
	 * Retrieves those "sub" assignments that reference the same top-level HU and data-record as the given <code>assigment</code>, but also reference a particular (sub-)component of the top-level HU
	 *
	 * @param assignment
	 * @return included assignments
	 */
	List<I_M_HU_Assignment> retrieveIncludedHUAssignments(I_M_HU_Assignment assignment);

	/**
	 * @param model
	 * @return true if there are HUs assigned to given model
	 */
	boolean hasHUAssignmentsForModel(Object model);

	void deleteHUAssignments(Object model, Collection<I_M_HU> husToUnAssign, String trxName);

	/**
	 * @param contextProvider
	 * @param adTableId
	 * @param hu
	 * @return all HU assignments for the given HU and table
	 */
	List<I_M_HU_Assignment> retrieveTableHUAssignments(IContextAware contextProvider, int adTableId, I_M_HU hu);

	/**
	 * @param contextProvider
	 * @param adTableId
	 * @param hu
	 * @return HU assignment count for the given HU and table
	 */
	int retrieveTableHUAssignmentsCount(IContextAware contextProvider, int adTableId, I_M_HU hu);

	/**
	 *
	 * @param contextProvider
	 * @param adTableId
	 * @param hu
	 * @return all HU assignments for the given HU and table
	 */
	IQueryBuilder<I_M_HU_Assignment> retrieveTableHUAssignmentsQuery(IContextAware contextProvider, int adTableId, I_M_HU hu);

	/**
	 * @param ctx
	 * @param model
	 * @param topLevelHU
	 * @param luHU
	 * @param tuHU
	 * @param trxName
	 * @return true if there are any trading unit assignments on the given model's table - among all assignments (document line expected)
	 */
	boolean hasDerivedTradingUnitAssignmentsOnLUTU(Properties ctx, Object model, I_M_HU topLevelHU, I_M_HU luHU, I_M_HU tuHU, String trxName);

	/**
	 * @param ctx
	 * @param model
	 * @param topLevelHU
	 * @param luHU
	 * @param tuHU
	 * @param trxName
	 * @return true if there are any trading unit assignments on the given model (document line expected)
	 */
	boolean hasDerivedTradingUnitAssignments(Properties ctx, Object model, I_M_HU topLevelHU, I_M_HU luHU, I_M_HU tuHU, String trxName);

	/**
	 * Checks if the LU from given assignment: <lu> <li>is assigned to a model from same table as the model from this assignment is <li>that model on which it could be assigned is created BEFORE the
	 * model from this assignment </lu>
	 *
	 * @param luAssignment
	 * @return true if the LU was already assignment to a table/record which was created before the one from our assignment
	 */
	boolean hasMoreLUAssigmentsForSameModelType(I_M_HU_Assignment luAssignment);

	/**
	 * Asserts given model has no assignments.
	 *
	 * @param model
	 * @throws HUException in case any HU assignment was found
	 */
	void assertNoHUAssignmentsForModel(Object model);

	/**
	 * Call {@link #retrieveModelsForHU(I_M_HU, Class, boolean)} with <code>topLevel==true</code>.
	 *
	 * @param hu
	 * @param clazz
	 * @return
	 */
	<T> List<T> retrieveModelsForHU(I_M_HU hu, Class<T> clazz);

	/**
	 * Retrieve the models whose table name matches the given class and which have an (active) assignment with the given <code>hu</code>.
	 * <p>
	 * <b>IMPORTANT:</b> assume that the correct key column name and <code>AD_Table_ID</code> can be extracted from the given <code>clazz</code> using {@link InterfaceWrapperHelper#getTableId(Class)}
	 * and {@link InterfaceWrapperHelper#getKeyColumnName(Class)}.
	 *
	 * @param hu
	 * @param clazz
	 * @param topLevel if <code>true</code>, then only assignments which reference the given <code>hu</code> via <code>M_HU_ID</code> are considered, and none which reference the <code>hu</code> via
	 *            <code>M_LU_HU_ID</code>. If <code>false</code>, then it is the other way round.
	 * @return
	 */
	<T> List<T> retrieveModelsForHU(I_M_HU hu, Class<T> clazz, boolean topLevel);
}
