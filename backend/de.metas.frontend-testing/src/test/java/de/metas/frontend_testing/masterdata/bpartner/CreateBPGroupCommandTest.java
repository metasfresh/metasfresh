package de.metas.frontend_testing.masterdata.bpartner;

import de.metas.bpartner.BPGroupId;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.frontend-testing
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

public class CreateBPGroupCommandTest
{
	private MasterdataContext context;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		context = new MasterdataContext();
	}

	@Test
	public void execute_createsGroupAndRegistersIdentifier()
	{
		final JsonBPGroupResponse response = CreateBPGroupCommand.builder()
				.context(context)
				.request(JsonBPGroupRequest.builder().name("Wholesale").build())
				.identifier(Identifier.ofString("grpA"))
				.build()
				.execute();

		final I_C_BP_Group record = InterfaceWrapperHelper.load(response.getId(), I_C_BP_Group.class);
		assertThat(record.isActive()).isTrue();
		assertThat(record.getName()).startsWith("Wholesale");
		assertThat(record.getValue()).startsWith("Wholesale");
		assertThat(context.getId(Identifier.ofString("grpA"), BPGroupId.class)).isEqualTo(response.getId());
	}

	@Test
	public void execute_withoutName_usesUniqueIdentifier_andTwoRunsDoNotCollide()
	{
		final JsonBPGroupResponse r1 = createGroup("grpX");
		final JsonBPGroupResponse r2 = createGroup("grpY");

		assertThat(r1.getId()).isNotEqualTo(r2.getId());
		assertThat(InterfaceWrapperHelper.load(r1.getId(), I_C_BP_Group.class).getName()).startsWith("grpX");
	}

	private JsonBPGroupResponse createGroup(final String identifier)
	{
		return CreateBPGroupCommand.builder()
				.context(context)
				.request(JsonBPGroupRequest.builder().build())
				.identifier(Identifier.ofString(identifier))
				.build()
				.execute();
	}
}
