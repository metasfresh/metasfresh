package de.metas.procurement.base;

import java.util.Date;
import java.util.List;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
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

public interface IPMMProductDAO extends ISingletonService
{
	IQueryBuilder<I_PMM_Product> retrieveAllPMMProductsValidOnDateQuery(Date date);

	List<I_PMM_Product> retrieveByHUPIItemProduct(I_M_HU_PI_Item_Product huPIItemProduct);

	List<I_PMM_Product> retrieveByProduct(I_M_Product product);

	List<I_PMM_Product> retrieveByBPartner(I_C_BPartner bpartner);

	/**
	 * Retrieve the PMM Product for the given product, date and M_HU_PI_Item_Product.
	 * The PMM product must be for the given partner or not have a partner set at all.
	 * 
	 * @param date
	 * @param productId
	 * @param partnerId
	 * @param huPIPId
	 * @return
	 */
	I_PMM_Product retrieveForDateAndProduct(Date date, int productId, int partnerId, int huPIPId);
}
