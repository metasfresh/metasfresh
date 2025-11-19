package de.metas.attachments;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.CreatedUpdatedInfo;
import de.metas.common.util.CoalesceUtil;
import de.metas.util.Check;
import lombok.AccessLevel;
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

	/**
	 * The record-references to which this instance is attached, as loaded by the repo.
	 */
	ImmutableSet<TableRecordReference> linkedRecords;

	/**
	 * Record-References that were added since the instance was loaded. Will be synched to DB on the next repo-save.
	 */
	@Getter(AccessLevel.PACKAGE)
	Set<TableRecordReference> linkedRecordsToAdd = new HashSet<>();

	/**
	 * Record-References that were removed since the instance was loaded. Will be synched to DB on the next repo-save.
	 */
	@Getter(AccessLevel.PACKAGE)
	Set<TableRecordReference> linkedRecordsToRemove = new HashSet<>();

	ImmutableMap<TableRecordReference, String> linkedRecord2AttachmentName;

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

		this.linkedRecords = ImmutableSet.copyOf(linkedRecords);
		this.linkedRecord2AttachmentName = linkedRecord2AttachmentName == null ? null : ImmutableMap.copyOf(linkedRecord2AttachmentName);

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

	/**
	 * @return the record-references to which the entry was attached when loaded by the repo, plus the references added meanwhile, minus the references removed meanwhile.
	 */
	public ImmutableSet<TableRecordReference> getLinkedRecords()
	{
		final Set<TableRecordReference> result = new HashSet<>();
		result.addAll(linkedRecords);
		result.addAll(linkedRecordsToAdd);
		result.removeAll(linkedRecordsToRemove);

		return ImmutableSet.copyOf(result);
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

	public void addLinkedRecord(@NonNull final TableRecordReference modelRef)
	{
		if (getLinkedRecords().contains(modelRef))
		{
			return;
		}
		linkedRecordsToAdd.add(modelRef);
	}

	public void removedLinkedRecord(@NonNull final TableRecordReference modelRef)
	{
		linkedRecordsToRemove.add(modelRef);
	}

	public void removeAllLinkedRecords()
	{
		linkedRecordsToAdd.clear();
		linkedRecordsToRemove.addAll(linkedRecords);
	}

	public void addLinkedRecords(@Nullable final List<TableRecordReference> additionalLinkedRecords)
	{
		if (additionalLinkedRecords == null)
		{
			return;
		}

		linkedRecordsToAdd.addAll(additionalLinkedRecords);
		additionalLinkedRecords.forEach(this.linkedRecordsToRemove::remove);
	}

	public void removeLinkedRecords(@Nullable final List<TableRecordReference> linkedRecordsToRemove)
	{
		if (linkedRecordsToRemove == null)
		{
			return;
		}
		this.linkedRecordsToRemove.addAll(linkedRecordsToRemove);
		linkedRecordsToRemove.forEach(this.linkedRecordsToAdd::remove);
	}

	public AttachmentEntry withAdditionalTag(@NonNull final AttachmentTags attachmentTags)
	{
		final AttachmentEntry build = toBuilder()
				.tags(getTags().withTags(attachmentTags))
				.build();
		build.linkedRecordsToAdd.addAll(this.linkedRecordsToAdd);
		build.linkedRecordsToRemove.addAll(this.linkedRecordsToRemove);
		return build;
	}

	public AttachmentEntry withoutTags(@NonNull final AttachmentTags attachmentTags)
	{
		final AttachmentEntry build = toBuilder()
				.tags(getTags().withoutTags(attachmentTags))
				.build();
		build.linkedRecordsToAdd.addAll(this.linkedRecordsToAdd);
		build.linkedRecordsToRemove.addAll(this.linkedRecordsToRemove);
		return build;
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
		return (linkedRecords.contains(tableRecordReference) || linkedRecordsToAdd.contains(tableRecordReference))
				&& !linkedRecordsToRemove.contains(tableRecordReference);
	}
}
