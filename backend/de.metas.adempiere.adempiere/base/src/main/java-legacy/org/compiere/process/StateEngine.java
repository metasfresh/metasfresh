package org.compiere.process;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/**
 * Process State Engine.
 * Based on OMG Workflow State
 *
 * @author Jorg Janke
 */
@EqualsAndHashCode
public final class StateEngine
{
	public static StateEngine ofCode(@NonNull final String code)
	{
		return new StateEngine(code);
	}

	//	Same as AD_WF_Process.WFSTATE

	/**
	 * Open - Not Started
	 */
	public static final String STATE_NotStarted = "ON";
	/**
	 * Open - Running
	 */
	public static final String STATE_Running = "OR";
	/**
	 * Open - Suspended
	 */
	public static final String STATE_Suspended = "OS";
	/**
	 * Closed - Completed - normal exit
	 */
	public static final String STATE_Completed = "CC";
	/**
	 * Closed - Aborted - Environment/Setup Error
	 */
	public static final String STATE_Aborted = "CA";
	/**
	 * Closed - Teminated - Execution Error
	 */
	public static final String STATE_Terminated = "CT";

	public static final String ACTION_Suspend = "Suspend";
	public static final String ACTION_Start = "Start";
	public static final String ACTION_Resume = "Resume";
	public static final String ACTION_Complete = "Complete";
	public static final String ACTION_Abort = "Abort";
	public static final String ACTION_Terminate = "Terminate";

	@Getter
	private final String code;

	private StateEngine(@NonNull final String code)
	{
		this.code = code;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getStateInfo();
	}

	/**
	 * @return clear text State Info.
	 */
	public String getStateInfo()
	{
		final String stateCode = getCode();
		if (STATE_Running.equals(stateCode))
			return "Running";
		else if (STATE_NotStarted.equals(stateCode))
			return "Not Started";
		else if (STATE_Suspended.equals(stateCode))
			return "Suspended";
		else if (STATE_Completed.equals(stateCode))
			return "Completed";
		else if (STATE_Aborted.equals(stateCode))
			return "Aborted";
		else if (STATE_Terminated.equals(stateCode))
			return "Terminated";
		else
			return stateCode;
	}    //	getStateInfo

	/**
	 * State is Open
	 *
	 * @return true if open (running, not started, suspended)
	 */
	public boolean isOpen()
	{
		return STATE_Running.equals(code)
				|| STATE_NotStarted.equals(code)
				|| STATE_Suspended.equals(code);
	}    //	isOpen

	/**
	 * State is Not Running
	 *
	 * @return true if not running (not started, suspended)
	 */
	public boolean isNotRunning()
	{
		return STATE_NotStarted.equals(code)
				|| STATE_Suspended.equals(code);
	}    //	isNotRunning

	/**
	 * State is Closed
	 *
	 * @return true if closed (completed, aborted, terminated)
	 */
	public boolean isClosed()
	{
		return STATE_Completed.equals(code)
				|| STATE_Aborted.equals(code)
				|| STATE_Terminated.equals(code);
	}    //	isClosed

	/**
	 * State is Not Started
	 *
	 * @return true if Not Started
	 */
	public boolean isNotStarted()
	{
		return STATE_NotStarted.equals(code);
	}    //	isNotStarted

	/**
	 * State is Running
	 *
	 * @return true if Running
	 */
	public boolean isRunning()
	{
		return STATE_Running.equals(code);
	}    //	isRunning

	/**
	 * State is Suspended
	 *
	 * @return true if Suspended
	 */
	public boolean isSuspended()
	{
		return STATE_Suspended.equals(code);
	}    //	isSuspended

	/**
	 * State is Completed
	 *
	 * @return true if Completed
	 */
	public boolean isCompleted()
	{
		return STATE_Completed.equals(code);
	}    //	isCompleted

	/**
	 * @return true if state is Aborted (Environment/Setup issue)
	 */
	public boolean isAborted()
	{
		return STATE_Aborted.equals(code);
	}

	/**
	 * @return true if state is Terminated (Execution issue)
	 */
	public boolean isTerminated()
	{
		return STATE_Terminated.equals(code);
	}

	/**
	 * Get New State Options based on current State
	 */
	private String[] getNewStateOptions()
	{
		if (isNotStarted())
			return new String[] { STATE_Running, STATE_Aborted, STATE_Terminated };
		if (isRunning())
			return new String[] { STATE_Suspended, STATE_Completed, STATE_Aborted, STATE_Terminated };
		if (isSuspended())
			return new String[] { STATE_Running, STATE_Aborted, STATE_Terminated };
		//
		return new String[] {};
	}

	/**
	 * Is the new State valid based on current state
	 *
	 * @param newState new state
	 * @return true valid new state
	 */
	public boolean isValidNewState(final String newState)
	{
		final String[] options = getNewStateOptions();
		for (final String option : options)
		{
			if (option.equals(newState))
				return true;
		}
		return false;
	}    //	isValidNewState

	/**
	 * @return valid actions based on current State
	 */
	private String[] getActionOptions()
	{
		if (isNotStarted())
			return new String[] { ACTION_Start, ACTION_Abort, ACTION_Terminate };
		if (isRunning())
			return new String[] { ACTION_Suspend, ACTION_Complete, ACTION_Abort, ACTION_Terminate };
		if (isSuspended())
			return new String[] { ACTION_Resume, ACTION_Abort, ACTION_Terminate };
		//
		return new String[] {};
	}    //	getActionOptions

	/**
	 * @return true if the action is valid based on current state
	 */
	public boolean isValidAction(final String action)
	{
		final String[] options = getActionOptions();
		for (final String option : options)
		{
			if (option.equals(action))
			{
				return true;
			}
		}

		return false;
	}
}
