/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.report;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_C_Doc_Outbound_Config;
import org.compiere.model.I_C_Doc_Outbound_Config_CC;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import static de.metas.common.util.CoalesceUtil.coalesce;

@Repository
public class DocOutboundConfigRepository
{
	public static final IQueryBL queryBL = Services.get(IQueryBL.class);
	public static final transient DocOutboundConfigRepository instance = new DocOutboundConfigRepository();

	private final CCache<Integer, DocOutboundConfigMap> cache = CCache.<Integer, DocOutboundConfigMap>builder()
			.tableName(I_C_Doc_Outbound_Config.Table_Name)
			.build();

	private DocOutboundConfigMap getAll()
	{
		return cache.getOrLoad(0, this::retrieveAll);
	}

	public DocOutboundConfig getById(@NonNull final DocOutboundConfigId id)
	{
		return getAll().getById(id);
	}

	private DocOutboundConfigMap retrieveAll()
	{
		final ImmutableList<DocOutboundConfig> list = queryBL.createQueryBuilder(I_C_Doc_Outbound_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(DocOutboundConfigRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new DocOutboundConfigMap(list);
	}

	private static DocOutboundConfig fromRecord(@NonNull final I_C_Doc_Outbound_Config record)
	{
		final DocOutboundConfigId id = DocOutboundConfigId.ofRepoId(record.getC_Doc_Outbound_Config_ID());
		final ImmutableList<DocOutboundConfigCC> configCCList = retrieveDocOutboundConfigCC(id);

		return DocOutboundConfig.builder()
				.id(id)
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.tableId(AdTableId.ofRepoId(record.getAD_Table_ID()))
				.printFormatId(PrintFormatId.ofRepoIdOrNull(record.getAD_PrintFormat_ID()))
				.lines(configCCList)
				.build();
	}

	public I_C_Doc_Outbound_Config load(@NonNull final DocOutboundConfigId docOutboundConfigId)
	{
		return InterfaceWrapperHelper.load(docOutboundConfigId, I_C_Doc_Outbound_Config.class);
	}

	// note that this method doesn't directly access the DB. Therefore, a unit test DAO implementation can extend this
	// class without problems.
	@Nullable
	public final I_C_Doc_Outbound_Config retrieveConfig(final Properties ctx, @NonNull final AdTableId tableId)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);

		DocOutboundConfig configSys = null;
		DocOutboundConfig config = null;
		for (final DocOutboundConfig currentConfig : retrieveAllConfigs())
		{
			if (AdTableId.equals(currentConfig.getTableId(), tableId))
			{
				if (currentConfig.getClientId().getRepoId() == adClientId)
				{
					throwExceptionIfNotNull(config, tableId, adClientId, currentConfig);
					config = currentConfig;
				}
				else if (currentConfig.getClientId().getRepoId() == 0) // system
				{
					throwExceptionIfNotNull(configSys, tableId, adClientId, currentConfig);
					configSys = currentConfig;
				}
			}
		}

		DocOutboundConfig finaConfig = coalesce(config, configSys);
		return finaConfig != null ? load(coalesce(config, configSys).getId()) : null;
	}

	private void throwExceptionIfNotNull(
			@Nullable final DocOutboundConfig alreadyFoundConfig,
			@NonNull final AdTableId tableId,
			final int adClientId,
			@NonNull final DocOutboundConfig currentConfig)
	{
		if (alreadyFoundConfig == null)
		{
			return;
		}
		final String msg = StringUtils.formatMessage(
				"Only one configuration shall exist for tableId '{}' on client '{}' but we found: {}, {}",
				tableId, adClientId, alreadyFoundConfig, currentConfig);

		throw new AdempiereException(msg)
				.markAsUserValidationError(); // this error message is not exactly nice, but we still need to inform the user
	}

	public final I_C_Doc_Outbound_Config retrieveConfigForModel(@NonNull final Object model)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final AdTableId adTableId = AdTableId.ofRepoId(InterfaceWrapperHelper.getModelTableId(model));

		return retrieveConfig(ctx, adTableId);
	}

	public ImmutableList<PrintFormatId> retrieveAllPrintFormatIds(@NonNull final DocOutboundConfigId docOutboundConfigId)
	{
		final List<PrintFormatId> printFormatIdList = new ArrayList<>();
		final DocOutboundConfig config = getById(docOutboundConfigId);
		if (config.getPrintFormatId() != null)
		{
			printFormatIdList.add(config.getPrintFormatId());
		}

		final List<DocOutboundConfigCC> configCCList = config.getLines();
		final List<PrintFormatId> printFormatIds = configCCList.stream()
				.map(DocOutboundConfigCC::getPrintFormatId)
				.collect(Collectors.toList());

		if (!printFormatIds.isEmpty()) {printFormatIdList.addAll(printFormatIds);}

		return ImmutableList.copyOf(printFormatIdList);
	}

	public ImmutableList<DocOutboundConfig> retrieveAllConfigs()
	{
		return getAll().getAllConfigs();
	}

	public Optional<AdColumnId> retrievePartnerColumnCorelatedWithPrintFormatId(@NonNull final Object model, final @Nullable PrintFormatId printFormatId)
	{
		if (printFormatId == null)
		{
			return Optional.empty();
		}

		final I_C_Doc_Outbound_Config config = retrieveConfigForModel(model);

		return queryBL.createQueryBuilderOutOfTrx(I_C_Doc_Outbound_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Doc_Outbound_Config.COLUMNNAME_C_Doc_Outbound_Config_ID, config.getC_Doc_Outbound_Config_ID())
				.andCollectChildren(I_C_Doc_Outbound_Config_CC.COLUMN_C_Doc_Outbound_Config_ID)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Doc_Outbound_Config_CC.COLUMN_AD_PrintFormat_ID, printFormatId)
				.andCollect(I_C_Doc_Outbound_Config_CC.COLUMN_BPartner_ColumnName_ID, I_AD_Column.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnlyOptional(AdColumnId::ofRepoIdOrNull);
	}

	private static ImmutableList<DocOutboundConfigCC> retrieveDocOutboundConfigCC(@NonNull final DocOutboundConfigId docOutboundConfigId)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_C_Doc_Outbound_Config_CC.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Doc_Outbound_Config_CC.COLUMN_C_Doc_Outbound_Config_ID, docOutboundConfigId)
				.stream()
				.map(DocOutboundConfigRepository::fromRecordConfigCC)
				.collect(ImmutableList.toImmutableList());
	}

	private static DocOutboundConfigCC fromRecordConfigCC(@NonNull final I_C_Doc_Outbound_Config_CC record)
	{
		return DocOutboundConfigCC.builder()
				.id(DocOutboundConfigCCId.ofRepoId(record.getC_Doc_Outbound_Config_CC_ID()))
				.docOutboundConfigId(DocOutboundConfigId.ofRepoId(record.getC_Doc_Outbound_Config_ID()))
				.columnId(AdColumnId.ofRepoId(record.getBPartner_ColumnName_ID()))
				.printFormatId(PrintFormatId.ofRepoId(record.getAD_PrintFormat_ID()))
				.build();
	}

	@Nullable
	public DocOutboundConfigCC retrieveDocOutboundConfigCC(@NonNull final DocOutboundConfigId docOutboundConfigId, @NonNull PrintFormatId printFormatId)
	{
		final DocOutboundConfig config = getById(docOutboundConfigId);
		return config.getLines()
				.stream()
				.filter(configCC -> configCC.getPrintFormatId().getRepoId() == printFormatId.getRepoId())
				.findFirst()
				.map(configCC -> configCC)
				.orElse(null);

	}

}
