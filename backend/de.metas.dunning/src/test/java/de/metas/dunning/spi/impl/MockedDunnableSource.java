package de.metas.dunning.spi.impl;

/*
 * #%L
 * de.metas.dunning
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

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.dunning.api.IDunnableDoc;
import de.metas.dunning.api.IDunningContext;

public class MockedDunnableSource extends AbstractDunnableSource
{
	private final List<IDunnableDoc> documents = new ArrayList<IDunnableDoc>();

	/**
	 * Gets a live list of dunnable documents. Test writer can add the {@link IDunnableDoc}s directly to this list.
	 * 
	 * @return
	 */
	public List<IDunnableDoc> getDunnableDocList()
	{
		return documents;
	}

	@Override
	protected Iterator<IDunnableDoc> createRawSourceIterator(IDunningContext context)
	{
		final Iterator<IDunnableDoc> it = new ArrayList<IDunnableDoc>(documents).iterator();

		// we are mocking it because we want to validate if the iterator will be closed
		return MockedCloseableIterator.mock(it);
	}

	@Override
	public String getSourceTableName()
	{
		return I_C_Invoice.Table_Name;
	}
}
