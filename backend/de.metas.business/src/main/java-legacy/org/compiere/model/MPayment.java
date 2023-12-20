/*
 * #%L
 * de.metas.business
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
package org.compiere.model;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.banking.BankAccountId;
import de.metas.banking.api.BankAccountService;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.CacheMgt;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyConversionResult;
import de.metas.currency.ICurrencyBL;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.i18n.IMsgBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentTrxType;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.impl.PaymentBL;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;

/**
 * Payment Model. - retrieve and create payments for invoice
 *
 * <pre>
 *  Event chain
 *  - Payment inserted
 *      C_Payment_Trg fires
 *          update DocumentNo with payment summary
 *  - Payment posted (C_Payment_Post)
 *      create allocation line
 *          C_Allocation_Trg fires
 *              Update C_BPartner Open Item Amount
 *      update invoice (IsPaid)
 *      link invoice-payment if batch
 *
 *  Lifeline:
 *  -   Created by VPayment or directly
 *  -   When changed in VPayment
 *      - old payment is reversed
 *      - new payment created
 *
 *  When Payment is posed, the Allocation is made
 * </pre>
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * <li>FR [ 1948157 ] Is necessary the reference for document reverse
 * @author Carlos Ruiz - globalqss [ 2141475 ] Payment <> allocations must not be completed - implement lots of validations on prepareIt
 * @version $Id: MPayment.java,v 1.4 2006/10/02 05:18:39 jjanke Exp $
 * @sse http://sourceforge.net/tracker/index.php?func=detail&aid=1866214&group_id=176962&atid=879335
 * <li>FR [ 2520591 ] Support multiples calendar for Org
 * Also see http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1948157&group_id=176962
 * <li>FR [ 1866214 ]
 * Also see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 */
