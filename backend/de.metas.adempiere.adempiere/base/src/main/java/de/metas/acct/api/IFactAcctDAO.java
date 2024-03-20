package de.metas.acct.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableSet;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.document.engine.IDocument;
import de.metas.util.ISingletonService;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.document.engine.IDocument;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_Fact_Acct;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;
import java.time.Instant;
import java.util.List;
import java.util.Properties;

public interface IFactAcctDAO extends ISingletonService
{
	String DB_SCHEMA = "de_metas_acct";
	/**
	 * Function used to calculate ending balance for a given {@link I_Fact_Acct} line.
	 */
	String DB_FUNC_Fact_Acct_EndingBalance = DB_SCHEMA + ".Fact_Acct_EndingBalance";

	I_Fact_Acct getById(int factAcctId);

	void save(I_Fact_Acct factAcct);

	/**
	 * Deletes all accounting records for given document.
	 * <p>
	 * NOTE: this method is NOT checking if the accounting period of given document is open!
	 */
	void deleteForDocument(IDocument document);

	void deleteForDocumentModel(final Object documentObj);

	void deleteForRecordRef(@NonNull TableRecordReference recordRef);

	/**
	 * Retries all accounting records for given document.
	 *
	 * @return query
	 */
	IQueryBuilder<I_Fact_Acct> retrieveQueryForDocument(IDocument document);

	/**
	 * Retries all accounting records for given document line.
	 */
	List<I_Fact_Acct> retrieveForDocumentLine(String tableName, int recordId, Object documentLine);

	/**
	 * Update directly all the fact accounts of the given document by setting their docStatus from document.
	 *
	 * @implSpec <a href="http://dewiki908/mediawiki/index.php/09243_Stornobuchungen_ausblenden_%28Liste%2C_Konteninfo%29">task</a>
	 */
	void updateDocStatusForDocument(IDocument document);

	List<ElementValueId> retrieveAccountsForTimeFrame(@NonNull AcctSchemaId acctSchemaId, @NonNull Instant dateAcctFrom, @NonNull Instant dateAcctTo);

	/**
	 * Update directly all {@link I_Fact_Acct} records for given document line and sets the given activity.
	 *
	 * @param adTableId  document header's AD_Table_ID
	 * @param recordId   document header's ID
	 * @param lineId     document line's ID
	 * @param activityId activity to set
	 */
	void updateActivityForDocumentLine(Properties ctx, int adTableId, int recordId, int lineId, int activityId);

	void updatePOReference(@NonNull TableRecordReference recordRef, @Nullable String poReference);

	List<I_Fact_Acct> list(@NonNull List<FactAcctQuery> queries);

	List<I_Fact_Acct> list(@NonNull FactAcctQuery query);

	ImmutableSet<FactAcctId> listIds(@NonNull FactAcctQuery query);

	Stream<I_Fact_Acct> stream(@NonNull FactAcctQuery query);

	void setOpenItemTrxInfo(@NonNull FAOpenItemTrxInfo openItemTrxInfo, @NonNull FactAcctQuery query);
}
