package de.metas.attachments;

import java.io.File;

import org.compiere.util.MimeType;

import lombok.Builder;
import lombok.Data;

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
	private final String filename;
	private final String contentType;

	@Builder
	private AttachmentEntry(final int id,
			final String name,
			final String filename,
			final String contentType)
	{
		this.id = id;
		this.name = name == null ? "?" : name;
		this.filename = filename != null ? filename : new File(this.name).getName();
		this.contentType = contentType != null ? contentType : MimeType.getMimeType(this.name);
	}

	@Override
	public String toString()
	{
		return getName();
	}

	public String toStringX()
	{
		final StringBuilder sb = new StringBuilder(getName());

		// final byte[] data = getData();
		// if (data != null)
		// {
		// sb.append(" (");
		// //
		// float size = data.length;
		// if (size <= 1024)
		// sb.append(data.length).append(" B");
		// else
		// {
		// size /= 1024;
		// if (size > 1024)
		// {
		// size /= 1024;
		// sb.append(size).append(" MB");
		// }
		// else
		// sb.append(size).append(" kB");
		// }
		// //
		// sb.append(")");
		// }

		sb.append(" - ").append(getContentType());

		return sb.toString();
	}	// toStringX

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
