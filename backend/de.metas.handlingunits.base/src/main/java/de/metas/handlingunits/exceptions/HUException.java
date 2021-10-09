package de.metas.handlingunits.exceptions;

/*
 * #%L
 * de.metas.handlingunits.base
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

import org.adempiere.exceptions.AdempiereException;

import de.metas.i18n.AdMessageKey;
import de.metas.util.Check;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * Root of Handling Units module exceptions hierarchy.
 *
 * @author tsa
 *
 */
public class HUException extends AdempiereException
{

	private static final long serialVersionUID = 800341714184424257L;

	public static final HUException ofAD_Message(final String adMessage)
	{
		final String adMessageEffective = !Check.isEmpty(adMessage, true) ? adMessage : "Error";
		return new HUException("@" + adMessageEffective + "@");
	}

	public HUException(final String message, final Throwable cause)
	{
		super(message, cause);
		appendParametersToMessage(); // preserve HUException's historical behavior
	}

	public HUException(final String message)
	{
		super(message);
		appendParametersToMessage(); // preserve HUException's historical behavior
	}

	public HUException(final Throwable cause)
	{
		super(cause);
		appendParametersToMessage(); // preserve HUException's historical behavior
	}
	
	public HUException(@NonNull final AdMessageKey message)
	{
		super(message);
	}

	@Override
	public HUException setParameter(final @NonNull String name, @Nullable final Object value)
	{
		super.setParameter(name, value);
		return this;
	}

	@Override
	public final HUException appendParametersToMessage()
	{
		super.appendParametersToMessage();
		return this;
	}
}
