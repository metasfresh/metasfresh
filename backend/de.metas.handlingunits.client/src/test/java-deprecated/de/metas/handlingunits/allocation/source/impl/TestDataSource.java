package de.metas.handlingunits.allocation.source.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Attribute;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.api.IHUCapacityDefinition;
import de.metas.handlingunits.attribute.strategy.impl.NullAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.impl.NullSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.impl.SumAggregationStrategy;
import de.metas.handlingunits.document.IDataSource;
import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.document.IHUReference;
import de.metas.handlingunits.document.impl.HUDocumentListDataSource;
import de.metas.handlingunits.document.impl.PlainHUDocument;
import de.metas.handlingunits.document.impl.PlainHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;

public class TestDataSource implements IDataSource
{
	private final HUTestHelper helper;

	public I_M_Attribute attr_CountryMadeIn;
	public I_M_Attribute attr_Volume;
	public I_M_Attribute attr_FragileSticker;
	public I_M_Attribute attr_Description;
	public I_M_Attribute attr_TrackingNo;

	public I_M_HU_PI huDefBlister;
	private I_M_HU_PI_Item_Product huDefBlister_Tomato;
	private I_M_HU_PI_Item_Product huDefBlister_Tomato2;
	private I_M_HU_PI_Item_Product huDefBlister_Salad;
	private I_M_HU_PI_Item_Product huDefBlister_Salad3;

	public I_M_HU_PI huDefIFCO;
	public I_M_HU_PI huDefPalet;
	public I_M_HU_PI huDefMixedPalet;

	public I_C_UOM uomEach;

	public I_M_Product pBlister;
	public I_M_HU_PackingMaterial pmBlister;

	public I_M_Product pIFCO;
	public I_M_HU_PackingMaterial pmIFCO;

	public I_M_Product pPalet;
	public I_M_HU_PackingMaterial pmPalet;

	public I_M_Product pMixedPalet;
	public I_M_HU_PackingMaterial pmMixedPalet;

	public I_M_Product pTomato;
	public I_M_Product pTomato2;
	public I_M_Product pTomato3;

	public I_M_Product pSalad;
	public I_M_Product pSalad2;
	public I_M_Product pSalad3;

	private List<IHUDocument> documents;

	public TestDataSource()
	{
		super();
		helper = new HUTestHelper();

		documents = createHUDocuments();
	}

