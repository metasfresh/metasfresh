package de.metas.ui.web.window.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.service.IADProcessDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Form;
import org.compiere.model.I_AD_Process;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.i18n.IModelTranslationMap;
import de.metas.logging.LogManager;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.model.DocumentAction.DocumentActionType;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class DocumentActionsService
{
	private static final Logger logger = LogManager.getLogger(DocumentActionsService.class);

	@Autowired
	private UserSession userSession;

	public DocumentActionsList getDocumentActions(final String tableName)
	{
		final IUserRolePermissions role = userSession.getUserRolePermissions();

		final Properties ctx = userSession.getCtx();
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		final List<I_AD_Process> adProcesses = Services.get(IADProcessDAO.class).retrieveProcessesForTable(ctx, adTableId);

		final List<DocumentAction> documentActions = new ArrayList<>(adProcesses.size());
		for (final I_AD_Process adProcess : adProcesses)
		{
			// Filter out processes on which we don't have access
			final Boolean accessRW = role.checkProcessAccess(adProcess.getAD_Process_ID());
			if (accessRW == null)
			{
				logger.debug("Removing process {} because user has no access at all to it", adProcess);
				continue;
			}
			else if (!accessRW)
			{
				logger.debug("Removing process {} because user has only readonly access to it", adProcess);
				continue;
			}

			final DocumentAction documentAction = createDocumentAction(adProcess);
			documentActions.add(documentAction);
		}

		return DocumentActionsList.of(documentActions);
	}

	private DocumentAction createDocumentAction(final I_AD_Process adProcess)
	{
		final IModelTranslationMap adProcessTrlsMap = InterfaceWrapperHelper.getModelTranslationMap(adProcess);
		return DocumentAction.builder()
				.setActionId(adProcess.getAD_Process_ID())
				.setCaption(adProcessTrlsMap.getColumnTrl(I_AD_Process.COLUMNNAME_Name, adProcess.getName()))
				.setDescription(adProcessTrlsMap.getColumnTrl(I_AD_Process.COLUMNNAME_Description, adProcess.getDescription()))
				.setType(extractType(adProcess))
				.setPreconditionsClass(extractPreconditionsClassOrNull(adProcess))
				.build();
	}

	private static final DocumentActionType extractType(final I_AD_Process adProcess)
	{
		final DocumentActionType type;
		if (adProcess.getAD_Form_ID() > 0)
		{
			type = DocumentActionType.Form;
		}
		else if (adProcess.getAD_Workflow_ID() > 0)
		{
			type = DocumentActionType.Workflow;
		}
		else if (adProcess.isReport())
		{
			type = DocumentActionType.Report;
		}
		else
		{
			type = DocumentActionType.Process;
		}
		return type;
	}

	private static Class<? extends ISvrProcessPrecondition> extractPreconditionsClassOrNull(final I_AD_Process adProcess)
	{
		final String classname = extractClassnameOrNull(adProcess);
		if (classname == null)
		{
			return null;
		}

		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		final Class<?> processClass;
		try
		{
			processClass = classLoader.loadClass(classname);
		}
		catch (ClassNotFoundException e)
		{
			logger.error("Cannot load class: " + classname, e);
			return null;
		}

		if (!ISvrProcessPrecondition.class.isAssignableFrom(processClass))
		{
			return null;
		}

		try
		{
			return processClass.asSubclass(ISvrProcessPrecondition.class);
		}
		catch (Exception e)
		{
			logger.error(e.getLocalizedMessage(), e);
			return null;
		}
	}

	private static final String extractClassnameOrNull(final I_AD_Process adProcess)
	{
		//
		// First try: Check process classname
		if (!Check.isEmpty(adProcess.getClassname(), true))
		{
			return adProcess.getClassname();
		}

		//
		// Second try: form classname (05089)
		final I_AD_Form form = adProcess.getAD_Form();
		if (form != null && !Check.isEmpty(form.getClassname(), true))
		{
			return form.getClassname();
		}

		return null;
	}

}
