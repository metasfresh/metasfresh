/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.purchasecandidate;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.IdentifierIds_StepDefData;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_Item_Product_StepDefData;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.purchasecandidate.v2.CreatePurchaseCandidate_StepDef;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.async.C_PurchaseCandidates_GeneratePurchaseOrders;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;
import org.springframework.lang.Nullable;

import java.util.List;

import static de.metas.purchasecandidate.model.I_C_PurchaseCandidate.COLUMNNAME_C_OrderLineSO_ID;
import static de.metas.purchasecandidate.model.I_C_PurchaseCandidate.COLUMNNAME_C_OrderSO_ID;
import static de.metas.purchasecandidate.model.I_C_PurchaseCandidate.COLUMNNAME_M_Product_ID;
import static org.assertj.core.api.Assertions.assertThat;

public class C_PurchaseCandidate_StepDef
{
	private final static Logger logger = LogManager.getLogger(CreatePurchaseCandidate_StepDef.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final IdentifierIds_StepDefData identifierIdsTable;
	private final C_PurchaseCandidate_StepDefData purchaseCandidateTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_Product_StepDefData productTable;
	private final C_Order_StepDefData orderTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_BPartner_Location_StepDefData bpartnerLocationTable;
	private final M_HU_PI_Item_Product_StepDefData huPiItemProductTable;

	public C_PurchaseCandidate_StepDef(
			@NonNull final IdentifierIds_StepDefData identifierIdsTable,
			@NonNull final C_PurchaseCandidate_StepDefData purchaseCandidateTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bpartnerLocationTable,
			@NonNull final M_HU_PI_Item_Product_StepDefData huPiItemProductTable)
	{
		this.identifierIdsTable = identifierIdsTable;
		this.purchaseCandidateTable = purchaseCandidateTable;
		this.orderLineTable = orderLineTable;
		this.productTable = productTable;
		this.orderTable = orderTable;
		this.bpartnerTable = bpartnerTable;
		this.bpartnerLocationTable = bpartnerLocationTable;
		this.huPiItemProductTable = huPiItemProductTable;
	}

	/**
	 * Asserts that no {@code C_PurchaseCandidate} record exists for the given order line identifier.
	 * Fails immediately if any candidate is found (no polling).
	 */
	@And("^no C_PurchaseCandidate found for orderLine (.*)$")
	public void validate_no_C_PurchaseCandidate_found(@NonNull final String orderLineIdentifier)
	{
		final OrderLineId orderLineId = getOrderLineIdByIdentifier(orderLineIdentifier);
		assertThat(orderLineId).isNotNull();

		try
		{
			assertThat(getQueryByOrderLineId(orderLineId).count() == 0).isTrue();
		}
		catch (final Throwable throwable)
		{
			logCurrentContextExpectedNoRecords(orderLineId);
		}
	}

	/**
	 * Polls (up to {@code timeoutSec} seconds) until a {@code C_PurchaseCandidate} matching the given order line and optional quantity appears.
	 * Stores the found record under the row's identifier for use in later validation steps.
	 */
	@And("^after not more than (.*)s, C_PurchaseCandidate found for orderLine (.*)$")
	public void validate_C_PurchaseCandidate_found_for_OrderLine(
			final int timeoutSec,
			@NonNull final String orderLineIdentifier,
			@NonNull final DataTable dataTable) throws InterruptedException
	{

		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_PurchaseCandidate candidate = StepDefUtil.<I_C_PurchaseCandidate>tryAndWaitForItem()
					.worker(() -> validatePurchaseCandidate(row, orderLineIdentifier))
					.maxWaitSeconds(timeoutSec)
					.execute();
			row.getAsOptionalIdentifier().ifPresent(identifier -> purchaseCandidateTable.putOrReplace(identifier, candidate));
		});
	}

	/**
	 * Enqueues the specified {@code C_PurchaseCandidate} records for asynchronous purchase order generation via
	 * {@link C_PurchaseCandidates_GeneratePurchaseOrders}. The generated work packages are processed by the
	 * async processor and result in {@code C_Order} (purchase) records and {@code C_PurchaseCandidate_Alloc} links.
	 * <p>
	 * Note: since {@code PP_Product_Planning.IsCreatePlan=Y} now triggers auto-enqueue, this manual step is only
	 * needed when the auto-enqueue path is not active (e.g. for testing the UI workflow explicitly).
	 */
	@And("the following C_PurchaseCandidates are enqueued for generating C_Orders")
	public void enqueueC_PurchaseCandidates(@NonNull final DataTable dataTable)
	{
		C_PurchaseCandidates_GeneratePurchaseOrders.enqueue(DataTableRows.of(dataTable)
				.stream()
				.map(this::getPurchaseCandidateId)
				.collect(ImmutableSet.toImmutableSet()));
	}

	@NonNull
	private PurchaseCandidateId getPurchaseCandidateId(@NonNull final DataTableRow row)
	{
		final I_C_PurchaseCandidate purchaseCandidateRecord = row.getAsIdentifier(I_C_PurchaseCandidate.COLUMNNAME_C_PurchaseCandidate_ID).lookupNotNullIn(purchaseCandidateTable);
		assertThat(purchaseCandidateRecord).isNotNull();

		return PurchaseCandidateId.ofRepoId(purchaseCandidateRecord.getC_PurchaseCandidate_ID());
	}

	/**
	 * Polls (up to {@code timeoutSec} seconds, checking every 500 ms) until a {@code C_PurchaseCandidate}
	 * matching the given {@code C_OrderSO_ID}, {@code C_OrderLineSO_ID}, and {@code M_Product_ID} appears.
	 * Stores each found record under its row identifier for use in later validation steps.
	 */
	@And("^after not more than (.*)s, C_PurchaseCandidates are found$")
	public void find_purchase_candidates(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		DataTableRows.of(dataTable)
				.forEach(row -> findPurchaseCandidate(timeoutSec, row));
	}

	/**
	 * Synchronously validates the state of previously-found {@code C_PurchaseCandidate} records.
	 * Supported optional columns: {@code OPT.QtyToPurchase}.
	 * The candidate must have been stored via a prior "C_PurchaseCandidates are found" step.
	 */
	@And("C_PurchaseCandidates are validated")
	public void validate_purchase_candidates(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(row -> this.validatePurchaseCandidate(row));
	}

	@NonNull
	private IQuery<I_C_PurchaseCandidate> getQueryByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return getQueryByOrderLineIdAndQty(orderLineId, null);
	}

	@NonNull
	private IQuery<I_C_PurchaseCandidate> getQueryByOrderLineIdAndQty(@NonNull final OrderLineId orderLineId, @Nullable final Quantity quantityToPurchase)
	{
		final IQueryBuilder<I_C_PurchaseCandidate> builder = queryBL.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_C_OrderLineSO_ID, orderLineId);
		if (quantityToPurchase != null)
		{
			builder.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_QtyToPurchase, quantityToPurchase.toBigDecimal());
			builder.addEqualsFilter(I_C_PurchaseCandidate.COLUMNNAME_C_UOM_ID, quantityToPurchase.getUomId());

		}
		return builder.create();
	}

	private void logCurrentContextExpectedNoRecords(@NonNull final OrderLineId orderLineId)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Validating no records found for orderLineID :").append("\n")
				.append(COLUMNNAME_C_OrderLineSO_ID).append(" : ").append(orderLineId).append("\n");

		message.append("C_PurchaseCandidate records:").append("\n");

		getQueryByOrderLineId(orderLineId)
				.stream(I_C_PurchaseCandidate.class)
				.forEach(purchaseCandidate -> message
						.append(I_C_PurchaseCandidate.COLUMNNAME_C_PurchaseCandidate_ID).append(" : ").append(purchaseCandidate.getC_PurchaseCandidate_ID()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while validating no C_PurchaseCandidate found for orderLineId: " + orderLineId + ", see found records: \n" + message);
	}

	@Nullable
	private OrderLineId getOrderLineIdByIdentifier(@NonNull final String orderLineIdentifier)
	{
		return OrderLineId.ofRepoIdOrNull(identifierIdsTable.getOptional(orderLineIdentifier)
				.orElseGet(() -> orderLineTable.get(orderLineIdentifier).getC_OrderLine_ID()));
	}

	@NonNull
	private String logCurrentContext(@NonNull final DataTableRow row)
	{
		final I_C_Order orderRecord = row.getAsIdentifier(COLUMNNAME_C_OrderSO_ID).lookupIn(orderTable);
		final I_C_OrderLine orderLineRecord = row.getAsIdentifier(COLUMNNAME_C_OrderLineSO_ID).lookupIn(orderLineTable);
		final I_M_Product productRecord = row.getAsIdentifier(COLUMNNAME_M_Product_ID).lookupIn(productTable);

		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(COLUMNNAME_C_OrderSO_ID).append(" : ").append(orderRecord.getC_Order_ID()).append("\n")
				.append(COLUMNNAME_C_OrderLineSO_ID).append(" : ").append(orderLineRecord.getC_OrderLine_ID()).append("\n")
				.append(COLUMNNAME_M_Product_ID).append(" : ").append(productRecord.getM_Product_ID()).append("\n");

		message.append("C_PurchaseCandidate records:").append("\n");

		queryBL.createQueryBuilder(I_C_PurchaseCandidate.class)
				.create()
				.stream(I_C_PurchaseCandidate.class)
				.forEach(purchaseCandidateRecord -> message
						.append(COLUMNNAME_C_OrderSO_ID).append(" : ").append(purchaseCandidateRecord.getC_OrderSO_ID()).append(" ; ")
						.append(COLUMNNAME_C_OrderLineSO_ID).append(" : ").append(purchaseCandidateRecord.getC_OrderLineSO_ID()).append(" ; ")
						.append(COLUMNNAME_M_Product_ID).append(" : ").append(purchaseCandidateRecord.getM_Product_ID()).append(" ; ")
						.append("\n"));

		return "see current context: \n" + message;
	}

	private ItemProvider.ProviderResult<I_C_PurchaseCandidate> validatePurchaseCandidate(@NonNull final DataTableRow tableRow, final @NonNull String orderLineIdentifier)
	{
		final OrderLineId orderLineId = getOrderLineIdByIdentifier(orderLineIdentifier);
		assertThat(orderLineId).isNotNull();

		final Quantity qtyToPurchase = tableRow.getAsOptionalQuantity(I_C_PurchaseCandidate.COLUMNNAME_QtyToPurchase, I_C_PurchaseCandidate.COLUMNNAME_C_UOM_ID, uomDAO::getByX12DE355).orElse(null);

		final List<I_C_PurchaseCandidate> candidates = getQueryByOrderLineIdAndQty(orderLineId, qtyToPurchase).list();
		if (candidates.size() == 1)
		{
			return ItemProvider.ProviderResult.resultWasFound(candidates.get(0));
		}
		else if (candidates.isEmpty())
		{
			return ItemProvider.ProviderResult.resultWasNotFound("No C_PurchaseCandidate found for qtyToPurchase=" + qtyToPurchase);
		}
		else
		{
			return ItemProvider.ProviderResult.resultWasNotFound("Multiple C_PurchaseCandidate found "
					+ "\n\trow=" + tableRow
					+ "\n\tcandidates=" + candidates);
		}

	}

	private void validatePurchaseCandidate(@NonNull final DataTableRow row)
	{
		final I_C_PurchaseCandidate purchaseCandidateRecord = row.getAsIdentifier(I_C_PurchaseCandidate.COLUMNNAME_C_PurchaseCandidate_ID).lookupNotNullIn(purchaseCandidateTable);

		row.getAsOptionalBigDecimal(I_C_PurchaseCandidate.COLUMNNAME_QtyToPurchase)
				.ifPresent(qtyToPurchase -> assertThat(purchaseCandidateRecord.getQtyToPurchase()).isEqualTo(qtyToPurchase));

		row.getAsOptionalBoolean(I_C_PurchaseCandidate.COLUMNNAME_IsDropShip)
				.ifPresent(isDropShip -> assertThat(purchaseCandidateRecord.isDropShip())
						.as("IsDropShip")
						.isEqualTo(isDropShip));

		row.getAsOptionalIdentifier(I_C_PurchaseCandidate.COLUMNNAME_DropShip_BPartner_ID)
				.map(bpartnerTable::getId)
				.ifPresent(bpartnerId -> assertThat(purchaseCandidateRecord.getDropShip_BPartner_ID())
						.as("DropShip_BPartner_ID")
						.isEqualTo(bpartnerId.getRepoId()));

		row.getAsOptionalIdentifier(I_C_PurchaseCandidate.COLUMNNAME_DropShip_Location_ID)
				.map(bpartnerLocationTable::getId)
				.ifPresent(locationId -> assertThat(purchaseCandidateRecord.getDropShip_Location_ID())
						.as("DropShip_Location_ID")
						.isEqualTo(locationId.getRepoId()));

		row.getAsOptionalIdentifier(I_C_PurchaseCandidate.COLUMNNAME_M_HU_PI_Item_Product_ID)
				.map(huPiItemProductTable::get)
				.ifPresent(huPiItemProduct -> assertThat(purchaseCandidateRecord.getM_HU_PI_Item_Product_ID())
						.as("M_HU_PI_Item_Product_ID")
						.isEqualTo(huPiItemProduct.getM_HU_PI_Item_Product_ID()));

		row.getAsOptionalBigDecimal(I_C_PurchaseCandidate.COLUMNNAME_QtyEnteredTU)
				.ifPresent(qtyEnteredTU -> assertThat(purchaseCandidateRecord.getQtyEnteredTU())
						.as("QtyEnteredTU")
						.isEqualByComparingTo(qtyEnteredTU));
	}

	private void findPurchaseCandidate(final int timeoutSec, @NonNull final DataTableRow row) throws InterruptedException
	{
		final I_C_Order orderRecord = row.getAsIdentifier(COLUMNNAME_C_OrderSO_ID).lookupIn(orderTable);
		final I_C_OrderLine orderLineRecord = row.getAsIdentifier(COLUMNNAME_C_OrderLineSO_ID).lookupIn(orderLineTable);
		final I_M_Product productRecord = row.getAsIdentifier(COLUMNNAME_M_Product_ID).lookupIn(productTable);

		final I_C_PurchaseCandidate purchaseCandidateRecord = StepDefUtil
				.tryAndWaitForItem(timeoutSec, 500,
						() -> getPurchaseCandidate(orderRecord, orderLineRecord, productRecord),
						() -> logCurrentContext(row));

		purchaseCandidateTable.putOrReplace(row.getAsIdentifier(), purchaseCandidateRecord);
	}

	@NonNull
	private ItemProvider.ProviderResult<I_C_PurchaseCandidate> getPurchaseCandidate(
			@NonNull final I_C_Order orderRecord,
			@NonNull final I_C_OrderLine orderLineRecord,
			@NonNull final I_M_Product productRecord)
	{
		return queryBL
				.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(COLUMNNAME_C_OrderSO_ID, orderRecord.getC_Order_ID())
				.addEqualsFilter(COLUMNNAME_C_OrderLineSO_ID, orderLineRecord.getC_OrderLine_ID())
				.addEqualsFilter(COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
				.create()
				.firstOnlyOptional(I_C_PurchaseCandidate.class)
				.map(ItemProvider.ProviderResult::resultWasFound)
				.orElseGet(() -> ItemProvider.ProviderResult.resultWasNotFound("Couldn't find any C_PurchaseCandidate querying by"
						+ " C_OrderSO_ID=" + orderRecord.getC_Order_ID()
						+ " C_OrderLineSO_ID=" + orderLineRecord.getC_OrderLine_ID()
						+ " M_Product_ID=" + productRecord.getM_Product_ID()));
	}
}
