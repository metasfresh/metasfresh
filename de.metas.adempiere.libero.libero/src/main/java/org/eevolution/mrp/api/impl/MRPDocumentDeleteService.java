package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.ModelColumn;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.IMRPDocumentDeleteService;
import org.eevolution.mrp.spi.IMRPSupplyProducer;
import org.eevolution.mrp.spi.IMRPSupplyProducerFactory;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.material.planning.ErrorCodes;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMaterialPlanningContext;

public class MRPDocumentDeleteService implements IMRPDocumentDeleteService
{
	/** How many documents shall be fetched at once to be deleted */
	private static final int DELETE_BufferSize = 50;

	// Services
	private final transient Logger loggerDefault = LogManager.getLogger(getClass());
	private transient Logger logger = loggerDefault;
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final transient IMRPDAO mrpDAO = Services.get(IMRPDAO.class);
	private final transient IMRPSupplyProducerFactory mrpSupplyProducerFactory = Services.get(IMRPSupplyProducerFactory.class);

	// SysConfigs
	private static final String SYSCONFIG_EnableEnqueueToDelete = "org.eevolution.mrp.api.IMRPDocumentDeleteService.EnableEnqueueToDelete";
	private static final boolean DEFAULT_EnableEnqueueToDelete = true;

	// Common column names that each document shall have in order to support delayed deletion
	private static final String COLUMNNAME_IsActive = "IsActive";
	private static final String COLUMNNAME_MRP_ToDelete = "MRP_ToDelete";

	// Context parameters
	private final boolean enableEnqueueToDelete;
	private IMaterialPlanningContext _mrpContext;
	private IMRPNotesCollector _mrpNotesCollector = DirectMRPNotesCollector.instance;

	/** <code>true</code> if {@link #deletePending()} can be interrupted when Thread is interrupted */
	private boolean _interruptible = false;
	private int _maxPendingDocumentsToDelete = 0;

	public MRPDocumentDeleteService()
	{
		super();
		enableEnqueueToDelete = sysConfigBL.getBooleanValue(SYSCONFIG_EnableEnqueueToDelete, DEFAULT_EnableEnqueueToDelete);
	}

	@Override
	public IMRPDocumentDeleteService setMRPContext(final IMaterialPlanningContext mrpContext)
	{
		_mrpContext = mrpContext;
		if (mrpContext != null && mrpContext.getLogger() != null)
		{
			logger = mrpContext.getLogger();
		}
		else
		{
			logger = loggerDefault;
		}

		return this;
	}

	private final IMaterialPlanningContext getMRPContext()
	{
		return _mrpContext;
	}

	@Override
	public IMRPDocumentDeleteService setMRPNotesCollector(final IMRPNotesCollector mrpNotesCollector)
	{
		_mrpNotesCollector = mrpNotesCollector;

		return this;
	}

	private final IMRPNotesCollector getMRPNotesCollector()
	{
		return _mrpNotesCollector;
	}

	@Override
	public <ModelType> int delete(final IQueryBuilder<ModelType> modelsQuery)
	{
		if (isEnqueueToDelete(modelsQuery.getModelClass()))
		{
			return deleteDelayed(modelsQuery);
		}
		else
		{
			return deleteNow(modelsQuery);
		}
	}

	/**
	 * Checks if we shall use the enqueue to delete functionality for given document class.
	 *
	 * In case this method returns false, the document(s) shall be deleted right away.
	 *
	 * @param documentClass
	 * @return <code>true</code> if we can and if we are allowed to enqueue to delete given document class.
	 */
	private final boolean isEnqueueToDelete(final Class<?> documentClass)
	{
		if (!enableEnqueueToDelete)
		{
			return false;
		}

		if (!hasEnqueueToDeleteSupport(documentClass))
		{
			return false;
		}

		return true;
	}

	/**
	 * @param documentClass
	 * @return true if given document class has enqueue to delete support
	 */
	private boolean hasEnqueueToDeleteSupport(final Class<?> documentClass)
	{
		// If testing mode/database decoupled, we don't have enqueue to delete support
		if (Adempiere.isUnitTestMode())
		{
			return false;
		}

		// If model does not have MRP_ToDelete column, for sure it does not have enqueue to delete support
		if (!InterfaceWrapperHelper.hasColumnName(documentClass, COLUMNNAME_MRP_ToDelete))
		{
			return false;
		}

		return true;
	}

	private final <ModelType> int deleteDelayed(final IQueryBuilder<ModelType> modelsQuery)
	{
		//
		// Mark documents to be deleted.
		// They will be actually deleted later.
		final Class<ModelType> modelClass = modelsQuery.getModelClass();
		final ICompositeQueryUpdater<ModelType> toDeleteMarker = queryBL.createCompositeQueryUpdater(modelClass)
				.addSetColumnValue(COLUMNNAME_IsActive, false)
				.addSetColumnValue(COLUMNNAME_MRP_ToDelete, true);
		final int countToDelete = modelsQuery.copy()
				.addEqualsFilter(COLUMNNAME_MRP_ToDelete, false) // ... because we don't want to count those which were already marked as deleted
				.create()
				.updateDirectly(toDeleteMarker);

		//
		// Delete MRP records which are linked to our documents
		deleteMRPRecords(modelsQuery);

		return countToDelete;
	}

	private final <ModelType> void deleteMRPRecords(final IQueryBuilder<ModelType> modelsQuery)
	{
		final Class<ModelType> modelClass = modelsQuery.getModelClass();
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(modelClass);
		final ModelColumn<I_PP_MRP, ModelType> keyColumn = new ModelColumn<>(I_PP_MRP.class, keyColumnName, modelClass);

		final IQueryBuilder<I_PP_MRP> mrpsQuery = modelsQuery.andCollectChildren(keyColumn, I_PP_MRP.class);

		mrpDAO.deleteMRP(mrpsQuery);
	}

