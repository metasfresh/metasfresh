package de.metas.procurement.base;

import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.logging.LogManager;
import org.slf4j.Logger;

/*
 * #%L
 * de.metas.procurement.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class ProcurementConstants
{
	public static final Logger getLogger(Class<?> clazz)
	{
		return LogManager.getLogger(clazz);
	}

	public static final Topic USER_NOTIFICATIONS_TOPIC = Topic.builder()
			.name("de.metas.procurement.UserNotifications")
			.type(Type.DISTRIBUTED)
			.build();

	private ProcurementConstants()
	{
	}
}
