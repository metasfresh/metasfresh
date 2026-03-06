package de.metas.edi.esb.xls;

/*
 * #%L
 * de.metas.edi.esb
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

import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.excelimport.Excel_OLCand_Row;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIImpCCurrencyLookupISOCodeType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIImpCUOMLookupUOMSymbolType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.XLSImpCOLCandType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Defines and asserts expectations on an {@link Excel_OLCand_Row} or {@link XLSImpCOLCandType}.
 *
 * @author tsa
 *
 */
public class XLS_OLCand_Row_Expectation
{
	private Integer lineNo;
	private boolean lineNoSet;
	private String productDescription;
	private boolean productDescriptionSet;
	private String uom_x12de355;
	private boolean uom_x12de355_set;
	private Integer M_Product_ID;
	private Integer M_ProductPrice_ID;
	private Integer M_ProductPrice_Attribute_ID;
	private Integer M_HU_PI_Item_Product_ID;
	//
	private Integer C_BPartner_ID;
	private Integer C_BPartner_Location_ID;
	//
	private String currencyISOCode;
	private BigDecimal price;
	//
	private String poReference;
	private boolean poReferenceSet;
	//
	private Date datePromised;
	//
	private BigDecimal qtyTUs;
	private BigDecimal qtyCUsPerTU;

	/** This qty is the CU-qty only if M_HU_PI_Item_Product_ID is not set. */
	private BigDecimal qtyUOM;

	private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	public void assertExpected(final Excel_OLCand_Row row)
	{
		final String messageSuffix = "\nRow: " + row
				+ "\n";

		if (lineNoSet)
		{
			assertThat(row.getLineNo()).as("LineNo" + messageSuffix).isEqualTo(lineNo);
		}
		if (productDescriptionSet)
		{
			assertThat(row.getProductDescription()).as("ProductDescription" + messageSuffix).isEqualTo(productDescription);
		}
		if (uom_x12de355_set)
		{
			assertThat(row.getUOM_x12de355()).as("UOM" + messageSuffix).isEqualTo(uom_x12de355);
		}

		if (M_Product_ID != null)
		{
			assertThat(row.getM_Product_ID()).as("M_Product_ID" + messageSuffix).isEqualTo(M_Product_ID);
		}
		if (M_ProductPrice_ID != null)
		{
			assertThat(row.getM_ProductPrice_ID()).as("M_ProductPrice_ID" + messageSuffix).isEqualTo(M_ProductPrice_ID);
		}
		if (M_ProductPrice_Attribute_ID != null)
		{
			assertThat(row.getM_ProductPrice_Attribute_ID()).as("M_ProductPrice_Attribute_ID" + messageSuffix).isEqualTo(M_ProductPrice_Attribute_ID);
		}
		if (M_HU_PI_Item_Product_ID != null)
		{
			assertThat(row.getM_HU_PI_Item_Product_ID()).as("M_HU_PI_Item_Product_ID" + messageSuffix).isEqualTo(M_HU_PI_Item_Product_ID);
		}

		if (C_BPartner_ID != null)
		{
			assertThat(row.getC_BPartner_ID()).as("C_BPartner_ID" + messageSuffix).isEqualTo(C_BPartner_ID);
		}
		if (C_BPartner_Location_ID != null)
		{
			assertThat(row.getC_BPartner_Location_ID()).as("C_BPartner_Location_ID" + messageSuffix).isEqualTo(C_BPartner_Location_ID);
		}

		if (price != null)
		{
			assertThat(row.getPrice()).as("Price" + messageSuffix).isEqualByComparingTo(price);
		}
		if (currencyISOCode != null)
		{
			assertThat(row.getCurrencyISOCode()).as("Currency" + messageSuffix).isEqualTo(currencyISOCode);
		}

		if (qtyTUs != null)
		{
			assertThat(row.getQtyTUs()).as("QtyTUs" + messageSuffix).isEqualByComparingTo(qtyTUs);
		}
		if (qtyCUsPerTU != null)
		{
			assertThat(row.getQtyCUsPerTU()).as("QtyCUsPerTU" + messageSuffix).isEqualByComparingTo(qtyCUsPerTU);
		}
		if (qtyUOM != null)
		{
			assertThat(row.getQtyInUOM()).as("QtyUOM" + messageSuffix).isEqualByComparingTo(qtyUOM);
		}

		if (datePromised != null)
		{
			assertThat(row.getDatePromised()).as("DatePromised" + messageSuffix).isEqualTo(datePromised);
		}
		if (poReferenceSet)
		{
			assertThat(row.getPOReference()).as("POReference" + messageSuffix).isEqualTo(poReference);
		}
	}

