package de.metas.procurement.base.order.facet.impl;

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
import org.compiere.model.I_M_Product;

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

public class PMM_PurchaseCandidate_Product_FacetCollector extends SingleFacetCategoryCollectorTemplate<I_PMM_PurchaseCandidate>
{
	public PMM_PurchaseCandidate_Product_FacetCollector()
	{
		super(FacetCategory.builder()
				.setDisplayNameAndTranslate(I_PMM_PurchaseCandidate.COLUMNNAME_M_Product_ID)
				.setCollapsed(true) // 08755: default collapsed
				.build());
	}

	@Override
	protected List<IFacet<I_PMM_PurchaseCandidate>> collectFacets(final IQueryBuilder<I_PMM_PurchaseCandidate> queryBuilder)
	{
		final List<Map<String, Object>> bpartners = queryBuilder
				.andCollect(I_PMM_PurchaseCandidate.COLUMN_M_Product_ID)
				.create()
				.listDistinct(I_M_Product.COLUMNNAME_M_Product_ID, I_M_Product.COLUMNNAME_Name, I_M_Product.COLUMNNAME_Value);

		final List<IFacet<I_PMM_PurchaseCandidate>> facets = new ArrayList<>(bpartners.size());
		for (final Map<String, Object> row : bpartners)
		{
			final IFacet<I_PMM_PurchaseCandidate> facet = createFacet(row);
			facets.add(facet);
		}
		return facets;
	}

	private IFacet<I_PMM_PurchaseCandidate> createFacet(final Map<String, Object> row)
	{
		final IFacetCategory facetCategoryBPartners = getFacetCategory();

		final int bpartnerId = (int)row.get(I_M_Product.COLUMNNAME_M_Product_ID);
		final String bpartnerName = new StringBuilder()
				.append(row.get(I_M_Product.COLUMNNAME_Value))
				.append(" - ")
				.append(row.get(I_M_Product.COLUMNNAME_Name))
				.toString();
		final Facet<I_PMM_PurchaseCandidate> facet = Facet.<I_PMM_PurchaseCandidate> builder()
				.setFacetCategory(facetCategoryBPartners)
				.setDisplayName(bpartnerName)
				.setFilter(TypedSqlQueryFilter.of(I_PMM_PurchaseCandidate.COLUMNNAME_M_Product_ID + "=" + bpartnerId))
				.build();
		return facet;
	}

}
