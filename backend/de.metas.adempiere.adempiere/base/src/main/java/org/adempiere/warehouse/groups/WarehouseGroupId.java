/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.adempiere.warehouse.groups;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class WarehouseGroupId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static WarehouseGroupId ofRepoId(final int repoId) {return new WarehouseGroupId(repoId);}

	@Nullable
	public static WarehouseGroupId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new WarehouseGroupId(repoId) : null;}

	public static Optional<WarehouseGroupId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	public static Optional<WarehouseGroupId> optionalOfRepoIdObject(@Nullable final Object repoIdObj) {return optionalOfRepoId(NumberUtils.asInt(repoIdObj, -1));}

	public static int toRepoId(final WarehouseGroupId id) {return id != null ? id.getRepoId() : -1;}

	private WarehouseGroupId(final int repoId) {this.repoId = Check.assumeGreaterThanZero(repoId, "M_Warehouse_Group_ID");}

	@Override
	@JsonValue
	public int getRepoId() {return repoId;}
}
