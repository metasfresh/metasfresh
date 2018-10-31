package org.adempiere.impexp;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.impexp.spi.IAsyncImportProcessBuilder;

import com.google.common.base.Supplier;

import de.metas.util.ISingletonService;

/**
 * {@link IImportProcess} factory.
 * 
 * @author tsa
 *
 */
public interface IImportProcessFactory extends ISingletonService
{
	/**
	 * Registers which {@link IImportProcess} shall be used for a given import table model.
	 * 
	 * @param modelImportClass import table model (e.g. I_I_BPartner).
	 * @param importProcessClass import process class to be used
	 */
	<ImportRecordType> void registerImportProcess(Class<ImportRecordType> modelImportClass, Class<? extends IImportProcess<ImportRecordType>> importProcessClass);

	<ImportRecordType> IImportProcess<ImportRecordType> newImportProcess(Class<ImportRecordType> modelImportClass);

	<ImportRecordType> IImportProcess<ImportRecordType> newImportProcessOrNull(Class<ImportRecordType> modelImportClass);

	<ImportRecordType> IImportProcess<ImportRecordType> newImportProcessForTableNameOrNull(String tableName);

	<ImportRecordType> IImportProcess<ImportRecordType> newImportProcessForTableName(String tableName);

	void setAsyncImportProcessBuilderSupplier(final Supplier<IAsyncImportProcessBuilder> asyncImportProcessBuilderSupplier);

	IAsyncImportProcessBuilder newAsyncImportProcessBuilder();
}
