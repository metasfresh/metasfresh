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
import de.metas.util.Check;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.model.MobileApplicationInfo;
import de.metas.workflow.rest_api.service.MobileApplication;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class ScanAnythingMobileApplication implements MobileApplication
{
	private static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("scanAnything");
	//private static final AdMessageKey MSG_Caption = AdMessageKey.of("mobileui.scanAnything.appName");

	private static final MobileApplicationInfo APPLICATION_INFO = computeApplicationInfo(
			WorkplaceManager.APPLICATION_INFO,
			WorkstationManager.APPLICATION_INFO
	);

	private static MobileApplicationInfo computeApplicationInfo(final MobileApplicationInfo... handledApplicationInfos)
	{
		Check.assumeNotEmpty(handledApplicationInfos, "handledApplicationInfos not empty");

		final ImmutableSet<MobileApplicationId> handledApplicationIds = Stream.of(handledApplicationInfos)
				.map(MobileApplicationInfo::getId)
				.collect(ImmutableSet.toImmutableSet());

		final ITranslatableString caption = Stream.of(handledApplicationInfos)
				.map(MobileApplicationInfo::getCaption)
				.collect(TranslatableStrings.joining(", "));

		return MobileApplicationInfo.builder()
				.id(APPLICATION_ID)
				.caption(caption)
				.applicationParameter("handledApplicationIds", handledApplicationIds)
				.build();
	}

	@Override
	public MobileApplicationId getApplicationId()
	{
		return APPLICATION_ID;
	}

	@Override
	public @NonNull MobileApplicationInfo getApplicationInfo(@NonNull UserId loggedUserId)
	{
		return APPLICATION_INFO;
	}
}
