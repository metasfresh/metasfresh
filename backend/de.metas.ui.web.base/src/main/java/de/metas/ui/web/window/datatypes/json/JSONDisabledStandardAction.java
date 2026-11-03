package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.i18n.BooleanWithReason;
import de.metas.ui.web.window.controller.DocumentPermissionsHelper;
import de.metas.ui.web.window.model.DocumentStandardAction;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * A standard action which is transmitted but shall be rendered disabled, together with the reason why.
 * <p>
 * The action stays present in {@code standardActions} so the client can grey it out instead of dropping it;
 * {@code reason} is the already translated text to show, {@code reasonKey} the stable AD_Message key
 * automated tests assert on (rendered text is language dependent and must never be asserted).
 */
@Value
@Builder
public class JSONDisabledStandardAction
{
	@JsonProperty("action")
	@NonNull DocumentStandardAction action;

	@JsonProperty("reason")
	@NonNull String reason;

	@JsonProperty("reasonKey")
	@NonNull String reasonKey;

	/**
	 * Builds the disabled entry for a create-producing standard action ({@link DocumentStandardAction#New} or
	 * {@link DocumentStandardAction#Clone}) that the role may not create.
	 *
	 * @param action                  the create-producing action to mark disabled (New or Clone)
	 * @param roleCanCreateNewRecords the answer of {@link DocumentPermissionsHelper#checkRoleCanCreateNewRecords}
	 * @return the disabled {@code action}, or null if the role may create records
	 */
	@Nullable
	public static JSONDisabledStandardAction refusedByRole(
			@NonNull final DocumentStandardAction action,
			@NonNull final BooleanWithReason roleCanCreateNewRecords,
			@NonNull final String adLanguage)
	{
		if (!roleCanCreateNewRecords.isFalse())
		{
			return null;
		}

		return builder()
				.action(action)
				.reason(roleCanCreateNewRecords.getReason().translate(adLanguage))
				.reasonKey(DocumentPermissionsHelper.MSG_ROLE_CREATE_NOT_ALLOWED.toAD_Message())
				.build();
	}
}
