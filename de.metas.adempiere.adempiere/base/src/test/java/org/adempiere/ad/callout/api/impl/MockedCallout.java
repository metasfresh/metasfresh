package org.adempiere.ad.callout.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.ad.callout.api.ICallout;
import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.junit.Ignore;

@Ignore
public class MockedCallout implements ICallout
{
	private boolean called = false;
	private Exception onExecuteFailException = null;

	@Override
	public void onFieldChanged(ICalloutExecutor executor, ICalloutField field) throws Exception
	{
		this.called = true;
		if (onExecuteFailException != null)
		{
			throw onExecuteFailException;
		}
	}

	public boolean isCalled()
	{
		return called;
	}

	public void setCalled(boolean called)
	{
		this.called = called;
	}

	public Exception getOnExecuteFailException()
	{
		return onExecuteFailException;
	}

	public void setOnExecuteFailException(Exception onExecuteFailException)
	{
		this.onExecuteFailException = onExecuteFailException;
	}
}
