/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.pos;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CacheMgt;
import de.metas.costing.ChargeId;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.charge.C_ChargeType_StepDefData;
import de.metas.cucumber.stepdefs.charge.C_Charge_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.payment.api.IPaymentBL;
import de.metas.pos.POSService;
import de.metas.pos.POSTerminalId;
import de.metas.pos.withdrawal.POSCashWithdrawalCategory;
import de.metas.pos.withdrawal.POSCashWithdrawalRequest;
import de.metas.pos.withdrawal.POSCashWithdrawalResult;
import de.metas.pos.withdrawal.POSCashWithdrawalService;
import de.metas.user.UserId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_C_ChargeType;
import org.compiere.model.I_C_Payment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for POS cash withdrawals: configuring which charges are offered as withdrawal categories, taking
 * cash out of a POS terminal's till via {@link POSService#withdrawCash}, and asserting the offered categories.
 */
@RequiredArgsConstructor
public class POS_CashWithdrawal_StepDef
{
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	@NonNull private final POSService posService = SpringContextHolder.instance.getBean(POSService.class);

	@NonNull private final C_POS_StepDefData posTable;
	@NonNull private final C_ChargeType_StepDefData chargeTypeTable;
	@NonNull private final C_Charge_StepDefData chargeTable;
	@NonNull private final C_Payment_StepDefData paymentTable;

	/**
	 * Configures the cash withdrawal categories offered at POS terminals: the active charges of the given charge type
	 * (sysconfig {@value POSCashWithdrawalService#SYSCONFIG_ChargeTypeId}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends C_ChargeType_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the charges of C_ChargeType withdrawalChargeType are offered as POS cash withdrawal categories
	 * </pre>
	 */
	@And("^the charges of C_ChargeType (\\S+) are offered as POS cash withdrawal categories$")
	public void configureCashWithdrawalCategories(@NonNull final String chargeTypeIdentifier)
	{
		final I_C_ChargeType chargeType = StepDefDataIdentifier.ofString(chargeTypeIdentifier).lookupNotNullIn(chargeTypeTable);

		sysConfigBL.setValue(POSCashWithdrawalService.SYSCONFIG_ChargeTypeId, chargeType.getC_ChargeType_ID(), ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);
		CacheMgt.get().reset(I_AD_SysConfig.Table_Name); // the cache invalidation event may not be processed in time
	}

	/**
	 * Takes cash out of the POS terminal's till for the given category, via {@link POSService#withdrawCash} (as the
	 * mobile POS client does). The resulting outbound {@code C_Payment} is registered under {@code C_Payment_ID}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Charge_ID</b> — (required, identifier-ref) the withdrawal category's charge<br>
	 *   <b>Amount</b> — (required) gross amount taken out of the till, in the terminal's currency<br>
	 *   <b>C_Payment_ID</b> — (optional) identifier under which the resulting payment is registered<br>
	 * @cucumber.depends StepDefData: C_POS_StepDefData, C_Charge_StepDefData, C_Payment_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When a cash withdrawal is taken at POS terminal till by metasfresh:
	 *   | C_Charge_ID | Amount | C_Payment_ID |
	 *   | travelCosts | 12.00  | withdrawal   |
	 * </pre>
	 */
	@And("^a cash withdrawal is taken at POS terminal (\\S+) by (\\S+):$")
	public void withdrawCash(
			@NonNull final String terminalIdentifier,
			@NonNull final String userLogin,
			@NonNull final DataTable dataTable)
	{
		final POSTerminalId posTerminalId = posTable.getId(StepDefDataIdentifier.ofString(terminalIdentifier));
		final UserId cashierId = StepDefUtil.getUserIdByLogin(userLogin);

		DataTableRows.of(dataTable).forEach(row -> withdrawCash(posTerminalId, cashierId, row));
	}

	private void withdrawCash(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final UserId cashierId,
			@NonNull final DataTableRow row)
	{
		final POSCashWithdrawalResult result = posService.withdrawCash(POSCashWithdrawalRequest.builder()
				.posTerminalId(posTerminalId)
				.cashierId(cashierId)
				.chargeId(row.getAsIdentifier(I_C_Payment.COLUMNNAME_C_Charge_ID).lookupNotNullIdIn(chargeTable))
				.amount(row.getAsBigDecimal("Amount"))
				.build());

		row.getAsOptionalIdentifier(I_C_Payment.COLUMNNAME_C_Payment_ID)
				.ifPresent(identifier -> paymentTable.putOrReplace(identifier, paymentBL.getById(result.getPaymentId())));
	}

	/**
	 * Asserts the cash withdrawal categories offered at the POS terminal, in the given order, via
	 * {@link POSService#getCashWithdrawalCategories}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Charge_ID</b> — (required, identifier-ref) an offered category's charge<br>
	 * @cucumber.depends StepDefData: C_POS_StepDefData, C_Charge_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the POS cash withdrawal categories of till are:
	 *   | C_Charge_ID |
	 *   | travelCosts |
	 * </pre>
	 */
	@And("^the POS cash withdrawal categories of (\\S+) are:$")
	public void assertCashWithdrawalCategories(@NonNull final String terminalIdentifier, @NonNull final DataTable dataTable)
	{
		final POSTerminalId posTerminalId = posTable.getId(StepDefDataIdentifier.ofString(terminalIdentifier));

		final ImmutableList<ChargeId> expectedChargeIds = DataTableRows.of(dataTable)
				.stream()
				.map(row -> row.getAsIdentifier(I_C_Payment.COLUMNNAME_C_Charge_ID).lookupNotNullIdIn(chargeTable))
				.collect(ImmutableList.toImmutableList());

		final List<POSCashWithdrawalCategory> categories = posService.getCashWithdrawalCategories(posTerminalId);
		assertThat(categories)
				.as("POS cash withdrawal categories of %s", terminalIdentifier)
				.extracting(POSCashWithdrawalCategory::getChargeId)
				.containsExactlyElementsOf(expectedChargeIds);
	}
}
