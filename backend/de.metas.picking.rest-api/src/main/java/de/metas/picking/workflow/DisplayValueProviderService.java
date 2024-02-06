/*
 * #%L
 * de.metas.picking.rest-api
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.picking.workflow;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.organization.IOrgDAO;
import de.metas.picking.config.MobileUIPickingUserProfile;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisplayValueProviderService
{
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IDocumentLocationBL documentLocationBL;

	public DisplayValueProvider newDisplayValueProvider(@NonNull final MobileUIPickingUserProfile profile)
	{
		return DisplayValueProvider.builder()
				.partnerDAO(partnerDAO)
				.orgDAO(orgDAO)
				.documentLocationBL(documentLocationBL)
				//
				.profile(profile)
				//
				.build();
	}
}
