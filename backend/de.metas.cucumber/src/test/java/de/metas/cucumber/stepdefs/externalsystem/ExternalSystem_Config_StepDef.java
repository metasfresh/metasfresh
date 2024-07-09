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

import de.metas.cucumber.stepdefs.AD_UserGroup_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Alberta;
import de.metas.externalsystem.model.I_ExternalSystem_Config_GRSSignum;
import de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl;
import de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6;
import de.metas.externalsystem.model.I_ExternalSystem_Other_ConfigParameter;
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
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_PInstance_Para;
import org.compiere.model.I_AD_UserGroup;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.externalsystem.ExternalSystemType.LeichUndMehl;
import static de.metas.externalsystem.model.I_ExternalSystem_Config.COLUMNNAME_ExternalSystem_Config_ID;
import static de.metas.externalsystem.model.I_ExternalSystem_Config_GRSSignum.COLUMNNAME_IsSyncHUsOnMaterialReceipt;
import static de.metas.externalsystem.model.I_ExternalSystem_Config_GRSSignum.COLUMNNAME_IsSyncHUsOnProductionReceipt;
import static de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP.COLUMNNAME_IsAutoSendWhenCreatedByUserGroup;
import static de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP.COLUMNNAME_IsSyncBPartnersToRabbitMQ;
import static de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP.COLUMNNAME_IsSyncExternalReferencesToRabbitMQ;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ExternalSystem_Config_StepDef
{
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IADPInstanceDAO instanceDAO = Services.get(IADPInstanceDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ExternalSystemConfigRepo externalSystemConfigRepo = SpringContextHolder.instance.getBean(ExternalSystemConfigRepo.class);

	private final ExternalSystem_Config_StepDefData configTable;
	private final AD_UserGroup_StepDefData userGroupTable;
	private final ExternalSystem_Config_LeichMehl_StepDefData leichMehlConfigTable;

	private final TestContext testContext;

	public ExternalSystem_Config_StepDef(
			@NonNull final ExternalSystem_Config_StepDefData configTable,
			@NonNull final ExternalSystem_Config_LeichMehl_StepDefData leichMehlConfigTable,
			@NonNull final AD_UserGroup_StepDefData userGroupTable,
			@NonNull final TestContext testContext)
	{
		this.configTable = configTable;
		this.leichMehlConfigTable = leichMehlConfigTable;
		this.userGroupTable = userGroupTable;
		this.testContext = testContext;
	}

	@And("add external system parent-child pair")
	public void new_externalSystemChild_is_created_if_missing(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			saveExternalSystemConfig(tableRow);
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

	@And("a new metasfresh AD_PInstance_Para is stored for the external system invocation")
	public void new_metasfresh_ad_pinstance_para_is_stored_for_external_system_process(@NonNull final DataTable dataTable) throws JSONException
	{
		final JSONObject result = new JSONObject(testContext.getApiResponse().getContent());
		final int pInstanceId = result.getInt("pinstanceID");

		final Map<String, I_AD_PInstance_Para> paramNameToParameter = queryBL.createQueryBuilder(I_AD_PInstance_Para.class)
				.addInArrayFilter(I_AD_PInstance_Para.COLUMNNAME_AD_PInstance_ID, pInstanceId)
				.create()
				.list()
				.stream()
				.collect(Collectors.toMap(I_AD_PInstance_Para::getParameterName, Function.identity()));

		assertThat(paramNameToParameter).isNotNull();
		assertThat(paramNameToParameter.size()).isGreaterThan(0);

		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> row : tableRows)
		{
			final String key = DataTableUtil.extractStringForColumnName(row, "param.key");
			final String value = DataTableUtil.extractStringForColumnName(row, "param.value");

			final I_AD_PInstance_Para parameter = paramNameToParameter.get(key);
			assertThat(parameter.getP_String()).isEqualTo(value);
		}
	}

	@And("deactivate ExternalSystem_Config")
	public void deactivate_ExternalSystem_Config(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String configIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_ExternalSystem_Config_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_ExternalSystem_Config parentConfig = configTable.get(configIdentifier);
			assertThat(parentConfig).isNotNull();

			parentConfig.setIsActive(false);
			saveRecord(parentConfig);

			final ExternalSystemType externalSystemType = ExternalSystemType.ofCode(parentConfig.getType());

			switch (externalSystemType)
			{
				case RabbitMQ:
					final I_ExternalSystem_Config_RabbitMQ_HTTP configRabbitMQHttp = queryBL.createQueryBuilder(I_ExternalSystem_Config_RabbitMQ_HTTP.class)
							.addEqualsFilter(I_ExternalSystem_Config_RabbitMQ_HTTP.COLUMNNAME_ExternalSystem_Config_ID, parentConfig.getExternalSystem_Config_ID())
							.create()
							.firstOnlyNotNull(I_ExternalSystem_Config_RabbitMQ_HTTP.class);

					configRabbitMQHttp.setIsActive(false);
					saveRecord(configRabbitMQHttp);
					break;
				case GRSSignum:
					final I_ExternalSystem_Config_GRSSignum configGrsSignum = queryBL.createQueryBuilder(I_ExternalSystem_Config_GRSSignum.class)
							.addEqualsFilter(I_ExternalSystem_Config_GRSSignum.COLUMNNAME_ExternalSystem_Config_ID, parentConfig.getExternalSystem_Config_ID())
							.create()
							.firstOnlyNotNull(I_ExternalSystem_Config_GRSSignum.class);

					configGrsSignum.setIsActive(false);
					saveRecord(configGrsSignum);
					break;
				case Shopware6:
					final I_ExternalSystem_Config_Shopware6 configShopware = queryBL.createQueryBuilder(I_ExternalSystem_Config_Shopware6.class)
							.addEqualsFilter(I_ExternalSystem_Config_Shopware6.COLUMNNAME_ExternalSystem_Config_ID, parentConfig.getExternalSystem_Config_ID())
							.create()
							.firstOnlyNotNull(I_ExternalSystem_Config_Shopware6.class);

					configShopware.setIsActive(false);
					saveRecord(configShopware);
					break;
				default:
					throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", externalSystemType);
			}
		}
	}

	@And("update ExternalSystem_Config")
	public void update_ExternalSystem_Config(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String configIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_ExternalSystem_Config_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_ExternalSystem_Config externalSystemConfig = configTable.get(configIdentifier);
			assertThat(externalSystemConfig).isNotNull();

			final ExternalSystemType externalSystemType = ExternalSystemType.ofCode(externalSystemConfig.getType());

			if (externalSystemType == ExternalSystemType.Shopware6)
			{
				final I_ExternalSystem_Config_Shopware6 configShopware = queryBL.createQueryBuilder(I_ExternalSystem_Config_Shopware6.class)
						.addEqualsFilter(I_ExternalSystem_Config_Shopware6.COLUMNNAME_ExternalSystem_Config_ID, externalSystemConfig.getExternalSystem_Config_ID())
						.create()
						.firstOnlyNotNull(I_ExternalSystem_Config_Shopware6.class);

				final BigDecimal percentageOfAvailableForSalesToSync = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_ExternalSystem_Config_Shopware6.COLUMNNAME_PercentageOfAvailableForSalesToSync);

				if (percentageOfAvailableForSalesToSync != null)
				{
					configShopware.setPercentageOfAvailableForSalesToSync(percentageOfAvailableForSalesToSync);
				}

				saveRecord(configShopware);
			}
			else
			{
				throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", externalSystemType);
			}
		}
	}

	@And("^add Other external system config with identifier: (.*)$")
	public void add_other_sys_config(@NonNull final String configIdentifier, @NonNull final DataTable dataTable)
	{
		final I_ExternalSystem_Config externalSystemParentConfigRecord = createExternalSystemConfig(configIdentifier, ExternalSystemType.Other);

		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String name = DataTableUtil.extractStringForColumnName(row, I_ExternalSystem_Other_ConfigParameter.COLUMNNAME_Name);
			final String value = DataTableUtil.extractStringForColumnName(row, I_ExternalSystem_Other_ConfigParameter.COLUMNNAME_Value);

			final I_ExternalSystem_Other_ConfigParameter otherConfigParam = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Other_ConfigParameter.class);
			otherConfigParam.setExternalSystem_Config_ID(externalSystemParentConfigRecord.getExternalSystem_Config_ID());
			otherConfigParam.setName(name);
			otherConfigParam.setValue(value);
			otherConfigParam.setIsActive(true);

			saveRecord(otherConfigParam);
		}
	}

	@And("^store endpointPath (.*) in context, resolving placeholder as ExternalSystem.value$")
	public void store_endpointPath_in_context(@NonNull String endpointPath)
	{
		final String regex = ".*(:[a-zA-Z]+)/?.*";

		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(endpointPath);

		while (matcher.find())
		{
			final String externalSysConfigIdentifierGroup = matcher.group(1);
			final String externalSysConfigIdentifier = externalSysConfigIdentifierGroup.replace(":", "");

			final I_ExternalSystem_Config parentConfig = configTable.get(externalSysConfigIdentifier);
			assertThat(parentConfig).isNotNull();

			final ExternalSystemParentConfigId parentConfigId = ExternalSystemParentConfigId.ofRepoId(parentConfig.getExternalSystem_Config_ID());
			final ExternalSystemType systemType = ExternalSystemType.ofCode(parentConfig.getType());

			final IExternalSystemChildConfig childConfig = externalSystemConfigRepo.getChildByParentIdAndType(parentConfigId, systemType)
					.orElse(null);

			assertThat(childConfig).isNotNull();

			endpointPath = endpointPath.replace(externalSysConfigIdentifierGroup, childConfig.getValue());

			testContext.setEndpointPath(endpointPath);
		}
	}

	private void saveExternalSystemConfig(@NonNull final Map<String, String> tableRow)
	{
		final String configIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_ExternalSystem_Config_ID + ".Identifier");
		final String typeCode = DataTableUtil.extractStringForColumnName(tableRow, I_ExternalSystem_Config.COLUMNNAME_Type);
		final String externalSystemChildValue = DataTableUtil.extractStringForColumnName(tableRow, I_ExternalSystem_Config_RabbitMQ_HTTP.COLUMNNAME_ExternalSystemValue);

		final ExternalSystemType externalSystemType = ExternalSystemType.ofCode(typeCode);

		final Optional<ExternalSystemParentConfig> externalSystemParentConfig = externalSystemConfigRepo.getByTypeAndValue(externalSystemType, externalSystemChildValue);

		if (externalSystemParentConfig.isPresent())
		{
			final I_ExternalSystem_Config externalSystemParentConfigEntity = InterfaceWrapperHelper.load(externalSystemParentConfig.get().getId().getRepoId(), I_ExternalSystem_Config.class);
			configTable.put(configIdentifier, externalSystemParentConfigEntity);

			if (externalSystemType.equals(LeichUndMehl))
			{
				final I_ExternalSystem_Config_LeichMehl leichMehlConfig = InterfaceWrapperHelper.load(externalSystemParentConfig.get().getChildConfig().getId(), I_ExternalSystem_Config_LeichMehl.class);

				final String leichMehlConfigIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ExternalSystem_Config_LeichMehl.COLUMNNAME_ExternalSystem_Config_LeichMehl_ID
						+ "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
				leichMehlConfigTable.putOrReplace(leichMehlConfigIdentifier, leichMehlConfig);
			}
			return;
		}

		final I_ExternalSystem_Config externalSystemParentConfigEntity = createExternalSystemConfig(configIdentifier, externalSystemType);

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

				final boolean isSyncAvailableForSalesToShopware6 = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_ExternalSystem_Config_Shopware6.COLUMNNAME_IsSyncAvailableForSalesToShopware6, false);
				externalSystemConfigShopware6.setIsSyncAvailableForSalesToShopware6(isSyncAvailableForSalesToShopware6);

				final BigDecimal percentageOfAvailableStockToSync = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_ExternalSystem_Config_Shopware6.COLUMNNAME_PercentageOfAvailableForSalesToSync);

				if (percentageOfAvailableStockToSync != null)
				{
					externalSystemConfigShopware6.setPercentageOfAvailableForSalesToSync(percentageOfAvailableStockToSync);
				}

				InterfaceWrapperHelper.save(externalSystemConfigShopware6);
				break;
			case RabbitMQ:
				final I_ExternalSystem_Config_RabbitMQ_HTTP externalSystemConfigRabbitMQ = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Config_RabbitMQ_HTTP.class);
				externalSystemConfigRabbitMQ.setExternalSystem_Config_ID(externalSystemParentConfigEntity.getExternalSystem_Config_ID());
				externalSystemConfigRabbitMQ.setExternalSystemValue(externalSystemChildValue);
				externalSystemConfigRabbitMQ.setAuthToken("notImportant");
				externalSystemConfigRabbitMQ.setRemoteURL("notImportant");
				externalSystemConfigRabbitMQ.setRouting_Key("notImportant");
				externalSystemConfigRabbitMQ.setIsActive(true);

				final boolean isSyncBPartnersToRabbitMQ = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + COLUMNNAME_IsSyncBPartnersToRabbitMQ, false);
				externalSystemConfigRabbitMQ.setIsSyncBPartnersToRabbitMQ(isSyncBPartnersToRabbitMQ);

				final boolean isAutoSendWhenCreatedByUserGroup = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + COLUMNNAME_IsAutoSendWhenCreatedByUserGroup, false);
				externalSystemConfigRabbitMQ.setIsAutoSendWhenCreatedByUserGroup(isAutoSendWhenCreatedByUserGroup);
				final String userGroupIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ExternalSystem_Config_RabbitMQ_HTTP.COLUMNNAME_SubjectCreatedByUserGroup_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(userGroupIdentifier))
				{
					final I_AD_UserGroup userGroup = userGroupTable.get(userGroupIdentifier);
					assertThat(userGroup).isNotNull();
					externalSystemConfigRabbitMQ.setSubjectCreatedByUserGroup_ID(userGroup.getAD_UserGroup_ID());
				}

				final boolean isSyncExternalReferencesToRabbitMQ = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + COLUMNNAME_IsSyncExternalReferencesToRabbitMQ, false);
				externalSystemConfigRabbitMQ.setIsSyncExternalReferencesToRabbitMQ(isSyncExternalReferencesToRabbitMQ);

				InterfaceWrapperHelper.save(externalSystemConfigRabbitMQ);
				break;
			case GRSSignum:
				final boolean isSyncHUsOnMaterialReceipt = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + COLUMNNAME_IsSyncHUsOnMaterialReceipt, false);
				final boolean isSyncHUsOnProductionReceipt = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + COLUMNNAME_IsSyncHUsOnProductionReceipt, false);

				final I_ExternalSystem_Config_GRSSignum externalSystemConfigGrsSignum = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Config_GRSSignum.class);
				externalSystemConfigGrsSignum.setExternalSystem_Config_ID(externalSystemParentConfigEntity.getExternalSystem_Config_ID());
				externalSystemConfigGrsSignum.setExternalSystemValue(externalSystemChildValue);
				externalSystemConfigGrsSignum.setBaseURL("notImportant");
				externalSystemConfigGrsSignum.setAuthToken("notImportant");
				externalSystemConfigGrsSignum.setTenantId("tenantId");
				externalSystemConfigGrsSignum.setIsSyncBPartnersToRestEndpoint(true);
				externalSystemConfigGrsSignum.setIsActive(true);
				externalSystemConfigGrsSignum.setIsSyncHUsOnMaterialReceipt(isSyncHUsOnMaterialReceipt);
				externalSystemConfigGrsSignum.setIsSyncHUsOnProductionReceipt(isSyncHUsOnProductionReceipt);
				InterfaceWrapperHelper.save(externalSystemConfigGrsSignum);
				break;

			case LeichUndMehl:
				final int portNumber = DataTableUtil.extractIntForColumnName(tableRow, I_ExternalSystem_Config_LeichMehl.COLUMNNAME_TCP_PortNumber);
				final String host = DataTableUtil.extractStringForColumnName(tableRow, I_ExternalSystem_Config_LeichMehl.COLUMNNAME_TCP_Host);
				final String product_BaseFolderName = DataTableUtil.extractStringForColumnName(tableRow, I_ExternalSystem_Config_LeichMehl.COLUMNNAME_Product_BaseFolderName);

				final I_ExternalSystem_Config_LeichMehl leichMehlConfig = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Config_LeichMehl.class);
				leichMehlConfig.setTCP_PortNumber(portNumber);
				leichMehlConfig.setTCP_Host(host);
				leichMehlConfig.setProduct_BaseFolderName(product_BaseFolderName);
				leichMehlConfig.setExternalSystemValue(externalSystemChildValue);
				leichMehlConfig.setIsActive(true);
				leichMehlConfig.setExternalSystem_Config_ID(externalSystemParentConfigEntity.getExternalSystem_Config_ID());
				InterfaceWrapperHelper.saveRecord(leichMehlConfig);

				final String leichMehlConfigIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ExternalSystem_Config_LeichMehl.COLUMNNAME_ExternalSystem_Config_LeichMehl_ID
						+ "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
				leichMehlConfigTable.putOrReplace(leichMehlConfigIdentifier, leichMehlConfig);

				break;
			default:
				throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", externalSystemType);
		}
	}

	@NonNull
	private I_ExternalSystem_Config createExternalSystemConfig(@NonNull final String configIdentifier, @NonNull final ExternalSystemType externalSystemType)
	{
		final I_ExternalSystem_Config externalSystemParentConfigEntity = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Config.class);
		externalSystemParentConfigEntity.setType(externalSystemType.getCode());
		externalSystemParentConfigEntity.setName(externalSystemType.getCode() + "_ConfigName");
		externalSystemParentConfigEntity.setIsActive(true);
		InterfaceWrapperHelper.save(externalSystemParentConfigEntity);

		configTable.put(configIdentifier, externalSystemParentConfigEntity);

		return externalSystemParentConfigEntity;
	}
}
