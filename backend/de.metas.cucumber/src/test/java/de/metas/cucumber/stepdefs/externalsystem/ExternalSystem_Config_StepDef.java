/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.externalsystem;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Alberta;
import de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IADProcessDAO;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfoLog;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_PInstance;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class ExternalSystem_Config_StepDef
{
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IADPInstanceDAO instanceDAO = Services.get(IADPInstanceDAO.class);
	private final ExternalSystemConfigRepo externalSystemConfigRepo = SpringContextHolder.instance.getBean(ExternalSystemConfigRepo.class);

	private final StepDefData<I_ExternalSystem_Config> configTable;

	private final TestContext testContext;

	public ExternalSystem_Config_StepDef(
			@NonNull final StepDefData<I_ExternalSystem_Config> configTable,
			final TestContext testContext)
	{
		this.configTable = configTable;
		this.testContext = testContext;
	}

	@And("add external system parent-child pair")
	public void new_externalSystemChild_is_created_if_missing(@NonNull final DataTable dataTable)
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String configIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_ExternalSystem_Config.COLUMNNAME_ExternalSystem_Config_ID + ".Identifier");
		final String typeCode = DataTableUtil.extractStringForColumnName(tableRow, I_ExternalSystem_Config.COLUMNNAME_Type);
		final String externalSystemChildValue = DataTableUtil.extractStringForColumnName(tableRow, I_ExternalSystem_Config_RabbitMQ_HTTP.COLUMNNAME_ExternalSystemValue);

		final ExternalSystemType externalSystemType = ExternalSystemType.ofCode(typeCode);

		final Optional<ExternalSystemParentConfig> externalSystemParentConfig = externalSystemConfigRepo.getByTypeAndValue(externalSystemType, externalSystemChildValue);

		if (externalSystemParentConfig.isPresent())
		{
			if (configIdentifier != null)
			{

				final I_ExternalSystem_Config externalSystemParentConfigEntity = InterfaceWrapperHelper.load(externalSystemParentConfig.get().getId().getRepoId(), I_ExternalSystem_Config.class);
				configTable.put(configIdentifier, externalSystemParentConfigEntity);
			}
			return;
		}

		final I_ExternalSystem_Config externalSystemParentConfigEntity = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Config.class);
		externalSystemParentConfigEntity.setType(externalSystemType.getCode());
		externalSystemParentConfigEntity.setName("notImportant");
		InterfaceWrapperHelper.save(externalSystemParentConfigEntity);

		if (configIdentifier != null)
		{
			configTable.put(configIdentifier, externalSystemParentConfigEntity);
		}

		switch (externalSystemType)
		{
			case Alberta:
				final I_ExternalSystem_Config_Alberta externalSystemConfigAlberta = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Config_Alberta.class);
				externalSystemConfigAlberta.setExternalSystem_Config_ID(externalSystemParentConfigEntity.getExternalSystem_Config_ID());
				externalSystemConfigAlberta.setExternalSystemValue(externalSystemChildValue);
				externalSystemConfigAlberta.setApiKey("notImportant");
				externalSystemConfigAlberta.setBaseURL("notImportant.com");
				externalSystemConfigAlberta.setTenant("notImportant");
				externalSystemConfigAlberta.setIsActive(true);
				InterfaceWrapperHelper.save(externalSystemConfigAlberta);
				break;
			case Shopware6:
				final I_ExternalSystem_Config_Shopware6 externalSystemConfigShopware6 = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Config_Shopware6.class);
				externalSystemConfigShopware6.setExternalSystem_Config_ID(externalSystemParentConfigEntity.getExternalSystem_Config_ID());
				externalSystemConfigShopware6.setExternalSystemValue(externalSystemChildValue);
				externalSystemConfigShopware6.setClient_Secret("notImportant");
				externalSystemConfigShopware6.setClient_Id("notImportant");
				externalSystemConfigShopware6.setBaseURL("notImportant.com");
				externalSystemConfigShopware6.setIsActive(true);
				InterfaceWrapperHelper.save(externalSystemConfigShopware6);
				break;
			case RabbitMQ:
				final I_ExternalSystem_Config_RabbitMQ_HTTP externalSystemConfigRabbitMQ = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Config_RabbitMQ_HTTP.class);
				externalSystemConfigRabbitMQ.setExternalSystem_Config_ID(externalSystemParentConfigEntity.getExternalSystem_Config_ID());
				externalSystemConfigRabbitMQ.setExternalSystemValue(externalSystemChildValue);
				externalSystemConfigRabbitMQ.setAuthToken("notImportant");
				externalSystemConfigRabbitMQ.setRemoteURL("notImportant");
				externalSystemConfigRabbitMQ.setRouting_Key("notImportant");
				externalSystemConfigRabbitMQ.setIsSyncBPartnersToRabbitMQ(true);
				externalSystemConfigRabbitMQ.setIsActive(true);
				InterfaceWrapperHelper.save(externalSystemConfigRabbitMQ);
				break;
			default:
				throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", externalSystemType);
		}
	}

	@Then("a new metasfresh AD_PInstance_Log is stored for the external system {string} invocation")
	public void new_metasfresh_ad_pinstance_log_is_stored_for_external_system_process(final String externalSystemCode) throws JSONException
	{
		final ExternalSystemType externalSystemType = ExternalSystemType.ofCode(externalSystemCode);
		final AdProcessId adProcessId =
				adProcessDAO.retrieveProcessIdByClassIfUnique(externalSystemType.getExternalSystemProcessClassName());

		final JSONObject result = new JSONObject(testContext.getApiResponse().getContent());
		final int pInstanceId = result.getInt("pinstanceID");

		final I_AD_PInstance pInstance = instanceDAO.getById(PInstanceId.ofRepoId(pInstanceId));

		assertThat(pInstance).isNotNull();
		assertThat(pInstance.getAD_Process_ID()).isEqualTo(adProcessId.getRepoId());

		final List<ProcessInfoLog> processInfoLogs = instanceDAO.retrieveProcessInfoLogs(PInstanceId.ofRepoId(pInstance.getAD_PInstance_ID()));

		assertThat(processInfoLogs).isNotNull();
	}

}
