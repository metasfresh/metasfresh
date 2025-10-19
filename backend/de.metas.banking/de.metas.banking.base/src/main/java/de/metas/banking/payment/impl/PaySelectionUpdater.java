package de.metas.banking.payment.impl;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.ReferenceId;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.PaySelectionId;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.banking.payment.IPaySelectionUpdater;
import de.metas.banking.payment.PaySelectionMatchingMode;
import de.metas.banking.payment.PaySelectionTrxType;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.OrderPayScheduleId;
import de.metas.order.paymentschedule.OrderPayScheduleStatus;
import de.metas.payment.PaymentRule;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class PaySelectionUpdater implements IPaySelectionUpdater
{
	// services
	private static final Logger logger = LogManager.getLogger(PaySelectionUpdater.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IPaySelectionDAO paySelectionsRepo = Services.get(IPaySelectionDAO.class);
	private final transient IBPBankAccountDAO bpBankAccountRepo = Services.get(IBPBankAccountDAO.class);

	private final transient ModelCacheInvalidationService modelCacheInvalidationService = ModelCacheInvalidationService.get();
	private final ADReferenceService adReferenceService = ADReferenceService.get();
	private final HashSet<Integer> paySelectionLineIdsToUpdate = new HashSet<>();

	/**
	 * Payment Rules (from C_Invoice.PaymentRule's available values list)
	 */
	private final Supplier<ImmutableSet<PaymentRule>> _paymentRulesSupplier = Suppliers.memoize(this::retrieveInvoicePaymentRules);
	private boolean _configurable = true;
	private boolean _onlyDiscount = false;
	private boolean _onlyDue = false;
	private Boolean _includeInDispute = null;
	private Optional<PaySelectionMatchingMode> _matchRequirement = Optional.empty();
	private Timestamp _payDate = null;
	private PaymentRule _paymentRule = null;
	private int _bpartnerId = 0;
	private int _bpGroupId = 0;
	/**
	 * Payment Selection
	 */
	private I_C_PaySelection _paySelection;

	private final Supplier<PaySelectionLinesToUpdate> _paySelectionLinesToUpdate = Suppliers.memoize(this::retrievePaySelectionLinesToUpdate);

	//
	// Statistics
	private int countCreated = 0;
	private int countUpdated = 0;
	private int countDeleted = 0;
	private Integer _nextLineNo = null;

	PaySelectionUpdater()
	{
	}

	@Override
	public void update()
	{
		trxManager.runInThreadInheritedTrx(this::updateInTrx);
	}

	private void updateInTrx()
	{
		assertConfigurable();
		_configurable = false; // Lock this updater. Shall not be configurable anymore.

		//
		// Validate InvoiceMatchingMode vs PaySelectionTrxType
		final I_C_PaySelection paySelection = getC_PaySelection();
		final PaySelectionMatchingMode paySelectionMatchingMode = getMatchRequirement().orElse(null);
		if (paySelectionMatchingMode != null)
		{
			final PaySelectionTrxType paySelectionTrxType = PaySelectionTrxType.ofNullableCode(paySelection.getPaySelectionTrxType());
			if (paySelectionTrxType != null && !paySelectionMatchingMode.isCompatibleWith(paySelectionTrxType))
			{
				throw new AdempiereException("@Invalid@ @PaySelectionTrxType@");
			}
		}

		//
		// Create/Update PaySelection lines
		final ArrayList<Object> sqlParams = new ArrayList<>();
		final String sql = buildSelectSQL(sqlParams);
		DB.forEachRow(sql, sqlParams, this::createOrUpdatePaySelectionLine);

		//
		// Delete remaining pay selection lines (which were enqueued to be updated),
		// because if they were not updated until now, for sure no invoice matched so it's safe to delete them
		final PaySelectionLinesToUpdate paySelectionLinesToUpdate = getPaySelectionLinesToUpdate();
		for (final I_C_PaySelectionLine paySelectionLine : paySelectionLinesToUpdate.dequeueAll())
		{
			deletePaySelectionLine(paySelectionLine);
		}

		//
		// Update the PaySelection
		if (paySelectionMatchingMode != null)
		{
			paySelection.setPaySelectionTrxType(paySelectionMatchingMode.getPaySelectionTrxType().getCode());
			InterfaceWrapperHelper.save(paySelection);
		}

		// make sure pay selection is invalidated
		cacheInvalidationForCurrentPaySelection();
	}

	private void assertConfigurable()
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

		if (logger.isInfoEnabled())
		{
			logger.info("C_PaySelection_ID=" + getC_PaySelection()
					+ ", OnlyDiscount=" + isOnlyDiscount() + ", OnlyDue=" + isOnlyDue()
					+ ", IncludeInDispute=" + getIncludeInDispute()
					+ ", MatchRequirement=" + getMatchRequirement()
					+ ", PaymentRule=" + getPaymentRule()
					+ ", C_BP_Group_ID=" + getC_BP_Group_ID() + ", C_BPartner_ID=" + getC_BPartner_ID());
		}

		final I_C_PaySelection paySelection = getC_PaySelection();
		final CurrencyId C_CurrencyTo_ID = CurrencyId.ofRepoId(paySelection.getC_BP_BankAccount().getC_Currency_ID());
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

		return buildInvoiceSql(sqlParams, C_CurrencyTo_ID, payDate, paySelection)
				+ " UNION "
				+ buildOrderSql(sqlParams, C_CurrencyTo_ID, payDate, paySelection);
	}

	private @NonNull String buildInvoiceSql(final List<Object> sqlParams, final CurrencyId C_CurrencyTo_ID, final Timestamp payDate, final I_C_PaySelection paySelection)
	{
		String sql = "SELECT "
				+ " C_Invoice_ID,"
				+ " -1 as C_Order_ID,"
				+ " -1 as C_OrderPaySchedule_ID,"
				// OpenAmt
				+ " invoiceOpen(i.C_Invoice_ID, 0) as OpenAmt,"
				// DiscountAmt
				+ " paymentTermDiscount(i.GrandTotal,i.C_Currency_ID,i.C_PaymentTerm_ID,i.DateInvoiced, ?) as DiscountAmt," // #1 PayDate
				+ " i.PaymentRule, " // 4
				+ " i.IsSOTrx, " // 5
				+ " i.C_Bpartner_ID," // 6

				// C_BP_BankAccount_ID
				+ " (SELECT max(bpb.C_BP_BankAccount_ID) FROM C_BP_BankAccount bpb WHERE bpb.C_BPartner_ID = i.C_BPartner_ID AND bpb.IsActive='Y' "
				+ " AND bpb.BPBankAcctUse = (CASE WHEN EXISTS(SELECT 1 FROM C_BP_BankAccount sub WHERE sub.BPBankAcctUse = i.PaymentRule)"
				+ " THEN i.PaymentRule ELSE 'B' END) AND bpb.C_Currency_ID = i.C_Currency_ID) as C_BP_BankAccount_ID "
				//
				+ " FROM C_Invoice i "
				+ " LEFT JOIN C_Doctype dt on i.C_Doctype_ID = dt.C_Doctype_ID "
				+ " WHERE true " //
				;
		sqlParams.add(payDate); // #1

		// Not already paid invoices
		{
			sql += " AND i.IsPaid=?";
			sqlParams.add(false);
		}

		// Only COmpleted/CLosed invoices
		{
			sql += " AND i.DocStatus IN (?,?)";
			sqlParams.add(DocStatus.Completed);
			sqlParams.add(DocStatus.Closed);
		}

		// Only those invoices which are matching C_PaySelection's currency (07885)
		{
			sql += " AND i.C_Currency_ID=?";
			sqlParams.add(C_CurrencyTo_ID);
		}

		// Only for Pay Selection's Organization (if set)
		if (paySelection.getAD_Org_ID() > 0)
		{
			sql += " AND i.AD_Org_ID=? ";
			sqlParams.add(paySelection.getAD_Org_ID());
		}

		// Only those invoices from our tenant (guard, shall not happen)
		{
			sql += " AND i.AD_Client_ID=?";
			sqlParams.add(paySelection.getAD_Client_ID());
		}

		//
		// Exclude invoices from existing pay selections if we were not explicitly asked to just update a couple of pay selection lines
		// or, Include only the pay selection lines that we were advised to include.
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

		sql += buildSelectSQL_InvoiceMatchRequirement();
		return sql;
	}

	private String buildSelectSQL_InvoiceMatchRequirement()
	{
		final String whereCreditTransferToVendor = " i.IsSOTrx='N' AND i.PaymentRule IN ('" + PaymentRule.DirectDeposit.getCode() + "','" + PaymentRule.OnCredit.getCode() + "') ";
		final String whereDirectDebitFromCustomer = " i.IsSOTrx='Y' AND dt.DocBaseType !='ARC' AND i.PaymentRule = '" + PaymentRule.DirectDebit.getCode() + "'";
		final String whereCreditTransferToCustomer = " i.IsSOTrx='Y' AND dt.DocBaseType='ARC' AND i.PaymentRule IN ('" + PaymentRule.DirectDeposit.getCode() + "','" + PaymentRule.OnCredit.getCode() + "') ";

		final PaySelectionMatchingMode matchRequirement = getMatchRequirement().orElse(null);
		if (matchRequirement == null) // ALL
		{
			return "AND ( (" + whereCreditTransferToVendor + ") OR ( " + whereDirectDebitFromCustomer + " ) OR (" + whereCreditTransferToCustomer + ") )";
		}
		else if (PaySelectionMatchingMode.CREDIT_TRANSFER_TO_VENDOR.equals(matchRequirement))
		{
			return " AND " + whereCreditTransferToVendor;
		}
		else if (PaySelectionMatchingMode.DIRECT_DEBIT_FROM_CUSTOMER.equals(matchRequirement))
		{
			return " AND " + whereDirectDebitFromCustomer;
		}
		else if (PaySelectionMatchingMode.CREDIT_TRANSFER_TO_CUSTOMER.equals(matchRequirement))
		{
			return " AND " + whereCreditTransferToCustomer;
		}
		else
		{
			throw new AdempiereException("Unknown matchRequirement: " + matchRequirement);
		}
	}

	private @NonNull String buildOrderSql(final List<Object> sqlParams, final CurrencyId C_CurrencyTo_ID, final Timestamp payDate, final I_C_PaySelection paySelection)
	{
		String sql = "SELECT "
				+ " -1 as C_Invoice_ID," // 1
				+ " o.C_Order_ID," // 2
				+ " ops.C_OrderPaySchedule_ID," // 3
				+ " ops.dueamt as OpenAmt," // 4
				+ " null as DiscountAmt," // 5
				+ " null as PaymentRule, "  // 6
				+ " o.IsSOTrx, " // 7
				+ " o.Bill_BPartner_ID as C_BPartner_ID," // 8
				// C_BP_BankAccount_ID
				+ " (SELECT max(bpb.C_BP_BankAccount_ID) FROM C_BP_BankAccount bpb WHERE bpb.C_BPartner_ID = o.Bill_BPartner_ID AND bpb.IsActive='Y' "
				+ " AND bpb.IBAN IS NOT NULL AND bpb.C_Currency_ID = o.C_Currency_ID) as C_BP_BankAccount_ID "  //9
				//
				+ " FROM C_Order o "
				+ " INNER JOIN C_Doctype dt on o.C_Doctype_ID = dt.C_Doctype_ID "
				+ " INNER JOIN C_OrderPaySchedule ops on o.C_Order_ID = ops.C_Order_ID "
				+ " WHERE true AND ops.Status = ? "  //
				;
		sqlParams.add(OrderPayScheduleStatus.Awaiting_Pay.getCode()); // #1

		// Only COmpleted/CLosed payment
		{
			sql += " AND o.DocStatus IN (?,?)";
			sqlParams.add(DocStatus.Completed);
			sqlParams.add(DocStatus.Closed);
		}

		// Only those orders which are matching C_PaySelection's currency
		{
			sql += " AND o.C_Currency_ID=?";
			sqlParams.add(C_CurrencyTo_ID);
		}

		// Only for Pay Selection's Organization (if set)
		if (paySelection.getAD_Org_ID() > 0)
		{
			sql += " AND o.AD_Org_ID=? ";
			sqlParams.add(paySelection.getAD_Org_ID());
		}

		// Only those payments from our tenant (guard, shall not happen)
		{
			sql += " AND o.AD_Client_ID=?";
			sqlParams.add(paySelection.getAD_Client_ID());
		}

		//
		// Exclude orders from existing pay selections if we were not explicitelly asked to just update a couple of pay selection lines
		// or, Include only the pay selection lines that we were advised to include.
		if (paySelectionLineIdsToUpdate.isEmpty())
		{
			sql += " AND NOT EXISTS (" //
					+ "         SELECT 1 FROM C_PaySelectionLine psl " //
					+ "         WHERE psl.C_Order_ID=o.C_Order_ID AND psl.IsActive='Y' " //
					+ " )";
		}
		else
		{
			sql += " AND EXISTS (" //
					+ " SELECT 1 FROM C_PaySelectionLine psl "
					+ " WHERE psl.C_Order_ID=o.C_Order_ID AND psl.IsActive='Y' "
					+ " AND " + DB.buildSqlList("psl.C_PaySelectionLine_ID", paySelectionLineIdsToUpdate, sqlParams)
					+ " )";
		}

		// Business Partner
		if (getC_BPartner_ID() > 0)
		{
			sql += " AND o.Bill_BPartner_ID=?"; // ##
			sqlParams.add(getC_BPartner_ID());
		}
		// Business Partner Group
		else if (getC_BP_Group_ID() > 0)
		{
			sql += " AND EXISTS (SELECT * FROM C_BPartner bp "
					+ "WHERE bp.C_BPartner_ID=o.Bill_BPartner_ID AND bp.C_BP_Group_ID=?)"; // ##
			sqlParams.add(getC_BP_Group_ID());
		}

		// DateTrx
		if (getPayDate() != null)
		{
			sql += " AND o.DateAcct=?";
			sqlParams.add(getPayDate());
		}

		// Match Requirement
		final PaySelectionMatchingMode matchRequirement = getMatchRequirement().orElse(null);
		if (matchRequirement == null) // ALL
		{
			// no restriction
		}
		else if (PaySelectionMatchingMode.CREDIT_TRANSFER_TO_VENDOR.equals(matchRequirement))
		{
			sql += " AND o.IsSOTrx='N'";
		}
		else if (PaySelectionMatchingMode.CREDIT_TRANSFER_TO_CUSTOMER.equals(matchRequirement))
		{
			sql += " AND o.IsSOTrx='Y'";
		}
		return sql;
	}

	private void createOrUpdatePaySelectionLine(final ResultSet rs) throws SQLException
	{
		final PaySelectionLineCandidate candidate = retrievePaySelectionLineCandidate(rs);
		createOrUpdatePaySelectionLine(candidate);
	}

	private PaySelectionLineCandidate retrievePaySelectionLineCandidate(final ResultSet rs) throws SQLException
	{
		final PaySelectionLineCandidate.Builder candidateBuilder = PaySelectionLineCandidate.builder();
		final int invoiceId = rs.getInt("C_Invoice_ID");
		candidateBuilder.setInvoiceId(InvoiceId.ofRepoIdOrNull(invoiceId));

		final int orderId = rs.getInt("C_Order_ID");
		candidateBuilder.setOrderId(OrderId.ofRepoIdOrNull(orderId));
		final int orderPayScheduleId = rs.getInt("C_OrderPaySchedule_ID");
		candidateBuilder.setOrderPayScheduleId(OrderPayScheduleId.ofRepoIdOrNull(orderPayScheduleId));

		final BigDecimal openAmt = rs.getBigDecimal("OpenAmt");
		candidateBuilder.setOpenAmt(openAmt);

		final BigDecimal payDiscountAmt = rs.getBigDecimal("DiscountAmt");
		candidateBuilder.setDiscountAmt(payDiscountAmt);

		final boolean isSOTrx = DisplayType.toBooleanNonNull(rs.getString("IsSOTrx"), false);
		candidateBuilder.setIsSOTrx(isSOTrx);

		final int bpartnerId = rs.getInt("C_BPartner_ID");
		candidateBuilder.setBPartnerId(BPartnerId.ofRepoId(bpartnerId));

		final int bpBankAccountId = rs.getInt("C_BP_BankAccount_ID");
		candidateBuilder.setBPartnerBankAccountId(BankAccountId.ofRepoIdOrNull(bpBankAccountId));

		PaymentRule paymentRule = PaymentRule.ofNullableCode(rs.getString("PaymentRule"));
		// check active payment rules
		final ImmutableSet<PaymentRule> paymentRules = getInvoicePaymentRules();
		if (paymentRule == null || !paymentRules.contains(paymentRule))
		{
			paymentRule = PaymentRule.DirectDeposit;
		}
		candidateBuilder.setPaymentRule(paymentRule.getCode());

		return candidateBuilder.build();
	}

	private void createOrUpdatePaySelectionLine(@NonNull final PaySelectionLineCandidate candidate)
	{
		if (candidate.getInvoiceId() == null && candidate.getOrderId() == null) // shall not happen
		{
			return;
		}

		final I_C_PaySelectionLine existingPaySelectionLine = dequePaySelectionLine(candidate);

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
			paySelectionLine = newInstance(I_C_PaySelectionLine.class);
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
			logger.debug("Created {}", paySelectionLine);
		}
		else
		{
			countUpdated++;
			logger.debug("Updated {}", paySelectionLine);
		}
	}

	private @Nullable I_C_PaySelectionLine dequePaySelectionLine(final @NonNull PaySelectionLineCandidate candidate)
	{
		final I_C_PaySelection paySelection = getC_PaySelection();

		//
		// Dequeue the existing C_PaySelectionLine to be updated.
		// Also, make sure the existing pay selection is valid in our context.
		final PaySelectionLinesToUpdate paySelectionLinesToUpdate = getPaySelectionLinesToUpdate();
		final I_C_PaySelectionLine existingPaySelectionLine = paySelectionLinesToUpdate.dequeueFor(candidate).orElse(null);
		if (existingPaySelectionLine != null && existingPaySelectionLine.getC_PaySelection_ID() != paySelection.getC_PaySelection_ID())
		{
			throw new AdempiereException("PaySelectionLine to update it's not matching the PaySelection header"
					+ "\n Expected: " + paySelection
					+ "\n Actual: " + existingPaySelectionLine.getC_PaySelection());
		}
		return existingPaySelectionLine;
	}

	private void deletePaySelectionLine(@Nullable final I_C_PaySelectionLine paySelectionLine)
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
		logger.debug("Deleted {}", paySelectionLine);
	}

	/**
	 * Updates {@link I_C_PaySelectionLine} from: given candidate and current pay selection header
	 */
	private void updatePaySelectionLine(final I_C_PaySelectionLine paySelectionLine, final PaySelectionLineCandidate candidate)
	{
		//
		// Update from C_PaySelection header
		final I_C_PaySelection paySelection = getC_PaySelection();
		paySelectionLine.setAD_Org_ID(paySelection.getAD_Org_ID());
		paySelectionLine.setC_PaySelection(paySelection);

		//
		// Update from candidate
		paySelectionLine.setPaymentRule(candidate.getPaymentRule());
		paySelectionLine.setC_Invoice_ID(InvoiceId.toRepoId(candidate.getInvoiceId()));
		paySelectionLine.setC_Order_ID(OrderId.toRepoId(candidate.getOrderId()));
		paySelectionLine.setC_OrderPaySchedule_ID(OrderPayScheduleId.toRepoId(candidate.getOrderPayScheduleId()));
		paySelectionLine.setIsSOTrx(candidate.isSOTrx());
		paySelectionLine.setOpenAmt(candidate.getOpenAmt());
		paySelectionLine.setPayAmt(candidate.getPayAmt());
		paySelectionLine.setDiscountAmt(candidate.getDiscountAmt());
		paySelectionLine.setDifferenceAmt(candidate.getDifferenceAmt());
		paySelectionLine.setC_BPartner_ID(BPartnerId.toRepoId(candidate.getBPartnerId()));
		final BankAccountId bpBankAccountId = candidate.getBPartnerBankAccountId();
		if (bpBankAccountId != null)
		{
			// task 09500: only set the account if it has the same currency as the one of the header
			// note that this checking is just for extra precaution. the candidate shall already have that currency

			final CurrencyId paySelectionCurrencyID = CurrencyId.ofRepoId(paySelection.getC_BP_BankAccount().getC_Currency_ID());

			final BankAccount candidateAccount = bpBankAccountRepo.getById(bpBankAccountId);

			final CurrencyId candidateCurrencyID = candidateAccount.getCurrencyId();

			if (candidateCurrencyID.equals(paySelectionCurrencyID))
			{
				paySelectionLine.setC_BP_BankAccount_ID(BankAccountId.toRepoId(bpBankAccountId));
			}
		}

		// Set line no (if not already set)
		if (paySelectionLine.getLine() <= 0)
		{
			final int lineNo = getAndIncrementNextLineNo();
			paySelectionLine.setLine(lineNo);
		}
	}

	private ImmutableSet<PaymentRule> getInvoicePaymentRules()
	{
		return _paymentRulesSupplier.get();
	}

	private ImmutableSet<PaymentRule> retrieveInvoicePaymentRules()
	{
		final POInfo invoicePOInfo = POInfo.getPOInfoNotNull(I_C_Invoice.Table_Name);
		final ReferenceId paymentRuleReferenceId = invoicePOInfo.getColumnReferenceValueId(I_C_Invoice.COLUMNNAME_PaymentRule);

		Check.assumeNotNull(paymentRuleReferenceId, "paymentRuleReferenceId not null");

		final Set<String> paymentRules = adReferenceService.getRefListById(paymentRuleReferenceId).getValues();
		if (paymentRules == null || paymentRules.isEmpty())
		{
			throw new AdempiereException("No active payment rules were found");
		}

		return paymentRules.stream()
				.map(PaymentRule::ofCode)
				.collect(ImmutableSet.toImmutableSet());
	}

	private void cacheInvalidationForCurrentPaySelection()
	{
		final PaySelectionId paySelectionId = getPaySelectionId();

		modelCacheInvalidationService.invalidate(
				CacheInvalidateMultiRequest.of(
						CacheInvalidateRequest.rootRecord(I_C_PaySelection.Table_Name, paySelectionId),
						CacheInvalidateRequest.allChildRecords(I_C_PaySelection.Table_Name, paySelectionId, I_C_PaySelectionLine.Table_Name)),
				ModelCacheInvalidationTiming.AFTER_CHANGE);
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

	private Optional<PaySelectionMatchingMode> getMatchRequirement()
	{
		return _matchRequirement;
	}

	@Override
	public IPaySelectionUpdater setMatchRequirement(@Nullable final PaySelectionMatchingMode matchRequirement)
	{
		assertConfigurable();
		_matchRequirement = Optional.ofNullable(matchRequirement);
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

	private PaymentRule getPaymentRule()
	{
		return _paymentRule;
	}

	@Override
	public IPaySelectionUpdater setPaymentRule(final PaymentRule paymentRule)
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
	public IPaySelectionUpdater setC_PaySelection(@NonNull final I_C_PaySelection paySelection)
	{
		assertConfigurable();
		_paySelection = paySelection;

		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(paySelection.getDocStatus());
		if (!docStatus.isDraftedOrInProgress())
		{
			throw new AdempiereException("Document already processed");
		}

		return this;
	}

	private I_C_PaySelection getC_PaySelection()
	{
		Check.assumeNotNull(_paySelection, "_paySelection not null");
		return _paySelection;
	}

	private PaySelectionId getPaySelectionId()
	{
		return PaySelectionId.ofRepoId(getC_PaySelection().getC_PaySelection_ID());
	}

	private int getAndIncrementNextLineNo()
	{
		if (_nextLineNo == null)
		{
			final int lastLineNo = paySelectionsRepo.retrieveLastPaySelectionLineNo(getPaySelectionId());
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
	public IPaySelectionUpdater addPaySelectionLinesToUpdate(final Iterable<? extends I_C_PaySelectionLine> paySelectionLines)
	{
		assertConfigurable();

		for (final I_C_PaySelectionLine paySelectionLine : paySelectionLines)
		{
			paySelectionLineIdsToUpdate.add(paySelectionLine.getC_PaySelectionLine_ID());
		}

		return this;
	}

	private PaySelectionLinesToUpdate getPaySelectionLinesToUpdate()
	{
		return _paySelectionLinesToUpdate.get();
	}

	private PaySelectionLinesToUpdate retrievePaySelectionLinesToUpdate()
	{
		if (paySelectionLineIdsToUpdate.isEmpty())
		{
			return new PaySelectionLinesToUpdate(ImmutableList.of());
		}

		final List<I_C_PaySelectionLine> lines = queryBL.createQueryBuilder(I_C_PaySelectionLine.class)
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID, getPaySelectionId())
				.addInArrayFilter(I_C_PaySelectionLine.COLUMNNAME_C_PaySelectionLine_ID, paySelectionLineIdsToUpdate)
				.create()
				.list();

		return new PaySelectionLinesToUpdate(lines);
	}

	//
	//
	//

	private static class PaySelectionLinesToUpdate
	{
		private final HashMap<InvoiceId, I_C_PaySelectionLine> byInvoiceId = new HashMap<>();
		private final HashMap<OrderPayScheduleId, I_C_PaySelectionLine> byOrderPaySchedId = new HashMap<>();

		PaySelectionLinesToUpdate(final List<I_C_PaySelectionLine> lines)
		{
			for (final I_C_PaySelectionLine line : lines)
			{
				final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(line.getC_Invoice_ID());
				final OrderPayScheduleId orderPaySchedId = OrderPayScheduleId.ofRepoIdOrNull(line.getC_OrderPaySchedule_ID());
				if (invoiceId != null)
				{
					final I_C_PaySelectionLine paySelectionLineOld = byInvoiceId.put(invoiceId, line);
					Check.assumeNull(paySelectionLineOld, "Only one pay selection line shall exist for an invoice but we found: {}, {}", line, paySelectionLineOld); // shall not happen
				}
				else if (orderPaySchedId != null)
				{

					final I_C_PaySelectionLine old = byOrderPaySchedId.put(orderPaySchedId, line);
					Check.assumeNull(old, "Duplicate pay selection line for orderpay scheduele: {}, {}", line, old);
				}
			}
		}

		private Optional<I_C_PaySelectionLine> dequeueFor(@NonNull final PaySelectionLineCandidate candidate)
		{
			if (candidate.getInvoiceId() != null)
			{
				return Optional.ofNullable(byInvoiceId.remove(candidate.getInvoiceId()));
			}
			else if (candidate.getOrderPayScheduleId() != null)
			{
				return Optional.ofNullable(byOrderPaySchedId.remove(candidate.getOrderPayScheduleId()));
			}
			else
			{
				return Optional.empty();
			}
		}

		private List<I_C_PaySelectionLine> dequeueAll()
		{
			final List<I_C_PaySelectionLine> result = new ArrayList<>(byInvoiceId.values());
			result.addAll(byOrderPaySchedId.values());

			byInvoiceId.clear();
			byOrderPaySchedId.clear();

			return result;
		}
	}

}
