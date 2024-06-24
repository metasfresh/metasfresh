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
import de.metas.common.handlingunits.JsonAllowedHUClearanceStatuses;
import de.metas.common.handlingunits.JsonDisposalReason;
import de.metas.common.handlingunits.JsonDisposalReasonsList;
import de.metas.common.handlingunits.JsonGetSingleHUResponse;
import de.metas.common.handlingunits.JsonHU;
import de.metas.common.handlingunits.JsonHUAttribute;
import de.metas.common.handlingunits.JsonHUAttributes;
import de.metas.common.handlingunits.JsonHUAttributesRequest;
import de.metas.common.handlingunits.JsonHUProduct;
import de.metas.common.handlingunits.JsonHUType;
import de.metas.common.handlingunits.JsonSetClearanceStatusRequest;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAttribute;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.handlingunits.qrcodes.service.HUQRCodeGenerateRequest;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.rest_api.move_hu.MoveHURequest;
import de.metas.inventory.InventoryCandidateService;
import de.metas.product.IProductBL;
import de.metas.rest_api.utils.v2.JsonErrors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.MimeType;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Supplier;

import static de.metas.common.rest_api.v2.APIConstants.ENDPOINT_MATERIAL;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.HU_IDENTIFIER_DOC;

@RequestMapping(value = { HandlingUnitsRestController.HU_REST_CONTROLLER_PATH })
@RestController
@Profile(Profiles.PROFILE_App)
public class HandlingUnitsRestController
{
	public static final String HU_REST_CONTROLLER_PATH = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + ENDPOINT_MATERIAL + "/handlingunits";

	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final InventoryCandidateService inventoryCandidateService;
	private final HandlingUnitsService handlingUnitsService;
	private final HUQRCodesService huQRCodesService;

	public HandlingUnitsRestController(
			@NonNull final InventoryCandidateService inventoryCandidateService,
			@NonNull final HandlingUnitsService handlingUnitsService,
			@NonNull final HUQRCodesService huQRCodesService)
	{
		this.inventoryCandidateService = inventoryCandidateService;
		this.handlingUnitsService = handlingUnitsService;
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

	@PostMapping("/byQRCode")
	public ResponseEntity<JsonGetSingleHUResponse> getByQRCode(
			@RequestBody @NonNull final JsonGetByQRCodeRequest request)
	{
		final ResponseEntity<JsonGetSingleHUResponse> responseEntity = getByIdSupplier(() -> {
			final GlobalQRCode globalQRCode = GlobalQRCode.parse(request.getQrCode()).orNullIfError();
			if (globalQRCode != null)
			{
				final HUQRCode huQRCode = HUQRCode.fromGlobalQRCode(globalQRCode);
				return huQRCodesService.getHuIdByQRCodeIfExists(huQRCode).orElse(null);
			}
			else
			{
				return HuId.ofHUValue(request.getQrCode());
			}
		}, request.isIncludeAllowedClearanceStatuses());

		//
		// If no HU was found for given QR Code then try to directly convert given QR code to a "new HU"
		if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND)
		{
			try
			{
				final HUQRCode huQRCode = HUQRCode.fromGlobalQRCodeJsonString(request.getQrCode());
				final JsonHU jsonHU = toNewJsonHU(huQRCode);
				return ResponseEntity.ok(JsonGetSingleHUResponse.ofResult(jsonHU));
			}
			catch (Exception ex)
			{
				return toBadRequestResponseEntity(ex);
			}
		}

		return responseEntity;
	}

	@GetMapping("/byId/{M_HU_ID}")
	public ResponseEntity<JsonGetSingleHUResponse> getById(
			@ApiParam(required = true, value = HU_IDENTIFIER_DOC) //
			@PathVariable("M_HU_ID") final int huRepoId)
	{
		return getByIdSupplier(() -> HuId.ofRepoId(huRepoId));
	}

