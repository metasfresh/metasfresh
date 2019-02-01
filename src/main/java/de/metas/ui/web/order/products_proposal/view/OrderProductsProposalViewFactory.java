package de.metas.ui.web.order.products_proposal.view;

import java.time.LocalDate;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.product.stats.BPartnerProductStatsService;
import de.metas.i18n.ITranslatableString;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.pricing.PriceListId;
import de.metas.process.IADProcessDAO;
import de.metas.ui.web.order.products_proposal.process.WEBUI_Order_ProductsProposal_Launcher;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import lombok.NonNull;

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

@ViewFactory(windowId = OrderProductsProposalViewFactory.WINDOW_ID_STRING)
public class OrderProductsProposalViewFactory extends ProductsProposalViewFactoryTemplate
{
	public static final String WINDOW_ID_STRING = "orderProductsProposal";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	private final BPartnerProductStatsService bpartnerProductStatsService;

	public OrderProductsProposalViewFactory(
			@NonNull final BPartnerProductStatsService bpartnerProductStatsService)
	{
		super(WINDOW_ID);

		this.bpartnerProductStatsService = bpartnerProductStatsService;
	}

	@Override
	protected ViewLayout createViewLayout(final ViewLayoutKey key)
	{
		final ITranslatableString caption = Services.get(IADProcessDAO.class)
				.retrieveProcessNameByClassIfUnique(WEBUI_Order_ProductsProposal_Launcher.class)
				.orElse(null);

		return ViewLayout.builder()
				.setWindowId(key.getWindowId())
				.setCaption(caption)
				.addElementsFromViewRowClass(ProductsProposalRow.class, key.getViewDataType())
				.allowViewCloseAction(ViewCloseAction.CANCEL)
				.allowViewCloseAction(ViewCloseAction.DONE)
				.build();
	}

	@Override
	protected ProductsProposalRowsLoader createRowsLoaderFromRecord(@NonNull final TableRecordReference recordRef)
	{
		final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);

		recordRef.assertTableName(I_C_Order.Table_Name);
		final OrderId orderId = OrderId.ofRepoId(recordRef.getRecord_ID());

		final I_C_Order orderRecord = ordersRepo.getById(orderId);
		final LocalDate date = TimeUtil.asLocalDate(orderRecord.getDatePromised());
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(orderRecord.getC_BPartner_ID());
		final PriceListId priceListId = PriceListId.ofRepoId(orderRecord.getM_PriceList_ID());
		final SOTrx soTrx = SOTrx.ofBoolean(orderRecord.isSOTrx());

		return ProductsProposalRowsLoader.builder()
				.bpartnerProductStatsService(bpartnerProductStatsService)
				.priceListId(priceListId)
				.date(date)
				.orderId(orderId)
				.bpartnerId(bpartnerId)
				.soTrx(soTrx)
				.build();
	}

	@Override
	protected void beforeViewClose(final ViewId viewId, final ViewCloseAction closeAction)
	{
		if (ViewCloseAction.DONE.equals(closeAction))
		{
			final ProductsProposalView view = getById(viewId);
			if (view.getOrderId() != null)
			{
				createOrderLines(view);
			}
		}
	}

	private void createOrderLines(final ProductsProposalView view)
	{
		OrderLinesFromProductProposalsProducer.builder()
				.orderId(view.getOrderId())
				.rows(view.getRowsWithQtySet())
				.build()
				.produce();
	}
}
