package de.metas.global_qrcodes;

import com.google.common.base.Joiner;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class PrintableQRCode
{
	@Nullable String topText;
	@Nullable String bottomText;
	@NonNull String qrCode;

	private static final Joiner STRING_JOINER = Joiner.on(" ").skipNulls();

	public String getDisplayableString()
	{
		final String displayable = STRING_JOINER.join(
				StringUtils.trimBlankToNull(topText),
				StringUtils.trimBlankToNull(bottomText));

		return !displayable.isEmpty() ? displayable : qrCode;
	}

	public JsonDisplayableQRCode toJsonDisplayableQRCode()
	{
		return JsonDisplayableQRCode.builder()
				.code(qrCode)
				.displayable(getDisplayableString())
				.build();
	}
}
