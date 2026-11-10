package de.metas.cucumber.stepdefs.pos;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.cucumber.stepdefs.process.AD_Process_Run_StepDef;
import de.metas.cucumber.stepdefs.role.AD_Role_StepDefData;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
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
import de.metas.pos.POSCashJournalLine;
import de.metas.pos.POSCashJournalLineType;
import de.metas.pos.POSPaymentMethod;
import de.metas.pos.POSService;
import de.metas.pos.POSTerminalId;
import de.metas.pos.invoice_settlement.POSOpenInvoice;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlement;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementBooking;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementCheckResult;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementReleaseRequest;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementReleaseResult;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementRepository;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementService;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStartRequest;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import de.metas.pos.repository.model.I_C_POS_InvoiceCardSettlement;
import de.metas.pos.rest_api.json.JsonCashJournalSummary;
import de.metas.pos.rest_api.json.JsonContext;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.SelectionSize;
import de.metas.user.UserId;
import de.metas.util.Services;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
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
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_AD_User_NotificationGroup;
import org.compiere.util.DB;
import org.compiere.util.Env;

import javax.annotation.Nullable;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import java.util.stream.Stream;

import static de.metas.cucumber.stepdefs.StepDefConstants.CLIENT_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.ORG_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Step definitions for POS invoice card settlements: wiring a POS terminal to the in-process SumUp API stub
 * ({@link SumUpApiStub}), programming the stub, and asserting on settlements, their SumUp transactions and
 * the checkouts the stub received.
 */
@RequiredArgsConstructor
public class POS_InvoiceCardSettlement_StepDef
{
	private static final String STUB_API_KEY = "stub-api-key";
	private static final String STUB_MERCHANT_CODE = "STUBMERCHANT";
	private static final ImmutableSet<POSInvoiceCardSettlementStatus> STATUSES_WAITING_FOR_BOOKING = ImmutableSet.of(
			POSInvoiceCardSettlementStatus.PENDING,
			POSInvoiceCardSettlementStatus.UNKNOWN,
			POSInvoiceCardSettlementStatus.ABANDONED,
			POSInvoiceCardSettlementStatus.APPROVED);

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	@NonNull private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	@NonNull private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@NonNull private final C_POS_StepDefData posTable;
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final C_POS_InvoiceCardSettlement_StepDefData settlementTable;
	@NonNull private final C_Payment_StepDefData paymentTable;
	@NonNull private final AD_Role_StepDefData roleTable;
	@NonNull private final AD_Process_Run_StepDef processRunStepDef;

	// the transaction of "another user" that holds an invoice or settlement row lock; released by a step or, at the latest, after the scenario
	@Nullable private String rowLockHolderTrxName;
	// accounting schemas whose automatic period control a scenario changed, with the original Period_OpenFuture; restored
	// after the scenario, because all scenarios share them
	private final List<I_C_AcctSchema> changedAcctSchemas = new ArrayList<>();
	private final List<Integer> originalPeriodOpenFuture = new ArrayList<>();

	// outcomes of the last concurrent start; a step-def instance lives for one scenario
	private final List<StartOutcome> concurrentStartOutcomes = new ArrayList<>();

	// a start whose checkout the stub holds, see startSettlementInBackground
	@Nullable private ExecutorService backgroundStartExecutor;
	@Nullable private Future<POSInvoiceCardSettlement> backgroundStart;

	@Nullable private POSInvoiceCardSettlementCheckResult lastCheckResult;
	@Nullable private POSInvoiceCardSettlementReleaseResult lastReleaseResult;
	@Nullable private String lastBackOfficeProcessSummary;

	// test-only trigger that makes every SUMUP_Transaction insert fail; see failSumUpTransactionInsert
	private static final String SUMUP_TRX_INSERT_FAILURE = "cucumber_sumup_transaction_insert_fails";
	private boolean isSumUpTransactionInsertFailing;

