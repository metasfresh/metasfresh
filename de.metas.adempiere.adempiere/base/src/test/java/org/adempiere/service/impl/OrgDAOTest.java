package org.adempiere.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.adempiere.service.IOrgDAO.OrgQuery;
import org.adempiere.service.OrgId;
import org.adempiere.service.OrgIdNotFoundException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

class OrgDAOTest
{

	private OrgDAO orgDAO;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		orgDAO = new OrgDAO();
	}

	@ParameterizedTest
	@ValueSource(strings = { "true", "false" })
	void retrieveOrgIdBy_non_existing_fail(final String outOfTrx)
	{
		final boolean valueOf = Boolean.valueOf(outOfTrx);
		final OrgQuery query = OrgQuery
				.builder()
				.failIfNotExists(true)
				.orgValue("orgValue")
				.outOfTrx(valueOf)
				.build();

		assertThrows(OrgIdNotFoundException.class, () -> orgDAO.retrieveOrgIdBy(query));
	}

	@ParameterizedTest
	@ValueSource(strings = { "true", "false" })
	void retrieveOrgIdBy_non_existing_dont_fail(final String outOfTrx)
	{
		final boolean valueOf = Boolean.valueOf(outOfTrx);
		final OrgQuery query = OrgQuery
				.builder()
				.failIfNotExists(false)
				.orgValue("orgValue")
				.outOfTrx(valueOf)
				.build();

		final Optional<OrgId> result = orgDAO.retrieveOrgIdBy(query);
		assertThat(result).isEmpty();
	}

}
