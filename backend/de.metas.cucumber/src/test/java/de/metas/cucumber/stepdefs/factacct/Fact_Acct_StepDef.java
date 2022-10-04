package de.metas.cucumber.stepdefs.factacct;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.sectioncode.M_SectionCode_StepDefData;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_M_SectionCode;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class Fact_Acct_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	private final M_SectionCode_StepDefData sectionCodeTable;
	private final C_Invoice_StepDefData invoiceTable;

	public Fact_Acct_StepDef(
			@NonNull final M_SectionCode_StepDefData sectionCodeTable,
			@NonNull final C_Invoice_StepDefData invoiceTable)
	{
		this.sectionCodeTable = sectionCodeTable;
		this.invoiceTable = invoiceTable;
	}

	@And("^after not more than (.*)s, Fact_Acct are found$")
	public void find_Fact_Acct(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final List<I_Fact_Acct> factAcctRecords = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> load_Fact_Acct(row));

			final String sectionCodeIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_SectionCode.COLUMNNAME_M_SectionCode_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(sectionCodeIdentifier))
			{
				final I_M_SectionCode sectionCode = sectionCodeTable.get(sectionCodeIdentifier);
				final boolean allFactAcctRecordsMatch = factAcctRecords.stream()
						.allMatch(factAcct -> sectionCode.getM_SectionCode_ID() == factAcct.getM_SectionCode_ID());

				assertThat(allFactAcctRecordsMatch).isTrue();
			}
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
			default:
				throw new AdempiereException("Unhandled tableName")
						.appendParametersToMessage()
						.setParameter("tableName:", tableName);
		}

		return queryBuilder.create();
	}
}
