/*
 * #%L
 * de.metas.edi
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

package de.metas.edi.process;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.edi.api.impl.pack.EDIDesadvPackId;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;

import java.util.List;
import java.util.stream.Collectors;

public class EDI_DesadvPackGenerateCSV_FileForSSCC_Labels extends EDI_GenerateCSV_FileForSSCC_Labels
{

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.getSelectionSize().isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (context.getSelectionSize().isMoreThanOneSelected())
		{
			final IQueryFilter<I_EDI_Desadv_Pack> selectedRecordsFilter = context.getQueryFilter(I_EDI_Desadv_Pack.class);

			final int differentConfigsSize = queryBL
					.createQueryBuilder(I_EDI_Desadv_Pack.class)
					.filter(selectedRecordsFilter)
					.andCollect(I_EDI_Desadv.COLUMN_EDI_Desadv_ID, I_EDI_Desadv.class)
					.create()
					.list()
					.stream()
					.map(ediDesadv -> BPartnerId.ofRepoId(ediDesadv.getC_BPartner_ID()))
					.map(bpartnerId -> zebraConfigRepository.retrieveZebraConfigId(bpartnerId, zebraConfigRepository.getDefaultZebraConfigId()))
					.collect(Collectors.toSet())
					.size();

			if (differentConfigsSize > 1)
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason(msgBL.getTranslatableMsgText(EDI_GenerateCSV_FileForSSCC_Labels.MSG_DIFFERENT_ZEBRA_CONFIG_NOT_SUPPORTED));
			}
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_EDI_Desadv_Pack> selectedRecordsFilter = getProcessInfo()
				.getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final List<EDIDesadvPackId> list = queryBL
				.createQueryBuilder(I_EDI_Desadv_Pack.class)
				.filter(selectedRecordsFilter)
				.create()
				.stream()
				.map(I_EDI_Desadv_Pack::getEDI_Desadv_Pack_ID)
				.map(EDIDesadvPackId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		if (!Check.isEmpty(list))
		{
			generateCSV_FileForSSCC_Labels(list);
		}

		return MSG_OK;
	}
}
