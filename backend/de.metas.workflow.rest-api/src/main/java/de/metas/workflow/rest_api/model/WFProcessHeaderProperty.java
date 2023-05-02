/*
 * #%L
 * de.metas.workflow.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.workflow.rest_api.model;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class WFProcessHeaderProperty
{
	@NonNull ITranslatableString caption;
	@NonNull ITranslatableString value;

	@SuppressWarnings("unused")
	public static class WFProcessHeaderPropertyBuilder
	{
		public WFProcessHeaderPropertyBuilder value(final ITranslatableString value)
		{
			this.value = value;
			return this;
		}

		public WFProcessHeaderPropertyBuilder value(final String value)
		{
			return value(TranslatableStrings.anyLanguage(value));
		}

		public WFProcessHeaderPropertyBuilder value(final ZonedDateTime value)
		{
			return value(TranslatableStrings.dateAndTime(value));
		}

		public WFProcessHeaderPropertyBuilder value(final int value)
		{
			return value(TranslatableStrings.number(value));
		}
	}
}