	private void setupMasterData()
	{
		// Products
		{
			uomEach = helper.createUOM("Ea");

			pBlister = helper.createProduct("Blister-Product", uomEach); // Blisters are a kind of packing material which contain material items
			pmBlister = helper.createPackingMaterial("Blister-PM", pBlister);

			pIFCO = helper.createProduct("IFCO-Product", uomEach); // IFCOs are a kind of packing material which allow Blisters
			pmIFCO = helper.createPackingMaterial("IFCO-PM", pIFCO);

			pPalet = helper.createProduct("Palet-Product", uomEach); // Pallets are a kind of packing material which allow IFCOs
			pmPalet = helper.createPackingMaterial("Palet-PM", pPalet);

			pMixedPalet = helper.createProduct("Mixed-Palet-Product", uomEach); // Pallets are a kind of packing material which allow IFCOs and Blisters
			pmMixedPalet = helper.createPackingMaterial("Mixed-Palet-PM", pMixedPalet);

			pTomato = helper.createProduct("Tomato-Product", uomEach);
			pTomato2 = helper.createProduct("Tomato-Product2", uomEach);
			pTomato3 = helper.createProduct("Tomato-Product3", uomEach);
			pSalad = helper.createProduct("Salad-Product", uomEach);
			pSalad2 = helper.createProduct("Salad-Product2", uomEach);
			pSalad3 = helper.createProduct("Salad-Product3", uomEach);
		}

		// HU Attributes
		{
			attr_CountryMadeIn = helper.createAttribute("Made In", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
			helper.createAttributeListValue(attr_CountryMadeIn, "RO", "Romania");
			helper.createAttributeListValue(attr_CountryMadeIn, "DE", "Germany");
			helper.createAttributeListValue(attr_CountryMadeIn, "HU", "Hungary");

			attr_Volume = helper.createAttribute("Volume", X_M_Attribute.ATTRIBUTEVALUETYPE_Number, true);
			attr_FragileSticker = helper.createAttribute("Fragile", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, false);
			attr_Description = helper.createAttribute("Description", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
			attr_TrackingNo = helper.createAttribute("TrackingNo", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		}

		// Handling Units Definition
		{
			huDefBlister = helper.createHuDefinition("Blister");
			{
				// create included tomato definitions to blister
				final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefBlister);
				huDefBlister_Tomato = helper.assignProduct(itemMA, pTomato, BigDecimal.TEN, uomEach);
				huDefBlister_Tomato2 = helper.assignProduct(itemMA, pTomato2, new BigDecimal("12"), uomEach);
				helper.assignProduct(itemMA, pTomato3, new BigDecimal("13"), uomEach);

				// create included salad definitions to blister
				final I_M_HU_PI_Item itemMA2 = helper.createHU_PI_Item_Material(huDefBlister);
				huDefBlister_Salad = helper.assignProduct(itemMA2, pSalad, BigDecimal.TEN, uomEach);
				helper.assignProduct(itemMA2, pSalad2, new BigDecimal("12"), uomEach);
				huDefBlister_Salad3 = helper.assignProduct(itemMA2, pSalad3, new BigDecimal("13"), uomEach);

				helper.createHU_PI_Item_PackingMaterial(huDefBlister, pmBlister);

				helper.createAttribute(huDefBlister, attr_TrackingNo, X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation);
				helper.createAttribute(huDefBlister, attr_Volume,
						X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp,
						NullSplitterStrategy.class,
						SumAggregationStrategy.class);
			}

			huDefIFCO = helper.createHuDefinition("IFCO");
			{
				helper.createHU_PI_Item_IncludedHU(huDefIFCO, huDefBlister, BigDecimal.TEN);
				helper.createHU_PI_Item_PackingMaterial(huDefIFCO, pmIFCO);

				helper.createAttribute(huDefIFCO, attr_CountryMadeIn, X_M_HU_PI_Attribute.PROPAGATIONTYPE_InheritValue);
				helper.createAttribute(huDefIFCO, attr_TrackingNo, X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation);
				helper.createAttribute(huDefIFCO, attr_Volume,
						X_M_HU_PI_Attribute.PROPAGATIONTYPE_InheritValue,
						NullSplitterStrategy.class,
						SumAggregationStrategy.class);
			}

			huDefPalet = helper.createHuDefinition("Palet");
			{
				helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, new BigDecimal("2"));
				helper.createHU_PI_Item_PackingMaterial(huDefPalet, pmPalet);

				helper.createAttribute(huDefPalet, attr_CountryMadeIn, X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown, NullSplitterStrategy.class, NullAggregationStrategy.class);
				helper.createAttribute(huDefPalet, attr_FragileSticker, X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation);
				helper.createAttribute(huDefPalet, attr_TrackingNo, X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation);
				helper.createAttribute(huDefPalet, attr_Volume,
						X_M_HU_PI_Attribute.PROPAGATIONTYPE_InheritValue,
						NullSplitterStrategy.class,
						SumAggregationStrategy.class);
			}

			huDefMixedPalet = helper.createHuDefinition("Mixed Palet");
			{
				helper.createHU_PI_Item_IncludedHU(huDefMixedPalet, huDefIFCO, new BigDecimal("2"));
				helper.createHU_PI_Item_IncludedHU(huDefMixedPalet, huDefBlister, new BigDecimal("6"));
				helper.createHU_PI_Item_PackingMaterial(huDefMixedPalet, pmMixedPalet);

				helper.createAttribute(huDefMixedPalet, attr_CountryMadeIn, X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown, NullSplitterStrategy.class, NullAggregationStrategy.class);
				helper.createAttribute(huDefMixedPalet, attr_FragileSticker, X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation);
				helper.createAttribute(huDefMixedPalet, attr_TrackingNo, X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation);
				helper.createAttribute(huDefMixedPalet, attr_Volume,
						X_M_HU_PI_Attribute.PROPAGATIONTYPE_InheritValue,
						NullSplitterStrategy.class,
						SumAggregationStrategy.class);
			}
		}
	}

	private List<IHUDocument> createHUDocuments()
	{
		setupMasterData();

		// create HU Documents
		final List<IHUDocument> documents = new ArrayList<IHUDocument>();
		{
			final List<IHUDocumentLine> documentLines = new ArrayList<IHUDocumentLine>();
			{
				final IHUDocumentLine documentLine = createHUDocumentLine(
						pTomato, BigDecimal.TEN,
						huDefBlister_Tomato,
						pmPalet,
						huDefPalet
						);
				documentLines.add(documentLine);
			}
			{
				final IHUDocumentLine documentLine = createHUDocumentLine(
						pSalad, BigDecimal.TEN,
						huDefBlister_Salad,
						pmPalet,
						huDefPalet
						);
				documentLines.add(documentLine);
			}
			{
				final IHUDocumentLine documentLine = createHUDocumentLine(
						pSalad3, new BigDecimal("13"),
						huDefBlister_Salad3,
						pmPalet,
						huDefPalet
						);
				documentLines.add(documentLine);
			}
			final IHUDocument source = createHUDocumentLine(Collections.unmodifiableList(documentLines));
			documents.add(source);
		}
		{
			final List<IHUDocumentLine> documentLines = new ArrayList<IHUDocumentLine>();
			{
				final IHUDocumentLine documentLine = createHUDocumentLine(
						pTomato2, new BigDecimal("12"),
						huDefBlister_Tomato2,
						pmPalet,
						huDefPalet
						);
				documentLines.add(documentLine);
			}
			final IHUDocument document = createHUDocumentLine(Collections.unmodifiableList(documentLines));
			documents.add(document);
		}

		return Collections.unmodifiableList(documents);
	}

	private int nextSourceId = 1;

	protected IHUDocument createHUDocumentLine(final List<IHUDocumentLine> sourceLines)
	{
		final int id = nextSourceId;
		nextSourceId++;

		final String displayName = "source#" + id;
		return new PlainHUDocument(displayName, Collections.unmodifiableList(sourceLines));
	}

	protected IHUDocumentLine createHUDocumentLine(
			final I_M_Product product, final BigDecimal qty,
			I_M_HU_PI_Item_Product suggestedItemProduct,
			I_M_HU_PackingMaterial suggestedPackingMaterial,
			I_M_HU_PI suggestedPI)
	{
		final PlainHUDocumentLine documentLine = new PlainHUDocumentLine(product, qty, uomEach);
		documentLine.setSuggestedItemProduct(suggestedItemProduct);
		documentLine.setSuggestedPackingMaterial(suggestedPackingMaterial);
		documentLine.setSuggestedPI(suggestedPI);
		return documentLine;
	}

	@Override
	public List<IHUReference> getHandlingUnits()
	{
		return HUDocumentListDataSource.getAssignedHandlingUnits(getHUDocuments());
	}

	@Override
	public List<IHUDocument> getHUDocuments()
	{
		return documents;
	}

	@Override
	public void process()
	{
	}

	@Override
	public List<IHUCapacityDefinition> getTargetCapacities()
	{
		return Collections.emptyList();
	}
}
