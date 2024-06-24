/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.JsonObjectMapperHolder;
import de.metas.cucumber.stepdefs.event.AD_EventLog_StepDefData;
import de.metas.event.Event;
import de.metas.event.model.I_AD_EventLog;
import de.metas.event.model.I_AD_EventLog_Entry;
import de.metas.event.remote.JacksonJsonEventSerializer;
import de.metas.logging.LogManager;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class AD_EventLog_Entry_StepDef
{
	private final static Logger logger = LogManager.getLogger(AD_EventLog_Entry_StepDef.class);

	final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AD_EventLog_Entry_StepDefData eventLogEntryTable;
	private final AD_EventLog_StepDefData eventLogTable;
	private final M_Product_StepDefData productTable;

	public AD_EventLog_Entry_StepDef(
			@NonNull final AD_EventLog_Entry_StepDefData eventLogEntryTable,
			@NonNull final AD_EventLog_StepDefData eventLogTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.eventLogEntryTable = eventLogEntryTable;
		this.eventLogTable = eventLogTable;
		this.productTable = productTable;
	}

	@And("metasfresh has no AD_EventLog_Entry records")
	public void delete_AD_EventLog_Entry_records()
	{
		DB.executeUpdateEx("DELETE FROM AD_EventLog_Entry;", ITrx.TRXNAME_None);
	}

	@And("metasfresh has no AD_EventLog records")
	public void delete_AD_EventLog_records()
	{
		DB.executeUpdateEx("DELETE FROM AD_EventLog_Entry;", ITrx.TRXNAME_None);
	}

	@And("^after not more than (.*)s, AD_EventLog_Entry are found$")
	public void findAD_EventLog_Entry(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String classname = DataTableUtil.extractStringForColumnName(row, I_AD_EventLog_Entry.COLUMNNAME_Classname);
			final String msgText = DataTableUtil.extractStringForColumnName(row, I_AD_EventLog_Entry.COLUMNNAME_MsgText);
			final boolean processed = DataTableUtil.extractBooleanForColumnName(row, I_AD_EventLog_Entry.COLUMNNAME_Processed);
			final String eventLogIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_EventLog.COLUMNNAME_AD_EventLog_ID + "." + TABLECOLUMN_IDENTIFIER);
			final int eventLogId = eventLogTable.get(eventLogIdentifier).getAD_EventLog_ID();

			final String eventLogEntryIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_EventLog_Entry.COLUMNNAME_AD_EventLog_Entry_ID + "." + TABLECOLUMN_IDENTIFIER);

			final Supplier<Boolean> logEntryRecordFound = () -> {
				final I_AD_EventLog_Entry eventLogEntryRecord = queryBL.createQueryBuilder(I_AD_EventLog_Entry.class)
						.addEqualsFilter(I_AD_EventLog_Entry.COLUMNNAME_AD_EventLog_ID, eventLogId)
						.addEqualsFilter(I_AD_EventLog_Entry.COLUMNNAME_Classname, classname)
						.addEqualsFilter(I_AD_EventLog_Entry.COLUMNNAME_Processed, processed)
						.addStringLikeFilter(I_AD_EventLog_Entry.COLUMNNAME_MsgText, msgText, true)
						.create()
						.firstOnly(I_AD_EventLog_Entry.class);

				if (eventLogEntryRecord == null)
				{
					return false;
				}

				eventLogEntryTable.put(eventLogEntryIdentifier, eventLogEntryRecord);
				return true;
			};

			StepDefUtil.tryAndWait(timeoutSec, 1000, logEntryRecordFound, () -> logCurrentContext(row));
		}
	}

	@And("^after not more than (.*)s, AD_EventLog are found$")
	public void findAD_EventLog(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String eventDataPattern = DataTableUtil.extractStringForColumnName(row, I_AD_EventLog.COLUMNNAME_EventData + ".Pattern");
			final String eventType = DataTableUtil.extractStringForColumnName(row, "EventType");

			final Supplier<Optional<I_AD_EventLog>> getEventLogRecord = () -> {

				final List<I_AD_EventLog> eventLogRecords = queryBL.createQueryBuilder(I_AD_EventLog.class)
						.addStringLikeFilter(I_AD_EventLog.COLUMNNAME_EventData, eventDataPattern, true)
						.create()
						.list(I_AD_EventLog.class);

				if (eventLogRecords.size() == 0)
				{
					return Optional.empty();
				}

				if (SupplyRequiredEvent.TYPE.equals(eventType))
				{
					return extractSupplyRequiredEventLog(row, eventLogRecords);
				}

				throw new RuntimeException("Unsupported MaterialEvent type=" + eventType);
			};

			final I_AD_EventLog eventLog = StepDefUtil.tryAndWaitForItem(timeoutSec,
																		 500,
																		 getEventLogRecord,
																		 this::logCurrentContextForEventLogRecords);

			final String eventLogIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_EventLog.COLUMNNAME_AD_EventLog_ID + "." + TABLECOLUMN_IDENTIFIER);

			eventLogTable.put(eventLogIdentifier, eventLog);
		}
	}


	private void logCurrentContext(@NonNull final Map<String, String> row)
	{
		final String classname = DataTableUtil.extractStringForColumnName(row, I_AD_EventLog_Entry.COLUMNNAME_Classname);
		final String msgText = DataTableUtil.extractStringForColumnName(row, I_AD_EventLog_Entry.COLUMNNAME_MsgText);
		final boolean processed = DataTableUtil.extractBooleanForColumnName(row, I_AD_EventLog_Entry.COLUMNNAME_Processed);

		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(I_AD_EventLog_Entry.COLUMNNAME_Classname).append(" : ").append(classname).append("\n")
				.append(I_AD_EventLog_Entry.COLUMNNAME_MsgText).append(" : ").append(msgText).append("\n")
				.append(I_AD_EventLog_Entry.COLUMNNAME_Processed).append(" : ").append(processed).append("\n");

		message.append("AD_EventLog_Entry records:").append("\n");

		queryBL.createQueryBuilder(I_AD_EventLog_Entry.class)
				.create()
				.stream(I_AD_EventLog_Entry.class)
				.forEach(eventLogEntry -> message
						.append(I_AD_EventLog_Entry.COLUMNNAME_Classname).append(" : ").append(eventLogEntry.getClassname()).append(" ; ")
						.append(I_AD_EventLog_Entry.COLUMNNAME_MsgText).append(" : ").append(eventLogEntry.getMsgText()).append(" ; ")
						.append(I_AD_EventLog_Entry.COLUMNNAME_Processed).append(" : ").append(eventLogEntry.isProcessed()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while looking for AD_EventLog_Entry records, see current context: \n" + message);
	}

	@NonNull
	private Optional<I_AD_EventLog> extractSupplyRequiredEventLog(
			@NonNull final Map<String, String> row,
			@NonNull final List<I_AD_EventLog> eventLogRecords)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, SupplyRequiredEvent.TYPE + "." + I_M_Product.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);

		final int productId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(() -> Integer.parseInt(productIdentifier));

		final List<I_AD_EventLog> matchingLogs = new ArrayList<>();

		for (final I_AD_EventLog eventLog : eventLogRecords)
		{
			final Event event = JacksonJsonEventSerializer.instance.fromString(eventLog.getEventData());

			final SupplyRequiredEvent supplyRequiredEvent = getSpecificMaterialEvent(event, SupplyRequiredEvent.class);

			if (supplyRequiredEvent != null
					&& supplyRequiredEvent.getSupplyRequiredDescriptor().getMaterialDescriptor().getProductId() == productId)
			{
				matchingLogs.add(eventLog);

				if (matchingLogs.size() > 1)
				{
					throw new RuntimeException("Too many matching logs found!");
				}
			}
		}

		return matchingLogs.size() == 1
				? Optional.of(matchingLogs.get(0))
				: Optional.empty();
	}

	private void logCurrentContextForEventLogRecords()
	{
		final StringBuilder message = new StringBuilder("These are all the records in AD_EventLog table: \n");

		queryBL.createQueryBuilder(I_AD_EventLog.class)
				.create()
				.stream(I_AD_EventLog.class)
				.forEach(eventLogEntry -> message
						.append(I_AD_EventLog.COLUMNNAME_EventData).append(" : ").append(eventLogEntry.getEventData())
						.append("\n"));

		logger.error("*** Error while looking for AD_EventLog records, see current context: \n" + message);
	}

	@Nullable
	private static  <T> T getSpecificMaterialEvent(@NonNull final Event materialEvent, @NonNull final Class<T> clazz)
	{
		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper()
					.readValue((String)materialEvent.getProperties().get("MaterialEvent"), clazz);
		}
		catch (final Exception exception)
		{
			return null;
		}
	}
}
