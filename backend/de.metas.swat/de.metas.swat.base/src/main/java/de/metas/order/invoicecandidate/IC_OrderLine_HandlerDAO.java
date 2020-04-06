package de.metas.order.invoicecandidate;

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

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.I_C_OrderLine;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.ISingletonService;

/**
 * DAO to be used for {@link C_OrderLine_Handler}.
 *
 * @author tsa
 *
 */
public interface IC_OrderLine_HandlerDAO extends ISingletonService
{

	/**
	 * Fetch {@link I_C_OrderLine}s which do not have an {@link I_C_Invoice_Candidate} already created.
	 */
	IQueryBuilder<I_C_OrderLine> retrieveMissingOrderLinesQuery(Properties ctx, String trxName);

	/**
	 * Add additional filters to allow other modules restricting the set of order lines for which the system automatically creates invoice candidates.
	 */
	void addAdditionalFilter(IQueryFilter<I_C_OrderLine> filter);

	/**
	 * @return additional order line filters
	 */
	IQueryFilter<I_C_OrderLine> getAdditionalFilters();
}
