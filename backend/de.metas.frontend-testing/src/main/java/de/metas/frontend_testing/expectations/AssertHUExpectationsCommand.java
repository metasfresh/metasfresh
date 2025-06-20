package de.metas.frontend_testing.expectations;

import com.google.common.collect.ImmutableList;
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
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

import java.util.HashMap;
import java.util.Map;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;
import static de.metas.frontend_testing.expectations.assertions.Assertions.fail;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softly;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softlyPutContext;

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

		softly(() -> {
			softlyPutContext("huId", context.describeId(huId));
			softlyPutContext("expectations", expectations);
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
		final Quantity expectedQty = expectedQtyStr.toQuantity();
		softlyPutContext("expectedQty", expectedQty);

		assertThat(actualStorage.getQty()).as("Qty").isEqualTo(expectedQty);
	}

	private HuId getHUIdByMatcherString(@NonNull final String matcherStr)
	{
		final HuId huId = context.getOptionalId(Identifier.ofString(matcherStr), HuId.class).orElse(null);
		if (huId != null)
		{
			return huId;
		}

		return services.getHuIdByQRCode(HUQRCode.fromGlobalQRCodeJsonString(matcherStr));
	}

	private void assertAttributes(@NonNull final Map<String, String> expectations, @NonNull final HuId huId)
	{
		if (expectations.isEmpty())
		{
			return;
		}

		final ImmutableAttributeSet actualAttributes = services.getAttributes(huId);

		softly(() -> {
			softlyPutContext("huId", context.describeId(huId));
			softlyPutContext("expectations", expectations);
			softlyPutContext("actualAttributes", actualAttributes);

			expectations.forEach((attributeCodeStr, expectedValueStr) -> {
				final AttributeCode attributeCode = AttributeCode.ofString(attributeCodeStr);
				softlyPutContext("attributeCode", attributeCode);

				final String actualValueStr = actualAttributes.getValueAsString(attributeCode);
				assertThat(actualValueStr).as("Attribute " + attributeCode).isEqualTo(expectedValueStr);
			});
		});

	}

}
