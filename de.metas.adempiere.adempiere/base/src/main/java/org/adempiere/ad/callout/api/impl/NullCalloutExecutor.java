package org.adempiere.ad.callout.api.impl;

import java.util.function.Function;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.exceptions.CalloutException;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Immutable
public final class NullCalloutExecutor implements ICalloutExecutor
{
	public static final transient NullCalloutExecutor instance = new NullCalloutExecutor();

	private NullCalloutExecutor()
	{
		super();
	}

	@Override
	public void execute(final ICalloutField field) throws CalloutException
	{
		// nothing
	}

	@Override
	public void executeAll(final Function<String, ICalloutField> calloutFieldsSupplier)
	{
		// nothing
	}

	@Override
	public boolean hasCallouts(final ICalloutField field)
	{
		return false;
	}

	@Override
	public int getActiveCalloutInstancesCount()
	{
		return 0;
	}

	@Override
	public ICalloutExecutor newInstanceSharingMasterData()
	{
		return this;
	}

}
