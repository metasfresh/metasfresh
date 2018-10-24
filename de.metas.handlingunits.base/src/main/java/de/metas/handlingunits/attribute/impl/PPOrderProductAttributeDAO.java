package de.metas.handlingunits.attribute.impl;

import java.math.BigDecimal;
import java.util.Collection;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.Util.ArrayKey;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.slf4j.Logger;

import de.metas.handlingunits.attribute.IPPOrderProductAttributeDAO;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_PP_Order_ProductAttribute;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.logging.LogManager;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;

public class PPOrderProductAttributeDAO implements IPPOrderProductAttributeDAO
{
	private static final transient Logger logger = LogManager.getLogger(PPOrderProductAttributeDAO.class);

	@Override
	public List<I_PP_Order_ProductAttribute> retrieveProductAttributesForPPOrder(final int ppOrderId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_PP_Order_ProductAttribute.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_PP_Order_ProductAttribute.COLUMNNAME_PP_Order_ID, ppOrderId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_PP_Order_ProductAttribute.class);
	}

	private Stream<I_PP_Order_ProductAttribute> streamProductAttributesForHUAttributes(final int ppOrderId, final Collection<I_M_HU_Attribute> huAttributes)
	{
		if (huAttributes.isEmpty())
		{
			return Stream.empty();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_PP_Order_ProductAttribute> queryBuilder = queryBL.createQueryBuilder(I_PP_Order_ProductAttribute.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_PP_Order_ProductAttribute.COLUMNNAME_PP_Order_ID, ppOrderId)
				.addOnlyActiveRecordsFilter();

		final ICompositeQueryFilter<I_PP_Order_ProductAttribute> huFilters = queryBuilder.addCompositeQueryFilter().setJoinOr();
		huAttributes.forEach(huAttribute -> {
			huFilters.addCompositeQueryFilter().setJoinAnd()
					.addEqualsFilter(I_PP_Order_ProductAttribute.COLUMNNAME_M_HU_ID, huAttribute.getM_HU_ID())
					.addEqualsFilter(I_PP_Order_ProductAttribute.COLUMNNAME_M_Attribute_ID, huAttribute.getM_Attribute_ID());
		});

		return queryBuilder
				.create()
				.stream(I_PP_Order_ProductAttribute.class);

	}

	@Override
	public void addPPOrderProductAttributes(final I_PP_Cost_Collector costCollector, final List<I_M_HU_Attribute> huAttributes)
	{
		if (huAttributes.isEmpty())
		{
			return;
		}

		final I_PP_Order ppOrder = costCollector.getPP_Order();
		addPPOrderProductAttributes(ppOrder, huAttributes, ppOrderAttribute -> ppOrderAttribute.setPP_Cost_Collector(costCollector));
	}

	@Override
	public void addPPOrderProductAttributesFromIssueCandidate(final I_PP_Order_Qty issueCandidate, final List<I_M_HU_Attribute> huAttributes)
	{
		if (huAttributes.isEmpty())
		{
			return;
		}

		final I_PP_Order ppOrder = issueCandidate.getPP_Order();
		addPPOrderProductAttributes(ppOrder, huAttributes, ppOrderAttribute -> {
			// ppOrderAttribute.setPP_Order_Qty(issueCandidate); // TODO: shall we add it?
		});
	}

	private void addPPOrderProductAttributes(final I_PP_Order ppOrder, final List<I_M_HU_Attribute> huAttributes, final Consumer<I_PP_Order_ProductAttribute> beforeSave)
	{
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		
		//
		// Fetch the existing PP_Order_ProductAttribute records matching given huAttributes (matched by M_HU_ID/M_Attribute_ID)
		final Map<ArrayKey, I_PP_Order_ProductAttribute> existingPPOrderAttributes = streamProductAttributesForHUAttributes(ppOrder.getPP_Order_ID(), huAttributes)
				.collect(GuavaCollectors.toHashMapByKey(ppOrderAttribute -> ArrayKey.of(ppOrderAttribute.getM_HU_ID(), ppOrderAttribute.getM_Attribute_ID())));

		//
		// Iterate and create/update one PP_Order_ProductAttribute for each HU_Attribute
		for (final I_M_HU_Attribute huAttribute : huAttributes)
		{
			final I_M_Attribute attribute = attributesRepo.getAttributeById(huAttribute.getM_Attribute_ID());

			//
			// Find existing PP_Order_ProductAttribute or create a new one
			final ArrayKey key = ArrayKey.of(huAttribute.getM_HU_ID(), huAttribute.getM_Attribute_ID());
			final I_PP_Order_ProductAttribute ppOrderAttribute = existingPPOrderAttributes.computeIfAbsent(key, k -> {
				final I_PP_Order_ProductAttribute ppOrderAttributeNew = InterfaceWrapperHelper.newInstance(I_PP_Order_ProductAttribute.class);
				ppOrderAttributeNew.setAD_Org_ID(ppOrder.getAD_Org_ID());
				ppOrderAttributeNew.setPP_Order(ppOrder);
				ppOrderAttributeNew.setM_Attribute(attribute);
				ppOrderAttributeNew.setM_HU_ID(huAttribute.getM_HU_ID()); // provenance HU
				return ppOrderAttributeNew;
			});

			//
			// Set Value/ValueNumber
			final String attributeValueType = attribute.getAttributeValueType();
			if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
			{
				ppOrderAttribute.setValue(null);

				final BigDecimal valueNumber = InterfaceWrapperHelper.getValueOrNull(huAttribute, I_M_HU_Attribute.COLUMNNAME_ValueNumber);
				ppOrderAttribute.setValueNumber(valueNumber);
			}
			else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
			{
				// TODO: introduce PP_Order_ProductAttribute.ValueDate
				continue;
			}
			else if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(attributeValueType)
					|| X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
			{
				ppOrderAttribute.setValue(huAttribute.getValue());
				ppOrderAttribute.setValueNumber(null);
			}
			else
			{
				logger.warn("AttributeValueType {} not supported for {} / {}", attributeValueType, attribute, huAttribute);
				continue;
			}

			// Execute beforeSave custom code
			beforeSave.accept(ppOrderAttribute);

			// save it
			InterfaceWrapperHelper.save(ppOrderAttribute);
		}
	}

	@Override
	public void deactivateForCostCollector(final int costCollectorId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		queryBL.createQueryBuilder(I_PP_Order_ProductAttribute.class)
				.addEqualsFilter(org.eevolution.model.I_PP_Order_ProductAttribute.COLUMNNAME_PP_Cost_Collector_ID, costCollectorId)
				.addOnlyActiveRecordsFilter()
				.create()
				.updateDirectly()
				.addSetColumnValue(org.eevolution.model.I_PP_Order_ProductAttribute.COLUMNNAME_IsActive, false) // deactivate all for cost collector
				.execute();

	}

	@Override
	public void deleteForHU(final int ppOrderId, final int huId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		queryBL.createQueryBuilder(I_PP_Order_ProductAttribute.class)
				.addEqualsFilter(I_PP_Order_ProductAttribute.COLUMNNAME_PP_Order_ID, ppOrderId)
				.addEqualsFilter(I_PP_Order_ProductAttribute.COLUMNNAME_M_HU_ID, huId)
				.addOnlyActiveRecordsFilter()
				.create()
				.deleteDirectly();
	}
}
