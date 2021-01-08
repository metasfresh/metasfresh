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

	private int C_BPartner_ID;
	private int M_Product_ID;
	private int M_AttributeSetInstance_ID;
	private int M_HU_PI_Item_Product_ID;
	private int C_Flatrate_DataEntry_ID;
	//
	private Date date;
	//
	private BigDecimal qtyPromised;
	private BigDecimal qtyPromised_TU;
	private BigDecimal qtyOrdered;
	private BigDecimal qtyOrdered_TU;
	private BigDecimal qtyDelivered;
	
	public PMMBalanceChangeEvent()
	{
		// NOTE: we need to make the event class mutable because we want to serialize/deserialize as JSON when we are calling REST apis 
		super();
	}

	private PMMBalanceChangeEvent(final Builder builder)
	{
		C_BPartner_ID = builder.C_BPartner_ID;
		M_Product_ID = builder.M_Product_ID;
		M_AttributeSetInstance_ID = builder.M_AttributeSetInstance_ID > 0 ? builder.M_AttributeSetInstance_ID : 0;
		M_HU_PI_Item_Product_ID = builder.M_HU_PI_Item_Product_ID;
		C_Flatrate_DataEntry_ID = builder.C_Flatrate_DataEntry_ID;
		//
		date = (Date)builder.date.clone();
		//
		qtyPromised = builder.qtyPromised;
		qtyPromised_TU = builder.qtyPromised_TU;
		qtyOrdered = builder.qtyOrdered;
		qtyOrdered_TU = builder.qtyOrdered_TU;
		qtyDelivered = builder.qtyDelivered;
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
	
	public void setC_BPartner_ID(int c_BPartner_ID)
	{
		C_BPartner_ID = c_BPartner_ID;
	}

	public int getM_Product_ID()
	{
		return M_Product_ID;
	}
	
	public void setM_Product_ID(int m_Product_ID)
	{
		M_Product_ID = m_Product_ID;
	}
	
	public int getM_AttributeSetInstance_ID()
	{
		return M_AttributeSetInstance_ID;
	}
	
	public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID)
	{
		this.M_AttributeSetInstance_ID = M_AttributeSetInstance_ID > 0 ? M_AttributeSetInstance_ID : 0;
	}

	public int getM_HU_PI_Item_Product_ID()
	{
		return M_HU_PI_Item_Product_ID;
	}
	
	public void setM_HU_PI_Item_Product_ID(int M_HU_PI_Item_Product_ID)
	{
		this.M_HU_PI_Item_Product_ID = M_HU_PI_Item_Product_ID;
	}
	
	public int getC_Flatrate_DataEntry_ID()
	{
		return C_Flatrate_DataEntry_ID;
	}
	
	public void setC_Flatrate_DataEntry_ID(int C_Flatrate_DataEntry_ID)
	{
		this.C_Flatrate_DataEntry_ID = C_Flatrate_DataEntry_ID;
	}

	public Date getDate()
	{
		return date;
	}
	
	public void setDate(Date date)
	{
		this.date = date;
	}

	public BigDecimal getQtyOrdered()
	{
		return qtyOrdered;
	}
	
	public void setQtyOrdered(BigDecimal qtyOrdered)
	{
		this.qtyOrdered = qtyOrdered;
	}

	public BigDecimal getQtyOrdered_TU()
	{
		return qtyOrdered_TU;
	}
	
	public void setQtyOrdered_TU(BigDecimal qtyOrdered_TU)
	{
		this.qtyOrdered_TU = qtyOrdered_TU;
	}

	public BigDecimal getQtyPromised()
	{
		return qtyPromised;
	}
	
	public void setQtyPromised(BigDecimal qtyPromised)
	{
		this.qtyPromised = qtyPromised;
	}

	public BigDecimal getQtyPromised_TU()
	{
		return qtyPromised_TU;
	}
	
	public void setQtyPromised_TU(BigDecimal qtyPromised_TU)
	{
		this.qtyPromised_TU = qtyPromised_TU;
	}
	
	public BigDecimal getQtyDelivered()
	{
		return qtyDelivered;
	}
	
	public void setQtyDelivered(BigDecimal qtyDelivered)
	{
		this.qtyDelivered = qtyDelivered;
	}

	public static final class Builder
	{
		private Integer C_BPartner_ID;
		private Integer M_Product_ID;
		private Integer M_AttributeSetInstance_ID;
		private Integer M_HU_PI_Item_Product_ID;
		private int C_Flatrate_DataEntry_ID = -1;
		//
		private Date date;
		//
		private BigDecimal qtyPromised = BigDecimal.ZERO;
		private BigDecimal qtyPromised_TU = BigDecimal.ZERO;
		private BigDecimal qtyOrdered = BigDecimal.ZERO;
		private BigDecimal qtyOrdered_TU = BigDecimal.ZERO;
		private BigDecimal qtyDelivered = BigDecimal.ZERO;

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
		
		public Builder setM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
		{
			this.M_AttributeSetInstance_ID = M_AttributeSetInstance_ID;
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
		
		public Builder setQtyDelivered(BigDecimal qtyDelivered)
		{
			this.qtyDelivered = qtyDelivered;
			return this;
		}
	}
}
