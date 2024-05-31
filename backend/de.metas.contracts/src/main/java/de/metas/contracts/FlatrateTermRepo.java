/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts;

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.location.ContractLocationHelper;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.location.DocumentLocation;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.order.InvoiceRule;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

@Repository
public class FlatrateTermRepo
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
		
	public FlatrateTerm getById(@NonNull final FlatrateTermId id)
	{
		final I_C_Flatrate_Term term = flatrateDAO.getById(id);

		final OrgId orgId = OrgId.ofRepoId(term.getAD_Org_ID());

		final I_C_Flatrate_Conditions flatrateConditionsRecord = term.getC_Flatrate_Conditions();
		final ProductId productId = ProductId.ofRepoId(flatrateConditionsRecord.getM_Product_Flatrate_ID());
		final I_C_UOM termUom = uomDAO.getById(CoalesceUtil.coalesceNotNull(UomId.ofRepoIdOrNull(term.getC_UOM_ID()), productBL.getStockUOMId(productId)));

		final BPartnerLocationAndCaptureId billPartnerLocationAndCaptureId = ContractLocationHelper.extractBillToLocationId(term);
		final DocumentLocation billLocation = ContractLocationHelper.extractBillLocation(term);
		
		final BPartnerLocationAndCaptureId dropshipLPartnerLocationAndCaptureId = ContractLocationHelper.extractDropshipLocationId(term);

		return FlatrateTerm.builder()
				.id(id)
				.orgId(orgId)
				.billPartnerLocationAndCaptureId(billPartnerLocationAndCaptureId)
				.billLocation(billLocation)
				.dropshipPartnerLocationAndCaptureId(dropshipLPartnerLocationAndCaptureId)
				.productId(productId)
				.flatrateConditionsId(ConditionsId.ofRepoId(term.getC_Flatrate_Conditions_ID()))
				.pricingSystemId(PricingSystemId.ofRepoIdOrNull(flatrateConditionsRecord.getM_PricingSystem_ID()))
				.isSimulation(term.isSimulation())
				.status(FlatrateTermStatus.ofNullableCode(term.getContractStatus()))
				.userInChargeId(UserId.ofRepoIdOrNull(term.getAD_User_InCharge_ID()))
				.startDate(TimeUtil.asInstant(term.getStartDate()))
				.endDate(TimeUtil.asInstant(term.getEndDate()))
				.masterStartDate(TimeUtil.asInstant(term.getMasterStartDate()))
				.masterEndDate(TimeUtil.asInstant(term.getMasterEndDate()))
				.plannedQtyPerUnit(Quantity.of(term.getPlannedQtyPerUnit(), termUom))
				.deliveryRule(DeliveryRule.ofNullableCode(term.getDeliveryRule()))
				.deliveryViaRule(DeliveryViaRule.ofNullableCode(term.getDeliveryViaRule()))
				.invoiceRule(InvoiceRule.ofCode(flatrateConditionsRecord.getInvoiceRule()))
				.build();
	}
}
