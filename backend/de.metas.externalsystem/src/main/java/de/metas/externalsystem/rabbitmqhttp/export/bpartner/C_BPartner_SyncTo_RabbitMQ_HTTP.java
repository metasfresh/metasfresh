/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.rabbitmqhttp.export.bpartner;

import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.export.ExportToExternalSystemService;
import de.metas.externalsystem.export.bpartner.C_BPartner_SyncTo_ExternalSystem;
import de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP;
import de.metas.externalsystem.rabbitmqhttp.ExportBPartnerToRabbitMQService;
import de.metas.externalsystem.rabbitmqhttp.ExternalSystemRabbitMQConfig;
import de.metas.externalsystem.rabbitmqhttp.ExternalSystemRabbitMQConfigId;
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;

import java.util.Optional;

public class C_BPartner_SyncTo_RabbitMQ_HTTP extends C_BPartner_SyncTo_ExternalSystem
{
	private final ExportBPartnerToRabbitMQService exportBPartnerToRabbitMQService = SpringContextHolder.instance.getBean(ExportBPartnerToRabbitMQService.class);
	private final DataExportAuditRepository dataExportAuditRepository = SpringContextHolder.instance.getBean(DataExportAuditRepository.class);

	private static final AdMessageKey MSG_RABBIT_MQ_SENT = AdMessageKey.of("RabbitMQ_Sent");

	private static final String PARAM_EXTERNAL_SYSTEM_CONFIG_RABBITMQ_HTTP_ID = I_ExternalSystem_Config_RabbitMQ_HTTP.COLUMNNAME_ExternalSystem_Config_RabbitMQ_HTTP_ID;
	@Param(parameterName = PARAM_EXTERNAL_SYSTEM_CONFIG_RABBITMQ_HTTP_ID)
	private int externalSystemConfigRabbitMQId;

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.RabbitMQ;
	}

	@Override
	protected IExternalSystemChildConfigId getExternalSystemChildConfigId()
	{
		return ExternalSystemRabbitMQConfigId.ofRepoId(externalSystemConfigRabbitMQId);
	}

	@Override
	protected String getExternalSystemParam()
	{
		return PARAM_EXTERNAL_SYSTEM_CONFIG_RABBITMQ_HTTP_ID;
	}

	@Override
	protected ExportToExternalSystemService getExportToBPartnerExternalSystem()
	{
		return exportBPartnerToRabbitMQService;
	}

	@Override
	protected boolean isExportAllowed(@NonNull final IExternalSystemChildConfig childConfig)
	{
		final ExternalSystemRabbitMQConfig rabbitMQConfig = ExternalSystemRabbitMQConfig.cast(childConfig);

		return rabbitMQConfig.isSyncBPartnerToRabbitMQ();
	}

	protected Optional<ProcessPreconditionsResolution> applyCustomPreconditionsIfAny(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return Optional.empty();
		}

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(context.getSingleSelectedRecordId());

		return dataExportAuditRepository.getByTableRecordReference(TableRecordReference.of(I_C_BPartner.Table_Name, bPartnerId))
				.map(bPartnerAudit -> ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_RABBIT_MQ_SENT)));
	}
}
