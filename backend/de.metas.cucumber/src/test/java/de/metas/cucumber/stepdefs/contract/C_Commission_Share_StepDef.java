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

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.commission.model.I_C_CommissionSettingsLine;
import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.contracts.commission.model.I_C_Customer_Trade_Margin_Line;
import de.metas.contracts.commission.model.I_C_LicenseFeeSettingsLine;
import de.metas.contracts.commission.model.I_C_MediatedCommissionSettingsLine;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.contract.commission.hierarchy.C_CommissionSettingsLine_StepDefData;
import de.metas.cucumber.stepdefs.contract.commission.licensefee.C_LicenseFeeSettingsLine_StepDefData;
import de.metas.cucumber.stepdefs.contract.commission.margin.C_Customer_Trade_Margin_Line_StepDefData;
import de.metas.cucumber.stepdefs.contract.commission.mediated.C_MediatedCommissionSettingsLine_StepDefData;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.contracts.commission.model.I_C_Commission_Share.COLUMNNAME_C_BPartner_Payer_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Share.COLUMNNAME_C_BPartner_SalesRep_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Share.COLUMNNAME_C_Commission_Instance_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Share.COLUMNNAME_C_Commission_Share_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Share.COLUMNNAME_C_Customer_Trade_Margin_Line_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Share.COLUMNNAME_C_Flatrate_Term_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Share.COLUMNNAME_C_LicenseFeeSettingsLine_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Share.COLUMNNAME_C_MediatedCommissionSettingsLine_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Share.COLUMNNAME_Commission_Product_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Share.COLUMNNAME_IsSOTrx;
import static de.metas.contracts.commission.model.I_C_Commission_Share.COLUMNNAME_LevelHierarchy;
import static de.metas.contracts.commission.model.I_C_Commission_Share.COLUMNNAME_PointsSum_Forecasted;
import static de.metas.contracts.commission.model.I_C_Commission_Share.COLUMNNAME_PointsSum_Invoiceable;
import static de.metas.contracts.commission.model.I_C_Commission_Share.COLUMNNAME_PointsSum_Invoiced;
import static de.metas.contracts.commission.model.I_C_Commission_Share.COLUMNNAME_PointsSum_Settled;
import static de.metas.contracts.commission.model.I_C_Commission_Share.COLUMNNAME_PointsSum_ToSettle;
import static de.metas.cucumber.stepdefs.StepDefConstants.METASFRESH_VALUE;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_Commission_Share_StepDef
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Commission_Instance_StepDefData commissionInstanceTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final M_Product_StepDefData productTable;
	private final C_Flatrate_Term_StepDefData contractTable;
	private final C_CommissionSettingsLine_StepDefData hierarchySettingsLineTable;
	private final C_Commission_Share_StepDefData commissionShareTable;
	private final C_LicenseFeeSettingsLine_StepDefData licenseFeeSettingsLineTable;
	private final C_Customer_Trade_Margin_Line_StepDefData customerTradeMarginLineTable;
	private final C_MediatedCommissionSettingsLine_StepDefData mediatedCommissionSettingsLineTable;

	public C_Commission_Share_StepDef(
			@NonNull final C_Commission_Instance_StepDefData commissionInstanceTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Flatrate_Term_StepDefData contractTable,
			@NonNull final C_CommissionSettingsLine_StepDefData hierarchySettingsLineTable,
			@NonNull final C_Commission_Share_StepDefData commissionShareTable,
			@NonNull final C_LicenseFeeSettingsLine_StepDefData licenseFeeSettingsLineTable,
			@NonNull final C_Customer_Trade_Margin_Line_StepDefData customerTradeMarginLineTable,
			@NonNull final C_MediatedCommissionSettingsLine_StepDefData mediatedCommissionSettingsLineTable)
	{
		this.commissionInstanceTable = commissionInstanceTable;
		this.bPartnerTable = bPartnerTable;
		this.productTable = productTable;
		this.contractTable = contractTable;
		this.hierarchySettingsLineTable = hierarchySettingsLineTable;
		this.commissionShareTable = commissionShareTable;
		this.licenseFeeSettingsLineTable = licenseFeeSettingsLineTable;
		this.customerTradeMarginLineTable = customerTradeMarginLineTable;
		this.mediatedCommissionSettingsLineTable = mediatedCommissionSettingsLineTable;
	}

	@And("^validate commission deed for commission instance (.*)$")
	public void validate_commission_deed(@NonNull final String commissionInstanceIdentifier, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> dataTableRows = dataTable.asMaps(String.class, String.class);

		final I_C_Commission_Instance commissionInstance = commissionInstanceTable.get(commissionInstanceIdentifier);
		assertThat(commissionInstance).isNotNull();

		for (final Map<String, String> row : dataTableRows)
		{
			StepDefUtil.tryAndWait(90, 500, () -> retrieveShare(row, commissionInstanceIdentifier));

			final String commissionShareIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Commission_Share_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_C_Commission_Share commissionShare = commissionShareTable.get(commissionShareIdentifier);

			validateCommissionDeed(row, commissionShare);
		}
	}

	private void validateCommissionDeed(
			@NonNull final Map<String, String> row,
			@NonNull final I_C_Commission_Share commissionShare)
	{
		//C_Commission_Share.C_BPartner_Payer_ID
		{
			final String payerIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_BPartner_Payer_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_C_BPartner payer = payerIdentifier.equals(METASFRESH_VALUE)
					? bpartnerDAO.retrieveBPartnerByValue(Env.getCtx(), payerIdentifier)
					: bPartnerTable.get(payerIdentifier);

			assertThat(payer).isNotNull();
			assertThat(commissionShare.getC_BPartner_Payer_ID()).isEqualTo(payer.getC_BPartner_ID());
		}

		//C_Commission_Share.C_BPartner_SalesRep_ID
		{
			final String salesRepIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_BPartner_SalesRep_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner salesRep = salesRepIdentifier.equals(METASFRESH_VALUE)
					? bpartnerDAO.retrieveBPartnerByValue(Env.getCtx(), salesRepIdentifier)
					: bPartnerTable.get(salesRepIdentifier);

			assertThat(salesRep).isNotNull();
			assertThat(commissionShare.getC_BPartner_SalesRep_ID()).isEqualTo(salesRep.getC_BPartner_ID());
		}

		//C_Commission_Share.C_Flatrate_Term_ID
		{
			final String contractIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_C_Flatrate_Term contract = contractTable.get(contractIdentifier);

			assertThat(commissionShare.getC_Flatrate_Term_ID()).isEqualTo(contract.getC_Flatrate_Term_ID());
			contractTable.putOrReplace(contractIdentifier, contract);
		}

		//C_Commission_Share.Commission_Product_ID
		{
			final String commissionProductIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Commission_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(commissionProductIdentifier);
			assertThat(commissionShare.getCommission_Product_ID()).isEqualTo(product.getM_Product_ID());
		}

		//C_Commission_Share.LevelHierarchy
		{
			final Integer levelHierarchy = DataTableUtil.extractIntForColumnName(row, COLUMNNAME_LevelHierarchy);
			assertThat(commissionShare.getLevelHierarchy()).isEqualTo(levelHierarchy);
		}

		//C_Commission_Share.C_CommissionSettingsLine_ID
		{
			final String hierarchySettingsLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Commission_Share.COLUMNNAME_C_CommissionSettingsLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(hierarchySettingsLineIdentifier))
			{
				final I_C_CommissionSettingsLine commissionSettingsLine = hierarchySettingsLineTable.get(hierarchySettingsLineIdentifier);

				assertThat(commissionSettingsLine).isNotNull();
				assertThat(commissionShare.getC_CommissionSettingsLine_ID()).isEqualTo(commissionSettingsLine.getC_CommissionSettingsLine_ID());
			}
		}

		//C_Commission_Share.C_LicenseFeeSettingsLine_ID
		{
			final String licenseFeeSettingsLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_LicenseFeeSettingsLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(licenseFeeSettingsLineIdentifier))
			{
				final I_C_LicenseFeeSettingsLine licenseFeeSettingsLine = licenseFeeSettingsLineTable.get(licenseFeeSettingsLineIdentifier);
				assertThat(commissionShare.getC_LicenseFeeSettingsLine_ID()).isEqualTo(licenseFeeSettingsLine.getC_LicenseFeeSettings_ID());
			}
		}

		//C_Commission_Share.C_Customer_Trade_Margin_Line_ID
		{
			final String marginSettingsIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Customer_Trade_Margin_Line_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(marginSettingsIdentifier))
			{
				final I_C_Customer_Trade_Margin_Line customerTradeMarginLine = customerTradeMarginLineTable.get(marginSettingsIdentifier);
				assertThat(commissionShare.getC_Customer_Trade_Margin_Line_ID()).isEqualTo(customerTradeMarginLine.getC_Customer_Trade_Margin_Line_ID());
			}
		}

		//C_Commission_Share.C_MediatedCommissionSettingsLine_ID
		{
			final String mediatedSettingsLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_MediatedCommissionSettingsLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(mediatedSettingsLineIdentifier))
			{
				final I_C_MediatedCommissionSettingsLine mediatedCommissionSettingsLine = mediatedCommissionSettingsLineTable.get(mediatedSettingsLineIdentifier);
				assertThat(commissionShare.getC_MediatedCommissionSettingsLine_ID()).isEqualTo(mediatedCommissionSettingsLine.getC_MediatedCommissionSettingsLine_ID());
			}
		}

		//C_Commission_Share.IsSOTrx && C_Commission_Share.IsSimulation
		{
			final Boolean isSoTrx = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_IsSOTrx);
			assertThat(commissionShare.isSOTrx()).isEqualTo(isSoTrx);

			final Boolean isSimulation = DataTableUtil.extractBooleanForColumnName(row, I_C_Commission_Share.COLUMNNAME_IsSimulation);
			assertThat(commissionShare.isSimulation()).isEqualTo(isSimulation);
		}

		//C_Commission_Share.PointsSum_Forecasted && C_Commission_Share.PointsSum_Invoiceable && C_Commission_Share.PointsSum_Invoiced
		{
			final BigDecimal forecasted = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsSum_Forecasted);
			assertThat(commissionShare.getPointsSum_Forecasted().stripTrailingZeros()).isEqualTo(forecasted.stripTrailingZeros());

			final BigDecimal invoiceable = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsSum_Invoiceable);
			assertThat(commissionShare.getPointsSum_Invoiceable().stripTrailingZeros()).isEqualTo(invoiceable.stripTrailingZeros());

			final BigDecimal invoiced = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsSum_Invoiced);
			assertThat(commissionShare.getPointsSum_Invoiced().stripTrailingZeros()).isEqualTo(invoiced.stripTrailingZeros());
		}

		//C_Commission_Share.PointsSum_ToSettle && C_Commission_Share.PointsSum_Settled
		{
			final BigDecimal toSettle = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsSum_ToSettle);
			assertThat(commissionShare.getPointsSum_ToSettle()).isEqualTo(toSettle);

			final BigDecimal settled = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsSum_Settled);
			assertThat(commissionShare.getPointsSum_Settled()).isEqualTo(settled);
		}

		final String commissionShareIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Commission_Share_ID + "." + TABLECOLUMN_IDENTIFIER);

		commissionShareTable.putOrReplace(commissionShareIdentifier, commissionShare);
	}

	@NonNull
	private Boolean retrieveShare(@NonNull final Map<String, String> row, @NonNull final String commissionIdentifier)
	{
		final I_C_Commission_Instance commissionInstance = commissionInstanceTable.get(commissionIdentifier);

		final BigDecimal forecasted = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsSum_Forecasted);
		final BigDecimal invoiceable = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsSum_Invoiceable);
		final BigDecimal invoiced = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsSum_Invoiced);
		final BigDecimal toSettle = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsSum_ToSettle);
		final BigDecimal settled = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PointsSum_Settled);

		final String salesRepIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_BPartner_SalesRep_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner salesRep = salesRepIdentifier.equals(METASFRESH_VALUE)
				? bpartnerDAO.retrieveBPartnerByValue(Env.getCtx(), salesRepIdentifier)
				: bPartnerTable.get(salesRepIdentifier);

		assertThat(salesRep).isNotNull();

		final I_C_Commission_Share commissionShare = queryBL.createQueryBuilder(I_C_Commission_Share.class)
				.addEqualsFilter(COLUMNNAME_C_Commission_Instance_ID, commissionInstance.getC_Commission_Instance_ID())
				.addEqualsFilter(COLUMNNAME_C_BPartner_SalesRep_ID, salesRep.getC_BPartner_ID())
				.addEqualsFilter(COLUMNNAME_PointsSum_Forecasted, forecasted)
				.addEqualsFilter(COLUMNNAME_PointsSum_Invoiceable, invoiceable)
				.addEqualsFilter(COLUMNNAME_PointsSum_Invoiced, invoiced)
				.addEqualsFilter(COLUMNNAME_PointsSum_ToSettle, toSettle)
				.addEqualsFilter(COLUMNNAME_PointsSum_Settled, settled)
				.create()
				.firstOnlyOrNull(I_C_Commission_Share.class);

		if (commissionShare == null)
		{
			return false;
		}

		final String commissionShareIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Commission_Share_ID + "." + TABLECOLUMN_IDENTIFIER);
		commissionShareTable.putOrReplace(commissionShareIdentifier, commissionShare);

		return true;
	}
}
