/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2025 metas GmbH
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RetrievePrinterConfigTest extends AbstractPrintingTest
{
	public static final int WORKPLACE_1_ID = 1000001;
	public static final int WORKPLACE_2_ID = 1000002;
	public static final String HOST_KEY = "hostKey";
	final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testRetrievePrinterConfigWithUserToPrintId()
	{

		final I_AD_Printer_Config configWithUserToPrintId = newInstance(I_AD_Printer_Config.class);
		configWithUserToPrintId.setAD_User_PrinterMatchingConfig_ID(UserId.METASFRESH.getRepoId());
		configWithUserToPrintId.setConfigHostKey(HOST_KEY);
		save(configWithUserToPrintId);

		final I_AD_Printer_Config retrievePrinterConfigRecord = printingDAO.retrievePrinterConfig(HOST_KEY, UserId.METASFRESH, null);

		assertEquals(configWithUserToPrintId, retrievePrinterConfigRecord);
	}

	@Test
	public void testRetrievePrinterConfigWithWorkplaceId()
	{

		final WorkplaceId workplaceId = WorkplaceId.ofRepoId(WORKPLACE_1_ID);
		final I_AD_Printer_Config configWithWorkplaceId = newInstance(I_AD_Printer_Config.class);
		configWithWorkplaceId.setC_Workplace_ID(WORKPLACE_1_ID);
		save(configWithWorkplaceId);

		final I_AD_Printer_Config retrievePrinterConfigRecord = printingDAO.retrievePrinterConfig(null, null, workplaceId);

		assertEquals(configWithWorkplaceId, retrievePrinterConfigRecord);
	}

	@Test
	public void testRetrievePrinterConfigWithWorkplaceIdAndUserToPrintId()
	{
		final I_AD_Printer_Config configWithUserToPrintId = newInstance(I_AD_Printer_Config.class);
		configWithUserToPrintId.setAD_User_PrinterMatchingConfig_ID(UserId.METASFRESH.getRepoId());
		configWithUserToPrintId.setConfigHostKey(HOST_KEY);
		save(configWithUserToPrintId);

		final WorkplaceId workplaceId = WorkplaceId.ofRepoId(WORKPLACE_1_ID);
		final I_AD_Printer_Config configWithWorkplaceId = newInstance(I_AD_Printer_Config.class);
		configWithWorkplaceId.setC_Workplace_ID(WORKPLACE_1_ID);
		save(configWithWorkplaceId);

		final WorkplaceService workplaceService = new WorkplaceService(new WorkplaceRepository(), new WorkplaceUserAssignRepository());

		workplaceService.assignWorkplace(WorkplaceAssignmentCreateRequest.builder()
				.workplaceId(workplaceId)
				.userId(UserId.METASFRESH)
				.build());

		final I_AD_Printer_Config retrievePrinterConfigRecord = printingDAO.retrievePrinterConfig(HOST_KEY, UserId.METASFRESH, workplaceId);

		assertEquals(configWithWorkplaceId, retrievePrinterConfigRecord);
	}

	@Test
	public void testRetrievePrinterConfigWithHostKeyOnly()
	{
		final I_AD_Printer_Config configWithHostKey = newInstance(I_AD_Printer_Config.class);
		configWithHostKey.setConfigHostKey(HOST_KEY);
		save(configWithHostKey);

		final I_AD_Printer_Config retrievePrinterConfigRecord = printingDAO.retrievePrinterConfig(HOST_KEY, null, null);

		assertEquals(configWithHostKey, retrievePrinterConfigRecord);
	}

	@Test
	public void testRetrievePrinterConfigReturnsNullWhenNoMatch()
	{
		final I_AD_Printer_Config retrievePrinterConfigRecord = printingDAO.retrievePrinterConfig("nonExistentHostKey", UserId.METASFRESH, null);

		assertEquals(null, retrievePrinterConfigRecord);
	}

	@Test
	public void testRetrievePrinterConfigPrefersHostKeyMatch()
	{
		final I_AD_Printer_Config configWithoutHostKey = newInstance(I_AD_Printer_Config.class);
		configWithoutHostKey.setAD_User_PrinterMatchingConfig_ID(UserId.METASFRESH.getRepoId());
		save(configWithoutHostKey);

		final I_AD_Printer_Config configWithHostKey = newInstance(I_AD_Printer_Config.class);
		configWithHostKey.setAD_User_PrinterMatchingConfig_ID(UserId.METASFRESH.getRepoId());
		configWithHostKey.setConfigHostKey(HOST_KEY);
		save(configWithHostKey);

		final I_AD_Printer_Config retrievePrinterConfigRecord = printingDAO.retrievePrinterConfig(HOST_KEY, UserId.METASFRESH, null);

		assertEquals(configWithHostKey, retrievePrinterConfigRecord);
	}

	@Test
	public void testRetrievePrinterConfigWithHostKeyAndWorkplace()
	{
		final WorkplaceId workplaceId = WorkplaceId.ofRepoId(WORKPLACE_1_ID);
		final I_AD_Printer_Config configWithWorkplaceAndHostKey = newInstance(I_AD_Printer_Config.class);
		configWithWorkplaceAndHostKey.setC_Workplace_ID(WORKPLACE_1_ID);
		configWithWorkplaceAndHostKey.setConfigHostKey(HOST_KEY);
		save(configWithWorkplaceAndHostKey);

		final I_AD_Printer_Config retrievePrinterConfigRecord = printingDAO.retrievePrinterConfig(HOST_KEY, null, workplaceId);

		assertEquals(configWithWorkplaceAndHostKey, retrievePrinterConfigRecord);
	}


	/**
	 * If the user does have a config, but the user's workplace does not, then we want metasfresh to return the user's config.
	 */
	@Test
	public void testRetrievePrinterConfigFallsBackToUserWhenWorkplaceMissing()
	{
		final I_AD_Printer_Config configWithUserToPrintId = newInstance(I_AD_Printer_Config.class);
		configWithUserToPrintId.setAD_User_PrinterMatchingConfig_ID(UserId.METASFRESH.getRepoId());
		configWithUserToPrintId.setConfigHostKey(HOST_KEY);
		save(configWithUserToPrintId);

		final I_AD_Printer_Config configWithWorkplace1Id = newInstance(I_AD_Printer_Config.class);
		configWithWorkplace1Id.setC_Workplace_ID(WORKPLACE_1_ID);
		save(configWithWorkplace1Id);

		final WorkplaceService workplaceService = new WorkplaceService(new WorkplaceRepository(), new WorkplaceUserAssignRepository());
		final WorkplaceId workplace2Id = WorkplaceId.ofRepoId(WORKPLACE_2_ID);
		workplaceService.assignWorkplace(WorkplaceAssignmentCreateRequest.builder()
				.workplaceId(workplace2Id)
				.userId(UserId.METASFRESH)
				.build());

		final I_AD_Printer_Config retrievePrinterConfigRecord = printingDAO.retrievePrinterConfig(HOST_KEY, UserId.METASFRESH, workplace2Id);

		assertEquals(configWithUserToPrintId, retrievePrinterConfigRecord);
	}
}