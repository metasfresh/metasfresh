package de.metas.inventory.mobileui.job.qrcode;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.gs1.GTIN;
import de.metas.gs1.ean13.EAN13;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.qrcodes.custom.CustomHUQRCode;
import de.metas.handlingunits.qrcodes.ean13.EAN13HUQRCode;
import de.metas.handlingunits.qrcodes.gs1.GS1HUQRCode;
import de.metas.handlingunits.qrcodes.leich_und_mehl.LMQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.i18n.AdMessageKey;
import de.metas.inventory.InventoryLineId;
import de.metas.inventory.mobileui.job.InventoryJob;
import de.metas.inventory.mobileui.job.InventoryJobLine;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.service.ClientId;
import org.compiere.model.IQuery;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class InventoryScannedCodeResolveCommand
{
	private final static AdMessageKey ERR_LMQ_LotNoNotFound = AdMessageKey.of("de.metas.handlingunits.picking.job.L_M_QR_CODE_ERROR_MSG");
	private final static AdMessageKey ERR_QR_ProductNotMatching = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG");

	//
	// Services
	@NonNull private final IProductBL productBL;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final HUCache huCache;

	//
	// Params
	@NonNull private final ScannedCode scannedCode;
	@NonNull private final ImmutableList<InventoryJobLine> lines;
	@NonNull private final ImmutableSet<ProductId> eligibleProductIds;

	@Builder
	private InventoryScannedCodeResolveCommand(
			@NonNull final IProductBL productBL,
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			@NonNull final HUQRCodesService huQRCodesService,
			//
			@NonNull final ScannedCode scannedCode,
			@NonNull final InventoryJob job,
			@Nullable final InventoryLineId lineId)
	{
		this.productBL = productBL;
		this.huQRCodesService = huQRCodesService;
		this.huCache = new HUCache(handlingUnitsBL);

		this.scannedCode = scannedCode;
		this.lines = lineId != null
				? ImmutableList.of(job.getLineById(lineId))
				: job.getLines();
		this.eligibleProductIds = this.lines.stream().map(InventoryJobLine::getProductId).collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public ScannedCodeResolveResponse execute()
	{
		final IHUQRCode parsedScannedCode = huQRCodesService.parse(scannedCode);
		try
		{
			if (parsedScannedCode instanceof HUQRCode)
			{
				final HUQRCode huQRCode = (HUQRCode)parsedScannedCode;
				final HuId huId = huQRCodesService.getHuIdByQRCode(huQRCode);
				return resolveByHUId(huId);
			}
			else if (parsedScannedCode instanceof LMQRCode)
			{
				final LMQRCode lmQRCode = (LMQRCode)parsedScannedCode;
				return resolveByLMQRCode(lmQRCode);
			}
			else if (parsedScannedCode instanceof GS1HUQRCode)
			{
				final GS1HUQRCode gs1QRCode = (GS1HUQRCode)parsedScannedCode;
				return resolveByGS1(gs1QRCode);
			}
			else if (parsedScannedCode instanceof EAN13HUQRCode)
			{
				final EAN13 ean13 = ((EAN13HUQRCode)parsedScannedCode).unbox();
				return resolveByEAN13(ean13);
			}
			else if (parsedScannedCode instanceof CustomHUQRCode)
			{
				final CustomHUQRCode customQRCode = (CustomHUQRCode)parsedScannedCode;
				return resolveByCustomHUQRCode(customQRCode);
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

	private ScannedCodeResolveResponse resolveByHUId(@NonNull final HuId huId)
	{
		final I_M_HU hu = huCache.getHUById(huId);
		return resolveByHUs(ImmutableList.of(hu));
	}

	private ScannedCodeResolveResponse resolveByHUs(@NonNull final List<I_M_HU> hus)
	{
		if (hus.isEmpty())
		{
			return ScannedCodeResolveResponse.EMPTY;
		}

		final ArrayList<ScannedCodeResolveResponse.EligibleLine> eligibleLines = new ArrayList<>();
		for (final InventoryJobLine line : lines)
		{
			final ArrayList<ScannedCodeResolveResponse.EligibleHU> eligibleHUs = new ArrayList<>();
			for (final I_M_HU hu : hus)
			{
				final ScannedCodeResolveResponse.EligibleHU eligibleHU = toEligibleHUOrNull(hu, line);
				if (eligibleHU != null)
				{
					eligibleHUs.add(eligibleHU);
				}
			}

			if (!eligibleHUs.isEmpty())
			{
				eligibleLines.add(ScannedCodeResolveResponse.EligibleLine.builder()
						.lineId(line.getId())
						.eligibleHUs(eligibleHUs)
						.build());
			}
		}

		return ScannedCodeResolveResponse.builder()
				.eligibleLines(eligibleLines)
				.build();
	}

	private ScannedCodeResolveResponse.EligibleHU toEligibleHUOrNull(final I_M_HU hu, final InventoryJobLine line)
	{
		final ProductId productId = line.getProductId();
		final Quantity qty = huCache.getQtyOrNull(hu, productId);
		if (qty != null)
		{
			return ScannedCodeResolveResponse.EligibleHU.builder()
					.huId(HuId.ofRepoId(hu.getM_HU_ID()))
					.productId(productId)
					.qty(qty)
					.build();
		}
		else
		{
			return null;
		}
	}

	private ScannedCodeResolveResponse resolveByLMQRCode(final LMQRCode lmQRCode)
	{
		final String lotNumber = lmQRCode.getLotNumber().map(StringUtils::trimBlankToNull).orElseThrow(() -> new AdempiereException(ERR_LMQ_LotNoNotFound));
		return resolveByExternalLotNo(lotNumber);
	}

	private ScannedCodeResolveResponse resolveByExternalLotNo(@NonNull final String lotNumber)
	{
		final List<I_M_HU> hus = huCache.getHUsByExternalLotNo(lotNumber, null);
		return resolveByHUs(hus);
	}

	private ScannedCodeResolveResponse resolveByGS1(@NonNull GS1HUQRCode gs1QRCode)
	{
		final String lotNumber = gs1QRCode.getLotNumber().map(StringUtils::trimBlankToNull).orElseThrow(() -> new AdempiereException(ERR_LMQ_LotNoNotFound));

		final GTIN gtin = gs1QRCode.getGTIN().orElse(null);
		final ProductId gs1ProductId = gtin != null
				? productBL.getProductIdByGTINStrictlyNotNull(gtin, ClientId.METASFRESH)
				: null;
		if (gs1ProductId != null && !eligibleProductIds.contains(gs1ProductId))
		{
			throw new AdempiereException(ERR_QR_ProductNotMatching)
					.setParameter("GTIN", gtin)
					.setParameter("eligibleProductIds", eligibleProductIds)
					.setParameter("gs1ProductId", gs1ProductId);
		}

		final List<I_M_HU> hus = huCache.getHUsByExternalLotNo(lotNumber, gs1ProductId);
		return resolveByHUs(hus);
	}

	private ScannedCodeResolveResponse resolveByEAN13(@NonNull final EAN13 ean13)
	{
		final ArrayList<ScannedCodeResolveResponse.EligibleLine> eligibleLines = new ArrayList<>();

		for (final InventoryJobLine line : lines)
		{
			final ScannedCodeResolveResponse.EligibleLine eligibleLine = resolveByEAN13(ean13, line);
			if (eligibleLine != null)
			{
				eligibleLines.add(eligibleLine);
			}
		}

		return ScannedCodeResolveResponse.builder()
				.eligibleLines(eligibleLines)
				.build();
	}

	private ScannedCodeResolveResponse.EligibleLine resolveByEAN13(@NotNull final EAN13 ean13, @NonNull final InventoryJobLine line)
	{
		if (!productBL.isValidEAN13Product(ean13, line.getProductId(), null))
		{
			return null;
		}

		final ImmutableList<ScannedCodeResolveResponse.EligibleHU> eligibleHUs = huCache.queryByJobLine(line)
				.stream()
				.map(hu -> toEligibleHUOrNull(hu, line))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
		if (eligibleHUs.isEmpty())
		{
			return null;
		}

		return ScannedCodeResolveResponse.EligibleLine.builder()
				.lineId(line.getId())
				.eligibleHUs(eligibleHUs)
				.build();
	}

	private ScannedCodeResolveResponse resolveByCustomHUQRCode(final CustomHUQRCode customQRCode)
	{
		final String scannedQRCodeAsString = customQRCode.getAsString();
		final String qrCodeProductNo = customQRCode.getProductNo().map(StringUtils::trimBlankToNull).orElse(null);

		final IQuery<I_M_HU> query = lines.stream()
				.filter(line -> qrCodeProductNo == null || Objects.equals(line.getProductNo(), qrCodeProductNo))
				.map(line -> huCache.queryByJobLine(line)
						.addOnlyWithAttribute(HUAttributeConstants.ATTR_QRCode, scannedQRCodeAsString)
						.createQuery())
				.reduce(IQuery.unionDistict())
				.orElse(null);
		if (query == null)
		{
			return ScannedCodeResolveResponse.EMPTY;
		}

		final List<I_M_HU> hus = query.list();
		return resolveByHUs(hus);
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

		@Nullable
		public Quantity getQtyOrNull(@NonNull final ProductId productId)
		{
			final IHUProductStorage huProductStorage = byProductId.get(productId);
			return huProductStorage != null ? huProductStorage.getQty() : null;
		}
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
		@NonNull private final QueryLimit limit = QueryLimit.ofInt(10);

		private final HashMap<HuId, I_M_HU> husById = new HashMap<>();
		private final HashMap<HuId, HUProductStorages> productStoragesByHUId = new HashMap<>();

		public I_M_HU getHUById(final @NotNull HuId huId)
		{
			return husById.computeIfAbsent(huId, handlingUnitsBL::getById);
		}

		private void addToCache(final List<I_M_HU> hus) {hus.forEach(this::addToCache);}

		private void addToCache(final I_M_HU hu) {husById.put(HuId.ofRepoId(hu.getM_HU_ID()), hu);}

		public List<I_M_HU> getHUsByExternalLotNo(@NotNull final String externalLotNo, @Nullable final ProductId productId)
		{
			final IHUQueryBuilder huQueryBuilder = handlingUnitsBL.createHUQueryBuilder()
					.setHUStatus(X_M_HU.HUSTATUS_Active)
					.setOnlyTopLevelHUs()
					.addOnlyWithAttribute(AttributeConstants.HU_ExternalLotNumber, externalLotNo);

			if (productId != null)
			{
				huQueryBuilder.addOnlyWithProductId(productId);
			}

			final List<I_M_HU> hus = huQueryBuilder
					.createQuery()
					.setLimit(limit)
					.list();

			addToCache(hus);

			return hus;
		}

		private Quantity getQtyOrNull(final I_M_HU hu, final ProductId productId)
		{
			return getProductStorages(hu).getQtyOrNull(productId);
		}

		private HUProductStorages getProductStorages(final I_M_HU hu)
		{
			return productStoragesByHUId.computeIfAbsent(HuId.ofRepoId(hu.getM_HU_ID()), huId -> retrieveProductStorages(hu));
		}

		private HUProductStorages retrieveProductStorages(final I_M_HU hu)
		{
			return new HUProductStorages(handlingUnitsBL.getStorageFactory().getProductStorages(hu));
		}

		private IHUQueryBuilder queryByJobLine(final InventoryJobLine line)
		{
			return handlingUnitsBL.createHUQueryBuilder()
					.setHUStatus(X_M_HU.HUSTATUS_Active)
					.setOnlyTopLevelHUs()
					.addOnlyWithProductId(line.getProductId())
					.addOnlyInLocatorId(line.getLocatorId())
					.setExcludeReserved();
		}
	}

}
