package de.metas.dunning.api.impl;

/*
 * #%L
 * de.metas.dunning
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.Assert;
import org.junit.Test;

import de.metas.dunning.DunningTestBase;
import de.metas.dunning.exception.DunningException;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.organization.OrgId;

public class DunningDAOTest extends DunningTestBase
{
	@SuppressWarnings("unused")
	@Test
	public void retrieveDunningByOrg()
	{
		// AD_Org_ID, IsDefault, IsActive
		final I_C_Dunning dunning_301_notDefault_active = createDunning(301, false, true);
		final I_C_Dunning dunning_301_notDefault_notActive = createDunning(301, false, false);
		final I_C_Dunning dunning_302_default_notActive = createDunning(302, true, false);
		final I_C_Dunning dunning_302_default_active = createDunning(302, true, true);

		Assert.assertEquals("Invalid Dunning for org=302", dunning_302_default_active, dao.retrieveDunningByOrg(OrgId.ofRepoId(302)));

		Assert.assertNull("Invalid Dunning for org=301 - no default shall be found", dao.retrieveDunningByOrg(OrgId.ofRepoId(301)));
	}

	@Test(expected = AdempiereException.class)
	public void retrieveDunningByOrg_InvalidOrgArgument()
	{
		dao.retrieveDunningByOrg(OrgId.ofRepoIdOrAny(-1));
	}

	@SuppressWarnings("unused")
	@Test(expected = AdempiereException.class)
	public void retrieveDunningByOrg_MoreThenOneDefaultFound()
	{
		final I_C_Dunning dunning_303_default_notActive = createDunning(303, true, false);
		final I_C_Dunning dunning_303_default_active = createDunning(303, true, true);
		final I_C_Dunning dunning_303_default_active_2 = createDunning(303, true, true);

		dao.retrieveDunningByOrg(OrgId.ofRepoId(303));
	}

	private I_C_Dunning createDunning(int adOrgId, boolean isDefault, boolean isActive)
	{
		final I_C_Dunning dunning = createDunning("Dunning (Org:" + adOrgId + ", default:" + isDefault + ", active:" + isActive + ")");
		dunning.setAD_Org_ID(adOrgId);
		dunning.setIsDefault(isDefault);
		dunning.setIsActive(isActive);
		InterfaceWrapperHelper.save(dunning);
		return dunning;
	}
}
