/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.handlingunits.rest_api;

import com.google.common.collect.ImmutableList;
import de.metas.common.handlingunits.JsonHU;
import de.metas.common.handlingunits.JsonHUAttributes;
import de.metas.common.handlingunits.JsonHUProduct;
import de.metas.common.handlingunits.JsonHUType;
import de.metas.handlingunits.HUBarcode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.WarehouseAndLocatorValue;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HandlingUnitsService
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

	@NonNull
	public JsonHU getFullHU(@NonNull final HuId huId, @NonNull final String adLanguage)
	{
		final I_M_HU hu = Optional.ofNullable(handlingUnitsBL.getById(huId))
				.orElseThrow(() -> new AdempiereException("No HU found for M_HU_ID: " + huId));

		return toJson(hu, adLanguage);
	}

	@NonNull
	public JsonHU toJson(@NonNull final I_M_HU hu, @NonNull final String adLanguage)
	{
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext();

		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

		final boolean isAggregatedTU = handlingUnitsBL.isAggregateHU(hu);

		final JsonHU.JsonHUBuilder jsonHUBuilder = JsonHU.builder()
				.id(String.valueOf(huId.getRepoId()))
				.huStatus(hu.getHUStatus())
				.huStatusCaption(TranslatableStrings.adRefList(X_M_HU.HUSTATUS_AD_Reference_ID, hu.getHUStatus()).translate(adLanguage))
				.displayName(handlingUnitsBL.getDisplayName(hu))
				.barcode(HUBarcode.ofHuId(huId).getAsString())
				.jsonHUType(toJsonHUType(hu))
				.products(getProductStorage(huContext, hu))
				.attributes(getAttributes(huContext, hu));

		if (isAggregatedTU)
		{
			jsonHUBuilder.numberOfAggregatedHUs(handlingUnitsBL.getTUsCount(hu).toInt());
		}

		final WarehouseAndLocatorValue warehouseAndLocatorValue = getWarehouseAndLocatorValue(hu);
		if (warehouseAndLocatorValue != null)
		{
			jsonHUBuilder.warehouseValue(warehouseAndLocatorValue.getWarehouseValue())
					.locatorValue(warehouseAndLocatorValue.getLocatorValue());
		}

		return jsonHUBuilder.includedHUs(getIncludedHUs(hu, adLanguage))
				.build();
	}

	public void updateAttributes(@NonNull final HuId huId, @NonNull final JsonHUAttributes jsonHUAttributes)
	{
		final Map<String, Object> attributes = jsonHUAttributes.getAttributes();

		for (final Map.Entry<String, Object> attribute : attributes.entrySet())
		{
			final AttributeCode attributeCode = AttributeCode.ofString(attribute.getKey());

			huAttributesBL.updateHUAttributeRecursive(huId, attributeCode, attribute.getValue(), null);
		}
	}

	@NonNull
	private List<ExtractCounterAttributesCommand.CounterAttribute> extractCounterAttributes(@NonNull final ImmutableAttributeSet huAttributes)
	{
		return new ExtractCounterAttributesCommand(huAttributes).execute();
	}

	@NonNull
	private JsonHUProduct toJson(@NonNull final IHUProductStorage productStorage)
	{
		final I_M_Product product = productBL.getById(productStorage.getProductId());
		final Quantity qty = productStorage.getQty();

		return JsonHUProduct.builder()
				.productValue(product.getValue())
				.productName(product.getName())
				.qty(qty.toBigDecimal().toString())
				.uom(qty.getX12DE355().getCode())
				.build();
	}

	@NonNull
	private JsonHUAttributes toJson(final ImmutableAttributeSet huAttributes)
	{
		final JsonHUAttributes json = new JsonHUAttributes();
		for (final AttributeCode attributeCode : huAttributes.getAttributeCodes())
		{
			final Object value = huAttributes.getValue(attributeCode);
			json.putAttribute(attributeCode.getCode(), value);
		}

		return json;
	}

	@NonNull
	private ImmutableList<JsonHUProduct> getProductStorage(
			@NonNull final IMutableHUContext huContext,
			@NonNull final I_M_HU hu)
	{
		return huContext.getHUStorageFactory()
				.getStorage(hu)
				.streamProductStorages()
				.map(this::toJson)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private JsonHUAttributes getAttributes(
			@NonNull final IMutableHUContext huContext,
			@NonNull final I_M_HU hu)
	{
		final ImmutableAttributeSet huAttributes = huContext.getHUAttributeStorageFactory()
				.getImmutableAttributeSet(hu);

		final JsonHUAttributes jsonHUAttributes = toJson(huAttributes);

		for (final ExtractCounterAttributesCommand.CounterAttribute counterAttribute : extractCounterAttributes(huAttributes))
		{
			jsonHUAttributes.putAttribute(counterAttribute.getAttributeCode(), counterAttribute.getCounter());
		}

		return jsonHUAttributes;
	}

	@NonNull
	private JsonHUType toJsonHUType(@NonNull final I_M_HU hu)
	{
		if (handlingUnitsBL.isLoadingUnit(hu))
		{
			return JsonHUType.LU;
		}
		else if (handlingUnitsBL.isTransportUnitOrAggregate(hu))
		{
			return JsonHUType.TU;
		}
		else if (handlingUnitsBL.isVirtual(hu))
		{
			return JsonHUType.CU;
		}
		else
		{
			throw new AdempiereException("Unknown huType for hu")
					.appendParametersToMessage()
					.setParameter("huId", hu.getM_HU_ID());
		}
	}

	@Nullable
	private WarehouseAndLocatorValue getWarehouseAndLocatorValue(@NonNull final I_M_HU hu)
	{
		if (hu.getM_Locator_ID() <= 0)
		{
			return null;
		}

		return warehouseDAO.retrieveWarehouseAndLocatorValueByLocatorRepoId(hu.getM_Locator_ID());
	}

	@NonNull
	private List<JsonHU> getIncludedHUs(@NonNull final I_M_HU hu, @NonNull final String adLanguage)
	{
		return handlingUnitsDAO.retrieveIncludedHUs(hu)
				.stream()
				.map(includedHU -> toJson(includedHU, adLanguage))
				.collect(ImmutableList.toImmutableList());
	}
}
