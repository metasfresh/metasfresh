package de.metas.frontend_testing.expectations;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.frontend_testing.expectations.request.JsonHUExpectation;
import de.metas.frontend_testing.expectations.request.QtyAndUOMString;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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

		if (expectation.getCus() != null)
		{
			assertCUs(expectation.getCus(), huId);
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

		final I_M_HU hu = services.getHUById(huId);
		assertAttributes(expectations, hu);
	}

	private void assertAttributes(@NonNull final Map<String, String> expectations, @NonNull final I_M_HU hu)
	{
		if (expectations.isEmpty())
		{
			return;
		}

		final ImmutableAttributeSet actualAttributes = services.getAttributes(hu);

		softly(() -> {
			final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
			softlyPutContext("huId", context.describeId(huId));
			softlyPutContext("expectations", expectations);
			softlyPutContext("actualAttributes", actualAttributes);

			expectations.forEach((attributeCodeStr, expectedValueStr) -> {
				final AttributeCode attributeCode = AttributeCode.ofString(attributeCodeStr);
				softlyPutContext("attributeCode", attributeCode);

				if (actualAttributes.hasAttribute(attributeCode))
				{
					final AttributeValueType type = actualAttributes.getAttributeValueType(attributeCode);
					switch (type)
					{
						case STRING:
						case LIST:
							assertAttributeValue_String(expectedValueStr, actualAttributes, attributeCode);
							break;
						case NUMBER:
							assertAttributeValue_Number(expectedValueStr, actualAttributes, attributeCode);
							break;
						case DATE:
							assertAttributeValue_Date(expectedValueStr, actualAttributes, attributeCode);
							break;
						default:
							fail("Unknown attribute value type: " + type);
					}
				}
				else if (expectedValueStr != null)
				{
					fail("Expected missing attribute " + attributeCode + " to be <" + expectedValueStr + ">");
				}
			});
		});
	}

	private void assertAttributeValue_String(final String expectedValueStr, final ImmutableAttributeSet actualAttributes, final AttributeCode attributeCode)
	{
		final String actualValueStr = actualAttributes.getValueAsString(attributeCode);
		assertThat(actualValueStr).as("String attribute " + attributeCode).isEqualTo(expectedValueStr);
	}

	private void assertAttributeValue_Number(final String expectedValueStr, final ImmutableAttributeSet actualAttributes, final AttributeCode attributeCode)
	{
		final BigDecimal actualValue = actualAttributes.getValueAsBigDecimal(attributeCode);
		final BigDecimal expectedValue = NumberUtils.asBigDecimal(expectedValueStr);
		assertThat(actualValue).as("Number attribute " + attributeCode).isEqualTo(expectedValue);
	}

	private void assertAttributeValue_Date(final String expectedValueStr, final ImmutableAttributeSet actualAttributes, final AttributeCode attributeCode)
	{
		final LocalDate actualValue = actualAttributes.getValueAsLocalDate(attributeCode);

		final LocalDate expectedValue;
		if (expectedValueStr == null || expectedValueStr.trim().equals("-"))
		{
			expectedValue = null;
		}
		else if (expectedValueStr.equalsIgnoreCase("today"))
		{
			expectedValue = SystemTime.asLocalDate();
		}
		else
		{
			expectedValue = TimeUtil.asLocalDate(expectedValueStr);
		}

		assertThat(actualValue).as("Date attribute " + attributeCode).isEqualTo(expectedValue);
	}

	private void assertCUs(@NonNull final List<JsonHUExpectation.CU> expectations, @NonNull final HuId huId)
	{
		final ArrayList<I_M_HU> cus = new ArrayList<>(services.getCUs(huId));
		cus.sort(Comparator.comparing(I_M_HU::getM_HU_ID)); // make sure we are iterating them in the creation order

		assertThat(cus).hasSameSize(expectations);

		softly(() -> {
			softlyPutContext("huId", context.describeId(huId));
			softlyPutContext("expectations", expectations);
			softlyPutContext("actual CUs", cus);

			for (int i = 0; i < expectations.size(); i++)
			{
				softlyPutContext("index", i);

				final JsonHUExpectation.CU expectation = expectations.get(i);
				softlyPutContext("expectation", expectation);

				final I_M_HU cu = cus.get(i);
				softlyPutContext("actual CU", cu);

				assertCU(expectation, cu);
			}
		});
	}

	private void assertCU(final JsonHUExpectation.CU expectation, final I_M_HU cu)
	{
		if (expectation.getQty() != null)
		{
			final IHUProductStorage storage = services.getSingleProductStorage(cu);
			final Quantity qtyExpected = expectation.getQty().toQuantity();
			assertThat(storage.getQty()).as("Qty").isEqualTo(qtyExpected);
		}

		if (expectation.getAttributes() != null)
		{
			assertAttributes(expectation.getAttributes(), cu);
		}
	}

}
