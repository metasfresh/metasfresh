package de.metas.process;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_AD_Process;

import javax.annotation.Nullable;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum ReportResultDataTargetType implements ReferenceListAwareEnum
{
	SaveToServerDirectory(X_AD_Process.STOREPROCESSRESULTFILEON_Server),
	ForwardToUserBrowser(X_AD_Process.STOREPROCESSRESULTFILEON_Browser),
	Both(X_AD_Process.STOREPROCESSRESULTFILEON_Both),
	None("N"),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<ReportResultDataTargetType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static ReportResultDataTargetType ofCode(@NonNull final String code) {return index.ofCode(code);}

	public static Optional<ReportResultDataTargetType> optionalOfNullableCode(@Nullable final String code) {return index.optionalOfNullableCode(code);}

	public boolean isSaveToServerDirectory() {return this.equals(Both) || this.equals(SaveToServerDirectory);}

	public boolean isForwardToUserBrowser() {return this.equals(Both) || this.equals(ForwardToUserBrowser);}

	public ReportResultDataTargetType forwardingToUserBrowser(final boolean forward)
	{
		if (forward)
		{
			return switch (this)
			{
				case SaveToServerDirectory, Both -> Both;
				case ForwardToUserBrowser, None -> ForwardToUserBrowser;
			};
		}
		else
		{
			return switch (this)
			{
				case SaveToServerDirectory, Both -> SaveToServerDirectory;
				case ForwardToUserBrowser, None -> None;
			};
		}
	}

}
