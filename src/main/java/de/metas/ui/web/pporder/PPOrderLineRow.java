package de.metas.ui.web.pporder;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.view.DocumentViewCreateRequest;
import de.metas.ui.web.view.ForwardingDocumentView;
import de.metas.ui.web.view.IDocumentView;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.view.IDocumentViewsRepository;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ToString
public class PPOrderLineRow extends ForwardingDocumentView implements IPPOrderBOMLine
{
	public static final Builder builder(final IDocumentView delegate)
	{
		return new Builder(delegate);
	}

	public static final PPOrderLineRow cast(final IDocumentView viewRecord)
	{
		return (PPOrderLineRow)viewRecord;
	}

	private final boolean processed;
	private final int ppOrderId;
	private final int ppOrderBOMLineId;
	private final int ppOrderQtyId;

	private volatile String husToIssueViewId = null; // lazy

	private PPOrderLineRow(final Builder builder)
	{
		super(builder.delegate);

		processed = builder.processed;

		ppOrderId = builder.ppOrderId;
		ppOrderBOMLineId = builder.ppOrderBOMLineId;

		ppOrderQtyId = builder.ppOrderQtyId;
	}

	public int getPP_Order_ID()
	{
		return ppOrderId;
	}

	public int getPP_Order_BOMLine_ID()
	{
		return ppOrderBOMLineId;
	}

	public int getPP_Order_Qty_ID()
	{
		return ppOrderQtyId;
	}

	@Override
	public boolean isProcessed()
	{
		return processed;
	}

	@Override
	public PPOrderLineType getType()
	{
		return PPOrderLineType.cast(super.getType());
	}

	public String getBOMLineType()
	{
		return (String)getDelegate().getFieldValueAsJson(IPPOrderBOMLine.COLUMNNAME_BOMType);
	}

	public JSONLookupValue getProduct()
	{
		return (JSONLookupValue)getDelegate().getFieldValueAsJson(IPPOrderBOMLine.COLUMNNAME_M_Product_ID);
	}

	public int getM_Product_ID()
	{
		final JSONLookupValue product = getProduct();
		return product == null ? -1 : product.getKeyAsInt();
	}

	public JSONLookupValue getUOM()
	{
		return (JSONLookupValue)getDelegate().getFieldValueAsJson(IPPOrderBOMLine.COLUMNNAME_C_UOM_ID);
	}

	public int getC_UOM_ID()
	{
		final JSONLookupValue uom = getUOM();
		return uom == null ? -1 : uom.getKeyAsInt();
	}

	public I_C_UOM getC_UOM()
	{
		final int uomId = getC_UOM_ID();
		return InterfaceWrapperHelper.load(uomId, I_C_UOM.class);
	}

	public BigDecimal getQty()
	{
		return (BigDecimal)getDelegate().getFieldValueAsJson(IPPOrderBOMLine.COLUMNNAME_Qty);
	}

	public BigDecimal getQtyPlan()
	{
		return (BigDecimal)getDelegate().getFieldValueAsJson(IPPOrderBOMLine.COLUMNNAME_QtyPlan);
	}

	public boolean isReceipt()
	{
		return getType().canReceive();
	}

	public boolean isIssue()
	{
		return getType().canIssue();
	}

	@Override
	public List<PPOrderLineRow> getIncludedDocuments()
	{
		return getIncludedDocuments(PPOrderLineRow.class);
	}

	@Override
	public boolean hasIncludedView()
	{
		return isIssue();
	}

	@Override
	public IDocumentViewSelection getCreateIncludedView(final IDocumentViewsRepository viewsRepo)
	{
		if (isIssue())
		{
			return getCreateHUsToIssueView(viewsRepo);
		}
		else
		{
			throw new EntityNotFoundException("Line " + this + " does not have an included view");
		}
	}

	private synchronized IDocumentViewSelection getCreateHUsToIssueView(final IDocumentViewsRepository viewsRepo)
	{
		if (husToIssueViewId != null)
		{
			final IDocumentViewSelection existingView = viewsRepo.getViewIfExists(husToIssueViewId);
			if (existingView != null)
			{
				return existingView;
			}
		}

		//
		// Create new view
		final IDocumentViewSelection newView = createHUsToIssueView(viewsRepo);
		husToIssueViewId = newView.getViewId();
		return newView;
	}

	private IDocumentViewSelection createHUsToIssueView(final IDocumentViewsRepository viewsRepo)
	{
		// TODO: move it to DAO/Repository
		// TODO: rewrite the whole shit, this is just prototyping
		final List<Integer> huIdsToAvailableToIssue = Services.get(IHandlingUnitsDAO.class)
				.createHUQueryBuilder()
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				//
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active)
				.addOnlyWithProductId(getM_Product_ID())
				.setOnlyTopLevelHUs()
				//
				.createQuery()
				.listIds();

		return viewsRepo.createView(DocumentViewCreateRequest.builder(WEBUI_HU_Constants.WEBUI_HU_Window_ID, JSONViewDataType.grid)
				// .setParentViewId(parentViewId) // TODO: implement
				.setFilterOnlyIds(huIdsToAvailableToIssue)
				.build());
	}

	public static final class Builder
	{
		private final IDocumentView delegate;
		private int ppOrderId;
		private int ppOrderBOMLineId;
		private int ppOrderQtyId;
		private boolean processed = false;

		private Builder(final IDocumentView delegate)
		{
			this.delegate = delegate;
		}

		public PPOrderLineRow build()
		{
			return new PPOrderLineRow(this);
		}

		public Builder ppOrder(final int ppOrderId)
		{
			this.ppOrderId = ppOrderId;
			ppOrderBOMLineId = -1;
			return this;
		}

		public Builder ppOrderBOMLineId(final int ppOrderId, final int ppOrderBOMLineId)
		{
			this.ppOrderId = ppOrderId;
			this.ppOrderBOMLineId = ppOrderBOMLineId;
			return this;
		}

		public Builder ppOrderQtyId(final int ppOrderQtyId)
		{
			this.ppOrderQtyId = ppOrderQtyId;
			return this;
		}

		public Builder processed(final boolean processed)
		{
			this.processed = processed;
			return this;
		}
	}

}
