/**
 * 
 */
package de.metas.document.archive.api;

import de.metas.document.archive.model.I_C_BPartner;
import de.metas.util.ISingletonService;
import org.compiere.model.I_AD_User;

/*
 * #%L
 * de.metas.document.archive.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IBPartnerBL extends ISingletonService
{
	/**
	 * Check if the given partner or user have invoice email enabled<br>
	 * The priority is: check first partner, then user<br>
	 * flag <code>IsInvoiceEmailEnabled</code> is a list with values : empty, Yes, No <br>
	 * If  the flag <code>IsInvoiceEmailEnabled</code> is empty, this will return TRUE
	 */
	boolean isInvoiceEmailEnabled(final I_C_BPartner bpartner, final I_AD_User user);
}
