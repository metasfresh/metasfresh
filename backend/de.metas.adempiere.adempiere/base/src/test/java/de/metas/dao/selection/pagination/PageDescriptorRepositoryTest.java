package de.metas.dao.selection.pagination;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.Instant;

import de.metas.common.util.time.SystemTime;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
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

class PageDescriptorRepositoryTest
{
	private PageDescriptorRepository pageDescriptorRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		pageDescriptorRepository = new PageDescriptorRepository();
	}

	@Test
	void test()
	{
		final Instant time = SystemTime.asInstant();
		final PageDescriptor pageDescriptor = PageDescriptor.createNew("querySelectionUUID", 10, 100, time);
		pageDescriptorRepository.save(pageDescriptor);

		final PageIdentifier pageIdentifier = pageDescriptor.getPageIdentifier();
		//final String pageUid = pageIdentifier.getPageUid();

		final PageDescriptor result = pageDescriptorRepository.getBy(pageIdentifier.getCombinedUid());

		assertThat(result).isEqualTo(pageDescriptor);

	}

}
