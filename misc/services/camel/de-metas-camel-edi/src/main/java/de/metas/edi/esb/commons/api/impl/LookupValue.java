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

/* package */class LookupValue<KT extends Object> implements ILookupValue<KT>
{
	private final ILookupTemplate<KT> lookupTemplate;
	private final KT value;
	private final boolean mandatoryLookup;

	public LookupValue(final ILookupTemplate<KT> lookupTemplate, final KT value, final boolean mandatoryLookup)
	{
		this.lookupTemplate = lookupTemplate;
		this.value = value;
		this.mandatoryLookup = mandatoryLookup;
	}

	@Override
	public ILookupTemplate<KT> getLookupTemplate()
	{
		return lookupTemplate;
	}

	@Override
	public KT getValue()
	{
		return value;
	}

	@Override
	public boolean isMandatoryLookup()
	{
		return mandatoryLookup;
	}
}
