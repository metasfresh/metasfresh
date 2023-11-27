package de.metas.ui.web.order.products_proposal.process;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.products_proposal.view.BasePLVProductsProposalViewFactory;
import de.metas.ui.web.order.products_proposal.view.ProductsProposalView;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * #%L
 * metasfresh-webui-api
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

public class WEBUI_ProductsProposal_ShowProductsToAddFromBasePriceList extends ProductsProposalViewBasedProcess
{
	private static final AdMessageKey MSG_MissingBasePriceListVersion = AdMessageKey.of("WEBUI_Missing_Base_PriceList_Version");

	@Autowired
	private BasePLVProductsProposalViewFactory basePLVProductsProposalViewFactory;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getView().getBasePriceListVersionId().isPresent())
		{
			final ITranslatableString msg = msgBL.getTranslatableMsgText(MSG_MissingBasePriceListVersion);
			return ProcessPreconditionsResolution.reject(msg);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ProductsProposalView basePriceListView = basePLVProductsProposalViewFactory.createView(getView());

		afterCloseOpenView(basePriceListView.getViewId());

		return MSG_OK;
	}
}
