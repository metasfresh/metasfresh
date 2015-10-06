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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


/**
 * Implementations of this interface support {@link #dispose()} capability.
 * 
 * @author tsa
 *
 */
public interface IDisposable
{
	/**
	 * Clean-up all internal state of this object/component (i.e. remove listeners, destroy underlying components etc).
	 * 
	 * This method will be called by API when it wants to destroy a component or it can be called programatically by developer.
	 * 
	 * After calling this method, this object is no longer usable.
	 */
	void dispose();
}
