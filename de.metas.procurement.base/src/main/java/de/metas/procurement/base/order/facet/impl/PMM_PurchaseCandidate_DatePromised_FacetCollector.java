package de.metas.procurement.base.order.facet.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.facet.IFacet;
import org.adempiere.facet.impl.Facet;
import org.adempiere.facet.impl.FacetCategory;
import org.adempiere.facet.impl.SingleFacetCategoryCollectorTemplate;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class PMM_PurchaseCandidate_DatePromised_FacetCollector extends SingleFacetCategoryCollectorTemplate<I_PMM_PurchaseCandidate>
{
	private final DateFormat dateFormat = DisplayType.getDateFormat(DisplayType.Date);

	public PMM_PurchaseCandidate_DatePromised_FacetCollector()
	{
		super(FacetCategory.builder()
				.setDisplayNameAndTranslate(I_PMM_PurchaseCandidate.COLUMNNAME_DatePromised)
				.setCollapsed(true) // 08755: default collapsed
				.build());
	}

	@Override
	protected List<IFacet<I_PMM_PurchaseCandidate>> collectFacets(final IQueryBuilder<I_PMM_PurchaseCandidate> queryBuilder)
	{
		final Timestamp today = Env.getDate(queryBuilder.getCtx());
		final List<Map<String, Object>> datePromised = queryBuilder
				.addNotEqualsFilter(I_PMM_PurchaseCandidate.COLUMN_DatePromised, null)
				.addCompareFilter(I_PMM_PurchaseCandidate.COLUMN_DatePromised, Operator.GREATER_OR_EQUAL, today)
				.create()
				.listDistinct(I_PMM_PurchaseCandidate.COLUMNNAME_DatePromised);

		final List<IFacet<I_PMM_PurchaseCandidate>> facets = new ArrayList<>(datePromised.size() + 1);
		for (final Map<String, Object> row : datePromised)
		{
			final IFacet<I_PMM_PurchaseCandidate> facet = createFacet(row);
			facets.add(facet);
		}
		

		//
		// Add Before Today facet
		facets.add(0, Facet.<I_PMM_PurchaseCandidate> builder()
				.setFacetCategory(getFacetCategory())
				.setDisplayName(" < " + dateFormat.format(today))
				.setFilter(TypedSqlQueryFilter.of(I_PMM_PurchaseCandidate.COLUMNNAME_DatePromised + "<" + DB.TO_DATE(today)))
				.build()
		);


		return facets;
	}

	private IFacet<I_PMM_PurchaseCandidate> createFacet(final Map<String, Object> row)
	{
		final Timestamp date = (Timestamp)row.get(I_PMM_PurchaseCandidate.COLUMNNAME_DatePromised);

		return Facet.<I_PMM_PurchaseCandidate> builder()
				.setFacetCategory(getFacetCategory())
				.setDisplayName(dateFormat.format(date))
				.setFilter(TypedSqlQueryFilter.of(I_PMM_PurchaseCandidate.COLUMNNAME_DatePromised + "=" + DB.TO_DATE(date)))
				.build();
	}

}
