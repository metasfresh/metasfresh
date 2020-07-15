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
import java.util.Date;

import javax.annotation.Nullable;

import org.adempiere.util.lang.ObjectUtils;

import de.metas.banking.payment.paymentallocation.service.IPaymentDocument;
import de.metas.banking.payment.paymentallocation.service.PaymentDocument;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.util.Services;
import org.compiere.util.TimeUtil;

public final class PaymentRow extends AbstractAllocableDocRow implements IPaymentRow
{
	public static Builder builder()
	{
		return new Builder();
	}

	public static IPaymentRow castOrNull(final IAllocableDocRow row)
	{
		return row instanceof IPaymentRow ? (IPaymentRow)row : null;
	}

	private final OrgId orgId;
	private final String docTypeName;
	private final int C_Payment_ID;
	private final String documentNo;
	private final int C_BPartner_ID;
	private final String BPartnerName;
	private final Date paymentDate;
	// task 09643
	private final Date dateAcct;
	private final CurrencyCode currencyISOCode;
	private final BigDecimal payAmt;
	private final BigDecimal payAmtConv;
	private final BigDecimal openAmtConv;
	private final BigDecimal multiplierAP;

	//
	private boolean selected = false;
	private BigDecimal discountAmt = BigDecimal.ZERO;
	private BigDecimal appliedAmt = BigDecimal.ZERO;
	private boolean taboo = false;

