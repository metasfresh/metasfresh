package de.metas.ui.web.window_old.model;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Throwables;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import de.metas.ui.web.window_old.shared.command.ViewCommandResult;

/*
 * #%L
 * metasfresh-webui-api
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

final class ModelCommandHelper
{
	public static ListenableFuture<ViewCommandResult> notSupported(final ModelCommand command, final PropertyValue propertyValue)
	{
		return Futures.immediateFailedCheckedFuture(new UnsupportedOperationException("Command " + command + " not supported by " + propertyValue));
	}

	public static ListenableFuture<ViewCommandResult> error(final String message, final ModelCommand command, final PropertyValue propertyValue)
	{
		return Futures.immediateFailedCheckedFuture(new RuntimeException("Error while executing " + command + " on " + propertyValue + ": " + message));
	}

	public static ListenableFuture<ViewCommandResult> result(final Object resultObj)
	{
		return Futures.immediateFuture(ViewCommandResult.of(resultObj));
	}
	
	public static ListenableFuture<ViewCommandResult> noResult()
	{
		return Futures.immediateFuture(ViewCommandResult.of(null));
	}

	public static ViewCommandResult extractResult(final ListenableFuture<ViewCommandResult> futureResult)
	{
		try
		{
			return futureResult.get(10, TimeUnit.SECONDS);
		}
		catch (Exception e)
		{
			throw Throwables.propagate(Throwables.getRootCause(e));
		}

	}

	private ModelCommandHelper()
	{
		super();
	}
}
