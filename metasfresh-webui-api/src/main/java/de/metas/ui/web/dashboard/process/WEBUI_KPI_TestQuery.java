package de.metas.ui.web.dashboard.process;

import java.util.Date;

import org.compiere.Adempiere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.WebRestApiApplication;
import de.metas.ui.web.base.model.I_WEBUI_KPI;
import de.metas.ui.web.dashboard.KPI;
import de.metas.ui.web.dashboard.KPIData;
import de.metas.ui.web.dashboard.UserDashboardRepository;
import de.metas.ui.web.exceptions.EntityNotFoundException;

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

@Profile(WebRestApiApplication.PROFILE_Webui)
public class WEBUI_KPI_TestQuery extends JavaProcess implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Autowired
	private UserDashboardRepository userDashboardRepo;
	@Autowired
	private ObjectMapper jsonObjectMapper;

	@Param(parameterName = "DateFrom")
	private Date p_DateFrom;
	@Param(parameterName = "DateTo")
	private Date p_DateTo;

	public WEBUI_KPI_TestQuery()
	{
		Adempiere.autowire(this);
	}

	@Override
	protected String doIt() throws JsonProcessingException
	{
		final int kpiId = getRecord_ID();
		if (kpiId <= 0)
		{
			throw new EntityNotFoundException("@NotFound@ @" + I_WEBUI_KPI.COLUMNNAME_WEBUI_KPI_ID + "@");
		}

		userDashboardRepo.invalidateKPI(kpiId);
		final KPI kpi = userDashboardRepo.getKPI(kpiId);

		final long fromMillis = p_DateFrom == null ? -1 : p_DateFrom.getTime();
		final long toMillis = p_DateTo == null ? -1 : p_DateTo.getTime();
		final KPIData kpiData = kpi.retrieveData(fromMillis, toMillis);

		final String jsonData = jsonObjectMapper.writeValueAsString(kpiData);
		log.info("jsonData:\n {}", jsonData);

		return jsonData;
	}
}
