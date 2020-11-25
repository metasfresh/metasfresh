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

public class CctopInvoice500V implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3382144262679233645L;

	private String cInvoiceID;

	private String eancomUom;
	private String eancomPriceUom;
	private String isoCode;
	private String line;
	private String lineNetAmt;
	private String name;
	private String name2;
	private String priceActual;
	private String priceList;
	private String qtyInvoiced;
	private String rate;
	private String lineGrossAmt;
	private String taxfree;
	private String upc;
	private String value;
	private String vendorProductNo;
	private String leergut;
	private String productDescription;
	private String orderPOReference;
	private String orderLine;
	private String taxAmount;

	public final String getcInvoiceID()
	{
		return cInvoiceID;
	}

	public final void setcInvoiceID(final String cInvoiceID)
	{
		this.cInvoiceID = cInvoiceID;
	}

	public String getEancomUom()
	{
		return eancomUom;
	}

	public void setEancomUom(final String eancomUom)
	{
		this.eancomUom = eancomUom;
	}

	public String getEancomPriceUom()
	{
		return eancomPriceUom;
	}

	public void setEancomPriceUom(final String eancomPriceUom)
	{
		this.eancomPriceUom = eancomPriceUom;
	}

	public String getIsoCode()
	{
		return isoCode;
	}

	public void setIsoCode(final String isoCode)
	{
		this.isoCode = isoCode;
	}

	public String getLine()
	{
		return line;
	}

	public void setLine(final String line)
	{
		this.line = line;
	}

	public String getLineNetAmt()
	{
		return lineNetAmt;
	}

	public void setLineNetAmt(final String lineNetAmt)
	{
		this.lineNetAmt = lineNetAmt;
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

	public String getPriceActual()
	{
		return priceActual;
	}

	public void setPriceActual(final String priceActual)
	{
		this.priceActual = priceActual;
	}

	public String getPriceList()
	{
		return priceList;
	}

	public void setPriceList(final String priceList)
	{
		this.priceList = priceList;
	}

	public String getQtyInvoiced()
	{
		return qtyInvoiced;
	}

	public void setQtyInvoiced(final String qtyInvoiced)
	{
		this.qtyInvoiced = qtyInvoiced;
	}

	public String getRate()
	{
		return rate;
	}

	public void setRate(final String rate)
	{
		this.rate = rate;
	}

	public String getLineGrossAmt()
	{
		return lineGrossAmt;
	}

	public void setLineGrossAmt(final String lineGrossAmt)
	{
		this.lineGrossAmt = lineGrossAmt;
	}

	public String getTaxfree()
	{
		return taxfree;
	}

	public void setTaxfree(final String taxfree)
	{
		this.taxfree = taxfree;
	}

	public String getUpc()
	{
		return upc;
	}

	public void setUpc(final String upc)
	{
		this.upc = upc;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(final String value)
	{
		this.value = value;
	}

	public String getVendorProductNo()
	{
		return vendorProductNo;
	}

	public void setVendorProductNo(final String vendorProductNo)
	{
		this.vendorProductNo = vendorProductNo;
	}

	public String getLeergut()
	{
		return leergut;
	}

	public void setLeergut(final String leergut)
	{
		this.leergut = leergut;
	}

	public String getProductDescription()
	{
		return productDescription;
	}

	public void setProductDescription(final String productDescription)
	{
		this.productDescription = productDescription;
	}

	public String getOrderPOReference()
	{
		return orderPOReference;
	}

	public void setOrderPOReference(final String orderPOReference)
	{
		this.orderPOReference = orderPOReference;
	}

	public String getOrderLine()
	{
		return orderLine;
	}

	public void setOrderLine(final String orderLine)
	{
		this.orderLine = orderLine;
	}

	public String getTaxAmount()
	{
		return taxAmount;
	}

	public void setTaxAmount(final String taxAmtInfo)
	{
		taxAmount = taxAmtInfo;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cInvoiceID == null) ? 0 : cInvoiceID.hashCode());
		result = prime * result + ((eancomPriceUom == null) ? 0 : eancomPriceUom.hashCode());
		result = prime * result + ((eancomUom == null) ? 0 : eancomUom.hashCode());
		result = prime * result + ((isoCode == null) ? 0 : isoCode.hashCode());
		result = prime * result + ((leergut == null) ? 0 : leergut.hashCode());
		result = prime * result + ((line == null) ? 0 : line.hashCode());
		result = prime * result + ((lineGrossAmt == null) ? 0 : lineGrossAmt.hashCode());
		result = prime * result + ((lineNetAmt == null) ? 0 : lineNetAmt.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((name2 == null) ? 0 : name2.hashCode());
		result = prime * result + ((orderLine == null) ? 0 : orderLine.hashCode());
		result = prime * result + ((orderPOReference == null) ? 0 : orderPOReference.hashCode());
		result = prime * result + ((priceActual == null) ? 0 : priceActual.hashCode());
		result = prime * result + ((priceList == null) ? 0 : priceList.hashCode());
		result = prime * result + ((productDescription == null) ? 0 : productDescription.hashCode());
		result = prime * result + ((qtyInvoiced == null) ? 0 : qtyInvoiced.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + ((taxAmount == null) ? 0 : taxAmount.hashCode());
		result = prime * result + ((taxfree == null) ? 0 : taxfree.hashCode());
		result = prime * result + ((upc == null) ? 0 : upc.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((vendorProductNo == null) ? 0 : vendorProductNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CctopInvoice500V other = (CctopInvoice500V)obj;
		if (cInvoiceID == null)
		{
			if (other.cInvoiceID != null)
				return false;
		}
		else if (!cInvoiceID.equals(other.cInvoiceID))
			return false;
		if (eancomPriceUom == null)
		{
			if (other.eancomPriceUom != null)
				return false;
		}
		else if (!eancomPriceUom.equals(other.eancomPriceUom))
			return false;
		if (eancomUom == null)
		{
			if (other.eancomUom != null)
				return false;
		}
		else if (!eancomUom.equals(other.eancomUom))
			return false;
		if (isoCode == null)
		{
			if (other.isoCode != null)
				return false;
		}
		else if (!isoCode.equals(other.isoCode))
			return false;
		if (leergut == null)
		{
			if (other.leergut != null)
				return false;
		}
		else if (!leergut.equals(other.leergut))
			return false;
		if (line == null)
		{
			if (other.line != null)
				return false;
		}
		else if (!line.equals(other.line))
			return false;
		if (lineGrossAmt == null)
		{
			if (other.lineGrossAmt != null)
				return false;
		}
		else if (!lineGrossAmt.equals(other.lineGrossAmt))
			return false;
		if (lineNetAmt == null)
		{
			if (other.lineNetAmt != null)
				return false;
		}
		else if (!lineNetAmt.equals(other.lineNetAmt))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (name2 == null)
		{
			if (other.name2 != null)
				return false;
		}
		else if (!name2.equals(other.name2))
			return false;
		if (orderLine == null)
		{
			if (other.orderLine != null)
				return false;
		}
		else if (!orderLine.equals(other.orderLine))
			return false;
		if (orderPOReference == null)
		{
			if (other.orderPOReference != null)
				return false;
		}
		else if (!orderPOReference.equals(other.orderPOReference))
			return false;
		if (priceActual == null)
		{
			if (other.priceActual != null)
				return false;
		}
		else if (!priceActual.equals(other.priceActual))
			return false;
		if (priceList == null)
		{
			if (other.priceList != null)
				return false;
		}
		else if (!priceList.equals(other.priceList))
			return false;
		if (productDescription == null)
		{
			if (other.productDescription != null)
				return false;
		}
		else if (!productDescription.equals(other.productDescription))
			return false;
		if (qtyInvoiced == null)
		{
			if (other.qtyInvoiced != null)
				return false;
		}
		else if (!qtyInvoiced.equals(other.qtyInvoiced))
			return false;
		if (rate == null)
		{
			if (other.rate != null)
				return false;
		}
		else if (!rate.equals(other.rate))
			return false;
		if (taxAmount == null)
		{
			if (other.taxAmount != null)
				return false;
		}
		else if (!taxAmount.equals(other.taxAmount))
			return false;
		if (taxfree == null)
		{
			if (other.taxfree != null)
				return false;
		}
		else if (!taxfree.equals(other.taxfree))
			return false;
		if (upc == null)
		{
			if (other.upc != null)
				return false;
		}
		else if (!upc.equals(other.upc))
			return false;
		if (value == null)
		{
			if (other.value != null)
				return false;
		}
		else if (!value.equals(other.value))
			return false;
		if (vendorProductNo == null)
		{
			if (other.vendorProductNo != null)
				return false;
		}
		else if (!vendorProductNo.equals(other.vendorProductNo))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "CctopInvoice500V [cInvoiceID=" + cInvoiceID + ", eancomUom=" + eancomUom + ", isoCode=" + isoCode + ", line=" + line + ", lineNetAmt=" + lineNetAmt + ", name=" + name + ", name2="
				+ name2 + ", priceActual=" + priceActual + ", priceList=" + priceList + ", qtyInvoiced=" + qtyInvoiced + ", rate=" + rate + ", lineGrossAmt=" + lineGrossAmt + ", taxfree=" + taxfree
				+ ", upc=" + upc + ", value=" + value + ", vendorProductNo=" + vendorProductNo + ", leergut=" + leergut + ", productDescription=" + productDescription + ", orderLine=" + orderLine
				+ ", taxAmtInfo=" + taxAmount + "]";
	}

}
