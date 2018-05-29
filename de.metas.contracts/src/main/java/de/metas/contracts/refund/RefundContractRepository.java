package de.metas.contracts.refund;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.sql.Timestamp;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.PlainContextAware;
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
	private static final CCache<ArrayKey, I_C_Flatrate_Term> CACHE = //
			CCache.<ArrayKey, I_C_Flatrate_Term> newCache(
					I_C_Flatrate_Term.Table_Name + "#by"
							+ I_C_Flatrate_Term.COLUMNNAME_Type_Conditions + "#"
							+ I_C_Flatrate_Term.COLUMNNAME_DocStatus + "#"
							+ I_C_Flatrate_Term.COLUMNNAME_StartDate + "#"
							+ I_C_Flatrate_Term.COLUMNNAME_EndDate + "#"
							+ I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID + "#"
							+ I_C_Flatrate_Term.COLUMNNAME_M_Product_ID,
					0,
					CCache.EXPIREMINUTES_Never);

	private final RefundConfigRepository refundConfigRepository;

	public RefundContractRepository(@NonNull final RefundConfigRepository refundConfigRepository)
	{
		this.refundConfigRepository = refundConfigRepository;
	}

	public Optional<FlatrateTermId> getIdByQuery(@NonNull final RefundContractQuery query)
	{
		final I_C_Flatrate_Term contractRecord = retrieveRecordOrNull(query);

		if (contractRecord != null)
		{
			return Optional.of(FlatrateTermId.ofRepoId(contractRecord.getC_Flatrate_Term_ID()));
		}
		return Optional.empty();
	}

	private I_C_Flatrate_Term retrieveRecordOrNull(@NonNull final RefundContractQuery query)
	{
		final Timestamp invoicableFromTimestamp = TimeUtil.asTimestamp(query.getDate());
		final int billPartnerId = query.getBPartnerId().getRepoId();
		final int productId = query.getProductId().getRepoId();

		final ArrayKey key = ArrayKey.of(invoicableFromTimestamp, billPartnerId, productId);
		final I_C_Flatrate_Term contractRecord = CACHE.getOrLoad(
				key,
				() -> retrieveRecordForCache(invoicableFromTimestamp, billPartnerId, productId));
		return contractRecord;
	}

	private static I_C_Flatrate_Term retrieveRecordForCache(
			@NonNull final Timestamp invoicableFromTimestamp,
			final int billPartnerId,
			final int productId)
	{
		final I_C_Flatrate_Term contractRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_Term.class, PlainContextAware.newOutOfTrx())
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
				.first();
		return contractRecord;
	}

	public Optional<RefundContract> getByQuery(@NonNull final RefundContractQuery query)
	{
		return getIdByQuery(query).map(this::getById);
	}

	public RefundContract getById(@NonNull final FlatrateTermId flatrateTermId)
	{
		final I_C_Flatrate_Term contractRecord = loadOutOfTrx(flatrateTermId.getRepoId(), I_C_Flatrate_Term.class);

		return ofRecord(contractRecord);
	}

	public RefundContract ofRecord(@NonNull final I_C_Flatrate_Term contractRecord)
	{
		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(contractRecord.getC_Flatrate_Term_ID());
		final RefundConfig refundConfig = refundConfigRepository.getByRefundContractId(flatrateTermId);

		return RefundContract
				.builder()
				.id(flatrateTermId)
				.refundConfig(refundConfig)
				.startDate(TimeUtil.asLocalDate(contractRecord.getStartDate()))
				.endDate(TimeUtil.asLocalDate(contractRecord.getEndDate()))
				.build();
	}
}
