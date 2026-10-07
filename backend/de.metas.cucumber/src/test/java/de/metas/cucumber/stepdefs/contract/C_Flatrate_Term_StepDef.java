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

package de.metas.cucumber.stepdefs.contract;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.IContractChangeBL.ContractChangeParameters;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateBL.ContractExtendingRequest;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.PMM_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static de.metas.contracts.model.I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID;
import static de.metas.contracts.model.I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID;
import static de.metas.contracts.model.I_C_Flatrate_Term.COLUMNNAME_C_OrderLine_Term_ID;
import static de.metas.contracts.model.I_C_Flatrate_Term.COLUMNNAME_DocStatus;
import static de.metas.contracts.model.I_C_Flatrate_Term.COLUMNNAME_DropShip_BPartner_ID;
import static de.metas.contracts.model.I_C_Flatrate_Term.COLUMNNAME_M_Product_ID;
import static de.metas.contracts.model.I_C_Flatrate_Term.COLUMNNAME_Processed;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.procurement.base.model.I_C_Flatrate_Term.COLUMNNAME_PMM_Product_ID;
import static org.assertj.core.api.Assertions.assertThat;

public class C_Flatrate_Term_StepDef
{
	private final C_BPartner_StepDefData bpartnerTable;
	private final M_Product_StepDefData productTable;
	private final C_Flatrate_Conditions_StepDefData conditionsTable;
	private final PMM_Product_StepDefData pmmProductTable;
	private final C_Flatrate_Term_StepDefData contractTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IContractChangeBL contractChangeBL = Services.get(IContractChangeBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public C_Flatrate_Term_StepDef(
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Flatrate_Conditions_StepDefData conditionsTable,
			@NonNull final PMM_Product_StepDefData pmmProductTable,
			@NonNull final C_Flatrate_Term_StepDefData contractTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable)
	{
		this.bpartnerTable = bpartnerTable;
		this.productTable = productTable;
		this.conditionsTable = conditionsTable;
		this.pmmProductTable = pmmProductTable;
		this.contractTable = contractTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
	}

	@Given("metasfresh contains C_Flatrate_Terms:")
	public void metasfresh_contains_c_flatrate_terms(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String billPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner billPartner = bpartnerTable.get(billPartnerIdentifier);

			final I_C_BPartner_Location billPartnerLocation = bPartnerDAO.retrieveBPartnerLocation(IBPartnerDAO.BPartnerLocationQuery.builder().bpartnerId(BPartnerId.ofRepoId(billPartner.getC_BPartner_ID()))
																										   .type(IBPartnerDAO.BPartnerLocationQuery.Type.BILL_TO)
																										   .build());
			assertThat(billPartnerLocation).isNotNull(); // guard

			final String conditionsIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Flatrate_Conditions_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Flatrate_Conditions conditions = conditionsTable.get(conditionsIdentifier);

			final I_C_Flatrate_Data flatrateData = flatrateDAO.retrieveOrCreateFlatrateData(billPartner);

			final I_C_Flatrate_Term contractRecord = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class);
			contractRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			
			contractRecord.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
			contractRecord.setC_UOM_ID(conditions.getC_UOM_ID());
			
			contractRecord.setC_Flatrate_Data_ID(flatrateData.getC_Flatrate_Data_ID());
			contractRecord.setBill_BPartner_ID(billPartner.getC_BPartner_ID());
			contractRecord.setBill_Location_ID(billPartnerLocation.getC_BPartner_Location_ID());

			final DocStatus docStatus = DocStatus.ofNullableCode(tableRow.get("OPT." + COLUMNNAME_DocStatus));
			contractRecord.setDocStatus(CoalesceUtil.coalesceNotNull(docStatus, DocStatus.Completed).getCode());
			// TODO change to be *not* processed and completed by default
			final boolean processed = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + COLUMNNAME_Processed, true);
			contractRecord.setProcessed(processed);

