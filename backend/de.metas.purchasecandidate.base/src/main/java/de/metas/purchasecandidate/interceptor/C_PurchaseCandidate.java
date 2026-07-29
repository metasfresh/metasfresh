package de.metas.purchasecandidate.interceptor;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.order.OrderAndLineId;
import de.metas.order.grossprofit.model.I_C_OrderLine;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductLifeCycleAction;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

@Interceptor(I_C_PurchaseCandidate.class)
@Component
public class C_PurchaseCandidate
{
	private final PurchaseCandidateRepository purchaseCandidateRepository;

	private final IProductBL productBL = Services.get(IProductBL.class);

	public C_PurchaseCandidate(@NonNull final PurchaseCandidateRepository purchaseCandidateRepository)
	{
		this.purchaseCandidateRepository = purchaseCandidateRepository;
	}

	/**
	 * Enforce the product's life-cycle status on purchase-candidate creation.
	 * <p>
	 * {@code TYPE_BEFORE_NEW} only, so it covers every creation path (material-event handler, REST
	 * {@code CreatePurchaseCandidatesService}, and the manual Sales-Order&rarr;Purchase WebUI
	 * {@code PurchaseRowsSaver}) yet never blocks an update of an already-open candidate whose
	 * product was blocked after the candidate was created (retroactive-safety).
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void assertProductAllowedForPurchase(@NonNull final I_C_PurchaseCandidate candidate)
	{
		final int productRepoId = candidate.getM_Product_ID();
		if (productRepoId > 0)
		{
			productBL.assertAllowed(ProductId.ofRepoId(productRepoId), ProductLifeCycleAction.PURCHASE);
		}
	}

	@ModelChange(//
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, //
			ifColumnsChanged = I_C_PurchaseCandidate.COLUMNNAME_ProfitPurchasePriceActual)
	public void updateSalesOrderLineProfit(@NonNull final I_C_PurchaseCandidate purchaseCandidateRecord)
	{
		final PurchaseCandidate purchaseCandidate = purchaseCandidateRepository.getById(PurchaseCandidateId.ofRepoId(purchaseCandidateRecord.getC_PurchaseCandidate_ID()));

		final OrderAndLineId salesOrderAndLineId = purchaseCandidate.getSalesOrderAndLineIdOrNull();
		if (salesOrderAndLineId == null)
		{
			return; // nothing to update
		}

		final I_C_OrderLine salesOrderLineRecord = load(salesOrderAndLineId.getOrderLineRepoId(), I_C_OrderLine.class);

		final BigDecimal value = Optional.ofNullable(purchaseCandidate.getProfitInfoOrNull())
				.flatMap(PurchaseProfitInfo::getProfitPercent)
				.map(percent -> percent.roundToHalf(RoundingMode.HALF_UP))
				.map(Percent::toBigDecimal)
				.orElse(ZERO);
		salesOrderLineRecord.setProfitPercent(value);
		saveRecord(salesOrderLineRecord);
	}
}
