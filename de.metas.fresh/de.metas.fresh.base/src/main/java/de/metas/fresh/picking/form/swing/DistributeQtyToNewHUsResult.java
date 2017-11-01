package de.metas.fresh.picking.form.swing;

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


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.TokenizedStringBuilder;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.i18n.IMsgBL;

/**
 * Immutable distribution result.
 * 
 * This is calculated based on {@link DistributeQtyToNewHUsRequest} and specifies exactly what needs to be done.
 * 
 * @author tsa
 *
 */
public class DistributeQtyToNewHUsResult
{
	// services
	private final transient IHUPIItemProductBL huPIItemProductBL = Services.get(IHUPIItemProductBL.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final DecimalFormat qtyFormat = DisplayType.getNumberFormat(DisplayType.Quantity);

	public static final Builder builder()
	{
		return new Builder();
	}

	/**
	 * Calculates the result based on given request.
	 * 
	 * @param request
	 * @return result; never returns null
	 */
	public static DistributeQtyToNewHUsResult calculateResult(final DistributeQtyToNewHUsRequest request)
	{
		Check.assumeNotNull(request, "request not null");

		final I_M_HU_PI_Item_Product piItemProduct = request.getM_HU_PI_Item_Product();
		final BigDecimal qtyToDistribute = request.getQtyToDistribute();
		final DistributeQtyToNewHUsResult.Builder resultBuilder = DistributeQtyToNewHUsResult.builder()
				.setM_HU_PI_Item_Product(piItemProduct);

		final BigDecimal qtyPerHU = request.getQtyToDistributePerHU();
		final BigDecimal qtyTU = BigDecimal.valueOf(request.getQtyTU());

		// Make sure we have some valid quantities
		if (qtyTU.signum() <= 0 || qtyPerHU.signum() <= 0 || qtyToDistribute.signum() <= 0)
		{
			return resultBuilder.build();
		}

		// Case: the Qty/HU is bigger than the total qty we have to distribute
		// => we will have only one HU with the total qty to distribute
		if (qtyPerHU.compareTo(qtyToDistribute) > 0)
		{
			resultBuilder.addItem(1, qtyToDistribute);
			return resultBuilder.build();
		}

		BigDecimal qtyToDistributeRemaining = qtyToDistribute;
		BigDecimal qtyTURemaining = qtyTU;

		//
		// Distribute as much as posible in complete HUs
		final BigDecimal qtyTUMax = qtyToDistribute.divide(qtyPerHU, 0, RoundingMode.DOWN);
		if (qtyTUMax.signum() > 0)
		{
			final BigDecimal qtyTUActual = qtyTU.min(qtyTUMax);
			resultBuilder.addItem(qtyTUActual.intValueExact(), qtyPerHU);

			qtyToDistributeRemaining = qtyToDistributeRemaining.subtract(qtyTUActual.multiply(qtyPerHU));
			qtyTURemaining = qtyTURemaining.subtract(qtyTUActual);
		}

		//
		// Last HU: If we have remaining qty and remaining TUs to distribute, then
		// Put everything to one last HU.
		if (qtyTURemaining.signum() > 0 && qtyToDistributeRemaining.signum() > 0)
		{
			resultBuilder.addItem(1, qtyToDistributeRemaining);
			qtyToDistributeRemaining = BigDecimal.ZERO;
			qtyTURemaining = BigDecimal.ZERO;
		}

		resultBuilder.setQtyNotDistributed(qtyToDistributeRemaining);

		return resultBuilder.build();
	}

	private final I_M_HU_PI_Item_Product piItemProduct;
	private final BigDecimal qtyNotDistributed;
	private final List<Item> items;
	private final String displayInfo;

	private DistributeQtyToNewHUsResult(final Builder builder)
	{
		super();

		this.piItemProduct = builder.piItemProduct;
		Check.assumeNotNull(piItemProduct, "piItemProduct not null");

		this.qtyNotDistributed = builder.qtyNotDistributed;
		Check.assumeNotNull(qtyNotDistributed, "qtyNotDistributed not null");

		this.items = ImmutableList.copyOf(builder.items);

		this.displayInfo = buildDisplayInfo();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/** @return item product; never returns null */
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		return piItemProduct;
	}

	public List<Item> getItems()
	{
		return items;
	}

	public BigDecimal getQtyNotDistributed()
	{
		return qtyNotDistributed;
	}

	public String getDisplayInfo()
	{
		return displayInfo;
	}

	private final String buildDisplayInfo()
	{
		final TokenizedStringBuilder info = new TokenizedStringBuilder("\n")
				.setAutoAppendSeparator(false);

		for (final Item item : getItems())
		{
			final String itemInfo = huPIItemProductBL.buildDisplayName()
					.setM_HU_PI_Item_Product(piItemProduct)
					.setQtyCapacity(item.getQtyPerHU())
					.setQtyTUPlanned(item.getQtyHU())
					.build();

			if (Check.isEmpty(itemInfo))
			{
				continue;
			}

			info.appendSeparatorIfNeeded();
			info.append(itemInfo);
		}

		//
		// Add QtyNotDistributed info
		if (qtyNotDistributed != null && qtyNotDistributed.signum() != 0)
		{
			info.appendSeparatorIfNeeded();
			info.append(msgBL.translate(Env.getCtx(), "QtyNotDistributed"))
					.append(": ")
					.append(qtyFormat.format(qtyNotDistributed));
		}

		return info.toString();
	}

	public void validate()
	{
		if (items.isEmpty())
		{
			throw new TerminalException("@Invalid@");
		}
	}

	public static class Item
	{
		private final int qtyHU;
		private final BigDecimal qtyPerHU;

		private Item(final int qtyHU, final BigDecimal qtyPerHU)
		{
			super();
			Check.assume(qtyHU > 0, "qtyHU > 0");
			this.qtyHU = qtyHU;

			Check.assumeNotNull(qtyPerHU, "qtyPerHU not null");
			Check.assume(qtyPerHU.signum() > 0, "qtyPerHU > 0");
			this.qtyPerHU = qtyPerHU;
		}

		public int getQtyHU()
		{
			return qtyHU;
		}

		public BigDecimal getQtyPerHU()
		{
			return qtyPerHU;
		}

	}

	public static class Builder
	{
		private I_M_HU_PI_Item_Product piItemProduct;
		private BigDecimal qtyNotDistributed = BigDecimal.ZERO;
		private List<Item> items = new ArrayList<>();

		private Builder()
		{
			super();
		}

		public DistributeQtyToNewHUsResult build()
		{
			return new DistributeQtyToNewHUsResult(this);
		}

		public Builder setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product piItemProduct)
		{
			this.piItemProduct = piItemProduct;
			return this;
		}

		public Builder setQtyNotDistributed(final BigDecimal qtyNotDistributed)
		{
			Check.assumeNotNull(qtyNotDistributed, "qtyNotDistributed not null");
			this.qtyNotDistributed = qtyNotDistributed;
			return this;
		}

		public Builder addItem(final int qtyHU, final BigDecimal qtyPerHU)
		{
			final Item item = new Item(qtyHU, qtyPerHU);
			items.add(item);
			return this;
		}
	}
}
