package de.metas.attachments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.base.Splitter.MapSplitter;
import com.google.common.collect.ImmutableMap;

import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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

@EqualsAndHashCode
public final class AttachmentTags
{
	public static AttachmentTags ofString(@Nullable final String tagsAsString)
	{
		if (Check.isEmpty(tagsAsString, true))
		{
			return EMPTY;
		}

		final Map<String, String> map = TAGS_STRING_SPLITTER.split(tagsAsString.trim());
		return ofMap(map);
	}

	public static AttachmentTags ofMap(@NonNull final Map<String, String> map)
	{
		if (map.isEmpty())
		{
			return EMPTY;
		}

		return new AttachmentTags(map);
	}

	public static final AttachmentTags EMPTY = new AttachmentTags();

	public static final String TAGNAME_IS_DOCUMENT = "IsDocument";

	/**
	 * if set to {@code true}, it advises the system that
	 * the respective attachment is a PDF
	 * and when a PDF is created for the invoice to which it is attached, then this attachment's PDF shall be appended to that invoice's PDF.
	 */
	public static final String TAGNAME_CONCATENATE_PDF_TO_INVOICE_PDF = "Concatenate_Pdf_to_InvoicePdf";
	public static final String TAGNAME_BPARTNER_RECIPIENT_ID = "C_BPartner_Recipient_ID";
	public static final String TAGNAME_STORED_PREFIX = "Stored_";

	private static final String TAGS_SEPARATOR = "\n";
	private static final String TAGS_KEY_VALUE_SEPARATOR = "=";
	private static final MapSplitter TAGS_STRING_SPLITTER = Splitter.on(TAGS_SEPARATOR)
			.withKeyValueSeparator(TAGS_KEY_VALUE_SEPARATOR);
	private static final MapJoiner TAGS_STRING_JOINER = Joiner.on(TAGS_SEPARATOR)
			.withKeyValueSeparator(TAGS_KEY_VALUE_SEPARATOR);

	private final ImmutableMap<String, String> tags;

	@Builder
	private AttachmentTags(@Singular final Map<String, String> tags)
	{
		this.tags = ImmutableMap.copyOf(tags);
		assertTagsValid(tags);
	}

	private static void assertTagsValid(@NonNull final Map<String, String> tags)
	{
		tags.forEach((tagName, tagValue) -> assertTagValid(tagName, tagValue));
	}

	private static void assertTagValid(final String tagName, final String tagValue)
	{
		Check.errorIf(tagName.contains(TAGS_SEPARATOR),
				"Tags may not contain {}; illegal entry: name={}; value={}",
				TAGS_SEPARATOR, tagName, tagValue);
		Check.errorIf(tagName.contains(TAGS_KEY_VALUE_SEPARATOR),
				"Tags may not contain {}; illegal entry: name={}; value={}",
				TAGS_KEY_VALUE_SEPARATOR, tagName, tagValue);

		Check.errorIf(tagValue.contains(TAGS_SEPARATOR),
				"Tags may not contain {}; illegal entry: name={}; value={}",
				TAGS_SEPARATOR, tagName, tagValue);
		Check.errorIf(tagValue.contains(TAGS_KEY_VALUE_SEPARATOR),
				"Tags may not contain {}; illegal entry: name={}; value={}",
				TAGS_KEY_VALUE_SEPARATOR, tagName, tagValue);
	}

	private AttachmentTags()
	{
		this.tags = ImmutableMap.of();
	}

	public ImmutableMap<String, String> toMap()
	{
		return tags;
	}

	/**
	 * @deprecated Please use {@link #getTagsAsString()}
	 */
	@Deprecated
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(tags)
				.toString();
	}

	public String getTagsAsString()
	{
		return TAGS_STRING_JOINER.join(tags);
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
		return StringUtils.toBoolean(getTagValueOrNull(tagName), false);
	}

	public boolean hasTagSetToString(@NonNull final String tagName, @NonNull final String tagValue)
	{
		return Objects.equals(getTagValueOrNull(tagName), tagValue);
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

	public AttachmentTags withTag(@NonNull final String tagName, @NonNull final String tagValue)
	{
		if (hasTagSetToString(tagName, tagValue))
		{
			return this;
		}
		else
		{
			final HashMap<String, String> map = new HashMap<>(this.tags);
			map.put(tagName, tagValue);
			return new AttachmentTags(map);
		}
	}
}
