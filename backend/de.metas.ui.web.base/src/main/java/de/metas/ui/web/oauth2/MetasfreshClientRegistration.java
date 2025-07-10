package de.metas.ui.web.oauth2;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

import javax.annotation.Nullable;

@Value
@Builder
public class MetasfreshClientRegistration
{
	@NonNull ClientRegistration springClientRegistration;
	@Nullable String logoUri;

	public String getRegistrationId() {return springClientRegistration.getRegistrationId();}

	public String getClientName() {return springClientRegistration.getClientName();}
}
