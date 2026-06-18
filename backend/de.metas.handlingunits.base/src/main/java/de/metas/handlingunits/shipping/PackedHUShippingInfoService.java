package de.metas.handlingunits.shipping;

import com.google.common.annotations.VisibleForTesting;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.shipping.weighting.ShippingWeightCalculator;
import de.metas.handlingunits.shipping.weighting.ShippingWeightSourceTypes;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.service.ISysConfigBL;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class PackedHUShippingInfoService
{
	private static final AttributeCode ATTR_COUNTRY_OF_ORIGIN = HUAttributeConstants.ATTR_CountryOfOrigin;

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUPackageBL huPackageBL = Services.get(IHUPackageBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@VisibleForTesting
	public static PackedHUShippingInfoService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(PackedHUShippingInfoService.class, PackedHUShippingInfoService::new);
	}

	@NonNull
	public PackedHUShippingInfo of(@NonNull final I_M_HU hu)
	{
		return PackedHUShippingInfo.builder()
				.weightInKg(calculateWeightInKg(hu))
				.dimensions(huPackageBL.getPackageDimensions(hu))
				.topLevelType(deriveTopLevelType(hu))
				.countryOfOrigin(readCountryOfOrigin(hu))
				.build();
	}

	/**
	 * Returns one {@link PackedHUProductItem} per product contained in the given top-level HU
	 * (one per {@code M_HU_Storage} product row), enabling a COMPLETE multi-product carrier advise.
	 *
	 * <p>Uses the same source as the existing single-product advise path
	 * ({@code handlingUnitsBL.getStorageFactory().getProductStorages(topLevelHU)}); the qty is the
	 * product's stocking-UOM quantity, matching that path.
	 *
	 * <p>Country of origin is HU-level, not per-product: {@code getProductStorages(topLevelHU)} returns
	 * the aggregated per-product {@code M_HU_Storage} rows of the top-level HU, and each
	 * {@link IHUProductStorage#getM_HU()} resolves to that same top-level HU (not the owning child VHU),
	 * so there is no per-product attribute storage to read. We therefore read the HU-level
	 * {@code CountryOfOrigin} attribute once and apply it to every item — matching {@link #of(I_M_HU)} semantics.
	 */
	@NonNull
	public List<PackedHUProductItem> getProductItems(@NonNull final I_M_HU topLevelHU)
	{
		final List<IHUProductStorage> productStorages = handlingUnitsBL
				.getStorageFactory()
				.getProductStorages(topLevelHU);

		final String countryOfOrigin = readCountryOfOrigin(topLevelHU);

		return productStorages.stream()
				.map(productStorage -> PackedHUProductItem.builder()
						.productId(productStorage.getProductId())
						.qty(productStorage.getQtyInStockingUOM())
						.countryOfOrigin(countryOfOrigin)
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	private Quantity calculateWeightInKg(@NonNull final I_M_HU hu)
	{
		final ShippingWeightSourceTypes weightSourceTypes = ShippingWeightSourceTypes
				.ofCommaSeparatedString(sysConfigBL.getValue(ShippingWeightCalculator.SYSCONFIG_WeightSourceTypes))
				.orElse(ShippingWeightSourceTypes.DEFAULT);
		return ShippingWeightCalculator.builder()
				.weightSourceTypes(weightSourceTypes)
				.build()
				.calculateWeightInKg(hu)
				.orElse(null);
	}

	@NonNull
	private HuUnitType deriveTopLevelType(@NonNull final I_M_HU hu)
	{
		final HuUnitType huUnitType = HuUnitType.ofNullableCode(handlingUnitsBL.getHU_UnitType(hu));
		return huUnitType != null ? huUnitType : HuUnitType.VHU;
	}

	// countryOfOrigin source — keep in sync with NShiftDraftDeliveryOrderCreator#readCountryOfOrigin:
	// both read ATTR_CountryOfOrigin (here: HU attribute storage; there: inout-line ASI).
	// The two resolve to the same value because ShipmentLineBuilder.transferAttributesToShipmentLine()
	// copies HU attributes (including CountryOfOrigin) into the shipment-line ASI on shipment creation.
	@Nullable
	private String readCountryOfOrigin(@NonNull final I_M_HU hu)
	{
		final IAttributeStorage huAttributes = handlingUnitsBL.getAttributeStorage(hu);

		if (!huAttributes.hasAttribute(ATTR_COUNTRY_OF_ORIGIN))
		{
			return null;
		}
		return huAttributes.getValueAsString(ATTR_COUNTRY_OF_ORIGIN);
	}
}
