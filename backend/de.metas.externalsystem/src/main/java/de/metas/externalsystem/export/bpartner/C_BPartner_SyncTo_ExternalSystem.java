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

package de.metas.externalsystem.export.bpartner;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;

import javax.annotation.Nullable;
import java.util.Iterator;

public abstract class C_BPartner_SyncTo_ExternalSystem extends JavaProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final ExternalSystemConfigRepo externalSystemConfigRepo = SpringContextHolder.instance.getBean(ExternalSystemConfigRepo.class);

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (getExternalSystemParam().equals(parameter.getColumnName()))
		{
			final ImmutableList<ExternalSystemParentConfig> activeConfigs = externalSystemConfigRepo.getAllByType(getExternalSystemType())
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

		if (!externalSystemConfigRepo.isAnyConfigActive(getExternalSystemType()))
		{
			return ProcessPreconditionsResolution.reject();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		addLog("Calling with params: externalSystemChildConfigId: {}", getExternalSystemChildConfigId());

		final Iterator<I_C_BPartner> bPartnerIterator = getSelectedBPartnerRecords();

		final IExternalSystemChildConfigId externalSystemChildConfigId = getExternalSystemChildConfigId();

		while (bPartnerIterator.hasNext())
		{
			final BPartnerId bPartnerId = BPartnerId.ofRepoId(bPartnerIterator.next().getC_BPartner_ID());

			exportBPartner(externalSystemChildConfigId, bPartnerId, getPinstanceId());
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

	protected abstract void exportBPartner(
			@NonNull final IExternalSystemChildConfigId externalSystemChildConfigId,
			@NonNull final BPartnerId bpartnerId,
			@Nullable final PInstanceId pInstanceId);

	protected abstract ExternalSystemType getExternalSystemType();

	protected abstract IExternalSystemChildConfigId getExternalSystemChildConfigId();

	protected abstract String getExternalSystemParam();
}
