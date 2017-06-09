package de.metas.payment.esr.dataimporter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.metas.payment.esr.dataimporter.ESRDataLoaderFactory;
import de.metas.payment.esr.model.X_ESR_Import;

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

public class ESRDataLoaderFactoryTests
{
	@Test
	public void testGuessTypeFromFileName_camt54()
	{
		final String complicatedFileNamePrefix1 = "metasfresh_584898041309959900_camt.054-ESR-ASR_P_CH6909000000800420109_329466311_0_2016122923042261-1";
		assertCamt54(complicatedFileNamePrefix1);

		final String complicatedFileNamePrefix2 = "metasfresh_584898041309959900_-ESR-ASR_P_CH6909000000800420109_329466311_0_2016122923042261-1";
		assertCamt54(complicatedFileNamePrefix2);
	}

	private void assertCamt54(final String complicatedFileNamePrefix1)
	{
		assertThat(ESRDataLoaderFactory.guessTypeFromFileName(complicatedFileNamePrefix1 + ".xml"), is(X_ESR_Import.DATATYPE_Camt54));
		assertThat(ESRDataLoaderFactory.guessTypeFromFileName(complicatedFileNamePrefix1 + ".XML"), is(X_ESR_Import.DATATYPE_Camt54));
		assertThat(ESRDataLoaderFactory.guessTypeFromFileName(complicatedFileNamePrefix1 + ".camt"), is(X_ESR_Import.DATATYPE_Camt54));
		assertThat(ESRDataLoaderFactory.guessTypeFromFileName(complicatedFileNamePrefix1 + ".camt54"), is(X_ESR_Import.DATATYPE_Camt54));
		assertThat(ESRDataLoaderFactory.guessTypeFromFileName(complicatedFileNamePrefix1 + ".camt.54"), is(X_ESR_Import.DATATYPE_Camt54));
	}

	@Test
	public void testGuessTypeFromFileName_v11()
	{
		final String complicatedFileNamePrefix1 = "metasfresh_584898041309959900_v11-ESR-ASR_P_CH6909000000800420109_329466311_0_2016122923042261-1";
		assertv11(complicatedFileNamePrefix1);

		final String complicatedFileNamePrefix2 = "metasfresh_584898041309959900_-ESR-ASR_P_CH6909000000800420109_329466311_0_2016122923042261-1";
		assertv11(complicatedFileNamePrefix2);
	}

	private void assertv11(final String complicatedFileNamePrefix1)
	{
		assertThat(ESRDataLoaderFactory.guessTypeFromFileName(complicatedFileNamePrefix1 + ".v11"), is(X_ESR_Import.DATATYPE_V11));
		assertThat(ESRDataLoaderFactory.guessTypeFromFileName(complicatedFileNamePrefix1 + ".V11"), is(X_ESR_Import.DATATYPE_V11));
	}
}
