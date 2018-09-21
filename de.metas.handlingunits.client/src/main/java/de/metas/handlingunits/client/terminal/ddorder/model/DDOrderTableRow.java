package de.metas.handlingunits.client.terminal.ddorder.model;

/*
 * #%L
 * de.metas.handlingunits.client
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
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.EqualsBuilder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.util.ArrayKeyBuilder;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;

import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.client.terminal.ddorder.api.impl.DDOrderTableRowAggregationKey;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.util.Check;
import de.metas.util.Services;

public class DDOrderTableRow implements IDDOrderTableRow
{
	// services
	private final IHUPIItemProductBL huPIItemProductBL = Services.get(IHUPIItemProductBL.class);

	private final DDOrderTableRowAggregationKey aggregationKey;
	private final List<DDOrderLineAndAlternatives> ddOrderLinesAndAlternatives;
	private final List<I_DD_OrderLine> ddOrderLines;
	private final Set<Integer> ddOrderIds;

	private final I_C_BPartner partner;
	private String bpartnerName;
	private final I_M_Locator locatorFrom;
	private String sourceWarehouseName;
	private final List<I_M_Product> products;
	private String productsName;
	private final Set<Integer> productIds;
	private final I_M_HU_PI_Item_Product piItemProduct;
	private String piItemProductName = null; // built name

	private final Date dateOrdered;
	private final Date datePromised;

	private final BigDecimal qtyEnteredTU;
	private final BigDecimal qtyOrdered;
	private final BigDecimal qtyDelivered;

	private final ArrayKey hashKey;

	public DDOrderTableRow(final DDOrderTableRowAggregationKey aggregationKey, final List<DDOrderLineAndAlternatives> ddOrderLinesAndAlternatives)
	{
		super();

		Check.assumeNotNull(aggregationKey, "aggregationKey not null");
		this.aggregationKey = aggregationKey;
		this.ddOrderLinesAndAlternatives = Collections.unmodifiableList(new ArrayList<>(ddOrderLinesAndAlternatives));

		partner = aggregationKey.getC_BPartner();
		locatorFrom = aggregationKey.getM_LocatorFrom();
		piItemProduct = aggregationKey.getM_HU_PI_Item_Product();
		productIds = aggregationKey.getProductIds();
		products = aggregationKey.getProducts();
		ddOrderLines = new ArrayList<I_DD_OrderLine>();

		//
		// Iterate DD OrderLine&Alternatives and aggregate values
		final Set<Integer> ddOrderIds = new HashSet<>();
		BigDecimal qtyOrdered = BigDecimal.ZERO;
		BigDecimal qtyDelivered = BigDecimal.ZERO;
		BigDecimal qtyEnteredTU = BigDecimal.ZERO;
		Date minDateOrdered = null;
		Date minDatePromised = null;
		final ArrayKeyBuilder hashKeyBuilder = Util.mkKey();
		for (final DDOrderLineAndAlternatives ddOrderLineAndAlternatives : ddOrderLinesAndAlternatives)
		{
			final de.metas.handlingunits.model.I_DD_OrderLine ddOrderLine = ddOrderLineAndAlternatives.getDD_OrderLine();
			// We need to make sure each DD_Order_Line is out-of-transaction
			// because we will save it after each setter
			final String trxName = InterfaceWrapperHelper.getTrxName(ddOrderLine);
			Check.assume(Services.get(ITrxManager.class).isNull(trxName), "dd order line's transaction shall be null");

			//
			// Aggregate informations from DD_OrderLine
			qtyEnteredTU = qtyEnteredTU.add(ddOrderLine.getQtyEnteredTU());
			qtyOrdered = qtyOrdered.add(ddOrderLine.getQtyOrdered()); // NOTE: we assume we aggregated on UOMs too
			qtyDelivered = qtyDelivered.add(ddOrderLine.getQtyDelivered());
			minDateOrdered = TimeUtil.min(minDateOrdered, ddOrderLine.getDateOrdered());
			minDatePromised = TimeUtil.min(minDatePromised, ddOrderLine.getDatePromised());
			hashKeyBuilder.append("L", ddOrderLine.getDD_OrderLine_ID());
			ddOrderIds.add(ddOrderLine.getDD_Order_ID());
			ddOrderLines.add(ddOrderLine);

			//
			// Iterate alternatives and aggregate informations
			final List<I_DD_OrderLine_Alternative> alternatives = ddOrderLineAndAlternatives.getDD_OrderLine_Alternatives();
			for (final I_DD_OrderLine_Alternative alternative : alternatives)
			{
				// NOTE: sum up only the QtyOrdered from DD Order Lines (excluding Alternatives) because else we would get double/triple/etc quantity

				final BigDecimal alternativeQtyDelivered = alternative.getQtyDelivered();
				// NOTE: even if we are adding qty from different products (main product and alternatives), consider this quantity as informative
				qtyDelivered = qtyDelivered.add(alternativeQtyDelivered);
				hashKeyBuilder.append("A", alternative.getDD_OrderLine_Alternative_ID());
			}
		}

		dateOrdered = minDateOrdered;
		datePromised = minDatePromised;
		this.qtyOrdered = qtyOrdered;
		this.qtyDelivered = qtyDelivered;
		this.qtyEnteredTU = qtyEnteredTU;
		this.ddOrderIds = Collections.unmodifiableSet(ddOrderIds);
		hashKey = hashKeyBuilder.build();
	}

	@Override
	public String toString()
	{
		return "DDOrderTableRow ["
				+ "BPName=" + getBPName()
				+ ", ProductName=" + getProductName()
				+ ", QtyOrdered=" + getQtyOrdered()
				+ ", QtyDelivered=" + getQtyDelivered()
				+ ", Packing=" + getM_HU_PI_Item_Product_Name()
				+ ", SourceWarehouse=" + getSourceWarehouseName()
				+ ", DateOrdered=" + getDateOrdered()
				+ ", DatePromised=" + getDatePromised()
				+ "]";
	}

	@Override
	public DDOrderTableRowAggregationKey getAggregationKey()
	{
		return aggregationKey;
	}

	@Override
	public List<DDOrderLineAndAlternatives> getDDOrderLineAndAlternatives()
	{
		return ddOrderLinesAndAlternatives;
	}

	@Override
	public int hashCode()
	{
		return hashKey.hashCode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final DDOrderTableRow other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return hashKey.equals(other.hashKey);
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return partner;
	}

	@Override
	public Date getDateOrdered()
	{
		return dateOrdered;
	}

	@Override
	public Date getDatePromised()
	{
		return datePromised;
	}

	@Override
	public String getBPName()
	{
		if (bpartnerName == null)
		{
			bpartnerName = getC_BPartner().getName();
		}
		return bpartnerName;
	}

	@Override
	public String getProductName()
	{
		if (productsName == null)
		{
			final StringBuilder sb = new StringBuilder();
			for (final I_M_Product product : getM_Products())
			{
				if (sb.length() > 0)
				{
					sb.append(" / ");
				}
				sb.append(product.getName());
			}
			productsName = sb.toString();
		}
		return productsName;
	}

	@Override
	public BigDecimal getQtyOrdered()
	{
		return qtyOrdered;
	}

	@Override
	public BigDecimal getQtyDelivered()
	{
		return qtyDelivered;
	}

	@Override
	public String getM_HU_PI_Item_Product_Name()
	{
		if (piItemProductName != null)
		{
			return piItemProductName;
		}

		if (piItemProduct == null)
		{
			return null;
		}

		piItemProductName = huPIItemProductBL.buildDisplayName()
				.setM_HU_PI_Item_Product(piItemProduct)
				.setQtyTUPlanned(qtyEnteredTU)
				.build();
		return piItemProductName;
	}

	@Override
	public Set<Integer> getDD_Order_IDs()
	{
		return ddOrderIds;
	}

	@Override
	public List<I_DD_OrderLine> getDD_OrderLines()
	{
		return ddOrderLines;
	}

	@Override
	public I_M_Locator getM_Locator_From()
	{
		return locatorFrom;
	}

	@Override
	public String getSourceWarehouseName()
	{
		if (sourceWarehouseName == null)
		{
			// no NPE shall occur because the locator is mandatory in ddOrderLine
			sourceWarehouseName = locatorFrom.getM_Warehouse().getName();
		}
		return sourceWarehouseName;
	}

	@Override
	public List<I_M_Product> getM_Products()
	{
		return products;
	}

	@Override
	public Set<Integer> getM_Product_IDs()
	{
		return productIds;
	}

	@Override
	public I_C_Order getC_Order()
	{
		return null; // no order
	}
}
