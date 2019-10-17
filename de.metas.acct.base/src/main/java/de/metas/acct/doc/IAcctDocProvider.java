package de.metas.acct.doc;

import java.util.List;
import java.util.Set;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.Doc;
import org.compiere.acct.PostingExecutionException;

import de.metas.acct.api.AcctSchema;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Provides accountable document instances.
 * 
 * Implementations of this interface, if annotated with @Component will be automatically discovered.
 * 
 * Instead of implementing this interface, consider extending {@link AcctDocProviderTemplate}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IAcctDocProvider
{
	/** @return all accountable docs table names which are handled by this provider */
	Set<String> getDocTableNames();

	/**
	 * @return Document or <code>null</code> if there is no such accountable document for given AD_Table_ID/Record_ID
	 * @throws PostingExecutionException if the document could not be created
	 */
	Doc<?> getOrNull(List<AcctSchema> acctSchemas, TableRecordReference documentRef);
}
