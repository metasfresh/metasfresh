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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfigId;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.i18n.AdMessageKey;
import de.metas.organization.OrgId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class InvokeAlbertaForBPartnerIds extends JavaProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final InvokeAlbertaService invokeAlbertaService = SpringContextHolder.instance.getBean(InvokeAlbertaService.class);
	private final ExternalSystemConfigRepo externalSystemConfigDAO = SpringContextHolder.instance.getBean(ExternalSystemConfigRepo.class);

	public static final AdMessageKey MSG_ERR_PROCESS_NOT_AVAILABLE = AdMessageKey.of("ALBERTA_NO_CONFIG_AVAILABLE");

	public static final String PARAM_EXTERNAL_SYSTEM_CONFIG_ALBERTA_ID = "ExternalSystem_Config_Alberta_ID";
	@Param(parameterName = PARAM_EXTERNAL_SYSTEM_CONFIG_ALBERTA_ID)
	protected int externalSystemConfigAlbertaId;

	private static final String PARAM_IGNORE_SELECTION = "IsIgnoreSelectionSyncAll";
	@Param(parameterName = PARAM_IGNORE_SELECTION)
	private Boolean ignoreSelection;

	public static final String PARAM_ORG_ID = "AD_Org_ID";
	@Param(parameterName = PARAM_ORG_ID)
	protected int orgId;

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_EXTERNAL_SYSTEM_CONFIG_ALBERTA_ID.equals(parameter.getColumnName()))
		{
			final ImmutableList<ExternalSystemParentConfig> configs = externalSystemConfigDAO.getActiveByType(ExternalSystemType.Alberta);

			return configs.size() == 1
					? configs.get(0).getChildConfig().getId().getRepoId()
					: IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		final ImmutableList<ExternalSystemParentConfig> configs = externalSystemConfigDAO.getActiveByType(ExternalSystemType.Alberta);

		if (configs.size() > 0)
		{
			return ProcessPreconditionsResolution.accept();
		}
		return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_ERR_PROCESS_NOT_AVAILABLE, ExternalSystemType.Alberta.getName()));
	}

	@Override
	protected String doIt() throws Exception
	{
		addLog("Calling with params: externalSystemConfigAlbertaId {}, ignoreSelection {}, orgId {}", externalSystemConfigAlbertaId, ignoreSelection, orgId);

		final Set<BPartnerId> bPartnerIdSet = (ignoreSelection ? getAllBPartnerRecords() : getSelectedBPartnerRecords())
				.stream()
				.map(I_C_BPartner::getC_BPartner_ID)
				.map(BPartnerId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final OrgId computedOrgId = orgId > 0 ? OrgId.ofRepoId(orgId) : getProcessInfo().getOrgId();
		final ExternalSystemAlbertaConfigId albertaConfigId = ExternalSystemAlbertaConfigId.ofRepoId(externalSystemConfigAlbertaId);

		final Stream<JsonExternalSystemRequest> requestList = invokeAlbertaService
				.streamSyncExternalRequestsForBPartnerIds(bPartnerIdSet, albertaConfigId, getPinstanceId(), computedOrgId);

		final ExternalSystemMessageSender externalSystemMessageSender = SpringContextHolder.instance.getBean(ExternalSystemMessageSender.class);

		requestList.forEach(externalSystemMessageSender::send);

		return JavaProcess.MSG_OK;
	}

	@NonNull
	private List<I_C_BPartner> getAllBPartnerRecords()
	{
		final IQueryBuilder<I_C_BPartner> bPartnerQuery = queryBL.createQueryBuilder(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter();

		if (orgId > 0)
		{
			bPartnerQuery.addEqualsFilter(I_C_BPartner.COLUMNNAME_AD_Org_ID, orgId);
		}

		return bPartnerQuery.create()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private List<I_C_BPartner> getSelectedBPartnerRecords()
	{
		final IQueryBuilder<I_C_BPartner> bPartnerQuery = retrieveActiveSelectedRecordsQueryBuilder(I_C_BPartner.class);

		if (orgId > 0)
		{
			bPartnerQuery.addEqualsFilter(I_C_BPartner.COLUMNNAME_AD_Org_ID, orgId);
		}

		return bPartnerQuery.create()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}
}
