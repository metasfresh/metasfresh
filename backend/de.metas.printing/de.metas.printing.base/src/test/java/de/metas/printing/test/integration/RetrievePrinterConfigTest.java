/*
 * #%L
 * de.metas.printing.base
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

package de.metas.printing.test.integration;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.impl.AbstractPrintingTest;
import de.metas.printing.model.I_AD_Printer_Config;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceAssignmentCreateRequest;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceRepository;
import de.metas.workplace.WorkplaceService;
import de.metas.workplace.WorkplaceUserAssignRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.junit.Assert.assertEquals;

public class RetrievePrinterConfigTest extends AbstractPrintingTest
{
	public static final int WORKPLACE_ID = 1000001;
	public static final String HOST_KEY = "hostKey";
	final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testRetrievePrinterConfigWithUserToPrintId() {

		final I_AD_Printer_Config configWithUserToPrintId = newInstance(I_AD_Printer_Config.class);
		configWithUserToPrintId.setAD_User_PrinterMatchingConfig_ID(UserId.METASFRESH.getRepoId());
		configWithUserToPrintId.setConfigHostKey(HOST_KEY);
		save(configWithUserToPrintId);

		final I_AD_Printer_Config retrievePrinterConfigRecord = printingDAO.retrievePrinterConfig(HOST_KEY, UserId.METASFRESH, null);

		assertEquals(configWithUserToPrintId, retrievePrinterConfigRecord);
	}

	@Test
	public void testRetrievePrinterConfigWithWorkplaceId() {

		final WorkplaceId workplaceId = WorkplaceId.ofRepoId(WORKPLACE_ID);
		final I_AD_Printer_Config configWithWorkplaceId = newInstance(I_AD_Printer_Config.class);
		configWithWorkplaceId.setC_Workplace_ID(WORKPLACE_ID);
		save(configWithWorkplaceId);

		final I_AD_Printer_Config retrievePrinterConfigRecord = printingDAO.retrievePrinterConfig(null, null, workplaceId);

		assertEquals(configWithWorkplaceId, retrievePrinterConfigRecord);
	}

	@Test
	public void testRetrievePrinterConfigWithWorkplaceIdAndUserToPrintId() {

		final I_AD_Printer_Config configWithUserToPrintId = newInstance(I_AD_Printer_Config.class);
		configWithUserToPrintId.setAD_User_PrinterMatchingConfig_ID(UserId.METASFRESH.getRepoId());
		configWithUserToPrintId.setConfigHostKey(HOST_KEY);
		save(configWithUserToPrintId);

		final WorkplaceId workplaceId = WorkplaceId.ofRepoId(WORKPLACE_ID);
		final I_AD_Printer_Config configWithWorkplaceId = newInstance(I_AD_Printer_Config.class);
		configWithWorkplaceId.setC_Workplace_ID(WORKPLACE_ID);
		save(configWithWorkplaceId);

		final WorkplaceService workplaceService = new WorkplaceService(new WorkplaceRepository(), new WorkplaceUserAssignRepository());

		workplaceService.assignWorkplace(WorkplaceAssignmentCreateRequest.builder()
												 .workplaceId(workplaceId)
												 .userId(UserId.METASFRESH)
												 .build());

		final I_AD_Printer_Config retrievePrinterConfigRecord = printingDAO.retrievePrinterConfig(HOST_KEY, UserId.METASFRESH, workplaceId);

		assertEquals(configWithWorkplaceId, retrievePrinterConfigRecord);
	}
}