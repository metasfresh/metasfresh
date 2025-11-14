package de.metas.ui.web.window.health;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.TreeMultimap;
import de.metas.ui.web.window.health.json.JsonContextVariablesResponse;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.Map;

class MissingContextVariables
{
	private static final ImmutableMap<String, String> KNOWN_MISSING_CONTEXT_VARIABLES_DEFAULTS = ImmutableMap.<String, String>builder()
			.put("AD_Process/165.IsOneInstanceOnly.DisplayLogic", "IsServerProcess")
			.put("AD_Process/165.IsOneInstanceOnly.MandatoryLogic", "IsServerProcess")
			.put("AD_Sequence/112.DateColumn.MandatoryLogic", "StartNewYear")
			.put("A_Asset/251.A_Asset_Change/53158.AD_User_ID.DisplayLogic", "Ad_User_ID")
			.put("A_Asset/251.A_Asset_Change/53158.C_BPartner_ID.DisplayLogic", "C_Bpartner_ID")
			.put("A_Asset/251.A_Asset_Change/53158.SerNo.DisplayLogic", "Serno")
			.put("A_Asset/251.A_Asset_Change/53158.VersionNo.DisplayLogic", "Versionno")
			.put("C_Async_Batch_Type/540248.NotificationType.DisplayLogic", "IsSendNotification")
			.put("C_BPartner/123.C_BPartner_Location/222.Previous_ID.DisplayLogic", "ValidFrom")
			.put("C_BPartner/123.C_BPartner_Location/222.Previous_ID.MandatoryLogic", "ValidFrom")
			.put("C_BPartner/123.C_BPartner_Location/548422.Previous_ID.DisplayLogic", "ValidFrom")
			.put("C_BPartner/123.C_BPartner_Location/548422.Previous_ID.MandatoryLogic", "ValidFrom")
			.put("C_BankStatementLine_Ref/540355.C_Payment_ID.DisplayLogic", "IsMultiplePayment")
			.put("C_BankStatementLine_Ref/540355.C_Payment_ID.MandatoryLogic", "IsMultiplePayment")
			.put("C_BankStatementLine_Ref/540868.C_Payment_ID.DisplayLogic", "IsMultiplePayment")
			.put("C_BankStatementLine_Ref/540868.C_Payment_ID.MandatoryLogic", "IsMultiplePayment")
			.put("C_Country_Trl/540447.RegionName.DisplayLogic", "HasRegion")
			.put("C_Flatrate_Term/540359.C_Flatrate_DataEntry/540860.Date_Reported.ReadonlyLogic", "IsReadyForInvoicing")
			.put("C_Flatrate_Term/540359.C_Flatrate_DataEntry/540861.Date_Reported.ReadonlyLogic", "IsReadyForInvoicing")
			.put("C_Flatrate_Term/540359.C_Flatrate_DataEntry/540862.Date_Reported.ReadonlyLogic", "IsReadyForInvoicing")
			.put("C_HierarchyCommissionSettings/540742.IsSubtractLowerLevelCommissionFromBase.ReadonlyLogic", "IsConditionsProcessed")
			.put("C_Invoice/167.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Invoice/167.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Invoice/167.CreateAdjustmentCharge.DisplayLogic", "Ref_AdjustmentCharge_ID")
			.put("C_Invoice/167.CreateCreditMemo.DisplayLogic", "Ref_CreditMemo_ID")
			.put("C_Invoice/167.IncotermLocation.DisplayLogic", "Incoterm")
			.put("C_Invoice/183.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Invoice/183.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Invoice/183.IncotermLocation.DisplayLogic", "Incoterm")
			.put("C_Invoice/262.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Invoice/262.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Order/143.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Order/143.C_OrderLine/187.M_Warehouse_ID.DisplayLogic", "DirectShip")
			.put("C_Order/143.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Order/143.IsEdiEnabled.ReadonlyLogic", "IsEdiDesadvRecipient")
			.put("C_Order/181.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Order/181.C_OrderLine/293.M_Warehouse_ID.DisplayLogic", "DirectShip")
			.put("C_Order/181.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Order/181.IsEdiEnabled.ReadonlyLogic", "IsEdiDesadvRecipient")
			.put("C_Order/540072.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Order/540072.C_OrderLine/540212.AD_OrgTrx_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.C_Activity_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.C_Campaign_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.C_Charge_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.C_Project_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.C_Tax_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.C_Tax_ID.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.C_UOM_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.C_UOM_ID.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.DateOrdered.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.DateOrdered.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.DatePromised.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.Discount.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.FreightAmt.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.FreightAmt.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.M_AttributeSetInstance_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.M_Shipper_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.M_Warehouse_ID.DisplayLogic", "DirectShip")
			.put("C_Order/540072.C_OrderLine/540212.OrderDiscount.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.PriceActual.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.PriceActual.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.PriceList.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.PriceList.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyDelivered.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyDelivered.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyInvoiced.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyInvoiced.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyLostSales.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyLostSales.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyOrdered.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyOrdered.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyReserved.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyReserved.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.S_ResourceAssignment_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.User1_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.User2_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Order/540072.IncotermLocation.DisplayLogic", "Incoterm")
			.put("C_OrderLine/540019.C_Flatrate_Term_ID.MandatoryLogic", "DocSubType")
			.put("ESR_Import/540159.ESR_ImportLine/540443.C_BPartner_ID.ReadonlyLogic", "ErrorMsg")
			.put("Fact_Acct_Transactions_View/162.AD_OrgTrx_ID.DisplayLogic", "$Element_TO")
			.put("M_AttributeValue/540825.IsActive.ReadonlyLogic", "IsReadOnlyValues")
			.put("M_AttributeValue/540825.Name.ReadonlyLogic", "IsReadOnlyValues")
			.put("M_DiscountSchemaBreak/540612.DisplayLogic", "DiscountType")
			.put("M_DiscountSchemaBreak/540612.M_DiscountSchemaBreak_ID.DisplayLogic", "DiscountType")
			.put("M_DiscountSchemaBreak/540612.M_DiscountSchemaBreak_ID.MandatoryLogic", "DiscountType")
			.put("M_InOut/169.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/169.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/169.IncotermLocation.DisplayLogic", "Incoterm")
			.put("M_InOut/184.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/184.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/184.IncotermLocation.DisplayLogic", "Incoterm")
			.put("M_InOut/53097.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/53097.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/53098.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/53098.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/540322.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/540322.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/540323.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/540323.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/541014.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/541014.ChargeAmt.DisplayLogic", "HasCharges")
			.put("PP_Order/53009.PP_Order_Node/53036.IsMilestone.DisplayLogic", "WorkflowType")
			.put("PP_Order/53009.PP_Order_Node/53036.IsSubcontracting.DisplayLogic", "WorkflowType")
			.put("PP_Order/53009.PP_Order_Node/53036.S_Resource_ID.DisplayLogic", "WorkflowType")
			.put("PP_Product_BOMLine/540720.Feature.DisplayLogic", "BOMType")
			.put("PP_Product_BOMLine/540720.Forecast.DisplayLogic", "BOMUse")
			.put("PP_Product_Planning/540750.MaxManufacturedQtyPerOrderDispo_UOM_ID.MandatoryLogic", "MaxManufacturedQtyPerOrder")
			.put("S_Resource/236.M_Product/417.S_ExpenseType_ID.DisplayLogic", "ProductTypeType")
			.build();