			final String dropshipPartnerIdentifier = tableRow.get("OPT." + COLUMNNAME_DropShip_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(dropshipPartnerIdentifier))
			{
				final I_C_BPartner dropshipPartner = bpartnerTable.get(dropshipPartnerIdentifier);
				contractRecord.setDropShip_BPartner_ID(dropshipPartner.getC_BPartner_ID());

				final I_C_BPartner_Location dropshipLocation = bPartnerDAO.retrieveBPartnerLocation(IBPartnerDAO.BPartnerLocationQuery.builder().bpartnerId(BPartnerId.ofRepoId(billPartner.getC_BPartner_ID()))
																											.type(IBPartnerDAO.BPartnerLocationQuery.Type.SHIP_TO)
																											.build());
				assertThat(dropshipLocation).isNotNull(); // guard
				contractRecord.setDropShip_Location_ID(dropshipLocation.getC_BPartner_Location_ID());
			}

			final String productIdentifier = tableRow.get("OPT." + COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(productIdentifier))
			{
				final I_M_Product product = productTable.get(productIdentifier);
				contractRecord.setM_Product_ID(product.getM_Product_ID());
			} 
			else 
			{
				contractRecord.setM_Product_ID(conditions.getM_Product_Flatrate_ID());
			}

			final String pmmProductIdentifier = tableRow.get("OPT." + COLUMNNAME_PMM_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(pmmProductIdentifier))
			{
				final I_PMM_Product pmmProductRecord = pmmProductTable.get(pmmProductIdentifier);

				final de.metas.procurement.base.model.I_C_Flatrate_Term prodcurementContractRecord = InterfaceWrapperHelper.create(contractRecord, de.metas.procurement.base.model.I_C_Flatrate_Term.class);
				prodcurementContractRecord.setPMM_Product_ID(pmmProductRecord.getPMM_Product_ID());
			}

			final Integer nrOfDaysFromNow = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT.NrOfDaysFromNow");

			if (nrOfDaysFromNow != null)
			{
				final Instant today = SystemTime.asLocalDate(SystemTime.zoneId()).atStartOfDay(SystemTime.zoneId()).toInstant();
				contractRecord.setStartDate(Timestamp.from(today.minus(1, ChronoUnit.DAYS)));
				contractRecord.setEndDate(Timestamp.from(today.plus(nrOfDaysFromNow, ChronoUnit.DAYS)));
			}
			else
			{
				contractRecord.setStartDate(DataTableUtil.extractDateTimestampForColumnName(tableRow, "StartDate"));
				final Timestamp endDate = DataTableUtil.extractDateTimestampForColumnNameOrNull(tableRow, "EndDate");
				if (endDate != null)
				{
					contractRecord.setEndDate(endDate);
				}
				else
				{
					flatrateBL.updateNoticeDateAndEndDate(contractRecord);
				}
			}