	@Override
	public final <ModelType> int deleteNow(final IQueryBuilder<ModelType> modelsQuery)
	{
		final Class<ModelType> modelClass = modelsQuery.getModelClass();
		final int maxToDelete = modelsQuery.getLimit();
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(modelClass);

		final int bufferSize = maxToDelete > 0 ? Math.min(maxToDelete, DELETE_BufferSize) : DELETE_BufferSize;
		final Set<Integer> recordIdsWithErrors = new HashSet<>();
		int countDeleted = 0;
		int countErrors = 0;

		boolean hasMoreRecords = true;
		while (hasMoreRecords)
		{
			// Stop if interrupted
			if (isInterrupted())
			{
				break;
			}

			// Make sure we are not deleting more than we were asked to
			if (maxToDelete > 0 && countDeleted >= maxToDelete)
			{
				break;
			}

			// Retrieve first "bufferSize" records to delete
			final IQueryBuilder<ModelType> queryBuilder = modelsQuery.copy();
			// Skip those records on which we encountered errors while we tried to delete them previously
			if (!recordIdsWithErrors.isEmpty())
			{
				queryBuilder.addNotInArrayFilter(keyColumnName, recordIdsWithErrors);
			}

			//
			// Load the buffer of records to delete in this round
			queryBuilder.setLimit(bufferSize);
			final List<ModelType> records = queryBuilder
					.create()
					.list(modelClass);
			hasMoreRecords = !records.isEmpty() && records.size() >= bufferSize;

			//
			// Iterate retrieved records and try deleting them
			for (final ModelType record : records)
			{
				// Stop if interrupted
				if (isInterrupted())
				{
					break;
				}

				try
				{
					InterfaceWrapperHelper.delete(record);
					countDeleted++;
				}
				catch (final Exception e)
				{
					notifyDeleteError(record, e);
					//
					countErrors++;
					//
					final int recordId = InterfaceWrapperHelper.getId(record);
					recordIdsWithErrors.add(recordId);
				}
			}
		}

		if (countDeleted != 0 || countErrors != 0)
		{
			logger.info("MRP documents deleted #{} (with errors #{})"
					+ "\nModelClass: {}"
					+ "\nQuery: {}"
					, new Object[] { countDeleted, countErrors, modelClass, modelsQuery });
		}

		return countDeleted < 0 ? 0 : countDeleted;
	}

	private void notifyDeleteError(final Object document, final Exception e)
	{
		final IMaterialPlanningContext mrpContext = getMRPContext();
		final IMRPNotesCollector mrpNotesCollector = getMRPNotesCollector();
		mrpNotesCollector.newMRPNoteBuilder(mrpContext, ErrorCodes.MRP_ERROR_999_Unknown)
				.addParameter("Document", document)
				.setComment("Cannot delete document")
				.setException(e)
				.collect();
	}

	@Override
	public int deletePending()
	{
		int countDeletedTotal = 0;
		for (final IMRPSupplyProducer mrpSupplyProducer : mrpSupplyProducerFactory.getAllSupplyProducersList())
		{
			// Stop if interrupted
			if (isInterrupted())
			{
				break;
			}

			final Class<?> documentClass = mrpSupplyProducer.getDocumentClass();

			// If the supply producer does not specify a document class, then there is nothing to delete
			if (documentClass == null)
			{
				continue;
			}

			// If document class does not support Enqueue to Delete, then there is nothing to do
			if (!hasEnqueueToDeleteSupport(documentClass))
			{
				continue;
			}

			//
			// Calculate how much we can deleted.
			// Stop if we deleted maximum allowed.
			final int maxToDelete;
			if (_maxPendingDocumentsToDelete > 0)
			{
				maxToDelete = _maxPendingDocumentsToDelete - countDeletedTotal;
				if (maxToDelete <= 0)
				{
					break;
				}
			}
			else
			{
				maxToDelete = -1;
			}

			// Delete enqueued documents for given document class
			final int countDeleted = deletePending(documentClass, maxToDelete);

			// Aggregate totals
			countDeletedTotal += countDeleted;
		}

		return countDeletedTotal;
	}

	private final int deletePending(final Class<?> documentClass, final int maxToDelete)
	{
		final IQueryBuilder<?> documentsToDeleteQuery = queryBL.createQueryBuilder(documentClass, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(COLUMNNAME_MRP_ToDelete, true)
				.setLimit(maxToDelete);

		final int countDeleted = deleteNow(documentsToDeleteQuery);
		return countDeleted;
	}

	@Override
	public MRPDocumentDeleteService setInterruptible(final boolean interruptible)
	{
		this._interruptible = interruptible;
		return this;
	}

	private final boolean isInterrupted()
	{
		// If interruption is not allowed, return false (not interrupted)
		if (!_interruptible)
		{
			return false;
		}

		//
		// Check if current thread was interrupted
		final Thread currentThread = Thread.currentThread();
		final boolean interrupted = currentThread.isInterrupted();
		if (interrupted)
		{
			logger.info("Thread " + currentThread.getName() + " has been interrupted.");
		}

		return interrupted;
	}

	@Override
	public IMRPDocumentDeleteService setMaxPendingDocumentsToDelete(int maxPendingDocumentsToDelete)
	{
		this._maxPendingDocumentsToDelete = maxPendingDocumentsToDelete;
		return this;
	}
}
