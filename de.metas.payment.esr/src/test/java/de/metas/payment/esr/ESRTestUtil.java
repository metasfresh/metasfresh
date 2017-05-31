package de.metas.payment.esr;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.util.Services;

import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.payment.esr
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

@UtilityClass
public class ESRTestUtil
{
	public I_ESR_ImportLine retrieveSingleLine(final I_ESR_Import esrImport)
	{
		final List<I_ESR_ImportLine> esrImportLines = Services.get(IESRImportDAO.class).retrieveLines(esrImport);
		assertThat(esrImportLines.size(), is(1));
		final I_ESR_ImportLine esrImportLine = esrImportLines.get(0);
		return esrImportLine;
	}

	/**
	 * Returns a trx config that is used by tests here and there.
	 * Note that the {@link TrxPropagation#REQUIRES_NEW} goes hand in hand with {@link ESRTestBase#contextProvider} being created with {@link ITrx#TRXNAME_None}.
	 * 
	 * @return
	 */
	public ITrxRunConfig createTrxRunconfig()
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final ITrxRunConfig trxRunConfig = trxManager.newTrxRunConfigBuilder()
				.setTrxPropagation(TrxPropagation.REQUIRES_NEW)
				.setOnRunnableSuccess(OnRunnableSuccess.COMMIT)
				.setOnRunnableFail(OnRunnableFail.ASK_RUNNABLE)
				.build();
		return trxRunConfig;
	}

}
