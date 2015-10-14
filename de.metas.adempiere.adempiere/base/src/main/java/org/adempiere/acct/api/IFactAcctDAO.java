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


import java.util.List;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_Fact_Acct;

public interface IFactAcctDAO extends ISingletonService
{
	/**
	 * Deletes all accounting records for given document.
	 * 
	 * NOTE: this method is NOT checking if the accounting period of given document is open!
	 *
	 * @param document
	 * @return how many {@link I_Fact_Acct} were deleted
	 */
	int deleteForDocument(Object document);

	/**
	 * Retries all accounting records for given document.
	 *
	 * @param document
	 * @return
	 */
	List<I_Fact_Acct> retrieveForDocument(Object document);

	/**
	 * Retries all accounting records for given document.
	 * 
	 * @param document
	 * @return query
	 */
	IQueryBuilder<I_Fact_Acct> retrieveQueryForDocument(Object document);

	/**
	 * Retries all accounting records for given document line.
	 *
	 * @param tableName
	 * @param recordId
	 * @param documentLine
	 * @return
	 */
	List<I_Fact_Acct> retrieveForDocumentLine(String tableName, int recordId, Object documentLine);

	/**
	 * Update directly all the fact accounts of the given document by setting their docStatus with the given docStatus
	 * 
	 * @param document
	 * @param docStatus
	 * @task http://dewiki908/mediawiki/index.php/09243_Stornobuchungen_ausblenden_%28Liste%2C_Konteninfo%29
	 */
	void updateDocStatusForDocument(Object document, String docStatus);
}
