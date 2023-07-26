/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.mediatedorder;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.services.CommissionProductService;
import de.metas.contracts.order.model.I_C_Order;
import de.metas.document.engine.DocStatus;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Tax;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.X_AD_OrgInfo.STORECREDITCARDDATA_Speichern;
import static org.mockito.ArgumentMatchers.eq;

public class MediatedOrderFactoryTest
{
	private MediatedOrderFactory mediatedOrderFactory;
	private CommissionProductService commissionProductServiceMock;

	@BeforeEach
	public void beforeEach()
	{
		commissionProductServiceMock = Mockito.mock(CommissionProductService.class);
		mediatedOrderFactory = new MediatedOrderFactory(commissionProductServiceMock);
	}

	@BeforeAll
	static void init()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
		AdempiereTestHelper.get().init();
	}

	@Test
	public void givenValidMediatedOrder_taxIncluded_whenForRecord_thenReturnMediatedOrderModel()
	{
		//given
		final ProductId transactionProductId = ProductId.ofRepoId(100);
		final BigDecimal priceActual = BigDecimal.valueOf(22);
		final BigDecimal qtyOrdered = BigDecimal.TEN;
		final BPartnerId orgBPartnerId = BPartnerId.ofRepoId(1);
		final BPartnerId vendorBPartnerId = BPartnerId.ofRepoId(2);

		de.metas.common.util.time.SystemTime.setTimeSource(() -> 1631157038);

		final I_C_Order mediatedOrderRecord = createMediatedOrderRecordsBuilder()
				.orgBPartnerId(orgBPartnerId)
				.priceActual(priceActual)
				.qtyOrdered(qtyOrdered)
				.transactionProductId(transactionProductId)
				.vendorBPartnerId(vendorBPartnerId)
				.taxIncluded(true)
				.build();

		Mockito.when(commissionProductServiceMock.productPreventsCommissioning(eq(transactionProductId)))
				.thenReturn(Boolean.FALSE);

		//when
		final MediatedOrder result = mediatedOrderFactory.forRecord(mediatedOrderRecord).get();

		//then
		expect(result).toMatchSnapshot();
	}

	@Test
	public void givenValidMediatedOrder_taxNotIncluded_whenForRecord_thenReturnMediatedOrderModel()
	{
		//given
		final ProductId transactionProductId = ProductId.ofRepoId(100);
		final BigDecimal priceActual = BigDecimal.valueOf(22);
		final BigDecimal qtyOrdered = BigDecimal.TEN;
		final BPartnerId orgBPartnerId = BPartnerId.ofRepoId(1);
		final BPartnerId vendorBPartnerId = BPartnerId.ofRepoId(2);

		de.metas.common.util.time.SystemTime.setTimeSource(() -> 1631157038);

		final I_C_Order mediatedOrderRecord = createMediatedOrderRecordsBuilder()
				.orgBPartnerId(orgBPartnerId)
				.priceActual(priceActual)
				.qtyOrdered(qtyOrdered)
				.transactionProductId(transactionProductId)
				.vendorBPartnerId(vendorBPartnerId)
				.build();

		Mockito.when(commissionProductServiceMock.productPreventsCommissioning(eq(transactionProductId)))
				.thenReturn(Boolean.FALSE);

		//when
		final MediatedOrder result = mediatedOrderFactory.forRecord(mediatedOrderRecord).get();

		//then
		expect(result).toMatchSnapshot();
	}

	@Test
	public void givenMediatedOrder_ProductPreventsCommissioning_whenForRecord_thenReturnEmpty()
	{
		//given
		final ProductId transactionProductId = ProductId.ofRepoId(100);
		final BigDecimal priceActual = BigDecimal.valueOf(22);
		final BigDecimal qtyOrdered = BigDecimal.TEN;
		final BPartnerId orgBPartnerId = BPartnerId.ofRepoId(1);
		final BPartnerId vendorBPartnerId = BPartnerId.ofRepoId(2);

		de.metas.common.util.time.SystemTime.setTimeSource(() -> 1631157038);

		final I_C_Order mediatedOrderRecord = createMediatedOrderRecordsBuilder()
				.orgBPartnerId(orgBPartnerId)
				.priceActual(priceActual)
				.qtyOrdered(qtyOrdered)
				.transactionProductId(transactionProductId)
				.vendorBPartnerId(vendorBPartnerId)
				.build();

		Mockito.when(commissionProductServiceMock.productPreventsCommissioning(eq(transactionProductId)))
				.thenReturn(Boolean.TRUE);

		//when
		final Optional<MediatedOrder> result = mediatedOrderFactory.forRecord(mediatedOrderRecord);

		//then
		assertThat(result).isEmpty();
	}

	@Builder(builderMethodName = "createMediatedOrderRecordsBuilder")
	private I_C_Order createMediatedOrderAndComplementaryRecords(
			final ProductId transactionProductId,
			final BigDecimal priceActual,
			final BigDecimal qtyOrdered,
			final BPartnerId orgBPartnerId,
			final BPartnerId vendorBPartnerId,
			final boolean taxIncluded
	)
	{
		//org
		final I_AD_Org org = newInstance(I_AD_Org.class);
		InterfaceWrapperHelper.save(org);
		final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		;
		orgInfo.setAD_Org_ID(orgId.getRepoId());
		orgInfo.setStoreCreditCardData(STORECREDITCARDDATA_Speichern);
		InterfaceWrapperHelper.save(orgInfo);

		//mediated doctype
		final I_C_DocType mediatedDocType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		mediatedDocType.setDocBaseType(X_C_DocType.DOCBASETYPE_PurchaseOrder);
		mediatedDocType.setDocSubType(X_C_DocType.DOCSUBTYPE_Mediated);
		InterfaceWrapperHelper.saveRecord(mediatedDocType);

		//org bpartner
		final I_C_BPartner orgLinkedBPartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		orgLinkedBPartner.setC_BPartner_ID(orgBPartnerId.getRepoId());
		orgLinkedBPartner.setAD_Org_ID(orgId.getRepoId());
		orgLinkedBPartner.setAD_OrgBP_ID(orgId.getRepoId());
		InterfaceWrapperHelper.saveRecord(orgLinkedBPartner);

		//vendor
		final I_C_BPartner vendorBPartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		vendorBPartner.setC_BPartner_ID(vendorBPartnerId.getRepoId());
		vendorBPartner.setAD_Org_ID(orgId.getRepoId());
		InterfaceWrapperHelper.saveRecord(vendorBPartner);

		//tax
		final I_C_Tax taxRecord = newInstance(I_C_Tax.class);
		taxRecord.setSOPOType(X_C_Tax.SOPOTYPE_Both);
		taxRecord.setValidFrom(TimeUtil.parseTimestamp("2019-01-01"));
		taxRecord.setRate(BigDecimal.TEN);
		taxRecord.setC_TaxCategory_ID(101);
		saveRecord(taxRecord);

		//pricelist
		final I_M_PriceList priceList = newInstance(I_M_PriceList.class);
		priceList.setPricePrecision(2);
		saveRecord(priceList);

		//pricelistversion
		final I_M_PriceList_Version priceListVersion = newInstance(I_M_PriceList_Version.class);
		priceListVersion.setM_PriceList_ID(priceList.getM_PriceList_ID());
		saveRecord(priceListVersion);

		//order
		final I_C_Order mediatedOrder = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		mediatedOrder.setDocStatus(DocStatus.Completed.getCode());
		mediatedOrder.setC_DocType_ID(mediatedDocType.getC_DocType_ID());
		mediatedOrder.setAD_Org_ID(orgId.getRepoId());
		mediatedOrder.setC_BPartner_ID(vendorBPartner.getC_BPartner_ID());
		mediatedOrder.setDateAcct(TimeUtil.asTimestamp(LocalDate.of(2021, 3, 18)));
		mediatedOrder.setIsTaxIncluded(taxIncluded);
		InterfaceWrapperHelper.saveRecord(mediatedOrder);

		//line
		final I_C_UOM lineUOM = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		lineUOM.setC_UOM_ID(1);
		InterfaceWrapperHelper.saveRecord(lineUOM);

		final I_C_OrderLine mediatedLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		mediatedLine.setC_Order_ID(mediatedOrder.getC_Order_ID());
		mediatedLine.setM_Product_ID(transactionProductId.getRepoId());
		mediatedLine.setM_PriceList_Version_ID(priceListVersion.getM_PriceList_Version_ID());
		mediatedLine.setAD_Org_ID(orgId.getRepoId());
		mediatedLine.setPriceActual(priceActual);
		mediatedLine.setQtyOrdered(qtyOrdered);
		mediatedLine.setC_UOM_ID(1);
		mediatedLine.setPrice_UOM_ID(1);
		mediatedLine.setC_Tax_ID(taxRecord.getC_Tax_ID());
		InterfaceWrapperHelper.saveRecord(mediatedLine);

		return mediatedOrder;
	}
}
