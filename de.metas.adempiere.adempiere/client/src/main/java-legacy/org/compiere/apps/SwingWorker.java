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
package org.compiere.apps;

import javax.swing.SwingUtilities;

/**
 *  SwingWorker (based on SwingWorker 3).
 *  To use the SwingWorker class, you create a subclass of it.
 *  In the subclass, you must implement the construct() method contains
 *  the code to perform your lengthy operation.
 *  You invoke start() on your SwingWorker object to start the thread,
 *  which then calls your construct() method.
 *  When you need the object returned by the construct() method,
 *  you call the SwingWorker's get() method.
 *  <pre>
 *      SwingWorker worker = new SwingWorker()
 *      {
 *          public Object construct()
 *          {
 *              return new expensiveOperation();
 *          }
 *      };
 *      worker.start();
 *      //  do something
 *      //  when you need the result:
 *      x = worker.get();   //  this blocks the UI !!
 *  </pre>
 */
public abstract class SwingWorker
{
	/**
	 * Start a thread that will call the <code>construct</code> method
	 * and then exit.
	 */
	public SwingWorker()
	{
		/** Finish Runnable		*/
		final Runnable doFinished = new Runnable()
		{
			public void run()
			{
				finished();
			}
		};

		/** Worker Runnable		*/
		Runnable doConstruct = new Runnable()
		{
			public void run()
			{
				try
				{
					setValue(construct());
				}
				finally
				{
					m_threadVar.clear();
				}
				SwingUtilities.invokeLater(doFinished);
			}
		};

		Thread t = new Thread (doConstruct);
		m_threadVar = new ThreadVar(t);
	}   //  SwingWorker

	/** Worker Thread			*/
	private ThreadVar   m_threadVar;
	/** Return value			*/
	private Object      m_value;  // see getValue(), setValue()

	/**
	 *  Compute the value to be returned by the <code>get</code> method.
	 *  @return value
	 */
	public abstract Object construct();

	/**
	 * Called on the event dispatching thread (not on the worker thread)
	 * after the <code>construct</code> method has returned.
	 */
	public void finished()
	{
	}	//	finished

	/**
	 * Get the value produced by the worker thread,
	 * or null if it hasn't been constructed yet.
	 * @return value of worker
	 */
	protected synchronized Object getValue()
	{
		return m_value;
	}   //  getValue

	/**
	 *  Set the value produced by worker thread
	 *  @param x worker value
	 */
	private synchronized void setValue(Object x)
	{
		m_value = x;
	}   //  setValue

	
	/*************************************************************************
	 *  Start the worker thread.
	 */
	public void start()
	{
		Thread t = m_threadVar.get();
		if (t != null)
			t.start();
	}   //  start

	/**
	 *  Return the value created by the <code>construct</code> method.
	 *  Returns null if either the constructing thread or the current
	 *  thread was interrupted before a value was produced.
	 *	(Blocks UI)
	 *  @return the value created by the <code>construct</code> method
	 */
	public Object get()
	{
		while (true)
		{
			Thread t = m_threadVar.get();
			if (t == null)
			{
				return getValue();
			}
			try
			{
				t.join();
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt(); // propagate
				return null;
			}
		}
	}   //  get

	/**
	 * A new method that interrupts the worker thread.  Call this method
	 * to force the worker to stop what it's doing.
	 */
	public void interrupt()
	{
		Thread t = m_threadVar.get();
		if (t != null)
		{
			t.interrupt();
		}
		m_threadVar.clear();
	}   //  interrupt

	/**
	 * 	Is worker Alive
	 * 	@return true if alive
	 */
	public boolean isAlive()
	{
		Thread t = m_threadVar.get();
		if (t == null)
			return false;
		return t.isAlive();
	}	//	isAlive

	
	/**************************************************************************
	 * Class to maintain reference to current worker thread
	 * under separate synchronization control.
	 */
	private static class ThreadVar
	{
		/**
		 * 	Constructor
		 *	@param t sync thread
		 */
		ThreadVar(Thread t)
		{
			thread = t;
		}
		/** The Sync Thread	*/
		private Thread thread;

		/**
		 * 	Get Sync Thread
		 *	@return thread
		 */
		synchronized Thread get()
		{
			return thread;
		}	//	get
		
		/**
		 * 	Clear Sync thread
		 */
		synchronized void clear()
		{
			thread = null;
		}	//	clear
	}   //  ThreadVar

}   //  SwingWorker
