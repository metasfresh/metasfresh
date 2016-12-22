package de.metas.procurement.base;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;

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

public interface IPMMContractsDAO extends ISingletonService
{

	List<I_C_Flatrate_Term> retrieveAllRunningContractsOnDate(Date date);

	List<I_C_Flatrate_Term> retrieveRunningContractsOnDateForBPartner(Date date, int bpartnerId);

	boolean hasRunningContract(I_C_BPartner bpartner);

	I_C_Flatrate_DataEntry retrieveFlatrateDataEntry(de.metas.flatrate.model.I_C_Flatrate_Term flatrateTerm, Timestamp date);

	boolean hasRunningContracts(I_PMM_Product pmmProduct);

	/**
	 * Retrieve the running procurement contract for the given Date, Partner and Product.
	 * In case there are many, choose the one with the latest start date
	 * 
	 * @param date
	 * @param bPartnerID
	 * @param pmmProductId
	 * @return
	 */
	I_C_Flatrate_Term retrieveTermForPartnerAndProduct(Date date, int bPartnerID, int pmmProductId);

}
