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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import static org.compiere.model.X_C_Invoice.PAYMENTRULE_DirectDebit;
import static org.compiere.model.X_C_Invoice.PAYMENTRULE_DirectDeposit;
import static org.compiere.model.X_C_Invoice.PAYMENTRULE_OnCredit;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Constants;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.POInfo;
import org.compiere.model.X_C_Invoice;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.banking.payment.IPaySelectionUpdater;

public class PaySelectionUpdater implements IPaySelectionUpdater
{
	// services
	private static final transient Logger log = LogManager.getLogger(PaySelectionUpdater.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);

	// private static final String INV_WITH_PO = "P";
	// private static final String INV_WITH_PAY_RECEIPT = "R";
	// private static final String INV_EMPLOYEE = "E";
	// private static final String INV_INTERNAL = "C";

	/**
	 * Customer direct debit
	 */
	private static final String INV_C_DD = "CDD";

	/**
	 * Customer remittance
	 */
	private static final String INV_C_RE = "CRE";

	private static final String INV_ALL = "ALL";

	/**
	 * Internal (recurring payment) and vendor invoices
	 */
	private static final String INV_OUT = "OUT";

	/**
	 * Salary/Commission invoices
	 */
	private static final String INV_SAL = "SAL";

	private boolean _configurable = true;
	private Properties _ctx = null;
	private String _trxNameInitial = ITrx.TRXNAME_ThreadInherited;
	private String _trxName = null;

	/** Only When Discount */
	private boolean _onlyDiscount = false;
	/** Only when Due */
	private boolean _onlyDue = false;
	/** Include Disputed */
	private Boolean _includeInDispute = null;
	/** Match Requirement */
	private String _matchRequirement = INV_ALL;
	/** Match Requirement */
	private Timestamp _payDate = null;

	/** Payment Rule */
	private String _paymentRule = null;
	/** BPartner */
	private int _bpartnerId = 0;
	/** BPartner Group */
	private int _bpGroupId = 0;
	/** Payment Selection */
	private I_C_PaySelection _paySelection;

	private final Set<Integer> paySelectionLineIdsToUpdate = new HashSet<>();
	/** Provides a map of C_Invoice_ID to {@link I_C_PaySelectionLine} which were enqueued to be updated */
	private final Supplier<Map<Integer, I_C_PaySelectionLine>> _invoiceId2paySelectionLineToUpdateSupplier = Suppliers.memoize(new Supplier<Map<Integer, I_C_PaySelectionLine>>()
	{
		@Override
		public Map<Integer, I_C_PaySelectionLine> get()
		{
			if (paySelectionLineIdsToUpdate.isEmpty())
			{
				return new HashMap<>();
			}

			final List<I_C_PaySelectionLine> paySelectionLines = Services.get(IQueryBL.class)
					.createQueryBuilder(I_C_PaySelectionLine.class, getCtx(), getTrxName())
					.addEqualsFilter(org.compiere.model.I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID, getC_PaySelection_ID())
					.addInArrayOrAllFilter(org.compiere.model.I_C_PaySelectionLine.COLUMNNAME_C_PaySelectionLine_ID, paySelectionLineIdsToUpdate)
					.addEqualsFilter(org.compiere.model.I_C_PaySelectionLine.COLUMNNAME_Processed, false) // not already processed
					.create()
					.list();

			final Map<Integer, I_C_PaySelectionLine> invoiceId2paySelectionLine = new HashMap<>(paySelectionLines.size());
			for (final I_C_PaySelectionLine paySelectionLine : paySelectionLines)
			{
				final int invoiceId = paySelectionLine.getC_Invoice_ID();
				final I_C_PaySelectionLine paySelectionLineOld = invoiceId2paySelectionLine.put(invoiceId, paySelectionLine);
				Check.assumeNull(paySelectionLineOld, "Only one pay selection line shall exist for an invoice but we found: {}, {}", paySelectionLine, paySelectionLineOld); // shall not happen
			}

			return invoiceId2paySelectionLine;
		}
	});

