package de.metas.attachments;

import de.metas.CreatedUpdatedInfo;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.MimeType;

import javax.annotation.Nullable;
import java.io.File;
import java.net.URI;

/**
 * Represents an attachment. For the entity that links an attachment to the actual data records it is attache to, see {@link AttachmentReference}.
 */
@Value
@ToString
public class AttachmentEntry
{
	@With(AccessLevel.PACKAGE)
	AttachmentEntryId id;

	String name;

	AttachmentEntryType type;

	String filename;

	String mimeType;

	URI url;

	@Getter
	AttachmentTags tags;

	/**
	 * Like the {@code id}, this is {@code null} if the attachment was not persisted yet.
	 */
	CreatedUpdatedInfo createdUpdatedInfo;

	@lombok.Builder(toBuilder = true)
	private AttachmentEntry(
			@Nullable final AttachmentEntryId id,
			@Nullable final String name,
			@NonNull final AttachmentEntryType type,
			@Nullable final String filename,
			@Nullable final String mimeType,
			@Nullable final URI url,
			@Nullable final AttachmentTags tags,
			@Nullable final CreatedUpdatedInfo createdUpdatedInfo)
	{
		this.id = id;
		this.name = name == null ? "?" : name;
		this.type = type;
		this.filename = filename != null ? filename : new File(this.name).getName();

		this.tags = tags != null ? tags : AttachmentTags.EMPTY;
		this.createdUpdatedInfo = createdUpdatedInfo;

		switch (type)
		{
			case Data:
				this.mimeType = mimeType != null ? mimeType : MimeType.getMimeType(this.name);
				this.url = null;
				break;
			case URL:
				this.mimeType = null;
				this.url = Check.assumeNotNull(url, "url may not be null for this={}", this);
				break;
			case LocalFileURL:
				this.mimeType = mimeType != null ? mimeType : MimeType.getMimeType(this.name);
				this.url = Check.assumeNotNull(url, "url may not be null for this={}", this);
				break;
			default:
				throw new AdempiereException("Attachment entry type not supported: " + type);
		}
	}

	@NonNull
	public AttachmentEntryId getIdNonNull()
	{
		return Check.assumeNotNull(id, "id may not be null for this={}", this);
	}

	public String toStringX()
	{
		return getName() + " - " + getMimeType();
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

	public AttachmentEntry withAdditionalTag(@NonNull final String tagName, @NonNull final String tagValue)
	{
		return toBuilder()
				.tags(getTags().withTag(tagName, tagValue))
				.build();
	}

	public AttachmentEntry withAdditionalTag(@NonNull final AttachmentTags attachmentTags)
	{
		return toBuilder()
				.tags(getTags().withTags(attachmentTags))
				.build();
	}

	public AttachmentEntry withoutTags(@NonNull final AttachmentTags attachmentTags)
	{
		return toBuilder()
				.tags(getTags().withoutTags(attachmentTags))
				.build();
	}
}
