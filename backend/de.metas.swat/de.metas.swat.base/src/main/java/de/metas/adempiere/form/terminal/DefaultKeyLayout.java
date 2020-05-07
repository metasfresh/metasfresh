package de.metas.adempiere.form.terminal;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.adempiere.util.Check;

import de.metas.adempiere.form.terminal.context.ITerminalContext;

public class DefaultKeyLayout
		extends KeyLayout
		implements IKeyLayoutSelectionModelAware
{
	private final String id;

	public DefaultKeyLayout(final ITerminalContext tc)
	{
		this(tc, null);
	}

	public DefaultKeyLayout(final ITerminalContext tc, final String id)
	{
		super(tc);

		if (Check.isEmpty(id, true))
		{
			this.id = getClass().getName() + "-" + UUID.randomUUID();
		}
		else
		{
			this.id = id;
		}
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public boolean isText()
	{
		return false;
	}

	@Override
	public boolean isNumeric()
	{
		return false;
	}

	@Override
	protected final List<ITerminalKey> createKeys()
	{
		// nothing; we will set them programatically
		return Collections.emptyList();
	}
}
