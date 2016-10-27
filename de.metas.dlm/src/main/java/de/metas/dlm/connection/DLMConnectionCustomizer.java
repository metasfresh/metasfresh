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

	public DLMConnectionCustomizer(final int dlmLevel, final int dlmCoalesceLevel)
	{
		this.dlmLevel = dlmLevel;
		this.dlmCoalesceLevel = dlmCoalesceLevel;
	};

	/**
	 * Does nothing.
	 * As of now, the customizers make sure that their connection is the way it needs to be when the connection is checked out.
	 * There is not need to spend extra effort cleaning up when the connection is returned.
	 */
	@Override
	public void undoCustomization()
	{
		// nothing
	}

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
