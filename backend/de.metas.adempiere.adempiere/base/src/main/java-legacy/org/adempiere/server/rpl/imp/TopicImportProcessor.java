/**********************************************************************
 * This file is part of Adempiere ERP Bazaar *
 * http://www.adempiere.org *
 * *
 * Copyright (C) Trifon Trifonov. *
 * Copyright (C) Contributors *
 * *
 * This program is free software; you can redistribute it and/or *
 * modify it under the terms of the GNU General Public License *
 * as published by the Free Software Foundation; either version 2 *
 * of the License, or (at your option) any later version. *
 * *
 * This program is distributed in the hope that it will be useful, *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the *
 * GNU General Public License for more details. *
 * *
 * You should have received a copy of the GNU General Public License *
 * along with this program; if not, write to the Free Software *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, *
 * MA 02110-1301, USA. *
 * *
 * Contributors: *
 * - Trifon Trifonov (trifonnt@users.sourceforge.net) *
 * *
 * Sponsors: *
 * - E-evolution (http://www.e-evolution.com/) *
 **********************************************************************/
package org.adempiere.server.rpl.imp;

import java.util.List;
import java.util.Properties;

import org.adempiere.server.rpl.IImportProcessor;
import org.adempiere.server.rpl.IReplicationProcessor;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.server.rpl.api.IIMPProcessorDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_IMP_Processor;
import org.compiere.model.I_IMP_ProcessorParameter;
// import org.compiere.model.MIMPProcessor;
// import org.compiere.model.X_IMP_ProcessorParameter;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Aim of this class is to import records from JMS Server.
 *
 * @author Trifon N. Trifonov
 * @version $Id:$
 */
public class TopicImportProcessor implements IImportProcessor
{

	/** Logger */
	protected Logger log = LogManager.getLogger(TopicImportProcessor.class);

	public static final String PARAM_topicName = "topicName";

	public static final String PARAM_protocol = "protocol";

	public static final String PARAM_isDurableSubscription = "isDurableSubscription";

	public static final String PARAM_subscriptionName = "subscriptionName";

	public static final String PARAM_clientID = "clientID";

	/**
	 * Topic Listener
	 */
	private TopicListener topicListener = null;

	@Override
	public void start(final Properties ctx, final IReplicationProcessor replicationProcessor, final String trxName) throws Exception
	{
		log.info("Starting {} ({})", replicationProcessor, replicationProcessor.getMImportProcessor());

		final I_IMP_Processor impProcessor = replicationProcessor.getMImportProcessor();
		final List<I_IMP_ProcessorParameter> processorParameters = Services.get(IIMPProcessorDAO.class).retrieveParameters(impProcessor, trxName);

		final String host = impProcessor.getHost();
		final int port = impProcessor.getPort();
		final String account = impProcessor.getAccount();
		final String password = impProcessor.getPasswordInfo();

		// mandatory parameters!
		String topicName = null;
		String protocol = null;
		boolean isDurableSubscription = true;
		String subscriptionName = null;
		final String options = null;
		String clientID = null;

		for (final I_IMP_ProcessorParameter processorParameter : processorParameters)
		{
			final String parameterName = processorParameter.getValue();
			log.debug("Parameters: {} = {}" + parameterName, processorParameter.getParameterValue());

			if (parameterName.equals(PARAM_topicName))
			{
				topicName = processorParameter.getParameterValue();
			}
			else if (parameterName.equals(PARAM_protocol))
			{
				protocol = processorParameter.getParameterValue();
			}
			else if (parameterName.equals(PARAM_isDurableSubscription))
			{
				isDurableSubscription = Boolean.parseBoolean(processorParameter.getParameterValue());
			}
			else if (parameterName.equals(PARAM_subscriptionName))
			{
				subscriptionName = processorParameter.getParameterValue();
			}
			else if (parameterName.equals(PARAM_clientID))
			{
				clientID = processorParameter.getParameterValue();
			}
			else
			{
				// Some other mandatory parameter here
			}
		}

		if (topicName == null || topicName.length() == 0)
		{
			throw new Exception("Missing " + I_IMP_ProcessorParameter.Table_Name + " with key '" + PARAM_topicName + "'!");
		}
		if (protocol == null || protocol.length() == 0)
		{
			throw new Exception("Missing " + I_IMP_ProcessorParameter.Table_Name + " with key '" + PARAM_protocol + "'!");
		}
		if (isDurableSubscription && subscriptionName == null || subscriptionName.length() == 0)
		{
			throw new Exception("Missing " + I_IMP_ProcessorParameter.Table_Name + " with key '" + PARAM_subscriptionName + "'!");
		}
		if (clientID == null || clientID.length() == 0)
		{
			throw new Exception("Missing " + I_IMP_ProcessorParameter.Table_Name + " with key '" + PARAM_clientID + "'!");
		}

		topicListener = new TopicListener(ctx,
				replicationProcessor,
				protocol,
				host,
				port,
				isDurableSubscription,
				subscriptionName,
				topicName,
				clientID,
				account,
				password,
				options,
				trxName);

		topicListener.run();
		log.info("Topic listener started: {}", topicListener);
	}

	@Override
	public void stop() throws Exception
	{

		if (topicListener != null)
		{
			topicListener.stop();
			log.info("Topic listener stopped: {}", topicListener);
		}
		topicListener = null;
	}

	@Override
	public void createInitialParameters(final I_IMP_Processor processor)
	{
		final IIMPProcessorBL impProcessorBL = Services.get(IIMPProcessorBL.class);

		impProcessorBL.createParameter(processor,
				PARAM_topicName,
				"Name of JMS Topic from where xml will be Imported",
				"Import Processor Parameter Description",
				"JMS Topic Import Processor Parameter Help",
				"ExampleTopic");
		impProcessorBL.createParameter(processor,
				PARAM_protocol,
				"protocol which will be used for JMS connection",
				"Import Processor Parameter Description",
				"JMS Topic Import Processor Parameter Help",
				"tcp");
		impProcessorBL.createParameter(processor,
				PARAM_isDurableSubscription,
				"Durable Subscription",
				"Import Processor Parameter Description",
				"JMS Topic Import Processor Parameter Help",
				"true");
		impProcessorBL.createParameter(processor,
				PARAM_subscriptionName,
				"Subscription Name",
				"Import Processor Parameter Description",
				"JMS Topic Import Processor Parameter Help",
				"exampleSubName");
		impProcessorBL.createParameter(processor,
				PARAM_clientID,
				"JMS Connection Client ID",
				"Import Processor Parameter Description",
				"JMS Topic Import Processor Parameter Help",
				"ImpClientID");
	}
}
