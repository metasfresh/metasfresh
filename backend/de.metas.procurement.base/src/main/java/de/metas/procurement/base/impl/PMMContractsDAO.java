package de.metas.procurement.base.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.contracts.IFlatrateDAO;
import de.metas.document.engine.IDocument;
import de.metas.procurement.base.IPMMContractsDAO;
import de.metas.procurement.base.model.I_C_Flatrate_Conditions;
import de.metas.procurement.base.model.I_C_Flatrate_DataEntry;
import de.metas.procurement.base.model.I_C_Flatrate_Term;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

	private IQueryBuilder<de.metas.contracts.model.I_C_Flatrate_Term> retrieveAllRunningContractsOnDateQuery(@Nullable final Date date)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<de.metas.contracts.model.I_C_Flatrate_Term> queryBuilder = queryBL.createQueryBuilder(de.metas.contracts.model.I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.contracts.model.I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, I_C_Flatrate_Conditions.TYPE_CONDITIONS_Procuremnt)

				// completed contract restriction
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, IDocument.STATUS_Completed)

				// gh #1241: we don't maintain the contract status for procurement contracts; in particular, we do not set it to "running" "sometimes, but not if a contract is extended
				// also, even if a contract's current status is not running, it doesn't mean that it's not valid within the period if time between its start and end date! 
				// .addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, X_C_Flatrate_Term.CONTRACTSTATUS_Laufend) 
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
				.anyMatch();
	}

	@Override
	public boolean hasRunningContracts(final I_PMM_Product pmmProduct)
	{
		Check.assumeNotNull(pmmProduct, "pmmProduct not null");
		final Date date = null; // any date
		return retrieveAllRunningContractsOnDateQuery(date)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_PMM_Product_ID, pmmProduct.getPMM_Product_ID())
				.create()
				.anyMatch();
	}

	@Nullable
	@Override
	public I_C_Flatrate_DataEntry retrieveFlatrateDataEntry(
			@NonNull final de.metas.contracts.model.I_C_Flatrate_Term flatrateTerm,
			@NonNull final Timestamp date)
	{
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

	@Nullable
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