	/** Payment Rules (from C_Invoice.PaymentRule's available values list) */
	private final Supplier<Set<String>> _paymentRulesSupplier = Suppliers.memoize(new Supplier<Set<String>>()
	{
		@Override
		public Set<String> get()
		{
			final POInfo invoicePOInfo = POInfo.getPOInfo(I_C_Invoice.Table_Name);
			final int paymentRuleReferenceId = invoicePOInfo.getColumnReferenceValueId(I_C_Invoice.COLUMNNAME_PaymentRule);

			final Set<String> paymentRules = Services.get(IADReferenceDAO.class).retrieveListValues(Env.getCtx(), paymentRuleReferenceId);
			if (paymentRules == null || paymentRules.isEmpty())
			{
				throw new AdempiereException("No active payment rules were found");
			}

			return paymentRules;
		}
	});

	//
	// Statistics
	private int countCreated = 0;
	private int countUpdated = 0;
	private int countDeleted = 0;

	PaySelectionUpdater()
	{
		super();
	}

	@Override
	public void update()
	{
		trxManager.run(getTrxNameInitial(), new TrxRunnableAdapter()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				setTrxName(localTrxName);

				update0();
			}

			@Override
			public void doFinally()
			{
				setTrxName(null); // reset current transaction
			}
		});
	}

	private final void update0()
	{
		// Lock this updater. Shall not be configurable anymore.
		assertConfigurable();
		_configurable = false;

		final List<Object> sqlParams = new ArrayList<>();
		final String sql = buildSelectSQL(sqlParams);
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, getTrxName());
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final PaySelectionLineCandidate candidate = retrievePaySelectionLineCandidate(rs);
				createOrUpdatePaySelectionLine(candidate);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);

		}
		finally
		{
			DB.close(rs, pstmt);
		}

		//
		// Delete remaining pay selection lines (which were enqueued to be updated),
		// because if they were not updated until now, for sure no invoice matched so it's safe to delete them
		for (final I_C_PaySelectionLine paySelectionLine : dequeueAllPaySelectionLinesToUpdate())
		{
			deletePaySelectionLine(paySelectionLine);
		}
	}

	private final void assertConfigurable()
	{
		Check.assume(_configurable, "Not already executed");
	}

	@Override
	public String getSummary()
	{
		final StringBuilder sb = new StringBuilder("@C_PaySelectionLine_ID@ - ");

		sb.append("@Created@: ").append(countCreated);
		if (countUpdated > 0)
		{
			sb.append(", @Updated@: ").append(countUpdated);
		}
		if (countDeleted > 0)
		{
			sb.append(", @Deleted@: ").append(countDeleted);
		}
		return sb.toString();
	}

	private String buildSelectSQL(final List<Object> sqlParams)
	{
		// NOTE!!! Please keep in sync with org.compiere.model.CalloutPaySelection.invoice(Properties, int, GridTab, GridField, Object)

		Check.assume(sqlParams != null && sqlParams.isEmpty(), "instantiated empty list");

		if (log.isInfoEnabled())
		{
			log.info("C_PaySelection_ID=" + getC_PaySelection()
					+ ", OnlyDiscount=" + isOnlyDiscount() + ", OnlyDue=" + isOnlyDue()
					+ ", IncludeInDispute=" + getIncludeInDispute()
					+ ", MatchRequirement=" + getMatchRequirement()
					+ ", PaymentRule=" + getPaymentRule()
					+ ", C_BP_Group_ID=" + getC_BP_Group_ID() + ", C_BPartner_ID=" + getC_BPartner_ID());
		}

		final I_C_PaySelection paySelection = getC_PaySelection();
		final int C_CurrencyTo_ID = paySelection.getC_BP_BankAccount().getC_Currency_ID();
		final Timestamp payDate;

		//
		// 08419: depending on whether onlyDue is checked, consider p_Date either DateTo or DateFrom
		if (getPayDate() != null)
		{
			payDate = getPayDate();
		}
		else
		{
			payDate = paySelection.getPayDate();
		}

		String sql = "SELECT "
				+ " C_Invoice_ID,"
				// OpenAmt
				+ " currencyConvert(invoiceOpen(i.C_Invoice_ID, 0)"
				+ ",i.C_Currency_ID, ?,?, i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID) as OpenAmt," // ##1/2 Currency_To,PayDate
				// DiscountAmt
				+ " currencyConvert(paymentTermDiscount(i.GrandTotal,i.C_Currency_ID,i.C_PaymentTerm_ID,i.DateInvoiced, ?)" // ##3 PayDate
				+ ",i.C_Currency_ID, ?,?,i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID) as DiscountAmt," // ##4/5 Currency_To,PayDate
				+ " i.PaymentRule, " // 4
				+ " i.IsSOTrx, " // 5
				+ " i.C_Bpartner_ID," // 6

				// C_BP_BankAccount_ID
				+ " (SELECT max(bpb.C_BP_BankAccount_ID) FROM C_BP_BankAccount bpb WHERE bpb.C_BPartner_ID = i.C_BPartner_ID AND bpb.IsActive='Y' "
				+ " AND bpb.BPBankAcctUse = (CASE WHEN EXISTS(SELECT 1 FROM C_BP_BankAccount sub WHERE sub.BPBankAcctUse = i.PaymentRule)"
				+ " THEN i.PaymentRule ELSE 'B' END)) as C_BP_BankAccount_ID "
				//
				+ " FROM C_Invoice i "
				+ " LEFT JOIN C_Doctype dt on i.C_Doctype_ID = dt.C_Doctype_ID "
				+ " WHERE true " //
		;
		sqlParams.add(C_CurrencyTo_ID); // #1
		sqlParams.add(payDate); // #2
		sqlParams.add(payDate); // #3
		sqlParams.add(C_CurrencyTo_ID); // #4
		sqlParams.add(payDate); // #5

		// Not already paid invoices
		{
			sql += " AND i.IsPaid=?";
			sqlParams.add(false);
		}

		// Only COmpleted/CLosed invoices
		{
			sql += " AND i.DocStatus IN ('CO','CL')";
		}

		// Only those invoices which are matching C_PaySelection's currency (07885)
		{
			sql += " AND i.C_Currency_ID=?";
			sqlParams.add(C_CurrencyTo_ID);
		}

		// Only those invoices from our tenant (guard, shall not happen)
		{
			sql += " AND i.AD_Client_ID=?";
			sqlParams.add(paySelection.getAD_Client_ID());
		}

		// Only for Pay Selection's Organization (if set)
		if (paySelection.getAD_Org_ID() > 0)
		{
			sql += " AND i.AD_Org_ID=? ";
			sqlParams.add(paySelection.getAD_Org_ID());
		}

		// // Existing Payments - Will reselect Invoice if prepared but not paid
		// + " AND NOT EXISTS (SELECT * FROM C_PaySelectionLine psl"
		// + " INNER JOIN C_PaySelectionCheck psc ON (psl.C_PaySelectionCheck_ID=psc.C_PaySelectionCheck_ID)"
		// + " LEFT OUTER JOIN C_Payment pmt ON (pmt.C_Payment_ID=psc.C_Payment_ID)"
		// + " WHERE i.C_Invoice_ID=psl.C_Invoice_ID AND psl.IsActive='Y'"
		// + " AND (pmt.DocStatus IS NULL OR pmt.DocStatus NOT IN ('VO','RE')) )"

		//
		// Exclude invoices from existing pay selections if we were not explicitelly asked to just update a couple of pay selection lines
		// or, Include only the pay selection lines that we were adviced to include.
		if (paySelectionLineIdsToUpdate.isEmpty())
		{
			sql += " AND NOT EXISTS (" //
					+ "         SELECT 1 FROM C_PaySelectionLine psl " //
					+ "         WHERE psl.C_Invoice_ID=i.C_Invoice_ID AND psl.IsActive='Y' " //
					+ " )";
		}
		else
		{
			sql += " AND EXISTS (" //
					+ " SELECT 1 FROM C_PaySelectionLine psl "
					+ " WHERE psl.C_Invoice_ID=i.C_Invoice_ID"
					+ " AND " + DB.buildSqlList("psl.C_PaySelectionLine_ID", paySelectionLineIdsToUpdate, sqlParams)
					+ " )";
		}

		// Disputed
		final Boolean includeInDispute = getIncludeInDispute();
		if (includeInDispute != null && !includeInDispute)
		{
			sql += " AND i.IsInDispute='N'";
		}

		// PaymentRule (optional)
		if (getPaymentRule() != null)
		{
			sql += " AND PaymentRule=?"; // ##
			sqlParams.add(getPaymentRule());
		}
		// OnlyDiscount
		if (isOnlyDiscount())
		{
			if (isOnlyDue())
			{
				sql += " AND (";
			}
			else
			{
				sql += " AND ";
			}
			sql += "paymentTermDiscount(invoiceOpen(C_Invoice_ID, 0), C_Currency_ID, C_PaymentTerm_ID, DateInvoiced, ?) > 0"; // ##
			sqlParams.add(payDate);
		}
		// OnlyDue
		if (isOnlyDue())
		{
			if (isOnlyDiscount())
			{
				sql += " OR ";
			}
			else
			{
				sql += " AND ";
			}

			sql += "paymentTermDueDays(C_PaymentTerm_ID, DateInvoiced, ?) >= 0"; // ##
			sqlParams.add(payDate);

			if (isOnlyDiscount())
			{
				sql += ")";
			}
		}

		// Business Partner
		if (getC_BPartner_ID() > 0)
		{
			sql += " AND C_BPartner_ID=?"; // ##
			sqlParams.add(getC_BPartner_ID());
		}
		// Business Partner Group
		else if (getC_BP_Group_ID() > 0)
		{
			sql += " AND EXISTS (SELECT * FROM C_BPartner bp "
					+ "WHERE bp.C_BPartner_ID=i.C_BPartner_ID AND bp.C_BP_Group_ID=?)"; // ##
			sqlParams.add(getC_BP_Group_ID());
		}

		// // PO Matching Requiremnent
		// if (p_MatchRequirement.equals(INV_WITH_PO) || p_MatchRequirement.equals("B"))
		// {
		// sql += " AND EXISTS (SELECT * FROM C_InvoiceLine il "
		// + "WHERE i.C_Invoice_ID=il.C_Invoice_ID"
		// + " AND QtyInvoiced=(SELECT SUM(Qty) FROM M_MatchPO m "
		// + "WHERE il.C_InvoiceLine_ID=m.C_InvoiceLine_ID))";
		// }
		// // Receipt Matching Requiremnent
		// else if (p_MatchRequirement.equals(INV_WITH_PAY_RECEIPT) || p_MatchRequirement.equals("B"))
		// {
		// sql += " AND EXISTS (SELECT * FROM C_InvoiceLine il "
		// + "WHERE i.C_Invoice_ID=il.C_Invoice_ID"
		// + " AND QtyInvoiced=(SELECT SUM(Qty) FROM M_MatchInv m "
		// + "WHERE il.C_InvoiceLine_ID=m.C_InvoiceLine_ID))";
		// }

		// Vendor invoice and recurrent payment with direct deposit
		final String whereVendorRE = " i.IsSOTrx='N' AND i.PaymentRule IN ('" + PAYMENTRULE_DirectDeposit + "','" + PAYMENTRULE_OnCredit + "') ";

		// customer invoice with direct debit
		final String whereCustomerDD = " i.IsSOTrx='Y' AND dt.DocBaseType!='ARC' AND i.PaymentRule IN ('" + PAYMENTRULE_DirectDebit + "','" + PAYMENTRULE_OnCredit + "') ";

		// Customer credit memo with direct deposit
		final String whereCustomerRE = " i.IsSOTrx='Y' AND dt.DocBaseType='ARC' AND i.PaymentRule IN ('" + PAYMENTRULE_DirectDeposit + "','" + PAYMENTRULE_OnCredit + "') ";

		final String matchRequirement = getMatchRequirement();
		if (INV_SAL.equals(matchRequirement))
		{
			// salary/commission. Note: this is a subset of 'whereVendorRE'
			sql += " AND dt.Docbasetype = '" + Constants.DOCBASETYPE_AEInvoice + "' AND i.PaymentRule IN ('" + PAYMENTRULE_DirectDeposit + "','" + PAYMENTRULE_OnCredit + "') ";
		}
		else if (INV_OUT.equals(matchRequirement))
		{
			sql += " AND " + whereVendorRE;
		}
		else if (INV_C_DD.equals(matchRequirement))
		{
			sql += " AND " + whereCustomerDD;
		}
		else if (INV_C_RE.equals(matchRequirement))
		{
			sql += " AND " + whereCustomerRE;
		}
		else if (INV_ALL.equals(matchRequirement))
		{
			sql += "AND ( (" + whereVendorRE + ") OR ( " + whereCustomerDD + " ) OR (" + whereCustomerRE + ") )";
		}

		return sql;
	}

	private PaySelectionLineCandidate retrievePaySelectionLineCandidate(final ResultSet rs) throws SQLException
	{
		final PaySelectionLineCandidate.Builder candidateBuilder = PaySelectionLineCandidate.builder();
		final int invoiceId = rs.getInt("C_Invoice_ID");
		candidateBuilder.setC_Invoice_ID(invoiceId);

		final BigDecimal invoiceOpenAmt = rs.getBigDecimal("OpenAmt");
		candidateBuilder.setOpenAmt(invoiceOpenAmt);

		final BigDecimal payDiscountAmt = rs.getBigDecimal("DiscountAmt");
		candidateBuilder.setDiscountAmt(payDiscountAmt);

		final boolean isSOTrx = DisplayType.toBoolean(rs.getString("IsSOTrx"), false);
		candidateBuilder.setIsSOTrx(isSOTrx);

		final int bpartnerId = rs.getInt("C_BPartner_ID");
		candidateBuilder.setC_BPartner_ID(bpartnerId);

		final int bpBankAccountId = rs.getInt("C_BP_BankAccount_ID");
		candidateBuilder.setC_BP_BankAccount_ID(bpBankAccountId);

		String paymentRule = rs.getString("PaymentRule");
		// check active payment rules
		final Set<String> paymentRules = getInvoicePaymentRules();
		if (!paymentRules.contains(paymentRule))
		{
			paymentRule = X_C_Invoice.PAYMENTRULE_DirectDeposit;
		}
		candidateBuilder.setPaymentRule(paymentRule);

		final PaySelectionLineCandidate candidate = candidateBuilder.build();
		return candidate;
	}

	private void createOrUpdatePaySelectionLine(final PaySelectionLineCandidate candidate)
	{
		if (candidate.getC_Invoice_ID() <= 0) // shall not happen
		{
			return;
		}

		final I_C_PaySelection paySelection = getC_PaySelection();

		//
		// Dequeue existing C_PaySelectionLine to be updated.
		// Also make sure the existing pay selection is valid in our context.
		final I_C_PaySelectionLine existingPaySelectionLine = dequeuePaySelectionLineToUpdateByInvoice(candidate.getC_Invoice_ID()).orNull();
		if (existingPaySelectionLine != null && existingPaySelectionLine.getC_PaySelection_ID() != paySelection.getC_PaySelection_ID())
		{
			throw new AdempiereException("PaySelectionLine to update it's not matching the PaySelection header"
					+ "\n Expected: " + paySelection
					+ "\n Actual: " + existingPaySelectionLine.getC_PaySelection());
		}

		// If the candidate is no longer eligible for a pay selection line, delete the existing pay selection line (if any)
		if (candidate.getPayAmt().signum() <= 0)
		{
			deletePaySelectionLine(existingPaySelectionLine);
			return;
		}

		//
		// Create a new C_PaySelectionLine or re-use existing (if found).
		final I_C_PaySelectionLine paySelectionLine;
		final boolean isNewPaySelectionLine;
		if (existingPaySelectionLine == null)
		{
			paySelectionLine = InterfaceWrapperHelper.create(getCtx(), I_C_PaySelectionLine.class, getTrxName());
			isNewPaySelectionLine = true;
		}
		else
		{
			paySelectionLine = existingPaySelectionLine;
			isNewPaySelectionLine = false;
		}

		// Update from candidate
		updatePaySelectionLine(paySelectionLine, candidate);

		// Save & update statistics
		InterfaceWrapperHelper.save(paySelectionLine);
		if (isNewPaySelectionLine)
		{
			countCreated++;
			log.debug("Created {}", paySelectionLine);
		}
		else
		{
			countUpdated++;
			log.debug("Updated {}", paySelectionLine);
		}
	}

	private final void deletePaySelectionLine(final I_C_PaySelectionLine paySelectionLine)
	{
		if (paySelectionLine == null)
		{
			return;
		}
		if (InterfaceWrapperHelper.isNew(paySelectionLine))
		{
			return;
		}

		// Actually delete the pay selection line & update statistics
		InterfaceWrapperHelper.delete(paySelectionLine);
		countDeleted++;
		log.debug("Deleted {}", paySelectionLine);
	}

	/** Updates {@link I_C_PaySelectionLine} from: given candidate and current pay selection header */
	private final void updatePaySelectionLine(final I_C_PaySelectionLine paySelectionLine, final PaySelectionLineCandidate candidate)
	{
		//
		// Update from C_PaySelection header
		final I_C_PaySelection paySelection = getC_PaySelection();
		paySelectionLine.setAD_Org_ID(paySelection.getAD_Org_ID());
		paySelectionLine.setC_PaySelection(paySelection);

		//
		// Update from candidate
		paySelectionLine.setPaymentRule(candidate.getPaymentRule());
		paySelectionLine.setC_Invoice_ID(candidate.getC_Invoice_ID());
		paySelectionLine.setIsSOTrx(candidate.isSOTrx());
		paySelectionLine.setOpenAmt(candidate.getOpenAmt());
		paySelectionLine.setPayAmt(candidate.getPayAmt());
		paySelectionLine.setDiscountAmt(candidate.getDiscountAmt());
		paySelectionLine.setDifferenceAmt(candidate.getDifferenceAmt());
		paySelectionLine.setC_BPartner_ID(candidate.getC_BPartner_ID());
		final int bpBankAccountId = candidate.getC_BP_BankAccount_ID();
		if (bpBankAccountId > 0)
		{
			// task 09500: only set the account if it has the same currency as the one of the header
			// note that this checking is just for extra precaution. the candidate shall already have that currency

			final int paySelectionCurrencyID = paySelection.getC_BP_BankAccount().getC_Currency_ID();

			final Properties ctx = InterfaceWrapperHelper.getCtx(paySelectionLine);
			final String trxName = InterfaceWrapperHelper.getTrxName(paySelectionLine);

			final I_C_BP_BankAccount candidateAccount = InterfaceWrapperHelper.create(ctx, bpBankAccountId, I_C_BP_BankAccount.class, trxName);

			final int candidateCurrencyID = candidateAccount.getC_Currency_ID();

			if (paySelectionCurrencyID == candidateCurrencyID)
			{
				paySelectionLine.setC_BP_BankAccount_ID(bpBankAccountId);
			}
		}

		// Set line no (if not already set)
		if (paySelectionLine.getLine() <= 0)
		{
			final int lineNo = getAndIncrementNextLineNo();
			paySelectionLine.setLine(lineNo);
		}
	}

	private Set<String> getInvoicePaymentRules()
	{
		return _paymentRulesSupplier.get();
	}

	@Override
	public IPaySelectionUpdater setContext(final Properties ctx, final String trxName)
	{
		assertConfigurable();
		_ctx = ctx;
		_trxNameInitial = trxName;
		return this;
	}

	@Override
	public IPaySelectionUpdater setContext(final IContextAware context)
	{
		return setContext(context.getCtx(), context.getTrxName());
	}

	private final Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "_ctx not null");
		return _ctx;
	}

	/** @return initial trxName which was set when this updater was configured */
	private final String getTrxNameInitial()
	{
		return _trxNameInitial;
	}

	/** @return current running transaction; never returns a null transaction */
	private final String getTrxName()
	{
		trxManager.assertTrxNameNotNull(_trxName);
		return _trxName;
	}

	/** Sets current running transaction */
	private void setTrxName(final String trxName)
	{
		if (trxName != null)
		{
			trxManager.assertTrxNameNull(_trxName);
		}
		_trxName = trxName;
	}

	private boolean isOnlyDiscount()
	{
		return _onlyDiscount;
	}

	@Override
	public IPaySelectionUpdater setOnlyDiscount(final boolean onlyDiscount)
	{
		assertConfigurable();
		_onlyDiscount = onlyDiscount;
		return this;
	}

	private boolean isOnlyDue()
	{
		return _onlyDue;
	}

	@Override
	public IPaySelectionUpdater setOnlyDue(final boolean onlyDue)
	{
		assertConfigurable();
		_onlyDue = onlyDue;
		return this;
	}

	private Boolean getIncludeInDispute()
	{
		return _includeInDispute;
	}

	@Override
	public IPaySelectionUpdater setIncludeInDispute(final boolean includeInDispute)
	{
		assertConfigurable();
		_includeInDispute = includeInDispute;
		return this;
	}

	private String getMatchRequirement()
	{
		return _matchRequirement;
	}

	@Override
	public IPaySelectionUpdater setMatchRequirement(final String matchRequirement)
	{
		assertConfigurable();
		_matchRequirement = matchRequirement;
		return this;
	}

	private Timestamp getPayDate()
	{
		return _payDate;
	}

	@Override
	public IPaySelectionUpdater setPayDate(final Timestamp payDate)
	{
		assertConfigurable();
		_payDate = TimeUtil.copyOf(payDate);
		return this;
	}

	private String getPaymentRule()
	{
		return _paymentRule;
	}

	@Override
	public IPaySelectionUpdater setPaymentRule(final String paymentRule)
	{
		assertConfigurable();
		_paymentRule = paymentRule;
		return this;
	}

	private int getC_BPartner_ID()
	{
		return _bpartnerId;
	}

	@Override
	public IPaySelectionUpdater setC_BPartner_ID(final int bpartnerId)
	{
		assertConfigurable();
		_bpartnerId = bpartnerId;
		return this;
	}

	private int getC_BP_Group_ID()
	{
		return _bpGroupId;
	}

	@Override
	public IPaySelectionUpdater setC_BP_Group_ID(final int bpGroupId)
	{
		assertConfigurable();
		_bpGroupId = bpGroupId;
		return this;
	}

	@Override
	public IPaySelectionUpdater setC_PaySelection(final I_C_PaySelection paySelection)
	{
		assertConfigurable();
		_paySelection = paySelection;
		return this;
	}

	private I_C_PaySelection getC_PaySelection()
	{
		Check.assumeNotNull(_paySelection, "_paySelection not null");
		return _paySelection;
	}

	private int getC_PaySelection_ID()
	{
		return getC_PaySelection().getC_PaySelection_ID();
	}

	private Integer _nextLineNo = null;

	private int getAndIncrementNextLineNo()
	{
		if (_nextLineNo == null)
		{
			final int lastLineNo = paySelectionDAO.retrieveLastPaySelectionLineNo(getCtx(), getC_PaySelection_ID(), getTrxName());
			_nextLineNo = lastLineNo + 10;
		}

		final int nextLineNoToReturn = _nextLineNo;
		_nextLineNo += 10;

		return nextLineNoToReturn;
	}

	@Override
	public IPaySelectionUpdater addPaySelectionLineToUpdate(final int paySelectionLineId)
	{
		assertConfigurable();
		paySelectionLineIdsToUpdate.add(paySelectionLineId);
		return this;
	}

	@Override
	public IPaySelectionUpdater addPaySelectionLinesToUpdate(final Iterable<? extends org.compiere.model.I_C_PaySelectionLine> paySelectionLines)
	{
		assertConfigurable();

		for (final org.compiere.model.I_C_PaySelectionLine paySelectionLine : paySelectionLines)
		{
			paySelectionLineIdsToUpdate.add(paySelectionLine.getC_PaySelectionLine_ID());
		}

		return this;
	}

	/**
	 * Dequeue (gets and removes from internal list) the {@link I_C_PaySelectionLine} which was scheduled to be updated.
	 *
	 * @param invoiceId C_Invoice_ID
	 * @return existing {@link I_C_PaySelectionLine} which was scheduled to be updated
	 */
	private Optional<I_C_PaySelectionLine> dequeuePaySelectionLineToUpdateByInvoice(final int invoiceId)
	{
		final Map<Integer, I_C_PaySelectionLine> invoiceId2paySelectionLineToUpdate = _invoiceId2paySelectionLineToUpdateSupplier.get();
		final I_C_PaySelectionLine paySelectionLine = invoiceId2paySelectionLineToUpdate.remove(invoiceId);
		return Optional.fromNullable(paySelectionLine);
	}

	/**
	 * Dequeue (gets and removes from internal list) all remaining {@link I_C_PaySelectionLine}s which were scheduled to be updated.
	 */
	private List<I_C_PaySelectionLine> dequeueAllPaySelectionLinesToUpdate()
	{
		final Map<Integer, I_C_PaySelectionLine> invoiceId2paySelectionLineToUpdate = _invoiceId2paySelectionLineToUpdateSupplier.get();
		final List<I_C_PaySelectionLine> paySelectionLines = new ArrayList<>(invoiceId2paySelectionLineToUpdate.values());
		invoiceId2paySelectionLineToUpdate.clear();
		return paySelectionLines;
	}
}
