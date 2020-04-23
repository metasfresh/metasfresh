/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.serviceprovider.everhour;

import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.serviceprovider.timebooking.importer.ImportTimeBookingsRequest;
import de.metas.serviceprovider.timebooking.importer.TimeBookingsImporterService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;

import java.time.LocalDate;

import static de.metas.serviceprovider.everhour.EverhourImportConstants.EverhourImporterSysConfig.ACCESS_TOKEN;

public class EverhourImportProcess extends JavaProcess
{
	@Param(parameterName = "DateFrom")
	private LocalDate dateFrom;

	@Param(parameterName = "DateTo")
	private LocalDate dateTo;

	private final EverhourImporterService everhourImporterService = SpringContextHolder.instance.getBean(EverhourImporterService.class);
	private final TimeBookingsImporterService timeBookingsImporterService = SpringContextHolder.instance.getBean(TimeBookingsImporterService.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Override protected String doIt() throws Exception
	{
		final ImportTimeBookingsRequest timeBookingsRequest = ImportTimeBookingsRequest
				.builder()
				.authToken(sysConfigBL.getValue(ACCESS_TOKEN.getName()))
				.startDate(dateFrom)
				.endDate(dateTo)
				.build();

		timeBookingsImporterService.importTimeBookings(everhourImporterService,timeBookingsRequest);

		return MSG_OK;
	}

	protected void overwriteParameters(@NonNull final LocalDate dateFrom, @NonNull final LocalDate dateTo)
	{
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}
}