	// resolved lazily: this class is instantiated for the @Before hook, i.e. before metasfresh is started
	@NonNull private final SpringContextHolder.Lazy<SumUpService> sumUpService = SpringContextHolder.lazyBean(SumUpService.class);
	@NonNull private final SpringContextHolder.Lazy<SumUpTransactionRepository> sumUpTransactionRepository = SpringContextHolder.lazyBean(SumUpTransactionRepository.class);
	@NonNull private final SpringContextHolder.Lazy<SumUpConfigRepository> sumUpConfigRepository = SpringContextHolder.lazyBean(SumUpConfigRepository.class);
	@NonNull private final SpringContextHolder.Lazy<POSInvoiceCardSettlementRepository> settlementRepository = SpringContextHolder.lazyBean(POSInvoiceCardSettlementRepository.class);
	@NonNull private final SpringContextHolder.Lazy<POSInvoiceCardSettlementService> settlementService = SpringContextHolder.lazyBean(POSInvoiceCardSettlementService.class);
	@NonNull private final SpringContextHolder.Lazy<POSInvoiceCardSettlementBooking> settlementBooking = SpringContextHolder.lazyBean(POSInvoiceCardSettlementBooking.class);
	@NonNull private final SpringContextHolder.Lazy<POSService> posService = SpringContextHolder.lazyBean(POSService.class);
	@NonNull private final SpringContextHolder.Lazy<CurrencyRepository> currencyRepository = SpringContextHolder.lazyBean(CurrencyRepository.class);

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
		sumUpConfigRepository.get().updateById(sumUpConfigId, config -> config.toBuilder()
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

		final POSInvoiceCardSettlement settlement = settlementRepository.get().create(
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
	 * {@link SumUpService#cardReaderCheckout}, with a posRef naming the settlement, as the till does. Returns only after
	 * the SumUp events the checkout fired were handled (see {@link SumUpEventsTestProbe#waitUntilFiredEventsAreHandled}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_POS_StepDefData (via the settlement's terminal), C_POS_InvoiceCardSettlement_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When a SumUp card checkout is sent for card settlement settlement1
	 * </pre>
	 */
	@And("^a SumUp card checkout is sent for card settlement (\\S+)$")
	public void sendCheckout(@NonNull final String settlementIdentifier) throws InterruptedException
	{
		final POSInvoiceCardSettlement settlement = loadSettlement(settlementIdentifier);
		final I_C_POS posRecord = InterfaceWrapperHelper.load(settlement.getPosTerminalId(), I_C_POS.class);
		final SumUpConfigId sumUpConfigId = SumUpConfigId.ofRepoId(posRecord.getSUMUP_Config_ID());
		final SumUpConfig sumUpConfig = sumUpService.get().getConfig(sumUpConfigId);

		final Amount amount = Amount.of(
				settlement.getAmount().toBigDecimal(),
				currencyDAO.getCurrencyCodeById(settlement.getAmount().getCurrencyId()));

		sumUpService.get().cardReaderCheckout(SumUpCardReaderCheckoutRequest.builder()
				.configId(sumUpConfigId)
				.cardReaderId(sumUpConfig.getDefaultCardReaderExternalIdNotNull())
				.amount(amount)
				.description("card settlement " + settlement.getId().getRepoId())
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(CLIENT_ID, ORG_ID))
				.posRef(SumUpPOSRef.builder()
						.posInvoiceCardSettlementId(settlement.getId().getRepoId())
						.build())
				.build());
		SumUpEventsTestProbe.waitUntilFiredEventsAreHandled();
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
		SumUpApiStub.setNextCheckoutBehavior(toCheckoutBehavior(behavior));
	}

	private static SumUpApiStub.CheckoutBehavior toCheckoutBehavior(@NonNull final String behavior)
	{
		switch (behavior)
		{
			case "SUCCESS":
				return SumUpApiStub.CheckoutBehavior.SUCCESS;
			case "HTTP 4xx":
				return SumUpApiStub.CheckoutBehavior.HTTP_4XX;
			case "HTTP 5xx":
				return SumUpApiStub.CheckoutBehavior.HTTP_5XX;
			case "CONNECTION_RESET":
				return SumUpApiStub.CheckoutBehavior.CONNECTION_RESET;
			default:
				throw new IllegalArgumentException("Unknown checkout behavior: " + behavior);
		}
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
	 * And SumUp API stub reports transaction of card settlement settlement1 as REFUNDED refunded 16.07
	 * </pre>
	 */
	@And("^SumUp API stub reports transaction of card settlement (\\S+) as (PENDING|SUCCESSFUL|FAILED|CANCELLED|REFUNDED)(?: refunded (\\S+))?$")
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
	 * Runs one poller cycle, or the given number of cycles one after the other: updates the PENDING SumUp transactions
	 * from the stub, then calls every {@link SumUpPollerCycleListener} (the card settlement sweep) in an empty context,
	 * as the poller's own thread has no login context. SumUp status events are handled asynchronously; each cycle
	 * returns only after every event it fired was handled (see {@link SumUpEventsTestProbe#waitUntilFiredEventsAreHandled}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And metasfresh polls SumUp
	 * And metasfresh polls SumUp 4 times
	 * </pre>
	 */
	@And("^metasfresh polls SumUp(?: (\\d+) times)?$")
	public void pollSumUp(@Nullable final String times) throws InterruptedException
	{
		final int cycles = times != null ? Integer.parseInt(times) : 1;
		for (int i = 0; i < cycles; i++)
		{
			sumUpService.get().bulkUpdatePendingTransactions(false);
			try (final IAutoCloseable ignored = Env.switchContext(Env.newTemporaryCtx()))
			{
				SpringContextHolder.instance.getBeansOfType(SumUpPollerCycleListener.class)
						.forEach(SumUpPollerCycleListener::afterPollCycle);
			}
			SumUpEventsTestProbe.waitUntilFiredEventsAreHandled();
		}
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
		sumUpService.get().updateTransactionFromRemote(trx.getClientTransactionId());
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
	 * Asserts the settlement's status and, optionally, its {@code IsAbandoned} flag.
	 *
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
	 * Asserts the settlement's {@code IsAbandoned} flag.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then card settlement settlement1 has isAbandoned true
	 * </pre>
	 */
	@And("^card settlement (\\S+) has isAbandoned (true|false)$")
	public void assertSettlementIsAbandoned(@NonNull final String settlementIdentifier, @NonNull final String expectedIsAbandoned)
	{
		assertThat(loadSettlement(settlementIdentifier).isAbandoned())
				.as("isAbandoned of card settlement %s", settlementIdentifier)
				.isEqualTo(Boolean.parseBoolean(expectedIsAbandoned));
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
	 * registers it under the given identifier. SumUp status events are handled asynchronously; the step returns only
	 * after the new-transaction event the start fired was handled (see {@link SumUpEventsTestProbe#waitUntilFiredEventsAreHandled}),
	 * so that a late handler cannot book the payment during a later step.
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
			@NonNull final String cashierLogin) throws InterruptedException
	{
		final POSInvoiceCardSettlement settlement = settlementService.get().start(toStartRequest(invoiceIdentifier, terminalIdentifier, cashierLogin));
		settlementTable.putOrReplace(
				StepDefDataIdentifier.ofString(settlementIdentifier),
				InterfaceWrapperHelper.load(settlement.getId(), I_C_POS_InvoiceCardSettlement.class));
		SumUpEventsTestProbe.waitUntilFiredEventsAreHandled();
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

		assertThatThrownBy(() -> settlementService.get().start(request))
				.as("card settlement start must be refused")
				.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.getErrorCode()).as("AD_Message").isEqualTo(expectedErrorCode(expectedAdMessage)));

		assertThat(countSettlements(request.getInvoiceId())).as("card settlements of the invoice after the refused start").isEqualTo(countBefore);
	}

	/**
	 * Two threads start a card settlement of the same invoice at the same moment; the outcomes are asserted by
	 * {@link #assertConcurrentStartOutcome}. Returns after the fired SumUp events are handled.
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
						return settlementService.get().start(request);
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
		SumUpEventsTestProbe.waitUntilFiredEventsAreHandled();
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
		trxManager.runInNewTrx(() -> {
			final POSInvoiceCardSettlement settlement = settlementRepository.get().getByIdForUpdate(id);
			settlementRepository.get().save(settlement.toBuilder()
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
		assertThat(paymentDAO.streamPaymentIdsByBPartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID())).count())
				.as("payments of the invoice's business partner")
				.isZero();
		assertThat(allocationDAO.retrieveOpenAmtInInvoiceCurrency(invoice, true).toBigDecimal())
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

		final POSOpenInvoice openInvoice = posService.get()
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
		final Runnable delivery = newStatusEventDelivery(settlementIdentifier);

		if ("one after the other".equals(mode))
		{
			for (int i = 0; i < times; i++)
			{
				delivery.run();
			}
			return;
		}

		runAtOnce(Collections.nCopies(times, delivery));
	}

	/**
	 * Delivers the status events of two settlements' current SumUp transactions at the same moment, on separate
	 * threads, as when SumUp approves two card payments almost at once.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the SumUp status events of card settlements settlement1 and settlement2 are delivered at once
	 * </pre>
	 */
	@And("^the SumUp status events of card settlements (\\S+) and (\\S+) are delivered at once$")
	public void deliverSumUpStatusEventsOfTwoSettlementsAtOnce(
			@NonNull final String settlementIdentifier1,
			@NonNull final String settlementIdentifier2) throws Exception
	{
		runAtOnce(ImmutableList.of(
				newStatusEventDelivery(settlementIdentifier1),
				newStatusEventDelivery(settlementIdentifier2)));
	}

	/**
	 * @return a delivery of the status event of the settlement's current SumUp transaction to every
	 * {@link SumUpTransactionStatusChangedListener}, in the event's client/org context, as the event bus does
	 */
	@NonNull
	private Runnable newStatusEventDelivery(@NonNull final String settlementIdentifier)
	{
		final SumUpTransactionStatusChangedEvent event = SumUpTransactionStatusChangedEvent.ofNewTransaction(
				getSumUpTransaction(loadSettlement(settlementIdentifier).getId()));
		final Properties eventCtx = Env.newTemporaryCtx();
		Env.setClientId(eventCtx, event.getClientId());
		Env.setOrgId(eventCtx, event.getOrgId());
		return () -> {
			try (final IAutoCloseable ignored = Env.switchContext(eventCtx))
			{
				SpringContextHolder.instance.getBeansOfType(SumUpTransactionStatusChangedListener.class)
						.forEach(listener -> listener.onStatusChanged(event));
			}
		};
	}

	/**
	 * Runs the deliveries on separate threads, all released at the same moment, and waits until all are done.
	 */
	private static void runAtOnce(@NonNull final List<Runnable> deliveries) throws Exception
	{
		final CountDownLatch startSignal = new CountDownLatch(1);
		final ExecutorService executor = Executors.newFixedThreadPool(deliveries.size());
		try
		{
			final List<Future<?>> futures = new ArrayList<>();
			for (final Runnable delivery : deliveries)
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
	 * Asserts that, of two settlements, one has the first status and the other one the second, in either order (e.g.
	 * when two concurrent bookings of the same invoice serialise and it is not known which one finishes first).
	 * <p>
	 * With {@code after not more than Ns of SumUp polls}, it first lets the SumUp poll run (as the poller would) until
	 * neither settlement is still waiting for its booking (PENDING, UNKNOWN, ABANDONED or APPROVED) or the time is up:
	 * a booking that had to wait for the invoice lock longer than the booking lock timeout stays APPROVED and is
	 * completed by the next poll. A booking that already finished is not changed by a poll, so this cannot hide two
	 * bookings that both allocated.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then of card settlements settlement1 and settlement2, one has status SUCCESSFUL and the other one status CREDIT_REFUND_PENDING
	 * Then after not more than 30s of SumUp polls, of card settlements settlement1 and settlement2, one has status SUCCESSFUL and the other one status CREDIT_REFUND_PENDING
	 * </pre>
	 */
	@And("^(?:after not more than (\\d+)s of SumUp polls, )?of card settlements (\\S+) and (\\S+), one has status ([A-Z_]+) and the other one status ([A-Z_]+)$")
	public void assertStatusesInEitherOrder(
			@Nullable final String timeoutSec,
			@NonNull final String settlementIdentifier1,
			@NonNull final String settlementIdentifier2,
			@NonNull final String expectedStatus1,
			@NonNull final String expectedStatus2) throws InterruptedException
	{
		if (timeoutSec != null)
		{
			final Instant deadline = Instant.now().plusSeconds(Integer.parseInt(timeoutSec));
			while (isWaitingForBooking(settlementIdentifier1, settlementIdentifier2) && Instant.now().isBefore(deadline))
			{
				pollSumUp(null);
				Thread.sleep(500);
			}
		}

		final List<POSInvoiceCardSettlementStatus> actualStatuses = ImmutableList.of(
				loadSettlement(settlementIdentifier1).getStatus(),
				loadSettlement(settlementIdentifier2).getStatus());
		assertThat(actualStatuses)
				.as("statuses of card settlements %s and %s", settlementIdentifier1, settlementIdentifier2)
				.containsExactlyInAnyOrder(parseStatus(expectedStatus1), parseStatus(expectedStatus2));
	}

	private boolean isWaitingForBooking(@NonNull final String... settlementIdentifiers)
	{
		return Arrays.stream(settlementIdentifiers)
				.anyMatch(identifier -> STATUSES_WAITING_FOR_BOOKING.contains(loadSettlement(identifier).getStatus()));
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
		assertThat(settlementService.get().getAllocatedAmount(loadSettlement(settlementIdentifier)).toBigDecimal())
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
		settlementBooking.get()
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
				paymentDAO.getById(settlement.getPaymentId()));
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
		assertThat(paymentDAO.streamPaymentIdsByBPartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID())).count())
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
	 * Asserts the description of the one card payment line of the terminal's open cash journal that references the
	 * settlement. In the expected text, {@code {DocumentNo}} stands for the document number of the given invoice (the
	 * invoices of a feature get their document numbers from the sequence, so the number is not known in advance).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_POS_StepDefData, C_POS_InvoiceCardSettlement_StepDefData, C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the card payment line of card settlement settlement1 in the cash journal of POS terminal till has description 'Rechnung {DocumentNo} (Karte)' of invoice invoice1
	 * </pre>
	 */
	@And("^the card payment line of card settlement (\\S+) in the cash journal of POS terminal (\\S+) has description '(.*)' of invoice (\\S+)$")
	public void assertCardPaymentLineDescription(
			@NonNull final String settlementIdentifier,
			@NonNull final String terminalIdentifier,
			@NonNull final String expectedDescriptionPattern,
			@NonNull final String invoiceIdentifier)
	{
		final POSInvoiceCardSettlementId settlementId = settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier));
		final I_C_Invoice invoice = invoiceTable.get(StepDefDataIdentifier.ofString(invoiceIdentifier));
		InterfaceWrapperHelper.refresh(invoice);
		final String expectedDescription = expectedDescriptionPattern.replace("{DocumentNo}", invoice.getDocumentNo());

		final List<POSCashJournalLine> lines = getCurrentCashJournal(terminalIdentifier).getLines()
				.stream()
				.filter(line -> line.getType() == POSCashJournalLineType.CARD_PAYMENT)
				.filter(line -> settlementId.equals(line.getPosInvoiceCardSettlementId()))
				.collect(ImmutableList.toImmutableList());
		assertThat(lines).as("card payment lines referencing card settlement %s", settlementIdentifier).hasSize(1);
		assertThat(lines.get(0).getDescription()).as("description of the card payment line of card settlement %s", settlementIdentifier).isEqualTo(expectedDescription);
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
						.currencyRepository(currencyRepository.get())
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
		return posService.get().getCurrentCashJournal(posTerminalId)
				.orElseThrow(() -> new AdempiereException("No open cash journal for POS terminal " + terminalIdentifier));
	}


	/**
	 * Asserts that the settlement's card payment was created in the client and organization of the POS terminal (and not
	 * in the context of whatever thread booked it).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_POS_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the payment of card settlement settlement1 belongs to the client and organization of POS terminal till
	 * </pre>
	 */
	@And("^the payment of card settlement (\\S+) belongs to the client and organization of POS terminal (\\S+)$")
	public void assertPaymentClientAndOrg(@NonNull final String settlementIdentifier, @NonNull final String terminalIdentifier)
	{
		final POSInvoiceCardSettlement settlement = loadSettlement(settlementIdentifier);
		assertThat(settlement.getPaymentId()).as("C_Payment_ID of card settlement %s", settlementIdentifier).isNotNull();
		final I_C_Payment payment = InterfaceWrapperHelper.load(settlement.getPaymentId(), I_C_Payment.class);
		final I_C_POS posRecord = posTable.get(StepDefDataIdentifier.ofString(terminalIdentifier));
		InterfaceWrapperHelper.refresh(posRecord);

		assertThat(payment.getAD_Client_ID()).as("AD_Client_ID of the card payment").isEqualTo(posRecord.getAD_Client_ID());
		assertThat(payment.getAD_Org_ID()).as("AD_Org_ID of the card payment").isEqualTo(posRecord.getAD_Org_ID());
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And SumUp API stub answers the status lookup again
	 * </pre>
	 */
	@And("^SumUp API stub answers the status lookup again$")
	public void stubAnswersStatusLookupAgain()
	{
		SumUpApiStub.setFailStatusLookup(false);
	}

	/**
	 * The stub does not answer the next checkout until {@code SumUp API stub answers the held checkout with ...}, so the
	 * send of a settlement started meanwhile stays in flight.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And SumUp API stub holds the next checkout
	 * </pre>
	 */
	@And("^SumUp API stub holds the next checkout$")
	public void stubHoldsNextCheckout()
	{
		SumUpApiStub.holdNextCheckout();
	}

	/**
	 * Starts a card settlement as the till does ({@link POSInvoiceCardSettlementService#start}), but on a separate thread,
	 * because its checkout is held by the stub; returns as soon as the settlement exists (status PENDING, send in flight)
	 * and registers it under the given identifier. The start completes with
	 * {@code SumUp API stub answers the held checkout with ...}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_POS_StepDefData, C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When card settlement settlement1 is being started for invoice invoice1 at POS terminal till by cashier metasfresh
	 * </pre>
	 */
	@And("^card settlement (\\S+) is being started for invoice (\\S+) at POS terminal (\\S+) by cashier (\\S+)$")
	public void startSettlementInBackground(
			@NonNull final String settlementIdentifier,
			@NonNull final String invoiceIdentifier,
			@NonNull final String terminalIdentifier,
			@NonNull final String cashierLogin) throws InterruptedException
	{
		assertThat(backgroundStart).as("a background start is already running").isNull();
		final POSInvoiceCardSettlementStartRequest request = toStartRequest(invoiceIdentifier, terminalIdentifier, cashierLogin);
		final long countBefore = countSettlements(request.getInvoiceId());

		final Properties ctx = Env.getCtx();
		backgroundStartExecutor = Executors.newSingleThreadExecutor();
		backgroundStart = backgroundStartExecutor.submit(() -> {
			try (final IAutoCloseable ignored = Env.switchContext(ctx))
			{
				return settlementService.get().start(request);
			}
		});

		final long deadline = System.currentTimeMillis() + 30_000;
		while (countSettlements(request.getInvoiceId()) <= countBefore)
		{
			assertThat(System.currentTimeMillis()).as("the started card settlement was not created within 30 s").isLessThan(deadline);
			assertThat(backgroundStart.isDone()).as("the card settlement start ended before the settlement was created").isFalse();
			Thread.sleep(100);
		}

		final I_C_POS_InvoiceCardSettlement record = queryBL.createQueryBuilder(I_C_POS_InvoiceCardSettlement.class)
				.addEqualsFilter(I_C_POS_InvoiceCardSettlement.COLUMNNAME_C_Invoice_ID, request.getInvoiceId())
				.orderByDescending(I_C_POS_InvoiceCardSettlement.COLUMNNAME_C_POS_InvoiceCardSettlement_ID)
				.create()
				.first(I_C_POS_InvoiceCardSettlement.class);
		settlementTable.putOrReplace(StepDefDataIdentifier.ofString(settlementIdentifier), record);
	}

	/**
	 * Answers the checkout the stub holds, then waits until the card settlement start that sent it has applied the
	 * answer and the SumUp events it fired were handled.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When SumUp API stub answers the held checkout with HTTP 4xx
	 * </pre>
	 */
	@And("^SumUp API stub answers the held checkout with (SUCCESS|HTTP 4xx|HTTP 5xx|CONNECTION_RESET)$")
	public void stubAnswersHeldCheckout(@NonNull final String behavior) throws Exception
	{
		final Future<POSInvoiceCardSettlement> start = backgroundStart;
		assertThat(start).as("no card settlement is being started").isNotNull();

		SumUpApiStub.answerHeldCheckout(toCheckoutBehavior(behavior));
		try
		{
			start.get(120, TimeUnit.SECONDS);
		}
		finally
		{
			stopBackgroundStart();
		}
		SumUpEventsTestProbe.waitUntilFiredEventsAreHandled();
	}

	/**
	 * Checks the settlement as the till does ({@link POSInvoiceCardSettlementService#check}); the result is asserted by
	 * {@link #assertCheckResult}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When card settlement settlement1 is checked
	 * </pre>
	 */
	@And("^card settlement (\\S+) is checked$")
	public void checkSettlement(@NonNull final String settlementIdentifier) throws InterruptedException
	{
		lastCheckResult = settlementService.get().check(settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier)));
		SumUpEventsTestProbe.waitUntilFiredEventsAreHandled();
	}

	/**
	 * Asserts the result of the last {@code card settlement ... is checked}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the check of card settlement settlement1 returned status PENDING with checkFailed true
	 * </pre>
	 */
	@And("^the check of card settlement (\\S+) returned status ([A-Z_]+) with checkFailed (true|false)$")
	public void assertCheckResult(
			@NonNull final String settlementIdentifier,
			@NonNull final String expectedStatus,
			@NonNull final String expectedCheckFailed)
	{
		final POSInvoiceCardSettlementCheckResult result = lastCheckResult;
		assertThat(result).as("result of the last check").isNotNull();
		assertThat(result.getSettlement().getId()).as("checked card settlement").isEqualTo(settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier)));
		assertThat(result.getSettlement().getStatus()).as("status returned by the check").isEqualTo(parseStatus(expectedStatus));
		assertThat(result.isCheckFailed()).as("checkFailed returned by the check").isEqualTo(Boolean.parseBoolean(expectedCheckFailed));
	}

	/**
	 * The cashier releases the settlement as the till does, first step ({@link POSInvoiceCardSettlementService#release}
	 * without confirmation); the result is asserted by the {@code the release of card settlement ...} steps.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When card settlement settlement1 is released by cashier metasfresh
	 * </pre>
	 */
	@And("^card settlement (\\S+) is released by cashier (\\S+)$")
	public void releaseSettlement(@NonNull final String settlementIdentifier, @NonNull final String cashierLogin) throws InterruptedException
	{
		lastReleaseResult = settlementService.get().release(POSInvoiceCardSettlementReleaseRequest.builder()
				.id(settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier)))
				.cashierId(StepDefUtil.getUserIdByLogin(cashierLogin))
				.confirmNotCharged(false)
				.build());
		SumUpEventsTestProbe.waitUntilFiredEventsAreHandled();
	}

	/**
	 * The cashier confirms the release prompt of the last release: sends the confirmation with the status the prompt
	 * showed (the status returned by the last release) as expected status.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the release of card settlement settlement1 is confirmed by cashier metasfresh
	 * </pre>
	 */
	@And("^the release of card settlement (\\S+) is confirmed by cashier (\\S+)$")
	public void confirmRelease(@NonNull final String settlementIdentifier, @NonNull final String cashierLogin) throws InterruptedException
	{
		final POSInvoiceCardSettlementReleaseResult prompt = lastReleaseResult;
		assertThat(prompt).as("result of the last release").isNotNull();
		assertThat(prompt.isConfirmationRequired()).as("the last release asked for a confirmation").isTrue();

		lastReleaseResult = settlementService.get().release(POSInvoiceCardSettlementReleaseRequest.builder()
				.id(settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier)))
				.cashierId(StepDefUtil.getUserIdByLogin(cashierLogin))
				.confirmNotCharged(true)
				.expectedStatus(prompt.getSettlement().getStatus())
				.build());
		SumUpEventsTestProbe.waitUntilFiredEventsAreHandled();
	}

	/**
	 * Asserts that the last release (or its confirmation) was not refused and returned the given status and flags.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the release of card settlement settlement1 returned status PENDING with confirmationRequired true
	 * Then the release of card settlement settlement1 returned status PENDING with confirmationRequired true and checkFailed true
	 * </pre>
	 */
	@And("^the release of card settlement (\\S+) returned status ([A-Z_]+) with confirmationRequired (true|false)(?: and checkFailed (true|false))?$")
	public void assertReleaseResult(
			@NonNull final String settlementIdentifier,
			@NonNull final String expectedStatus,
			@NonNull final String expectedConfirmationRequired,
			@Nullable final String expectedCheckFailed)
	{
		final POSInvoiceCardSettlementReleaseResult result = assertLastReleaseResultOf(settlementIdentifier);
		assertThat(result.getRefusedMessageKey()).as("refusal of the release").isNull();
		assertThat(result.getSettlement().getStatus()).as("status returned by the release").isEqualTo(parseStatus(expectedStatus));
		assertThat(result.isConfirmationRequired()).as("confirmationRequired returned by the release").isEqualTo(Boolean.parseBoolean(expectedConfirmationRequired));
		if (expectedCheckFailed != null)
		{
			assertThat(result.isCheckFailed()).as("checkFailed returned by the release").isEqualTo(Boolean.parseBoolean(expectedCheckFailed));
		}
	}

	/**
	 * Asserts that the last release (or its confirmation) was refused with the given AD_Message, without asking for a
	 * confirmation; optionally that the message names the given number of seconds.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the release of card settlement settlement1 is refused with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleaseNotYetAllowed' for 150 seconds
	 * Then the release of card settlement settlement1 is refused with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleaseRefusedApproved'
	 * </pre>
	 */
	@And("^the release of card settlement (\\S+) is refused with AD_Message '(.*)'(?: for (\\d+) seconds)?$")
	public void assertReleaseRefused(
			@NonNull final String settlementIdentifier,
			@NonNull final String expectedAdMessage,
			@Nullable final String expectedSeconds)
	{
		final POSInvoiceCardSettlementReleaseResult result = assertLastReleaseResultOf(settlementIdentifier);
		assertThat(result.getRefusedMessageKey()).as("refusal of the release").isEqualTo(AdMessageKey.of(expectedAdMessage));
		assertThat(result.getRefusedMessage()).as("refusal text of the release").isNotNull();
		assertThat(result.isConfirmationRequired()).as("confirmationRequired of a refused release").isFalse();
		if (expectedSeconds != null)
		{
			assertThat(result.getRefusedMessage().translate(Env.getADLanguageOrBaseLanguage())).as("refusal text of the release").contains(expectedSeconds);
		}
	}

	private POSInvoiceCardSettlementReleaseResult assertLastReleaseResultOf(@NonNull final String settlementIdentifier)
	{
		final POSInvoiceCardSettlementReleaseResult result = lastReleaseResult;
		assertThat(result).as("result of the last release").isNotNull();
		assertThat(result.getSettlement().getId()).as("released card settlement").isEqualTo(settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier)));
		return result;
	}

	/**
	 * Asserts how many terminate requests the stub received, optionally that each was for the given reader.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the SumUp stub received 1 terminate on reader reader-till
	 * Then the SumUp stub received 0 terminates
	 * </pre>
	 */
	@And("^the SumUp stub received (\\d+) terminates?(?: on reader (\\S+))?$")
	public void assertStubReceivedTerminates(final int expectedCount, @Nullable final String expectedReader)
	{
		final List<String> readerIds = SumUpApiStub.getReceivedTerminateReaderIds();
		assertThat(readerIds).as("terminate requests received by the SumUp stub").hasSize(expectedCount);
		if (expectedReader != null)
		{
			assertThat(readerIds).as("readers of the terminate requests").allMatch(expectedReader::equals);
		}
	}

	/**
	 * Counts the AD_Issue rows tagged with the settlement whose error code is the given AD_Message's. (That the issue is
	 * written in the settlement's client and organization is pinned by a unit test: here the scenario runs in the same
	 * client and organization as the settlement.)
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then card settlement settlement1 has 1 AD_Issue with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleasedAsAbandoned'
	 * </pre>
	 */
	@And("^card settlement (\\S+) has (\\d+) AD_Issues? with AD_Message '(.*)'$")
	public void assertSettlementIssuesWithMessage(
			@NonNull final String settlementIdentifier,
			final int expectedCount,
			@NonNull final String adMessage)
	{
		final TableRecordReference recordRef = settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier)).toRecordRef();
		final int actualCount = queryBL.createQueryBuilder(I_AD_Issue.class)
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_ErrorCode, expectedErrorCode(adMessage))
				.create()
				.count();
		assertThat(actualCount).as("AD_Issues of card settlement %s with AD_Message %s", settlementIdentifier, adMessage).isEqualTo(expectedCount);
	}

	/**
	 * Every INSERT into {@code SUMUP_Transaction} fails with a database error (a test-only trigger), as when the database
	 * fails right after SumUp accepted a checkout. Removed after the scenario ({@link #dropSumUpTransactionInsertFailure()}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And saving a SumUp transaction fails with a database error
	 * </pre>
	 */
	@And("^saving a SumUp transaction fails with a database error$")
	public void failSumUpTransactionInsert()
	{
		isSumUpTransactionInsertFailing = true;
		DB.executeUpdateAndThrowExceptionOnFail(
				"CREATE OR REPLACE FUNCTION " + SUMUP_TRX_INSERT_FAILURE + "() RETURNS trigger LANGUAGE plpgsql AS $$"
						+ " BEGIN RAISE EXCEPTION 'cucumber: SUMUP_Transaction cannot be saved'; END; $$",
				ITrx.TRXNAME_None);
		DB.executeUpdateAndThrowExceptionOnFail("DROP TRIGGER IF EXISTS " + SUMUP_TRX_INSERT_FAILURE + " ON SUMUP_Transaction", ITrx.TRXNAME_None);
		DB.executeUpdateAndThrowExceptionOnFail(
				"CREATE TRIGGER " + SUMUP_TRX_INSERT_FAILURE + " BEFORE INSERT ON SUMUP_Transaction FOR EACH ROW EXECUTE PROCEDURE " + SUMUP_TRX_INSERT_FAILURE + "()",
				ITrx.TRXNAME_None);
	}

	/**
	 * Removes the failure of {@link #failSumUpTransactionInsert()}, if the scenario set it up: the table is shared by all
	 * scenarios.
	 */
	@After
	public void dropSumUpTransactionInsertFailure()
	{
		if (!isSumUpTransactionInsertFailing)
		{
			return;
		}
		isSumUpTransactionInsertFailing = false;
		DB.executeUpdateAndThrowExceptionOnFail("DROP TRIGGER IF EXISTS " + SUMUP_TRX_INSERT_FAILURE + " ON SUMUP_Transaction", ITrx.TRXNAME_None);
		DB.executeUpdateAndThrowExceptionOnFail("DROP FUNCTION IF EXISTS " + SUMUP_TRX_INSERT_FAILURE + "()", ITrx.TRXNAME_None);
	}

	/**
	 * Asserts that the settlement's AD_Issue names the {@code client_transaction_id} that the SumUp stub returned for its
	 * last accepted checkout, so that back office can find the charge in the SumUp dashboard although metasfresh has no
	 * SumUp transaction for it.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the AD_Issue of card settlement settlement1 names the client transaction id of the last checkout the SumUp stub accepted
	 * </pre>
	 */
	@And("^the AD_Issue of card settlement (\\S+) names the client transaction id of the last checkout the SumUp stub accepted$")
	public void assertIssueNamesClientTransactionId(@NonNull final String settlementIdentifier)
	{
		final List<SumUpApiStub.ReceivedCheckout> checkouts = SumUpApiStub.getReceivedCheckouts();
		assertThat(checkouts).as("checkouts received by the SumUp stub").isNotEmpty();
		final String clientTransactionId = checkouts.get(checkouts.size() - 1).getClientTransactionId();
		assertThat(clientTransactionId).as("client_transaction_id of the last checkout (accepted by the stub)").isNotNull();

		final TableRecordReference recordRef = settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier)).toRecordRef();
		final List<I_AD_Issue> issues = queryBL.createQueryBuilder(I_AD_Issue.class)
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
				.create()
				.list();
		assertThat(issues).as("AD_Issues of card settlement %s", settlementIdentifier).hasSize(1);
		assertThat(issues.get(0).getIssueSummary()).as("summary of the AD_Issue of card settlement %s", settlementIdentifier).contains(clientTransactionId);
	}

	/**
	 * Ends a background start the scenario left running, releasing its held checkout.
	 */
	@After
	public void stopBackgroundStart()
	{
		final ExecutorService executor = backgroundStartExecutor;
		backgroundStart = null;
		backgroundStartExecutor = null;
		if (executor != null)
		{
			SumUpApiStub.releaseHeldCheckout();
			executor.shutdown();
		}
	}

	/**
	 * Releases what the scenario left behind for the next ones: an invoice or settlement row lock still held by "another user", and
	 * a closed accounting period (the accounting schema is shared by all scenarios).
	 */
	@After
	public void releaseHeldRowLockAndReopenPeriod()
	{
		try
		{
			releaseHeldRowLock();
		}
		finally
		{
			restoreChangedAcctSchemas();
		}
	}

	private void restoreChangedAcctSchemas()
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

	/**
	 * Locks the invoice row ({@code FOR UPDATE}) in a separate, still open transaction, as a concurrent user or process
	 * would; a booking that needs the invoice lock meanwhile waits until its lock timeout. The lock is held until
	 * {@link #releaseHeldRowLock()} (step or after-scenario hook).
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
		assertThat(rowLockHolderTrxName).as("a row lock of another transaction is already held").isNull();
		final InvoiceId invoiceId = invoiceTable.getId(StepDefDataIdentifier.ofString(invoiceIdentifier));
		final String trxName = trxManager.createTrxName("cucumber-invoice-lock-holder", true);
		rowLockHolderTrxName = trxName;
		DB.getSQLValueEx(trxName, "SELECT C_Invoice_ID FROM C_Invoice WHERE C_Invoice_ID=? FOR UPDATE", invoiceId.getRepoId());
	}

	/**
	 * Locks the settlement row ({@code FOR UPDATE}) in a separate, still open transaction, as a concurrent booking would;
	 * a release confirmation that needs the settlement lock meanwhile waits until its lock timeout. The lock is held until
	 * {@link #releaseHeldRowLock()} (step or after-scenario hook).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_POS_InvoiceCardSettlement_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the row of card settlement settlement1 is locked by another transaction
	 * </pre>
	 */
	@And("^the row of card settlement (\\S+) is locked by another transaction$")
	public void lockSettlementInOtherTransaction(@NonNull final String settlementIdentifier)
	{
		assertThat(rowLockHolderTrxName).as("a row lock of another transaction is already held").isNull();
		final POSInvoiceCardSettlementId id = settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier));
		final String trxName = trxManager.createTrxName("cucumber-settlement-lock-holder", true);
		rowLockHolderTrxName = trxName;
		DB.getSQLValueEx(trxName, "SELECT C_POS_InvoiceCardSettlement_ID FROM C_POS_InvoiceCardSettlement WHERE C_POS_InvoiceCardSettlement_ID=? FOR UPDATE", id.getRepoId());
	}

	/**
	 * Ends the transaction that holds the invoice or settlement row lock (see {@link #lockInvoiceInOtherTransaction},
	 * {@link #lockSettlementInOtherTransaction}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the other transaction releases its row lock
	 * </pre>
	 */
	@And("^the other transaction releases its row lock$")
	public void releaseHeldRowLock()
	{
		final String trxName = rowLockHolderTrxName;
		if (trxName == null)
		{
			return;
		}
		rowLockHolderTrxName = null;

		final ITrx trx = trxManager.getTrxOrNull(trxName);
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

	/**
	 * Reopens the accounting period that {@link #closePeriodForToday()} closed, as back office would after fixing the
	 * cause of a booking error.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the accounting period is reopened
	 * </pre>
	 */
	@And("^the accounting period is reopened$")
	public void reopenPeriod()
	{
		assertThat(changedAcctSchemas).as("accounting schemas closed by this scenario").isNotEmpty();
		restoreChangedAcctSchemas();
	}

	/**
	 * Asserts whether the process's precondition offers it for the settlement, i.e. whether back office sees the process
	 * on that row of the card settlement window.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And process C_POS_InvoiceCardSettlement_RetryBooking is available for card settlement settlement1
	 * And process C_POS_InvoiceCardSettlement_MarkRefunded is not available for card settlement settlement1
	 * </pre>
	 */
	@And("^process (\\S+) is (available|not available) for card settlement (\\S+)$")
	public void assertProcessAvailable(
			@NonNull final String processValue,
			@NonNull final String availability,
			@NonNull final String settlementIdentifier)
	{
		final ProcessPreconditionsResolution resolution = checkPreconditions(processValue, settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier)));
		assertThat(resolution.isRejected())
				.as("precondition of process %s for card settlement %s rejects (reason: %s)", processValue, settlementIdentifier, resolution.getRejectReason())
				.isEqualTo("not available".equals(availability));
	}

	/**
	 * Back office starts the process on the settlement's row of the card settlement window: the process must be offered
	 * for it (precondition), and then runs like a user's process (client context + {@code WebUI} role). Fails if the
	 * process reports an error.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When back office runs process C_POS_InvoiceCardSettlement_RetryBooking on card settlement settlement1
	 * When back office runs process C_POS_InvoiceCardSettlement_MarkRefunded on card settlement settlement1 with note 'refunded'
	 * </pre>
	 */
	@And("^back office runs process (\\S+) on card settlement (\\S+)(?: with note '(.*)')?$")
	public void runBackOfficeProcess(
			@NonNull final String processValue,
			@NonNull final String settlementIdentifier,
			@Nullable final String note)
	{
		runBackOfficeProcessAndGetError(processValue, settlementIdentifier, note).ifPresent(error -> {
			throw AdempiereException.wrapIfNeeded(error);
		});
	}

	/**
	 * As {@link #runBackOfficeProcess}, but the process must refuse with the given AD_Message.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When back office running process C_POS_InvoiceCardSettlement_MarkResolvedManually on card settlement settlement1 with note 'resolved' is refused with AD_Message 'de.metas.pos.InvoiceCardSettlement.MarkResolvedRefused'
	 * When back office running process C_POS_InvoiceCardSettlement_RetryBooking on card settlement settlement1 is refused with AD_Message 'de.metas.pos.InvoiceCardSettlement.RetryBookingNotCompleted'
	 * </pre>
	 */
	@And("^back office running process (\\S+) on card settlement (\\S+)(?: with note '(.*)')? is refused with AD_Message '(.*)'$")
	public void runBackOfficeProcessRefused(
			@NonNull final String processValue,
			@NonNull final String settlementIdentifier,
			@Nullable final String note,
			@NonNull final String expectedAdMessage)
	{
		final Throwable error = runBackOfficeProcessAndGetError(processValue, settlementIdentifier, note)
				.orElseThrow(() -> new AssertionError("process " + processValue + " must be refused"));
		assertThat(error).isInstanceOfSatisfying(AdempiereException.class,
				ex -> assertThat(ex.getErrorCode()).as("AD_Message").isEqualTo(expectedErrorCode(expectedAdMessage)));
	}

	/**
	 * As {@link #runBackOfficeProcess}, but the process must refuse because the note is empty.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When back office running process C_POS_InvoiceCardSettlement_MarkRefunded on card settlement settlement1 with note '' is refused because the note is missing
	 * </pre>
	 */
	@And("^back office running process (\\S+) on card settlement (\\S+) with note '(.*)' is refused because the note is missing$")
	public void runBackOfficeProcessRefusedForMissingNote(
			@NonNull final String processValue,
			@NonNull final String settlementIdentifier,
			@NonNull final String note)
	{
		final Throwable error = runBackOfficeProcessAndGetError(processValue, settlementIdentifier, note)
				.orElseThrow(() -> new AssertionError("process " + processValue + " must be refused"));
		assertThat(Throwables.getCausalChain(error))
				.as("refusal of process %s for a missing note: %s", processValue, error)
				.anyMatch(FillMandatoryException.class::isInstance);
	}

	/**
	 * Asserts the message the last back-office process run reported to back office (its summary), in any of the
	 * AD_Message's languages.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the back-office process reports AD_Message 'de.metas.pos.InvoiceCardSettlement.RetryBookingWillRetry'
	 * </pre>
	 */
	@And("^the back-office process reports AD_Message '(.*)'$")
	public void assertBackOfficeProcessSummary(@NonNull final String expectedAdMessage)
	{
		final ITranslatableString expected = msgBL.getTranslatableMsgText(AdMessageKey.of(expectedAdMessage));
		assertThat(lastBackOfficeProcessSummary)
				.as("summary of the last back-office process")
				.isIn(expected.translate("de_DE"), expected.translate("en_US"), expected.getDefaultValue());
	}

	private Optional<Throwable> runBackOfficeProcessAndGetError(
			@NonNull final String processValue,
			@NonNull final String settlementIdentifier,
			@Nullable final String note)
	{
		final POSInvoiceCardSettlementId id = settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier));
		final ProcessPreconditionsResolution resolution = checkPreconditions(processValue, id);
		assertThat(resolution.isRejected())
				.as("precondition of process %s for card settlement %s rejects (reason: %s)", processValue, settlementIdentifier, resolution.getRejectReason())
				.isFalse();

		final ProcessInfo.ProcessInfoBuilder processInfo = processRunStepDef.newProcessInfoBuilder(adProcessDAO.retrieveProcessIdByValue(processValue))
				.setRecord(id.toRecordRef());
		if (note != null)
		{
			processInfo.addParameter(I_C_POS_InvoiceCardSettlement.COLUMNNAME_Note, note);
		}
		final ProcessExecutionResult result = processInfo.buildAndPrepareExecution()
				.switchContextWhenRunning()
				.executeSync()
				.getResult();
		lastBackOfficeProcessSummary = result.getSummary();
		if (!result.isError())
		{
			return Optional.empty();
		}
		return Optional.of(result.getThrowable() != null ? result.getThrowable() : new AdempiereException(result.getSummary()));
	}

	private ProcessPreconditionsResolution checkPreconditions(@NonNull final String processValue, @NonNull final POSInvoiceCardSettlementId id)
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByValue(processValue);
		assertThat(processId).as("AD_Process with Value=%s", processValue).isNotNull();
		final String classname = adProcessDAO.getById(processId).getClassname();
		final IProcessPrecondition process;
		try
		{
			process = (IProcessPrecondition)Class.forName(classname).newInstance();
		}
		catch (final ReflectiveOperationException ex)
		{
			throw new AdempiereException("Cannot instantiate process " + classname, ex);
		}
		return process.checkPreconditionsApplicable(new SingleSettlementPreconditionsContext(id));
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then card settlement settlement1 has note 'refunded'
	 * </pre>
	 */
	@And("^card settlement (\\S+) has note '(.*)'$")
	public void assertNote(@NonNull final String settlementIdentifier, @NonNull final String expectedNote)
	{
		assertThat(loadSettlement(settlementIdentifier).getNote())
				.as("Note of card settlement %s", settlementIdentifier)
				.isEqualTo(expectedNote);
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then card settlement settlement1 has no note
	 * </pre>
	 */
	@And("^card settlement (\\S+) has no note$")
	public void assertNoNote(@NonNull final String settlementIdentifier)
	{
		assertThat(loadSettlement(settlementIdentifier).getNote())
				.as("Note of card settlement %s", settlementIdentifier)
				.isNull();
	}

	/**
	 * Asserts whether the settlement is among the {@code C_POS_InvoiceCardSettlement} rows with the given status, as a
	 * proxy for the card settlement window filtered by that status (e.g. CREDIT_REFUND_PENDING, the refund worklist);
	 * it queries the table, not the window's filter.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then card settlement settlement1 is listed under status CREDIT_REFUND_PENDING
	 * Then card settlement settlement1 is not listed under status CREDIT_REFUND_PENDING
	 * </pre>
	 */
	@And("^card settlement (\\S+) is (listed|not listed) under status ([A-Z_]+)$")
	public void assertListedUnderStatus(
			@NonNull final String settlementIdentifier,
			@NonNull final String listed,
			@NonNull final String status)
	{
		final POSInvoiceCardSettlementId id = settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier));
		final List<POSInvoiceCardSettlementId> idsWithStatus = queryBL.createQueryBuilder(I_C_POS_InvoiceCardSettlement.class)
				.addEqualsFilter(I_C_POS_InvoiceCardSettlement.COLUMNNAME_Status, parseStatus(status).getCode())
				.create()
				.listIds(POSInvoiceCardSettlementId::ofRepoId)
				.asList();
		assertThat(idsWithStatus.contains(id))
				.as("card settlement %s is among the settlements with status %s", settlementIdentifier, status)
				.isEqualTo("listed".equals(listed));
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then invoice invoice1 is not blocked by a card settlement
	 * </pre>
	 */
	@And("^invoice (\\S+) is not blocked by a card settlement$")
	public void assertInvoiceNotBlocked(@NonNull final String invoiceIdentifier)
	{
		assertThat(settlementService.get().getBlockingByInvoiceId(invoiceTable.getId(StepDefDataIdentifier.ofString(invoiceIdentifier))))
				.as("blocking card settlement of invoice %s", invoiceIdentifier)
				.isEmpty();
	}

	/**
	 * The single row of the card settlement window a back-office process is started on, in the shape the processes'
	 * preconditions read it. Implements only what they read (the table, the single selected record); every other method
	 * throws, so a precondition that starts reading something else fails loudly here.
	 */
	@RequiredArgsConstructor
	private static class SingleSettlementPreconditionsContext implements IProcessPreconditionsContext
	{
		@NonNull private final POSInvoiceCardSettlementId id;

		@Override
		public AdWindowId getAdWindowId() {throw notImplemented("getAdWindowId");}

		@Override
		public AdTabId getAdTabId() {throw notImplemented("getAdTabId");}

		@Override
		public String getTableName() {return I_C_POS_InvoiceCardSettlement.Table_Name;}

		@Override
		public <T> T getSelectedModel(final Class<T> modelClass) {throw notImplemented("getSelectedModel");}

		@Override
		public <T> List<T> getSelectedModels(final Class<T> modelClass) {throw notImplemented("getSelectedModels");}

		@NonNull
		@Override
		public <T> Stream<T> streamSelectedModels(@NonNull final Class<T> modelClass) {throw notImplemented("streamSelectedModels");}

		@Override
		public int getSingleSelectedRecordId() {return id.getRepoId();}

		@Override
		public SelectionSize getSelectionSize() {return SelectionSize.ofSize(1);}

		@Override
		public <T> IQueryFilter<T> getQueryFilter(@NonNull final Class<T> recordClass) {throw notImplemented("getQueryFilter");}

		private static UnsupportedOperationException notImplemented(@NonNull final String methodName)
		{
			return new UnsupportedOperationException(SingleSettlementPreconditionsContext.class.getSimpleName() + " does not implement " + methodName);
		}
	}

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

	private String expectedErrorCode(@NonNull final String adMessage)
	{
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
		return settlementRepository.get().getById(settlementTable.getId(StepDefDataIdentifier.ofString(settlementIdentifier)));
	}

	/**
	 * Reloads the transaction from the DB, found by the settlement reference.
	 */
	private SumUpTransaction getSumUpTransaction(@NonNull final POSInvoiceCardSettlementId settlementId)
	{
		final List<SumUpTransaction> trxs = sumUpTransactionRepository.get().list(SumUpTransactionQuery.builder()
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
