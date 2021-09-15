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

package de.metas.externalsystem.rabbitmqhttp.process;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.rabbitmqhttp.ExternalSystemRabbitMQConfigId;
import de.metas.externalsystem.rabbitmqhttp.RabbitMQExternalSystemService;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;

import javax.annotation.Nullable;
import java.util.Iterator;

public class C_BPartner_SyncTo_RabbitMQ_HTTP extends JavaProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final RabbitMQExternalSystemService rabbitMQExternalSystemService = SpringContextHolder.instance.getBean(RabbitMQExternalSystemService.class);
	private final ExternalSystemConfigRepo externalSystemConfigRepo = SpringContextHolder.instance.getBean(ExternalSystemConfigRepo.class);

	private static final String PARAM_EXTERNAL_SYSTEM_CONFIG_RABBITMQ_HTTP_ID = "ExternalSystem_Config_RabbitMQ_HTTP_ID";
	@Param(parameterName = PARAM_EXTERNAL_SYSTEM_CONFIG_RABBITMQ_HTTP_ID)
	private int externalSystemConfigRabbitMQId;

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_EXTERNAL_SYSTEM_CONFIG_RABBITMQ_HTTP_ID.equals(parameter.getColumnName()))
		{
			final ImmutableList<ExternalSystemParentConfig> activeConfigs = externalSystemConfigRepo.getAllByType(ExternalSystemType.RabbitMQ)
					.stream()
					.filter(ExternalSystemParentConfig::getIsActive)
					.collect(ImmutableList.toImmutableList());

			return activeConfigs.size() == 1
					? activeConfigs.get(0).getChildConfig().getId().getRepoId()
					: IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{

		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (externalSystemConfigRepo.isAnyConfigActive(ExternalSystemType.RabbitMQ))
		{
			return ProcessPreconditionsResolution.accept();
		}
		return ProcessPreconditionsResolution.reject();
	}

	@Override
	protected String doIt() throws Exception
	{
		addLog("Calling with params: externalSystemConfigRabbitMQId {}", externalSystemConfigRabbitMQId);

		final Iterator<I_C_BPartner> bPartnerIterator = getSelectedBPartnerRecords();

		final ExternalSystemRabbitMQConfigId externalSystemRabbitMQConfigId = ExternalSystemRabbitMQConfigId.ofRepoId(externalSystemConfigRabbitMQId);

		while (bPartnerIterator.hasNext())
		{
			final BPartnerId bPartnerId = BPartnerId.ofRepoId(bPartnerIterator.next().getC_BPartner_ID());

			rabbitMQExternalSystemService.exportBPartner(externalSystemRabbitMQConfigId, bPartnerId, getPinstanceId());
		}

		return JavaProcess.MSG_OK;
	}

	@NonNull
	private Iterator<I_C_BPartner> getSelectedBPartnerRecords()
	{
		final IQueryBuilder<I_C_BPartner> bPartnerQuery = retrieveSelectedRecordsQueryBuilder(I_C_BPartner.class);

		return bPartnerQuery
				.create()
				.iterate(I_C_BPartner.class);
	}
}
