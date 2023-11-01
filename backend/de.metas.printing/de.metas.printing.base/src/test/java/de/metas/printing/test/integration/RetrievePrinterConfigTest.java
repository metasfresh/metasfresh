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

import de.metas.printing.api.impl.AbstractPrintingTest;
import de.metas.printing.model.I_AD_Printer_Config;
import de.metas.user.UserId;
import de.metas.workplace.WorkplaceId;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery;
import org.junit.Test;

import static de.metas.bpartner.api.impl.BPRelationDAO.queryBL;
import static org.junit.Assert.assertEquals;

public class RetrievePrinterConfigTest extends AbstractPrintingTest
{
	@Test
	public void testRetrievePrinterConfigWithUserToPrintId() {
		final IQueryBuilder<I_AD_Printer_Config> queryBuilder = queryBL.createQueryBuilderOutOfTrx(I_AD_Printer_Config.class);
		final UserId userToPrintId = UserId.METASFRESH;

		queryBuilder.addOnlyActiveRecordsFilter();
		queryBuilder.addEqualsFilter(I_AD_Printer_Config.COLUMNNAME_AD_User_PrinterMatchingConfig_ID, userToPrintId);
		queryBuilder.orderBy(I_AD_Printer_Config.COLUMNNAME_AD_User_PrinterMatchingConfig_ID);
		final IQuery<I_AD_Printer_Config> query = queryBuilder.create();
		final I_AD_Printer_Config printerConfig = query.first();

		assertEquals(printerConfig, printerConfig);
	}

	@Test
	public void testRetrievePrinterConfigWithWorkplaceId() {
		final IQueryBuilder<I_AD_Printer_Config> queryBuilder = queryBL.createQueryBuilderOutOfTrx(I_AD_Printer_Config.class);
		final WorkplaceId workplaceId = WorkplaceId.ofRepoId(1000001);

		queryBuilder.addOnlyActiveRecordsFilter();
		queryBuilder.addEqualsFilter(I_AD_Printer_Config.COLUMNNAME_C_Workplace_ID, workplaceId);
		final IQuery<I_AD_Printer_Config> query = queryBuilder.create();
		final I_AD_Printer_Config printerConfig = query.first();

		assertEquals(printerConfig, printerConfig);
	}

	@Test
	public void testRetrievePrinterConfigWithWorkplaceIdAndUserToPrintId() {
		final IQueryBuilder<I_AD_Printer_Config> queryBuilder = queryBL.createQueryBuilderOutOfTrx(I_AD_Printer_Config.class);
		final UserId userToPrintId = UserId.METASFRESH;
		final WorkplaceId workplaceId = WorkplaceId.ofRepoId(1000001);

		queryBuilder.addOnlyActiveRecordsFilter();
		queryBuilder.addEqualsFilter(I_AD_Printer_Config.COLUMNNAME_AD_User_PrinterMatchingConfig_ID, userToPrintId);
		queryBuilder.addEqualsFilter(I_AD_Printer_Config.COLUMNNAME_C_Workplace_ID, workplaceId);
		final IQuery<I_AD_Printer_Config> query = queryBuilder.create();
		final I_AD_Printer_Config printerConfig = query.first();

		assertEquals(printerConfig, printerConfig);
	}
}