package org.compiere.acct;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.GLCategoryId;
import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.FactAcctId;
import de.metas.acct.api.PostingType;
import de.metas.acct.api.impl.AcctSegmentType;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.doc.AcctDocRequiredServicesFacade;
import de.metas.acct.doc.PostingException;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.acct.vatcode.VATCode;
import de.metas.acct.vatcode.VATCodeMatchingRequest;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.costing.CostElementId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRate;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.dimension.Dimension;
import de.metas.document.engine.DocStatus;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.sales_region.SalesRegionId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.tax.api.TaxId;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_M_Movement;
import org.compiere.model.MAccount;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.OptionalInt;

public class FactLine
{
	private static final Logger log = LogManager.getLogger(FactLine.class);
	@NonNull @Getter(AccessLevel.PACKAGE) private final AcctDocRequiredServicesFacade services;

	@NonNull private final Doc<?> m_doc;
	@Nullable private final DocLine<?> m_docLine;

	@Nullable private FactAcctId factAcctId;
	@Nullable @Getter @Setter(AccessLevel.PACKAGE) private FactAcctId Counterpart_Fact_Acct_ID;

	@NonNull @Getter private ClientId AD_Client_ID;
	@NonNull @Getter private OrgId orgId;

	@Getter private final Timestamp dateTrx;
	@Getter private final Timestamp dateAcct;
	@Getter private final int C_Period_ID;

	@Getter @Setter(AccessLevel.PRIVATE) private BigDecimal amtAcctDr;
	@Getter @Setter(AccessLevel.PRIVATE) private BigDecimal amtAcctCr;
	@Getter @Setter(AccessLevel.PRIVATE) private CurrencyId currencyId;
	@Getter @Setter(AccessLevel.PRIVATE) private BigDecimal CurrencyRate;
	@Getter @Setter(AccessLevel.PRIVATE) private BigDecimal amtSourceDr;
	@Getter @Setter(AccessLevel.PRIVATE) private BigDecimal amtSourceCr;

	@Nullable @Getter private TaxId taxId;
	@Nullable @Getter private String vatCode;

	@Getter private final TableRecordReference docRecordRef;
	@Getter @Setter private int Line_ID;
	@Getter private final int SubLine_ID;
	@Getter private final String documentNo;
	@Getter private final DocBaseType docBaseType;
	@Getter private final DocStatus docStatus;
	@Getter private final DocTypeId C_DocType_ID;
	@Getter private final String poReference;

	@Getter @Setter(AccessLevel.PRIVATE) private ProductId M_Product_ID;
	@Getter private final Quantity qty;
	@Getter private int M_Locator_ID;

	@Getter @NonNull private final PostingType postingType;
	@Getter private final AcctSchema acctSchema;
	private final MAccount m_acct;
	@Getter private final ElementValueId account_ID;
	@Getter private final int C_SubAcct_ID;
	@Nullable @Getter private final AccountConceptualName accountConceptualName;

	@Getter @Setter(AccessLevel.PACKAGE) private CostElementId costElementId;

	@Getter @Setter private String description;

	@Getter @Setter(AccessLevel.PRIVATE) private OrderId C_OrderSO_ID;
	@Getter @Setter(AccessLevel.PACKAGE) private LocationId C_LocFrom_ID;
	@Getter @Setter(AccessLevel.PACKAGE) private LocationId C_LocTo_ID;
	@Getter private SectionCodeId M_SectionCode_ID;
	@Getter private OrgId AD_OrgTrx_ID;
	@Getter private BPartnerId C_BPartner_ID;
	@Getter private BPartnerLocationId C_BPartner_Location_ID;
	@Getter private BPartnerId C_BPartner2_ID;
	@Getter private final int GL_Budget_ID;
	@Getter private final GLCategoryId GL_Category_ID;
	@Getter private SalesRegionId C_SalesRegion_ID;
	@Getter @Setter(AccessLevel.PRIVATE) private ProjectId C_Project_ID;
	@Getter @Setter(AccessLevel.PRIVATE) private ActivityId C_Activity_ID;
	@Getter @Setter(AccessLevel.PRIVATE) private int C_Campaign_ID;
	@Getter @Setter(AccessLevel.PRIVATE) private int User1_ID;
	@Getter @Setter(AccessLevel.PRIVATE) private int User2_ID;
	@Getter @Setter(AccessLevel.PRIVATE) private int userElement1_ID;
	@Getter @Setter(AccessLevel.PRIVATE) private int userElement2_ID;
	@Getter @Setter(AccessLevel.PRIVATE) private String userElementString1;
	@Getter @Setter(AccessLevel.PRIVATE) private String userElementString2;
	@Getter @Setter(AccessLevel.PRIVATE) private String userElementString3;
	@Getter @Setter(AccessLevel.PRIVATE) private String userElementString4;
	@Getter @Setter(AccessLevel.PRIVATE) private String userElementString5;
	@Getter @Setter(AccessLevel.PRIVATE) private String userElementString6;
	@Getter @Setter(AccessLevel.PRIVATE) private String userElementString7;

	@Getter @Nullable private FAOpenItemTrxInfo openItemTrxInfo;

	private CurrencyConversionContext currencyConversionCtx = null;

