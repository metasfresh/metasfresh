package de.metas.ui.web.view;

import java.time.ZonedDateTime;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Builder
public class ViewHeaderProperty
{
	@NonNull
	ITranslatableString caption;
	@NonNull
	ITranslatableString value;

	public static class ViewHeaderPropertyBuilder
	{
		public ViewHeaderPropertyBuilder value(final ITranslatableString value)
		{
			this.value = value;
			return this;
		}

		public ViewHeaderPropertyBuilder value(final String value)
		{
			return value(TranslatableStrings.anyLanguage(value));
		}

		public ViewHeaderPropertyBuilder value(final ZonedDateTime value)
		{
			return value(TranslatableStrings.dateAndTime(value));
		}

		public ViewHeaderPropertyBuilder value(final int value)
		{
			return value(TranslatableStrings.number(value));
		}
	}
}
