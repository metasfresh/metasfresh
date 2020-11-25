package de.metas.edi.esb;

import java.util.StringJoiner;

import org.springframework.lang.Nullable;

import io.github.jsonSnapshot.SnapshotConfig;
import io.github.jsonSnapshot.SnapshotMatchingStrategy;
import io.github.jsonSnapshot.matchingstrategy.StringEqualsMatchingStrategy;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public final class SnapshotHelper
{
	public static final SnapshotConfig SNAPSHOT_CONFIG = new SnapshotConfig()
	{
		@Override
		public String getFilePath()
		{
			return "src/test/resources/";
		}

		@Override
		public SnapshotMatchingStrategy getSnapshotMatchingStrategy()
		{
			return StringEqualsMatchingStrategy.INSTANCE;
		}
	};

	public String toArrayAwareString(@Nullable final Object obj)
	{
		if (obj == null)
		{
			return "<NULL>";
		}

		final StringJoiner joiner = new StringJoiner(";", "[\n", "\n]");
		if (obj instanceof Object[])
		{
			final Object[] objArray = (Object[])obj;
			for (int i = 0; i < objArray.length; i++)
			{
				joiner.add(objArray[i].toString());
			}
		}
		else if (obj instanceof Iterable<?>)
		{
			final Iterable<?> objIterable = (Iterable<?>)obj;
			for (final Object currentObj : objIterable)
			{
				joiner.add(currentObj.toString());
			}

		}
		else
		{
			joiner.add(obj.toString());
		}
		return joiner.toString();
	}
}
