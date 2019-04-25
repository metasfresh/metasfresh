package de.metas.security.permissions.record_access.handlers;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.security.permissions.record_access.RecordAccessFeature;
import de.metas.security.permissions.record_access.RecordAccess;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@EqualsAndHashCode
@ToString
public class ManualRecordAccessHandler implements RecordAccessHandler
{
	public static final ManualRecordAccessHandler ofTableNames(final Collection<String> tableNames)
	{
		return new ManualRecordAccessHandler(tableNames);
	}

	private static final ImmutableSet<RecordAccessFeature> HANDLED_FEATURES = ImmutableSet.of(RecordAccessFeature.MANUAL_TABLE);

	private final ImmutableSet<String> handledTableNames;

	private ManualRecordAccessHandler(final Collection<String> tableNames)
	{
		Check.assumeNotEmpty(tableNames, "tableNames is not empty");
		this.handledTableNames = ImmutableSet.copyOf(tableNames);
	}

	@Override
	public Set<RecordAccessFeature> getHandledFeatures()
	{
		return HANDLED_FEATURES;
	}

	@Override
	public Set<String> getHandledTableNames()
	{
		return handledTableNames;
	}

	@Override
	public void onAccessGranted(final RecordAccess request)
	{
		// nothing
	}

	@Override
	public void onAccessRevoked(final RecordAccess request)
	{
		// nothing
	}
}
