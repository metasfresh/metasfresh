package de.metas.procurement.base.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.process.DocAction;
import org.compiere.util.Env;

import de.metas.flatrate.api.IFlatrateDAO;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.procurement.base.IPMMContractsDAO;
import de.metas.procurement.base.model.I_C_Flatrate_Conditions;
import de.metas.procurement.base.model.I_C_Flatrate_DataEntry;
import de.metas.procurement.base.model.I_C_Flatrate_Term;
import de.metas.procurement.base.model.I_PMM_Product;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class PMMContractsDAO implements IPMMContractsDAO
{
	@Override
	public List<I_C_Flatrate_Term> retrieveAllRunningContractsOnDate(final Date date)
	{
		return retrieveAllRunningContractsOnDateQuery(date)
				.create()
				.list(I_C_Flatrate_Term.class);
	}

	@Override
	public List<I_C_Flatrate_Term> retrieveRunningContractsOnDateForBPartner(final Date date, final int bpartnerId)
	{
		return retrieveAllRunningContractsOnDateQuery(date)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_DropShip_BPartner_ID, bpartnerId)
				.create()
				.list(I_C_Flatrate_Term.class);
	}

	private IQueryBuilder<de.metas.flatrate.model.I_C_Flatrate_Term> retrieveAllRunningContractsOnDateQuery(final Date date)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<de.metas.flatrate.model.I_C_Flatrate_Term> queryBuilder = queryBL.createQueryBuilder(I_C_Flatrate_Conditions.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.flatrate.model.I_C_Flatrate_Conditions.COLUMNNAME_Type_Conditions, I_C_Flatrate_Conditions.TYPE_CONDITIONS_Procuremnt)
				//
				// Collect linked C_Flatrate_Terms
				.andCollectChildren(I_C_Flatrate_Term.COLUMN_C_Flatrate_Conditions_ID, de.metas.flatrate.model.I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				//
				// Running contract restriction
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, DocAction.STATUS_Completed)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, X_C_Flatrate_Term.CONTRACTSTATUS_Laufend)
		//
		;

		// Date restriction
		if (date != null)
		{
			// queryBuilder.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, date) also add terms that start in future, so we already have them on the frontend-UI
			queryBuilder.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, date);
		}

		return queryBuilder;
	}

	@Override
	public boolean hasRunningContract(final I_C_BPartner bpartner)
	{
		Check.assumeNotNull(bpartner, "bpartner not null");

		final Date date = SystemTime.asDayTimestamp();
		return retrieveAllRunningContractsOnDateQuery(date)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_DropShip_BPartner_ID, bpartner.getC_BPartner_ID())
				.create()
				.match();
	}

	@Override
	public boolean hasRunningContracts(final I_PMM_Product pmmProduct)
	{
		Check.assumeNotNull(pmmProduct, "pmmProduct not null");
		final Date date = null; // any date
		return retrieveAllRunningContractsOnDateQuery(date)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_PMM_Product_ID, pmmProduct.getPMM_Product_ID())
				.create()
				.match();
	}

	@Override
	public I_C_Flatrate_DataEntry retrieveFlatrateDataEntry(final de.metas.flatrate.model.I_C_Flatrate_Term flatrateTerm, final Timestamp date)
	{
		Check.assumeNotNull(flatrateTerm, "flatrateTerm not null");
		Check.assumeNotNull(date, "date not null");

		final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
		final List<I_C_Flatrate_DataEntry> dataEntries = InterfaceWrapperHelper.createList(
				flatrateDAO.retrieveDataEntries(flatrateTerm, date, I_C_Flatrate_DataEntry.TYPE_Procurement_PeriodBased, true),            // onlyNonSim = true
				I_C_Flatrate_DataEntry.class);

		for (final I_C_Flatrate_DataEntry dataEntry : dataEntries)
		{
			if (dataEntry.getM_Product_DataEntry_ID() == flatrateTerm.getM_Product_ID())
			{
				return dataEntry;
			}
		}
		return null;
	}

	@Override
	public I_C_Flatrate_Term retrieveTermForPartnerAndProduct(final Date date, final int bPartnerID, final int pmmProductId)
	{
		return retrieveAllRunningContractsOnDateQuery(date)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_DropShip_BPartner_ID, bPartnerID)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_PMM_Product_ID, pmmProductId)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, date)
				.orderBy()
				.addColumn(I_C_Flatrate_Term.COLUMNNAME_StartDate, Direction.Descending, Nulls.Last)
				.endOrderBy()
				.create()
				.first(I_C_Flatrate_Term.class);
	}
}
