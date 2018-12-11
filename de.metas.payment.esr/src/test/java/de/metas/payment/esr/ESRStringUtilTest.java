package de.metas.payment.esr;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ESRStringUtilTest
{

	@Test
	public void formatReferenceNumber()
	{
		assertThat(ESRStringUtil.formatReferenceNumber("123456200001888888888888885")).isEqualTo("12 34562 00001 88888 88888 88885");
		assertThat(ESRStringUtil.formatReferenceNumber("12 34562 00001 88888 88888 88885")).isEqualTo("12 34562 00001 88888 88888 88885");
	}

}
