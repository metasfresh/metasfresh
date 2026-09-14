package org.adempiere.archive.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_AD_Archive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
public class ArchiveBLTest
{
	private IArchiveBL archiveBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		archiveBL = new ArchiveBL();
	}

	private I_AD_Archive newArchive()
	{
		return InterfaceWrapperHelper.newInstance(I_AD_Archive.class);
	}

	@Test
	public void isSuppressAutoPrint_defaultsToFalse_whenNeverSet()
	{
		assertThat(archiveBL.isSuppressAutoPrint(newArchive())).isFalse();
	}

	@Test
	public void setSuppressAutoPrint_roundTrips()
	{
		final I_AD_Archive archive = newArchive();

		archiveBL.setSuppressAutoPrint(archive, true);
		assertThat(archiveBL.isSuppressAutoPrint(archive)).isTrue();

		archiveBL.setSuppressAutoPrint(archive, false);
		assertThat(archiveBL.isSuppressAutoPrint(archive)).isFalse();
	}
}
