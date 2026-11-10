package de.metas.pos.invoice_settlement.card;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.repository.SumUpTransactionRepository;
import de.metas.pos.POSCashJournalService;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalService;
import de.metas.pos.invoice_settlement.POSInvoiceSettlementService;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Issue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class POSInvoiceCardSettlementBookingTest
{
	/**
	 * The statuses step A books from (PENDING, UNKNOWN, ABANDONED).
	 */
	private static final ImmutableSet<POSInvoiceCardSettlementStatus> STEP_A_STATUSES = ImmutableSet.of(
			POSInvoiceCardSettlementStatus.PENDING,
			POSInvoiceCardSettlementStatus.UNKNOWN,
			POSInvoiceCardSettlementStatus.ABANDONED);

	private POSInvoiceCardSettlementRepository repository;
	private POSInvoiceCardSettlementBooking booking;

	private IQueryBL queryBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		queryBL = Services.get(IQueryBL.class);
		// the in-memory test DB cannot run "SET LOCAL lock_timeout"; 0 = no lock timeout
		Services.get(ISysConfigBL.class).setValue("de.metas.pos.InvoiceCardSettlement.BookingLockTimeoutMillis", 0, ClientId.SYSTEM, OrgId.ANY);

		repository = new POSInvoiceCardSettlementRepository();
		booking = new POSInvoiceCardSettlementBooking(
				repository,
				mock(POSInvoiceSettlementService.class),
				mock(POSTerminalService.class),
				mock(POSCashJournalService.class),
				mock(SumUpTransactionRepository.class),
				mock(SumUpService.class));
	}

	private POSInvoiceCardSettlement createSettlement(final POSInvoiceCardSettlementStatus status)
	{
		final POSInvoiceCardSettlement settlement = repository.create(
				OrgId.ofRepoId(1),
				POSTerminalId.ofRepoId(1),
				InvoiceId.ofRepoId(100),
				BPartnerId.ofRepoId(2),
				UserId.ofRepoId(3),
				Money.of(new BigDecimal("16.07"), CurrencyId.ofRepoId(102)));
		return repository.save(settlement.toBuilder().status(status).build());
	}

	private int countIssues(final POSInvoiceCardSettlementId id)
	{
		final TableRecordReference recordRef = id.toRecordRef();
		return queryBL.createQueryBuilder(I_AD_Issue.class)
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
				.create()
				.count();
	}

	private static RuntimeException lockTimeoutError()
	{
		return new AdempiereException("booking failed", new SQLException("canceling statement due to lock timeout", "55P03"));
	}

	@Test
	void handleBookingError_statusAlreadyMovedOn_leavesSettlementUnchanged()
	{
		// e.g. a concurrent delivery of the same approval booked the settlement meanwhile
		final POSInvoiceCardSettlement settlement = createSettlement(POSInvoiceCardSettlementStatus.SUCCESSFUL);

		booking.handleBookingError(settlement.getId(), new AdempiereException("payment could not be created"), STEP_A_STATUSES);

		final POSInvoiceCardSettlement after = repository.getById(settlement.getId());
		assertThat(after.getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.SUCCESSFUL);
		assertThat(after.getBookingAttempts()).isZero();
		assertThat(after.getErrorMsg()).isNull();
		assertThat(countIssues(settlement.getId())).isZero();
	}

	@Test
	void handleBookingError_nonRetryable_setsBookingErrorWithOneIssue()
	{
		final POSInvoiceCardSettlement settlement = createSettlement(POSInvoiceCardSettlementStatus.PENDING);

		booking.handleBookingError(settlement.getId(), new AdempiereException("payment could not be created"), STEP_A_STATUSES);

		final POSInvoiceCardSettlement after = repository.getById(settlement.getId());
		assertThat(after.getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.BOOKING_ERROR);
		assertThat(after.getBookingAttempts()).isEqualTo(1);
		assertThat(after.getErrorMsg()).contains("payment could not be created");
		assertThat(countIssues(settlement.getId())).isEqualTo(1);
	}

	@Test
	void handleBookingError_retryable_countsAttemptsUntilTheLimit()
	{
		final POSInvoiceCardSettlement settlement = createSettlement(POSInvoiceCardSettlementStatus.PENDING);

		for (int attempt = 1; attempt < POSInvoiceCardSettlementBooking.MAX_BOOKING_ATTEMPTS; attempt++)
		{
			booking.handleBookingError(settlement.getId(), lockTimeoutError(), STEP_A_STATUSES);

			final POSInvoiceCardSettlement after = repository.getById(settlement.getId());
			assertThat(after.getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.PENDING);
			assertThat(after.getBookingAttempts()).isEqualTo(attempt);
			assertThat(countIssues(settlement.getId())).isZero();
		}

		booking.handleBookingError(settlement.getId(), lockTimeoutError(), STEP_A_STATUSES);

		final POSInvoiceCardSettlement after = repository.getById(settlement.getId());
		assertThat(after.getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.BOOKING_ERROR);
		assertThat(after.getBookingAttempts()).isEqualTo(POSInvoiceCardSettlementBooking.MAX_BOOKING_ATTEMPTS);
		assertThat(countIssues(settlement.getId())).isEqualTo(1);
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"55P03", // lock_not_available, e.g. lock_timeout expired
			"40001", // serialization_failure
			"40P01", // deadlock_detected
			"08006", // connection_failure
			"08003" // connection_does_not_exist
	})
	void isRetryable_transientSQLState(final String sqlState)
	{
		final RuntimeException bookingError = new AdempiereException("booking failed", new RuntimeException(new SQLException("transient", sqlState)));
		assertThat(POSInvoiceCardSettlementBooking.isRetryable(bookingError)).isTrue();
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"23505", // unique_violation
			"23503", // foreign_key_violation
			"57014" // query_canceled (statement timeout): may have done work, not retried
	})
	void isRetryable_otherSQLState(final String sqlState)
	{
		final RuntimeException bookingError = new AdempiereException("booking failed", new SQLException("not transient", sqlState));
		assertThat(POSInvoiceCardSettlementBooking.isRetryable(bookingError)).isFalse();
	}

	@Test
	void isRetryable_noSQLException()
	{
		assertThat(POSInvoiceCardSettlementBooking.isRetryable(new AdempiereException("payment could not be created"))).isFalse();
	}
}
