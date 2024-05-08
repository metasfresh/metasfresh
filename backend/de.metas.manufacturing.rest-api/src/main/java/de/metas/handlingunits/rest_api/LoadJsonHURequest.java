/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.handlingunits.rest_api;

import de.metas.handlingunits.model.I_M_HU;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class LoadJsonHURequest
{
	@With
	@NonNull
	I_M_HU hu;

	@NonNull
	String adLanguage;

	@With
	boolean getAllowedClearanceStatuses;

	@NonNull
	public static LoadJsonHURequest ofHUAndLanguage(@NonNull final I_M_HU hu, @NonNull final String adLanguage)
	{
		return LoadJsonHURequest.builder()
				.adLanguage(adLanguage)
				.hu(hu)
				.build();
	}
}
