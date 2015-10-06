/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.util;

import java.io.Serializable;

//import org.compiere.db.CConnection;

/**
 *  Message Queuing
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: Queue.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public final class Queue implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4858407108018676415L;
/*
	private static Connection	s_connection;
	private static AQSession	s_session;
	private static AQQueue		s_queueS;
	private static AQQueue		s_queueC;
	//
	private static String		s_queueName = "AQ_";
	private static String		s_toServer = "S";
	private static String		s_toClient = "C";
	private static String		s_table_extension = "_Tab";


	/**
	 *	Create AQ Session
	 *
	private static AQSession createSession()
	{
		log.config( "Queue.createSession");
		if (s_connection == null)	//	autoCommit
			//	get dedicated
			s_connection = DB.createConnection(true, Connection.TRANSACTION_READ_COMMITTED);

		AQSession aq_sess = null;
		try
		{
			Class.forName("oracle.AQ.AQOracleDriver");
		}
		catch (ClassNotFoundException e)
		{
			log.log(Level.SEVERE, "Queue.createSession (Driver)", e);
		}
		try
		{
			aq_sess = AQDriverManager.createAQSession(s_connection);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Queue.createSession", e);
		}

		return aq_sess;
	}	//	createSession

	/**
	 *	Get AQ Session
	 *
	private static AQSession getSession()
	{
		if (s_session == null)
			s_session = createSession();
		return s_session;
	}	//	getSession

	/**
	 *	Create Queue
	 *
	private static AQQueue createQueue(String name)
	{
		log.config( "Queue.createQueue - " + name);
		//
		AQSession session = getSession();
		if (session == null)
			return null;

		AQQueue queue = null;
		try
		{
			//	Payload type: raw	- Age Ordered
			AQQueueTableProperty t_property = new AQQueueTableProperty("RAW");
			t_property.setComment("Adempiere Client/Server Communication Table");
			t_property.setSortOrder("ENQ_TIME");
			t_property.setMultiConsumer(false);
		//	t_property.setCompatible("8.1");
			//	Create table in the Adempiere scheme
			String tabName = name + s_table_extension;
			AQQueueTable table = session.createQueueTable(CConnection.get().getDbUid(),
				tabName, t_property);
			log.fine( "Queue Table created - " + tabName);

			//	Create Queue property
			AQQueueProperty q_property = new AQQueueProperty();
			q_property.setComment("Adempiere Client/Server Communication Queue");
			q_property.setRetentionTime(AQQueueProperty.INFINITE);
			queue = session.createQueue(table, name, q_property);
			log.fine( "Queue created - " + name);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Queue.createQueue", e);
		}
		//
		return queue;
	}	//	createQueue

	/**
	 *	Get Queue "Test"
	 *
	protected static AQQueue getQueue(boolean toServer, boolean reset)
	{
		if (reset)
			close(false);
		//	Server Queue
		if (toServer && s_queueS == null)
		{
			AQSession session = getSession();
			try
			{
				s_queueS = session.getQueue(CConnection.get().getDbUid(),
					s_queueName+s_toServer);
			}
			catch (AQException e)
			{
				try
				{
					//	No Queue - So create it
					s_queueS = createQueue(s_queueName+s_toServer);
					s_queueS.start();
				}
				catch (Exception e2)
				{
					log.log(Level.SEVERE, "Queue.getQueue (create1)", e2);
				}
			}
		}
		//	Client Queue
		if (!toServer && s_queueC == null)
		{
			AQSession session = getSession();
			try
			{
				s_queueC = session.getQueue(CConnection.get().getDbUid(),
					s_queueName+s_toClient);
			}
			catch (AQException e)
			{
				try
				{
					//	No Queue - So create it
					s_queueC = createQueue(s_queueName+s_toClient);
					s_queueC.start();
				}
				catch (Exception e2)
				{
					log.log(Level.SEVERE, "Queue.getQueue (create2)", e2);
				}
			}
		}
		if (toServer)
			return s_queueS;
		return s_queueC;
	}	//	getQueue

	/**
	 *	Drop Queue
	 *
	private static void dropQueues()
	{
		log.config( "Queue.dropQueues");
		//
		String tabNameS = s_queueName + s_toServer + s_table_extension;
		String tabNameC = s_queueName + s_toClient + s_table_extension;
		try
		{
			AQQueueTable tableS = getSession().getQueueTable(CConnection.get().getDbUid(), tabNameS);
			AQQueueTable tableC = getSession().getQueueTable(CConnection.get().getDbUid(), tabNameC);
			tableS.drop(true);
			tableC.drop(true);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Queue.dropQueues - " + e.getMessage());
		}
	}	//	dropQueues

	/**
	 *	Close Queues & Session
	 *
	public static void close (boolean closeConnection)
	{
		s_queueC = null;
		s_queueS = null;
		s_session = null;
		if (closeConnection && s_connection != null)
		{
			try
			{
				s_connection.close();
			}
			catch (SQLException e)
			{
				log.log(Level.SEVERE, "Queue.close", e);
			}
			s_connection = null;
		}
	}	//	close


	/**
	 *	Send Message
	 *
	protected static boolean send (Serializable info, boolean toServer)
	{
		if (info == null)
			return false;

		//	Serialize info
		byte[] data = null;
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream (baos);
			oos.writeObject(info);
			oos.flush();
			oos.close();
			data = baos.toByteArray();
			baos.close();
		}
		catch (IOException ioe)
		{
			log.log(Level.SEVERE, "Queue.send IO - " + ioe.getMessage());
			return false;
		}

		try
		{
			//	create Message
			AQQueue queue = getQueue(toServer, false);
			AQMessage message = queue.createMessage();
			//	populate payload
			AQRawPayload rawPayload = message.getRawPayload();
			rawPayload.setStream(data, data.length);

			//	Standard enqueue Options
			AQEnqueueOption option = new AQEnqueueOption();

			//	Enqueue
			queue.enqueue(option, message);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Queue.send", e);
			return false;
		}
		log.config( "Queue.send " + info.getClass()
			+ (toServer ? " ToServer" : " ToClient")
			+ ", Size=" + data.length);
		return true;
	}	//	send

	/**
	 *	Receive message
	 *
	protected static Serializable receive (boolean fromServer)
	{
		//	Get Queue
		AQQueue queue = getQueue(!fromServer, false);

		//	Get Message
		AQMessage message = null;
		try
		{
			//	Set Dequeue Option
			AQDequeueOption option = new AQDequeueOption();
			option.setWaitTime(1);	//	one second wait
		//	option.setDequeueMode(AQDequeueOption.DEQUEUE_REMOVE);
			//
			message = queue.dequeue(option);
		}
		catch (AQOracleSQLException e)
		{
			if (e.getErrorCode() == 25228)	//	timeout
				return null;
			log.log(Level.SEVERE, "Queue.receive", e);
			return null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Queue.receive", e);
			return null;
		}

		Serializable info = deserialize (message);

		log.config( "Queue.receive " + info.getClass()
			+ (fromServer ? " FromServer" : " FromClient"));
		return info;
	}	//	receive

	/**
	 *	De-Serialize
	 *
	private static Serializable deserialize (AQMessage message)
	{
		//	Deserialize
		Serializable info = null;
		try
		{
			//	get Payload
			AQRawPayload raw_payload = message.getRawPayload();
			byte[] data = raw_payload.getBytes();
			//
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			ObjectInputStream ois = new ObjectInputStream (bais);
			info = (Serializable)ois.readObject();
			ois.close();
			bais.close();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Queue.deserialize", e);
			return null;
		}
		return info;
	}	//	deserialize

	/**
	 *	List message
	 *
	protected static ArrayList listMessages (boolean fromServer)
	{
		//	Get Queue
		AQQueue queue = getQueue(!fromServer, true);
		AQDequeueOption option = new AQDequeueOption();
		try
		{
			log.config( "Queue.listMessages - " + queue.getName());

			//	Set Dequeue Option
			option.setWaitTime(0);	//	no wait
			option.setDequeueMode(AQDequeueOption.DEQUEUE_BROWSE);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Queue.lsiMessages", e);
		}

		ArrayList list = new ArrayList();

		//	Get Messages
		AQMessage message = null;
		do
		{
			try
			{
				message = queue.dequeue(option);
				Serializable info = deserialize (message);
				list.add(info);
				log.config( "> " + info.toString());
			}
			catch (AQOracleSQLException e)
			{
				if (e.getErrorCode() != 25228)	//	timeout
					log.log(Level.SEVERE, "Queue.receive", e);
				message = null;
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, "Queue.receive", e);
			}
		} while (message != null);
		return list;
	}	//	listMessages




	public static boolean sendToServer(Serializable info)
	{
		return send (info, true);
	}

	public static boolean sendToClient(Serializable info)
	{
		return send (info, false);
	}

	public static Serializable receiveFromServer()
	{
		return receive (true);
	}

	public static Serializable receiveFromClient()
	{
		return receive (false);
	}

	/**************************************************************************
	 *	Main Test
	 *
	public static void main (String[] args)
	{
		Env.initTest(9, true);      //  run as Client

	//	dropQueues();
		Timestamp t = new Timestamp(System.currentTimeMillis());

		sendToServer ("This is a new test " + t.toString());
		sendToClient (new KeyNamePair (21, "Twenty-one " + t.toString()));
		sendToServer ("This is a second test " + t.toString());
		sendToClient (new KeyNamePair (22, "Twenty-two " + t.toString()));
		listMessages(true);
		listMessages(false);
		System.out.println(receiveFromClient());
		System.out.println(receiveFromServer());
		listMessages(true);
		listMessages(false);

		System.out.println("Fini");
		System.exit(0);
	//	AEnv.exit(0);
	}	//	Main
*/
}	//	Queue