public final class MPayment extends X_C_Payment
		implements IDocument
{

	/**
	 *
	 */
	private static final long serialVersionUID = 5273805787122033169L;

	/**
	 * Get Payments Of BPartner
	 *
	 * @param ctx           context
	 * @param C_BPartner_ID id
	 * @param trxName       transaction
	 * @return array
	 */
	public static MPayment[] getOfBPartner(final Properties ctx, final int C_BPartner_ID, final String trxName)
	{
		// FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
		final String whereClause = "C_BPartner_ID=?";
		final List<MPayment> list = new Query(ctx, I_C_Payment.Table_Name, whereClause, trxName)
				.setParameters(C_BPartner_ID)
				.list(MPayment.class);

		//
		final MPayment[] retValue = new MPayment[list.size()];
		list.toArray(retValue);
		return retValue;
	}    // getOfBPartner

	/**************************************************************************
	 * Default Constructor
	 *
	 * @param ctx context
	 * @param C_Payment_ID payment to load, (0 create new payment)
	 * @param trxName trx name
	 */
	public MPayment(final Properties ctx, final int C_Payment_ID, final String trxName)
	{
		super(ctx, C_Payment_ID, trxName);
		if (is_new())
		{
			setDocAction(DOCACTION_Complete);
			setDocStatus(DocStatus.Drafted.getCode());
			setTrxType(PaymentTrxType.Sales.getCode());
			//
			setR_AvsAddr(R_AVSADDR_Unavailable);
			setR_AvsZip(R_AVSZIP_Unavailable);
			//
			setIsReceipt(true);
			setIsApproved(false);
			setIsAllocated(false);
			setIsOnline(false);
			setIsSelfService(false);
			setIsDelayedCapture(false);
			setIsPrepayment(false);
			setProcessed(false);
			setProcessing(false);
			setPosted(false);
			//
			setPayAmt(BigDecimal.ZERO);
			setDiscountAmt(BigDecimal.ZERO);
			setTaxAmt(BigDecimal.ZERO);
			setWriteOffAmt(BigDecimal.ZERO);
			setIsOverUnderPayment(false);
			setOverUnderAmt(BigDecimal.ZERO);
			//
			setDateTrx(new Timestamp(System.currentTimeMillis()));
			setDateAcct(getDateTrx());
			setTenderType(TenderType.Check.getCode());

			PaymentBL.markNotReconciledNoSave(this);
		}
	}   // MPayment

	/**
	 * Load Constructor
	 *
	 * @param ctx     context
	 * @param rs      result set record
	 * @param trxName transaction
	 */
	public MPayment(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}    // MPayment

	/**
	 * Logger
	 */
	private static final Logger s_log = LogManager.getLogger(MPayment.class);
	//	/** Error Message */
	//	private String m_errorMessage = null;

	/**
	 * Reversal Indicator
	 */
	private static final String REVERSE_INDICATOR = "^";

	/**
	 * Is Cashbook Transfer Trx
	 *
	 * @return true if Cash Trx
	 */
	public boolean isCashTrx()
	{
		return Services.get(IPaymentBL.class).isCashTrx(this);
	}    // isCashTrx

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		// metas: tsa: us025b: begin: If is a cash bank, set TenderType=Cash
		final BankAccountId orgBankAccountId = BankAccountId.ofRepoIdOrNull(getC_BP_BankAccount_ID());
		if (orgBankAccountId != null)
		{
			final BankAccountService bankAccountService = SpringContextHolder.instance.getBean(BankAccountService.class);
			if (bankAccountService.isCashBank(orgBankAccountId))
			{
				setTenderType(TenderType.Cash.getCode());
			}
		}

		// We have a charge
		if (getC_Charge_ID() > 0)
		{
			if (newRecord || is_ValueChanged("C_Charge_ID"))
			{
				setC_Order_ID(0);
				setC_Invoice_ID(0);
				setWriteOffAmt(BigDecimal.ZERO);
				setDiscountAmt(BigDecimal.ZERO);
				setIsOverUnderPayment(false);
				setOverUnderAmt(BigDecimal.ZERO);
				setIsPrepayment(false);
			}
		}
		// We need a BPartner
		else if (getC_BPartner_ID() == 0 && !isCashTrx())
		{
			if (getC_Invoice_ID() != 0)
			{

			}
			else if (getC_Order_ID() != 0)
			{

			}
			else
			{
				throw new AdempiereException("@NotFound@: @C_BPartner_ID@");
			}
		}
		// Prepayment: No charge and order or project (not as acct dimension)
		if (newRecord
				|| is_ValueChanged("C_Charge_ID") || is_ValueChanged("C_Invoice_ID")
				|| is_ValueChanged("C_Order_ID") || is_ValueChanged("C_Project_ID"))
		{
			setIsPrepayment(getC_Charge_ID() == 0
									&& getC_BPartner_ID() != 0
									&& (getC_Order_ID() != 0
					|| (getC_Project_ID() != 0 && getC_Invoice_ID() == 0)));
			// metas: commented - Write off amount must not be set to 0.
			/*
			 * if (isPrepayment()) { if (newRecord || is_ValueChanged("C_Order_ID") || is_ValueChanged("C_Project_ID")) { setWriteOffAmt(Env.ZERO); setDiscountAmt(Env.ZERO); setIsOverUnderPayment(false);
			 * setOverUnderAmt(Env.ZERO); } }
			 */
		}

		// Document Type/Receipt
		if (getC_DocType_ID() <= 0)
		{
			setC_DocType_ID();
		}
		else
		{
			final MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
			setIsReceipt(dt.isSOTrx());
		}
		setDocumentNo();
		//
		if (getDateAcct() == null)
		{
			setDateAcct(getDateTrx());
		}
		//
		if (!isOverUnderPayment())
		{
			setOverUnderAmt(BigDecimal.ZERO);
		}

		// Organization
		//
		// [ adempiere-Bugs-1885417 ] Validate BP on Payment Prepare or BeforeSave
		// there is bp and (invoice or order)
		if (getC_BPartner_ID() != 0 && (getC_Invoice_ID() != 0 || getC_Order_ID() != 0))
		{
			if (getC_Invoice_ID() != 0)
			{
				final I_C_Invoice inv = getC_Invoice();
				Check.errorIf(inv.getC_BPartner_ID() != getC_BPartner_ID(),
							  "Payment {} has C_BPartner_ID={}, but invoice {} has C_BPartner_ID={}",
							  this, getC_BPartner_ID(), inv, inv.getC_BPartner_ID());
			}
			if (getC_Order_ID() != 0)
			{
				final I_C_Order ord = getC_Order();
				Check.errorIf(ord.getC_BPartner_ID() != getC_BPartner_ID(),
							  "Payment {} has C_BPartner_ID={}, but order {} has C_BPartner_ID={}",
							  this, getC_BPartner_ID(), ord, ord.getC_BPartner_ID());
			}
		}

		return true;
	}    // beforeSave

	/**
	 * Test Allocation (and set allocated flag)
	 *
	 * @return true if updated
	 */
	@Deprecated
	public boolean testAllocation()
	{
		return Services.get(IPaymentBL.class).testAllocation(this);
	}    // testAllocation

	/**
	 * Set Allocated Flag for payments
	 *
	 * @param ctx           context
	 * @param C_BPartner_ID if 0 all
	 * @param trxName       trx
	 */
	public static void setIsAllocated(final Properties ctx, final int C_BPartner_ID, final String trxName)
	{
		String sql = "SELECT * FROM C_Payment "
				+ "WHERE IsAllocated='N' AND DocStatus IN ('CO','CL')";
		if (C_BPartner_ID > 1)
		{
			sql += " AND C_BPartner_ID=?";
		}
		else
		{
			sql += " AND AD_Client_ID=" + Env.getAD_Client_ID(ctx);
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			if (C_BPartner_ID > 1)
			{
				pstmt.setInt(1, C_BPartner_ID);
			}
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final MPayment pay = new MPayment(ctx, rs, trxName);
				if (pay.testAllocation())
				{
					pay.saveEx();
				}
			}
		}
		catch (final Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}    // setIsAllocated

	/**************************************************************************
	 * Credit Card Number
	 *
	 * @param CreditCardNumber CreditCard Number
	 */
	@Override
	public void setCreditCardNumber(final String CreditCardNumber)
	{
		// metas: changed
		// super.setCreditCardNumber (MPaymentValidate.checkNumeric(CreditCardNumber));
		final String ccNumber = MPaymentValidate.checkNumeric(CreditCardNumber);

		volatileCCData.creditCardNumber = ccNumber; // metas 00036
		super.setCreditCardNumber(ccNumber);
	}    // setCreditCardNumber

	/**
	 * Verification Code
	 *
	 * @param newCreditCardVV CC verification
	 */
	@Override
	public void setCreditCardVV(final String newCreditCardVV)
	{
		// metas: changed
		// super.setCreditCardVV (MPaymentValidate.checkNumeric(newCreditCardVV));
		final String ccVV = MPaymentValidate.checkNumeric(newCreditCardVV);

		volatileCCData.creditCardVV = ccVV; // metas 00036
		super.setCreditCardVV(ccVV);
	}    // setCreditCardVV

	/**
	 * Two Digit CreditCard MM
	 *
	 * @param CreditCardExpMM Exp month
	 */
	@Override
	public void setCreditCardExpMM(final int CreditCardExpMM)
	{
		if (CreditCardExpMM < 1 || CreditCardExpMM > 12)
		{

		}
		else
		{
			super.setCreditCardExpMM(CreditCardExpMM);
		}
	}    // setCreditCardExpMM

	/**
	 * Two digit CreditCard YY (til 2020)
	 *
	 * @param newCreditCardExpYY 2 or 4 digit year
	 */
	@Override
	public void setCreditCardExpYY(final int newCreditCardExpYY)
	{
		int CreditCardExpYY = newCreditCardExpYY;
		if (newCreditCardExpYY > 1999)
		{
			CreditCardExpYY = newCreditCardExpYY - 2000;
		}
		super.setCreditCardExpYY(CreditCardExpYY);
	}    // setCreditCardExpYY

	/**
	 * CreditCard Exp MMYY
	 *
	 * @param mmyy Exp in form of mmyy
	 * @return true if valid
	 */
	public boolean setCreditCardExp(final String mmyy)
	{
		if (MPaymentValidate.validateCreditCardExp(mmyy).length() != 0)
		{
			return false;
		}
		//
		final String exp = MPaymentValidate.checkNumeric(mmyy);
		final String mmStr = exp.substring(0, 2);
		final String yyStr = exp.substring(2, 4);
		setCreditCardExpMM(Integer.parseInt(mmStr));
		setCreditCardExpYY(Integer.parseInt(yyStr));
		return true;
	}   // setCreditCardExp

	/**
	 * CreditCard Exp MMYY
	 *
	 * @param delimiter / - or null
	 * @return Exp
	 */
	public String getCreditCardExp(final String delimiter)
	{
		final String mm = String.valueOf(getCreditCardExpMM());
		final String yy = String.valueOf(getCreditCardExpYY());

		final StringBuffer retValue = new StringBuffer();
		if (mm.length() == 1)
		{
			retValue.append("0");
		}
		retValue.append(mm);
		//
		if (delimiter != null)
		{
			retValue.append(delimiter);
		}
		//
		if (yy.length() == 1)
		{
			retValue.append("0");
		}
		retValue.append(yy);
		//
		return (retValue.toString());
	}   // getCreditCardExp

	/**
	 * MICR
	 *
	 * @param MICR MICR
	 */
	@Override
	public void setMicr(final String MICR)
	{
		super.setMicr(MPaymentValidate.checkNumeric(MICR));
	}    // setBankMICR

	/**
	 * Routing No
	 *
	 * @param RoutingNo Routing No
	 */
	@Override
	public void setRoutingNo(final String RoutingNo)
	{
		super.setRoutingNo(MPaymentValidate.checkNumeric(RoutingNo));
	}    // setBankRoutingNo

	/**
	 * Bank Account No
	 *
	 * @param AccountNo AccountNo
	 */
	@Override
	public void setAccountNo(final String AccountNo)
	{
		super.setAccountNo(MPaymentValidate.checkNumeric(AccountNo));
	}    // setBankAccountNo

	/**
	 * Check No
	 *
	 * @param CheckNo Check No
	 */
	@Override
	public void setCheckNo(final String CheckNo)
	{
		super.setCheckNo(MPaymentValidate.checkNumeric(CheckNo));
	}    // setBankCheckNo

	/**
	 * Set DocumentNo to Payment info. If there is a R_PnRef that is set automatically
	 */
	private void setDocumentNo()
	{
		// Cash Transfer
		final TenderType tenderType = TenderType.ofCode(getTenderType());
		if (tenderType.isCash())
		{
			return;
		}

		// Current Document No
		String documentNo = getDocumentNo();
		// Existing reversal
		if (documentNo != null
				&& documentNo.indexOf(REVERSE_INDICATOR) >= 0)
		{
			return;
		}

		// If external number exists - enforce it
		// metas: not enforcing external number
		// if (getR_PnRef() != null && getR_PnRef().length() > 0)
		// {
		// if (!getR_PnRef().equals(documentNo))
		// setDocumentNo(getR_PnRef());
		// return;
		// }
		// metas: end

		documentNo = "";
		// globalqss - read configuration to assign credit card or check number number for Payments
		// Credit Card
		if (tenderType.isCreditCard())
		{
			if (Services.get(ISysConfigBL.class).getBooleanValue("PAYMENT_OVERWRITE_DOCUMENTNO_WITH_CREDIT_CARD", true, getAD_Client_ID()))
			{
				documentNo = getCreditCardType()
						+ " " + Obscure.obscure(getCreditCardNumber())
						+ " " + getCreditCardExpMM()
						+ "/" + getCreditCardExpYY();
			}
		}
		// Own Check No
		else if (tenderType.isCheck()
				&& !isReceipt()
				&& getCheckNo() != null && getCheckNo().length() > 0)
		{
			if (Services.get(ISysConfigBL.class).getBooleanValue("PAYMENT_OVERWRITE_DOCUMENTNO_WITH_CHECK_ON_PAYMENT", true, getAD_Client_ID()))
			{
				documentNo = getCheckNo();
			}
		}
		// Customer Check: Routing: Account #Check
		else if (tenderType.isCheck()
				&& isReceipt())
		{
			if (Services.get(ISysConfigBL.class).getBooleanValue("PAYMENT_OVERWRITE_DOCUMENTNO_WITH_CHECK_ON_RECEIPT", true, getAD_Client_ID()))
			{
				if (getIBAN() != null)
				{
					documentNo = getIBAN() + ": ";
				}
				if (getAccountNo() != null)
				{
					documentNo += getAccountNo();
				}
				if (getCheckNo() != null)
				{
					if (documentNo.length() > 0)
					{
						documentNo += " ";
					}
					documentNo += "#" + getCheckNo();
				}
			}
		}

		// Set Document No
		documentNo = documentNo.trim();
		if (documentNo.length() > 0)
		{
			setDocumentNo(documentNo);
		}
	}    // setDocumentNo

	/**
	 * Set Refernce No (and Document No)
	 *
	 * @param R_PnRef reference
	 */
	@Override
	public void setR_PnRef(final String R_PnRef)
	{
		super.setR_PnRef(R_PnRef);
		if (R_PnRef != null
				// metas: don't overwrite the documentno
				&& Services.get(ISysConfigBL.class).getBooleanValue("PAYMENT_OVERWRITE_DOCUMENTNO_WITH_CREDIT_CARD", true, getAD_Client_ID())
			// metas: end
		)
		{

			setDocumentNo(R_PnRef);
		}
	}    // setR_PnRef

	// ---------------

	/**
	 * Set Payment Amount
	 *
	 * @param PayAmt Pay Amt
	 */
	@Override
	public void setPayAmt(final BigDecimal PayAmt)
	{
		super.setPayAmt(PayAmt == null ? BigDecimal.ZERO : PayAmt);
	}    // setPayAmt

	/**
	 * Set Payment Amount
	 *
	 * @param currencyId currency (optional, may be <= 0)
	 * @param payAmt     amount
	 * @deprecated Will be deleted because it's used only by legacy API
	 */
	@Deprecated
	public void setAmount(final int currencyId, final BigDecimal payAmt)
	{
		final CurrencyId currencyIdEffective;
		if (currencyId > 0)
		{
			currencyIdEffective = CurrencyId.ofRepoId(currencyId);
		}
		else
		{
			currencyIdEffective = Services.get(ICurrencyBL.class).getBaseCurrencyId(ClientId.ofRepoId(getAD_Client_ID()), OrgId.ofRepoId(getAD_Org_ID()));
		}

		setC_Currency_ID(currencyIdEffective.getRepoId());
		setPayAmt(payAmt);
	}   // setAmount

	/**
	 * Discount Amt
	 *
	 * @param DiscountAmt Discount
	 */
	@Override
	public void setDiscountAmt(final BigDecimal DiscountAmt)
	{
		super.setDiscountAmt(DiscountAmt == null ? BigDecimal.ZERO : DiscountAmt);
	}    // setDiscountAmt

	/**
	 * WriteOff Amt
	 *
	 * @param WriteOffAmt WriteOff
	 */
	@Override
	public void setWriteOffAmt(final BigDecimal WriteOffAmt)
	{
		super.setWriteOffAmt(WriteOffAmt == null ? BigDecimal.ZERO : WriteOffAmt);
	}    // setWriteOffAmt

	/**
	 * OverUnder Amt
	 *
	 * @param OverUnderAmt OverUnder
	 */
	@Override
	public void setOverUnderAmt(final BigDecimal OverUnderAmt)
	{
		super.setOverUnderAmt(OverUnderAmt == null ? BigDecimal.ZERO : OverUnderAmt);
	}    // setOverUnderAmt

	/**
	 * Tax Amt
	 *
	 * @param TaxAmt Tax
	 */
	@Override
	public void setTaxAmt(final BigDecimal TaxAmt)
	{
		super.setTaxAmt(TaxAmt == null ? BigDecimal.ZERO : TaxAmt);
	}    // setTaxAmt

	/**
	 * Set Doc Type bases on IsReceipt
	 */
	private void setC_DocType_ID()
	{
		setIsReceiptAndUpdateDocType(isReceipt());
	}    // setC_DocType_ID

	@Deprecated
	public void setIsReceiptAndUpdateDocType(final boolean isReceipt)
	{
		setIsReceipt(isReceipt);

		final IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);
		final DocTypeId docTypeId = docTypesRepo.getDocTypeId(DocTypeQuery.builder()
																	  .docBaseType(isReceipt ? DocBaseType.ARReceipt : DocBaseType.APPayment)
																	  .adClientId(getAD_Client_ID())
																	  .adOrgId(getAD_Org_ID())
																	  .build());
		setC_DocType_ID(docTypeId.getRepoId());
	}

	/**
	 * Verify Document Type with Invoice
	 *
	 * @param pAllocs
	 * @return true if ok
	 */
	private boolean verifyDocType(final MPaymentAllocate[] pAllocs)
	{
		if (getC_DocType_ID() <= 0)
		{
			return false;
		}
		//
		Boolean documentSO = null;
		// Check Invoice First
		if (getC_Invoice_ID() > 0)
		{
			final String sql = "SELECT idt.IsSOTrx "
					+ "FROM C_Invoice i"
					+ " INNER JOIN C_DocType idt ON (i.C_DocType_ID=idt.C_DocType_ID) "
					+ "WHERE i.C_Invoice_ID=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, get_TrxName());
				pstmt.setInt(1, getC_Invoice_ID());
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					documentSO = StringUtils.toBoolean(rs.getString(1));
				}
			}
			catch (final Exception e)
			{
				log.error(sql, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}    // now Order - in Adempiere is allowed to pay PO or receive SO
		else if (getC_Order_ID() > 0)
		{
			final String sql = "SELECT odt.IsSOTrx "
					+ "FROM C_Order o"
					+ " INNER JOIN C_DocType odt ON (o.C_DocType_ID=odt.C_DocType_ID) "
					+ "WHERE o.C_Order_ID=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, get_TrxName());
				pstmt.setInt(1, getC_Order_ID());
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					documentSO = StringUtils.toBoolean(rs.getString(1));
				}
			}
			catch (final Exception e)
			{
				log.error(sql, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}    // now Charge
		else if (getC_Charge_ID() > 0)
		{
			// do nothing about charge
		}  // now payment allocate
		else
		{
			if (pAllocs.length > 0)
			{
				for (final MPaymentAllocate pAlloc : pAllocs)
				{
					final String sql = "SELECT idt.IsSOTrx "
							+ "FROM C_Invoice i"
							+ " INNER JOIN C_DocType idt ON (i.C_DocType_ID=idt.C_DocType_ID) "
							+ "WHERE i.C_Invoice_ID=?";
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					try
					{
						pstmt = DB.prepareStatement(sql, get_TrxName());
						pstmt.setInt(1, pAlloc.getC_Invoice_ID());
						rs = pstmt.executeQuery();
						if (rs.next())
						{
							if (documentSO != null)
							{ // already set, compare with current
								if (documentSO.booleanValue() != (StringUtils.toBoolean(rs.getString(1))))
								{
									return false;
								}
							}
							else
							{
								documentSO = StringUtils.toBoolean(rs.getString(1));
							}
						}
					}
					catch (final Exception e)
					{
						log.error(sql, e);
					}
					finally
					{
						DB.close(rs, pstmt);
						rs = null;
						pstmt = null;
					}
				}
			}
		}

		// DocumentType
		Boolean paymentSO = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final String sql = "SELECT IsSOTrx "
				+ "FROM C_DocType "
				+ "WHERE C_DocType_ID=?";
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_DocType_ID());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				paymentSO = StringUtils.toBoolean(rs.getString(1));
			}
		}
		catch (final Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		// No Payment info
		if (paymentSO == null)
		{
			return false;
		}
		setIsReceipt(paymentSO.booleanValue());

		// 07564
		// Exception: in case we want to make an incoming payment (Zahlungseigang) with a credit memo incoming invoice (eingangsrechnung)
		// it is fine to have the 2 doctypes of different SO Trx

		if (getC_Invoice_ID() > 0)  // task 08438: avoid NPE when getting the doctype
		{
			final I_C_Invoice invoice = getC_Invoice();

			final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
			final I_C_DocType doctype = docTypeDAO.getRecordById(invoice.getC_DocType_ID());

			if (X_C_DocType.DOCBASETYPE_APCreditMemo.equals(doctype.getDocBaseType()))
			{
				// in this case, the invoice doc type is credit memo, with IsSOTrx = false
				// if the payment doc type is IsSOTrx = true, the doctypes are ok
				if (paymentSO)
				{
					return true;
				}
			}
			else if (X_C_DocType.DOCBASETYPE_ARCreditMemo.equals(doctype.getDocBaseType()))
			{
				// in this case, the invoice doc type is credit memo, with IsSOTrx = true
				// if the payment doc type is IsSOTrx = false, the doctypes are ok
				if (!paymentSO)
				{
					return true;
				}
			}
		}
		// We have an Invoice .. and it does not match
		if (documentSO != null
				&& documentSO.booleanValue() != paymentSO.booleanValue())
		{
			return false;
		}
		// OK
		return true;
	}    // verifyDocType

	/**
	 * Verify Payment Allocate is ignored (must not exists) if the payment header has charge/invoice/order
	 *
	 * @param pAllocs
	 * @return true if ok
	 */
	private boolean verifyPaymentAllocateVsHeader(final MPaymentAllocate[] pAllocs)
	{
		if (pAllocs.length > 0)
		{
			if (getC_Charge_ID() > 0 || getC_Invoice_ID() > 0 || getC_Order_ID() > 0)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Verify Payment Allocate Sum must be equal to the Payment Amount
	 *
	 * @param pAllocs
	 * @return true if ok
	 */
	private boolean verifyPaymentAllocateSum(final MPaymentAllocate[] pAllocs)
	{
		BigDecimal sumPaymentAllocates = BigDecimal.ZERO;
		if (pAllocs.length > 0)
		{
			for (final MPaymentAllocate pAlloc : pAllocs)
			{
				sumPaymentAllocates = sumPaymentAllocates.add(pAlloc.getAmount());
			}
			if (getPayAmt().compareTo(sumPaymentAllocates) != 0)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Get Name of Credit Card
	 *
	 * @return Name
	 */
	public String getCreditCardName()
	{
		return getCreditCardName(getCreditCardType());
	}    // getCreditCardName

	private String getCreditCardName(final String CreditCardType)
	{
		if (CreditCardType == null)
		{
			return "--";
		}
		else if (CREDITCARDTYPE_MasterCard.equals(CreditCardType))
		{
			return "MasterCard";
		}
		else if (CREDITCARDTYPE_Visa.equals(CreditCardType))
		{
			return "Visa";
		}
		else if (CREDITCARDTYPE_Amex.equals(CreditCardType))
		{
			return "Amex";
		}
		else if (CREDITCARDTYPE_ATM.equals(CreditCardType))
		{
			return "ATM";
		}
		else if (CREDITCARDTYPE_Diners.equals(CreditCardType))
		{
			return "Diners";
		}
		else if (CREDITCARDTYPE_Discover.equals(CreditCardType))
		{
			return "Discover";
		}
		else if (CREDITCARDTYPE_PurchaseCard.equals(CreditCardType))
		{
			return "PurchaseCard";
		}
		return "?" + CreditCardType + "?";
	}    // getCreditCardName

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	public void addDescription(final String description)
	{
		final String desc = getDescription();
		if (desc == null)
		{
			setDescription(description);
		}
		else
		{
			setDescription(desc + " | " + description);
		}
	}    // addDescription

	/**
	 * Get Pay Amt
	 *
	 * @param absolute if true the absolute amount (i.e. negative if payment)
	 * @return amount
	 */
	public BigDecimal getPayAmt(final boolean absolute)
	{
		if (isReceipt())
		{
			return super.getPayAmt();
		}
		return super.getPayAmt().negate();
	}    // getPayAmt

	@Override
	public boolean processIt(final String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocumentBL.class).processIt(this, processAction); // task 09824
	}    // process

	/**
	 * Process Message
	 */
	private String m_processMsg = null;
	/**
	 * Just Prepared Flag
	 */
	private boolean m_justPrepared = false;

	@Override
	public boolean unlockIt()
	{
		setProcessing(false);
		return true;
	}    // unlockIt

	@Override
	public boolean invalidateIt()
	{
		setDocAction(DOCACTION_Prepare);
		return true;
	}    // invalidateIt

	@Override
	public String prepareIt()
	{
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return DocStatus.Invalid.getCode();
		}

		// Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateAcct(),
							isReceipt() ? DocBaseType.ARReceipt : DocBaseType.APPayment, getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return DocStatus.Invalid.getCode();
		}

		// Unsuccessful Online Payment
		if (isOnline() && !isApproved())
		{
			if (getR_Result() != null)
			{
				m_processMsg = "@OnlinePaymentFailed@";
			}
			else
			{
				m_processMsg = "@PaymentNotProcessed@";
			}
			return DocStatus.Invalid.getCode();
		}

		//
		// Waiting Payment - Need to create Invoice & Shipment
		final OrderId orderId = OrderId.ofRepoIdOrNull(getC_Order_ID());
		if (orderId != null && getC_Invoice_ID() <= 0)
		{
			final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);

			final I_C_Order order = ordersRepo.getById(orderId);
			final DocStatus orderDocStatus = DocStatus.ofCode(order.getDocStatus());
			if (orderDocStatus.isWaitingForPayment())
			{
				order.setC_Payment_ID(getC_Payment_ID());
				order.setDocAction(IDocument.ACTION_WaitComplete);
				order.setDateAcct(getDateAcct()); // Prepayment Order shall receive their DateAcct from Payment, because the Order Dateacct could be far back

				Services.get(IDocumentBL.class).processEx(order, IDocument.ACTION_WaitComplete);
				ordersRepo.save(order);

				// Set Invoice
				final MInvoice[] invoices = MOrder.getInvoices(orderId);
				final int length = invoices.length;
				if (length > 0)
				{
					setC_Invoice_ID(invoices[length - 1].getC_Invoice_ID());
				}
				//
				if (getC_Invoice_ID() == 0)
				{
					// metas (2009-0027-G5): with our new So Sub Type,
					// the invoice hasn't been created yet
					// m_processMsg = "@NotFound@ @C_Invoice_ID@";
					// return DocAction.STATUS_Invalid;
				}
			}    // WaitingPayment
		}

		final MPaymentAllocate[] pAllocs = MPaymentAllocate.get(this);

		// Consistency of Invoice / Document Type and IsReceipt
		if (!verifyDocType(pAllocs))
		{
			m_processMsg = "@PaymentDocTypeInvoiceInconsistent@";
			return DocStatus.Invalid.getCode();
		}

		// Payment Allocate is ignored if charge/invoice/order exists in header
		if (!verifyPaymentAllocateVsHeader(pAllocs))
		{
			m_processMsg = "@PaymentAllocateIgnored@";
			return DocStatus.Invalid.getCode();
		}

		// Payment Amount must be equal to sum of Allocate amounts
		if (!verifyPaymentAllocateSum(pAllocs))
		{
			m_processMsg = "@PaymentAllocateSumInconsistent@";
			return DocStatus.Invalid.getCode();
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return DocStatus.Invalid.getCode();
		}

		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
		{
			setDocAction(DOCACTION_Complete);
		}
		return DocStatus.InProgress.getCode();
	}    // prepareIt

	@Override
	public boolean approveIt()
	{
		setIsApproved(true);
		return true;
	}    // approveIt

	@Override
	public boolean rejectIt()
	{
		setIsApproved(false);
		return true;
	}    // rejectIt

	@Override
	public String completeIt()
	{
		// Re-Check
		if (!m_justPrepared)
		{
			final DocStatus docStatus = DocStatus.ofCode(prepareIt());
			if (!docStatus.isInProgress())
			{
				return docStatus.getCode();
			}
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
		{
			return DocStatus.Invalid.getCode();
		}

		// Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}
		log.debug("Completed: {}", this);

		// Charge Handling
		if (getC_Charge_ID() != 0)
		{
			setIsAllocated(true);
		}
		else
		{
			allocateIt();    // Create Allocation Records
			testAllocation();
		}

		// Project update
		if (getC_Project_ID() != 0)
		{
			// MProject project = new MProject(getCtx(), getC_Project_ID());
		}

		// Counter Doc
		final MPayment counter = createCounterDoc();
		if (counter != null)
		{
			m_processMsg += " @CounterDoc@: @C_Payment_ID@=" + counter.getDocumentNo();
		}

		// User Validation
		final String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocStatus.Invalid.getCode();
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		//
		setProcessed(true);
		setDocAction(DOCACTION_Reverse_Correct); // issue #347
		return DocStatus.Completed.getCode();
	}    // completeIt

	/**
	 * Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo()
	{
		final I_C_DocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete())
		{
			setDateTrx(new Timestamp(System.currentTimeMillis()));
		}
		if (dt.isOverwriteSeqOnComplete())
		{
			final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
			final String value = documentNoFactory.forDocType(getC_DocType_ID(), true) // useDefiniteSequence=true
					.setDocumentModel(this)
					.setFailOnError(false)
					.build();
			if (value != null && value != IDocumentNoBuilder.NO_DOCUMENTNO)
			{
				setDocumentNo(value);
			}
		}
	}

	/**
	 * Create Counter Document
	 *
	 * @return payment
	 */
	private MPayment createCounterDoc()
	{
		// Is this a counter doc ?
		if (getRef_Payment_ID() != 0)
		{
			return null;
		}

		// Org Must be linked to BPartner
		final MOrg org = MOrg.get(getCtx(), getAD_Org_ID());
		final int counterC_BPartner_ID = org.getLinkedC_BPartner_ID(get_TrxName());
		if (counterC_BPartner_ID == 0)
		{
			return null;
		}
		// Business Partner needs to be linked to Org
		final I_C_BPartner bp = Services.get(IBPartnerDAO.class).getById(getC_BPartner_ID());
		final int counterAD_Org_ID = bp.getAD_OrgBP_ID();
		if (counterAD_Org_ID <= 0)
		{
			return null;
		}

		final I_C_BPartner counterBP = Services.get(IBPartnerDAO.class).getById(counterC_BPartner_ID);
		// MOrgInfo counterOrgInfo = MOrgInfo.get(getCtx(), counterAD_Org_ID);

		// Document Type
		int C_DocTypeTarget_ID = 0;
		final MDocTypeCounter counterDT = MDocTypeCounter.getCounterDocType(getCtx(), getC_DocType_ID());
		if (counterDT != null)
		{
			if (!counterDT.isCreateCounter() || !counterDT.isValid())
			{
				return null;
			}
			C_DocTypeTarget_ID = counterDT.getCounter_C_DocType_ID();
		}
		else
		// indirect
		{
			C_DocTypeTarget_ID = MDocTypeCounter.getCounterDocType_ID(getCtx(), getC_DocType_ID());
			if (C_DocTypeTarget_ID <= 0)
			{
				return null;
			}
		}

		// Deep Copy
		final MPayment counter = new MPayment(getCtx(), 0, get_TrxName());
		counter.setAD_Org_ID(counterAD_Org_ID);
		counter.setC_BPartner_ID(counterBP.getC_BPartner_ID());
		counter.setIsReceipt(!isReceipt());
		counter.setC_DocType_ID(C_DocTypeTarget_ID);
		counter.setTrxType(getTrxType());
		counter.setTenderType(getTenderType());
		//
		counter.setPayAmt(getPayAmt());
		counter.setDiscountAmt(getDiscountAmt());
		counter.setTaxAmt(getTaxAmt());
		counter.setWriteOffAmt(getWriteOffAmt());
		counter.setIsOverUnderPayment(isOverUnderPayment());
		counter.setOverUnderAmt(getOverUnderAmt());
		counter.setC_Currency_ID(getC_Currency_ID());
		counter.setC_ConversionType_ID(getC_ConversionType_ID());
		//
		counter.setDateTrx(getDateTrx());
		counter.setDateAcct(getDateAcct());
		counter.setRef_Payment_ID(getC_Payment_ID());
		//
		final String sql = "SELECT C_BP_BankAccount_ID FROM C_BP_BankAccount "
				+ "WHERE C_Currency_ID=? AND AD_Org_ID IN (0,?) AND IsActive='Y' "
				+ "ORDER BY IsDefault DESC";
		final int C_BP_BankAccount_ID = DB.getSQLValue(get_TrxName(), sql, getC_Currency_ID(), counterAD_Org_ID);
		counter.setC_BP_BankAccount_ID(C_BP_BankAccount_ID);

		// References
		counter.setC_Activity_ID(getC_Activity_ID());
		counter.setC_Campaign_ID(getC_Campaign_ID());
		counter.setC_Project_ID(getC_Project_ID());
		counter.setUser1_ID(getUser1_ID());
		counter.setUser2_ID(getUser2_ID());
		counter.saveEx(get_TrxName());
		setRef_Payment_ID(counter.getC_Payment_ID());

		// Document Action
		if (counterDT != null)
		{
			if (counterDT.getDocAction() != null)
			{
				counter.setDocAction(counterDT.getDocAction());
				counter.processIt(counterDT.getDocAction());
				counter.saveEx(get_TrxName());
			}
		}
		return counter;
	}    // createCounterDoc

	/**
	 * Allocate It. Only call when there is NO allocation as it will create duplicates. If an invoice exists, it allocates that otherwise it allocates Payment Selection.
	 *
	 * @return true if allocated
	 */
	public boolean allocateIt()
	{
		// Create invoice Allocation - See also MCash.completeIt
		final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(getC_Invoice_ID());
		if (invoiceId != null)
		{
			return allocateInvoice(invoiceId);
		}

		if (getC_Order_ID() != 0)
		{
			return false;
		}

		// Allocate to multiple Payments based on entry
		final MPaymentAllocate[] pAllocs = MPaymentAllocate.get(this);
		if (pAllocs.length == 0)
		{
			return false;
		}

		final MAllocationHdr alloc = new MAllocationHdr(getCtx(), false,
														getDateTrx(), getC_Currency_ID(),
														Services.get(IMsgBL.class).translate(getCtx(), "C_Payment_ID") + ": " + getDocumentNo(),
														get_TrxName());
		alloc.setAD_Org_ID(getAD_Org_ID());
		if (!alloc.save())
		{
			log.error("P.Allocations not created");
			return false;
		}
		// Lines
		for (final MPaymentAllocate pa : pAllocs)
		{
			MAllocationLine aLine = null;
			if (isReceipt())
			{
				aLine = new MAllocationLine(alloc, pa.getAmount(),
											pa.getDiscountAmt(), pa.getWriteOffAmt(), pa.getOverUnderAmt());
			}
			else
			{
				aLine = new MAllocationLine(alloc, pa.getAmount().negate(),
											pa.getDiscountAmt().negate(), pa.getWriteOffAmt().negate(), pa.getOverUnderAmt().negate());
			}
			aLine.setDocInfo(pa.getC_BPartner_ID(), 0, pa.getC_Invoice_ID());
			aLine.setPaymentInfo(getC_Payment_ID(), 0);
			if (!aLine.save(get_TrxName()))
			{
				log.warn("P.Allocations - line not saved");
			}
			else
			{
				pa.setC_AllocationLine_ID(aLine.getC_AllocationLine_ID());
				pa.saveEx();
			}
		}
		// Should start WF
		alloc.processIt(IDocument.ACTION_Complete);
		m_processMsg = "@C_AllocationHdr_ID@: " + alloc.getDocumentNo();
		return alloc.save(get_TrxName());
	}    // allocateIt

	/**
	 * Allocate single AP/AR Invoice
	 *
	 * @return true if allocated
	 */
	private boolean allocateInvoice(@NonNull final InvoiceId invoiceId)
	{
		// 04627 begin (commented out old code)
		// calculate actual allocation
		// BigDecimal allocationAmt = getPayAmt(); // underpayment
		// when allocating an invoice, we don't want to allocate more than the invoice's open amount
		final I_C_Invoice invoice = Services.get(IInvoiceBL.class).getById(invoiceId);

		Check.errorIf(invoice == null, "Invoice cannot be null since C_Invoice_ID > 0, C_Invoice_ID = {}", invoiceId);

		final Money invoiceOpenAmt = Services.get(IAllocationDAO.class).retrieveOpenAmtInInvoiceCurrency(invoice, false);
		final Money payAmt = getPayAmtAsMoney();

		// note: zero is ok, but with negative, i don't see the case and don't know what to do
		Check.errorIf(invoiceOpenAmt.signum() < 0, "{} has a negative open amount = {}", invoice, invoiceOpenAmt);
		Check.errorIf(payAmt.signum() < 0, "{} has a negative PayAmt = {}", this, payAmt);

		// 04627 end

		// 04614 (commented out old code)
		// OverUnderAmt shall not be part of the allocation amount. It's just an information!
		// if (getOverUnderAmt().signum() < 0 && getPayAmt().signum() > 0)
		// allocationAmt = allocationAmt.add(getOverUnderAmt()); // overpayment (negative)

		// we're not yet there :-(
		//		// @formatter:off
//		final I_C_AllocationHdr alloc =
//				Services.get(IAllocationBL.class).newBuilder(this)
//					.setAD_Org_ID(getAD_Org_ID())
//					.setDateTrx(getDateTrx())
//					.setC_Currency_ID(getC_Currency_ID())
//					.setIsManual(false)
//					.setDescription(Msg.translate(getCtx(), "C_Payment_ID") + ": " + getDocumentNo() + " [1]")
//					.addLine()
//						.setAmount(isReceipt() ? allocationAmt: allocationAmt.negate())
//						.setDiscountAmt(isReceipt() ? getDiscountAmt(): getDiscountAmt().negate())
//						.setWriteOffAmt(isReceipt() ? getWriteOffAmt():getWriteOffAmt().negate())
//						.setOverUnderAmt(isReceipt() ? getOverUnderAmt(): getOverUnderAmt().negate())
//						.setC_Payment_ID(getC_Payment_ID())
//						.setC_BPartner_ID(getC_BPartner_ID())
//						.setC_Invoice_ID(getC_Invoice_ID())
//					.lineDone()
//					.create(true);
//		// @formatter:on
		//
		final MAllocationHdr alloc = new MAllocationHdr(getCtx(), false,
														getDateTrx(), invoiceOpenAmt.getCurrencyId().getRepoId(),
														Services.get(IMsgBL.class).translate(getCtx(), "C_Payment_ID") + ": " + getDocumentNo() + " [1]", get_TrxName());

		// task 09643
		// When the Allocation has both invoice and payment, allocation's accounting date must e the max between the invoice date and payment date

		Timestamp dateAcct = getDateAcct();

		final Timestamp invoiceDateAcct = invoice.getDateAcct();

		if (invoiceDateAcct.after(dateAcct))
		{
			dateAcct = invoiceDateAcct;
		}

		alloc.setDateAcct(dateAcct);

		// allocation's trx date must e the max between the invoice date and payment date

		Timestamp dateTrx = getDateTrx();

		final Timestamp invoiceDateTrx = invoice.getDateInvoiced();

		if (invoiceDateTrx.after(dateTrx))
		{
			dateTrx = invoiceDateTrx;
		}

		alloc.setDateTrx(dateTrx);

		alloc.setAD_Org_ID(getAD_Org_ID());
		alloc.saveEx();

		final Money allocationAmt = getAllocationAmt(alloc.getDateTrx(),
													 invoiceOpenAmt,
													 payAmt,
													 CurrencyConversionTypeId.ofRepoIdOrNull(invoice.getC_ConversionType_ID()));

		MAllocationLine aLine = null;
		if (isReceipt())
		{
			aLine = new MAllocationLine(alloc, allocationAmt.toBigDecimal(),
										getDiscountAmt(), getWriteOffAmt(), getOverUnderAmt());
		}
		else
		{
			aLine = new MAllocationLine(alloc, allocationAmt.toBigDecimal().negate(),
										getDiscountAmt().negate(), getWriteOffAmt().negate(), getOverUnderAmt().negate());
		}
		aLine.setDocInfo(getC_BPartner_ID(), 0, getC_Invoice_ID());
		aLine.setC_Payment_ID(getC_Payment_ID());
		aLine.saveEx(get_TrxName());
		// Should start WF
		alloc.processIt(IDocument.ACTION_Complete);
		alloc.saveEx(get_TrxName());
		m_processMsg = "@C_AllocationHdr_ID@: " + alloc.getDocumentNo();

		// Get Project from Invoice
		final int C_Project_ID = DB.getSQLValue(get_TrxName(),
												"SELECT MAX(C_Project_ID) FROM C_Invoice WHERE C_Invoice_ID=?", getC_Invoice_ID());
		if (C_Project_ID > 0 && getC_Project_ID() == 0)
		{
			setC_Project_ID(C_Project_ID);
		}
		else if (C_Project_ID > 0 && getC_Project_ID() > 0 && C_Project_ID != getC_Project_ID())
		{
			log.warn("Invoice C_Project_ID=" + C_Project_ID
							 + " <> Payment C_Project_ID=" + getC_Project_ID());
		}
		return true;
	}    // allocateInvoice

	/**
	 * De-allocate Payment. Unkink Invoices and Orders and delete Allocations
	 */
	private void deAllocate()
	{
		if (getC_Order_ID() != 0)
		{
			setC_Order_ID(0);
		}
		// if (getC_Invoice_ID() == 0)
		// return;
		// De-Allocate all
		final MAllocationHdr[] allocations = MAllocationHdr.getOfPayment(getCtx(),
																		 getC_Payment_ID(), get_TrxName());
		for (MAllocationHdr allocation : allocations)
		{
			final DocStatus allocDocStatus = DocStatus.ofCode(allocation.getDocStatus());

			// 07570: Skip allocations which were already Reversed or Voided
			if (allocDocStatus.isReversedOrVoided())
			{
				continue;
			}

			allocation.set_TrxName(get_TrxName());
			allocation.setDocAction(IDocument.ACTION_Reverse_Correct);
			if (!allocation.processIt(IDocument.ACTION_Reverse_Correct))
			{
				throw new AdempiereException(allocation.getProcessMsg());
			}
			allocation.saveEx();
		}

		// Unlink (in case allocation did not get it)
		if (getC_Invoice_ID() != 0)
		{
			// Invoice
			String sql = "UPDATE C_Invoice "
					+ "SET C_Payment_ID = NULL "
					+ "WHERE C_Invoice_ID=" + getC_Invoice_ID()
					+ " AND C_Payment_ID=" + getC_Payment_ID();
			int no = DB.executeUpdateAndSaveErrorOnFail(sql, get_TrxName());
			if (no != 0)
			{
				CacheMgt.get().reset(I_C_Invoice.Table_Name, getC_Invoice_ID());
				log.debug("Unlink Invoice #" + no);
			}
			// Order
			sql = "UPDATE C_Order o "
					+ "SET C_Payment_ID = NULL "
					+ "WHERE EXISTS (SELECT * FROM C_Invoice i "
					+ "WHERE o.C_Order_ID=i.C_Order_ID AND i.C_Invoice_ID=" + getC_Invoice_ID() + ")"
					+ " AND C_Payment_ID=" + getC_Payment_ID();
			no = DB.executeUpdateAndSaveErrorOnFail(sql, get_TrxName());
			if (no != 0)
			{
				log.debug("Unlink Order #" + no);
			}
		}

		setC_Invoice_ID(0);
		setIsAllocated(false);
	}    // deallocate

	/**
	 * Void Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean voidIt()
	{
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		//
		// Make sure not already closed
		final DocStatus docStatus = DocStatus.ofCode(getDocStatus());
		if (docStatus.isClosedReversedOrVoided())
		{
			throw new AdempiereException("Document Closed: " + docStatus);
		}

		//
		// Make sure the payment was not already reconciled
		if (isReconciled())
		{
			throw new AdempiereException("@void.payment@");
		}

		//
		// Not Processed
		if (docStatus.isNotProcessed())
		{
			addDescription(Services.get(IMsgBL.class).getMsg(getCtx(), "Voided") + " (" + getPayAmt() + ")");
			setPayAmt(BigDecimal.ZERO);
			setDiscountAmt(BigDecimal.ZERO);
			setWriteOffAmt(BigDecimal.ZERO);
			setOverUnderAmt(BigDecimal.ZERO);
			setIsAllocated(false);
			// Unlink & De-Allocate
			deAllocate();
		}
		else
		{
			return reverseCorrectIt();
		}

		//
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}    // voidIt

	/**
	 * Close Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean closeIt()
	{
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}
		setDocAction(DOCACTION_None);
		return true;
	}    // closeIt

	/**
	 * Reverse Correction
	 *
	 * @return true if success
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

		// Std Period open?
		Timestamp dateAcct = getDateAcct();
		if (!MPeriod.isOpen(getCtx(), dateAcct,
							isReceipt() ? DocBaseType.ARReceipt : DocBaseType.APPayment, getAD_Org_ID()))
		{
			dateAcct = new Timestamp(System.currentTimeMillis());
		}

		//
		// Make sure the payment was not already reconciled
		if (isReconciled())
		{
			throw new AdempiereException("@void.payment@");
		}

		// Create Reversal
		final MPayment reversal = new MPayment(getCtx(), 0, get_TrxName());
		copyValues(this, reversal);
		reversal.setClientOrg(this);
		reversal.setC_Order_ID(0);
		reversal.setC_Invoice_ID(0);
		reversal.setDateAcct(dateAcct);
		//
		reversal.setDocumentNo(getDocumentNo() + REVERSE_INDICATOR);    // indicate reversals
		reversal.setDocStatus(DocStatus.Drafted.getCode());
		reversal.setDocAction(DOCACTION_Complete);
		//
		reversal.setPayAmt(getPayAmt().negate());
		reversal.setDiscountAmt(getDiscountAmt().negate());
		reversal.setWriteOffAmt(getWriteOffAmt().negate());
		reversal.setOverUnderAmt(getOverUnderAmt().negate());
		//
		reversal.setIsAllocated(true);
		// reversal.setIsReconciled(reconciled); // will be handled by banking module
		reversal.setIsOnline(false);
		reversal.setIsApproved(true);
		reversal.setR_PnRef(null);
		reversal.setR_Result(null);
		reversal.setR_RespMsg(null);
		reversal.setR_AuthCode(null);
		reversal.setR_Info(null);
		reversal.setProcessing(false);
		reversal.setProcessed(false);
		reversal.setPosted(false);
		reversal.setDescription(getDescription());
		reversal.addDescription("{->" + getDocumentNo() + ")");
		// FR [ 1948157 ]
		reversal.setReversal_ID(getC_Payment_ID());
		reversal.saveEx(get_TrxName());
		// Post Reversal
		if (!reversal.processIt(IDocument.ACTION_Complete))
		{
			m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
			return false;
		}
		reversal.closeIt();
		reversal.setDocStatus(DocStatus.Reversed.getCode());
		reversal.setDocAction(DOCACTION_None);
		reversal.save(get_TrxName());

		// Unlink & De-Allocate
		deAllocate();
		// setIsReconciled(reconciled); // will be handled by banking module
		setIsAllocated(true);    // the allocation below is overwritten
		// Set Status
		addDescription("(" + reversal.getDocumentNo() + "<-)");
		setDocStatus(DocStatus.Reversed.getCode());
		setDocAction(DOCACTION_None);
		setProcessed(true);
		// FR [ 1948157 ]
		setReversal_ID(reversal.getC_Payment_ID());

		// Create automatic Allocation
		final MAllocationHdr alloc = new MAllocationHdr(getCtx(), false,
														getDateTrx(), getC_Currency_ID(),
														Services.get(IMsgBL.class).translate(getCtx(), "C_Payment_ID") + ": " + reversal.getDocumentNo(), get_TrxName());
		alloc.setAD_Org_ID(getAD_Org_ID());
		if (!alloc.save())
		{
			log.warn("Automatic allocation - hdr not saved");
		}
		else
		{
			// Original Allocation
			MAllocationLine aLine = new MAllocationLine(alloc, getPayAmt(true),
														BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
			aLine.setDocInfo(getC_BPartner_ID(), 0, 0);
			aLine.setPaymentInfo(getC_Payment_ID(), 0);
			if (!aLine.save(get_TrxName()))
			{
				log.warn("Automatic allocation - line not saved");
			}
			// Reversal Allocation
			aLine = new MAllocationLine(alloc, reversal.getPayAmt(true),
										BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
			aLine.setDocInfo(reversal.getC_BPartner_ID(), 0, 0);
			aLine.setPaymentInfo(reversal.getC_Payment_ID(), 0);
			if (!aLine.save(get_TrxName()))
			{
				log.warn("Automatic allocation - reversal line not saved");
			}
		}
		alloc.processIt(IDocument.ACTION_Complete);
		alloc.save(get_TrxName());
		//
		final StringBuffer info = new StringBuffer(reversal.getDocumentNo());
		info.append(" - @C_AllocationHdr_ID@: ").append(alloc.getDocumentNo());

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

		m_processMsg = info.toString();
		return true;
	}    // reverseCorrectionIt

	/**
	 * Reverse Accrual - none
	 *
	 * @return false, not supported
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
		{
			return false;
		}

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
		{
			return false;
		}

		return false;
	}    // reverseAccrualIt

	/**
	 * Re-activate
	 *
	 * @return true if success
	 */
	@Override
	public boolean reActivateIt()
	{
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		if (!reverseCorrectIt())
		{
			return false;
		}

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		return true;
	}    // reActivateIt

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer("MPayment[");
		sb.append(get_ID()).append("-").append(getDocumentNo())
				.append(",Receipt=").append(isReceipt())
				.append(",PayAmt=").append(getPayAmt())
				.append(",Discount=").append(getDiscountAmt())
				.append(",WriteOff=").append(getWriteOffAmt())
				.append(",OverUnder=").append(getOverUnderAmt());
		return sb.toString();
	}    // toString

	/**
	 * Get Document Info
	 *
	 * @return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		final MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}    // getDocumentInfo

	/**
	 * Create PDF
	 *
	 * @return File or null
	 */
	@Override
	public File createPDF()
	{
		try
		{
			final File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		}
		catch (final Exception e)
		{
			log.error("Could not create PDF - " + e.getMessage());
		}
		return null;
	}    // getPDF

	/**
	 * Create PDF file
	 *
	 * @param file output file
	 * @return file if success
	 */
	public File createPDF(final File file)
	{
		// ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.PAYMENT, getC_Payment_ID());
		// if (re == null)
		return null;
		// return re.getPDF(file);
	}    // createPDF

	/*************************************************************************
	 * Get Summary
	 *
	 * @return Summary of Document
	 */
	@Override
	public String getSummary()
	{
		final StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		// : Total Lines = 123.00 (#1)
		sb.append(": ")
				.append(Services.get(IMsgBL.class).translate(getCtx(), "PayAmt")).append("=").append(getPayAmt())
				.append(",").append(Services.get(IMsgBL.class).translate(getCtx(), "WriteOffAmt")).append("=").append(getWriteOffAmt());
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
		{
			sb.append(" - ").append(getDescription());
		}
		return sb.toString();
	}    // getSummary

	@Override
	public InstantAndOrgId getDocumentDate()
	{
		return InstantAndOrgId.ofTimestamp(getDateTrx(), OrgId.ofRepoId(getAD_Org_ID()));
	}

	/**
	 * Get Process Message
	 *
	 * @return clear text error message
	 */
	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}    // getProcessMsg

	/**
	 * Get Document Owner (Responsible)
	 *
	 * @return AD_User_ID
	 */
	@Override
	public int getDoc_User_ID()
	{
		return getCreatedBy();
	}    // getDoc_User_ID

	/**
	 * Get Document Approval Amount
	 *
	 * @return amount payment(AP) or write-off(AR)
	 */
	@Override
	public BigDecimal getApprovalAmt()
	{
		if (isReceipt())
		{
			return getWriteOffAmt();
		}
		return getPayAmt();
	}    // getApprovalAmt

	// metas: begin
	// metas 00036
	// adding a place where credit card data can be stored in memory.
	// The data might not be stored to DB due to privacy protection rules.
	private static class VolatileCCData
	{
		private String creditCardNumber;
		private String creditCardVV;
	}

	private final VolatileCCData volatileCCData = new VolatileCCData();

	// metas end

	@Override
	public void setC_Order_ID(final int C_Order_ID)
	{

		// first things first
		super.setC_Order_ID(C_Order_ID);

		// checking doc type of C_Order_ID=" + C_Order_ID
		final I_C_Order order = getC_Order();
		if (order == null || order.getC_Order_ID() <= 0)
		{
			return;
		}

		final DocTypeId orderDocTypeId = DocTypeId.ofRepoId(
				firstGreaterThanZero( // if the order and payment are linked from the order's model interceptor, we might need to fall back to the order's target-doctype
									  order.getC_DocType_ID(),
									  order.getC_DocTypeTarget_ID()));
		if (orderDocTypeId == null)
		{
			return; // shall not happen
		}
		final I_C_DocType orderDocType = Services.get(IDocTypeDAO.class).getRecordById(orderDocTypeId);

		final String orderDocSubType = orderDocType.getDocSubType();
		if (!X_C_DocType.DOCSUBTYPE_POSOrder.equals(orderDocSubType)
				&& !X_C_DocType.DOCSUBTYPE_OnCreditOrder.equals(orderDocSubType)
				&& !X_C_DocType.DOCSUBTYPE_PrepayOrder.equals(orderDocSubType))
		{

			// nothing to do
			return;
		}

		// retrieving the order's invoice
		final String whereClause = I_C_Invoice.COLUMNNAME_C_Order_ID + "=?"
				+ " AND " + I_C_Invoice.COLUMNNAME_C_DocType_ID + "=?";

		final Object[] parameters = { C_Order_ID, orderDocType.getC_DocTypeInvoice_ID() };

		final List<MInvoice> invoices = new Query(getCtx(), I_C_Invoice.Table_Name, whereClause, get_TrxName())
				.setParameters(parameters)
				.setClient_ID()
				.list(MInvoice.class);

		if (invoices.size() == 1)
		{
			System.out.println("invoices.get(0).get_ID():" + invoices.get(0).get_ID());
			setC_Invoice_ID(invoices.get(0).getC_Invoice_ID());
		}
	}

	// metas 00036
	public String getVolatileCreditCardNumber()
	{
		if (volatileCCData.creditCardNumber == null)
		{
			return getCreditCardNumber();
		}
		return volatileCCData.creditCardNumber;
	}

	public String getVolatileCreditCardVV()
	{
		if (volatileCCData.creditCardVV == null)
		{
			return getCreditCardVV();
		}
		return volatileCCData.creditCardVV;
	}

	@NonNull
	private Money getAllocationAmt(
			@NonNull final Timestamp dateTrx,
			@NonNull final Money invoiceOpenAmt,
			@NonNull final Money payAmt,
			@Nullable final CurrencyConversionTypeId conversionTypeId)
	{
		if (payAmt.getCurrencyId().equals(invoiceOpenAmt.getCurrencyId()))
		{
			return payAmt.min(invoiceOpenAmt);
		}

		final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
		final CurrencyConversionContext currencyConversionContext = currencyBL.createCurrencyConversionContext(TimeUtil.asInstant(dateTrx),
																											   conversionTypeId,
																											   ClientId.ofRepoId(getAD_Client_ID()),
																											   OrgId.ofRepoId(getAD_Org_ID()));

		final CurrencyConversionResult currencyConversionResult = currencyBL.convert(currencyConversionContext, payAmt, invoiceOpenAmt.getCurrencyId());
		return currencyConversionResult.getAmountAsMoney().min(invoiceOpenAmt);
	}

	@NonNull
	private Money getPayAmtAsMoney()
	{
		return Money.of(getPayAmt(), CurrencyId.ofRepoId(getC_Currency_ID()));
	}
} // MPayment
