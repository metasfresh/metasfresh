/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.edi.api.impl;

import de.metas.edi.model.I_M_InOut;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EDIInOutDAO
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	public I_M_InOut getByIdOutOfTrx(@NonNull final InOutId inoutId)
	{
		return inOutDAO.getByIdOutOfTrx(inoutId, I_M_InOut.class);
	}

	@NonNull
	public List<I_M_InOut> getByEDIPInstanceId(@NonNull final PInstanceId ediInstanceId)
	{
		return queryBL.createQueryBuilder(I_M_InOut.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InOut.COLUMNNAME_EDI_AD_PInstance_ID, ediInstanceId)
				.create()
				.listImmutable();
	}

	public void save(@NonNull final I_M_InOut inOut)
	{
		InterfaceWrapperHelper.save(inOut);
	}

	public void saveOutOfTrx(@NonNull final I_M_InOut inOut)
	{
		InterfaceWrapperHelper.save(inOut, ITrx.TRXNAME_None);
	}


}
