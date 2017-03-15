/**
 * 
 */
package de.metas.adempiere.service;

import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Country_Sequence;

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
public interface ICountrySequenceBL extends ISingletonService
{
	/**
	 * retrieve country sequence per org and language
	 * @param country
	 * @param org
	 * @param language
	 * @return
	 */
	public List<I_C_Country_Sequence> retrieveCountrySequence(final I_C_Country country, final I_AD_Org org, final String language);
}
