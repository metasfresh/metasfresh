package org.eevolution.mrp.spi.impl;

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


import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.ModelColumn;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPContext;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.IMRPDemandToSupplyAllocation;
import org.eevolution.mrp.api.IMRPDocumentDeleteService;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.api.IMRPExecutorService;
import org.eevolution.mrp.api.IMRPQueryBuilder;
import org.eevolution.mrp.api.IMRPSegment;
import org.eevolution.mrp.api.IMRPSegmentBL;
import org.eevolution.mrp.api.IMRPSourceEvent;
import org.eevolution.mrp.api.MRPFirmType;
import org.eevolution.mrp.spi.IMRPSupplyProducer;
import org.slf4j.Logger;

import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocActionBL;
import de.metas.logging.LogManager;

public abstract class AbstractMRPSupplyProducer implements IMRPSupplyProducer
{
	protected final transient Logger log = LogManager.getLogger(getClass());

	//
	// Services
	protected final transient IMRPDAO mrpDAO = Services.get(IMRPDAO.class);
	protected final transient IMRPBL mrpBL = Services.get(IMRPBL.class);
	protected final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private final Map<String, String[]> sourceColumnNames = new HashMap<String, String[]>();
	private final Set<String> sourceTableNames = new HashSet<String>();
	private final Set<String> sourceTableNamesRO = Collections.unmodifiableSet(sourceTableNames);

	protected void addSourceTableName(final String tableName)
	{
		sourceTableNames.add(tableName);
	}

	protected void addSourceColumnNames(final String tableName, final String[] columnNames)
	{
		sourceColumnNames.put(tableName, columnNames);
		addSourceTableName(tableName);
	}

