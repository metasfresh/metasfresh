package de.metas.invoicecandidate.facet.impl;

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

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.facet.IFacet;
import org.adempiere.facet.impl.Facet;
import org.adempiere.facet.impl.FacetCategory;
import org.adempiere.facet.impl.SingleFacetCategoryCollectorTemplate;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.util.Env;

import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * Miscellaneous {@link I_C_Invoice_Candidate} facets.
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08602_Rechnungsdispo_UI_%28106621797084%29
 */
public class C_Invoice_Candidate_Misc_FacetCollector extends SingleFacetCategoryCollectorTemplate<I_C_Invoice_Candidate>
{
	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private static final String MSG_FacetCategory_Misc = "org.adempiere.facet.IFacetCategory.Misc";
	private final IFacet<I_C_Invoice_Candidate> facetApprovalForInvoicing;

	public C_Invoice_Candidate_Misc_FacetCollector()
	{
		super(FacetCategory.builder()
				.setDisplayNameAndTranslate(MSG_FacetCategory_Misc)
				.build());

		facetApprovalForInvoicing = Facet.<I_C_Invoice_Candidate> builder()
				.setFacetCategory(getFacetCategory())
				.setDisplayName(msgBL.translate(Env.getCtx(), I_C_Invoice_Candidate.COLUMNNAME_ApprovalForInvoicing))
				.setFilter(new TypedSqlQueryFilter<I_C_Invoice_Candidate>(I_C_Invoice_Candidate.COLUMNNAME_ApprovalForInvoicing + "='Y'"))
				.build();

	}

	@Override
	protected List<IFacet<I_C_Invoice_Candidate>> collectFacets(IQueryBuilder<I_C_Invoice_Candidate> queryBuilder)
	{
		// FRESH-560: Add default filter
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilderWithDefaultFilters = Services.get(IInvoiceCandDAO.class).applyDefaultFilter(queryBuilder);

		final List<IFacet<I_C_Invoice_Candidate>> facets = new ArrayList<>();

		final boolean hasICApprovedForInvoicing = queryBuilderWithDefaultFilters
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_ApprovalForInvoicing, true)
				.create()
				.match();
		if (hasICApprovedForInvoicing)
		{
			facets.add(facetApprovalForInvoicing);
		}

		return facets;
	}
}
