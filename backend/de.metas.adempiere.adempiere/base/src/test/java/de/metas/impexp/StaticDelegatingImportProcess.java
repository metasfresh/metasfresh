package de.metas.impexp;

import java.util.Properties;
import java.util.function.Supplier;

import org.adempiere.service.ClientId;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import de.metas.impexp.processing.IImportProcess;
import de.metas.impexp.processing.ImportDataDeleteRequest;
import de.metas.impexp.processing.ImportProcessResult;
import de.metas.process.PInstanceId;
import de.metas.util.ILoggable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class StaticDelegatingImportProcess implements IImportProcess<Object>
{
	private static Supplier<? extends IImportProcess<?>> delegateFactory;

	public static <ImportRecordType, ImportProcessType extends IImportProcess<ImportRecordType>> Class<ImportProcessType> castTo(final Class<ImportRecordType> importRecordType)
	{
		@SuppressWarnings("unchecked")
		final Class<ImportProcessType> importProcessClass = (Class<ImportProcessType>)StaticDelegatingImportProcess.class;
		return importProcessClass;
	}

	public static void setDelegateFactory(final Supplier<? extends IImportProcess<?>> delegateFactory)
	{
		StaticDelegatingImportProcess.delegateFactory = delegateFactory;
	}

	private final IImportProcess<?> delegate;

	public StaticDelegatingImportProcess()
	{
		if (delegateFactory == null)
		{
			throw new IllegalStateException("No delegate factory configured for " + StaticDelegatingImportProcess.class);
		}

		this.delegate = delegateFactory.get();
	}

	@Override
	public IImportProcess<Object> setCtx(Properties ctx)
	{
		delegate.setCtx(ctx);
		return this;
	}

	@Override
	public IImportProcess<Object> clientId(ClientId clientId)
	{
		delegate.clientId(clientId);
		return this;
	}

	@Override
	public IImportProcess<Object> setParameters(IParams params)
	{
		delegate.setParameters(params);
		return this;
	}

	@Override
	public IImportProcess<Object> setLoggable(ILoggable loggable)
	{
		delegate.setLoggable(loggable);
		return this;
	}

	@Override
	public IImportProcess<Object> selectedRecords(TableRecordReferenceSet selectedRecordRefs)
	{
		delegate.selectedRecords(selectedRecordRefs);
		return this;
	}

	@Override
	public IImportProcess<Object> selectedRecords(PInstanceId selectionId)
	{
		delegate.selectedRecords(selectionId);
		return this;
	}

	@Override
	public IImportProcess<Object> validateOnly(boolean validateOnly)
	{
		delegate.validateOnly(validateOnly);
		return this;
	}

	@Override
	public IImportProcess<Object> completeDocuments(boolean completeDocuments)
	{
		delegate.completeDocuments(completeDocuments);
		return this;
	}

	@Override
	public Class<Object> getImportModelClass()
	{
		@SuppressWarnings("unchecked")
		final Class<Object> importModelClass = (Class<Object>)delegate.getImportModelClass();
		return importModelClass;
	}

	@Override
	public String getImportTableName()
	{
		return delegate.getImportTableName();
	}

	@Override
	public ImportProcessResult run()
	{
		return delegate.run();
	}

	@Override
	public int deleteImportRecords(ImportDataDeleteRequest request)
	{
		return delegate.deleteImportRecords(request);
	}
}
