<<<<<<< HEAD
package de.metas.acct.aggregation.legacy.impl;

import org.slf4j.Logger;
import de.metas.logging.LogManager;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
/*
 * #%L
 * de.metas.acct.base
 * %%
<<<<<<< HEAD
 * Copyright (C) 2016 metas GmbH
=======
 * Copyright (C) 2024 metas GmbH
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
<<<<<<< HEAD
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
=======
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

<<<<<<< HEAD
=======
package de.metas.acct.aggregation.legacy.impl;

import de.metas.logging.LogManager;
import org.slf4j.Logger;

/**
 * This file is needed for unit tests.
 */
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
public class PlainLegacyFactAcctLogDAO extends LegacyFactAcctLogDAO
{
	private static final Logger logger = LogManager.getLogger(PlainLegacyFactAcctLogDAO.class);

	@Override
	public void updateFactAcctEndingBalanceForTag(final String processingTag)
	{
		logger.warn("Updating Fact_Acct_EndingBalance not supported. Skipped.");
	}
}
