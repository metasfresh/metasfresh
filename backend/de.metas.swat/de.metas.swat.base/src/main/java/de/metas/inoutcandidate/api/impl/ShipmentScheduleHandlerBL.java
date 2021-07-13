package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getCtx;
import static org.adempiere.model.InterfaceWrapperHelper.getId;
import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_OrderLine;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;

import com.google.common.annotations.VisibleForTesting;

import ch.qos.logback.classic.Level;
import de.metas.cache.CCache;
import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.api.IDeliverRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_IolCandHandler;
import de.metas.inoutcandidate.model.I_M_IolCandHandler_Log;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ModelWithoutShipmentScheduleVetoer;
import de.metas.inoutcandidate.spi.ModelWithoutShipmentScheduleVetoer.OnMissingCandidate;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ShipmentScheduleHandlerBL implements IShipmentScheduleHandlerBL
{
	private static final String MSG_RECORDS_CREATED_1P = "de.metas.inoutCandidate.RECORDS_CREATED";
	private static final String MSG_RECORD_CREATION_VETOED_1P = "de.metas.inoutCandidate.RECORD_CREATION_VETOED";

	private final static Logger logger = LogManager.getLogger(ShipmentScheduleHandlerBL.class);

	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	private final Map<String, ShipmentScheduleHandler> tableName2Handler = new HashMap<>();

	private final Map<String, List<ModelWithoutShipmentScheduleVetoer>> tableName2Listeners = new HashMap<>();

	@Override
	public <T extends ShipmentScheduleHandler> void registerHandler(@NonNull final T handler)
	{
		Check.errorIf(tableName2Handler.containsKey(handler.getSourceTable()),
				"A handler was already registered for tableName={}; handlerClass={};",
				handler.getSourceTable(), handler.getClass());

		// do the actual registering
		final ShipmentScheduleHandler oldImpl = tableName2Handler.put(handler.getSourceTable(), handler);
		Check.assume(oldImpl == null, "There is only one attempt to register a handler for table '" + handler.getSourceTable() + "'");

		// make sure that there is also a list of listeners (albeit empty) for the handler's source table
		if (!tableName2Listeners.containsKey(handler.getSourceTable()))
		{
			tableName2Listeners.put(handler.getSourceTable(), new ArrayList<ModelWithoutShipmentScheduleVetoer>());
		}

		// make sure that there is an M_IolCandHandler record for the handler
		final I_M_IolCandHandler existingRecord = retrieveHandlerRecordOrNull(handler.getClass().getName());
		final int existingRecordId;
		if (existingRecord == null)
		{
			final I_M_IolCandHandler handlerRecord = newInstance(I_M_IolCandHandler.class);
			handlerRecord.setClassname(handler.getClass().getName());
			handlerRecord.setAD_Org_ID(0);
			handlerRecord.setTableName(handler.getSourceTable());
			saveRecord(handlerRecord);

			existingRecordId = handlerRecord.getM_IolCandHandler_ID();
		}
		else
		{
			existingRecordId = existingRecord.getM_IolCandHandler_ID();
		}

		handler.setM_IolCandHandler_IDOneTimeOnly(existingRecordId);

		logger.info("Registered handler: {}", handler);
	}

	private final CCache<String, I_M_IolCandHandler> className2HandlerRecord = //
			CCache.newCache(
					I_M_IolCandHandler.Table_Name + "#by" + I_M_IolCandHandler.COLUMNNAME_Classname,
					5,
					CCache.EXPIREMINUTES_Never);

	@VisibleForTesting
	public I_M_IolCandHandler retrieveHandlerRecordOrNull(final String className)
	{
		return queryBL
				.createQueryBuilder(I_M_IolCandHandler.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_IolCandHandler.COLUMNNAME_Classname, className)
				.orderBy()
				.addColumn(I_M_IolCandHandler.Table_Name)
				.endOrderBy()
				.create()
				.firstOnly(I_M_IolCandHandler.class);
	}

	@Override
	public void registerVetoer(final ModelWithoutShipmentScheduleVetoer l, final String tableName)
	{
		List<ModelWithoutShipmentScheduleVetoer> listeners = tableName2Listeners.get(tableName);
		if (listeners == null)
		{
			listeners = new ArrayList<>();
			tableName2Listeners.put(tableName, listeners);
		}
		listeners.add(l);
	}

	@Override
	public Set<ShipmentScheduleId> createMissingCandidates(@NonNull final Properties ctx)
	{
		final LinkedHashSet<ShipmentScheduleId> result = new LinkedHashSet<>();

		for (final String tableName : tableName2Handler.keySet())
		{
			final ShipmentScheduleHandler handler = tableName2Handler.get(tableName);
			try (final MDCCloseable handlerMDC = MDC.putCloseable("ShipmentScheduleHandler.className", handler.getClass().getName()))
			{
				result.addAll(invokeHandler(ctx, handler));
			}
		}
		return result;
	}

	private LinkedHashSet<ShipmentScheduleId> invokeHandler(
			@NonNull final Properties ctx,
			@NonNull final ShipmentScheduleHandler handler)
	{
		final String handlerClassName = handler.getClass().getName();

		final I_M_IolCandHandler handlerRecord = className2HandlerRecord.getOrLoad(
				handlerClassName,
				() -> retrieveHandlerRecordOrNull(handler.getClass().getName()));

		final LinkedHashSet<ShipmentScheduleId> result = new LinkedHashSet<>();

		final Iterator<? extends Object> missingCandidateModels = handler.retrieveModelsWithMissingCandidates(ctx, ITrx.TRXNAME_ThreadInherited);
		while (missingCandidateModels.hasNext())
		{
			final Object model = missingCandidateModels.next();
			try (final MDCCloseable modelMDC = TableRecordMDC.putTableRecordReference(model))
			{
				result.addAll(invokeHandlerForModel(ctx, handler, handlerRecord, model));
			}
		}
		return result;
	}

	private LinkedHashSet<ShipmentScheduleId> invokeHandlerForModel(
			@NonNull final Properties ctx,
			@NonNull final ShipmentScheduleHandler handler,
			@NonNull final I_M_IolCandHandler handlerRecord,
			@NonNull final Object model)
	{
		final LinkedHashSet<ShipmentScheduleId> result = new LinkedHashSet<>();

		final List<ModelWithoutShipmentScheduleVetoer> vetoListeners = new ArrayList<>();

		for (final ModelWithoutShipmentScheduleVetoer l : tableName2Listeners.get(handler.getSourceTable()))
		{
			final OnMissingCandidate listenerResult = l.foundModelWithoutInOutCandidate(model);
			if (listenerResult == OnMissingCandidate.I_VETO)
			{
				Loggables.withLogger(logger, Level.DEBUG)
						.addLog("IInOutCandHandlerListener {} doesn't want us to create a shipment schedule", l);
				vetoListeners.add(l);
			}
		}

		if (vetoListeners.isEmpty())
		{
			// There was no listener forbidding us to create one or more I_M_ShipmentSchedules for 'model'
			final List<I_M_ShipmentSchedule> candidatesForModel = handler.createCandidatesFor(model);
			for (final I_M_ShipmentSchedule newSched : candidatesForModel)
			{
				newSched.setM_IolCandHandler_ID(handlerRecord.getM_IolCandHandler_ID());
				saveRecord(newSched);

				result.add(ShipmentScheduleId.ofRepoId(newSched.getM_ShipmentSchedule_ID()));
			}

			saveHandlerLog(handlerRecord, model,
					msgBL.getMsg(ctx, MSG_RECORDS_CREATED_1P,
							new Object[] {
									candidatesForModel.size(), msgBL.translate(ctx, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID) }));
		}
		else
		{
			final StringBuilder vetoNames = new StringBuilder();
			for (final ModelWithoutShipmentScheduleVetoer vetoListener : vetoListeners)
			{
				if (vetoNames.length() > 0)
				{
					vetoNames.append(", ");
				}
				vetoNames.append(vetoListener.getClass().getName());
			}

			saveHandlerLog(handlerRecord, model,
					msgBL.getMsg(ctx, MSG_RECORD_CREATION_VETOED_1P,
							new Object[] {
									msgBL.translate(ctx, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID), vetoNames }));
		}

		return result;
	}

	@Override
	public void updateShipmentScheduleFromReferencedRecord(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		final ShipmentScheduleHandler handler = getHandlerFor(shipmentScheduleRecord);
		handler.updateShipmentScheduleFromReferencedRecord(shipmentScheduleRecord);
	}

	private void saveHandlerLog(
			@NonNull final I_M_IolCandHandler handlerRecord,
			@Nullable final Object referencedModel,
			@NonNull final String status)
	{
		final Properties ctx = getCtx(handlerRecord);
		final String trxName = getTrxName(handlerRecord);

		final I_M_IolCandHandler_Log logRecord = create(ctx, I_M_IolCandHandler_Log.class, trxName);
		logRecord.setAD_Table_ID(tableDAO.retrieveTableId(handlerRecord.getTableName()));
		logRecord.setRecord_ID(getId(referencedModel));
		logRecord.setM_IolCandHandler_ID(handlerRecord.getM_IolCandHandler_ID());
		logRecord.setStatus(status);

		try (final MDCCloseable logRecordMDC = TableRecordMDC.putTableRecordReference(logRecord))
		{
			saveRecord(logRecord);
			logger.debug(status);
		}
	}

	@Override
	public IDeliverRequest createDeliverRequest(@NonNull final I_M_ShipmentSchedule sched, final I_C_OrderLine salesOrderLine)
	{
		return getHandlerFor(sched).createDeliverRequest(sched, salesOrderLine);
	}

	@Override
	public ShipmentScheduleHandler getHandlerFor(@NonNull final I_M_ShipmentSchedule sched)
	{
		final String tableName = adTableDAO.retrieveTableName(sched.getAD_Table_ID());

		final ShipmentScheduleHandler shipmentScheduleHandler = tableName2Handler.get(tableName);
		if (shipmentScheduleHandler == null)
		{
			throw new AdempiereException("No shipment schedule handler defined for " + tableName + " (" + sched + ")");
		}

		return shipmentScheduleHandler;
	}
}
