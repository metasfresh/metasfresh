package de.metas.rpl.spi.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.process.rpl.XMLHelper;
import org.adempiere.server.rpl.exceptions.ExportProcessorException;
import org.adempiere.util.Check;
import org.compiere.model.I_EXP_ProcessorParameter;
import org.compiere.model.MEXPProcessor;
import org.compiere.model.MSysConfig;
import org.compiere.model.PO;
import org.compiere.model.X_EXP_ProcessorParameter;
import org.compiere.util.CLogger;
import org.compiere.util.Trx;
import org.w3c.dom.Document;

public class TopicRplExportProcessor implements org.adempiere.process.rpl.IExportProcessor2
{
	public static final String SYSCONFIG_Debug = "de.metas.processor.TopicRplExportProcessor.Debug";

	public static class TopicParameters
	{
		private String protocol = "http";
		private String host = "localhost";
		private int port = 61616;
		private String account = null;
		private String password = null;
		private String topicName = "";
		private String clientID = null;
		private int timeToLive = 10000;
		private boolean isDeliveryModePersistent = true;
		private String dirOutputMsg = null;
		private String dirErrorMsg = null;

		public String getProtocol()
		{
			return protocol;
		}

		public void setProtocol(String protocol)
		{
			this.protocol = protocol;
		}

		public String getHost()
		{
			return host;
		}

		public void setHost(String host)
		{
			this.host = host;
		}

		public int getPort()
		{
			return port;
		}

		public void setPort(int port)
		{
			this.port = port;
		}

		public String getAccount()
		{
			return account;
		}

		public void setAccount(String account)
		{
			this.account = account;
		}

		public String getPassword()
		{
			return password;
		}

		public void setPassword(String password)
		{
			this.password = password;
		}

		public String getTopicName()
		{
			return topicName;
		}

		public void setTopicName(String topicName)
		{
			this.topicName = topicName;
		}

		public String getClientID()
		{
			return clientID;
		}

		public void setClientID(String clientID)
		{
			this.clientID = clientID;
		}

		public int getTimeToLive()
		{
			return timeToLive;
		}

		public void setTimeToLive(int timeToLive)
		{
			this.timeToLive = timeToLive;
		}

		public boolean isDeliveryModePersistent()
		{
			return isDeliveryModePersistent;
		}

		public void setDeliveryModePersistent(boolean isDeliveryModePersistent)
		{
			this.isDeliveryModePersistent = isDeliveryModePersistent;
		}
		
		public String getDirOutputMsg()
		{
			return dirOutputMsg;
		}

		public void setDirOutputMsg(String dirOutputMsg)
		{
			this.dirOutputMsg = dirOutputMsg;
		}

		public String getDirErrorMsg()
		{
			return dirErrorMsg;
		}

		public void setDirErrorMsg(String dirErrorMsg)
		{
			this.dirErrorMsg = dirErrorMsg;
		}

		@Override
		public String toString()
		{
			return "TopicParameters [protocol=" + protocol + ", host=" + host + ", port=" + port + ", account=" + account + ", password=" + password + ", topicName=" + topicName
					+ ", clientID=" + clientID
					+ ", isDeliveryModePersistent=" + isDeliveryModePersistent
					+ ", timeToLive=" + timeToLive
					+ ", dirOutputMsg=" + dirOutputMsg
					+ ", dirErrorMsg=" + dirErrorMsg
					+ "]";
		}
	}

	protected final CLogger log = CLogger.getCLogger(getClass());

	@Override
	public void process(MEXPProcessor expProcessor, Document document, PO po) throws ExportProcessorException
	{
		final Properties ctx = po.getCtx();
		final Trx trx = Trx.get(po.get_TrxName(), false);
		this.process(ctx, expProcessor, document, trx);
	}

	@Override
	public void process(Properties ctx, MEXPProcessor expProcessor, Document document, Trx trx)
			throws ExportProcessorException
	{
		final TopicParameters params = getParameters(expProcessor);
		sendDocument(params, document);
	}

