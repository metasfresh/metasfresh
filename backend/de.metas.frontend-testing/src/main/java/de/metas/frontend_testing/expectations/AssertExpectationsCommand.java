package de.metas.frontend_testing.expectations;

import com.google.common.collect.ImmutableMap;
import de.metas.frontend_testing.expectations.request.JsonExpectations;
import de.metas.frontend_testing.expectations.request.JsonExpectationsResponse;
import de.metas.frontend_testing.expectations.request.JsonHUExpectation;
import de.metas.frontend_testing.expectations.request.JsonPickingExpectation;
import de.metas.frontend_testing.expectations.request.JsonShipmentScheduleExpectation;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataResponse;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class AssertExpectationsCommand
{
	@NonNull private final de.metas.frontend_testing.expectations.AssertExpectationsCommandServices services;
	@NonNull private final JsonExpectations expectations;

	@NonNull private final MasterdataContext context;

	@Builder
	private AssertExpectationsCommand(
			@NonNull final AssertExpectationsCommandServices services,
			@NonNull final JsonExpectations expectations)
	{
		this.services = services;
		this.expectations = expectations;
		this.context = createContext(expectations);
	}

	private static MasterdataContext createContext(@NonNull final JsonExpectations expectations)
	{
		final MasterdataContext context = new MasterdataContext();

		final JsonCreateMasterdataResponse masterdata = expectations.getMasterdata();
		if (masterdata != null)
		{
			masterdata.getBpartners().forEach((identifierStr, bpartner) -> context.putIdentifier(Identifier.ofString(identifierStr), bpartner.getId()));
			masterdata.getProducts().forEach((identifierStr, product) -> context.putIdentifier(Identifier.ofString(identifierStr), product.getId()));
			masterdata.getHandlingUnits().forEach((identifierStr, handlingUnit) -> context.putIdentifier(Identifier.ofString(identifierStr), HuId.ofObject(handlingUnit.getHuId())));
		}

		return context;
	}

	public JsonExpectationsResponse execute()
	{
		if (expectations.getPickings() != null)
		{
			expectations.getPickings().forEach(this::assertPicking);
		}
		if (expectations.getHus() != null)
		{
			assertHUs(expectations.getHus());
		}
		
		return JsonExpectationsResponse.builder().build();
	}

	private void assertPicking(@NonNull final JsonPickingExpectation pickingExpectation)
	{
		final PickingJobId pickingJobId = context.getOptionalId(pickingExpectation.getPickingJobId(), PickingJobId.class)
				.orElseGet(() -> pickingExpectation.getPickingJobId().toId(PickingJobId.class));

		final PickingJob pickingJob = services.getPickingJobById(pickingJobId);

		if (pickingExpectation.getShipmentSchedules() != null)
		{
			final Collection<I_M_ShipmentSchedule> shipmentSchedules = services.getShipmentSchedulesByIds(pickingJob.getShipmentScheduleIds());
			assertShipmentSchedules(pickingExpectation.getShipmentSchedules(), shipmentSchedules);
		}
	}

	private void assertShipmentSchedules(final List<JsonShipmentScheduleExpectation> expectations, final Collection<I_M_ShipmentSchedule> actuals)
	{
		throw new UnsupportedOperationException("not implemented"); // TODO
	}

	private void assertHUs(@NonNull final List<JsonHUExpectation> expectations)
	{
		expectations.forEach(this::assertHU);
	}

	private void assertHU(@NonNull final JsonHUExpectation expectation)
	{
		final HuId huId = getHUId(expectation.getMatch());

		if (expectation.getStorages() != null)
		{
			assertHUStorages(expectation.getStorages(), huId);
		}
	}

	private void assertHUStorages(@NonNull final List<JsonHUExpectation.Storage> expectations, @NonNull final HuId huId)
	{
		if (expectations.isEmpty())
		{
			return;
		}

		final IHUStorage actualStorages = services.getHUStorage(huId);

		expectations.forEach(expectation -> assertHUStorage(expectation, actualStorages));

		final ImmutableMap<ProductId, JsonHUExpectation.Storage> expectationsByProductId = expectations.stream()
				.collect(ImmutableMap.toImmutableMap(
						expectation -> context.getId(expectation.getProduct(), ProductId.class),
						expectation -> expectation
				));
	}

	private void assertHUStorage(@NonNull final JsonHUExpectation.Storage expectation, @NonNull final IHUStorage actualStorages)
	{
		final ProductId productId = context.getId(expectation.getProduct(), ProductId.class);
		final IHUProductStorage actualStorage = actualStorages.getProductStorage(productId);

		final Quantity expectedQty = expectation.getQty().toQuantity();
		assertEquals("Qty", expectedQty, actualStorage.getQty());
	}

	private HuId getHUId(@NonNull final JsonHUExpectation.Match match)
	{
		if (match.getByQRCode() != null)
		{
			@NonNull final HUQRCode qrCode = HUQRCode.fromGlobalQRCodeJsonString(match.getByQRCode());
			return services.getHuIdByQRCode(qrCode);
		}
		else
		{
			throw new AdempiereException("Don't know how to match HU by " + match);
		}
	}

	@SuppressWarnings("SameParameterValue")
	private <T> void assertEquals(@NonNull final String what, @Nullable final T expected, @Nullable final T actual)
	{
		if (!Objects.equals(expected, actual))
		{
			throw new AdempiereException("Expected " + what + " to be <" + expected + "> but was <" + actual + ">");
		}
	}
}
