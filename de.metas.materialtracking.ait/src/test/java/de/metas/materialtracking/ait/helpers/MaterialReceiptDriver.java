package de.metas.materialtracking.ait.helpers;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_InOut;

import de.metas.adempiere.model.I_M_Product;
import de.metas.document.engine.IDocument;
import de.metas.materialtracking.model.I_M_Material_Tracking;

/*
 * #%L
 * de.metas.materialtracking.ait
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class MaterialReceiptDriver
{
	public static void setupMaterialReceipts(
			final String documentNo,
			final int line,
			final String nameCountry,
			final String strDate,
			final String valueProduct,
			final String strQty,
			final String strTuQty,
			final String lotMaterialTracking) throws ParseException
	{
		final Timestamp movementDate = Helper.parseTimestamp(strDate);
		final I_M_Product product = Helper.retrieveExisting(valueProduct, I_M_Product.class);
		final I_C_BPartner_Location bpl = BPartnerDriver.getCreateBPartnerLocation(nameCountry);

		I_M_InOut io = Helper.retrieveExistingOrNull(documentNo, I_M_InOut.class);
		if (io == null)
		{
			io = InterfaceWrapperHelper.newInstance(I_M_InOut.class, Helper.context);
			io.setMovementDate(movementDate);
			io.setC_BPartner_Location(bpl);
			io.setDocStatus(IDocument.STATUS_Completed);
			InterfaceWrapperHelper.save(io);
			Helper.storeFirstTime(documentNo, io);
		}
		else
		{
			assertThat(io.getMovementDate(), is(movementDate));
			assertThat(io.getC_BPartner_Location(), is(bpl));
		}

		final I_M_Material_Tracking mt = Helper.retrieveExisting(lotMaterialTracking, I_M_Material_Tracking.class);

		final de.metas.handlingunits.model.I_M_InOutLine iol = InterfaceWrapperHelper.newInstance(de.metas.handlingunits.model.I_M_InOutLine.class, Helper.context);
		iol.setLine(line);
		iol.setM_InOut(io);
		iol.setM_Product(product);
		iol.setC_UOM(product.getC_UOM());
		iol.setMovementQty(new BigDecimal(strQty));
		// y
		iol.setQtyEnteredTU(new BigDecimal(strTuQty));
		iol.setM_Material_Tracking(mt);
		iol.setIsPackagingMaterial(false);
		InterfaceWrapperHelper.save(iol);

		Helper.link(mt, iol);
		Helper.storeFirstTime(documentNo + "_" + line, iol);
	}
}
