package de.metas.ui.web.order.products_proposal.process;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.ui.web.order.products_proposal.view.BPartnerProductsProposalViewFactory;
import de.metas.ui.web.view.CreateViewRequest;

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

public class WEBUI_BPartner_ProductsProposal_Launcher extends WEBUI_ProductsProposal_Launcher_Template
{
	@Autowired
	private BPartnerProductsProposalViewFactory productsProposalViewFactory;

	@Override
	protected CreateViewRequest createViewRequest(final TableRecordReference recordRef)
	{
		return productsProposalViewFactory.createViewRequest(recordRef);
	}
}
