package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Tax_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.currency.Amount;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.quantity.Quantity;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.TaxId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.text.tabular.Row;
import de.metas.util.text.tabular.Table;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.PO;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

import javax.annotation.Nullable;
import java.util.List;

@Builder
public class FactAcctToTabularStringConverter
{
	@NonNull private final IUOMDAO uomDAO;
	@NonNull private final ITaxDAO taxDAO;
	@NonNull private final MoneyService moneyService;
	@Nullable private final C_BPartner_StepDefData bpartnerTable;
	@Nullable private final C_Tax_StepDefData taxTable;
	@Nullable private final IdentifiersResolver identifiersResolver;

	@NonNull private static final ImmutableSet<String> poColumnNamesToIgnore = ImmutableSet.of(
			I_Fact_Acct.COLUMNNAME_AD_Client_ID,
			I_Fact_Acct.COLUMNNAME_AD_Org_ID,
			I_Fact_Acct.COLUMNNAME_Created,
			I_Fact_Acct.COLUMNNAME_CreatedBy,
			I_Fact_Acct.COLUMNNAME_Updated,
			I_Fact_Acct.COLUMNNAME_UpdatedBy,
			I_Fact_Acct.COLUMNNAME_IsActive,
			I_Fact_Acct.COLUMNNAME_AccountConceptualName,
			I_Fact_Acct.COLUMNNAME_AmtAcctDr,
			I_Fact_Acct.COLUMNNAME_AmtAcctCr,
			I_Fact_Acct.COLUMNNAME_C_Currency_ID,
			I_Fact_Acct.COLUMNNAME_AmtSourceDr,
			I_Fact_Acct.COLUMNNAME_AmtSourceCr,
			I_Fact_Acct.COLUMNNAME_C_UOM_ID,
			I_Fact_Acct.COLUMNNAME_Qty,
			I_Fact_Acct.COLUMNNAME_C_BPartner_ID
	);

	public Table toTabular(final I_Fact_Acct singleRecord, int lineNo)
	{
		return toTabular(ImmutableList.of(singleRecord), lineNo);
	}

	public Table toTabular(final List<I_Fact_Acct> records, Integer startLineNo)
	{
		final Table table = new Table();

		for (int i = 0; i < records.size(); i++)
		{
			final I_Fact_Acct record = records.get(i);
			final Integer lineNo = startLineNo != null ? startLineNo + i : null;
			table.addRow(toRow(record, lineNo));
		}
		table.updateHeaderFromRows();
		table.removeColumnsWithBlankValues();
		return table;
	}

	private Row toRow(final I_Fact_Acct record, final Integer lineNo)
	{
		final Row row = new Row();

		if (lineNo != null)
		{
			row.put("#", lineNo);
		}
		row.put("AccountConceptualName", record.getAccountConceptualName());
		row.put("AmtSourceDr", extractAmtSourceDr(record));
		row.put("AmtSourceCr", extractAmtSourceCr(record));
		row.put("AmtAcctDr", record.getAmtAcctDr());
		row.put("AmtAcctCr", record.getAmtAcctCr());
		row.put("Qty", extractQuantityString(record));
		row.put("C_BPartner_ID", extractBPartnerString(record));
		row.put("C_Tax_ID", extractTaxId(record));
		row.put("Record_ID", extractDocumentRef(record));

		final PO po = InterfaceWrapperHelper.getPO(record);
		for (int i = 0; i < po.get_ColumnCount(); i++)
		{
			final String columnName = po.get_ColumnName(i);
			if (poColumnNamesToIgnore.contains(columnName))
			{
				continue;
			}
			if (row.containsColumn(columnName))
			{
				continue;
			}

			final Object value;
			if (I_Fact_Acct.COLUMNNAME_AD_Table_ID.equals(columnName))
			{
				// see Record_ID
				continue;
			}
			else
			{
				value = po.get_Value(i);
			}

			row.put(columnName, value);
		}

		return row;
	}

	@Nullable
	private String extractTaxId(final I_Fact_Acct record)
	{
		final TaxId taxId = TaxId.ofRepoIdOrNull(record.getC_Tax_ID());
		if (taxId == null)
		{
			return null;
		}

		if (taxTable != null)
		{
			final String taxName = taxTable.getFirstIdentifierById(taxId)
					.map(StepDefDataIdentifier::getAsString)
					.orElse(null);
			if (taxName != null)
			{
				return taxName;
			}
		}

		return taxDAO.getTaxById(taxId).getName() + "/" + taxId.getRepoId();
	}

	@NonNull
	private String extractDocumentRef(final I_Fact_Acct record)
	{
		final TableRecordReference documentRef = TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID());
		if (identifiersResolver != null)
		{
			final StepDefDataIdentifier identifier = identifiersResolver.getIdentifier(documentRef).orElse(null);
			if (identifier != null)
			{
				return identifier.getAsString() + "/" + documentRef.getRecord_ID();
			}
		}

		// Fallback:
		return documentRef.toString();
	}

	private Amount extractAmtSourceDr(final I_Fact_Acct record)
	{
		return Money.of(record.getAmtSourceDr(), extractCurrencyId(record))
				.toAmount(moneyService::getCurrencyCodeByCurrencyId);
	}

	private Amount extractAmtSourceCr(final I_Fact_Acct record)
	{
		return Money.of(record.getAmtSourceCr(), extractCurrencyId(record))
				.toAmount(moneyService::getCurrencyCodeByCurrencyId);
	}

	private static CurrencyId extractCurrencyId(final I_Fact_Acct record)
	{
		return CurrencyId.ofRepoId(record.getC_Currency_ID());
	}

	private String extractQuantityString(final I_Fact_Acct record)
	{
		final UomId uomId = UomId.ofRepoIdOrNull(record.getC_UOM_ID());
		if (uomId != null)
		{
			final I_C_UOM uom = uomDAO.getById(uomId);
			return Quantity.of(record.getQty(), uom).toString();
		}
		else if (!InterfaceWrapperHelper.isNull(record, I_Fact_Acct.COLUMNNAME_Qty))
		{
			return record.getQty().toString();
		}
		else
		{
			return null;
		}
	}

	private String extractBPartnerString(final I_Fact_Acct record)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			return null;
		}

		if (bpartnerTable != null)
		{
			final StepDefDataIdentifier identifier = bpartnerTable.getFirstIdentifierById(bpartnerId).orElse(null);
			if (identifier != null)
			{
				return identifier.getAsString();
			}
		}

		return "<" + bpartnerId.getRepoId() + ">";
	}
}
