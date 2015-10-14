package de.metas.purchasing.service;

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


import java.util.List;

import de.metas.purchasing.model.I_M_PurchaseSchedule;
import org.adempiere.util.ISingletonService;

public interface IPurchaseSchedulePA extends ISingletonService {

	/**
	 * 
	 * @return all entries that have
	 *         {@link I_M_PurchaseSchedule#COLUMNNAME_IncludeInPurchase} set to
	 *         <code>'Y'</code>.
	 */
	List<I_M_PurchaseSchedule> retrieveToIncludeInPO(String trxName);
}
