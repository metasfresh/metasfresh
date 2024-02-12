package de.metas.handlingunits.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.report.HUToReport;
import de.metas.handlingunits.report.HUToReportWrapper;
import de.metas.handlingunits.report.labels.HULabelDirectPrintRequest;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.printing.IMassPrintingService;
import de.metas.process.AdProcessId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.report.PrintCopies;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class M_HU_MultipleSelection_Report_Print_Label extends JavaProcess implements IProcessPrecondition
{
	private final HULabelService labelService = SpringContextHolder.instance.getBean(HULabelService.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@Param(mandatory = true, parameterName = "AD_Process_ID")
	private AdProcessId p_AD_Process_ID;
	@Param(mandatory = true, parameterName = IMassPrintingService.PARAM_PrintCopies)
	private int p_PrintCopies;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final ImmutableList<HUToReport> hus = handlingUnitsDAO.streamByQuery(retrieveSelectedRecordsQueryBuilder(I_M_HU.class), HUToReportWrapper::of)
				.collect(ImmutableList.toImmutableList());

		labelService.printNow(HULabelDirectPrintRequest.builder()
									  .onlyOneHUPerPrint(true)
									  .printCopies(PrintCopies.ofIntOrOne(p_PrintCopies))
									  .printFormatProcessId(p_AD_Process_ID)
									  .hus(hus)
									  .build());
		return MSG_OK;
	}
}
