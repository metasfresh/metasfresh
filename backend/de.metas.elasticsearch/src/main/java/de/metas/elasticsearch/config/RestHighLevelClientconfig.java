/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestHighLevelClientconfig
{
	@Value("${elasticsearch.scheme:}")
	private String elasticsearchScheme;
	
	@Value("${elasticsearch.host:OFF}")
	private String elasticsearchHost;

	@Value("${elasticsearch.port:-1}")
	private int elasticsearchPort;
	
	@Bean(name = "metasfreshRestHighLevelClientconfig")
    //@Conditional()
	public RestHighLevelClient metasfreshRestHighLevelClientconfig()
	{
		final String effectiveScheme= "OFF".equals(elasticsearchScheme)?null:elasticsearchScheme;
		
		return new RestHighLevelClient(
				RestClient.builder(
						new HttpHost(elasticsearchHost, elasticsearchPort, effectiveScheme)));
	}
}
