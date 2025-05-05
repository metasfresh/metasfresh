/*
 * #%L
 * de.metas.shipper.gateway.dhl
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

package de.metas.shipper.gateway.dhl.logger;

import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.logging.LogManager;
import de.metas.shipper.gateway.dhl.model.I_Dhl_ShipmentOrder_Log;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.slf4j.Logger;

public class DhlDatabaseClientLogger
{
	public static final transient DhlDatabaseClientLogger instance = new DhlDatabaseClientLogger();
	private static final Logger logger = LogManager.getLogger(DhlDatabaseClientLogger.class);

	private DhlDatabaseClientLogger()
	{
	}

	public void log(@NonNull final DhlClientLogEvent event)
	{
		try
		{
			createLogRecord(event);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed creating Dhl log record: {}", event, ex);
		}

		normalLogging(event);
	}

	private void normalLogging(final DhlClientLogEvent event)
	{
		if (!logger.isTraceEnabled())
		{
			return;
		}

		if (event.getResponseException() != null)
		{
			logger.trace("Dhl Send/Receive error: {}", event, event.getResponseException());
		}
		else
		{
			logger.trace("Dhl Send/Receive OK: {}", event);
		}
	}

	private void createLogRecord(@NonNull final DhlClientLogEvent event)
	{
		final I_Dhl_ShipmentOrder_Log logRecord = InterfaceWrapperHelper.newInstance(I_Dhl_ShipmentOrder_Log.class);
		logRecord.setConfigSummary(event.getConfigSummary());
		logRecord.setDurationMillis((int)event.getDurationMillis());

		logRecord.setDHL_ShipmentOrderRequest_ID(DeliveryOrderId.toRepoId(event.getDeliveryOrderId()));

		//noinspection ConstantConditions
		logRecord.setRequestMessage(event.getRequestAsString());

		if (event.getResponseException() != null)
		{
			logRecord.setIsError(true);

			final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(event.getResponseException());
			logRecord.setAD_Issue_ID(issueId.getRepoId());
		}
		else
		{
			logRecord.setIsError(false);
			//noinspection ConstantConditions
			logRecord.setResponseMessage(event.getResponseAsString());
		}

		InterfaceWrapperHelper.save(logRecord);
	}

}
