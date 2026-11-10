package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import de.metas.pos.repository.model.I_C_POS_Payment;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Issue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * The late SumUp approval of a deleted or released card payment: the payment stays as it is, nothing is booked,
 * and one AD_Issue on the payment tells the back office.
 */
class POSOrdersServiceTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final String RELEASED_TRX = "released-trx";

	private POSOrdersRepository ordersRepository;
	private POSOrderProcessingServices posOrderProcessingServices;
	private POSOrdersService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		POSTestMessages.createLateApprovalNotBooked();

		ordersRepository = new POSOrdersRepository(new POSOrderEventDispatcher(Optional.empty()));
		posOrderProcessingServices = mock(POSOrderProcessingServices.class);
		service = new POSOrdersService(mock(POSTerminalService.class), ordersRepository, mock(CurrencyRepository.class), posOrderProcessingServices);
	}

	private POSOrderAndPaymentId newOrderWithPayment(final POSPayment.POSPaymentBuilder payment)
	{
		final POSOrder order = ordersRepository.createOrUpdateByExternalId(
				POSOrderExternalId.ofString(UUID.randomUUID().toString()),
				externalId -> POSOrder.builder()
						.externalId(externalId)
						.status(POSOrderStatus.WaitingPayment)
						.salesOrderDocTypeId(DocTypeId.ofRepoId(1))
						.pricingSystemAndListId(PricingSystemAndListId.ofRepoIds(1, 1))
						.cashbookId(BankAccountId.ofRepoId(1))
						.cashierId(UserId.ofRepoId(100))
						.date(Instant.parse("2026-11-10T10:00:00Z"))
						.shipToCustomerAndLocationId(BPartnerLocationAndCaptureId.ofRepoId(1, 1))
						.shipFrom(POSShipFrom.builder()
								.warehouseId(WarehouseId.ofRepoId(1))
								.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(0, 1))
								.countryId(CountryId.ofRepoId(101))
								.build())
						.currencyId(EUR)
						.posTerminalId(POSTerminalId.ofRepoId(1))
						.payments(ImmutableList.of(payment
								.externalId(POSPaymentExternalId.ofString(UUID.randomUUID().toString()))
								.paymentMethod(POSPaymentMethod.CARD)
								.amount(Money.of(new BigDecimal("10.00"), EUR))
								.build()))
						.build(),
				o -> {});
		return POSOrderAndPaymentId.of(order.getLocalIdNotNull(), order.getAllPayments().get(0).getLocalIdNotNull());
	}

	private static POSPaymentProcessResponse response(final POSPaymentProcessingStatus status, final String clientTransactionId)
	{
		return POSPaymentProcessResponse.builder()
				.status(status)
				.config(POSTerminalPaymentProcessorConfig.builder().type(POSPaymentProcessorType.SumUp).build())
				.transactionId("ext-1")
				.clientTransactionId(clientTransactionId)
				.build();
	}

	private List<I_AD_Issue> retrieveIssues(final POSOrderAndPaymentId ids)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Issue.class)
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_Table_ID, Services.get(IADTableDAO.class).retrieveTableId(I_C_POS_Payment.Table_Name))
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_Record_ID, ids.getPaymentId().getRepoId())
				.create()
				.list();
	}

	private POSPaymentProcessingStatus statusOf(final POSOrderAndPaymentId ids)
	{
		return ordersRepository.getById(ids.getOrderId()).getAllPayments().get(0).getPaymentProcessingStatus();
	}

	@Test
	void lateApprovalOfDeletedPayment_staysDeleted_notBooked_oneIssueOnThePayment()
	{
		final POSOrderAndPaymentId ids = newOrderWithPayment(POSPayment.builder().paymentProcessingStatus(POSPaymentProcessingStatus.DELETED));

		service.updatePaymentStatusFromRemoteAndTryCompleteOrder(ids, response(POSPaymentProcessingStatus.SUCCESSFUL, "trx-1"));

		assertThat(statusOf(ids)).isEqualTo(POSPaymentProcessingStatus.DELETED);
		final List<I_AD_Issue> issues = retrieveIssues(ids);
		assertThat(issues).hasSize(1);
		assertThat(issues.get(0).getErrorCode()).isEqualTo("de.metas.pos.POSPayment.LateApprovalNotBooked");
		verifyNoInteractions(posOrderProcessingServices);
	}

	@Test
	void lateApprovalOfReleasedCheckout_staysFailed_notBooked_oneIssueOnThePayment()
	{
		final POSOrderAndPaymentId ids = newOrderWithPayment(POSPayment.builder()
				.paymentProcessingStatus(POSPaymentProcessingStatus.FAILED)
				.releasedSumUpClientTransactionId(RELEASED_TRX));

		service.updatePaymentStatusFromRemoteAndTryCompleteOrder(ids, response(POSPaymentProcessingStatus.SUCCESSFUL, RELEASED_TRX));

		assertThat(statusOf(ids)).isEqualTo(POSPaymentProcessingStatus.FAILED);
		final List<I_AD_Issue> issues = retrieveIssues(ids);
		assertThat(issues).hasSize(1);
		assertThat(issues.get(0).getErrorCode()).isEqualTo("de.metas.pos.POSPayment.LateApprovalNotBooked");
		verifyNoInteractions(posOrderProcessingServices);
	}

	@Test
	void nonSuccessfulResultOfReleasedCheckout_staysFailed_noIssue()
	{
		final POSOrderAndPaymentId ids = newOrderWithPayment(POSPayment.builder()
				.paymentProcessingStatus(POSPaymentProcessingStatus.FAILED)
				.releasedSumUpClientTransactionId(RELEASED_TRX));

		service.updatePaymentStatusFromRemoteAndTryCompleteOrder(ids, response(POSPaymentProcessingStatus.CANCELLED, RELEASED_TRX));

		assertThat(statusOf(ids)).isEqualTo(POSPaymentProcessingStatus.FAILED);
		assertThat(retrieveIssues(ids)).isEmpty();
	}

	@Test
	void lateApprovalOfPlainFailedPayment_isApplied_noIssue()
	{
		final POSOrderAndPaymentId ids = newOrderWithPayment(POSPayment.builder().paymentProcessingStatus(POSPaymentProcessingStatus.FAILED));

		service.updatePaymentStatusFromRemoteAndTryCompleteOrder(ids, response(POSPaymentProcessingStatus.SUCCESSFUL, "trx-1"));

		assertThat(statusOf(ids)).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
		assertThat(retrieveIssues(ids)).isEmpty();
	}
}
