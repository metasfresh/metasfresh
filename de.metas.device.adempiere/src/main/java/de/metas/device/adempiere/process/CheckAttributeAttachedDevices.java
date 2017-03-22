package de.metas.device.adempiere.process;

import java.net.UnknownHostException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.net.IHostIdentifier;
import org.adempiere.util.net.NetUtils;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Stopwatch;

import de.metas.device.adempiere.AttributesDevicesHub;
import de.metas.device.adempiere.AttributesDevicesHub.AttributeDeviceAccessor;
import de.metas.device.adempiere.IDevicesHubFactory;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;

/*
 * #%L
 * de.metas.device.adempiere
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Checks all attribute attached devices.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CheckAttributeAttachedDevices extends JavaProcess
{
	// services
	private static final Logger logger = LogManager.getLogger(CheckAttributeAttachedDevices.class);
	private final transient IDevicesHubFactory devicesHubFactory = Services.get(IDevicesHubFactory.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	//
	// parameters
	@Param(parameterName = "Host")
	private String p_Host;
	@Param(parameterName = "Counter")
	private int p_AccessTimes;
	private static final int DEFAULT_AccessTimes = 10;
	@Param(parameterName = "M_Attribute_ID")
	private int p_M_Attribute_ID;

	//
	// state
	private int countDevicesChecked = 0;

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final IHostIdentifier host = getHost();
		addLog("Using host: " + host);

		final int adClientId = Env.getAD_Client_ID(getCtx());
		final int adOrgId = Env.getAD_Org_ID(getCtx());
		addLog("Using AD_Client_ID: " + adClientId);
		addLog("Using AD_Org_ID: " + adOrgId);

		final int accessTimesPerDevice = p_AccessTimes > 0 ? p_AccessTimes : DEFAULT_AccessTimes;
		addLog("Access times per device: " + p_AccessTimes);

		final AttributesDevicesHub devicesHub = devicesHubFactory.getAttributesDevicesHub(host, adClientId, adOrgId);
		addLog("Using devices hub: " + devicesHub);

		streamAllAttributeCodes()
				.map(attributeCode -> devicesHub.getAttributeDeviceAccessors(attributeCode))
				.peek(deviceAccessorsList -> deviceAccessorsList.consumeWarningMessageIfAny(warningMessage -> addLog("WARNING: " + warningMessage)))
				.flatMap(deviceAccessorsList -> deviceAccessorsList.stream())
				.forEach(deviceAccessor -> accessDeviceNTimes(deviceAccessor, accessTimesPerDevice));

		if (countDevicesChecked <= 0)
		{
			throw new AdempiereException("No devices found");
		}

		return MSG_OK;
	}

	private final IHostIdentifier getHost() throws UnknownHostException
	{
		if (Check.isEmpty(p_Host, true))
		{
			return NetUtils.getLocalHost();
		}
		else
		{
			return NetUtils.of(p_Host);
		}
	}

	private final Stream<String> streamAllAttributeCodes()
	{
		final IQueryBuilder<I_M_Attribute> queryBuilder = queryBL
				.createQueryBuilder(I_M_Attribute.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClientOrSystem()
				//
				.orderBy()
				.addColumn(I_M_Attribute.COLUMN_M_Attribute_ID)
				.endOrderBy();

		if (p_M_Attribute_ID > 0)
		{
			queryBuilder.addEqualsFilter(I_M_Attribute.COLUMN_M_Attribute_ID, p_M_Attribute_ID);
		}

		return queryBuilder
				.create()
				.stream(I_M_Attribute.class)
				.map(attribute -> attribute.getValue());
	}

	private void accessDeviceNTimes(final AttributeDeviceAccessor deviceAccessor, final int times)
	{
		IntStream.rangeClosed(1, times)
				.forEach(time -> accessDevice(deviceAccessor, time));
	}

	private void accessDevice(final AttributeDeviceAccessor deviceAccessor, final int time)
	{
		logger.info("Accessing({}) {}", time, deviceAccessor);

		countDevicesChecked++;

		final Stopwatch stopwatch = Stopwatch.createStarted();

		try
		{
			logger.debug("Getting response from {}", deviceAccessor);

			final Object value = deviceAccessor.acquireValue();

			logger.debug("Got respose from {}: {}", deviceAccessor, value);

			addLog("OK(" + time + "): accessed " + deviceAccessor + " and got '" + value + "' in " + stopwatch);
		}
		catch (final Exception ex)
		{
			final String errmsg = "Error(" + time + "): Failed accessing " + deviceAccessor + ": " + ex.getLocalizedMessage();
			addLog(errmsg);
			logger.warn(errmsg, ex);
		}
	}
}
