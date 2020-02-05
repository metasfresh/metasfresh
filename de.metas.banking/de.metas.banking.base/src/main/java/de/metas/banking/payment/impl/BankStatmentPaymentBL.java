package de.metas.banking.payment.impl;

/*
 * #%L
 * de.metas.banking.base
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

import com.google.common.collect.ImmutableSet;
import de.metas.banking.api.BankAccountId;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.model.IBankStatementLineOrRef;
import de.metas.banking.model.I_C_BankStatement;
import de.metas.banking.payment.IBankStatmentPaymentBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.ICurrencyBL;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.DefaultPaymentBuilder;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.X_C_AllocationHdr;
import org.compiere.model.X_C_Payment;
import org.compiere.model.X_I_BankStatement;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class BankStatmentPaymentBL implements IBankStatmentPaymentBL
{

	private static final transient Logger logger = LogManager.getLogger(BankStatmentPaymentBL.class);

	@Override
	public void setC_Payment(@NonNull final IBankStatementLineOrRef lineOrRef, @Nullable final I_C_Payment payment)
	{
		if (payment == null)
		{
			lineOrRef.setC_Payment_ID(0);
			lineOrRef.setDiscountAmt(BigDecimal.ZERO);
			lineOrRef.setWriteOffAmt(BigDecimal.ZERO);
			lineOrRef.setIsOverUnderPayment(false);
			lineOrRef.setOverUnderAmt(BigDecimal.ZERO);
			setPayAmt(lineOrRef, BigDecimal.ZERO);
			return;
		}

		lineOrRef.setC_Payment_ID(payment.getC_Payment_ID());
		lineOrRef.setC_Currency_ID(payment.getC_Currency_ID());
		lineOrRef.setC_BPartner_ID(payment.getC_BPartner_ID());
		lineOrRef.setC_Invoice_ID(payment.getC_Invoice_ID());
		//
		BigDecimal multiplier = BigDecimal.ONE;
		if (!payment.isReceipt())
		{
			multiplier = multiplier.negate();
		}

		final BigDecimal payAmt = payment.getPayAmt().multiply(multiplier);

		setPayAmt(lineOrRef, payAmt);
		lineOrRef.setDiscountAmt(payment.getDiscountAmt().multiply(multiplier));
		lineOrRef.setWriteOffAmt(payment.getWriteOffAmt().multiply(multiplier));
		lineOrRef.setOverUnderAmt(payment.getOverUnderAmt().multiply(multiplier));
		lineOrRef.setIsOverUnderPayment(payment.isOverUnderPayment());

		//
		// Bank Statement Line specific:
		if (lineOrRef instanceof org.compiere.model.I_C_BankStatementLine)
		{
			final org.compiere.model.I_C_BankStatementLine bsl = (org.compiere.model.I_C_BankStatementLine)lineOrRef;
			bsl.setDescription(payment.getDescription());
		}
	}

	@Override
	public void findOrCreateUnreconciledPaymentsAndLinkToBankStatementLine(final de.metas.banking.model.I_C_BankStatementLine line)
	{
		final boolean manualActionRequired = findAndLinkPaymentToBankStatementLineIfPossible(line);

		if (!manualActionRequired)
		{
			setOrCreateAndLinkPaymentToBankStatementLine(line, null);
		}
	}

	/**
	 * @return true if the automatic flow should STOP as manual action is required; false if the automatic flow should continue
	 */
	private boolean findAndLinkPaymentToBankStatementLineIfPossible(final de.metas.banking.model.I_C_BankStatementLine line)
	{
		// a payment is already linked
		if (line.getC_Payment_ID() > 0)
		{
			return true;
		}
		if (line.getC_BPartner_ID() <= 0)
		{
			return true;
		}

		final boolean isReceipt = line.getStmtAmt().signum() >= 0;
		final BigDecimal expectedPaymentAmount = isReceipt ? line.getStmtAmt() : line.getStmtAmt().negate();

		final Money money = Money.of(expectedPaymentAmount, CurrencyId.ofRepoId(line.getC_Currency_ID()));
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(line.getC_BPartner_ID());
		final ImmutableSet<PaymentId> possiblePayments = Services.get(IPaymentDAO.class).retrieveAllMatchingPayments(isReceipt, bPartnerId, money);

		// Don't create a new Payment and don't link any of the existing payments if there are multiple payments found.
		// The user must fix this case manually by choosing the correct Payment
		if (possiblePayments.size() > 1)
		{
			return true;
		}

		if (possiblePayments.size() == 1)
		{
			line.setC_Payment_ID(possiblePayments.iterator().next().getRepoId());
			InterfaceWrapperHelper.save(line);
		}
		return false;
	}

	@Override
	public Optional<PaymentId> setOrCreateAndLinkPaymentToBankStatementLine(@NonNull final de.metas.banking.model.I_C_BankStatementLine line, @Nullable final PaymentId paymentIdToSet)
	{
		// a payment is already linked
		if (line.getC_Payment_ID() > 0)
		{
			return Optional.of(PaymentId.ofRepoId(line.getC_Payment_ID()));
		}

		if (paymentIdToSet != null)
		{
			final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);

			final I_C_Payment payment = paymentDAO.getById(paymentIdToSet);
			setC_Payment(line, payment);

			InterfaceWrapperHelper.save(line);
			paymentDAO.save(payment);
			return Optional.of(paymentIdToSet);
		}

		if (line.getC_BPartner_ID() <= 0)
		{
			return Optional.empty();
		}

		final CurrencyId currencyId = CurrencyId.ofRepoId(line.getC_Currency_ID());
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(line.getC_BPartner_ID());
		final OrgId orgId = OrgId.ofRepoId(line.getAD_Org_ID());
		final LocalDate statementLineDate = TimeUtil.asLocalDate(line.getStatementLineDate());

		final Optional<BankAccountId> bankAccountIdOptional = Services.get(IBPBankAccountDAO.class).retrieveFirstIdByBPartnerAndCurrency(bpartnerId, currencyId);
		if (!bankAccountIdOptional.isPresent())
		{
			return Optional.empty();
		}

		final boolean isReceipt = line.getStmtAmt().signum() >= 0;
		final BigDecimal payAmount = isReceipt ? line.getStmtAmt() : line.getStmtAmt().negate();

		final I_C_Payment createdPayment = createAndCompletePayment(bankAccountIdOptional.get(), statementLineDate, payAmount, isReceipt, orgId, bpartnerId, currencyId);
		setC_Payment(line, createdPayment);

		InterfaceWrapperHelper.save(line);
		return Optional.of(PaymentId.ofRepoId(createdPayment.getC_Payment_ID()));
	}

	private I_C_Payment createAndCompletePayment(
			@NonNull final BankAccountId bankAccountId,
			@NonNull final LocalDate dateAcct,
			@NonNull final BigDecimal payAmt,
			final boolean isReceipt,
			@NonNull final OrgId adOrgId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final CurrencyId currencyId)
	{
		final DefaultPaymentBuilder paymentBuilder;

		final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
		if (isReceipt)
		{
			paymentBuilder = paymentBL.newInboundReceiptBuilder();
		}
		else
		{
			paymentBuilder = paymentBL.newOutboundPaymentBuilder();
		}

		return paymentBuilder
				.adOrgId(adOrgId)
				.bpartnerId(bpartnerId)
				.bpBankAccountId(bankAccountId)
				.currencyId(currencyId)
				.payAmt(payAmt)
				.dateAcct(dateAcct)
				.dateTrx(dateAcct)
				.description("Automatically created when BankStatement was completed.")
				.tenderType(TenderType.DirectDeposit)
				.createAndProcess();
	}

	public void setPayAmt(final IBankStatementLineOrRef lineOrRef, final BigDecimal payAmt)
	{
		setPayAmt(lineOrRef, payAmt, false);
	}

	public void setPayAmt(final IBankStatementLineOrRef lineOrRef, final BigDecimal payAmt, final boolean updateStatementAmt)
	{
		Check.assumeNotNull(lineOrRef, "lineOrRef not null");

		if (lineOrRef instanceof I_C_BankStatementLine)
		{
			final I_C_BankStatementLine bsl = (I_C_BankStatementLine)lineOrRef;
			bsl.setTrxAmt(payAmt);
			if (updateStatementAmt || bsl.getStmtAmt().signum() == 0)
			{
				bsl.setStmtAmt(payAmt);
			}
		}
		else if (lineOrRef instanceof I_C_BankStatementLine_Ref)
		{
			((I_C_BankStatementLine_Ref)lineOrRef).setTrxAmt(payAmt);
		}
		else
		{
			throw new IllegalStateException("Object not supported: " + lineOrRef);
		}
	}

	/**
	 * Create Payment for Import
	 *
	 * @param ibs import bank statement
	 * @return Message
	 */
	@Override
	public String createPayment(final X_I_BankStatement ibs)
	{
		if (ibs == null || ibs.getC_Payment_ID() != 0)
		{
			return "--";
		}
		logger.debug(ibs.toString());
		if (ibs.getC_Invoice_ID() == 0 && ibs.getC_BPartner_ID() == 0)
		{
			throw new AdempiereException("@NotFound@ @C_Invoice_ID@ / @C_BPartner_ID@");
		}
		if (ibs.getC_BP_BankAccount_ID() == 0)
		{
			throw new AdempiereException("@NotFound@ @C_BP_BankAccount_ID@");
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(ibs);
		final String trxName = InterfaceWrapperHelper.getTrxName(ibs);

		//
		final MPayment payment = createPayment(ctx,
				ibs.getC_Invoice_ID(), ibs.getC_BPartner_ID(),
				ibs.getC_Currency_ID(), ibs.getStmtAmt(), ibs.getTrxAmt(),
				BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, // metas
				ibs.getC_BP_BankAccount_ID(),
				ibs.getStatementLineDate() == null ? ibs.getStatementDate() : ibs.getStatementLineDate(),
				ibs.getDateAcct(), ibs.getDescription(), ibs.getAD_Org_ID(),
				trxName);
		if (payment == null)
		{
			throw new AdempiereException("Could not create Payment");
		}

		ibs.setC_Payment_ID(payment.getC_Payment_ID());
		ibs.setC_Currency_ID(payment.getC_Currency_ID());
		ibs.setTrxAmt(payment.getPayAmt());
		ibs.save();
		//
		String retString = "@C_Payment_ID@ = " + payment.getDocumentNo();
		if (payment.getOverUnderAmt().signum() != 0)
		{
			retString += " - @OverUnderAmt@=" + payment.getOverUnderAmt();
		}
		return retString;
	}    // createPayment - Import

	/**
	 * Create Payment for BankStatement
	 *
	 * @return Message
	 */
	@Override
	public String createPayment(final MBankStatementLine bslPO)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bslPO);
		final String trxName = InterfaceWrapperHelper.getTrxName(bslPO);

		Check.assumeNotNull(bslPO, "Param bslPO not null");
		if (bslPO.getC_Payment_ID() > 0)
		{
			return "--";
		}
		logger.debug(bslPO.toString());

		// CHANGED
		final I_C_BankStatementLine bsl = InterfaceWrapperHelper.create(bslPO, I_C_BankStatementLine.class);

		final org.compiere.model.I_C_BankStatement bs = bsl.getC_BankStatement();
		final MPayment payment;

		if (!bsl.isMultiplePaymentOrInvoice()) // only one payment/invoice
		{
			if (bsl.getC_Invoice_ID() <= 0 && bsl.getC_BPartner_ID() <= 0)
			{
				throw new AdempiereException("@NotFound@ @C_Invoice_ID@ / @C_BPartner_ID@");
			}
			//
			payment = createPayment(ctx,
					bsl.getC_Invoice_ID(),
					bsl.getC_BPartner_ID(),
					bsl.getC_Currency_ID(),
					bsl.getStmtAmt(),
					bsl.getTrxAmt(),
					bsl.getDiscountAmt(),
					bsl.getOverUnderAmt(),
					bsl.getWriteOffAmt(),
					bs.getC_BP_BankAccount_ID(),
					bsl.getStatementLineDate(),
					bsl.getDateAcct(),
					bsl.getDescription(),
					bsl.getAD_Org_ID(),
					trxName);
			if (payment == null)
			{
				throw new AdempiereException("Could not create Payment");
			}
			// update statement
			bslPO.setPayment(payment);
			bslPO.saveEx();
		}
		// multiple Invoices, one payment
		// all Invoices must have the same BPartner!
		else if (!bsl.isMultiplePayment())
		{
			final BigDecimal stmtAmt = bsl.getStmtAmt();
			final BigDecimal trxAmt = bsl.getTrxAmt();
			BigDecimal discountAmt = bsl.getDiscountAmt();
			BigDecimal writeOffAmt = bsl.getWriteOffAmt();
			BigDecimal overUnderAmt = bsl.getOverUnderAmt();

			final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
			final List<I_C_BankStatementLine_Ref> refLines = bankStatementDAO.retrieveLineReferences(bsl);

			//
			// Get business partner, validate lines
			int bpartnerId = -1;
			for (final I_C_BankStatementLine_Ref refLine : refLines)
			{
				final int linePartnerId = refLine.getC_BPartner_ID();
				if (bpartnerId > 0 && linePartnerId > 0 && linePartnerId != bpartnerId)
				{
					throw new AdempiereException("@Invalid@ @C_BPartner_ID@");
				}
				if (bpartnerId <= 0 && linePartnerId > 0)
				{
					bpartnerId = refLine.getC_BPartner_ID();
				}
				//
				if (refLine.getC_Payment_ID() > 0)
				{
					throw new AdempiereException("Line " + refLine + " already paid"); // TODO: AD_Message
				}
			}

			// create the payment
			payment = createPayment(ctx,
					0, bpartnerId,
					bsl.getC_Currency_ID(), stmtAmt, trxAmt, discountAmt, overUnderAmt, writeOffAmt,
					bs.getC_BP_BankAccount_ID(), bsl.getStatementLineDate(), bsl.getDateAcct(),
					bsl.getDescription(), bsl.getAD_Org_ID(),
					trxName);
			if (payment == null)
			{
				throw new AdempiereException("Could not create Payment");
			}
			// update statement
			bslPO.setPayment(payment);
			bslPO.saveEx();

			// allocate the payment
			final MAllocationHdr alloc = new MAllocationHdr(ctx, true,
					bsl.getDateAcct(), bsl.getC_Currency_ID(),
					Env.getContext(Env.getCtx(), "#AD_User_Name"),
					trxName);
			alloc.setAD_Org_ID(bsl.getAD_Org_ID());
			alloc.saveEx();
			for (final I_C_BankStatementLine_Ref refLine : refLines)
			{
				final MInvoice inv = new MInvoice(ctx, refLine.getC_Invoice_ID(), trxName);
				BigDecimal amount = refLine.getTrxAmt();
				discountAmt = refLine.getDiscountAmt();
				writeOffAmt = refLine.getWriteOffAmt();
				overUnderAmt = refLine.getOverUnderAmt();
				if (refLine.getC_Currency_ID() != bsl.getC_Currency_ID())
				{
					// convert amounts
					final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);

					final CurrencyId refLineCurrencyId = CurrencyId.ofRepoId(refLine.getC_Currency_ID());
					final CurrencyId bslCurrencyId = CurrencyId.ofRepoId(bsl.getC_Currency_ID());
					final LocalDate dateConv = TimeUtil.asLocalDate(bsl.getDateAcct());
					final CurrencyConversionTypeId conversionTypeId = CurrencyConversionTypeId.ofRepoIdOrNull(inv.getC_ConversionType_ID());
					final ClientId clientId = ClientId.ofRepoId(bsl.getAD_Client_ID());
					final OrgId orgId = OrgId.ofRepoId(bsl.getAD_Org_ID());
					amount = currencyConversionBL.convert(
							amount,
							refLineCurrencyId,
							bslCurrencyId,
							dateConv,
							conversionTypeId,
							clientId,
							orgId);
					discountAmt = currencyConversionBL.convert(
							discountAmt,
							refLineCurrencyId,
							bslCurrencyId,
							dateConv,
							conversionTypeId,
							clientId,
							orgId);
					writeOffAmt = currencyConversionBL.convert(
							writeOffAmt,
							refLineCurrencyId,
							bslCurrencyId,
							dateConv,
							conversionTypeId,
							clientId,
							orgId);
					overUnderAmt = currencyConversionBL.convert(
							overUnderAmt,
							refLineCurrencyId,
							bslCurrencyId,
							dateConv,
							conversionTypeId,
							clientId,
							orgId);
				}
				final MAllocationLine aLine = new MAllocationLine(alloc, amount, discountAmt, writeOffAmt, overUnderAmt);
				aLine.setDocInfo(inv.getC_BPartner_ID(), inv.getC_Order_ID(), inv.get_ID());
				aLine.setC_Payment_ID(payment.get_ID());
				aLine.saveEx();
			}
			final boolean ok = alloc.processIt(X_C_AllocationHdr.DOCACTION_Complete);
			alloc.saveEx();
			if (!ok)
			{
				throw new AdempiereException(alloc.getProcessMsg());
			}
		}
		// multiple Invoices, multiple payments
		else
		{
			throw new AdempiereException("@NotSupported@"); // TODO: implement... i.e. redirect to createPayment(ref)
		}

		//
		String retString = "@C_Payment_ID@ = " + payment.getDocumentNo();
		if (payment.getOverUnderAmt().signum() != 0)
		{
			retString += " - @OverUnderAmt@=" + payment.getOverUnderAmt();
		}
		return retString;
	}    // createPayment

	@Override
	public String createPayment(final I_C_BankStatementLine_Ref ref)
	{
		if (ref == null || ref.getC_Payment_ID() > 0)
		{
			return "--";
		}

		if (ref.getC_Invoice_ID() <= 0 && ref.getC_BPartner_ID() > 0)
		{
			throw new AdempiereException("@NotFound@ @C_Invoice_ID@ / @C_BPartner_ID@");
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(ref);
		final String trxName = InterfaceWrapperHelper.getTrxName(ref);

		final I_C_BankStatementLine bsl = InterfaceWrapperHelper.create(ref.getC_BankStatementLine(), I_C_BankStatementLine.class);
		final I_C_BankStatement bs = InterfaceWrapperHelper.create(bsl.getC_BankStatement(), I_C_BankStatement.class);
		if (bsl == null || bsl.getC_BankStatementLine_ID() == 0 || bs == null || bs.getC_BankStatement_ID() == 0)
		{
			throw new AdempiereException("@NotFound@ MBankStatementLine/MBankStatement");
		}

		if (!bsl.isMultiplePaymentOrInvoice() || !bsl.isMultiplePayment())
		{
			throw new AdempiereException("@IsMultiplePayment@=N");
		}

		final MPayment payment = createPayment(ctx,
				ref.getC_Invoice_ID(), ref.getC_BPartner_ID(),
				ref.getC_Currency_ID(), ref.getTrxAmt(), ref.getTrxAmt(),
				ref.getDiscountAmt(), ref.getOverUnderAmt(), ref.getWriteOffAmt(),
				bs.getC_BP_BankAccount_ID(), bsl.getStatementLineDate(), bsl.getDateAcct(),
				bsl.getDescription(), bsl.getAD_Org_ID(),
				trxName);
		if (payment == null)
		{
			throw new AdempiereException("Could not create Payment");
		}
		// update statement
		ref.setC_Payment(payment);
		InterfaceWrapperHelper.save(ref);
		//
		String retString = "@C_Payment_ID@ = " + payment.getDocumentNo();
		if (payment.getOverUnderAmt().signum() != 0)
		{
			retString += " - @OverUnderAmt@=" + payment.getOverUnderAmt();
		}
		return retString;
	}    // createPayment

	// CHANGED - add discount/overunder/writeoff

	/**
	 * Create actual Payment.
	 *
	 * @param C_Invoice_ID        invoice
	 * @param C_BPartner_ID       partner ignored when invoice exists
	 * @param C_Currency_ID       currency
	 * @param StmtAmt             statement amount may be <code>null</code>. If is is <code>null</code> and <code>TrxAmt</code> is also null, then the invoice's open ampount is used as pay amount
	 * @param TrxAmt              maybe be <code>null</code>. If set, then it is used as the payment's payAmt. If <code>null</code>, then <code>StmAmt</code> is used instead
	 * @param C_BP_BankAccount_ID bank account
	 * @param DateTrx             transaction date
	 * @param DateAcct            accounting date
	 * @param Description         description
	 * @param AD_Org_ID           org
	 * @return payment
	 */
	private MPayment createPayment(final Properties ctx,
			final int C_Invoice_ID,
			final int C_BPartner_ID,
			final int C_Currency_ID,
			final BigDecimal StmtAmt,
			final BigDecimal TrxAmt,
			final BigDecimal discountAmt,
			final BigDecimal overUnderAmt,
			final BigDecimal writeOffAmt, // metas
			final int C_BP_BankAccount_ID,
			final Timestamp DateTrx,
			final Timestamp DateAcct,
			final String Description,
			final int AD_Org_ID,
			final String trxName)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug(C_Invoice_ID + " - " + C_BPartner_ID + " - " + C_Currency_ID
					+ " - " + StmtAmt + " - " + TrxAmt + " - " + discountAmt
					+ " - " + overUnderAmt + " - " + writeOffAmt + " - " + C_BP_BankAccount_ID
					+ " - " + DateTrx + " - " + DateAcct + " - " + Description + " - " + AD_Org_ID);
		}

		// Trx Amount = Payment overwrites Statement Amount if defined
		BigDecimal PayAmt = TrxAmt;
		if (PayAmt == null || PayAmt.signum() == 0)
		{
			PayAmt = StmtAmt;
		}
		if (C_Invoice_ID <= 0 && (PayAmt == null || PayAmt.signum() == 0))
		{
			throw new IllegalStateException("@PayAmt@ = 0");
		}
		if (PayAmt == null)
		{
			PayAmt = BigDecimal.ZERO;
		}
		//
		final MPayment payment = new MPayment(ctx, 0, trxName);
		payment.setAD_Org_ID(AD_Org_ID);
		payment.setC_BP_BankAccount_ID(C_BP_BankAccount_ID);

		// FIXME this is a weak workaround
		if (C_BP_BankAccount_ID > 0)
		{
			payment.setTenderType(TenderType.DirectDeposit.getCode());
		}
		else
		{
			payment.setTenderType(TenderType.Cash.getCode());
		}

		if (DateTrx != null)
		{
			payment.setDateTrx(DateTrx);
		}
		else if (DateAcct != null)
		{
			payment.setDateTrx(DateAcct);
		}
		if (DateAcct != null)
		{
			payment.setDateAcct(DateAcct);
		}
		else
		{
			payment.setDateAcct(payment.getDateTrx());
		}
		payment.setDescription(Description);
		//
		if (C_Invoice_ID > 0)
		{
			final MInvoice invoice = new MInvoice(ctx, C_Invoice_ID, ITrx.TRXNAME_None);
			payment.setIsReceiptAndUpdateDocType(invoice.isSOTrx());
			payment.setC_Invoice_ID(invoice.getC_Invoice_ID());
			payment.setC_BPartner_ID(invoice.getC_BPartner_ID());
			if (PayAmt.signum() != 0)    // explicit Amount
			{
				payment.setC_Currency_ID(C_Currency_ID);
				if (invoice.isSOTrx())
				{
					payment.setPayAmt(PayAmt);
				}
				else
				{
					payment.setPayAmt(PayAmt.negate());
				}
				// CHANGED - explicite discount/writeoff/overunder
				payment.setDiscountAmt(discountAmt);
				payment.setWriteOffAmt(writeOffAmt);
				// payment.setOverUnderAmt(overUnderAmt);
				// ensure correct overunder amount
				payment.setOverUnderAmt(invoice.getOpenAmt(true, null)
						.subtract(payment.getPayAmt())
						.subtract(discountAmt)
						.subtract(writeOffAmt));
			}
			else
			// set Pay Amout from Invoice
			{
				payment.setC_Currency_ID(invoice.getC_Currency_ID());
				payment.setPayAmt(invoice.getOpenAmt(true, null));
				payment.setDiscountAmt(discountAmt);
				payment.setWriteOffAmt(writeOffAmt);
				// payment.setOverUnderAmt(overUnderAmt);
			}
		}
		else if (C_BPartner_ID > 0)
		{
			payment.setC_BPartner_ID(C_BPartner_ID);
			payment.setC_Currency_ID(C_Currency_ID);
			if (PayAmt.signum() < 0)    // Payment
			{
				payment.setPayAmt(PayAmt.abs());
				payment.setIsReceiptAndUpdateDocType(false);
			}
			else
			// Receipt
			{
				payment.setPayAmt(PayAmt);
				payment.setIsReceiptAndUpdateDocType(true);
			}
			// metas: begin
			payment.setDiscountAmt(discountAmt);
			payment.setWriteOffAmt(writeOffAmt);
			payment.setOverUnderAmt(overUnderAmt);
			// metas: end
		}
		else
		{
			throw new AdempiereException("@NotFound@ @C_Invoice_ID@ / @C_BPartner_ID@"); // metas
		}
		payment.saveEx(); // metas
		//
		final boolean ok = payment.processIt(X_C_Payment.DOCACTION_Complete);
		payment.saveEx();
		if (!ok)
		{
			throw new AdempiereException(payment.getProcessMsg());
		}
		return payment;
	}    // createPayment
}
