package de.metas.util.lang;

import org.junit.Test;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class Fresh_All_RepoId_Classes_Test
{
	@Test
	public void test()
	{
		new ClasspathRepoIdAwaresTester()
				.skip(de.metas.bpartner.BPartnerLocationId.class)
				.skip(de.metas.bpartner.BPartnerContactId.class)
				.skip(de.metas.bpartner.BPartnerBankAccountId.class)
				//
				.skip(de.metas.phonecall.PhonecallSchemaVersionId.class)
				.skip(de.metas.phonecall.PhonecallSchemaVersionLineId.class)
				//
				.skip(org.adempiere.warehouse.LocatorId.class)
				//
				.skip(de.metas.customs.CustomsInvoiceLineId.class)
				.skip(de.metas.shipment.ShipmentDeclarationLineId.class)
				//
				.skip(org.eevolution.api.PPOrderRoutingActivityId.class)
				.skip(de.metas.material.planning.pporder.PPRoutingActivityId.class)
				//
				.test();
	}
}
