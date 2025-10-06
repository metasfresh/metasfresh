package de.metas.inventory.mobileui.job.qrcode;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.gs1.ean13.EAN13;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.ean13.EAN13HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.inventory.InventoryLineId;
import de.metas.inventory.mobileui.job.InventoryJob;
import de.metas.inventory.mobileui.job.InventoryJobLine;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.LocatorId;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.adempiere.mm.attributes.api.AttributeConstants.ATTR_BestBeforeDate;
import static org.adempiere.mm.attributes.api.AttributeConstants.ATTR_LotNumber;

public class ResolveHUCommand
{
	//
	// Services
	@NonNull private final IProductBL productBL;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final HUCache huCache;

	//
	// Params
	@NonNull private final ScannedCode scannedCode;
	@NonNull private final ImmutableList<InventoryJobLine> lines;

	@Builder
	private ResolveHUCommand(
			@NonNull final IProductBL productBL,
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			@NonNull final HUQRCodesService huQRCodesService,
			//
			@NonNull final ScannedCode scannedCode,
			@NonNull final InventoryJob job,
			@Nullable final InventoryLineId lineId,
			@NonNull final LocatorId locatorId)
	{
		this.productBL = productBL;
		this.huQRCodesService = huQRCodesService;
		this.huCache = new HUCache(handlingUnitsBL);

		this.scannedCode = scannedCode;
		this.lines = (lineId != null ? Stream.of(job.getLineById(lineId)) : job.getLines().stream())
				.filter(line -> !line.isCounted()
						&& LocatorId.equals(line.getLocatorId(), locatorId))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public ResolveHUResponse execute()
	{
		if (this.lines.isEmpty())
		{
			throw new AdempiereException("No eligible lines found");
		}

		final IHUQRCode parsedScannedCode = huQRCodesService.parse(scannedCode);
		try
		{
			if (parsedScannedCode instanceof HUQRCode)
			{
				final HUQRCode huQRCode = (HUQRCode)parsedScannedCode;
				return resolveByHUQRCode(huQRCode);
			}
			else if (parsedScannedCode instanceof EAN13HUQRCode)
			{
				final EAN13 ean13 = ((EAN13HUQRCode)parsedScannedCode).unbox();
				return resolveByEAN13(ean13);
			}
			else
			{
				throw new AdempiereException("Unknown QR code type: " + parsedScannedCode); // TODO trl 
			}
		}
		catch (Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("parsedScannedCode", parsedScannedCode);
		}
	}

	private ResolveHUResponse resolveByHUQRCode(@NonNull final HUQRCode huQRCode)
	{
		final HuId huId = huQRCodesService.getHuIdByQRCode(huQRCode);
		final I_M_HU hu = huCache.getHUById(huId);
		final InventoryJobLine matchingLine = findFirstMatchingLine(line -> isLineMatchingHU(line, hu));
		final ImmutableAttributeSet attributes = huCache.getAttributes(hu);

		final boolean hasBestBeforeDateAttribute = attributes.hasAttribute(ATTR_BestBeforeDate);
		final boolean hasLotNoAttribute = attributes.hasAttribute(ATTR_LotNumber);

		return ResolveHUResponse.builder()
				.lineId(matchingLine.getId())
				.huId(huId)
				.productId(matchingLine.getProductId())
				.qtyBooked(huCache.getQty(hu, matchingLine.getProductId()))
				//
				.hasBestBeforeDateAttribute(hasBestBeforeDateAttribute)
				.bestBeforeDate(hasBestBeforeDateAttribute ? attributes.getValueAsLocalDate(ATTR_BestBeforeDate) : null)
				.hasLotNoAttribute(hasLotNoAttribute)
				.lotNo(hasLotNoAttribute ? attributes.getValueAsString(ATTR_LotNumber) : null)
				.build();
	}

	private ResolveHUResponse resolveByEAN13(@NonNull final EAN13 ean13)
	{
		for (final InventoryJobLine line : lines)
		{
			final ProductId lineProductId = line.getProductId();
			if (!productBL.isValidEAN13Product(ean13, lineProductId))
			{
				continue;
			}

			return ResolveHUResponse.builder()
					.lineId(line.getId())
					.huId(null)
					.productId(lineProductId)
					.qtyBooked(Quantitys.zero(lineProductId))
					//
					.hasBestBeforeDateAttribute(true)
					.bestBeforeDate(null)
					.hasLotNoAttribute(true)
					.lotNo(null)
					.build();

		}

		throw new AdempiereException("No line found for the given HU QR code");
	}

	private InventoryJobLine findFirstMatchingLine(final Predicate<InventoryJobLine> predicate)
	{
		return lines.stream()
				.filter(predicate)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No line found for the given HU QR code"));
	}

	private boolean isLineMatchingHU(final InventoryJobLine line, final I_M_HU hu)
	{
		return !line.isCounted()
				&& LocatorId.equals(line.getLocatorId(), huCache.getLocatorId(hu))
				&& huCache.getProductIds(hu).contains(line.getProductId());
	}

	//
	//
	//
	//
	//

	private static class HUProductStorages
	{
		private final ImmutableMap<ProductId, IHUProductStorage> byProductId;

		private HUProductStorages(final List<IHUProductStorage> productStorages)
		{
			this.byProductId = Maps.uniqueIndex(productStorages, IProductStorage::getProductId);
		}

		@NonNull
		public Quantity getQty(@NonNull final ProductId productId)
		{
			final IHUProductStorage huProductStorage = byProductId.get(productId);
			if (huProductStorage == null)
			{
				throw new AdempiereException("HU does not contain product")
						.setParameter("huProductStorages", byProductId.values())
						.setParameter("productId", productId);
			}
			return huProductStorage.getQty();
		}

		private Set<ProductId> getProductIds() {return byProductId.keySet();}
	}

	//
	//
	//
	//
	//

	@RequiredArgsConstructor
	private static class HUCache
	{
		@NonNull private final IHandlingUnitsBL handlingUnitsBL;

		private final HashMap<HuId, LocatorId> huLocatorsByHUId = new HashMap<>();
		private final HashMap<HuId, I_M_HU> husById = new HashMap<>();
		private final HashMap<HuId, HUProductStorages> productStoragesByHUId = new HashMap<>();
		private final HashMap<HuId, ImmutableAttributeSet> attributesByHUId = new HashMap<>();

		public I_M_HU getHUById(final @NotNull HuId huId)
		{
			return husById.computeIfAbsent(huId, handlingUnitsBL::getById);
		}

		@NonNull
		private Set<ProductId> getProductIds(final I_M_HU hu)
		{
			return getProductStorages(hu).getProductIds();
		}

		@NonNull
		private Quantity getQty(final I_M_HU hu, final ProductId productId)
		{
			return getProductStorages(hu).getQty(productId);
		}

		private HUProductStorages getProductStorages(final I_M_HU hu)
		{
			return productStoragesByHUId.computeIfAbsent(HuId.ofRepoId(hu.getM_HU_ID()), huId -> retrieveProductStorages(hu));
		}

		private HUProductStorages retrieveProductStorages(final I_M_HU hu)
		{
			return new HUProductStorages(handlingUnitsBL.getStorageFactory().getProductStorages(hu));
		}

		public ImmutableAttributeSet getAttributes(final I_M_HU hu)
		{
			return attributesByHUId.computeIfAbsent(HuId.ofRepoId(hu.getM_HU_ID()), huId -> handlingUnitsBL.getImmutableAttributeSet(hu));
		}

		public LocatorId getLocatorId(@NonNull final I_M_HU hu)
		{
			return huLocatorsByHUId.computeIfAbsent(HuId.ofRepoId(hu.getM_HU_ID()), huId -> IHandlingUnitsBL.extractLocatorId(hu));
		}
	}

}
