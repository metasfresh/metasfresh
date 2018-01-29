package org.adempiere.acct.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.acct.Doc;
import org.compiere.acct.PostingExecutionException;
import org.compiere.model.MAcctSchema;

/**
 * Accountable document factory. Use this interface to create the right {@link Doc} instance for your accountable document.
 * 
 * @author tsa
 *
 */
public interface IDocFactory extends ISingletonService
{
	/**
	 * Create Accountable document
	 *
	 * @param ass accounting schemas
	 * @param AD_Table_ID document's AD_Table_ID
	 * @param Record_ID document's ID
	 * @param trxName transaction name
	 * @return Document or <code>null</code> if there is no such accountable document for given AD_Table_ID/Record_ID
	 * @throws PostingExecutionException if the document could not be created
	 */
	Doc<?> getOrNull(final Properties ctx, MAcctSchema[] ass, int AD_Table_ID, int Record_ID, String trxName);

	/**
	 * Create Accountable document
	 * 
	 * @param docMetaInfo accountable document descriptor
	 * @param ass accounting schemas
	 * @param rs result set
	 * @param trxName
	 * @return Document; never returns <code>null</code>
	 * @throws PostingExecutionException if the document could not be created
	 */
	Doc<?> get(final Properties ctx, IDocMetaInfo docMetaInfo, MAcctSchema[] ass, ResultSet rs, String trxName);

	/**
	 * 
	 * @return a list of all accountable documents (meta data), registered on system
	 */
	List<IDocMetaInfo> getDocMetaInfoList();
}
