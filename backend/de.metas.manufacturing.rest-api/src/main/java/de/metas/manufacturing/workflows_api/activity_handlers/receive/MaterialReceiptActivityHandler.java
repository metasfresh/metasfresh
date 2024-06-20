package de.metas.manufacturing.workflows_api.activity_handlers.receive;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLine;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.json.JsonAllergen;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.json.JsonHazardSymbol;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonFinishedGoodsReceiveLine;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonHUQRCodeTargetConverters;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewLUTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewLUTargetsList;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.allergen.ProductAllergensService;
import de.metas.product.hazard_symbol.ProductHazardSymbolService;
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
import org.adempiere.util.api.Params;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Component
public class MaterialReceiptActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.materialReceipt");
	private static final UIComponentType COMPONENT_TYPE = UIComponentType.ofString("manufacturing/materialReceipt");

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUPIItemProductDAO huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IBPartnerBL bpartnerBL;
	private final HUQRCodesService huQRCodeService;
	private final ProductHazardSymbolService productHazardSymbolService;
	private final ProductAllergensService productAllergensService;

	public MaterialReceiptActivityHandler(
			final @NonNull IBPartnerBL bpartnerBL,
			final @NonNull HUQRCodesService huQRCodeService,
			final @NonNull ProductHazardSymbolService productHazardSymbolService,
			final @NonNull ProductAllergensService productAllergensService)
	{
		this.bpartnerBL = bpartnerBL;
		this.huQRCodeService = huQRCodeService;
		this.productHazardSymbolService = productHazardSymbolService;
		this.productAllergensService = productAllergensService;
	}

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		final ManufacturingJob job = ManufacturingMobileApplication.getManufacturingJob(wfProcess);
		final ImmutableList<JsonFinishedGoodsReceiveLine> lines = job.getActivityById(wfActivity.getId())
				.getFinishedGoodsReceiveAssumingNotNull()
				.streamLines()
				.map(line -> toJson(line, job.getCustomerId(), jsonOpts))
				.collect(ImmutableList.toImmutableList());

		return UIComponent.builderFrom(COMPONENT_TYPE, wfActivity)
				.properties(Params.builder()
						.valueObj("lines", lines)
						.build())
				.build();
	}

	private JsonFinishedGoodsReceiveLine toJson(
			@NonNull final FinishedGoodsReceiveLine line,
			@Nullable final BPartnerId customerId,
			@NonNull final JsonOpts jsonOpts)
	{
		final JsonNewLUTargetsList newLUTargets = getNewLUTargets(line.getProductId(), customerId);
		final String adLanguage = jsonOpts.getAdLanguage();

		return JsonFinishedGoodsReceiveLine.builder()
				.id(line.getId().toJson())
				.coproduct(line.getCoProductBOMLineId() != null)
				.productName(line.getProductName().translate(adLanguage))
				.uom(line.getQtyToReceive().getUOMSymbol())
				.hazardSymbols(getJsonHazardSymbols(line.getProductId(), adLanguage))
				.allergens(getJsonAllergens(line.getProductId(), adLanguage))
				.qtyToReceive(line.getQtyToReceive().toBigDecimal())
				.qtyReceived(line.getQtyReceived().toBigDecimal())
				.currentReceivingHU(JsonHUQRCodeTargetConverters.fromNullable(line.getReceivingTarget(), huQRCodeService))
				.availableReceivingTargets(newLUTargets)
				.build();
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
	private JsonNewLUTargetsList getNewLUTargets(@NonNull final ProductId productId, final @Nullable BPartnerId customerId)
	{
		final List<I_M_HU_PI_Item_Product> tuPIItemProducts = huPIItemProductDAO.retrieveTUs(Env.getCtx(), productId, customerId, false);
		if (tuPIItemProducts.isEmpty())
		{
			return JsonNewLUTargetsList.emptyBecause("No CU/TU associations found for "
					+ productBL.getProductName(productId)
					+ " and " + (customerId != null ? bpartnerBL.getBPartnerName(customerId) : "any customer"));
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

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return wfActivity.getStatus();
	}
}
