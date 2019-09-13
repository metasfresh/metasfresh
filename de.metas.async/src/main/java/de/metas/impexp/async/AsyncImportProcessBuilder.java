package de.metas.impexp.async;

import java.util.Collection;

/*
 * #%L
 * de.metas.async
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

import java.util.HashSet;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.DB;

import com.google.common.collect.ImmutableSet;

import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.impexp.processing.spi.IAsyncImportProcessBuilder;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * {@link IAsyncImportProcessBuilder} implementation which creates and enqueues an {@link AsyncImportWorkpackageProcessor} workpackage.
 * 
 * @author tsa
 *
 */
final class AsyncImportProcessBuilder implements IAsyncImportProcessBuilder
{
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	private String _importTableName;
	private final Set<Integer> importRecordIds = new HashSet<>();

	private Properties _ctx;

	AsyncImportProcessBuilder()
	{
	}

	@Override
	public void buildAndEnqueue()
	{
		final Properties ctx = getCtx();
		final String importTableName = getImportTableName();

		if (importRecordIds.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @Record_ID@");
		}
		final PInstanceId selectionId = DB.createT_Selection(importRecordIds, ITrx.TRXNAME_None);

		workPackageQueueFactory
				.getQueueForEnqueuing(ctx, AsyncImportWorkpackageProcessor.class)
				.newBlock()
				.setContext(ctx)
				.newWorkpackage()
				.parameters()
				.setParameter(AsyncImportWorkpackageProcessor.PARAM_ImportTableName, importTableName)
				.setParameter(AsyncImportWorkpackageProcessor.PARAM_Selection_ID, selectionId)
				.end()
				//
				.build();
	}

	@Override
	public IAsyncImportProcessBuilder setCtx(@NonNull final Properties ctx)
	{
		this._ctx = ctx;
		return this;
	}

	private Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "_ctx not null");
		return _ctx;
	}

	@Override
	public IAsyncImportProcessBuilder setImportTableName(@NonNull final String importTableName)
	{
		Check.assumeNull(this._importTableName, "importTableName not already configured");
		Check.assumeNotEmpty(importTableName, "importTableName not empty");
		this._importTableName = importTableName;
		return this;
	}

	private String getImportTableName()
	{
		Check.assumeNotEmpty(_importTableName, "importTableName not empty");
		return _importTableName;
	}

	@Override
	public IAsyncImportProcessBuilder addImportRecord(@NonNull final TableRecordReference importRecordRef)
	{
		final String importTableName = getImportTableName();
		if (!Objects.equals(importTableName, importRecordRef.getTableName()))
		{
			throw new AdempiereException("Record " + importRecordRef + " not matching " + importTableName + " table name");
		}

		importRecordIds.add(importRecordRef.getRecord_ID());

		return this;
	}

	@Override
	public IAsyncImportProcessBuilder addImportRecords(@NonNull final Collection<TableRecordReference> importRecordRefs)
	{
		if (importRecordRefs.isEmpty())
		{
			return this;
		}

		final ImmutableSet<Integer> recordIds = importRecordRefs.stream()
				.map(this::extractImportRecordId)
				.collect(ImmutableSet.toImmutableSet());

		importRecordIds.addAll(recordIds);

		return this;
	}

	private int extractImportRecordId(final TableRecordReference importRecordRef)
	{
		importRecordRef.assertTableName(getImportTableName());
		return importRecordRef.getRecord_ID();
	}

}
