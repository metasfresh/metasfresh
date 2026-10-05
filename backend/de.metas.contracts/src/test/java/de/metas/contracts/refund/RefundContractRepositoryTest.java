package de.metas.contracts.refund;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.product.ProductId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.invoice.service.InvoiceScheduleRepository;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class RefundContractRepositoryTest
{
	private static final LocalDate NOW = LocalDate.now();
	private static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(10);

	private RefundContractRepository refundContractRepository;
	private I_C_UOM uomRecord;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		refundContractRepository = new RefundContractRepository(new RefundConfigRepository(new InvoiceScheduleRepository()));

		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);
	}

	@Test
	public void getById()
	{
		final I_C_Flatrate_Conditions conditionsRecord = newInstance(I_C_Flatrate_Conditions.class);
		conditionsRecord.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditionsRecord);

		final ConditionsId conditionsId = ConditionsId.ofRepoId(conditionsRecord.getC_Flatrate_Conditions_ID());
		final List<I_C_Flatrate_RefundConfig> //
		refundConfigRecords = RefundConfigRepositoryTest.createThreeRefundConfigRecords(conditionsId);

		// make sure that we don't have a record with qty=zero (needed for the test further down)
		final I_C_Flatrate_RefundConfig configWithZeroQty = refundConfigRecords
				.stream()
				.filter(r -> r.getMinQty().signum() == 0)
				.findFirst()
				.get();
		configWithZeroQty.setMinQty(ONE);
		saveRecord(configWithZeroQty);

		final I_C_Flatrate_Term contractRecord = createContractRecord(conditionsRecord);

		// invoke the method under test
		final FlatrateTermId contractId = FlatrateTermId.ofRepoId(contractRecord.getC_Flatrate_Term_ID());
		final RefundContract contract = refundContractRepository.getById(contractId);

		assertThat(contract.getStartDate()).isEqualTo(NOW);
		assertThat(contract.getBPartnerId()).isEqualTo(BPARTNER_ID);
		assertThat(contract.getRefundConfigs()).hasSize(4); // we expect a 4th "artificial" config with qty=zero
		assertThat(contract.getRefundConfig(ZERO).getPercent().isZero()).isTrue();
	}

	/**
	 * All matching terms are returned, a term with the queried product before a term without product.
	 */
	@Test
	public void getByQuery_returnsAllMatchingContracts()
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(77);
		final ProductId productId = ProductId.ofRepoId(78);

		final I_C_Flatrate_Term termWithoutProduct = createRefundTerm(bpartnerId, 0);
		final I_C_Flatrate_Term termWithProduct1 = createRefundTerm(bpartnerId, productId.getRepoId());
		final I_C_Flatrate_Term termWithProduct2 = createRefundTerm(bpartnerId, productId.getRepoId());
		createRefundTerm(bpartnerId, 79); // another product
		createRefundTerm(BPartnerId.ofRepoId(80), productId.getRepoId()); // another partner

		// invoke the method under test
		final List<FlatrateTermId> ids = refundContractRepository.getIdsByQuery(new RefundContractQuery(bpartnerId, productId, NOW));

		assertThat(ids).containsExactly(
				FlatrateTermId.ofRepoId(termWithProduct1.getC_Flatrate_Term_ID()),
				FlatrateTermId.ofRepoId(termWithProduct2.getC_Flatrate_Term_ID()),
				FlatrateTermId.ofRepoId(termWithoutProduct.getC_Flatrate_Term_ID()));
	}

	/**
	 * A term whose config has a category base matches the products of that category and of its sub-categories, and no others.
	 */
	@Test
	public void getByQuery_categoryBase()
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(91);

		final I_M_Product_Category category = newInstance(I_M_Product_Category.class);
		saveRecord(category);
		final I_M_Product_Category subCategory = newInstance(I_M_Product_Category.class);
		subCategory.setM_Product_Category_Parent_ID(category.getM_Product_Category_ID());
		saveRecord(subCategory);
		final I_M_Product_Category otherCategory = newInstance(I_M_Product_Category.class);
		saveRecord(otherCategory);

		final ProductId productInCategory = createProduct(category);
		final ProductId productInSubCategory = createProduct(subCategory);
		final ProductId productInOtherCategory = createProduct(otherCategory);

		final I_C_Flatrate_Term term = createRefundTerm(bpartnerId, 0);
		for (final I_C_Flatrate_RefundConfig config : RefundConfigRepositoryTest.createThreeRefundConfigRecords(ConditionsId.ofRepoId(term.getC_Flatrate_Conditions_ID())))
		{
			config.setM_Product_Category_ID(category.getM_Product_Category_ID());
			saveRecord(config);
		}
		final FlatrateTermId termId = FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID());

		assertThat(refundContractRepository.getByQuery(new RefundContractQuery(bpartnerId, productInCategory, NOW))).extracting(RefundContract::getId).containsExactly(termId);
		assertThat(refundContractRepository.getByQuery(new RefundContractQuery(bpartnerId, productInSubCategory, NOW))).extracting(RefundContract::getId).containsExactly(termId);
		assertThat(refundContractRepository.getByQuery(new RefundContractQuery(bpartnerId, productInOtherCategory, NOW))).isEmpty();
	}

	/**
	 * A config with a product and a category requires both: the term's product is the config's product, and it has to be in the category.
	 */
	@Test
	public void getByQuery_configWithProductAndCategory_requiresBoth()
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(92);

		final I_M_Product_Category category = newInstance(I_M_Product_Category.class);
		saveRecord(category);
		final I_M_Product_Category otherCategory = newInstance(I_M_Product_Category.class);
		saveRecord(otherCategory);
		final ProductId productInCategory = createProduct(category);
		final ProductId productInOtherCategory = createProduct(otherCategory);

		for (final ProductId productId : new ProductId[] { productInCategory, productInOtherCategory })
		{
			final I_C_Flatrate_Term term = createRefundTerm(bpartnerId, productId.getRepoId());
			for (final I_C_Flatrate_RefundConfig config : RefundConfigRepositoryTest.createThreeRefundConfigRecords(ConditionsId.ofRepoId(term.getC_Flatrate_Conditions_ID())))
			{
				config.setM_Product_ID(productId.getRepoId());
				config.setM_Product_Category_ID(category.getM_Product_Category_ID());
				saveRecord(config);
			}
		}

		assertThat(refundContractRepository.getByQuery(new RefundContractQuery(bpartnerId, productInCategory, NOW))).hasSize(1);
		assertThat(refundContractRepository.getByQuery(new RefundContractQuery(bpartnerId, productInOtherCategory, NOW))).isEmpty();
	}

	private ProductId createProduct(@NonNull final I_M_Product_Category category)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setM_Product_Category_ID(category.getM_Product_Category_ID());
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private I_C_Flatrate_Term createRefundTerm(@NonNull final BPartnerId bpartnerId, final int productRepoId)
	{
		final I_C_Flatrate_Conditions conditionsRecord = newInstance(I_C_Flatrate_Conditions.class);
		conditionsRecord.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditionsRecord);

		final I_C_Flatrate_Term contractRecord = newInstance(I_C_Flatrate_Term.class);
		contractRecord.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		contractRecord.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		contractRecord.setC_Flatrate_Conditions(conditionsRecord);
		contractRecord.setM_Product_ID(productRepoId);
		contractRecord.setStartDate(TimeUtil.asTimestamp(NOW));
		contractRecord.setEndDate(TimeUtil.asTimestamp(NOW.plusDays(10)));
		contractRecord.setBill_BPartner_ID(bpartnerId.getRepoId());
		saveRecord(contractRecord);
		return contractRecord;
	}

	private static I_C_Flatrate_Term createContractRecord(@NonNull final I_C_Flatrate_Conditions conditionsRecord)
	{
		final I_C_Flatrate_Term contractRecord = newInstance(I_C_Flatrate_Term.class);

		contractRecord.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		contractRecord.setC_Flatrate_Conditions(conditionsRecord);
		contractRecord.setM_Product_ID(30);
		contractRecord.setStartDate(TimeUtil.asTimestamp(NOW));
		contractRecord.setEndDate(TimeUtil.asTimestamp(NOW.plusDays(10)));
		contractRecord.setBill_BPartner_ID(BPARTNER_ID.getRepoId());
		saveRecord(contractRecord);
		return contractRecord;
	}
}
