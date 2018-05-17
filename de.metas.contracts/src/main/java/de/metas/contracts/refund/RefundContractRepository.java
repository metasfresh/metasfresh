package de.metas.contracts.refund;

import java.sql.Timestamp;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util.ArrayKey;
import org.springframework.stereotype.Repository;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.document.engine.IDocument;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class RefundContractRepository
{
	private static final CCache<ArrayKey, Integer> CACHE = CCache.<ArrayKey, Integer> newCache(
			I_C_Flatrate_Term.Table_Name + "#by"
					+ I_C_Flatrate_Term.COLUMNNAME_Type_Conditions + "#"
					+ I_C_Flatrate_Term.COLUMNNAME_DocStatus + "#"
					+ I_C_Flatrate_Term.COLUMNNAME_StartDate + "#"
					+ I_C_Flatrate_Term.COLUMNNAME_EndDate + "#"
					+ I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID + "#"
					+ I_C_Flatrate_Term.COLUMNNAME_M_Product_ID,
			0,
			CCache.EXPIREMINUTES_Never);

	public Optional<FlatrateTermId> getMatchingIdByInvoiceCandidate(@NonNull final AssignableInvoiceCandidate invoiceCandidate)
	{
		final Timestamp invoicableFromTimestamp = TimeUtil.asTimestamp(invoiceCandidate.getInvoiceableFrom());
		final int billPartnerId = invoiceCandidate.getBpartnerId().getRepoId();
		final int productId = invoiceCandidate.getProductId().getRepoId();

		final ArrayKey key = ArrayKey.of(invoicableFromTimestamp, billPartnerId, productId);
		final int contractRecordId = CACHE.getOrLoad(
				key,
				() -> retrieveIdNoCache(invoicableFromTimestamp, billPartnerId, productId));

		if (contractRecordId > 0)
		{
			return Optional.of(FlatrateTermId.ofRepoId(contractRecordId));
		}
		return Optional.empty();
	}

	private int retrieveIdNoCache(final Timestamp invoicableFromTimestamp, final int billPartnerId, final int productId)
	{
		final int contractRecordId = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_Type_Conditions, X_C_Flatrate_Term.TYPE_CONDITIONS_Refund)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_DocStatus, IDocument.STATUS_Completed)
				.addCompareFilter(I_C_Flatrate_Term.COLUMN_StartDate, Operator.LESS_OR_EQUAL, invoicableFromTimestamp)
				.addCompareFilter(I_C_Flatrate_Term.COLUMN_EndDate, Operator.GREATER_OR_EQUAL, invoicableFromTimestamp)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_Bill_BPartner_ID, billPartnerId)
				.addInArrayFilter(I_C_Flatrate_Term.COLUMN_M_Product_ID, null, productId)
				.orderBy()
				.addColumn(I_C_Flatrate_Term.COLUMNNAME_M_Product_ID, IQueryOrderBy.Direction.Descending, IQueryOrderBy.Nulls.Last)
				.endOrderBy()
				.create()
				.firstId();
		return contractRecordId;
	}
}
