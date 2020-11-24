package de.metas.materialtracking.test.expectations;

/*
 * #%L
 * de.metas.materialtracking
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

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.Assert;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Detail;
import de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl.InvoiceCandidateWriter;
import de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl.InvoiceDetailWriter;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLine;
import de.metas.pricing.IPricingResult;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;

/**
 *
 * NOTEs to developer:
 * <ul>
 * <li>when adding a new expectation here, please also change the {@link QualityInvoiceLineGroupExpectationsJavaWriter}.writeJavaBody(String expectationName, final IQualityInvoiceLine
 * invoiceableLine).
 * </ul>
 *
 * @author tsa
 *
 */
public class QualityInvoiceLineExpectation extends AbstractExpectation
{
	private final QualityInvoiceLineGroupExpectation parent;
	private String expectationName = null;

	private Boolean printBefore = null;
	private Boolean printed = true;

	private I_M_Product product;
	private boolean productSet = false;

	private String productName;
	private boolean productNameSet;

	private BigDecimal percentage;
	private boolean percentageSet = false;

	@Getter
	private BigDecimal qtyEntered;
	private boolean qtyEnteredSet = false;

	private I_C_UOM uom;
	private boolean uomSet = false;

	private Boolean displayed = null;

	private String description = null;
	private boolean descriptionSet = false;

	private BigDecimal price = null;
	private boolean priceSet = false;
	private Integer lineNo = null;

	private HandlingUnitsInfoExpectation<QualityInvoiceLineExpectation> handlingUnitsInfoExpectation = null;

	protected QualityInvoiceLineExpectation(QualityInvoiceLineGroupExpectation parent)
	{
		super();
		this.parent = parent;
	}

	@Override
	public String toString()
	{
		return "QualityInvoiceLineExpectation ["
				+ "expectationName=" + expectationName
				+ ", productSet=" + productSet
				+ ", product=" + (product == null ? null : product.getName())
				+ ", productNameSet=" + productNameSet
				+ ", productName=" + productName
				+ ", percentageSet=" + percentageSet
				+ ", percentage=" + percentage
				+ ", qtyEnteredSet=" + qtyEnteredSet
				+ ", qtyEntered=" + qtyEntered
				+ ", uomSet=" + uomSet
				+ ", uom=" + (uom == null ? null : uom.getUOMSymbol())
				+ ", displayed=" + displayed
				+ ", descriptionSet=" + descriptionSet
				+ ", description=" + description
				+ ", priceSet=" + priceSet
				+ ", price=" + price
				+ ", lineNo=" + lineNo
				+ "]";
	}

	private String createPrefix(final String message, final Object lineToValidate)
	{
		final StringBuilder sb = new StringBuilder();

		sb.append(message);
		sb.append("\n expectationName: ").append(expectationName);
		sb.append("\n expectation: ").append(this);
		sb.append("\n Line to validate: ").append(lineToValidate);

		sb.append("\n\n Invalid: ");

		return sb.toString();
	}

	@OverridingMethodsMustInvokeSuper
	public String assertExpected(final String message, @NonNull final IQualityInvoiceLine line)
	{
		final String prefix = createPrefix(message, line);

		if (printed != null)
		{
			Assert.assertEquals(prefix + "Printed", this.printed, line.isDisplayed());
		}
		if (productSet)
		{
			assertModelEquals(prefix + "Product", this.product, line.getM_Product());
		}
		if (productNameSet)
		{
			Assert.assertEquals(prefix + "ProductName", this.productName, line.getProductName());
		}
		if (percentageSet)
		{
			assertEquals(prefix + "Percentage", this.percentage, line.getPercentage());
		}
		if (qtyEnteredSet)
		{
			assertEquals(prefix + "QtyEntered", this.qtyEntered, line.getQty().toBigDecimal());
		}
		if (uomSet)
		{
			assertModelEquals(prefix + "UOM", this.uom, line.getQty().getUOM());
		}
		if (descriptionSet)
		{
			Assert.assertEquals(prefix + "Description", this.description, line.getDescription());
		}
		if (priceSet)
		{
			final boolean calculatedExpected = this.price != null;

			final IPricingResult pricingResult = line.getPrice();
			final boolean calculatedActual = pricingResult == null ? false : pricingResult.isCalculated();
			final BigDecimal priceActual = pricingResult == null ? null : pricingResult.getPriceStd();

			Assert.assertEquals(prefix + "PricingResult calculated", calculatedExpected, calculatedActual);
			assertEquals(prefix + "Price", this.price, priceActual);
		}
		if (handlingUnitsInfoExpectation != null)
		{
			handlingUnitsInfoExpectation.assertExpected(prefix, line.getHandlingUnitsInfo());
		}

		return prefix;
	}

