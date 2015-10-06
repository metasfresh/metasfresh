package de.metas.payment.sepa.api.impl;

/*
 * #%L
 * de.metas.payment.sepa
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


import java.util.Iterator;

import org.adempiere.ad.wrapper.IPOJOFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;

import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;

public class PlainSEPADocumentDAO extends SEPADocumentDAO
{
	@Override
	public Iterator<I_SEPA_Export_Line> retrieveLines(final I_SEPA_Export doc)
	{
		return POJOLookupMap.get().getRecords(I_SEPA_Export_Line.class, new IPOJOFilter<I_SEPA_Export_Line>()
		{

			@Override
			public boolean accept(final I_SEPA_Export_Line pojo)
			{
				if (pojo.getSEPA_Export_ID() != doc.getSEPA_Export_ID())
				{
					return false;
				}
				return true;
			}
		})
				.iterator();
	}
}