	@NonNull
	private ResponseEntity<JsonGetSingleHUResponse> getByIdSupplier(@NonNull final Supplier<HuId> huIdSupplier)
	{
		final boolean getAllowedClearanceStatuses = false;
		return getByIdSupplier(huIdSupplier, getAllowedClearanceStatuses);
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
					.result(handlingUnitsService.toJson(LoadJsonHURequest.ofHUAndLanguage(hu, adLanguage)))
					.build());
		}
		catch (final Exception ex)
		{
			return toBadRequestResponseEntity(ex);
		}
	}

	@PostMapping("/byId/{M_HU_ID}/dispose")
	public void disposeWholeHU(
			@PathVariable("M_HU_ID") final int huRepoId,
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

	@PutMapping
	public ResponseEntity<?> updateAttributes(@RequestBody @NonNull final JsonHUAttributesRequest request)
	{
		try
		{
			final HuId huId = HuId.ofRepoId(Integer.parseInt(request.getHuId()));

			handlingUnitsService.updateAttributes(huId, request.getAttributes());

			return ResponseEntity.ok().build();
		}
		catch (final Exception e)
		{
			return ResponseEntity.badRequest().body(e);
		}
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
	public ResponseEntity<?> generateQRCodes(@RequestBody @NonNull final JsonQRCodesGenerateRequest jsonRequest)
	{
		final int count = jsonRequest.getCount();
		if (count >= 100)
		{
			throw new AdempiereException("Maximum allowed count is 100");
		}

		final HUQRCodeGenerateRequest request = JsonQRCodesGenerateRequestConverters.toHUQRCodeGenerateRequest(jsonRequest, attributeDAO);
		final List<HUQRCode> qrCodes = huQRCodesService.generate(request);
		final QRCodePDFResource pdf = huQRCodesService.createPDF(qrCodes);

		if (jsonRequest.isOnlyPrint())
		{
			huQRCodesService.print(pdf);
			return ResponseEntity.ok().body(null);
		}
		else
		{
			final String filename = StringUtils.trimBlankToOptional(pdf.getFilename()).orElse("qrCodes.pdf");
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MimeType.getMediaType(filename));
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"");
			headers.set("AD_PInstance_ID", String.valueOf(pdf.getPinstanceId().getRepoId()));
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
		}
	}

	@PutMapping("/clearance")
	public ResponseEntity<?> setHUClearanceStatus(
			@RequestBody @NonNull final JsonSetClearanceStatusRequest request)
	{
		handlingUnitsService.setClearanceStatus(request);

		return ResponseEntity.ok().body(null);
	}

	@GetMapping("/byId/{M_HU_ID}/allowedClearanceStatuses")
	public ResponseEntity<JsonAllowedHUClearanceStatuses> getAllowedClearanceStatuses(@PathVariable("M_HU_ID") final int huId)
	{
		return ResponseEntity.ok().body(handlingUnitsService.getAllowedClearanceStatusesForHUId(HuId.ofRepoId(huId)));
	}

	@PostMapping("/move")
	public ResponseEntity<JsonGetSingleHUResponse> moveHU(
			@RequestBody @NonNull final JsonMoveHURequest request)
	{
		final HUQRCode huQRCode = HUQRCode.fromGlobalQRCodeJsonString(request.getHuQRCode());

		handlingUnitsService.move(MoveHURequest.builder()
				.huId(request.getHuId())
				.huQRCode(huQRCode)
				.targetQRCode(GlobalQRCode.ofString(request.getTargetQRCode()))
				.build());

		// IMPORTANT: don't retrieve by ID because the ID might be different
		// (e.g. we extracted one TU from an aggregated TU),
		// but the QR Code is always the same.
		return getByIdSupplier(() -> huQRCodesService.getHuIdByQRCode(huQRCode));
	}

	@Deprecated
	@PutMapping("/byId/{M_HU_ID}/qty")
	public ResponseEntity<JsonGetSingleHUResponse> changeHUQty(
			@PathVariable("M_HU_ID") final int huId,
			@RequestBody @NonNull final JsonHUQtyChangeRequest request)
	{
		return changeHUQty(request.withHuIdAndValidate(HuId.ofRepoId(huId)));
	}

	@PostMapping("/qty")
	public ResponseEntity<JsonGetSingleHUResponse> changeHUQty(@RequestBody @NonNull final JsonHUQtyChangeRequest request)
	{
		final HuId huId = handlingUnitsService.updateQty(request);
		return getByIdSupplier(() -> huId);
	}

	@NonNull
	private ResponseEntity<JsonGetSingleHUResponse> getByIdSupplier(@NonNull final Supplier<HuId> huIdSupplier, final boolean getAllowedClearanceStatuses)
	{
		final String adLanguage = Env.getADLanguageOrBaseLanguage();

		try
		{
			final HuId huId = huIdSupplier.get();
			if (huId == null)
			{
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok(JsonGetSingleHUResponse.builder()
					.result(handlingUnitsService
							.getFullHU(huId, adLanguage, getAllowedClearanceStatuses)
							.withIsDisposalPending(inventoryCandidateService.isDisposalPending(huId)))
					.build());
		}
		catch (final Exception e)
		{
			return toBadRequestResponseEntity(e);
		}
	}

	private JsonHU toNewJsonHU(final @NonNull HUQRCode huQRCode)
	{
		final I_M_Product product = productBL.getById(huQRCode.getProductId());
		final I_C_UOM uom = productBL.getStockUOM(product);

		return JsonHU.builder()
				.id(null)
				.huStatusCaption("-")
				.displayName(huQRCode.getPackingInfo().getCaption())
				.qrCode(HandlingUnitsService.toJsonHUQRCode(huQRCode))
				.warehouseValue(null)
				.locatorValue(null)
				.product(JsonHUProduct.builder()
						.productValue(product.getValue())
						.productName(product.getName())
						.qty("0")
						.uom(uom.getX12DE355())
						.build())
				.attributes2(JsonHUAttributes.builder()
						.list(huQRCode.getAttributes().stream()
								.map(this::toJsonHUAttribute)
								.collect(ImmutableList.toImmutableList()))
						.build())
				.jsonHUType(toJsonHUType(huQRCode.getPackingInfo().getHuUnitType()))
				.build();
	}

	private JsonHUAttribute toJsonHUAttribute(final HUQRCodeAttribute huQRCodeAttribute)
	{
		final AttributeCode attributeCode = huQRCodeAttribute.getCode();
		final I_M_Attribute attribute = attributeDAO.getAttributeByCode(attributeCode);
		return JsonHUAttribute.builder()
				.code(attributeCode.getCode())
				.caption(attribute.getName())
				.value(huQRCodeAttribute.getValueRendered())
				.build();
	}

	private static JsonHUType toJsonHUType(@NonNull HUQRCodeUnitType huUnitType)
	{
		switch (huUnitType)
		{
			case LU:
				return JsonHUType.LU;
			case TU:
				return JsonHUType.TU;
			case VHU:
				return JsonHUType.CU;
			default:
				throw new AdempiereException("Unknown HU Unit Type: " + huUnitType);
		}
	}

	private static @NonNull ResponseEntity<JsonGetSingleHUResponse> toBadRequestResponseEntity(final Exception e)
	{
		final String adLanguage = Env.getADLanguageOrBaseLanguage();
		return ResponseEntity.badRequest().body(JsonGetSingleHUResponse.ofError(JsonErrors.ofThrowable(e, adLanguage)));
	}

}