	/**
	 * NOTEs to developer:
	 * <ul>
	 * <li>keep logic in sync with {@link InvoiceCandidateWriter}.createInvoiceCandidate
	 * </ul>
	 *
	 * @param message
	 * @param ic
	 */
	@OverridingMethodsMustInvokeSuper
	public String assertExpected(final String message, final I_C_Invoice_Candidate ic)
	{
		Assert.assertNotNull("ic not null", ic);

		final String prefix = createPrefix(message, ic);

		if (printed != null)
		{
			Assert.assertEquals(prefix + "Printed", this.printed, ic.isPrinted());
		}
		if (productSet)
		{
			assertModelEquals(prefix + "Product", this.product.getM_Product_ID(), ic.getM_Product_ID());
		}
		if (productNameSet)
		{
			// TODO: handle this case
			// Assert.fail(prefix + "ProductName cannot be validated in invoice candidate because it does not exist");
		}
		if (percentageSet)
		{
			// TODO: handle this case
			// Assert.fail(prefix + "Percentage cannot be validated in invoice candidate because it does not exist");
		}
		if (qtyEnteredSet)
		{
			assertEquals(prefix + "EnteredSet", this.qtyEntered, ic.getQtyEntered());
		}
		if (uomSet)
		{
			assertModelEquals(prefix + "UOM", this.uom.getC_UOM_ID(), ic.getC_UOM_ID());
		}
		if (descriptionSet)
		{
			Assert.assertEquals(prefix + "Description", this.description, ic.getDescription());
		}
		if (priceSet)
		{
			assertEquals(prefix + "PriceEntered", this.price, ic.getPriceEntered());
			assertEquals(prefix + "PriceActual", this.price, ic.getPriceActual());
		}
		if (lineNo != null)
		{
			Assert.assertEquals(prefix + "LineNo", (int)this.lineNo, ic.getLine());
		}

		return prefix;
	}

	/**
	 * NOTEs to developer:
	 * <ul>
	 * <li>keep logic in sync with {@link InvoiceDetailWriter}.save
	 * </ul>
	 */
	@OverridingMethodsMustInvokeSuper
	public String assertExpected(final String message, @NonNull final I_C_Invoice_Detail detail)
	{
		final String prefix = createPrefix(message, detail);

		if (printBefore != null)
		{
			Assert.assertEquals(prefix + "PrintBefore", this.printBefore, detail.isPrintBefore());
		}
		if (printed != null)
		{
			Assert.assertEquals(prefix + "Printed", this.printed, detail.isPrinted());
		}
		if (productSet)
		{
			assertModelEquals(prefix + "Product", this.product.getM_Product_ID(), detail.getM_Product_ID());
		}
		if (productNameSet)
		{
			Assert.assertEquals(prefix + "ProductName", this.productName, detail.getNote());
		}
		if (percentageSet)
		{
			assertEquals(prefix + "Percentage", this.percentage, detail.getPercentage());
		}
		if (qtyEnteredSet)
		{
			assertEquals(prefix + "QtyEnteredSet", this.qtyEntered, detail.getQty());
		}
		if (uomSet)
		{
			assertModelEquals(prefix + "UOM", this.uom.getC_UOM_ID(), detail.getC_UOM_ID());
		}
		if (descriptionSet)
		{
			Assert.assertEquals(prefix + "Description", this.description, detail.getDescription());
		}
		if (priceSet)
		{
			assertEquals(prefix + "PriceEntered", this.price, detail.getPriceEntered());
			assertEquals(prefix + "PriceActual", this.price, detail.getPriceActual());
		}

		return prefix;
	}

