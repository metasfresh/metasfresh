package org.adempiere.ad.callout.api.impl;

import org.adempiere.util.Check;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_ColumnCallout;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class PlainADColumnCalloutDAO extends ADColumnCalloutDAO
{
	/**
	 * Overriding standard logic because {@link I_AD_ColumnCallout#getColumnName()} is a SQL virtual column which is not supported in JUnit test mode.
	 */
	@Override
	protected String extractColumnName(final I_AD_ColumnCallout cc)
	{
		Check.assume(Adempiere.isUnitTestMode(), "JUnit test mode enabled");
		return cc.getAD_Column().getColumnName();
	}
}
