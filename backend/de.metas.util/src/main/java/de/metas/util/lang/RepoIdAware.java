package de.metas.util.lang;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * @implNote Following static methods shall be implemented
 * <ul>
 *     <li>ofRepoId(int)</li>
 *     <li>ofRepoIdOrNull(int)</li>
 * </ul>
 * Note that instances of RepoIdAware can be used in query-filters, instead of integers. 
 * metasfresh will unbox them by calling {@link #getRepoId()}.
 */
import lombok.NonNull;

import java.util.function.Function;

public interface RepoIdAware extends Comparable<RepoIdAware>
{
	int getRepoId();

	@Override
	default int compareTo(final RepoIdAware other)
	{
		return getRepoId() - other.getRepoId();
	}

	default <T> T map(@NonNull final Function<Integer, T> mappingFunction) {
		return mappingFunction.apply(getRepoId());
	}
}
