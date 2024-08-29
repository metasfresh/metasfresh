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

import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_PrintFormat;
import org.compiere.model.I_C_Doc_Outbound_Config;
import org.compiere.model.I_C_Doc_Outbound_Config_CC;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static de.metas.common.util.CoalesceUtil.coalesce;

@Repository
public class DocOutboundConfigRepository
{
	public static final IQueryBL queryBL = Services.get(IQueryBL.class);

	public I_C_Doc_Outbound_Config getConfigById(final int docOutboundConfigId)
	{
		return InterfaceWrapperHelper.load(docOutboundConfigId, I_C_Doc_Outbound_Config.class);
	}

	// note that this method doesn't directly access the DB. Therefore, a unit test DAO implementation can extend this
	// class without problems.
	public final I_C_Doc_Outbound_Config retrieveConfig(final Properties ctx, final int tableId)
	{
		Check.assume(tableId > 0, "tableId > 0");

		final int adClientId = Env.getAD_Client_ID(ctx);

		I_C_Doc_Outbound_Config configSys = null;
		I_C_Doc_Outbound_Config config = null;
		for (final I_C_Doc_Outbound_Config currentConfig : retrieveAllConfigs())
		{
			if (currentConfig.getAD_Table_ID() == tableId)
			{
				if (currentConfig.getAD_Client_ID() == adClientId)
				{
					throwExceptionIfNotNull(config, tableId, adClientId, currentConfig);
					config = currentConfig;
				}
				else if (currentConfig.getAD_Client_ID() == 0) // system
				{
					throwExceptionIfNotNull(configSys, tableId, adClientId, currentConfig);
					configSys = currentConfig;
				}
			}
		}
		return coalesce(config, configSys);
	}

	private void throwExceptionIfNotNull(
			@Nullable final I_C_Doc_Outbound_Config alreadyFoundConfig,
			final int tableId,
			final int adClientId,
			@NonNull final I_C_Doc_Outbound_Config currentConfig)
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
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);

		return retrieveConfig(ctx, adTableId);
	}

	@Cached(cacheName = I_C_Doc_Outbound_Config_CC.Table_Name + "#AD_PrintFormatAll")
	public List<PrintFormatId> retrieveAllPrintFormatIds(final int docOutboundConfigId)
	{
		final List<PrintFormatId> printFormatIdList = new ArrayList<>();
		final I_C_Doc_Outbound_Config config = getConfigById(docOutboundConfigId);
		if (config.getAD_PrintFormat_ID() > 0)
		{
			printFormatIdList.add(PrintFormatId.ofRepoId(config.getAD_PrintFormat_ID()));
		}
		final ImmutableSet<PrintFormatId> printFormatIds = queryBL.createQueryBuilderOutOfTrx(I_C_Doc_Outbound_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Doc_Outbound_Config.COLUMNNAME_C_Doc_Outbound_Config_ID, config.getC_Doc_Outbound_Config_ID())
				.andCollectChildren(I_C_Doc_Outbound_Config_CC.COLUMN_C_Doc_Outbound_Config_ID)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_AD_PrintFormat.COLUMN_AD_PrintFormat_ID, I_AD_PrintFormat.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds(PrintFormatId::ofRepoId);

		if (!printFormatIds.isEmpty()) {printFormatIdList.addAll(printFormatIds);}

		return printFormatIdList;
	}

	@Cached(cacheName = I_C_Doc_Outbound_Config.Table_Name + "#All")
	public List<I_C_Doc_Outbound_Config> retrieveAllConfigs()
	{
		return queryBL.createQueryBuilderOutOfTrx(I_C_Doc_Outbound_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	@Cached(cacheName = I_C_Doc_Outbound_Config_CC.Table_Name + "#AD_PrintFormat_ID")
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
}
