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
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_Fact_Acct;

import de.metas.document.engine.IDocument;

public interface IFactAcctDAO extends ISingletonService
{
	String DB_SCHEMA = "de_metas_acct";
	/** Function used to calculate ending balance for a given {@link I_Fact_Acct} line. */
	String DB_FUNC_Fact_Acct_EndingBalance = DB_SCHEMA + ".Fact_Acct_EndingBalance";

	/**
	 * Deletes all accounting records for given document.
	 * 
	 * NOTE: this method is NOT checking if the accounting period of given document is open!
	 *
	 * @param document
	 * @return how many {@link I_Fact_Acct} were deleted
	 */
	int deleteForDocument(IDocument document);
	
	int deleteForDocumentModel(final Object documentObj);

	/**
	 * Retries all accounting records for given document.
	 * 
	 * @param document
	 * @return query
	 */
	IQueryBuilder<I_Fact_Acct> retrieveQueryForDocument(IDocument document);

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
	 * Update directly all the fact accounts of the given document by setting their docStatus from document.
	 * 
	 * @param document
	 * @task http://dewiki908/mediawiki/index.php/09243_Stornobuchungen_ausblenden_%28Liste%2C_Konteninfo%29
	 */
	void updateDocStatusForDocument(IDocument document);

	/**
	 * Update directly all {@link I_Fact_Acct} records for given document line and sets the given activity.
	 * 
	 * @param ctx
	 * @param adTableId document header's AD_Table_ID
	 * @param recordId document header's ID
	 * @param lineId document line's ID
	 * @param activityId activity to set
	 * @return how many {@link I_Fact_Acct} records were updated
	 */
	int updateActivityForDocumentLine(Properties ctx, int adTableId, int recordId, int lineId, int activityId);
}
