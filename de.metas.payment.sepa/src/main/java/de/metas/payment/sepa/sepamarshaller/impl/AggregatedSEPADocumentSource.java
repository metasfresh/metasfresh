package de.metas.payment.sepa.sepamarshaller.impl;

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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.collections.IteratorChain;

import de.metas.payment.sepa.api.ISEPADocument;
import de.metas.payment.sepa.api.ISEPADocumentSourceQuery;
import de.metas.payment.sepa.sepamarshaller.ISEPADocumentSource;

public class AggregatedSEPADocumentSource implements ISEPADocumentSource
{
	
	final List<ISEPADocumentSource> sources = new ArrayList<ISEPADocumentSource>();
	
	public void addDocumentSource(ISEPADocumentSource source)
	{
		if (!sources.contains(source))
		{
			sources.add(source);
		}
	}
	
	@Override
	public Iterator<ISEPADocument> iterate(Properties ctx, ISEPADocumentSourceQuery query, String trxName)
	{
		final IteratorChain<ISEPADocument> chain = new IteratorChain<ISEPADocument>();
		
		for (ISEPADocumentSource source : sources)
		{
			chain.addIterator(source.iterate(ctx, query, trxName));
		}
		
		return chain;
	}

	@Override
	public String getSourceTableName()
	{
		// Aggregated source does not point to one table.
		return null;
	}

}
