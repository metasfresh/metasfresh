package de.metas.invoicecandidate.spi;

import de.metas.invoicecandidate.model.I_M_InventoryLine;
import de.metas.util.ISingletonService;

import java.util.Iterator;
import java.util.Properties;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public interface IInventoryLine_HandlerDAO extends ISingletonService
{

	/**
	 * Retrieve all the inventory lines that belong to completed material disposal (internal use inventory) documents. Only the lines which do not yet have invoice candidates will be retrieved
	 */
	Iterator<I_M_InventoryLine> retrieveAllLinesWithoutIC(Properties ctx, int limit, String trxName);

}
