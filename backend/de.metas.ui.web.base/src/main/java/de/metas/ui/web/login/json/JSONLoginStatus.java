package de.metas.ui.web.login.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JSONLoginStatus
{
	private static final JSONLoginStatus REQUIRES_AUTHENTICATION = builder().status(Status.REQUIRES_AUTHENTICATION).build();
	private static final JSONLoginStatus REQUIRES_2FA = builder().status(Status.REQUIRES_2FA).build();
	private static final JSONLoginStatus LOGGED_IN = builder().status(Status.LOGGED_IN).build();

	public enum Status
	{
		REQUIRES_AUTHENTICATION,
		REQUIRES_2FA,
		REQUIRES_LOGIN_COMPLETE,
		LOGGED_IN,
	}

	@NonNull Status status;
	List<JSONLoginRole> availableRoles;

	public static JSONLoginStatus requiresAuthentication() {return REQUIRES_AUTHENTICATION;}

	public static JSONLoginStatus requires2FA() {return REQUIRES_2FA;}

	public static JSONLoginStatus requiresLoginComplete(@NonNull final List<JSONLoginRole> availableRoles) {return builder().status(Status.REQUIRES_LOGIN_COMPLETE).availableRoles(availableRoles).build();}

	public static JSONLoginStatus loggedIn() {return LOGGED_IN;}
}
