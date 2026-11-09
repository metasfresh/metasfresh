package de.metas.cucumber.stepdefs.pos;

import com.google.common.collect.ImmutableList;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.cucumber.stepdefs.role.AD_Role_StepDefData;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.sumup.SumUpCardReader;
import de.metas.payment.sumup.SumUpCardReaderCheckoutRequest;
import de.metas.payment.sumup.SumUpCardReaderExternalId;
import de.metas.payment.sumup.SumUpConfig;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpPollerCycleListener;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.payment.sumup.SumUpTransactionStatusChangedEvent;
import de.metas.payment.sumup.SumUpTransactionStatusChangedListener;
import de.metas.payment.sumup.repository.SumUpConfigRepository;
import de.metas.payment.sumup.repository.SumUpTransactionQuery;
import de.metas.payment.sumup.repository.SumUpTransactionRepository;
import de.metas.payment.sumup.repository.model.I_SUMUP_Config;
import de.metas.payment.sumup.repository.model.I_SUMUP_Transaction;
import de.metas.pos.POSCashJournal;
import de.metas.pos.POSCashJournalLineType;
import de.metas.pos.POSPaymentMethod;
import de.metas.pos.POSService;
import de.metas.pos.POSTerminalId;
import de.metas.pos.invoice_settlement.POSOpenInvoice;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlement;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementBooking;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementRepository;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementService;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStartRequest;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import de.metas.pos.repository.model.I_C_POS_InvoiceCardSettlement;
import de.metas.pos.rest_api.json.JsonCashJournalSummary;
import de.metas.pos.rest_api.json.JsonContext;
import de.metas.user.UserId;
import de.metas.util.Services;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_AD_NotificationGroup;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_Role_NotificationGroup;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_POS;
import org.compiere.model.X_AD_User_NotificationGroup;
import org.compiere.util.DB;
import org.compiere.util.Env;

