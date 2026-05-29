package de.metas.hu_consolidation.mobile.job.commands.get_pickingslot_content;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.slot.PickingSlotQueue;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobId;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobRepository;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonHUConsolidationJobPickingSlotContent;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Builder
public class GetPickingSlotContentCommand
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final HUConsolidationJobRepository jobRepository;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final PickingSlotService pickingSlotService;

	@NonNull private final HUConsolidationJobId jobId;
	@NonNull private final PickingSlotId pickingSlotId;

	public JsonHUConsolidationJobPickingSlotContent execute()
	{
		final IHUStorageFactory huStorageFactory = handlingUnitsBL.getStorageFactory();

		final HUConsolidationJob job = jobRepository.getById(jobId);
		if (!job.containsPickingSlotId(pickingSlotId))
		{
			throw new AdempiereException("Job " + jobId + " does not have picking slot " + pickingSlotId);
		}

		final PickingSlotQueue queue = pickingSlotService.getPickingSlotQueue(pickingSlotId);

		final List<I_M_HU> hus = handlingUnitsBL.getByIds(queue.getHuIds());
		final ImmutableMap<HuId, I_M_HU> husById = Maps.uniqueIndex(hus, hu -> HuId.ofRepoId(hu.getM_HU_ID()));

		final ImmutableListMultimap<HuId, IHUProductStorage> huProductStoragesByHuId = huStorageFactory.streamHUProductStorages(hus)
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						IHUProductStorage::getHuId,
						huProductStorage -> huProductStorage
				));

		final Map<ProductId, String> productNamesById = productBL.getProductNames(extractProductIds(huProductStoragesByHuId.values()));

		final ArrayList<JsonHUConsolidationJobPickingSlotContent.Item> items = new ArrayList<>();
		for (final HuId huId : husById.keySet())
		{
			final I_M_HU hu = husById.get(huId);
			final String packingInfo = handlingUnitsBL.buildDisplayName(hu).getPIName();
			final HUQRCode qrCode = huQRCodesService.getFirstQRCodeByHuIdIfExists(huId).orElse(null);
			final String displayName = qrCode != null ? qrCode.toDisplayableQRCode() : hu.getValue();

			final ArrayList<JsonHUConsolidationJobPickingSlotContent.ItemStorage> storages = new ArrayList<>();
			for (final IHUProductStorage huProductStorage : huProductStoragesByHuId.get(huId))
			{
				final String productName = productNamesById.get(huProductStorage.getProductId());
				final Quantity qty = huProductStorage.getQty();

				storages.add(
						JsonHUConsolidationJobPickingSlotContent.ItemStorage.builder()
								.productName(productName)
								.qty(qty.toBigDecimal())
								.uom(qty.getUOMSymbol())
								.build()
				);
			}

			items.add(
					JsonHUConsolidationJobPickingSlotContent.Item.builder()
							.huId(huId)
							.displayName(displayName)
							.packingInfo(packingInfo)
							.storages(storages)
							.build()
			);
		}

		//
		return JsonHUConsolidationJobPickingSlotContent.builder()
				.pickingSlotId(pickingSlotId)
				.pickingSlotQRCode(pickingSlotService.getPickingSlotQRCode(pickingSlotId).toJsonDisplayableQRCode())
				.items(items)
				.build();
	}

	private static Set<ProductId> extractProductIds(final ImmutableCollection<IHUProductStorage> huProductStorages)
	{
		return huProductStorages.stream()
				.map(IHUProductStorage::getProductId)
				.collect(ImmutableSet.toImmutableSet());
	}
}

