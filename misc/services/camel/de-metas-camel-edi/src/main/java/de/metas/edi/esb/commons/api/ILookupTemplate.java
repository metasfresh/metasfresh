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


public interface ILookupTemplate<KT extends Object>
{
	/**
	 * @return type of the lookup key
	 */
	Class<KT> getKeyType();

	/**
	 * @return setter method of the lookup key
	 */
	String getMethodName();

	/**
	 * @param value
	 * @return lookup value with <code>mandatoryLookup=true</code>
	 */
	ILookupValue<KT> createMandatoryValueLookup(KT value);

	/**
	 * @param value
	 * @return lookup value with <code>mandatoryLookup=false</code>
	 */
	ILookupValue<KT> createNonMandatoryValueLookup(KT value);
}
