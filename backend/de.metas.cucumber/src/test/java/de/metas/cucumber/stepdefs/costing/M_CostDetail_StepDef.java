package de.metas.cucumber.stepdefs.costing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostElementId;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.inventory.M_InventoryLine_StepDefData;
import de.metas.cucumber.stepdefs.match_po.M_MatchPO_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.i18n.BooleanWithReason;
import de.metas.inout.InOutLineId;
import de.metas.inventory.InventoryLineId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.MatchPOId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_MatchPO;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class M_CostDetail_StepDef
{
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final CostDetailRepository costDetailRepository = SpringContextHolder.instance.getBean(CostDetailRepository.class);
	@NonNull private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
	@NonNull private final M_CostElement_StepDefData costElementTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_MatchPO_StepDefData matchPOTable;
	@NonNull private final M_InOutLine_StepDefData inoutLineTable;
	@NonNull private final M_InventoryLine_StepDefData inventoryLineTable;

	@And("^after not more than (.*)s, M_CostDetails are found for product (.*) and cost element (.*)$")
	public void validateCostDetails(
			final int timeoutSec,
			@NonNull final String productIdentifierStr,
			@NonNull final String costElementStr,
			@NonNull final DataTable table) throws Throwable
	{
		final ProductId productId = productTable.getId(productIdentifierStr);
		final Set<CostElementId> costElementIds = costElementTable.getIdsOfCommaSeparatedString(costElementStr);
		final CostDetailMatchers matchers = toCostDetailMatchers(table);

		assertThat(costElementIds).isNotEmpty();

		SharedTestContext.forEach(costElementIds, "costElement", costElementId -> {
			final CostDetailQuery costDetailQuery = CostDetailQuery.builder()
					.productId(productId)
					.costElementId(costElementId)
					.orderBy(CostDetailQuery.OrderBy.ID_ASC)
					.build();
			SharedTestContext.put("costDetailQuery", costDetailQuery);

			StepDefUtil.tryAndWaitForData(() -> costDetailRepository.stream(costDetailQuery).collect(ImmutableList.toImmutableList()))
					.validateUsingFunction(matchers::checkValid)
					.maxWaitSeconds(timeoutSec)
					.execute();
		});
	}

	private CostDetailMatchers toCostDetailMatchers(final DataTable table)
	{
		final ImmutableList<CostDetailMatcher> matchers = DataTableRows.of(table)
				.stream()
				.map(this::toCostDetailMatcher)
				.collect(ImmutableList.toImmutableList());

		return new CostDetailMatchers(matchers);
	}

	private CostDetailMatcher toCostDetailMatcher(final DataTableRow row)
	{
		final String tableName = row.getAsString("TableName");
		final StepDefDataIdentifier recordIdIdentifier = row.getAsIdentifier("Record_ID");
		final CostingDocumentRef documentRef;
		switch (tableName)
		{
			case I_M_MatchPO.Table_Name:
				final MatchPOId recordId = matchPOTable.getId(recordIdIdentifier);
				documentRef = CostingDocumentRef.ofMatchPOId(recordId);
				break;
			case I_M_InOutLine.Table_Name:
				final InOutLineId inoutLineId = inoutLineTable.getId(recordIdIdentifier);
				final boolean isSOTrx = row.getAsBoolean("IsSOTrx");
				documentRef = isSOTrx ? CostingDocumentRef.ofShipmentLineId(inoutLineId) : CostingDocumentRef.ofReceiptLineId(inoutLineId);
				break;
			case I_M_InventoryLine.Table_Name:
				final InventoryLineId inventoryLineId = inventoryLineTable.getId(recordIdIdentifier);
				documentRef = CostingDocumentRef.ofInventoryLineId(inventoryLineId);
				break;
			default:
				throw new AdempiereException("Table not handled: " + tableName);
		}

		return CostDetailMatcher.builder()
				.row(row)
				.documentRef(documentRef)
				.amt(row.getAsOptionalMoney("Amt", moneyService::getCurrencyIdByCurrencyCode).orElse(null))
				.qty(row.getAsOptionalQuantity("Qty", uomDAO::getByX12DE355).orElse(null))
				.build();
	}

	//
	//
	//
	//
	//

	@Builder
	private static class CostDetailMatcher
	{
		@NonNull @Getter private final DataTableRow row;
		@NonNull @Getter private final CostingDocumentRef documentRef;
		@Nullable private final Money amt;
		@Nullable private final Quantity qty;

		public BooleanWithReason checkValid(@NonNull final CostDetail costDetail)
		{
			if (!CostingDocumentRef.equals(costDetail.getDocumentRef(), documentRef))
			{
				return BooleanWithReason.falseBecause("Invalid documentRef " + costDetail.getDocumentRef() + ", expected " + documentRef);
			}

			if (amt != null)
			{
				final CostAmount amtActual = costDetail.getAmt();
				if (!Money.equals(amtActual.toMoney(), amt)
						&& !Money.equals(amtActual.toSourceMoney(), amt))
				{
					return BooleanWithReason.falseBecause("Invalid amt " + amtActual + ", expected " + amt);
				}
			}

			if (qty != null)
			{
				final Quantity actualQty = costDetail.getQty();
				if (actualQty.compareTo(qty) != 0)
				{
					return BooleanWithReason.falseBecause("Invalid qty " + actualQty + ", expected " + qty);
				}
			}

			return BooleanWithReason.TRUE;
		}
	}

	//
	//
	//
	//
	//

	private static class CostDetailMatchers
	{
		private final ImmutableList<CostDetailMatcher> matchers;

		public CostDetailMatchers(final ImmutableList<CostDetailMatcher> matchers)
		{
			this.matchers = Check.assumeNotEmpty(matchers, "no matchers");
		}

		public BooleanWithReason checkValid(List<CostDetail> costDetails)
		{
			if (costDetails.size() != matchers.size())
			{
				return BooleanWithReason.falseBecause("Invalid number of cost details: " + costDetails.size() + " != " + matchers.size());
			}

			final ImmutableMap<CostingDocumentRef, CostDetail> costDetailsByDocumentRef = Maps.uniqueIndex(costDetails, CostDetail::getDocumentRef);

			final ArrayList<String> notValidMessages = new ArrayList<>();

			for (int i = 0; i < matchers.size(); i++)
			{
				final int lineNo = i + 1;
				final CostDetailMatcher matcher = matchers.get(i);
				final CostDetail costDetail = costDetailsByDocumentRef.get(matcher.getDocumentRef());
				if (costDetail == null)
				{
					notValidMessages.add("line " + lineNo + ": no cost detail found for " + matcher.getRow());
					continue;
				}

				final BooleanWithReason valid = matcher.checkValid(costDetail);
				if (valid.isFalse())
				{
					notValidMessages.add("line " + lineNo + ": " + valid.getReason().translate(Env.getADLanguageOrBaseLanguage()));
				}
			}

			if (!notValidMessages.isEmpty())
			{
				return BooleanWithReason.falseBecause("Invalid cost details:\n" + String.join("\n", notValidMessages));
			}
			else
			{
				return BooleanWithReason.TRUE;
			}
		}
	}
}
