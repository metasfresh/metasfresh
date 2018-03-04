package de.metas.ui.web.letter;

import de.metas.ui.web.window.datatypes.DocumentPath;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

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

@Builder(toBuilder = true)
@Value
public class WebuiLetter
{
	@NonNull
	private final String letterId;
	private final int ownerUserId;

	private final boolean processed;
	/** PDF data; set when the letter is marked as processed too */
	private final byte[] temporaryPDFData;

	@Default
	private final int textTemplateId = -1;
	private final String content;
	private final String subject;

	// Context
	@NonNull
	private final DocumentPath contextDocumentPath;
	private final int adOrgId;
	private final int bpartnerId;
	private final int bpartnerLocationId;
	private final String bpartnerAddress;
	private final int bpartnerContactId;
}
