/*
 * #%L
 * marketing-base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.marketing.base.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.*;

class SyncDirectionTest
{
	@ParameterizedTest
	@EnumSource(SyncDirection.class)
	void map(SyncDirection syncDirection)
	{
		final SyncDirection.CaseMapper<SyncDirection> caseMapper = new SyncDirection.CaseMapper<SyncDirection>()
		{
			@Override
			public SyncDirection localToRemote() {return SyncDirection.LOCAL_TO_REMOTE;}

			@Override
			public SyncDirection remoteToLocal() {return SyncDirection.REMOTE_TO_LOCAL;}
		};

		assertThat(syncDirection.map(caseMapper)).isSameAs(syncDirection);
	}
}