package de.metas.frontend_testing.expectations;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.frontend_testing.expectations.request.JsonHUExpectation;
import de.metas.frontend_testing.expectations.request.QtyAndUOMString;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.generichumodel.HUType;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.ProductId;
import de.metas.util.GuavaCollectors;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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

	private final HashMap<HuId, I_M_HU> husCache = new HashMap<>();

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
		if (expectation.getHuStatus() != null)
		{
			final I_M_HU hu = getHUById(huId);
			assertThat(hu.getHUStatus()).as("HUStatus").isEqualTo(expectation.getHuStatus());
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
			assertAttributes(expectation.getAttributes(), huId);
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
			softlyPutContext("expectedAttributes", expectations);
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
			assertAttributes(expectation.getAttributes(), cu);
		}
	}

}
