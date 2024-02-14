package de.metas.attachments;

import com.google.common.base.Preconditions;
import de.metas.CreatedUpdatedInfo;
import de.metas.common.util.CoalesceUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.MimeType;

import javax.annotation.Nullable;
import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Attachment entry
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
@ToString
public class AttachmentEntry
{
	AttachmentEntryId id;
	String name;
	AttachmentEntryType type;
	String filename;
	String mimeType;
	URI url;

	@Getter
	AttachmentTags tags;

	/** The records to which this instance is attached. */
	Set<TableRecordReference> linkedRecords;

	Map<TableRecordReference, String> linkedRecord2AttachmentName;

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
			@Singular final Set<TableRecordReference> linkedRecords,
			@Nullable final Map<TableRecordReference, String> linkedRecord2AttachmentName,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo)
	{
		this.id = id;
		this.name = name == null ? "?" : name;
		this.type = type;
		this.filename = filename != null ? filename : new File(this.name).getName();

		this.tags = tags != null ? tags : AttachmentTags.EMPTY;

		this.linkedRecords = linkedRecords;
		this.linkedRecord2AttachmentName = linkedRecord2AttachmentName;
		
		this.createdUpdatedInfo=createdUpdatedInfo;

		switch (type)
		{
			case Data:
				this.mimeType = mimeType != null ? mimeType : MimeType.getMimeType(this.name);
				this.url = null;
				break;
			case URL:
				this.mimeType = null;
				this.url = Preconditions.checkNotNull(url, "url");
				break;
			case LocalFileURL:
				this.mimeType = mimeType != null ? mimeType : MimeType.getMimeType(this.name);
				this.url = Preconditions.checkNotNull(url, "url");
				break;
			default:
				throw new AdempiereException("Attachment entry type not supported: " + type);
		}
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

	public AttachmentEntry withAdditionalLinkedRecords(@NonNull final List<TableRecordReference> additionalLinkedRecords)
	{
		if (getLinkedRecords().containsAll(additionalLinkedRecords))
		{
			return this;
		}

		final Set<TableRecordReference> tmp = new HashSet<>(getLinkedRecords());
		tmp.addAll(additionalLinkedRecords);

		return toBuilder().linkedRecords(tmp).build();
	}

	public AttachmentEntry withRemovedLinkedRecords(@NonNull final List<TableRecordReference> linkedRecordsToRemove)
	{
		final HashSet<TableRecordReference> linkedRecords = new HashSet<>(getLinkedRecords());
		if (linkedRecords.removeAll(linkedRecordsToRemove))
		{
			return toBuilder().clearLinkedRecords().linkedRecords(linkedRecords).build();
		}
		else
		{
			return this;
		}
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

	/**
	 * @return the attachment's filename as seen from the given {@code tableRecordReference}. Note that different records might share the same attachment, but refer to it under different file names.
	 */
	@NonNull
	public String getFilename(@NonNull final TableRecordReference tableRecordReference)
	{
		if (linkedRecord2AttachmentName == null)
		{
			return filename;
		}

		return CoalesceUtil.coalesceNotNull(
				linkedRecord2AttachmentName.get(tableRecordReference), 
				filename);
	}

	public boolean hasLinkToRecord(@NonNull final TableRecordReference tableRecordReference)
	{
		return linkedRecords.contains(tableRecordReference);
	}
}
