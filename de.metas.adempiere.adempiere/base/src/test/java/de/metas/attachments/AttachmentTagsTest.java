package de.metas.attachments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class AttachmentTagsTest
{
	@Test
	public void test_convert_To_and_From_String()
	{
		assertThat(AttachmentTags.ofString(AttachmentTags.EMPTY.getTagsAsString()))
				.isSameAs(AttachmentTags.EMPTY);

		test_convert_To_and_From_String(AttachmentTags.builder()
				.tag("tag1", "value1")
				.tag("tag2", "value2")
				.build());
	}

	private void test_convert_To_and_From_String(final AttachmentTags tags)
	{
		final AttachmentTags tags2 = AttachmentTags.ofString(tags.getTagsAsString());
		assertThat(tags2).isEqualTo(tags);
	}
}
