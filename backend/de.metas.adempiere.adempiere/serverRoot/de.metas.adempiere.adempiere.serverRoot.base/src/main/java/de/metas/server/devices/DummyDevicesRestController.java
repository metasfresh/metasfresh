package de.metas.server.devices;

import de.metas.Profiles;
import de.metas.device.accessor.DeviceAccessorsHubFactory;
import de.metas.device.rest.DummyDevicesRestControllerTemplate;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/devices/dummy")
@RestController
@Profile(Profiles.PROFILE_App)
public class DummyDevicesRestController extends DummyDevicesRestControllerTemplate
{
	public DummyDevicesRestController(final @NonNull DeviceAccessorsHubFactory deviceAccessorsHubFactory)
	{
		super(deviceAccessorsHubFactory);
	}

	@Override
	protected void assertLoggedIn() {}
}
