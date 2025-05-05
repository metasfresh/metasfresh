package de.metas.document.refid.api;

/*
 * #%L
 * de.metas.document.refid
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

import de.metas.document.refid.api.impl.ReferenceNoId;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.document.refid.model.I_C_ReferenceNo_Type_Table;
import de.metas.document.refid.spi.IReferenceNoGenerator;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public interface IReferenceNoDAO extends ISingletonService
{
	/**
	 * @return all active {@link I_C_ReferenceNo_Type}s for all Tenants
	 */
	List<I_C_ReferenceNo_Type> retrieveReferenceNoTypes();

	/**
	 * @return the active {@link I_C_ReferenceNo_Type}s for all Tenants that have the name of the given <code>clazz</code>
	 */
	I_C_ReferenceNo_Type retrieveRefNoTypeByClass(Properties ctx, Class<? extends IReferenceNoGenerator> clazz);

	/**
	 * @return the active {@link I_C_ReferenceNo_Type}s for all Tenants with the given <code>typeName</code>
	 */
	I_C_ReferenceNo_Type retrieveRefNoTypeByName(String typeName);

	List<I_C_ReferenceNo_Type_Table> retrieveTableAssignments(I_C_ReferenceNo_Type type);

	I_C_ReferenceNo getCreateReferenceNo(I_C_ReferenceNo_Type type, String referenceNo, IContextAware ctxAware);

	/**
	 * Creates and saves an {@link I_C_ReferenceNo_Doc} record to link the given <code>referenceNo</code> with the PO specified by the given <code>referencedModel</code>. If such a
	 * <code>C_ReferenceNo_Doc</code> record already exists, it is loaded, set to <code>IsActive=Y</code> and saved.
	 *
	 * @param referenceNo the reference number record to link. If it is new (ID <= 0) then this method also saves it.
	 */
	I_C_ReferenceNo_Doc getCreateReferenceNoDoc(I_C_ReferenceNo referenceNo, ITableRecordReference referencedModel);

	/**
	 * Retrieve all document assignments for given tableId/recordId
	 *
	 * @param referenceNoTypeId optional; if not specified, assignments for all types will be returned
	 */
	List<I_C_ReferenceNo_Doc> retrieveAllDocAssignments(Properties ctx, int referenceNoTypeId, int tableId, int recordId, String trxName);

	List<I_C_ReferenceNo_Doc> retrieveDocAssignments(I_C_ReferenceNo referenceNo);

	List<I_C_ReferenceNo_Doc> retrieveDocAssignments(ReferenceNoId referenceNoId);

	/**
	 * Retrieves all <code>C_ReferenceNo</code> records that have the given <code>type</code> and are associated with the given doc/model object via <code>C_ReferenceNo_Doc</code>. Note that one
	 * doc/model can generally have multiple <code>C_ReferenceNo</code> of the same type.
	 *
	 * @param model can be a "PO/record" or an {@link ITableRecordReference}.
	 */
	List<I_C_ReferenceNo> retrieveReferenceNos(Object model, I_C_ReferenceNo_Type type);

	/**
	 * Retrieves all records with the given <code>class</code>, that are associated with the given <code>model</code> record by a referenceNo that has the type specified by <code>generatorClazz</code>.
	 * <p>
	 * "Associated" means that <code>model</code> and the retrieved records are linked to the same <code>C_ReferenceNo</code> via <code>C_ReferenceNo_Doc</code> records.
	 * <p>
	 * Using this method it's possible to retrieve all invoices for a given order candidate, given that both the order candidate and the invoices share one reference number.
	 *
	 * @param model
	 * @param generatorClazz mandatory filter for the type (via {@link I_C_ReferenceNo_Type#COLUMNNAME_Classname}) that the common referenceNo needs to have.
	 * @param clazz mandatory filter criterion for the associated records to be retrieved.
	 * @return
	 */
	<T> List<T> retrieveAssociatedRecords(Object model, Class<? extends IReferenceNoGenerator> generatorClazz, Class<T> clazz);

	@NonNull
	Optional<ReferenceNoId> getReferenceNoId(@NonNull String referenceNo, @NonNull I_C_ReferenceNo_Type invoiceReferenceNoType);

	Optional<I_C_ReferenceNo> retrieveRefNo(@NonNull TableRecordReference tableRecordReference, @NonNull I_C_ReferenceNo_Type type);
}
