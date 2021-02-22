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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ZebraConfigId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.edi.api.ZebraConfigRepository;
import de.metas.edi.api.ZebraPrinterService;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.report.ReportResultData;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract class EDI_GenerateCSV_FileForSSCC_Labels extends JavaProcess implements IProcessPrecondition
{
	protected final IQueryBL queryBL = Services.get(IQueryBL.class);
	private static final AdMessageKey MSG_DIFFERENT_ZEBRA_CONFIG_NOT_SUPPORTED = AdMessageKey.of("WEBUI_ZebraConfigError");
	private final ZebraPrinterService zebraPrinterService = SpringContextHolder.instance.getBean(ZebraPrinterService.class);
	private final ZebraConfigRepository zebraConfigRepository = SpringContextHolder.instance.getBean(ZebraConfigRepository.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	@Override public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull IProcessPreconditionsContext context)
	{
		if (context.getSelectionSize().isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else  if (context.getSelectionSize().isMoreThanOneSelected())
		{
			final IQueryFilter<I_EDI_Desadv> selectedRecordsFilter = context.getQueryFilter(I_EDI_Desadv.class);
			Integer differentConfigsSize = queryBL
					.createQueryBuilder(I_EDI_Desadv.class)
					.filter(selectedRecordsFilter)
					.create()
					.list()
					.stream()
					.map(ediDesadv -> BPartnerId.ofRepoId(ediDesadv.getC_BPartner_ID()))
					.map(bpartnerId -> zebraConfigRepository.retrieveZebraConfigId(bpartnerId))
					.collect(Collectors.toSet())
					.size();

			if (differentConfigsSize != 1)
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason(msgBL.getTranslatableMsgText(MSG_DIFFERENT_ZEBRA_CONFIG_NOT_SUPPORTED));
			}

		}
		return ProcessPreconditionsResolution.accept();
	}

	void generateCSV_FileForSSCC_Labels(final Collection<Integer> desadvLinePackIDsToPrint)
	{
		final ZebraConfigId zebraConfigId = zebraConfigRepository.getZebraConfigForDesadvLinePackID(desadvLinePackIDsToPrint.stream().findFirst().get());
		final ReportResultData reportResultData = zebraPrinterService
				.createCSV_FileForSSCC18_Labels(desadvLinePackIDsToPrint, zebraConfigId, getProcessInfo().getPinstanceId());

		getProcessInfo().getResult().setReportData(reportResultData);
	}
}