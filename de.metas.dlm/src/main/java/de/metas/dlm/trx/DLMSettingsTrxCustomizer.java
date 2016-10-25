package de.metas.dlm.trx;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.spi.ITrxCustomizer;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*
 * #%L
 * metasfresh-dlm
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

public class DLMSettingsTrxCustomizer implements ITrxCustomizer
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	public static DLMSettingsTrxCustomizer INSTANCE = new DLMSettingsTrxCustomizer();

	private int dlmLevel = 1;
	private int dlmCoalesceLevel = 2;

	private DLMSettingsTrxCustomizer()
	{
	};

	@Override
	public void onTrxCreated(final ITrx trx)
	{
		if (Adempiere.isUnitTestMode())
		{
			logger.debug("doing nothing because we are in unit test mode");
			return;
		}

		final boolean perTrx = Services.get(ITrxManager.class).isNull(trx) ? false : true;
		// https://www.postgresql.org/docs/9.5/static/functions-admin.html
//		DB.executeFunctionCallEx(trx.getTrxName(), "select set_config('metasfresh.DLM_Level', ?, ?)", new Object[] { Integer.toString(dlmLevel), perTrx });
//		DB.executeFunctionCallEx(trx.getTrxName(), "select set_config('metasfresh.DLM_Coalesce_Level', ?, ?)", new Object[] { Integer.toString(dlmCoalesceLevel), perTrx });
	}

}
