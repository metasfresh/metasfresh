package de.metas.printing.interceptor;

/*
 * #%L
 * de.metas.printing.base
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

import de.metas.bpartner.service.BPartnerPrintFormatRepository;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_PrintFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

class C_BP_PrintFormatTest
{
	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		Services.get(IModelInterceptorRegistry.class)
				.addModelInterceptor(new C_BP_PrintFormat(new BPartnerPrintFormatRepository()));
	}

	private static I_C_BP_PrintFormat newPrintFormat()
	{
		final I_C_BP_PrintFormat printFormat = newInstance(I_C_BP_PrintFormat.class);
		// non-zero SeqNo so the seqno interceptor short-circuits and this test stays focused on the copies-override
		printFormat.setSeqNo(10);
		return printFormat;
	}

	private static Integer getDocumentCopiesOverrideRaw(final I_C_BP_PrintFormat printFormat)
	{
		return InterfaceWrapperHelper.getValueOrNull(printFormat, I_C_BP_PrintFormat.COLUMNNAME_DocumentCopies_Override);
	}

	@Test
	void newRecord_copiesOverride0_storedAsNull()
	{
		final I_C_BP_PrintFormat printFormat = newPrintFormat();
		printFormat.setDocumentCopies_Override(0);
		save(printFormat);

		assertThat(getDocumentCopiesOverrideRaw(printFormat)).isNull();
	}

	@Test
	void newRecord_copiesOverridePositive_kept()
	{
		final I_C_BP_PrintFormat printFormat = newPrintFormat();
		printFormat.setDocumentCopies_Override(3);
		save(printFormat);

		assertThat(getDocumentCopiesOverrideRaw(printFormat)).isEqualTo(3);
	}

	@Test
	void changedRecord_copiesOverrideSetTo0_storedAsNull()
	{
		final I_C_BP_PrintFormat printFormat = newPrintFormat();
		printFormat.setDocumentCopies_Override(3);
		save(printFormat);
		assertThat(getDocumentCopiesOverrideRaw(printFormat)).isEqualTo(3);

		printFormat.setDocumentCopies_Override(0);
		save(printFormat);

		assertThat(getDocumentCopiesOverrideRaw(printFormat)).isNull();
	}
}
