package de.metas.impexp.processing.spi;

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

import de.metas.impexp.processing.IImportProcess;
import de.metas.process.PInstanceId;

/**
 * Builds an {@link IImportProcess} instance and executes it asynchronously.
 *
 * @author tsa
 *
 */
public interface IAsyncImportProcessBuilder
{
	/**
	 * Builds the {@link IImportProcess} and starts it asynchronously.
	 *
	 * This method will return directly and it will not wait for the actual import process to finish.
	 */
	void buildAndEnqueue();

	IAsyncImportProcessBuilder setCtx(Properties ctx);

	/**
	 * Sets the import table name (the source).
	 *
	 * @param tableName import table name (e.g. I_BPartner).
	 */
	IAsyncImportProcessBuilder setImportTableName(String tableName);

	/**
	 * Enqueues all import table record identified by given selection
	 */
	IAsyncImportProcessBuilder setImportFromSelectionId(PInstanceId fromSelectionId);

	/**
	 * If feasible for the given type of data, the import processor implementation will complete the documents that it imported.
	 * Default if not set: {@code false}.
	 */
	IAsyncImportProcessBuilder setCompleteDocuments(boolean completeDocuments);

	/** Default if not set: {@code false}. */
	IAsyncImportProcessBuilder setDeleteOldImported(boolean deleteOldImported);

	/** Default if not set: {@code false}. */
	IAsyncImportProcessBuilder setInsertOnly(boolean insertOnly);

	/** Default if not set: {@code false}. */
	IAsyncImportProcessBuilder setValidateOnly(boolean validateOnly);
}
