package de.metas.handlingunits.process;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.HUReportService;
import de.metas.handlingunits.report.HUToReport;
import de.metas.process.AdProcessId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import java.util.List;

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

public class M_HU_Assign_QRCode extends JavaProcess implements IProcessPrecondition
{
	private final HUReportService huReportService = HUReportService.get();
	private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);

	@Param(parameterName = "Barcode", mandatory = true)
	private String p_Barcode;



	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		final ImmutableSet<HuId> huIds = huReportService.getHuIdsFromSelection(getPinstanceId());

		if ( huIds.size() > 1)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("");
		}

		return ProcessPreconditionsResolution.accept();
	}
	@Override
	protected String doIt()
	{
		final ImmutableSet<HuId> huIds = huReportService.getHuIdsFromSelection(getPinstanceId());

		System.out.println("huIds=" + huIds.size());
		// huIds.get
		// huQRCodesService.assign(huIds.iterator().next(), p_Barcode);

		return MSG_OK;
	}

}