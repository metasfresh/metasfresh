/**********************************************************************
* This file is part of Adempiere ERP Bazaar                           *
* http://www.adempiere.org                                            *
*                                                                     *
* Copyright (C) Trifon Trifonov.                                      *
* Copyright (C) Contributors                                          *
*                                                                     *
* This program is free software; you can redistribute it and/or       *
* modify it under the terms of the GNU General Public License         *
* as published by the Free Software Foundation; either version 2      *
* of the License, or (at your option) any later version.              *
*                                                                     *
* This program is distributed in the hope that it will be useful,     *
* but WITHOUT ANY WARRANTY; without even the implied warranty of      *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
* GNU General Public License for more details.                        *
*                                                                     *
* You should have received a copy of the GNU General Public License   *
* along with this program; if not, write to the Free Software         *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
* MA 02110-1301, USA.                                                 *
*                                                                     *
* Contributors:                                                       *
* - Trifon Trifonov (trifonnt@users.sourceforge.net)
* - Antonio Cañaveral, e-Evolution                  
*                                                                     *
* Sponsors:                                                           *
* - E-evolution (http://www.e-evolution.com)                          *
***********************************************************************/
package org.adempiere.process.rpl.exp;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.process.rpl.IExportProcessor;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.compiere.model.I_EXP_ProcessorParameter;
import org.compiere.model.MEXPProcessor;
import org.compiere.model.X_EXP_ProcessorParameter;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Trx;
import org.w3c.dom.Document;

/**
 * @author Trifon N. Trifonov
 * @author Antonio Cañaveral, e-Evolution
 * 				<li>[ 2195051 ] Implementing Message Transaction
 * 				<li>http://sourceforge.net/tracker/index.php?func=detail&aid=2195051&group_id=176962&atid=879335
 * @author Michael Judd
 *				<li>[ 2862500 ]   	 TopicExporter TransformerFactory setAttribute error
 *				<li>https://sourceforge.net/tracker/?func=detail&aid=2862500&group_id=176962&atid=879332
 */
public class TopicExportProcessor implements IExportProcessor {

	/**	Logger	*/
	protected Logger	log = LogManager.getLogger(getClass());
	
	/**
	 * 
	 */
	public void process(Properties ctx, MEXPProcessor expProcessor, Document document, Trx trx) 
			throws Exception 
	{
		String host 	      = expProcessor.getHost();
		int port 		      = expProcessor.getPort();
		String account 	      = expProcessor.getAccount();
		String password       = expProcessor.getPasswordInfo();
		String protocol       = null;
        String topicName      = "";
        String clientID       = null;
        String timeToLiveStr  = null;
        int timeToLive        = 10000;
        boolean isDeliveryModePersistent = true;
        
        // Read all processor parameters and set them!        
        I_EXP_ProcessorParameter[] processorParameters = expProcessor.getEXP_ProcessorParameters();
        if (processorParameters != null && processorParameters.length > 0) {
        	for (int i = 0; i < processorParameters.length; i++) {
        		log.info("ProcesParameter          Value = " + processorParameters[i].getValue());
        		log.info("ProcesParameter ParameterValue = " + processorParameters[i].getParameterValue());
        		if (processorParameters[i].getValue().equals("topicName")) {
        			topicName = processorParameters[i].getParameterValue();
        		} else if (processorParameters[i].getValue().equals("protocol")) {
        			protocol = processorParameters[i].getParameterValue();
        		} else if (processorParameters[i].getValue().equals("clientID")) {
        			clientID = processorParameters[i].getParameterValue();
        		} else if (processorParameters[i].getValue().equals("timeToLive")) {
        			timeToLiveStr = processorParameters[i].getParameterValue();
        			timeToLive = Integer.parseInt( timeToLiveStr );
        		} else if (processorParameters[i].getValue().equals("isDeliveryModePersistent")) {
        			isDeliveryModePersistent = Boolean.parseBoolean( processorParameters[i].getParameterValue() );
        		} else {
        			// Some other mandatory parameter here
        		}
        	}
        }
        
        if (topicName == null || topicName.length() == 0) {
        	throw new Exception("Missing "+X_EXP_ProcessorParameter.Table_Name+" with key 'topicName'!");
        }
        if (protocol == null || protocol.length() == 0) {
        	throw new Exception("Missing "+X_EXP_ProcessorParameter.Table_Name+" with key 'protocol'!");
        }
        if (clientID == null || clientID.length() == 0) {
        	throw new Exception("Missing "+X_EXP_ProcessorParameter.Table_Name+" with key 'clientID'!");
        }
        if (timeToLiveStr == null || timeToLiveStr.length() == 0) {
        	throw new Exception("Missing "+X_EXP_ProcessorParameter.Table_Name+" with key 'timeToLive'!");
        }
        
		// Construct Transformer Factory and Transformer
        TransformerFactory tranFactory = TransformerFactory.newInstance();
        String jVersion = System.getProperty("java.version");
		if (jVersion.startsWith("1.5.0"))
			tranFactory.setAttribute("indent-number", Integer.valueOf(1));
        
        Transformer aTransformer = tranFactory.newTransformer();
        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Source src = new DOMSource( document );
		
        // =================================== Write to String
        Writer writer = new StringWriter();
        Result dest2 = new StreamResult(writer);
        aTransformer.transform(src, dest2);
        
        sendJMSMessage(host, port, writer.toString(), protocol, topicName, clientID, account, password, timeToLive, isDeliveryModePersistent);
		
	}

	private void sendJMSMessage(String host, int port, String msg, String protocol, String topicName
			, String clientID, String userName, String password, int timeToLive
			, boolean isDeliveryModePersistent) throws JMSException 
	{
		// Create a ConnectionFactory
		// network protocol (tcp, ...) set as EXP_ProcessorParameter
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(protocol + "://" + host + ":" + port);
		
		Connection connection = null;
		Session session = null;
		try {
			// Create a Connection
			if (userName != null && password != null) {
				connection = connectionFactory.createConnection(userName, password);
			} else {
				connection = connectionFactory.createConnection();
			}
			
			// connection.setClientID( clientID ); Commented by Victor as he had issue!
			connection.start();
			
			// Create a Session
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE); //TODO - Trifon could be EXP_ProcessorParameter

			// Create the destination (Topic or Queue)
			Destination destination = session.createTopic(topicName);
			
			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer( destination );
			producer.setTimeToLive( timeToLive ); // EXP_ProcessorParameter
			if ( isDeliveryModePersistent ) {
				producer.setDeliveryMode( DeliveryMode.PERSISTENT ); // EXP_ProcessorParameter	
			} else {
				producer.setDeliveryMode( DeliveryMode.NON_PERSISTENT ); // EXP_ProcessorParameter
			}
			
			// How to send to multiple destinations.
			//MessageProducer producer = session.createProducer(null);
			//producer.send(someDestination, message);
			//producer.send(anotherDestination, message);
			
			// Create a message
			TextMessage message = session.createTextMessage( msg );
			
			// Tell the producer to send the message
			try
			{
				producer.send(message);
				session.commit();
				log.info("JMS Message sent!");
			}catch(JMSException ex)
			{
				session.rollback();
				log.info("JMS Can't send the message!");
				throw ex;
			}
			
		} finally {
			// Clean up
			if (session != null) { 
				try { session.close(); } catch (JMSException ex) { /* ignored */ }
			}
			if (connection != null) {
				try { connection.close(); } catch (JMSException ex) { /* ignored */ }
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
	}
}
