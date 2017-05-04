package de.metas.inoutcandidate.spi.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.comparator.NullComparator;
import org.apache.commons.collections4.list.UnmodifiableList;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.product.IProductBL;

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

			private final int getM_Product_ID(final HUPackingMaterialDocumentLineCandidate candidate)
			{
				return candidate == null ? -1 : candidate.getM_Product_ID();
			}
		};

	}

	private final I_M_Product product;
	private final I_C_UOM uom;
	private BigDecimal qty = BigDecimal.ZERO;
	private final I_M_Locator locator;
	private final I_M_Material_Tracking materialTracking;
	private final List<I_M_InOutLine> sources = new ArrayList<I_M_InOutLine>();

	/**
	 *
	 * @param product packing material product
	 */
	/* package */ HUPackingMaterialDocumentLineCandidate(final I_M_Product product, final I_M_Material_Tracking materialTracking, final I_M_Locator locator)
	{
		super();
		Check.assumeNotNull(product, "product not null");
		this.product = product;

		this.locator = locator;

		this.materialTracking = materialTracking;

		final Properties ctx = InterfaceWrapperHelper.getCtx(product);
		uom = Services.get(IUOMDAO.class).retrieveEachUOM(ctx);
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("HUPackingMaterialDocumentLineCandidate [product=");
		builder.append(product);
		builder.append(", uom=");
		builder.append(uom);
		builder.append(", qty=");
		builder.append(qty);
		builder.append(", locator=");
		builder.append(locator);
		builder.append(", materialTracking=");
		builder.append(materialTracking);
		builder.append("]");
		return builder.toString();
	}

	/**
	 *
	 * @return packing material product
	 */
	public I_M_Product getM_Product()
	{
		return product;
	}

	/**
	 *
	 * @return packing material product's M_Product_ID
	 */
	public int getM_Product_ID()
	{
		return product.getM_Product_ID();
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
		final I_M_Product product = getM_Product();
		final I_C_UOM uomFrom = getC_UOM();
		final BigDecimal qtySrc = getQty();

		final BigDecimal qty = Services.get(IUOMConversionBL.class).convertQty(product, qtySrc, uomFrom, uomTo);
		return qty;
	}

	/**
	 *
	 * @return packing materials amount (qty) converted to given stocking unit of measure.
	 * @see IProductBL#getStockingUOM(I_M_Product)
	 */
	public BigDecimal getQtyInStockingUOM()
	{
		final I_C_UOM uomTo = Services.get(IProductBL.class).getStockingUOM(product);
		return getQty(uomTo);
	}

	protected void addQty(final int qtyToAdd)
	{
		qty = qty.add(BigDecimal.valueOf(qtyToAdd));
	}

	protected void subtractQty(final int qtyToSubtract)
	{
		qty = qty.add(BigDecimal.valueOf(qtyToSubtract));
	}

	/**
	 *
	 * @return packing materials amount(qty)'s unit of measure
	 */
	public I_C_UOM getC_UOM()
	{
		return uom;
	}

	private final int getC_UOM_ID()
	{
		final int uomId = uom == null ? -1 : uom.getC_UOM_ID();
		return uomId > 0 ? uomId : -1;
	}

	public I_M_Locator getM_Locator()
	{
		return locator;
	}

	private final int getM_Locator_ID()
	{
		final int locatorId = locator == null ? -1 : locator.getM_Locator_ID();
		return locatorId > 0 ? locatorId : -1;
	}

	public I_M_Material_Tracking getM_MaterialTracking()
	{
		return materialTracking;
	}

	private final int getM_MaterialTracking_ID()
	{
		final int materialTrackingId = materialTracking == null ? -1 : materialTracking.getM_Material_Tracking_ID();
		return materialTrackingId > 0 ? materialTrackingId : -1;
	}

	protected void add(final HUPackingMaterialDocumentLineCandidate candidateToAdd)
	{
		Check.assumeNotNull(candidateToAdd, "candidateToAdd not null");
		if (this == candidateToAdd)
		{
			throw new IllegalArgumentException("Cannot add to it self: " + candidateToAdd);
		}

		if (getM_Product_ID() != candidateToAdd.getM_Product_ID()
				|| getC_UOM_ID() != candidateToAdd.getC_UOM_ID()
				|| getM_Locator_ID() != candidateToAdd.getM_Locator_ID()
				|| getM_MaterialTracking_ID() != candidateToAdd.getM_MaterialTracking_ID())
		{
			throw new HUException("Candidates are not matching."
					+ "\nthis: " + this
					+ "\ncandidate to add: " + candidateToAdd);
		}

		qty = qty.add(candidateToAdd.qty);
	}

	public void addSourceIfNotNull(final I_M_InOutLine iol)
	{
		if (iol != null)
		{
			sources.add(iol);
		}
	}

	public List<I_M_InOutLine> getSources()
	{
		return new UnmodifiableList<I_M_InOutLine>(sources);
	}
}
