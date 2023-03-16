package de.metas.costing.impl;

import de.metas.acct.api.AcctSchemaId;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.methods.CostAmountType;
import de.metas.costrevaluation.CostRevaluationLineId;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.money.CurrencyId;
import de.metas.order.MatchPOId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Builder;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_CostDetail;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
class CostDetailRepositoryTest
{
	private static final ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");

	private CostDetailRepository costDetailRepository;
	private I_C_UOM uomKg;
	private static final CurrencyId currencyId = CurrencyId.ofRepoId(102);
	private final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(1);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);

		costDetailRepository = new CostDetailRepository();
		uomKg = BusinessTestHelper.createUomKg();
	}

	private CostDetailPreviousAmounts zeroPreviousAmounts()
	{
		return CostDetailPreviousAmounts.builder()
				.costPrice(CostPrice.zero(currencyId, UomId.ofRepoId(uomKg.getC_UOM_ID())))
				.qty(Quantity.zero(uomKg))
				.cumulatedAmt(CostAmount.zero(currencyId))
				.cumulatedQty(Quantity.zero(uomKg))
				.build();
	}

	private Instant instant(String localDate)
	{
		return LocalDate.parse(localDate).atStartOfDay(ZONE_ID).toInstant();
	}

	@Builder(builderMethodName = "costDetail", builderClassName = "$CostDetailBuilder", buildMethodName = "create")
	private CostDetail prepareCostDetail(
			final CostElementId costElementId,
			final String dateAcct)
	{
		return costDetailRepository.create(CostDetail.builder()
				.clientId(ClientId.METASFRESH)
				.orgId(OrgId.MAIN)
				.acctSchemaId(acctSchemaId)
				.costElementId(costElementId)
				.productId(ProductId.ofRepoId(3))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(4))
				.amt(CostAmount.of(100, currencyId)).amtType(CostAmountType.MAIN)
				.qty(Quantity.of("10", uomKg))
				.changingCosts(true)
				.previousAmounts(zeroPreviousAmounts())
				.documentRef(CostingDocumentRef.ofMatchPOId(123))
				.dateAcct(instant(dateAcct)));
	}

	@Test
	void create_and_load()
	{
		final CostDetail costDetail = costDetailRepository.create(CostDetail.builder()
				.clientId(ClientId.METASFRESH)
				.orgId(OrgId.MAIN)
				.acctSchemaId(AcctSchemaId.ofRepoId(1))
				.costElementId(CostElementId.ofRepoId(2))
				.productId(ProductId.ofRepoId(3))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(4))
				.amt(CostAmount.of(100, currencyId)).amtType(CostAmountType.MAIN)
				.qty(Quantity.of("10", uomKg))
				.changingCosts(true)
				.previousAmounts(zeroPreviousAmounts())
				.documentRef(CostingDocumentRef.ofMatchPOId(123))
				.dateAcct(instant("2022-08-09")));

		assertThat(
				costDetailRepository.firstOnly(CostDetailQuery.builder().costElementId(CostElementId.ofRepoId(2)).build())
		).contains(costDetail);
	}

	@Nested
	class list
	{
		private final CostElementId ce1 = CostElementId.ofRepoId(201);
		private final CostElementId ce2 = CostElementId.ofRepoId(202);

		private CostDetail cd_E1_20220615;
		private CostDetail cd_E1_20220614;
		private CostDetail cd_E1_20220613;

		@BeforeEach
		void beforeEach()
		{
			cd_E1_20220615 = costDetail().costElementId(ce1).dateAcct("2022-06-15").create();
			cd_E1_20220613 = costDetail().costElementId(ce1).dateAcct("2022-06-13").create();
			cd_E1_20220614 = costDetail().costElementId(ce1).dateAcct("2022-06-14").create();

			// some noise:
			costDetail().costElementId(ce2).dateAcct("2022-06-15").create();
			costDetail().costElementId(ce2).dateAcct("2022-06-13").create();
			costDetail().costElementId(ce2).dateAcct("2022-06-14").create();
		}

		@Test
		void ascending_By_DateAcct_thenBy_ID()
		{
			assertThat(
					costDetailRepository.list(CostDetailQuery.builder()
							.costElementId(ce1)
							.orderBy(CostDetailQuery.OrderBy.DATE_ACCT_ASC)
							.orderBy(CostDetailQuery.OrderBy.ID_ASC)
							.build())
			).containsExactly(cd_E1_20220613, cd_E1_20220614, cd_E1_20220615);
		}

		@Test
		void descending_By_DateAcct_thenBy_ID()
		{
			assertThat(
					costDetailRepository.list(CostDetailQuery.builder()
							.costElementId(ce1)
							.orderBy(CostDetailQuery.OrderBy.DATE_ACCT_DESC)
							.orderBy(CostDetailQuery.OrderBy.ID_DESC)
							.build())
			).containsExactly(cd_E1_20220615, cd_E1_20220614, cd_E1_20220613);
		}
	}

	private static Stream<Arguments> sampleCostingDocumentRefs()
	{
		return Stream.of(
				Arguments.of(CostingDocumentRef.ofMatchPOId(MatchPOId.ofRepoId(123))),
				Arguments.of(CostingDocumentRef.ofMatchInvoiceId(MatchInvId.ofRepoId(123))),
				Arguments.of(CostingDocumentRef.ofReceiptLineId(123)),
				Arguments.of(CostingDocumentRef.ofShipmentLineId(123)),
				Arguments.of(CostingDocumentRef.ofInventoryLineId(123)),
				Arguments.of(CostingDocumentRef.ofInventoryLineId(123)),
				Arguments.of(CostingDocumentRef.ofOutboundMovementLineId(123)),
				Arguments.of(CostingDocumentRef.ofInboundMovementLineId(123)),
				Arguments.of(CostingDocumentRef.ofProjectIssueId(123)),
				Arguments.of(CostingDocumentRef.ofCostCollectorId(123)),
				Arguments.of(CostingDocumentRef.ofCostRevaluationLineId(CostRevaluationLineId.ofRepoId(123, 456)))
		);
	}

	@Nested
	class updateRecordFromDocumentRef_extractDocumentRef
	{
		@ParameterizedTest
		@MethodSource("de.metas.costing.impl.CostDetailRepositoryTest#sampleCostingDocumentRefs")
		void test(CostingDocumentRef costingDocumentRef)
		{
			final I_M_CostDetail costDetail = InterfaceWrapperHelper.newInstance(I_M_CostDetail.class);
			CostDetailRepository.updateRecordFromDocumentRef(costDetail, costingDocumentRef);
			if (costingDocumentRef.getOutboundTrx() != null)
			{
				costDetail.setIsSOTrx(costingDocumentRef.getOutboundTrx());
			}

			final CostingDocumentRef costingDocumentRef2 = CostDetailRepository.extractDocumentRef(costDetail);
			assertThat(costingDocumentRef2).isEqualTo(costingDocumentRef);
		}
	}

}