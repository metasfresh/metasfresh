/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.workflow;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_WF_Activity;

/**
 * Process State Engine.
 * Based on OMG Workflow State
 *
 * @author Jorg Janke
 */
public enum WFState implements ReferenceListAwareEnum
{
	/**
	 * Open - Not Started
	 */
	NotStarted(X_AD_WF_Activity.WFSTATE_NotStarted),
	/**
	 * Open - Running
	 */
	Running(X_AD_WF_Activity.WFSTATE_Running),
	/**
	 * Open - Suspended
	 */
	Suspended(X_AD_WF_Activity.WFSTATE_Suspended),
	/**
	 * Closed - Completed - normal exit
	 */
	Completed(X_AD_WF_Activity.WFSTATE_Completed),
	/**
	 * Closed - Aborted - Environment/Setup Error
	 */
	Aborted(X_AD_WF_Activity.WFSTATE_Aborted),
	/**
	 * Closed - Teminated - Execution Error
	 */
	Terminated(X_AD_WF_Activity.WFSTATE_Terminated);

	private static final ReferenceListAwareEnums.ValuesIndex<WFState> typesByCode = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	WFState(@NonNull final String code)
	{
		this.code = code;
	}

	public static WFState ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}

	/**
	 * @return true if open (running, not started, suspended)
	 */
	public boolean isOpen()
	{
		return Running.equals(this)
				|| NotStarted.equals(this)
				|| Suspended.equals(this);
	}

	/**
	 * State is Not Running
	 *
	 * @return true if not running (not started, suspended)
	 */
	public boolean isNotRunning()
	{
		return NotStarted.equals(this)
				|| Suspended.equals(this);
	}    //	isNotRunning

	/**
	 * State is Closed
	 *
	 * @return true if closed (completed, aborted, terminated)
	 */
	public boolean isClosed()
	{
		return Completed.equals(this)
				|| Aborted.equals(this)
				|| Terminated.equals(this);
	}

	public boolean isNotStarted()
	{
		return NotStarted.equals(this);
	}    //	isNotStarted

	public boolean isRunning()
	{
		return Running.equals(this);
	}

	public boolean isSuspended()
	{
		return Suspended.equals(this);
	}

	public boolean isCompleted() { return Completed.equals(this); }

	public boolean isError() { return isAborted() || isTerminated(); }

	/**
	 * @return true if state is Aborted (Environment/Setup issue)
	 */
	public boolean isAborted()
	{
		return Aborted.equals(this);
	}

	/**
	 * @return true if state is Terminated (Execution issue)
	 */
	public boolean isTerminated()
	{
		return Terminated.equals(this);
	}

	/**
	 * Get New State Options based on current State
	 */
	private WFState[] getNewStateOptions()
	{
		if (isNotStarted())
			return new WFState[] { Running, Aborted, Terminated };
		else if (isRunning())
			return new WFState[] { Suspended, Completed, Aborted, Terminated };
		else if (isSuspended())
			return new WFState[] { Running, Aborted, Terminated };
		else
			return new WFState[] {};
	}

	/**
	 * Is the new State valid based on current state
	 *
	 * @param newState new state
	 * @return true valid new state
	 */
	public boolean isValidNewState(final WFState newState)
	{
		final WFState[] options = getNewStateOptions();
		for (final WFState option : options)
		{
			if (option.equals(newState))
				return true;
		}
		return false;
	}

	/**
	 * @return true if the action is valid based on current state
	 */
	public boolean isValidAction(final WFAction action)
	{
		final WFAction[] options = getActionOptions();
		for (final WFAction option : options)
		{
			if (option.equals(action))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * @return valid actions based on current State
	 */
	private WFAction[] getActionOptions()
	{
		if (isNotStarted())
			return new WFAction[] { WFAction.Start, WFAction.Abort, WFAction.Terminate };
		if (isRunning())
			return new WFAction[] { WFAction.Suspend, WFAction.Complete, WFAction.Abort, WFAction.Terminate };
		if (isSuspended())
			return new WFAction[] { WFAction.Resume, WFAction.Abort, WFAction.Terminate };
		else
			return new WFAction[] {};
	}
}
