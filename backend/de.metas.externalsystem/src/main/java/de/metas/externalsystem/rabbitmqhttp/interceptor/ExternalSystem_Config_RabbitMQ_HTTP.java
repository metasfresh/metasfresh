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

package de.metas.externalsystem.rabbitmqhttp.interceptor;

import de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_ExternalSystem_Config_RabbitMQ_HTTP.class)
@Component
public class ExternalSystem_Config_RabbitMQ_HTTP
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_ExternalSystem_Config_RabbitMQ_HTTP.COLUMNNAME_IsSyncBPartnersToRabbitMQ,
					I_ExternalSystem_Config_RabbitMQ_HTTP.COLUMNNAME_IsSyncExternalReferencesToRabbitMQ })
	public void syncBPartner(final I_ExternalSystem_Config_RabbitMQ_HTTP configRabbitMQHttp)
	{
		if (!configRabbitMQHttp.isSyncBPartnersToRabbitMQ() && !configRabbitMQHttp.isSyncExternalReferencesToRabbitMQ())
		{
			configRabbitMQHttp.setIsAutoSendWhenCreatedByUserGroup(false);
		}
	}
}
