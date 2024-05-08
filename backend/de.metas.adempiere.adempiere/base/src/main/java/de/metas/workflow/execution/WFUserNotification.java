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

package de.metas.workflow.execution;

import de.metas.i18n.ADMessageAndParams;
import de.metas.i18n.AdMessageKey;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

@Value
@Builder
class WFUserNotification
{
	@NonNull
	UserId userId;

	@NonNull
	ADMessageAndParams content;

	@Nullable
	TableRecordReference documentToOpen;

	public static class WFUserNotificationBuilder
	{
		public WFUserNotificationBuilder content(@NonNull final AdMessageKey adMessage, @Nullable final Object... params)
		{
			return content(ADMessageAndParams.of(adMessage, params));
		}

		public WFUserNotificationBuilder content(@Nullable final ADMessageAndParams content)
		{
			this.content = content;
			return this;
		}

	}
}
