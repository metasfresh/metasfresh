package de.metas.letter.process;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;

import com.google.common.collect.ImmutableList;

import de.metas.letter.service.async.spi.impl.C_Letter_CreateFromMKTG_ContactPerson_Async;
import de.metas.marketing.base.model.I_MKTG_Campaign_ContactPerson;
import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.marketing.serialletter
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

public class C_Letter_CreateFrom_MKTG_ContactPerson extends JavaProcess
{
	private int campaignId;

	@Override
	protected void prepare()
	{
		campaignId = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception
	{
		final List<Integer>	 campaignContactPersonIds = Services.get(IQueryBL.class).createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_Campaign_ID, campaignId)
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.iterateAndStream()
				.map(I_MKTG_Campaign_ContactPerson::getMKTG_Campaign_ContactPerson_ID)
				.collect(ImmutableList.toImmutableList());

		C_Letter_CreateFromMKTG_ContactPerson_Async.createWorkpackage(campaignContactPersonIds);

		return MSG_OK;
	}
}
