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
package org.compiere.process;

import org.compiere.util.CLogger;


/**
 *	Process State Engine.
 *	Based on OMG Workflow State
 *	
 *  @author Jorg Janke
 *  @version $Id: StateEngine.java,v 1.3 2006/07/30 00:54:44 jjanke Exp $
 */
public class StateEngine
{
	/**
	 * 	Default Constructor (not started)
	 */
	public StateEngine ()
	{
		this (STATE_NotStarted);
		log = CLogger.getCLogger(getClass());
	}	//	State
	
	/**
	 * 	Initialized Constructor
	 * 	@param startState start state
	 */
	public StateEngine (String startState)
	{
		if (startState != null)
			m_state = startState;
	}	//	State
	
	//	Same as AD_WF_Process.WFSTATE
	
	/** Open - Not Started 	*/
	public static final String	STATE_NotStarted = "ON";
	/** Open - Running		*/
	public static final String	STATE_Running 	= "OR";
	/** Open - Suspended	*/
	public static final String	STATE_Suspended = "OS";
	/** Closed - Completed - normal exit	*/
	public static final String	STATE_Completed = "CC";
	/** Closed - Aborted - Environment/Setup Error	*/
	public static final String	STATE_Aborted = "CA";
	/** Closed - Teminated - Execution Error		*/
	public static final String	STATE_Terminated = "CT";

	/** Suspend			*/
	public static final String	ACTION_Suspend = "Suspend";
	/** Start			*/
	public static final String	ACTION_Start = "Start";
	/** Resume			*/
	public static final String	ACTION_Resume = "Resume";
	/** Complete		*/
	public static final String	ACTION_Complete = "Complete";
	/** Abort			*/
	public static final String	ACTION_Abort = "Abort";
	/** Terminate		*/
	public static final String	ACTION_Terminate = "Terminate";
	
	/**	Internal State				*/
	private String	m_state = STATE_NotStarted;

	/** If true throw exceptions	*/
	private boolean	m_throwException	= false;
	
	/**	Logger			*/
	protected CLogger	log = null;
	
	/**
	 * 	Are Exception Thrown
	 *	@return trie if exceptions thrown
	 */
	public boolean isThrowException()
	{
		return m_throwException;
	}	//	isThrowException
	
	/**
	 * 	Set if Exceptions are Thrown
	 *	 * @param throwException boolean
	 */
	public void setThrowException(boolean throwException)
	{
		m_throwException = throwException;
	}	//	setThrowException
	
	
	/**
	 * 	Get State
	 *	@return state
	 */
	public String getState()
	{
		return m_state;
	}	//	getState

	/**
	 * 	Get clear text State Info.
	 *	@return state info
	 */
	public String getStateInfo()
	{
		String state = getState();	//	is overwritten to update
		/**
		int AD_Reference_ID = 305;
		MRefList.getList(AD_Reference_ID, false);
		**/
		if (STATE_Running.equals(state))
			return "Running";
		else if (STATE_NotStarted.equals(state))
			return "Not Started";
		else if (STATE_Suspended.equals(state))
			return "Suspended";
		else if (STATE_Completed.equals(state))
			return "Completed";
		else if (STATE_Aborted.equals(state))
			return "Aborted";
		else if (STATE_Terminated.equals(state))
			return "Terminated";
		return state;
	}	//	getStateInfo

	/**
	 * 	State is Open
	 *	@return true if open (running, not started, suspended)
	 */
	public boolean isOpen()
	{
		return STATE_Running.equals(m_state)
			|| STATE_NotStarted.equals(m_state)
			|| STATE_Suspended.equals(m_state);
	}	//	isOpen
	
	/**
	 * 	State is Not Running
	 *	@return true if not running (not started, suspended)
	 */
	public boolean isNotRunning()
	{
		return STATE_NotStarted.equals(m_state)
			|| STATE_Suspended.equals(m_state);
	}	//	isNotRunning
	
	/**
	 * 	State is Closed
	 *	@return true if closed (completed, aborted, terminated)
	 */
	public boolean isClosed()
	{
		return STATE_Completed.equals(m_state)
			|| STATE_Aborted.equals(m_state)
			|| STATE_Terminated.equals(m_state);
	}	//	isClosed
	
