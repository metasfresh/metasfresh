package de.metas.acct;

import org.adempiere.util.ISingletonService;

/*
 * #%L
 * de.metas.acct.base
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
 * {@link VATCode} DAO
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IVATCodeDAO extends ISingletonService
{
	/**
	 * Find matching {@link VATCode} for given context.
	 * 
	 * @param request
	 * @return vat code or {@link VATCode#NULL}; never returns null
	 */
	VATCode findVATCode(VATCodeMatchingRequest request);

}
