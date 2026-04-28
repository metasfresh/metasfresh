package de.metas.ui.web.window.datatypes.json;

import com.google.common.collect.ImmutableSet;
import de.metas.security.IUserRolePermissions;
import de.metas.ui.web.window.controller.DocumentPermissionsHelper;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentFieldLogicExpressionResultRevaluator;
import de.metas.ui.web.window.model.DocumentStandardAction;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

public class JSONDocumentPermissions
{
	@NonNull private final IUserRolePermissions permissions;

	private final Map<DocumentPath, Boolean> readonlyDocuments = new HashMap<>();
	private DocumentFieldLogicExpressionResultRevaluator logicExpressionRevaluator; // lazy

	JSONDocumentPermissions(@NonNull final IUserRolePermissions permissions)
	{
		this.permissions = permissions;
	}

	public void apply(final Document document, final JSONDocumentField jsonField)
	{
		if (!jsonField.isReadonly())
		{
			if (isReadonly(document))
			{
				jsonField.setReadonly(true, "no document access");
				return;
			}
		}

		// TODO: check column level access
	}

	public void apply(final DocumentPath documentPath, final JSONDocumentField jsonField)
	{
		// TODO: apply JSONDocumentPermissions to fields
		// atm it's not so important because user cannot reach in that situation,
		// because he/she cannot update the document in that case.
	}

	public void apply(final Document document, final JSONIncludedTabInfo jsonIncludedTabInfo)
	{
		if (isReadonly(document))
		{
			jsonIncludedTabInfo.setAllowCreateNew(false, "no document access");
			jsonIncludedTabInfo.setAllowDelete(false, "no document access");
		}
	}

	public void apply(final DocumentPath documentPath, final JSONIncludedTabInfo jsonIncludedTabInfo)
	{
		// TODO: implement... but it's not so critical atm
	}

	private boolean isReadonly(@NonNull final Document document)
	{
		return readonlyDocuments.computeIfAbsent(document.getDocumentPath(), documentPath -> !DocumentPermissionsHelper.canEdit(document, permissions));
	}

	public DocumentFieldLogicExpressionResultRevaluator getLogicExpressionResultRevaluator()
	{
		DocumentFieldLogicExpressionResultRevaluator logicExpressionRevaluator = this.logicExpressionRevaluator;
		if (logicExpressionRevaluator == null)
		{
			logicExpressionRevaluator = this.logicExpressionRevaluator = DocumentFieldLogicExpressionResultRevaluator.using(permissions);
		}
		return logicExpressionRevaluator;
	}

	public Set<DocumentStandardAction> getStandardActions(@NonNull final Document document)
	{
		final HashSet<DocumentStandardAction> standardActions = new HashSet<>(document.getStandardActions());

		Boolean allowWindowEdit = null;
		Boolean allowDocumentEdit = null;

		for (final Iterator<DocumentStandardAction> it = standardActions.iterator(); it.hasNext(); )
		{
			final DocumentStandardAction action = it.next();

			if (action.isDocumentWriteAccessRequired())
			{
				if (allowDocumentEdit == null)
				{
					allowDocumentEdit = DocumentPermissionsHelper.canEdit(document, permissions);
				}

				if (!allowDocumentEdit)
				{
					it.remove();
					continue;
				}
			}

			if (action.isWindowWriteAccessRequired())
			{
				if (allowWindowEdit == null)
				{
					final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();
					final AdWindowId adWindowId = entityDescriptor.getDocumentType().isWindow()
							? entityDescriptor.getWindowId().toAdWindowId()
							: null;
					allowWindowEdit = adWindowId != null && permissions.checkWindowPermission(adWindowId).hasWriteAccess();
				}

				if (!allowWindowEdit)
				{
					it.remove();
					//noinspection UnnecessaryContinue
					continue;
				}
			}
		}

		return ImmutableSet.copyOf(standardActions);
	}

}
