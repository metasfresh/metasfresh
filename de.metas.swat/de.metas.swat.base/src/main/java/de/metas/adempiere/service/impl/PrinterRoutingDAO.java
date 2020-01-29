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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.I_AD_PrinterRouting;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_AD_Printer;
import de.metas.adempiere.service.IPrinterRoutingDAO;
import de.metas.cache.annotation.CacheCtx;
import de.metas.logging.LogManager;
import de.metas.util.Check;

public class PrinterRoutingDAO implements IPrinterRoutingDAO
{
	private final Logger log = LogManager.getLogger(getClass());

	@Override
	public <T> List<T> fetchPrinterRoutings(
			@CacheCtx final Properties ctx,
			final int AD_Client_ID, final int AD_Org_ID,
			final int AD_Role_ID, final int AD_User_ID,
			final int C_DocType_ID, final int AD_Process_ID,
			final int AD_Table_ID,
			final String printerType,
			final Class<T> clazz)
	{
		if (LogManager.isLevelFine())
		{
			log.debug("AD_Client_ID=" + AD_Client_ID + ", AD_Org_ID=" + AD_Org_ID
					+ ", AD_Role_ID=" + AD_Role_ID + ", AD_User_ID=" + AD_User_ID
					+ ", C_DocType_ID=" + C_DocType_ID + ", AD_Process_ID=" + AD_Process_ID
					+ ", AD_Table_ID= " + AD_Table_ID
					+ ", printerType=" + printerType);
		}

		final List<Object> params = new ArrayList<>();
		final StringBuffer whereClause = new StringBuffer(
				I_AD_PrinterRouting.COLUMNNAME_AD_Client_ID + " IN (0,?)"
						+ " AND " + I_AD_PrinterRouting.COLUMNNAME_AD_Org_ID + " IN (0,?)"
						+ " AND ("
						+ "	" + I_AD_PrinterRouting.COLUMNNAME_C_DocType_ID + "=?"
						+ "	OR " + I_AD_PrinterRouting.COLUMNNAME_C_DocType_ID + "=0"
						+ "	OR " + I_AD_PrinterRouting.COLUMNNAME_C_DocType_ID + " IS NULL"
						+ ")"
						+ " AND ("
						+ "	" + I_AD_PrinterRouting.COLUMNNAME_AD_Process_ID + "=?"
						+ "	OR " + I_AD_PrinterRouting.COLUMNNAME_AD_Process_ID + " IS NULL"
						+ ")"

						+ " AND ("
						+ "	" + I_AD_PrinterRouting.COLUMNNAME_AD_Table_ID + "=?"
						+ "	OR " + I_AD_PrinterRouting.COLUMNNAME_AD_Table_ID + " IS NULL"
						+ ")"

						+ " AND ("
						+ "	" + I_AD_PrinterRouting.COLUMNNAME_AD_Role_ID + "=?"
						+ "	OR " + I_AD_PrinterRouting.COLUMNNAME_AD_Role_ID + " IS NULL"
						+ ")"
						+ " AND ("
						+ "	" + I_AD_PrinterRouting.COLUMNNAME_AD_User_ID + "=?"
						+ "	OR " + I_AD_PrinterRouting.COLUMNNAME_AD_User_ID + " IS NULL"
						+ ")");
		params.add(AD_Client_ID);
		params.add(AD_Org_ID);
		params.add(C_DocType_ID);
		params.add(AD_Process_ID);
		params.add(AD_Table_ID);
		params.add(AD_Role_ID);
		params.add(AD_User_ID);

		// Printer whereClause
		whereClause.append(
				" AND EXISTS(SELECT 1 FROM " + I_AD_Printer.Table_Name + " p"
						+ "	WHERE p." + I_AD_Printer.COLUMNNAME_AD_Printer_ID + "=" + I_AD_PrinterRouting.Table_Name + "." + I_AD_PrinterRouting.COLUMNNAME_AD_Printer_ID
						+ "	AND p." + I_AD_Printer.COLUMNNAME_IsActive + "=?");
		params.add(true); // AD_Printer.IsActive
		if (printerType != null)
		{
			whereClause.append(" AND p." + I_AD_Printer.COLUMNNAME_PrinterType + "=?");
			params.add(printerType);
		}
		whereClause.append(")");

		final String orderBy = I_AD_PrinterRouting.COLUMNNAME_SeqNo
				+ ", " + I_AD_PrinterRouting.COLUMNNAME_AD_Client_ID + " DESC"
				+ ", " + I_AD_PrinterRouting.COLUMNNAME_AD_Org_ID + " DESC"
				+ ", COALESCE(" + I_AD_PrinterRouting.COLUMNNAME_C_DocType_ID + ",0) DESC"
				+ ", COALESCE(" + I_AD_PrinterRouting.COLUMNNAME_AD_Role_ID + ",0) DESC"
				+ ", COALESCE(" + I_AD_PrinterRouting.COLUMNNAME_AD_User_ID + ",0) DESC"
				+ ", " + I_AD_PrinterRouting.COLUMNNAME_AD_PrinterRouting_ID;

		return new Query(ctx, I_AD_PrinterRouting.Table_Name, whereClause.toString(), null)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.list(clazz);
	}

	@Override
	public I_AD_Printer findPrinterByName(final Properties ctx, final String printerName)
	{
		if (Check.isEmpty(printerName))
		{
			return null;
		}
		final String trxName = null;
		final int AD_Client_ID = Env.getAD_Client_ID(ctx);

		final String whereClause = I_AD_Printer.COLUMNNAME_PrinterName + "=?"
				+ " AND " + I_AD_Printer.COLUMNNAME_AD_Client_ID + " IN (0,?)";
		return new Query(ctx, I_AD_Printer.Table_Name, whereClause, trxName)
				.setParameters(printerName, AD_Client_ID)
				.firstOnly(I_AD_Printer.class);
	}
}
