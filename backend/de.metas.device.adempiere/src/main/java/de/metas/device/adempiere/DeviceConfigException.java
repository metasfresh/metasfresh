package de.metas.device.adempiere;

/*
 * #%L
 * de.metas.device.adempiere
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

import org.adempiere.exceptions.AdempiereException;

@SuppressWarnings("serial")
public class DeviceConfigException extends AdempiereException
{
	public static DeviceConfigException permanentFailure(final String msg, final Throwable cause)
	{
		final boolean permanentFailure = true;
		return new DeviceConfigException(msg, cause, permanentFailure);
	}

	public static DeviceConfigException permanentFailure(final String msg)
	{
		final Throwable cause = null;
		final boolean permanentFailure = true;
		return new DeviceConfigException(msg, cause, permanentFailure);
	}

	public static DeviceConfigException wrapIfNeeded(final Throwable throwable)
	{
		if (throwable == null)
		{
			return null;
		}

		final Throwable cause = extractCause(throwable);
		if (cause instanceof DeviceConfigException)
		{
			return (DeviceConfigException)cause;
		}
		else
		{
			final String msg = extractMessage(cause);
			final boolean permanentFailure = false;
			return new DeviceConfigException(msg, cause, permanentFailure);
		}
	}

	private final boolean permanentFailure;

	public DeviceConfigException(final String msg)
	{
		super(msg);
		permanentFailure = false;
	}

	private DeviceConfigException(final String msg, final Throwable cause, final boolean permanentFailure)
	{
		super(msg, cause);
		this.permanentFailure = permanentFailure;
	}

	public boolean isPermanentFailure()
	{
		return permanentFailure;
	}
}
