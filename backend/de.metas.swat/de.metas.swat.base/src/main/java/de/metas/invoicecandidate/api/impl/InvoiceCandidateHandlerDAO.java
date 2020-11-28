package de.metas.invoicecandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;

import de.metas.cache.annotation.CacheCtx;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.util.Services;
import lombok.NonNull;

public class InvoiceCandidateHandlerDAO implements IInvoiceCandidateHandlerDAO
{
	@Cached(cacheName = I_C_ILCandHandler.Table_Name + "#All")
	@Override
	public List<I_C_ILCandHandler> retrieveAll(@CacheCtx final Properties ctx)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_ILCandHandler> queryBuilder = queryBL
				.createQueryBuilder(I_C_ILCandHandler.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter() // only those handlers which are active
		;

		queryBuilder.orderBy()
				.addColumn(I_C_ILCandHandler.COLUMN_C_ILCandHandler_ID); // to have a predictible order

		return queryBuilder
				.create()
				.list(I_C_ILCandHandler.class);
	}

	@Override
	public List<I_C_ILCandHandler> retrieveForTable(final Properties ctx, final String tableName)
	{
		final List<I_C_ILCandHandler> result = new ArrayList<>();
		for (final I_C_ILCandHandler handlerDef : retrieveAll(ctx))
		{
			if (tableName.equals(handlerDef.getTableName()))
			{
				result.add(handlerDef);
			}
		}
		return result;
	}

	@Override
	public I_C_ILCandHandler retrieveForClassOneOnly(final Properties ctx,
			@NonNull final Class<? extends IInvoiceCandidateHandler> handlerClass)
	{
		final List<I_C_ILCandHandler> result = retrieveForClass(ctx, handlerClass);

		//
		// Retain only active handlers
		for (final Iterator<I_C_ILCandHandler> it = result.iterator(); it.hasNext();)
		{
			final I_C_ILCandHandler handlerDef = it.next();
			if (!handlerDef.isActive())
			{
				it.remove();
			}
		}

		if (result.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @C_ILCandHandler@ (@Classname@:" + handlerClass + ")");
		}
		else if (result.size() != 1)
		{
			throw new AdempiereException("@QueryMoreThanOneRecordsFound@ @C_ILCandHandler@ (@Classname@:" + handlerClass + "): " + result);
		}

		return result.get(0);
	}

	@Override
	public List<I_C_ILCandHandler> retrieveForClass(
			final Properties ctx,
			@NonNull final Class<? extends IInvoiceCandidateHandler> clazz)
	{
		final String classname = clazz.getName();

		final List<I_C_ILCandHandler> result = new ArrayList<>();
		for (final I_C_ILCandHandler handlerDef : retrieveAll(ctx))
		{
			if (classname.equals(handlerDef.getClassname()))
			{
				result.add(handlerDef);
			}
		}
		return result;
	}

	@Override
	public I_C_ILCandHandler retrieveFor(final I_C_Invoice_Candidate ic)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ic);

		for (final I_C_ILCandHandler handlerDef : retrieveAll(ctx))
		{
			if (ic.getC_ILCandHandler_ID() == handlerDef.getC_ILCandHandler_ID())
			{
				return handlerDef;
			}
		}

		throw new AdempiereException("Missing C_ILCandHandler return for C_ILCandHandler_ID=" + ic.getC_ILCandHandler_ID());
	}
}
