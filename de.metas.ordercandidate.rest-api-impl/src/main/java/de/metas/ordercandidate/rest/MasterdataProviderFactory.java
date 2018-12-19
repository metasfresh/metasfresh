package de.metas.ordercandidate.rest;

import javax.annotation.Nullable;

import org.springframework.stereotype.Service;

import lombok.Builder;

/*
 * #%L
 * de.metas.ordercandidate.rest-api-impl
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

@Service
public class MasterdataProviderFactory
{
	private BPartnerMasterDataProvider bpartnerMasterDataProvider;
	private PermissionService permissionService;
	private ProductMasterDataProvider productMasterDataProvider;

	public MasterdataProviderFactory()
	{
		this(null/* bpartnerMasterDataProvider */, null/* productMasterDataProvider */, null/* permissionService */);
	}

	@Builder
	private MasterdataProviderFactory(
			@Nullable final BPartnerMasterDataProvider bpartnerMasterDataProvider,
			@Nullable final ProductMasterDataProvider productMasterDataProvider,
			@Nullable final PermissionService permissionService)
	{
		this.bpartnerMasterDataProvider = bpartnerMasterDataProvider;
		this.productMasterDataProvider = productMasterDataProvider;
		this.permissionService = permissionService;
	}

	public MasterdataProvider createMasterDataProvider()
	{
		return MasterdataProvider.builder()
				.bpartnerMasterDataProvider(bpartnerMasterDataProvider)
				.permissionService(permissionService)
				.productMasterDataProvider(productMasterDataProvider)
				.build();
	}
}
