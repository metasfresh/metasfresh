/**
 * 
 */
package de.metas.adempiere.process;

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


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Util;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.adempiere.model.I_C_Order;
import de.metas.document.IDocumentLocationBL;
import de.metas.document.model.IDocumentBillLocation;
import de.metas.document.model.IDocumentDeliveryLocation;
import de.metas.document.model.IDocumentLocation;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * @author cg
 * 
 */
public class UpdateAddresses extends JavaProcess
{

	private int p_AD_Table_ID = -1;
	private int p_AD_Org_ID = -1;

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				;
			}
			else if (name.equals("AD_Table_ID"))
			{
				p_AD_Table_ID = para.getParameterAsInt();
			}
			else if (name.equals("AD_Org_ID"))
			{
				p_AD_Org_ID = para.getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (p_AD_Table_ID <= 0)
		{
			throw new FillMandatoryException("@AD_Table_ID@");
		}

		updateAddresses(p_AD_Table_ID);

		return "OK";
	}

	private void updateAddresses(final int AD_Table_ID)
	{
		final MTable table = MTable.get(getCtx(), AD_Table_ID);

		updateAddresses(table,
				I_C_Order.COLUMNNAME_BPartnerAddress,
				I_C_Order.COLUMNNAME_BillToAddress,
				I_C_Order.COLUMNNAME_DeliveryToAddress,
				I_C_BPartner_Location.COLUMNNAME_Address);
	}

	private void updateAddresses(final MTable table, final String... columnNames)
	{
		//
		// Identify available column names
		final List<String> availableColumnNames = new ArrayList<String>();
		for (String columnName : columnNames)
		{
			final I_AD_Column column = table.getColumn(columnName);
			if (column != null && column.isActive())
			{
				availableColumnNames.add(column.getColumnName());
			}
		}
		if (availableColumnNames.isEmpty())
		{
			addLog("WARNING: Skip table '" + table.getTableName() + "' because it does not have address columns");
		}

		int count_all = 0;
		int count_updated = 0;
		final Iterator<PO> it = fetchRecords(table, availableColumnNames);
		while (it.hasNext())
		{
			final PO po = it.next();
			count_all++;

			boolean updated = false; // number of updated POs

			// Try updating current PO
			for (final String columnName : availableColumnNames)
			{
				if (updateAddress(po, columnName))
				{
					updated = true;
				}
			}

			if (updated)
			{
				count_updated++;
			}

			// Optimization: commit on each 50rows
			if (count_updated % 50 == 0)
			{
				try
				{
					commitEx();
				}
				catch (SQLException e)
				{
					throw new AdempiereException("Cannot commit transaction", e);
				}
			}

		}

		addLog("@Updated@ #" + count_updated + "/" + count_all);
	}

	public boolean updateAddress(PO po, String columnName)
	{
		try
		{
			return updateAddress0(po, columnName);
		}
		catch (Exception e)
		{
			addLog("Error on " + po + ", " + columnName + ": " + e.getLocalizedMessage());
			log.warn(e.getLocalizedMessage(), e);
		}

		return true;
	}

	private boolean updateAddress0(PO po, String columnName) throws Exception
	{
		final String addressOld = po.get_ValueAsString(columnName);
		if (!Util.isEmpty(addressOld, true))
		{
			return false;
		}

		//
		// Case: I_C_BPartner_Location.Address
		// See http://dewiki908/mediawiki/index.php/03771_Rechnung_Anschrift-Text_nicht_gef%C3%BCllt_%282013010910000028%29
		if (I_C_BPartner_Location.Table_Name.equals(po.get_TableName()) && I_C_BPartner_Location.COLUMNNAME_Address.equals(columnName))
		{
			final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.create(po, I_C_BPartner_Location.class);
			Services.get(IBPartnerBL.class).setAddress(bpLocation);
			if (Util.isEmpty(bpLocation.getAddress()))
			{
				log.warn("Cannot calculate Address for " + bpLocation);
			}

			InterfaceWrapperHelper.save(bpLocation);
			return true;
		}
		//
		// Case: DocumentLocation
		else if (I_C_Order.COLUMNNAME_BPartnerAddress.equals(columnName))
		{
			final IDocumentLocation doc = InterfaceWrapperHelper.create(po, IDocumentLocation.class);
			Services.get(IDocumentLocationBL.class).setBPartnerAddress(doc);
			InterfaceWrapperHelper.save(doc);
		}
		//
		// Case: DocumentBillLocation
		else if (I_C_Order.COLUMNNAME_BillToAddress.equals(columnName))
		{
			final IDocumentBillLocation doc = InterfaceWrapperHelper.create(po, IDocumentBillLocation.class);
			Services.get(IDocumentLocationBL.class).setBillToAddress(doc);
			InterfaceWrapperHelper.save(doc);
		}
		//
		// Case: DocumentDeliveryToAddress
		else if (I_C_Order.COLUMNNAME_DeliveryToAddress.equals(columnName))
		{
			final IDocumentDeliveryLocation doc = InterfaceWrapperHelper.create(po, IDocumentDeliveryLocation.class);
			Services.get(IDocumentLocationBL.class).setDeliveryToAddress(doc);
			InterfaceWrapperHelper.save(doc);
		}
		// Not handled column => error
		else
		{
			throw new IllegalStateException("The column is not one of the expected - " + columnName);
		}

		return true;
	}

	private Iterator<PO> fetchRecords(final MTable table, final List<String> addressColumnNames)
	{
		final StringBuilder whereClause = new StringBuilder("1=1");
		final List<Object> params = new ArrayList<Object>();

		if (p_AD_Org_ID > 0)
		{
			whereClause.append(" AND AD_Org_ID = ?");
			params.add(p_AD_Org_ID);
		}

		final StringBuilder whereAddresses = new StringBuilder();
		for (String columnName : addressColumnNames)
		{
			if (table.getColumn(columnName) == null)
				continue;
			if (whereAddresses.length() > 0)
				whereAddresses.append(" OR ");
			whereAddresses.append(columnName).append(" IS NULL");
		}
		if (whereAddresses.length() == 0)
		{
			throw new AdempiereException("Table " + table.getTableName() + " does not have address columns");
		}
		else
		{
			whereClause.append(" AND ( ").append(whereAddresses).append(" )");
		}
		
		return new Query(getCtx(), table.getTableName(), whereClause.toString(), get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setParameters(params)
				.iterate();
	}
}
