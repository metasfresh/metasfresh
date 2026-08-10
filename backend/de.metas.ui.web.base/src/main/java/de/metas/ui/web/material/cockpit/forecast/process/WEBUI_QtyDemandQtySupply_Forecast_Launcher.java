package de.metas.ui.web.material.cockpit.forecast.process;

import com.google.common.collect.ImmutableSet;
import de.metas.material.cockpit.model.I_QtyDemand_QtySupply_V;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.material.cockpit.forecast.ForecastOverlayViewFactory;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;

/**
 * {@code Sprung zu Prognose} launcher for Material Cockpit v2 ({@code AD_Process} 585515). Instead of opening the
 * standard {@code M_Forecast} window, it opens the product-scoped {@link ForecastOverlayViewFactory} custom view,
 * seeded with the selected cockpit row so the view can compute a per-product {@code Menge}.
 */
public class WEBUI_QtyDemandQtySupply_Forecast_Launcher extends JavaProcess implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		getResult().setRecordsToOpen(
				ImmutableSet.of(TableRecordReference.of(I_QtyDemand_QtySupply_V.Table_Name, getRecord_ID())),
				ForecastOverlayViewFactory.WINDOW_ID_STRING);
		return MSG_OK;
	}
}
