package de.metas.invoicecandidate.expectations;

import java.math.BigDecimal;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.util.test.AbstractExpectation;
import org.adempiere.util.test.ErrorMessage;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

public class InvoiceCandidateExpectation<ParentExpectationType> extends AbstractExpectation<ParentExpectationType>
{
	/**
	 * Use this method to create a "standalone" expectation.
	 *
	 * @return
	 */
	public static InvoiceCandidateExpectation<Object> newExpectation()
	{
		return new InvoiceCandidateExpectation<>();
	}

	private I_M_Product product = null;

	private BigDecimal netAmtToInvoice = null;
	private Boolean error = null;
	private String errorMessage = null;
	private boolean errorMessageSet = false;
	private I_AD_Note errorNote = null;
	private boolean errorNoteSet = false;
	private Boolean processed;
	private BigDecimal priceActual = null;
	private BigDecimal priceActualOverride;
	private Boolean inDispute;
	private I_C_UOM uom = null;
	private String description = null;
	private Boolean soTrx = null;
	private I_C_DocType docTypeInvoice = null;

	private I_M_PriceList_Version priceListVersion;
	private boolean priceListVersionSet = false;

	private Integer line;

	//
	private BigDecimal qtyOrdered;
	private BigDecimal qtyDelivered;
	private BigDecimal qtyToInvoice;
	private BigDecimal qtyToInvoiceOverride;
	//
	private BigDecimal qtyWithIssues;
	private BigDecimal qualityDiscountPercent;
	private BigDecimal qualityDiscountPercentOverride;

	private boolean qualityNoticesSet;

	protected InvoiceCandidateExpectation(ParentExpectationType parent)
	{
		super(parent);
	}

