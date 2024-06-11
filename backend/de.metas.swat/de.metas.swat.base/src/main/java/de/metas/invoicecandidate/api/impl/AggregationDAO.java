package de.metas.invoicecandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.adempiere.model.I_M_Product;
import de.metas.invoicecandidate.api.IAggregationDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation;
import de.metas.invoicecandidate.model.I_M_ProductGroup;
import de.metas.invoicecandidate.model.I_M_ProductGroup_Product;
import de.metas.logging.LogManager;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.DBUniqueConstraintException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.Query;
import org.slf4j.Logger;

import java.util.Properties;

public class AggregationDAO implements IAggregationDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final Logger logger = LogManager.getLogger(InvoiceCandBL.class);

	@Override
	public I_C_Invoice_Candidate_Agg retrieveAggregate(@NonNull final I_C_Invoice_Candidate ic)
	{
		final IProductDAO productDAO = Services.get(IProductDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
		final String trxName = InterfaceWrapperHelper.getTrxName(ic);

		final String wc = "true "
				+ " AND ("
				+ "  COALESCE(" + I_C_Invoice_Candidate_Agg.COLUMNNAME_AD_Org_ID + ",0)=0 OR "
				+ I_C_Invoice_Candidate_Agg.COLUMNNAME_AD_Org_ID + "=?"
				+ ") AND ("
				+ "  COALESCE(" + I_C_Invoice_Candidate_Agg.COLUMNNAME_C_BPartner_ID + ",0)=0 OR "
				+ I_C_Invoice_Candidate_Agg.COLUMNNAME_C_BPartner_ID + "=?"
				+ ") AND ("
				+ "  COALESCE(" + I_C_Invoice_Candidate_Agg.COLUMNNAME_M_ProductGroup_ID + ",0)=0 OR "
				+ I_C_Invoice_Candidate_Agg.COLUMNNAME_M_ProductGroup_ID + " IN ("
				+ "   select pg." + I_M_ProductGroup.COLUMNNAME_M_ProductGroup_ID
				+ "   from " + I_M_ProductGroup.Table_Name + " pg "
				+ "      join " + I_M_ProductGroup_Product.Table_Name + " pp on"
				+ "        pp." + I_M_ProductGroup_Product.COLUMNNAME_M_ProductGroup_ID + "=pg." + I_M_ProductGroup.COLUMNNAME_M_ProductGroup_ID
				+ "   where "
				+ "      pg." + I_M_ProductGroup.COLUMNNAME_IsActive + "='Y' AND "
				+ "      pp." + I_M_ProductGroup_Product.COLUMNNAME_IsActive + "='Y' AND "
				+ "      pp." + I_M_ProductGroup_Product.COLUMNNAME_M_Product_ID + "=? OR ("
				+ "         COALESCE(" + I_M_ProductGroup_Product.COLUMNNAME_M_Product_ID + ",0)=0 AND "
				+ "         " + I_M_ProductGroup_Product.COLUMNNAME_M_Product_Category_ID + "=?"
				+ "      )"
				+ "))";

		final String orderBy =
				// I_C_Invoice_Candidate_Agg.COLUMNNAME_C_BPartner_ID + " DESC";
				I_C_Invoice_Candidate_Agg.COLUMNNAME_SeqNo + ", "
						+ I_C_Invoice_Candidate_Agg.COLUMNNAME_AD_Org_ID + " DESC, "
						+ I_C_Invoice_Candidate_Agg.COLUMNNAME_C_Invoice_Candidate_Agg_ID;

		final I_M_Product product = productDAO.getById(ProductId.ofRepoId(ic.getM_Product_ID()), I_M_Product.class);

		return new Query(ctx, I_C_Invoice_Candidate_Agg.Table_Name, wc, trxName)
				.setParameters(
						ic.getAD_Org_ID(),
						ic.getBill_BPartner_ID(),
						product == null ? 0 : product.getM_Product_ID(),
						product == null ? 0 : product.getM_Product_Category_ID())
				.setOnlyActiveRecords(true)
				.setRequiredAccess(Access.READ)
				.setOrderBy(orderBy)
				.first(I_C_Invoice_Candidate_Agg.class);
	}

	@Override
	public int findC_Invoice_Candidate_HeaderAggregationKey_ID(@NonNull final I_C_Invoice_Candidate ic)
	{
		try
		{
			return findC_Invoice_Candidate_HeaderAggregationKey_ID0(ic);
		}
		catch (final DBUniqueConstraintException e)
		{
			final String msg = "Caught DBUniqueConstraintException while trying to get or create C_Invoice_Candidate_HeaderAggregationKey for C_Invoice_Candidate_ID=" + ic.getC_Invoice_Candidate_ID() + ". => Retrying";
			logger.info(msg, e);
			Loggables.get().addLog(msg);

			// retrying is not super-cool and we minimized the possibility for concurrent invocations, 
			// but I couldn't figure out how to get rid of them at de.metas.invoicecandidate.api.impl.InvoiceCandBL.handleCompleteForInvoice
			return findC_Invoice_Candidate_HeaderAggregationKey_ID0(ic);
		}
	}

	private int findC_Invoice_Candidate_HeaderAggregationKey_ID0(@NonNull final I_C_Invoice_Candidate ic)
	{
		final String headerAggregationKeyCalc = ic.getHeaderAggregationKey_Calc();
		if (Check.isBlank(headerAggregationKeyCalc))
		{
			return -1;
		}

		final int bpartnerId = ic.getBill_BPartner_ID();
		if (bpartnerId <= 0)
		{
			return -1;
		}

		//
		// Find existing header aggregation key ID
		final int headerAggregationKeyId = lookupHeaderAggregationKeyId(ic, headerAggregationKeyCalc);
		if (headerAggregationKeyId > 0)
		{
			return headerAggregationKeyId;
		}

		//
		// Create a new header aggregation key record and return it
		final I_C_Invoice_Candidate_HeaderAggregation headerAggregationKeyRecord = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate_HeaderAggregation.class, ic);
		headerAggregationKeyRecord.setHeaderAggregationKey(headerAggregationKeyCalc);
		headerAggregationKeyRecord.setHeaderAggregationKeyBuilder_ID(ic.getHeaderAggregationKeyBuilder_ID());
		headerAggregationKeyRecord.setC_BPartner_ID(bpartnerId);
		headerAggregationKeyRecord.setIsSOTrx(ic.isSOTrx());
		headerAggregationKeyRecord.setIsActive(true);
		return saveHeaderAggregationKeyWithLookupOnFail(headerAggregationKeyRecord, ic, headerAggregationKeyCalc);
	}

	private int saveHeaderAggregationKeyWithLookupOnFail(
			@NonNull final I_C_Invoice_Candidate_HeaderAggregation headerAggregationKeyRecord,
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final String headerAggregationKeyCalc)
	{
		try
		{
			InterfaceWrapperHelper.save(headerAggregationKeyRecord);
			return headerAggregationKeyRecord.getC_Invoice_Candidate_HeaderAggregation_ID();
		}
		catch (final DBUniqueConstraintException e)
		{
			final int lookupHeaderAggregationKeyId = lookupHeaderAggregationKeyId(ic, headerAggregationKeyCalc);

			if (lookupHeaderAggregationKeyId <= 0)
			{
				throw e;
			}
			return lookupHeaderAggregationKeyId;
		}
	}

	private int lookupHeaderAggregationKeyId(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final String headerAggregationKeyCalc)
	{
		return queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_HeaderAggregation.class, ic)
				.addEqualsFilter(I_C_Invoice_Candidate_HeaderAggregation.COLUMN_HeaderAggregationKey, headerAggregationKeyCalc)
				.create()
				.firstIdOnly();
	}
}
