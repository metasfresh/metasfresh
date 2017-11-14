package de.metas.printing.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.printing.api.IPrintJobBL.ContextForAsyncProcessing;

/*
 * #%L
 * de.metas.printing.base
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

public class IPrintJobBLTest
{

	/** test to gain confidence in the default behavior of lombok */
	@Test
	public void contextForAsyncProcessingDefaults()
	{
		final ContextForAsyncProcessing result = ContextForAsyncProcessing.builder().build();
		assertThat(result.getAdPInstanceId()).isEqualTo(-1);
		assertThat(result.getParentAsyncBatchId()).isEqualTo(-1);
	}

	/** test to gain confidence in the default behavior of lombok */
	@Test
	public void contextForAsyncProcessingDefaults2()
	{
		final ContextForAsyncProcessing result = ContextForAsyncProcessing.builder()
				.adPInstanceId(23)
				.build();
		assertThat(result.getAdPInstanceId()).isEqualTo(23);
		assertThat(result.getParentAsyncBatchId()).isEqualTo(-1);
	}
}