	@SafeVarargs
	protected final <T> void addSourceColumnNames(final Class<T> modelClass, final ModelColumn<T, ?>... columns)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);

		final String[] columnNames = new String[columns.length];
		for (int i = 0; i < columns.length; i++)
		{
			columnNames[i] = columns[i].getColumnName();
		}

		addSourceColumnNames(tableName, columnNames);
	}

	@Override
	public final Set<String> getSourceTableNames()
	{
		return sourceTableNamesRO;
	}

	protected int getC_DocType_ID(final IMRPContext mrpContext, final String docBaseType)
	{
		final Properties ctx = mrpContext.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		final I_AD_Org org = mrpContext.getAD_Org();
		final int adOrgId = org == null ? 0 : org.getAD_Org_ID();
		final String trxName = ITrx.TRXNAME_None; // retrieve master data, out of trx
		return Services.get(IDocTypeDAO.class).getDocTypeId(ctx, docBaseType, adClientId, adOrgId, trxName);
	}

	/**
	 * Check if a persistent object is changed, from MRP point of view
	 *
	 * @param po MRP relevant PO (e.g. MOrder, MOrderLine, I_PP_Order etc)
	 * @return true if object changed
	 */
	protected boolean isChanged(final Object model)
	{
		if (InterfaceWrapperHelper.isNew(model))
		{
			return true;
		}

		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final String[] columnNames = sourceColumnNames.get(tableName);
		if (columnNames == null || columnNames.length == 0)
		{
			return false;
		}

		if (InterfaceWrapperHelper.isValueChanged(model, "IsActive"))
		{
			return true;
		}

		for (final String columnName : columnNames)
		{
			if (InterfaceWrapperHelper.isValueChanged(model, columnName))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void onRecordChange(final Object model, final ModelChangeType changeType)
	{
		log.debug("onRecordChange: {}, Type: {}", model, changeType);

		final IMRPSourceEvent event = createMRPSourceEvent(model, changeType);
		if (event.isDelete() || event.isVoided() || !event.isModelActive() || event.isClosed())
		{
			onRecordDeleted(event);
		}
		else
		{
			onRecordChange(event);
		}
	}

	private IMRPSourceEvent createMRPSourceEvent(final Object model, final ModelChangeType changeType)
	{

		final boolean change = ModelChangeType.AFTER_NEW == changeType
				|| ModelChangeType.AFTER_CHANGE == changeType && isChanged(model);
		final boolean delete = ModelChangeType.BEFORE_DELETE == changeType;

		boolean released = false;
		boolean voided = false;
		boolean closed = false;
		boolean active = true;
		final DocAction document = getDocument(model);
		if (document != null)
		{
			final String docStatus = document.getDocStatus();
			released = MRPFirmType.Firm.hasDocStatus(docStatus);
			voided = MRPFirmType.Voided.hasDocStatus(docStatus);
			closed = MRPFirmType.Closed.hasDocStatus(docStatus);
			active = document.isActive();
		}

		final IMRPSourceEvent event = new MRPSourceEvent(model, change, delete, released, voided, closed, active, changeType);
		return event;
	}

	protected void onRecordDeleted(final IMRPSourceEvent event)
	{
		final Object model = event.getModel();
		mrpDAO.deleteMRP(model);
	}

	protected abstract void onRecordChange(IMRPSourceEvent event);

	@Override
	public boolean isRecreatedMRPRecordsSupported(final String tableName)
	{
		return !getSourceTableNames().isEmpty();
	}

	@Override
	public final void recreateMRPRecords(final Object model)
	{
		Check.assumeNotNull(model, "model not null");
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		if (!isRecreatedMRPRecordsSupported(tableName))
		{
			throw new LiberoException("Re-creating MRP records for " + tableName + " is not allowed");
		}

		// Delete existing records
		onRecordChange(model, ModelChangeType.BEFORE_DELETE);

		// Re-create them
		onRecordChange(model, ModelChangeType.AFTER_CHANGE);
	}

	private DocAction getDocument(final Object model)
	{
		final DocAction doc = Services.get(IDocActionBL.class).getDocActionOrNull(model);
		if (doc != null)
		{
			return doc;
		}

		if (model instanceof I_C_OrderLine)
		{
			final I_C_Order order = ((I_C_OrderLine)model).getC_Order();
			return Services.get(IDocActionBL.class).getDocAction(order);
		}

		return null;
	}

	protected final <ModelType> void deletePO(final IMRPContext mrpContext,
			final IMRPExecutor mrpExecutor,
			final Class<ModelType> modelClass,
			final IQueryFilter<ModelType> filter)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<ModelType> modelsQuery = queryBL
				.createQueryBuilder(modelClass, mrpContext)
				.filter(filter);

		final int countDeleted = Services.get(IMRPDocumentDeleteService.class)
				.setMRPContext(mrpContext)
				.setMRPNotesCollector(mrpExecutor.getMRPNotesCollector())
				.delete(modelsQuery);

		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		mrpExecutor.addDeletedDocuments(tableName, countDeleted);
	}

	/**
	 * Called to notify the {@link IMRPExecutorService} that the MRP segment for given <code>mrp</code> record changed.
	 *
	 * The executor can decide if it shall evaluate the given segment again.
	 *
	 * @param mrp
	 */
	protected void notifyAffectedMRPSegment(final I_PP_MRP mrp)
	{
		// services
		final IMRPExecutorService mrpExecutorService = Services.get(IMRPExecutorService.class);
		final IMRPBL mrpBL = Services.get(IMRPBL.class);
		final IMRPSegmentBL mrpSegmentBL = Services.get(IMRPSegmentBL.class);

		final IMRPSegment mrpSegment = mrpBL.createMRPSegment(mrp)
				.setM_Product(null);

		// Make sure that the MRP segment extracted from MRP record is fully qualified.
		// If it's not, there is no point to notify because we cannot identify what was changed.
		if (!mrpSegmentBL.isFullyDefined(mrpSegment))
		{
			// TODO: create a notification

			// final LiberoException ex = new LiberoException("MRP segment for MRP record is not fully defined. Skipped."
			// + "\nMRP Segment: " + mrpSegment
			// + "\nMRP record: " + mrp);
			// log.warn(ex.getLocalizedMessage(), ex);

			return;
		}

		mrpExecutorService.notifyMRPSegmentChanged(mrpSegment);
	}

	protected IMRPQueryBuilder createMRPQueryBuilderForCleanup(final IMRPContext mrpContext, final IMRPExecutor executor)
	{
		return executor.createMRPQueryBuilder(mrpContext)
				.setSkipIfMRPExcluded(false) // when cleaning up, don't exclude anything
		;
	}

	@Override
	public void onQtyOnHandReservation(final IMRPContext mrpContext,
			final IMRPExecutor mrpExecutor,
			final IMRPDemandToSupplyAllocation mrpDemandToSupplyAllocation)
	{
		// nothing
	}
}
