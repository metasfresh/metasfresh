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

import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.adapter.DocumentLocationAdaptersRegistry;
import de.metas.document.location.adapter.IDocumentBillLocationAdapter;
import de.metas.document.location.adapter.IDocumentDeliveryLocationAdapter;
import de.metas.document.location.adapter.IDocumentLocationAdapter;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author cg
 *
 */
public class UpdateAddresses extends JavaProcess
{
	private final IBPartnerBL partnerBL = Services.get(IBPartnerBL.class);
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	private final DocumentLocationAdaptersRegistry documentLocationAdaptersRegistry = SpringContextHolder.instance.getBean(DocumentLocationAdaptersRegistry.class);
	private final IDocumentLocationBL documentLocationBL = SpringContextHolder.instance.getBean(IDocumentLocationBL.class);

	@Param(parameterName = "AD_Table_ID", mandatory = true)
	private AdTableId p_AD_Table_ID;

	@Param(parameterName = "AD_Org_ID")
	private OrgId p_AD_Org_ID;

	@Override
	protected String doIt()
	{
		if (p_AD_Table_ID == null)
		{
			throw new FillMandatoryException("@AD_Table_ID@");
		}

		updateAddresses(p_AD_Table_ID);

		return MSG_OK;
	}

	private void updateAddresses(final AdTableId adTableId)
	{
		final MTable table = MTable.get(getCtx(), adTableId.getRepoId());

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
		final List<String> availableColumnNames = new ArrayList<>();
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

	private boolean updateAddress0(PO po, String columnName)
	{
		final String addressOld = po.get_ValueAsString(columnName);
		if (!Check.isBlank(addressOld))
		{
			return false;
		}

		//
		// Case: I_C_BPartner_Location.Address
		// See http://dewiki908/mediawiki/index.php/03771_Rechnung_Anschrift-Text_nicht_gef%C3%BCllt_%282013010910000028%29
		if (I_C_BPartner_Location.Table_Name.equals(po.get_TableName()) && I_C_BPartner_Location.COLUMNNAME_Address.equals(columnName))
		{
			final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.create(po, I_C_BPartner_Location.class);
			partnerBL.setAddress(bpLocation);
			if (Check.isEmpty(bpLocation.getAddress()))
			{
				log.warn("Cannot calculate Address for " + bpLocation);
			}

			partnerDAO.save(bpLocation);

			return true;
		}
		//
		// Case: DocumentLocation
		else
		{
			if (I_C_Order.COLUMNNAME_BPartnerAddress.equals(columnName))
			{
				final IDocumentLocationAdapter doc = documentLocationAdaptersRegistry.getDocumentLocationAdapterIfHandled(po).get();
				documentLocationBL.updateRenderedAddressAndCapturedLocation(doc);
				InterfaceWrapperHelper.save(doc);
			}
			//
			// Case: DocumentBillLocation
			else if (I_C_Order.COLUMNNAME_BillToAddress.equals(columnName))
			{
				final IDocumentBillLocationAdapter doc = documentLocationAdaptersRegistry.getDocumentBillLocationAdapterIfHandled(po).get();
				documentLocationBL.updateRenderedAddressAndCapturedLocation(doc);
				InterfaceWrapperHelper.save(doc);
			}
			//
			// Case: DocumentDeliveryToAddress
			else if (I_C_Order.COLUMNNAME_DeliveryToAddress.equals(columnName))
			{
				final IDocumentDeliveryLocationAdapter doc = documentLocationAdaptersRegistry.getDocumentDeliveryLocationAdapter(po).get();
				documentLocationBL.updateRenderedAddressAndCapturedLocation(doc);
				InterfaceWrapperHelper.save(doc);
			}
			// Not handled column => error
			else
			{
				throw new AdempiereException("The column is not one of the expected - " + columnName);
			}
		}

		return true;
	}

	private Iterator<PO> fetchRecords(final MTable table, final List<String> addressColumnNames)
	{
		final StringBuilder whereClause = new StringBuilder("1=1");
		final List<Object> params = new ArrayList<>();

		if (p_AD_Org_ID != null)
		{
			whereClause.append(" AND AD_Org_ID = ?");
			params.add(p_AD_Org_ID);
		}

		final StringBuilder whereAddresses = new StringBuilder();
		for (String columnName : addressColumnNames)
		{
			if (table.getColumn(columnName) == null)
			{
				continue;
			}
			if (whereAddresses.length() > 0)
			{
				whereAddresses.append(" OR ");
			}
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
