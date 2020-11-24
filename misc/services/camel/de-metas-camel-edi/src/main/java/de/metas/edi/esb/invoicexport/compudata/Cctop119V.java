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

public class Cctop119V implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1650345883526756636L;

	private String cbPartnerLocationID;
	private String cInvoiceID;
	private String mInOutID;

	private String address1;
	private String address2;
	private String city;
	private String countryCode;
	private String eancomLocationtype;
	private String fax;
	private String gln;
	private String name;
	private String name2;
	private String phone;
	private String postal;
	private String value;
	private String vaTaxID;
	private String referenceNo;

	public final String getCbPartnerLocationID()
	{
		return cbPartnerLocationID;
	}

	public final void setCbPartnerLocationID(final String cbPartnerLocationID)
	{
		this.cbPartnerLocationID = cbPartnerLocationID;
	}

	public final String getcInvoiceID()
	{
		return cInvoiceID;
	}

	public final void setcInvoiceID(final String cInvoiceID)
	{
		this.cInvoiceID = cInvoiceID;
	}

	public final String getmInOutID()
	{
		return mInOutID;
	}

	public final void setmInOutID(final String mInOutID)
	{
		this.mInOutID = mInOutID;
	}

	public String getAddress1()
	{
		return address1;
	}

	public void setAddress1(final String address1)
	{
		this.address1 = address1;
	}

	public String getAddress2()
	{
		return address2;
	}

	public void setAddress2(final String address2)
	{
		this.address2 = address2;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(final String city)
	{
		this.city = city;
	}

	public String getCountryCode()
	{
		return countryCode;
	}

	public void setCountryCode(final String countryCode)
	{
		this.countryCode = countryCode;
	}

	public String getEancomLocationtype()
	{
		return eancomLocationtype;
	}

	public void setEancomLocationtype(final String eancomLocationtype)
	{
		this.eancomLocationtype = eancomLocationtype;
	}

	public String getFax()
	{
		return fax;
	}

	public void setFax(final String fax)
	{
		this.fax = fax;
	}

	public String getGln()
	{
		return gln;
	}

	public void setGln(final String gln)
	{
		this.gln = gln;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getName2()
	{
		return name2;
	}

	public void setName2(final String name2)
	{
		this.name2 = name2;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(final String phone)
	{
		this.phone = phone;
	}

	public String getPostal()
	{
		return postal;
	}

	public void setPostal(final String postal)
	{
		this.postal = postal;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(final String value)
	{
		this.value = value;
	}

	public String getVaTaxID()
	{
		return vaTaxID;
	}

	public void setVaTaxID(final String vaTaxID)
	{
		this.vaTaxID = vaTaxID;
	}

	public String getReferenceNo()
	{
		return referenceNo;
	}

	public void setReferenceNo(final String referenceNo)
	{
		this.referenceNo = referenceNo;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (address1 == null ? 0 : address1.hashCode());
		result = prime * result + (address2 == null ? 0 : address2.hashCode());
		result = prime * result + (cInvoiceID == null ? 0 : cInvoiceID.hashCode());
		result = prime * result + (cbPartnerLocationID == null ? 0 : cbPartnerLocationID.hashCode());
		result = prime * result + (city == null ? 0 : city.hashCode());
		result = prime * result + (countryCode == null ? 0 : countryCode.hashCode());
		result = prime * result + (eancomLocationtype == null ? 0 : eancomLocationtype.hashCode());
		result = prime * result + (fax == null ? 0 : fax.hashCode());
		result = prime * result + (gln == null ? 0 : gln.hashCode());
		result = prime * result + (mInOutID == null ? 0 : mInOutID.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (name2 == null ? 0 : name2.hashCode());
		result = prime * result + (phone == null ? 0 : phone.hashCode());
		result = prime * result + (postal == null ? 0 : postal.hashCode());
		result = prime * result + (referenceNo == null ? 0 : referenceNo.hashCode());
		result = prime * result + (vaTaxID == null ? 0 : vaTaxID.hashCode());
		result = prime * result + (value == null ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) // NOPMD by al
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
		final Cctop119V other = (Cctop119V)obj;
		if (address1 == null)
		{
			if (other.address1 != null)
			{
				return false;
			}
		}
		else if (!address1.equals(other.address1))
		{
			return false;
		}
		if (address2 == null)
		{
			if (other.address2 != null)
			{
				return false;
			}
		}
		else if (!address2.equals(other.address2))
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
		if (cbPartnerLocationID == null)
		{
			if (other.cbPartnerLocationID != null)
			{
				return false;
			}
		}
		else if (!cbPartnerLocationID.equals(other.cbPartnerLocationID))
		{
			return false;
		}
		if (city == null)
		{
			if (other.city != null)
			{
				return false;
			}
		}
		else if (!city.equals(other.city))
		{
			return false;
		}
		if (countryCode == null)
		{
			if (other.countryCode != null)
			{
				return false;
			}
		}
		else if (!countryCode.equals(other.countryCode))
		{
			return false;
		}
		if (eancomLocationtype == null)
		{
			if (other.eancomLocationtype != null)
			{
				return false;
			}
		}
		else if (!eancomLocationtype.equals(other.eancomLocationtype))
		{
			return false;
		}
		if (fax == null)
		{
			if (other.fax != null)
			{
				return false;
			}
		}
		else if (!fax.equals(other.fax))
		{
			return false;
		}
		if (gln == null)
		{
			if (other.gln != null)
			{
				return false;
			}
		}
		else if (!gln.equals(other.gln))
		{
			return false;
		}
		if (mInOutID == null)
		{
			if (other.mInOutID != null)
			{
				return false;
			}
		}
		else if (!mInOutID.equals(other.mInOutID))
		{
			return false;
		}
		if (name == null)
		{
			if (other.name != null)
			{
				return false;
			}
		}
		else if (!name.equals(other.name))
		{
			return false;
		}
		if (name2 == null)
		{
			if (other.name2 != null)
			{
				return false;
			}
		}
		else if (!name2.equals(other.name2))
		{
			return false;
		}
		if (phone == null)
		{
			if (other.phone != null)
			{
				return false;
			}
		}
		else if (!phone.equals(other.phone))
		{
			return false;
		}
		if (postal == null)
		{
			if (other.postal != null)
			{
				return false;
			}
		}
		else if (!postal.equals(other.postal))
		{
			return false;
		}
		if (referenceNo == null)
		{
			if (other.referenceNo != null)
			{
				return false;
			}
		}
		else if (!referenceNo.equals(other.referenceNo))
		{
			return false;
		}
		if (vaTaxID == null)
		{
			if (other.vaTaxID != null)
			{
				return false;
			}
		}
		else if (!vaTaxID.equals(other.vaTaxID))
		{
			return false;
		}
		if (value == null)
		{
			if (other.value != null)
			{
				return false;
			}
		}
		else if (!value.equals(other.value))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "Cctop119V [cbPartnerLocationID=" + cbPartnerLocationID + ", cInvoiceID=" + cInvoiceID + ", mInOutID=" + mInOutID + ", address1=" + address1 + ", address2="
				+ address2 + ", city="
				+ city + ", countryCode=" + countryCode + ", eancomLocationtype=" + eancomLocationtype + ", fax=" + fax + ", gln=" + gln + ", name=" + name + ", name2="
				+ name2 + ", phone=" + phone
				+ ", postal=" + postal + ", value=" + value + ", vaTaxID=" + vaTaxID + ", referenceNo=" + referenceNo + "]";
	}
}
