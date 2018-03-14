package org.compiere.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Util.ArrayKey;

/**
 * Helper class to assist developer to build immutable {@link ArrayKey}s. Use {@link Util#mkKey()} or {@link Util#mkKey(Object...)} to get yours.
 * 
 * @author tsa
 *
 */
public class ArrayKeyBuilder
{
	// private static final transient Logger logger = CLogMgt.getLogger(ArrayKeyBuilder.class);

	private final List<Object> keyParts = new ArrayList<>();

	/* package */ ArrayKeyBuilder()
	{
		super();
	}

	public ArrayKey build()
	{
		return new ArrayKey(keyParts.toArray());
	}

	/**
	 * Directly append given object.
	 * 
	 * @param obj object to append (could be null)
	 * @return this
	 */
	public ArrayKeyBuilder append(final Object obj)
	{
		keyParts.add(obj);
		return this;
	}

	public ArrayKeyBuilder appendAll(final Collection<Object> objs)
	{
		keyParts.addAll(objs);
		return this;
	}

	public ArrayKeyBuilder append(final String name, final Object obj)
	{
		keyParts.add(name);
		keyParts.add(obj);
		return this;
	}

	/**
	 * Appends given ID. If ID is less or equal with ZERO then -1 will be appended.
	 * 
	 * @param id ID to append
	 * @return this
	 */
	public ArrayKeyBuilder appendId(final int id)
	{
		keyParts.add(id <= 0 ? -1 : id);
		return this;
	}

	public ArrayKeyBuilder appendModelId(final Object model)
	{
		final int modelId;
		if (model == null)
		{
			modelId = -1;
		}
		else
		{
			modelId = InterfaceWrapperHelper.getId(model);
		}

		keyParts.add(modelId);
		return this;
	}
}
