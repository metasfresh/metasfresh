package org.eevolution.api;

import de.metas.material.event.commons.AttributesKey;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributesTestHelper;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_M_Attribute;
import org.eevolution.model.I_PP_Order_Candidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * When candidates merged into one PP_Order carry <b>distinct</b> {@code M_AttributeSetInstance_ID} rows that
 * nevertheless hold <b>identical storage-relevant content</b>, the created PP_Order must keep that content
 * instead of nulling it out.
 * <p>
 * This test targets {@link PPOrderCreateRequestBuilder} directly, bypassing the candidate-aggregation-key
 * grouping mechanism ({@code PPOrderCandidateAggregationFactory}) which unconditionally includes the raw
 * {@code M_AttributeSetInstance_ID} in its default header aggregation key
 * ({@code PPOrderCandidateKeyValueHandler.getValues()}). That means two {@code PP_Order_Candidate}s with distinct
 * ASI ids (even with identical content) never reach the same {@link PPOrderCreateRequestBuilder} instance in the
 * real candidate-to-order flow unless a custom manufacturing-aggregation config is set up to exclude ASI from the
 * grouping key — not currently expressible via the cucumber step-defs. This focused unit test on the builder
 * exercises the exact aggregation logic under test without that dependency.
 */
class PPOrderCreateRequestBuilderTest
{
	private static final int WAREHOUSE_ID = 1000000;
	private static final int RESOURCE_ID = 1000001;
	private static final int PRODUCT_ID = 1000002;
	private static final Timestamp DATE_PROMISED = Timestamp.valueOf("2026-07-14 00:00:00");

	private AttributesTestHelper attributesTestHelper;
	private IAttributeSetInstanceBL attributeSetInstanceBL;
	private I_C_UOM uom;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		attributesTestHelper = new AttributesTestHelper();
		attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

		uom = newInstance(I_C_UOM.class);
		uom.setName("Each");
		uom.setStdPrecision(0);
		saveRecord(uom);
	}

	@Test
	void distinctButIdenticalContentASIs_areMergedIntoOneOrder_keepingTheAttribute()
	{
		// two DISTINCT M_AttributeSetInstance rows, both carrying the identical storage-relevant "Bio" content
		final I_M_Attribute attribute = attributesTestHelper.createM_Attribute("TestFlavor", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attribute.setIsStorageRelevant(true);
		saveRecord(attribute);
		final AttributeListValue bio = attributesTestHelper.createM_AttributeValue(attribute, "Bio");

		final AttributeSetInstanceId asi1 = createAsi(bio);
		final AttributeSetInstanceId asi2 = createAsi(bio);
		assertThat(asi1).as("sanity check: distinct ASI rows").isNotEqualTo(asi2);

		final I_PP_Order_Candidate candidate1 = newCandidate(asi1);
		final I_PP_Order_Candidate candidate2 = newCandidate(asi2);

		final PPOrderCreateRequestBuilder builder = new PPOrderCreateRequestBuilder();
		builder.addRecord(candidate1);
		builder.addRecord(candidate2);

		final PPOrderCreateRequest request = builder.build(Quantity.of(20, uom));

		final AttributesKey expectedAttributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(asi1).orElse(AttributesKey.NONE);
		final AttributesKey actualAttributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(request.getAttributeSetInstanceId()).orElse(AttributesKey.NONE);

		// CURRENT (buggy) behaviour: PPOrderCreateRequestBuilder aggregates the ASI by repo-id (Objects.equals on
		// AttributeSetInstanceId); asi1 != asi2 (distinct repo ids) -> nulled to AttributeSetInstanceId.NONE ->
		// actualAttributesKey resolves to AttributesKey.NONE, while expectedAttributesKey is the real {Bio} key.
		// This assertion therefore FAILS on current code (RED) and must PASS after the content-aware fix.
		assertThat(actualAttributesKey)
				.as("AttributeKeys")
				.isEqualTo(expectedAttributesKey);
	}

	@Test
	void genuinelyDifferentContentASIs_areMergedIntoOneOrder_withTheAttributeNulled()
	{
		// two DISTINCT M_AttributeSetInstance rows, carrying genuinely DIFFERENT storage-relevant content
		final I_M_Attribute attribute = attributesTestHelper.createM_Attribute("TestFlavor", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attribute.setIsStorageRelevant(true);
		saveRecord(attribute);
		final AttributeListValue bio = attributesTestHelper.createM_AttributeValue(attribute, "Bio");
		final AttributeListValue konventionell = attributesTestHelper.createM_AttributeValue(attribute, "Konventionell");

		final AttributeSetInstanceId asiBio = createAsi(bio);
		final AttributeSetInstanceId asiKonventionell = createAsi(konventionell);

		final I_PP_Order_Candidate candidate1 = newCandidate(asiBio);
		final I_PP_Order_Candidate candidate2 = newCandidate(asiKonventionell);

		final PPOrderCreateRequestBuilder builder = new PPOrderCreateRequestBuilder();
		builder.addRecord(candidate1);
		builder.addRecord(candidate2);

		final PPOrderCreateRequest request = builder.build(Quantity.of(20, uom));

		final AttributesKey actualAttributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(request.getAttributeSetInstanceId()).orElse(AttributesKey.NONE);

		// Regression guard (AC2, locked decision): a genuine content conflict stays nulled to NONE, both now
		// and after the content-aware fix — unlike the identical-content case above.
		assertThat(actualAttributesKey)
				.as("AttributeKeys")
				.isEqualTo(AttributesKey.NONE);
	}

	private AttributeSetInstanceId createAsi(final AttributeListValue value)
	{
		final I_M_AttributeSetInstance asi = newInstance(I_M_AttributeSetInstance.class);
		saveRecord(asi);
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());

		attributeSetInstanceBL.getCreateAttributeInstance(asiId, value);

		return asiId;
	}

	private I_PP_Order_Candidate newCandidate(final AttributeSetInstanceId asiId)
	{
		final I_PP_Order_Candidate candidate = newInstance(I_PP_Order_Candidate.class);
		candidate.setM_Warehouse_ID(WAREHOUSE_ID);
		candidate.setS_Resource_ID(RESOURCE_ID);
		candidate.setM_Product_ID(PRODUCT_ID);
		candidate.setDatePromised(DATE_PROMISED);
		candidate.setDateStartSchedule(DATE_PROMISED);
		candidate.setM_AttributeSetInstance_ID(asiId.getRepoId());
		return candidate;
	}
}
