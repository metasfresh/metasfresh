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
 * Provides the application's {@link VATaxIDOnlineChecker} bean: one shared {@link VIESClient} on one
 * shared {@link RestTemplate}. Without this, the base half's check service would have an unsatisfiable
 * dependency in the real application context — the SPI would only ever be implemented by test doubles.
 *
 * <h2>Why one shared client, and no factory</h2>
 *
 * The sibling {@code CreditPassClientFactory} pattern is deliberately <b>not</b> used. That factory exists
 * because creditpass puts the per-organisation base URL into the {@code RestTemplate}'s {@code rootUri},
 * which forces one client — and one template — per configuration.
 * {@link VIESClient#check(de.metas.tax.api.VATIdentifier, de.metas.vatid.VATaxIDConfig)} instead takes the
 * base URL from its {@code VATaxIDConfig} argument on every call, so the client holds no per-organisation
 * state and one instance serves every organisation.
 *
 * <h2>Why timeouts</h2>
 *
 * {@code CreditPassClientFactory} sets <b>none</b>, which leaves a hung socket waiting indefinitely. This
 * client is called from an after-commit path and from a batch run, where that would stall the caller: the
 * save-triggered check would pin a thread long after the user's save returned, and one unresponsive member
 * state would hold up a whole nightly run. Both timeouts are therefore set explicitly. Precedent for the
 * builder API: {@code de.metas.issue.tracking.everhour}'s {@code RestService} (its {@code restTemplate()}
 * method).
 *
 * <p>The values are plain documented constants, deliberately <b>not</b> SysConfigs or Application
 * Dictionary metadata: there is no evidence of a tuning need, and new AD metadata would cost an ID-server
 * call and another migration script for a value nobody has asked to change. Should a real need appear — a
 * member state that is reliably slow, say — a SysConfig can be introduced then, at which point these two
 * constants become its defaults.
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
