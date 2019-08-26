package org.compiere.impexp;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import org.compiere.model.I_C_DataImport;
import org.springframework.stereotype.Repository;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class DataImportRepository
{
	public DataImport getById(@NonNull final DataImportId id)
	{
		final I_C_DataImport record = loadOutOfTrx(id, I_C_DataImport.class);
		return toDataImport(record);
	}

	private static DataImport toDataImport(@NonNull final I_C_DataImport record)
	{
		return DataImport.builder()
				.id(DataImportId.ofRepoId(record.getC_DataImport_ID()))
				.impFormatId(ImpFormatId.ofRepoId(record.getAD_ImpFormat_ID()))
				.build();
	}
}
