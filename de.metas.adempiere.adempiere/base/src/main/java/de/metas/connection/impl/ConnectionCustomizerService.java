package de.metas.connection.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;

import de.metas.connection.IConnectionCustomizer;
import de.metas.connection.IConnectionCustomizerService;
import de.metas.connection.ITemporaryConnectionCustomizer;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class ConnectionCustomizerService implements IConnectionCustomizerService
{

	private final List<IConnectionCustomizer> permanentCustomizers = new ArrayList<>();

	private final ThreadLocal<List<ITemporaryConnectionCustomizer>> temporaryCustomizers = ThreadLocal.withInitial(() -> new ArrayList<>());

	@Override
	public void registerPermanentCustomizer(final IConnectionCustomizer connectionCustomizer)
	{
		permanentCustomizers.add(connectionCustomizer);
	}

	@Override
	public List<IConnectionCustomizer> getRegisteredCustomizers()
	{
		return new ImmutableList.Builder<IConnectionCustomizer>()
				.addAll(permanentCustomizers)
				.addAll(temporaryCustomizers.get())
				.build();
	}

	@Override
	public AutoCloseable registerTemporaryCustomizer(final ITemporaryConnectionCustomizer connectionCustomizer)
	{
		temporaryCustomizers.get().add(connectionCustomizer);

		return new AutoCloseable()
		{
			@Override
			public void close() throws Exception
			{
				removeTemporaryCustomizer(connectionCustomizer);
				connectionCustomizer.undoCustomization();
			}
		};
	}

	private void removeTemporaryCustomizer(final ITemporaryConnectionCustomizer dlmConnectionCustomizer)
	{
		final boolean wasInTheList = temporaryCustomizers.get().remove(dlmConnectionCustomizer);
		Check.errorIf(!wasInTheList, "ITemporaryConnectionCustomizer={} was not in the thread-local list of temperary customizers; this={}", dlmConnectionCustomizer, this);
	}

	@Override
	public String toString()
	{
		return "ConnectionCustomizerService [permanentCustomizers=" + permanentCustomizers + ", (thread-local-)temporaryCustomizers=" + temporaryCustomizers.get() + "]";
	}
}
