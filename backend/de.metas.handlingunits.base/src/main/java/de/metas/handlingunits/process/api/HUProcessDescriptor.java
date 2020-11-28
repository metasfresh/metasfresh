package de.metas.handlingunits.process.api;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.process.AdProcessId;
import de.metas.util.Check;
import de.metas.common.util.CoalesceUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
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
public class HUProcessDescriptor
{
	AdProcessId processId;
	String internalName;

	boolean provideAsUserAction;

	boolean acceptOnlyTopLevelHUs;

	@Getter(AccessLevel.NONE)
	private final ImmutableSet<String> acceptHUUnitTypes;

	@Builder
	private HUProcessDescriptor(
			@NonNull final AdProcessId processId,
			@NonNull final String internalName,
			final Boolean provideAsUserAction,
			final Boolean acceptOnlyTopLevelHUs,
			@Singular final Set<String> acceptHUUnitTypes)
	{
		Check.assumeNotEmpty(acceptHUUnitTypes, "acceptHUUnitTypes is not empty");

		this.processId = processId;
		this.internalName = Check.assumeNotEmpty(internalName, "internalName is not empty");
		this.acceptHUUnitTypes = ImmutableSet.copyOf(acceptHUUnitTypes);

		this.provideAsUserAction = CoalesceUtil.coalesce(provideAsUserAction, true);
		this.acceptOnlyTopLevelHUs = CoalesceUtil.coalesce(acceptOnlyTopLevelHUs, false);
	}

	public boolean appliesToHUUnitType(@NonNull final String huUnitType)
	{
		return acceptHUUnitTypes.contains(huUnitType);
	}

	public boolean appliesToAllHUUnitTypes(@NonNull final Collection<String> huUnitTypes)
	{
		final boolean doesNotApply = huUnitTypes.stream().anyMatch(huUnitType -> !appliesToHUUnitType(huUnitType));
		return !doesNotApply;
	}

}
