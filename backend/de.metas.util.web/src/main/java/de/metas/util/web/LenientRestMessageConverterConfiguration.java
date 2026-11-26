/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.util.web;

import de.metas.JsonObjectMapperHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * REST message converter shared by the metasfresh REST entrypoints (app server and webui-api).
 * <p>
 * The converter's mapper is the shared {@code @Primary} mapper's configuration, plus the settings of the pre-JDK25 REST converter mapper
 * ({@code Jackson2ObjectMapperBuilder.json()} under springfox's {@code @EnableWebMvc}) that matter on the wire:
 * <ul>
 * <li>tolerant of unknown request properties;</li>
 * <li>date/time values written as timestamps (epoch seconds/milliseconds, {@code java.time} local types as arrays), not as ISO strings;</li>
 * <li>zoned/offset date/time request values adjusted to UTC.</li>
 * </ul>
 * See {@link JsonObjectMapperHolder#newHttpMessageConverterJsonObjectMapper()} for the details.
 * Spring Boot's own converter auto-configuration backs off in favour of this bean.
 * <p>
 * Deliberately <b>not</b> a {@code @Configuration}: it is not picked up by component scanning, but {@code @Import}-ed explicitly by the
 * applications that want it, so that no other {@code de.metas}-scanning application silently becomes lenient.
 * <p>
 * Note: this only takes effect as long as Spring Boot's MVC auto-configuration is active, i.e. no {@code @EnableWebMvc} and no
 * {@code WebMvcConfigurationSupport} subclass in the application context.
 *
 * @see JsonObjectMapperHolder#newHttpMessageConverterJsonObjectMapper()
 */
public class LenientRestMessageConverterConfiguration
{
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter()
	{
		return new MappingJackson2HttpMessageConverter(JsonObjectMapperHolder.newHttpMessageConverterJsonObjectMapper());
	}
}
