package de.metas.doctextline;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.compiere.model.I_C_Doc_TextLine;

import javax.annotation.Nullable;

@Value
public class DocTextLineId implements RepoIdAware
{
	@JsonCreator
	public static DocTextLineId ofRepoId(final int repoId)
	{
		return new DocTextLineId(repoId);
	}

	@Nullable
	public static DocTextLineId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new DocTextLineId(repoId) : null;
	}

	public static int toRepoId(@Nullable final DocTextLineId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private DocTextLineId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_C_Doc_TextLine.COLUMNNAME_C_Doc_TextLine_ID);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
