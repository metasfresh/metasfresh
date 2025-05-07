package de.metas.frontend_testing.expectations;

import de.metas.frontend_testing.expectations.request.JsonExpectations;
import de.metas.frontend_testing.expectations.request.JsonExpectationsResponse;
import de.metas.frontend_testing.expectations.request.JsonHUExpectation;
import de.metas.frontend_testing.expectations.request.JsonPickingExpectation;
import de.metas.frontend_testing.expectations.request.JsonShipmentScheduleExpectation;
import de.metas.frontend_testing.expectations.request.QtyAndUOMString;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataResponse;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	@SuppressWarnings("unused")
	private void assertShipmentSchedules(final List<JsonShipmentScheduleExpectation> expectations, final Collection<I_M_ShipmentSchedule> actuals)
	{
		throw new UnsupportedOperationException("not implemented"); // TODO
	}

	private void assertHUs(final Map<String, JsonHUExpectation> expectations)
	{
		expectations.forEach(this::assertHU);
	}

	private void assertHU(@NonNull String huMatcherStr, @NonNull final JsonHUExpectation expectation)
	{
		final HuId huId = getHUIdByMatcherString(huMatcherStr);

		if (expectation.getStorages() != null)
		{
			assertHUStorages(expectation.getStorages(), huId);
		}

		if (expectation.getAttributes() != null)
		{
			assertAttributes(expectation.getAttributes(), huId);
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

		expectations.forEach((productIdentifierStr, expectedQtyStr) -> {
			final Identifier productIdentifier = Identifier.ofString(productIdentifierStr);
			final ProductId productId = context.getId(productIdentifier, ProductId.class);
			final IHUProductStorage actualStorage = actualStorages.remove(productId);
			if (actualStorage == null)
			{
				throw new AdempiereException("No storage found for product " + productId + " in HU " + huId);
			}

			assertHUStorage(QtyAndUOMString.parseString(expectedQtyStr), actualStorage);
		});

		if (!actualStorages.isEmpty())
		{
			throw new AdempiereException("Following storages were not expected for " + huId + ": " + actualStorages.values());
		}
	}

	private void assertHUStorage(@NonNull final QtyAndUOMString expectedQtyStr, @NonNull final IHUProductStorage actualStorage)
	{
		final Quantity expectedQty = expectedQtyStr.toQuantity();
		assertEquals("Qty", expectedQty, actualStorage.getQty());
	}

	private HuId getHUIdByMatcherString(@NonNull final String matcherStr)
	{
		@NonNull final HUQRCode qrCode = HUQRCode.fromGlobalQRCodeJsonString(matcherStr);
		return services.getHuIdByQRCode(qrCode);
	}

	@SuppressWarnings("SameParameterValue")
	private <T> void assertEquals(@NonNull final String what, @Nullable final T expected, @Nullable final T actual)
	{
		if (!Objects.equals(expected, actual))
		{
			throw new AdempiereException("Expected " + what + " to be <" + expected + "> but was <" + actual + ">");
		}
	}

	private void assertAttributes(@NonNull final Map<String, String> expectations, @NonNull final HuId huId)
	{
		if (expectations.isEmpty())
		{
			return;
		}

		final ImmutableAttributeSet actualAttributes = services.getAttributes(huId);

		expectations.forEach((attributeCodeStr, expectedValueStr) -> {
			final AttributeCode attributeCode = AttributeCode.ofString(attributeCodeStr);
			final String actualValueStr = actualAttributes.getValueAsString(attributeCode);
			assertEquals("Attribute " + attributeCode, expectedValueStr, actualValueStr);
		});
	}

}
