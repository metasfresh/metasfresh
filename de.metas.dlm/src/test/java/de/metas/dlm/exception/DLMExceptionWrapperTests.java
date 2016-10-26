package de.metas.dlm.exception;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/*
 * #%L
 * metasfresh-dlm
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DLMExceptionWrapperTests
{
	@Test(expected = RuntimeException.class)
	public void testInvalidString()
	{
		DLMReferenceExceptionWrapper.extractInfos("");
	}

	/**
	 * Verify that parsing the error message's HINT works and is also a bit lenient.
	 */
	@Test
	public void test()
	{
		assertOK("DLM_Referenced_Table_Name = c_payment;  DLM_Referencing_Table_Name = c_bankstatementline_ref; DLM_Referencig_Column_Name = c_payment_id;");
		assertOK("DLM_Referenced_Table_Name = c_payment_tbl DLM_Referencing_Table_Name=c_bankstatementline_ref; DLM_Referencig_Column_Name=c_payment_id;");
		assertOK("dlm_Referenced_Table_Name= c_payment   dlm_Referencing_Table_Name=c_bankstatementline_ref;dLm_Referencig_Column_Name=c_payment_id;");
		assertOK("DLM_Referenced_Table_Name = c_payment;DLM_Referencing_Table_Name=c_bankstatementline_ref  DLM_Referencig_Column_Name=c_payment_id;");
		assertOK("   DLM_Referenced_Table_Name = c_payment;DLM_Referencing_Table_Name=c_bankstatementline_ref_tbl,DLM_Referencig_Column_Name=c_payment_id");
		assertOK("; DLM_Referenced_Table_Name = c_payment;  DLM_Referencing_Table_Name=c_bankstatementline_ref,DLM_Referencig_Column_Name=c_payment_id   ");
	}

	private void assertOK(final String string)
	{
		final String[] infos = DLMReferenceExceptionWrapper.extractInfos(string);

		assertThat(infos[0], is("c_payment"));
		assertThat(infos[1], is("c_bankstatementline_ref"));
		assertThat(infos[2], is("c_payment_id"));
	}
}
