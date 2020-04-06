package org.adempiere.ad.modelvalidator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class ModelChangeTypeTest
{
	@Test
	public void test_valueOf()
	{
		for (final ModelChangeType changeType : ModelChangeType.values())
		{
			assertThat(ModelChangeType.valueOf(changeType.toInt())).isSameAs(changeType);
		}
	}

	@Test
	public void test_isBeforeSaveTrx()
	{
		assertThat(ModelChangeType.isBeforeSaveTrx(ModelChangeType.AFTER_NEW)).isFalse();
		assertThat(ModelChangeType.isBeforeSaveTrx(ModelChangeType.BEFORE_SAVE_TRX)).isTrue();

		assertThat(ModelChangeType.isBeforeSaveTrx(DocTimingType.BEFORE_COMPLETE)).isFalse();
	}
}
