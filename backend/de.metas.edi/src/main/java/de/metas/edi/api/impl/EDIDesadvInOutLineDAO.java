/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2024 metas GmbH
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

import de.metas.esb.edi.model.I_EDI_DesadvLine_InOutLine;
import de.metas.inout.InOutLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EDIDesadvInOutLineDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public Optional<I_EDI_DesadvLine_InOutLine> getByInOutLineId(@NonNull final InOutLineId shipmentLineId)
	{
		return queryBL.createQueryBuilder(I_EDI_DesadvLine_InOutLine.class)
				.addEqualsFilter(I_EDI_DesadvLine_InOutLine.COLUMNNAME_M_InOutLine_ID, shipmentLineId)
				.create()
				.firstOnlyOptional(I_EDI_DesadvLine_InOutLine.class);
	}

	public void save(@NonNull final I_EDI_DesadvLine_InOutLine record)
	{
		InterfaceWrapperHelper.save(record);
	}

	@NonNull
	public I_EDI_DesadvLine_InOutLine newInstance()
	{
		return InterfaceWrapperHelper.newInstance(I_EDI_DesadvLine_InOutLine.class);
	}

	public void delete(@NonNull final I_EDI_DesadvLine_InOutLine record)
	{
		InterfaceWrapperHelper.delete(record);
	}
}
