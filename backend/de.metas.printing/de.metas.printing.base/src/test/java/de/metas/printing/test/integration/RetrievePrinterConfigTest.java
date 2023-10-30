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

import static de.metas.bpartner.api.impl.BPRelationDAO.queryBL;
import static de.metas.printing.api.impl.Helper.printingDAO;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.metas.user.UserId;

import de.metas.workplace.WorkplaceId;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery;
import org.junit.Test;

public class RetrievePrinterConfigTest extends AbstractPrintingTest
{
	@Test
	public void testRetrievePrinterConfigWithUserToPrintId() {

		final IQueryBuilder<I_AD_Printer_Config> queryBuilder = mock(IQueryBuilder.class);
		final I_AD_Printer_Config printerConfig = mock(I_AD_Printer_Config.class);
		final UserId userToPrintId = mock(UserId.class);

		when(queryBL.createQueryBuilderOutOfTrx(I_AD_Printer_Config.class)).thenReturn(queryBuilder);
		when(queryBuilder.addOnlyActiveRecordsFilter()).thenReturn(queryBuilder);
		when(queryBuilder.addEqualsFilter(I_AD_Printer_Config.COLUMNNAME_AD_User_PrinterMatchingConfig_ID, userToPrintId)).thenReturn(queryBuilder);
		when(queryBuilder.orderBy(I_AD_Printer_Config.COLUMNNAME_AD_User_PrinterMatchingConfig_ID)).thenReturn(queryBuilder);
		when(queryBuilder.create()).thenReturn((IQuery<I_AD_Printer_Config>)queryBuilder);
		when(((IQuery<?>)queryBuilder).first()).thenReturn(printerConfig);

		final I_AD_Printer_Config result = printingDAO.retrievePrinterConfig(null, userToPrintId, null);
		assertEquals(printerConfig, result);
	}

	@Test
	public void testRetrievePrinterConfigWithWorkplaceId() {

		final IQueryBuilder<I_AD_Printer_Config> queryBuilder = mock(IQueryBuilder.class);
		final I_AD_Printer_Config printerConfig = mock(I_AD_Printer_Config.class);
		final WorkplaceId workplaceId = mock(WorkplaceId.class);

		when(queryBL.createQueryBuilderOutOfTrx(I_AD_Printer_Config.class)).thenReturn(queryBuilder);
		when(queryBuilder.addOnlyActiveRecordsFilter()).thenReturn(queryBuilder);
		when(queryBuilder.addEqualsFilter(I_AD_Printer_Config.COLUMNNAME_C_Workplace_ID, workplaceId)).thenReturn(queryBuilder);
		when(queryBuilder.create()).thenReturn((IQuery<I_AD_Printer_Config>)queryBuilder);
		when(((IQuery<?>)queryBuilder).first()).thenReturn(printerConfig);

		final I_AD_Printer_Config result = printingDAO.retrievePrinterConfig(null, null, workplaceId);
		assertEquals(printerConfig, result);
	}

	@Test
	public void testRetrievePrinterConfigWithWorkplaceIdAndUserToPrintId() {

		final IQueryBuilder<I_AD_Printer_Config> queryBuilder = mock(IQueryBuilder.class);
		final I_AD_Printer_Config printerConfig = mock(I_AD_Printer_Config.class);
		final UserId userToPrintId = mock(UserId.class);
		final WorkplaceId workplaceId = mock(WorkplaceId.class);

		when(queryBL.createQueryBuilderOutOfTrx(I_AD_Printer_Config.class)).thenReturn(queryBuilder);
		when(queryBuilder.addOnlyActiveRecordsFilter()).thenReturn(queryBuilder);
		when(queryBuilder.addEqualsFilter(I_AD_Printer_Config.COLUMNNAME_AD_User_PrinterMatchingConfig_ID, userToPrintId)).thenReturn(queryBuilder);
		when(queryBuilder.addEqualsFilter(I_AD_Printer_Config.COLUMNNAME_C_Workplace_ID, workplaceId)).thenReturn(queryBuilder);
		when(queryBuilder.create()).thenReturn((IQuery<I_AD_Printer_Config>)queryBuilder);
		when(((IQuery<?>)queryBuilder).first()).thenReturn(printerConfig);

		final I_AD_Printer_Config result = printingDAO.retrievePrinterConfig(null, userToPrintId, workplaceId);
		assertEquals(printerConfig, result);
	}
}