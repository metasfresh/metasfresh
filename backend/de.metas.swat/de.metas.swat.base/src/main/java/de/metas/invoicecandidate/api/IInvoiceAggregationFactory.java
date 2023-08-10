package de.metas.invoicecandidate.api;

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
import de.metas.aggregation.api.IAggregationFactory;
import de.metas.aggregation.api.IAggregationKeyBuilder;
import de.metas.aggregation.model.X_C_Aggregation;
import de.metas.invoicecandidate.model.I_C_BPartner;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.ISingletonService;

import java.util.Properties;

/**
 * It's an extension of {@link IAggregationFactory} but addressing invoicing concerns.
 *
 * @author tsa
 *
 */
public interface IInvoiceAggregationFactory extends ISingletonService
{
	/**
	 * Gets the header or line aggregation key builder for aggregating invoice candidates.
	 *
	 * NOTE: if the BPartner has no aggregation configured this method will use the default aggregation in the system.
	 *
	 * @param ctx
	 * @param bpartner
	 * @param isSOTrx
	 * @param aggregationUsageLevel {@link X_C_Aggregation#AGGREGATIONUSAGELEVEL_Header} or {@link X_C_Aggregation#AGGREGATIONUSAGELEVEL_Line}
	 * @return header aggregation key builder; never returns <code>null</code>
	 */
	IAggregationKeyBuilder<I_C_Invoice_Candidate> getAggregationKeyBuilder(Properties ctx, I_C_BPartner bpartner, boolean isSOTrx, String aggregationUsageLevel);

	/**
	 * Gets the header/line aggregation to be used for aggregation invoice candidates.
	 *
	 * NOTE: if the BPartner has no aggregation configured this method will use the default aggregation in the system.
	 *
	 * @param ctx
	 * @param bpartner
	 * @param isSOTrx
	 * @param aggregationUsageLevel {@link X_C_Aggregation#AGGREGATIONUSAGELEVEL_Header} or {@link X_C_Aggregation#AGGREGATIONUSAGELEVEL_Line}
	 * @return header/line aggregation; never returns <code>null</code>.
	 */
	Aggregation getAggregation(Properties ctx, I_C_BPartner bpartner, boolean isSOTrx, final String aggregationUsageLevel);

	/**
	 * Gets the header aggregation to be used when aggregating invoice candidates which have as source a prepay order
	 *
	 * @param ctx
	 * @return
	 */
	IAggregationKeyBuilder<I_C_Invoice_Candidate> getPrepayOrderAggregationKeyBuilder(Properties ctx);

	/**
	 * Gets the aggregation to be used when aggregating invoice candidates which have as source an issue
	 */
	IAggregationKeyBuilder<I_C_Invoice_Candidate> getIssueAggregationKeyBuilder(Properties ctx, String aggregationUsageLevel);


	/**
	 * Gets the header aggregation to be used when aggregating invoice candidates which have harvesting details
	 */
	IAggregationKeyBuilder<I_C_Invoice_Candidate> getHarvestingAggregationKeyBuilder(Properties ctx);
}
