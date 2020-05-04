/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.github;

import de.metas.uom.UomId;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface GithubImporterConstants
{
	int CHUNK_SIZE = 100;
	UomId HOUR_UOM_ID = UomId.ofRepoId(101);

	@AllArgsConstructor
	@Getter
	enum GitHubConfig
	{
		ACCESS_TOKEN("accessToken"),
		LOOK_FOR_PARENT("lookForParent");

		private final String name;
	}
}
