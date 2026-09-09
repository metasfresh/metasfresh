package de.metas.frontend_testing.expectations;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.frontend_testing.expectations.request.JsonHUExpectation;
import de.metas.frontend_testing.expectations.request.QtyAndUOMString;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.generichumodel.HUType;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_InOut;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;
import static de.metas.frontend_testing.expectations.assertions.Assertions.fail;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softly;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softlyPutContext;

@Builder
class AssertHUExpectationsCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(AssertHUExpectationsCommand.class);
	@NonNull private final AssertExpectationsCommandServices services;
	@NonNull private final MasterdataContext context;
	@NonNull final Map<String, JsonHUExpectation> expectations;

	private final HashMap<HuId, I_M_HU> husCache = new HashMap<>();

	/**
	 * How long to poll for the async-generated shipment-line assignment before failing.
	 * Shipment generation runs in a background workpackage (PickingShipmentService uses
	 * {@code waitForShipments(false)}), so the {@code M_HU_Assignment} linking the packed HU to its
	 * shipment line appears shortly after the picking job completes; once committed the poll sees it
	 * within ~1s. This ceiling only needs to cover the workpackage's run time and MUST stay below the
	 * Playwright per-test timeout (120s) so a genuine "shipment never generated" failure surfaces here
	 * with a clear message rather than as an opaque Playwright test-timeout.
	 */
	private static final Duration SHIPPED_ASSERTION_TIMEOUT = Duration.ofSeconds(60);

	void execute()
	{
		expectations.forEach(this::assertHU);
	}

	private I_M_HU getHUById(@NonNull final HuId huId) {return husCache.computeIfAbsent(huId, services::getHUById);}

	private void addHUsToCache(@NonNull final Collection<I_M_HU> hus) {hus.forEach(this::addHUToCache);}

	private void addHUToCache(@NonNull final I_M_HU hu) {husCache.put(HuId.ofRepoId(hu.getM_HU_ID()), hu);}

	private void assertHU(@NonNull String huMatcherStr, @NonNull final JsonHUExpectation expectation)
	{
		softly(() -> {
			softlyPutContext("huMatcherStr", huMatcherStr);
			softlyPutContext("expectation", expectation);

			final HuId huId = getHUIdByMatcherString(huMatcherStr);
			softlyPutContext("huId", context.describeId(huId));

			assertHU(huId, expectation);
		});
	}

	private void assertHU(@NonNull final HuId huId, final @NotNull JsonHUExpectation expectation)
	{
		if (expectation.getWarehouse() != null || expectation.getLocator() != null)
		{
			final I_M_HU hu = getHUById(huId);
			final LocatorId actualLocatorId = IHandlingUnitsBL.extractLocatorId(hu);

			if (expectation.getWarehouse() != null)
			{
				final WarehouseId expectedWarehouseId = context.getId(expectation.getWarehouse(), WarehouseId.class);
				assertThat(actualLocatorId.getWarehouseId()).as("Warehouse").isEqualTo(expectedWarehouseId);
			}
			if (expectation.getLocator() != null)
			{
				final LocatorId expectedLocatorId = context.getId(expectation.getLocator(), LocatorId.class);
				assertThat(actualLocatorId).as("Locator").isEqualTo(expectedLocatorId);
			}
		}

		if (expectation.getBpartner() != null)
		{
			final I_M_HU hu = getHUById(huId);
			final BPartnerId expectedBPartnerId = expectation.getBpartner().isNullPlaceholder()
					? null
					: context.getId(expectation.getBpartner(), BPartnerId.class);
			assertThat(BPartnerId.ofRepoIdOrNull(hu.getC_BPartner_ID()))
					.as("C_BPartner_ID")
					.isEqualTo(expectedBPartnerId);
		}

		if (expectation.getBpartnerLocation() != null)
		{
			final I_M_HU hu = getHUById(huId);
			final BPartnerLocationId expectedBPLocationId = expectation.getBpartnerLocation().isNullPlaceholder()
					? null
					: context.getBPartnerLocationId(expectation.getBpartnerLocation());
			assertThat(BPartnerLocationId.ofRepoIdOrNull(hu.getC_BPartner_ID(), hu.getC_BPartner_Location_ID()))
					.as("C_BPartner_Location_ID")
					.isEqualTo(expectedBPLocationId);
		}

		if (expectation.getHuStatus() != null)
		{
			final I_M_HU hu = getHUById(huId);
			assertThat(hu.getHUStatus()).as("HUStatus").isEqualTo(expectation.getHuStatus());
		}

		if (expectation.getBpartner() != null)
		{
			final I_M_HU hu = getHUById(huId);
			final BPartnerId expectedBPartnerId = context.getId(expectation.getBpartner(), BPartnerId.class);
			assertThat(BPartnerId.ofRepoIdOrNull(hu.getC_BPartner_ID())).as("HU consignee C_BPartner_ID").isEqualTo(expectedBPartnerId);
		}

		if (Boolean.TRUE.equals(expectation.getConsigneeCleared()))
		{
			final I_M_HU hu = getHUById(huId);
			assertThat(hu.getC_BPartner_ID() <= 0).as("HU consignee cleared (C_BPartner_ID <= 0)").isEqualTo(true);
		}

		if (expectation.getHuType() != null)
		{
			final I_M_HU hu = getHUById(huId);
			final HUType expectedHuType = HUType.ofCode(expectation.getHuType());
			assertThat(services.getHUUnitType(hu)).as("HUType").isEqualTo(expectedHuType);
		}

		if (expectation.getIsAggregatedTU() != null)
		{
			final I_M_HU hu = getHUById(huId);
			assertThat(services.handlingUnitsBL.isAggregateHU(hu))
					.as("IsAggregatedTU")
					.isEqualTo(expectation.getIsAggregatedTU());
		}

		if (expectation.getQtyTUs() != null)
		{
			final I_M_HU hu = getHUById(huId);
			assertThat(services.handlingUnitsBL.isTransportUnitOrAggregate(hu))
					.as("isTransportUnitOrAggregate")
					.isEqualTo(true);
			assertThat(computeQtyTUs(hu))
					.as("QtyTUs")
					.isEqualTo(expectation.getQtyTUs());
		}

		if (expectation.getStorages() != null)
		{
			assertHUStorages(expectation.getStorages(), huId);
		}

		if (expectation.getAttributes() != null)
		{
			HUAttributeAssertions.assertAttributes(services, expectation.getAttributes(), huId);
		}

		if (expectation.getTus() != null)
		{
			final I_M_HU hu = getHUById(huId);
			assertThat(services.getHUUnitType(hu)).as("HUUnitType").isEqualTo(HUType.LoadLogistiqueUnit);
			assertTUs(expectation.getTus(), huId);
		}
		if (expectation.getCus() != null)
		{
			assertCUs(expectation.getCus(), huId);
		}

		if (expectation.getShipped() != null)
		{
			assertShipped(huId, expectation.getShipped());
		}
	}

	private QtyTU computeQtyTUs(final I_M_HU hu)
	{
		if (services.handlingUnitsBL.isLoadingUnit(hu))
		{
			QtyTU qtyTUsTotal = QtyTU.ZERO;
			final List<I_M_HU> tus = services.getIncludedHUs(HuId.ofRepoId(hu.getM_HU_ID()));
			addHUsToCache(tus);
			for (final I_M_HU tu : tus)
			{
				final QtyTU qtyTUs = services.handlingUnitsBL.getTUsCount(tu);
				qtyTUsTotal = qtyTUsTotal.add(qtyTUs);
			}
			return qtyTUsTotal;
		}
		else
		{
			return services.handlingUnitsBL.getTUsCount(hu);
		}
	}

	private void assertHUStorages(@NonNull final Map<String, String> expectations, @NonNull final HuId huId)
	{
		if (expectations.isEmpty())
		{
			return;
		}

		final HashMap<ProductId, IHUProductStorage> actualStorages = services.getHUStorage(huId)
				.streamProductStorages()
				.collect(GuavaCollectors.toHashMapByKey(IHUProductStorage::getProductId));

		softly(() -> {
			softlyPutContext("expectedStorages", expectations);
			softlyPutContext("actualStorages", ImmutableList.copyOf(actualStorages.values()));

			expectations.forEach((productIdentifierStr, expectedQtyStr) -> {
				final Identifier productIdentifier = Identifier.ofString(productIdentifierStr);
				softlyPutContext("productIdentifier", productIdentifier);

				final ProductId productId = context.getId(productIdentifier, ProductId.class);
				softlyPutContext("productId", productId);

				final IHUProductStorage actualStorage = actualStorages.remove(productId);
				if (actualStorage == null)
				{
					fail("No storage found for product " + context.describeId(productId) + " in HU " + context.describeId(huId));
				}
				else
				{
					assertHUStorage(QtyAndUOMString.parseString(expectedQtyStr), actualStorage);
				}
			});

			assertThat(actualStorages).as("remaining not matched by expectations storages").isEmpty();
		});
	}

	private void assertHUStorage(@NonNull final QtyAndUOMString expectedQtyStr, @NonNull final IHUProductStorage actualStorage)
	{
		assertThat(actualStorage.getQty()).as("Qty").isEqualTo(expectedQtyStr.toQuantity());
	}

	private HuId getHUIdByMatcherString(@NonNull final String matcherStr)
	{
		final HuId huId = context.getOptionalId(Identifier.ofString(matcherStr), HuId.class).orElse(null);
		if (huId != null)
		{
			return huId;
		}

		// Allow raw numeric HU IDs (e.g. returned by the massPrinting/scan REST response in packedHUIds).
		try
		{
			final int numericId = Integer.parseInt(matcherStr.trim());
			return HuId.ofRepoId(numericId);
		}
		catch (final NumberFormatException ignored)
		{
			// not a numeric id — fall through to QR code lookup
		}

		return services.getHuIdByQRCode(HUQRCode.fromGlobalQRCodeJsonString(matcherStr));
	}

	private void assertTUs(@NonNull final List<JsonHUExpectation> expectations, @NonNull final HuId luId)
	{
		final ArrayList<I_M_HU> tus = new ArrayList<>(services.getIncludedHUs(luId));
		tus.sort(Comparator.comparing(I_M_HU::getM_HU_ID)); // make sure we are iterating them in the creation order
		addHUsToCache(tus);

		assertThat(tus).hasSameSize(expectations);

		softly(() -> {
			softlyPutContext("TUs: luId", context.describeId(luId));
			softlyPutContext("TUs: expectations", expectations);
			softlyPutContext("TUs: actual TUs", tus);

			for (int i = 0; i < expectations.size(); i++)
			{
				softlyPutContext("TUs: index", i);

				final JsonHUExpectation expectation = expectations.get(i);
				softlyPutContext("TUs: expectation", expectation);

				final I_M_HU tu = tus.get(i);
				softlyPutContext("TUs: actual TU", tu);

				assertHU(HuId.ofRepoId(tu.getM_HU_ID()), expectation);
			}
		});

	}

	private void assertCUs(@NonNull final List<JsonHUExpectation.CU> expectations, @NonNull final HuId huId)
	{
		final ArrayList<I_M_HU> cus = new ArrayList<>(services.getCUs(huId));
		cus.sort(Comparator.comparing(I_M_HU::getM_HU_ID)); // make sure we are iterating them in the creation order

		assertThat(cus).hasSameSize(expectations);

		softly(() -> {
			softlyPutContext("CUs: huId", context.describeId(huId));
			softlyPutContext("CUs: expectations", expectations);
			softlyPutContext("CUs: actual CUs", cus);

			for (int i = 0; i < expectations.size(); i++)
			{
				softlyPutContext("CUs: index", i);

				final JsonHUExpectation.CU expectation = expectations.get(i);
				softlyPutContext("CUs: expectation", expectation);

				final I_M_HU cu = cus.get(i);
				softlyPutContext("CUs: actual CU", cu);

				assertCU(expectation, cu);
			}
		});
	}

	private void assertCU(final JsonHUExpectation.CU expectation, final I_M_HU cu)
	{
		if (expectation.getQty() != null)
		{
			final IHUProductStorage storage = services.getSingleProductStorage(cu);
			assertThat(storage.getQty()).as("Qty").isEqualTo(expectation.getQty().toQuantity());
		}

		if (expectation.getAttributes() != null)
		{
			HUAttributeAssertions.assertAttributes(services, expectation.getAttributes(), cu);
		}
	}

	/**
	 * Asserts whether the given HU is (or is not) assigned to a sales-shipment line.
	 *
	 * <p>When {@code expectedShipped=true}: polls (up to {@link #SHIPPED_ASSERTION_TIMEOUT}) until
	 * {@link #getSalesShipmentLinesForHU(I_M_HU)} returns at least one line belonging to a
	 * sales shipment ({@code M_InOut.IsSOTrx=Y}).  Shipment assignment is async, so polling is required.
	 *
	 * <p>When {@code expectedShipped=false}: a single check is performed (no polling); asserts
	 * the HU is NOT on any sales-shipment line.
	 */
	private void assertShipped(@NonNull final HuId huId, final boolean expectedShipped)
	{
		final I_M_HU hu = getHUById(huId);

		if (expectedShipped)
		{
			// Poll until the HU appears on a sales-shipment line (assignment is async).
			final Stopwatch stopwatch = Stopwatch.createStarted();
			List<I_M_InOutLine> salesShipmentLines = getSalesShipmentLinesForHU(hu);
			while (salesShipmentLines.isEmpty() && stopwatch.elapsed().compareTo(SHIPPED_ASSERTION_TIMEOUT) < 0)
			{
				logger.info("Waiting for HU {} to appear on a sales-shipment line (elapsed: {})", huId, stopwatch);
				try
				{
					//noinspection BusyWait
					Thread.sleep(1000);
				}
				catch (final InterruptedException e)
				{
					Thread.currentThread().interrupt();
					throw new AdempiereException("Interrupted while waiting for HU " + huId + " to be shipped", e);
				}
				salesShipmentLines = getSalesShipmentLinesForHU(hu);
			}

			if (salesShipmentLines.isEmpty())
			{
				throw new AdempiereException("HU " + huId + " is not assigned to any sales-shipment line after " + stopwatch);
			}
		}
		else
		{
			// shipped=false: assert NOT on any sales-shipment line (single check, no poll needed).
			// Refresh to avoid a stale husCache entry yielding a wrong-clean result.
			InterfaceWrapperHelper.refresh(hu);
			final List<I_M_InOutLine> salesShipmentLines = getSalesShipmentLinesForHU(hu);
			assertThat(salesShipmentLines)
					.as("sales-shipment lines for HU " + huId + " (expected none)")
					.isEmpty();
		}
	}

	/**
	 * Returns all {@link I_M_InOutLine} records that belong to a <em>sales</em> shipment
	 * ({@code M_InOut.IsSOTrx=Y}) and are associated with the given HU.
	 */
	private List<I_M_InOutLine> getSalesShipmentLinesForHU(@NonNull final I_M_HU hu)
	{
		final List<I_M_InOutLine> lines = services.getInOutLinesForHU(hu);

		// Load each distinct M_InOut_ID at most once to avoid redundant DB round-trips
		// in the hot polling loop (up to 60 iterations × N HUs).
		// Use a HashMap (not Collectors.toMap) because load() may return null,
		// and Collectors.toMap throws NPE on null values.
		final Map<Integer, I_M_InOut> inOutById = new HashMap<>();
		lines.stream()
				.map(I_M_InOutLine::getM_InOut_ID)
				.distinct()
				.forEach(id -> inOutById.put(id, InterfaceWrapperHelper.load(id, I_M_InOut.class)));

		return lines.stream()
				.filter(line -> {
					final I_M_InOut inOut = inOutById.get(line.getM_InOut_ID());
					return inOut != null && inOut.isSOTrx();
				})
				.collect(Collectors.toList());
	}

}