	private static final Splitter COMMA_SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();

	@NonNull private final ImmutableSetMultimap<ContextPath, String> all;
	@NonNull private final TreeMultimap<ContextPath, String> reportedMissingButNotUsed;
	@NonNull private final TreeMultimap<ContextPath, String> foundMissing = TreeMultimap.create();
	@NonNull private final TreeMultimap<ContextPath, String> reportedMissingButExists = TreeMultimap.create();
	@Nullable @Setter private AdWindowIdSelection adWindowIdsInScope;

	@Builder
	private MissingContextVariables(
			@Nullable final Map<String, String> knownMissing)
	{
		final HashMultimap<ContextPath, String> all = HashMultimap.create();

		KNOWN_MISSING_CONTEXT_VARIABLES_DEFAULTS.forEach((pathStr, contextVariables) -> {
			final ContextPath path = ContextPath.ofJson(pathStr);
			COMMA_SPLITTER.splitToList(contextVariables)
					.forEach(missingContextVariable -> all.put(path, missingContextVariable));
		});

		if (knownMissing != null)
		{
			knownMissing.forEach((pathStr, contextVariables) -> {
				final ContextPath path = ContextPath.ofJson(pathStr);
				COMMA_SPLITTER.splitToList(contextVariables)
						.forEach(contextVariable -> all.put(path, contextVariable));
			});
		}

		this.all = ImmutableSetMultimap.copyOf(all);
		this.reportedMissingButNotUsed = TreeMultimap.create(this.all);
	}

	public void recordContextVariableUsed(final ContextPath path, final String contextVariable, boolean wasFound)
	{
		reportedMissingButNotUsed.remove(path, contextVariable);

		final boolean isKnownAsMissing = isKnownAsMissing(path, contextVariable);
		if (wasFound)
		{
			if (isKnownAsMissing)
			{
				reportedMissingButExists.put(path, contextVariable);
			}
		}
		else // not found
		{
			if (!isKnownAsMissing)
			{
				foundMissing.put(path, contextVariable);
			}
		}
	}

	public boolean isKnownAsMissing(final ContextPath path, String contextVariable)
	{
		return all.containsEntry(path, contextVariable);
	}

	public JsonContextVariablesResponse toJsonContextVariablesResponse()
	{
		return JsonContextVariablesResponse.builder()
				.missing(toKeyAndCommaSeparatedValues(foundMissing, adWindowIdsInScope))
				.reportedMissingButNotUsed(toKeyAndCommaSeparatedValues(reportedMissingButNotUsed, adWindowIdsInScope))
				.reportedMissingButExists(toKeyAndCommaSeparatedValues(reportedMissingButExists, adWindowIdsInScope))
				.build();
	}

	private static Map<String, String> toKeyAndCommaSeparatedValues(
			@NonNull final SetMultimap<ContextPath, String> multimap,
			@Nullable final AdWindowIdSelection adWindowIdsInScope)
	{
		final ImmutableMap.Builder<String, String> result = ImmutableMap.builder();
		multimap.asMap().forEach((path, values) -> {
			if (adWindowIdsInScope != null && !adWindowIdsInScope.contains(path))
			{
				return;
			}

			result.put(path.toJson(), Joiner.on(',').join(values));
		});
		return result.build();
	}

}