	private PaymentRow(final Builder builder)
	{
		// FIXME: validate: not null, etc
		orgId = builder.orgId;
		docTypeName = builder.docTypeName;
		C_Payment_ID = builder.C_Payment_ID;
		documentNo = builder.documentNo;
		C_BPartner_ID = builder.C_BPartner_ID;
		BPartnerName = builder.BPartnerName;
		paymentDate = builder.paymentDate;
		dateAcct = builder.dateAcct;
		currencyISOCode = builder.currencyISOCode;
		//
		// Amounts
		multiplierAP = builder.multiplierAP;
		payAmt = notNullOrZero(builder.payAmt);
		payAmtConv = notNullOrZero(builder.payAmtConv);
		openAmtConv = notNullOrZero(builder.openAmtConv);
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
	public String getDocTypeName()
	{
		return docTypeName;
	}

	@Override
	public int getC_Payment_ID()
	{
		return C_Payment_ID;
	}

	@Override
	public String getDocumentNo()
	{
		return documentNo;
	}

	@Override
	public String getBPartnerName()
	{
		return BPartnerName;
	}

	@Override
	public Date getDocumentDate()
	{
		return paymentDate;
	}

	@Override
	public Date getDateAcct()
	{
		return dateAcct;
	}

	@Override
	public String getCurrencyISOCodeAsString()
	{
		return currencyISOCode != null ? currencyISOCode.toThreeLetterCode() : null;
	}

	public CurrencyCode getCurrencyISOCode()
	{
		return currencyISOCode;
	}

	@Override
	public BigDecimal getPayAmt()
	{
		return payAmt;
	}

	@Override
	public BigDecimal getPayAmtConv()
	{
		return payAmtConv;
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
	public void setAppliedAmtAndUpdate(PaymentAllocationContext context, BigDecimal appliedAmt)
	{
		setAppliedAmt(appliedAmt);

		// NOTE: no other field needs to be updated
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
	public int getC_BPartner_ID()
	{
		return C_BPartner_ID;
	}

	@Override
	public BigDecimal getOpenAmtConv()
	{
		return openAmtConv;
	}

	@Override
	public BigDecimal getMultiplierAP()
	{
		return multiplierAP;
	}

	@Override
	public boolean isCreditMemo()
	{
		// a payment is never a credit memo
		return false;
	}

	@Override
	public IPaymentDocument copyAsPaymentDocument()
	{
		final PaymentRow paymentRow = this;

		final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);
		final CurrencyId currencyId = currenciesRepo.getByCurrencyCode(paymentRow.getCurrencyISOCode()).getId();

		return PaymentDocument.builder()
				.orgId(paymentRow.getOrgId())
				.paymentId(PaymentId.ofRepoId(paymentRow.getC_Payment_ID()))
				.bpartnerId(BPartnerId.ofRepoIdOrNull(paymentRow.getC_BPartner_ID()))
				.paymentDirection(paymentRow.getPaymentDirection())
				.documentNo(paymentRow.getDocumentNo())
				.openAmt(Money.of(paymentRow.getOpenAmtConv_APAdjusted(), currencyId))
				.amountToAllocate(Money.of(paymentRow.getAppliedAmt_APAdjusted(), currencyId))
				.dateTrx(TimeUtil.asLocalDate(paymentRow.getDocumentDate()))
				.build();

	}

	@Override
	public BigDecimal getDiscountAmt()
	{
		return discountAmt;
	}

	@Override
	public void setDiscountAmt(BigDecimal discountAmt)
	{
		this.discountAmt = notNullOrZero(discountAmt);
	}

	@Override
	public void resetDiscountAmount()
	{
		setDiscountAmt(BigDecimal.ZERO);
	}

	@Override
	public void setDiscountManual(final PaymentAllocationContext context, BigDecimal discountAmt)
	{
		final BigDecimal openAmt = getOpenAmtConv();

		// Discount amount shall not be bigger then open amount.
		// cusotmer documents - amounts are all the time positives
		if (isCustomerDocument() && (discountAmt.compareTo(openAmt) > 0))
		{
			discountAmt = openAmt;
		}

		// vendor documents
		if (isVendorDocument())
		{
			// Regularly, open amounts shall be positives all the time and discount negatives
			// discount shall be negative and maximum the negated open amount
			// we allow also possible wrong case(but the result will not be wrong) in order to make possible for the user to correct payments

			// case: open amount positve and discount pozitive and bigger then open amount
			if ((openAmt.signum() > 0 && discountAmt.signum() > 0 && (discountAmt.compareTo(openAmt) >= 0))
					// case: open amount positive and discount negative and bigger in absolute value then open amount
					|| (openAmt.signum() > 0 && discountAmt.signum() < 0 && (discountAmt.abs().compareTo(openAmt) > 0))
					// case: open amount negative and discount positive and bigger in absolute value then open amount
					|| (openAmt.signum() < 0 && discountAmt.signum() > 0 && (discountAmt.compareTo(openAmt.abs()) > 0))
					// case: open amount negative and discount negative and bigger in absolute value then open amount
					|| (openAmt.signum() < 0 && discountAmt.signum() < 0 && (discountAmt.abs().compareTo(openAmt.abs()) >= 0)))
			{
				discountAmt = openAmt.negate();
			}

			// case: open amount positive and discount positive and lower in absolute value then open amount
			if ((openAmt.signum() > 0 && discountAmt.signum() > 0 && (discountAmt.compareTo(openAmt) < 0))
					// case: open amount negative and discount negative and lower in absolute value then open amount
					|| (openAmt.signum() < 0 && discountAmt.signum() < 0 && (discountAmt.abs().compareTo(openAmt.abs()) < 0)))
			{
				discountAmt = discountAmt.negate();
			}

		}

		//
		// Set the discount amount
		setDiscountAmt(discountAmt);
		setTaboo(true); // don't affect this row on calculation from now on

		//
		// Re-calculate the AppliedAmt as "Open amount" minus "discount amount"
		setAppliedAmt_As_OpenAmt_Minus_DiscountAmt();
	}

	private void setAppliedAmt_As_OpenAmt_Minus_DiscountAmt()
	{
		final BigDecimal openAmt = getOpenAmtConv();
		final BigDecimal discount = getDiscountAmt();
		final BigDecimal appliedAmt;

		if (isCustomerDocument())
		{
			appliedAmt = openAmt.subtract(discount);
		}
		else
		{
			// discount allways should be negative, and open amount allways positive
			// but we need to treat also other f**ked up cases

			// wrong case, with discount and open amount positive (user should not get in this case, but we treat it anyway)
			// => the result in this case will be that we increase open amount
			if (openAmt.signum() > 0 && discount.signum() > 0)
			{
				appliedAmt = openAmt.add(discount);
			}
			// regular case
			else if (openAmt.signum() >= 0 && discount.signum() < 0)
			{
				appliedAmt = openAmt.subtract(discount.abs());
			}
			// wrong case(should not exist), with discount positive and open amount negative
			// => the result in this case will be that we decrease negative open amount

			// wrong case, with discount negative and open amount negative
			// => the result in this case will be that we increase negative open amount
			else
			{
				appliedAmt = openAmt.add(discount);
			}

		}
		setAppliedAmt(appliedAmt);
	}

	@Override
	public void recalculateDiscountAmount(final PaymentAllocationContext context)
	{
		final boolean allowAutomaticCalculations = !isTaboo();
		//
		// Row allows automatic calculations:
		// * set AppliedAmt=OpenAmt
		// * reset DiscountAmt
		if (allowAutomaticCalculations)
		{
			final BigDecimal openAmt = getOpenAmtConv();
			setAppliedAmtAndUpdate(context, openAmt);
		}
		//
		// Row does not allow automatic
		// * set DiscountAmt to zero if discount amount is not allowed
		// * set AppliedAmt = OpenAmt - sum of WriteOff amounts
		else
		{
			if (!context.getAllowedWriteOffTypes().contains(InvoiceWriteOffAmountType.Discount))
			{
				setDiscountAmt(BigDecimal.ZERO);
			}

			setAppliedAmt_As_OpenAmt_Minus_DiscountAmt();
		}
	}

	public static final class Builder
	{
		private OrgId orgId;
		private String docTypeName;
		private int C_Payment_ID;
		private String documentNo;
		private int C_BPartner_ID;
		private String BPartnerName;
		private Date paymentDate;
		// task 09643
		private Date dateAcct;
		private CurrencyCode currencyISOCode;
		private BigDecimal payAmt;
		private BigDecimal payAmtConv;
		private BigDecimal openAmtConv;
		private BigDecimal multiplierAP;

		private Builder()
		{
			super();
		}

		public PaymentRow build()
		{
			return new PaymentRow(this);
		}

		public Builder setOrgId(final OrgId orgId)
		{
			this.orgId = orgId;
			return this;
		}

		public Builder setDocTypeName(final String docTypeName)
		{
			this.docTypeName = docTypeName;
			return this;
		}

		public Builder setC_Payment_ID(final int C_Payment_ID)
		{
			this.C_Payment_ID = C_Payment_ID;
			return this;
		}

		public Builder setDocumentNo(final String documentNo)
		{
			this.documentNo = documentNo;
			return this;
		}

		public Builder setBPartnerName(final String bPartnerName)
		{
			BPartnerName = bPartnerName;
			return this;
		}

		public Builder setPaymentDate(final Date paymentDate)
		{
			this.paymentDate = paymentDate;
			return this;
		}

		/**
		 * task 09643: separate transaction date from the accounting date
		 * 
		 * @param dateAcct
		 * @return
		 */
		public Builder setDateAcct(final Date dateAcct)
		{
			this.dateAcct = dateAcct;
			return this;
		}

		public Builder setCurrencyISOCode(@Nullable final CurrencyCode currencyISOCode)
		{
			this.currencyISOCode = currencyISOCode;
			return this;
		}

		public Builder setPayAmt(final BigDecimal payAmt)
		{
			this.payAmt = payAmt;
			return this;
		}

		public Builder setPayAmtConv(final BigDecimal payAmtConv)
		{
			this.payAmtConv = payAmtConv;
			return this;
		}

		public Builder setC_BPartner_ID(final int C_BPartner_ID)
		{
			this.C_BPartner_ID = C_BPartner_ID;
			return this;
		}

		public Builder setOpenAmtConv(final BigDecimal openAmtConv)
		{
			this.openAmtConv = openAmtConv;
			return this;
		}

		public Builder setMultiplierAP(final BigDecimal multiplierAP)
		{
			this.multiplierAP = multiplierAP;
			return this;
		}

	}
}
