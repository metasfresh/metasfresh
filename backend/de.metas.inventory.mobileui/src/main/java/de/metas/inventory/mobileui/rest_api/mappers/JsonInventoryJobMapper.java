package de.metas.inventory.mobileui.rest_api.mappers;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.NumberUtils;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.handlingunits.inventory.InventoryLineHUId;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.inventory.mobileui.deps.handlingunits.HULoadingCache;
import de.metas.inventory.mobileui.deps.products.ASILoadingCache;
import de.metas.inventory.mobileui.deps.products.ProductInfo;
import de.metas.inventory.mobileui.deps.products.ProductsLoadingCache;
import de.metas.inventory.mobileui.deps.warehouse.WarehouseInfo;
import de.metas.inventory.mobileui.deps.warehouse.WarehousesLoadingCache;
import de.metas.inventory.mobileui.rest_api.json.JsonCountStatus;
import de.metas.inventory.mobileui.rest_api.json.JsonInventoryJob;
import de.metas.inventory.mobileui.rest_api.json.JsonInventoryJobLine;
import de.metas.inventory.mobileui.rest_api.json.JsonInventoryLineHU;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.LocatorId;
import org.compiere.util.DisplayType;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder(access = AccessLevel.PACKAGE)
public class JsonInventoryJobMapper
{
	@NonNull private final WarehousesLoadingCache warehouses;
	@NonNull private final ProductsLoadingCache products;
	@NonNull private final ASILoadingCache asis;
	@NonNull private final HULoadingCache handlingUnits;
	@NonNull private final String adLanguage;

	private boolean loadAllDetails;

	public JsonInventoryJobMapper loadAllDetails(final boolean loadAllDetails)
	{
		this.loadAllDetails = loadAllDetails;
		return this;
	}

	public JsonInventoryJob toJson(final Inventory inventory)
	{
		final WarehouseInfo warehouseInfo = inventory.getWarehouseId() != null ? warehouses.getById(inventory.getWarehouseId()) : null;

		final ImmutableList<JsonInventoryJobLine> lines = inventory.getLines()
				.stream()
				.map(this::toJson)
				.collect(ImmutableList.toImmutableList());

		return JsonInventoryJob.builder()
				.id(inventory.getId())
				.documentNo(inventory.getDocumentNo())
				.movementDate(inventory.getMovementDate().toLocalDate().toString())
				.warehouseName(warehouseInfo != null ? warehouseInfo.getWarehouseName() : "")
				.lines(lines)
				.countStatus(computeCountStatus(lines))
				.build();
	}

	private JsonInventoryJobLine toJson(final InventoryLine line)
	{
		final ProductInfo productInfo = products.getById(line.getProductId());

		final LocatorId locatorId = line.getLocatorId();
		final String locatorName = warehouses.getLocatorName(locatorId);

		return JsonInventoryJobLine.builder()
				.id(line.getIdNonNull())
				.caption(productInfo.getProductNo() + "_" + productInfo.getProductName())
				.productId(productInfo.getProductId())
				.productNo(productInfo.getProductNo())
				.productName(productInfo.getProductName().translate(adLanguage))
				.locatorId(locatorId.getRepoId())
				.locatorName(locatorName)
				.uom(line.getUOMSymbol())
				.qtyBooked(line.getQtyBookFixed().toBigDecimal())
				.qtyCount(line.getQtyCountFixed().toBigDecimal())
				.countStatus(computeCountStatus(line))
				.build();
	}

	private static @NotNull JsonCountStatus computeCountStatus(final ImmutableList<JsonInventoryJobLine> lines)
	{
		return JsonCountStatus.combine(lines, JsonInventoryJobLine::getCountStatus)
				.orElse(JsonCountStatus.COUNTED); // consider counted when there are no lines (shall not happen)
	}

