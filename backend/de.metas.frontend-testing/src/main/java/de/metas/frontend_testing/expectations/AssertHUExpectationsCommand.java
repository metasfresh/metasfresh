package de.metas.frontend_testing.expectations;

import de.metas.frontend_testing.expectations.assertions.Assertions;
import de.metas.frontend_testing.expectations.request.JsonHUExpectation;
import de.metas.frontend_testing.expectations.request.QtyAndUOMString;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

import java.util.HashMap;
import java.util.Map;

@Builder
class AssertHUExpectationsCommand
{
	@NonNull private final AssertExpectationsCommandServices services;
	@NonNull private final MasterdataContext context;
	@NonNull final Map<String, JsonHUExpectation> expectations;

	void execute()
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
		Assertions.assertThat(actualStorage.getQty()).as("Qty").isEqualTo(expectedQty);
	}

	private HuId getHUIdByMatcherString(@NonNull final String matcherStr)
	{
		@NonNull final HUQRCode qrCode = HUQRCode.fromGlobalQRCodeJsonString(matcherStr);
		return services.getHuIdByQRCode(qrCode);
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
			Assertions.assertThat(actualValueStr).as("Attribute " + attributeCode).isEqualTo(expectedValueStr);
		});
	}

}
