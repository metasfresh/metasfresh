package de.metas.cucumber.stepdefs.factacct;

import de.metas.acct.api.IPostingRequestBuilder;
import de.metas.acct.api.IPostingService;
import de.metas.acct.api.PostingType;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_Currency_StepDefData;
import de.metas.cucumber.stepdefs.C_ElementValue_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.TableType;
import de.metas.cucumber.stepdefs.activity.C_Activity_StepDefData;
import de.metas.cucumber.stepdefs.calendar.C_Calendar_StepDefData;
import de.metas.cucumber.stepdefs.calendar.C_Year_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.matchinv.M_MatchInv_StepDefData;
import de.metas.cucumber.stepdefs.project.C_Project_StepDefData;
import de.metas.cucumber.stepdefs.sectioncode.M_SectionCode_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.cucumber.stepdefs.shippingNotification.M_Shipping_Notification_StepDefData;
import de.metas.money.CurrencyId;
import de.metas.product.ProductId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.LocatorId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_SectionCode;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class Fact_Acct_StepDef
{
	private static final String TABLE_COLUMN_ACCOUNT_FEATURE = "Account";
	private static final String TABLE_COLUMN_DR_FEATURE = "DR";
	private static final String TABLE_COLUMN_CR_FEATURE = "CR";

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IPostingService postingService = Services.get(IPostingService.class);

	private final M_SectionCode_StepDefData sectionCodeTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final C_Project_StepDefData projectTable;
	private final C_Activity_StepDefData activityTable;
	private final M_MatchInv_StepDefData matchInvTable;
	private final M_InOut_StepDefData inoutTable;
	private final Fact_Acct_StepDefData factAcctTable;
	private final C_ElementValue_StepDefData elementValueTable;
	private final C_Currency_StepDefData currencyTable;
	private final C_Calendar_StepDefData calendarTable;
	private final C_Year_StepDefData yearTable;
	private final M_Shipping_Notification_StepDefData shippingNotificationTable;
	private final M_Product_StepDefData productTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final M_Locator_StepDefData locatorTable;

	public Fact_Acct_StepDef(
			@NonNull final M_SectionCode_StepDefData sectionCodeTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_Project_StepDefData projectTable,
			@NonNull final C_Activity_StepDefData activityTable,
			@NonNull final M_MatchInv_StepDefData matchInvTable,
			@NonNull final Fact_Acct_StepDefData factAcctTable,
			@NonNull final C_ElementValue_StepDefData elementValueTable,
			@NonNull final C_Currency_StepDefData currencyTable,
			@NonNull final C_Calendar_StepDefData calendarTable,
			@NonNull final C_Year_StepDefData yearTable,
			@NonNull final M_InOut_StepDefData inoutTable,
			@NonNull final M_Shipping_Notification_StepDefData shippingNotificationTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final M_Locator_StepDefData locatorTable)
	{
		this.sectionCodeTable = sectionCodeTable;
		this.invoiceTable = invoiceTable;
		this.projectTable = projectTable;
		this.activityTable = activityTable;
		this.matchInvTable = matchInvTable;
		this.factAcctTable = factAcctTable;
		this.elementValueTable = elementValueTable;
		this.currencyTable = currencyTable;
		this.calendarTable = calendarTable;
		this.yearTable = yearTable;
		this.inoutTable = inoutTable;
		this.shippingNotificationTable = shippingNotificationTable;
		this.productTable = productTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.locatorTable = locatorTable;
	}

	@And("^after not more than (.*)s, Fact_Acct are found$")
	public void find_Fact_Acct(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final SoftAssertions softly = new SoftAssertions();

			final List<I_Fact_Acct> factAcctRecords = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> load_Fact_Acct(row));

			final String sectionCodeIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_SectionCode.COLUMNNAME_M_SectionCode_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(sectionCodeIdentifier))
			{
				final I_M_SectionCode sectionCode = sectionCodeTable.get(sectionCodeIdentifier);
				final boolean allFactAcctRecordsMatch = factAcctRecords.stream()
						.allMatch(factAcct -> sectionCode.getM_SectionCode_ID() == factAcct.getM_SectionCode_ID());

				softly.assertThat(allFactAcctRecordsMatch).isTrue();
			}

			final String postingType = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_Fact_Acct.COLUMNNAME_PostingType);
			if (Check.isNotBlank(postingType))
			{
				final PostingType type = PostingType.valueOf(postingType);

				final boolean allFactAcctRecordsMatchPostingType = factAcctRecords.stream()
						.allMatch(factAcct -> factAcct.getPostingType().equals(type.getCode()));
				softly.assertThat(allFactAcctRecordsMatchPostingType).isTrue();
			}

			final String projectIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_Fact_Acct.COLUMNNAME_C_Project_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(projectIdentifier))
			{
				final I_C_Project project = projectTable.get(projectIdentifier);

				final boolean allFactAcctRecordsMatchProjectId = factAcctRecords.stream()
						.allMatch(factAcct -> project.getC_Project_ID() == factAcct.getC_Project_ID());
				softly.assertThat(allFactAcctRecordsMatchProjectId).isTrue();
			}

			final String activityIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_Fact_Acct.COLUMNNAME_C_Activity_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(activityIdentifier))
			{
				final I_C_Activity activity = activityTable.get(activityIdentifier);

				final boolean allFactAcctRecordsMatchProjectId = factAcctRecords.stream()
						.allMatch(factAcct -> activity.getC_Activity_ID() == factAcct.getC_Activity_ID());
				softly.assertThat(allFactAcctRecordsMatchProjectId).isTrue();
			}

			softly.assertAll();
		}
	}

	@And("^after not more than (.*)s, the (invoice|matchInvoice|inout|shippingNotification) document with identifier (.*) has the following accounting records:$")
	public void find_Fact_Acct(
			final int timeoutSec,
			@NonNull final String tableType,
			@NonNull final String identifier,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			findFactAcct(getTableRecordReference(tableType, identifier), row, timeoutSec);
		}
	}

	@And("^fact account repost (invoice|matchInvoice) document with identifier (.*):$")
	public void repost_document(
			@NonNull final String documentType,
			@NonNull final String identifier,
			@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			repostDocument(row, documentType, identifier);
		}
	}

	@NonNull
	private ItemProvider.ProviderResult<List<I_Fact_Acct>> load_Fact_Acct(@NonNull final Map<String, String> row)
	{
		final List<I_Fact_Acct> factAcctRecords = buildQuery(row)
				.list();

		if (factAcctRecords.isEmpty())
		{
			return ItemProvider.ProviderResult.resultWasNotFound("No I_Fact_Acct found for row=" + row);
		}

		return ItemProvider.ProviderResult.resultWasFound(factAcctRecords);
	}

	@NonNull
	private IQuery<I_Fact_Acct> buildQuery(@NonNull final Map<String, String> row)
	{
		final String tableName = DataTableUtil.extractStringForColumnName(row, I_AD_Table.COLUMNNAME_TableName);
		final AdTableId tableId = AdTableId.ofRepoIdOrNull(tableDAO.retrieveTableId(tableName));
		assertThat(tableId).isNotNull();

		final IQueryBuilder<I_Fact_Acct> queryBuilder = queryBL.createQueryBuilder(I_Fact_Acct.class)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, tableId);

		final String recordIdentifier = DataTableUtil.extractStringForColumnName(row, I_Fact_Acct.COLUMNNAME_Record_ID + "." + TABLECOLUMN_IDENTIFIER);

		switch (tableName)
		{
			case I_C_Invoice.Table_Name:
				final I_C_Invoice invoice = invoiceTable.get(recordIdentifier);
				queryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, invoice.getC_Invoice_ID());
				break;
			case I_M_InOut.Table_Name:
				final I_M_InOut inout = inoutTable.get(recordIdentifier);
				queryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, inout.getM_InOut_ID());
				break;
			default:
				throw new AdempiereException("Unhandled tableName")
						.appendParametersToMessage()
						.setParameter("tableName:", tableName);
		}

		return queryBuilder.create();
	}

	private void repostDocument(
			@NonNull final Map<String, String> row,
			@NonNull final String tableType,
			@NonNull final String identifier)
	{
		final boolean isEnforcePosting = DataTableUtil.extractBooleanForColumnNameOr(row, "IsEnforcePosting", false);
		final TableRecordReference tableRecordReference = getTableRecordReference(tableType, identifier);

		postingService
				.newPostingRequest()
				.setClientId(Env.getClientId())
				.setDocumentRef(tableRecordReference)
				.setForce(isEnforcePosting)
				.setPostImmediate(IPostingRequestBuilder.PostImmediate.Yes)
				.setFailOnError(true)
				.onErrorNotifyUser(Env.getLoggedUserId())
				.postIt();
	}

	private void findFactAcct(
			@NonNull final TableRecordReference tableRecordReference,
			@NonNull final Map<String, String> row,
			final int timeoutSec) throws InterruptedException
	{
		final FactAcctQuery factAcctQuery = buildFactAcctQuery(tableRecordReference, row);

		final I_Fact_Acct factAcctRecord = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> loadFactAcct(factAcctQuery), () -> getCurrentContext(factAcctQuery));

		final String identifier = DataTableUtil.extractStringForColumnName(row, I_Fact_Acct.COLUMNNAME_Fact_Acct_ID + "." + TABLECOLUMN_IDENTIFIER);
		factAcctTable.putOrReplace(identifier, factAcctRecord);
	}

	@NonNull
	private FactAcctQuery buildFactAcctQuery(
			@NonNull final TableRecordReference tableRecordReference,
			@NonNull final Map<String, String> row)
	{
		final String accountIdentifier = DataTableUtil.extractStringForColumnName(row, TABLE_COLUMN_ACCOUNT_FEATURE);
		final BigDecimal crAmt = DataTableUtil.extractBigDecimalForColumnName(row, TABLE_COLUMN_CR_FEATURE);
		final BigDecimal drAmt = DataTableUtil.extractBigDecimalForColumnName(row, TABLE_COLUMN_DR_FEATURE);

		final String currencyIdentifier = DataTableUtil.extractStringForColumnName(row, I_Fact_Acct.COLUMNNAME_C_Currency_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Currency currencyRecord = currencyTable.get(currencyIdentifier);

		final FactAcctQuery.FactAcctQueryBuilder factAcctQueryBuilder = FactAcctQuery.builder()
				.tableRecordReference(tableRecordReference)
				.accountId(getAccountId(accountIdentifier))
				.crAmt(crAmt)
				.drAmt(drAmt)
				.currencyId(CurrencyId.ofRepoId(currencyRecord.getC_Currency_ID()));

		Optional.ofNullable(DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_Fact_Acct.COLUMNNAME_CurrencyRate))
				.ifPresent(factAcctQueryBuilder::currencyRate);

		Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_Fact_Acct.COLUMNNAME_AccountConceptualName))
				.ifPresent(factAcctQueryBuilder::accountConceptualName);

		Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_Fact_Acct.COLUMNNAME_C_Harvesting_Calendar_ID+ "." + TABLECOLUMN_IDENTIFIER))
				.ifPresent(calendarIdentifier -> {
					final I_C_Calendar calendarRecord = calendarTable.get(calendarIdentifier);
					factAcctQueryBuilder.calendarId(CalendarId.ofRepoId(calendarRecord.getC_Calendar_ID()));
				});

		Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_Fact_Acct.COLUMNNAME_Harvesting_Year_ID+ "." + TABLECOLUMN_IDENTIFIER))
				.ifPresent(yearIdentifier -> {
					final I_C_Year year = yearTable.get(yearIdentifier);
					factAcctQueryBuilder.yearId(YearId.ofRepoId(year.getC_Year_ID()));
				});

		Optional.ofNullable(DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_Fact_Acct.COLUMNNAME_Qty))
				.ifPresent(factAcctQueryBuilder::qty);

		Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_Fact_Acct.COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER))
				.ifPresent(bPartnerLocationIdentifier -> {
					final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(bPartnerLocationIdentifier);
					factAcctQueryBuilder.bPartnerLocationId(BPartnerLocationId.ofRepoId(bPartnerLocation.getC_BPartner_ID(), bPartnerLocation.getC_BPartner_Location_ID()));
				});

		Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_Fact_Acct.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER))
				.ifPresent(productIdentifier -> {
					final I_M_Product product = productTable.get(productIdentifier);
					factAcctQueryBuilder.productId(ProductId.ofRepoId(product.getM_Product_ID()));
				});

		Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_Fact_Acct.COLUMNNAME_M_Locator_ID + "." + TABLECOLUMN_IDENTIFIER))
				.ifPresent(locatorIdentifier -> {
					final I_M_Locator locator = locatorTable.get(locatorIdentifier);
					factAcctQueryBuilder.locatorId(LocatorId.ofRepoId(locator.getM_Warehouse_ID(), locator.getM_Locator_ID()));
				});

		Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_Fact_Acct.COLUMNNAME_M_SectionCode_ID + "." + TABLECOLUMN_IDENTIFIER))
				.ifPresent(sectionCodeIdentifier -> {
					final I_M_SectionCode sectionCode = sectionCodeTable.get(sectionCodeIdentifier);
					factAcctQueryBuilder.sectionCodeId(SectionCodeId.ofRepoId(sectionCode.getM_SectionCode_ID()));
				});

		return factAcctQueryBuilder
				.build();
	}

	@NonNull
	private String getCurrentContext(@NonNull final FactAcctQuery factAcctQuery)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(I_Fact_Acct.COLUMNNAME_AD_Table_ID).append(" : ").append(factAcctQuery.getAD_Table_ID()).append("\n")
				.append(I_Fact_Acct.COLUMNNAME_Record_ID).append(" : ").append(factAcctQuery.getRecord_ID()).append("\n")
				.append(I_Fact_Acct.COLUMNNAME_Account_ID).append(" : ").append(factAcctQuery.getAccountId()).append("\n")
				.append(I_Fact_Acct.COLUMNNAME_AmtSourceCr).append(" : ").append(factAcctQuery.getCrAmt()).append("\n")
				.append(I_Fact_Acct.COLUMNNAME_AmtSourceDr).append(" : ").append(factAcctQuery.getDrAmt()).append("\n")
				.append(I_Fact_Acct.COLUMNNAME_C_Currency_ID).append(" : ").append(factAcctQuery.getCurrencyId().getRepoId()).append("\n");

		Optional.ofNullable(factAcctQuery.getCurrencyRate())
				.ifPresent(currencyRate -> message.append(I_Fact_Acct.COLUMNNAME_CurrencyRate).append(" : ").append(currencyRate).append("\n"));

		Optional.ofNullable(factAcctQuery.getAccountConceptualName())
				.ifPresent(accountConceptualName -> message.append(I_Fact_Acct.COLUMNNAME_AccountConceptualName).append(" : ").append(accountConceptualName).append("\n"));

		message.append("Fact_Acct records:").append("\n");

		queryBL.createQueryBuilder(I_Fact_Acct.class)
				.create()
				.stream(I_Fact_Acct.class)
				.forEach(factAcctRecord -> message
						.append(I_Fact_Acct.COLUMNNAME_AD_Table_ID).append(" : ").append(factAcctRecord.getAD_Table_ID()).append(" ; ")
						.append(I_Fact_Acct.COLUMNNAME_Record_ID).append(" : ").append(factAcctRecord.getRecord_ID()).append(" ; ")
						.append(I_Fact_Acct.COLUMNNAME_Account_ID).append(" : ").append(factAcctRecord.getAccount_ID()).append(" ; ")
						.append(I_Fact_Acct.COLUMNNAME_AmtSourceCr).append(" : ").append(factAcctRecord.getAmtSourceCr()).append(" ; ")
						.append(I_Fact_Acct.COLUMNNAME_AmtSourceDr).append(" : ").append(factAcctRecord.getAmtSourceDr()).append(" ; ")
						.append(I_Fact_Acct.COLUMNNAME_C_Currency_ID).append(" : ").append(factAcctRecord.getC_Currency_ID()).append(" ; ")
						.append(I_Fact_Acct.COLUMNNAME_CurrencyRate).append(" : ").append(factAcctRecord.getCurrencyRate()).append(" ; ")
						.append(I_Fact_Acct.COLUMNNAME_AccountConceptualName).append(" : ").append(factAcctRecord.getAccountConceptualName()).append(" ; ")
						.append(I_Fact_Acct.COLUMNNAME_C_Harvesting_Calendar_ID).append(" : ").append(factAcctRecord.getC_Harvesting_Calendar_ID()).append(" ; ")
						.append(I_Fact_Acct.COLUMNNAME_Harvesting_Year_ID).append(" : ").append(factAcctRecord.getHarvesting_Year_ID()).append(" ; ")
						.append("\n"));

		return "see current context: \n" + message;
	}

	@NonNull
	private ItemProvider.ProviderResult<I_Fact_Acct> loadFactAcct(@NonNull final FactAcctQuery factAcctQuery)
	{
		final IQueryBuilder<I_Fact_Acct> queryBuilder = queryBL.createQueryBuilder(I_Fact_Acct.class)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, factAcctQuery.getRecord_ID())
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, factAcctQuery.getAD_Table_ID())
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Account_ID, factAcctQuery.getAccountId().getRepoId())
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AmtSourceCr, factAcctQuery.getCrAmt())
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AmtSourceDr, factAcctQuery.getDrAmt())
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_C_Currency_ID, factAcctQuery.getCurrencyId());

		Optional.ofNullable(factAcctQuery.getCurrencyRate())
				.ifPresent(currencyRate -> queryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_CurrencyRate, currencyRate));

		Optional.ofNullable(factAcctQuery.getAccountConceptualName())
				.ifPresent(accountConceptualName -> queryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AccountConceptualName, accountConceptualName));

		Optional.ofNullable(factAcctQuery.getCalendarId())
				.ifPresent(calendarId -> queryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_C_Harvesting_Calendar_ID, calendarId));

		Optional.ofNullable(factAcctQuery.getYearId())
				.ifPresent(yearId -> queryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Harvesting_Year_ID, yearId));

		Optional.ofNullable(factAcctQuery.getQty())
				.ifPresent(qty -> queryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Qty, qty));

		Optional.ofNullable(factAcctQuery.getProductId())
						.ifPresent(productId -> queryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_M_Product_ID, productId));

		Optional.ofNullable(factAcctQuery.getBPartnerLocationId())
						.ifPresent(bPartnerLocationId -> {
							queryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_C_BPartner_ID, bPartnerLocationId.getBpartnerId());
							queryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_C_BPartner_Location_ID, bPartnerLocationId);
						});

		Optional.ofNullable(factAcctQuery.getLocatorId())
						.ifPresent(locatorId -> queryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_M_Locator_ID, locatorId));

		Optional.ofNullable(factAcctQuery.getSectionCodeId())
						.ifPresent(sectionCodeId -> queryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_M_SectionCode_ID, sectionCodeId));

		final I_Fact_Acct factAcctRecord = queryBuilder
				.create()
				.firstOnlyOrNull(I_Fact_Acct.class);

		if (factAcctRecord == null)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("No I_Fact_Acct found for query=" + factAcctQuery);
		}

		return ItemProvider.ProviderResult.resultWasFound(factAcctRecord);
	}

	@NonNull
	private ElementValueId getAccountId(@NonNull final String identifier)
	{
		return ElementValueId.ofRepoId(elementValueTable.get(identifier).getC_ElementValue_ID());
	}

	@NonNull
	private TableRecordReference getTableRecordReference(
			@NonNull final String tableType,
			@NonNull final String identifier)
	{
		switch (TableType.valueOf(tableType))
		{
			case invoice:
				return TableRecordReference.of(I_C_Invoice.Table_Name, invoiceTable.get(identifier).getC_Invoice_ID());
			case matchInvoice:
				return TableRecordReference.of(I_M_MatchInv.Table_Name, matchInvTable.get(identifier).getM_MatchInv_ID());
			case inout:
				return TableRecordReference.of(I_M_InOut.Table_Name, inoutTable.get(identifier).getM_InOut_ID());
			case shippingNotification:
				return TableRecordReference.of(I_M_Shipping_Notification.Table_Name, shippingNotificationTable.get(identifier).getM_Shipping_Notification_ID());
			default:
				throw new AdempiereException("Invalid table type!")
						.appendParametersToMessage()
						.setParameter("TableType", tableType)
						.setParameter("Valid types", TableType.values());
		}
	}

	@Value
	@Builder
	private static class FactAcctQuery
	{
		@NonNull TableRecordReference tableRecordReference;

		@NonNull ElementValueId accountId;

		@NonNull BigDecimal crAmt;

		@NonNull BigDecimal drAmt;

		@NonNull CurrencyId currencyId;

		@Nullable
		BigDecimal currencyRate;

		@Nullable
		String accountConceptualName;

		@Nullable
		CalendarId calendarId;

		@Nullable
		YearId yearId;

		@Nullable
		BigDecimal qty;

		@Nullable
		ProductId productId;

		@Nullable
		SectionCodeId sectionCodeId;

		@Nullable
		LocatorId locatorId;

		@Nullable
		BPartnerLocationId bPartnerLocationId;

		public int getRecord_ID()
		{
			return tableRecordReference.getRecord_ID();
		}

		public int getAD_Table_ID()
		{
			return tableRecordReference.getAD_Table_ID();
		}
	}
}

