package org.adempiere.misc.service.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.misc.service.ITablePA;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

public final class TablePA implements ITablePA {

	private static final CLogger logger = CLogger.getCLogger(TablePA.class);

	public PO retrievePO(final I_AD_Table table, final int recordId,
			final String trxName) {

		if (!(table instanceof MTable)) {

			String msg = "Param 'table' needs to be an MTable. Is: " + table;
			logger.severe(msg);
			throw new IllegalArgumentException(msg);
		}

		final MTable mTable = (MTable) table;
		return mTable.getPO(recordId, trxName);
	}

	public I_AD_Table retrieveTable(final int adTableId, final String trxName) {

		return new MTable(Env.getCtx(), adTableId, trxName);
	}

	public I_AD_Column retrieveColumn(final int columnId, final String trxName) {

		return new MColumn(Env.getCtx(), columnId, trxName);
	}

}
