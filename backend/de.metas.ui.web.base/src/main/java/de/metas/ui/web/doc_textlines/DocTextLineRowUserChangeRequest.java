package de.metas.ui.web.doc_textlines;

import de.metas.doctextline.TextLineScope;
import lombok.Builder;
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
 * Shape copied from {@code shipment_candidates_editor}'s {@code ShipmentCandidateRowUserChangeRequest}
 * (DESIGN.md § D-E): a partial-update DTO where {@code null} means "field not touched by this patch", not
 * "clear the field" -- so an edit to an empty text line (AC24) is represented as {@code textLine=""}, which
 * is non-null and therefore applied.
 */
@Value
@Builder
public class DocTextLineRowUserChangeRequest
{
	@Nullable
	String textLine;

	@Nullable
	TextLineScope textLineScope;
}
