package de.metas.security.qr;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.security.UserAuthToken;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
@Jacksonized
public class UserAuthQRCode
{
	@NonNull String token;
	@NonNull UserId userId;

	public static boolean equals(@Nullable final UserAuthQRCode o1, @Nullable final UserAuthQRCode o2)
	{
		return Objects.equals(o1, o2);
	}

	@NonNull
	public static UserAuthQRCode ofGlobalQRCodeJsonString(final String json)
	{
		return UserAuthQRCodeJsonConverter.fromGlobalQRCodeJsonString(json);
	}

	@NonNull
	public static UserAuthQRCode ofGlobalQRCode(final GlobalQRCode globalQRCode)
	{
		return UserAuthQRCodeJsonConverter.fromGlobalQRCode(globalQRCode);
	}

	@NonNull
	public static UserAuthQRCode ofAuthToken(@NonNull final UserAuthToken authToken)
	{
		return builder()
				.userId(authToken.getUserId())
				.token(authToken.getAuthToken())
				.build();
	}

	@NonNull
	public PrintableQRCode toPrintableQRCode()
	{
		return PrintableQRCode.builder()
				.qrCode(toGlobalQRCodeJsonString())
				.bottomText(getDisplayableQrCode())
				.build();
	}

	public String getDisplayableQrCode() {
		return String.valueOf(userId.getRepoId());
	}

	@NonNull
	public String toGlobalQRCodeJsonString()
	{
		return UserAuthQRCodeJsonConverter.toGlobalQRCodeJsonString(this);
	}

	public static boolean isTypeMatching(@NonNull final GlobalQRCode globalQRCode)
	{
		return UserAuthQRCodeJsonConverter.isTypeMatching(globalQRCode);
	}
}
