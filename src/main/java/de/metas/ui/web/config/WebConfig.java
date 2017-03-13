package de.metas.ui.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Configuration
// @EnableWebMvc // NOTE: if u enable it, the swagger won't work!
public class WebConfig extends WebMvcConfigurerAdapter
{
	public static final String ENDPOINT_ROOT = "/rest/api";

	//
	// Common endpoint parameter names
	public static final String PARAM_WindowId = "type";
	public static final String PARAM_DocumentId = "id";
	public static final String PARAM_TabId = "tabid";
	public static final String PARAM_RowId = "rowId";


	@Override
	public void addCorsMappings(final CorsRegistry registry)
	{
		// FIXME: for now we enable CORS for the whole REST API
		// registry.addMapping(ENDPOINT_ROOT + "/**");
		
		// NOTE: this seems to not work (i.e. headers are not added), so:
		// pls check de.metas.ui.web.config.CORSFilter.doFilter(ServletRequest, ServletResponse, FilterChain)
		// because we are setting the headers there... and that works!
		
		registry.addMapping("/**");
	}
}
