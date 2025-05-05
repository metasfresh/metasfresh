package de.metas.process;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.util.Check;
import de.metas.util.OptionalBoolean;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.nio.file.Path;

/**
 * Metadata that specifies where to forward and/or store the result data of a process. 
 * Note that the process data itself is stored in {@link de.metas.report.ReportResultData}.
 */
@Value
public class ReportResultDataTarget
{
	public static final ReportResultDataTarget ForwardToUserBrowser = builder().targetType(ReportResultDataTargetType.ForwardToUserBrowser).build();

	@NonNull @With ReportResultDataTargetType targetType;
	@Nullable Path serverTargetDirectory;
	@Nullable String targetFilename;

	@Builder(toBuilder = true)
	@Jacksonized
	private ReportResultDataTarget(
			@NonNull final ReportResultDataTargetType targetType,
			@Nullable final Path serverTargetDirectory,
			@Nullable final String targetFilename)
	{
		if (targetType.isSaveToServerDirectory() && serverTargetDirectory == null)
		{
			throw new AdempiereException("targetDir shall be provided when targetType is " + targetType);
		}

		this.targetType = targetType;
		this.serverTargetDirectory = serverTargetDirectory;
		this.targetFilename = StringUtils.trimBlankToNull(targetFilename);
	}

	public boolean isSaveToServerDirectory() {return targetType.isSaveToServerDirectory();}

	public boolean isForwardToUserBrowser() {return targetType.isForwardToUserBrowser();}

	@NonNull
	@JsonIgnore
	public Path getServerTargetDirectoryNotNull() {return Check.assumeNotNull(serverTargetDirectory, "targetDir is set for {}", this);}

	public ReportResultDataTarget forwardingToUserBrowser(final OptionalBoolean forward)
	{
		return forward.isPresent() ? forwardingToUserBrowser(forward.isTrue()) : this;
	}

	public ReportResultDataTarget forwardingToUserBrowser(final boolean forward)
	{
		return withTargetType(targetType.forwardingToUserBrowser(forward));
	}
}
