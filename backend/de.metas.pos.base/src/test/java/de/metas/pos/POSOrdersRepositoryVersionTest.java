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
import de.metas.user.UserId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Every save stamps the POS order with a strictly increasing version, and the "order changed" event carries the same
 * version as the order returned to the REST caller. Clients use it to drop snapshots which arrive out of order (the
 * websocket events are debounced, so they can arrive after the REST response of a newer save).
 */
class POSOrdersRepositoryVersionTest
{
	private static final POSOrderExternalId EXTERNAL_ID = POSOrderExternalId.ofString("order-1");

	private POSOrdersRepository repository;
	private final List<Long> versionsSentToListeners = new ArrayList<>();

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		versionsSentToListeners.clear();
		final POSOrderEventListener listener = order -> versionsSentToListeners.add(order.getVersion());
		repository = new POSOrdersRepository(new POSOrderEventDispatcher(Optional.of(ImmutableList.of(listener))));
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
	void versionIsNotPersisted_aLoadedOrderHasNone()
	{
		repository.createOrUpdateByExternalId(EXTERNAL_ID, this::newOrder, order -> {});

		final POSOrder loaded = repository.getById(repository.getIdByExternalId(EXTERNAL_ID));

		assertThat(loaded.getVersion()).isNull();
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
