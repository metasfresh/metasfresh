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

package de.metas.report.jasper;

import de.metas.util.Check;
import de.metas.util.FileUtil;
import lombok.NonNull;

import java.util.Optional;

class OrgImageResourceNameMatcher
{
	public static final OrgImageResourceNameMatcher instance = new OrgImageResourceNameMatcher();
	private static final String RESOURCENAME_PREFIX = "de/metas/org/images/";

	private OrgImageResourceNameMatcher() {}

	public Optional<String> getImageColumnName(@NonNull final String resourceName)
	{
		final int idx = resourceName.indexOf(RESOURCENAME_PREFIX);
		if (idx < 0)
		{
			return Optional.empty();
		}

		final String imageFileName = resourceName.substring(idx + RESOURCENAME_PREFIX.length());
		final String imageColumnName = FileUtil.getFileBaseName(imageFileName);
		return !Check.isBlank(imageColumnName)
				? Optional.of(imageColumnName.trim())
				: Optional.empty();
	}
	
	
	public boolean matches(@NonNull final String resourceName)
	{
		// Skip if no resourceName
		if (resourceName == null || resourceName.isEmpty())
		{
			return false;
		}

		final int idx = resourceName.indexOf(RESOURCENAME_PREFIX);
		if (idx < 0)
		{
			return false;
		}

		return true;
	}
}
