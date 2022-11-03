/*
 * #%L
 * de.metas.business
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

package de.metas.incoterms.repository;

import de.metas.incoterms.Incoterms;
import de.metas.incoterms.IncotermsId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Incoterms;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

@Repository
public class IncotermsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public Incoterms getById(@NonNull final IncotermsId incotermsId)
	{
		return ofRecord(loadOutOfTrx(incotermsId, I_C_Incoterms.class));
	}

	@NonNull
	public Incoterms getIncotermsByValue(@NonNull final String value)
	{
		final I_C_Incoterms incotermsRecord = queryBL.createQueryBuilder(I_C_Incoterms.class)
				.addEqualsFilter(I_C_Incoterms.COLUMNNAME_Value, value)
				.create()
				.firstOnlyNotNull(I_C_Incoterms.class);

		return ofRecord(incotermsRecord);
	}

	@NonNull
	private static Incoterms ofRecord(@NonNull final I_C_Incoterms incotermsRecord)
	{
		return Incoterms.builder()
				.incotermsId(IncotermsId.ofRepoId(incotermsRecord.getC_Incoterms_ID()))
				.name(incotermsRecord.getName())
				.value(incotermsRecord.getValue())
				.description(incotermsRecord.getDescription())
				.build();
	}
}