import javax.annotation.Nullable;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static de.metas.cucumber.stepdefs.StepDefConstants.CLIENT_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.ORG_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
	@NonNull private final C_Payment_StepDefData paymentTable;
	@NonNull private final AD_Role_StepDefData roleTable;

	// the transaction of "another user" that holds an invoice row lock; released by a step or, at the latest, after the scenario
	@Nullable private String invoiceLockHolderTrxName;
	// accounting schemas whose automatic period control a scenario changed, with the original Period_OpenFuture; restored
	// after the scenario, because all scenarios share them
	private final List<I_C_AcctSchema> changedAcctSchemas = new ArrayList<>();
	private final List<Integer> originalPeriodOpenFuture = new ArrayList<>();

	// outcomes of the last concurrent start; a step-def instance lives for one scenario
	private final List<StartOutcome> concurrentStartOutcomes = new ArrayList<>();

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
		configurePOSTerminalWithSumUp(terminalIdentifier, true);
	}

	/**
	 * Like the step above, but the config has no card reader (hence no default one), so a checkout fails with a
	 * configuration error before any SumUp call.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_POS_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Given POS terminal till is configured with SumUp and no default card reader
	 * </pre>
	 */
	@And("^POS terminal (\\S+) is configured with SumUp and no default card reader$")
	public void configurePOSTerminalWithSumUpWithoutDefaultReader(@NonNull final String terminalIdentifier)
	{
		configurePOSTerminalWithSumUp(terminalIdentifier, false);
	}

	private void configurePOSTerminalWithSumUp(@NonNull final String terminalIdentifier, final boolean withDefaultReader)
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
				// SumUpConfig picks the only reader as default, so "no default reader" means "no reader at all"
				.cardReaders(withDefaultReader
						? ImmutableList.of(SumUpCardReader.builder()
						.name("Stub reader " + terminalIdentifier)
						.externalId(cardReaderExternalId)
						.isActive(true)
						.build())
						: ImmutableList.of())
				.defaultCardReaderExternalId(withDefaultReader ? cardReaderExternalId : null)
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
	 * {@link SumUpPollerCycleListener}. SumUp status events are handled asynchronously; the step returns only after
	 * every event the cycle fired was handled (see {@link SumUpEventsTestProbe#waitUntilFiredEventsAreHandled}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And metasfresh polls SumUp
	 * </pre>
	 */
	@And("^metasfresh polls SumUp$")
	public void pollSumUp() throws InterruptedException
	{
		sumUpService().bulkUpdatePendingTransactions(false);
		SpringContextHolder.instance.getBeansOfType(SumUpPollerCycleListener.class)
				.forEach(SumUpPollerCycleListener::afterPollCycle);
		SumUpEventsTestProbe.waitUntilFiredEventsAreHandled();
	}

	/**
	 * Updates the settlement's SumUp transaction from the stub only ({@link SumUpService#updateTransactionFromRemote}),
	 * and returns after the status event it fired (if any) was handled.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the SumUp transaction of card settlement settlement1 is updated from remote
	 * </pre>
	 */
	@And("^the SumUp transaction of card settlement (\\S+) is updated from remote$")
	public void updateTransactionFromRemote(@NonNull final String settlementIdentifier) throws InterruptedException
	{
		final SumUpTransaction trx = getSumUpTransaction(loadSettlement(settlementIdentifier).getId());
		sumUpService().updateTransactionFromRemote(trx.getClientTransactionId());
		SumUpEventsTestProbe.waitUntilFiredEventsAreHandled();
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

	/**
	 * Starts a card settlement of the invoice's open amount ({@link POSInvoiceCardSettlementService#start}) and
	 * registers it under the given identifier.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_POS_StepDefData, C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
	 * </pre>
	 */
	@And("^card settlement (\\S+) is started for invoice (\\S+) at POS terminal (\\S+) by cashier (\\S+)$")
	public void startSettlement(
			@NonNull final String settlementIdentifier,
			@NonNull final String invoiceIdentifier,
			@NonNull final String terminalIdentifier,
			@NonNull final String cashierLogin)
	{
		final POSInvoiceCardSettlement settlement = settlementService().start(toStartRequest(invoiceIdentifier, terminalIdentifier, cashierLogin));
		settlementTable.putOrReplace(
				StepDefDataIdentifier.ofString(settlementIdentifier),
				InterfaceWrapperHelper.load(settlement.getId(), I_C_POS_InvoiceCardSettlement.class));
	}

	/**
	 * Asserts that starting a card settlement is refused with the given AD_Message and that no settlement was
	 * created by the attempt.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then starting a card settlement for invoice invoice1 at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceSettlement.NoLongerOpen'
	 * </pre>
	 */
	@And("^starting a card settlement for invoice (\\S+) at POS terminal (\\S+) by cashier (\\S+) fails with AD_Message '(.*)'$")
	public void startSettlementFails(
			@NonNull final String invoiceIdentifier,
			@NonNull final String terminalIdentifier,
			@NonNull final String cashierLogin,
			@NonNull final String expectedAdMessage)
	{
		final POSInvoiceCardSettlementStartRequest request = toStartRequest(invoiceIdentifier, terminalIdentifier, cashierLogin);
		final long countBefore = countSettlements(request.getInvoiceId());

		assertThatThrownBy(() -> settlementService().start(request))
				.as("card settlement start must be refused")
				.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.getErrorCode()).as("AD_Message").isEqualTo(expectedErrorCode(expectedAdMessage)));

		assertThat(countSettlements(request.getInvoiceId())).as("card settlements of the invoice after the refused start").isEqualTo(countBefore);
	}

	/**
	 * Two threads start a card settlement of the same invoice at the same moment; the outcomes are asserted by
	 * {@link #assertConcurrentStartOutcome}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When two card settlements are started at once for invoice invoice1 at POS terminal till by cashier metasfresh
	 * </pre>
	 */
	@And("^two card settlements are started at once for invoice (\\S+) at POS terminal (\\S+) by cashier (\\S+)$")
	public void startTwoSettlementsConcurrently(
			@NonNull final String invoiceIdentifier,
			@NonNull final String terminalIdentifier,
			@NonNull final String cashierLogin) throws Exception
	{
		final POSInvoiceCardSettlementStartRequest request = toStartRequest(invoiceIdentifier, terminalIdentifier, cashierLogin);
		final Properties ctx = Env.getCtx();
		final CountDownLatch startSignal = new CountDownLatch(1);
		final ExecutorService executor = Executors.newFixedThreadPool(2);
		try
		{
			final List<Future<POSInvoiceCardSettlement>> futures = new ArrayList<>();
			for (int i = 0; i < 2; i++)
			{
				futures.add(executor.submit(() -> {
					try (final IAutoCloseable ignored = Env.switchContext(ctx))
					{
						startSignal.await();
						return settlementService().start(request);
					}
				}));
			}
			startSignal.countDown();

			concurrentStartOutcomes.clear();
			for (final Future<POSInvoiceCardSettlement> future : futures)
			{
				try
				{
					concurrentStartOutcomes.add(StartOutcome.success(future.get(120, TimeUnit.SECONDS)));
				}
				catch (final ExecutionException ex)
				{
					concurrentStartOutcomes.add(StartOutcome.failure(ex.getCause()));
				}
			}
		}
		finally
		{
			executor.shutdownNow();
		}
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then exactly one of them succeeded with status PENDING and the other failed with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen'
	 * </pre>
	 */
	@And("^exactly one of them succeeded with status (\\w+) and the other failed with AD_Message '(.*)'$")
	public void assertConcurrentStartOutcome(@NonNull final String expectedStatus, @NonNull final String expectedAdMessage)
	{
		assertThat(concurrentStartOutcomes).as("outcomes of the concurrent starts").hasSize(2);
		final List<StartOutcome> successes = concurrentStartOutcomes.stream().filter(o -> o.getSettlement() != null).collect(Collectors.toList());
		final List<StartOutcome> failures = concurrentStartOutcomes.stream().filter(o -> o.getSettlement() == null).collect(Collectors.toList());
		assertThat(successes).as("successful starts (failures: %s)", failures).hasSize(1);
		assertThat(successes.get(0).getSettlement().getStatus()).isEqualTo(parseStatus(expectedStatus));
		final Throwable failure = failures.get(0).getError();
		assertThat(failure).isInstanceOfSatisfying(AdempiereException.class,
				ex -> assertThat(ex.getErrorCode()).as("AD_Message").isEqualTo(expectedErrorCode(expectedAdMessage)));
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then invoice invoice1 has 0 card settlements
	 * </pre>
	 */
	@And("^invoice (\\S+) has (\\d+) card settlements?$")
	public void assertSettlementCount(@NonNull final String invoiceIdentifier, final int expectedCount)
	{
		final InvoiceId invoiceId = invoiceTable.getId(StepDefDataIdentifier.ofString(invoiceIdentifier));
		assertThat(countSettlements(invoiceId)).as("card settlements of invoice %s", invoiceIdentifier).isEqualTo(expectedCount);
	}

	/**
	 * Fixture: forces the settlement's status and IsAbandoned flag, as the states that are reached by later steps
	 * of the state machine would leave them.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And card settlement settlement1 is set to status UN with isAbandoned false
	 * </pre>
	 */
	@And("^card settlement (\\S+) is set to status ([A-Z_]+) with isAbandoned (true|false)$")
	public void forceSettlementStatus(
			@NonNull final String settlementIdentifier,
			@NonNull final String status,
			@NonNull final String isAbandoned)
	{
		final POSInvoiceCardSettlementId id = settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier));
		Services.get(ITrxManager.class).runInNewTrx(() -> {
			final POSInvoiceCardSettlement settlement = settlementRepository().getByIdForUpdate(id);
			settlementRepository().save(settlement.toBuilder()
					.status(parseStatus(status))
					.isAbandoned(Boolean.parseBoolean(isAbandoned))
					.build());
		});
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the SumUp stub received a checkout described by card settlement settlement1
	 * </pre>
	 */
	@And("^the SumUp stub received a checkout described by card settlement (\\S+)$")
	public void assertStubCheckoutDescription(@NonNull final String settlementIdentifier)
	{
		final String expected = String.valueOf(settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier)).getRepoId());
		assertThat(SumUpApiStub.getReceivedCheckouts())
				.as("checkouts received by the SumUp stub")
				.extracting(SumUpApiStub.ReceivedCheckout::getDescription)
				.contains(expected);
	}

	/**
	 * Asserts the invoice has no payment and no allocation and is still open for the given amount.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And nothing is booked for invoice invoice1 and its open amount is 16.07
	 * </pre>
	 */
	@And("^nothing is booked for invoice (\\S+) and its open amount is (\\S+)$")
	public void assertNothingBooked(@NonNull final String invoiceIdentifier, @NonNull final String expectedOpenAmt)
	{
		final I_C_Invoice invoice = invoiceTable.get(StepDefDataIdentifier.ofString(invoiceIdentifier));
		InterfaceWrapperHelper.refresh(invoice);
		final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
		assertThat(paymentDAO.streamPaymentIdsByBPartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID())).count())
				.as("payments of the invoice's business partner")
				.isZero();
		assertThat(Services.get(IAllocationDAO.class).retrieveOpenAmtInInvoiceCurrency(invoice, true).toBigDecimal())
				.as("open amount of invoice %s", invoiceIdentifier)
				.isEqualByComparingTo(new BigDecimal(expectedOpenAmt));
	}

	/**
	 * Asserts that the till's open-invoice list ({@link POSService#findOpenInvoices}) shows the invoice together
	 * with the given blocking settlement.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the open invoice list of POS terminal till2 for invoice invoice1 shows blocking card settlement settlement1
	 * </pre>
	 */
	@And("^the open invoice list of POS terminal (\\S+) for invoice (\\S+) shows blocking card settlement (\\S+)$")
	public void assertOpenInvoiceShowsBlockingSettlement(
			@NonNull final String terminalIdentifier,
			@NonNull final String invoiceIdentifier,
			@NonNull final String settlementIdentifier)
	{
		final I_C_Invoice invoice = invoiceTable.get(StepDefDataIdentifier.ofString(invoiceIdentifier));
		InterfaceWrapperHelper.refresh(invoice);
		final POSTerminalId posTerminalId = posTable.getId(StepDefDataIdentifier.ofString(terminalIdentifier));
		final POSInvoiceCardSettlementId expectedId = settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier));

		final POSOpenInvoice openInvoice = SpringContextHolder.instance.getBean(POSService.class)
				.findOpenInvoices(posTerminalId, invoice.getDocumentNo())
				.stream()
				.filter(candidate -> candidate.getInvoiceId().getRepoId() == invoice.getC_Invoice_ID())
				.findFirst()
				.orElseThrow(() -> new AdempiereException("Invoice not in the open invoice list: " + invoiceIdentifier));
		assertThat(openInvoice.getBlockingCardSettlement()).as("blocking card settlement").isNotNull();
		assertThat(openInvoice.getBlockingCardSettlement().getId()).isEqualTo(expectedId);
	}

	/**
	 * Sets the settlement's local SumUp transaction to SUCCESSFUL by saving the record directly, so that no status event
	 * is fired. In real life the transaction is updated by the callback or the poller and the event is delivered by the
	 * event bus; here the scenario delivers the event itself (step below), so it controls how often and how concurrently.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
	 * </pre>
	 */
	@And("^the SumUp transaction of card settlement (\\S+) is set to SUCCESSFUL locally without a status event$")
	public void setSumUpTransactionSuccessfulWithoutEvent(@NonNull final String settlementIdentifier)
	{
		final POSInvoiceCardSettlementId settlementId = settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier));
		final I_SUMUP_Transaction record = queryBL.createQueryBuilder(I_SUMUP_Transaction.class)
				.addEqualsFilter(I_SUMUP_Transaction.COLUMNNAME_C_POS_InvoiceCardSettlement_ID, settlementId)
				.create()
				.firstOnlyNotNull(I_SUMUP_Transaction.class);
		// saved via the model, not SumUpTransactionRepository: the repository would fire the status event
		record.setStatus(SumUpTransactionStatus.SUCCESSFUL.getCode());
		InterfaceWrapperHelper.save(record);
	}

	/**
	 * Delivers the status event of the settlement's current SumUp transaction to every
	 * {@link SumUpTransactionStatusChangedListener}, as the event bus does (same client/org context), either several
	 * times one after the other or several times at the same moment on separate threads.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the SumUp status event of card settlement settlement1 is delivered 2 times one after the other
	 * When the SumUp status event of card settlement settlement1 is delivered 2 times at once
	 * </pre>
	 */
	@And("^the SumUp status event of card settlement (\\S+) is delivered (\\d+) times (one after the other|at once)$")
	public void deliverSumUpStatusEvent(
			@NonNull final String settlementIdentifier,
			final int times,
			@NonNull final String mode) throws Exception
	{
		final SumUpTransactionStatusChangedEvent event = SumUpTransactionStatusChangedEvent.ofNewTransaction(
				getSumUpTransaction(loadSettlement(settlementIdentifier).getId()));
		final Properties eventCtx = Env.newTemporaryCtx();
		Env.setClientId(eventCtx, event.getClientId());
		Env.setOrgId(eventCtx, event.getOrgId());
		final Runnable delivery = () -> {
			try (final IAutoCloseable ignored = Env.switchContext(eventCtx))
			{
				SpringContextHolder.instance.getBeansOfType(SumUpTransactionStatusChangedListener.class)
						.forEach(listener -> listener.onStatusChanged(event));
			}
		};

		if ("one after the other".equals(mode))
		{
			for (int i = 0; i < times; i++)
			{
				delivery.run();
			}
			return;
		}

		final CountDownLatch startSignal = new CountDownLatch(1);
		final ExecutorService executor = Executors.newFixedThreadPool(times);
		try
		{
			final List<Future<?>> futures = new ArrayList<>();
			for (int i = 0; i < times; i++)
			{
				futures.add(executor.submit(() -> {
					startSignal.await();
					delivery.run();
					return null;
				}));
			}
			startSignal.countDown();
			for (final Future<?> future : futures)
			{
				future.get(120, TimeUnit.SECONDS); // an exception outside the listeners fails the step; the POS listener records its own failures as AD_Issues of the settlement, so scenarios assert those
			}
		}
		finally
		{
			executor.shutdownNow();
		}
	}

	/**
	 * Asserts the settlement's {@code ErrorMsg}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And card settlement settlement1 has error message 'refunded at SumUp'
	 * </pre>
	 */
	@And("^card settlement (\\S+) has error message '(.*)'$")
	public void assertSettlementErrorMsg(@NonNull final String settlementIdentifier, @NonNull final String expectedErrorMsg)
	{
		assertThat(loadSettlement(settlementIdentifier).getErrorMsg())
				.as("ErrorMsg of card settlement %s", settlementIdentifier)
				.isEqualTo(expectedErrorMsg);
	}

	/**
	 * Asserts the part of the settlement's card payment that is allocated to the settlement's invoice
	 * ({@link POSInvoiceCardSettlementService#getAllocatedAmount}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the card payment of card settlement settlement1 is allocated with 10.00 to its invoice
	 * </pre>
	 */
	@And("^the card payment of card settlement (\\S+) is allocated with (\\S+) to its invoice$")
	public void assertAllocatedAmount(@NonNull final String settlementIdentifier, @NonNull final String expectedAmount)
	{
		assertThat(settlementService().getAllocatedAmount(loadSettlement(settlementIdentifier)).toBigDecimal())
				.as("allocated amount of the card payment of card settlement %s", settlementIdentifier)
				.isEqualByComparingTo(new BigDecimal(expectedAmount));
	}

	/**
	 * Makes the role the only one that gets the notification group, so that exactly the users of this role are
	 * notified, however many roles earlier runs of the scenarios left behind. Notifies by internal message.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: AD_Role_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And role backOffice is the only role notified of group 'de.metas.pos.InvoiceCardSettlement'
	 * </pre>
	 */
	@And("^role (\\S+) is the only role notified of group '(.*)'$")
	public void setOnlyRoleNotifiedOfGroup(@NonNull final String roleIdentifier, @NonNull final String groupInternalName)
	{
		final I_AD_Role role = roleTable.get(StepDefDataIdentifier.ofString(roleIdentifier));
		final I_AD_NotificationGroup group = queryBL.createQueryBuilder(I_AD_NotificationGroup.class)
				.addEqualsFilter(I_AD_NotificationGroup.COLUMNNAME_InternalName, groupInternalName)
				.create()
				.firstOnlyNotNull(I_AD_NotificationGroup.class);

		queryBL.createQueryBuilder(I_AD_Role_NotificationGroup.class)
				.addEqualsFilter(I_AD_Role_NotificationGroup.COLUMNNAME_AD_NotificationGroup_ID, group.getAD_NotificationGroup_ID())
				.create()
				.delete();

		final I_AD_Role_NotificationGroup roleGroup = InterfaceWrapperHelper.newInstance(I_AD_Role_NotificationGroup.class);
		roleGroup.setAD_Role_ID(role.getAD_Role_ID());
		roleGroup.setAD_NotificationGroup_ID(group.getAD_NotificationGroup_ID());
		roleGroup.setNotificationType(X_AD_User_NotificationGroup.NOTIFICATIONTYPE_Notice);
		InterfaceWrapperHelper.saveRecord(roleGroup);
	}

	/**
	 * Waits until the settlement has exactly the given number of notifications (AD_Note) with the AD_Message, then
	 * checks once more after a second that no further one arrived: notifications are created asynchronously.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And after not more than 30s, card settlement settlement1 has 1 notification with message 'de.metas.pos.InvoiceCardSettlement.CreditRefundPending'
	 * </pre>
	 */
	@And("^after not more than (\\d+)s, card settlement (\\S+) has (\\d+) notifications? with message '(.*)'$")
	public void assertSettlementNotificationCount(
			final int timeoutSec,
			@NonNull final String settlementIdentifier,
			final int expectedCount,
			@NonNull final String adMessageValue) throws InterruptedException
	{
		final POSInvoiceCardSettlementId id = settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier));
		final TableRecordReference recordRef = TableRecordReference.of(I_C_POS_InvoiceCardSettlement.Table_Name, id.getRepoId());
		final Supplier<Integer> countSupplier = () -> queryBL.createQueryBuilder(I_AD_Note.class)
				.addEqualsFilter(I_AD_Note.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Note.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
				.addInSubQueryFilter(
						I_AD_Note.COLUMNNAME_AD_Message_ID,
						I_AD_Message.COLUMNNAME_AD_Message_ID,
						queryBL.createQueryBuilder(I_AD_Message.class).addEqualsFilter(I_AD_Message.COLUMNNAME_Value, adMessageValue).create())
				.create()
				.count();

		StepDefUtil.tryAndWait(timeoutSec, 500, () -> countSupplier.get() >= expectedCount);
		Thread.sleep(1000);
		assertThat(countSupplier.get()).as("notifications %s of card settlement %s", adMessageValue, settlementIdentifier).isEqualTo(expectedCount);
	}

	/**
	 * Runs only the first booking step ({@link POSInvoiceCardSettlementBooking#bookPaymentIfApproved}): the settlement's
	 * SumUp transaction is approved, so the card payment is created and the settlement is APPROVED, but the payment is
	 * not allocated yet. This is the state in which a crash or a retryable error leaves a settlement between the two steps.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the payment of card settlement settlement1 is booked without being allocated
	 * </pre>
	 */
	@And("^the payment of card settlement (\\S+) is booked without being allocated$")
	public void bookPaymentWithoutAllocating(@NonNull final String settlementIdentifier)
	{
		SpringContextHolder.instance.getBean(POSInvoiceCardSettlementBooking.class)
				.bookPaymentIfApproved(settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier)));
	}

	/**
	 * Registers the settlement's booked {@code C_Payment} under the given identifier, for {@code validate payments}
	 * and the other payment steps; fails if the settlement has no payment.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_POS_InvoiceCardSettlement_StepDefData, C_Payment_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And card settlement settlement1 has payment cardPayment
	 * </pre>
	 */
	@And("^card settlement (\\S+) has payment (\\S+)$")
	public void registerSettlementPayment(@NonNull final String settlementIdentifier, @NonNull final String paymentIdentifier)
	{
		final POSInvoiceCardSettlement settlement = loadSettlement(settlementIdentifier);
		assertThat(settlement.getPaymentId()).as("C_Payment_ID of card settlement %s", settlementIdentifier).isNotNull();
		paymentTable.putOrReplace(
				StepDefDataIdentifier.ofString(paymentIdentifier),
				Services.get(IPaymentDAO.class).getById(settlement.getPaymentId()));
	}

	/**
	 * Counts the {@code C_Payment} rows of the invoice's business partner (each scenario creates its own partner).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the partner of invoice invoice1 has 1 payment
	 * </pre>
	 */
	@And("^the partner of invoice (\\S+) has (\\d+) payments?$")
	public void assertPartnerPaymentCount(@NonNull final String invoiceIdentifier, final int expectedCount)
	{
		final I_C_Invoice invoice = invoiceTable.get(StepDefDataIdentifier.ofString(invoiceIdentifier));
		assertThat(Services.get(IPaymentDAO.class).streamPaymentIdsByBPartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID())).count())
				.as("payments of the partner of invoice %s", invoiceIdentifier)
				.isEqualTo(expectedCount);
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And invoice invoice1 has 1 allocation line
	 * </pre>
	 */
	@And("^invoice (\\S+) has (\\d+) allocation lines?$")
	public void assertInvoiceAllocationLineCount(@NonNull final String invoiceIdentifier, final int expectedCount)
	{
		final InvoiceId invoiceId = invoiceTable.getId(StepDefDataIdentifier.ofString(invoiceIdentifier));
		assertThat(queryBL.createQueryBuilder(I_C_AllocationLine.class)
				.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Invoice_ID, invoiceId)
				.create()
				.count())
				.as("allocation lines of invoice %s", invoiceIdentifier)
				.isEqualTo(expectedCount);
	}

	/**
	 * Asserts the number of card payment lines of the terminal's open cash journal that reference the settlement.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the cash journal of POS terminal till has 1 card payment line referencing card settlement settlement1
	 * </pre>
	 */
	@And("^the cash journal of POS terminal (\\S+) has (\\d+) card payment lines? referencing card settlement (\\S+)$")
	public void assertJournalLinesReferencingSettlement(
			@NonNull final String terminalIdentifier,
			final int expectedCount,
			@NonNull final String settlementIdentifier)
	{
		final POSInvoiceCardSettlementId settlementId = settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier));
		final long actualCount = getCurrentCashJournal(terminalIdentifier).getLines()
				.stream()
				.filter(line -> line.getType() == POSCashJournalLineType.CARD_PAYMENT)
				.filter(line -> settlementId.equals(line.getPosInvoiceCardSettlementId()))
				.count();
		assertThat(actualCount).as("card payment lines referencing card settlement %s", settlementIdentifier).isEqualTo(expectedCount);
	}

	/**
	 * Asserts the totals of the closing summary the till shows for the terminal's open cash journal
	 * ({@link JsonCashJournalSummary}): the card total and the cash (drawer) total.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the closing summary of the cash journal of POS terminal till shows card total 16.07 and cash total 100
	 * </pre>
	 */
	@And("^the closing summary of the cash journal of POS terminal (\\S+) shows card total (\\S+) and cash total (\\S+)$")
	public void assertClosingSummaryTotals(
			@NonNull final String terminalIdentifier,
			@NonNull final String expectedCardTotal,
			@NonNull final String expectedCashTotal)
	{
		final JsonCashJournalSummary summary = JsonCashJournalSummary.of(
				getCurrentCashJournal(terminalIdentifier),
				JsonContext.builder()
						.currencyRepository(SpringContextHolder.instance.getBean(CurrencyRepository.class))
						.adLanguage(Env.getADLanguageOrBaseLanguage())
						.build());
		assertThat(getPaymentMethodTotal(summary, POSPaymentMethod.CARD)).as("card total").isEqualByComparingTo(new BigDecimal(expectedCardTotal));
		assertThat(getPaymentMethodTotal(summary, POSPaymentMethod.CASH)).as("cash total").isEqualByComparingTo(new BigDecimal(expectedCashTotal));
	}

	private static BigDecimal getPaymentMethodTotal(@NonNull final JsonCashJournalSummary summary, @NonNull final POSPaymentMethod paymentMethod)
	{
		return summary.getPaymentMethods()
				.stream()
				.filter(method -> method.getPaymentMethod() == paymentMethod)
				.map(JsonCashJournalSummary.JsonPaymentMethodSummary::getAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private POSCashJournal getCurrentCashJournal(@NonNull final String terminalIdentifier)
	{
		final POSTerminalId posTerminalId = posTable.getId(StepDefDataIdentifier.ofString(terminalIdentifier));
		return SpringContextHolder.instance.getBean(POSService.class).getCurrentCashJournal(posTerminalId)
				.orElseThrow(() -> new AdempiereException("No open cash journal for POS terminal " + terminalIdentifier));
	}


	/**
	 * Releases what the scenario left behind for the next ones: an invoice row lock still held by "another user", and
	 * a closed accounting period (the accounting schema is shared by all scenarios).
	 */
	@After
	public void releaseInvoiceLockAndReopenPeriod()
	{
		try
		{
			releaseInvoiceLock();
		}
		finally
		{
			for (int i = 0; i < changedAcctSchemas.size(); i++)
			{
				final I_C_AcctSchema acctSchema = changedAcctSchemas.get(i);
				acctSchema.setPeriod_OpenFuture(originalPeriodOpenFuture.get(i));
				InterfaceWrapperHelper.saveRecord(acctSchema);
			}
			changedAcctSchemas.clear();
			originalPeriodOpenFuture.clear();
		}
	}

	/**
	 * Locks the invoice row ({@code FOR UPDATE}) in a separate, still open transaction, as a concurrent user or process
	 * would; a booking that needs the invoice lock meanwhile waits until its lock timeout. The lock is held until
	 * {@link #releaseInvoiceLock()} (step or after-scenario hook).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the invoice row of invoice invoice1 is locked by another transaction
	 * </pre>
	 */
	@And("^the invoice row of invoice (\\S+) is locked by another transaction$")
	public void lockInvoiceInOtherTransaction(@NonNull final String invoiceIdentifier)
	{
		assertThat(invoiceLockHolderTrxName).as("an invoice lock is already held").isNull();
		final InvoiceId invoiceId = invoiceTable.getId(StepDefDataIdentifier.ofString(invoiceIdentifier));
		final String trxName = Services.get(ITrxManager.class).createTrxName("cucumber-invoice-lock-holder", true);
		invoiceLockHolderTrxName = trxName;
		DB.getSQLValueEx(trxName, "SELECT C_Invoice_ID FROM C_Invoice WHERE C_Invoice_ID=? FOR UPDATE", invoiceId.getRepoId());
	}

	/**
	 * Ends the transaction that holds the invoice row lock (see {@link #lockInvoiceInOtherTransaction}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the other transaction releases its invoice lock
	 * </pre>
	 */
	@And("^the other transaction releases its invoice lock$")
	public void releaseInvoiceLock()
	{
		final String trxName = invoiceLockHolderTrxName;
		if (trxName == null)
		{
			return;
		}
		invoiceLockHolderTrxName = null;

		final ITrx trx = Services.get(ITrxManager.class).getTrxOrNull(trxName);
		if (trx != null)
		{
			try
			{
				trx.rollback();
			}
			finally
			{
				trx.close();
			}
		}
	}

	/**
	 * Closes the accounting period for today's date (the cucumber-frozen current date): the client's accounting schemas,
	 * which use automatic period control, get no open day in the future, so their last open day is yesterday and no
	 * document dated today can be completed ("period closed"). Reopened after the scenario.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the accounting period is closed for today
	 * </pre>
	 */
	@And("^the accounting period is closed for today$")
	public void closePeriodForToday()
	{
		final List<I_C_AcctSchema> acctSchemas = queryBL.createQueryBuilder(I_C_AcctSchema.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_AcctSchema.COLUMNNAME_AD_Client_ID, CLIENT_ID)
				.create()
				.list();
		assertThat(acctSchemas).as("accounting schemas of the client").isNotEmpty();
		for (final I_C_AcctSchema acctSchema : acctSchemas)
		{
			assertThat(acctSchema.isAutoPeriodControl()).as("automatic period control of %s", acctSchema.getName()).isTrue();
			changedAcctSchemas.add(acctSchema);
			originalPeriodOpenFuture.add(acctSchema.getPeriod_OpenFuture());
			acctSchema.setPeriod_OpenFuture(-1);
			InterfaceWrapperHelper.saveRecord(acctSchema);
		}
	}

	/**
	 * Asserts the settlement's {@code BookingAttempts} (failed booking attempts counted so far).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And card settlement settlement1 has 5 booking attempts
	 * </pre>
	 */
	@And("^card settlement (\\S+) has (\\d+) booking attempts?$")
	public void assertBookingAttempts(@NonNull final String settlementIdentifier, final int expectedAttempts)
	{
		assertThat(loadSettlement(settlementIdentifier).getBookingAttempts())
				.as("BookingAttempts of card settlement %s", settlementIdentifier)
				.isEqualTo(expectedAttempts);
	}

	/**
	 * Asserts the settlement has no {@code C_Payment_ID} (no card payment was booked).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And card settlement settlement1 has no payment
	 * </pre>
	 */
	@And("^card settlement (\\S+) has no payment$")
	public void assertNoPayment(@NonNull final String settlementIdentifier)
	{
		assertThat(loadSettlement(settlementIdentifier).getPaymentId())
				.as("C_Payment_ID of card settlement %s", settlementIdentifier)
				.isNull();
	}

	/**
	 * Asserts the settlement's {@code ErrorMsg} is empty.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And card settlement settlement1 has no error message
	 * </pre>
	 */
	@And("^card settlement (\\S+) has no error message$")
	public void assertNoErrorMsg(@NonNull final String settlementIdentifier)
	{
		assertThat(loadSettlement(settlementIdentifier).getErrorMsg())
				.as("ErrorMsg of card settlement %s", settlementIdentifier)
				.isNull();
	}


	private POSInvoiceCardSettlementService settlementService() {return SpringContextHolder.instance.getBean(POSInvoiceCardSettlementService.class);}

	private POSInvoiceCardSettlementStartRequest toStartRequest(
			@NonNull final String invoiceIdentifier,
			@NonNull final String terminalIdentifier,
			@NonNull final String cashierLogin)
	{
		return POSInvoiceCardSettlementStartRequest.builder()
				.posTerminalId(posTable.getId(StepDefDataIdentifier.ofString(terminalIdentifier)))
				.cashierId(StepDefUtil.getUserIdByLogin(cashierLogin))
				.invoiceId(invoiceTable.getId(StepDefDataIdentifier.ofString(invoiceIdentifier)))
				.build();
	}

	private long countSettlements(@NonNull final InvoiceId invoiceId)
	{
		return queryBL.createQueryBuilder(I_C_POS_InvoiceCardSettlement.class)
				.addEqualsFilter(I_C_POS_InvoiceCardSettlement.COLUMNNAME_C_Invoice_ID, invoiceId)
				.create()
				.count();
	}

	private static String expectedErrorCode(@NonNull final String adMessage)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final AdMessageKey key = AdMessageKey.of(adMessage);
		return Optional.ofNullable(msgBL.getErrorCode(key)).orElseGet(key::toAD_Message);
	}

	@Value
	private static class StartOutcome
	{
		@Nullable POSInvoiceCardSettlement settlement;
		@Nullable Throwable error;

		static StartOutcome success(final POSInvoiceCardSettlement settlement) {return new StartOutcome(settlement, null);}

		static StartOutcome failure(final Throwable error) {return new StartOutcome(null, error);}
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