	private static @NonNull JsonCountStatus computeCountStatus(final InventoryLine line)
	{
		return JsonCountStatus.combine(line.getInventoryLineHUs(), JsonInventoryJobMapper::computeCountStatus)
				.orElse(JsonCountStatus.COUNTED);
	}

	private static JsonCountStatus computeCountStatus(final InventoryLineHU lineHU)
	{
		return JsonCountStatus.ofIsCountedFlag(lineHU.isCounted());
	}

	public List<JsonInventoryLineHU> toJsonInventoryLineHUs(@NonNull final InventoryLine line)
	{
		return line.getInventoryLineHUs()
				.stream()
				.filter(lineHU -> lineHU.getId() != null)
				.map(lineHU -> toJson(lineHU, line))
				.collect(ImmutableList.toImmutableList());
	}

	public JsonInventoryLineHU toJson(final InventoryLineHU lineHU, InventoryLine line)
	{
		final ProductInfo product = products.getById(line.getProductId());
		final HuId huId = lineHU.getHuId();

		final String productName = product.getProductName().translate(adLanguage);
		final String huDisplayName = huId != null ? handlingUnits.getDisplayName(huId) : null;

		return JsonInventoryLineHU.builder()
				.id(lineHU.getIdNotNull())
				.caption(CoalesceUtil.firstNotBlank(huDisplayName, productName))
				.productId(product.getProductId())
				.productNo(product.getProductNo())
				.productName(productName)
				.locatorId(line.getLocatorId().getRepoId())
				.locatorName(warehouses.getLocatorName(line.getLocatorId()))
				.huId(huId)
				.huDisplayName(huDisplayName)
				.uom(lineHU.getUOMSymbol())
				.qtyBooked(lineHU.getQtyBook().toBigDecimal())
				.qtyCount(lineHU.getQtyCount().toBigDecimal())
				.countStatus(computeCountStatus(lineHU))
				.attributes(loadAllDetails ? toJson(asis.getById(lineHU.getAsiId())) : null)
				.build();
	}

	public JsonInventoryLineHU toJson(final InventoryLine line, final InventoryLineHUId lineHUId)
	{
		final InventoryLineHU lineHU = line.getInventoryLineHUById(lineHUId);
		return toJson(lineHU, line);
	}

	private List<JsonInventoryLineHU.Attribute> toJson(final ImmutableAttributeSet attributes)
	{
		return attributes.getAttributeCodes()
				.stream()
				.map(attributeCode -> toJson(attributes, attributeCode))
				.collect(ImmutableList.toImmutableList());
	}

	private JsonInventoryLineHU.Attribute toJson(final ImmutableAttributeSet attributes, final AttributeCode attributeCode)
	{
		return JsonInventoryLineHU.Attribute.builder()
				.caption(attributes.getAttributeNameByCode(attributeCode))
				.value(getValueAsTranslatableString(attributes, attributeCode).translate(adLanguage))
				.build();
	}

	private static ITranslatableString getValueAsTranslatableString(final ImmutableAttributeSet attributes, final AttributeCode attributeCode)
	{
		final AttributeValueType valueType = attributes.getAttributeValueType(attributeCode);
		switch (valueType)
		{
			case STRING:
			case LIST:
			{
				final String valueStr = attributes.getValueAsString(attributeCode);
				return TranslatableStrings.anyLanguage(valueStr);
			}
			case NUMBER:
			{
				final BigDecimal valueBD = attributes.getValueAsBigDecimal(attributeCode);
				return valueBD != null
						? TranslatableStrings.number(valueBD, NumberUtils.isInteger(valueBD) ? DisplayType.Integer : DisplayType.Number)
						: TranslatableStrings.empty();
			}
			case DATE:
			{
				final LocalDate valueDate = attributes.getValueAsLocalDate(attributeCode);
				return valueDate != null
						? TranslatableStrings.date(valueDate, DisplayType.Date)
						: TranslatableStrings.empty();
			}
			default:
			{
				return TranslatableStrings.empty();
			}
		}
	}

}
