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

import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

/**
 * Valid immutable object used to define how much we want to distribute to new TUs.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08754_Kommissionierung_Erweiterung_Verteilung_%28103380135151%29
 */
public class DistributeQtyToNewHUsRequest
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final I_M_HU_PI_Item_Product piItemProduct;
	private final BigDecimal qtyToDistribute;
	private final I_C_UOM qtyToDistributeUOM;
	private final BigDecimal qtyToDistributePerHU;
	private final int qtyTU;

	private DistributeQtyToNewHUsRequest(final Builder builder)
	{
		super();

		piItemProduct = builder.piItemProduct;
		Check.assumeNotNull(piItemProduct, "piItemProduct not null");

		qtyToDistribute = builder.qtyToDistribute;
		Check.assumeNotNull(qtyToDistribute, "qtyToDistribute not null");

		qtyToDistributeUOM = builder.qtyToDistributeUOM;
		Check.assumeNotNull(qtyToDistributeUOM, "qtyToDistributeUOM not null");

		qtyToDistributePerHU = builder.qtyToDistributePerHU;
		Check.assumeNotNull(qtyToDistributePerHU, "qtyToDistributePerHU not null");

		qtyTU = builder.qtyTU;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		return piItemProduct;
	}

	public BigDecimal getQtyToDistribute()
	{
		return qtyToDistribute == null ? BigDecimal.ZERO : qtyToDistribute;
	}

	public I_C_UOM getQtyToDistributeUOM()
	{
		return qtyToDistributeUOM;
	}

	public BigDecimal getQtyToDistributePerHU()
	{
		return qtyToDistributePerHU == null ? BigDecimal.ZERO : qtyToDistributePerHU;
	}

	public int getQtyTU()
	{
		return qtyTU;
	}

	public static class Builder
	{
		private I_M_HU_PI_Item_Product piItemProduct;
		private BigDecimal qtyToDistribute;
		private I_C_UOM qtyToDistributeUOM;
		private BigDecimal qtyToDistributePerHU;
		private int qtyTU = 0;

		private Builder()
		{
			super();
		}

		public DistributeQtyToNewHUsRequest build()
		{
			return new DistributeQtyToNewHUsRequest(this);
		}

		public Builder setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product piItemProduct)
		{
			this.piItemProduct = piItemProduct;
			return this;
		}

		public Builder setQtyToDistribute(final BigDecimal qtyToDistribute)
		{
			this.qtyToDistribute = qtyToDistribute;
			return this;
		}

		public Builder setQtyToDistributeUOM(final I_C_UOM qtyToDistributeUOM)
		{
			this.qtyToDistributeUOM = qtyToDistributeUOM;
			return this;
		}

		public Builder setQtyToDistributePerHU(final BigDecimal qtyToDistributePerHU)
		{
			this.qtyToDistributePerHU = qtyToDistributePerHU;
			return this;
		}

		public Builder setQtyTU(final int qtyTU)
		{
			this.qtyTU = qtyTU;
			return this;
		}
	}
}
