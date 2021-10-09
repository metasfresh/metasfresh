package de.metas.banking.payment.paymentallocation.model;

/*
 * #%L
 * de.metas.banking.swingui
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import de.metas.organization.ClientAndOrgId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.ObjectUtils;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.metas.banking.payment.paymentallocation.service.AllocationAmounts;
import de.metas.banking.payment.paymentallocation.service.PayableDocument;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.invoice.InvoiceId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.compiere.util.Env;

/**
 * Invoice or Prepaid Order.
 * 
 * @author tsa
 *
 */
public final class InvoiceRow extends AbstractAllocableDocRow implements IInvoiceRow
{

	public static Builder builder()
	{
		return new Builder();
	}

	public static IInvoiceRow castOrNull(final IAllocableDocRow row)
	{
		return row instanceof IInvoiceRow ? (IInvoiceRow)row : null;
	}

	private final OrgId orgId;
	private final int C_Invoice_ID;
	private final int C_Order_ID;
	private final String documentNo;
	private final String docTypeName;
	private final Date dateInvoiced;
	// task 09643
	private final Date dateAcct;
	private final int C_BPartner_ID;
	private final String BPartnerName;
	private final CurrencyCode currencyISOCode;
	private final BigDecimal grandTotal;
	private final BigDecimal grandTotalConv;
	private final BigDecimal openAmtConv;
	private final Supplier<BigDecimal> paymentRequestAmtSupplier;
	private final BigDecimal multiplierAP;
	private final boolean isPrepayOrder;
	private final String POReference;
	private final boolean creditMemo;
	//
	private BigDecimal discount = BigDecimal.ZERO;
	private BigDecimal writeOffAmt = BigDecimal.ZERO;
	private BigDecimal overUnderAmt = BigDecimal.ZERO;
	private BigDecimal appliedAmt = BigDecimal.ZERO;
	private boolean selected = false;
	private boolean taboo = false;

