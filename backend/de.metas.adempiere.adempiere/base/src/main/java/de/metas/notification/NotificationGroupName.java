package de.metas.notification;

import de.metas.event.Topic;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class NotificationGroupName
{
	public static NotificationGroupName of(final String valueAsString)
	{
		return new NotificationGroupName(valueAsString);
	}

	public static NotificationGroupName of(@NonNull final Topic topic)
	{
		return NotificationGroupName.of(topic.getName());
	}

	private final String valueAsString;

	private NotificationGroupName(@NonNull final String valueAsString)
	{
		Check.assumeNotEmpty(valueAsString, "valueAsString shall not be empty");
		this.valueAsString = valueAsString;
	}

	public Topic toTopic()
	{
		return Topic.distributed(valueAsString);
	}
}
