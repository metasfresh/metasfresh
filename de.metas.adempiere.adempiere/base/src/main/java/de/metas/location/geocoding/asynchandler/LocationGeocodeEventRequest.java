package de.metas.location.geocoding.asynchandler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.location.LocationId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
@Builder
@JsonDeserialize(builder = LocationGeocodeEventRequest.LocationGeocodeEventRequestBuilder.class)
public class LocationGeocodeEventRequest
{
	@NonNull
	LocationId locationId;

	public static LocationGeocodeEventRequest of(@NonNull final LocationId locationId)
	{
		return new LocationGeocodeEventRequest(locationId);
	}

	// this is a workaround for
	@JsonIgnoreProperties(ignoreUnknown = true) // the annotation to ignore properties should be set on the deserializer method (on the builder), and not on the base class
	@JsonPOJOBuilder(withPrefix = "")
	static class LocationGeocodeEventRequestBuilder
	{
	}
}

/*
Note for future me.
There's a lot wrong with this class, and a lot of workarounds. It's all this magic that jackson is supposed to do, yet doesnt correctly.

1. It would have been nice if jackson would work by simply annotating `of` method with @JsonBuilder
	This doesn't work (for some unknown reason) as jackson fails to deserialize: not LocationGeocodeEventRequest, but LocationId (WTF?!?!).
	Because of this bug, I have created the builder, and that seems wo be working... for now.

2. @Value(staticConstructor = "of") and @Builder don't work together
	`of` method is not created => i created the method manually

 */
