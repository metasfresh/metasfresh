package de.metas.event.impl;

import java.util.Objects;

import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;

import de.metas.event.Event;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class EventMDC
{
	/**
	 * @return {@code null} if the given {@code event} was put already.
	 *         Thx to https://stackoverflow.com/a/35372185/1012103
	 */
	public MDCCloseable putEvent(@NonNull final Event event)
	{
		if (Objects.equals(MDC.get("de.metas.event.uuid"), event.getUuid().toString()))
		{
			return null;
		}
		return MDC.putCloseable("de.metas.event.uuid", event.getUuid().toString());
	}
}
