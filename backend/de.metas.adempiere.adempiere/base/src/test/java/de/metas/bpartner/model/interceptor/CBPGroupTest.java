/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.bpartner.model.interceptor;

import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CBPGroupTest
{
	private static final ClientAndOrgId REGULAR_ORG_1 = ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.MAIN);
	private static final ClientAndOrgId REGULAR_ORG_2 = ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.ofRepoId(2));
	private static final ClientAndOrgId ANY_ORG = ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.ANY);
	private C_BP_Group bpGroupMI;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		bpGroupMI = new C_BP_Group();
	}

	@Test
	public void testSameOrgDefault()
	{
		final I_C_BP_Group org1Default = createGroup(true, REGULAR_ORG_1);
		final I_C_BP_Group org2Default = createGroup(true, REGULAR_ORG_2);
		final I_C_BP_Group anyOrgDefault = createGroup(true, ANY_ORG);
		final I_C_BP_Group org1NonDefault = createGroup(false, REGULAR_ORG_1);

		org1NonDefault.setIsDefault(true);
		bpGroupMI.unsetPreviousDefault(org1NonDefault);
		InterfaceWrapperHelper.save(org1NonDefault);

		//previous default is no longer
		assertDefault(org1Default, false);
		//other org is still default
		assertDefault(org2Default, true);
		//any org is still default
		assertDefault(anyOrgDefault, true);
		//new group is now default
		assertDefault(org1NonDefault, true);
	}

	@Test
	public void testAnyOrgDefaultOnly()
	{
		final I_C_BP_Group org1NonDefault1 = createGroup(false, REGULAR_ORG_1);
		final I_C_BP_Group anyOrgDefault = createGroup(true, ANY_ORG);
		final I_C_BP_Group org1NonDefault2 = createGroup(false, REGULAR_ORG_1);

		org1NonDefault2.setIsDefault(true);
		bpGroupMI.unsetPreviousDefault(org1NonDefault2);
		InterfaceWrapperHelper.save(org1NonDefault2);

		// org1NonDefault1 is still not default
		assertDefault(org1NonDefault1, false);
		// anyOrgDefault is still default
		assertDefault(anyOrgDefault, true);
		// org1NonDefault2 is now default
		assertDefault(org1NonDefault2, true);
	}

	@Test
	public void testNoDefault()
	{
		final I_C_BP_Group org1NonDefault1 = createGroup(false, REGULAR_ORG_1);
		final I_C_BP_Group anyOrgNonDefault = createGroup(false, ANY_ORG);
		final I_C_BP_Group org1NonDefault2 = createGroup(false, REGULAR_ORG_1);

		org1NonDefault2.setIsDefault(true);
		bpGroupMI.unsetPreviousDefault(org1NonDefault2);
		InterfaceWrapperHelper.save(org1NonDefault2);

		// org1NonDefault1 is still not default
		assertDefault(org1NonDefault1, false);
		// anyOrgDefault is still not default
		assertDefault(anyOrgNonDefault, false);
		// org1NonDefault2 is now default
		assertDefault(org1NonDefault2, true);
	}

	private void assertDefault(@NonNull final I_C_BP_Group group, final boolean expectedIsDefaultValue)
	{
		InterfaceWrapperHelper.refresh(group);
		Check.assumeEquals(expectedIsDefaultValue, group.isDefault());
	}

	private I_C_BP_Group createGroup(final boolean isDefault, final ClientAndOrgId clientAndOrgId)
	{
		final I_C_BP_Group group = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
		group.setIsDefault(isDefault);
		group.setAD_Org_ID(clientAndOrgId.getOrgId().getRepoId());
		InterfaceWrapperHelper.save(group);
		return group;
	}

}
