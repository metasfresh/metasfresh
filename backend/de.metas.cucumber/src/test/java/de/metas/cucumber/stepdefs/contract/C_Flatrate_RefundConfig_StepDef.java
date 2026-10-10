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

package de.metas.cucumber.stepdefs.contract;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig_PackingOption;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.hu.M_HU_PackingMaterial_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_InvoiceSchedule_StepDefData;
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import de.metas.currency.ICurrencyBL;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Responsible for the refund configuration of refund-type {@link I_C_Flatrate_Conditions}:
 * {@link I_C_Flatrate_RefundConfig} and its {@link I_C_Flatrate_RefundConfig_PackingOption} rows.
 */
@RequiredArgsConstructor
public class C_Flatrate_RefundConfig_StepDef
{
	@NonNull private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	@NonNull private final C_Flatrate_RefundConfig_StepDefData refundConfigTable;
	@NonNull private final C_Flatrate_Conditions_StepDefData conditionsTable;
	@NonNull private final C_InvoiceSchedule_StepDefData invoiceScheduleTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Product_Category_StepDefData productCategoryTable;
	@NonNull private final M_HU_PackingMaterial_StepDefData packingMaterialTable;

	/**
	 * Creates refund configurations of refund-type conditions.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>C_Flatrate_Conditions_ID</b> — (required, identifier-ref) the refund conditions<br>
	 *   <b>C_InvoiceSchedule_ID</b> — (required, identifier-ref) invoicing schedule of the refund<br>
	 *   <b>RefundPercent</b> — (required unless RefundAmt is given) the percentage that is refunded<br>
	 *   <b>RefundAmt</b> — (optional) the amount per unit that is refunded, instead of a percentage<br>
	 *   <b>C_Currency.ISO_Code</b> — (required with RefundAmt) currency of the amount per unit; optional with RefundPercent<br>
	 *   <b>M_Product_ID</b> — (optional, identifier-ref) product the refund applies to; none = every product<br>
	 *   <b>RefundMode</b> — (optional, default A) A = accumulated, T = tiered<br>
	 *   <b>RefundInvoiceType</b> — (optional, default Invoice) Invoice or Creditmemo<br>
	 *   <b>MinQty</b> — (optional, default 0)<br>
	 *   <b>Bonus_Product_ID</b> — (optional, identifier-ref) product that the refund line is booked on<br>
	 *   <b>M_Product_Category_ID</b> — (optional, identifier-ref) product category the refund is based on<br>
	 *   <b>IsPackingOptionFiltered</b> — (optional) only lines of the configured packaging options count<br>
	 *   <b>IsDeductedAtPayment</b> — (optional) the customer deducts the bonus when paying the invoice<br>
	 * @cucumber.depends StepDefData: C_Flatrate_Conditions_StepDefData, C_InvoiceSchedule_StepDefData, M_Product_StepDefData, M_Product_Category_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_Flatrate_RefundConfigs:
	 *   | Identifier   | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_ID |
	 *   | refundConfig | refundConditions         | monthlySchedule      | 20            | goodsProduct |
	 * </pre>
	 */
	@Given("metasfresh contains C_Flatrate_RefundConfigs:")
	public void metasfresh_contains_C_Flatrate_RefundConfigs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Flatrate_Conditions conditions = row.getAsIdentifier(I_C_Flatrate_RefundConfig.COLUMNNAME_C_Flatrate_Conditions_ID).lookupNotNullIn(conditionsTable);

