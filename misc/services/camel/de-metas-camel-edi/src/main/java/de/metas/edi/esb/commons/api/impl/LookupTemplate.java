package de.metas.edi.esb.commons.api.impl;

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


import de.metas.edi.esb.commons.api.ILookupTemplate;
import de.metas.edi.esb.commons.api.ILookupValue;

public class LookupTemplate<KT extends Object> implements ILookupTemplate<KT>
{
	private final Class<KT> keyType;
	private final String methodName;

	/**
	 * Create an {@link ILookupTemplate} instance.<br>
	 * <br>
	 * Call {@link #createMandatoryValueLookup(Object)} or {@link #createNonMandatoryValueLookup(Object)} in order to instantiate an {@link ILookupValue}
	 * 
	 * @param keyType
	 * @param methodName
	 */
	public LookupTemplate(final Class<KT> keyType, final String methodName)
	{
		this.keyType = keyType;
		this.methodName = methodName;
	}

	@Override
	public Class<KT> getKeyType()
	{
		return keyType;
	}

	@Override
	public String getMethodName()
	{
		return methodName;
	}

	@Override
	public ILookupValue<KT> createMandatoryValueLookup(final KT value)
	{
		return new LookupValue<KT>(this, value, true); // mandatoryLookup=true
	}

	@Override
	public ILookupValue<KT> createNonMandatoryValueLookup(final KT value)
	{
		return new LookupValue<KT>(this, value, false); // mandatoryLookup=false
	}
}
