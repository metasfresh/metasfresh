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

package de.metas.externalsystem.externalservice.process;

import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.externalservice.ExternalServices;
import de.metas.externalsystem.process.InvokeExternalSystemProcess;
import org.compiere.SpringContextHolder;

public abstract class AlterExternalSystemServiceStatusAction extends InvokeExternalSystemProcess
{
	private final ExternalServices externalServices = SpringContextHolder.instance.getBean(ExternalServices.class);

	@Override
	protected String doIt() throws Exception
	{
		final ExternalSystemParentConfig parentConfig = externalSystemConfigDAO.getById(getExternalChildConfigId());

		this.externalServices.handleStatusUpdateIfRequired(parentConfig.getId(), this.externalRequest);

		super.doIt();

		return MSG_OK;
	}
}
