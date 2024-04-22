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
import de.metas.common.handlingunits.JsonAllowedHUClearanceStatuses;
import de.metas.common.handlingunits.JsonClearanceStatus;
import de.metas.common.handlingunits.JsonClearanceStatusInfo;
import de.metas.common.handlingunits.JsonHU;
import de.metas.common.handlingunits.JsonHUAttribute;
import de.metas.common.handlingunits.JsonHUAttributeCodeAndValues;
import de.metas.common.handlingunits.JsonHUAttributes;
import de.metas.common.handlingunits.JsonHUProduct;
import de.metas.common.handlingunits.JsonHUQRCode;
import de.metas.common.handlingunits.JsonHUType;
import de.metas.common.handlingunits.JsonSetClearanceStatusRequest;
import de.metas.global_qrcodes.JsonDisplayableQRCode;
import de.metas.handlingunits.ClearanceStatus;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.UpdateHUQtyRequest;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.impl.HUQtyService;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.movement.HUIdAndQRCode;
import de.metas.handlingunits.movement.MoveHUCommand;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.rest_api.move_hu.MoveHURequest;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseAndLocatorValue;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.handlingunits.rest_api.JsonHUHelper.fromJsonClearanceStatus;
import static de.metas.handlingunits.rest_api.JsonHUHelper.toJsonClearanceStatus;

