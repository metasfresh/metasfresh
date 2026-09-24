package de.metas.pos.withdrawal;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.time.SystemTime;
import de.metas.costing.ChargeId;
import de.metas.i18n.AdMessageKey;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.pos.POSCashJournal;
import de.metas.pos.POSCashJournalId;
import de.metas.pos.POSCashJournalService;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalService;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Payment;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Cash taken out of the POS till for an expense (e.g. travel costs paid on the spot).
 * <p>
 * A withdrawal is booked as a completed outbound cash payment to the organization's own business partner, carrying the
 * chosen category's {@code C_Charge}; its posting therefore goes DR the charge's expense account / CR the till bank
 * account's in-transit account. The till's open cash journal gets a matching negative cash in/out line, in the same
 * transaction.
 * <p>
 * The offered categories are the active charges of the charge type configured in sysconfig {@value #SYSCONFIG_ChargeTypeId}.
 */
@Service
@RequiredArgsConstructor
public class POSCashWithdrawalService
{
	public static final String SYSCONFIG_ChargeTypeId = "de.metas.pos.CashWithdrawal.C_ChargeType_ID";

	private static final AdMessageKey MSG_NoCategories = AdMessageKey.of("de.metas.pos.CashWithdrawal.NoCategories");
	private static final AdMessageKey MSG_AmountMustBePositive = AdMessageKey.of("de.metas.pos.CashWithdrawal.AmountMustBePositive");

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	@NonNull private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	@NonNull private final IUserBL userBL = Services.get(IUserBL.class);

	@NonNull private final POSTerminalService posTerminalService;
	@NonNull private final POSCashJournalService posCashJournalService;
	@NonNull private final POSCashWithdrawalCategoryRepository categoryRepository;

	/**
	 * @throws AdempiereException ({@code de.metas.pos.CashWithdrawal.NoCategories}) if no charge type is configured or it has no active charges
	 */
	@NonNull
	public ImmutableList<POSCashWithdrawalCategory> getCategories(@NonNull final POSTerminalId posTerminalId)
	{
		final POSTerminal terminal = posTerminalService.getPOSTerminalById(posTerminalId);
		return getCategories(terminal);
	}

	@NonNull
	private ImmutableList<POSCashWithdrawalCategory> getCategories(@NonNull final POSTerminal terminal)
	{
		final OrgId orgId = terminal.getOrgId();
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(orgDAO.getClientIdByOrgId(orgId), orgId);

		final int chargeTypeRepoId = sysConfigBL.getIntValue(SYSCONFIG_ChargeTypeId, -1, clientAndOrgId);
		if (chargeTypeRepoId <= 0)
		{
			throw new AdempiereException(MSG_NoCategories);
		}

		final ImmutableList<POSCashWithdrawalCategory> categories = categoryRepository.getByChargeTypeId(chargeTypeRepoId, clientAndOrgId);
		if (categories.isEmpty())
		{
			throw new AdempiereException(MSG_NoCategories);
		}
		return categories;
	}

	/**
	 * @throws AdempiereException ({@code de.metas.pos.CashJournalNotOpen}) if the terminal has no open cash journal
	 * @throws AdempiereException ({@code de.metas.pos.CashWithdrawal.AmountMustBePositive}) if the amount is not greater than zero
	 */
	@NonNull
	public POSCashWithdrawalResult withdraw(@NonNull final POSCashWithdrawalRequest request)
	{
		return trxManager.callInThreadInheritedTrx(() -> withdrawInTrx(request));
	}

	@NonNull
	private POSCashWithdrawalResult withdrawInTrx(@NonNull final POSCashWithdrawalRequest request)
	{
		final POSTerminal terminal = posTerminalService.getPOSTerminalById(request.getPosTerminalId());
		final POSCashJournalId journalId = terminal.getCashJournalIdNotNull();

		if (request.getAmount().signum() <= 0)
		{
			throw new AdempiereException(MSG_AmountMustBePositive);
		}
		final Money amount = Money.of(request.getAmount(), terminal.getCurrencyId());

		final POSCashWithdrawalCategory category = getCategory(terminal, request.getChargeId());

		final OrgId orgId = terminal.getOrgId();
		final BPartnerId orgBPartnerId = bpartnerOrgBL.retrieveLinkedBPartnerId(orgId)
				.orElseThrow(() -> new AdempiereException("No business partner is linked to the POS terminal's organization")
						.setParameter("AD_Org_ID", orgId)
						.setParameter("posTerminalId", terminal.getId()));

		final Instant dateTrx = SystemTime.asInstant();
		final I_C_Payment payment = paymentBL.newOutboundPaymentBuilder()
				.adOrgId(orgId)
				.orgBankAccountId(terminal.getCashbookId())
				.bpartnerId(orgBPartnerId)
				.chargeId(category.getChargeId())
				.payAmt(amount.toBigDecimal())
				.currencyId(amount.getCurrencyId())
				.tenderType(TenderType.Cash)
				.dateTrx(dateTrx)
				.description(category.getName())
				.createAndProcess();

		final POSCashJournal journal = posCashJournalService.changeJournalById(
				journalId,
				cashJournal -> cashJournal.addCashInOut(amount.negate(), request.getCashierId(), category.getName()));

		return POSCashWithdrawalResult.builder()
				.paymentId(PaymentId.ofRepoId(payment.getC_Payment_ID()))
				.documentNo(payment.getDocumentNo())
				.chargeName(category.getName())
				.amount(amount)
				.dateTrx(dateTrx)
				.cashierName(userBL.getUserFullNameById(request.getCashierId()))
				.terminalName(terminal.getName())
				.journal(journal)
				.build();
	}

	/**
	 * The UI only offers the configured categories, so a charge outside of them indicates a client bug, not a cashier error.
	 */
	@NonNull
	private POSCashWithdrawalCategory getCategory(@NonNull final POSTerminal terminal, @NonNull final ChargeId chargeId)
	{
		return getCategories(terminal)
				.stream()
				.filter(category -> category.getChargeId().equals(chargeId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("Charge is not a cash withdrawal category")
						.setParameter("C_Charge_ID", chargeId)
						.setParameter("posTerminalId", terminal.getId()));
	}
}
