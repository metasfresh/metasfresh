package de.metas.dao.selection.pagination;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.Instant;

import de.metas.common.util.time.SystemTime;
import org.junit.jupiter.api.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

class PageDescriptorTest
{
	@Test
	void createNext()
	{
		final Instant time = SystemTime.asInstant();
		final PageDescriptor pd = PageDescriptor.createNew("querySelectionUUID", 10, 100, time);
		assertThat(pd.getOffset()).isEqualTo(0);

		final PageDescriptor nextPd = pd.createNext();
		assertThat(nextPd.getOffset()).isEqualTo(10);

		assertThat(nextPd.getPageIdentifier()).isNotEqualTo(pd.getPageIdentifier());
		assertThat(nextPd.getPageIdentifier().getSelectionUid()).isEqualTo(pd.getPageIdentifier().getSelectionUid());

		assertThat(nextPd.getTotalSize()).isEqualTo(pd.getTotalSize());
		assertThat(nextPd.getPageSize()).isEqualTo(pd.getPageSize());
		assertThat(nextPd.getSelectionTime()).isEqualTo(time);
	}
}
