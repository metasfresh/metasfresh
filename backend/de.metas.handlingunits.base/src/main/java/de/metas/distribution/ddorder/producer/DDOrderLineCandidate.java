package de.metas.distribution.ddorder.producer;

import com.google.common.collect.ImmutableMap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.materialtracking.IHUMaterialTrackingBL;
import de.metas.handlingunits.materialtracking.IQualityInspectionSchedulable;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.i18n.IMsgBL;
import de.metas.product.LotNumberQuarantine;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.ArrayKeyBuilder;
import org.compiere.util.Env;
import org.compiere.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

final class DDOrderLineCandidate
{
	@Getter
	private final LocatorId locatorFromId;
	@Getter
	private final ProductId productId;
	private final Util.ArrayKey aggregationKey;
	private final ArrayList<PickFromHU> pickFromHUs = new ArrayList<>();

	private final I_M_HU_PI_Item_Product piItemProduct;
	private final Map<I_M_Attribute, Object> attributes;

	private final LotNumberQuarantine lotNoQuarantine;

	public DDOrderLineCandidate(
			final IHUMaterialTrackingBL huMaterialTrackingBL,
			final IHUContext huContext,
			final IHUProductStorage huProductStorage,
			final LotNumberQuarantine lotNoQuarantine)
	{
		final ArrayKeyBuilder aggregationKeyBuilder = Util.mkKey();

		//
		// Locator from
		final I_M_HU hu = huProductStorage.getM_HU();
		locatorFromId = IHandlingUnitsBL.extractLocatorId(hu);
		aggregationKeyBuilder.appendId(locatorFromId.getRepoId());

		//
		// Product & UOM
		productId = huProductStorage.getProductId();
		final I_C_UOM uom = huProductStorage.getC_UOM();
		aggregationKeyBuilder.appendId(productId.getRepoId());
		aggregationKeyBuilder.appendId(uom.getC_UOM_ID());

		//
		// PI Item Product
		piItemProduct = IHandlingUnitsBL.extractPIItemProductOrNull(hu);
		aggregationKeyBuilder.appendId(piItemProduct == null ? -1 : piItemProduct.getM_HU_PI_Item_Product_ID());

		//
		// Fetch relevant attributes
		final IAttributeStorage huAttributeStorage = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
		final IQualityInspectionSchedulable qualityInspectionSchedulable = huMaterialTrackingBL.asQualityInspectionSchedulable(huContext, huAttributeStorage).orElse(null);
		if (qualityInspectionSchedulable != null)
		{
			attributes = qualityInspectionSchedulable.getAttributesAsMap();
		}
		else
		{
			attributes = ImmutableMap.of();
		}
		for (final Map.Entry<I_M_Attribute, Object> attribute2value : attributes.entrySet())
		{
			aggregationKeyBuilder.append(attribute2value.getKey().getValue(), attribute2value.getValue());
		}

		this.lotNoQuarantine = lotNoQuarantine;

		aggregationKeyBuilder.append(lotNoQuarantine == null ? -1 : lotNoQuarantine.getId());

		aggregationKey = aggregationKeyBuilder.build();

		//
		// Add this HUProductStoarge
		addHUProductStorage(huProductStorage);
	}

	public Util.ArrayKey getAggregationKey()
	{
		return Check.assumeNotNull(aggregationKey, "aggregationKey not null");
	}

	public void addDDOrderLineCandidate(final DDOrderLineCandidate candidateToAdd)
	{
		Check.assume(Objects.equals(aggregationKey, candidateToAdd.getAggregationKey()), "Same aggregation key\n.Expected: {} \nBut it was: {}", aggregationKey, candidateToAdd.getAggregationKey());

		pickFromHUs.addAll(candidateToAdd.getPickFromHUs());
	}

	private void addHUProductStorage(final IHUProductStorage huProductStorage)
	{
		pickFromHUs.add(PickFromHU.builder()
				.huId(huProductStorage.getHuId())
				.qtyToPick(huProductStorage.getQty())
				.qtyToPickInStockingUOM(huProductStorage.getQtyInStockingUOM())
				.build());
	}

	public Quantity getQtyInSourceUOM()
	{
		return pickFromHUs.stream()
				.map(PickFromHU::getQtyToPick)
				.reduce(Quantity::add)
				.orElseThrow(() -> new AdempiereException("No HUs"));
	}

	public Quantity getQtyInStockingUOM()
	{
		return pickFromHUs.stream()
				.map(PickFromHU::getQtyToPickInStockingUOM)
				.reduce(Quantity::add)
				.orElseThrow(() -> new AdempiereException("No HUs"));
	}

	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		return piItemProduct;
	}

	public List<PickFromHU> getPickFromHUs()
	{
		return pickFromHUs;
	}

	public String getDescription()
	{
		final StringBuilder description = new StringBuilder();
		for (final PickFromHU pickFromHU : pickFromHUs)
		{
			final String huValue = String.valueOf(pickFromHU.getHuId().getRepoId());
			if (description.length() > 0)
			{
				description.append(", ");
			}
			description.append(huValue);
		}

		description.insert(0, Services.get(IMsgBL.class).translate(Env.getCtx(), "M_HU_ID") + ": ");

		return description.toString();
	}

	public LotNumberQuarantine getLotNumberQuarantine()
	{
		return lotNoQuarantine;
	}

	public Map<I_M_Attribute, Object> getAttributes()
	{
		return attributes;
	}

	//
	// ---------------------------------------------------------------
	//
	@Value
	@Builder
	public static class PickFromHU
	{
		@NonNull HuId huId;
		@NonNull Quantity qtyToPick;
		@NonNull Quantity qtyToPickInStockingUOM;
	}
}
