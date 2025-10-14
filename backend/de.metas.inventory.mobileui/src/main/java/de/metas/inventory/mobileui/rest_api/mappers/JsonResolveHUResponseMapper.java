package de.metas.inventory.mobileui.rest_api.mappers;

import com.google.common.collect.ImmutableList;
import de.metas.inventory.mobileui.deps.handlingunits.HULoadingCache;
import de.metas.inventory.mobileui.deps.products.ProductInfo;
import de.metas.inventory.mobileui.deps.products.ProductsLoadingCache;
import de.metas.inventory.mobileui.deps.warehouse.WarehousesLoadingCache;
import de.metas.inventory.mobileui.job.qrcode.ResolveHUResponse;
import de.metas.inventory.mobileui.rest_api.json.JsonAttributeValueType;
import de.metas.inventory.mobileui.rest_api.json.JsonResolveHUResponse;
import de.metas.inventory.mobileui.rest_api.json.JsonResolveHUResponseAttribute;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeValueType;

@Builder(access = AccessLevel.PACKAGE)
public class JsonResolveHUResponseMapper
{
	@NonNull private final WarehousesLoadingCache warehouses;
	@NonNull private final ProductsLoadingCache products;
	@NonNull private final HULoadingCache handlingUnits;
	@NonNull private final String adLanguage;

	public JsonResolveHUResponse toJson(@NonNull final ResolveHUResponse response)
	{
		final ProductInfo productInfo = products.getById(response.getProductId());

		return JsonResolveHUResponse.builder()
				.lineId(response.getLineId())
				.huId(response.getHuId())
				.huDisplayName(response.getHuId() != null ? handlingUnits.getDisplayName(response.getHuId()) : null)
				.locatorId(response.getLocatorId())
				.locatorName(warehouses.getLocatorName(response.getLocatorId()))
				.productId(response.getProductId())
				.productNo(productInfo.getProductNo())
				.productName(productInfo.getProductName().translate(adLanguage))
				.uom(response.getQtyBooked().getUOMSymbol())
				.qtyBooked(response.getQtyBooked().toBigDecimal())
				.attributes(response.getAttributes()
						.stream()
						.map(this::toJson)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public JsonResolveHUResponseAttribute toJson(@NonNull ResolveHUResponse.Attribute attribute)
	{
		return JsonResolveHUResponseAttribute.builder()
				.code(attribute.getAttributeCode())
				.caption(attribute.getDisplayName().translate(adLanguage))
				.valueType(JsonAttributeValueType.of(attribute.getValueType()))
				.value(extractJsonValue(attribute))
				.build();
	}

	private static Object extractJsonValue(final ResolveHUResponse.Attribute attribute)
	{
		final AttributeValueType valueType = attribute.getValueType();
		if (AttributeValueType.STRING.equals(valueType))
		{
			return attribute.getValueAsString();
		}
		else if (AttributeValueType.NUMBER.equals(valueType))
		{
			return attribute.getValueAsBigDecimal();
		}
		else if (AttributeValueType.DATE.equals(valueType))
		{
			return attribute.getValueAsLocalDate();
		}
		else if (AttributeValueType.LIST.equals(valueType))
		{
			return attribute.getValueAsString();
		}
		else
		{
			throw new AdempiereException("Unknown attribute type: " + valueType);
		}
	}
}