	private InvoiceRow(final Builder builder)
	{
		super();
		// FIXME: validate: not null, etc
		orgId = builder.orgId;
		C_Invoice_ID = builder.C_Invoice_ID;
		C_Order_ID = builder.C_Order_ID;
		documentNo = builder.documentNo;
		docTypeName = builder.docTypeName;
		dateInvoiced = builder.dateInvoiced;
		// task 09643
		dateAcct = builder.dateAcct;
		C_BPartner_ID = builder.C_BPartner_ID;
		BPartnerName = builder.BPartnerName;
		currencyISOCode = builder.currencyISOCode;
		isPrepayOrder = builder.isPrepayOrder;
		POReference = builder.POReference;
		creditMemo = builder.creditMemo;
		//
		// Amounts
		multiplierAP = builder.multiplierAP;
		grandTotal = notNullOrZero(builder.grandTotal);
		grandTotalConv = notNullOrZero(builder.grandTotalConv);
		openAmtConv = notNullOrZero(builder.openAmtConv);
		paymentRequestAmtSupplier = builder.paymentRequestAmtSupplier;
		// Write-Off amounts
		discount = notNullOrZero(builder.discount);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public boolean isSelected()
	{
		return selected;
	}

	@Override
	public void setSelected(final boolean selected)
	{
		this.selected = selected;
	}

	public OrgId getOrgId()
	{
		return orgId;
	}

	@Override
	public int getC_Invoice_ID()
	{
		return C_Invoice_ID;
	}

	@Override
	public int getC_Order_ID()
	{
		return C_Order_ID;
	}

	@Override
	public String getDocumentNo()
	{
		return documentNo;
	}

	@Override
	public String getDocTypeName()
	{
		return docTypeName;
	}

	@Override
	public Date getDocumentDate()
	{
		return dateInvoiced;
	}

	@Override
	public Date getDateAcct()
	{
		return dateAcct;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return C_BPartner_ID;
	}

	@Override
	public String getBPartnerName()
	{
		return BPartnerName;
	}

	@Override
	public String getCurrencyISOCodeAsString()
	{
		return currencyISOCode != null ? currencyISOCode.toThreeLetterCode() : null;
	}

	@Override
	public CurrencyCode getCurrencyISOCode()
	{
		return currencyISOCode;
	}

	@Override
	public BigDecimal getGrandTotal()
	{
		return grandTotal;
	}

	@Override
	public BigDecimal getGrandTotalConv()
	{
		return grandTotalConv;
	}

	@Override
	public BigDecimal getOpenAmtConv()
	{
		return openAmtConv;
	}

	@Override
	public BigDecimal getDiscount()
	{
		return discount;
	}

	@Override
	public BigDecimal getDiscount_APAdjusted()
	{
		return adjustAP(getDiscount());
	}

	@Override
	public void setDiscount(final BigDecimal discount)
	{
		this.discount = notNullOrZero(discount);
	}

	@Override
	public BigDecimal getPaymentRequestAmt()
	{
		if (paymentRequestAmtSupplier == null)
		{
			return BigDecimal.ZERO;
		}
		return paymentRequestAmtSupplier.get();
	}

	@Override
	public BigDecimal getMultiplierAP()
	{
		return multiplierAP;
	}

	@Override
	public boolean isPrepayOrder()
	{
		return isPrepayOrder;
	}

	@Override
	public boolean isCreditMemo()
	{
		return creditMemo;
	}

	@Override
	public String getPOReference()
	{
		return POReference;
	}

	@Override
	public BigDecimal getWriteOffAmt()
	{
		return writeOffAmt;
	}

	@Override
	public BigDecimal getWriteOffAmt_APAdjusted()
	{
		return adjustAP(getWriteOffAmt());
	}

	@Override
	public void setWriteOffAmt(final BigDecimal writeOffAmt)
	{
		this.writeOffAmt = notNullOrZero(writeOffAmt);
	}

	@Override
	public void setWriteOffAmtOfType(final InvoiceWriteOffAmountType type, final BigDecimal writeOffAmt)
	{
		Check.assumeNotNull(type, "type not null");
		if (type == InvoiceWriteOffAmountType.WriteOff)
		{
			setWriteOffAmt(writeOffAmt);
		}
		else if (type == InvoiceWriteOffAmountType.OverUnder)
		{
			setOverUnderAmt(writeOffAmt);
		}
		else if (type == InvoiceWriteOffAmountType.Discount)
		{
			setDiscount(writeOffAmt);
		}
		else
		{
			throw new IllegalArgumentException("Unknown write-off type: " + type);
		}
	}

	@Override
	public BigDecimal getWriteOffAmtOfType(final InvoiceWriteOffAmountType type)
	{
		Check.assumeNotNull(type, "type not null");
		if (type == InvoiceWriteOffAmountType.WriteOff)
		{
			return getWriteOffAmt();
		}
		else if (type == InvoiceWriteOffAmountType.OverUnder)
		{
			return getOverUnderAmt();
		}
		else if (type == InvoiceWriteOffAmountType.Discount)
		{
			return getDiscount();
		}
		else
		{
			throw new IllegalArgumentException("Unknown write-off type: " + type);
		}
	}

	@Override
	public void resetAllWriteOffAmounts()
	{
		for (final InvoiceWriteOffAmountType type : InvoiceWriteOffAmountType.values())
		{
			setWriteOffAmtOfType(type, BigDecimal.ZERO);
		}
	}

	/** @return sum of all write-off amounts */
	private BigDecimal getWriteOffsSum()
	{
		BigDecimal writeOffSum = BigDecimal.ZERO;
		for (final InvoiceWriteOffAmountType type : InvoiceWriteOffAmountType.values())
		{
			final BigDecimal writeOffOfType = getWriteOffAmtOfType(type);
			writeOffSum = writeOffSum.add(writeOffOfType);
		}
		return writeOffSum;
	}

	@Override
	public BigDecimal getOverUnderAmt()
	{
		return overUnderAmt;
	}

	@Override
	public BigDecimal getOverUnderAmt_APAdjusted()
	{
		return adjustAP(getOverUnderAmt());
	}

	@Override
	public void setOverUnderAmt(final BigDecimal overUnderAmt)
	{
		this.overUnderAmt = notNullOrZero(overUnderAmt);
	}

	@Override
	public BigDecimal getAppliedAmt()
	{
		return appliedAmt;
	}

	@Override
	public void setAppliedAmt(final BigDecimal appliedAmt)
	{
		this.appliedAmt = notNullOrZero(appliedAmt);
	}

	@Override
	public boolean isTaboo()
	{
		return taboo;
	}

	@Override
	public void setTaboo(final boolean taboo)
	{
		this.taboo = taboo;
	}

	@Override
	public void distributeNotAppliedAmtToWriteOffs(final PaymentAllocationContext context)
	{
		//
		// Use the FIRST enabled write-off amount.
		final InvoiceWriteOffAmountType type = context.getFirstAllowedWriteOffTypeOrNull();
		distributeNotAppliedAmtToWriteOffs(type);
	}

	@Override
	public void distributeNotAppliedAmtToWriteOffs(InvoiceWriteOffAmountType type)
	{
		// avoid double write off amounts e.g. when changing one of the allowance check boxes
		resetAllWriteOffAmounts();

		final BigDecimal notAppliedAmt = getNotAppliedAmt();
		if (notAppliedAmt.signum() == 0)
		{
			return;
		}

		if (type == null)
		{
			return;
		}
		setWriteOffAmtOfType(type, notAppliedAmt);
	}

	@Override
	public void setWriteOffManual(final PaymentAllocationContext context, final InvoiceWriteOffAmountType writeOffType, BigDecimal writeOffAmt)
	{
		final BigDecimal openAmt = getOpenAmtConv();

		// WriteOff amount shall not be bigger then open amount.
		if (writeOffAmt.compareTo(openAmt) > 0)
		{
			writeOffAmt = openAmt;
		}

		//
		// Make sure the write-off amount is not above a given percentage of open amount
		final BigDecimal writeOffExceedOpenAmtTolerance = context.getWriteOffExceedOpenAmtTolerance();
		final BigDecimal writeOffAmt_MaxAllowed = writeOffExceedOpenAmtTolerance.signum() >= 0 ? openAmt.multiply(writeOffExceedOpenAmtTolerance) : BigDecimal.ZERO;
		if (writeOffAmt_MaxAllowed.signum() >= 0 // We have a writeOff tolerance defined
				&& writeOffAmt.compareTo(writeOffAmt_MaxAllowed) > 0 // our write-off amount is above the tolerance
		)
		{
			context.addWarning(new AdempiereException("@PaymentAllocationForm.WriteOffWarn@"
					+ "\n @Type@: @" + writeOffType.getAD_Message() + "@"
					+ "\n @Amount@: " + writeOffAmt
					+ "\n @Max@: " + writeOffAmt_MaxAllowed.setScale(2, RoundingMode.HALF_UP)));
		}

		//
		// Set the write-off amount
		setWriteOffAmtOfType(writeOffType, writeOffAmt);
		setTaboo(true); // don't affect this row on calculation.

		//
		// Re-calculate the AppliedAmt as "Open amount" minus "sum of WriteOff amounts"
		setAppliedAmt_As_OpenAmt_Minus_WriteOffAmts();
	}

	private void setAppliedAmt_As_OpenAmt_Minus_WriteOffAmts()
	{
		final BigDecimal openAmt = getOpenAmtConv();
		final BigDecimal writeOffSum = getWriteOffsSum();
		final BigDecimal appliedAmt = openAmt.subtract(writeOffSum);
		setAppliedAmt(appliedAmt);
	}

	@Override
	public void setAppliedAmtAndUpdate(final PaymentAllocationContext context, final BigDecimal appliedAmt)
	{
		setAppliedAmt(appliedAmt);
		distributeNotAppliedAmtToWriteOffs(context);
	}

	@Override
	public void recalculateWriteOffAmounts(final PaymentAllocationContext context)
	{
		final boolean allowAutomaticCalculations = !isTaboo();
		//
		// Row allows automatic calculations:
		// * set AppliedAmt=OpenAmt
		// * reset ALL write-off amounts
		if (allowAutomaticCalculations)
		{
			final BigDecimal openAmt = getOpenAmtConv();
			setAppliedAmtAndUpdate(context, openAmt);
		}
		//
		// Rows disallows automatic calculations (i.e. user manually changed some amounts, before):
		// * set all disabled write-off columns to zero
		// * set AppliedAmt = OpenAmt - sum of WriteOff amounts
		else
		{
			for (final InvoiceWriteOffAmountType type : context.getNotAllowedWriteOffTypes())
			{
				setWriteOffAmtOfType(type, BigDecimal.ZERO);
			}

			setAppliedAmt_As_OpenAmt_Minus_WriteOffAmts();
		}
	}

	@Override
	public PayableDocument copyAsPayableDocument()
	{
		final InvoiceRow invoiceRow = this;
		final InvoiceId invoiceId;
		final OrderId prepayOrderId;
		final boolean creditMemo;
		if (invoiceRow.getC_Invoice_ID() > 0)
		{
			invoiceId = InvoiceId.ofRepoId(invoiceRow.getC_Invoice_ID());
			prepayOrderId = null;
			creditMemo = invoiceRow.isCreditMemo();
		}
		else if (invoiceRow.getC_Order_ID() > 0)
		{
			invoiceId = null;
			prepayOrderId = OrderId.ofRepoId(invoiceRow.getC_Order_ID());
			creditMemo = false;
		}
		else
		{
			throw new AdempiereException("No document reference: " + invoiceRow);
		}

		final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);
		final CurrencyId currencyId = currenciesRepo.getByCurrencyCode(invoiceRow.getCurrencyISOCode()).getId();

		return PayableDocument.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(Env.getClientId(), invoiceRow.getOrgId()))
				.invoiceId(invoiceId)
				.prepayOrderId(prepayOrderId)
				.bpartnerId(BPartnerId.ofRepoIdOrNull(invoiceRow.getC_BPartner_ID()))
				.soTrx(SOTrx.ofBoolean(invoiceRow.isCustomerDocument()))
				.creditMemo(creditMemo)
				.documentNo(invoiceRow.getDocumentNo())
				//
				.openAmt(Money.of(invoiceRow.getOpenAmtConv_APAdjusted(), currencyId))
				.amountsToAllocate(AllocationAmounts.builder()
						.payAmt(Money.of(invoiceRow.getAppliedAmt_APAdjusted(), currencyId))
						.discountAmt(Money.of(invoiceRow.getDiscount_APAdjusted(), currencyId))
						.writeOffAmt(Money.of(invoiceRow.getWriteOffAmt_APAdjusted(), currencyId))
						.build())
				//
				.build();
	}

	//
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//
	public static final class Builder
	{
		private OrgId orgId;
		private int C_Invoice_ID = -1;
		private int C_Order_ID = -1;
		private String documentNo;
		private String docTypeName;
		private Date dateInvoiced;
		// task 09643
		private Date dateAcct;
		private Integer C_BPartner_ID;
		private String BPartnerName;
		private CurrencyCode currencyISOCode;
		private BigDecimal grandTotal;
		private BigDecimal grandTotalConv;
		private BigDecimal openAmtConv;
		private BigDecimal discount = BigDecimal.ZERO;
		private Supplier<BigDecimal> paymentRequestAmtSupplier;
		private BigDecimal multiplierAP;
		private boolean isPrepayOrder = false;
		private String POReference;
		private boolean creditMemo;

		private Builder()
		{
			super();
		}

		public InvoiceRow build()
		{
			return new InvoiceRow(this);
		}

		public Builder setOrgId(OrgId orgId)
		{
			this.orgId = orgId;
			return this;
		}

		public Builder setC_Invoice_ID(final int C_Invoice_ID)
		{
			this.C_Invoice_ID = C_Invoice_ID;
			return this;
		}

		public Builder setC_Order_ID(final int C_Order_ID)
		{
			this.C_Order_ID = C_Order_ID;
			return this;
		}

		public Builder setDocumentNo(final String documentNo)
		{
			this.documentNo = documentNo;
			return this;
		}

		public Builder setDocTypeName(final String docTypeName)
		{
			this.docTypeName = docTypeName;
			return this;
		}

		public Builder setDateInvoiced(final Date dateInvoiced)
		{
			this.dateInvoiced = dateInvoiced;
			return this;
		}

		public Builder setDateAcct(final Date dateAcct)
		{
			this.dateAcct = dateAcct;
			return this;
		}

		public Builder setC_BPartner_ID(final int C_BPartner_ID)
		{
			this.C_BPartner_ID = C_BPartner_ID;
			return this;
		}

		public Builder setBPartnerName(final String BPartnerName)
		{
			this.BPartnerName = BPartnerName;
			return this;
		}

		public Builder setCurrencyISOCode(final CurrencyCode currencyISOCode)
		{
			this.currencyISOCode = currencyISOCode;
			return this;
		}

		public Builder setGrandTotal(final BigDecimal grandTotal)
		{
			this.grandTotal = grandTotal;
			return this;
		}

		public Builder setGrandTotalConv(final BigDecimal grandTotalConv)
		{
			this.grandTotalConv = grandTotalConv;
			return this;
		}

		public Builder setOpenAmtConv(final BigDecimal openAmtConv)
		{
			this.openAmtConv = openAmtConv;
			return this;
		}

		public Builder setDiscount(final BigDecimal discount)
		{
			this.discount = discount;
			return this;
		}

		public Builder setPaymentRequestAmt(final BigDecimal paymentRequestAmt)
		{
			Check.assumeNotNull(paymentRequestAmt, "paymentRequestAmt not null");
			this.paymentRequestAmtSupplier = Suppliers.ofInstance(paymentRequestAmt);
			return this;
		}

		public Builder setPaymentRequestAmt(final Supplier<BigDecimal> paymentRequestAmtSupplier)
		{
			this.paymentRequestAmtSupplier = paymentRequestAmtSupplier;
			return this;
		}

		public Builder setMultiplierAP(final BigDecimal multiplierAP)
		{
			this.multiplierAP = multiplierAP;
			return this;
		}

		public Builder setIsPrepayOrder(final boolean isPrepayOrder)
		{
			this.isPrepayOrder = isPrepayOrder;
			return this;
		}

		public Builder setPOReference(final String POReference)
		{
			this.POReference = POReference;
			return this;
		}

		public Builder setCreditMemo(final boolean creditMemo)
		{
			this.creditMemo = creditMemo;
			return this;
		}
	}

}
