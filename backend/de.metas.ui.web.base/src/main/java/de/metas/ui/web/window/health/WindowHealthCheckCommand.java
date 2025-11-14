package de.metas.ui.web.window.health;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.logging.LogManager;
import de.metas.rest_api.utils.v2.JsonErrors;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.exceptions.DocumentLayoutBuildException;
import de.metas.ui.web.window.health.json.JsonWindowHealthCheckRequest;
import de.metas.ui.web.window.health.json.JsonWindowsHealthCheckResponse;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class WindowHealthCheckCommand
{
	private static final ImmutableSet<AdWindowId> SKIP_WINDOW_IDS = ImmutableSet.of(
			AdWindowId.ofRepoId(540371), // Picking Tray Clearing - placeholder window
			AdWindowId.ofRepoId(540674), // Shipment Schedule Editor - placeholder window
			AdWindowId.ofRepoId(540759), // Payment Allocation - placeholder window
			AdWindowId.ofRepoId(540485) // Picking Terminal (v2) - placeholder window
	);

	private static final ImmutableSet<String> KNOWN_CONTEXT_VARIABLES_DEFAULTS = ImmutableSet.<String>builder()
			.addAll(ImmutableSet.of(
					"#AD_User_ID",
					"#AD_Role_ID",
					"#AD_Client_ID",
					"#Date",
					"#IsLiberoEnabled",
					"$C_AcctSchema_ID",
					"$C_Currency_ID",
					"$HasAlias"
			))
			.addAll(Stream.of(AcctSchemaElementType.values())
					.map(elementType -> "$Element_" + elementType.getCode())
					.collect(ImmutableSet.toImmutableSet()))
			.build();

	private static final ImmutableSetMultimap<String, String> KNOWN_MISSING_CONTEXT_VARIABLES_DEFAULTS = ImmutableSetMultimap.<String, String>builder()
			.put("AD_Process/245.IsOneInstanceOnly.DisplayLogic", "IsServerProcess")
			.put("AD_Process/245.IsOneInstanceOnly.MandatoryLogic", "IsServerProcess")
			.put("AD_Sequence/146.DateColumn.MandatoryLogic", "StartNewYear")
			.put("A_Asset/450.A_Asset_Change/53158.AD_User_ID.DisplayLogic", "Ad_User_ID")
			.put("A_Asset/450.A_Asset_Change/53158.C_BPartner_ID.DisplayLogic", "C_Bpartner_ID")
			.put("A_Asset/450.A_Asset_Change/53158.SerNo.DisplayLogic", "Serno")
			.put("A_Asset/450.A_Asset_Change/53158.VersionNo.DisplayLogic", "Versionno")
			.put("C_Async_Batch_Type/540640.NotificationType.DisplayLogic", "IsSendNotification")
			.put("C_BPartner/220.C_BPartner_Location/222.Previous_ID.DisplayLogic", "ValidFrom")
			.put("C_BPartner/220.C_BPartner_Location/222.Previous_ID.MandatoryLogic", "ValidFrom")
			.put("C_BPartner/220.C_BPartner_Location/548422.Previous_ID.DisplayLogic", "ValidFrom")
			.put("C_BPartner/220.C_BPartner_Location/548422.Previous_ID.MandatoryLogic", "ValidFrom")
			.put("C_BankStatementLine_Ref/540853.C_Payment_ID.DisplayLogic", "IsMultiplePayment")
			.put("C_BankStatementLine_Ref/540853.C_Payment_ID.MandatoryLogic", "IsMultiplePayment")
			.put("C_BankStatementLine_Ref/542322.C_Payment_ID.DisplayLogic", "IsMultiplePayment")
			.put("C_BankStatementLine_Ref/542322.C_Payment_ID.MandatoryLogic", "IsMultiplePayment")
			.put("C_Country_Trl/541150.RegionName.DisplayLogic", "HasRegion")
			.put("C_Flatrate_Term/540859.C_Flatrate_DataEntry/540860.Date_Reported.ReadonlyLogic", "IsReadyForInvoicing")
			.put("C_Flatrate_Term/540859.C_Flatrate_DataEntry/540861.Date_Reported.ReadonlyLogic", "IsReadyForInvoicing")
			.put("C_Flatrate_Term/540859.C_Flatrate_DataEntry/540862.Date_Reported.ReadonlyLogic", "IsReadyForInvoicing")
			.put("C_HierarchyCommissionSettings/542066.IsSubtractLowerLevelCommissionFromBase.ReadonlyLogic", "IsConditionsProcessed")
			.put("C_Invoice/263.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Invoice/263.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Invoice/263.CreateAdjustmentCharge.DisplayLogic", "Ref_AdjustmentCharge_ID")
			.put("C_Invoice/263.CreateCreditMemo.DisplayLogic", "Ref_CreditMemo_ID")
			.put("C_Invoice/263.IncotermLocation.DisplayLogic", "Incoterm")
			.put("C_Invoice/290.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Invoice/290.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Invoice/290.IncotermLocation.DisplayLogic", "Incoterm")
			.put("C_Invoice/470.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Invoice/470.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Order/186.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Order/186.C_OrderLine/187.M_Warehouse_ID.DisplayLogic", "DirectShip")
			.put("C_Order/186.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Order/186.IsEdiEnabled.ReadonlyLogic", "IsEdiDesadvRecipient")
			.put("C_Order/294.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Order/294.C_OrderLine/293.M_Warehouse_ID.DisplayLogic", "DirectShip")
			.put("C_Order/294.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Order/294.IsEdiEnabled.ReadonlyLogic", "IsEdiDesadvRecipient")
			.put("C_Order/540211.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Order/540211.C_OrderLine/540212.AD_OrgTrx_ID.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.C_Activity_ID.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.C_Campaign_ID.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.C_Charge_ID.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.C_Project_ID.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.C_Tax_ID.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.C_Tax_ID.MandatoryLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.C_UOM_ID.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.C_UOM_ID.MandatoryLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.DateOrdered.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.DateOrdered.MandatoryLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.DatePromised.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.Discount.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.FreightAmt.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.FreightAmt.MandatoryLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.M_AttributeSetInstance_ID.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.M_Shipper_ID.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.M_Warehouse_ID.DisplayLogic", "DirectShip")
			.put("C_Order/540211.C_OrderLine/540212.OrderDiscount.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.PriceActual.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.PriceActual.MandatoryLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.PriceList.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.PriceList.MandatoryLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.QtyDelivered.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.QtyDelivered.MandatoryLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.QtyInvoiced.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.QtyInvoiced.MandatoryLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.QtyLostSales.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.QtyLostSales.MandatoryLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.QtyOrdered.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.QtyOrdered.MandatoryLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.QtyReserved.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.QtyReserved.MandatoryLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.S_ResourceAssignment_ID.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.User1_ID.DisplayLogic", "IsComment")
			.put("C_Order/540211.C_OrderLine/540212.User2_ID.DisplayLogic", "IsComment")
			.put("C_Order/540211.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Order/540211.IncotermLocation.DisplayLogic", "Incoterm")
			.put("C_OrderLine/540094.C_Flatrate_Term_ID.MandatoryLogic", "DocSubType")
			.put("ESR_Import/540442.ESR_ImportLine/540443.C_BPartner_ID.ReadonlyLogic", "ErrorMsg")
			.put("Fact_Acct_Transactions_View/242.AD_OrgTrx_ID.DisplayLogic", "$Element_TO")
			.put("M_AttributeValue/542161.IsActive.ReadonlyLogic", "IsReadOnlyValues")
			.put("M_AttributeValue/542161.Name.ReadonlyLogic", "IsReadOnlyValues")
			.put("M_DiscountSchemaBreak/541683.DisplayLogic", "DiscountType")
			.put("M_DiscountSchemaBreak/541683.M_DiscountSchemaBreak_ID.DisplayLogic", "DiscountType")
			.put("M_DiscountSchemaBreak/541683.M_DiscountSchemaBreak_ID.MandatoryLogic", "DiscountType")
			.put("M_InOut/257.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/257.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/257.IncotermLocation.DisplayLogic", "Incoterm")
			.put("M_InOut/296.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/296.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/296.IncotermLocation.DisplayLogic", "Incoterm")
			.put("M_InOut/53271.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/53271.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/53276.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/53276.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/540778.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/540778.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/540782.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/540782.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/543272.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/543272.ChargeAmt.DisplayLogic", "HasCharges")
			.put("PP_Order/53054.PP_Order_Node/53036.IsMilestone.DisplayLogic", "WorkflowType")
			.put("PP_Order/53054.PP_Order_Node/53036.IsSubcontracting.DisplayLogic", "WorkflowType")
			.put("PP_Order/53054.PP_Order_Node/53036.S_Resource_ID.DisplayLogic", "WorkflowType")
			.put("PP_Product_BOMLine/542034.Feature.DisplayLogic", "BOMType")
			.put("PP_Product_BOMLine/542034.Forecast.DisplayLogic", "BOMUse")
			.put("PP_Product_Planning/542102.MaxManufacturedQtyPerOrderDispo_UOM_ID.MandatoryLogic", "MaxManufacturedQtyPerOrder")
			.put("S_Resource/414.M_Product/417.S_ExpenseType_ID.DisplayLogic", "ProductTypeType")
			.build();

	private static final Joiner PATH_JOINER = Joiner.on(".").skipNulls();

	//
	// Services
	@NonNull private static final Logger logger = LogManager.getLogger(WindowHealthCheckCommand.class);
	@NonNull private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
	@NonNull private final DocumentDescriptorFactory documentDescriptorFactory;

	//
	// Params
	@NonNull private final String adLanguage;
	@Nullable private final ImmutableSet<AdWindowId> onlyAdWindowIds;
	@NonNull private final ImmutableSet<String> knownContextVariables;
	@NonNull private final ImmutableSetMultimap<String, String> knownMissingContextVariables;

	//
	// State
	private AdWindowId currentWindowId;
	private String currentWindowName;
	private final ArrayList<JsonWindowsHealthCheckResponse.Entry> errors = new ArrayList<>();
	private final HashMultimap<String, String> foundMissingContextVariables = HashMultimap.create();

	@Builder
	private WindowHealthCheckCommand(
			@NonNull final DocumentDescriptorFactory documentDescriptorFactory,
			@NonNull final JsonWindowHealthCheckRequest request)
	{
		this.documentDescriptorFactory = documentDescriptorFactory;
		this.adLanguage = Env.getADLanguageOrBaseLanguage();
		this.onlyAdWindowIds = request.getOnlyAdWindowIds();
		this.knownMissingContextVariables = computeKnownMissingContextVariables(request);
		this.knownContextVariables = computeKnownContextVariables(request);
	}

	private static ImmutableSet<String> computeKnownContextVariables(@NonNull final JsonWindowHealthCheckRequest request)
	{
		final ImmutableSet.Builder<String> result = ImmutableSet.builder();
		result.addAll(KNOWN_CONTEXT_VARIABLES_DEFAULTS);

		if (request.getKnownContextVariables() != null)
		{
			result.addAll(request.getKnownContextVariables());
		}

		return result.build();
	}

	private static ImmutableSetMultimap<String, String> computeKnownMissingContextVariables(@NonNull final JsonWindowHealthCheckRequest request)
	{
		final HashMultimap<String, String> result = HashMultimap.create();
		result.putAll(KNOWN_MISSING_CONTEXT_VARIABLES_DEFAULTS);

		final Map<String, Set<String>> knownMissingContextVariables = request.getKnownMissingContextVariables();
		if (knownMissingContextVariables != null)
		{
			knownMissingContextVariables.forEach((path, missingContextVariables) -> {
				missingContextVariables.forEach(missingContextVariable -> result.put(path, missingContextVariable));
			});
		}

		return ImmutableSetMultimap.copyOf(result);
	}

	public JsonWindowsHealthCheckResponse execute()
	{
		final ImmutableSet<AdWindowId> allAdWidowIds = adWindowDAO.retrieveAllActiveAdWindowIds();
		final ImmutableSet<AdWindowId> adWindowIds = onlyAdWindowIds != null && !onlyAdWindowIds.isEmpty() ? onlyAdWindowIds : allAdWidowIds;

		final int countTotal = adWindowIds.size();
		int countCurrent = 0;
		final Stopwatch stopwatch = Stopwatch.createStarted();
		for (final AdWindowId adWindowId : adWindowIds)
		{
			this.currentWindowId = adWindowId;
			countCurrent++;

			if (SKIP_WINDOW_IDS.contains(adWindowId))
			{
				continue;
			}

			final WindowId windowId = WindowId.of(adWindowId);
			try
			{
				if (!allAdWidowIds.contains(adWindowId))
				{
					throw new AdempiereException("Not an existing/active window");
				}

				documentDescriptorFactory.invalidateForWindow(windowId);
				final DocumentDescriptor documentDescriptor = documentDescriptorFactory.getDocumentDescriptor(windowId);
				documentDescriptorFactory.invalidateForWindow(windowId);

				checkExpressions(null, documentDescriptor.getEntityDescriptor(), knownContextVariables);

				final String windowName = this.currentWindowName = documentDescriptor.getEntityDescriptor().getCaption().translate(adLanguage);
				logger.info("testWindows [{}/{}] Window `{}` ({}) is OK", countCurrent, countTotal, windowName, windowId);
			}
			catch (final Exception ex)
			{
				final String windowName = this.currentWindowName = adWindowDAO.retrieveWindowName(adWindowId).translate(adLanguage);
				logger.info("testWindows [{}/{}] Window `{}` ({}) is NOK: {}", countCurrent, countTotal, windowName, windowId, ex.getLocalizedMessage());

				collectError(DocumentLayoutBuildException.extractCause(ex));
			}
		}

		stopwatch.stop();

		dumpFoundMissingContextVariables();

		return JsonWindowsHealthCheckResponse.builder()
				.took(stopwatch.toString())
				.countTotal(countTotal)
				.errors(errors)
				.build();
	}

	private void dumpFoundMissingContextVariables()
	{
		if (foundMissingContextVariables.isEmpty()) {return;}

		System.out.println("Found following missing context variables: ");

		foundMissingContextVariables.asMap().entrySet().stream()
				.sorted(Map.Entry.comparingByKey()) // sort by path
				.forEach(entry -> {
					entry.getValue().stream()
							.sorted() // sort context variables
							.forEach(missingContextVariable ->
									System.out.println(".put(\"" + entry.getKey() + "\", \"" + missingContextVariable + "\")")
							);
				});
	}

	private void collectError(final Throwable exception)
	{
		final String windowName = this.currentWindowName != null
				? this.currentWindowName
				: adWindowDAO.retrieveWindowName(currentWindowId).translate(adLanguage);

		errors.add(JsonWindowsHealthCheckResponse.Entry.builder()
				.windowId(WindowId.of(currentWindowId))
				.windowName(windowName)
				.error(JsonErrors.ofThrowable(exception, adLanguage))
				.build());

	}

	private void checkExpressions(
			@Nullable final String parentPath,
			@NonNull final DocumentEntityDescriptor entityDescriptor,
			@NonNull final ImmutableSet<String> parentContextVariables)
	{
		try
		{
			final String path = PATH_JOINER.join(parentPath, entityDescriptor.getTableNameOrNull() + "/" + entityDescriptor.getAdTabId().getRepoId());

			//
			// Entity check:
			{
				checkExpression(path, "AllowCreateNewLogic", entityDescriptor.getAllowCreateNewLogic(), parentContextVariables);
				checkExpression(path, "AllowDeleteLogic", entityDescriptor.getAllowDeleteLogic(), parentContextVariables);
				checkExpression(path, "ReadonlyLogic", entityDescriptor.getReadonlyLogic(), parentContextVariables);
				checkExpression(path, "DisplayLogic", entityDescriptor.getDisplayLogic(), parentContextVariables);
			}

			final ImmutableSet<String> contextVariables = ImmutableSet.<String>builder()
					.addAll(extractFieldNames(entityDescriptor))
					.addAll(parentContextVariables)
					.build();

			//
			// Fields check:
			{
				for (final DocumentFieldDescriptor field : entityDescriptor.getFields())
				{
					final String fieldPath = PATH_JOINER.join(path, field.getFieldName());
					checkExpression(fieldPath, "ReadonlyLogic", field.getReadonlyLogic(), contextVariables);
					checkExpression(fieldPath, "DisplayLogic", field.getDisplayLogic(), contextVariables);
					checkExpression(fieldPath, "MandatoryLogic", field.getMandatoryLogic(), contextVariables);
				}
			}

			//
			// Included tabs
			for (final DocumentEntityDescriptor includedEntityDescriptor : entityDescriptor.getIncludedEntities())
			{
				checkExpressions(path, includedEntityDescriptor, contextVariables);
			}
		}
		catch (Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("TableName", entityDescriptor.getTableNameOrNull())
					.setParameter("AD_Tab_ID", entityDescriptor.getAdTabId());
		}
	}

	private static ImmutableSet<String> extractFieldNames(final DocumentEntityDescriptor entityDescriptor)
	{
		return entityDescriptor.getFields().stream().map(DocumentFieldDescriptor::getFieldName).collect(ImmutableSet.toImmutableSet());
	}

	private void checkExpression(
			@NonNull final String parentPath,
			@NonNull final String name,
			@Nullable final ILogicExpression expression,
			@NonNull final ImmutableSet<String> contextVariables)
	{
		if (expression == null || expression.isConstant())
		{
			return;
		}

		try
		{
			final String path = PATH_JOINER.join(parentPath, name);

			for (final String parameterName : expression.getParameterNames())
			{
				if (knownMissingContextVariables.containsEntry(path, parameterName))
				{
					continue;
				}

				if (contextVariables.contains(parameterName))
				{
					continue;
				}

				String message = "Context variable `" + parameterName + "` in " + path + ".";
				final String suggestedParameterName = findLenient(contextVariables, parameterName);
				if (suggestedParameterName != null)
				{
					message += " You might want to use `" + suggestedParameterName + "` instead.";
				}
				foundMissingContextVariables.put(path, parameterName);
				throw new AdempiereException(message)
						.setParameter("expression", expression)
						.setParameter("contextVariables", String.join(",", contextVariables));
			}
		}
		catch (Exception ex)
		{
			collectError(ex);
		}
	}

	private String findLenient(Collection<String> collection, String element)
	{
		for (String item : collection)
		{
			if (item.equalsIgnoreCase(element))
			{
				return item;
			}
		}

		return null;
	}
}
