package de.metas.payment.sepa.sepamarshaller;

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


import java.util.Iterator;
import java.util.Properties;

import de.metas.payment.sepa.api.ISEPADocument;
import de.metas.payment.sepa.api.ISEPADocumentSourceQuery;

/**
 * Part of a generic framework that can generate generic {@link ISEPADocument}s from arbitrary sources. The idat was probably that those <code>ISEPADocument</code>s then can be further processed.
 * <p>
 * <b>Currently unused</b>, but the whole thing will be very useful when there are more use cases than just export
 * 
 * @author tsa
 *
 */
public interface ISEPADocumentSource
{
	/**
	 * Uses the given <code>query</code> to retrieve document source records and create sepa documents.
	 * 
	 * @param ctx
	 * @param query
	 * @param trxName
	 * @return
	 */
	Iterator<ISEPADocument> iterate(Properties ctx, final ISEPADocumentSourceQuery query, String trxName);

	String getSourceTableName();
}
