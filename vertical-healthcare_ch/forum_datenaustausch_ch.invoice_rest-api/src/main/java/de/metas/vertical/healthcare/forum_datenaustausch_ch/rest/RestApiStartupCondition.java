package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest;

import lombok.NonNull;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.google.common.collect.ImmutableSet;

import de.metas.Profiles;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_rest-api
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

public class RestApiStartupCondition implements Condition
{
	@Override
	public boolean matches(
			@NonNull final ConditionContext context,
			@NonNull final AnnotatedTypeMetadata metadata)
	{
		final String[] activeProfilesArray = context.getEnvironment().getActiveProfiles();
		final ImmutableSet<String> activeProfiles = ImmutableSet.copyOf(activeProfilesArray);

		return activeProfiles.contains(ForumDatenaustauschChConstants.PROFILE)
				&& activeProfiles.contains(Profiles.PROFILE_App);
	}
}
