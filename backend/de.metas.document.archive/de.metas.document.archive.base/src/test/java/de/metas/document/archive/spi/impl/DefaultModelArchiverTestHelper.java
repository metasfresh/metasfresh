/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.document.archive.spi.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.document.DocTypeId;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.invoice.service.InvoiceDocumentReportAdvisor;
import de.metas.process.AdProcessId;
import de.metas.report.DefaultPrintFormatsRepository;
import de.metas.report.DocTypePrintOptionsRepository;
import de.metas.report.DocumentPrintOptionDescriptorsRepository;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.PrintFormatId;
import de.metas.report.PrintFormatRepository;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_PrintFormat;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;

import javax.annotation.Nullable;

public class DefaultModelArchiverTestHelper
{
	public ClientId createClient()
	{
		final I_AD_Client record = InterfaceWrapperHelper.newInstance(I_AD_Client.class);
		InterfaceWrapperHelper.save(record);
		return ClientId.ofRepoId(record.getAD_Client_ID());
	}

	public BPartnerId createBPartner(final String adLanguage)
	{
		final I_C_BPartner record = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		record.setAD_Language(adLanguage);
		InterfaceWrapperHelper.save(record);
		return BPartnerId.ofRepoId(record.getC_BPartner_ID());
	}

	@Builder(builderMethodName = "docType", builderClassName = "$DocTypeBuilder")
	private DocTypeId createDocType(@Nullable final PrintFormatId printFormatId)
	{
		final I_C_DocType record = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		record.setAD_PrintFormat_ID(PrintFormatId.toRepoId(printFormatId));
		InterfaceWrapperHelper.save(record);
		return DocTypeId.ofRepoId(record.getC_DocType_ID());
	}

	@Builder(builderMethodName = "printFormat", builderClassName = "$PrintFormatBuilder")
	private PrintFormatId createPrintFormat(@Nullable final AdProcessId printProcessId)
	{
		final I_AD_PrintFormat record = InterfaceWrapperHelper.newInstance(I_AD_PrintFormat.class);
		record.setJasperProcess_ID(AdProcessId.toRepoId(printProcessId));
		InterfaceWrapperHelper.save(record);
		return PrintFormatId.ofRepoId(record.getAD_PrintFormat_ID());
	}

	@Builder(builderMethodName = "process", builderClassName = "$ProcessBuilder")
	private AdProcessId createProcess()
	{
		final I_AD_Process record = InterfaceWrapperHelper.newInstance(I_AD_Process.class);
		InterfaceWrapperHelper.save(record);
		return AdProcessId.ofRepoId(record.getAD_Process_ID());
	}

	@Builder(builderMethodName = "docOutboundConfig", builderClassName = "$DocOutboundConfigBuilder")
	private void createDocOutboundConfig(
			@NonNull final String tableName,
			@Nullable final PrintFormatId printFormatId)
	{
		final I_C_Doc_Outbound_Config record = InterfaceWrapperHelper.newInstance(I_C_Doc_Outbound_Config.class);
		record.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(tableName));
		record.setAD_PrintFormat_ID(PrintFormatId.toRepoId(printFormatId));
		InterfaceWrapperHelper.save(record);
	}
}
