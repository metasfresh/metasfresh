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
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.With;

import javax.annotation.Nullable;

@ToString
public class WFActivity
{
	@Getter
	@NonNull private final WFActivityId id;

	@Getter
	@NonNull private final ITranslatableString caption;

	@Getter
	@NonNull private final WFActivityType wfActivityType;

	@Getter
	@With
	@NonNull private final WFActivityStatus status;

	@Builder
	private WFActivity(
			@NonNull final WFActivityId id,
			@NonNull final ITranslatableString caption,
			@NonNull final WFActivityType wfActivityType,
			@Nullable final WFActivityStatus status)
	{
		this.id = id;
		this.caption = caption;
		this.wfActivityType = wfActivityType;
		this.status = status != null ? status : WFActivityStatus.NOT_STARTED;
	}

	public boolean isCompleted()
	{
		return getStatus().isCompleted();
	}
}
