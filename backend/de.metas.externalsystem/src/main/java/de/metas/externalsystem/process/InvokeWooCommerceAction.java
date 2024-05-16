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

package de.metas.externalsystem.process;

import de.metas.common.util.EmptyUtil;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.externalservice.process.AlterExternalSystemServiceStatusAction;
import de.metas.externalsystem.model.I_ExternalSystem_Config_WooCommerce;
import de.metas.externalsystem.woocommerce.ExternalSystemWooCommerceConfig;
import de.metas.externalsystem.woocommerce.ExternalSystemWooCommerceConfigId;
import de.metas.process.IProcessPreconditionsContext;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.util.HashMap;
import java.util.Map;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CAMEL_HTTP_RESOURCE_AUTH_KEY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE;

public class InvokeWooCommerceAction extends AlterExternalSystemServiceStatusAction
{
	public final ExternalSystemConfigRepo externalSystemConfigDAO = SpringContextHolder.instance.getBean(ExternalSystemConfigRepo.class);

	@Override
	protected IExternalSystemChildConfigId getExternalChildConfigId()
	{
		final int id;

		if (this.childConfigId > 0)
		{
			id = this.childConfigId;
		}
		else
		{
			final IExternalSystemChildConfig childConfig = externalSystemConfigDAO.getChildByParentIdAndType(ExternalSystemParentConfigId.ofRepoId(getRecord_ID()), getExternalSystemType())
					.orElseThrow(() -> new AdempiereException("No childConfig found for type WooCommerce and parent config")
							.appendParametersToMessage()
							.setParameter("externalSystemParentConfigId:", ExternalSystemParentConfigId.ofRepoId(getRecord_ID())));

			id = childConfig.getId().getRepoId();
		}

		return ExternalSystemWooCommerceConfigId.ofRepoId(id);
	}

	@Override
	protected Map<String, String> extractExternalSystemParameters(@NonNull final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final ExternalSystemWooCommerceConfig woocommerceConfig = ExternalSystemWooCommerceConfig.cast(externalSystemParentConfig.getChildConfig());

		if (EmptyUtil.isEmpty(woocommerceConfig.getCamelHttpResourceAuthKey()))
		{
			throw new AdempiereException("camelHttpResourceAuthKey for childConfig should not be empty at this point")
					.appendParametersToMessage()
					.setParameter("childConfigId", woocommerceConfig.getId());
		}

		final Map<String, String> parameters = new HashMap<>();
		parameters.put(PARAM_CAMEL_HTTP_RESOURCE_AUTH_KEY, woocommerceConfig.getCamelHttpResourceAuthKey());
		parameters.put(PARAM_CHILD_CONFIG_VALUE, woocommerceConfig.getValue());

		return parameters;
	}

	@Override
	protected String getTabName()
	{
		return ExternalSystemType.WOO.getName();
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.WOO;
	}

	@Override
	protected long getSelectedRecordCount(@NonNull final IProcessPreconditionsContext context)
	{
		return context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_ExternalSystem_Config_WooCommerce.Table_Name.equals(recordRef.getTableName()))
				.count();
	}
}
