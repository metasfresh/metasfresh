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

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.api.Params;

@Value
@Builder
public class UIComponent
{
	@NonNull UIComponentType type;

	@Builder.Default
	@NonNull WFActivityAlwaysAvailableToUser alwaysAvailableToUser = WFActivityAlwaysAvailableToUser.DEFAULT;

	@Builder.Default
	@NonNull Params properties = Params.EMPTY;

	public static UIComponentBuilder builderFrom(
			@NonNull final UIComponentType type,
			@NonNull final WFActivity wfActivity)
	{
		return builder()
				.type(type)
				.alwaysAvailableToUser(wfActivity.getAlwaysAvailableToUser());
	}
}