	/**
	 * 	State is Not Started
	 *	@return true if Not Started
	 */
	public boolean isNotStarted()
	{
		return STATE_NotStarted.equals(m_state);
	}	//	isNotStarted
	
	/**
	 * 	State is Running
	 *	@return true if Running
	 */
	public boolean isRunning()
	{
		return STATE_Running.equals(m_state);
	}	//	isRunning
	
	/**
	 * 	State is Suspended
	 *	@return true if Suspended
	 */
	public boolean isSuspended()
	{
		return STATE_Suspended.equals(m_state);
	}	//	isSuspended
	
	/**
	 * 	State is Completed
	 *	@return true if Completed
	 */
	public boolean isCompleted()
	{
		return STATE_Completed.equals(m_state);
	}	//	isCompleted
	
	/**
	 * 	State is Aborted (Environment/Setup issue)
	 *	@return true if Aborted
	 */
	public boolean isAborted()
	{
		return STATE_Aborted.equals(m_state);
	}	//	isAborted

	/**
	 * 	State is Terminated (Execution issue)
	 *	@return true if Terminated
	 */
	public boolean isTerminated()
	{
		return STATE_Terminated.equals(m_state);
	}	//	isTerminated

	


	/**************************************************************************
	 * 	Start: not started -> running
	 *	@return true if set to running
	 */
	public boolean start()
	{
		if (log == null)
			log = CLogger.getCLogger(getClass());
		if (isNotStarted())
		{
			m_state = STATE_Running;
			log.info("starting ...");
			return true;
		}
		String msg = "start failed: Not Not Started (" + getState() + ")";
		if (m_throwException)
			throw new IllegalStateException (msg);
		log.warning(msg);
		return false;
	}	//	start
	
	/**
	 * 	Resume: suspended -> running
	 *	@return true if set to sunning
	 */
	public boolean resume()	//	raises CannotResume, NotRunning, NotSuspended
	{
		if (log == null)
			log = CLogger.getCLogger(getClass());
		if (isSuspended())
		{
			m_state = STATE_Running;
			log.info("resuming ...");
			return true;
		}
		String msg = "resume failed: Not Suspended (" + getState() + ")";
		if (m_throwException)
			throw new IllegalStateException (msg);
		log.warning(msg);
		return false;
	}	//	resume

	/**
	 * 	Suspend: running -> suspended
	 *	@return true if suspended
	 */
	public boolean suspend()	//	raises CannotSuspend, NotRunning, AlreadySuspended
	{
		if (log == null)
			log = CLogger.getCLogger(getClass());
		if (isRunning())
		{
			m_state = STATE_Suspended;
			log.info("suspending ...");
			return true;
		}
		String msg = "suspend failed: Not Running (" + getState() + ")";
		if (m_throwException)
			throw new IllegalStateException (msg);
		log.warning(msg);
		return false;
	}	//	suspend

	/**
	 * 	Complete: running -> completed
	 *	@return true if set to completed
	 */
	public boolean complete()
	{
		if (log == null)
			log = CLogger.getCLogger(getClass());
		if (isRunning())
		{
			m_state = STATE_Completed;
			log.info("completing ...");
			return true;
		}
		String msg = "complete failed: Not Running (" + getState() + ")";
		if (m_throwException)
			throw new IllegalStateException (msg);
		log.warning(msg);
		return false;
	}	//	complete
	
	/**
	 * 	Abort: open -> aborted
	 *	@return true if set to aborted
	 */
	public boolean abort()	//	raises CannotStop, NotRunning
	{
		if (log == null)
			log = CLogger.getCLogger(getClass());
		if (isOpen())
		{
			m_state = STATE_Aborted;
			log.info("aborting ...");
			return true;
		}
		String msg = "abort failed: Not Open (" + getState() + ")";
		if (m_throwException)
			throw new IllegalStateException (msg);
		log.warning(msg);
		return false;
	}	//	abort
	
