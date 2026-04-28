package org.adempiere.util.trxConstraints.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.trxConstraints.api.ITrxConstraints;
import org.adempiere.util.trxConstraints.api.ITrxConstraintsBL;

import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrxConstraintsBLTest
{
	/**
	 * service under test
	 */
	private TrxConstraintsBL trxConstraintsBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		Services.registerService(ITrxConstraintsBL.class, new TrxConstraintsBL()
		{
			@Override
			protected boolean isDisabled()
			{
				return false;
			}
		});
		this.trxConstraintsBL = (TrxConstraintsBL)Services.get(ITrxConstraintsBL.class);
	}

	@Test
	public void test_saveRestore()
	{
		final ITrxConstraints constraints1 = trxConstraintsBL.getConstraints();
		constraints1.addAllowedTrxNamePrefix("Trx1");

		//
		// Copy to constraints2 and test
		trxConstraintsBL.saveConstraints();
		final ITrxConstraints constraints2 = trxConstraintsBL.getConstraints();
		Assertions.assertNotSame(constraints1, constraints2);
		Assertions.assertEquals(
				CollectionUtils.asSet("Trx1"), // expected
				constraints2.getAllowedTrxNamePrefixes(), // actual
				"Invalid AllowedTrxNamePrefixes for: " + constraints2
		);
		//
		constraints2.addAllowedTrxNamePrefix("Trx2");

		//
		// Copy to constraints3 and test
		trxConstraintsBL.saveConstraints();
		final ITrxConstraints constraints3 = trxConstraintsBL.getConstraints();
		Assertions.assertNotSame(constraints2, constraints3);
		Assertions.assertEquals(
				CollectionUtils.asSet("Trx1", "Trx2"), // expected
				constraints3.getAllowedTrxNamePrefixes(), // actual
				"Invalid AllowedTrxNamePrefixes for: " + constraints3
		);
		//
		constraints3.addAllowedTrxNamePrefix("Trx3");

		//
		// Restore to constraints2 and test
		{
			trxConstraintsBL.restoreConstraints();
			final ITrxConstraints constraints2Restored = trxConstraintsBL.getConstraints();
			Assertions.assertSame(constraints2, constraints2Restored);
			Assertions.assertEquals(
					CollectionUtils.asSet("Trx1", "Trx2"), // expected
					constraints2Restored.getAllowedTrxNamePrefixes(), // actual
					"Invalid AllowedTrxNamePrefixes for: " + constraints2Restored
			);
		}

		//
		// Restore to constraints1 and test
		{
			trxConstraintsBL.restoreConstraints();
			final ITrxConstraints constraints1Restored = trxConstraintsBL.getConstraints();
			Assertions.assertSame(constraints1, constraints1Restored);
			Assertions.assertEquals(CollectionUtils.asSet("Trx1"), // expected
					constraints1Restored.getAllowedTrxNamePrefixes(), // actual
					"Invalid AllowedTrxNamePrefixes for: " + constraints1Restored
			);
		}

		//
		// Restore to constraints1 and test (AGAIN)
		{
			trxConstraintsBL.restoreConstraints();
			final ITrxConstraints constraints1Restored = trxConstraintsBL.getConstraints();
			Assertions.assertSame(constraints1, constraints1Restored);
			Assertions.assertEquals(CollectionUtils.asSet("Trx1"), // expected
					constraints1Restored.getAllowedTrxNamePrefixes(), // actual
					"Invalid AllowedTrxNamePrefixes for: " + constraints1Restored
			);
		}

	}
}
