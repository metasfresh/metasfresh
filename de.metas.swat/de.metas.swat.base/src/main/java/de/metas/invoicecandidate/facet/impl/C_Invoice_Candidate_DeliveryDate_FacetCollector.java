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

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.facet.IFacet;
import org.adempiere.facet.IFacetCategory;
import org.adempiere.facet.impl.Facet;
import org.adempiere.facet.impl.FacetCategory;
import org.adempiere.facet.impl.SingleFacetCategoryCollectorTemplate;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * Collects {@link I_C_Invoice_Candidate#COLUMN_DeliveryDate} facets.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08602_Rechnungsdispo_UI_%28106621797084%29
 */
public class C_Invoice_Candidate_DeliveryDate_FacetCollector extends SingleFacetCategoryCollectorTemplate<I_C_Invoice_Candidate>
{
	private final DateFormat dateFormat = DisplayType.getDateFormat(DisplayType.Date);

	public C_Invoice_Candidate_DeliveryDate_FacetCollector()
	{
		super(FacetCategory.builder()
				.setDisplayNameAndTranslate(I_C_Invoice_Candidate.COLUMNNAME_DeliveryDate)
				.setCollapsed(true) // 08755: default collapsed
				.build());
	}

	@Override
	protected List<IFacet<I_C_Invoice_Candidate>> collectFacets(final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder)
	{
		// FRESH-560: Add default filter
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilderWithDefaultFilters = Services.get(IInvoiceCandDAO.class).applyDefaultFilter(queryBuilder);

		final List<Map<String, Object>> deliveryDates = queryBuilderWithDefaultFilters
				.addNotEqualsFilter(I_C_Invoice_Candidate.COLUMN_DeliveryDate, null)
				.create()
				.listDistinct(I_C_Invoice_Candidate.COLUMNNAME_DeliveryDate);

		final List<IFacet<I_C_Invoice_Candidate>> facets = new ArrayList<>(deliveryDates.size());
		for (final Map<String, Object> row : deliveryDates)
		{
			final IFacet<I_C_Invoice_Candidate> facet = createFacet(row);
			facets.add(facet);
		}

		return facets;
	}

	private IFacet<I_C_Invoice_Candidate> createFacet(final Map<String, Object> row)
	{
		final IFacetCategory facetCategoryDeliveryDates = getFacetCategory();

		final Timestamp deliveryDate = (Timestamp)row.get(I_C_Invoice_Candidate.COLUMNNAME_DeliveryDate);

		return Facet.<I_C_Invoice_Candidate> builder()
				.setFacetCategory(facetCategoryDeliveryDates)
				.setDisplayName(dateFormat.format(deliveryDate))
				.setFilter(new TypedSqlQueryFilter<I_C_Invoice_Candidate>(I_C_Invoice_Candidate.COLUMNNAME_DeliveryDate + "=" + DB.TO_DATE(deliveryDate)))
				.build();
	}

}
