package de.metas.handlingunits.receiptschedule.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfo;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.ProcessInfoSelectionQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOut;

import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentFactory;
import de.metas.handlingunits.document.IHUDocumentFactoryService;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;

public class ReceiptScheduleHUDocumentFactory implements IHUDocumentFactory
{
	private static final Logger logger = LogManager.getLogger(ReceiptScheduleHUDocumentFactory.class);

	private final void assumeTableName(final String tableName)
	{
		Check.assume(de.metas.inoutcandidate.model.I_M_ReceiptSchedule.Table_Name.equals(tableName), "Invalid tableName {}", tableName);
	}

	@Override
	public List<IHUDocument> createHUDocuments(final Properties ctx, final String tableName, final int recordId)
	{
		final List<IHUDocument> subsequentDocuments = null; // we don't care for subsequent documents
		return createHUDocuments(ctx, tableName, recordId, subsequentDocuments);
	}

	public List<IHUDocument> createHUDocuments(final Properties ctx, final String tableName, final int recordId, final List<IHUDocument> subsequentDocuments)
	{
		assumeTableName(tableName);

		final I_M_ReceiptSchedule schedule = InterfaceWrapperHelper.create(ctx, recordId, I_M_ReceiptSchedule.class, ITrx.TRXNAME_None);

		final IHUDocumentLine lines = createHUDocumentLine(schedule, subsequentDocuments);
		final IHUDocument doc = createHUDocumentFromLines(Collections.singletonList(lines));
		return Collections.singletonList(doc);
	}

	private IHUDocument createHUDocumentFromLines(final List<IHUDocumentLine> lines)
	{
		return new ReceiptScheduleHUDocument("Selection", lines);
	}

	private IHUDocumentLine createHUDocumentLine(final I_M_ReceiptSchedule schedule, final List<IHUDocument> subsequentDocuments)
	{
		final ReceiptScheduleHUDocumentLine documentLine = new ReceiptScheduleHUDocumentLine(schedule);

		// Collect subsequent documents if caller asked for that
		if (subsequentDocuments != null)
		{
			final Set<Integer> seenInOutIds = new HashSet<Integer>();
			for (final I_M_ReceiptSchedule_Alloc rsa : Services.get(IReceiptScheduleDAO.class).retrieveRsaForRs(schedule))
			{
				final int inoutId = rsa.getM_InOut_ID();
				if (inoutId <= 0)
				{
					continue;
				}
				if (!seenInOutIds.add(inoutId))
				{
					continue;
				}

				final I_M_InOut inout = rsa.getM_InOut();

				final List<IHUDocument> receiptDocuments = createSubsequentDocumentFromModel(inout);
				subsequentDocuments.addAll(receiptDocuments);
			}
		}

		return documentLine;
	}

	private List<IHUDocument> createSubsequentDocumentFromModel(final Object model)
	{
		final List<IHUDocument> documents = Services.get(IHUDocumentFactoryService.class).createHUDocumentsFromModel(model);
		for (final IHUDocument document : documents)
		{
			for (final IHUDocumentLine line : document.getLines())
			{
				line.setReadOnly(true);
			}
		}
		return documents;
	}

	@Override
	public <T> List<IHUDocument> createHUDocuments(final Properties ctx, final Class<T> modelClass, final Iterator<T> records)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		assumeTableName(tableName);

		final List<IHUDocumentLine> lines = new ArrayList<IHUDocumentLine>();
		while (records.hasNext())
		{
			final T record = records.next();
			final I_M_ReceiptSchedule schedule = InterfaceWrapperHelper.create(record, I_M_ReceiptSchedule.class);
			try
			{
				final List<IHUDocument> subsequentDocuments = null; // we don't care about subsequent documents
				final IHUDocumentLine line = createHUDocumentLine(schedule, subsequentDocuments);
				lines.add(line);
			}
			catch (final Exception e)
			{
				logger.warn("Error while creating line from " + schedule + ". Skipping.", e);
			}
		}

		final List<IHUDocument> docs = new ArrayList<IHUDocument>();

		final IHUDocument doc = createHUDocumentFromLines(lines);
		docs.add(doc);

		return docs;
	}

	@Override
	public List<IHUDocument> createHUDocuments(final ProcessInfo pi)
	{
		Check.assumeNotNull(pi, "process info not null");

		final String tableName = pi.getTableNameOrNull();
		assumeTableName(tableName);

		final Iterator<I_M_ReceiptSchedule> schedules = retrieveReceiptSchedules(pi);

		return createHUDocuments(pi.getCtx(), I_M_ReceiptSchedule.class, schedules);
	}

	private Iterator<I_M_ReceiptSchedule> retrieveReceiptSchedules(final ProcessInfo pi)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_ReceiptSchedule.class, pi.getCtx(), ITrx.TRXNAME_None)
				// Filter by process info selection
				.filter(new ProcessInfoSelectionQueryFilter<I_M_ReceiptSchedule>(pi))

				// FIXME: we need to fetch also those who where processed because on some of them we fully allocated to HUs and we cannot distinguish right now
				// // Just those that are not processed
				// .filter(new EqualsQueryFilter<I_M_ReceiptSchedule>(I_M_ReceiptSchedule.COLUMNNAME_Processed, false))

				.create()
				// Only active records
				.setOnlyActiveRecords(true)
				// Only those on which logged in user has RW access
				.setApplyAccessFilterRW(true)
				//
				.iterate(I_M_ReceiptSchedule.class);

	}

	@Override
	public List<IHUDocument> createHUDocumentsFromModel(final Object model)
	{
		final I_M_ReceiptSchedule schedule = InterfaceWrapperHelper.create(model, I_M_ReceiptSchedule.class);

		final List<IHUDocument> subsequentDocuments = null; // we don't care for subsequent documents
		final IHUDocumentLine line = createHUDocumentLine(schedule, subsequentDocuments);
		final IHUDocument doc = createHUDocumentFromLines(Collections.singletonList(line));
		return Collections.singletonList(doc);
	}
}
