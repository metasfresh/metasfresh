/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.process;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.process.ProcessInfo;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CBPartnerUpdateMemoTest
{
	private static final int RECORD_ID = 100;

	@BeforeAll
	static void init()
	{
		AdempiereTestHelper.get().init();
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);
	}

	@BeforeEach
	public void createTestData()
	{
		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setC_BPartner_ID(RECORD_ID);
		bpartner.setMemo("New Memo");
		save(bpartner);
	}

	@Test
	public void testMemoUpdate() throws Exception
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

		// Set up the process
		final C_BPartner_Update_Memo c_BPartner_Update_Memo_Process = new C_BPartner_Update_Memo();

		c_BPartner_Update_Memo_Process.init(ProcessInfo.builder()
													.setRecord(I_C_BPartner.Table_Name, RECORD_ID)
													.setCtx(Env.getCtx())
													.build());

		final Field memoField = C_BPartner_Update_Memo.class.getDeclaredField("p_Memo");
		memoField.setAccessible(true);
		memoField.set(c_BPartner_Update_Memo_Process, "Updated Memo");

		// Execute the process
		final String result = c_BPartner_Update_Memo_Process.doIt();

		// Verify that the memo was updated
		final I_C_BPartner updatedBPartner = bpartnerDAO.getById(BPartnerId.ofRepoId(RECORD_ID));
		assertEquals("Updated Memo", updatedBPartner.getMemo());
		assertEquals("@Success@", result);
		System.out.println("Memo updated to: " + updatedBPartner.getMemo());
	}
}
