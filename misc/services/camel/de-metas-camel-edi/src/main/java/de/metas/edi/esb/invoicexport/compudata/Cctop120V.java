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

public class Cctop120V implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4933918540400838557L;

	private String cInvoiceID;

	private String isoCode;
	private Date netdate;
	private String netDays;
	private String singlevat;
	private String taxfree;

	public final String getcInvoiceID()
	{
		return cInvoiceID;
	}

	public final void setcInvoiceID(final String cInvoiceID)
	{
		this.cInvoiceID = cInvoiceID;
	}

	public String getIsoCode()
	{
		return isoCode;
	}

	public void setIsoCode(final String isoCode)
	{
		this.isoCode = isoCode;
	}

	public Date getNetdate()
	{
		return netdate;
	}

	public void setNetdate(final Date netdate)
	{
		this.netdate = netdate;
	}

	public String getNetDays()
	{
		return netDays;
	}

	public void setNetDays(final String netDays)
	{
		this.netDays = netDays;
	}

	public String getSinglevat()
	{
		return singlevat;
	}

	public void setSinglevat(final String singlevat)
	{
		this.singlevat = singlevat;
	}

	public String getTaxfree()
	{
		return taxfree;
	}

	public void setTaxfree(final String taxfree)
	{
		this.taxfree = taxfree;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (cInvoiceID == null ? 0 : cInvoiceID.hashCode());
		result = prime * result + (isoCode == null ? 0 : isoCode.hashCode());
		result = prime * result + (netDays == null ? 0 : netDays.hashCode());
		result = prime * result + (netdate == null ? 0 : netdate.hashCode());
		result = prime * result + (singlevat == null ? 0 : singlevat.hashCode());
		result = prime * result + (taxfree == null ? 0 : taxfree.hashCode());
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
		final Cctop120V other = (Cctop120V)obj;
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
		if (isoCode == null)
		{
			if (other.isoCode != null)
			{
				return false;
			}
		}
		else if (!isoCode.equals(other.isoCode))
		{
			return false;
		}
		if (netDays == null)
		{
			if (other.netDays != null)
			{
				return false;
			}
		}
		else if (!netDays.equals(other.netDays))
		{
			return false;
		}
		if (netdate == null)
		{
			if (other.netdate != null)
			{
				return false;
			}
		}
		else if (!netdate.equals(other.netdate))
		{
			return false;
		}
		if (singlevat == null)
		{
			if (other.singlevat != null)
			{
				return false;
			}
		}
		else if (!singlevat.equals(other.singlevat))
		{
			return false;
		}
		if (taxfree == null)
		{
			if (other.taxfree != null)
			{
				return false;
			}
		}
		else if (!taxfree.equals(other.taxfree))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "Cctop120V [cInvoiceID=" + cInvoiceID + ", isoCode=" + isoCode + ", netdate=" + netdate + ", netDays=" + netDays + ", singlevat=" + singlevat + ", taxfree="
				+ taxfree + "]";
	}
}
