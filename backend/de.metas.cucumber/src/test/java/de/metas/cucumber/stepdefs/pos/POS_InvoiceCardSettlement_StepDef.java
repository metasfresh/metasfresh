package de.metas.cucumber.stepdefs.pos;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.ICurrencyDAO;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.SumUpCardReader;
import de.metas.payment.sumup.SumUpCardReaderExternalId;
import de.metas.payment.sumup.SumUpCardReaderCheckoutRequest;
import de.metas.payment.sumup.SumUpConfig;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpPollerCycleListener;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.repository.SumUpConfigRepository;
import de.metas.payment.sumup.repository.SumUpTransactionQuery;
import de.metas.payment.sumup.repository.SumUpTransactionRepository;
import de.metas.payment.sumup.repository.model.I_SUMUP_Config;
import de.metas.pos.POSTerminalId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlement;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementRepository;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import de.metas.pos.repository.model.I_C_POS_InvoiceCardSettlement;
import de.metas.user.UserId;
import de.metas.util.Services;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_POS;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static de.metas.cucumber.stepdefs.StepDefConstants.CLIENT_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.METASFRESH_VALUE;
import static de.metas.cucumber.stepdefs.StepDefConstants.ORG_ID;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for POS invoice card settlements: wiring a POS terminal to the in-process SumUp API stub
 * ({@link SumUpApiStub}), programming the stub, and asserting on settlements, their SumUp transactions and
 * the checkouts the stub received. The settlement start/check/release/back-office steps are added by the tasks
 * that implement those operations.
 */
