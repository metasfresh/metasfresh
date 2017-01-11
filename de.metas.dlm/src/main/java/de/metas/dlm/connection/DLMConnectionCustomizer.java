package de.metas.dlm.connection;

import java.sql.Connection;

import javax.annotation.concurrent.Immutable;

import de.metas.connection.ITemporaryConnectionCustomizer;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * This instance is intended to be used in <code>try-with-resource</code> blocks. Its {@link #getDlmLevel()} and {@link #getDlmCoalesceLevel()} return a constant value.
 *
 * @see IConnectionCustomizerService#registerTemporaryCustomizer(ITemporaryConnectionCustomizer).
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
public class DLMConnectionCustomizer
		extends AbstractDLMCustomizer
		implements ITemporaryConnectionCustomizer
{
	private final int dlmLevel;
	private final int dlmCoalesceLevel;

	public static DLMConnectionCustomizer withLevels(final int dlmLevel, final int dlmCoalesceLevel)
	{
		return new DLMConnectionCustomizer(dlmLevel, dlmCoalesceLevel);
	}

	/**
	 * Returns an instance with both DLM level and DLM coalesce level beeing the maximum value that a PostgreSQL {@code SMALLINT} can hold. Therefore, while this customizer is active, all records will be visislbe.
	 * <p>
	 * See <a href="https://www.postgresql.org/docs/9.5/static/datatype-numeric.html">PostgreSQL Numeric Types</a> documentation.
	 *
	 * @return
	 */
	public static DLMConnectionCustomizer seeThemAllCustomizer()
	{
		return new DLMConnectionCustomizer(Short.MAX_VALUE, Short.MAX_VALUE);
	}

	private DLMConnectionCustomizer(final int dlmLevel, final int dlmCoalesceLevel)
	{
		this.dlmLevel = dlmLevel;
		this.dlmCoalesceLevel = dlmCoalesceLevel;
	};

	@Override
	public int getDlmLevel(final Connection IGNORED)
	{
		return dlmLevel;
	}

	@Override
	public int getDlmCoalesceLevel(final Connection IGNORED)
	{
		return dlmCoalesceLevel;
	}
}
