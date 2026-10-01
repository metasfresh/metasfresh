package de.metas.ui.web.cache;

import com.google.common.collect.ImmutableMap;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
 * #%L
 * de.metas.ui.web.base
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

/**
 * Covers {@link ETagResponseEntityBuilder#includeRoleInETag()}.
 * <p>
 * The window layout is served with an ETag that is otherwise role-independent (window version + language),
 * but the layout embeds role-dependent bits (the lookup "new record" option is gated by the role's create
 * permission). Without a role component in the ETag the response is cached across roles and only clears on an
 * app restart. These tests pin that the role fingerprint carried by the layout options is folded into the
 * ETag, so two roles get different ETags and a permission-version bump changes the ETag.
 */
class ETagResponseEntityBuilderRoleTest
{
	private static final ETagAware BASE_ETAG = () -> ETag.of(1L, ImmutableMap.of());

	/** Builds the layout response ETag that would be sent for the given role fingerprint. */
	private static String layoutETagFor(final String roleETagFingerprint)
	{
		final JSONOptions jsonOpts = JSONDocumentLayoutOptions.ofAdLanguage("en_US").getJsonOpts();
		final JSONDocumentLayoutOptions layoutOptions = JSONDocumentLayoutOptions._builder()
				.jsonOpts(jsonOpts)
				.roleETagFingerprint(roleETagFingerprint)
				.build();

		final WebRequest request = mock(WebRequest.class);
		when(request.checkNotModified(anyString())).thenReturn(false); // force a full (non-304) build so we can read the ETag

		final ResponseEntity<String> response = ETagResponseEntityBuilder.ofETagAware(request, BASE_ETAG)
				.includeLanguageInETag()
				.includeRoleInETag()
				.map(x -> x)
				.jsonLayoutOptions(() -> layoutOptions)
				.toLayoutJson((result, options) -> "layout-body");

		return response.getHeaders().getETag();
	}

	@Test
	void etag_differs_between_roles()
	{
		// role 100 vs role 200, same permission version -> must not share a cache entry
		assertThat(layoutETagFor("100-5")).isNotEqualTo(layoutETagFor("200-5"));
	}

	@Test
	void etag_changes_when_permission_version_bumps()
	{
		// same role, permission version 5 -> 6 (e.g. an AD_Table_Access change): the cached layout must refresh
		assertThat(layoutETagFor("100-5")).isNotEqualTo(layoutETagFor("100-6"));
	}

	@Test
	void etag_is_stable_for_the_same_role_and_version()
	{
		// unchanged role + permissions -> identical ETag, so the 304 fast-path still works
		assertThat(layoutETagFor("100-5")).isEqualTo(layoutETagFor("100-5"));
	}
}