	protected void sendDocument(TopicParameters params, Document document) throws ExportProcessorException
	{
		if (MSysConfig.getBooleanValue(SYSCONFIG_Debug, false))
		{
			sendToTempFile(document, params.getTopicName());
		}
		else
		{
			try
			{
				sendJMSMessage(params, document);
				
				// Save output message
				final String dirOutputMsg = params.getDirOutputMsg();
				if (!Check.isEmpty(dirOutputMsg, true))
				{
					final File dir = new File(dirOutputMsg.trim());
					try
					{
						sendToFile(dir, document, params.getTopicName());
					}
					catch(Exception e)
					{
						log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					}
				}
			}
			catch (Exception e)
			{
				// Save error message
				final String dirErrorMsg = params.getDirErrorMsg();
				if (!Check.isEmpty(dirErrorMsg, true))
				{
					final File dir = new File(dirErrorMsg.trim());
					try
					{
						sendToFile(dir, document, params.getTopicName());
					}
					catch(Exception e2)
					{
						log.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
					}
				}

				// throw exception
				throw new ExportProcessorException(e.getLocalizedMessage(), e);
			}
		}
	}

	@Override
	public void createInitialParameters(MEXPProcessor processor)
	{
		processor.createParameter(
				"topicName",
				"Name of JMS Topic from where xml will be Emported",
				"Export Processor Parameter Description",
				"JMS Topic Export Processor Parameter Help",
				"ExampleTopic");
		processor.createParameter(
				"protocol",
				"protocol which will be used for JMS connection",
				"Export Processor Parameter Description",
				"JMS Topic Export Processor Parameter Help",
				"tcp");
		processor.createParameter(
				"clientID",
				"JMS Connection Client ID",
				"Export Processor Parameter Description",
				"JMS Topic Export Processor Parameter Help",
				"ExpClientID");
		processor.createParameter(
				"timeToLive",
				"Time to Live",
				"Export Processor Parameter Description",
				"JMS Topic Export Processor Parameter Help",
				"10000");
		processor.createParameter(
				"isDeliveryModePersistent",
				"Persistent Delivery",
				"Export Processor Parameter Description",
				"JMS Topic Export Processor Parameter Help",
				"true");
		
		processor.createParameter(
				"SaveOutputToDir",
				"Directory for outgoing messages",
				"Directory where system is saving outgoing messages, for audit purposes",
				"Leave it empty if you don't want to save them",
				"");
		processor.createParameter(
				"SaveErrorsToDir",
				"Directory for messages with errors",
				"Directory where system is saving outgoing messages that were not send for various reasons",
				"Leave it empty if you don't want to save them",
				"");
	}

	protected TopicParameters getParameters(MEXPProcessor expProcessor)
	{
		TopicParameters params = new TopicParameters();
		params.setHost(expProcessor.getHost());
		params.setPort(expProcessor.getPort());
		params.setAccount(expProcessor.getAccount());
		params.setPassword(expProcessor.getPasswordInfo());

		// Read all processor parameters and set them!
		I_EXP_ProcessorParameter[] processorParameters = expProcessor.getEXP_ProcessorParameters();
		if (processorParameters != null && processorParameters.length > 0)
		{
			for (int i = 0; i < processorParameters.length; i++)
			{
				if (processorParameters[i].getValue().equals("topicName"))
				{
					params.setTopicName(processorParameters[i].getParameterValue());
				}
				else if (processorParameters[i].getValue().equals("protocol"))
				{
					params.setProtocol(processorParameters[i].getParameterValue());
				}
				else if (processorParameters[i].getValue().equals("clientID"))
				{
					params.setClientID(processorParameters[i].getParameterValue());
				}
				else if (processorParameters[i].getValue().equals("timeToLive"))
				{
					params.setTimeToLive(Integer.parseInt(processorParameters[i].getParameterValue()));
				}
				else if (processorParameters[i].getValue().equals("isDeliveryModePersistent"))
				{
					params.setDeliveryModePersistent(Boolean.parseBoolean(processorParameters[i].getParameterValue()));
				}
				else if (processorParameters[i].getValue().equals("SaveOutputToDir"))
				{
					params.setDirOutputMsg(processorParameters[i].getParameterValue());
				}
				else if (processorParameters[i].getValue().equals("SaveErrorsToDir"))
				{
					params.setDirErrorMsg(processorParameters[i].getParameterValue());
				}
				else
				{
					// Some other mandatory parameter here
				}
			}
		}
		log.info("Parameters: " + params);

		if (Check.isEmpty(params.getTopicName()))
		{
			throw new AdempiereException("Missing " + X_EXP_ProcessorParameter.Table_Name + " with key 'topicName'!");
		}
		if (Check.isEmpty(params.getProtocol()))
		{
			throw new AdempiereException("Missing " + X_EXP_ProcessorParameter.Table_Name + " with key 'protocol'!");
		}
		if (Check.isEmpty(params.getClientID()))
		{
			throw new AdempiereException("Missing " + X_EXP_ProcessorParameter.Table_Name + " with key 'clientID'!");
		}
		if (params.getTimeToLive() <= 0)
		{
			throw new AdempiereException("Missing " + X_EXP_ProcessorParameter.Table_Name + " with key 'timeToLive'!");
		}

		return params;
	}

