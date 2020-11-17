/*
 * #%L
 * de.metas.adempiere.libero.libero
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

package org.eevolution.callout;

import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.util.Services;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.eevolution.model.I_PP_Product_BOM;

public class PP_Product_BOM_TabCallout extends TabCalloutAdapter
{
	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_PP_Product_BOM request = calloutRecord.getModel(I_PP_Product_BOM.class);

		final String documentNo = Services.get(IDocumentNoBuilderFactory.class)
				.forTableName(I_PP_Product_BOM.Table_Name, request.getAD_Client_ID(), request.getAD_Org_ID())
				.setDocumentModel(request)
				.setFailOnError(false)
				.setUsePreliminaryDocumentNo(true)
				.build();

		if (documentNo == IDocumentNoBuilder.NO_DOCUMENTNO)
		{
			return;
		}

		request.setDocumentNo(documentNo);
	}
}
