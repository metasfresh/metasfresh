package de.metas.invoicecandidate.api.impl;

import java.time.LocalDate;
import java.util.List;

import org.compiere.model.I_C_DocType;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.invoicecandidate.api.IInvoiceCandAggregate;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.Getter;
import lombok.Setter;

/* package */class InvoiceHeaderImpl implements IInvoiceHeader
{
	/** @return builder */
	public static final InvoiceHeaderImplBuilder builder()
	{
		return new InvoiceHeaderImplBuilder();
	}

	private List<IInvoiceCandAggregate> lines;

	private String docBaseType;

	private String poReference;

	private LocalDate dateInvoiced;

	private LocalDate dateAcct;

	@Getter
	@Setter
	private OrgId orgId;

	private int C_Order_ID;

	private int M_PriceList_ID;

	private int Bill_Location_ID;

	@Getter
	@Setter
	private BPartnerId billBPartnerId;

	private int Bill_User_ID;

	@Getter
	@Setter
	private BPartnerId salesPartnerId;

	// 03805: add attribute C_Currency_ID
	@Getter
	@Setter
	private CurrencyId currencyId;

	// 04258
	private String Description;
	private String DescriptionBottom;

	private boolean isSOTrx;

	// 06630
	private int M_InOut_ID = -1;

	private I_C_DocType docTypeInvoice;

	private boolean taxIncluded;

	private int C_PaymentTerm_ID = -1;

	/* package */ InvoiceHeaderImpl()
	{
	}

	@Override
	public String toString()
	{
		return "InvoiceHeaderImpl ["
				+ "docBaseType=" + docBaseType
				+ ", dateInvoiced=" + dateInvoiced
				+ ", AD_Org_ID=" + OrgId.toRepoId(orgId)
				+ ", M_PriceList_ID=" + M_PriceList_ID
				+ ", isSOTrx=" + isSOTrx
				+ ", Bill_BPartner_ID=" + BPartnerId.toRepoId(billBPartnerId)
				+ ", Bill_Location_ID=" + Bill_Location_ID
				+ ", Bill_User_ID=" + Bill_User_ID
				+ ", currencyId=" + currencyId
				+ ", C_Order_ID=" + C_Order_ID
				+ ", docTypeInvoiceId=" + docTypeInvoice
				+ ", lines=" + lines
				+ "]";
	}

	@Override
	public List<IInvoiceCandAggregate> getLines()
	{
		return lines;
	}

	@Override
	public List<I_C_Invoice_Candidate> getAllInvoiceCandidates()
	{
		return getLines()
				.stream()
				.flatMap(line -> line.getAllCands().stream())
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public String getDocBaseType()
	{
		return docBaseType;
	}

	@Override
	public String getPOReference()
	{
		return poReference;
	}

	@Override
	public LocalDate getDateInvoiced()
	{
		return dateInvoiced;
	}

	@Override
	public LocalDate getDateAcct()
	{
		return dateAcct;
	}

	@Override
	public int getC_Order_ID()
	{
		return C_Order_ID;
	}

	@Override
	public int getM_PriceList_ID()
	{
		return M_PriceList_ID;
	}

	@Override
	public int getBill_Location_ID()
	{
		return Bill_Location_ID;
	}

	@Override
	public int getBill_User_ID()
	{
		return Bill_User_ID;
	}

	public void setLines(final List<IInvoiceCandAggregate> lines)
	{
		this.lines = lines;
	}

	public void setDocBaseType(final String docBaseType)
	{
		this.docBaseType = docBaseType;
	}

	public void setPOReference(final String poReference)
	{
		this.poReference = poReference;
	}

	public void setDateInvoiced(final LocalDate dateInvoiced)
	{
		this.dateInvoiced = dateInvoiced;
	}

	public void setDateAcct(final LocalDate dateAcct)
	{
		this.dateAcct = dateAcct;
	}

	public void setC_Order_ID(final int c_Order_ID)
	{
		C_Order_ID = c_Order_ID;
	}

	public void setM_PriceList_ID(final int M_PriceList_ID)
	{
		this.M_PriceList_ID = M_PriceList_ID;
	}

	public void setBill_Location_ID(final int bill_Location_ID)
	{
		Bill_Location_ID = bill_Location_ID;
	}

	public void setBill_User_ID(final int bill_User_ID)
	{
		Bill_User_ID = bill_User_ID;
	}

	@Override
	public String getDescriptionBottom()
	{
		return DescriptionBottom;
	}

	@Override
	public String getDescription()
	{
		return Description;
	}

	public void setDescription(final String description)
	{
		Description = description;
	}

	public void setDescriptionBottom(final String descriptionBottom)
	{
		DescriptionBottom = descriptionBottom;
	}

	@Override
	public boolean isSOTrx()
	{
		return isSOTrx;
	}

	public void setIsSOTrx(final boolean isSOTrx)
	{
		this.isSOTrx = isSOTrx;
	}

	@Override
	public int getM_InOut_ID()
	{
		return M_InOut_ID;
	}

	public void setM_InOut_ID(final int M_InOut_ID)
	{
		this.M_InOut_ID = M_InOut_ID;
	}

	@Override
	public I_C_DocType getC_DocTypeInvoice()
	{
		return docTypeInvoice;
	}

	public void setC_DocTypeInvoice(final I_C_DocType docType)
	{
		this.docTypeInvoice = docType;
	}

	@Override
	public boolean isTaxIncluded()
	{
		return taxIncluded;
	}

	public void setTaxIncluded(boolean taxIncluded)
	{
		this.taxIncluded = taxIncluded;
	}

	/**
	 * Negate all line amounts
	 */
	public void negateAllLineAmounts()
	{
		for (final IInvoiceCandAggregate lineAgg : getLines())
		{
			lineAgg.negateLineAmounts();
		}
	}

	/**
	 * Calculates total net amount by summing up all {@link IInvoiceLineRW#getNetLineAmt()}s.
	 *
	 * @return total net amount
	 */
	public Money calculateTotalNetAmtFromLines()
	{
		final List<IInvoiceCandAggregate> lines = getLines();
		Check.assume(lines != null && !lines.isEmpty(), "Invoice {} was not aggregated yet", this);

		Money totalNetAmt = Money.zero(currencyId);
		for (final IInvoiceCandAggregate lineAgg : lines)
		{
			for (final IInvoiceLineRW line : lineAgg.getAllLines())
			{
				final Money lineNetAmt = line.getNetLineAmt();
				totalNetAmt = totalNetAmt.add(lineNetAmt);
			}
		}

		return totalNetAmt;
	}

	public void setC_PaymentTerm_ID(final int paymentTermId)
	{
		C_PaymentTerm_ID = paymentTermId;
	}

	@Override
	public int getC_PaymentTerm_ID()
	{
		return C_PaymentTerm_ID;
	}
}
