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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.aggregation.api.Aggregation;
import de.metas.aggregation.api.AggregationId;
import de.metas.aggregation.api.IAggregationDAO;
import de.metas.aggregation.api.IAggregationFactory;
import de.metas.aggregation.api.IAggregationKeyBuilder;
import de.metas.aggregation.model.X_C_Aggregation;
import de.metas.invoicecandidate.api.IInvoiceAggregationFactory;
import de.metas.invoicecandidate.model.I_C_BPartner;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;

import javax.annotation.Nullable;
import java.util.Properties;

public class InvoiceAggregationFactory implements IInvoiceAggregationFactory
{
	private final static String INVOICE_AGGREGATION_ID_FOR_PREPAYORDER = "InvoiceAggregationFactory_PrepayOrderAggregationId";
	private final static String INVOICE_AGGREGATION_ID_FOR_ISSUE = "InvoiceAggregationFactory_IssueAggregationId";
	private final static String INVOICE_LINE_AGGREGATION_ID_FOR_ISSUE = "InvoiceLineAggregationFactory_IssueAggregationId";
	private final static String INVOICE_AGGREGATION_ID_FOR_HARVESTING_DETAILS = "InvoiceAggregationFactory_HarvestingAggregationId";

	private final IAggregationFactory aggregationFactory = Services.get(IAggregationFactory.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Override
	public IAggregationKeyBuilder<I_C_Invoice_Candidate> getAggregationKeyBuilder(final Properties ctx, final I_C_BPartner bpartner, final boolean isSOTrx, final String aggregationUsageLevel)
	{
		final int aggregationId = getInvoice_Aggregation_ID(bpartner, isSOTrx, aggregationUsageLevel);

		final IAggregationKeyBuilder<I_C_Invoice_Candidate> aggregation;
		if (aggregationId > 0)
		{
			aggregation = aggregationFactory.getAggregationKeyBuilder(ctx, I_C_Invoice_Candidate.class, aggregationId);
		}
		else
		{
			aggregation = aggregationFactory.getDefaultAggregationKeyBuilder(ctx, I_C_Invoice_Candidate.class, isSOTrx, aggregationUsageLevel);
		}
		return aggregation;
	}

	private int getInvoice_Aggregation_ID(final I_C_BPartner bpartner, final boolean isSOTrx, final String aggregationUsageLevel)
	{
		final int aggregationId;
		if (X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header.equals(aggregationUsageLevel))
		{
			aggregationId = isSOTrx ? bpartner.getSO_Invoice_Aggregation_ID() : bpartner.getPO_Invoice_Aggregation_ID();
		}
		else if (X_C_Aggregation.AGGREGATIONUSAGELEVEL_Line.equals(aggregationUsageLevel))
		{
			aggregationId = isSOTrx ? bpartner.getSO_InvoiceLine_Aggregation_ID() : bpartner.getPO_InvoiceLine_Aggregation_ID();
		}
		else
		{
			throw new IllegalArgumentException("Unknown AggregationUsageLevel: " + aggregationUsageLevel);
		}
		return aggregationId <= 0 ? -1 : aggregationId;
	}

	@Override
	public Aggregation getAggregation(final Properties ctx, final I_C_BPartner bpartner, final boolean isSOTrx, final String aggregationUsageLevel)
	{
		final IAggregationDAO aggregationDAO = Services.get(IAggregationDAO.class);
		final int aggregationId = getInvoice_Aggregation_ID(bpartner, isSOTrx, aggregationUsageLevel);

		final Aggregation aggregation;
		if (aggregationId > 0)
		{
			aggregation = aggregationDAO.retrieveAggregation(ctx, aggregationId);
		}
		else
		{
			aggregation = aggregationDAO.retrieveDefaultAggregationOrNull(ctx, I_C_Invoice_Candidate.class, isSOTrx, aggregationUsageLevel);
			if (aggregation == null)
			{
				throw new AdempiereException("@NotFound@ @C_Aggregation_ID@ (@IsDefault@=@Y@)"
						+ "\n @C_BPartner_ID@: " + bpartner
						+ "\n @IsSOTrx@: @" + isSOTrx + "@"
						+ "\n @AggregationUsageLevel@: " + aggregationUsageLevel
						+ "\n @TableName@: " + I_C_Invoice_Candidate.Table_Name);
			}
		}
		return aggregation;
	}

	@Override
	public IAggregationKeyBuilder<I_C_Invoice_Candidate> getPrepayOrderAggregationKeyBuilder(final Properties ctx)
	{
		final int aggregationId = getCustom_InvoiceCandidate_Aggregation_ID(INVOICE_AGGREGATION_ID_FOR_PREPAYORDER);

		final IAggregationKeyBuilder<I_C_Invoice_Candidate> aggregation;
		if (aggregationId > 0)
		{
			aggregation = aggregationFactory.getAggregationKeyBuilder(ctx, I_C_Invoice_Candidate.class, aggregationId);
		}
		else
		{
			aggregation = aggregationFactory.getDefaultAggregationKeyBuilder(ctx, I_C_Invoice_Candidate.class, true, X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header);
		}
		return aggregation;
	}

	@Override
	public IAggregationKeyBuilder<I_C_Invoice_Candidate> getIssueAggregationKeyBuilder(
			@NonNull final Properties ctx,
			@NonNull final String aggregationUsageLevel)
	{
		final String customAggregationConfig = aggregationUsageLevel.equals(X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header)
				? INVOICE_AGGREGATION_ID_FOR_ISSUE
				: INVOICE_LINE_AGGREGATION_ID_FOR_ISSUE;

		final int effortControlAggregationId = getCustom_InvoiceCandidate_Aggregation_ID(customAggregationConfig);

		return effortControlAggregationId > 0
				? aggregationFactory.getAggregationKeyBuilder(ctx, I_C_Invoice_Candidate.class, effortControlAggregationId)
				: aggregationFactory.getDefaultAggregationKeyBuilder(ctx, I_C_Invoice_Candidate.class, true, aggregationUsageLevel);
	}

	@Nullable
	@Override
	public IAggregationKeyBuilder<I_C_Invoice_Candidate> getHarvestingAggregationKeyBuilder(@NonNull final Properties ctx)
	{
		final AggregationId aggregationId = AggregationId.ofRepoIdOrNull(getCustom_InvoiceCandidate_Aggregation_ID(INVOICE_AGGREGATION_ID_FOR_HARVESTING_DETAILS));

		if (aggregationId != null)
		{
			return aggregationFactory.getAggregationKeyBuilder(ctx, I_C_Invoice_Candidate.class, aggregationId.getRepoId());
		}

		return null;
	}

	private int getCustom_InvoiceCandidate_Aggregation_ID(@NonNull final String customAggregationConfig)
	{
		return sysConfigBL.getIntValue(customAggregationConfig, -1);
	}
}
