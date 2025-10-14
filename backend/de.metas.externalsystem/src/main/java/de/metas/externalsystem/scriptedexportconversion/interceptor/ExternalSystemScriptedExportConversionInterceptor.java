/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem.scriptedexportconversion.interceptor;

import de.metas.document.engine.IDocumentBL;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion;
import de.metas.externalsystem.process.InvokeScriptedExportConversionAction;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionService;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfo;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableAndClientId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.compiere.model.I_AD_Client;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import static de.metas.common.externalsystem.ExternalSystemConstants.COMMAND_CONVERT_MESSAGE_FROM_METASFRESH;
import static de.metas.externalsystem.process.InvokeExternalSystemProcess.PARAM_CHILD_CONFIG_ID;
import static de.metas.externalsystem.process.InvokeExternalSystemProcess.PARAM_EXTERNAL_REQUEST;
import static de.metas.externalsystem.process.InvokeScriptedExportConversionAction.PARAM_Record_ID;
import static org.compiere.util.Env.getCtx;

/**
 * Interceptor which listens to a table specified in {@link I_ExternalSystem_Config_ScriptedExportConversion}.
 */
/* package */ class ExternalSystemScriptedExportConversionInterceptor implements ModelValidator
{
	private final Logger log = LogManager.getLogger(getClass());

	private final transient IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final transient IClientDAO clientDAO = Services.get(IClientDAO.class);
	private final transient IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	private final ModelValidationEngine modelValidationEngine;
	private final ExternalSystemScriptedExportConversionService externalSystemScriptedExportConversionService;
	private int m_AD_Client_ID;
	private final AdTableId adTableId;
	private final String tableName;
	private final boolean isDocument;

	public ExternalSystemScriptedExportConversionInterceptor(
			@NonNull final ModelValidationEngine modelValidationEngine,
			@NonNull final ExternalSystemScriptedExportConversionService externalSystemScriptedExportConversionService,
			@NonNull final AdTableAndClientId tableAndClientId)
	{
		this.modelValidationEngine = modelValidationEngine;
		this.externalSystemScriptedExportConversionService = externalSystemScriptedExportConversionService;
		this.m_AD_Client_ID = tableAndClientId.getClientId().getRepoId();
		this.adTableId = tableAndClientId.getTableId();
		this.tableName = tableDAO.retrieveTableName(adTableId);
		this.isDocument = documentBL.isDocumentTable(tableName);
	}

	// NOTE: keep in sync with destroy method
	@Override
	public void initialize(@NonNull final ModelValidationEngine engine, @Nullable final MClient client)
	{
		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
		}

		final String tableName = getTableName();
		if (isDocument())
		{
			engine.addDocValidate(tableName, this);
		}
		else
		{
			engine.addModelChange(tableName, this);
		}
	}

	public void init()
	{
		if (m_AD_Client_ID > 0)
		{
			final I_AD_Client client = clientDAO.getById(m_AD_Client_ID);
			modelValidationEngine.addModelValidator(this, client);
		}
		else
		{
			// register for all clients
			modelValidationEngine.addModelValidator(this);
		}
	}

	// NOTE: keep in sync with initialize method
	public void destroy()
	{
		// Unregister from model validation engine
		final String tableName = getTableName();
		modelValidationEngine.removeModelChange(tableName, this);
		modelValidationEngine.removeDocValidate(tableName, this);
	}

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null; // nothing
	}

	@Nullable
	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		return null; // nothing
	}

	@Nullable
	@Override
	public String docValidate(final PO po, final int timing) throws Exception
	{
		Check.assume(isDocument(), "PO '{}' is a document", po);

		if (timing == ModelValidator.TIMING_AFTER_COMPLETE
				&& !documentBL.isReversalDocument(po))
		{
			externalSystemScriptedExportConversionService
					.retrieveBestMatchingConfig(AdTableAndClientId.of(AdTableId.ofRepoId(po.get_Table_ID()),
							ClientId.ofRepoId(getAD_Client_ID())), po.get_ID())
					.ifPresent(config -> {
						try
						{
							ProcessInfo.builder()
									.setCtx(getCtx())
									.setRecord(tableDAO.retrieveTableId(I_ExternalSystem_Config_ScriptedExportConversion.Table_Name), config.getId().getRepoId())
									.setAD_ProcessByClassname(InvokeScriptedExportConversionAction.class.getName())
									.addParameter(PARAM_EXTERNAL_REQUEST, COMMAND_CONVERT_MESSAGE_FROM_METASFRESH)
									.addParameter(PARAM_CHILD_CONFIG_ID, config.getId().getRepoId())
									.addParameter(PARAM_Record_ID, po.get_ID())
									.buildAndPrepareExecution()
									.executeSync();
						}
						catch (final Exception e)
						{
							log.warn(InvokeScriptedExportConversionAction.class.getName() + " process failed for Config ID {}, Record ID: {}",
									config.getId(), po.get_ID());
						}
					});
		}

		return null;
	}

	@NonNull
	public AdTableId getTableId()
	{
		return adTableId;
	}

	private String getTableName()
	{
		return tableName;
	}

	private boolean isDocument()
	{
		return isDocument;
	}
}