	@Builder
	private FactLine(
			@NonNull final AcctDocRequiredServicesFacade services,
			@NonNull final Doc<?> doc,
			@Nullable final DocLine<?> docLine,
			@NonNull final TableRecordReference docRecordRef,
			final int Line_ID,
			final Integer SubLine_ID,
			@NonNull final PostingType postingType,
			@NonNull final AcctSchema acctSchema,
			@NonNull final MAccount account,
			@Nullable final AccountConceptualName accountConceptualName,
			@Nullable final Quantity qty,
			@Nullable final ClientId clientId,
			@Nullable final OrgId orgId,
			@Nullable final Integer M_Locator_ID,
			@Nullable final ProjectId projectId,
			@Nullable final ActivityId activityId,
			@Nullable final Integer campaignId,
			@Nullable final String additionalDescription)
	{
		this.services = services;
		this.m_doc = doc;
		this.m_docLine = docLine;
		this.docRecordRef = docRecordRef;
		this.Line_ID = Line_ID;
		this.SubLine_ID = SubLine_ID != null ? SubLine_ID : 0;

		this.postingType = postingType;
		this.acctSchema = acctSchema;
		this.m_acct = account;
		this.account_ID = ElementValueId.ofRepoId(account.getAccount_ID());
		this.C_SubAcct_ID = account.getC_SubAcct_ID();
		this.accountConceptualName = accountConceptualName;

		this.M_Locator_ID = M_Locator_ID != null ? M_Locator_ID : 0;
		this.C_Project_ID = projectId;
		this.C_Activity_ID = activityId;
		this.C_Campaign_ID = campaignId != null ? campaignId : 0;
		this.C_SalesRegion_ID = computeSalesRegionId(doc, docLine, account);

		this.AD_Client_ID = computeClientId(clientId, doc, this.m_acct);
		this.orgId = computeOrgIdEffective(orgId, M_Locator_ID, doc, docLine, account);
		//
		this.amtAcctCr = BigDecimal.ZERO;
		this.amtAcctDr = BigDecimal.ZERO;
		this.CurrencyRate = BigDecimal.ONE;
		this.amtSourceCr = BigDecimal.ZERO;
		this.amtSourceDr = BigDecimal.ZERO;

		//
		//
		//
		// setDocumentInfo
		//

		this.dateTrx = m_docLine != null ? m_docLine.getDateDocAsTimestamp() : doc.getDateDocAsTimestamp();
		this.dateAcct = m_docLine != null ? m_docLine.getDateAcctAsTimestamp() : doc.getDateAcctAsTimestamp();
		this.C_Period_ID = m_docLine != null && m_docLine.getC_Period_ID() > 0 ? m_docLine.getC_Period_ID() : doc.getC_Period_ID();

		//
		// Tax
		setTaxIdAndUpdateVatCode(m_docLine != null ? m_docLine.getTaxId().orElse(null) : null);

		// Document infos
		this.documentNo = doc.getDocumentNo();
		this.C_DocType_ID = doc.getC_DocType_ID();
		this.docBaseType = doc.getDocBaseType();
		this.poReference = doc.getPOReference();

		this.docStatus = doc.getDocStatus();

		// Description
		this.description = computeDescription(doc, docLine, additionalDescription);

		// Journal Info
		this.GL_Budget_ID = doc.getGL_Budget_ID();
		this.GL_Category_ID = doc.getGL_Category_ID();

		// Product
		if (m_docLine != null)
		{
			this.M_Product_ID = m_docLine.getProductId();
		}
		if (this.M_Product_ID == null)
		{
			this.M_Product_ID = doc.getProductId();
		}

		// Qty
		if (qty != null)
		{
			this.qty = qty;
		}
		else
		{
			this.qty = m_docLine != null && m_docLine.getQty() != null
					? m_docLine.getQty()
					: null;
		}

		// Loc From (maybe set earlier)
		if (this.C_LocFrom_ID == null && m_docLine != null)
		{
			this.C_LocFrom_ID = m_docLine.getLocationFromId();
		}
		if (this.C_LocFrom_ID == null)
		{
			this.C_LocFrom_ID = doc.getLocationFromId();
		}

		// Loc To (maybe set earlier)
		if (this.C_LocTo_ID == null && m_docLine != null)
		{
			this.C_LocTo_ID = m_docLine.getLocationToId();
		}
		if (this.C_LocTo_ID == null)
		{
			this.C_LocTo_ID = doc.getLocationToId();
		}

		// BPartner
		if (m_docLine != null && (m_docLine.getBPartnerId() != null || m_docLine.getBPartnerLocationId() != null))
		{
			setBPartnerIdAndLocation(m_docLine.getBPartnerId(), m_docLine.getBPartnerLocationId());
		}
		else
		{
			setBPartnerIdAndLocation(doc.getBPartnerId(), doc.getBPartnerLocationId());
		}

		// Sales Region from BPLocation/Sales Rep
		// NOTE: it will be set on save or on first call of getC_SalesRegion_ID() method.

		// Trx Org
		if (m_docLine != null)
		{
			this.AD_OrgTrx_ID = m_docLine.getOrgTrxId();
		}
		if (this.AD_OrgTrx_ID == null)
		{
			this.AD_OrgTrx_ID = doc.getOrgTrxId();
		}

		// Project
		if (m_docLine != null)
		{
			this.C_Project_ID = ProjectId.ofRepoIdOrNull(m_docLine.getC_Project_ID());
		}
		if (this.C_Project_ID == null)
		{
			this.C_Project_ID = doc.getC_Project_ID();
		}

		// Campaign
		if (m_docLine != null)
		{
			this.C_Campaign_ID = m_docLine.getC_Campaign_ID();
		}
		if (this.C_Campaign_ID <= 0)
		{
			this.C_Campaign_ID = doc.getC_Campaign_ID();
		}

		// Activity
		if (m_docLine != null)
		{
			this.C_Activity_ID = m_docLine.getActivityId();
		}
		if (this.C_Activity_ID == null)
		{
			this.C_Activity_ID = doc.getActivityId();
		}

		// Order
		if (m_docLine != null)
		{
			this.C_OrderSO_ID = m_docLine.getSalesOrderId();
		}
		if (this.C_OrderSO_ID == null)
		{
			this.C_OrderSO_ID = doc.getSalesOrderId();
		}

		// SectionCode
		if (m_docLine != null)
		{
			this.M_SectionCode_ID = m_docLine.getSectionCodeId();
		}
		if (this.M_SectionCode_ID == null)
		{
			this.M_SectionCode_ID = doc.getSectionCodeId();
		}

		// C_BPartner_ID2
		this.C_BPartner2_ID = CoalesceUtil.coalesceSuppliers(
				() -> (docLine != null) ? docLine.getBPartnerId2() : null,
				doc::getBPartnerId2
		);

		// User List 1
		if (m_docLine != null)
		{
			this.User1_ID = m_docLine.getUser1_ID();
		}
		if (this.User1_ID <= 0)
		{
			this.User1_ID = doc.getUser1_ID();
		}

		// User List 2
		if (m_docLine != null)
		{
			this.User2_ID = m_docLine.getUser2_ID();
		}
		if (this.User2_ID <= 0)
		{
			this.User2_ID = doc.getUser2_ID();
			// References in setAccount
		}

		this.userElement1_ID = computeAcctSchemaElementAsId(AcctSchemaElementType.UserElement1, acctSchema, m_doc, m_docLine).orElse(0);
		this.userElement2_ID = computeAcctSchemaElementAsId(AcctSchemaElementType.UserElement2, acctSchema, m_doc, m_docLine).orElse(0);

		this.userElementString1 = computeAcctSchemaElementAsString(AcctSchemaElementType.UserElementString1, this.acctSchema, m_doc, m_docLine).orElse(null);
		this.userElementString2 = computeAcctSchemaElementAsString(AcctSchemaElementType.UserElementString2, this.acctSchema, m_doc, m_docLine).orElse(null);
		this.userElementString3 = computeAcctSchemaElementAsString(AcctSchemaElementType.UserElementString3, this.acctSchema, m_doc, m_docLine).orElse(null);
		this.userElementString4 = computeAcctSchemaElementAsString(AcctSchemaElementType.UserElementString4, this.acctSchema, m_doc, m_docLine).orElse(null);
		this.userElementString5 = computeAcctSchemaElementAsString(AcctSchemaElementType.UserElementString5, this.acctSchema, m_doc, m_docLine).orElse(null);
		this.userElementString6 = computeAcctSchemaElementAsString(AcctSchemaElementType.UserElementString6, this.acctSchema, m_doc, m_docLine).orElse(null);
		this.userElementString7 = computeAcctSchemaElementAsString(AcctSchemaElementType.UserElementString7, this.acctSchema, m_doc, m_docLine).orElse(null);
	}   // FactLine