	protected InvoiceCandidateExpectation()
	{
		super();
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> assertExpected(final I_C_Invoice_Candidate actual)
	{
		return assertExpected(newErrorMessage(), actual);
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> assertExpected(final String message, final I_C_Invoice_Candidate actual)
	{
		return assertExpected(newErrorMessage(message), actual);
	}

	/**
	 * Don't make it final so that subclasses can add verify additional columns.
	 *
	 * @param message
	 * @param actual
	 * @return
	 */
	@OverridingMethodsMustInvokeSuper
	public InvoiceCandidateExpectation<ParentExpectationType> assertExpected(final ErrorMessage message, final I_C_Invoice_Candidate actual)
	{
		final ErrorMessage messageToUse = derive(message)
				.addContextInfo(actual);

		assertNotNull(messageToUse.expect("invoice candidate not null"), actual);

		if (line != null)
		{
			assertEquals(messageToUse.expect("Line"), line, actual.getLine());
		}
		if (description != null)
		{
			assertEquals(messageToUse.expect("Description"), description, actual.getDescription());
		}
		if (soTrx != null)
		{
			assertEquals(messageToUse.expect("IsSOTrx"), soTrx, actual.isSOTrx());
		}
		if (docTypeInvoice != null)
		{
			assertModelEquals(messageToUse.expect("C_DocTypeInvoice_ID"), docTypeInvoice.getC_DocType_ID(), actual.getC_DocTypeInvoice_ID());
		}
		if (product != null)
		{
			assertModelEquals(messageToUse.expect("M_Product_ID"), product.getM_Product_ID(), actual.getM_Product_ID());
		}
		if (uom != null)
		{
			assertModelEquals(messageToUse.expect("C_UOM_ID"), uom.getC_UOM_ID(), actual.getC_UOM_ID());
		}
		if (error != null)
		{
			assertEquals(messageToUse.expect("IsError"), error, actual.isError());
		}
		if (errorMessageSet)
		{
			assertEquals(messageToUse.expect("ErrorMsg"), errorMessage, actual.getErrorMsg());
		}
		if (errorNoteSet)
		{
			assertModelEquals(messageToUse.expect("AD_Note"), errorNote.getAD_Note_ID(), actual.getAD_Note_ID());
		}

		if (processed != null)
		{
			assertEquals(messageToUse.expect("Processed"), processed, actual.isProcessed());
		}

		if (priceListVersionSet)
		{
			assertModelEquals(messageToUse.expect("M_PriceList_Version_ID"), priceListVersion.getM_PriceList_Version_ID(), actual.getM_PriceList_Version_ID());
		}

		if (priceActual != null)
		{
			assertEquals(messageToUse.expect("PriceActual"), priceActual, actual.getPriceActual());
		}

		if (priceActualOverride != null)
		{
			assertEquals(messageToUse.expect("PriceActualOverride"), priceActualOverride, actual.getPriceActual_Override());
		}

		if (netAmtToInvoice != null)
		{
			assertCloseToExpected(messageToUse.expect("netAmtToInvoice"), netAmtToInvoice, actual.getNetAmtToInvoice());
		}

		if (qtyOrdered != null)
		{
			assertCloseToExpected(messageToUse.expect("qtyOrdered"), qtyOrdered, actual.getQtyOrdered());
		}

		if (qtyDelivered != null)
		{
			assertCloseToExpected(messageToUse.expect("qtyDelivered"), qtyDelivered, actual.getQtyDelivered());
		}

		if (qtyToInvoice != null)
		{
			assertCloseToExpected(messageToUse.expect("qtyToInvoice"), qtyToInvoice, actual.getQtyToInvoice());
		}

		if (qtyToInvoiceOverride != null)
		{
			assertCloseToExpected(messageToUse.expect("qtyToInvoiceOverride"), qtyToInvoiceOverride, actual.getQtyToInvoice_Override());
		}

		if (inDispute != null)
		{
			assertEquals(message.expect("InDispute"), inDispute, actual.isInDispute());
		}

		//
		// Qty & Quality
		{
			if (qtyWithIssues != null)
			{
				assertCloseToExpected(messageToUse.expect("qtyWithIssues"), qtyWithIssues, actual.getQtyWithIssues());
			}
			if (qualityDiscountPercent != null)
			{
				assertCloseToExpected(messageToUse.expect("qualityDiscountPercent"), this.qualityDiscountPercent, actual.getQualityDiscountPercent());
			}
			if (qualityDiscountPercentOverride != null)
			{
				assertCloseToExpected(messageToUse.expect("qualityDiscountPercentOverride"), this.qualityDiscountPercentOverride, actual.getQualityDiscountPercent_Override());
			}
			if (qualityNoticesSet)
			{
				// NOTE: there is no QualityNote column (yet?!)
				// assertEquals(messageToUse.expect("qualityNotices"), this.qualityNotices, actual.getQ);
			}
		}

		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> netAmtToInvoice(final BigDecimal netAmtToInvoice)
	{
		this.netAmtToInvoice = netAmtToInvoice;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> netAmtToInvoice(final String netAmtToInvoice)
	{
		return netAmtToInvoice(new BigDecimal(netAmtToInvoice));
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> netAmtToInvoice(final int netAmtToInvoice)
	{
		return netAmtToInvoice(new BigDecimal(netAmtToInvoice));
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> error(final boolean error)
	{
		this.error = error;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> errorMessage(final String errorMessage)
	{
		this.errorMessage = errorMessage;
		this.errorMessageSet = true;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> errorNote(final I_AD_Note errorNote)
	{
		this.errorNote = errorNote;
		this.errorNoteSet = true;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> processed(final boolean processed)
	{
		this.processed = processed;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> priceActualOverride(final BigDecimal priceActualOverride)
	{
		this.priceActualOverride = priceActualOverride;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> inDispute(final boolean inDispute)
	{
		this.inDispute = inDispute;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> qtyToInvoiceOverride(final BigDecimal qtyToInvoiceOverride)
	{
		this.qtyToInvoiceOverride = qtyToInvoiceOverride;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> qtyToInvoiceOverride(final String qtyToInvoiceOverride)
	{
		return qtyToInvoiceOverride(new BigDecimal(qtyToInvoiceOverride));
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> qtyToInvoice(final BigDecimal qtyToInvoice)
	{
		this.qtyToInvoice = qtyToInvoice;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> qtyDelivered(final BigDecimal qtyDelivered)
	{
		this.qtyDelivered = qtyDelivered;
		return this;
	}

	public InvoiceCandidateExpectation<ParentExpectationType> qtyOrdered(final BigDecimal qtyOrdered)
	{
		this.qtyOrdered = qtyOrdered;
		return this;
	}

	//
	//
	// Qty & Quality
	//
	//

	public final InvoiceCandidateExpectation<ParentExpectationType> qtyWithIssues(final BigDecimal qtyWithIssues)
	{
		this.qtyWithIssues = qtyWithIssues;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> qtyWithIssues(final String qtyWithIssues)
	{
		return qtyWithIssues(new BigDecimal(qtyWithIssues));
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> qualityDiscountPercent(final BigDecimal qualityDiscountPercent)
	{
		this.qualityDiscountPercent = qualityDiscountPercent;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> qualityDiscountPercent(final String qualityDiscountPercent)
	{
		return qualityDiscountPercent(new BigDecimal(qualityDiscountPercent));
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> qualityDiscountPercent(final int qualityDiscountPercent)
	{
		return qualityDiscountPercent(new BigDecimal(qualityDiscountPercent));
	}

	public final BigDecimal getQualityDiscountPercent()
	{
		return qualityDiscountPercent;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> qualityDiscountPercentOverride(final BigDecimal qualityDiscountPercentOverride)
	{
		this.qualityDiscountPercentOverride = qualityDiscountPercentOverride;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> qualityDiscountPercentOverride(final String qualityDiscountPercentOverride)
	{
		return qualityDiscountPercentOverride(new BigDecimal(qualityDiscountPercentOverride));
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> qualityDiscountPercentOverride(final int qualityDiscountPercentOverride)
	{
		return qualityDiscountPercentOverride(new BigDecimal(qualityDiscountPercentOverride));
	}

	public final BigDecimal getQualityDiscountPercentOverride()
	{
		return qualityDiscountPercentOverride;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> product(I_M_Product product)
	{
		this.product = product;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> uom(I_C_UOM uom)
	{
		this.uom = uom;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> priceActual(BigDecimal priceActual)
	{
		this.priceActual = priceActual;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> soTrx(boolean soTrx)
	{
		this.soTrx = soTrx;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> description(String description)
	{
		this.description = description;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> docTypeInvoice(I_C_DocType docTypeInvoice)
	{
		this.docTypeInvoice = docTypeInvoice;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> priceListVersion(I_M_PriceList_Version priceListVersion)
	{
		this.priceListVersion = priceListVersion;
		this.priceListVersionSet = true;
		return this;
	}

	public final InvoiceCandidateExpectation<ParentExpectationType> line(int line)
	{
		this.line = line;
		return this;
	}
}
