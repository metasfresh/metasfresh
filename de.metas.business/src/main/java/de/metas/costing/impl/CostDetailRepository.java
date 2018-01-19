package de.metas.costing.impl;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_CostDetail;
import org.slf4j.Logger;

import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostDetailRepository;
import de.metas.logging.LogManager;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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

public class CostDetailRepository implements ICostDetailRepository
{
	private static final Logger logger = LogManager.getLogger(CostDetailRepository.class);

	@Override
	public void save(final I_M_CostDetail costDetail)
	{
		InterfaceWrapperHelper.save(costDetail);
	}

	@Override
	public void delete(final I_M_CostDetail costDetail)
	{
		costDetail.setProcessed(false);
		InterfaceWrapperHelper.delete(costDetail);
	}

	@Override
	public void delete(@NonNull final CostDetailQuery query)
	{
		createQueryBuilder(query)
				.create()
				.list(I_M_CostDetail.class)
				.forEach(this::delete);
	}

	@Override
	public void deleteUnprocessedWithNoChanges(@NonNull final CostDetailQuery query)
	{
		final int countDeleted = createQueryBuilder(query)
				.addEqualsFilter(I_M_CostDetail.COLUMN_Processed, false)
				.addInArrayFilter(I_M_CostDetail.COLUMN_DeltaAmt, null, BigDecimal.ZERO)
				.addInArrayFilter(I_M_CostDetail.COLUMN_DeltaQty, null, BigDecimal.ZERO)
				.create()
				.deleteDirectly();
		if (countDeleted > 0)
		{
			logger.debug("Deleted {} not processed cost details for {}", countDeleted, query);
		}
	}

	@Override
	public I_M_CostDetail getCostDetailOrNull(@NonNull final CostDetailQuery query)
	{
		return createQueryBuilder(query)
				.create()
				.firstOnly(I_M_CostDetail.class);
	}

	public BigDecimal getCostDetailAmt(@NonNull final CostDetailQuery query)
	{
		final I_M_CostDetail costDetail = getCostDetailOrNull(query);
		return costDetail != null ? costDetail.getAmt() : BigDecimal.ZERO;
	}

	private IQueryBuilder<I_M_CostDetail> createQueryBuilder(@NonNull final CostDetailQuery query)
	{
		final CostingDocumentRef documentRef = query.getDocumentRef();

		final IQueryBuilder<I_M_CostDetail> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_CostDetail.class)
				.addEqualsFilter(I_M_CostDetail.COLUMN_C_AcctSchema_ID, query.getAcctSchemaId())
				.addEqualsFilter(documentRef.getCostDetailColumnName(), documentRef.getRecordId());

		if (query.getAttributeSetInstanceId() > 0)
		{
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMN_M_AttributeSetInstance_ID, query.getAttributeSetInstanceId());
		}
		else
		{
			queryBuilder.addInArrayFilter(I_M_CostDetail.COLUMN_M_AttributeSetInstance_ID, null, 0);
		}

		if (documentRef.getOutboundTrx() != null)
		{
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMN_IsSOTrx, documentRef.getOutboundTrx());
		}

		return queryBuilder;
	}

	@Override
	public List<I_M_CostDetail> getAllForDocument(@NonNull final CostingDocumentRef documentRef)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_CostDetail.class)
				.addEqualsFilter(documentRef.getCostDetailColumnName(), documentRef.getRecordId())
				.orderBy(I_M_CostDetail.COLUMN_M_CostDetail_ID)
				.create()
				.list(I_M_CostDetail.class);
	}

	@Override
	public boolean hasCostDetailsForProductId(final int productId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_CostDetail.class)
				.addEqualsFilter(I_M_CostDetail.COLUMN_M_Product_ID, productId)
				.create()
				.match();
	}
}
