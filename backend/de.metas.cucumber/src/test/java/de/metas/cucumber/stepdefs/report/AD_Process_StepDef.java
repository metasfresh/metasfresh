/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.report;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import de.metas.i18n.Language;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessCalledFrom;
import de.metas.process.ProcessInfo;
import de.metas.report.server.LocalReportServer;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportResult;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.ArchiveRequest;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Process;
import org.compiere.util.Env;
import org.springframework.core.io.ByteArrayResource;

import static de.metas.cucumber.stepdefs.util.IdentifiersResolver.RECORD_ID;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class AD_Process_StepDef
{
	@NonNull private final IdentifiersResolver identifiersResolver;

	@NonNull private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);
	@NonNull private final IADPInstanceDAO pInstanceDAO = Services.get(IADPInstanceDAO.class);
	@NonNull private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);

	@And("The jasper process is run")
	public void run_jasper_processes(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::run_jasper_process);
	}

	public void run_jasper_process(@NonNull final DataTableRow row)
	{
		final String value = row.getAsString(I_AD_Process.COLUMNNAME_Value);
		final AdProcessId processId = processDAO.retrieveProcessIdByValue(value);
		assertThat(processId).isNotNull();

		final TableRecordReference recordRef = identifiersResolver.getTableRecordReference(row.getAsIdentifier(RECORD_ID));

		final ProcessInfo jasperProcessInfo = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setProcessCalledFrom(ProcessCalledFrom.Scheduler)
				.setAD_Process_ID(processId)
				.setAD_PInstance(pInstanceDAO.createAD_PInstance(processId))
				.setReportLanguage(Language.getBaseLanguage())
				.setJRDesiredOutputType(OutputType.PDF)
				.setArchiveReportData(true)
				.setRecord(recordRef)
				.build();

		pInstanceDAO.saveProcessInfoOnly(jasperProcessInfo);

		final LocalReportServer server = new LocalReportServer();
		final ReportResult reportResult = server.report(
				processId.getRepoId(),
				jasperProcessInfo.getPinstanceId().getRepoId(),
				Language.getBaseLanguage().getAD_Language(),
				OutputType.PDF
		);

		archiveBL.archive(ArchiveRequest.builder()
				.ctx(jasperProcessInfo.getCtx())
				.trxName(ITrx.TRXNAME_ThreadInherited)
				.data(new ByteArrayResource(reportResult.getReportContent()))
				.force(true) // archive it even if AutoArchive says no
				.save(true)
				.isReport(jasperProcessInfo.isReportingProcess())
				.recordRef(jasperProcessInfo.getRecordRefNotNull())
				.processId(jasperProcessInfo.getAdProcessId())
				.pinstanceId(jasperProcessInfo.getPinstanceId())
				.archiveName(reportResult.getReportFilename())
				.isDirectProcessQueueItem(false)
				.build());
	}
}
