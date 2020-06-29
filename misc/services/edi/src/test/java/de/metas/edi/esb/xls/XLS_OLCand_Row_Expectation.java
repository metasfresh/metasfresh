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


import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Assert;

import de.metas.edi.esb.bean.imports.excel.Excel_OLCand_Row;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.metasfresh.EDIImpCCurrencyLookupISOCodeType;
import de.metas.edi.esb.jaxb.metasfresh.EDIImpCUOMLookupUOMSymbolType;
import de.metas.edi.esb.jaxb.metasfresh.XLSImpCOLCandType;

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
			Assert.assertEquals("LineNo" + messageSuffix, lineNo, row.getLineNo());
		}
		if (productDescriptionSet)
		{
			Assert.assertEquals("ProductDescription" + messageSuffix, productDescription, row.getProductDescription());
		}
		if (uom_x12de355_set)
		{
			Assert.assertEquals("UOM" + messageSuffix, uom_x12de355, row.getUOM_x12de355());
		}

		if (M_Product_ID != null)
		{
			Assert.assertEquals("M_Product_ID" + messageSuffix, M_Product_ID, (Integer)row.getM_Product_ID());
		}
		if (M_ProductPrice_ID != null)
		{
			Assert.assertEquals("M_ProductPrice_ID" + messageSuffix, M_ProductPrice_ID, (Integer)row.getM_ProductPrice_ID());
		}
		if (M_ProductPrice_Attribute_ID != null)
		{
			Assert.assertEquals("M_ProductPrice_Attribute_ID" + messageSuffix, M_ProductPrice_Attribute_ID, (Integer)row.getM_ProductPrice_Attribute_ID());
		}
		if (M_HU_PI_Item_Product_ID != null)
		{
			Assert.assertEquals("M_HU_PI_Item_Product_ID" + messageSuffix, M_HU_PI_Item_Product_ID, (Integer)row.getM_HU_PI_Item_Product_ID());
		}

		if (C_BPartner_ID != null)
		{
			Assert.assertEquals("C_BPartner_ID" + messageSuffix, C_BPartner_ID, (Integer)row.getC_BPartner_ID());
		}
		if (C_BPartner_Location_ID != null)
		{
			Assert.assertEquals("C_BPartner_Location_ID" + messageSuffix, C_BPartner_Location_ID, (Integer)row.getC_BPartner_Location_ID());
		}

		if (price != null)
		{
			Assert.assertThat("Price" + messageSuffix, row.getPrice(), Matchers.comparesEqualTo(price));
		}
		if (currencyISOCode != null)
		{
			Assert.assertThat("Currency" + messageSuffix, row.getCurrencyISOCode(), Matchers.equalTo(currencyISOCode));
		}

		if (qtyTUs != null)
		{
			Assert.assertThat("QtyTUs" + messageSuffix, row.getQtyTUs(), Matchers.comparesEqualTo(qtyTUs));
		}
		if (qtyCUsPerTU != null)
		{
			Assert.assertThat("QtyCUsPerTU" + messageSuffix, row.getQtyCUsPerTU(), Matchers.comparesEqualTo(qtyCUsPerTU));
		}
		if (qtyUOM != null)
		{
			Assert.assertThat("QtyUOM" + messageSuffix, row.getQtyUOM(), Matchers.comparesEqualTo(qtyUOM));
		}

		if (datePromised != null)
		{
			Assert.assertThat("DatePromised" + messageSuffix, row.getDatePromised(), Matchers.comparesEqualTo(datePromised));
		}
		if (poReferenceSet)
		{
			Assert.assertEquals("POReference" + messageSuffix, poReference, row.getPOReference());
		}
	}

	public void assertExpected(final XLSImpCOLCandType xml)
	{
		final String messageSuffix = "\nXML: " + xml
				+ "\n";

		Assert.assertNotNull("XML not null", xml);
		if (lineNoSet)
		{
			Assert.assertEquals("LineNo" + messageSuffix, BigInteger.valueOf(lineNo), xml.getLine());
		}
		if (productDescriptionSet)
		{
			Assert.assertEquals("ProductDescription" + messageSuffix, productDescription, xml.getProductDescription());
		}
		if (uom_x12de355_set)
		{
			final EDIImpCUOMLookupUOMSymbolType uomLookup = xml.getCUOMID();
			final String uom_x12de355_actual = uomLookup == null ? null : uomLookup.getX12DE355();
			Assert.assertEquals("UOM_x12de355" + messageSuffix, uom_x12de355, uom_x12de355_actual);
		}

		if (M_Product_ID != null)
		{
			Assert.assertEquals("M_Product_ID" + messageSuffix, BigInteger.valueOf(M_Product_ID), xml.getMProductID());
		}
		if (M_ProductPrice_ID != null)
		{
			Assert.assertEquals("M_ProductPrice_ID" + messageSuffix, BigInteger.valueOf(M_ProductPrice_ID), xml.getMProductPriceID());
		}

		Assert.assertEquals("IsExplicitProductPriceAttribute" + messageSuffix, "Y", xml.getIsExplicitProductPriceAttribute());
		if (M_ProductPrice_Attribute_ID != null)
		{
			Assert.assertEquals("M_ProductPrice_Attribute_ID" + messageSuffix, BigInteger.valueOf(M_ProductPrice_Attribute_ID), xml.getMProductPriceAttributeID());
		}

		if (M_HU_PI_Item_Product_ID != null)
		{
			Assert.assertEquals("M_HU_PI_Item_Product_ID" + messageSuffix, BigInteger.valueOf(M_HU_PI_Item_Product_ID), xml.getMHUPIItemProductID());
		}

		if (C_BPartner_ID != null)
		{
			Assert.assertEquals("C_BPartner_ID" + messageSuffix, BigInteger.valueOf(C_BPartner_ID), xml.getCBPartnerID());
		}
		if (C_BPartner_Location_ID != null)
		{
			Assert.assertEquals("C_BPartner_Location_ID" + messageSuffix, BigInteger.valueOf(C_BPartner_Location_ID), xml.getCBPartnerLocationID());
		}

		if (price != null)
		{
			Assert.assertThat("Price" + messageSuffix, xml.getPriceEntered(), Matchers.comparesEqualTo(price));
		}
		if (currencyISOCode != null)
		{
			final EDIImpCCurrencyLookupISOCodeType currencyLookup = xml.getCCurrencyID();
			final String currencyISOCode_actual = currencyLookup == null ? null : currencyLookup.getISOCode();
			Assert.assertThat("Currency" + messageSuffix, currencyISOCode_actual, Matchers.equalTo(currencyISOCode));
		}

		// N/A
		// if (qtyTUs != null)
		// Assert.assertThat("QtyTUs" + messageSuffix, xml.getQtyTU(), Matchers.comparesEqualTo(qtyTUs));
		if (qtyCUsPerTU != null)
		{
			Assert.assertThat("QtyCUsPerTU" + messageSuffix, xml.getQtyItemCapacity(), Matchers.comparesEqualTo(qtyCUsPerTU));
		}
		if (qtyUOM != null)
		{
			Assert.assertThat("QtyUOM" + messageSuffix, xml.getQty(), Matchers.comparesEqualTo(qtyUOM));
		}

		if (datePromised != null)
		{
			Assert.assertThat("DatePromised" + messageSuffix, Util.toDate(xml.getDatePromised()), Matchers.comparesEqualTo(datePromised));
		}
		if (poReferenceSet)
		{
			Assert.assertEquals("POReference" + messageSuffix, poReference, xml.getPOReference());
		}

	}

	/**
	 * Sets Excel row number used to identify the row
	 *
	 * @param lineNo
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
