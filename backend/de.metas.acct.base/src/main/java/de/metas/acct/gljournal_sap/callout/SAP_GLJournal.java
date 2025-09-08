package de.metas.acct.gljournal_sap.callout;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyRate;
import de.metas.currency.ICurrencyBL;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.ad.ui.spi.TabCallout;
import org.adempiere.service.ClientId;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Callout(value = I_SAP_GLJournal.class, recursionAvoidanceLevel = Callout.RecursionAvoidanceLevel.CalloutMethod)
@TabCallout(I_SAP_GLJournal.class)
@Component
public class SAP_GLJournal implements ITabCallout
{
	private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IDocumentNoBuilderFactory documentNoBuilderFactory;

	public SAP_GLJournal(final IDocumentNoBuilderFactory documentNoBuilderFactory) {this.documentNoBuilderFactory = documentNoBuilderFactory;}

	@PostConstruct
	public void postConstruct()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_SAP_GLJournal glJournal = calloutRecord.getModel(I_SAP_GLJournal.class);

		final AcctSchemaId acctSchemaId = acctSchemaDAO.getAcctSchemaIdByClientAndOrgOrNull(
				ClientId.ofRepoId(glJournal.getAD_Client_ID()),
				OrgId.ofRepoIdOrAny(glJournal.getAD_Org_ID()));
		if (acctSchemaId != null)
		{
			glJournal.setC_AcctSchema_ID(acctSchemaId.getRepoId());
			//onC_AcctSchema_ID(glJournal);
		}
	}

	@CalloutMethod(columnNames = I_SAP_GLJournal.COLUMNNAME_C_AcctSchema_ID)
	public void onC_AcctSchema_ID(final I_SAP_GLJournal glJournal)
	{
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoIdOrNull(glJournal.getC_AcctSchema_ID());
		if (acctSchemaId == null)
		{
			return;
		}

		final AcctSchema acctSchema = acctSchemaDAO.getById(acctSchemaId);
		glJournal.setAcct_Currency_ID(acctSchema.getCurrencyId().getRepoId());
		if (glJournal.getC_Currency_ID() <= 0)
		{
			glJournal.setC_Currency_ID(acctSchema.getCurrencyId().getRepoId());
		}
	}

	@CalloutMethod(columnNames = I_SAP_GLJournal.COLUMNNAME_C_DocType_ID)
	public void onC_DocType_ID(final I_SAP_GLJournal glJournal)
	{
		final IDocumentNoInfo documentNoInfo = documentNoBuilderFactory
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(DocTypeId.optionalOfRepoId(glJournal.getC_DocType_ID())
						.map(docTypeBL::getById)
						.orElse(null))
				.setOldDocumentNo(glJournal.getDocumentNo())
				.setDocumentModel(glJournal)
				.buildOrNull();
		if (documentNoInfo != null && documentNoInfo.isDocNoControlled())
		{
			glJournal.setDocumentNo(documentNoInfo.getDocumentNo());
		}
	}

	@CalloutMethod(columnNames = I_SAP_GLJournal.COLUMNNAME_DateDoc)
	public void onDateDoc(final I_SAP_GLJournal glJournal)
	{
		final Timestamp dateDoc = glJournal.getDateDoc();
		if (dateDoc == null)
		{
			return;
		}

		glJournal.setDateAcct(dateDoc);
	}

	@CalloutMethod(columnNames = {
			I_SAP_GLJournal.COLUMNNAME_DateAcct,
			I_SAP_GLJournal.COLUMNNAME_Acct_Currency_ID,
			I_SAP_GLJournal.COLUMNNAME_C_Currency_ID,
			I_SAP_GLJournal.COLUMNNAME_C_ConversionType_ID })
	public void updateCurrencyRate(final I_SAP_GLJournal glJournal)
	{
		final BigDecimal currencyRate = computeCurrencyRate(glJournal);
		if (currencyRate == null)
		{
			return;
		}

		glJournal.setCurrencyRate(currencyRate);
	}

	@Nullable
	private BigDecimal computeCurrencyRate(final I_SAP_GLJournal glJournal)
	{
		//
		// Extract data from source Journal
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(glJournal.getC_Currency_ID());
		if (currencyId == null)
		{
			// not set yet
			return null;
		}

		final CurrencyId acctCurrencyId = CurrencyId.ofRepoIdOrNull(glJournal.getAcct_Currency_ID());
		if (acctCurrencyId == null)
		{
			return null;
		}

		final CurrencyConversionTypeId conversionTypeId = CurrencyConversionTypeId.ofRepoIdOrNull(glJournal.getC_ConversionType_ID());

		Instant dateAcct = TimeUtil.asInstant(glJournal.getDateAcct());
		if (dateAcct == null)
		{
			dateAcct = SystemTime.asInstant();
		}

		final ClientId adClientId = ClientId.ofRepoId(glJournal.getAD_Client_ID());
		final OrgId adOrgId = OrgId.ofRepoId(glJournal.getAD_Org_ID());

		return currencyBL.getCurrencyRateIfExists(
						currencyId,
						acctCurrencyId,
						dateAcct,
						conversionTypeId,
						adClientId,
						adOrgId)
				.map(CurrencyRate::getConversionRate)
				.orElse(BigDecimal.ZERO);
	}
}
