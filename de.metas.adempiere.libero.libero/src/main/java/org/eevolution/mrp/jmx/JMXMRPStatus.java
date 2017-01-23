package org.eevolution.mrp.jmx;

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


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.jmx.IJMXNameAware;
import org.adempiere.util.text.IndentedStringBuilder;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPContext;
import org.eevolution.mrp.api.IMRPSegment;
import org.eevolution.mrp.api.IMRPSegmentBL;
import org.eevolution.mrp.spi.impl.DDOrderLineMRPForwardNavigator;
import org.slf4j.Logger;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;

public class JMXMRPStatus implements JMXMRPStatusMBean, IJMXNameAware
{
	private final String jmxName;

	public JMXMRPStatus()
	{
		super();

		jmxName = IMRPContext.LOGGERNAME + ":type=MRPStatus";
	}

	@Override
	public String getJMXName()
	{
		return jmxName;
	}

	@Override
	public String[] getAllMRPSegments()
	{
		//
		// Retrieve all AD_Clients
		final Properties ctx = Env.getCtx();
		final List<I_AD_Client> adClients = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Client.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		final IMRPSegmentBL mrpSegmentBL = Services.get(IMRPSegmentBL.class);

		final Set<IMRPSegment> mrpSegmentsAll = new LinkedHashSet<>();

		for (final I_AD_Client adClient : adClients)
		{
			final int adClientId = adClient.getAD_Client_ID();
			if (adClientId == 0)
			{
				// skip System client because it's not relevant
				continue;
			}

			final IMRPSegment mrpSegment_Client = mrpSegmentBL.createMRPSegment(adClientId);
			final List<IMRPSegment> mrpSegmentExploded = mrpSegmentBL.createFullyDefinedMRPSegments(ctx, mrpSegment_Client);
			mrpSegmentsAll.addAll(mrpSegmentExploded);
		}

		final List<String> mrpSegmentInfos = new ArrayList<>();
		for (final IMRPSegment mrpSegment : mrpSegmentsAll)
		{
			final String mrpSegmentStr = mrpSegment.toString();
			mrpSegmentInfos.add(mrpSegmentStr);
		}

		return mrpSegmentInfos.toArray(new String[mrpSegmentInfos.size()]);
	}

	private final Logger getMRPLogger()
	{
		final Logger mrpLogger = LogManager.getLogger(IMRPContext.LOGGERNAME);
		return mrpLogger;
	}

	@Override
	public String getMRPLogLevel()
	{
		final Logger mrpLogger = getMRPLogger();
		return LogManager.getLoggerLevelName(mrpLogger);
	}

	@Override
	public void setMRPLogLevel(final String logLevel)
	{
		LogManager.setLoggerLevel(getMRPLogger(), logLevel);
	}

	@Override
	public void checkForwardDDOrderDemandsByDDOrderDocumentNo(final String ddOrderDocumentNo)
	{
		Check.assumeNotEmpty(ddOrderDocumentNo, "ddOrderDocumentNo not empty");

		// services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		//
		// Running context
		final IContextAware context = new PlainContextAware(Env.getCtx());

		//
		// Retrieve ALL MRP demands for given DD_Order
		final List<I_PP_MRP> mrpDemands = queryBL
				.createQueryBuilder(I_DD_Order.class, context)
				.addEqualsFilter(I_DD_Order.COLUMN_DocumentNo, ddOrderDocumentNo)
				.andCollectChildren(I_PP_MRP.COLUMN_DD_Order_ID, I_PP_MRP.class)
				.addEqualsFilter(I_PP_MRP.COLUMN_TypeMRP, X_PP_MRP.TYPEMRP_Demand)
				.create()
				.list();
		if (mrpDemands.isEmpty())
		{
			throw new IllegalArgumentException("No MRP Demands found for DD_Order.DocumentNo: " + ddOrderDocumentNo);
		}

		//
		// Iterate MRP demands and find their forward line
		for (final I_PP_MRP mrpDemand : mrpDemands)
		{
			final int ppPlantId = mrpDemand.getS_Resource_ID();

			DDOrderLineMRPForwardNavigator navigator = new DDOrderLineMRPForwardNavigator()
					.setContext(context)
					.setPP_Plant_ID(ppPlantId);
			final Logger logger = navigator.getLogger();

			LogManager.setLoggerLevel(logger, Level.ALL);
			logger.info("-----------------------------------------------------------------------------------");

			navigator.navigateForward(mrpDemand);

			final Set<Integer> collectedDD_Order_IDs = navigator.getCollectedDD_Order_IDs();

			if (!collectedDD_Order_IDs.isEmpty())
			{
				final List<I_DD_Order> collectedDDOrders = queryBL.createQueryBuilder(I_DD_Order.class, context)
						.addInArrayOrAllFilter(I_DD_Order.COLUMN_DD_Order_ID, collectedDD_Order_IDs)
						.create()
						.list();
				logger.info("Collected DD Orders: {}", collectedDDOrders);
			}
			else
			{
				logger.info("No DD Orders were collected");
			}

			logger.info("-----------------------------------------------------------------------------------");
		}
	}

	@Override
	public String checkBackwardDDOrdersForPPOrderDocumentNo(String ppOrderDocumentNo)
	{
		// services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IndentedStringBuilder info = new IndentedStringBuilder();
		info.appendLine("PP_Order DocumentNo:" + ppOrderDocumentNo);

		//
		// Running context
		final IContextAware context = new PlainContextAware(Env.getCtx());

		//
		// Retrieve PP_Order
		final I_PP_Order ppOrder = queryBL
				.createQueryBuilder(I_PP_Order.class, context)
				.addEqualsFilter(I_PP_Order.COLUMN_DocumentNo, ppOrderDocumentNo)
				.create()
				.firstOnly(I_PP_Order.class);
		if (ppOrder == null)
		{
			throw new IllegalArgumentException("No PP_Order found for: " + ppOrderDocumentNo);
		}
		info.appendLine("PP_Order:" + ppOrder);

		final List<I_DD_Order> backwardDDOrders = Services.get(IDDOrderDAO.class).retrieveBackwardSupplyDDOrders(ppOrder);
		info.appendLine("Found " + backwardDDOrders.size() + " backward DD_Order(s).");
		for (I_DD_Order ddOrder : backwardDDOrders)
		{
			info.appendLine("DD_Order: " + ddOrder);
		}

		return info.toString();
	}
}
