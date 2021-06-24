package de.metas.bpartner.impexp;

import static org.assertj.core.api.Assertions.assertThat;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_I_BPartner;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * A collection of bpartner import test helpers.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
/* package */class BPartnerImportTestHelper
{
	public static void assertImported(final I_I_BPartner ibpartner)
	{
		assertBPartnerImported(ibpartner);
		assertLocationImported(ibpartner);
		assertContactImported(ibpartner);
	}

	public static void assertBPartnerImported(final I_I_BPartner ibpartner)
	{
		final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);

		final I_C_BPartner bpartner = partnerDAO.getById(BPartnerId.ofRepoId(ibpartner.getC_BPartner_ID()));
		assertThat(bpartner).isNotNull();
		assertThat(bpartner.getValue()).isNotNull();
		assertThat(bpartner.getName()).isNotNull();
		assertThat(bpartner.getValue()).isEqualTo(ibpartner.getBPValue());
		assertThat(bpartner.getAD_Language()).isEqualTo(ibpartner.getAD_Language());
	}

	public static void assertLocationImported(final I_I_BPartner ibpartner)
	{

		final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);

		final I_C_BPartner_Location bplocation = partnerDAO.getBPartnerLocationByIdEvenInactive(BPartnerLocationId.ofRepoId(ibpartner.getC_BPartner_ID(), ibpartner.getC_BPartner_Location_ID()));
		assertThat(bplocation).isNotNull();
		assertThat(bplocation.getC_Location_ID()).isGreaterThan(0);
		assertThat(bplocation.isBillToDefault()).isEqualTo(ibpartner.isBillToDefault());
		assertThat(bplocation.isBillTo()).isEqualTo(BPartnerLocationImportHelper.extractIsBillTo(ibpartner));
		assertThat(bplocation.isShipToDefault()).isEqualTo(ibpartner.isShipToDefault());
		assertThat(bplocation.isShipTo()).isEqualTo(BPartnerLocationImportHelper.extractIsShipTo(ibpartner));
		assertThat(bplocation.getPhone()).isEqualTo(ibpartner.getPhone());
		assertThat(bplocation.getPhone2()).isEqualTo(ibpartner.getPhone2());
		assertThat(bplocation.getFax()).isEqualTo(ibpartner.getFax());
		assertThat(bplocation.getGLN()).isEqualTo(ibpartner.getGLN());

		// Location
		final I_C_Location location = bplocation.getC_Location();
		assertThat(location).isNotNull();
		assertThat(location.getAddress1()).isEqualTo(ibpartner.getAddress1());
		assertThat(location.getAddress2()).isEqualTo(ibpartner.getAddress2());
		assertThat(location.getAddress3()).isEqualTo(ibpartner.getAddress3());
		assertThat(location.getAddress4()).isEqualTo(ibpartner.getAddress4());
		assertThat(location.getCity()).isEqualTo(ibpartner.getCity());
		assertThat(location.getC_Region_ID()).isEqualTo(ibpartner.getC_Region_ID());
		assertThat(location.getC_Country_ID()).isEqualTo(ibpartner.getC_Country_ID());
	}

	public static void assertContactImported(final I_I_BPartner ibpartner)
	{
		final IUserDAO userDAO = Services.get(IUserDAO.class);

		final I_AD_User user = userDAO.getById(UserId.ofRepoIdOrNull(ibpartner.getAD_User_ID()));
		assertThat(user).isNotNull();
		assertThat(user.getLastname()).isEqualTo(ibpartner.getLastname());
		assertThat(user.getFirstname()).isEqualTo(ibpartner.getFirstname());
		assertThat(user.isShipToContact_Default()).isEqualTo(ibpartner.isShipToContact_Default());
		assertThat(user.isBillToContact_Default()).isEqualTo(ibpartner.isBillToContact_Default());
	}

}
