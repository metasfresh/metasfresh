package de.metas.procurement.base.balance;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.util.lang.ObjectUtils;

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

public final class PMMBalanceChangeEvent
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final int C_BPartner_ID;
	private final int M_Product_ID;
	private final int M_HU_PI_Item_Product_ID;
	private final int C_Flatrate_DataEntry_ID;
	//
	private final Date date;
	//
	private final BigDecimal qtyPromised;
	private final BigDecimal qtyPromised_TU;
	private final BigDecimal qtyOrdered;
	private final BigDecimal qtyOrdered_TU;

	private PMMBalanceChangeEvent(final Builder builder)
	{
		super();
		C_BPartner_ID = builder.C_BPartner_ID;
		M_Product_ID = builder.M_Product_ID;
		M_HU_PI_Item_Product_ID = builder.M_HU_PI_Item_Product_ID;
		C_Flatrate_DataEntry_ID = builder.C_Flatrate_DataEntry_ID;
		//
		date = (Date)builder.date.clone();
		//
		qtyPromised = builder.qtyPromised;
		qtyPromised_TU = builder.qtyPromised_TU;
		qtyOrdered = builder.qtyOrdered;
		qtyOrdered_TU = builder.qtyOrdered_TU;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public int getC_BPartner_ID()
	{
		return C_BPartner_ID;
	}

	public int getM_Product_ID()
	{
		return M_Product_ID;
	}

	public int getM_HU_PI_Item_Product_ID()
	{
		return M_HU_PI_Item_Product_ID;
	}
	
	public int getC_Flatrate_DataEntry_ID()
	{
		return C_Flatrate_DataEntry_ID;
	}

	public Date getDate()
	{
		return date;
	}

	public BigDecimal getQtyOrdered()
	{
		return qtyOrdered;
	}

	public BigDecimal getQtyOrdered_TU()
	{
		return qtyOrdered_TU;
	}

	public BigDecimal getQtyPromised()
	{
		return qtyPromised;
	}

	public BigDecimal getQtyPromised_TU()
	{
		return qtyPromised_TU;
	}

	public static final class Builder
	{
		private Integer C_BPartner_ID;
		private Integer M_Product_ID;
		private Integer M_HU_PI_Item_Product_ID;
		private int C_Flatrate_DataEntry_ID = -1;
		//
		private Date date;
		//
		private BigDecimal qtyPromised = BigDecimal.ZERO;
		private BigDecimal qtyPromised_TU = BigDecimal.ZERO;
		private BigDecimal qtyOrdered = BigDecimal.ZERO;
		private BigDecimal qtyOrdered_TU = BigDecimal.ZERO;

		private Builder()
		{
			super();
		}

		public PMMBalanceChangeEvent build()
		{
			return new PMMBalanceChangeEvent(this);
		}

		public Builder setC_BPartner_ID(final int C_BPartner_ID)
		{
			this.C_BPartner_ID = C_BPartner_ID;
			return this;
		}

		public Builder setM_Product_ID(final int M_Product_ID)
		{
			this.M_Product_ID = M_Product_ID;
			return this;
		}

		public Builder setM_HU_PI_Item_Product_ID(final int M_HU_PI_Item_Product_ID)
		{
			this.M_HU_PI_Item_Product_ID = M_HU_PI_Item_Product_ID;
			return this;
		}
		
		public Builder setC_Flatrate_DataEntry_ID(int C_Flatrate_DataEntry_ID)
		{
			this.C_Flatrate_DataEntry_ID = C_Flatrate_DataEntry_ID;
			return this;
		}

		public Builder setDate(final Date date)
		{
			this.date = date;
			return this;
		}

		public Builder setQtyPromised(final BigDecimal qtyPromised, final BigDecimal qtyPromised_TU)
		{
			this.qtyPromised = qtyPromised;
			this.qtyPromised_TU = qtyPromised_TU;
			return this;
		}

		public Builder setQtyOrdered(final BigDecimal qtyOrdered, final BigDecimal qtyOrdered_TU)
		{
			this.qtyOrdered = qtyOrdered;
			this.qtyOrdered_TU = qtyOrdered_TU;
			return this;
		}
	}
}
