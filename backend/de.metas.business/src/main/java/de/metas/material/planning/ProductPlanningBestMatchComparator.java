package de.metas.material.planning;

import org.eevolution.model.I_PP_Product_Planning;

import java.util.Comparator;

/**
 * Picks the "best match" from a list of PP_Product_Planning records.
 * <p>
 * Replicates the ORDER BY used by {@link de.metas.material.planning.impl.ProductPlanningDAO#toSql}:
 * <ol>
 *   <li>SeqNo ASC (lowest wins)</li>
 *   <li>IsAttributeDependant DESC (Y before N — prefer attribute-specific)</li>
 *   <li>AD_Org_ID DESC nulls last (specific org before wildcard 0)</li>
 *   <li>M_Warehouse_ID DESC nulls last (specific warehouse before null/0)</li>
 *   <li>S_Resource_ID DESC nulls last (specific plant before null/0)</li>
 * </ol>
 * <p>
 * Used by {@code ForecastLineGeneratorRepository} for in-memory best-match after bulk load.
 * A cross-check test verifies this comparator stays consistent with ProductPlanningDAO's SQL ORDER BY.
 *
 * @see de.metas.material.planning.impl.ProductPlanningDAO
 */
public class ProductPlanningBestMatchComparator implements Comparator<I_PP_Product_Planning>
{
	public static final ProductPlanningBestMatchComparator INSTANCE = new ProductPlanningBestMatchComparator();

	@Override
	public int compare(final I_PP_Product_Planning a, final I_PP_Product_Planning b)
	{
		// 1. SeqNo ASC (lowest wins)
		int cmp = Integer.compare(a.getSeqNo(), b.getSeqNo());
		if (cmp != 0)
		{
			return cmp;
		}

		// 2. IsAttributeDependant DESC (true before false)
		cmp = Boolean.compare(b.isAttributeDependant(), a.isAttributeDependant());
		if (cmp != 0)
		{
			return cmp;
		}

		// 3. AD_Org_ID DESC, nulls/0 last (higher = more specific)
		cmp = Integer.compare(b.getAD_Org_ID(), a.getAD_Org_ID());
		if (cmp != 0)
		{
			return cmp;
		}

		// 4. M_Warehouse_ID DESC, nulls/0 last
		cmp = Integer.compare(b.getM_Warehouse_ID(), a.getM_Warehouse_ID());
		if (cmp != 0)
		{
			return cmp;
		}

		// 5. S_Resource_ID DESC, nulls/0 last
		return Integer.compare(b.getS_Resource_ID(), a.getS_Resource_ID());
	}
}
