package de.metas.builder;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;

import de.metas.util.Check;

/**
 * First try of extracting common code from all builders.
 * 
 * @author ts
 * 
 * @param <LB> type of the {@link ILineBuilder} that this instance shall create.
 */
public class BuilderSupport<LB extends ILineBuilder>
{

	private final List<LB> lines = new ArrayList<LB>();

	private final IBuilder parent;

	public BuilderSupport(final IBuilder parent)
	{
		Check.assumeNotNull(parent, "Param 'parent' is not null");
		this.parent = parent;
	}

	/**
	 * Returns the lineBuilders created by this instance. This method is usually used by the builder implementation when the lines for a header record shall be created.
	 * 
	 * @return
	 */
	public List<LB> getLines()
	{
		return lines;
	}

	/**
	 * Creates a new {@link ILineBuilder}. This method is usually called by the builder implementation, e.g. when a new invoice line builder shall be added for an invoice builder.
	 * 
	 * @param implClazz
	 * @return
	 */
	public final LB addLine(final Class<? extends LB> implClazz)
	{
		try
		{
			@SuppressWarnings("unchecked")
			final Constructor<LB> c = (Constructor<LB>)implClazz.getConstructor(parent.getClass());
			final LB line = c.newInstance(parent);
			lines.add(line);
			return line;
		}
		catch (Exception e)
		{
			throw new AdempiereException("Unable to create new " + parent.getClass() + " with class " + implClazz, e);
		}
	}

}
