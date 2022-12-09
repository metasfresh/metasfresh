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

package de.metas.externalsystem.rabbitmqhttp.export.externalreference;

import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.export.ExportToExternalSystemService;
import de.metas.externalsystem.export.externalreference.S_ExternalReference_SyncTo_ExternalSystem;
import de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP;
import de.metas.externalsystem.rabbitmqhttp.ExportExternalReferenceToRabbitMQService;
import de.metas.externalsystem.rabbitmqhttp.ExternalSystemRabbitMQConfigId;
import de.metas.process.Param;
import org.compiere.SpringContextHolder;

public class S_ExternalReference_SyncTo_RabbitMQ_HTTP extends S_ExternalReference_SyncTo_ExternalSystem
{
	private final ExportExternalReferenceToRabbitMQService exportExternalReferenceToRabbitMQService = SpringContextHolder.instance.getBean(ExportExternalReferenceToRabbitMQService.class);

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
	protected ExportToExternalSystemService getExportExternalReferenceToExternalSystem()
	{
		return exportExternalReferenceToRabbitMQService;
	}
}
