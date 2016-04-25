package de.metas.materialtracking.ait.setup;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import de.metas.materialtracking.ait.helpers.MaterialReceiptDriver;
import de.metas.materialtracking.ait.helpers.MaterialTrackingDriver;
import de.metas.materialtracking.ait.helpers.PurchaseOrderDriver;

/*
 * #%L
 * de.metas.materialtracking.ait
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
@RunWith(ConcordionRunner.class)
public class SetupMaterialTrackingFixture
{
	public void setupMaterialTracking(
			final String lot,
			final String nameFC,
			final String valueProduct,
			final String strValidFrom,
			final String strValidTo) throws Throwable
	{
		MaterialTrackingDriver.setupMaterialTracking(lot, nameFC, valueProduct, strValidFrom, strValidTo);
	}

	public void setupPurchaseOrders(
			final String nameCountry,
			final String strDate,
			final String valueProduct,
			final String strQty,
			final String strTuQty,
			final String lotMaterialTracking) throws Throwable
	{
		PurchaseOrderDriver.setupPurchaseOrders(nameCountry, strDate, valueProduct, strQty, strTuQty, lotMaterialTracking);
	}


	public void setupMaterialReceipts(
			final String documentNo,
			final int line,
			final String nameCountry,
			final String strDate,
			final String valueProduct,
			final String strQty,
			final String strTuQty,
			final String lotMaterialTracking) throws Throwable
	{
		MaterialReceiptDriver.setupMaterialReceipts(documentNo, line, nameCountry, strDate, valueProduct, strQty, strTuQty, lotMaterialTracking);
	}


}