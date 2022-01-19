/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
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
import de.metas.Profiles;
import de.metas.common.handlingunits.JsonDisposalReason;
import de.metas.common.handlingunits.JsonDisposalReasonsList;
import de.metas.common.handlingunits.JsonGetSingleHUResponse;
import de.metas.common.handlingunits.JsonHU;
import de.metas.common.handlingunits.JsonHUAttributes;
import de.metas.common.handlingunits.JsonHUProduct;
import de.metas.handlingunits.HUBarcode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodeGenerateRequest;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.i18n.TranslatableStrings;
import de.metas.inventory.InventoryCandidateService;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import de.metas.rest_api.utils.v2.JsonErrors;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.WarehouseAndLocatorValue;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.MimeType;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

@RequestMapping(HandlingUnitsRestController.ENDPOINT)
@RestController
@Profile(Profiles.PROFILE_App)
public class HandlingUnitsRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/hu";

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final InventoryCandidateService inventoryCandidateService;
	private final HUQRCodesService huQRCodesService;

	public HandlingUnitsRestController(
			@NonNull final InventoryCandidateService inventoryCandidateService,
			@NonNull final HUQRCodesService huQRCodesService)
	{
		this.inventoryCandidateService = inventoryCandidateService;
		this.huQRCodesService = huQRCodesService;
	}

	@GetMapping("/bySerialNo/{serialNo}")
	public ResponseEntity<JsonGetSingleHUResponse> getBySerialNo(
			@PathVariable("serialNo") @NonNull final String serialNo)
	{
		return toSingleHUResponseEntity(() -> retrieveActiveHUIdBySerialNo(serialNo));
	}

	private I_M_HU retrieveActiveHUIdBySerialNo(final @NonNull String serialNo)
	{
		final List<I_M_HU> hus = handlingUnitsDAO
				.createHUQueryBuilder()
				.setHUStatus(X_M_HU.HUSTATUS_Active)
				.addOnlyWithAttribute(AttributeConstants.ATTR_SerialNo, serialNo)
				.list();
		if (hus.isEmpty())
		{
			throw new AdempiereException("No active HU found for serialNo=`" + serialNo + "`");
		}
		else if (hus.size() == 1)
		{
			return hus.get(0);
		}
		else
		{
			throw new AdempiereException("More than one active HUs found for serialNo=`" + serialNo + "`:" + hus);
		}
	}

	@GetMapping("/byBarcode/{barcode}")
	public ResponseEntity<JsonGetSingleHUResponse> getByBarcode(
			@PathVariable("barcode") @NonNull final String barcodeStr)
	{
		final HuId huId = HUBarcode.ofBarcodeString(barcodeStr).toHuId();

		return toSingleHUResponseEntity(() -> {
			final I_M_HU hu = handlingUnitsBL.getById(huId);
			if (hu == null)
			{
				throw new AdempiereException("No HU found for barcode: " + barcodeStr);
			}
			return hu;

		});
	}

	@GetMapping("/byId/{id}")
	public ResponseEntity<JsonGetSingleHUResponse> getById(
			@PathVariable("id") final int huRepoId)
	{
		final HuId huId = HuId.ofRepoId(huRepoId);

		return toSingleHUResponseEntity(() -> {
			final I_M_HU hu = handlingUnitsBL.getById(huId);
			if (hu == null)
			{
				throw new AdempiereException("No HU found for ID: " + huRepoId);
			}
			return hu;
		});
	}

	private ResponseEntity<JsonGetSingleHUResponse> toSingleHUResponseEntity(@NonNull final Supplier<I_M_HU> huSupplier)
	{
		final String adLanguage = Env.getADLanguageOrBaseLanguage();

		try
		{
			final I_M_HU hu = huSupplier.get();
			if (hu == null)
			{
				throw new AdempiereException("No HU found");
			}

			return ResponseEntity.ok(JsonGetSingleHUResponse.builder()
					.result(toJson(hu, adLanguage))
					.build());
		}
		catch (final Exception ex)
		{
			return ResponseEntity.badRequest().body(JsonGetSingleHUResponse.builder()
					.error(JsonErrors.ofThrowable(ex, adLanguage))
					.build());
		}
	}

	private JsonHU toJson(@NonNull final I_M_HU hu, @NonNull final String adLanguage)
	{
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext();

		final ImmutableList<JsonHUProduct> jsonHUProducts = huContext.getHUStorageFactory()
				.getStorage(hu)
				.streamProductStorages()
				.map(this::toJson)
				.collect(ImmutableList.toImmutableList());

		final ImmutableAttributeSet huAttributes = huContext.getHUAttributeStorageFactory()
				.getImmutableAttributeSet(hu);
		final JsonHUAttributes jsonHUAttributes = toJson(huAttributes);

		for (final ExtractCounterAttributesCommand.CounterAttribute counterAttribute : extractCounterAttributes(huAttributes))
		{
			jsonHUAttributes.putAttribute(counterAttribute.getAttributeCode(), counterAttribute.getCounter());
		}

		final WarehouseAndLocatorValue warehouseAndLocatorValue = hu.getM_Locator_ID() > 0
				? warehouseDAO.retrieveWarehouseAndLocatorValueByLocatorRepoId(hu.getM_Locator_ID())
				: null;

		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

		return JsonHU.builder()
				.id(String.valueOf(huId.getRepoId()))
				.huStatus(hu.getHUStatus())
				.huStatusCaption(TranslatableStrings.adRefList(X_M_HU.HUSTATUS_AD_Reference_ID, hu.getHUStatus()).translate(adLanguage))
				.displayName(handlingUnitsBL.getDisplayName(hu))
				.barcode(HUBarcode.ofHuId(huId).getAsString())
				.warehouseValue(warehouseAndLocatorValue != null ? warehouseAndLocatorValue.getWarehouseValue() : null)
				.locatorValue(warehouseAndLocatorValue != null ? warehouseAndLocatorValue.getLocatorValue() : null)
				.products(jsonHUProducts)
				.attributes(jsonHUAttributes)
				.build();
	}

	private List<ExtractCounterAttributesCommand.CounterAttribute> extractCounterAttributes(@NonNull final ImmutableAttributeSet huAttributes)
	{
		return new ExtractCounterAttributesCommand(huAttributes).execute();
	}

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

	@PostMapping("/byId/{id}/dispose")
	public void disposeWholeHU(
			@PathVariable("id") final int huRepoId,
			@RequestParam("reasonCode") final String reasonCodeStr)
	{
		final HuId huId = HuId.ofRepoId(huRepoId);
		final QtyRejectedReasonCode reasonCode = QtyRejectedReasonCode.ofCode(reasonCodeStr);
		inventoryCandidateService.createDisposeCandidates(huId, reasonCode);
	}

	@GetMapping("/disposalReasons")
	public JsonDisposalReasonsList getDisposalReasons()
	{
		final String adLanguage = Env.getADLanguageOrBaseLanguage();
		return toJsonDisposalReasonsList(inventoryCandidateService.getDisposalReasons(), adLanguage);
	}

	private static JsonDisposalReasonsList toJsonDisposalReasonsList(final IADReferenceDAO.ADRefList adRefList, final String adLanguage)
	{
		return JsonDisposalReasonsList.builder()
				.reasons(adRefList.getItems()
						.stream()
						.map(item -> JsonDisposalReason.builder()
								.key(item.getValue())
								.caption(item.getName().translate(adLanguage))
								.build())
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@PostMapping("/qrCodes/generate")
	public ResponseEntity<?> generateQRCodes(@RequestBody @NonNull final JsonQRCodesGenerateRequest request)
	{
		final List<HUQRCode> qrCodes = huQRCodesService.generate(toHUQRCodeGenerateRequest(request));
		final Resource pdf = huQRCodesService.createPDF(qrCodes, request.isOnlyPrint());

		if (request.isOnlyPrint())
		{
			return ResponseEntity.ok().body(null);
		}
		else
		{
			final String filename = StringUtils.trimBlankToOptional(pdf.getFilename()).orElse("qrCodes.pdf");
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MimeType.getMediaType(filename));
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"");
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
		}

	}

	private HUQRCodeGenerateRequest toHUQRCodeGenerateRequest(final JsonQRCodesGenerateRequest json)
	{
		final int count = json.getCount();
		if (count >= 100)
		{
			throw new AdempiereException("Maximum allowed count is 100");
		}

		return HUQRCodeGenerateRequest.builder()
				.count(count)
				.huUnitType(json.getHuUnitType())
				.productId(json.getProductId())
				.attributes(json.getAttributes()
						.stream()
						.map(this::toHUQRCodeGenerateRequestAttribute)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private HUQRCodeGenerateRequest.Attribute toHUQRCodeGenerateRequestAttribute(final JsonQRCodesGenerateRequest.Attribute json)
	{
		final I_M_Attribute attribute = attributeDAO.retrieveAttributeByValue(json.getAttributeCode());
		final AttributeId attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());

		final HUQRCodeGenerateRequest.Attribute.AttributeBuilder resultBuilder = HUQRCodeGenerateRequest.Attribute.builder()
				.attributeId(attributeId);

		return AttributeValueType.ofCode(attribute.getAttributeValueType())
				.map(new AttributeValueType.CaseMapper<HUQRCodeGenerateRequest.Attribute>()
				{
					@Override
					public HUQRCodeGenerateRequest.Attribute string()
					{
						return resultBuilder.valueString(json.getValue()).build();
					}

					@Override
					public HUQRCodeGenerateRequest.Attribute number()
					{
						final BigDecimal valueNumber = NumberUtils.asBigDecimal(json.getValue());
						return resultBuilder.valueNumber(valueNumber).build();
					}

					@Override
					public HUQRCodeGenerateRequest.Attribute date()
					{
						final LocalDate valueDate = StringUtils.trimBlankToOptional(json.getValue()).map(LocalDate::parse).orElse(null);
						return resultBuilder.valueDate(valueDate).build();
					}

					@Override
					public HUQRCodeGenerateRequest.Attribute list()
					{
						final String listItemCode = json.getValue();
						if (listItemCode != null)
						{
							final AttributeListValue listItem = attributeDAO.retrieveAttributeValueOrNull(attributeId, listItemCode);
							if (listItem == null)
							{
								throw new AdempiereException("No M_AttributeValue_ID found for " + attributeId + " and `" + listItemCode + "`");
							}
							return resultBuilder.valueListId(listItem.getId()).build();
						}
						else
						{
							return resultBuilder.valueListId(null).build();
						}
					}
				});
	}
}
