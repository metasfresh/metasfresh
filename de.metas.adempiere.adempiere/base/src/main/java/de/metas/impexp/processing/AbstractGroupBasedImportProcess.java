package de.metas.impexp.processing;

import java.util.Properties;

import org.adempiere.service.ClientId;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import de.metas.util.ILoggable;

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

public class AbstractGroupBasedImportProcess<ImportRecordType, ImportGroupType extends ImportGroup<ImportRecordType>>
		implements IImportProcess<ImportRecordType>
{

	@Override
	public IImportProcess<ImportRecordType> setCtx(Properties ctx)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public IImportProcess<ImportRecordType> clientId(ClientId clientId)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public IImportProcess<ImportRecordType> setParameters(IParams params)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public IImportProcess<ImportRecordType> setLoggable(ILoggable loggable)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public IImportProcess<ImportRecordType> selectedRecords(TableRecordReferenceSet selectedRecordRefs)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public IImportProcess<ImportRecordType> validateOnly(boolean validateOnly)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<ImportRecordType> getImportModelClass()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public String getImportTableName()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public ImportProcessResult run()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int deleteImportRecords(ImportDataDeleteRequest request)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
