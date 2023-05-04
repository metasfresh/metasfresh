/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.project.process;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.AdMessageKey;
import de.metas.organization.IOrgDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.project.ProjectId;
import de.metas.project.workorder.undertest.WOProjectObjectUnderTest;
import de.metas.project.workorder.undertest.WorkOrderProjectObjectUnderTestService;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class C_WO_Project_ObjectUnderTest_OrderProvision extends JavaProcess implements IProcessPrecondition
{
	private static final String WO_ObjectUnderTest_Without_Product_Message = "C_Project_WO_ObjectUnderTest";

	private final WorkOrderProjectObjectUnderTestService woProjectObjectUnderTestService = SpringContextHolder.instance.getBean(WorkOrderProjectObjectUnderTestService.class);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private static final String PARAM_C_BPARTNER_ID = "C_BPartner_ID";
	@Param(parameterName = PARAM_C_BPARTNER_ID)
	private int bPartnerId;

	private static final String PARAM_DATE_PROMISED = "DatePromised";
	@Param(parameterName = PARAM_DATE_PROMISED)
	private LocalDate datePromised;

	@Override
	protected String doIt() throws Exception
	{
		final List<WOProjectObjectUnderTest> woProjectObjectUnderTestList = woProjectObjectUnderTestService.getByProjectId(ProjectId.ofRepoId(getRecord_ID()));

		if (!woProjectObjectUnderTestList.isEmpty())
		{
			final ZoneId timeZone = orgDAO.getTimeZone(woProjectObjectUnderTestList.get(0).getOrgId());
			final ZonedDateTime zonedDatePromised = datePromised.atStartOfDay(timeZone);

			woProjectObjectUnderTestService.createOrder(BPartnerId.ofRepoId(bPartnerId), zonedDatePromised, woProjectObjectUnderTestList);
		}
		return MSG_OK;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final List<WOProjectObjectUnderTest> objectUnderTestList = woProjectObjectUnderTestService.getByProjectId(ProjectId.ofRepoId(context.getSingleSelectedRecordId()));

		final boolean anyObjectUnderTestWithoutProduct = objectUnderTestList
				.stream()
				.anyMatch(woProjectObjectUnderTest -> woProjectObjectUnderTest.getProductId() == null);

		if (objectUnderTestList.isEmpty() || anyObjectUnderTestWithoutProduct)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(AdMessageKey.of(WO_ObjectUnderTest_Without_Product_Message)));
		}

		return ProcessPreconditionsResolution.accept();
	}
}
