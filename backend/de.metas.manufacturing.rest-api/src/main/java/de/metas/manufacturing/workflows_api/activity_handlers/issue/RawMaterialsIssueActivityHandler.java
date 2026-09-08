package de.metas.manufacturing.workflows_api.activity_handlers.issue;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.ADRefList;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.generichumodel.HUType;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.picking.QtyRejectedReasonContext;
import de.metas.manufacturing.config.MobileUIManufacturingConfig;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.RawMaterialsIssueLine;
import de.metas.manufacturing.job.model.RawMaterialsIssueStep;
import de.metas.manufacturing.job.service.ManufacturingJobService;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.json.JsonAllergen;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.json.JsonHazardSymbol;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.json.JsonRawMaterialsIssueLine;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.json.JsonRejectReasonsList;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.json.JsonScaleDevice;
import de.metas.product.ProductId;
import de.metas.product.allergen.ProductAllergensService;
import de.metas.product.hazard_symbol.ProductHazardSymbolService;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.adempiere.service.ClientId;
import org.adempiere.util.api.Params;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
@RequiredArgsConstructor
public class RawMaterialsIssueActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.rawMaterialsIssue");
	private static final UIComponentType COMPONENT_TYPE = UIComponentType.ofString("manufacturing/rawMaterialsIssue");

	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);

	@NonNull private final ManufacturingJobService manufacturingJobService;
	@NonNull private final ProductHazardSymbolService productHazardSymbolService;
	@NonNull private final ProductAllergensService productAllergensService;
	@NonNull private final ADReferenceService adReferenceService;
	@NonNull private final MobileUIManufacturingConfigRepository mobileUIManufacturingConfigRepository;

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		final ManufacturingJob job = ManufacturingMobileApplication.getManufacturingJob(wfProcess);
		final MobileUIManufacturingConfig emptyingHUsConfig = resolveEmptyingHUsConfig(job);
		final boolean offerEmptyingHUs = emptyingHUsConfig.getIsAllowEmptyingHUs().isTrue();
		final boolean confirmEmptyingHU = emptyingHUsConfig.getIsConfirmEmptyingHU().isTrue();

		return UIComponent.builderFrom(COMPONENT_TYPE, wfActivity)
				.properties(Params.builder()
						.valueObj("scaleDevice", getCurrentScaleDevice(job, jsonOpts))
						.valueObj("lines", getLines(job, wfActivity.getId(), jsonOpts, offerEmptyingHUs))
						.valueObj("qtyRejectedReasons", getJsonRejectReasonsList(jsonOpts, offerEmptyingHUs))
						// Literal key (Params.valueObj, not a getter-derived name): the client reads
						// componentProps.confirmEmptyingHU. Gates whether the mobile UI prompts before
						// booking the "empty (auto. inventory)" write-off (cf. isShowPromptWhenOverPicking).
						.valueObj("confirmEmptyingHU", confirmEmptyingHU)
						.build())
				.build();
	}

	@Nullable
	private JsonScaleDevice getCurrentScaleDevice(final ManufacturingJob job, final @NonNull JsonOpts jsonOpts)
	{
		return manufacturingJobService.getCurrentScaleDevice(job)
				.map(scaleDevice -> JsonScaleDevice.of(scaleDevice, jsonOpts.getAdLanguage()))
				.orElse(null);
	}

	private ImmutableList<JsonRawMaterialsIssueLine> getLines(final ManufacturingJob job, final @NonNull WFActivityId wfActivityId, final @NonNull JsonOpts jsonOpts, final boolean offerEmptyingHUs)
	{
		return job.getActivityById(wfActivityId)
				.getRawMaterialsIssueAssumingNotNull()
				.getLines().stream()
				.map(line -> toJson(line, jsonOpts, offerEmptyingHUs))
				.collect(ImmutableList.toImmutableList());
	}

	private JsonRawMaterialsIssueLine toJson(final @NonNull RawMaterialsIssueLine line, final @NonNull JsonOpts jsonOpts, final boolean offerEmptyingHUs)
	{
		final ImmutableList<RawMaterialsIssueStep> stepsWithAllowEmptying = line.getSteps().stream()
				.map(step -> step.withAllowEmptying(isAllowEmptying(step, offerEmptyingHUs)))
				.collect(ImmutableList.toImmutableList());

		// The per-step flag (above) is authoritative. This line-level flag is only a coarse hint
		// ("at least one of this line's steps allows emptying") kept for consumers that need a single boolean per line.
		final boolean lineAllowsEmptying = stepsWithAllowEmptying.stream().anyMatch(RawMaterialsIssueStep::isAllowEmptying);

		final RawMaterialsIssueLine enrichedLine = line.toBuilder()
				.steps(stepsWithAllowEmptying)
				.isAllowEmptying(lineAllowsEmptying)
				.build();

		return JsonRawMaterialsIssueLine.builderFrom(enrichedLine, jsonOpts)
				.hazardSymbols(getJsonHazardSymbols(line.getProductId(), jsonOpts.getAdLanguage()))
				.allergens(getJsonAllergens(line.getProductId(), jsonOpts.getAdLanguage()))
				.build();
	}

	/** @return {@code true} if the step's source HU may be written off via the "empty (auto. inventory)" reason. */
	private boolean isAllowEmptying(@NonNull final RawMaterialsIssueStep step, final boolean offerEmptyingHUs)
	{
		if (!offerEmptyingHUs)
		{
			return false;
		}

		final I_M_HU hu = handlingUnitsBL.getById(step.getIssueFromHU().getId());
		return isAllowEmptying(hu);
	}

	private boolean isAllowEmptying(@NonNull final I_M_HU hu)
	{
		if (handlingUnitsBL.isAggregateHU(hu)) { return false; }          // orthogonal to unit type

		final HUType huType = HUType.ofCodeOrNull(handlingUnitsBL.getHU_UnitType(hu));
		if (huType == null) { return false; }                             // getHU_UnitType is @Nullable

		switch (huType)
		{
			case TransportUnit:
			case VirtualPI:
				return isSingleProductStorage(hu);
			case LoadLogistiqueUnit:
				return false;
			default:
				return false;                                             // a future unit type is not silently accepted
		}
	}

	private boolean isSingleProductStorage(@NonNull final I_M_HU hu)
	{
		return handlingUnitsBL.getStorageFactory().getProductStorages(hu).size() <= 1;
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

	private JsonRejectReasonsList getJsonRejectReasonsList(final @NonNull JsonOpts jsonOpts, final boolean offerEmptyingHUs)
	{
		ADRefList reasons = QtyRejectedReasonCode.reasonsFor(
				adReferenceService.getRefListById(QtyRejectedReasonCode.REFERENCE_ID),
				QtyRejectedReasonContext.ManufacturingIssue);

		if (!offerEmptyingHUs)
		{
			reasons = reasons.excluding(ImmutableSet.of(QtyRejectedReasonCode.EMPTIED.getCode()));
		}

		return JsonRejectReasonsList.of(reasons, jsonOpts);
	}

	/**
	 * Resolved once per request (from the job's own {@code AD_Client_ID}) and threaded into the per-step
	 * {@code isAllowEmptying} computation, the reject-reasons list and the confirmation flag, so all three
	 * derive from the same decision.
	 */
	private MobileUIManufacturingConfig resolveEmptyingHUsConfig(@NonNull final ManufacturingJob job)
	{
		// getConfig(), not getGlobalConfig(): the merged chain falls back to DEFAULT_CONFIG, which carries
		// the flag's 'on' default. getGlobalConfig() returns null when the client has no active
		// MobileUI_MFG_Config row, which would silently disable the feature on any instance that never
		// created one -- the opposite of the documented default. The user profile cannot influence these
		// two flags: MobileUI_UserProfile_MFG has no columns for them, so they stay UNKNOWN and fall through.
		final I_PP_Order ppOrder = ppOrderDAO.getById(job.getPpOrderId());
		final ClientId clientId = ClientId.ofRepoId(ppOrder.getAD_Client_ID());
		// job.getResponsibleId() is @Nullable (e.g. an order whose AD_User_Responsible_ID was never set);
		// fall back to the currently logged-in user rather than fail the whole request on a config lookup.
		final UserId responsibleId = job.getResponsibleId() != null ? job.getResponsibleId() : Env.getLoggedUserId();

		return mobileUIManufacturingConfigRepository.getConfig(responsibleId, clientId);
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return wfActivity.getStatus();
	}
}