	public void assertExpected(final XLSImpCOLCandType xml)
	{
		final String messageSuffix = "\nXML: " + xml
				+ "\n";

		assertThat(xml).as("XML not null").isNotNull();
		if (lineNoSet)
		{
			assertThat(xml.getLine()).as("LineNo" + messageSuffix).isEqualTo(BigInteger.valueOf(lineNo));
		}
		if (productDescriptionSet)
		{
			assertThat(xml.getProductDescription()).as("ProductDescription" + messageSuffix).isEqualTo(productDescription);
		}
		if (uom_x12de355_set)
		{
			final EDIImpCUOMLookupUOMSymbolType uomLookup = xml.getCUOMID();
			final String uom_x12de355_actual = uomLookup == null ? null : uomLookup.getX12DE355();
			assertThat(uom_x12de355_actual).as("UOM_x12de355" + messageSuffix).isEqualTo(uom_x12de355);
		}

		if (M_Product_ID != null)
		{
			assertThat(xml.getMProductID()).as("M_Product_ID" + messageSuffix).isEqualTo(BigInteger.valueOf(M_Product_ID));
		}
		if (M_ProductPrice_ID != null)
		{
			assertThat(xml.getMProductPriceID()).as("M_ProductPrice_ID" + messageSuffix).isEqualTo(BigInteger.valueOf(M_ProductPrice_ID));
		}

		assertThat(xml.getIsExplicitProductPriceAttribute()).as("IsExplicitProductPriceAttribute" + messageSuffix).isEqualTo("Y");
		if (M_ProductPrice_Attribute_ID != null)
		{
			assertThat(xml.getMProductPriceAttributeID()).as("M_ProductPrice_Attribute_ID" + messageSuffix).isEqualTo(BigInteger.valueOf(M_ProductPrice_Attribute_ID));
		}

		if (M_HU_PI_Item_Product_ID != null)
		{
			assertThat(xml.getMHUPIItemProductID()).as("M_HU_PI_Item_Product_ID" + messageSuffix).isEqualTo(BigInteger.valueOf(M_HU_PI_Item_Product_ID));
		}

		if (C_BPartner_ID != null)
		{
			assertThat(xml.getCBPartnerID()).as("C_BPartner_ID" + messageSuffix).isEqualTo(BigInteger.valueOf(C_BPartner_ID));
		}
		if (C_BPartner_Location_ID != null)
		{
			assertThat(xml.getCBPartnerLocationID()).as("C_BPartner_Location_ID" + messageSuffix).isEqualTo(BigInteger.valueOf(C_BPartner_Location_ID));
		}

		if (price != null)
		{
			assertThat(xml.getPriceEntered()).as("Price" + messageSuffix).isEqualByComparingTo(price);
		}
		if (currencyISOCode != null)
		{
			final EDIImpCCurrencyLookupISOCodeType currencyLookup = xml.getCCurrencyID();
			final String currencyISOCode_actual = currencyLookup == null ? null : currencyLookup.getISOCode();
			assertThat(currencyISOCode_actual).as("Currency" + messageSuffix).isEqualTo(currencyISOCode);
		}

		// N/A
		// if (qtyTUs != null)
		// assertThat(xml.getQtyTU()).as("QtyTUs" + messageSuffix).isEqualByComparingTo(qtyTUs);
		if (qtyCUsPerTU != null)
		{
			assertThat(xml.getQtyItemCapacity()).as("QtyCUsPerTU" + messageSuffix).isEqualByComparingTo(qtyCUsPerTU);
		}
		if (qtyUOM != null)
		{
			assertThat(xml.getQty()).as("QtyUOM" + messageSuffix).isEqualByComparingTo(qtyUOM);
		}

		if (datePromised != null)
		{
			assertThat(Util.toDate(xml.getDatePromised())).as("DatePromised" + messageSuffix).isEqualTo(datePromised);
		}
		if (poReferenceSet)
		{
			assertThat(xml.getPOReference()).as("POReference" + messageSuffix).isEqualTo(poReference);
		}
	}

