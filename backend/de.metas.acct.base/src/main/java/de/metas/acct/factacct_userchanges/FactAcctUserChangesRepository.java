package de.metas.acct.factacct_userchanges;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.model.I_Fact_Acct_UserChange;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public class FactAcctUserChangesRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void save(
			@NonNull final FactAcctChangesList lineChangesList,
			@NonNull final TableRecordReference docRecordRef)
	{
		final InvoiceId invoiceId;
		if (I_C_Invoice.Table_Name.equals(docRecordRef.getTableName()))
		{
			invoiceId = InvoiceId.ofRepoId(docRecordRef.getRecord_ID());
		}
		else
		{
			throw new AdempiereException("Searching by " + docRecordRef.getTableName() + " is not supported");
		}

		deleteByDocRecordRef(docRecordRef);

		for (final FactAcctChanges lineChanges : lineChangesList)
		{
			final I_Fact_Acct_UserChange record = InterfaceWrapperHelper.newInstance(I_Fact_Acct_UserChange.class);
			record.setC_Invoice_ID(InvoiceId.toRepoId(invoiceId));
			updateRecord(record, lineChanges);
			InterfaceWrapperHelper.save(record);
		}
	}

	public FactAcctChangesList getByDocRecordRef(@NonNull final TableRecordReference docRecordRef)
	{
		final IQueryBuilder<I_Fact_Acct_UserChange> queryBuilder = queryByDocRecordRef(docRecordRef);
		if (queryBuilder == null)
		{
			return FactAcctChangesList.EMPTY;
		}

		return queryBuilder
				.stream()
				.map(FactAcctUserChangesRepository::fromRecord)
				.collect(FactAcctChangesList.collect());
	}

	private void deleteByDocRecordRef(final @NonNull TableRecordReference docRecordRef)
	{
		final IQueryBuilder<I_Fact_Acct_UserChange> queryBuilder = queryByDocRecordRef(docRecordRef);
		if (queryBuilder == null)
		{
			return;
		}

		queryBuilder.create().delete();
	}

	@Nullable
	private IQueryBuilder<I_Fact_Acct_UserChange> queryByDocRecordRef(final @NonNull TableRecordReference docRecordRef)
	{
		final IQueryBuilder<I_Fact_Acct_UserChange> queryBuilder = queryBL.createQueryBuilder(I_Fact_Acct_UserChange.class)
				.orderBy(I_Fact_Acct_UserChange.COLUMNNAME_Fact_Acct_UserChange_ID)
				.addOnlyActiveRecordsFilter();

		if (I_C_Invoice.Table_Name.equals(docRecordRef.getTableName()))
		{
			queryBuilder.addEqualsFilter(I_Fact_Acct_UserChange.COLUMNNAME_C_Invoice_ID, docRecordRef.getRecord_ID());
		}
		else
		{
			return null;
		}

		return queryBuilder;
	}

	private static FactAcctChanges fromRecord(final I_Fact_Acct_UserChange record)
	{
		return FactAcctChanges.builder()
				.matchKey(FactLineMatchKey.ofNullableString(record.getMatchKey()))
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				.postingSign(PostingSign.ofCode(record.getPostingSign()))
				.accountId(ElementValueId.ofRepoId(record.getAccount_ID()))
				.amount_DC(Money.of(record.getAmount_DC(), CurrencyId.ofRepoId(record.getLocal_Currency_ID())))
				.amount_LC(Money.of(record.getAmount_LC(), CurrencyId.ofRepoId(record.getDocument_Currency_ID())))
				.taxId(TaxId.ofRepoIdOrNull(record.getC_Tax_ID()))
				.description(StringUtils.trimBlankToNull(record.getDescription()))
				.sectionCodeId(SectionCodeId.ofRepoIdOrNull(record.getM_SectionCode_ID()))
				.productId(ProductId.ofRepoIdOrNull(record.getM_Product_ID()))
				.userElementString1(StringUtils.trimBlankToNull(record.getUserElementString1()))
				.salesOrderId(OrderId.ofRepoIdOrNull(record.getC_OrderSO_ID()))
				.activityId(ActivityId.ofRepoIdOrNull(record.getC_Activity_ID()))
				.build();
	}

	private static void updateRecord(final I_Fact_Acct_UserChange record, final FactAcctChanges from)
	{
		record.setMatchKey(from.getMatchKey() != null ? from.getMatchKey().getAsString() : null);
		record.setC_AcctSchema_ID(from.getAcctSchemaId().getRepoId());
		record.setPostingSign(from.getPostingSign().getCode());
		record.setAccount_ID(ElementValueId.toRepoId(from.getAccountId()));
		record.setDocument_Currency_ID(from.getAmount_DC().getCurrencyId().getRepoId());
		record.setAmount_DC(from.getAmount_DC().toBigDecimal());
		record.setLocal_Currency_ID(from.getAmount_LC().getCurrencyId().getRepoId());
		record.setAmount_LC(from.getAmount_LC().toBigDecimal());
		record.setC_Tax_ID(TaxId.toRepoId(from.getTaxId()));
		record.setDescription(StringUtils.trimBlankToNull(from.getDescription()));
		record.setM_SectionCode_ID(SectionCodeId.toRepoId(from.getSectionCodeId()));
		record.setM_Product_ID(ProductId.toRepoId(from.getProductId()));
		record.setUserElementString1(StringUtils.trimBlankToNull(from.getUserElementString1()));
		record.setC_OrderSO_ID(OrderId.toRepoId(from.getSalesOrderId()));
		record.setC_Activity_ID(ActivityId.toRepoId(from.getActivityId()));
	}
}
