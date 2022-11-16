/*
 * #%L
 * metasfresh-report-service
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

package de.metas.report.jasper.class_loader.images.org;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;

class OrgLogoResourceNameMatcher
{
	//
	// Org Logo resource matchers
	private static final String SYSCONFIG_ResourceNameEndsWith = "de.metas.adempiere.report.jasper.OrgLogoClassLoaderHook.ResourceNameEndsWith";
	private static final String DEFAULT_ResourceNameEndsWith = "de/metas/generics/logo.png";
	private final ImmutableSet<String> resourceNameEndsWithMatchers;

	public OrgLogoResourceNameMatcher(@NonNull final ISysConfigBL sysConfigBL)
	{
		final String resourceNameEndsWithStr = sysConfigBL.getValue(SYSCONFIG_ResourceNameEndsWith, DEFAULT_ResourceNameEndsWith);
		this.resourceNameEndsWithMatchers = buildResourceNameEndsWithMatchers(resourceNameEndsWithStr);
	}

	private static ImmutableSet<String> buildResourceNameEndsWithMatchers(final String resourceNameEndsWithStr)
	{
		if (Check.isEmpty(resourceNameEndsWithStr, true))
		{
			return ImmutableSet.of();
		}

		return ImmutableSet.copyOf(Splitter.on(';')
				.trimResults()
				.omitEmptyStrings()
				.splitToList(resourceNameEndsWithStr));
	}

	public boolean matches(final String resourceName)
	{
		// Skip if no resourceName
		if (resourceName == null || resourceName.isEmpty())
		{
			return false;
		}

		// Check if our resource name ends with one of our predefined matchers
		for (final String resourceNameEndsWithMatcher : resourceNameEndsWithMatchers)
		{
			if ( resourceName.endsWith(resourceNameEndsWithMatcher))
			{
				return true;
			}
		}

		// Fallback: not an org image resource
		return false;
	}

}
