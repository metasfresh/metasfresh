package de.metas.attachments;

import java.io.File;
import java.net.URI;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.MimeType;

import com.google.common.base.Preconditions;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Attachment entry
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
@Data
public final class AttachmentEntry
{
	private final int id;
	private final String name;
	private final AttachmentEntryType type;
	private final String filename;
	private final String contentType;
	private final URI url;

	@Builder
	private AttachmentEntry(
			final int id,
			@Nullable final String name,
			@NonNull final AttachmentEntryType type,
			@Nullable final String filename,
			@Nullable final String contentType,
			@Nullable final URI url)
	{
		this.id = id;
		this.name = name == null ? "?" : name;
		this.type = type;
		this.filename = filename != null ? filename : new File(this.name).getName();

		if (type == AttachmentEntryType.Data)
		{
			this.contentType = contentType != null ? contentType : MimeType.getMimeType(this.name);
			this.url = null;
		}
		else if (type == AttachmentEntryType.URL)
		{
			this.contentType = null;
			this.url = Preconditions.checkNotNull(url, "url");
		}
		else
		{
			throw new AdempiereException("Attachment entry type not supported: " + type);
		}
	}

	@Override
	public String toString()
	{
		return getName();
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
}
