package de.metas.edi.esb.commons.api;

/*
 * #%L
 * de.metas.edi.esb
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


public interface ILookupValue<KT extends Object>
{
	/**
	 * @return {@link ILookupTemplate} the Method Template
	 */
	ILookupTemplate<KT> getLookupTemplate();

	/**
	 * @return KT value
	 */
	KT getValue();

	/**
	 * @return true if lookup field is mandatory <b>(will NOT create a lookup-object instance if <code>{@link #getValue()}==null</code>)</b>, false otherwise
	 */
	boolean isMandatoryLookup();
}
