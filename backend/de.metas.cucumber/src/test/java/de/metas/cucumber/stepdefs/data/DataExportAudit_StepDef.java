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

package de.metas.cucumber.stepdefs.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.bpartner.v2.response.JsonResponseBPartner;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_Data_Export_Audit;
import org.compiere.model.I_Data_Export_Audit_Log;
import org.compiere.util.DB;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class DataExportAudit_StepDef
{
	private final StepDefData<I_C_BPartner> bpartnerTable;
	private final StepDefData<I_C_BPartner_Location> bpartnerLocationTable;
	private final StepDefData<I_C_Location> locationTable;
	private final StepDefData<I_Data_Export_Audit> dataExportAuditTable;
	private final StepDefData<I_ExternalSystem_Config> externalSystemConfigTable;
	private final StepDefData<I_AD_PInstance> pinstanceTable;

	private final TestContext testContext;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public DataExportAudit_StepDef(
			@NonNull final StepDefData<I_C_BPartner> bpartnerTable,
			@NonNull final StepDefData<I_C_BPartner_Location> bpartnerLocationTable,
			@NonNull final StepDefData<I_C_Location> locationTable,
			@NonNull final StepDefData<I_Data_Export_Audit> dataExportAuditTable,
			@NonNull final StepDefData<I_ExternalSystem_Config> externalSystemConfigTable,
			@NonNull final StepDefData<I_AD_PInstance> pinstanceTable,
			@NonNull final TestContext testContext)
	{
		this.bpartnerTable = bpartnerTable;
		this.bpartnerLocationTable = bpartnerLocationTable;
		this.locationTable = locationTable;
		this.dataExportAuditTable = dataExportAuditTable;
		this.externalSystemConfigTable = externalSystemConfigTable;
		this.pinstanceTable = pinstanceTable;
		this.testContext = testContext;
	}

	@And("process bpartner endpoint response")
	public void retrieve_bpartner_endpoint_response(@NonNull final DataTable table) throws JsonProcessingException
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final JsonResponseComposite compositeResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonResponseComposite.class);
		assertThat(compositeResponse).isNotNull();

		final Map<String, String> row = table.asMaps().get(0);
		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner.COLUMNNAME_C_BPartner_ID + ".Identifier");
		final String bpartnerLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID + ".Identifier");
		final String locationIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Location.COLUMNNAME_C_Location_ID + ".Identifier");

		processBPartnerResponse(compositeResponse.getBpartner(), bpartnerIdentifier);

		if (bpartnerLocationIdentifier == null)
		{
			assertThat(compositeResponse.getLocations()).isEqualTo(null);
		}
		else
		{
			assertThat(compositeResponse.getLocations()).hasSize(1);
			processLocations(compositeResponse.getLocations().get(0), bpartnerLocationIdentifier, locationIdentifier);
		}
	}

	private void processBPartnerResponse(
			@NonNull final JsonResponseBPartner response,
			@NonNull final String bpartnerIdentifier)
	{
		final JsonMetasfreshId bpartnerId = response.getMetasfreshId();

		final I_C_BPartner bpartner = InterfaceWrapperHelper.load(bpartnerId.getValue(), I_C_BPartner.class);

		bpartnerTable.put(bpartnerIdentifier, bpartner);
	}

	private void processLocations(
			@NonNull final JsonResponseLocation jsonBPartnerLocation,
			@NonNull final String bpartnerLocationIdentifier,
			@NonNull final String locationIdentifier)
	{

		final I_C_BPartner_Location bPartnerLocation = InterfaceWrapperHelper.load(jsonBPartnerLocation.getMetasfreshId().getValue(), I_C_BPartner_Location.class);

		assertThat(bPartnerLocation).isNotNull();

		bpartnerLocationTable.put(bpartnerLocationIdentifier, bPartnerLocation);

		final I_C_Location location = InterfaceWrapperHelper.load(bPartnerLocation.getC_Location_ID(), I_C_Location.class);

		assertThat(location).isNotNull();

		locationTable.put(locationIdentifier, location);
	}

	@And("^after not more than (.*)s, there are added records in Data_Export_Audit$")
	public void data_export_audit_validation(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadDataExportAudit(tableRow));

			final String dataExportAuditIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_Data_Export_Audit.COLUMNNAME_Data_Export_Audit_ID + ".Identifier");
			final String parentIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_Data_Export_Audit.COLUMNNAME_Data_Export_Audit_Parent_ID + ".Identifier");

			final I_Data_Export_Audit dataExportAudit = dataExportAuditTable.get(dataExportAuditIdentifier);

			assertThat(dataExportAudit).isNotNull();

			if (parentIdentifier != null)
			{
				final I_Data_Export_Audit parentDataExportAudit = dataExportAuditTable.get(parentIdentifier);

				assertThat(parentDataExportAudit).isNotNull();
				assertThat(dataExportAudit.getData_Export_Audit_Parent_ID()).isEqualTo(parentDataExportAudit.getData_Export_Audit_ID());
			}
			else
			{
				assertThat(dataExportAudit.getData_Export_Audit_Parent_ID()).isEqualTo(0);
			}
		}
	}

	private Boolean loadDataExportAudit(@NonNull final Map<String, String> tableRow)
	{
		final String tableName = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Table.COLUMNNAME_TableName);
		final String recordIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_Data_Export_Audit.COLUMNNAME_Record_ID + ".Identifier");

		final TableRecordReference tableRecordReference;

		switch (tableName)
		{
			case I_C_BPartner.Table_Name:
				final I_C_BPartner bPartner = bpartnerTable.get(recordIdentifier);
				tableRecordReference = TableRecordReference.of(bPartner);
				break;
			case I_C_BPartner_Location.Table_Name:
				final I_C_BPartner_Location bpartnerLocation = bpartnerLocationTable.get(recordIdentifier);
				tableRecordReference = TableRecordReference.of(bpartnerLocation);
				break;
			case I_C_Location.Table_Name:
				final I_C_Location location = locationTable.get(recordIdentifier);
				tableRecordReference = TableRecordReference.of(location);
				break;
			default:
				throw new AdempiereException("Table not supported! TableName:" + tableName);
		}

		final Optional<I_Data_Export_Audit> dataExportAuditOptional = queryBL.createQueryBuilder(I_Data_Export_Audit.class)
				.addEqualsFilter(I_Data_Export_Audit.COLUMNNAME_Record_ID, tableRecordReference.getRecord_ID())
				.addEqualsFilter(I_Data_Export_Audit.COLUMNNAME_AD_Table_ID, tableRecordReference.getAD_Table_ID())
				.create()
				.firstOnlyOptional(I_Data_Export_Audit.class);

		final String dataExportAuditIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_Data_Export_Audit.COLUMNNAME_Data_Export_Audit_ID + ".Identifier");

		if (!dataExportAuditOptional.isPresent())
		{
			return false;
		}

		dataExportAuditTable.put(dataExportAuditIdentifier, dataExportAuditOptional.get());
		return true;
	}

	@And("there are added records in Data_Export_Audit_Log")
	public void data_export_audit_log_validation(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			validateDataExportAuditLog(tableRow);
		}
	}

	private void validateDataExportAuditLog(@NonNull final Map<String, String> tableRow)
	{
		final String dataExportAuditIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_Data_Export_Audit.COLUMNNAME_Data_Export_Audit_ID + ".Identifier");
		final String action = DataTableUtil.extractStringForColumnName(tableRow, I_Data_Export_Audit_Log.COLUMNNAME_Data_Export_Action);
		final String configIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_Data_Export_Audit_Log.COLUMNNAME_ExternalSystem_Config_ID + ".Identifier");
		final String pinstanceIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_Data_Export_Audit_Log.COLUMNNAME_AD_PInstance_ID + ".Identifier");

		final I_Data_Export_Audit dataExportAudit = dataExportAuditTable.get(dataExportAuditIdentifier);

		final I_Data_Export_Audit_Log dataExportAuditLog = queryBL
				.createQueryBuilder(I_Data_Export_Audit_Log.class)
				.addEqualsFilter(I_Data_Export_Audit_Log.COLUMN_Data_Export_Audit_ID, dataExportAudit.getData_Export_Audit_ID())
				.create()
				.firstOnlyNotNull(I_Data_Export_Audit_Log.class);

		assertThat(dataExportAuditLog).isNotNull();
		assertThat(dataExportAuditLog.getData_Export_Action()).isEqualTo(action);

		if (configIdentifier != null)
		{
			final I_ExternalSystem_Config externalSystemConfig = externalSystemConfigTable.get(configIdentifier);

			assertThat(dataExportAuditLog.getExternalSystem_Config_ID()).isEqualTo(externalSystemConfig.getExternalSystem_Config_ID());
		}
		else
		{
			assertThat(dataExportAuditLog.getExternalSystem_Config_ID()).isEqualTo(0);
		}

		if (pinstanceIdentifier != null)
		{
			final I_AD_PInstance adPInstance = pinstanceTable.get(pinstanceIdentifier);

			assertThat(dataExportAuditLog.getAD_PInstance_ID()).isEqualTo(adPInstance.getAD_PInstance_ID());
		}
		else
		{
			assertThat(dataExportAuditLog.getAD_PInstance_ID()).isEqualTo(0);
		}
	}

	@And("all the export audit data is reset")
	public void reset_data_export_audit()
	{
		DB.executeUpdateEx("TRUNCATE TABLE Data_Export_Audit cascade", ITrx.TRXNAME_None);
		DB.executeUpdateEx("TRUNCATE TABLE Data_Export_Audit_Log cascade", ITrx.TRXNAME_None);
	}

	@And("add external system config and pinstance headers")
	public void add_sysConfig_and_pinstance_headers(@NonNull final DataTable dataTable)
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String configIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_Data_Export_Audit_Log.COLUMNNAME_ExternalSystem_Config_ID + ".Identifier");
		final String pinstanceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_Data_Export_Audit_Log.COLUMNNAME_AD_PInstance_ID + ".Identifier");

		final I_AD_PInstance adPInstance = InterfaceWrapperHelper.newInstance(I_AD_PInstance.class);
		adPInstance.setAD_Org_ID(1);
		adPInstance.setAD_Process_ID(1);
		adPInstance.setIsActive(true);
		adPInstance.setIsProcessing(true);
		adPInstance.setRecord_ID(1);
		InterfaceWrapperHelper.saveRecord(adPInstance);
		pinstanceTable.put(pinstanceIdentifier, adPInstance);

		final I_ExternalSystem_Config externalSystemConfig = externalSystemConfigTable.get(configIdentifier);
		final Map<String, String> additionalHeaders = new HashMap<>();
		additionalHeaders.put(ExternalSystemConstants.HEADER_EXTERNALSYSTEM_CONFIG_ID, Integer.toString(externalSystemConfig.getExternalSystem_Config_ID()));
		additionalHeaders.put(ExternalSystemConstants.HEADER_PINSTANCE_ID, Integer.toString(adPInstance.getAD_PInstance_ID()));

		testContext.setHttpHeaders(additionalHeaders);
	}

	@And("the following c_bpartner_location is changed")
	public void change_bpartner_location(@NonNull final DataTable dataTable)
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String bpartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID + ".Identifier");

		final I_C_BPartner_Location bPartnerLocation = bpartnerLocationTable.get(bpartnerLocationIdentifier);

		bPartnerLocation.setGLN("not-relevant");

		InterfaceWrapperHelper.save(bPartnerLocation);
	}
}