	/**
	 * 	Terminate: open -> terminated
	 *	@return true if set to terminated
	 */
	public boolean terminate()	//	raises CannotStop, NotRunning
	{
		if (log == null)
			log = CLogger.getCLogger(getClass());
		if (isOpen())
		{
			m_state = STATE_Terminated;
			log.info("terminating ...");
			return true;
		}
		String msg = "terminate failed: Not Open (" + getState() + ")";
		if (m_throwException)
			throw new IllegalStateException (msg);
		log.warning(msg);
		return false;
	}	//	terminate
	
	
	/**
	 * 	Get New State Options based on current State
	 *	@return array of new states
	 */
	public String[] getNewStateOptions()
	{
		if (isNotStarted())
			return new String[] {STATE_Running, STATE_Aborted, STATE_Terminated};
		if (isRunning())
			return new String[] {STATE_Suspended, STATE_Completed, STATE_Aborted, STATE_Terminated};
		if (isSuspended())
			return new String[] {STATE_Running, STATE_Aborted, STATE_Terminated};
		//
		return new String[] {};
	}	//	getNewStateOptions

	/**
	 * 	Is the new State valid based on current state
	 *	@param newState new state
	 *	@return true valid new state
	 */
	public boolean isValidNewState (String newState)
	{
		String[] options = getNewStateOptions();
		for (int i = 0; i < options.length; i++)
		{
			if (options[i].equals(newState))
				return true;
		}
	 	return false;
	}	//	isValidNewState
	
	
	/**
	 * 	Set State to new State
	 *	@param newState new state
	 *	@return true if set to new state
	 */
	public boolean setState (String newState)	//	raises InvalidState, TransitionNotAllowed
	{
		if (STATE_Running.equals(newState))
		{
			if (isNotStarted())
				return start();
			else
				return resume();
		}
		else if (STATE_Suspended.equals(newState))
			return suspend();
		else if (STATE_Completed.equals(newState))
			return complete();
		else if (STATE_Aborted.equals(newState))
			return abort();
		else if (STATE_Terminated.equals(newState))
			return terminate();
		return false;
	}	//	setState

	/**
	 * 	Get Action Options based on current State
	 *	@return array of actions
	 */
	public String[] getActionOptions()
	{
		if (isNotStarted())
			return new String[] {ACTION_Start, ACTION_Abort, ACTION_Terminate};
		if (isRunning())
			return new String[] {ACTION_Suspend, ACTION_Complete, ACTION_Abort, ACTION_Terminate};
		if (isSuspended())
			return new String[] {ACTION_Resume, ACTION_Abort, ACTION_Terminate};
		//
		return new String[] {};
	}	//	getActionOptions

	/**
	 * 	Is The Action Valid based on current state
	 *	@param action action
	 *	@return true if valid
	 */
	public boolean isValidAction (String action)
	{
		String[] options = getActionOptions();
		for (int i = 0; i < options.length; i++)
		{
			if (options[i].equals(action))
				return true;
		}
		return false;
	}	//	isValidAction
	
	/**
	 * 	Process
	 *	@param action action
	 *	@return true if set to new state
	 */
	public boolean process (String action)	//	raises InvalidState, TransitionNotAllowed
	{
		if (ACTION_Start.equals(action))
			return start();
		else if (ACTION_Complete.equals(action))
			return complete();
		else if (ACTION_Suspend.equals(action))
			return suspend();
		else if (ACTION_Resume.equals(action))
			return resume();
		else if (ACTION_Abort.equals(action))
			return abort();
		else if (ACTION_Terminate.equals(action))
			return terminate();
		return false;
	}	//	process

	/**
	 * 	Get New State If Action performed
	 *	@param action action
	 *	@return potential new state
	 */
	public String getNewStateIfAction (String action)
	{
		if (isValidAction(action))
		{
			if (ACTION_Start.equals(action))
				return STATE_Running;
			else if (ACTION_Complete.equals(action))
				return STATE_Completed;
			else if (ACTION_Suspend.equals(action))
				return STATE_Suspended;
			else if (ACTION_Resume.equals(action))
				return STATE_Running;
			else if (ACTION_Abort.equals(action))
				return STATE_Aborted;
			else if (ACTION_Terminate.equals(action))
				return STATE_Terminated;
		}
		//	Unchanged
		return getState();
	}	//	getNewStateIfAction
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		return getStateInfo();
	}	//	toString
	
}	//	State
