package de.metas.contracts.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.document.engine.IDocument;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/*
 * #%L
 * de.metas.contracts
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

public class FlatrateDAOTest extends ContractsTestBase
{
	@Test
	public void test()
	{
		final Timestamp now = SystemTime.asTimestamp();

		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		InterfaceWrapperHelper.save(product);

		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		InterfaceWrapperHelper.save(bpartner);

		final I_C_Flatrate_Conditions fc = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class);
		fc.setDocStatus(IDocument.STATUS_Completed);
		fc.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_QualityBasedInvoicing);
		InterfaceWrapperHelper.save(fc);

		final I_C_Flatrate_Matching matching = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Matching.class);
		matching.setC_Flatrate_Conditions(fc);
		matching.setM_Product_ID(product.getM_Product_ID());
		InterfaceWrapperHelper.save(matching);

		final I_C_Flatrate_Term ft = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class);
		ft.setDocStatus(IDocument.STATUS_Completed);
		ft.setC_Flatrate_Conditions(fc);
		ft.setBill_BPartner_ID(bpartner.getC_BPartner_ID());
		ft.setStartDate(TimeUtil.addDays(now, -10));
		ft.setEndDate(TimeUtil.addDays(now, 10));
		InterfaceWrapperHelper.save(ft);

		final List<I_C_Flatrate_Term> result = new FlatrateDAO().retrieveTerms(
				Env.getCtx(),
				Env.getOrgId(),
				bpartner.getC_BPartner_ID(),
				now,
				0,
				product.getM_Product_ID(),
				0,
				ITrx.TRXNAME_ThreadInherited);

		assertThat(result.size(), is(1));
		assertThat(result.get(0), is(ft));
	}
}