			final String orderLineIdentifier = tableRow.get("OPT." + COLUMNNAME_C_OrderLine_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderLineIdentifier))
			{
				final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
				contractRecord.setC_OrderLine_Term_ID(orderLine.getC_OrderLine_ID());
			}

			InterfaceWrapperHelper.saveRecord(contractRecord);

			contractTable.put(
					DataTableUtil.extractRecordIdentifier(tableRow, "C_FlatrateTerm"),
					contractRecord);
		}
	}

	/**
	 * Finds a previously created {@link I_C_Flatrate_Term} by conditions/bill-partner/product and asserts its
	 * fields, then registers it under an identifier for later reference.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Flatrate_Term_ID</b> — (required) alias to register the found record under<br>
	 *   <b>C_Flatrate_Conditions_ID</b> — (required, identifier-ref) conditions the term must reference<br>
	 *   <b>Bill_BPartner_ID</b> — (required, identifier-ref) bill-to partner the term must reference<br>
	 *   <b>M_Product_ID</b> — (required, identifier-ref) product the term must reference<br>
	 *   <b>OPT.C_OrderLine_Term_ID</b> — (optional, identifier-ref) expected linked order line<br>
	 *   <b>OPT.C_Order_Term_ID</b> — (optional, identifier-ref) expected linked order<br>
	 *   <b>OPT.C_UOM_ID.X12DE355</b> — (optional) expected UOM, by X12DE355 code<br>
	 *   <b>OPT.PriceActual</b> — (optional) expected price<br>
	 *   <b>OPT.PlannedQtyPerUnit</b> — (optional) expected planned qty per unit<br>
	 *   <b>OPT.EndDate</b> — (optional) expected end date<br>
	 *   <b>OPT.NoticeDate</b> — (optional) expected notice date<br>
	 * @cucumber.depends StepDefData: C_Flatrate_Conditions_StepDefData, C_BPartner_StepDefData, M_Product_StepDefData,
	 *   C_OrderLine_StepDefData, C_Order_StepDefData, C_Flatrate_Term_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And validate created C_Flatrate_Term:
	 *   | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.EndDate |
	 *   | contract_1                    | conditions_1                        | bpartner_1                  | product_1                | 2022-05-30  |
	 * </pre>
	 */
	@And("validate created C_Flatrate_Term:")
	public void validate_created_C_Flatrate_Term(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Flatrate_Conditions contractConditions = row.getAsIdentifier(COLUMNNAME_C_Flatrate_Conditions_ID).lookupNotNullIn(conditionsTable);
			final I_C_BPartner bPartner = row.getAsIdentifier(COLUMNNAME_Bill_BPartner_ID).lookupNotNullIn(bpartnerTable);
			final I_M_Product product = row.getAsIdentifier(COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);

			final I_C_Flatrate_Term contract = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(COLUMNNAME_C_Flatrate_Conditions_ID, contractConditions.getC_Flatrate_Conditions_ID())
					.addEqualsFilter(COLUMNNAME_Bill_BPartner_ID, bPartner.getC_BPartner_ID())
					.addEqualsFilter(COLUMNNAME_M_Product_ID, product.getM_Product_ID())
					.create()
					.firstOnlyNotNull(I_C_Flatrate_Term.class);

			row.getAsOptionalIdentifier(I_C_Flatrate_Term.COLUMNNAME_C_OrderLine_Term_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(identifier -> {
						final I_C_OrderLine orderLine = identifier.lookupNotNullIn(orderLineTable);
						assertThat(contract.getC_OrderLine_Term_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
					});

			row.getAsOptionalIdentifier(I_C_Flatrate_Term.COLUMNNAME_C_Order_Term_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(identifier -> {
						final I_C_Order order = identifier.lookupNotNullIn(orderTable);
						assertThat(contract.getC_Order_Term_ID()).isEqualTo(order.getC_Order_ID());
					});

			row.getAsOptionalUOMCode(I_C_Flatrate_Term.COLUMNNAME_C_UOM_ID)
					.ifPresent(uomCode -> {
						final UomId uomId = uomDAO.getUomIdByX12DE355(uomCode);
						assertThat(contract.getC_UOM_ID()).isEqualTo(uomId.getRepoId());
					});

			row.getAsOptionalBigDecimal(I_C_Flatrate_Term.COLUMNNAME_PriceActual)
					.ifPresent(priceActual -> assertThat(contract.getPriceActual()).isEqualTo(priceActual));

			row.getAsOptionalBigDecimal(I_C_Flatrate_Term.COLUMNNAME_PlannedQtyPerUnit)
					.ifPresent(plannedQtyPerUnit -> assertThat(contract.getPlannedQtyPerUnit()).isEqualTo(plannedQtyPerUnit));

			row.getAsOptionalLocalDate(I_C_Flatrate_Term.COLUMNNAME_EndDate)
					.ifPresent(endDate -> assertThat(TimeUtil.asLocalDate(contract.getEndDate())).as("EndDate").isEqualTo(endDate));

			row.getAsOptionalLocalDate(I_C_Flatrate_Term.COLUMNNAME_NoticeDate)
					.ifPresent(noticeDate -> assertThat(TimeUtil.asLocalDate(contract.getNoticeDate())).as("NoticeDate").isEqualTo(noticeDate));

			contractTable.put(row.getAsIdentifier(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID), contract);
		});
	}

	@And("^the C_Flatrate_Term identified by (.*) is completed$")
	public void the_C_Flatrate_Term_IsCompleted(@NonNull final String identifier)
	{
		final I_C_Flatrate_Term flatrateTermRecord = contractTable.get(identifier);
		assertThat(flatrateTermRecord).as("Missing C_Flatrate_Term with identifier %s", identifier).isNotNull();
		documentBL.processEx(flatrateTermRecord, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	/**
	 * Cancels the given {@link I_C_Flatrate_Term} at the given change date, via {@link IContractChangeBL#cancelContract}
	 * (the same business logic the "Vertrag Kündigen" / contract-change UI action invokes).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the C_Flatrate_Term identified by contract_1 is cancelled with change date 2022-06-15
	 * </pre>
	 */
	@And("^the C_Flatrate_Term identified by (.*) is cancelled with change date (.*)$")
	public void the_C_Flatrate_Term_is_cancelled(@NonNull final String identifier, @NonNull final String changeDateStr)
	{
		final I_C_Flatrate_Term flatrateTermRecord = contractTable.get(identifier);
		assertThat(flatrateTermRecord).as("Missing C_Flatrate_Term with identifier %s", identifier).isNotNull();

		final Timestamp changeDate = TimeUtil.asTimestamp(LocalDate.parse(changeDateStr));

		final ContractChangeParameters contractChangeParameters = ContractChangeParameters.builder()
				.changeDate(changeDate)
				.build();

		contractChangeBL.cancelContract(flatrateTermRecord, contractChangeParameters);
	}

	/**
	 * Asserts that completing the given {@link I_C_Flatrate_Term} is REFUSED, and that it is refused for the
	 * expected reason: the thrown {@link org.adempiere.exceptions.AdempiereException} must carry the given
	 * error code. Locale-independent — mirrors {@code C_Order_StepDef#order_cannot_be_completed_because_of_error_code}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the C_Flatrate_Term identified by contract_1 cannot be completed because of error code de.metas.flatrate.process.C_Flatrate_Term_Create.OverlappingTerm
	 * </pre>
	 */
	@And("^the C_Flatrate_Term identified by (.*) cannot be completed because of error code (.*)$")
	public void the_C_Flatrate_Term_cannot_be_completed_because_of_error_code(
			@NonNull final String identifier,
			@NonNull final String errorCode)
	{
		final I_C_Flatrate_Term flatrateTermRecord = contractTable.get(identifier);
		assertThat(flatrateTermRecord).as("Missing C_Flatrate_Term with identifier %s", identifier).isNotNull();

		StepDefUtil.assertRefusedWithErrorCode(errorCode, () -> documentBL.processEx(flatrateTermRecord, IDocument.ACTION_Complete, IDocument.STATUS_Completed));
	}

	/**
	 * Runs the scheduled contract extension ("Vertragsverlängerung", process {@code C_Flatrate_Term_Extend_And_Notify_User}
	 * started without a record) for the given term.
	 * <p>
	 * In production the scheduler runs that process over all eligible terms of the client. This step does not run the process
	 * itself, because it would also extend other scenarios' leftover terms of the shared test DB. Instead it
	 * <ul>
	 *     <li>asserts that the term matches the process's selection (active, completed, not yet processed by an extension run,
	 *     notice date before "now", not quit or voided), and</li>
	 *     <li>calls {@link IFlatrateBL#extendContractAndNotifyUser} with the request the process builds for each selected term
	 *     ({@code forceExtend=false}, no forced completion, no explicit start date), in a new transaction, as the process does.</li>
	 * </ul>
	 * The step fails if the extension throws.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the scheduled contract extension runs for the C_Flatrate_Term identified by contract_1
	 * </pre>
	 */
	@And("^the scheduled contract extension runs for the C_Flatrate_Term identified by (.*)$")
	public void the_scheduled_contract_extension_runs(@NonNull final String identifier)
	{
		final RuntimeException error = runScheduledContractExtension(identifier);
		if (error != null)
		{
			throw error;
		}
	}

	/**
	 * Like {@link #the_scheduled_contract_extension_runs(String)}, but asserts that the extension of the given term FAILS, with
	 * an error message containing the given text. The process logs such an error per term and carries on with the next one.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the scheduled contract extension fails for the C_Flatrate_Term identified by contract_1 with message containing EndDate
	 * </pre>
	 */
	@And("^the scheduled contract extension fails for the C_Flatrate_Term identified by (.*) with message containing (.*)$")
	public void the_scheduled_contract_extension_fails(@NonNull final String identifier, @NonNull final String expectedMessagePart)
	{
		final RuntimeException error = runScheduledContractExtension(identifier);
		assertThat(error).as("Extension of C_Flatrate_Term %s was expected to fail", identifier).isNotNull();
		assertThat(error.getMessage()).as("Error message of the failed extension").contains(expectedMessagePart);
	}

	@Nullable
	private RuntimeException runScheduledContractExtension(@NonNull final String identifier)
	{
		final I_C_Flatrate_Term termToExtend = loadFresh(identifier);

		// the selection of C_Flatrate_Term_Extend_And_Notify_User.extendAllEligibleTerms
		assertThat(termToExtend.isActive()).as("IsActive").isTrue();
		assertThat(termToExtend.getDocStatus()).as("DocStatus").isEqualTo(IDocument.STATUS_Completed);
		assertThat(termToExtend.getAD_PInstance_EndOfTerm_ID()).as("AD_PInstance_EndOfTerm_ID").isLessThanOrEqualTo(0);
		assertThat(termToExtend.getNoticeDate()).as("NoticeDate").isNotNull();
		assertThat(termToExtend.getNoticeDate()).as("NoticeDate must be before now").isBefore(SystemTime.asTimestamp());
		assertThat(termToExtend.getContractStatus()).as("ContractStatus")
				.isNotIn(X_C_Flatrate_Term.CONTRACTSTATUS_Quit, X_C_Flatrate_Term.CONTRACTSTATUS_Voided);

		final ContractExtendingRequest request = ContractExtendingRequest.builder()
				.contract(termToExtend)
				.forceExtend(false)
				.forceComplete(null)
				.nextTermStartDate(null)
				.build();

		final RuntimeException[] error = new RuntimeException[1];
		trxManager.runInNewTrx(() -> {
			try
			{
				flatrateBL.extendContractAndNotifyUser(request);
			}
			catch (final RuntimeException e)
			{
				error[0] = e; // like the process: catch per term, so its transaction is not rolled back as a whole
			}
		});
		return error[0];
	}

	/**
	 * Asserts the follow-up term which the contract extension created for the given term (the term's {@code C_FlatrateTerm_Next_ID}),
	 * and registers it under the given identifier.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias to register the follow-up term under<br>
	 *   <b>StartDate</b> — (optional) expected start date<br>
	 *   <b>EndDate</b> — (optional) expected end date<br>
	 *   <b>NoticeDate</b> — (optional) expected notice date<br>
	 *   <b>DocStatus</b> — (optional) expected document status<br>
	 *   <b>IsAutoRenew</b> — (optional) expected auto-renew flag<br>
	 *   <b>C_Flatrate_Conditions_ID</b> — (optional, identifier-ref) expected conditions<br>
	 *   <b>Bill_BPartner_ID</b> — (optional, identifier-ref) expected invoice partner<br>
	 * @cucumber.depends StepDefData: C_Flatrate_Term_StepDefData, C_Flatrate_Conditions_StepDefData, C_BPartner_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the C_Flatrate_Term identified by contract_1 has the follow-up C_Flatrate_Term:
	 *   | Identifier | StartDate  | EndDate    | DocStatus | IsAutoRenew |
	 *   | contract_2 | 2027-01-01 | 2027-12-31 | CO        | true        |
	 * </pre>
	 */
	@And("^the C_Flatrate_Term identified by (.*) has the follow-up C_Flatrate_Term:$")
	public void the_C_Flatrate_Term_has_follow_up_term(@NonNull final String identifier, @NonNull final DataTable dataTable)
	{
		final I_C_Flatrate_Term term = loadFresh(identifier);
		assertThat(term.getC_FlatrateTerm_Next_ID()).as("C_FlatrateTerm_Next_ID of C_Flatrate_Term %s", identifier).isGreaterThan(0);

		final I_C_Flatrate_Term nextTerm = flatrateDAO.getById(FlatrateTermId.ofRepoId(term.getC_FlatrateTerm_Next_ID()));

		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		row.getAsOptionalLocalDate(I_C_Flatrate_Term.COLUMNNAME_StartDate)
				.ifPresent(startDate -> assertThat(TimeUtil.asLocalDate(nextTerm.getStartDate())).as("StartDate").isEqualTo(startDate));
		row.getAsOptionalLocalDate(I_C_Flatrate_Term.COLUMNNAME_EndDate)
				.ifPresent(endDate -> assertThat(TimeUtil.asLocalDate(nextTerm.getEndDate())).as("EndDate").isEqualTo(endDate));
		row.getAsOptionalLocalDate(I_C_Flatrate_Term.COLUMNNAME_NoticeDate)
				.ifPresent(noticeDate -> assertThat(TimeUtil.asLocalDate(nextTerm.getNoticeDate())).as("NoticeDate").isEqualTo(noticeDate));
		row.getAsOptionalString(COLUMNNAME_DocStatus)
				.ifPresent(docStatus -> assertThat(nextTerm.getDocStatus()).as("DocStatus").isEqualTo(docStatus));
		row.getAsOptionalBoolean(I_C_Flatrate_Term.COLUMNNAME_IsAutoRenew)
				.ifPresent(isAutoRenew -> assertThat(nextTerm.isAutoRenew()).as("IsAutoRenew").isEqualTo(isAutoRenew));
		row.getAsOptionalIdentifier(COLUMNNAME_C_Flatrate_Conditions_ID)
				.ifPresent(conditionsId -> assertThat(nextTerm.getC_Flatrate_Conditions_ID()).as("C_Flatrate_Conditions_ID")
						.isEqualTo(conditionsId.lookupNotNullIn(conditionsTable).getC_Flatrate_Conditions_ID()));
		row.getAsOptionalIdentifier(COLUMNNAME_Bill_BPartner_ID)
				.ifPresent(bpartnerId -> assertThat(nextTerm.getBill_BPartner_ID()).as("Bill_BPartner_ID")
						.isEqualTo(bpartnerId.lookupNotNullIn(bpartnerTable).getC_BPartner_ID()));

		contractTable.putOrReplace(row.getAsIdentifier(), nextTerm);
	}

	/**
	 * Asserts that the given term was not extended: it has no {@code C_FlatrateTerm_Next_ID}, and no other term exists for its
	 * invoice partner and conditions (so no half-created follow-up term was left behind either).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the C_Flatrate_Term identified by contract_1 has no follow-up C_Flatrate_Term
	 * </pre>
	 */
	@And("^the C_Flatrate_Term identified by (.*) has no follow-up C_Flatrate_Term$")
	public void the_C_Flatrate_Term_has_no_follow_up_term(@NonNull final String identifier)
	{
		final I_C_Flatrate_Term term = loadFresh(identifier);
		assertThat(term.getC_FlatrateTerm_Next_ID()).as("C_FlatrateTerm_Next_ID of C_Flatrate_Term %s", identifier).isLessThanOrEqualTo(0);

		final List<Integer> otherTermIds = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addEqualsFilter(COLUMNNAME_Bill_BPartner_ID, term.getBill_BPartner_ID())
				.addEqualsFilter(COLUMNNAME_C_Flatrate_Conditions_ID, term.getC_Flatrate_Conditions_ID())
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID, term.getC_Flatrate_Term_ID())
				.create()
				.listIds();
		assertThat(otherTermIds).as("Other C_Flatrate_Terms of the same invoice partner and conditions").isEmpty();
	}

	private I_C_Flatrate_Term loadFresh(@NonNull final String identifier)
	{
		final I_C_Flatrate_Term term = contractTable.get(identifier);
		assertThat(term).as("Missing C_Flatrate_Term with identifier %s", identifier).isNotNull();
		return flatrateDAO.getById(FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID()));
	}

	@And("^the C_Flatrate_Term identified by (.*) has (.*) C_Flatrate_DataEntries.$")
	public void the_C_Flatrate_Term_has_DataEntries(@NonNull final String identifier, final String countStr)
	{
		final int count = NumberUtils.asInt(countStr);
		
		final I_C_Flatrate_Term flatrateTermRecord = contractTable.get(identifier);
		assertThat(flatrateTermRecord)
				.as("Missing C_Flatrate_Term record for identifier=%s", identifier)
				.isNotNull();

		final List<I_C_Flatrate_DataEntry> list = queryBL.createQueryBuilder(I_C_Flatrate_DataEntry.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_DataEntry.COLUMNNAME_C_Flatrate_Term_ID, flatrateTermRecord.getC_Flatrate_Term_ID())
				.create()
				.list();

		assertThat(list)
				.as("Number of C_Flatrate_DataEntry record for C_Flatrate_Term_ID=%s - identifier=%s", flatrateTermRecord.getC_Flatrate_Term_ID(), identifier)
				.hasSize(count);
	}

}
