package de.metas.aggregation.model;

/*
 * #%L
 * de.metas.aggregation
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.Env;

import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Builder class for {@link I_C_Aggregation}
 *
 * @author tsa
 *
 */
public class C_Aggregation_Builder
{
	private IContextAware context = new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_None);
	private String name;
	private boolean isDefault;
	private boolean isDefaultSO;
	private boolean isDefaultPO;
	private int adTableId = -1;
	private String aggregationUsageLevel;

	private final List<C_AggregationItem_Builder> itemBuilders = new ArrayList<>();

	public I_C_Aggregation build()
	{
		final I_C_Aggregation aggregation = InterfaceWrapperHelper.newInstance(I_C_Aggregation.class, context);
		aggregation.setName(name);
		aggregation.setAD_Table_ID(adTableId);
		aggregation.setIsDefault(isDefault);
		aggregation.setIsDefaultPO(isDefaultPO);
		aggregation.setIsDefaultSO(isDefaultSO);

		Check.assumeNotEmpty(aggregationUsageLevel, "aggregationUsageLevel not empty");
		aggregation.setAggregationUsageLevel(aggregationUsageLevel);

		InterfaceWrapperHelper.save(aggregation);

		for (final C_AggregationItem_Builder itemBuilder : itemBuilders)
		{
			itemBuilder.setC_Aggregation(aggregation);
			itemBuilder.build();
		}

		return aggregation;
	}

	public C_Aggregation_Builder setContext(final IContextAware context)
	{
		this.context = context;
		return this;
	}

	public C_Aggregation_Builder setName(final String name)
	{
		this.name = name;
		return this;
	}

	public C_Aggregation_Builder setAD_Table_ID(final int adTableId)
	{
		this.adTableId = adTableId;
		return this;
	}

	public C_Aggregation_Builder setAD_Table_ID(final String tableName)
	{
		this.adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		return this;
	}

	public C_Aggregation_Builder setIsDefault(final boolean isDefault)
	{
		this.isDefault = isDefault;
		return this;
	}

	public C_Aggregation_Builder setIsDefaultSO(final boolean isDefaultSO)
	{
		this.isDefaultSO = isDefaultSO;
		return this;
	}

	public C_Aggregation_Builder setIsDefaultPO(final boolean isDefaultPO)
	{
		this.isDefaultPO = isDefaultPO;
		return this;
	}

	public C_Aggregation_Builder setAggregationUsageLevel(final String aggregationUsageLevel)
	{
		this.aggregationUsageLevel = aggregationUsageLevel;
		return this;
	}

	public C_AggregationItem_Builder newItem()
	{
		if (adTableId <= 0)
		{
			throw new AdempiereException("Before creating a new item, a adTableId needs to be set to the builder")
					.appendParametersToMessage()
					.setParameter("C_Aggregation_Builder", this);
		}
		final C_AggregationItem_Builder itemBuilder = new C_AggregationItem_Builder(this, AdTableId.ofRepoId(adTableId));
		itemBuilders.add(itemBuilder);
		return itemBuilder;
	}
}
