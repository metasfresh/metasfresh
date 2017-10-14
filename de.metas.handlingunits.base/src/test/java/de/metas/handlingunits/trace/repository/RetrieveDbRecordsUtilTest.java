package de.metas.handlingunits.trace.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.OptionalInt;

import org.adempiere.ad.dao.IQueryBuilder;
import org.junit.Test;

import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceEventQuery;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class RetrieveDbRecordsUtilTest
{

	@Test
	public void configureQueryBuilder_orgId()
	{
		final HUTraceEventQuery query = HUTraceEventQuery.builder().orgId(30).build();

		final IQueryBuilder<I_M_HU_Trace> queryBuilder = RetrieveDbRecordsUtil.createQueryBuilderOrNull(query);
		assertThat(queryBuilder).isNotNull();
	}

	@Test
	public void configureQueryBuilder_huTraceEventId()
	{
		final HUTraceEventQuery query = HUTraceEventQuery.builder().huTraceEventId(OptionalInt.of(30)).build();

		final IQueryBuilder<I_M_HU_Trace> queryBuilder = RetrieveDbRecordsUtil.createQueryBuilderOrNull(query);
		assertThat(queryBuilder).isNotNull();
	}

}
