package de.metas.invoicecandidate.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.auction.AuctionId;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.document.DocTypeId;
import de.metas.document.dimension.Dimension;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolId;
import de.metas.forex.ForexContractRef;
import de.metas.impex.InputDataSourceId;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoicecandidate.api.IInvoiceCandAggregate;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/* package */class InvoiceHeaderImpl implements IInvoiceHeader
{
	/**
	 * @return builder
	 */
	public static InvoiceHeaderImplBuilder builder()
	{
		return new InvoiceHeaderImplBuilder();
	}

	@Setter
	private List<IInvoiceCandAggregate> lines;

	@Setter
	private InvoiceDocBaseType docBaseType;

	@Nullable
	private String poReference;

	@Nullable
	private String eMail;

	@Nullable
	@Getter
	@Setter
	private InputDataSourceId inputDataSourceId;

	@Setter
	private LocalDate dateInvoiced;

	@Setter
	private LocalDate dateAcct;

	@Setter
	private LocalDate overrideDueDate;

	@Getter
	@Setter
	private OrgId orgId;

	@Setter
	private int C_Order_ID;

	@Setter
	private int M_PriceList_ID;

	@Getter
	@Setter
	private BPartnerInfo billTo;

	@Getter
	@Setter
	private BPartnerId salesPartnerId;

	@Getter
	@Setter
	private UserId salesRepId;

	// 03805: add attribute C_Currency_ID
	@Getter
	@Setter
	private CurrencyId currencyId;

	// 04258
	@Setter
	private String Description;
	
	@Setter
	private String DescriptionBottom;

	@Setter
	private SOTrx soTrx;

	@Setter
	private boolean isTakeDocTypeFromPool;

	// 06630
	@Setter
	private int M_InOut_ID = -1;

	@Nullable
	private DocTypeId docTypeInvoiceId;

	@Nullable
	private DocTypeInvoicingPoolId docTypeInvoicingPoolId;

	@Setter
	private boolean taxIncluded;
	
	private String externalId;

	private @Nullable PaymentTermId paymentTermId;

	private @Nullable String paymentRule;

	@Setter
	private int C_Async_Batch_ID;

	@Setter
	private int C_Incoterms_ID;

	@Setter
	private String incotermLocation;

	@Setter
	private SectionCodeId sectionCodeId;

	@Setter
	private String invoiceAdditionalText;

	@Setter
	private boolean notShowOriginCountry;

	@Setter
	@Getter
	private ProjectId projectId;

	@Setter
	@Getter
	private ActivityId activityId;

	private int C_PaymentInstruction_ID;
	
	@Getter
	@Setter
	private CountryId C_Tax_Departure_Country_ID;

	@Nullable @Getter private BankAccountId bankAccountId;

	@Setter @Getter @Nullable ForexContractRef forexContractRef;

	@Setter @Getter @NonNull Dimension dimension;

	@Setter @Getter @Nullable
	CalendarId calendarId;

	@Setter @Getter @Nullable
	YearId yearId;

	@Setter @Getter @Nullable
	WarehouseId warehouseId;

	@Getter @Setter @Nullable
	private AuctionId auctionId;

	/* package */ InvoiceHeaderImpl()
	{
	}

	@Override
	public String toString()
	{
		return "InvoiceHeaderImpl ["
				+ "docBaseType=" + docBaseType
				+ ", dateInvoiced=" + dateInvoiced
				+ ", OverrideDueDate=" + overrideDueDate
				+ ", AD_Org_ID=" + OrgId.toRepoId(orgId)
				+ ", M_PriceList_ID=" + M_PriceList_ID
				+ ", soTrx=" + soTrx.toString()
				+ ", billTo=" + billTo
				+ ", currencyId=" + currencyId
				+ ", C_Order_ID=" + C_Order_ID
				+ ", docTypeInvoiceId=" + docTypeInvoiceId
				+ ", docTypeInvoicingPoolId=" + docTypeInvoicingPoolId
				+ ", externalID=" + externalId
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
	public InvoiceDocBaseType getDocBaseType()
	{
		return docBaseType;
	}

	@Override
	public String getPOReference()
	{
		return poReference;
	}

	@Nullable
	@Override
	public String getEMail()
	{
		return eMail;
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
	public LocalDate getOverrideDueDate()
	{
		return overrideDueDate;
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
	
	public void setPOReference(@Nullable final String poReference)
	{
		this.poReference = poReference;
	}

	public void setEMail(@Nullable final String eMail)
	{
		this.eMail = eMail;
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

	@Override
	public boolean isSOTrx()
	{
		return soTrx.isSales();
	}

	@Override
	public int getM_InOut_ID()
	{
		return M_InOut_ID;
	}

	@Override
	@Nullable
	public Optional<DocTypeId> getDocTypeInvoiceId()
	{
		return Optional.ofNullable(docTypeInvoiceId);
	}

	@Override
	@NonNull
	public Optional<DocTypeInvoicingPoolId> getDocTypeInvoicingPoolId()
	{
		return Optional.ofNullable(docTypeInvoicingPoolId);
	}




	@Override
	public boolean isTakeDocTypeFromPool()
	{
		return isTakeDocTypeFromPool;
	}

	@Override
	public void setDocTypeInvoicingPoolId(@Nullable final DocTypeInvoicingPoolId docTypeInvoicingPoolId)
	{
		this.docTypeInvoicingPoolId = docTypeInvoicingPoolId;
	}

	@Override
	public void setDocTypeInvoiceId(@Nullable final DocTypeId docTypeId)
	{
		this.docTypeInvoiceId = docTypeId;
	}

	@Override
	public boolean isTaxIncluded()
	{
		return taxIncluded;
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

	public void setPaymentTermId(@Nullable final PaymentTermId paymentTermId)
	{
		this.paymentTermId = paymentTermId;
	}

	@Nullable
	@Override
	public PaymentTermId getPaymentTermId()
	{
		return paymentTermId;
	}

	public void setPaymentRule(@Nullable final String paymentRule)
	{
		this.paymentRule = paymentRule;
	}

	@Nullable
	@Override
	public String getPaymentRule()
	{
		return paymentRule;
	}

	@Override
	public String getExternalId()
	{
		return externalId;
	}

	@Override
	public int getC_Async_Batch_ID()
	{
		return C_Async_Batch_ID;
	}

	public String setExternalId(final String externalId)
	{
		return this.externalId = externalId;
	}

	@Override
	public int getC_Incoterms_ID()
	{
		return C_Incoterms_ID;
	}

	@Override
	public String getIncotermLocation()
	{
		return incotermLocation;
	}

	@Override
	public InputDataSourceId getAD_InputDataSource_ID()
	{
		return inputDataSourceId;
	}

	public void setAD_InputDataSource_ID(@Nullable final InputDataSourceId inputDataSourceId)
	{
		this.inputDataSourceId = inputDataSourceId;
	}

	@Override
	public SectionCodeId getM_SectionCode_ID()
	{
		return sectionCodeId;
	}

	@Nullable
	@Override
	public String getInvoiceAdditionalText()
	{
		return invoiceAdditionalText;
	}

	@Override
	public boolean isNotShowOriginCountry()
	{
		return notShowOriginCountry;
	}

	@Override
	public void setC_PaymentInstruction_ID(final int C_PaymentInstruction_ID)
	{
		this.C_PaymentInstruction_ID = C_PaymentInstruction_ID;
	}

	@Override
	public int getC_PaymentInstruction_ID()
	{
		return C_PaymentInstruction_ID;
	}

	public void setBankAccountId(@Nullable final BankAccountId bankAccountId)
	{
		this.bankAccountId = bankAccountId;
	}
}