	private static ClientId computeClientId(
			@Nullable ClientId clientId,
			@NonNull Doc<?> doc,
			@NonNull MAccount acct)
	{
		if (clientId != null && clientId.isRegular())
		{
			return clientId;
		}

		final ClientId docClientId = doc.getClientId();
		if (docClientId.isRegular())
		{
			return docClientId;
		}

		final ClientId acctClientId = ClientId.ofRepoId(acct.getAD_Client_ID());
		if (acctClientId.isRegular())
		{
			return acctClientId;
		}

		return ClientId.SYSTEM; // shall not happen
	}

	private static OrgId computeOrgIdEffective(
			@Nullable final OrgId orgId,
			final Integer locatorRepoId,
			@NonNull final Doc<?> doc,
			@Nullable final DocLine<?> docLine,
			@NonNull final MAccount account)
	{
		// Prio 0 - provided Org
		if (orgId != null && orgId.isRegular())
		{
			return orgId;
		}

		// Prio 1 - get from locator - if exist
		if (locatorRepoId != null && locatorRepoId > 0)
		{
			final AcctDocRequiredServicesFacade services = doc.getServices();
			final OrgId locatorOrgId = services.getOrgIdByLocatorRepoId(locatorRepoId);
			if (locatorOrgId != null && locatorOrgId.isRegular())
			{
				return locatorOrgId;
			}
		}

		// Prio 2 - get from doc line - if exists (document context overwrites)
		if (docLine != null && docLine.getOrgId().isRegular())
		{
			return docLine.getOrgId();
		}

		// Prio 3 - get from doc - if not GL
		{
			final OrgId computedOrgId = DocBaseType.equals(DocBaseType.GLJournal, doc.getDocBaseType())
					? OrgId.ofRepoIdOrAny(account.getAD_Org_ID())
					: doc.getOrgId();
			if (computedOrgId.isRegular())
			{
				return computedOrgId;
			}
		}
		// Prio 4 - get from account - if not GL
		{
			final OrgId computedOrgId = DocBaseType.equals(DocBaseType.GLJournal, doc.getDocBaseType())
					? doc.getOrgId()
					: OrgId.ofRepoIdOrAny(account.getAD_Org_ID());
			if (computedOrgId.isRegular())
			{
				return computedOrgId;
			}
		}

		return OrgId.ANY; // shall not happen
	}

	@NonNull
	private static String computeDescription(
			@NonNull final Doc<?> doc,
			@Nullable final DocLine<?> docLine,
			@Nullable final String additionalDescription)
	{
		final StringBuilder result = new StringBuilder(doc.getDocumentNo());
		if (docLine != null)
		{
			result.append(" #").append(docLine.getLine());
			if (docLine.getDescription() != null)
			{
				result.append(" (").append(docLine.getDescription()).append(")");
			}
			else if (doc.getDescription() != null && doc.getDescription().length() > 0)
			{
				result.append(" (").append(doc.getDescription()).append(")");
			}
		}
		else if (doc.getDescription() != null && doc.getDescription().length() > 0)
		{
			result.append(" (").append(doc.getDescription()).append(")");
		}

		if (additionalDescription != null && !Check.isBlank(additionalDescription))
		{
			if (result.length() > 0)
			{
				result.append(" - ");
			}
			result.append(additionalDescription.trim());
		}

		return result.toString();
	}

