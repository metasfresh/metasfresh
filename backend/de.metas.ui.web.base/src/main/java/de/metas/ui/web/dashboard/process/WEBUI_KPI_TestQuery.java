package de.metas.ui.web.dashboard.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.Profiles;
import de.metas.elasticsearch.IESSystem;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.ui.web.kpi.data.KPIDataContext;
import de.metas.ui.web.kpi.data.KPIDataProvider;
import de.metas.ui.web.kpi.data.KPIDataRequest;
import de.metas.ui.web.kpi.data.KPIDataResult;
import de.metas.ui.web.kpi.data.KPIPermissionsProvider;
import de.metas.ui.web.kpi.descriptor.KPI;
import de.metas.ui.web.kpi.descriptor.KPIId;
import de.metas.ui.web.kpi.descriptor.KPIRepository;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.springframework.context.annotation.Profile;

import java.time.Instant;

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

@Profile(Profiles.PROFILE_Webui)
public class WEBUI_KPI_TestQuery extends JavaProcess implements IProcessPrecondition
{
	private final IESSystem esSystem = Services.get(IESSystem.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final KPIRepository kpisRepo = SpringContextHolder.instance.getBean(KPIRepository.class);
	private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	private final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	@Param(parameterName = "DateFrom")
	private Instant p_DateFrom;
	@Param(parameterName = "DateTo")
	private Instant p_DateTo;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws JsonProcessingException
	{
		final KPIId kpiId = KPIId.ofRepoId(getRecord_ID());
		kpisRepo.invalidateCache();

		final KPI kpi = kpisRepo.getKPI(kpiId);

		final KPIDataResult kpiData = KPIDataProvider.builder()
				.kpiRepository(kpisRepo)
				.esSystem(esSystem)
				.sysConfigBL(sysConfigBL)
				.kpiPermissionsProvider(new KPIPermissionsProvider(userRolePermissionsDAO))
				.build()
				.getKPIData(KPIDataRequest.builder()
						.kpiId(kpiId)
						.timeRangeDefaults(kpi.getTimeRangeDefaults())
						.context(KPIDataContext.ofEnvProperties(getCtx())
								.toBuilder()
								.from(p_DateFrom)
								.to(p_DateTo)
								.build())
						.build());

		final String jsonData = jsonObjectMapper.writeValueAsString(kpiData);
		log.info("jsonData:\n {}", jsonData);

		return jsonData;
	}
}
