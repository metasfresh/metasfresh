package de.metas.acct.posting.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import de.metas.acct.posting.IDocumentRepostingSupplierService;
import de.metas.acct.spi.IDocumentRepostingSupplier;
import de.metas.acct.spi.impl.CompositeDocumentRepostingSupplier;
import de.metas.document.engine.IDocument;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class DocumentRepostingSupplierService implements IDocumentRepostingSupplierService
{
	private final CompositeDocumentRepostingSupplier repostingSuppliers = new CompositeDocumentRepostingSupplier();

	@Override
	public void registerSupplier(final IDocumentRepostingSupplier supplier)
	{
		repostingSuppliers.addSupplier(supplier);
	}

	@Override
	public List<IDocument> retrievePostedWithoutFactAcct (final Properties ctx, final Timestamp startTime)
	{
		return repostingSuppliers.retrievePostedWithoutFactAcct(ctx, startTime);
	}
}
