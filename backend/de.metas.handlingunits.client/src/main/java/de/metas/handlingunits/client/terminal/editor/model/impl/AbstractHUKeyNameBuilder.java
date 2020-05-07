package de.metas.handlingunits.client.terminal.editor.model.impl;

/*
 * #%L
 * de.metas.handlingunits.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.Check;
import org.adempiere.util.lang.IReference;
import org.adempiere.util.lang.ImmutableReference;

import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyNameBuilder;

public abstract class AbstractHUKeyNameBuilder<T extends IHUKey> implements IHUKeyNameBuilder
{
	private final IReference<T> keyRef;

	public AbstractHUKeyNameBuilder(final T key)
	{
		super();

		Check.assumeNotNull(key, "key not null");
		this.keyRef = ImmutableReference.<T>valueOf(key);
	}

	/**
	 *
	 * @return HU Key; never return null
	 */
	protected final T getKey()
	{
		final T key = keyRef.getValue();
		Check.assumeNotNull(key, "key reference not expired");
		return key;
	}
}
