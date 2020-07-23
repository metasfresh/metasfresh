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
import java.util.List;

/**
 * POJO that maps the C_Invoice data for the EDI data-format
 *
 * @author al
 */
public class CctopInvoice implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 4129268669443776288L;

	/*
	 * metasfresh-mapped fields
	 */
	private String cbPartnerLocationID;
	private String cInvoiceID;
	private String creditMemoReason;
	private String creditMemoReasonText;
	private Date dateInvoiced;
	private Date dateOrdered;
	private String eancomDoctype;
	private String grandTotal;
	private String invoiceDocumentno;
	private String isoCode;
	private Date movementDate;
	private String poReference;
	private String receivergln;
	private String sendergln;
	private String shipmentDocumentno;
	private String vataxID;
	private String totalLines;
	private String totaltaxbaseamt;
	private String totalvat;
	private List<CctopInvoice500V> cctopInvoice500V;
	private Cctop000V cctop000V;
	private Cctop111V cctop111V;
	private List<Cctop119V> cctop119V;
	private List<Cctop120V> cctop120V;
	private List<Cctop140V> cctop140V;
	private List<Cctop901991V> cctop901991V;
	private String countryCode;
	private String countryCode3Digit;

	/*
	 * EDI-specific fields
	 */
	private Date currentDate;

	public String getCbPartnerLocationID()
	{
		return cbPartnerLocationID;
	}

	public void setCbPartnerLocationID(final String cbPartnerLocationID)
	{
		this.cbPartnerLocationID = cbPartnerLocationID;
	}

	public String getcInvoiceID()
	{
		return cInvoiceID;
	}

	public void setcInvoiceID(final String cInvoiceID)
	{
		this.cInvoiceID = cInvoiceID;
	}

	public String getCreditMemoReason()
	{
		return creditMemoReason;
	}

	public void setCreditMemoReason(final String creditMemoReason)
	{
		this.creditMemoReason = creditMemoReason;
	}

	public String getCreditMemoReasonText()
	{
		return creditMemoReasonText;
	}

	public void setCreditMemoReasonText(final String creditMemoReasonText)
	{
		this.creditMemoReasonText = creditMemoReasonText;
	}

	public Date getDateInvoiced()
	{
		return dateInvoiced;
	}

	public void setDateInvoiced(final Date dateInvoiced)
	{
		this.dateInvoiced = dateInvoiced;
	}

	public Date getDateOrdered()
	{
		return dateOrdered;
	}

	public void setDateOrdered(final Date dateOrdered)
	{
		this.dateOrdered = dateOrdered;
	}

	public String getEancomDoctype()
	{
		return eancomDoctype;
	}

	public void setEancomDoctype(final String eancomDoctype)
	{
		this.eancomDoctype = eancomDoctype;
	}

	public String getGrandTotal()
	{
		return grandTotal;
	}

	public void setGrandTotal(final String grandTotal)
	{
		this.grandTotal = grandTotal;
	}

	public String getInvoiceDocumentno()
	{
		return invoiceDocumentno;
	}

	public void setInvoiceDocumentno(final String invoiceDocumentno)
	{
		this.invoiceDocumentno = invoiceDocumentno;
	}

	public String getIsoCode()
	{
		return isoCode;
	}

	public void setIsoCode(final String isoCode)
	{
		this.isoCode = isoCode;
	}

	public Date getMovementDate()
	{
		return movementDate;
	}

	public void setMovementDate(final Date movementDate)
	{
		this.movementDate = movementDate;
	}

	public String getPoReference()
	{
		return poReference;
	}

	public void setPoReference(final String poReference)
	{
		this.poReference = poReference;
	}

	public String getReceivergln()
	{
		return receivergln;
	}

	public void setReceivergln(final String receivergln)
	{
		this.receivergln = receivergln;
	}

	public String getSendergln()
	{
		return sendergln;
	}

	public void setSendergln(final String sendergln)
	{
		this.sendergln = sendergln;
	}

	public String getShipmentDocumentno()
	{
		return shipmentDocumentno;
	}

	public void setShipmentDocumentno(final String shipmentDocumentno)
	{
		this.shipmentDocumentno = shipmentDocumentno;
	}

	public String getVataxID()
	{
		return vataxID;
	}

	public void setVataxID(final String vataxID)
	{
		this.vataxID = vataxID;
	}

	public String getTotalLines()
	{
		return totalLines;
	}

	public void setTotalLines(final String totalLines)
	{
		this.totalLines = totalLines;
	}

	public String getTotaltaxbaseamt()
	{
		return totaltaxbaseamt;
	}

	public void setTotaltaxbaseamt(final String totaltaxbaseamt)
	{
		this.totaltaxbaseamt = totaltaxbaseamt;
	}

	public String getTotalvat()
	{
		return totalvat;
	}

	public void setTotalvat(final String totalvat)
	{
		this.totalvat = totalvat;
	}

	public List<CctopInvoice500V> getCctopInvoice500V()
	{
		return cctopInvoice500V;
	}

	public void setCctopInvoice500V(final List<CctopInvoice500V> cctopInvoice500V)
	{
		this.cctopInvoice500V = cctopInvoice500V;
	}

	public Cctop000V getCctop000V()
	{
		return cctop000V;
	}

	public void setCctop000V(final Cctop000V cctop000v)
	{
		cctop000V = cctop000v;
	}

	public Cctop111V getCctop111V()
	{
		return cctop111V;
	}

	public void setCctop111V(final Cctop111V cctop111v)
	{
		cctop111V = cctop111v;
	}

	public List<Cctop119V> getCctop119V()
	{
		return cctop119V;
	}

	public void setCctop119V(final List<Cctop119V> cctop119v)
	{
		cctop119V = cctop119v;
	}

	public List<Cctop120V> getCctop120V()
	{
		return cctop120V;
	}

	public void setCctop120V(final List<Cctop120V> cctop120v)
	{
		cctop120V = cctop120v;
	}

	public List<Cctop140V> getCctop140V()
	{
		return cctop140V;
	}

	public void setCctop140V(final List<Cctop140V> cctop140v)
	{
		cctop140V = cctop140v;
	}

	public List<Cctop901991V> getCctop901991V()
	{
		return cctop901991V;
	}

	public void setCctop901991V(final List<Cctop901991V> cctop901991v)
	{
		cctop901991V = cctop901991v;
	}

	public Date getCurrentDate()
	{
		return currentDate;
	}

	public void setCurrentDate(final Date currentDate)
	{
		this.currentDate = currentDate;
	}

	public String getCountryCode()
	{
		return countryCode;
	}

	public void setCountryCode(final String countryCode)
	{
		this.countryCode = countryCode;
	}

	public String getCountryCode3Digit()
	{
		return countryCode3Digit;
	}

	public void setCountryCode3Digit(final String countryCode3Digit)
	{
		this.countryCode3Digit = countryCode3Digit;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (cInvoiceID == null ? 0 : cInvoiceID.hashCode());
		result = prime * result + (cbPartnerLocationID == null ? 0 : cbPartnerLocationID.hashCode());
		result = prime * result + (cctop000V == null ? 0 : cctop000V.hashCode());
		result = prime * result + (cctop111V == null ? 0 : cctop111V.hashCode());
		result = prime * result + (cctop119V == null ? 0 : cctop119V.hashCode());
		result = prime * result + (cctop120V == null ? 0 : cctop120V.hashCode());
		result = prime * result + (cctop140V == null ? 0 : cctop140V.hashCode());
		result = prime * result + (cctop901991V == null ? 0 : cctop901991V.hashCode());
		result = prime * result + (cctopInvoice500V == null ? 0 : cctopInvoice500V.hashCode());
		result = prime * result + (countryCode == null ? 0 : countryCode.hashCode());
		result = prime * result + (countryCode3Digit == null ? 0 : countryCode3Digit.hashCode());
		result = prime * result + (creditMemoReason == null ? 0 : creditMemoReason.hashCode());
		result = prime * result + (creditMemoReasonText == null ? 0 : creditMemoReasonText.hashCode());
		result = prime * result + (currentDate == null ? 0 : currentDate.hashCode());
		result = prime * result + (dateInvoiced == null ? 0 : dateInvoiced.hashCode());
		result = prime * result + (dateOrdered == null ? 0 : dateOrdered.hashCode());
		result = prime * result + (eancomDoctype == null ? 0 : eancomDoctype.hashCode());
		result = prime * result + (grandTotal == null ? 0 : grandTotal.hashCode());
		result = prime * result + (invoiceDocumentno == null ? 0 : invoiceDocumentno.hashCode());
		result = prime * result + (isoCode == null ? 0 : isoCode.hashCode());
		result = prime * result + (movementDate == null ? 0 : movementDate.hashCode());
		result = prime * result + (poReference == null ? 0 : poReference.hashCode());
		result = prime * result + (receivergln == null ? 0 : receivergln.hashCode());
		result = prime * result + (sendergln == null ? 0 : sendergln.hashCode());
		result = prime * result + (shipmentDocumentno == null ? 0 : shipmentDocumentno.hashCode());
		result = prime * result + (totalLines == null ? 0 : totalLines.hashCode());
		result = prime * result + (totaltaxbaseamt == null ? 0 : totaltaxbaseamt.hashCode());
		result = prime * result + (totalvat == null ? 0 : totalvat.hashCode());
		result = prime * result + (vataxID == null ? 0 : vataxID.hashCode());
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
		final CctopInvoice other = (CctopInvoice)obj;
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
		if (cctop000V == null)
		{
			if (other.cctop000V != null)
			{
				return false;
			}
		}
		else if (!cctop000V.equals(other.cctop000V))
		{
			return false;
		}
		if (cctop111V == null)
		{
			if (other.cctop111V != null)
			{
				return false;
			}
		}
		else if (!cctop111V.equals(other.cctop111V))
		{
			return false;
		}
		if (cctop119V == null)
		{
			if (other.cctop119V != null)
			{
				return false;
			}
		}
		else if (!cctop119V.equals(other.cctop119V))
		{
			return false;
		}
		if (cctop120V == null)
		{
			if (other.cctop120V != null)
			{
				return false;
			}
		}
		else if (!cctop120V.equals(other.cctop120V))
		{
			return false;
		}
		if (cctop140V == null)
		{
			if (other.cctop140V != null)
			{
				return false;
			}
		}
		else if (!cctop140V.equals(other.cctop140V))
		{
			return false;
		}
		if (cctop901991V == null)
		{
			if (other.cctop901991V != null)
			{
				return false;
			}
		}
		else if (!cctop901991V.equals(other.cctop901991V))
		{
			return false;
		}
		if (cctopInvoice500V == null)
		{
			if (other.cctopInvoice500V != null)
			{
				return false;
			}
		}
		else if (!cctopInvoice500V.equals(other.cctopInvoice500V))
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
		if (countryCode3Digit == null)
		{
			if (other.countryCode3Digit != null)
			{
				return false;
			}
		}
		else if (!countryCode3Digit.equals(other.countryCode3Digit))
		{
			return false;
		}
		if (creditMemoReason == null)
		{
			if (other.creditMemoReason != null)
			{
				return false;
			}
		}
		else if (!creditMemoReason.equals(other.creditMemoReason))
		{
			return false;
		}
		if (creditMemoReasonText == null)
		{
			if (other.creditMemoReasonText != null)
			{
				return false;
			}
		}
		else if (!creditMemoReasonText.equals(other.creditMemoReasonText))
		{
			return false;
		}
		if (currentDate == null)
		{
			if (other.currentDate != null)
			{
				return false;
			}
		}
		else if (!currentDate.equals(other.currentDate))
		{
			return false;
		}
		if (dateInvoiced == null)
		{
			if (other.dateInvoiced != null)
			{
				return false;
			}
		}
		else if (!dateInvoiced.equals(other.dateInvoiced))
		{
			return false;
		}
		if (dateOrdered == null)
		{
			if (other.dateOrdered != null)
			{
				return false;
			}
		}
		else if (!dateOrdered.equals(other.dateOrdered))
		{
			return false;
		}
		if (eancomDoctype == null)
		{
			if (other.eancomDoctype != null)
			{
				return false;
			}
		}
		else if (!eancomDoctype.equals(other.eancomDoctype))
		{
			return false;
		}
		if (grandTotal == null)
		{
			if (other.grandTotal != null)
			{
				return false;
			}
		}
		else if (!grandTotal.equals(other.grandTotal))
		{
			return false;
		}
		if (invoiceDocumentno == null)
		{
			if (other.invoiceDocumentno != null)
			{
				return false;
			}
		}
		else if (!invoiceDocumentno.equals(other.invoiceDocumentno))
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
		if (movementDate == null)
		{
			if (other.movementDate != null)
			{
				return false;
			}
		}
		else if (!movementDate.equals(other.movementDate))
		{
			return false;
		}
		if (poReference == null)
		{
			if (other.poReference != null)
			{
				return false;
			}
		}
		else if (!poReference.equals(other.poReference))
		{
			return false;
		}
		if (receivergln == null)
		{
			if (other.receivergln != null)
			{
				return false;
			}
		}
		else if (!receivergln.equals(other.receivergln))
		{
			return false;
		}
		if (sendergln == null)
		{
			if (other.sendergln != null)
			{
				return false;
			}
		}
		else if (!sendergln.equals(other.sendergln))
		{
			return false;
		}
		if (shipmentDocumentno == null)
		{
			if (other.shipmentDocumentno != null)
			{
				return false;
			}
		}
		else if (!shipmentDocumentno.equals(other.shipmentDocumentno))
		{
			return false;
		}
		if (totalLines == null)
		{
			if (other.totalLines != null)
			{
				return false;
			}
		}
		else if (!totalLines.equals(other.totalLines))
		{
			return false;
		}
		if (totaltaxbaseamt == null)
		{
			if (other.totaltaxbaseamt != null)
			{
				return false;
			}
		}
		else if (!totaltaxbaseamt.equals(other.totaltaxbaseamt))
		{
			return false;
		}
		if (totalvat == null)
		{
			if (other.totalvat != null)
			{
				return false;
			}
		}
		else if (!totalvat.equals(other.totalvat))
		{
			return false;
		}
		if (vataxID == null)
		{
			if (other.vataxID != null)
			{
				return false;
			}
		}
		else if (!vataxID.equals(other.vataxID))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "CctopInvoice [cbPartnerLocationID=" + cbPartnerLocationID + ", cInvoiceID=" + cInvoiceID + ", creditMemoReason=" + creditMemoReason + ", creditMemoReasonText=" + creditMemoReasonText
				+ ", dateInvoiced=" + dateInvoiced + ", dateOrdered=" + dateOrdered + ", eancomDoctype=" + eancomDoctype + ", grandTotal=" + grandTotal + ", invoiceDocumentno=" + invoiceDocumentno
				+ ", isoCode=" + isoCode + ", movementDate=" + movementDate + ", poReference=" + poReference + ", receivergln=" + receivergln + ", sendergln=" + sendergln + ", shipmentDocumentno="
				+ shipmentDocumentno + ", vataxID=" + vataxID + ", totalLines=" + totalLines + ", totaltaxbaseamt=" + totaltaxbaseamt + ", totalvat=" + totalvat
				+ ", cctopInvoice500V=" + cctopInvoice500V + ", cctop000V=" + cctop000V + ", cctop111V=" + cctop111V + ", cctop119V=" + cctop119V + ", cctop120V=" + cctop120V + ", cctop140V="
				+ cctop140V + ", cctop901991V=" + cctop901991V + ", countryCode=" + countryCode + ", countryCode3Digit=" + countryCode3Digit + ", currentDate=" + currentDate + "]";
	}
}
