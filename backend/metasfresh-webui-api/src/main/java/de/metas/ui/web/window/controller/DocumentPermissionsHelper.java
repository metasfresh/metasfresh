package de.metas.ui.web.window.controller;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.IRolePermLoggingBL;
import org.adempiere.service.IRolePermLoggingBL.NoSuchForeignKeyException;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.ElementPermission;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.exceptions.DocumentPermissionException;
import de.metas.ui.web.window.exceptions.DocumentPermissionException.DocumentPermission;
import de.metas.ui.web.window.model.Document;
import de.metas.util.Services;
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
		final AdWindowId adWindowId = entityDescriptor.getWindowId().toAdWindowId();
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
		final AdWindowId adWindowId = windowId.toAdWindowIdOrNull();
		if (adWindowId == null)
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
			@NonNull final AdWindowId adWindowId,
			@NonNull final AdempiereException ex)
	{
		final IRolePermLoggingBL rolePermLoggingBL = Services.get(IRolePermLoggingBL.class);
		final Boolean readWriteAccess = null; // none

		try
		{
			rolePermLoggingBL.logWindowAccess(permissions.getRoleId(), adWindowId, readWriteAccess, ex.getLocalizedMessage());
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
		final AdWindowId adWindowId = document.getDocumentPath().getWindowId().toAdWindowIdOrNull();
		if (adWindowId != null && !permissions.checkWindowPermission(adWindowId).hasReadAccess())
		{
			throw DocumentPermissionException.of(DocumentPermission.View, "no window read permission");
		}

		final int adTableId = getAdTableId(document);
		if (adTableId <= 0)
		{
			// cannot apply security because this is not table based
			return;
		}

		final int recordId = getRecordId(document);
		final OrgId orgId = document.getOrgId();
		if (orgId == null)
		{
			return; // the user cleared the field; field is flagged as mandatory; until the user set the field, don't make a fuss.
		}
		final String errmsg = permissions.checkCanView(document.getClientId(), orgId, adTableId, recordId);
		if (errmsg != null)
		{
			throw DocumentPermissionException.of(DocumentPermission.View, errmsg);
		}
	}

	public static void assertCanEdit(final Document document)
	{
		// If running from a background thread, consider it editable
		if (!UserSession.isWebuiThread())
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
		final AdWindowId adWindowId = documentPath.getWindowId().toAdWindowIdOrNull();
		if (adWindowId != null && !permissions.checkWindowPermission(adWindowId).hasWriteAccess())
		{
			return "no window edit permission";
		}

		final int adTableId = getAdTableId(document);
		if (adTableId <= 0)
		{
			return null; // not table based => OK
		}
		final int recordId = getRecordId(document);

		final ClientId adClientId = document.getClientId();
		final OrgId adOrgId = document.getOrgId();
		if (adOrgId == null)
		{
			return null; // the user cleared the field; field is flagged as mandatory; until user set the field, don't make a fuss.
		}
		return permissions.checkCanUpdate(adClientId, adOrgId, adTableId, recordId);
	}

	private static int getAdTableId(final Document document)
	{
		final String tableName = document.getEntityDescriptor().getTableNameOrNull();
		if (tableName == null)
		{
			// cannot apply security because this is not table based
			return -1; // OK
		}

		return Services.get(IADTableDAO.class).retrieveTableId(tableName);
	}

	private static int getRecordId(final Document document)
	{
		if (document.isNew())
		{
			return -1;
		}
		else
		{
			return document.getDocumentId().toIntOr(-1);
		}
	}

}
