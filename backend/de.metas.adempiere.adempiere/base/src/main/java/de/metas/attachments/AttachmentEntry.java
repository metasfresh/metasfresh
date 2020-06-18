package de.metas.attachments;

import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.MimeType;

import com.google.common.base.Preconditions;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

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

	AttachmentEntryId id;
	String name;
	Type type;
	String filename;
	String mimeType;
	URI url;

	@Getter
	AttachmentTags tags;

	/** The records to which this instance is attached. */
	Set<TableRecordReference> linkedRecords;

	@Builder(toBuilder = true)
	private AttachmentEntry(
			@Nullable final AttachmentEntryId id,
			@Nullable final String name,
			@NonNull final Type type,
			@Nullable final String filename,
			@Nullable final String mimeType,
			@Nullable final URI url,
			@Nullable AttachmentTags tags,
			@Singular final Set<TableRecordReference> linkedRecords)
	{
		this.id = id;
		this.name = name == null ? "?" : name;
		this.type = type;
		this.filename = filename != null ? filename : new File(this.name).getName();

		this.tags = tags != null ? tags : AttachmentTags.EMPTY;

		this.linkedRecords = linkedRecords;

		if (type == Type.Data)
		{
			this.mimeType = mimeType != null ? mimeType : MimeType.getMimeType(this.name);
			this.url = null;
		}
		else if (type == Type.URL)
		{
			this.mimeType = null;
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
		sb.append(" - ").append(getMimeType());
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

	public AttachmentEntry withAdditionalLinkedRecord(@NonNull final TableRecordReference modelRef)
	{
		if (getLinkedRecords().contains(modelRef))
		{
			return this;
		}
		return toBuilder().linkedRecord(modelRef).build();
	}

	public AttachmentEntry withAdditionalTag(@NonNull final String tagName, @NonNull final String tagValue)
	{
		return toBuilder()
				.tags(getTags().withTag(tagName, tagValue))
				.build();
	}

	public AttachmentEntry withRemovedLinkedRecord(@NonNull final TableRecordReference modelRef)
	{
		final HashSet<TableRecordReference> linkedRecords = new HashSet<>(getLinkedRecords());
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
}
