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
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.util.ISingletonService;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public interface IHUAssignmentDAO extends ISingletonService
{
	/**
	 * Retrieve single top-level handling unit assignment. "Top-level" means that both {@code M_LU_HU_ID} and {@code M_TU_HU_ID} are null. If no such record is found, return null.
	 */
	I_M_HU_Assignment retrieveHUAssignmentOrNull(Properties ctx, int huId, int adTableId, int recordId, String trxName);

	/**
	 * Retrieve handling unit assignments for given document record, identified by <code>adTableId</code> and <code>recordId</code>.
	 */
	List<I_M_HU_Assignment> retrieveHUAssignmentsForModel(Properties ctx, int adTableId, int recordId, String trxName);

	/**
	 * Retrieve <b>top-level</b> handling unit assignments for given document
	 */
	List<I_M_HU_Assignment> retrieveTopLevelHUAssignmentsForModel(Object model);

	/**
	 * For all active HU-Assignments that the given {@code model} has, retrieve pojos that contain the different respective lowest level (e.g. VHU) HUs.
	 */
	List<HuAssignment> retrieveLowLevelHUAssignmentsForModel(Object model);

	/**
	 * This in an "encapsulated" representation of a {@link I_M_HU_Assignment} data record.
	 * <p>
	 * Please prefer using this one over the mere data record, and extend is as needed (also, feel free to extract the class onto another file).
	 */
	@Value
	@EqualsAndHashCode(exclude = "lowestLevelHU")
	class HuAssignment
	{
		public static HuAssignment ofDataRecordAllowMissingHU(
				@NonNull final I_M_HU_Assignment huAssignmentRecord)
		{
			final I_M_HU lowestLevelHuOrNull = extractLowestLevelHuOrNull(huAssignmentRecord);

			return new HuAssignment(
					lowestLevelHuOrNull,
					TableRecordReference.ofReferencedOrNull(huAssignmentRecord));
		}

		public static HuAssignment ofDataRecord(
				@NonNull final I_M_HU_Assignment huAssignmentRecord)
		{
			final I_M_HU lowestLevelHu = extractLowestLevelHuOrNull(huAssignmentRecord);
			if (lowestLevelHu == null)
			{
				throw new AdempiereException("The given I_M_HU_Assignment has no HU")
						.appendParametersToMessage()
						.setParameter("I_M_HU_Assignment", huAssignmentRecord);
			}
			return new HuAssignment(
					lowestLevelHu,
					TableRecordReference.ofReferencedOrNull(huAssignmentRecord));
		}

		@Nullable
		private static I_M_HU extractLowestLevelHuOrNull(
				@NonNull final I_M_HU_Assignment huAssignmentRecord)
		{
			final I_M_HU hu;
			if (huAssignmentRecord.getVHU_ID() > 0)
			{
				hu = huAssignmentRecord.getVHU();
			}
			else if (huAssignmentRecord.getM_TU_HU_ID() > 0)
			{
				hu = huAssignmentRecord.getM_TU_HU();
			}
			else if (huAssignmentRecord.getM_LU_HU_ID() > 0)
			{
				hu = huAssignmentRecord.getM_LU_HU();
			}
			else if (huAssignmentRecord.getM_HU_ID() > 0)
			{
				hu = huAssignmentRecord.getM_HU();
			}
			else
			{
				hu = null;
			}
			return hu;
		}

		HuId lowestLevelHUId;

		ITableRecordReference referencedRecord;

		/**
		 * The lowest level of an HU assignment record is interesting, because it does not contains different products
		 */
		I_M_HU lowestLevelHU;

		private HuAssignment(
				@Nullable final I_M_HU lowestLevelHU,
				@NonNull final ITableRecordReference referencedRecord)
		{
			this.lowestLevelHU = lowestLevelHU;
			this.lowestLevelHUId = lowestLevelHU != null ? HuId.ofRepoId(lowestLevelHU.getM_HU_ID()) : null;

			this.referencedRecord = referencedRecord;
		}
	}

	IQueryBuilder<I_M_HU_Assignment> retrieveHUAssignmentsForModelQuery(Object model);

	ImmutableSetMultimap<TableRecordReference, HuId> retrieveHUsByRecordRefs(@NonNull Set<TableRecordReference> recordRefs);

	/**
	 * @see #retrieveTopLevelHUsForModel(Object, String)
	 */
	List<I_M_HU> retrieveTopLevelHUsForModel(Object model);

	/**
	 * Retrieves HUs which are top level and assigned to given model.
	 * <p>
	 * NOTE: this method will NOT exclude destroyed HUs.
	 *
	 * @return HUs which are top level and assigned to given model.
	 */
	List<I_M_HU> retrieveTopLevelHUsForModel(Object model, String trxName);

	/**
	 * @return assignments which have M_TU_HU_ID set and are <code>model</code>
	 */
	IQueryBuilder<I_M_HU_Assignment> retrieveTUHUAssignmentsForModelQuery(Object model);

	/**
	 * Retrieves those "sub" assignments that reference the same top-level HU and data-record as the given <code>assigment</code>, but also reference a particular (sub-)component of the top-level HU
	 *
	 * @return included assignments
	 */
	List<I_M_HU_Assignment> retrieveIncludedHUAssignments(I_M_HU_Assignment assignment);

	/**
	 * @return true if there are HUs assigned to given model
	 */
	boolean hasHUAssignmentsForModel(Object model);

	void deleteHUAssignments(Object model, Collection<I_M_HU> husToUnAssign, String trxName);

	/**
	 * @return all HU assignments for the given HU and table
	 */
	IQueryBuilder<I_M_HU_Assignment> retrieveTableHUAssignmentsQuery(IContextAware contextProvider, int adTableId, I_M_HU hu);

	/**
	 * Checks if the LU from given assignment: <lu>
	 * <li>is assigned to a model from same table as the model from this assignment is
	 * <li>that model on which it could be assigned is created BEFORE the
	 * model from this assignment </lu>
	 *
	 * @return true if the LU was already assignment to a table/record which was created before the one from our assignment
	 */
	boolean hasMoreLUAssigmentsForSameModelType(I_M_HU_Assignment luAssignment);

	/**
	 * Asserts given model has no assignments.
	 *
	 * @throws HUException in case any HU assignment was found
	 */
	void assertNoHUAssignmentsForModel(Object model);

	/**
	 * Call {@link #retrieveModelsForHU(I_M_HU, Class, boolean)} with <code>topLevel==true</code>.
	 */
	<T> List<T> retrieveModelsForHU(I_M_HU hu, Class<T> clazz);

	/**
	 * Retrieve the models whose table name matches the given class and which have an (active) assignment with the given <code>hu</code>.
	 * <p>
	 * <b>IMPORTANT:</b> assume that the correct key column name and <code>AD_Table_ID</code> can be extracted from the given <code>clazz</code> using {@link InterfaceWrapperHelper#getTableId(Class)}
	 * and {@link InterfaceWrapperHelper#getKeyColumnName(Class)}.
	 *
	 * @param topLevel if <code>true</code>, then only assignments which reference the given <code>hu</code> via <code>M_HU_ID</code> are considered, and none which reference the <code>hu</code> via
	 *                 <code>M_LU_HU_ID</code>. If <code>false</code>, then it is the other way round.
	 */
	<T> List<T> retrieveModelsForHU(I_M_HU hu, Class<T> clazz, boolean topLevel);

	/**
	 * Retrieve the table hu assignments for the given HU even if they have LU and/or TU set. This is useful in the shipment hu assignments.
	 */
	List<I_M_HU_Assignment> retrieveTableHUAssignmentsNoTopFilter(IContextAware contextProvider, int adTableId, I_M_HU hu);

	/**
	 * Retrieve the hu assignments for the given table and HU.
	 * Do not force to be top level
	 * Make sure the tu is set
	 */
	List<I_M_HU_Assignment> retrieveTableHUAssignmentsNoTopFilterTUMandatory(IContextAware contextProvider, int adTableId, I_M_HU hu);
}
