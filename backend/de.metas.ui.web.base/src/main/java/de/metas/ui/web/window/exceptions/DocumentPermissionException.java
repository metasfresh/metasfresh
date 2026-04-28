package de.metas.ui.web.window.exceptions;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import org.adempiere.exceptions.AdempiereException;

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

/**
 * Exception thrown when a required document permission was not granted.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class DocumentPermissionException extends AdempiereException
{
	private static final long serialVersionUID = -6951387618389868436L;

	public enum DocumentPermission
	{
		WindowAccess,
		/**
		 * Document view access
		 */
		View,
		/**
		 * Document update access
		 */
		Update,
	}

	private static final AdMessageKey MSG_NoAccess = AdMessageKey.of("NoAccess");

	public static DocumentPermissionException noAccess(final DocumentPermission permissionRequired)
	{
		return new DocumentPermissionException(permissionRequired, MSG_NoAccess);
	}

	@Deprecated
	public static DocumentPermissionException of(final DocumentPermission permissionRequired, final AdMessageKey adMessage)
	{
		return new DocumentPermissionException(permissionRequired, adMessage);
	}

	public static DocumentPermissionException of(final DocumentPermission permissionRequired, final ITranslatableString errorMessage)
	{
		return new DocumentPermissionException(permissionRequired, errorMessage);
	}

	private DocumentPermissionException(final DocumentPermission permissionRequired, final AdMessageKey adMessage)
	{
		super(adMessage);
		setParameter("PermissionRequired", permissionRequired);
	}

	private DocumentPermissionException(final DocumentPermission permissionRequired, final ITranslatableString string)
	{
		super(string);
		setParameter("PermissionRequired", permissionRequired);
	}
}
