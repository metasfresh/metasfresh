package de.metas.document.archive.esb.test.util;

/*
 * #%L
 * de.metas.document.archive.esb.ait
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


import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.server.rpl.imp.TopicImportProcessor;
import org.adempiere.util.Services;
import org.compiere.model.I_IMP_Processor;
import org.compiere.model.I_IMP_Processor_Type;
import org.compiere.server.ReplicationProcessor;
import org.compiere.util.Env;
import org.junit.Ignore;

@Ignore
public class MockedAdempiereImportProcessor
{
	private static final MockedAdempiereImportProcessor instance = new MockedAdempiereImportProcessor();

	public static MockedAdempiereImportProcessor getInstance()
	{
		return instance;
	}

	private final Properties ctx;
	private I_IMP_Processor_Type _importProcessorType;

	private MockedAdempiereImportProcessor()
	{
		super();
		this.ctx = Env.getCtx();
	}

	private I_IMP_Processor_Type getIMP_Processor_Type()
	{
		if (_importProcessorType == null)
		{
			_importProcessorType = InterfaceWrapperHelper.create(ctx, I_IMP_Processor_Type.class, ITrx.TRXNAME_None);
			_importProcessorType.setJavaClass(TopicImportProcessor.class.getName());
			InterfaceWrapperHelper.save(_importProcessorType);
		}
		return _importProcessorType;
	}

	private I_IMP_Processor createIMP_Processor(
			final String jmsServerUrl,
			final String jmsTopicName,
			final boolean jmsDurableSubscription)
	{
		final URI url;
		try
		{
			url = new URI(jmsServerUrl);
		}
		catch (URISyntaxException e)
		{
			throw new IllegalArgumentException("Invalid url " + jmsServerUrl, e);
		}

		//
		// Import Processor & Parameters Definition
		final IIMPProcessorBL bl = Services.get(IIMPProcessorBL.class);
		final I_IMP_Processor importProcessorDef = InterfaceWrapperHelper.create(ctx, I_IMP_Processor.class, ITrx.TRXNAME_None);
		importProcessorDef.setIMP_Processor_Type_ID(getIMP_Processor_Type().getIMP_Processor_Type_ID());
		importProcessorDef.setHost(url.getHost());
		importProcessorDef.setPort(url.getPort());
		InterfaceWrapperHelper.save(importProcessorDef);

		bl.createParameter(importProcessorDef, TopicImportProcessor.PARAM_protocol, url.getScheme());
		bl.createParameter(importProcessorDef, TopicImportProcessor.PARAM_clientID, getClass().getCanonicalName() + "#clientId");
		bl.createParameter(importProcessorDef, TopicImportProcessor.PARAM_isDurableSubscription, jmsDurableSubscription ? "Y" : "N");
		bl.createParameter(importProcessorDef, TopicImportProcessor.PARAM_subscriptionName, getClass().getCanonicalName() + "#subscription");
		bl.createParameter(importProcessorDef, TopicImportProcessor.PARAM_topicName, jmsTopicName);

		return importProcessorDef;
	}

	private ReplicationProcessor createReplicationProcessor(
			final String jmsServerUrl,
			final String jmsTopicName,
			final boolean jmsDurableSubscription) throws Exception
	{
		// Create IMP_Processor definition
		final I_IMP_Processor importProcessorDef = createIMP_Processor(jmsServerUrl, jmsTopicName, jmsDurableSubscription);

		final ReplicationProcessor replicationProcessorThread = new ReplicationProcessor(
				Services.get(IIMPProcessorBL.class).asAdempiereProcessor(importProcessorDef),
				0 // initialNap=0ms
		);
		replicationProcessorThread.setDaemon(true);
		return replicationProcessorThread;
	}

	private Map<String, ReplicationProcessor> _replicationProcessors = new HashMap<String, ReplicationProcessor>();

	public ReplicationProcessor getCreateReplicationProcessor(final String jmsServerUrl,
			final String jmsTopicName,
			final boolean jmsDurableSubscription)
	{
		final String key = jmsServerUrl + "#" + jmsTopicName + "#" + jmsDurableSubscription;
		ReplicationProcessor replicationProcessor = _replicationProcessors.get(key);
		if (replicationProcessor == null)
		{
			try
			{
				replicationProcessor = createReplicationProcessor(jmsServerUrl, jmsTopicName, jmsDurableSubscription);
			}
			catch (Exception e)
			{
				throw new RuntimeException("Cannot create topic listener", e);
			}

			_replicationProcessors.put(key, replicationProcessor);
		}
		return replicationProcessor;
	}

	public void clearAllReplicationProcessors()
	{
		for (ReplicationProcessor replicationProcessor : _replicationProcessors.values())
		{
			replicationProcessor.interrupt();
		}
		_replicationProcessors.clear();
	}

}