	private ConnectionFactory getConnectionFactory(TopicParameters params)
	{
		String brokerURL = params.getProtocol() + "://" + params.getHost() + ":" + params.getPort();
		return getConnectionFactory(brokerURL);
	}

	// FIXME: commented @Cached out because is no longer applied anyways (not a service)
	//@Cached
	private static ConnectionFactory getConnectionFactory(String brokerURL)
	{
		// library commons-pool is required by PooledConnectionFactory
		assert org.apache.commons.pool.ObjectPoolFactory.class != null;

		ConnectionFactory connectionFactory = new org.apache.activemq.pool.PooledConnectionFactory(brokerURL);

		return connectionFactory;
	}

	protected void sendJMSMessage(TopicParameters params, Document document) throws JMSException
	{
		final String msg = XMLHelper.createStringFromDOMNode(document);

		final ConnectionFactory connectionFactory = getConnectionFactory(params);
		Connection connection = null;
		Session session = null;
		try
		{
			// Create a Connection
			if (params.getAccount() != null && params.getPassword() != null)
			{
				connection = connectionFactory.createConnection(params.getAccount(), params.getPassword());
			}
			else
			{
				connection = connectionFactory.createConnection();
			}

			// connection.setClientID( clientID ); Commented by Victor as he had issue!
			connection.start();

			// Create a Session
			// TODO - Trifon could be EXP_ProcessorParameter
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Destination destination = session.createTopic(params.getTopicName());

			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(destination);
			producer.setTimeToLive(params.getTimeToLive());
			if (params.isDeliveryModePersistent())
			{
				producer.setDeliveryMode(DeliveryMode.PERSISTENT); // EXP_ProcessorParameter
			}
			else
			{
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT); // EXP_ProcessorParameter
			}

			// How to send to multiple destinations.
			// MessageProducer producer = session.createProducer(null);
			// producer.send(someDestination, message);
			// producer.send(anotherDestination, message);

			// Create a message
			TextMessage message = session.createTextMessage(msg);

			// Tell the producer to send the message
			try
			{
				producer.send(message);
				session.commit();
				log.info("JMS Message sent!");
			}
			catch (JMSException ex)
			{
				session.rollback();
				log.info("JMS Can't send the message!");
				throw ex;
			}

		}
		finally
		{
			// Clean up
			if (session != null)
			{
				try
				{
					session.close();
				}
				catch (JMSException ex)
				{ /* ignored */
				}
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (JMSException ex)
				{ /* ignored */
				}
			}
		}
	}

		private void sendToTempFile(Document document, String prefix)
	{
		File dir = new File(System.getProperty("java.io.tmpdir") + "/" + getClass().getCanonicalName());
		sendToFile(dir, document, prefix);
	}
	
	private void sendToFile(File dir, Document document, String prefix)
	{
		dir.mkdirs();
		
		final SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss_SSS");
		
		File file = new File(dir, prefix + "_" + df.format(new Date()) + ".xml");

		FileWriter writer = null;
		try
		{
			writer = new FileWriter(file);
			XMLHelper.writeDocument(writer, document);
		}
		catch (IOException e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			try
			{
				writer.close();
			}
			catch (IOException e)
			{
			}
			writer = null;
		}
		log.info("Created " + file);
	}
}