	/**
	 * Sets Excel row number used to identify the row
	 */
	public XLS_OLCand_Row_Expectation setLineNo(final int lineNo)
	{
		this.lineNo = lineNo;
		return this;
	}

	public int getLineNo()
	{
		return lineNo;
	}

	public XLS_OLCand_Row_Expectation setProductDescription(final String productDescription)
	{
		this.productDescription = productDescription;
		return this;
	}

	public XLS_OLCand_Row_Expectation setUOM_x12de355(final String uom_x12de355)
	{
		this.uom_x12de355 = uom_x12de355;
		return this;
	}

	public XLS_OLCand_Row_Expectation setM_Product_ID(final int M_Product_ID)
	{
		this.M_Product_ID = M_Product_ID;
		return this;
	}

	public XLS_OLCand_Row_Expectation setM_ProductPrice_ID(final int M_ProductPrice_ID)
	{
		this.M_ProductPrice_ID = M_ProductPrice_ID;
		return this;
	}

	public XLS_OLCand_Row_Expectation setM_ProductPrice_Attribute_ID(final int M_ProductPrice_Attribute_ID)
	{
		this.M_ProductPrice_Attribute_ID = M_ProductPrice_Attribute_ID;
		return this;
	}

	public XLS_OLCand_Row_Expectation setM_HU_PI_Item_Product_ID(final int M_HU_PI_ItemProduct_ID)
	{
		M_HU_PI_Item_Product_ID = M_HU_PI_ItemProduct_ID;
		return this;
	}

	public XLS_OLCand_Row_Expectation setC_BPartner_ID(final int C_BPartner_ID)
	{
		this.C_BPartner_ID = C_BPartner_ID;
		return this;
	}

	public XLS_OLCand_Row_Expectation setC_BPartner_Location_ID(final int C_BPartner_Location_ID)
	{
		this.C_BPartner_Location_ID = C_BPartner_Location_ID;
		return this;
	}

	public XLS_OLCand_Row_Expectation setPrice(final String priceStr)
	{
		price = new BigDecimal(priceStr);
		return this;
	}

	public XLS_OLCand_Row_Expectation setQtyTUs(final int qtyTUs)
	{
		this.qtyTUs = BigDecimal.valueOf(qtyTUs);
		return this;
	}

	public XLS_OLCand_Row_Expectation setQtyCUsPerTU(final String qtyCUsPerTU)
	{
		this.qtyCUsPerTU = new BigDecimal(qtyCUsPerTU);
		return this;
	}

	public XLS_OLCand_Row_Expectation setQtyCUsPerTU(final int qtyCUsPerTU)
	{
		this.qtyCUsPerTU = BigDecimal.valueOf(qtyCUsPerTU);
		return this;
	}

	public XLS_OLCand_Row_Expectation setQtyUOM(final String qtyUOM)
	{
		this.qtyUOM = new BigDecimal(qtyUOM);
		return this;
	}

	public XLS_OLCand_Row_Expectation setQtyUOM(final int qtyUOM)
	{
		this.qtyUOM = BigDecimal.valueOf(qtyUOM);
		return this;
	}

	public XLS_OLCand_Row_Expectation setCurrencyISOCode(final String currencyISOCode)
	{
		this.currencyISOCode = currencyISOCode;
		return this;
	}

	public XLS_OLCand_Row_Expectation setDatePromised(final String datePromisedStr)
	{
		try
		{
			datePromised = dateFormat.parse(datePromisedStr);
		}
		catch (final ParseException e)
		{
			throw new RuntimeException("Failed parsing " + datePromisedStr, e);
		}
		return this;
	}

	public XLS_OLCand_Row_Expectation setPOReference(final String poReference)
	{
		this.poReference = poReference;
		this.poReferenceSet = true;
		return this;
	}
}
