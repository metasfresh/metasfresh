package de.metas.ui.web.material.cockpit.v2;

import de.metas.cache.CCache;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.model.I_QtyDemand_QtySupply_V;
import de.metas.order.OrderLineId;
import de.metas.process.PInstanceId;
import de.metas.ui.web.order.sales.hu.reservation.process.MaterialCockpitViewContext;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialCockpitV2Service
{
	static final String WINDOWID_MaterialCockpitV2_String = "541963";
	public static final WindowId WINDOWID_MaterialCockpitV2 = WindowId.fromJson(WINDOWID_MaterialCockpitV2_String);
	private static final String MATERIAL_COCKPIT_FUNCTION_NAME_PATTERN = "%MaterialCockpit_SelectForOrderLine";

	@NonNull private static final Logger logger = LogManager.getLogger(MaterialCockpitV2Service.class);
	@NonNull private final IViewsRepository viewsRepo;

	@NonNull private final CCache<Integer, Optional<String>> functionNameCache = CCache.<Integer, Optional<String>>builder()
			.cacheName("MaterialCockpitFunction")
			.build();

	@Nullable
	public ViewId createMaterialCockpitView(@NonNull MaterialCockpitViewContext context)
	{
		final String functionName = getMaterialCockpitFunction().orElse(null);
		if (functionName == null)
		{
			return null;
		}

		callMaterialCockpitSelectionFunction(functionName, context.getSourceSelectionId(), context.getSalesOrderLineId());

		return viewsRepo.createView(
						CreateViewRequest
								.builder(WindowId.of(getActualWindowId()))
								.addStickyFilters(MaterialCockpitV2SelectionFilterConverter.createSelectionFilter(context.getSourceSelectionId()))
								.setParameter(MaterialCockpitViewContext.VIEW_PARAMETER_NAME, context)
								.build()
				)
				.getViewId();
	}

	public void recreateSourceSelection(@NonNull MaterialCockpitViewContext context)
	{
		final String functionName = getMaterialCockpitFunction().orElseThrow(() -> new AdempiereException("No MaterialCockpit function found"));
		final PInstanceId selectionId = context.getSourceSelectionId();
		final OrderLineId salesOrderLineId = context.getSalesOrderLineId();

		DB.executeFunctionCallEx(
				ITrx.TRXNAME_ThreadInherited,
				"SELECT " + functionName + "(p_C_OrderLine_ID => ?, p_AD_PInstance_ID => ?)",
				new Object[] { salesOrderLineId.getRepoId(), selectionId.getRepoId() });
	}

	private AdWindowId getActualWindowId()
	{
		return RecordWindowFinder.findAdWindowId(I_QtyDemand_QtySupply_V.Table_Name)
				.orElseThrow(() -> new AdempiereException("No window found for " + I_QtyDemand_QtySupply_V.Table_Name));
	}

	private Optional<String> getMaterialCockpitFunction()
	{
		return functionNameCache.getOrLoad(0, this::findMaterialCockpitFunction);
	}

	/**
	 * Discover a custom DB function matching {@code %MaterialCockpit_SelectForOrderLine} in pg_proc.
	 * If multiple are found, log a warning and use the first one (alphabetically).
	 *
	 * @return the function name, or {@code null} if none found
	 */
	private Optional<String> findMaterialCockpitFunction()
	{
		final List<String> functionNames = DB.getFunctionsLike(MATERIAL_COCKPIT_FUNCTION_NAME_PATTERN);

		if (functionNames.isEmpty())
		{
			return Optional.empty();
		}

		if (functionNames.size() > 1)
		{
			logger.warn("Found multiple MaterialCockpit_SelectForOrderLine functions: {}. Using the first one: {}", functionNames, functionNames.get(0));
		}

		return Optional.of(functionNames.get(0));
	}

	private void callMaterialCockpitSelectionFunction(
			@NonNull final String functionName,
			@NonNull final PInstanceId selectionId,
			final OrderLineId salesOrderLineId)
	{
		DB.executeFunctionCallEx(
				ITrx.TRXNAME_None,
				"SELECT " + functionName + "(p_C_OrderLine_ID => ?, p_AD_PInstance_ID => ?)",
				new Object[] { salesOrderLineId.getRepoId(), selectionId.getRepoId() });
	}
}
