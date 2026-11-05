package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.order.OrderId;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Every committed save stamps the POS order with a strictly increasing version, and the "order changed" event carries the
 * same version as the order returned to the REST caller. A load gets a version taken before reading. Clients use it to drop
 * snapshots which arrive out of order (the websocket events are debounced, so they can arrive after the REST response of
 * a newer save).
 */
class POSOrdersRepositoryVersionTest
{
	private static final POSOrderExternalId EXTERNAL_ID = POSOrderExternalId.ofString("order-1");

	private ITrxManager trxManager;
	private POSOrdersRepository repository;
	private final List<Long> versionsSentToListeners = new ArrayList<>();
	/**
	 * Runs once right after the version of a load was taken; lets a test save "concurrently" between that and the read.
	 */
	private Runnable afterTakingLoadVersion = () -> {};

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		trxManager = Services.get(ITrxManager.class);
		versionsSentToListeners.clear();
		final POSOrderEventListener listener = order -> versionsSentToListeners.add(order.getVersion());
		repository = newRepository(new POSOrderVersionSource()
		{
			@Override
			public long current()
			{
				final long version = super.current();
				final Runnable hook = afterTakingLoadVersion;
				afterTakingLoadVersion = () -> {};
				hook.run();
				return version;
			}
		}, listener);
	}

	private static POSOrdersRepository newRepository(final POSOrderVersionSource versionSource, final POSOrderEventListener listener)
	{
		return new POSOrdersRepository(new POSOrderEventDispatcher(Optional.of(ImmutableList.of(listener))), versionSource);
	}

	@AfterEach
	void afterEach()
	{
		SystemTime.resetTimeSource();
	}

	@Test
	void everySaveStampsAGreaterVersion_andTheEventCarriesTheSameVersion()
	{
		final POSOrder created = repository.createOrUpdateByExternalId(EXTERNAL_ID, this::newOrder, order -> {});
		final POSOrder updated = repository.updateByExternalId(EXTERNAL_ID, order -> {});

		assertThat(created.getVersion()).isNotNull();
		assertThat(updated.getVersion()).isGreaterThan(created.getVersion());
		assertThat(versionsSentToListeners).containsExactly(created.getVersion(), updated.getVersion());
	}

	@Test
	void aLoadedOrderIsNotOlderThanItsLastSave_andTheNextSaveIsNewer()
	{
		SystemTime.setFixedTimeSource("2026-11-05T10:00:00Z");
		final long savedVersion = repository.createOrUpdateByExternalId(EXTERNAL_ID, this::newOrder, order -> {}).getVersion();

		final POSOrder loaded = repository.getById(repository.getIdByExternalId(EXTERNAL_ID));
		final long savedAgainVersion = repository.updateByExternalId(EXTERNAL_ID, order -> {}).getVersion();

		// a delayed event of the earlier save must not be considered newer than what a reload returned
		assertThat(loaded.getVersion()).isGreaterThanOrEqualTo(savedVersion);
		assertThat(savedAgainVersion).isGreaterThan(loaded.getVersion());
	}

	@Test
	void ordersLoadedByQueryHaveAVersionToo()
	{
		final long savedVersion = repository.createOrUpdateByExternalId(EXTERNAL_ID, this::newOrder, order -> {}).getVersion();
		final POSOrderQuery query = POSOrderQuery.builder().posTerminalId(POSTerminalId.ofRepoId(1)).cashierId(UserId.ofRepoId(1)).build();

		assertThat(repository.list(query)).singleElement().satisfies(order -> assertThat(order.getVersion()).isGreaterThanOrEqualTo(savedVersion));
		assertThat(repository.firstOnly(query)).hasValueSatisfying(order -> assertThat(order.getVersion()).isGreaterThanOrEqualTo(savedVersion));
	}

	@Test
	void versionKeepsIncreasing_evenWhenTheClockStandsStill()
	{
		SystemTime.setFixedTimeSource("2026-11-05T10:00:00Z");

		final long v1 = repository.createOrUpdateByExternalId(EXTERNAL_ID, this::newOrder, order -> {}).getVersion();
		final long v2 = repository.updateByExternalId(EXTERNAL_ID, order -> {}).getVersion();

		assertThat(v2).isGreaterThan(v1);
	}

	@Test
	void versionIsAtLeastTheCurrentTimeMillis_soThatVersionsStillIncreaseAfterAServerRestart()
	{
		final Instant now = Instant.parse("2099-01-01T00:00:00Z"); // later than any version handed out so far in this JVM
		SystemTime.setFixedTimeSource(now.toString());

		final long version = repository.createOrUpdateByExternalId(EXTERNAL_ID, this::newOrder, order -> {}).getVersion();

		assertThat(version).isGreaterThanOrEqualTo(now.toEpochMilli());
	}

	@Test
	void theVersionIsStampedAndTheEventSentOnlyAfterCommit()
	{
		final POSOrder created = trxManager.callInThreadInheritedTrx(() -> {
			final POSOrder order = repository.createOrUpdateByExternalId(EXTERNAL_ID, this::newOrder, o -> {});
			// a load running now cannot see this save yet, so the save must not have a version a load could already exceed
			assertThat(order.getVersion()).isNull();
			assertThat(versionsSentToListeners).isEmpty();
			return order;
		});

		assertThat(created.getVersion()).isNotNull();
		assertThat(versionsSentToListeners).containsExactly(created.getVersion());
	}

	@Test
	void aRolledBackSaveGetsNoVersionAndSendsNoEvent()
	{
		final POSOrder[] saved = new POSOrder[1];
		assertThatThrownBy(() -> trxManager.callInThreadInheritedTrx(() -> {
			saved[0] = repository.createOrUpdateByExternalId(EXTERNAL_ID, this::newOrder, order -> {});
			throw new AdempiereException("rollback");
		})).hasMessageContaining("rollback");

		assertThat(saved[0].getVersion()).isNull();
		assertThat(versionsSentToListeners).isEmpty();
	}

	@Test
	void theLoadVersionIsTakenBeforeReading_soASaveDuringTheReadIsNewer()
	{
		repository.createOrUpdateByExternalId(EXTERNAL_ID, this::newOrder, order -> {});
		final long[] concurrentSaveVersion = new long[1];
		afterTakingLoadVersion = () -> concurrentSaveVersion[0] = repository.updateByExternalId(EXTERNAL_ID, order -> order.setSalesOrderId(OrderId.ofRepoId(123))).getVersion();

		final POSOrder loaded = repository.getById(repository.getIdByExternalId(EXTERNAL_ID));

		assertThat(loaded.getSalesOrderId()).isEqualTo(OrderId.ofRepoId(123)); // the read happened after the concurrent save
		assertThat(concurrentSaveVersion[0]).isGreaterThan(loaded.getVersion()); // so the concurrent save's event is not dropped
	}

	@Test
	void afterARestart_versionsAreStillGreater()
	{
		SystemTime.setFixedTimeSource("2026-11-05T10:00:00Z");
		final POSOrderVersionSource beforeRestart = new POSOrderVersionSource();
		long lastVersionBeforeRestart = 0;
		for (int i = 0; i < 100; i++)
		{
			lastVersionBeforeRestart = beforeRestart.next();
		}

		SystemTime.setFixedTimeSource("2026-11-05T10:00:01Z");
		final POSOrderVersionSource afterRestart = new POSOrderVersionSource();

		assertThat(afterRestart.current()).isGreaterThan(lastVersionBeforeRestart);
		assertThat(afterRestart.next()).isGreaterThan(lastVersionBeforeRestart);
	}

	private POSOrder newOrder(final POSOrderExternalId externalId)
	{
		return POSOrder.builder()
				.externalId(externalId)
				.salesOrderDocTypeId(DocTypeId.ofRepoId(1))
				.pricingSystemAndListId(PricingSystemAndListId.ofRepoIds(1, 1))
				.cashbookId(BankAccountId.ofRepoId(1))
				.cashierId(UserId.ofRepoId(1))
				.date(Instant.parse("2026-11-05T10:00:00Z"))
				.shipToCustomerAndLocationId(BPartnerLocationAndCaptureId.ofRepoId(1, 1, 1))
				.shipFrom(POSShipFrom.builder()
						.warehouseId(WarehouseId.ofRepoId(1))
						.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(Env.getClientId(), OrgId.ofRepoId(1)))
						.countryId(CountryId.ofRepoId(1))
						.build())
				.currencyId(CurrencyId.ofRepoId(102))
				.posTerminalId(POSTerminalId.ofRepoId(1))
				.build();
	}
}
