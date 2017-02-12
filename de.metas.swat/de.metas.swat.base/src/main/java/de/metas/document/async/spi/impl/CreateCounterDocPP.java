package de.metas.document.async.spi.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.process.DocAction;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.document.ICounterDocBL;
import de.metas.document.engine.IDocActionBL;

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

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CreateCounterDocPP extends WorkpackageProcessorAdapter
{
	public static final void schedule(final Object model)
	{
		SCHEDULER.schedule(model);
	}

	private static final WorkpackagesOnCommitSchedulerTemplate<Object> SCHEDULER = new WorkpackagesOnCommitSchedulerTemplate<Object>(CreateCounterDocPP.class)
	{
		@Override
		protected boolean isEligibleForScheduling(final Object model)
		{
			return Services.get(ICounterDocBL.class).isCreateCounterDocument(model);
		};

		@Override
		protected Properties extractCtxFromItem(final Object item)
		{
			return InterfaceWrapperHelper.getCtx(item);
		}

		@Override
		protected String extractTrxNameFromItem(final Object item)
		{
			return InterfaceWrapperHelper.getTrxName(item);
		}

		@Override
		protected Object extractModelToEnqueueFromItem(final Collector collector, final Object item)
		{
			return TableRecordReference.of(item);
		}
	};

	// services
	private final transient IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);
	private final transient ICounterDocBL counterDocumentBL = Services.get(ICounterDocBL.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final List<Object> models = queueDAO.retrieveItemsSkipMissing(workpackage, Object.class, localTrxName);
		for (final Object model : models)
		{
			final DocAction document = docActionBL.getDocAction(model);

			final DocAction counterDocument = counterDocumentBL.createCounterDocument(document, false);
			Loggables.get().addLog("Document {0}: created counter document {1}", document, counterDocument);
		}
		return Result.SUCCESS;
	}
}
