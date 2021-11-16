/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

import de.metas.bpartner.BPartnerId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.export.bpartner.C_BPartner_SyncTo_ExternalSystem;
import de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP;
import de.metas.externalsystem.rabbitmqhttp.ExportToRabbitMQService;
import de.metas.externalsystem.rabbitmqhttp.ExternalSystemRabbitMQConfigId;
import de.metas.process.PInstanceId;
import de.metas.process.Param;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;

public class C_BPartner_SyncTo_RabbitMQ_HTTP extends C_BPartner_SyncTo_ExternalSystem
{
	private final ExportToRabbitMQService exportToRabbitMQService = SpringContextHolder.instance.getBean(ExportToRabbitMQService.class);

	private static final String PARAM_EXTERNAL_SYSTEM_CONFIG_RABBITMQ_HTTP_ID = I_ExternalSystem_Config_RabbitMQ_HTTP.COLUMNNAME_ExternalSystem_Config_RabbitMQ_HTTP_ID;
	@Param(parameterName = PARAM_EXTERNAL_SYSTEM_CONFIG_RABBITMQ_HTTP_ID)
	private int externalSystemConfigRabbitMQId;

	@Override
	protected void exportBPartner(
			@NonNull final IExternalSystemChildConfigId externalSystemChildConfigId,
			@NonNull final BPartnerId bpartnerId,
			@Nullable final PInstanceId pInstanceId)
	{
		exportToRabbitMQService.exportBPartner(externalSystemChildConfigId, bpartnerId, pInstanceId);
	}

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
}
