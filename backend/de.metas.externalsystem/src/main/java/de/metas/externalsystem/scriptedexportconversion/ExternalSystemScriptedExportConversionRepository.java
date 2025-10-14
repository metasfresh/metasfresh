/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem.scriptedexportconversion;

import de.metas.document.DocBaseType;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion;
import de.metas.process.AdProcessId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.AdTableAndClientId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.service.ClientId;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public class ExternalSystemScriptedExportConversionRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public Stream<ExternalSystemScriptedExportConversionConfig> retrieveActiveConfigs()
	{
		return queryBL
				.createQueryBuilder(I_ExternalSystem_Config_ScriptedExportConversion.class)
				.addOnlyActiveRecordsFilter()
				.iterateAndStream()
				.map(ExternalSystemScriptedExportConversionRepository::fromRecord);
	}

	@NonNull
	public Stream<ExternalSystemScriptedExportConversionConfig> retrieveActiveBy(@NonNull final AdTableAndClientId tableAndClientId)
	{
		return getOrderedQueryBuilder(tableAndClientId)
				.iterateAndStream()
				.map(ExternalSystemScriptedExportConversionRepository::fromRecord);
	}

	public boolean existsActive(@NonNull final AdTableAndClientId tableAndClientId)
	{
		return getOrderedQueryBuilder(tableAndClientId)
				.create()
				.anyMatch();
	}

	public boolean existsActiveOutOfTrx(@NonNull final AdTableAndClientId tableAndClientId)
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_ExternalSystem_Config_ScriptedExportConversion.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_ScriptedExportConversion.COLUMNNAME_AD_Table_ID, tableAndClientId.getTableId())
				.addEqualsFilter(I_ExternalSystem_Config_ScriptedExportConversion.COLUMNNAME_AD_Client_ID, tableAndClientId.getClientId())
				.orderBy(I_ExternalSystem_Config_ScriptedExportConversion.COLUMN_SeqNo)
				.create()
				.anyMatch();
	}

	@NonNull
	public static ExternalSystemScriptedExportConversionConfig fromRecord(@NonNull final I_ExternalSystem_Config_ScriptedExportConversion config)
	{
		return fromRecord(config, ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID()));
	}

	@NonNull
	public static ExternalSystemScriptedExportConversionConfig fromRecord(@NonNull final I_ExternalSystem_Config_ScriptedExportConversion config,
																		  @NonNull final ExternalSystemParentConfigId parentConfigId)
	{
		return ExternalSystemScriptedExportConversionConfig.builder()
				.id(ExternalSystemScriptedExportConversionConfigId.ofRepoId(config.getExternalSystem_Config_ScriptedExportConversion_ID()))
				.parentId(parentConfigId)
				.value(config.getExternalSystemValue())
				.description(config.getDescription())
				.outboundDataProcessId(AdProcessId.ofRepoIdOrNull(config.getAD_Process_OutboundData_ID()))
				.scriptIdentifier(config.getScriptIdentifier())
				.outboundHttpEndpoint(config.getOutboundHttpEP())
				.outboundHttpToken(config.getOutboundHttpToken())
				.outboundHttpMethod(config.getOutboundHttpMethod())
				.adTableId(AdTableId.ofRepoId(config.getAD_Table_ID()))
				.docBaseType(DocBaseType.ofNullableCode(config.getDocBaseType()))
				.whereClause(config.getWhereClause())
				.seqNo(config.getSeqNo())
				.isActive(config.isActive())
				.clientId(ClientId.ofRepoId(config.getAD_Client_ID()))
				.build();
	}

	@NonNull
	private IQueryBuilder<I_ExternalSystem_Config_ScriptedExportConversion> getOrderedQueryBuilder(@NonNull final AdTableAndClientId tableAndClientId)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_ScriptedExportConversion.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_ScriptedExportConversion.COLUMNNAME_AD_Table_ID, tableAndClientId.getTableId())
				.addEqualsFilter(I_ExternalSystem_Config_ScriptedExportConversion.COLUMNNAME_AD_Client_ID, tableAndClientId.getClientId())
				.orderBy(I_ExternalSystem_Config_ScriptedExportConversion.COLUMN_SeqNo);
	}
}
