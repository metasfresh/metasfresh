package de.metas.ui.web.window.datatypes.json;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.window.model.DocumentStandardAction;
import lombok.NonNull;
import lombok.Value;

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
 * The standard actions offered for a document: the actions themselves, plus the subset which is
 * offered but must be rendered disabled, each with its reason.
 * <p>
 * A disabled action is also contained in {@link #getActions()} — that is what lets the client grey it
 * out rather than drop it. An action which is not offered at all is simply absent from both.
 */
@Value
public class JSONStandardActions
{
	@NonNull ImmutableSet<DocumentStandardAction> actions;
	@NonNull ImmutableList<JSONDisabledStandardAction> disabledActions;
}
