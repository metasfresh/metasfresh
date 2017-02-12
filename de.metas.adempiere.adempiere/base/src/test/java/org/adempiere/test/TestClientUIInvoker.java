package org.adempiere.test;

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


import de.metas.adempiere.form.AbstractClientUIInvoker;
import de.metas.adempiere.form.IClientUIInstance;
import de.metas.adempiere.form.IClientUIInvoker;

public class TestClientUIInvoker extends AbstractClientUIInvoker
{

	public TestClientUIInvoker(IClientUIInstance clientUI)
	{
		super(clientUI);
	}

	@Override
	protected Runnable asInvokeLaterRunnable(Runnable runnable)
	{
		return runnable;
	}

	@Override
	protected Runnable asLongOperationRunnable(Runnable runnable)
	{
		return runnable;
	}
	
	@Override
	protected Runnable asShowGlassPaneRunnable(Runnable runnable)
	{
		return runnable;
	}

	@Override
	public IClientUIInvoker setParentComponent(Object parentComponent)
	{
		return this;
	}

	@Override
	public IClientUIInvoker setParentComponentByWindowNo(int windowNo)
	{
		return this;
	}

}
