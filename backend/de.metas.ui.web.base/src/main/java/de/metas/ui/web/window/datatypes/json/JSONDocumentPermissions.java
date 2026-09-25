package de.metas.ui.web.window.datatypes.json;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.BooleanWithReason;
import de.metas.security.IUserRolePermissions;
import de.metas.ui.web.window.controller.DocumentPermissionsHelper;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentFieldLogicExpressionResultRevaluator;
import de.metas.ui.web.window.model.DocumentStandardAction;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.LogicExpressionResult;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

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

	/**
	 * Transmits an included tab's create-permission result.
	 * <p>
	 * The role's refusal reaches us as the bare AD_Message key, because the constant carrying it is shared by
	 * all sessions and so cannot name the session's role; here the role is known, so the key becomes the
	 * translated text plus the key itself. Every other refusal is transmitted as the technical name it has
	 * always been.
	 */
	public void setAllowCreateNew(
			@NonNull final JSONIncludedTabInfo jsonIncludedTabInfo,
			@NonNull final LogicExpressionResult allowCreateNew,
			@NonNull final String adLanguage)
	{
		if (isRoleCreateRestrictionRefusal(allowCreateNew))
		{
			final String roleReasonKey = DocumentPermissionsHelper.MSG_ROLE_CREATE_NOT_ALLOWED.toAD_Message();
			final String reason = DocumentPermissionsHelper.roleCreateNotAllowedReason(permissions).translate(adLanguage);
			jsonIncludedTabInfo.setAllowCreateNew(false, reason, roleReasonKey);
		}
		else
		{
			jsonIncludedTabInfo.setAllowCreateNew(allowCreateNew.booleanValue(), allowCreateNew.getName());
		}
	}

	/**
	 * Whether this refusal is the role's per-table create restriction. It is the one {@code allowCreateNew=false}
	 * whose (technical) name is the shared {@link DocumentPermissionsHelper#MSG_ROLE_CREATE_NOT_ALLOWED} key —
	 * that key is what marks it out from every other refusal, and is why it gets the role-named translated reason.
	 */
	private static boolean isRoleCreateRestrictionRefusal(@NonNull final LogicExpressionResult allowCreateNew)
	{
		return allowCreateNew.isFalse()
				&& DocumentPermissionsHelper.MSG_ROLE_CREATE_NOT_ALLOWED.toAD_Message().equals(allowCreateNew.getName());
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

	public JSONStandardActions getStandardActions(@NonNull final Document document, @NonNull final String adLanguage)
	{
		final HashSet<DocumentStandardAction> standardActions = new HashSet<>(document.getStandardActions());
		final ImmutableList.Builder<JSONDisabledStandardAction> disabledActions = ImmutableList.builder();

		Boolean allowWindowEdit = null;
		Boolean allowDocumentEdit = null;
		Boolean allowCreateNewRecords = null;

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
					continue;
				}
			}

			if (action.isCreateNewRecordPermissionRequired())
			{
				if (allowCreateNewRecords == null)
				{
					allowCreateNewRecords = DocumentPermissionsHelper.checkRoleCanCreateNewRecords(document.getEntityDescriptor(), permissions).isTrue();
				}

				if (!allowCreateNewRecords)
				{
					it.remove();
					//noinspection UnnecessaryContinue
					continue;
				}
			}
		}

		//
		// The role may be forbidden to create new records in this table. Unlike the removals above, that
		// refusal is explained to the user, so the action stays in the list and is transmitted as disabled.
		if (standardActions.contains(DocumentStandardAction.New))
		{
			final BooleanWithReason roleCanCreate = DocumentPermissionsHelper.checkRoleCanCreateNewRecords(document.getEntityDescriptor(), permissions);
			final JSONDisabledStandardAction newRefused = JSONDisabledStandardAction.newRefusedByRole(roleCanCreate, adLanguage);
			if (newRefused != null)
			{
				disabledActions.add(newRefused);
			}
		}

		return new JSONStandardActions(ImmutableSet.copyOf(standardActions), disabledActions.build());
	}

}
