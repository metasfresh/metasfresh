/*
 * #%L
 * de.metas.vatid
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

package de.metas.vatid.vies;

import de.metas.vatid.VATaxIDOnlineChecker;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Provides the application's {@link VATaxIDOnlineChecker} bean: one shared {@link VIESClient} on one shared
 * {@link RestTemplate}. Without it the base half's check service would have an unsatisfiable dependency
 * outside tests.
 *
 * <p>One shared client, no factory: unlike {@code CreditPassClientFactory}, which needs one client per
 * configuration because it puts the base URL into the template's {@code rootUri}, {@link VIESClient} takes
 * the base URL from its {@code VATaxIDConfig} argument on every call and so holds no per-organisation state.
 *
 * <p>Both timeouts are set explicitly ({@code CreditPassClientFactory} sets none): this client is called from
 * an after-commit path and from a batch run, where a hung socket would pin a thread long past the user's save
 * or stall a whole nightly run. They are plain constants rather than SysConfigs or AD metadata because nobody
 * has asked to tune them; a SysConfig introduced later would take these as its defaults.
 */
@Configuration
public class VIESClientConfiguration
{
	/**
	 * Establishing a TCP connection to the VIES endpoint takes well under a second in practice; anything
	 * beyond this means the endpoint is not reachable right now, which the checker reports as
	 * {@code ServiceUnavailable}.
	 */
	private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(5);

	/**
	 * A VIES check is one small request about one VAT-ID. A member state that has not answered within this
	 * window is not going to answer usefully, and waiting longer only delays the caller — the after-commit
	 * check will be retried by the nightly run anyway.
	 */
	private static final Duration READ_TIMEOUT = Duration.ofSeconds(20);

	/**
	 * Note that {@link VIESClient}'s {@link RestTemplate} stays constructor-injected: that is what lets its
	 * unit tests drive it with a stubbed template, and nothing here changes that.
	 */
	@Bean
	public VIESClient viesClient()
	{
		final RestTemplate restTemplate = new RestTemplateBuilder()
				.setConnectTimeout(CONNECT_TIMEOUT)
				.setReadTimeout(READ_TIMEOUT)
				.build();

		return new VIESClient(restTemplate);
	}
}