@Service
public class HandlingUnitsService
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final HUQRCodesService huQRCodeService;
	private final HUQtyService huQtyService;
	private final HUTransformService huTransformService;

	public HandlingUnitsService(
			@NonNull final HUQRCodesService huQRCodeService,
			@NonNull final HUQtyService huQtyService)
	{
		this.huQRCodeService = huQRCodeService;
		this.huQtyService = huQtyService;
		this.huTransformService = HUTransformService.builder()
				.huQRCodesService(huQRCodeService)
				.build();
	}

	@NonNull
	public JsonHU getFullHU(@NonNull final HuId huId, @NonNull final String adLanguage, final boolean getAllowedClearanceStatuses)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		if (hu == null)
		{
			throw new AdempiereException("No HU found for M_HU_ID: " + huId);
		}

		if (!huStatusBL.isPhysicalHU(hu))
		{
			throw new AdempiereException("Not a physical HU: " + huId);
		}

		final LoadJsonHURequest loadJsonHURequest = LoadJsonHURequest
				.ofHUAndLanguage(hu, adLanguage)
				.withGetAllowedClearanceStatuses(getAllowedClearanceStatuses);

		return toJson(loadJsonHURequest);
	}

	@NonNull
	public JsonHU toJson(@NonNull final LoadJsonHURequest loadJsonHURequest)
	{
		final I_M_HU hu = loadJsonHURequest.getHu();
		final String adLanguage = loadJsonHURequest.getAdLanguage();

		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext();

		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

		final boolean isAggregatedTU = handlingUnitsBL.isAggregateHU(hu);

		final JsonHUAttributes jsonHUAttributes = toJsonHUAttributes(huContext, hu, adLanguage);

		final JsonHU.JsonHUBuilder jsonHUBuilder = JsonHU.builder()
				.id(String.valueOf(huId.getRepoId()))
				.huStatus(hu.getHUStatus())
				.huStatusCaption(TranslatableStrings.adRefList(X_M_HU.HUSTATUS_AD_Reference_ID, hu.getHUStatus()).translate(adLanguage))
				.displayName(handlingUnitsBL.getDisplayName(hu))
				.qrCode(toJsonHUQRCode(huId))
				.jsonHUType(toJsonHUType(hu))
				.products(getProductStorage(huContext, hu))
				.attributes2(jsonHUAttributes)
				.clearanceNote(hu.getClearanceNote())
				.clearanceStatus(getClearanceStatusInfo(hu));

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

		if (loadJsonHURequest.isGetAllowedClearanceStatuses())
		{
			jsonHUBuilder.allowedHUClearanceStatuses(getAllowedClearanceStatuses(hu));
		}

		return jsonHUBuilder
				.includedHUs(getIncludedHUs(loadJsonHURequest))
				.build();
	}

	public void updateAttributes(@NonNull final HuId huId, @NonNull final JsonHUAttributeCodeAndValues jsonHUAttributes)
	{
		final Map<String, Object> attributes = jsonHUAttributes.getAttributes();

		for (final Map.Entry<String, Object> attribute : attributes.entrySet())
		{
			final AttributeCode attributeCode = AttributeCode.ofString(attribute.getKey());

			huAttributesBL.updateHUAttributeRecursive(huId, attributeCode, attribute.getValue(), null);
		}
	}

	public void setClearanceStatus(@NonNull final JsonSetClearanceStatusRequest request)
	{
		final JsonSetClearanceStatusRequest.JsonHUIdentifier jsonHuIdentifier = request.getHuIdentifier();

		final HuId huId = resolveHUIdentifier(jsonHuIdentifier);

		final ClearanceStatus clearanceStatus = fromJsonClearanceStatus(request.getClearanceStatus());

		final ClearanceStatusInfo clearanceStatusInfo = ClearanceStatusInfo.builder()
				.clearanceStatus(clearanceStatus)
				.clearanceNote(request.getClearanceNote())
				.build();

		handlingUnitsBL.setClearanceStatusRecursively(huId, clearanceStatusInfo);
	}

	@NonNull
	public JsonAllowedHUClearanceStatuses getAllowedClearanceStatusesForHUId(@NonNull final HuId huId)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);

		if (hu == null)
		{
			throw MissingResourceException.builder()
					.resourceName("M_HU")
					.resourceIdentifier(huId.toString())
					.build();
		}

		return getAllowedClearanceStatuses(hu);
	}

	@NonNull
	private JsonAllowedHUClearanceStatuses getAllowedClearanceStatuses(@NonNull final I_M_HU hu)
	{
		final ClearanceStatus currentClearanceStatus = ClearanceStatus.ofNullableCode(hu.getClearanceStatus());

		final ImmutableList<JsonClearanceStatusInfo> huStatuses = Arrays.stream(ClearanceStatus.values())
				.filter(availableStatus -> !availableStatus.equals(currentClearanceStatus))
				.map(this::getClearanceStatusInfo)
				.collect(ImmutableList.toImmutableList());

		return JsonAllowedHUClearanceStatuses.builder()
				.clearanceStatuses(huStatuses)
				.build();
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
	private JsonHUAttributes toJsonHUAttributes(
			@NonNull final IMutableHUContext huContext,
			@NonNull final I_M_HU hu,
			@NonNull final String adLanguage)
	{
		final ImmutableAttributeSet huAttributes = huContext.getHUAttributeStorageFactory()
				.getImmutableAttributeSet(hu);

		final ArrayList<JsonHUAttribute> list = new ArrayList<>();
		for (final I_M_Attribute attribute : huAttributes.getAttributes())
		{
			final AttributeCode attributeCode = AttributeCode.ofString(attribute.getValue());
			final Object value = huAttributes.getValue(attributeCode);

			list.add(JsonHUAttribute.builder()
					.code(attributeCode.getCode())
					.caption(attribute.getName())
					.value(value)
					.valueDisplay(JsonHUAttributeConverters.toDisplayValue(value, adLanguage))
					.build());
		}

		for (final ExtractCounterAttributesCommand.CounterAttribute counterAttribute : extractCounterAttributes(huAttributes))
		{
			list.add(JsonHUAttribute.builder()
					.value(counterAttribute.getAttributeCode())
					.caption(counterAttribute.getAttributeCode())
					.value(counterAttribute.getCounter())
					.build());
		}

		return JsonHUAttributes.builder().list(ImmutableList.copyOf(list)).build();
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
	private List<JsonHU> getIncludedHUs(@NonNull final LoadJsonHURequest request)
	{
		return handlingUnitsDAO.retrieveIncludedHUs(request.getHu())
				.stream()
				.map(includedHU -> toJson(request.withHu(includedHU)))
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	private JsonHUQRCode toJsonHUQRCode(@NonNull final HuId huId)
	{
		return huQRCodeService.getFirstQRCodeByHuIdIfExists(huId)
				.map(HandlingUnitsService::toJsonHUQRCode)
				.orElse(null);
	}

	public static JsonHUQRCode toJsonHUQRCode(@NonNull final HUQRCode qrCode)
	{
		final JsonDisplayableQRCode rendered = qrCode.toRenderedJson();
		return JsonHUQRCode.builder()
				.code(rendered.getCode())
				.displayable(rendered.getDisplayable())
				.build();
	}

	@Nullable
	private JsonClearanceStatusInfo getClearanceStatusInfo(@NonNull final I_M_HU hu)
	{
		final ClearanceStatus clearanceStatus = ClearanceStatus.ofNullableCode(hu.getClearanceStatus());

		if (clearanceStatus == null)
		{
			return null;
		}

		return getClearanceStatusInfo(clearanceStatus);
	}

	@NonNull
	private JsonClearanceStatusInfo getClearanceStatusInfo(@NonNull final ClearanceStatus clearanceStatus)
	{
		final String caption = handlingUnitsBL.getClearanceStatusCaption(clearanceStatus)
				.translate(Env.getAD_Language());

		final JsonClearanceStatus jsonStatus = toJsonClearanceStatus(clearanceStatus);

		return JsonClearanceStatusInfo.builder()
				.key(jsonStatus.name())
				.caption(caption)
				.build();
	}

	public void move(@NonNull final MoveHURequest request)
	{
		MoveHUCommand.builder()
				.huQRCodesService(huQRCodeService)
				.husToMove(ImmutableList.of(HUIdAndQRCode.builder()
						.huQRCode(request.getHuQRCode())
						.huId(request.getHuId())
						.build()))
				.targetQRCode(request.getTargetQRCode())
				.build()
				.execute();
	}

	@NonNull
	public HuId updateQty(@NonNull final JsonHUQtyChangeRequest request)
	{
		return trxManager.callInThreadInheritedTrx(() -> updateQtyInTrx(request));
	}

	@NonNull
	private HuId updateQtyInTrx(@NonNull final JsonHUQtyChangeRequest request)
	{
		final HUQRCode qrCode = HUQRCode.fromGlobalQRCodeJsonString(request.getHuQRCode());

		boolean isSplitOneIfAggregated = request.isSplitOneIfAggregated();
		HuId huId = request.getHuId();
		LocatorId locatorId = null;
		if (huId == null)
		{
			huId = huQRCodeService.getHuIdByQRCodeIfExists(qrCode).orElse(null);
			if (huId == null)
			{
				locatorId = Optional.ofNullable(request.getLocatorQRCode())
						.map(locatorQRCode -> LocatorQRCode.ofGlobalQRCodeJsonString(locatorQRCode).getLocatorId())
						.orElseThrow(() -> new AdempiereException("No locator provided"));

				isSplitOneIfAggregated = false;
			}
		}
		else
		{
			huQRCodeService.assertQRCodeAssignedToHU(qrCode, huId);
		}

		final Quantity qty = Quantitys.create(request.getQty().getQty(), X12DE355.ofCode(request.getQty().getUomCode()));

		HuId huIdToUpdate = huId != null && isSplitOneIfAggregated
				? huTransformService.extractToTopLevel(huId, qrCode)
				: huId;

		final Inventory inventory = huQtyService.updateQty(UpdateHUQtyRequest.builder()
				.huId(huIdToUpdate)
				.huQRCode(qrCode)
				.locatorId(locatorId)
				.qty(qty)
				.description(request.getDescription())
				.build());

		if (inventory != null)
		{
			final HuId inventoryHuId = CollectionUtils.singleElement(inventory.getHuIds());
			if (huIdToUpdate == null)
			{
				huIdToUpdate = inventoryHuId;
			}
			else if (!HuId.equals(inventoryHuId, huIdToUpdate))
			{
				throw new AdempiereException("The 'inventoried' HU must match the hu to update!")
						.appendParametersToMessage()
						.setParameter("inventoryHuId", inventoryHuId)
						.setParameter("huIdToUpdate", huIdToUpdate);
			}
		}

		if (huId != null && !HuId.equals(huId, huIdToUpdate))
		{
			final I_M_HU splitHU = handlingUnitsBL.getById(huIdToUpdate);
			final I_M_HU initialParentHU = handlingUnitsBL.getTopLevelParent(huId);
			huTransformService.tusToExistingLU(ImmutableList.of(splitHU), initialParentHU);
		}

		if (huIdToUpdate != null)
		{
			updateHUAttributes(huIdToUpdate, request);
		}

		return huIdToUpdate;
	}

	private void updateHUAttributes(@NonNull final HuId huId, @NonNull final JsonHUQtyChangeRequest request)
	{
		if (!request.isSetBestBeforeDate() && !request.isSetLotNo())
		{
			return;
		}

		final I_M_HU hu = handlingUnitsBL.getById(huId);
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext();
		final IAttributeStorage huAttributes = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
		huAttributes.setSaveOnChange(true);

		if (request.isSetBestBeforeDate())
		{
			huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, request.getBestBeforeDate());
		}
		if (request.isSetLotNo())
		{
			huAttributes.setValue(AttributeConstants.ATTR_LotNumber, request.getLotNo());
		}
	}

	@NonNull
	private HuId resolveHUIdentifier(@NonNull final JsonSetClearanceStatusRequest.JsonHUIdentifier jsonHuIdentifier)
	{
		if (jsonHuIdentifier.getMetasfreshId() != null)
		{
			return HuId.ofRepoId(jsonHuIdentifier.getMetasfreshId().getValue());
		}

		if (Check.isNotBlank((jsonHuIdentifier.getQrCode())))
		{
			final HUQRCode huQRCode = HUQRCode.fromGlobalQRCodeJsonString(jsonHuIdentifier.getQrCode());
			final HuId huId = huQRCodeService.getHuIdByQRCode(huQRCode);

			return HUTransformService.newInstance()
					.extractIfAggregatedByQRCode(huId, huQRCode);
		}

		throw new AdempiereException("MetasfreshId or QRCode must be provided!")
				.appendParametersToMessage()
				.setParameter("huIdentifier", jsonHuIdentifier);
	}
}
