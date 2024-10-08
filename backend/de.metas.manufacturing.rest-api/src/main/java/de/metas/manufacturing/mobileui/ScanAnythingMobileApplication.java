/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.manufacturing.mobileui;

import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.user.UserId;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.mobile.application.MobileApplicationInfo;
import de.metas.workflow.rest_api.service.MobileApplication;
import de.metas.mobile.application.repository.MobileApplicationInfoRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScanAnythingMobileApplication implements MobileApplication
{
	private static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("scanAnything");

	private static final ImmutableSet<MobileApplicationId> HANDLED_APPLICATION_IDS = ImmutableSet.of(
			WorkplaceManager.APPLICATION_ID,
			WorkstationManager.APPLICATION_ID
	);

	@NonNull private final MobileApplicationInfoRepository mobileApplicationInfoRepository;

	@Override
	public MobileApplicationId getApplicationId()
	{
		return APPLICATION_ID;
	}

	@Override
	public @NonNull MobileApplicationInfo customizeApplicationInfo(@NonNull final MobileApplicationInfo applicationInfo, @NonNull final UserId loggedUserId)
	{
		final ITranslatableString caption = HANDLED_APPLICATION_IDS.stream()
				.map(mobileApplicationInfoRepository::getById)
				.map(MobileApplicationInfo::getCaption)
				.collect(TranslatableStrings.joining(", "));

		return applicationInfo.toBuilder()
				.caption(caption)
				.applicationParameter("handledApplicationIds", HANDLED_APPLICATION_IDS)
				.build();
	}

}
