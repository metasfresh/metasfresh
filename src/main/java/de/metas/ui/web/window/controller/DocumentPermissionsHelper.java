package de.metas.ui.web.window.controller;

import javax.annotation.Nullable;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.permissions.ElementPermission;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.IRolePermLoggingBL;
import org.adempiere.service.IRolePermLoggingBL.NoSuchForeignKeyException;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.exceptions.DocumentPermissionException;
import de.metas.ui.web.window.exceptions.DocumentPermissionException.DocumentPermission;
import de.metas.ui.web.window.model.Document;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class DocumentPermissionsHelper
{

	private static final Logger logger = LogManager.getLogger(DocumentPermissionsHelper.class);

	public static ElementPermission checkWindowAccess(@NonNull final DocumentEntityDescriptor entityDescriptor, final IUserRolePermissions permissions)
	{
		final int adWindowId = entityDescriptor.getWindowId().toInt();
		final ElementPermission windowPermission = permissions.checkWindowPermission(adWindowId);
		final boolean readAccess = windowPermission.hasReadAccess();
		final boolean writeAccess = windowPermission.hasWriteAccess();

		// no access
		if (!readAccess && !writeAccess)
		{
			final AdempiereException ex = DocumentPermissionException.of(DocumentPermission.WindowAccess, "@NoAccess@")
					.setParameter("Role", permissions.getName())
					.setParameter("WindowName", entityDescriptor.getCaption())
					.setParameter("AD_Window_ID", adWindowId);

			logAccessIfWindowExistsAndThrowEx(permissions, adWindowId, ex);
		}

		return windowPermission;
	}

	/**
	 * Asserts view access
	 * 
	 * @param windowId
	 * @param viewId optional viewId, used only for error reporting
	 * @param permissions
	 */
	public static void assertViewAccess(final WindowId windowId, @Nullable final String viewId, final IUserRolePermissions permissions)
	{
		final int adWindowId = windowId.toIntOr(-1);
		if (adWindowId < 0)
		{
			// cannot apply window access if the WindowId is not integer.
			// usually those are special window placeholders.
			return; // accept it
		}

		//
		// Check AD_Window_ID access
		final ElementPermission windowPermission = permissions.checkWindowPermission(adWindowId);
		if (!windowPermission.hasReadAccess())
		{
			final AdempiereException ex = DocumentPermissionException.of(DocumentPermission.WindowAccess, "@NoAccess@")
					.setParameter("roleName", permissions.getName())
					.setParameter("view", viewId)
					.setParameter("windowId", adWindowId);

			logAccessIfWindowExistsAndThrowEx(permissions, adWindowId, ex);
		}
	}

	private static void logAccessIfWindowExistsAndThrowEx(
			@NonNull final IUserRolePermissions permissions,
			final int adWindowId,
			@NonNull final AdempiereException ex)
	{
		final IRolePermLoggingBL rolePermLoggingBL = Services.get(IRolePermLoggingBL.class);
		final Boolean readWriteAccess = null; // none

		try
		{
			rolePermLoggingBL.logWindowAccess(permissions.getAD_Role_ID(), adWindowId, readWriteAccess, ex.getLocalizedMessage());
		}
		catch (final NoSuchForeignKeyException noSuchForeignKeyException)
		{
			logger.warn("Caught NoSuchForeignKeyException for AD_Window_ID=" + adWindowId, noSuchForeignKeyException); // log it, but throw the "important" one, i.e. ex
		}
		throw ex;
	}
	
	public static void assertCanView(@NonNull final Document document, @NonNull final IUserRolePermissions permissions)
	{
		// In case document type is not Window, return OK because we cannot validate
		if (document.getDocumentPath().getDocumentType() != DocumentType.Window)
		{
			return; // OK
		}

		// Check if we have window read permission
		final WindowId windowId = document.getDocumentPath().getWindowId();
		final int windowIdInt = windowId.toIntOr(-1);
		if (windowIdInt > 0 && !permissions.checkWindowPermission(windowIdInt).hasReadAccess())
		{
			throw DocumentPermissionException.of(DocumentPermission.View, "no window read permission");
		}

		final String tableName = document.getEntityDescriptor().getTableNameOrNull();
		if (tableName == null)
		{
			// cannot apply security because this is not table based
			return;
		}

		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		final int recordId = document.getDocumentId().toIntOr(-1);
		final String errmsg = permissions.checkCanView(document.getAD_Client_ID(), document.getAD_Org_ID(), adTableId, recordId);
		if (errmsg != null)
		{
			throw DocumentPermissionException.of(DocumentPermission.View, errmsg);
		}
	}

	public static void assertCanEdit(final Document document)
	{
		// If running from a background thread, consider it editable
		if(!UserSession.isWebuiThread())
		{
			return;
		}
		
		assertCanEdit(document, UserSession.getCurrentPermissions());
	}

	public static void assertCanEdit(final Document document, final IUserRolePermissions permissions)
	{
		final String errmsg = checkCanEdit(document, permissions);
		if (errmsg != null)
		{
			throw DocumentPermissionException.of(DocumentPermission.Update, errmsg);
		}
	}

	public static boolean canEdit(final Document document, final IUserRolePermissions permissions)
	{
		final String errmsg = checkCanEdit(document, permissions);
		return errmsg == null;
	}

	private static String checkCanEdit(@NonNull final Document document, @NonNull final IUserRolePermissions permissions)
	{
		// In case document type is not Window, return OK because we cannot validate
		final DocumentPath documentPath = document.getDocumentPath();
		if (documentPath.getDocumentType() != DocumentType.Window)
		{
			return null; // OK
		}

		// Check if we have window write permission
		final WindowId windowId = documentPath.getWindowId();
		final int windowIdInt = windowId.toIntOr(-1);
		if (windowIdInt > 0 && !permissions.checkWindowPermission(windowIdInt).hasWriteAccess())
		{
			return "no window edit permission";
		}

		final String tableName = document.getEntityDescriptor().getTableNameOrNull();
		if (tableName == null)
		{
			// cannot apply security because this is not table based
			return null; // OK
		}
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);

		int adClientId = document.getAD_Client_ID();
		int adOrgId = document.getAD_Org_ID();
		final int recordId = document.getDocumentId().toIntOr(-1);
		return permissions.checkCanUpdate(adClientId, adOrgId, adTableId, recordId);
	}

}
