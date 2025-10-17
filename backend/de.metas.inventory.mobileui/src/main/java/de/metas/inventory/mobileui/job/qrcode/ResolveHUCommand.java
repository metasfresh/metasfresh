package de.metas.inventory.mobileui.job.qrcode;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.gs1.ean13.EAN13;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.ean13.EAN13HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.inventory.InventoryLineId;
import de.metas.inventory.mobileui.deps.handlingunits.HULoadingCache;
import de.metas.inventory.mobileui.deps.handlingunits.HandlingUnitsService;
import de.metas.inventory.mobileui.deps.products.ASILoadingCache;
import de.metas.inventory.mobileui.deps.products.Attribute;
import de.metas.inventory.mobileui.deps.products.Attributes;
import de.metas.inventory.mobileui.deps.products.ProductService;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Predicate;

import static org.adempiere.mm.attributes.api.AttributeConstants.ATTR_BestBeforeDate;
import static org.adempiere.mm.attributes.api.AttributeConstants.ATTR_LotNumber;

public class ResolveHUCommand
{
	//
	// Services
	@NonNull private final ProductService productService;
	@NonNull private final HandlingUnitsService huService;
	@NonNull private final HULoadingCache huCache;
	@NonNull private final ASILoadingCache asiCache;

	//
	// Params
	@NonNull private final ScannedCode scannedCode;
	@NonNull private final ImmutableList<InventoryLine> lines;

	private static final ImmutableSet<AttributeCode> ATTRIBUTE_CODES = ImmutableSet.of(ATTR_BestBeforeDate, ATTR_LotNumber);

	@Builder
	private ResolveHUCommand(
			@NonNull final ProductService productService,
			@NonNull final HandlingUnitsService huService,
			//
			@NonNull final ScannedCode scannedCode,
			@NonNull final Inventory inventory,
			@Nullable final InventoryLineId lineId,
			@NonNull final LocatorId locatorId)
	{
		this.productService = productService;
		this.huService = huService;

		this.huCache = huService.newLoadingCache();
		this.asiCache = productService.newASILoadingCache();

		this.scannedCode = scannedCode;
		this.lines = inventory.streamLines(lineId)
				.filter(InventoryLine::isEligibleForCounting)
				.filter(line -> LocatorId.equals(line.getLocatorId(), locatorId))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public ResolveHUResponse execute()
	{
		if (this.lines.isEmpty())
		{
			throw new AdempiereException("No eligible lines found");
		}

		final IHUQRCode parsedScannedCode = huService.parse(scannedCode);
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
		final HuId huId = huService.getHuIdByQRCode(huQRCode);
		final I_M_HU hu = huCache.getHUById(huId);
		final InventoryLine matchingLine = findFirstMatchingLine(line -> isLineMatchingHU(line, hu));
		final InventoryLineHU matchingLineHU = matchingLine.getInventoryLineHUByHUId(huId).orElse(null);
		final AttributeSetInstanceId lineAsiId = (matchingLineHU != null ? matchingLineHU.getAsiId() : AttributeSetInstanceId.NONE)
				.orElseIfNone(matchingLine.getAsiId());

		final boolean isCounted = matchingLineHU != null && matchingLineHU.isCounted();
		final Quantity qtyCount = isCounted ? matchingLineHU.getQtyCount() : null;

		final Attributes lineAttributes = asiCache.getById(lineAsiId);
		final Attributes huAttributes = huCache.getAttributes(hu);

		return newResponse()
				.lineId(matchingLine.getIdNonNull())
				.locatorId(huCache.getLocatorId(hu))
				.huId(huId)
				.productId(matchingLine.getProductId())
				.qtyBooked(huCache.getQty(hu, matchingLine.getProductId()))
				.isCounted(isCounted)
				.qtyCount(qtyCount)
				.attributes(lineAttributes.retainOnly(ATTRIBUTE_CODES, huAttributes))
				.build();
	}

	private ResolveHUResponse.ResolveHUResponseBuilder newResponse()
	{
		return ResolveHUResponse.builder()
				.scannedCode(scannedCode);
	}

	private ResolveHUResponse resolveByEAN13(@NonNull final EAN13 ean13)
	{
		for (final InventoryLine line : lines)
		{
			final ProductId lineProductId = line.getProductId();
			if (!productService.isValidEAN13Product(ean13, lineProductId))
			{
				continue;
			}

			return newResponse()
					.lineId(line.getIdNonNull())
					.locatorId(line.getLocatorId())
					.huId(null)
					.productId(lineProductId)
					.qtyBooked(Quantitys.zero(lineProductId))
					.attributes(getDefaultAttributes())
					.build();
		}

		throw new AdempiereException("No line found for the given HU QR code");
	}

	private Attributes getDefaultAttributes()
	{
		return Attributes.ofList(
				ATTRIBUTE_CODES.stream()
						.map(this::getDefaultAttribute)
						.filter(Objects::nonNull)
						.collect(ImmutableList.toImmutableList())
		);
	}

	@Nullable
	private Attribute getDefaultAttribute(@NonNull AttributeCode attributeCode)
	{
		return Attribute.of(productService.getAttribute(attributeCode));
	}

	@NonNull
	private InventoryLine findFirstMatchingLine(final Predicate<InventoryLine> predicate)
	{
		return lines.stream()
				.filter(predicate)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No line found for the given HU QR code"));
	}

	private boolean isLineMatchingHU(final InventoryLine line, final I_M_HU hu)
	{
		return !line.isCounted()
				&& LocatorId.equals(line.getLocatorId(), huCache.getLocatorId(hu))
				&& huCache.getProductIds(hu).contains(line.getProductId());
	}
}
