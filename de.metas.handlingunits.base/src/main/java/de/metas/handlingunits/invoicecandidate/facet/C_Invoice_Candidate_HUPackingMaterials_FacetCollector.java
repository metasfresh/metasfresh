package de.metas.handlingunits.invoicecandidate.facet;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryAggregateBuilder;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.facet.IFacet;
import org.adempiere.facet.IFacetCategory;
import org.adempiere.facet.impl.Facet;
import org.adempiere.facet.impl.FacetCategory;
import org.adempiere.facet.impl.SingleFacetCategoryCollectorTemplate;
import org.adempiere.util.NumberUtils;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * Collects packing material product facets.
 * 
 * A facet will have following diplay name: PackingMaterialProductName: Sum of QtyToInvoice (in price UOM).
 * 
 * e.g. IFCO: 40
 *
 * @author al
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08755_HUs_QtyToInvoiceInPriceUOM_in_Rechnungsdispo_%28106184133770%29
 */
public class C_Invoice_Candidate_HUPackingMaterials_FacetCollector extends SingleFacetCategoryCollectorTemplate<I_C_Invoice_Candidate>
{
	// services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final ModelDynAttributeAccessor<I_M_Product, BigDecimal> DYNATTR_QtyToInvoiceSum = new ModelDynAttributeAccessor<>("QtyToInvoiceSum", BigDecimal.class);

	public C_Invoice_Candidate_HUPackingMaterials_FacetCollector()
	{
		super(FacetCategory.builder()
				.setDisplayNameAndTranslate(I_M_HU.COLUMNNAME_M_HU_ID)
				.setCollapsed(false)
				.setEagerRefresh()
				.build());
	}

	@Override
	protected List<IFacet<I_C_Invoice_Candidate>> collectFacets(final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder)
	{
		// FRESH-560: Add default filter
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilderWithDefaultFilters = Services.get(IInvoiceCandDAO.class).applyDefaultFilter(icQueryBuilder);
		
		// Match only Products which are Packing Materials
		final IQuery<I_M_HU_PackingMaterial> isPackingMaterialQueryFilter = queryBL.createQueryBuilder(I_M_HU_PackingMaterial.class, queryBuilderWithDefaultFilters.getCtx(), queryBuilderWithDefaultFilters.getTrxName())
				.create();

		//
		// Query invoice candidates, aggregate them by packing material products and sum the QtyToInvoice
		final IQueryAggregateBuilder<I_C_Invoice_Candidate, I_M_Product> aggregateOnQtyToInvoiceInPriceUOM = queryBuilderWithDefaultFilters
				.addInSubQueryFilter(I_C_Invoice_Candidate.COLUMN_M_Product_ID, I_M_HU_PackingMaterial.COLUMN_M_Product_ID, isPackingMaterialQueryFilter)
				.aggregateOnColumn(I_C_Invoice_Candidate.COLUMN_M_Product_ID);
		aggregateOnQtyToInvoiceInPriceUOM
				.sum(DYNATTR_QtyToInvoiceSum, I_C_Invoice_Candidate.COLUMN_QtyToInvoiceInPriceUOM);

		//
		// Get aggregated products and create facets from them.
		final List<I_M_Product> products = aggregateOnQtyToInvoiceInPriceUOM.aggregate();
		final List<IFacet<I_C_Invoice_Candidate>> facets = new ArrayList<IFacet<I_C_Invoice_Candidate>>(products.size());
		for (final I_M_Product product : products)
		{
			final IFacet<I_C_Invoice_Candidate> facet = createPackingMaterialFacet(product);
			facets.add(facet);
		}

		return facets;
	}

	private final IFacet<I_C_Invoice_Candidate> createPackingMaterialFacet(final I_M_Product product)
	{
		final IFacetCategory facetCategory = getFacetCategory();
		final BigDecimal qtyToInvoiceInPriceUOM = DYNATTR_QtyToInvoiceSum.getValue(product);

		final int productId = product.getM_Product_ID();
		final String packingMaterialInfo = new StringBuilder()
				.append(product.getName())
				.append(": ")
				.append(qtyToInvoiceInPriceUOM == null ? "?" : NumberUtils.stripTrailingDecimalZeros(qtyToInvoiceInPriceUOM))
				.toString();

		return Facet.<I_C_Invoice_Candidate> builder()
				.setFacetCategory(facetCategory)
				.setId(facetCategory.getDisplayName() + "_" + productId)
				.setDisplayName(packingMaterialInfo)
				.setFilter(new TypedSqlQueryFilter<I_C_Invoice_Candidate>(I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID + "=" + productId))
				.build();
	}
}
