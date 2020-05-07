package de.metas.adempiere.form.terminal;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * An {@link ITerminalLoginDialog} which logs in directly given user.
 *
 * @author tsa
 *
 */
public class DirectTerminalLoginDialog implements ITerminalLoginDialog
{

	private int userId = -1;

	private boolean disposed = false;

	@Override
	public void setAD_User_ID(int userId)
	{
		this.userId = userId;
	}

	@Override
	public int getAD_User_ID()
	{
		return userId;
	}

	@Override
	public boolean isExit()
	{
		return false;
	}

	@Override
	public boolean isLogged()
	{
		return true;
	}

	@Override
	public void activate()
	{
		// do nothing
	}

	/**
	 * Does nothing besides setting the internal disposed flag.
	 */
	@Override
	public void dispose()
	{
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

	@Override
	public String toString()
	{
		return "DirectTerminalLoginDialog [userId=" + userId + ", disposed=" + disposed + "]";
	}
}
