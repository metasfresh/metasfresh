/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.contract;

import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.contracts.commission.model.I_C_Commission_Instance.COLUMNNAME_Bill_BPartner_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Instance.COLUMNNAME_C_Commission_Instance_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Instance.COLUMNNAME_M_Product_Order_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Instance.COLUMNNAME_PointsBase_Forecasted;
import static de.metas.contracts.commission.model.I_C_Commission_Instance.COLUMNNAME_PointsBase_Invoiceable;
import static de.metas.contracts.commission.model.I_C_Commission_Instance.COLUMNNAME_PointsBase_Invoiced;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_Order.COLUMNNAME_C_Order_ID;

public class C_Commission_Instance_StepDef
{
	private final static Logger logger = LogManager.getLogger(C_Commission_Instance_StepDef.class);

	private final C_Commission_Instance_StepDefData commissionInstanceTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final M_Product_StepDefData productTable;
	private final C_Order_StepDefData orderTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public C_Commission_Instance_StepDef(
			@NonNull final C_Commission_Instance_StepDefData commissionInstanceTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Order_StepDefData orderTable)
	{
		this.commissionInstanceTable = commissionInstanceTable;
		this.bPartnerTable = bPartnerTable;
		this.productTable = productTable;
		this.orderTable = orderTable;
	}

	@And("validate created commission instance")
	public void created_commission_instance(@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> dataTableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : dataTableRows)
		{
			StepDefUtil.tryAndWait(90, 500, () -> retrieveCommissionInstance(row), () -> logCurrentContext(row));

			validateCommissionInstance(row);
		}
	}

	private void validateCommissionInstance(@NonNull final Map<String, String> row)
	{
		final String commissionInstanceIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Commission_Instance_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Commission_Instance commissionInstance = commissionInstanceTable.get(commissionInstanceIdentifier);
		assertThat(commissionInstance).isNotNull();

		//Bill_BPartner_ID
		{
			final String billBPartnerIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner billBPartner = bPartnerTable.get(billBPartnerIdentifier);
			assertThat(commissionInstance.getBill_BPartner_ID()).isEqualTo(billBPartner.getC_BPartner_ID());
		}

		//M_Product_Order_ID
		{
			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(commissionInstance.getM_Product_Order_ID()).isEqualTo(product.getM_Product_ID());
		}

		//PointsBase_Forecasted && PointsBase_Invoiceable && PointsBase_Invoiced
		{
			final BigDecimal forecasted = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsBase_Forecasted);
			assertThat(commissionInstance.getPointsBase_Forecasted().stripTrailingZeros()).isEqualTo(forecasted.stripTrailingZeros());

			final BigDecimal invoiceable = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsBase_Invoiceable);
			assertThat(commissionInstance.getPointsBase_Invoiceable().stripTrailingZeros()).isEqualTo(invoiceable.stripTrailingZeros());

			final BigDecimal invoiced = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsBase_Invoiced);
			assertThat(commissionInstance.getPointsBase_Invoiced().stripTrailingZeros()).isEqualTo(invoiced.stripTrailingZeros());
		}

		commissionInstanceTable.putOrReplace(commissionInstanceIdentifier, commissionInstance);
	}

	@NonNull
	private Boolean retrieveCommissionInstance(@NonNull final Map<String, String> row)
	{
		final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Order orderRecord = orderTable.get(orderIdentifier);

		final BigDecimal forecasted = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsBase_Forecasted);
		final BigDecimal invoiceable = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsBase_Invoiceable);
		final BigDecimal invoiced = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsBase_Invoiced);

		final I_C_Commission_Instance commissionInstance = queryBL.createQueryBuilder(I_C_Commission_Instance.class)
				.addEqualsFilter(I_C_Commission_Instance.COLUMNNAME_C_Order_ID, orderRecord.getC_Order_ID())
				.addEqualsFilter(COLUMNNAME_PointsBase_Forecasted, forecasted)
				.addEqualsFilter(COLUMNNAME_PointsBase_Invoiceable, invoiceable)
				.addEqualsFilter(COLUMNNAME_PointsBase_Invoiced, invoiced)
				.create()
				.firstOnlyOrNull(I_C_Commission_Instance.class);

		if (commissionInstance == null)
		{
			return false;
		}

		final String commissionInstanceIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Commission_Instance_ID + "." + TABLECOLUMN_IDENTIFIER);
		commissionInstanceTable.putOrReplace(commissionInstanceIdentifier, commissionInstance);

		return true;
	}

	private void logCurrentContext(@NonNull final Map<String, String> row)
	{
		final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Order orderRecord = orderTable.get(orderIdentifier);

		final BigDecimal forecasted = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsBase_Forecasted);
		final BigDecimal invoiceable = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsBase_Invoiceable);
		final BigDecimal invoiced = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsBase_Invoiced);

		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(COLUMNNAME_C_Order_ID).append(" : ").append(orderRecord.getC_Order_ID()).append("\n")
				.append(COLUMNNAME_PointsBase_Forecasted).append(" : ").append(forecasted).append("\n")
				.append(COLUMNNAME_PointsBase_Invoiceable).append(" : ").append(invoiceable).append("\n")
				.append(COLUMNNAME_PointsBase_Invoiced).append(" : ").append(invoiced).append("\n");

		message.append("C_Commission_Instance records:").append("\n");

		queryBL.createQueryBuilder(I_C_Commission_Instance.class)
				.create()
				.stream(I_C_Commission_Instance.class)
				.forEach(commissionInstanceRecord -> {

					message.append(COLUMNNAME_C_Commission_Instance_ID).append(" : ").append(commissionInstanceRecord.getC_Commission_Instance_ID()).append(" ; ")
							.append(COLUMNNAME_C_Order_ID).append(" : ").append(commissionInstanceRecord.getC_Order_ID()).append(" ; ")
							.append(COLUMNNAME_PointsBase_Forecasted).append(" : ").append(commissionInstanceRecord.getPointsBase_Forecasted()).append(" ; ")
							.append(COLUMNNAME_PointsBase_Invoiceable).append(" : ").append(commissionInstanceRecord.getPointsBase_Invoiceable()).append(" ; ")
							.append(COLUMNNAME_PointsBase_Invoiced).append(" : ").append(commissionInstanceRecord.getPointsBase_Invoiced()).append(" ; ")
							.append("\n");
				});

		logger.error("*** Error while looking for commission instance records, see current context: \n" + message.toString());
	}

}
