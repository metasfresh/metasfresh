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


import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;

import de.metas.aggregation.api.Aggregation;
import de.metas.aggregation.api.IAggregationDAO;
import de.metas.aggregation.api.IAggregationFactory;
import de.metas.aggregation.api.IAggregationKeyBuilder;
import de.metas.aggregation.model.X_C_Aggregation;
import de.metas.invoicecandidate.api.IInvoiceAggregationFactory;
import de.metas.invoicecandidate.model.I_C_BPartner;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;

public class InvoiceAggregationFactory implements IInvoiceAggregationFactory
{
	private final static String INVOICE_AGGREGATION_ID_FOR_PREPAYORDER = "InvoiceAggregationFactory_PrepayOrderAggregationId";

	@Override
	public IAggregationKeyBuilder<I_C_Invoice_Candidate> getAggregationKeyBuilder(final Properties ctx, final I_C_BPartner bpartner, final boolean isSOTrx, final String aggregationUsageLevel)
	{
		final IAggregationFactory aggregationFactory = Services.get(IAggregationFactory.class);

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
		final IAggregationFactory aggregationFactory = Services.get(IAggregationFactory.class);

		final int aggregationId = getPrepayOrder_Invoice_Aggregation_ID();

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

	private int getPrepayOrder_Invoice_Aggregation_ID()
	{
		return Services.get(ISysConfigBL.class).getIntValue(INVOICE_AGGREGATION_ID_FOR_PREPAYORDER, -1);
	}

}
