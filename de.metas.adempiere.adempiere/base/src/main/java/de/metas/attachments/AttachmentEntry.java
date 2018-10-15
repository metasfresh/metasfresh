package de.metas.attachments;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

import javax.annotation.Nullable;

import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.util.MimeType;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import de.metas.util.Check;

/**
 * Attachment entry
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
@ToString
public final class AttachmentEntry
{
	public enum Type
	{
		Data, URL
	}

	private final AttachmentEntryId id;
	private final String name;
	private final Type type;
	private final String filename;
	private final String contentType;
	private final URI url;

	private final ImmutableMap<String, String> labels;

	/** The records to which this instance is attached. */
	private final Set<ITableRecordReference> linkedRecords;

	@Builder(toBuilder = true)
	private AttachmentEntry(
			@Nullable final AttachmentEntryId id,
			@Nullable final String name,
			@NonNull final Type type,
			@Nullable final String filename,
			@Nullable final String contentType,
			@Nullable final URI url,
			@Singular final Map<String, String> labels,
			@Singular final Set<ITableRecordReference> linkedRecords)
	{
		this.id = id;
		this.name = name == null ? "?" : name;
		this.type = type;
		this.filename = filename != null ? filename : new File(this.name).getName();
		this.labels = ImmutableMap.copyOf(labels);

		this.linkedRecords = linkedRecords;

		if (type == Type.Data)
		{
			this.contentType = contentType != null ? contentType : MimeType.getMimeType(this.name);
			this.url = null;
		}
		else if (type == Type.URL)
		{
			this.contentType = null;
			this.url = Preconditions.checkNotNull(url, "url");
		}
		else
		{
			throw new AdempiereException("Attachment entry type not supported: " + type);
		}
	}

	public String toStringX()
	{
		final StringBuilder sb = new StringBuilder(getName());
		sb.append(" - ").append(getContentType());
		return sb.toString();
	}

	public boolean isPDF()
	{
		final String name = getName().trim().toLowerCase();
		return name.endsWith(".pdf");
	}

	public boolean isGraphic()
	{
		final String name = getName().trim().toLowerCase();
		return name.endsWith(".gif") || name.endsWith(".jpg") || name.endsWith(".png");
	}

	public AttachmentEntry withAdditionalLinkedRecord(@NonNull final ITableRecordReference modelRef)
	{
		if (getLinkedRecords().contains(modelRef))
		{
			return this;
		}
		return toBuilder().linkedRecord(modelRef).build();
	}

	public AttachmentEntry withRemovedLinkedRecord(@NonNull final ITableRecordReference modelRef)
	{
		final HashSet<ITableRecordReference> linkedRecords = new HashSet<>(getLinkedRecords());
		if (linkedRecords.remove(modelRef))
		{
			return toBuilder().clearLinkedRecords().linkedRecords(linkedRecords).build();
		}
		else
		{
			return this;
		}
	}

	public AttachmentEntry withoutLinkedRecords()
	{
		return toBuilder().clearLinkedRecords().build();
	}

	/** @return {@code true} if this attachment has a label with the given name; the label doesn't need to have a value though. */
	public boolean hasLabel(@NonNull final String label)
	{
		return labels.containsKey(label);
	}

	public String getLabelValue(@NonNull final String labelName)
	{
		return Check.assumeNotEmpty(
				labels.get(labelName),
				"This attachmentEntry needs to have a label with name={} and a value; this={}", labelName, this);
	}
}
