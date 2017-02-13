package org.adempiere.bpartner.callout;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;

import de.metas.document.documentNo.IDocumentNoBuilder;
import de.metas.document.documentNo.IDocumentNoBuilderFactory;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class C_BPartner_TabCallout extends TabCalloutAdapter
{
	@Override
	public void onNew(ICalloutRecord calloutRecord)
	{
		final I_C_BPartner bpartner = calloutRecord.getModel(I_C_BPartner.class);

		final String documentNo = Services.get(IDocumentNoBuilderFactory.class)
				.forTableName(I_C_BPartner.Table_Name, bpartner.getAD_Client_ID(), bpartner.getAD_Org_ID())
				.setDocumentModel(bpartner)
				.setFailOnError(false)
				.setUsePreliminaryDocumentNo(true)
				.build();

		if (documentNo == IDocumentNoBuilder.NO_DOCUMENTNO)
		{
			return;
		}

		bpartner.setValue(documentNo);
	}
}
