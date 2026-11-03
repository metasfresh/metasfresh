/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.model.interceptor;

import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

public class C_BPartner_LocationTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new C_BPartner_Location());
	}

	@Test
	public void newRecord_trailingSpace_isTrimmed()
	{
		final I_C_BPartner_Location bpLocation = newLocation("4000071000007 ");
		save(bpLocation);
		refresh(bpLocation);
		assertThat(bpLocation.getGLN()).isEqualTo("4000071000007");
	}

	@Test
	public void newRecord_leadingAndTrailingWhitespace_isTrimmed()
	{
		final I_C_BPartner_Location bpLocation = newLocation(" \t4000071000007\n ");
		save(bpLocation);
		refresh(bpLocation);
		assertThat(bpLocation.getGLN()).isEqualTo("4000071000007");
	}

	@Test
	public void changedRecord_trailingSpace_isTrimmed()
	{
		final I_C_BPartner_Location bpLocation = newLocation("4000071000007");
		save(bpLocation);

		bpLocation.setGLN("4000072000006 ");
		save(bpLocation);
		refresh(bpLocation);
		assertThat(bpLocation.getGLN()).isEqualTo("4000072000006");
	}

	@Test
	public void blankGLN_becomesNull()
	{
		final I_C_BPartner_Location bpLocation = newLocation("   ");
		save(bpLocation);
		refresh(bpLocation);
		assertThat(bpLocation.getGLN()).isNull();
	}

	@Test
	public void cleanGLN_isUnchanged()
	{
		final I_C_BPartner_Location bpLocation = newLocation("4000071000007");
		save(bpLocation);
		refresh(bpLocation);
		assertThat(bpLocation.getGLN()).isEqualTo("4000071000007");
	}

	private static I_C_BPartner_Location newLocation(final String gln)
	{
		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
		bpLocation.setGLN(gln);
		return bpLocation;
	}
}
