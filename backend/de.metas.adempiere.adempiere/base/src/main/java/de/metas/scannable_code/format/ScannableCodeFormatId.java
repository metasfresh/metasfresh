package de.metas.scannable_code.format;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_ScannableCode_Format;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class ScannableCodeFormatId implements RepoIdAware
{
	int repoId;

	private ScannableCodeFormatId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_C_ScannableCode_Format.COLUMNNAME_C_ScannableCode_Format_ID);
	}

	public static ScannableCodeFormatId ofRepoId(final int repoId) {return new ScannableCodeFormatId(repoId);}

	@Nullable
	public static ScannableCodeFormatId ofRepoIdOrNull(@Nullable final Integer repoId) {return repoId != null && repoId > 0 ? new ScannableCodeFormatId(repoId) : null;}

	public static Optional<ScannableCodeFormatId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	@JsonCreator
	public static ScannableCodeFormatId ofObject(@NonNull final Object object) {return RepoIdAwares.ofObject(object, ScannableCodeFormatId.class, ScannableCodeFormatId::ofRepoId);}

	public static int toRepoId(@Nullable final ScannableCodeFormatId id) {return id != null ? id.getRepoId() : -1;}

	@JsonValue
	public int toJson() {return getRepoId();}

	public static boolean equals(@Nullable final ScannableCodeFormatId o1, @Nullable final ScannableCodeFormatId o2) {return Objects.equals(o1, o2);}
}