			final I_C_Flatrate_RefundConfig config = newInstance(I_C_Flatrate_RefundConfig.class);
			config.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			config.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
			config.setC_InvoiceSchedule_ID(row.getAsIdentifier(I_C_Flatrate_RefundConfig.COLUMNNAME_C_InvoiceSchedule_ID).lookupNotNullIn(invoiceScheduleTable).getC_InvoiceSchedule_ID());
			final BigDecimal refundAmt = row.getAsOptionalBigDecimal(I_C_Flatrate_RefundConfig.COLUMNNAME_RefundAmt).orElse(null);
			if (refundAmt != null)
			{
				config.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Amount);
				config.setRefundAmt(refundAmt);
				config.setC_Currency_ID(currencyBL.getByCurrencyCode(row.getAsCurrencyCode()).getId().getRepoId());
			}
			else
			{
				config.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
				config.setRefundPercent(row.getAsBigDecimal(I_C_Flatrate_RefundConfig.COLUMNNAME_RefundPercent));
				row.getAsOptionalCurrencyCode()
						.ifPresent(currencyCode -> config.setC_Currency_ID(currencyBL.getByCurrencyCode(currencyCode).getId().getRepoId()));
			}
			config.setRefundMode(row.getAsOptionalString(I_C_Flatrate_RefundConfig.COLUMNNAME_RefundMode).orElse(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated));
			config.setRefundInvoiceType(row.getAsOptionalString(I_C_Flatrate_RefundConfig.COLUMNNAME_RefundInvoiceType).orElse(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice));
			config.setMinQty(row.getAsOptionalBigDecimal(I_C_Flatrate_RefundConfig.COLUMNNAME_MinQty).orElse(BigDecimal.ZERO));

			optionalIdentifier(row.getAsOptionalIdentifier(I_C_Flatrate_RefundConfig.COLUMNNAME_M_Product_ID))
					.ifPresent(identifier -> config.setM_Product_ID(productTable.getId(identifier).getRepoId()));
			optionalIdentifier(row.getAsOptionalIdentifier(I_C_Flatrate_RefundConfig.COLUMNNAME_Bonus_Product_ID))
					.ifPresent(identifier -> config.setBonus_Product_ID(productTable.getId(identifier).getRepoId()));
			optionalIdentifier(row.getAsOptionalIdentifier(I_C_Flatrate_RefundConfig.COLUMNNAME_M_Product_Category_ID))
					.ifPresent(identifier -> config.setM_Product_Category_ID(productCategoryTable.getId(identifier).getRepoId()));
			row.getAsOptionalBoolean(I_C_Flatrate_RefundConfig.COLUMNNAME_IsPackingOptionFiltered)
					.ifPresent(config::setIsPackingOptionFiltered);
			row.getAsOptionalBoolean(I_C_Flatrate_RefundConfig.COLUMNNAME_IsDeductedAtPayment)
					.ifPresent(config::setIsDeductedAtPayment);

			assignIdBeforeSaving(config);
			saveRecord(config);
			refundConfigTable.putOrReplace(row.getAsIdentifier(), config);
		});
	}

	/**
	 * Changes refund configurations like a user who corrects them in the window: the record is saved, so that its interceptors run.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required, identifier-ref) the refund configuration<br>
	 *   <b>C_Currency.ISO_Code</b> — (optional) new currency of the amount per unit<br>
	 *   <b>RefundAmt</b> — (optional) new amount per unit<br>
	 * @cucumber.depends StepDefData: C_Flatrate_RefundConfig_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And update C_Flatrate_RefundConfigs:
	 *   | Identifier   | C_Currency.ISO_Code |
	 *   | refundConfig | EUR                 |
	 * </pre>
	 */
	@Given("update C_Flatrate_RefundConfigs:")
	public void update_C_Flatrate_RefundConfigs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Flatrate_RefundConfig config = row.getAsIdentifier().lookupNotNullIn(refundConfigTable);
			InterfaceWrapperHelper.refresh(config);

			row.getAsOptionalCurrencyCode()
					.ifPresent(currencyCode -> config.setC_Currency_ID(currencyBL.getByCurrencyCode(currencyCode).getId().getRepoId()));
			row.getAsOptionalBigDecimal(I_C_Flatrate_RefundConfig.COLUMNNAME_RefundAmt)
					.ifPresent(config::setRefundAmt);

			saveRecord(config);
		});
	}

	/**
	 * Adds the packaging options that a packaging-option-filtered refund configuration counts.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Flatrate_RefundConfig_ID</b> — (required, identifier-ref) the refund configuration<br>
	 *   <b>M_HU_PackingMaterial_ID</b> — (required, identifier-ref) the packaging option<br>
	 * @cucumber.depends StepDefData: C_Flatrate_RefundConfig_StepDefData, M_HU_PackingMaterial_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_Flatrate_RefundConfig_PackingOptions:
	 *   | C_Flatrate_RefundConfig_ID | M_HU_PackingMaterial_ID |
	 *   | refundConfig               | packingMaterial         |
	 * </pre>
	 */
	@Given("metasfresh contains C_Flatrate_RefundConfig_PackingOptions:")
	public void metasfresh_contains_C_Flatrate_RefundConfig_PackingOptions(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Flatrate_RefundConfig config = row.getAsIdentifier(I_C_Flatrate_RefundConfig_PackingOption.COLUMNNAME_C_Flatrate_RefundConfig_ID).lookupNotNullIn(refundConfigTable);

			final I_C_Flatrate_RefundConfig_PackingOption packingOption = newInstance(I_C_Flatrate_RefundConfig_PackingOption.class);
			packingOption.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			packingOption.setC_Flatrate_RefundConfig_ID(config.getC_Flatrate_RefundConfig_ID());
			packingOption.setC_Flatrate_Conditions_ID(config.getC_Flatrate_Conditions_ID());
			packingOption.setM_HU_PackingMaterial_ID(packingMaterialTable.getId(row.getAsIdentifier(I_C_Flatrate_RefundConfig_PackingOption.COLUMNNAME_M_HU_PackingMaterial_ID)).getRepoId());
			saveRecord(packingOption);
		});
	}

	/**
	 * The WebUI allocates a new record's ID before it saves the record; the refund config's validating interceptor relies on that ID.
	 */
	private static void assignIdBeforeSaving(@NonNull final I_C_Flatrate_RefundConfig config)
	{
		final PO po = InterfaceWrapperHelper.getPO(config);
		po.set_ValueNoCheck(I_C_Flatrate_RefundConfig.COLUMNNAME_C_Flatrate_RefundConfig_ID, DB.getNextID(StepDefConstants.CLIENT_ID.getRepoId(), I_C_Flatrate_RefundConfig.Table_Name));
		po.setIsAssignedID(true);
	}

	/** A literal {@code null} cell is treated like an absent one. */
	private static Optional<StepDefDataIdentifier> optionalIdentifier(@NonNull final Optional<StepDefDataIdentifier> identifier)
	{
		return identifier.filter(StepDefDataIdentifier::isNotNullPlaceholder);
	}
}