	@Nullable
	private static SalesRegionId computeSalesRegionId(
			@NonNull final Doc<?> doc,
			@Nullable final DocLine<?> docLine,
			@NonNull final MAccount account)
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> docLine != null ? docLine.getC_SalesRegion_ID().orElse(null) : null,
				() -> doc.getC_SalesRegion_ID().orElse(null),
				() -> SalesRegionId.ofRepoIdOrNull(account.getC_SalesRegion_ID())
		);
	}

	public void setId(@NonNull final FactAcctId id)
	{
		if (this.factAcctId != null && !FactAcctId.equals(this.factAcctId, id))
		{
			throw new AdempiereException("Changing ID to " + id + " not allowed for " + this);
		}
		this.factAcctId = id;
	}

	@Nullable
	public FactAcctId getIdOrNull() {return factAcctId;}

	@NonNull
	public FactAcctId getIdNotNull()
	{
		if (this.factAcctId == null)
		{
			throw new AdempiereException("ID is null: " + this);
		}
		return this.factAcctId;
	}

	/**
	 * Create Reversal (negate DR/CR) of the line
	 *
	 * @param description new description
	 * @return reversal line
	 */
	public FactLine reverse(final String description)
	{
		final FactLine reversal = FactLine.builder()
				.services(services)
				.doc(m_doc)
				.docLine(m_docLine)
				.docRecordRef(this.docRecordRef)
				.Line_ID(this.Line_ID)
				.postingType(this.postingType)
				.acctSchema(acctSchema)
				.account(m_acct)
				.accountConceptualName(accountConceptualName)
				.qty(this.qty.negate())
				.clientId(AD_Client_ID)
				.orgId(orgId)
				.build();
		//
		reversal.setAmtSource(this.currencyId, this.amtSourceDr.negate(), this.amtSourceCr.negate());
		reversal.convert();
		reversal.setDescription(description);
		return reversal;
	}    // reverse

	/**
	 * Create Accrual (flip CR/DR) of the line
	 *
	 * @param description new description
	 * @return accrual line
	 */
	public FactLine accrue(final String description)
	{
		final FactLine accrual = FactLine.builder()
				.services(services)
				.doc(m_doc)
				.docLine(m_docLine)
				.docRecordRef(docRecordRef)
				.Line_ID(this.Line_ID)
				.postingType(this.postingType)
				.acctSchema(acctSchema)
				.account(m_acct)
				.accountConceptualName(accountConceptualName)
				.clientId(AD_Client_ID)
				.orgId(orgId)
				.build();
		//
		accrual.setAmtSource(currencyId, this.amtSourceCr, this.amtSourceDr);
		accrual.convert();
		accrual.setDescription(description);
		return accrual;
	}    // reverse

	private static OptionalInt computeAcctSchemaElementAsId(
			@NonNull AcctSchemaElementType acctSchemaElementType,
			@NonNull final AcctSchema acctSchema,
			@NonNull final Doc<?> doc,
			@Nullable final DocLine<?> docLine
	)
	{
		final AcctSchemaElement acctSchemaElement = acctSchema.getSchemaElementByType(acctSchemaElementType);
		if (acctSchemaElement == null)
		{
			return OptionalInt.empty();
		}

		final String displayColumnName = acctSchemaElement.getDisplayColumnName();
		int id = 0;
		if (docLine != null)
		{
			id = docLine.getValue(displayColumnName);
		}
		if (id <= 0)
		{
			id = doc.getValueAsIntOrZero(displayColumnName);
		}

		return id > 0 ? OptionalInt.of(id) : OptionalInt.empty();
	}

	private static Optional<String> computeAcctSchemaElementAsString(
			@NonNull final AcctSchemaElementType acctSchemaElementType,
			@NonNull final AcctSchema acctSchema,
			@NonNull final Doc<?> doc,
			@Nullable final DocLine<?> docLine)
	{
		final AcctSchemaElement acctSchemaElement = acctSchema.getSchemaElementByType(acctSchemaElementType);
		if (acctSchemaElement == null)
		{
			return Optional.empty();
		}

		final String columnName = acctSchemaElement.getDisplayColumnName();
		String value = null;
		if (docLine != null)
		{
			value = docLine.getValueAsString(columnName);
		}
		if (value == null)
		{
			value = doc.getValueAsString(columnName);
		}

		return Optional.ofNullable(value);
	}

	@NonNull
	public CurrencyId getAcctCurrencyId()
	{
		return getAcctSchema().getCurrencyId();
	}

	private void assertAcctCurrency(@NonNull final Money amt)
	{
		amt.assertCurrencyId(getAcctCurrencyId());
	}

	public boolean isZeroAmtSource() {return getAmtSourceDr().signum() == 0 && getAmtSourceCr().signum() == 0;}

	public boolean isZeroAmtSourceAndQty() {return isZeroAmtSource() && isZeroQty();}

	public boolean isZeroQty() {return qty == null || qty.isZero();}

	public void setAmtSource(@Nullable Money amtSourceDr, @Nullable Money amtSourceCr)
	{
		final CurrencyId currencyId = Money.getCommonCurrencyIdOfAll(amtSourceDr, amtSourceCr);
		setAmtSource(currencyId,
				amtSourceDr != null ? amtSourceDr.toBigDecimal() : BigDecimal.ZERO,
				amtSourceCr != null ? amtSourceCr.toBigDecimal() : BigDecimal.ZERO);
	}

	private void setAmtSource(final CurrencyId currencyId, @Nullable BigDecimal AmtSourceDr, @Nullable BigDecimal AmtSourceCr)
	{
		if (!acctSchema.isAllowNegativePosting())
		{
			// begin Victor Perez e-evolution 30.08.2005
			// fix Debit & Credit
			if (AmtSourceDr != null)
			{
				if (AmtSourceDr.compareTo(BigDecimal.ZERO) < 0)
				{
					AmtSourceCr = AmtSourceDr.abs();
					AmtSourceDr = BigDecimal.ZERO;
				}
			}
			if (AmtSourceCr != null)
			{
				if (AmtSourceCr.compareTo(BigDecimal.ZERO) < 0)
				{
					AmtSourceDr = AmtSourceCr.abs();
					AmtSourceCr = BigDecimal.ZERO;
				}
			}
			// end Victor Perez e-evolution 30.08.2005
		}

		setCurrencyId(currencyId);

		// Currency Precision
		final CurrencyPrecision precision = currencyId != null
				? services.getCurrencyStandardPrecision(currencyId)
				: ICurrencyDAO.DEFAULT_PRECISION;
		setAmtSourceDr(roundAmountToPrecision("AmtSourceDr", AmtSourceDr, precision));
		setAmtSourceCr(roundAmountToPrecision("AmtSourceCr", AmtSourceCr, precision));
	}   // setAmtSource

	/**
	 * Set Accounted Amounts (alternative: call convert)
	 */
	void setAmtAcct(@NonNull final Money amtAcctDr, @NonNull final Money amtAcctCr)
	{
		assertAcctCurrency(amtAcctDr);
		assertAcctCurrency(amtAcctCr);
		setAmtAcct(amtAcctDr.toBigDecimal(), amtAcctCr.toBigDecimal());
	}

	/**
	 * Set Accounted Amounts (alternative: call convert)
	 */
	void setAmtAcct(BigDecimal AmtAcctDr, BigDecimal AmtAcctCr)
	{
		if (AmtAcctDr == null)
		{
			AmtAcctDr = BigDecimal.ZERO;
		}
		if (AmtAcctCr == null)
		{
			AmtAcctCr = BigDecimal.ZERO;
		}

		if (!acctSchema.isAllowNegativePosting())
		{
			// begin Victor Perez e-evolution 30.08.2005
			// fix Debit & Credit
			if (AmtAcctDr.signum() < 0)
			{
				AmtAcctCr = AmtAcctDr.abs();
				AmtAcctDr = BigDecimal.ZERO;
			}
			if (AmtAcctCr.signum() < 0)
			{
				AmtAcctDr = AmtAcctCr.abs();
				AmtAcctCr = BigDecimal.ZERO;
			}
			// end Victor Perez e-evolution 30.08.2005
		}

		setAmtAcctDr(AmtAcctDr);
		setAmtAcctCr(AmtAcctCr);
	}   // setAmtAcct

	public void invertDrAndCrAmounts()
	{
		final BigDecimal amtSourceDr = getAmtSourceDr();
		final BigDecimal amtSourceCr = getAmtSourceCr();
		final BigDecimal amtAcctDr = getAmtAcctDr();
		final BigDecimal amtAcctCr = getAmtAcctCr();

		setAmtSourceDr(amtSourceCr);
		setAmtSourceCr(amtSourceDr);
		setAmtAcctDr(amtAcctCr);
		setAmtAcctCr(amtAcctDr);
	}

	public void invertDrAndCrAmountsIfTrue(final boolean condition)
	{
		if (!condition)
		{
			return;
		}
		invertDrAndCrAmounts();
	}

	@Nullable
	private BigDecimal roundAmountToPrecision(final String amountName, @Nullable final BigDecimal amt, final CurrencyPrecision precision)
	{
		if (amt == null)
		{
			return null;
		}

		if (amt.scale() <= precision.toInt())
		{
			return amt;
		}

		final BigDecimal amtRounded = precision.round(amt);

		// In case the amount was really changed, log a detailed warning
		final BigDecimal amtRoundingError = amt.subtract(amtRounded).abs();
		final BigDecimal errorMarginTolerated = NumberUtils.getErrorMarginForScale(8); // use a reasonable tolerance
		if (amtRoundingError.compareTo(errorMarginTolerated) > 0)
		{
			if (log.isDebugEnabled())
			{
				final PostingException ex = new PostingException("Precision fixed for " + amountName + ": " + amt + " -> " + amtRounded)
						.setAcctSchema(acctSchema)
						.setDocument(this.m_doc)
						.setDocLine(this.m_docLine)
						.setFactLine(this)
						.setParameter("Rounding error", amtRoundingError)
						.setParameter("Rounding error (tolerated)", errorMarginTolerated);
				log.debug(ex.getLocalizedMessage(), ex);
			}
		}

		return amtRounded;
	}

	public Money getAmtSourceDrAsMoney() {return Money.of(getAmtSourceDr(), getCurrencyId());}

	public Money getAmtSourceCrAsMoney() {return Money.of(getAmtSourceCr(), getCurrencyId());}

	public Money getAmtAcctDrAsMoney() {return Money.of(getAmtAcctDr(), getAcctCurrencyId());}

	public Money getAmtAcctCrAsMoney() {return Money.of(getAmtAcctCr(), getAcctCurrencyId());}

	public Doc<?> getDoc() {return m_doc;}

	public DocLine<?> getDocLine()
	{
		return m_docLine;
	}    // getDocLine

	public void addDescription(final String description)
	{
		final String original = getDescription();
		if (original == null || Check.isBlank(original))
		{
			setDescription(description);
		}
		else
		{
			setDescription(original + " - " + description);
		}
	}    // addDescription

	private void setLocation(@Nullable final LocationId C_Location_ID, final boolean isFrom)
	{
		if (isFrom)
		{
			setC_LocFrom_ID(C_Location_ID);
		}
		else
		{
			setC_LocTo_ID(C_Location_ID);
		}
	}

	@SuppressWarnings("unused")
	public void setLocationFromLocator(final int M_Locator_ID, final boolean isFrom)
	{
		getServices().getLocationIdByLocatorRepoId(M_Locator_ID)
				.ifPresent(locationId -> setLocation(locationId, isFrom));
	}

	public void setLocationFromBPartner(@Nullable final BPartnerLocationId C_BPartner_Location_ID, final boolean isFrom)
	{
		getServices().getLocationId(C_BPartner_Location_ID)
				.ifPresent(locationId -> setLocation(locationId, isFrom));
	}

	public void setLocationFromOrg(@NonNull final OrgId orgId, final boolean isFrom)
	{
		if (!orgId.isRegular())
		{
			return;
		}

		getServices().getLocationId(orgId)
				.ifPresent(locationId -> setLocation(locationId, isFrom));
	}

	public Balance getSourceBalance()
	{
		return Balance.of(this.currencyId, this.amtSourceDr, this.amtSourceCr);
	}

	/**
	 * @return true if DR source balance
	 */
	public boolean isDrSourceBalance()
	{
		return getSourceBalance().signum() >= 0;
	}

	public Balance getAcctBalance()
	{
		return Balance.of(getAcctCurrencyId(), getAmtAcctDr(), getAmtAcctCr());
	}

	/**
	 * @return AmtSourceDr/AmtAcctDr or AmtSourceCr/AmtAcctCr, which one is not ZERO
	 * @throws IllegalStateException if both of them are not ZERO
	 */
	public AmountSourceAndAcct getAmtSourceAndAcctDrOrCr()
	{
		final BigDecimal amtAcctDr = getAmtAcctDr();
		final int amtAcctDrSign = amtAcctDr == null ? 0 : amtAcctDr.signum();
		final BigDecimal amtAcctCr = getAmtAcctCr();
		final int amtAcctCrSign = amtAcctCr == null ? 0 : amtAcctCr.signum();

		if (amtAcctDrSign != 0 && amtAcctCrSign == 0)
		{
			final BigDecimal amtSourceDr = getAmtSourceDr();
			return AmountSourceAndAcct.of(amtSourceDr, amtAcctDr);
		}
		else if (amtAcctDrSign == 0 && amtAcctCrSign != 0)
		{
			final BigDecimal amtSourceCr = getAmtSourceCr();
			return AmountSourceAndAcct.of(amtSourceCr, amtAcctCr);
		}
		else if (amtAcctDrSign == 0 /*&& amtAcctCrSign == 0*/)
		{
			return AmountSourceAndAcct.ZERO;
		}
		else
		{
			// shall not happen
			throw new IllegalStateException("Both AmtAcctDr and AmtAcctCr are not zero: " + this);
		}

	}

	/**
	 * @return true if account is a balance sheet account
	 */
	public boolean isBalanceSheet() {return m_acct.isBalanceSheet();}

	/**
	 * Correct Accounting Amount.
	 *
	 * <pre>
	 *  Example:    1       -1      1       -1
	 *  Old         100/0   100/0   0/100   0/100
	 *  New         99/0    101/0   0/99    0/101
	 * </pre>
	 */
	void currencyCorrect(final Money deltaAmount)
	{
		assertAcctCurrency(deltaAmount);

		if (deltaAmount.isZero())
		{
			return;
		}

		final boolean negative = deltaAmount.isNegative();
		final boolean adjustDr = getAmtAcctDr().abs().compareTo(getAmtAcctCr().abs()) > 0;

		if (adjustDr)
		{
			if (negative)
			{
				setAmtAcctDr(getAmtAcctDr().subtract(deltaAmount.toBigDecimal()));
			}
			else
			{
				setAmtAcctDr(getAmtAcctDr().subtract(deltaAmount.toBigDecimal()));
			}
		}
		else if (negative)
		{
			setAmtAcctCr(getAmtAcctCr().add(deltaAmount.toBigDecimal()));
		}
		else
		{
			setAmtAcctCr(getAmtAcctCr().add(deltaAmount.toBigDecimal()));
		}
	}    // currencyCorrect

	/**
	 * Convert to Accounted Currency
	 */
	public void convert()
	{
		final CurrencyId acctCurrencyId = acctSchema.getCurrencyId();

		// Document has no currency => set it from accounting schema
		if (getCurrencyId() == null)
		{
			setCurrencyId(acctCurrencyId);
		}

		final CurrencyId currencyId = getCurrencyId();

		// If same currency as the accounting schema => no conversion is needed
		if (acctCurrencyId.equals(currencyId))
		{
			setAmtAcctDr(getAmtSourceDr());
			setAmtAcctCr(getAmtSourceCr());
			setCurrencyRate(BigDecimal.ONE);
		}
		else
		{
			final CurrencyRate currencyRate = getCurrencyRate(currencyId, acctCurrencyId);
			final BigDecimal amtAcctDr = currencyRate.convertAmount(getAmtSourceDr());
			final BigDecimal amtAcctCr = currencyRate.convertAmount(getAmtSourceCr());

			setAmtAcctDr(amtAcctDr);
			setAmtAcctCr(amtAcctCr);
			setCurrencyRate(currencyRate.getConversionRate());
		}
	}    // convert

	public CurrencyRate getCurrencyRate(final CurrencyId currencyFromId, final CurrencyId currencyToId)
	{
		final CurrencyConversionContext conversionCtx = getCurrencyConversionCtx();
		return services.getCurrencyRate(conversionCtx, currencyFromId, currencyToId);
	}

	public CurrencyRate getCurrencyRateFromDocumentToAcctCurrency() {return getCurrencyRate(getCurrencyId(), getAcctCurrencyId());}

	public void setCurrencyConversionCtx(final CurrencyConversionContext currencyConversionCtx)
	{
		this.currencyConversionCtx = currencyConversionCtx;
	}

	private CurrencyConversionContext getCurrencyConversionCtx()
	{
		// Use preset currency conversion context, if any
		if (currencyConversionCtx != null)
		{
			return currencyConversionCtx;
		}

		//
		// Conversion date
		LocalDateAndOrgId convDate;
		if (m_docLine != null)            // get from line
		{
			convDate = m_docLine.getDateAcct();
		}
		else
		{
			convDate = m_doc.getDateAcct();
		}

		//
		// Conversion Type
		CurrencyConversionTypeId conversionTypeId = m_docLine != null
				? m_docLine.getCurrencyConversionTypeId()
				: null;
		if (conversionTypeId == null)    // get from header
		{
			conversionTypeId = m_doc.getCurrencyConversionTypeId();
		}

		return services.createCurrencyConversionContext(convDate, conversionTypeId, m_doc.getClientId());
	}

	MAccount getAccount()
	{
		return m_acct;
	}    // getAccount

	@Override
	public String toString()
	{
		String sb = "FactLine=[" + this.docRecordRef
				+ "," + this.accountConceptualName + "/" + (m_acct != null ? m_acct.getCombination() : "?")
				+ ",Cur=" + this.currencyId
				+ ", DR=" + this.amtSourceDr + "|" + this.amtAcctDr
				+ ", CR=" + this.amtSourceCr + "|" + this.amtAcctCr
				+ (this.Line_ID > 0 ? ", Line=" + this.Line_ID : "");
		if (this.CurrencyRate != null && this.CurrencyRate.signum() != 0 && this.CurrencyRate.compareTo(BigDecimal.ONE) != 0)
		{
			sb = sb + ", currencyRate=" + this.CurrencyRate;
		}
		sb = sb + "]";
		return sb;
	}

	public void updateBeforeSaveNew()
	{
		// Set Default Account Info
		if (getM_Product_ID() == null)
		{
			setM_Product_ID(ProductId.ofRepoIdOrNull(m_acct.getM_Product_ID()));
		}
		if (getC_LocFrom_ID() == null)
		{
			setC_LocFrom_ID(LocationId.ofRepoIdOrNull(m_acct.getC_LocFrom_ID()));
		}
		if (getC_LocTo_ID() == null)
		{
			setC_LocTo_ID(LocationId.ofRepoIdOrNull(m_acct.getC_LocTo_ID()));
		}
		if (getC_BPartner_ID() == null)
		{
			setBPartnerId(BPartnerId.ofRepoIdOrNull(m_acct.getC_BPartner_ID()));
		}
		if (getAD_OrgTrx_ID() == null)
		{
			setAD_OrgTrx_ID(OrgId.ofRepoIdOrNull(m_acct.getAD_OrgTrx_ID()));
		}
		if (getC_Project_ID() == null)
		{
			setC_Project_ID(ProjectId.ofRepoIdOrNull(m_acct.getC_Project_ID()));
		}
		if (getC_Campaign_ID() <= 0)
		{
			setC_Campaign_ID(m_acct.getC_Campaign_ID());
		}
		if (getC_Activity_ID() == null)
		{
			setC_Activity_ID(ActivityId.ofRepoIdOrNull(m_acct.getC_Activity_ID()));
		}
		if (getC_OrderSO_ID() == null)
		{
			setC_OrderSO_ID(OrderId.ofRepoIdOrNull(m_acct.getC_OrderSO_ID()));
		}
		if (M_SectionCode_ID == null)
		{
			M_SectionCode_ID = SectionCodeId.ofRepoIdOrNull(m_acct.getM_SectionCode_ID());
		}
		if (getUser1_ID() <= 0)
		{
			setUser1_ID(m_acct.getUser1_ID());
		}
		if (getUser2_ID() <= 0)
		{
			setUser2_ID(m_acct.getUser2_ID());
		}

		// setVATCodeIfApplies();
		//
		// //
		// // Revenue Recognition for AR Invoices
		// if (DocBaseType.ARInvoice.equals(m_doc.getDocBaseType())
		// 		&& m_docLine != null
		// 		&& m_docLine.getC_RevenueRecognition_ID() > 0)
		// {
		// 	setAccount_ID(createRevenueRecognition(
		// 			m_docLine.getC_RevenueRecognition_ID(),
		// 			m_docLine.get_ID(),
		// 			toAccountDimension()));
		// }

		updateCurrencyRateIfNotSet();
	}

	public AccountDimension toAccountDimension()
	{
		return AccountDimension.builder()
				.setAcctSchemaId(this.acctSchema.getId())
				.setAD_Client_ID(ClientId.toRepoId(this.AD_Client_ID))
				.setAD_Org_ID(getOrgId().getRepoId())
				.setC_ElementValue_ID(this.account_ID)
				.setC_SubAcct_ID(this.C_SubAcct_ID)
				.setM_Product_ID(ProductId.toRepoId(this.M_Product_ID))
				.setC_BPartner_ID(BPartnerId.toRepoId(this.C_BPartner_ID))
				.setAD_OrgTrx_ID(OrgId.toRepoId(this.AD_OrgTrx_ID))
				.setC_LocFrom_ID(LocationId.toRepoId(this.C_LocFrom_ID))
				.setC_LocTo_ID(LocationId.toRepoId(this.C_LocTo_ID))
				.setC_SalesRegion_ID(getC_SalesRegion_ID())
				.setC_Project_ID(ProjectId.toRepoId(this.C_Project_ID))
				.setC_Campaign_ID(this.C_Campaign_ID)
				.setC_Activity_ID(ActivityId.toRepoId(this.C_Activity_ID))
				.setUser1_ID(this.User1_ID)
				.setUser2_ID(this.User2_ID)
				.setUserElement1_ID(this.userElement1_ID)
				.setUserElement2_ID(this.userElement2_ID)
				.setUserElementString1(this.userElementString1)
				.setUserElementString2(this.userElementString2)
				.setUserElementString3(this.userElementString3)
				.setUserElementString4(this.userElementString4)
				.setUserElementString5(this.userElementString5)
				.setUserElementString6(this.userElementString6)
				.setUserElementString7(this.userElementString7)
				.setSalesOrderId(OrderId.toRepoId(this.C_OrderSO_ID))
				.setM_SectionCode_ID(SectionCodeId.toRepoId(this.M_SectionCode_ID))
				.build();
	}

	/**************************************************************************
	 * Update Line with reversed Original Amount in Accounting Currency.
	 * Also copies original dimensions like Project, etc.
	 * Called from Doc_MatchInv
	 *
	 * @return true if success
	 */
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean updateReverseLine(
			final String tableName,
			final int Record_ID,
			final int Line_ID,
			final BigDecimal multiplier)
	{
		final AdTableId adTableId = TableIdsCache.instance.getTableId(tableName).orElseThrow(() -> new AdempiereException("No AD_Table_ID found for " + tableName));

		final IQueryBuilder<I_Fact_Acct> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fact_Acct.class)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_C_AcctSchema_ID, getAcctSchemaId())
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, Record_ID)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Line_ID, Line_ID)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Account_ID, m_acct.getAccount_ID());
		if (I_M_Movement.Table_Name.equals(tableName))
		{
			queryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_M_Locator_ID, getM_Locator_ID());
		}

		final I_Fact_Acct fact = queryBuilder.create().firstOnly(I_Fact_Acct.class);
		if (fact != null)
		{
			final CurrencyId currencyId = CurrencyId.ofRepoId(fact.getC_Currency_ID());
			// Accounted Amounts - reverse
			final BigDecimal dr = fact.getAmtAcctDr();
			final BigDecimal cr = fact.getAmtAcctCr();

			setAmtAcct(
					roundAmountToPrecision("AmtAcctDr", cr.multiply(multiplier), getAcctSchema().getStandardPrecision()),
					roundAmountToPrecision("AmtAcctCr", dr.multiply(multiplier), getAcctSchema().getStandardPrecision()));

			//
			// Fixing source amounts
			final BigDecimal drSourceAmt = fact.getAmtSourceDr();
			final BigDecimal crSourceAmt = fact.getAmtSourceCr();
			setAmtSource(
					currencyId,
					crSourceAmt.multiply(multiplier),
					drSourceAmt.multiply(multiplier));

			// Dimensions
			setAD_OrgTrx_ID(OrgId.ofRepoIdOrAny(fact.getAD_OrgTrx_ID()));
			setC_Project_ID(ProjectId.ofRepoIdOrNull(fact.getC_Project_ID()));
			setC_Activity_ID(ActivityId.ofRepoIdOrNull(fact.getC_Activity_ID()));
			setC_Campaign_ID(fact.getC_Campaign_ID());
			this.C_SalesRegion_ID = SalesRegionId.ofRepoIdOrNull(fact.getC_SalesRegion_ID());
			setC_LocFrom_ID(LocationId.ofRepoIdOrNull(fact.getC_LocFrom_ID()));
			setC_LocTo_ID(LocationId.ofRepoIdOrNull(fact.getC_LocTo_ID()));
			setM_Product_ID(ProductId.ofRepoIdOrNull(fact.getM_Product_ID()));
			this.M_Locator_ID = fact.getM_Locator_ID();
			setUser1_ID(fact.getUser1_ID());
			setUser2_ID(fact.getUser2_ID());
			//setC_UOM_ID(fact.getC_UOM_ID());
			this.taxId = TaxId.ofRepoIdOrNull(fact.getC_Tax_ID());
			this.vatCode = fact.getVATCode();
			this.orgId = OrgId.ofRepoIdOrAny(fact.getAD_Org_ID());
			setC_OrderSO_ID(OrderId.ofRepoIdOrNull(fact.getC_OrderSO_ID()));
			this.M_SectionCode_ID = SectionCodeId.ofRepoIdOrNull(fact.getM_SectionCode_ID());
			this.C_BPartner2_ID = BPartnerId.ofRepoIdOrNull(fact.getC_BPartner2_ID());

			return true; // success
		}
		else
		{
			//
			// Log the warning only if log level is info.
			// NOTE: we changed the level from WARNING to INFO because it seems this turned to be a common case,
			// since we are eagerly posting the MatchInv when Invoice/InOut was posted.
			// (and MatchInv needs to have the Invoice and InOut posted before)
			if (log.isInfoEnabled())
			{
				log.info("Not Found (try later) "
						+ ",C_AcctSchema_ID=" + this.acctSchema
						+ ", adTableId=" + adTableId
						+ ",Record_ID=" + Record_ID
						+ ",Line_ID=" + Line_ID
						+ ", Account_ID=" + m_acct.getAccount_ID());
			}

			return false; // not updated
		}
	}   // updateReverseLine

	public AcctSchemaId getAcctSchemaId() {return getAcctSchema().getId();}

	public void setAD_Org_ID(@NonNull final OrgId orgId) {this.orgId = orgId;}

	public void setAD_Org_ID(final int orgId) {setAD_Org_ID(OrgId.ofRepoIdOrAny(orgId));}

	public void setAD_OrgTrx_ID(final OrgId orgId) {this.AD_OrgTrx_ID = orgId;}

	public void setFromDimension(@NonNull final Dimension dimension)
	{
		setC_Project_ID(dimension.getProjectId());
		setC_Campaign_ID(dimension.getCampaignId());
		setC_Activity_ID(dimension.getActivityId());
		setC_OrderSO_ID(dimension.getSalesOrderId());
		this.M_SectionCode_ID = dimension.getSectionCodeId();
		setM_Product_ID(dimension.getProductId());
		this.C_BPartner2_ID = dimension.getBpartnerId2();
		setUser1_ID(dimension.getUser1_ID());
		setUser2_ID(dimension.getUser2_ID());
		setUserElement1_ID(dimension.getUserElement1Id());
		setUserElement2_ID(dimension.getUserElement2Id());
		setUserElementString1(dimension.getUserElementString1());
		setUserElementString2(dimension.getUserElementString2());
		setUserElementString3(dimension.getUserElementString3());
		setUserElementString4(dimension.getUserElementString4());
		setUserElementString5(dimension.getUserElementString5());
		setUserElementString6(dimension.getUserElementString6());
		setUserElementString7(dimension.getUserElementString7());
	}

	public void updateFromDimension(final AccountDimension dim)
	{
		if (dim.getAcctSchemaId() != null)
		{
			Check.assumeEquals(this.acctSchema.getId(), dim.getAcctSchemaId());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Client))
		{
			// fa.setAD_Client_ID(dim.getAD_Client_ID());
			de.metas.util.Check.assume(ClientId.equals(getAD_Client_ID(), ClientId.ofRepoIdOrNull(dim.getAD_Client_ID())), "Fact_Acct and dimension shall have the same AD_Client_ID");
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Organization))
		{
			setAD_Org_ID(OrgId.ofRepoIdOrAny(dim.getAD_Org_ID()));
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Account))
		{
			Check.assumeEquals(this.account_ID, dim.getC_ElementValue_ID(), "Account_ID");
		}
		if (dim.isSegmentValueSet(AcctSegmentType.SubAccount))
		{
			Check.assumeEquals(this.C_SubAcct_ID, dim.getC_SubAcct_ID(), "C_SubAcct_ID");
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Product))
		{
			setM_Product_ID(ProductId.ofRepoIdOrNull(dim.getM_Product_ID()));
		}
		if (dim.isSegmentValueSet(AcctSegmentType.BPartner))
		{
			setBPartnerId(BPartnerId.ofRepoIdOrNull(dim.getC_BPartner_ID()));
		}
		if (dim.isSegmentValueSet(AcctSegmentType.OrgTrx))
		{
			setAD_OrgTrx_ID(OrgId.ofRepoIdOrAny(dim.getAD_OrgTrx_ID()));
		}
		if (dim.isSegmentValueSet(AcctSegmentType.LocationFrom))
		{
			setC_LocFrom_ID(LocationId.ofRepoIdOrNull(dim.getC_LocFrom_ID()));
		}
		if (dim.isSegmentValueSet(AcctSegmentType.LocationTo))
		{
			setC_LocTo_ID(LocationId.ofRepoIdOrNull(dim.getC_LocTo_ID()));
		}
		if (dim.isSegmentValueSet(AcctSegmentType.SalesRegion))
		{
			this.C_SalesRegion_ID = SalesRegionId.ofRepoIdOrNull(dim.getC_SalesRegion_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Project))
		{
			setC_Project_ID(ProjectId.ofRepoIdOrNull(dim.getC_Project_ID()));
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Campaign))
		{
			setC_Campaign_ID(dim.getC_Campaign_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Activity))
		{
			setC_Activity_ID(ActivityId.ofRepoIdOrNull(dim.getC_Activity_ID()));
		}
		if (dim.isSegmentValueSet(AcctSegmentType.SalesOrder))
		{
			setC_OrderSO_ID(OrderId.ofRepoIdOrNull(dim.getSalesOrderId()));
		}
		if (dim.isSegmentValueSet(AcctSegmentType.SectionCode))
		{
			this.M_SectionCode_ID = SectionCodeId.ofRepoIdOrNull(dim.getM_SectionCode_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserList1))
		{
			setUser1_ID(dim.getUser1_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserList2))
		{
			setUser2_ID(dim.getUser2_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElement1))
		{
			setUserElement1_ID(dim.getUserElement1_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElement2))
		{
			setUserElement2_ID(dim.getUserElement2_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElementString1))
		{
			setUserElementString1(dim.getUserElementString1());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElementString2))
		{
			setUserElementString2(dim.getUserElementString2());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElementString3))
		{
			setUserElementString3(dim.getUserElementString3());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElementString4))
		{
			setUserElementString4(dim.getUserElementString4());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElementString5))
		{
			setUserElementString5(dim.getUserElementString5());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElementString6))
		{
			setUserElementString6(dim.getUserElementString6());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElementString7))
		{
			setUserElementString7(dim.getUserElementString7());
		}
	}

	public void setBPartnerIdAndLocation(final int bpartnerRepoId, final int bpartnerLocationRepoId)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartnerRepoId);
		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoIdOrNull(bpartnerId, bpartnerLocationRepoId);
		setBPartnerIdAndLocation(bpartnerId, bpartnerLocationId);
	}

	public void setBPartnerIdAndLocation(@Nullable final BPartnerId bPartnerId, @Nullable final BPartnerLocationId bPartnerLocationId)
	{
		if (bPartnerId != null && bPartnerLocationId != null)
		{
			Check.assume(bPartnerLocationId.getBpartnerId().getRepoId() == bPartnerId.getRepoId(),
					"BPartnerId && BPartnerLocation.BPartnerId must match!"
							+ " BPartnerId=" + bPartnerId.getRepoId()
							+ " BPartnerLocationID=" + bPartnerLocationId.getRepoId());
		}

		if (bPartnerLocationId != null)
		{
			this.C_BPartner_ID = bPartnerLocationId.getBpartnerId();
			this.C_BPartner_Location_ID = bPartnerLocationId;
		}
		else if (bPartnerId != null)
		{
			this.C_BPartner_ID = bPartnerId;
			this.C_BPartner_Location_ID = null;
		}
		else
		{
			this.C_BPartner_ID = null;
			this.C_BPartner_Location_ID = null;
		}
	}

	public void setBPartnerId(@Nullable final BPartnerId bPartnerId)
	{
		if (BPartnerId.equals(this.C_BPartner_ID, bPartnerId))
		{
			return;
		}

		this.C_BPartner_ID = bPartnerId;
		this.C_BPartner_Location_ID = null;
	}

	@NonNull
	private BigDecimal computeCurrencyRate()
	{
		final BigDecimal amtAcct = getAmtAcctDr().add(getAmtAcctCr());
		final BigDecimal amtSource = getAmtSourceDr().add(getAmtSourceCr());
		if (amtAcct.signum() == 0 || amtSource.signum() == 0)
		{
			return BigDecimal.ZERO; // dev-note: does not matter
		}

		final CurrencyPrecision schemaCurrencyPrecision = getAcctSchema().getStandardPrecision();
		return amtAcct.divide(amtSource, schemaCurrencyPrecision.toInt(), schemaCurrencyPrecision.getRoundingMode());
	}

	public void updateCurrencyRateIfNotSet()
	{
		if (getCurrencyRate().signum() == 0 || getCurrencyRate().compareTo(BigDecimal.ONE) == 0)
		{
			setCurrencyRate(computeCurrencyRate());
		}
	}

	public void setTaxIdAndUpdateVatCode(@Nullable final TaxId taxId)
	{
		if (TaxId.equals(this.taxId, taxId))
		{
			return;
		}

		this.taxId = taxId;
		this.vatCode = computeVATCode().map(VATCode::getCode).orElse(null);

	}

	private Optional<VATCode> computeVATCode()
	{
		if (taxId == null)
		{
			return Optional.empty();
		}

		final boolean isSOTrx = m_docLine != null ? m_docLine.isSOTrx() : m_doc.isSOTrx();
		return services.findVATCode(VATCodeMatchingRequest.builder()
				.setC_AcctSchema_ID(getAcctSchemaId().getRepoId())
				.setC_Tax_ID(taxId.getRepoId())
				.setIsSOTrx(isSOTrx)
				.setDate(this.dateAcct)
				.build());
	}

	public void updateFAOpenItemTrxInfo()
	{
		if (openItemTrxInfo != null)
		{
			return;
		}

		this.openItemTrxInfo = services.computeOpenItemTrxInfo(this).orElse(null);
	}

	void setOpenItemTrxInfo(@Nullable final FAOpenItemTrxInfo openItemTrxInfo)
	{
		this.openItemTrxInfo = openItemTrxInfo;
	}
}
