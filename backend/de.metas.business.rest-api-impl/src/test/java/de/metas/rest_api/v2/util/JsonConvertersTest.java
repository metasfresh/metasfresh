/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.rest_api.v2.util;

import de.metas.attachments.AttachmentEntryType;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentSourceType;
import org.junit.Test;

import java.util.Arrays;

public class JsonConvertersTest
{
	@Test
	public void givenAllPossibleAttachmentEntryTypes_whenToJsonAttachmentSourceType_thenReturnMatchingJson()
	{
		Arrays.stream(AttachmentEntryType.values()).forEach(JsonConverters::toJsonAttachmentSourceType);
	}

	@Test
	public void givenAllPossibleJsonAttachmentSourceTypes_whenToAttachmentType_thenReturnMatchingType()
	{
		Arrays.stream(JsonAttachmentSourceType.values()).forEach(JsonConverters::toAttachmentType);
	}
}
