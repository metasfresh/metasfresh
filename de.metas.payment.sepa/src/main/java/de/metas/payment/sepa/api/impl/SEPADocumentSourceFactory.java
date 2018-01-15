package de.metas.payment.sepa.api.impl;

/*
 * #%L
 * de.metas.payment.sepa
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


import java.util.HashMap;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import de.metas.payment.sepa.api.ISEPADocumentSourceFactory;
import de.metas.payment.sepa.api.ISEPADocumentSourceQuery;
import de.metas.payment.sepa.sepamarshaller.ISEPADocumentSource;

public class SEPADocumentSourceFactory implements ISEPADocumentSourceFactory
{
	private final Map<String, Class<? extends ISEPADocumentSource>> documentSourcesMap = new HashMap<String, Class<? extends ISEPADocumentSource>>();

	@Override
	public ISEPADocumentSourceQuery createSEPADocumentSourceQuery()
	{
		return new SEPADocumentSourceQuery();
	}

	@Override
	public ISEPADocumentSource createSEPADocumentSource(final String sourceTableName)
	{
		Check.assumeNotEmpty(sourceTableName, "sourceTableName not empty");
		final Class<? extends ISEPADocumentSource> sourceClass = documentSourcesMap.get(sourceTableName);
		if (sourceClass == null)
		{
			throw new AdempiereException("No " + ISEPADocumentSource.class + " implementation was found for " + sourceTableName);
		}

		final ISEPADocumentSource source;
		try
		{
			source = sourceClass.newInstance();
		}
		catch (Exception e)
		{
			throw new AdempiereException("Cannot instantiate " + sourceClass, e);
		}
		
		return source;
	}

	@Override
	public void registerSEPADocumentSource(final String sourceTableName, Class<? extends ISEPADocumentSource> sourceClass)
	{
		Check.assumeNotEmpty(sourceTableName, "sourceTableName not empty");
		Check.assumeNotNull(sourceClass, "sourceClass not null");

		documentSourcesMap.put(sourceTableName, sourceClass);
	}

}
