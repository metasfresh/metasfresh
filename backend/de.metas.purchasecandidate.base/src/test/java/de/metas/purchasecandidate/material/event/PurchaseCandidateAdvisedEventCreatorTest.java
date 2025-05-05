package de.metas.purchasecandidate.material.event;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.common.util.time.SystemTime;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateAdvisedEvent;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.impl.MaterialPlanningContext;
import de.metas.organization.IOrgDAO;
import de.metas.pricing.conditions.BreakValueType;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.VendorProductInfoService;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_DiscountSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.eevolution.model.X_PP_Order_Candidate.ISLOTFORLOT_No;
import static org.eevolution.model.X_PP_Order_Candidate.ISLOTFORLOT_Yes;

/*
 * #%L
 * metasfresh-material-planning
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

public class PurchaseCandidateAdvisedEventCreatorTest
{
	private PurchaseCandidateAdvisedEventCreator purchaseCandidateAdvisedEventCreator; // service under test

	private BPartnerId bpartnerVendorId;
	private ProductId productId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final IOrgDAO orgDAO = Mockito.mock(IOrgDAO.class);
		Services.registerService(IOrgDAO.class, orgDAO);
		Mockito.when(orgDAO.getTimeZone(Mockito.any())).thenReturn(SystemTime.zoneId());

		this.purchaseCandidateAdvisedEventCreator = new PurchaseCandidateAdvisedEventCreator(
				new PurchaseOrderDemandMatcher(),
				new VendorProductInfoService(new BPartnerBL(new UserRepository())));

		setupMasterdata();
	}

	private void setupMasterdata()
	{
		final I_M_DiscountSchema discountSchemaRecord = newInstance(I_M_DiscountSchema.class);
		discountSchemaRecord.setDiscountType(X_M_DiscountSchema.DISCOUNTTYPE_Breaks);
		discountSchemaRecord.setBreakValueType(BreakValueType.QUANTITY.getCode());
		discountSchemaRecord.setValidFrom(Timestamp.valueOf("2017-01-01 10:10:10.0"));
		save(discountSchemaRecord);

		final I_C_BPartner bPartnerVendorRecord = newInstance(I_C_BPartner.class);
		bPartnerVendorRecord.setPO_DiscountSchema(discountSchemaRecord); // note that right now we don't need to have an actual price
		save(bPartnerVendorRecord);
		this.bpartnerVendorId = BPartnerId.ofRepoId(bPartnerVendorRecord.getC_BPartner_ID());

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setM_Product_Category_ID(60);
		product.setValue("Value");
		product.setName("Name");
		save(product);
		this.productId = ProductId.ofRepoId(product.getM_Product_ID());
	}

	@Nested
	class createPurchaseAdvisedEvent
	{
		SupplyRequiredDescriptor createSupplyRequiredDescriptor()
		{
			return SupplyRequiredDescriptor.builder()
					.eventDescriptor(EventDescriptor.ofClientAndOrg(10, 20))
					.materialDescriptor(MaterialDescriptor.builder()
							.productDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(productId.getRepoId()))
							.warehouseId(WarehouseId.ofRepoId(40))
							.quantity(BigDecimal.ONE)
							.date(SystemTime.asInstant())
							.build())
					.demandCandidateId(50)
					.fullDemandQty(BigDecimal.TEN)
					.isLotForLot(null)
					.build();
		}

		@Test
		void withoutLotForLot()
		{
			final SupplyRequiredDescriptor supplyRequiredDescriptor = createSupplyRequiredDescriptor();
			final ProductPlanning productPlanning = ProductPlanning.builder().id(ProductPlanningId.ofRepoId(123)).isPurchased(true).isLotForLot(false).build();

			final IMaterialPlanningContext context = new MaterialPlanningContext();
			context.setProductPlanning(productPlanning);

			// invoke the method under test
			final PurchaseCandidateAdvisedEvent event = purchaseCandidateAdvisedEventCreator.createPurchaseAdvisedEvent(supplyRequiredDescriptor, context).orElse(null);

			assertThat(event).isNotNull();
			assertThat(event.getProductPlanningId()).isEqualTo(productPlanning.getIdNotNull().getRepoId());
			assertThat(event.getVendorId()).isEqualTo(bpartnerVendorId.getRepoId());

			final SupplyRequiredDescriptor expectedSupplyRequiredDescriptor = supplyRequiredDescriptor.toBuilder()
					.isLotForLot(ISLOTFORLOT_No)
					.build();
			assertThat(event.getSupplyRequiredDescriptor()).isEqualTo(expectedSupplyRequiredDescriptor);
		}

		@Test
		void withLotForLot()
		{
			final SupplyRequiredDescriptor supplyRequiredDescriptor = createSupplyRequiredDescriptor();
			final ProductPlanning productPlanning = ProductPlanning.builder().id(ProductPlanningId.ofRepoId(123)).isPurchased(true).isLotForLot(true).build();

			final IMaterialPlanningContext context = new MaterialPlanningContext();
			context.setProductPlanning(productPlanning);

			// invoke the method under test
			final PurchaseCandidateAdvisedEvent event = purchaseCandidateAdvisedEventCreator.createPurchaseAdvisedEvent(supplyRequiredDescriptor, context).orElse(null);

			assertThat(event).isNotNull();
			assertThat(event.getProductPlanningId()).isEqualTo(productPlanning.getIdNotNull().getRepoId());
			assertThat(event.getVendorId()).isEqualTo(bpartnerVendorId.getRepoId());

			final SupplyRequiredDescriptor expectedSupplyRequiredDescriptor = supplyRequiredDescriptor.toBuilder()
					.isLotForLot(ISLOTFORLOT_Yes)
					.materialDescriptor(supplyRequiredDescriptor.getMaterialDescriptor().withQuantity(new BigDecimal("10")))
					.build();
			assertThat(event.getSupplyRequiredDescriptor()).usingRecursiveComparison().isEqualTo(expectedSupplyRequiredDescriptor);
			assertThat(event.getSupplyRequiredDescriptor()).isEqualTo(expectedSupplyRequiredDescriptor);
		}
	}
}
