package de.metas.acct.gljournal_sap.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.acct.Account;
import de.metas.acct.AccountConceptualName;
import de.metas.acct.GLCategoryId;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.PostingType;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalCurrencyConversionCtx;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.SAPGLJournalLine;
import de.metas.acct.gljournal_sap.SAPGLJournalLineId;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.acct.model.I_SAP_GLJournalLine;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.acct.open_items.FAOpenItemTrxType;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineRefId;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.FixedConversionRate;
import de.metas.document.DocTypeId;
import de.metas.document.dimension.Dimension;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class SAPGLJournalLoaderAndSaver
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final HashMap<SAPGLJournalId, I_SAP_GLJournal> headersById = new HashMap<>();
	private final HashSet<SAPGLJournalId> headerIdsToAvoidSaving = new HashSet<>();

	private final HashMap<SAPGLJournalId, ArrayList<I_SAP_GLJournalLine>> linesByHeaderId = new HashMap<>();

	public void addToCacheAndAvoidSaving(@NonNull final I_SAP_GLJournal record)
	{
		@NonNull final SAPGLJournalId glJournalId = extractId(record);
		headersById.put(glJournalId, record);
		headerIdsToAvoidSaving.add(glJournalId);
	}

	@NonNull
	public SAPGLJournal getById(@NonNull final SAPGLJournalId id)
	{
		final I_SAP_GLJournal headerRecord = getHeaderRecordById(id);
		final List<I_SAP_GLJournalLine> lineRecords = getLineRecords(id);
		return fromRecord(headerRecord, lineRecords);
	}

	@NonNull
	private I_SAP_GLJournal getHeaderRecordById(@NonNull final SAPGLJournalId id)
	{
		return getHeaderRecordByIdIfExists(id)
				.orElseThrow(() -> new AdempiereException("No SAP GL Journal found for " + id));
	}

	private Optional<I_SAP_GLJournal> getHeaderRecordByIdIfExists(@NonNull final SAPGLJournalId id)
	{
		return Optional.ofNullable(headersById.computeIfAbsent(id, this::retrieveHeaderRecordById));
	}

	@Nullable
	private I_SAP_GLJournal retrieveHeaderRecordById(@NonNull final SAPGLJournalId id)
	{
		return queryBL.createQueryBuilder(I_SAP_GLJournal.class)
				.addEqualsFilter(I_SAP_GLJournal.COLUMNNAME_SAP_GLJournal_ID, id)
				.create()
				.firstOnly(I_SAP_GLJournal.class);
	}

	public static SAPGLJournalCurrencyConversionCtx extractConversionCtx(@NonNull final I_SAP_GLJournal glJournal)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(glJournal.getC_Currency_ID());
		final CurrencyId acctCurrencyId = CurrencyId.ofRepoId(glJournal.getAcct_Currency_ID());

		final FixedConversionRate fixedConversionRate;
		final BigDecimal currencyRateBD = glJournal.getCurrencyRate();
		if (currencyRateBD.signum() != 0)
		{
			fixedConversionRate = FixedConversionRate.builder()
					.fromCurrencyId(currencyId)
					.toCurrencyId(acctCurrencyId)
					.multiplyRate(currencyRateBD)
					.build();
		}
		else
		{
			fixedConversionRate = null;
		}

		return SAPGLJournalCurrencyConversionCtx.builder()
				.acctCurrencyId(acctCurrencyId)
				.currencyId(currencyId)
				.conversionTypeId(CurrencyConversionTypeId.ofRepoIdOrNull(glJournal.getC_ConversionType_ID()))
				.date(glJournal.getDateAcct().toInstant())
				.fixedConversionRate(fixedConversionRate)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(glJournal.getAD_Client_ID(), glJournal.getAD_Org_ID()))
				.build();
	}

	public ArrayList<I_SAP_GLJournalLine> getLineRecords(@NonNull final SAPGLJournalId glJournalId)
	{
		return linesByHeaderId.computeIfAbsent(glJournalId, this::retrieveLineRecords);
	}

	private ArrayList<I_SAP_GLJournalLine> retrieveLineRecords(final @NonNull SAPGLJournalId glJournalId)
	{
		return queryLinesByHeaderId(glJournalId)
				.create()
				.stream()
				.collect(Collectors.toCollection(ArrayList::new));

	}

	private IQueryBuilder<I_SAP_GLJournalLine> queryLinesByHeaderId(final @NonNull SAPGLJournalId glJournalId)
	{
		return queryBL.createQueryBuilder(I_SAP_GLJournalLine.class)
				.addInArrayFilter(I_SAP_GLJournalLine.COLUMNNAME_SAP_GLJournal_ID, glJournalId);
	}

	SeqNo getNextSeqNo(@NonNull final SAPGLJournalId glJournalId)
	{
		final int lastLineInt = queryLinesByHeaderId(glJournalId)
				.create()
				.maxInt(I_SAP_GLJournalLine.COLUMNNAME_Line);

		final SeqNo lastLineNo = SeqNo.ofInt(Math.max(lastLineInt, 0));
		return lastLineNo.next();
	}

	private static SAPGLJournal fromRecord(
			@NonNull final I_SAP_GLJournal headerRecord,
			@NonNull final List<I_SAP_GLJournalLine> lineRecords)
	{
		final SAPGLJournalCurrencyConversionCtx conversionCtx = extractConversionCtx(headerRecord);

		return SAPGLJournal.builder()
				.id(extractId(headerRecord))
				.docTypeId(DocTypeId.ofRepoId(headerRecord.getC_DocType_ID()))
				.conversionCtx(conversionCtx)
				.acctSchemaId(AcctSchemaId.ofRepoId(headerRecord.getC_AcctSchema_ID()))
				.postingType(PostingType.ofCode(headerRecord.getPostingType()))
				.lines(lineRecords.stream()
						.map(lineRecord -> fromRecord(lineRecord, conversionCtx))
						.sorted(Comparator.comparing(SAPGLJournalLine::getLine).thenComparing(SAPGLJournalLine::getIdNotNull))
						.collect(Collectors.toCollection(ArrayList::new)))
				.totalAcctDR(Money.of(headerRecord.getTotalDr(), conversionCtx.getAcctCurrencyId()))
				.totalAcctCR(Money.of(headerRecord.getTotalCr(), conversionCtx.getAcctCurrencyId()))
				.docStatus(DocStatus.ofCode(headerRecord.getDocStatus()))
				//
				.orgId(OrgId.ofRepoId(headerRecord.getAD_Org_ID()))
				.dimension(extractDimension(headerRecord))
				.description(headerRecord.getDescription())
				.glCategoryId(GLCategoryId.ofRepoIdOrNone(headerRecord.getGL_Category_ID()))
				//
				.build();
	}

	private static Dimension extractDimension(final I_SAP_GLJournal headerRecord)
	{
		return Dimension.builder()
				.sectionCodeId(SectionCodeId.ofRepoIdOrNull(headerRecord.getM_SectionCode_ID()))
				.build();
	}

	private static SAPGLJournalLine fromRecord(
			@NonNull final I_SAP_GLJournalLine record,
			@NonNull final SAPGLJournalCurrencyConversionCtx conversionCtx)
	{
		return SAPGLJournalLine.builder()
				.id(extractId(record))
				.processed(record.isProcessed())
				.parentId(SAPGLJournalLineId.ofRepoIdOrNull(record.getSAP_GLJournal_ID(), record.getParent_ID()))
				//
				.line(SeqNo.ofInt(record.getLine()))
				.description(StringUtils.trimBlankToNull(record.getDescription()))
				//
				.account(Account.ofId(AccountId.ofRepoId(record.getC_ValidCombination_ID())))
				.postingSign(PostingSign.ofCode(record.getPostingSign()))
				.amount(Money.of(record.getAmount(), conversionCtx.getCurrencyId()))
				.amountAcct(Money.of(record.getAmtAcct(), conversionCtx.getAcctCurrencyId()))
				//
				.taxId(TaxId.ofRepoIdOrNull(record.getC_Tax_ID()))
				//
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.bpartnerId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))
				.dimension(extractDimension(record))
				//
				.determineTaxBaseSAP(record.isSAP_DetermineTaxBase())
				//
				.openItemTrxInfo(extractFAOpenItemTrxInfoOrNull(record))
				//
				.isFieldsReadOnlyInUI(record.isFieldsReadOnlyInUI())
				//
				.build();
	}

	private static Dimension extractDimension(final @NonNull I_SAP_GLJournalLine record)
	{
		return Dimension.builder()
				.sectionCodeId(SectionCodeId.ofRepoIdOrNull(record.getM_SectionCode_ID()))
				.productId(ProductId.ofRepoIdOrNull(record.getM_Product_ID()))
				.salesOrderId(OrderId.ofRepoIdOrNull(record.getC_OrderSO_ID()))
				.activityId(ActivityId.ofRepoIdOrNull(record.getC_Activity_ID()))
				.userElementString1(record.getUserElementString1())
				.userElementString2(record.getUserElementString2())
				.userElementString3(record.getUserElementString3())
				.userElementString4(record.getUserElementString4())
				.userElementString5(record.getUserElementString5())
				.userElementString6(record.getUserElementString6())
				.userElementString7(record.getUserElementString7())
				.build();
	}

	@NonNull
	public static SAPGLJournalId extractId(final @NonNull I_SAP_GLJournal header)
	{
		return SAPGLJournalId.ofRepoId(header.getSAP_GLJournal_ID());
	}

	@NonNull
	private static SAPGLJournalLineId extractId(final @NonNull I_SAP_GLJournalLine line)
	{
		return SAPGLJournalLineId.ofRepoId(line.getSAP_GLJournal_ID(), line.getSAP_GLJournalLine_ID());
	}

	@Nullable
	private static FAOpenItemTrxInfo extractFAOpenItemTrxInfoOrNull(final I_SAP_GLJournalLine line)
	{
		final FAOpenItemTrxType trxType = FAOpenItemTrxType.ofNullableCode(line.getOI_TrxType());
		if (trxType == null)
		{
			return null;
		}

		final FAOpenItemKey openItemKey = FAOpenItemKey.parseNullable(line.getOpenItemKey()).orElse(null);
		if (openItemKey == null)
		{
			return null;
		}

		return FAOpenItemTrxInfo.builder()
				.trxType(trxType)
				.key(openItemKey)
				.build();
	}

	public void save(final SAPGLJournal glJournal)
	{
		final I_SAP_GLJournal headerRecord = getHeaderRecordById(glJournal.getId());
		updateHeaderRecord(headerRecord, glJournal);
		saveRecordIfAllowed(headerRecord);

		final ArrayList<I_SAP_GLJournalLine> lineRecords = getLineRecords(glJournal.getId());
		final ImmutableMap<SAPGLJournalLineId, I_SAP_GLJournalLine> lineRecordsById = Maps.uniqueIndex(lineRecords, SAPGLJournalLoaderAndSaver::extractId);

		//
		// NEW/UPDATE
		final HashSet<SAPGLJournalLineId> savedLineIds = new HashSet<>();
		for (SAPGLJournalLine line : glJournal.getLines())
		{
			SAPGLJournalLineId lineId = line.getIdOrNull();
			I_SAP_GLJournalLine lineRecord = null;
			if (lineId != null)
			{
				lineRecord = lineRecordsById.get(lineId);
			}

			if (lineRecord == null)
			{
				lineRecord = InterfaceWrapperHelper.newInstance(I_SAP_GLJournalLine.class);
				lineRecord.setSAP_GLJournal_ID(headerRecord.getSAP_GLJournal_ID());
				if (lineId != null)
				{
					lineRecord.setSAP_GLJournalLine_ID(lineId.getRepoId());
				}
				lineRecord.setAD_Org_ID(headerRecord.getAD_Org_ID());
			}

			updateLineRecord(lineRecord, line);
			InterfaceWrapperHelper.save(lineRecord);
			lineId = extractId(lineRecord);
			line.assignId(lineId);

			savedLineIds.add(lineId);
		}

		//
		// DELETE
		for (Iterator<I_SAP_GLJournalLine> it = lineRecords.iterator(); it.hasNext(); )
		{
			final I_SAP_GLJournalLine lineRecord = it.next();
			final SAPGLJournalLineId id = extractId(lineRecord);
			if (!savedLineIds.contains(id))
			{
				it.remove();
				InterfaceWrapperHelper.delete(lineRecord);
			}
		}
	}

	private static void updateHeaderRecord(final I_SAP_GLJournal headerRecord, final SAPGLJournal glJournal)
	{
		headerRecord.setTotalDr(glJournal.getTotalAcctDR().toBigDecimal());
		headerRecord.setTotalCr(glJournal.getTotalAcctCR().toBigDecimal());
		headerRecord.setDocStatus(glJournal.getDocStatus().getCode());
	}

	private static void updateLineRecord(final I_SAP_GLJournalLine lineRecord, final SAPGLJournalLine line)
	{
		lineRecord.setProcessed(line.isProcessed());
		lineRecord.setParent_ID(SAPGLJournalLineId.toRepoId(line.getParentId()));
		lineRecord.setLine(line.getLine().toInt());
		lineRecord.setDescription(StringUtils.trimBlankToNull(line.getDescription()));
		lineRecord.setC_ValidCombination_ID(line.getAccount().getAccountId().getRepoId());
		lineRecord.setPostingSign(line.getPostingSign().getCode());
		lineRecord.setAmount(line.getAmount().toBigDecimal());
		lineRecord.setAmtAcct(line.getAmountAcct().toBigDecimal());
		lineRecord.setC_Tax_ID(TaxId.toRepoId(line.getTaxId()));

		lineRecord.setAD_Org_ID(line.getOrgId().getRepoId());
		lineRecord.setC_BPartner_ID(BPartnerId.toRepoId(line.getBpartnerId()));
		updateLineRecordFromDimension(lineRecord, line.getDimension());

		lineRecord.setSAP_DetermineTaxBase(line.isDetermineTaxBaseSAP());

		updateRecordFromOpenItemTrxInfo(lineRecord, line.getOpenItemTrxInfo());

		lineRecord.setIsFieldsReadOnlyInUI(line.isFieldsReadOnlyInUI());
	}

	public static void updateRecordFromOpenItemTrxInfo(@NonNull final I_SAP_GLJournalLine lineRecord, @Nullable final FAOpenItemTrxInfo from)
	{
		lineRecord.setIsOpenItem(from != null);
		lineRecord.setOI_TrxType(from != null ? from.getTrxType().getCode() : null);

		final FAOpenItemKey openItemKey = from != null ? from.getKey() : null;
		final AccountConceptualName accountConceptualName = openItemKey != null ? openItemKey.getAccountConceptualName() : null;
		lineRecord.setOpenItemKey(openItemKey != null ? openItemKey.getAsString() : null);
		lineRecord.setOI_AccountConceptualName(accountConceptualName != null ? accountConceptualName.getAsString() : null);
		lineRecord.setOI_Invoice_ID(openItemKey != null ? openItemKey.getInvoiceId().map(InvoiceId::getRepoId).orElse(0) : 0);
		lineRecord.setOI_Payment_ID(openItemKey != null ? openItemKey.getPaymentId().map(PaymentId::getRepoId).orElse(0) : 0);
		lineRecord.setOI_BankStatement_ID(openItemKey != null ? openItemKey.getBankStatementId().map(BankStatementId::getRepoId).orElse(0) : 0);
		lineRecord.setOI_BankStatementLine_ID(openItemKey != null ? openItemKey.getBankStatementLineId().map(BankStatementLineId::getRepoId).orElse(0) : 0);
		lineRecord.setOI_BankStatementLine_Ref_ID(openItemKey != null ? openItemKey.getBankStatementLineRefId().map(BankStatementLineRefId::getRepoId).orElse(0) : 0);
	}

	private static void updateLineRecordFromDimension(final I_SAP_GLJournalLine lineRecord, final Dimension dimension)
	{
		lineRecord.setM_SectionCode_ID(SectionCodeId.toRepoId(dimension.getSectionCodeId()));
		lineRecord.setM_Product_ID(ProductId.toRepoId(dimension.getProductId()));
		lineRecord.setC_OrderSO_ID(OrderId.toRepoId(dimension.getSalesOrderId()));
		lineRecord.setC_Activity_ID(ActivityId.toRepoId(dimension.getActivityId()));
		lineRecord.setUserElementString1(dimension.getUserElementString1());
		lineRecord.setUserElementString2(dimension.getUserElementString2());
		lineRecord.setUserElementString3(dimension.getUserElementString3());
		lineRecord.setUserElementString4(dimension.getUserElementString4());
		lineRecord.setUserElementString5(dimension.getUserElementString5());
		lineRecord.setUserElementString6(dimension.getUserElementString6());
		lineRecord.setUserElementString7(dimension.getUserElementString7());
	}

	private void saveRecordIfAllowed(final I_SAP_GLJournal headerRecord)
	{
		if (headerIdsToAvoidSaving.contains(extractId(headerRecord)))
		{
			return;
		}

		InterfaceWrapperHelper.save(headerRecord);
	}

	public void updateById(
			@NonNull final SAPGLJournalId id,
			@NonNull Consumer<SAPGLJournal> consumer)
	{
		updateById(
				id,
				glJournal -> {
					consumer.accept(glJournal);
					return null; // N/A
				});
	}

	public <R> R updateById(
			@NonNull final SAPGLJournalId id,
			@NonNull Function<SAPGLJournal, R> processor)
	{
		final SAPGLJournal glJournal = getById(id);
		final R result = processor.apply(glJournal);
		save(glJournal);

		return result;
	}

	public DocStatus getDocStatus(final SAPGLJournalId glJournalId)
	{
		return getHeaderRecordByIdIfExists(glJournalId)
				.map(headerRecord -> DocStatus.ofNullableCodeOrUnknown(headerRecord.getDocStatus()))
				.orElse(DocStatus.Unknown);
	}

	@NonNull
	SAPGLJournal create(
			@NonNull final SAPGLJournalCreateRequest createRequest,
			@NonNull final SAPGLJournalCurrencyConverter currencyConverter)
	{
		final I_SAP_GLJournal headerRecord = InterfaceWrapperHelper.newInstance(I_SAP_GLJournal.class);

		headerRecord.setTotalDr(createRequest.getTotalAcctDR().toBigDecimal());
		headerRecord.setTotalCr(createRequest.getTotalAcctCR().toBigDecimal());
		headerRecord.setC_DocType_ID(createRequest.getDocTypeId().getRepoId());
		headerRecord.setC_AcctSchema_ID(createRequest.getAcctSchemaId().getRepoId());
		headerRecord.setPostingType(createRequest.getPostingType().getCode());
		headerRecord.setAD_Org_ID(createRequest.getOrgId().getRepoId());
		headerRecord.setDescription(createRequest.getDescription());
		headerRecord.setDocStatus(DocStatus.Drafted.getCode());

		headerRecord.setM_SectionCode_ID(SectionCodeId.toRepoId(createRequest.getDimension().getSectionCodeId()));

		final SAPGLJournalCurrencyConversionCtx conversionCtx = createRequest.getConversionCtx();

		headerRecord.setAcct_Currency_ID(conversionCtx.getAcctCurrencyId().getRepoId());
		headerRecord.setC_Currency_ID(conversionCtx.getCurrencyId().getRepoId());
		headerRecord.setC_ConversionType_ID(CurrencyConversionTypeId.toRepoId(conversionCtx.getConversionTypeId()));

		Optional.ofNullable(conversionCtx.getFixedConversionRate())
				.ifPresent(fixedConversionRate -> headerRecord.setCurrencyRate(fixedConversionRate.getMultiplyRate()));

		headerRecord.setDateAcct(TimeUtil.asTimestamp(createRequest.getDateDoc()));
		headerRecord.setDateDoc(TimeUtil.asTimestamp(createRequest.getDateDoc()));
		headerRecord.setGL_Category_ID(createRequest.getGlCategoryId().getRepoId());
		headerRecord.setReversal_ID(SAPGLJournalId.toRepoId(createRequest.getReversalId()));
		saveRecord(headerRecord);

		final SAPGLJournal createdJournal = fromRecord(headerRecord, ImmutableList.of());

		createRequest.getLines()
				.forEach(createLineRequest -> createdJournal.addLine(createLineRequest, currencyConverter));

		save(createdJournal);

		return createdJournal;
	}
}
