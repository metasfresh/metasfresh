package org.eevolution;

import org.compiere.acct.Doc_HRProcess;
import org.eevolution.model.I_HR_Process;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

import de.metas.acct.doc.AcctDocProviderTemplate;

/*
 * #%L
 * de.metas.adempiere.libero.liberoHR
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

@Component
public class PayrollAcctDocProvider extends AcctDocProviderTemplate
{
	public PayrollAcctDocProvider()
	{
		super(ImmutableMap.<String, AcctDocFactory> builder()
				.put(I_HR_Process.Table_Name, Doc_HRProcess::new)
				.build());
	}
}
