/**
 *
 */
package de.metas.impexp.processing;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.Properties;

import org.adempiere.service.ClientId;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import de.metas.process.PInstanceId;
import de.metas.util.ILoggable;

/**
 * All processes that are importing data should implement this interface.
 *
 * NOTE to developers: instead of implementing this class, please consider extending {@link ImportProcessTemplate}.
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <li>FR [ 2788276 ] Data Import Validator https://sourceforge.net/tracker/?func=detail&aid=2788276&group_id=176962&atid=879335
 */
public interface IImportProcess<ImportRecordType>
{
	String PARAM_DeleteOldImported = "DeleteOldImported";
	String PARAM_IsValidateOnly = "IsValidateOnly";
	String PARAM_Selection_ID = "Selection_ID";
	String PARAM_IsInsertOnly = "IsInsertOnly";
	String PARAM_IsDocComplete = "IsDocComplete";

	/** Sets the processing context */
	IImportProcess<ImportRecordType> setCtx(Properties ctx);

	IImportProcess<ImportRecordType> clientId(ClientId clientId);

	/** Sets process parameters to be used */
	IImportProcess<ImportRecordType> setParameters(IParams params);

	/** Sets the {@link ILoggable} where status notices shall be reported */
	IImportProcess<ImportRecordType> setLoggable(ILoggable loggable);

	IImportProcess<ImportRecordType> selectedRecords(TableRecordReferenceSet selectedRecordRefs);

	IImportProcess<ImportRecordType> selectedRecords(PInstanceId selectionId);

	IImportProcess<ImportRecordType> validateOnly(boolean validateOnly);

	IImportProcess<ImportRecordType> completeDocuments(boolean completeDocuments);

	/** @return import table model class */
	Class<ImportRecordType> getImportModelClass();

	/** @return The Name of Import Table (e.g. I_BPartner) */
	String getImportTableName();

	/**
	 * Run the import.
	 */
	ImportProcessResult run();

	/**
	 * Delete import records
	 * 
	 * @return how many rows were deleted
	 */
	int deleteImportRecords(ImportDataDeleteRequest request);
}
