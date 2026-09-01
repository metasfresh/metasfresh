package de.metas.manufacturing.workflows_api.activity_handlers.receive;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.frontend_testing.JsonTestId;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.attribute.json.JsonAttribute;
import de.metas.handlingunits.attribute.json.JsonAttributeListValue;
import de.metas.handlingunits.attribute.json.JsonAttributeValueType;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.manufacturing.config.FinishedGoodsReceiveLineConfig;
import de.metas.manufacturing.config.MobileUIManufacturingConfig;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import de.metas.manufacturing.config.ReceiveUnitType;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLine;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.json.JsonAllergen;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.json.JsonHazardSymbol;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonFinishedGoodsReceiveLine;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonHUQRCodeTargetConverters;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewLUTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewLUTargetsList;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewTUTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewTUTargetList;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.allergen.ProductAllergensService;
import de.metas.product.hazard_symbol.ProductHazardSymbolService;
import de.metas.scannable_code.format.json.JsonScannableCodeFormat;
import de.metas.scannable_code.format.service.ScannableCodeFormatService;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.api.Attribute;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.service.ClientId;
import org.adempiere.util.api.Params;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MaterialReceiptActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.materialReceipt");
	private static final UIComponentType COMPONENT_TYPE = UIComponentType.ofString("manufacturing/materialReceipt");

	// Guidance shown when no receiving Gebinde (TU/LU target) can be offered for the product. {0}=product name.
	private static final AdMessageKey MSG_NoReceivingGebinde = AdMessageKey.of("MaterialReceipt_NoReceivingGebinde");

	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUPIItemProductDAO huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IUOMDAO uomDao = Services.get(IUOMDAO.class);
	@NonNull private final HUQRCodesService huQRCodeService;
	@NonNull private final ProductHazardSymbolService productHazardSymbolService;
	@NonNull private final ProductAllergensService productAllergensService;
	@NonNull private final ScannableCodeFormatService scannableCodeFormatService;
	@NonNull private final MobileUIManufacturingConfigRepository mobileUIManufacturingConfigRepository;
	@NonNull private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		final ManufacturingJob job = ManufacturingMobileApplication.getManufacturingJob(wfProcess);

		final MobileUIManufacturingConfig config = mobileUIManufacturingConfigRepository.getConfig(job.getResponsibleId(), ClientId.METASFRESH);

		final ImmutableList<JsonFinishedGoodsReceiveLine> lines = job.getActivityById(wfActivity.getId())
				.getFinishedGoodsReceiveAssumingNotNull()
				.streamLines()
				.map(line -> toJson(line, job.getCustomerId(), config, jsonOpts))
				.collect(ImmutableList.toImmutableList());

		return UIComponent.builderFrom(COMPONENT_TYPE, wfActivity)
				.properties(Params.builder()
						.valueObj("lines", lines)
						.valueObj("readAttributes", config.getEditableAttributes())
						.valueObj(PROP_customQRCodeFormats, JsonScannableCodeFormat.ofCollection(scannableCodeFormatService.getAll()))
						.build())
				.build();
	}

	private JsonFinishedGoodsReceiveLine toJson(
			@NonNull final FinishedGoodsReceiveLine line,
			@Nullable final BPartnerId customerId,
			@NonNull final MobileUIManufacturingConfig config,
			@NonNull final JsonOpts jsonOpts)
	{
		final List<I_M_HU_PI_Item_Product> tuPIItemProducts = huPIItemProductDAO.retrieveTUs(
				Env.getCtx(),
				line.getProductId(),
				customerId,
				line.getCatchWeightUOMId() != null);

		final String adLanguage = jsonOpts.getAdLanguage();

		final boolean isMainFinishedGood = line.getCoProductBOMLineId() == null;
		final FinishedGoodsReceiveLineConfig lineConfig = config.effectiveForReceiveLine(isMainFinishedGood);

		// retrieveTUs is pinned to HU_UnitType='TU', so the virtual ('V') packing instruction never comes back from
		// it; add it here, as WEBUI_ProcessHelper#retrieveHUPIItemProductRecords(includeVirtualItem) does for the
		// WebUI. It carries a tuPIItemProductId and has no LU parent items, so it belongs to the TU list - hence
		// switching TU receiving off hides it too.
		final boolean offerVirtualTUTarget = lineConfig.isAllowReceiveToTU() && lineConfig.isAllowReceiveWithoutPackingItem();

		// A structure excluded by configuration comes out as an empty list WITHOUT an emptyReason: that reason is the
		// operator-facing no-receiving-Gebinde guidance and must only ever accompany "no target at all".
		final JsonNewTUTargetList tuTargetList = lineConfig.isAllowReceiveToTU()
				? getNewTUTargets(tuPIItemProducts, offerVirtualTUTarget, line.getProductId(), adLanguage)
				: JsonNewTUTargetList.ofList(ImmutableList.of());

		final JsonNewLUTargetsList newLUTargets;
		if (lineConfig.isAllowReceiveToLU())
		{
			newLUTargets = getNewLUTargets(tuPIItemProducts, offerVirtualTUTarget, line.getProductId(), customerId, adLanguage);
		}
		else if (lineConfig.isAllowReceiveToTU())
		{
			newLUTargets = JsonNewLUTargetsList.emptyWithoutReason();
		}
		else
		{
			// Both structures excluded by configuration: no target can be offered at all, so the guidance has to be
			// carried by one of the two lists - otherwise the operator faces an empty screen and a disabled quantity action.
			newLUTargets = JsonNewLUTargetsList.emptyBecause(noReceivingGebindeReason(line.getProductId(), adLanguage));
		}

		final ReceiveUnitType receiveUnitType = config.getReceiveUnitTypeEffective();

		final String uom;
		final java.math.BigDecimal qtyToReceive;
		final java.math.BigDecimal qtyReceived;

		if (receiveUnitType.isTU() && line.getTuPIItemProductId() != null)
		{
			final HUPIItemProduct huPIItemProduct = huPIItemProductDAO.getById(line.getTuPIItemProductId());
			final QtyTU qtyToReceiveTU = huPIItemProduct.computeQtyTUsOfTotalCUs(line.getQtyToReceive(), line.getProductId());
			final QtyTU qtyReceivedTU = huPIItemProduct.computeQtyTUsOfTotalCUs(line.getQtyReceived(), line.getProductId());
			qtyToReceive = qtyToReceiveTU.toBigDecimal();
			qtyReceived = qtyReceivedTU.toBigDecimal();
			uom = "TU";
		}
		else
		{
			qtyToReceive = line.getQtyToReceive().toBigDecimal();
			qtyReceived = line.getQtyReceived().toBigDecimal();
			uom = line.getQtyToReceive().getUOMSymbol();
		}

		final String catchWeightUomSymbol = lineConfig.isCaptureCatchWeight()
				? Optional.ofNullable(line.getCatchWeightUOMId())
						.map(uomDao::getById)
						.map(I_C_UOM::getUOMSymbol)
						.orElse(null)
				: null;

		return JsonFinishedGoodsReceiveLine.builder()
				.id(line.getId().toJson())
				.coproduct(!isMainFinishedGood)
				.skipReceiveTargetStep(lineConfig.isSkipReceiveTargetStep())
				.productName(line.getProductValueAndProductName().translate(adLanguage))
				.uom(uom)
				.hazardSymbols(getJsonHazardSymbols(line.getProductId(), adLanguage))
				.allergens(getJsonAllergens(line.getProductId(), adLanguage))
				.qtyToReceive(qtyToReceive)
				.qtyReceived(qtyReceived)
				.currentReceivingHU(JsonHUQRCodeTargetConverters.fromNullable(line.getReceivingTarget(), huQRCodeService))
				.availableReceivingTargets(newLUTargets)
				.availableReceivingTUTargets(tuTargetList)
				.catchWeightUomSymbol(catchWeightUomSymbol)
				.editableAttributes(buildEditableAttributes(line.getProductId(), config, adLanguage))
				.build();
	}

	/**
	 * Builds the generic, per-line editable-attribute list (issue #31771 Task 6): the config's editable-attribute
	 * codes, restricted to this product's {@code M_AttributeSet} (AC8) and to instance-level attributes only
	 * (AC10), in the config's {@code SeqNo} order (AC11; {@link MobileUIManufacturingConfig#getEditableAttributeCodesInOrder()}
	 * is already ordered). Applies uniformly to every line, main finished good or co-/by-product alike (AC9). No
	 * value is carried yet (AC4: nothing has been entered by the operator at this stage).
	 */
	@NonNull
	@VisibleForTesting
	List<JsonAttribute> buildEditableAttributes(
			@NonNull final ProductId productId,
			@NonNull final MobileUIManufacturingConfig config,
			@NonNull final String adLanguage)
	{
		final ImmutableList<AttributeCode> configuredCodes = config.getEditableAttributeCodesInOrder();
		if (configuredCodes.isEmpty())
		{
			return ImmutableList.of();
		}

		final AttributeSetId attributeSetId = productBL.getAttributeSetId(productId);
		if (attributeSetId.isNone())
		{
			return ImmutableList.of();
		}

		final ImmutableMap<AttributeCode, Attribute> instanceAttributesByCode = attributeDAO
				.retrieveAttributes(attributeSetId, /* isInstanceAttribute */true)
				.stream()
				.collect(ImmutableMap.toImmutableMap(Attribute::getAttributeCode, attribute -> attribute));

		final ImmutableList.Builder<JsonAttribute> result = ImmutableList.builder();
		for (final AttributeCode code : configuredCodes)
		{
			final Attribute attribute = instanceAttributesByCode.get(code);
			if (attribute != null)
			{
				result.add(toJsonAttribute(attribute, adLanguage));
			}
		}
		return result.build();
	}

	@NonNull
	private JsonAttribute toJsonAttribute(@NonNull final Attribute attribute, @NonNull final String adLanguage)
	{
		final JsonAttributeValueType valueType = JsonAttributeValueType.of(attribute.getValueType());
		final List<JsonAttributeListValue> listValues = valueType == JsonAttributeValueType.LIST
				? toJsonListValues(attribute, adLanguage)
				: null;

		return JsonAttribute.builder()
				.code(attribute.getAttributeCode())
				.caption(attribute.getDisplayName().translate(adLanguage))
				.valueType(valueType)
				.listValues(listValues)
				.build();
	}

	@NonNull
	private List<JsonAttributeListValue> toJsonListValues(@NonNull final Attribute attribute, @NonNull final String adLanguage)
	{
		return attributeDAO.retrieveAttributeValues(attribute)
				.stream()
				.filter(AttributeListValue::isActive)
				.map(listValue -> JsonAttributeListValue.builder()
						.value(listValue.getValue())
						.caption(listValue.getNameTrl().translate(adLanguage))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	private ImmutableList<JsonHazardSymbol> getJsonHazardSymbols(final @NonNull ProductId productId, final String adLanguage)
	{
		return productHazardSymbolService.getHazardSymbolsByProductId(productId)
				.stream()
				.map(hazardSymbol -> JsonHazardSymbol.of(hazardSymbol, adLanguage))
				.collect(ImmutableList.toImmutableList());
	}

	private ImmutableList<JsonAllergen> getJsonAllergens(final @NonNull ProductId productId, final String adLanguage)
	{
		return productAllergensService.getAllergensByProductId(productId)
				.stream()
				.map(allergen -> JsonAllergen.of(allergen, adLanguage))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	@VisibleForTesting
	JsonNewLUTargetsList getNewLUTargets(
			@NonNull final List<I_M_HU_PI_Item_Product> tuPIItemProducts,
			final boolean offerVirtualTUTarget,
			@NonNull final ProductId productId,
			@Nullable final BPartnerId customerId,
			@NonNull final String adLanguage)
	{
		if (tuPIItemProducts.isEmpty())
		{
			// The virtual packing instruction has no LU parent items, so it is never an LU target - but a target
			// does exist (in the TU list), so the guidance would contradict the screen the operator sees.
			return offerVirtualTUTarget
					? JsonNewLUTargetsList.emptyWithoutReason()
					: JsonNewLUTargetsList.emptyBecause(noReceivingGebindeReason(productId, adLanguage));
		}

		final ArrayList<JsonNewLUTarget> targets = new ArrayList<>();
		final ArrayList<String> debugMessages = new ArrayList<>();
		for (final I_M_HU_PI_Item_Product tuPIItemProduct : tuPIItemProducts)
		{
			final HuPackingInstructionsItemId tuPackingInstructionsItemId = HuPackingInstructionsItemId.ofRepoId(tuPIItemProduct.getM_HU_PI_Item_ID());
			final HuPackingInstructionsId tuPackingInstructionsId = handlingUnitsBL.getPackingInstructionsId(tuPackingInstructionsItemId);

			final List<I_M_HU_PI_Item> luPackingInstructionsItems = handlingUnitsBL.retrieveParentPIItemsForParentPI(
					tuPackingInstructionsId,
					X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit,
					customerId);

			if (!luPackingInstructionsItems.isEmpty())
			{
				for (final I_M_HU_PI_Item luPackingInstructionsItem : luPackingInstructionsItems)
				{
					targets.add(
							JsonNewLUTarget.builder()
									.luCaption(handlingUnitsBL.getPI(luPackingInstructionsItem).getName())
									.tuCaption(tuPIItemProduct.getName())
									.luPIItemId(HuPackingInstructionsItemId.ofRepoId(luPackingInstructionsItem.getM_HU_PI_Item_ID()))
									.tuPIItemProductId(HUPIItemProductId.ofRepoId(tuPIItemProduct.getM_HU_PI_Item_Product_ID()))
									.testId(extractNewLUTargetTestId(luPackingInstructionsItem))
									.build());
				}
			}
			else
			{
				debugMessages.add("Ignoring " + tuPackingInstructionsId + " (" + tuPIItemProduct + ") because it has no LU PI Items");
			}
		}

		if (targets.isEmpty())
		{
			return JsonNewLUTargetsList.emptyBecause("None of the TUs found are assigned to an LU", debugMessages);
		}
		else
		{
			return JsonNewLUTargetsList.ofList(targets, debugMessages);
		}
	}

	public static JsonTestId extractNewLUTargetTestId(final I_M_HU_PI_Item luPackingInstructionsItem)
	{
		return JsonTestId.ofString("luPIItem-" + luPackingInstructionsItem.getM_HU_PI_Item_ID());
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return wfActivity.getStatus();
	}

	@NonNull
	@VisibleForTesting
	JsonNewTUTargetList getNewTUTargets(
			@NonNull final List<I_M_HU_PI_Item_Product> tuPIItemProducts,
			final boolean offerVirtualTUTarget,
			@NonNull final ProductId productId,
			@NonNull final String adLanguage)
	{
		if (tuPIItemProducts.isEmpty() && !offerVirtualTUTarget)
		{
			return JsonNewTUTargetList.emptyBecause(noReceivingGebindeReason(productId, adLanguage));
		}

		final ImmutableList.Builder<JsonNewTUTarget> targets = ImmutableList.builder();
		tuPIItemProducts.stream()
				.map(MaterialReceiptActivityHandler::toJsonNewTUTarget)
				.forEach(targets::add);

		if (offerVirtualTUTarget)
		{
			targets.add(toJsonNewTUTarget(huPIItemProductDAO.retrieveVirtualPIMaterialItemProduct(Env.getCtx())));
		}

		return JsonNewTUTargetList.ofList(targets.build());
	}

	/** Localized, actionable guidance shown when no receiving Gebinde can be offered for the product. */
	private String noReceivingGebindeReason(@NonNull final ProductId productId, @NonNull final String adLanguage)
	{
		return msgBL.getMsg(adLanguage, MSG_NoReceivingGebinde, new Object[]{productBL.getProductName(productId)});
	}

	private static JsonNewTUTarget toJsonNewTUTarget(final I_M_HU_PI_Item_Product target)
	{
		return JsonNewTUTarget.builder()
				.caption(target.getName())
				.tuPIItemProductId(HUPIItemProductId.ofRepoId(target.getM_HU_PI_Item_Product_ID()))
				.testId(extractNewTUTargetTestId(target))
				.build();
	}

	public static JsonTestId extractNewTUTargetTestId(final I_M_HU_PI_Item_Product target)
	{
		return JsonTestId.ofString("tuPIItemProduct-" + target.getM_HU_PI_Item_Product_ID());
	}
}
