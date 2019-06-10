package de.metas.attachments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;

import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

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

public class AttachmentTags
{

	public static final String TAGNAME_IS_DOCUMENT = "IsDocument";

	/**
	 * if set to {@code true}, it advises the system that
	 * the respective attachment is a PDF
	 * and when a PDF is created for the invoice to which it is attached, then this attachment's PDF shall be appended to that invoice's PDF.
	 */
	public static final String TAGNAME_CONCATENATE_PDF_TO_INVOICE_PDF = "Concatenate_Pdf_to_InvoicePdf";

	public static final String TAGNAME_BPARTNER_RECIPIENT_ID = "C_BPartner_Recipient_ID";

	public static final String TAGNAME_STORED_PREFIX = "Stored_";

	public static final String TAGS_SEPARATOR = "\n";

	public static final String TAGS_KEY_VALUE_SEPARATOR = "=";

	private final ImmutableMap<String, String> tags;
	
	public static final ImmutableMap<String, String> emptyTags=ImmutableMap.of();

	@Builder(toBuilder = true)
	private AttachmentTags(@Singular final Map<String, String> tags)
	{
		this.tags = ImmutableMap.copyOf(tags);
		validateTags(tags);
	}

	public Map<String,String> toMap(){
		return new HashMap<String,String>(tags);
	}
	
	public String getTagsAsString()
	{

		final String tagsAsString = Joiner
				.on(TAGS_SEPARATOR)
				.withKeyValueSeparator(TAGS_KEY_VALUE_SEPARATOR)
				.join(tags);
		return tagsAsString;
	}

	/** @return {@code true} if this attachment has a tag with the given name; the label doesn't need to have a value though. */
	public boolean hasTag(@NonNull final String tag)
	{
		return tags.containsKey(tag);
	}

	public boolean hasAllTagsSetToTrue(@NonNull final List<String> tagNames)
	{
		for (final String tagName : tagNames)
		{
			if (!hasTagSetToTrue(tagName))
			{
				return false;
			}
		}
		return true;
	}

	public boolean hasTagSetToTrue(@NonNull final String tagName)
	{
		return StringUtils.toBoolean(tags.get(tagName), false);
	}

	public boolean hasTagSetToString(@NonNull final String tagName, @NonNull final String tagValue)
	{
		return Objects.equals(tags.get(tagName), tagValue);
	}

	public String getTagValue(@NonNull final String tagName)
	{
		return Check.assumeNotEmpty(
				getTagValueOrNull(tagName),
				"This attachmentEntry needs to have a tag with name={} and a value; this={}", tagName, this);
	}

	public String getTagValueOrNull(String tagName)
	{
		return tags.get(tagName);
	}

	public boolean hasAllTagsSetToAnyValue(@NonNull final List<String> tagNames)
	{
		for (final String tagName : tagNames)
		{
			if (getTagValueOrNull(tagName) == null)
			{
				return false;
			}
		}
		return true;
	}

	public static Map<String, String> getTagsFromString(final String tagsAsString)
	{
		final Map<String, String> tags;
		tags = Splitter
				.on(TAGS_SEPARATOR)
				.withKeyValueSeparator(TAGS_KEY_VALUE_SEPARATOR)
				.split(tagsAsString);
		return tags;
	}

	private static void validateTags(@NonNull final Map<String, String> tags)
	{
		for (final Entry<String, String> tag : tags.entrySet())
		{
			Check.errorIf(tag.getKey().contains(TAGS_SEPARATOR),
					"Tags may not contain {}; illegal entry: name={}; value={}",
					TAGS_SEPARATOR, tag.getKey(), tag.getValue());
			Check.errorIf(tag.getKey().contains(TAGS_KEY_VALUE_SEPARATOR),
					"Tags may not contain {}; illegal entry: name={}; value={}",
					TAGS_KEY_VALUE_SEPARATOR, tag.getKey(), tag.getValue());

			Check.errorIf(tag.getValue().contains(TAGS_SEPARATOR),
					"Tags may not contain {}; illegal entry: name={}; value={}",
					TAGS_SEPARATOR, tag.getKey(), tag.getValue());
			Check.errorIf(tag.getValue().contains(TAGS_KEY_VALUE_SEPARATOR),
					"Tags may not contain {}; illegal entry: name={}; value={}",
					TAGS_KEY_VALUE_SEPARATOR, tag.getKey(), tag.getValue());
		}
	}
}
