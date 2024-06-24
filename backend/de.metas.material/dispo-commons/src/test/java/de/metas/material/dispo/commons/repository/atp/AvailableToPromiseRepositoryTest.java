package de.metas.material.dispo.commons.repository.atp;

import de.metas.bpartner.BPartnerId;
import de.metas.material.commons.attributes.clasifiers.BPartnerClassifier;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseMultiQuery.AvailableToPromiseMultiQueryBuilder;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_ATP_QueryResult;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.EventTestHelper;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import org.adempiere.mm.attributes.keys.AttributesKeyPattern;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static de.metas.material.event.EventTestHelper.ATTRIBUTE_SET_INSTANCE_ID;
import static de.metas.material.event.EventTestHelper.BEFORE_BEFORE_NOW;
import static de.metas.material.event.EventTestHelper.BEFORE_NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

@ExtendWith(AdempiereTestWatcher.class)
public class AvailableToPromiseRepositoryTest
{
	private static final BPartnerId BPARTNER_ID_1 = BPartnerId.ofRepoId(10);
	private static final BPartnerId BPARTNER_ID_2 = BPartnerId.ofRepoId(20);
	private static final AttributesKey STORAGE_ATTRIBUTES_KEY = AttributesKey.ofAttributeValueIds(1, 2);

