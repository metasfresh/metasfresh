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

public class Cctop901991V implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = -7850719191002385720L;

	/*
	 * metasfresh-mapped fields
	 */
	private String cInvoiceID;
	private String rate;
	private String taxAmt;
	private String taxBaseAmt;
	private String totalAmt;
	private String ESRNumber;

	/*
	 * Deduced fields
	 */
	private String taxAmtSumTaxBaseAmt;

	public String getcInvoiceID()
	{
		return cInvoiceID;
	}

	public void setcInvoiceID(final String cInvoiceID)
	{
		this.cInvoiceID = cInvoiceID;
	}

	public String getRate()
	{
		return rate;
	}

	public void setRate(final String rate)
	{
		this.rate = rate;
	}

	public String getTaxAmt()
	{
		return taxAmt;
	}

	public void setTaxAmt(final String taxAmt)
	{
		this.taxAmt = taxAmt;
	}

	public String getTaxBaseAmt()
	{
		return taxBaseAmt;
	}

	public void setTaxBaseAmt(final String taxBaseAmt)
	{
		this.taxBaseAmt = taxBaseAmt;
	}

	public String getTotalAmt()
	{
		return totalAmt;
	}

	public void setTotalAmt(final String totalAmt)
	{
		this.totalAmt = totalAmt;
	}

	public String getTaxAmtSumTaxBaseAmt()
	{
		return taxAmtSumTaxBaseAmt;
	}

	public void setTaxAmtSumTaxBaseAmt(final String taxAmtSumTaxBaseAmt)
	{
		this.taxAmtSumTaxBaseAmt = taxAmtSumTaxBaseAmt;
	}

	public String getESRNumber()
	{
		return ESRNumber;
	}

	public void setESRNumber(final String eSRNumber)
	{
		ESRNumber = eSRNumber;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (ESRNumber == null ? 0 : ESRNumber.hashCode());
		result = prime * result + (cInvoiceID == null ? 0 : cInvoiceID.hashCode());
		result = prime * result + (rate == null ? 0 : rate.hashCode());
		result = prime * result + (taxAmt == null ? 0 : taxAmt.hashCode());
		result = prime * result + (taxAmtSumTaxBaseAmt == null ? 0 : taxAmtSumTaxBaseAmt.hashCode());
		result = prime * result + (taxBaseAmt == null ? 0 : taxBaseAmt.hashCode());
		result = prime * result + (totalAmt == null ? 0 : totalAmt.hashCode());
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
		final Cctop901991V other = (Cctop901991V)obj;
		if (ESRNumber == null)
		{
			if (other.ESRNumber != null)
			{
				return false;
			}
		}
		else if (!ESRNumber.equals(other.ESRNumber))
		{
			return false;
		}
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
		if (taxAmt == null)
		{
			if (other.taxAmt != null)
			{
				return false;
			}
		}
		else if (!taxAmt.equals(other.taxAmt))
		{
			return false;
		}
		if (taxAmtSumTaxBaseAmt == null)
		{
			if (other.taxAmtSumTaxBaseAmt != null)
			{
				return false;
			}
		}
		else if (!taxAmtSumTaxBaseAmt.equals(other.taxAmtSumTaxBaseAmt))
		{
			return false;
		}
		if (taxBaseAmt == null)
		{
			if (other.taxBaseAmt != null)
			{
				return false;
			}
		}
		else if (!taxBaseAmt.equals(other.taxBaseAmt))
		{
			return false;
		}
		if (totalAmt == null)
		{
			if (other.totalAmt != null)
			{
				return false;
			}
		}
		else if (!totalAmt.equals(other.totalAmt))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "Cctop901991V [cInvoiceID=" + cInvoiceID + ", rate=" + rate + ", taxAmt=" + taxAmt + ", taxBaseAmt=" + taxBaseAmt + ", totalAmt=" + totalAmt + ", ESRNumber=" + ESRNumber
				+ ", taxAmtSumTaxBaseAmt=" + taxAmtSumTaxBaseAmt + "]";
	}

}
