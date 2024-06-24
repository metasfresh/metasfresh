package de.metas.global_qrcodes.service;

import com.google.common.base.MoreObjects;
import de.metas.process.AdProcessId;
import de.metas.process.PInstanceId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.util.MimeType;
import org.springframework.core.io.ByteArrayResource;

public final class QRCodePDFResource extends ByteArrayResource
{
	private final String filename;
	@Getter
	private final PInstanceId pinstanceId;
	@Getter
	private final AdProcessId processId;

	@Builder
	private QRCodePDFResource(
			final byte[] data,
			@NonNull final String filename,
			@NonNull final PInstanceId pinstanceId,
			@NonNull final AdProcessId processId)
	{
		super(data);
		this.filename = filename;
		this.pinstanceId = pinstanceId;
		this.processId = processId;
	}

	@Override
	public @NonNull String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("filename", filename)
				.toString();
	}

	@Override
	public String getFilename()
	{
		return filename;
	}

	public String getContentType()
	{
		return MimeType.getMimeType(filename);
	}
}
