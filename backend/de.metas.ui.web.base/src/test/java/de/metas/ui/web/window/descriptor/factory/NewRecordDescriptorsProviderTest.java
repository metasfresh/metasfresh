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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.ui.web.window.descriptor.factory;

import de.metas.bpartner.quick_input.service.BPartnerQuickInputService;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Guards the webapi boot sequence: {@link NewRecordDescriptorsProvider} is a {@code @Component}, so its
 * constructor runs during bean instantiation, before {@code SpringContextHolder} has been configured
 * (that happens later, on {@code ContextRefreshedEvent}). Touching {@code bpartnerQuickInputService}
 * from the constructor therefore risks the fatal boot error
 * {@code AdempiereException: SpringApplicationContext not configured yet}. Registration must be deferred
 * to {@code ApplicationReadyEvent}.
 */
class NewRecordDescriptorsProviderTest
{
	@Test
	void constructor_doesNotTouchSpringBackedService()
	{
		final DocumentDescriptorFactory documentDescriptors = Mockito.mock(DocumentDescriptorFactory.class);
		final BPartnerQuickInputService bpartnerQuickInputService = Mockito.mock(BPartnerQuickInputService.class);

		new NewRecordDescriptorsProvider(documentDescriptors, bpartnerQuickInputService);

		// The constructor must not reach into bpartnerQuickInputService (which hits SpringContextHolder);
		// that lookup belongs in the deferred ApplicationReadyEvent handler, not in bean construction.
		Mockito.verifyZeroInteractions(bpartnerQuickInputService);
	}

	@Test
	void onApplicationReady_registersBPartnerDescriptor()
	{
		final DocumentDescriptorFactory documentDescriptors = Mockito.mock(DocumentDescriptorFactory.class);
		final BPartnerQuickInputService bpartnerQuickInputService = Mockito.mock(BPartnerQuickInputService.class);
		Mockito.when(bpartnerQuickInputService.getNewBPartnerWindowId())
				.thenReturn(Optional.of(AdWindowId.ofRepoId(123)));

		final NewRecordDescriptorsProvider provider = new NewRecordDescriptorsProvider(documentDescriptors, bpartnerQuickInputService);
		provider.registerBuiltInDescriptors();

		// The feature still works, just deferred: once the ApplicationReadyEvent handler runs, the built-in
		// C_BPartner new-record descriptor is registered (and thus resolvable at runtime).
		assertThat(provider.getNewRecordDescriptorOrNull(I_C_BPartner.Table_Name)).isNotNull();
	}
}
