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


import de.metas.aggregation.api.IAggregationFactory;
import de.metas.aggregation.api.IAggregationKeyBuilder;
import de.metas.aggregation.model.X_C_Aggregation;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * Default Invoice LineAggregationKey builder for {@link I_C_Invoice_Candidate}s.
 *
 * This implementation gets the actual {@link IAggregationKeyBuilder} from {@link IAggregationFactory} using the settings from {@link I_C_Invoice_Candidate}, and delegates all the work to it.
 */
public class LineAggregationKeyBuilder extends ForwardingICAggregationKeyBuilder
{
	public LineAggregationKeyBuilder()
	{
		super(X_C_Aggregation.AGGREGATIONUSAGELEVEL_Line);
	}
}
