/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.invoicexport.compudata;

import java.io.Serializable;
import java.util.Date;

public class Cctop140V implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -791114480567945931L;

	private String cInvoiceID;

	private String discount;
	private Date discountDate;
	private String discountDays;
	private String rate;

	public final String getcInvoiceID()
	{
		return cInvoiceID;
	}

	public final void setcInvoiceID(final String cInvoiceID)
	{
		this.cInvoiceID = cInvoiceID;
	}

	public String getDiscount()
	{
		return discount;
	}

	public void setDiscount(final String discount)
	{
		this.discount = discount;
	}

	public Date getDiscountDate()
	{
		return discountDate;
	}

	public void setDiscountDate(final Date discountDate)
	{
		this.discountDate = discountDate;
	}

	public String getDiscountDays()
	{
		return discountDays;
	}

	public void setDiscountDays(final String discountDays)
	{
		this.discountDays = discountDays;
	}

	public String getRate()
	{
		return rate;
	}

	public void setRate(final String rate)
	{
		this.rate = rate;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (cInvoiceID == null ? 0 : cInvoiceID.hashCode());
		result = prime * result + (discount == null ? 0 : discount.hashCode());
		result = prime * result + (discountDate == null ? 0 : discountDate.hashCode());
		result = prime * result + (discountDays == null ? 0 : discountDays.hashCode());
		result = prime * result + (rate == null ? 0 : rate.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final Cctop140V other = (Cctop140V)obj;
		if (cInvoiceID == null)
		{
			if (other.cInvoiceID != null)
			{
				return false;
			}
		}
		else if (!cInvoiceID.equals(other.cInvoiceID))
		{
			return false;
		}
		if (discount == null)
		{
			if (other.discount != null)
			{
				return false;
			}
		}
		else if (!discount.equals(other.discount))
		{
			return false;
		}
		if (discountDate == null)
		{
			if (other.discountDate != null)
			{
				return false;
			}
		}
		else if (!discountDate.equals(other.discountDate))
		{
			return false;
		}
		if (discountDays == null)
		{
			if (other.discountDays != null)
			{
				return false;
			}
		}
		else if (!discountDays.equals(other.discountDays))
		{
			return false;
		}
		if (rate == null)
		{
			if (other.rate != null)
			{
				return false;
			}
		}
		else if (!rate.equals(other.rate))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "Cctop140V [cInvoiceID=" + cInvoiceID + ", discount=" + discount + ", discountDate=" + discountDate + ", discountDays=" + discountDays + ", rate=" + rate + "]";
	}
}
