package de.metas.handlingunits.spi.impl;

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
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.adempiere.util.comparator.NullComparator;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Packing Material Document Line candidate.
 *
 * Use this class to create actual packing material lines for your particular document (e.g. shipment, material receipt etc).
 *
 * @author tsa
 *
 */
public final class HUPackingMaterialDocumentLineCandidate
{
	/**
	 * Creates a packing material line for given locator, empties product and empties count.
	 *
	 * @param locator on which locator the empties are
	 * @param emptiesProduct empties product (e.g. IFCO)
	 * @param emptiesCount how many empties we have (e.g. 5 IFCOs)
	 * @return packing material line
	 */
	public static HUPackingMaterialDocumentLineCandidate of(final I_M_Locator locator, final I_M_Product emptiesProduct, final int emptiesCount)
	{
		final I_M_Material_Tracking materialTracking = null; // N/A, not needed
		final HUPackingMaterialDocumentLineCandidate candidate = new HUPackingMaterialDocumentLineCandidate(emptiesProduct, materialTracking, locator);
		candidate.addQty(emptiesCount);
		return candidate;
	}

	public static Comparator<HUPackingMaterialDocumentLineCandidate> comparatorFromProductIds(final Comparator<Integer> productIdsComparator)
	{
		if (NullComparator.isNull(productIdsComparator))
		{
			return NullComparator.getInstance();
		}

		return new Comparator<HUPackingMaterialDocumentLineCandidate>()
		{
			@Override
			public int compare(final HUPackingMaterialDocumentLineCandidate o1, final HUPackingMaterialDocumentLineCandidate o2)
			{
				return productIdsComparator.compare(getM_Product_ID(o1), getM_Product_ID(o2));
			}

			private int getM_Product_ID(final HUPackingMaterialDocumentLineCandidate candidate)
			{
				return candidate == null ? -1 : candidate.getProductId().getRepoId();
			}
		};

	}

	private final I_M_Product product;
	private final I_C_UOM uom;
	private BigDecimal qty = BigDecimal.ZERO;
	private final I_M_Locator locator;
	private final I_M_Material_Tracking materialTracking;
	private final LinkedHashSet<IHUPackingMaterialCollectorSource> sources = new LinkedHashSet<>();

	/**
	 *
	 * @param product packing material product
	 */
	/* package */ HUPackingMaterialDocumentLineCandidate(final I_M_Product product, final I_M_Material_Tracking materialTracking, final I_M_Locator locator)
	{
		Check.assumeNotNull(product, "product not null");
		this.product = product;

		this.locator = locator;

		this.materialTracking = materialTracking;

		uom = Services.get(IUOMDAO.class).getEachUOM();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("product", product)
				.add("uom", uom)
				.add("qty", qty)
				.add("locator", locator)
				.add("materialTracking", materialTracking)
				.toString();
	}

	/**
	 *
	 * @return packing material product
	 */
	public I_M_Product getM_Product()
	{
		return product;
	}

	public ProductId getProductId()
	{
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	/**
	 *
	 * @return packing materials amount (qty); the unit of measure of returned amount is {@link #getC_UOM()}.
	 */
	public BigDecimal getQty()
	{
		return qty;
	}

	/**
	 *
	 * @param uomTo
	 * @return packing materials amount (qty) converted to given <code>uomTo</code> unit of measure.
	 */
	public BigDecimal getQty(final I_C_UOM uomTo)
	{
		final ProductId productId = getProductId();
		final I_C_UOM uomFrom = getC_UOM();
		final BigDecimal qtySrc = getQty();

		final BigDecimal qty = Services.get(IUOMConversionBL.class).convertQty(productId, qtySrc, uomFrom, uomTo);
		return qty;
	}

	/**
	 *
	 * @return packing materials amount (qty) converted to given stocking unit of measure.
	 * @see IProductBL#getStockingUOM(I_M_Product)
	 */
	public BigDecimal getQtyInStockingUOM()
	{
		final I_C_UOM uomTo = Services.get(IProductBL.class).getStockUOM(product);
		return getQty(uomTo);
	}

	protected void addQty(final int qtyToAdd)
	{
		qty = qty.add(BigDecimal.valueOf(qtyToAdd));
	}

	protected void subtractQty(final int qtyToSubtract)
	{
		qty = qty.subtract(BigDecimal.valueOf(qtyToSubtract));
	}

	/**
	 *
	 * @return packing materials amount(qty)'s unit of measure
	 */
	public I_C_UOM getC_UOM()
	{
		return uom;
	}

	private int getC_UOM_ID()
	{
		final int uomId = uom == null ? -1 : uom.getC_UOM_ID();
		return uomId > 0 ? uomId : -1;
	}

	public I_M_Locator getM_Locator()
	{
		return locator;
	}

	private int getM_Locator_ID()
	{
		final int locatorId = locator == null ? -1 : locator.getM_Locator_ID();
		return locatorId > 0 ? locatorId : -1;
	}

	public I_M_Material_Tracking getM_MaterialTracking()
	{
		return materialTracking;
	}

	private int getM_MaterialTracking_ID()
	{
		final int materialTrackingId = materialTracking == null ? -1 : materialTracking.getM_Material_Tracking_ID();
		return materialTrackingId > 0 ? materialTrackingId : -1;
	}

	protected void add(@NonNull final HUPackingMaterialDocumentLineCandidate candidateToAdd)
	{
		if (this == candidateToAdd)
		{
			throw new IllegalArgumentException("Cannot add to it self: " + candidateToAdd);
		}

		if (!Objects.equals(getProductId(), candidateToAdd.getProductId())
				|| getC_UOM_ID() != candidateToAdd.getC_UOM_ID()
				|| getM_Locator_ID() != candidateToAdd.getM_Locator_ID()
				|| getM_MaterialTracking_ID() != candidateToAdd.getM_MaterialTracking_ID())
		{
			throw new HUException("Candidates are not matching."
					+ "\nthis: " + this
					+ "\ncandidate to add: " + candidateToAdd);
		}

		qty = qty.add(candidateToAdd.qty);
		// add sources; might be different
		addSources(candidateToAdd.getSources());
	}

	public void addSourceIfNotNull(final IHUPackingMaterialCollectorSource huPackingMaterialCollectorSource)
	{
		if (huPackingMaterialCollectorSource != null)
		{
			sources.add(huPackingMaterialCollectorSource);
		}
	}

	private void addSources(final Set<IHUPackingMaterialCollectorSource> huPackingMaterialCollectorSources)
	{
		if (!huPackingMaterialCollectorSources.isEmpty())
		{
			sources.addAll(huPackingMaterialCollectorSources);
		}
	}

	public Set<IHUPackingMaterialCollectorSource> getSources()
	{
		return ImmutableSet.copyOf(sources);
	}
}
