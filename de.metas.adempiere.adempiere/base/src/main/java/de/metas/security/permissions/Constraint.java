package de.metas.security.permissions;

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


import org.adempiere.util.lang.ObjectUtils;

/**
 * Defines a security constraint.
 * 
 * A security constraint shall be seen as a custom rule which defines what shall be done or what's allowed in a given context.
 * 
 * The framework user shall know about a given constraint and it shall ask for it.
 * 
 * @author tsa
 *
 */
public abstract class Constraint
{
	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/**
	 * @return true if this constraint can be inherited from included roles.
	 */
	public abstract boolean isInheritable();
}
