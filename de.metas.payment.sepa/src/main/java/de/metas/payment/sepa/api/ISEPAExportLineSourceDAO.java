package de.metas.payment.sepa.api;

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
import java.util.Properties;

import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.util.ISingletonService;

public interface ISEPAExportLineSourceDAO extends ISingletonService
{
	/**
	 * Retrieves an iterator over all SEPA_Export_Lines of an org.
	 * Currently not used, but left for future requirements.
	 * 
	 * @param ctx
	 * @param adOrgId
	 * @param trxName
	 * @return
	 */
	Iterator<I_SEPA_Export_Line> retrieveRetrySEPAExportLines(Properties ctx, int adOrgId, String trxName);
}
