package de.metas.adempiere.service.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.adempiere.model.I_AD_Printer;
import de.metas.adempiere.model.I_AD_PrinterRouting;
import de.metas.adempiere.service.IPrinterRoutingDAO;
import de.metas.adempiere.service.PrinterRoutingsQuery;
import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.service.ClientId;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

public class PrinterRoutingDAO implements IPrinterRoutingDAO
{
	private final transient Logger logger = LogManager.getLogger(PrinterRoutingDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<PrinterRoutingsQuery, List<I_AD_PrinterRouting>> query2routingsCache = CCache
			.<PrinterRoutingsQuery, List<I_AD_PrinterRouting>>builder()
			.cacheName(I_AD_PrinterRouting.Table_Name + "#by#PrinterRoutingsQuery")
			.tableName(I_AD_PrinterRouting.Table_Name)
			.build();

	@Override
	public List<I_AD_PrinterRouting> fetchPrinterRoutings(@NonNull final PrinterRoutingsQuery query)
	{
		return query2routingsCache.getOrLoad(query, q -> fetchPrinterRoutings0(q));
	}

	private List<I_AD_PrinterRouting> fetchPrinterRoutings0(@NonNull final PrinterRoutingsQuery query)
	{
		logger.debug("fetchPrinterRoutings - Invoked with query={}", query);

		final IQueryBuilder<I_AD_Printer> printerQueryBuilder = queryBL
				.createQueryBuilder(I_AD_Printer.class)// only routings that reference active printers
				.addOnlyActiveRecordsFilter();
		if (Check.isNotBlank(query.getPrinterType()))
		{
			printerQueryBuilder.addEqualsFilter(I_AD_Printer.COLUMNNAME_PrinterType, query.getPrinterType());
		}

		final IQueryBuilder<I_AD_PrinterRouting> routingQueryBuilder = printerQueryBuilder
				.andCollectChildren(I_AD_PrinterRouting.COLUMN_AD_Printer_ID)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_AD_PrinterRouting.COLUMNNAME_AD_Client_ID, query.getClientId(), ClientId.SYSTEM)
				.addInArrayFilter(I_AD_PrinterRouting.COLUMNNAME_AD_Org_ID, query.getOrgId(), OrgId.ANY);
		if (query.getDocTypeId() != null)
		{ // do allow printer-routings without a doctype, but order such that they come last ("NULLS LAST")
			routingQueryBuilder.addInArrayFilter(I_AD_PrinterRouting.COLUMNNAME_C_DocType_ID, query.getDocTypeId(), null);
			routingQueryBuilder.orderBy(I_AD_PrinterRouting.COLUMNNAME_C_DocType_ID);
		}
		if (query.getProcessId() != null)
		{
			routingQueryBuilder.addInArrayFilter(I_AD_PrinterRouting.COLUMNNAME_AD_Process_ID, query.getProcessId(), null);
			routingQueryBuilder.orderBy(I_AD_PrinterRouting.COLUMNNAME_AD_Process_ID);
		}
		if (query.getTableId() != null)
		{
			routingQueryBuilder.addInArrayFilter(I_AD_PrinterRouting.COLUMNNAME_AD_Table_ID, query.getTableId(), null);
			routingQueryBuilder.orderBy(I_AD_PrinterRouting.COLUMNNAME_AD_Table_ID);
		}
		if (query.getRoleId() != null)
		{
			routingQueryBuilder.addInArrayFilter(I_AD_PrinterRouting.COLUMNNAME_AD_Role_ID, query.getRoleId(), null);
			routingQueryBuilder.orderBy(I_AD_PrinterRouting.COLUMNNAME_AD_Role_ID);
		}
		if (query.getUserId() != null)
		{
			routingQueryBuilder.addInArrayFilter(I_AD_PrinterRouting.COLUMNNAME_AD_User_ID, query.getUserId(), null);
			routingQueryBuilder.orderBy(I_AD_PrinterRouting.COLUMNNAME_AD_User_ID);
		}

		routingQueryBuilder
				.orderBy(I_AD_PrinterRouting.COLUMNNAME_SeqNo)
				.orderByDescending(I_AD_PrinterRouting.COLUMNNAME_AD_Client_ID)
				.orderByDescending(I_AD_PrinterRouting.COLUMNNAME_AD_Org_ID)
				.orderBy(I_AD_PrinterRouting.COLUMNNAME_AD_PrinterRouting_ID);

		return routingQueryBuilder
				.create()
				.list();
	}

	@Override
	public I_AD_Printer findPrinterByName(@Nullable final String printerName)
	{
		if (Check.isBlank(printerName))
		{
			return null;
		}

		return queryBL.createQueryBuilderOutOfTrx(I_AD_Printer.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Printer.COLUMNNAME_PrinterName, printerName)
				.addOnlyContextClientOrSystem()
				.create()
				.firstOnly(I_AD_Printer.class);
	}
}
