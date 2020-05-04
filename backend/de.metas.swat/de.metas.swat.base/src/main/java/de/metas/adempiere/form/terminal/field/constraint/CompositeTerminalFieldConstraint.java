package de.metas.adempiere.form.terminal.field.constraint;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.List;

import de.metas.adempiere.form.terminal.ITerminalField;
import de.metas.adempiere.form.terminal.WrongValueException;
import de.metas.util.Check;

public class CompositeTerminalFieldConstraint<T> implements ITerminalFieldConstraint<T>
{
	private final List<ITerminalFieldConstraint<T>> constraints = new ArrayList<ITerminalFieldConstraint<T>>();

	public void addConstraint(final ITerminalFieldConstraint<T> constraint)
	{
		Check.assumeNotNull(constraint, "constraint not null");
		constraints.add(constraint);
	}
	
	public void clear()
	{
		constraints.clear();
	}

	@Override
	public void evaluate(final ITerminalField<T> field, final T value) throws WrongValueException
	{
		for (final ITerminalFieldConstraint<T> constraint : constraints)
		{
			constraint.evaluate(field, value);
		}
	}
}