	public final QualityInvoiceLineGroupExpectation endExpectation()
	{
		Check.assumeNotNull(parent, "parent not null");
		return parent;
	}

	public final QualityInvoiceLineExpectation expectationName(final String name)
	{
		this.expectationName = name;
		return this;
	}

	/**
	 *
	 * NOTE: This method is automatically called by {@link QualityInvoiceLineGroupExpectation}.
	 *
	 * @param printBefore
	 * @return this
	 */
	protected final QualityInvoiceLineExpectation printBefore(final boolean printBefore)
	{
		this.printBefore = printBefore;
		return this;
	}

	public final QualityInvoiceLineExpectation printed(final boolean printed)
	{
		this.printed = printed;
		return this;
	}

	public final I_M_Product getProduct()
	{
		return product;
	}

	public final QualityInvoiceLineExpectation product(I_M_Product product)
	{
		this.product = product;
		this.productSet = true;
		return this;
	}

	public final QualityInvoiceLineExpectation product(int productId)
	{
		final I_M_Product productToSet;
		if (productId <= 0)
		{
			productToSet = null;
		}
		else
		{
			productToSet = InterfaceWrapperHelper.create(Env.getCtx(), productId, I_M_Product.class, ITrx.TRXNAME_None);
			Check.assumeNotNull(productToSet, "productToSet not null");
		}
		return product(productToSet);
	}

	public final String getProductName()
	{
		return productName;
	}

	public final QualityInvoiceLineExpectation productName(String productName)
	{
		this.productName = productName;
		this.productNameSet = true;
		return this;
	}

	public final BigDecimal getPercentage()
	{
		return percentage;
	}

	public final QualityInvoiceLineExpectation percentage(BigDecimal percentage)
	{
		this.percentage = percentage;
		this.percentageSet = true;
		return this;
	}

	public final QualityInvoiceLineExpectation qtyEntered(BigDecimal qtyEntered)
	{
		this.qtyEntered = qtyEntered;
		this.qtyEnteredSet = true;
		return this;
	}

	public final I_C_UOM getUom()
	{
		return uom;
	}

	public final QualityInvoiceLineExpectation uom(I_C_UOM uom)
	{
		this.uom = uom;
		this.uomSet = true;
		return this;
	}

	public final QualityInvoiceLineExpectation uom(final int uomId)
	{
		final I_C_UOM uomToSet;
		if (uomId <= 0)
		{
			uomToSet = null;
		}
		else
		{
			uomToSet = InterfaceWrapperHelper.create(Env.getCtx(), uomId, I_C_UOM.class, ITrx.TRXNAME_None);
			Check.assumeNotNull(uomToSet, "uomToSet not null");
		}

		return uom(uomToSet);
	}

	public final Boolean getDisplayed()
	{
		return displayed;
	}

	public final QualityInvoiceLineExpectation displayed(boolean displayed)
	{
		this.displayed = displayed;
		return this;
	}

	public final String getDescription()
	{
		return description;
	}

	public final QualityInvoiceLineExpectation description(String description)
	{
		this.description = description;
		this.descriptionSet = true;
		return this;
	}

	public final BigDecimal getPrice()
	{
		return price;
	}

	public final QualityInvoiceLineExpectation price(BigDecimal price)
	{
		this.price = price;
		this.priceSet = true;
		return this;
	}

	public HandlingUnitsInfoExpectation<QualityInvoiceLineExpectation> handlingUnitsInfoExpectation()
	{
		if (this.handlingUnitsInfoExpectation == null)
		{
			this.handlingUnitsInfoExpectation = new HandlingUnitsInfoExpectation<>(this);
		}
		return this.handlingUnitsInfoExpectation;
	}

	public final QualityInvoiceLineExpectation lineNo(final int lineNo)
	{
		this.lineNo = lineNo;
		return this;
	}

	public final Integer getLineNo()
	{
		return lineNo;
	}
}
