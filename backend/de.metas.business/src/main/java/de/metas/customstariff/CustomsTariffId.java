package de.metas.customstariff;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_CustomsTariff;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * de.metas.business
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

@Value
public class CustomsTariffId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static CustomsTariffId ofRepoId(final int repoId)
	{
		return new CustomsTariffId(repoId);
	}

	@Nullable
	public static CustomsTariffId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new CustomsTariffId(repoId) : null;
	}

	@Nullable
	public static CustomsTariffId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new CustomsTariffId(repoId) : null;
	}

	public static Optional<CustomsTariffId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	public static Set<CustomsTariffId> ofRepoIds(final Collection<Integer> repoIds)
	{
		return repoIds.stream()
				.filter(repoId -> repoId != null && repoId > 0)
				.map(CustomsTariffId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public static int toRepoId(@Nullable final CustomsTariffId customsTariffId)
	{
		return customsTariffId != null ? customsTariffId.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final CustomsTariffId o1, @Nullable final CustomsTariffId o2)
	{
		return Objects.equals(o1, o2);
	}

	private CustomsTariffId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_CustomsTariff_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public String getAsString() {return String.valueOf(getRepoId());}

	public TableRecordReference toTableRecordReference()
	{
		return TableRecordReference.of(I_M_CustomsTariff.Table_Name, getRepoId());
	}
}
