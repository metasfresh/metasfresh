package de.metas.invoicecandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.aggregation.api.AbstractAggregationKeyBuilder;
import de.metas.aggregation.api.AggregationKey;
import de.metas.aggregation.api.IAggregationFactory;
import de.metas.aggregation.api.IAggregationKeyBuilder;
import de.metas.aggregation.model.X_C_Aggregation;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.invoicecandidate.api.IInvoiceAggregationFactory;
import de.metas.invoicecandidate.model.I_C_BPartner;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

/**
 * Aggregation Key Builder for {@link I_C_Invoice_Candidate}s.
 * <p>
 * This implementation gets the actual {@link IAggregationKeyBuilder} from {@link IAggregationFactory} using the settings from {@link I_C_Invoice_Candidate}, and delegates all the work to it.
 */
public class ForwardingICAggregationKeyBuilder extends AbstractAggregationKeyBuilder<I_C_Invoice_Candidate>
{
	// services
	protected final transient IInvoiceAggregationFactory invoiceAggregationFactory = Services.get(IInvoiceAggregationFactory.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	private final String aggregationUsageLevel;

	public ForwardingICAggregationKeyBuilder(final String aggregationUsageLevel)
	{
		Check.assumeNotEmpty(aggregationUsageLevel, "aggregationUsageLevel not empty");
		this.aggregationUsageLevel = aggregationUsageLevel;
	}

	@Override
	public final String getTableName()
	{
		return I_C_Invoice_Candidate.Table_Name;
	}

	@Override
	public final List<String> getDependsOnColumnNames()
	{
		// NOTE: we cannot know which are the column names until we load the actual aggregation key builder
		throw new UnsupportedOperationException();
	}

	@Override
	public final AggregationKey buildAggregationKey(final I_C_Invoice_Candidate ic)
	{
		final IAggregationKeyBuilder<I_C_Invoice_Candidate> icAggregationKeyBuilder = getDelegate(ic);
		if (icAggregationKeyBuilder == null)
		{
			return AggregationKey.NULL;
		}

		return icAggregationKeyBuilder.buildAggregationKey(ic);
	}

	/**
	 * Finds the actual {@link IAggregationKeyBuilder} to be used, based on invoice candidate's settings.
	 *
	 * @param ic
	 * @return actual {@link IAggregationKeyBuilder} to be used
	 */
	protected IAggregationKeyBuilder<I_C_Invoice_Candidate> getDelegate(final I_C_Invoice_Candidate ic)
	{
		final I_C_BPartner bpartner = bpartnerDAO.getById(ic.getBill_BPartner_ID(), I_C_BPartner.class);
		if (bpartner == null)
		{
			return null;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(ic);

		final Optional<IAggregationKeyBuilder<I_C_Invoice_Candidate>> customAggregationOpt = getCustomAggregationIfAny(ic, ctx);

		if (customAggregationOpt.isPresent())
		{
			return customAggregationOpt.get();
		}

		final OrderId prepayOrderId = OrderId.ofRepoIdOrNull(ic.getC_Order_ID());
		if (prepayOrderId != null
				&& orderBL.isPrepay(prepayOrderId)
				&& X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header.equals(aggregationUsageLevel))
		{
			return invoiceAggregationFactory.getPrepayOrderAggregationKeyBuilder(ctx);
		}

		final boolean isSOTrx = ic.isSOTrx();
		return invoiceAggregationFactory.getAggregationKeyBuilder(ctx, bpartner, isSOTrx, aggregationUsageLevel);
	}

	@Override
	public final boolean isSame(final I_C_Invoice_Candidate ic1, final I_C_Invoice_Candidate ic2)
	{
		final AggregationKey aggregationKey1 = buildAggregationKey(ic1);
		if (aggregationKey1 == null)
		{
			return false;
		}

		final AggregationKey aggregationKey2 = buildAggregationKey(ic2);
		if (aggregationKey2 == null)
		{
			return false;
		}

		final boolean same = Objects.equals(aggregationKey1, aggregationKey2);
		return same;
	}

	@NonNull
	private Optional<IAggregationKeyBuilder<I_C_Invoice_Candidate>> getCustomAggregationIfAny(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final Properties ctx)
	{
		final AdTableId tableId = AdTableId.ofRepoIdOrNull(ic.getAD_Table_ID());

		if(tableId == null)
		{
			return Optional.empty();
		}

		final TableRecordReference icReferencedRecord = TableRecordReference.of(ic.getAD_Table_ID(), ic.getRecord_ID());

		//dev-note: ugly workaround to avoid circular dependency for I_S_Issue
		if (icReferencedRecord.getTableName().equals("S_Issue"))
		{
			return Optional.of(invoiceAggregationFactory.getIssueAggregationKeyBuilder(ctx, aggregationUsageLevel));
		}

		// dev-note: custom aggregation for harvesting details
		final CalendarId harvestingCalendarId = CalendarId.ofRepoIdOrNull(ic.getC_Harvesting_Calendar_ID());
		final YearId harvestingYearId = YearId.ofRepoIdOrNull(ic.getHarvesting_Year_ID());
		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(ic.getM_Warehouse_ID());
		if (harvestingCalendarId != null && harvestingYearId != null && warehouseId != null)
		{
			return Optional.ofNullable(invoiceAggregationFactory.getHarvestingAggregationKeyBuilder(ctx));
		}

		return Optional.empty();
	}
}