@RequiredArgsConstructor
public class POS_InvoiceCardSettlement_StepDef
{
	private static final String STUB_API_KEY = "stub-api-key";
	private static final String STUB_MERCHANT_CODE = "STUBMERCHANT";

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);

	@NonNull private final C_POS_StepDefData posTable;
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final C_POS_InvoiceCardSettlement_StepDefData settlementTable;

	// resolved lazily: this class is instantiated for the @Before hook, i.e. before metasfresh is started
	private SumUpService sumUpService() {return SpringContextHolder.instance.getBean(SumUpService.class);}

	private SumUpTransactionRepository sumUpTransactionRepository() {return SpringContextHolder.instance.getBean(SumUpTransactionRepository.class);}

	private SumUpConfigRepository sumUpConfigRepository() {return SpringContextHolder.instance.getBean(SumUpConfigRepository.class);}

	private POSInvoiceCardSettlementRepository settlementRepository() {return SpringContextHolder.instance.getBean(POSInvoiceCardSettlementRepository.class);}

	/**
	 * Every scenario starts with a clean stub: no programmed answers, no recorded checkouts, no known transactions.
	 */
	@Before
	public void resetSumUpApiStub()
	{
		SumUpApiStub.reset();
	}

	/**
	 * Creates an active SUMUP_Config (stub credentials) with one active card reader named {@code reader-<terminal>},
	 * makes that reader the config's default, and points the POS terminal's payment processor at the config.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_POS_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Given POS terminal till is configured with SumUp and a default card reader
	 * </pre>
	 */
	@And("^POS terminal (\\S+) is configured with SumUp and a default card reader$")
	public void configurePOSTerminalWithSumUp(@NonNull final String terminalIdentifier)
	{
		final I_C_POS posRecord = posTable.get(StepDefDataIdentifier.ofString(terminalIdentifier));

		// SumUpConfigRepository can only update an existing config, so the config record itself is created directly
		final I_SUMUP_Config configRecord = InterfaceWrapperHelper.newInstance(I_SUMUP_Config.class);
		configRecord.setAD_Org_ID(ORG_ID.getRepoId());
		configRecord.setIsActive(true);
		configRecord.setApiKey(STUB_API_KEY);
		configRecord.setSUMUP_merchant_code(STUB_MERCHANT_CODE);
		InterfaceWrapperHelper.save(configRecord);
		final SumUpConfigId sumUpConfigId = SumUpConfigId.ofRepoId(configRecord.getSUMUP_Config_ID());

		// the card reader and the default-reader link go through the repository
		final SumUpCardReaderExternalId cardReaderExternalId = SumUpCardReaderExternalId.ofString(cardReaderExternalId(terminalIdentifier));
		sumUpConfigRepository().updateById(sumUpConfigId, config -> config.toBuilder()
				.cardReaders(ImmutableList.of(SumUpCardReader.builder()
						.name("Stub reader " + terminalIdentifier)
						.externalId(cardReaderExternalId)
						.isActive(true)
						.build()))
				.defaultCardReaderExternalId(cardReaderExternalId)
				.build());

		// POSTerminalRepository has no API to set the payment processor of an existing terminal
		posRecord.setPOSPaymentProcessor(POSPaymentProcessorType.SumUp.getCode());
		posRecord.setSUMUP_Config_ID(sumUpConfigId.getRepoId());
		InterfaceWrapperHelper.save(posRecord);
	}

	private static String cardReaderExternalId(final String terminalIdentifier)
	{
		return "reader-" + terminalIdentifier;
	}

	/**
	 * Fixture: creates a settlement (status PENDING) for the invoice's grand total, as the settlement repository does.
	 * The settlement start step added by later tasks creates settlements through the service instead.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_POS_StepDefData, C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And card settlement settlement1 exists for invoice invoice1 at POS terminal till by cashier metasfresh
	 * </pre>
	 */
	@And("^card settlement (\\S+) exists for invoice (\\S+) at POS terminal (\\S+) by cashier (\\S+)$")
	public void createSettlement(
			@NonNull final String settlementIdentifier,
			@NonNull final String invoiceIdentifier,
			@NonNull final String terminalIdentifier,
			@NonNull final String cashierLogin)
	{
		final I_C_Invoice invoice = invoiceTable.get(StepDefDataIdentifier.ofString(invoiceIdentifier));
		InterfaceWrapperHelper.refresh(invoice);
		final POSTerminalId posTerminalId = posTable.getId(StepDefDataIdentifier.ofString(terminalIdentifier));
		final UserId cashierId = StepDefUtil.getUserIdByLogin(cashierLogin);

		final POSInvoiceCardSettlement settlement = settlementRepository().create(
				ORG_ID,
				posTerminalId,
				InvoiceId.ofRepoId(invoice.getC_Invoice_ID()),
				BPartnerId.ofRepoId(invoice.getC_BPartner_ID()),
				cashierId,
				Money.of(invoice.getGrandTotal(), CurrencyId.ofRepoId(invoice.getC_Currency_ID())));

		settlementTable.putOrReplace(
				StepDefDataIdentifier.ofString(settlementIdentifier),
				InterfaceWrapperHelper.load(settlement.getId(), I_C_POS_InvoiceCardSettlement.class));
	}

	/**
	 * Sends a card checkout for the settlement's amount on the terminal's default reader via
	 * {@link SumUpService#cardReaderCheckout}, with a posRef naming the settlement, as the till does.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_POS_StepDefData (via the settlement's terminal), C_POS_InvoiceCardSettlement_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When a SumUp card checkout is sent for card settlement settlement1
	 * </pre>
	 */
	@And("^a SumUp card checkout is sent for card settlement (\\S+)$")
	public void sendCheckout(@NonNull final String settlementIdentifier)
	{
		final POSInvoiceCardSettlement settlement = loadSettlement(settlementIdentifier);
		final I_C_POS posRecord = InterfaceWrapperHelper.load(settlement.getPosTerminalId(), I_C_POS.class);
		final SumUpConfigId sumUpConfigId = SumUpConfigId.ofRepoId(posRecord.getSUMUP_Config_ID());
		final SumUpConfig sumUpConfig = sumUpService().getConfig(sumUpConfigId);

		final Amount amount = Amount.of(
				settlement.getAmount().toBigDecimal(),
				currencyDAO.getCurrencyCodeById(settlement.getAmount().getCurrencyId()));

		sumUpService().cardReaderCheckout(SumUpCardReaderCheckoutRequest.builder()
				.configId(sumUpConfigId)
				.cardReaderId(sumUpConfig.getDefaultCardReaderExternalIdNotNull())
				.amount(amount)
				.description("card settlement " + settlement.getId().getRepoId())
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(CLIENT_ID, ORG_ID))
				.posRef(SumUpPOSRef.builder()
						.posInvoiceCardSettlementId(settlement.getId().getRepoId())
						.build())
				.build());
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Given SumUp API stub answers the next checkout with SUCCESS
	 * Given SumUp API stub answers the next checkout with HTTP 4xx
	 * Given SumUp API stub answers the next checkout with HTTP 5xx
	 * Given SumUp API stub answers the next checkout with CONNECTION_RESET
	 * </pre>
	 */
	@And("^SumUp API stub answers the next checkout with (SUCCESS|HTTP 4xx|HTTP 5xx|CONNECTION_RESET)$")
	public void stubAnswersNextCheckout(@NonNull final String behavior)
	{
		final SumUpApiStub.CheckoutBehavior checkoutBehavior;
		switch (behavior)
		{
			case "SUCCESS":
				checkoutBehavior = SumUpApiStub.CheckoutBehavior.SUCCESS;
				break;
			case "HTTP 4xx":
				checkoutBehavior = SumUpApiStub.CheckoutBehavior.HTTP_4XX;
				break;
			case "HTTP 5xx":
				checkoutBehavior = SumUpApiStub.CheckoutBehavior.HTTP_5XX;
				break;
			default:
				checkoutBehavior = SumUpApiStub.CheckoutBehavior.CONNECTION_RESET;
				break;
		}
		SumUpApiStub.setNextCheckoutBehavior(checkoutBehavior);
	}

	/**
	 * Programs what the stub reports for the settlement's SumUp transaction (found via
	 * {@code SUMUP_Transaction.C_POS_InvoiceCardSettlement_ID}); the transaction must already exist, i.e. the checkout
	 * must have been sent.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
	 * And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL refunded 16.07
	 * </pre>
	 */
	@And("^SumUp API stub reports transaction of card settlement (\\S+) as (PENDING|SUCCESSFUL|FAILED|CANCELLED)(?: refunded (\\S+))?$")
	public void stubReportsTransaction(
			@NonNull final String settlementIdentifier,
			@NonNull final String status,
			@Nullable final String refundedAmount)
	{
		final SumUpTransaction trx = getSumUpTransaction(loadSettlement(settlementIdentifier).getId());
		SumUpApiStub.setTransactionStatus(
				trx.getClientTransactionId().getAsString(),
				status,
				refundedAmount != null ? new BigDecimal(refundedAmount) : null);
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And SumUp API stub fails the status lookup
	 * </pre>
	 */
	@And("^SumUp API stub fails the status lookup$")
	public void stubFailsStatusLookup()
	{
		SumUpApiStub.setFailStatusLookup(true);
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And SumUp API stub answers terminate with HTTP 204
	 * And SumUp API stub answers terminate with HTTP 4xx
	 * </pre>
	 */
	@And("^SumUp API stub answers terminate with (HTTP 204|HTTP 4xx)$")
	public void stubAnswersTerminate(@NonNull final String answer)
	{
		SumUpApiStub.setTerminateFails("HTTP 4xx".equals(answer));
	}

	/**
	 * Runs one poller cycle: updates the PENDING SumUp transactions from the stub, then calls every
	 * {@link SumUpPollerCycleListener}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And metasfresh polls SumUp
	 * </pre>
	 */
	@And("^metasfresh polls SumUp$")
	public void pollSumUp()
	{
		sumUpService().bulkUpdatePendingTransactions(false);
		SpringContextHolder.instance.getBeansOfType(SumUpPollerCycleListener.class)
				.forEach(SumUpPollerCycleListener::afterPollCycle);
	}

	/**
	 * Updates the settlement's SumUp transaction from the stub only ({@link SumUpService#updateTransactionFromRemote}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the SumUp transaction of card settlement settlement1 is updated from remote
	 * </pre>
	 */
	@And("^the SumUp transaction of card settlement (\\S+) is updated from remote$")
	public void updateTransactionFromRemote(@NonNull final String settlementIdentifier)
	{
		final SumUpTransaction trx = getSumUpTransaction(loadSettlement(settlementIdentifier).getId());
		sumUpService().updateTransactionFromRemote(trx.getClientTransactionId());
	}

	/**
	 * Reloads the settlement's SumUp transaction from the DB by {@code C_POS_InvoiceCardSettlement_ID} (so it also
	 * proves the reference was persisted) and asserts its status.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the SumUp transaction of card settlement settlement1 has status SUCCESSFUL
	 * </pre>
	 */
	@And("^the SumUp transaction of card settlement (\\S+) has status (\\w+)$")
	public void assertSumUpTransactionStatus(@NonNull final String settlementIdentifier, @NonNull final String expectedStatus)
	{
		final SumUpTransaction trx = getSumUpTransaction(loadSettlement(settlementIdentifier).getId());
		assertThat(trx.getStatus().getCode()).as("SUMUP_Transaction.Status").isEqualTo(expectedStatus);
	}

	/**
	 * Backdates {@code C_POS_InvoiceCardSettlement.Created} to the given number of seconds before the (cucumber-frozen)
	 * current time.
	 * <p>
	 * In real life the settlement ages because time passes while the card dialog is open or abandoned; cucumber freezes
	 * the clock, and {@code Created} is a standard column that no service can set, so the age is written with a direct
	 * DB update.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And card settlement settlement1 was created 200 seconds ago
	 * </pre>
	 */
	@And("^card settlement (\\S+) was created (\\d+) seconds ago$")
	public void backdateSettlement(@NonNull final String settlementIdentifier, final int secondsAgo)
	{
		final POSInvoiceCardSettlementId id = settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier));
		final Instant created = SystemTime.asInstant().minusSeconds(secondsAgo);
		DB.executeUpdateAndThrowExceptionOnFail(
				"UPDATE " + I_C_POS_InvoiceCardSettlement.Table_Name + " SET Created=? WHERE C_POS_InvoiceCardSettlement_ID=?",
				new Object[] { Timestamp.from(created), id.getRepoId() },
				ITrx.TRXNAME_None);
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then card settlement settlement1 has status SUCCESSFUL
	 * Then card settlement settlement1 has status UNKNOWN and isAbandoned true
	 * </pre>
	 */
	@And("^card settlement (\\S+) has status ([A-Z_]+)(?: and isAbandoned (true|false))?$")
	public void assertSettlementStatus(
			@NonNull final String settlementIdentifier,
			@NonNull final String expectedStatus,
			@Nullable final String expectedIsAbandoned)
	{
		final POSInvoiceCardSettlement settlement = loadSettlement(settlementIdentifier);
		assertThat(settlement.getStatus())
				.as("status of card settlement %s", settlementIdentifier)
				.isEqualTo(parseStatus(expectedStatus));
		if (expectedIsAbandoned != null)
		{
			assertThat(settlement.isAbandoned())
					.as("isAbandoned of card settlement %s", settlementIdentifier)
					.isEqualTo(Boolean.parseBoolean(expectedIsAbandoned));
		}
	}

	/**
	 * Asserts how many checkouts the stub received, optionally the amount and reader of each of them.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the SumUp stub received 1 checkout with amount 16.07 on reader reader-till
	 * Then the SumUp stub received 0 checkouts
	 * </pre>
	 */
	@And("^the SumUp stub received (\\d+) checkouts?(?: with amount (\\S+) on reader (\\S+))?$")
	public void assertStubReceivedCheckouts(
			final int expectedCount,
			@Nullable final String expectedAmount,
			@Nullable final String expectedReader)
	{
		final List<SumUpApiStub.ReceivedCheckout> checkouts = SumUpApiStub.getReceivedCheckouts();
		assertThat(checkouts).as("checkouts received by the SumUp stub").hasSize(expectedCount);
		for (final SumUpApiStub.ReceivedCheckout checkout : checkouts)
		{
			if (expectedAmount != null)
			{
				assertThat(checkout.getAmount()).as("amount of checkout").isEqualByComparingTo(new BigDecimal(expectedAmount));
			}
			if (expectedReader != null)
			{
				assertThat(SumUpCardReaderExternalId.ofString(checkout.getReaderId()))
						.as("reader of checkout")
						.isEqualTo(SumUpCardReaderExternalId.ofString(expectedReader));
			}
		}
	}

	/**
	 * Counts the AD_Issue rows tagged with the settlement ({@code AD_Table_ID} = C_POS_InvoiceCardSettlement,
	 * {@code Record_ID} = the settlement).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then card settlement settlement1 has 1 AD_Issue
	 * Then card settlement settlement1 has 0 AD_Issues
	 * </pre>
	 */
	@And("^card settlement (\\S+) has (\\d+) AD_Issues?$")
	public void assertSettlementIssueCount(@NonNull final String settlementIdentifier, final int expectedCount)
	{
		final POSInvoiceCardSettlementId id = settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier));
		final TableRecordReference recordRef = TableRecordReference.of(I_C_POS_InvoiceCardSettlement.Table_Name, id.getRepoId());
		final int actualCount = queryBL.createQueryBuilder(I_AD_Issue.class)
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
				.create()
				.count();
		assertThat(actualCount).as("AD_Issues of card settlement %s", settlementIdentifier).isEqualTo(expectedCount);
	}

	//
	//
	//

	private POSInvoiceCardSettlement loadSettlement(@NonNull final String settlementIdentifier)
	{
		return settlementRepository().getById(settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier)));
	}

	/**
	 * Reloads the transaction from the DB, found by the settlement reference.
	 */
	private SumUpTransaction getSumUpTransaction(@NonNull final POSInvoiceCardSettlementId settlementId)
	{
		final List<SumUpTransaction> trxs = sumUpTransactionRepository().list(SumUpTransactionQuery.builder()
				.posRef(SumUpPOSRef.builder().posInvoiceCardSettlementId(settlementId.getRepoId()).build())
				.build());
		assertThat(trxs).as("SumUp transactions referencing card settlement %s", settlementId).hasSize(1);
		return trxs.get(0);
	}

	private static POSInvoiceCardSettlementStatus parseStatus(@NonNull final String name)
	{
		for (final POSInvoiceCardSettlementStatus status : POSInvoiceCardSettlementStatus.values())
		{
			if (status.name().equals(name) || status.getCode().equals(name))
			{
				return status;
			}
		}
		throw new IllegalArgumentException("Unknown card settlement status: " + name);
	}
}
