package de.metas.shipper.gateway.go;

import org.adempiere.ad.service.IErrorManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Issue;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder_Log;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.gateway.go
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class DatabaseGOClientLogger implements GOClientLogger
{
	public static final transient DatabaseGOClientLogger instance = new DatabaseGOClientLogger();
	private static final Logger logger = LogManager.getLogger(DatabaseGOClientLogger.class);

	private DatabaseGOClientLogger()
	{
	}

	@Override
	public void log(@NonNull final GOClientLogEvent event)
	{
		try
		{
			createLogRecord(event);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed creating GO log record: {}", event, ex);
		}

		// Also send it to SLF4J logger
		SLF4JGOClientLogger.instance.log(event);
	}

	private void createLogRecord(@NonNull final GOClientLogEvent event)
	{
		final I_GO_DeliveryOrder_Log logRecord = InterfaceWrapperHelper.newInstance(I_GO_DeliveryOrder_Log.class);
		logRecord.setAction(event.getAction());
		logRecord.setGO_ConfigSummary(event.getConfigSummary());
		logRecord.setDurationMillis((int)event.getDurationMillis());
		if (event.getDeliveryOrderRepoId() > 0)
		{
			logRecord.setGO_DeliveryOrder_ID(event.getDeliveryOrderRepoId());
		}

		logRecord.setRequestMessage(event.getRequestAsString());

		if (event.getResponseException() != null)
		{
			logRecord.setIsError(true);

			final I_AD_Issue adIssue = Services.get(IErrorManager.class).createIssue(event.getResponseException());
			logRecord.setAD_Issue_ID(adIssue.getAD_Issue_ID());
		}
		else
		{
			logRecord.setIsError(false);
			logRecord.setResponseMessage(event.getResponseAsString());
		}

		InterfaceWrapperHelper.save(logRecord);
	}
}
