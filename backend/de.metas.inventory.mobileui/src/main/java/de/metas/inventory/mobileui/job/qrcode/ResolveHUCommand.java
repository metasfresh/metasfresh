package de.metas.inventory.mobileui.job.qrcode;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.gs1.ean13.EAN13;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.ean13.EAN13HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.i18n.TranslatableStrings;
import de.metas.inventory.InventoryLineId;
import de.metas.inventory.mobileui.deps.handlingunits.HULoadingCache;
import de.metas.inventory.mobileui.deps.handlingunits.HandlingUnitsService;
import de.metas.inventory.mobileui.deps.products.ProductService;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.Attribute;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.util.List;
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

		return ResolveHUResponse.builder()
				.lineId(matchingLine.getIdNonNull())
				.huId(huId)
				.productId(matchingLine.getProductId())
				.qtyBooked(huCache.getQty(hu, matchingLine.getProductId()))
				.attributes(getAttributes(hu))
				.build();
	}

	private List<ResolveHUResponse.Attribute> getAttributes(final I_M_HU hu)
	{
		final ImmutableAttributeSet attributes = huCache.getAttributes(hu);

		return ATTRIBUTE_CODES.stream()
				.map(attributeCode -> getAttribute(attributes, attributeCode))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	private ResolveHUResponse.Attribute getAttribute(final ImmutableAttributeSet attributes, final AttributeCode attributeCode)
	{
		if (!attributes.hasAttribute(attributeCode))
		{
			return null;
		}

		return ResolveHUResponse.Attribute.builder()
				.attributeCode(attributeCode)
				.displayName(TranslatableStrings.anyLanguage(attributes.getAttributeNameByCode(attributeCode)))
				.valueType(attributes.getAttributeValueType(attributeCode))
				.value(attributes.getValue(attributeCode))
				.build();
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

			return ResolveHUResponse.builder()
					.lineId(line.getIdNonNull())
					.huId(null)
					.productId(lineProductId)
					.qtyBooked(Quantitys.zero(lineProductId))
					.attributes(getDefaultAttributes())
					.build();
		}

		throw new AdempiereException("No line found for the given HU QR code");
	}

	private List<ResolveHUResponse.Attribute> getDefaultAttributes()
	{
		return ATTRIBUTE_CODES.stream()
				.map(this::getDefaultAttribute)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	private ResolveHUResponse.Attribute getDefaultAttribute(@NonNull AttributeCode attributeCode)
	{
		final Attribute attribute = huService.getAttribute(attributeCode);
		return ResolveHUResponse.Attribute.builder()
				.attributeCode(attributeCode)
				.displayName(attribute.getDisplayName())
				.valueType(attribute.getValueType())
				.value(null)
				.build();
	}

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