	private AvailableToPromiseRepository availableToPromiseRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		availableToPromiseRepository = new AvailableToPromiseRepository();
	}

	/**
	 * The stock record without bpartnerId is created after, so its Qty is not contained in the stock record with bpartner; therefore we count both.
	 */
	@Test
	public void retrieveAvailableStock_for_material_descriptor()
	{
		createStockRecordWithBPartner(BPARTNER_ID_1, BEFORE_NOW);
		createStockRecordWithBPartner(null, BEFORE_NOW); // belongs to "any" bpartner
		createStockRecordWithBPartner(BPARTNER_ID_2, BEFORE_NOW); // belongs to an unrelated bPartner

		final MaterialDescriptor materialDescriptor = createMaterialDescriptor();
		assertThat(materialDescriptor.getCustomerId()).isEqualTo(BPARTNER_ID_1); // guard

		final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery.forDescriptorAndAllPossibleBPartnerIds(materialDescriptor);
		assertThat(query.getQueries()).hasSize(2); // guard

		final BigDecimal result = availableToPromiseRepository.retrieveAvailableStockQtySum(query);

		assertThat(result).isEqualByComparingTo("20");
	}

	/**
	 * The stock record without bpartnerId is created first, but it's date is after the one with bPartnerId.
	 * So its Qty is not contained in the stock record with bpartner; therefore we count both
	 */
	@Test
	public void retrieveAvailableStock_for_material_descriptor_2()
	{
		createStockRecordWithBPartner(null, BEFORE_NOW); // belongs to "any" bpartner
		createStockRecordWithBPartner(BPARTNER_ID_1, BEFORE_BEFORE_NOW);
		createStockRecordWithBPartner(BPARTNER_ID_2, BEFORE_NOW); // belongs to an unrelated bPartner

		final MaterialDescriptor materialDescriptor = createMaterialDescriptor();

		final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery.forDescriptorAndAllPossibleBPartnerIds(materialDescriptor);

		final BigDecimal result = availableToPromiseRepository.retrieveAvailableStockQtySum(query);

		assertThat(result).isEqualByComparingTo("20");
	}

	/**
	 * The stock record without bpartnerId is created first, so it's qty is already included in the stock record with bpartner.
	 * Therefore we only count the stock record with bpartner.
	 */
	@Test
	public void retrieveAvailableStock_for_material_descriptor_3()
	{
		createStockRecordWithBPartner(null, BEFORE_NOW); // belongs to "any" bpartner
		createStockRecordWithBPartner(BPARTNER_ID_1, BEFORE_NOW);
		createStockRecordWithBPartner(BPARTNER_ID_2, BEFORE_NOW); // belongs to an unrelated bPartner

		final MaterialDescriptor materialDescriptor = createMaterialDescriptor();
		final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery.forDescriptorAndAllPossibleBPartnerIds(materialDescriptor);

		final BigDecimal result = availableToPromiseRepository.retrieveAvailableStockQtySum(query);

		assertThat(result).isEqualByComparingTo(TEN);
	}

	/**
	 * If the query has no bpartnerId, then only records with no bpartnerId may be counted.
	 */
	@Test
	public void retrieveAvailableStock_for_material_descriptor_4()
	{
		createStockRecordWithBPartner(null, BEFORE_NOW); // belongs to "any" bpartner
		createStockRecordWithBPartner(BPARTNER_ID_1, BEFORE_NOW);
		createStockRecordWithBPartner(BPARTNER_ID_2, BEFORE_NOW); // belongs to an unrelated bPartner

		final MaterialDescriptor materialDescriptor = createMaterialDescriptor().withCustomerId(null);
		final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery.of(AvailableToPromiseQuery.forMaterialDescriptor(materialDescriptor));

		final BigDecimal result = availableToPromiseRepository.retrieveAvailableStockQtySum(query);

		assertThat(result).isEqualByComparingTo(TEN);
	}

	/**
	 * The stock record "for any bpartner" is created first, but it's date is after the one with {@code BPARTNER_ID}.
	 * So its qty is not contained within the stock record with {@code {@code BPARTNER_ID}}.
	 * Therefore for {@code BPARTNER_ID} we count both.
	 * However, for {@code BPARTNER_ID + 10}, we do not count the "for any bpartner"-record, because it's assumed to be included in {@code BPARTNER_ID + 10}'s record.
	 */
	@Test
	public void retrieveAvailableStock_differentBPartners_addToPredefinedBuckets()
	{
		retrieveAvailableStock_differentBPartners_performTest(true);
	}

	@Test
	public void retrieveAvailableStock_differentBPartners_doNot_addToPredefinedBuckets()
	{
		retrieveAvailableStock_differentBPartners_performTest(false);
	}

	private void retrieveAvailableStock_differentBPartners_performTest(final boolean addToPredefinedBuckets)
	{
		createStockRecordWithBPartner(null, BEFORE_NOW); // belongs to "any" bpartner
		createStockRecordWithBPartner(BPARTNER_ID_1, BEFORE_BEFORE_NOW);
		createStockRecordWithBPartner(BPARTNER_ID_2, BEFORE_NOW);

		final AvailableToPromiseMultiQueryBuilder multiQueryBuilder = AvailableToPromiseMultiQuery
				.builder()
				.addToPredefinedBuckets(addToPredefinedBuckets);

		final AvailableToPromiseQuery query1 = AvailableToPromiseQuery
				.builder()
				.productId(PRODUCT_ID)
				.storageAttributesKeyPattern(AttributesKeyPatternsUtil.ofAttributeKey(STORAGE_ATTRIBUTES_KEY))
				.bpartner(BPartnerClassifier.specific(BPARTNER_ID_1))
				.build();
		multiQueryBuilder.query(query1);

		final AvailableToPromiseQuery query2 = AvailableToPromiseQuery
				.builder()
				.productId(PRODUCT_ID)
				.storageAttributesKeyPattern(AttributesKeyPatternsUtil.ofAttributeKey(STORAGE_ATTRIBUTES_KEY))
				.bpartner(BPartnerClassifier.specific(BPARTNER_ID_2))
				.build();
		multiQueryBuilder.query(query2);

		final AvailableToPromiseMultiQuery multipQuery = multiQueryBuilder.build();

		final AvailableToPromiseResult result = availableToPromiseRepository.retrieveAvailableStock(multipQuery);

		assertThat(result).isNotNull();

		final List<AvailableToPromiseResultGroup> resultGroups = result.getResultGroups();
		assertThat(resultGroups).hasSize(2);

		assertThat(resultGroups)
				.allSatisfy(group -> assertThat(group.getProductId().getRepoId()).isEqualTo(PRODUCT_ID));

		assertThat(resultGroups)
				.filteredOn(group -> group.getBpartner().equals(BPartnerClassifier.specific(BPARTNER_ID_1)))
				.hasSize(1)
				.allSatisfy(group -> assertThat(group.getQty()).isEqualByComparingTo("20"));

		assertThat(resultGroups)
				.filteredOn(group -> group.getBpartner().equals(BPartnerClassifier.specific(BPARTNER_ID_2)))
				.hasSize(1)
				.allSatisfy(group -> assertThat(group.getQty()).isEqualByComparingTo(TEN));
	}

	@Test
	public void retrieveAvailableStock_differentStorageAttributesKeys_addToPredefinedBuckets()
	{
		boolean addToPredefinedBuckets = true;
		final AvailableToPromiseMultiQuery multiQuery = retrieveAvailableStock_differentStorageAttributesKeys_performTest(addToPredefinedBuckets);

		final AvailableToPromiseResult result = availableToPromiseRepository.retrieveAvailableStock(multiQuery);
		assertThat(result.getResultGroups()).hasSize(1); // there is just one predefined bucket
		assertThat(result.getResultGroups().get(0).getQty()).isEqualByComparingTo("30");
	}

	/**
	 * @task https://github.com/metasfresh/metasfresh/issues/4342
	 */
	@Test
	public void retrieveAvailableStock_differentStorageAttributesKeys_doNot_addToPredefinedBuckets()
	{
		boolean addToPredefinedBuckets = false;
		final AvailableToPromiseMultiQuery multiQuery = retrieveAvailableStock_differentStorageAttributesKeys_performTest(addToPredefinedBuckets);

		final AvailableToPromiseResult result = availableToPromiseRepository.retrieveAvailableStock(multiQuery);
		assertThat(result.getResultGroups())
				.hasSize(3) // there was no predefined bucket, and we have 3 ATP records with different attributes keys
				.allMatch(group -> group.getQty().compareTo(TEN) == 0);
	}

	private AvailableToPromiseMultiQuery retrieveAvailableStock_differentStorageAttributesKeys_performTest(boolean addToPredefinedBuckets)
	{
		createStockRecordWithProduct(PRODUCT_ID, AttributesKey.NONE, BEFORE_NOW);
		createStockRecordWithProduct(PRODUCT_ID, AttributesKey.ofAttributeValueIds(1000000), BEFORE_NOW);
		createStockRecordWithProduct(PRODUCT_ID, AttributesKey.ofAttributeValueIds(1000007), BEFORE_NOW);

		final AvailableToPromiseQuery query = AvailableToPromiseQuery.builder()
				.bpartner(BPartnerClassifier.specific(BPARTNER_ID_1)) // shall not matter since all 3 records are not assigned to a particular customer
				.productId(PRODUCT_ID)
				.storageAttributesKeyPattern(AttributesKeyPattern.ALL)
				.build();

		final AvailableToPromiseMultiQuery multiQuery = AvailableToPromiseMultiQuery.builder()
				.addToPredefinedBuckets(addToPredefinedBuckets)
				.query(query)
				.build();

		final BigDecimal resultQtySum = availableToPromiseRepository.retrieveAvailableStockQtySum(multiQuery);
		assertThat(resultQtySum).isEqualByComparingTo("30");

		return multiQuery;
	}

	private int seqNoCounter = 1; // we start with one, because 0 is not considered valid by the code under test

	private I_MD_Candidate_ATP_QueryResult createStockRecordWithBPartner(
			final BPartnerId bpartnerId,
			final Instant dateProjected)
	{
		final int productId = PRODUCT_ID;
		final AttributesKey storgateAttributesKey = STORAGE_ATTRIBUTES_KEY;

		return createStockRecord(bpartnerId, productId, storgateAttributesKey, dateProjected);
	}

	private I_MD_Candidate_ATP_QueryResult createStockRecordWithProduct(
			final int productId,
			final AttributesKey storgateAttributesKey,
			final Instant dateProjected)
	{
		final BPartnerId bpartnerId = null;
		return createStockRecord(bpartnerId, productId, storgateAttributesKey, dateProjected);
	}

	private I_MD_Candidate_ATP_QueryResult createStockRecord(
			final BPartnerId bpartnerId,
			final int productId,
			final AttributesKey storgateAttributesKey,
			final Instant dateProjected)
	{
		// set only the values we need
		final I_MD_Candidate candidateRecord = newInstance(I_MD_Candidate.class);
		candidateRecord.setDateProjected(TimeUtil.asTimestamp(dateProjected));
		candidateRecord.setIsActive(true);
		candidateRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK);
		candidateRecord.setSeqNo(seqNoCounter);
		save(candidateRecord);

		final I_MD_Candidate_ATP_QueryResult viewRecord = newInstance(I_MD_Candidate_ATP_QueryResult.class);
		viewRecord.setM_Product_ID(productId);
		viewRecord.setM_Warehouse_ID(WAREHOUSE_ID.getRepoId());
		viewRecord.setC_BPartner_Customer_ID(BPartnerId.toRepoId(bpartnerId));
		viewRecord.setDateProjected(TimeUtil.asTimestamp(dateProjected));
		viewRecord.setStorageAttributesKey(storgateAttributesKey.getAsString());
		viewRecord.setQty(BigDecimal.TEN);
		viewRecord.setSeqNo(seqNoCounter);
		save(viewRecord);

		seqNoCounter++;

		return viewRecord;
	}

	private MaterialDescriptor createMaterialDescriptor()
	{
		return createMaterialDescriptor(PRODUCT_ID, STORAGE_ATTRIBUTES_KEY);
	}

	private MaterialDescriptor createMaterialDescriptor(final int productId, final AttributesKey storageAttributesKey)
	{
		final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(
				productId,
				storageAttributesKey,
				ATTRIBUTE_SET_INSTANCE_ID);
		final MaterialDescriptor materialDescriptor = EventTestHelper
				.createMaterialDescriptor()
				.withCustomerId(BPARTNER_ID_1)
				.withProductDescriptor(productDescriptor);
		return materialDescriptor;
	}
}
