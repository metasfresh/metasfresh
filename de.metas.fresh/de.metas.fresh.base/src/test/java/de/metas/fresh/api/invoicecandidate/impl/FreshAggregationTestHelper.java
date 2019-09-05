package de.metas.fresh.api.invoicecandidate.impl;

/*
 * #%L
 * de.metas.fresh.base
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

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.fresh.invoicecandidate.spi.impl.FreshQuantityDiscountAggregator;
import de.metas.invoicecandidate.api.IAggregationDAO;
import de.metas.invoicecandidate.api.impl.PlainAggregationDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.util.Services;

public class FreshAggregationTestHelper
{
	private FreshAggregationTestHelper()
	{
	}

	/* package */static I_C_Invoice_Candidate_Agg configFreshAggregator(final Properties ctx, final String trxName)
	{
		final I_C_Invoice_Candidate_Agg freshAgg = InterfaceWrapperHelper.create(ctx, I_C_Invoice_Candidate_Agg.class, trxName);
		freshAgg.setAD_Org_ID(0);
		freshAgg.setSeqNo(0);
		freshAgg.setName(FreshQuantityDiscountAggregator.class.getSimpleName());
		freshAgg.setClassname(FreshQuantityDiscountAggregator.class.getName());
		freshAgg.setIsActive(true);
		freshAgg.setC_BPartner_ID(0);
		freshAgg.setM_ProductGroup(null);
		InterfaceWrapperHelper.save(freshAgg);

		final PlainAggregationDAO aggregationDAO = (PlainAggregationDAO)Services.get(IAggregationDAO.class);
		aggregationDAO.setDefaultAgg(freshAgg);

		return freshAgg;
	}

}
