package org.compiere.acct;

<<<<<<< HEAD
import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AccountId;
=======
import de.metas.acct.AccountConceptualName;
import de.metas.acct.GLCategoryId;
import de.metas.acct.api.AccountDimension;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.AcctSchemaId;
<<<<<<< HEAD
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.PostingException;
import de.metas.acct.vatcode.IVATCodeDAO;
import de.metas.acct.vatcode.VATCode;
import de.metas.acct.vatcode.VATCodeMatchingRequest;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRate;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyDAO;
import de.metas.location.LocationId;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_RevenueRecognition_Plan;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_M_Movement;
import org.compiere.model.MAccount;
import org.compiere.model.X_Fact_Acct;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Accounting Fact Entry.
 *
 * @author Jorg Janke
 * @version $Id: FactLine.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 * <p>
 * Contributor(s):
 * Chris Farley: Fix Bug [ 1657372 ] M_MatchInv records can not be balanced
 * https://sourceforge.net/forum/message.php?msg_id=4151117
 * Carlos Ruiz - globalqss: Add setAmtAcct method rounded by Currency
 * Armen Rizal, Goodwill Consulting
 * <li>BF [ 1745154 ] Cost in Reversing Material Related Docs Bayu Sistematika -
 * <li>BF [ 2213252 ] Matching Inv-Receipt generated unproperly value for src
 * amt Teo Sarca
 * <li>FR [ 2819081 ] FactLine.getDocLine should be public https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2819081&group_id=176962
 */
public final class FactLine extends X_Fact_Acct
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1287219868802190295L;

	FactLine(final int AD_Table_ID, final int Record_ID)
	{
		this(AD_Table_ID, Record_ID, 0);
	}

	/**
	 * @param AD_Table_ID - Table of Document Source
	 * @param Record_ID   - Record of document
	 * @param Line_ID     - Optional line id
	 */
	FactLine(final int AD_Table_ID, final int Record_ID, final int Line_ID)
	{
		super(Env.getCtx(), 0, ITrx.TRXNAME_ThreadInherited);
		setAD_Client_ID(0);                            // do not derive
		setAD_Org_ID(0);                            // do not derive
		//
		setAmtAcctCr(BigDecimal.ZERO);
		setAmtAcctDr(BigDecimal.ZERO);
		setCurrencyRate(BigDecimal.ONE);
		setAmtSourceCr(BigDecimal.ZERO);
		setAmtSourceDr(BigDecimal.ZERO);
		// Log.trace(this,Log.l1_User, "FactLine " + AD_Table_ID + ":" + Record_ID);
		setAD_Table_ID(AD_Table_ID);
		setRecord_ID(Record_ID);
		setLine_ID(Line_ID);
	}   // FactLine

	/**
	 * Account
	 */
	private MAccount m_acct = null;
	/**
	 * Accounting Schema
	 */
	private AcctSchema acctSchema = null;
	/**
	 * Document Header
	 */
	private Doc<?> m_doc = null;
	/**
	 * Document Line
	 */
	private DocLine<?> m_docLine = null;
	private CurrencyConversionContext currencyConversionCtx = null;
=======
import de.metas.acct.api.FactAcctId;
import de.metas.acct.api.PostingType;
import de.metas.acct.api.impl.AcctSegmentType;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.doc.AcctDocRequiredServicesFacade;
import de.metas.acct.doc.PostingException;
import de.metas.acct.factacct_userchanges.FactAcctChanges;
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
import java.time.Instant;
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

	@Getter private ProductId M_Product_ID;
	@Getter private final Quantity qty;
	@Getter private int M_Locator_ID;

	@Getter @NonNull private final PostingType postingType;
	@Getter private final AcctSchema acctSchema;
	// private final MAccount m_acct;
	@Getter @NonNull private ElementValueId accountId;
	@Getter private final int C_SubAcct_ID;
	@Nullable @Getter private final AccountConceptualName accountConceptualName;

	@Getter @Setter(AccessLevel.PACKAGE) private CostElementId costElementId;

	@Getter @Setter private String description;

	@Getter private OrderId C_OrderSO_ID;
	@Getter @Setter(AccessLevel.PACKAGE) private LocationId C_LocFrom_ID;
	@Getter @Setter(AccessLevel.PACKAGE) private LocationId C_LocTo_ID;
	@Getter private OrgId AD_OrgTrx_ID;
	@Getter private BPartnerId C_BPartner_ID;
	@Getter private BPartnerLocationId C_BPartner_Location_ID;
	@Getter private BPartnerId C_BPartner2_ID;
	@Getter private final int GL_Budget_ID;
	@Getter private final GLCategoryId GL_Category_ID;
	@Getter private SalesRegionId C_SalesRegion_ID;
	@Getter private ProjectId C_Project_ID;
	@Getter private ActivityId C_Activity_ID;
	@Getter private int C_Campaign_ID;
	@Getter private int User1_ID;
	@Getter private int User2_ID;
	@Getter @Setter(AccessLevel.PRIVATE) private int userElement1_ID;
	@Getter @Setter(AccessLevel.PRIVATE) private int userElement2_ID;
	@Getter @Setter(AccessLevel.PRIVATE) private String userElementString1;
	@Getter @Setter(AccessLevel.PRIVATE) private String userElementString2;
	@Getter @Setter(AccessLevel.PRIVATE) private String userElementString3;
	@Getter @Setter(AccessLevel.PRIVATE) private String userElementString4;
	@Getter @Setter(AccessLevel.PRIVATE) private String userElementString5;
	@Getter @Setter(AccessLevel.PRIVATE) private String userElementString6;
	@Getter @Setter(AccessLevel.PRIVATE) private String userElementString7;
	@Getter @Setter(AccessLevel.PRIVATE) private Instant userElementDate1;
	@Getter @Setter(AccessLevel.PRIVATE) private Instant userElementDate2;
	@Getter @Setter(AccessLevel.PRIVATE) private BigDecimal userElementNumber1;
	@Getter @Setter(AccessLevel.PRIVATE) private BigDecimal userElementNumber2;

	@Getter @Nullable private FAOpenItemTrxInfo openItemTrxInfo;

	private CurrencyConversionContext currencyConversionCtx = null;

	@Nullable @Getter private FactAcctChanges appliedUserChanges = null;

	@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull" })
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
			@Nullable final ElementValueId accountId,
			@Nullable final MAccount account,
			@Nullable final AccountConceptualName accountConceptualName,
			@Nullable final Optional<ProductId> productId,
			@Nullable final Quantity qty,
			@Nullable final ClientId clientId,
			@Nullable final OrgId orgId,
			@Nullable final OrgId orgTrxId,
			@Nullable final Integer M_Locator_ID,
			@Nullable final ProjectId projectId,
			@Nullable final Optional<ActivityId> activityId,
			@Nullable final Integer campaignId,
			@Nullable final Optional<OrderId> salesOrderId,
			@Nullable final Optional<String> userElementString1,
			@Nullable final Optional<String> description,
			@Nullable final String additionalDescription
	)
	{
		this.services = services;
		this.m_doc = doc;
		this.m_docLine = docLine;
		this.docRecordRef = docRecordRef;
		this.Line_ID = Line_ID;
		this.SubLine_ID = SubLine_ID != null ? SubLine_ID : 0;

		this.postingType = postingType;
		this.acctSchema = acctSchema;
		this.accountId = computeAccountId(accountId, account);
		this.C_SubAcct_ID = account != null ? account.getC_SubAcct_ID() : 0;
		this.accountConceptualName = accountConceptualName;

		this.M_Locator_ID = M_Locator_ID != null ? M_Locator_ID : 0;
		this.C_SalesRegion_ID = computeSalesRegionId(doc, docLine, account);

		this.AD_Client_ID = computeClientId(clientId, doc);
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
		this.description = computeDescription(description, doc, docLine, additionalDescription);

		// Journal Info
		this.GL_Budget_ID = doc.getGL_Budget_ID();
		this.GL_Category_ID = doc.getGL_Category_ID();

		// Product
		this.M_Product_ID = computeProductId(productId, doc, docLine, account);

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

		this.C_LocFrom_ID = CoalesceUtil.coalesceSuppliers(
				() -> m_docLine != null ? m_docLine.getLocationFromId() : null,
				doc::getLocationFromId,
				() -> account != null ? account.getLocFromId() : null
		);
		this.C_LocTo_ID = CoalesceUtil.coalesceSuppliers(
				() -> m_docLine != null ? m_docLine.getLocationToId() : null,
				doc::getLocationToId,
				() -> account != null ? account.getLocToId() : null
		);

		// BPartner
		if (m_docLine != null && (m_docLine.getBPartnerId() != null || m_docLine.getBPartnerLocationId() != null))
		{
			setBPartnerIdAndLocation(m_docLine.getBPartnerId(), m_docLine.getBPartnerLocationId());
		}
		else
		{
			setBPartnerIdAndLocation(doc.getBPartnerId(), doc.getBPartnerLocationId());
		}
		if (getC_BPartner_ID() == null && account != null)
		{
			setBPartnerIdAndLocation(BPartnerId.ofRepoIdOrNull(account.getC_BPartner_ID()), null);
		}

		this.AD_OrgTrx_ID = CoalesceUtil.coalesceSuppliers(
				() -> orgTrxId,
				() -> m_docLine != null ? m_docLine.getOrgTrxId() : null,
				doc::getOrgTrxId,
				() -> account != null ? account.getOrgTrxId() : null
		);

		// Project
		this.C_Project_ID = CoalesceUtil.coalesceSuppliers(
				() -> projectId,
				() -> m_docLine != null ? m_docLine.getC_Project_ID() : null,
				m_doc::getC_Project_ID,
				() -> account != null ? ProjectId.ofRepoIdOrNull(account.getC_Project_ID()) : null
		);

		// Campaign
		this.C_Campaign_ID = CoalesceUtil.firstGreaterThanZeroSupplier(
				() -> campaignId != null ? campaignId : 0,
				() -> docLine != null ? docLine.getC_Campaign_ID() : 0,
				doc::getC_Campaign_ID,
				() -> account != null ? account.getC_Campaign_ID() : 0
		);

		// Activity
		this.C_Activity_ID = computeActivityId(activityId, m_doc, m_docLine, account);

		// Order
		this.C_OrderSO_ID = computeSalesOrderId(salesOrderId, m_doc, m_docLine, account);

		// C_BPartner_ID2
		this.C_BPartner2_ID = CoalesceUtil.coalesceSuppliers(
				() -> (docLine != null) ? docLine.getBPartnerId2() : null,
				doc::getBPartnerId2
		);

		// User List 1
		this.User1_ID = CoalesceUtil.firstGreaterThanZeroSupplier(
				() -> (docLine != null) ? docLine.getUser1_ID() : null,
				doc::getUser1_ID,
				() -> account != null ? account.getUser1_ID() : 0,
				() -> 0
		);
		this.User2_ID = CoalesceUtil.firstGreaterThanZeroSupplier(
				() -> (docLine != null) ? docLine.getUser2_ID() : null,
				doc::getUser2_ID,
				() -> account != null ? account.getUser2_ID() : 0,
				() -> 0
		);

		this.userElement1_ID = computeAcctSchemaElementAsId(AcctSchemaElementType.UserElement1, acctSchema, m_doc, m_docLine).orElse(0);
		this.userElement2_ID = computeAcctSchemaElementAsId(AcctSchemaElementType.UserElement2, acctSchema, m_doc, m_docLine).orElse(0);

		this.userElementString1 = computeAcctSchemaElementAsString(userElementString1, AcctSchemaElementType.UserElementString1, this.acctSchema, m_doc, m_docLine).orElse(null);
		this.userElementString2 = computeAcctSchemaElementAsString(null, AcctSchemaElementType.UserElementString2, this.acctSchema, m_doc, m_docLine).orElse(null);
		this.userElementString3 = computeAcctSchemaElementAsString(null, AcctSchemaElementType.UserElementString3, this.acctSchema, m_doc, m_docLine).orElse(null);
		this.userElementString4 = computeAcctSchemaElementAsString(null, AcctSchemaElementType.UserElementString4, this.acctSchema, m_doc, m_docLine).orElse(null);
		this.userElementString5 = computeAcctSchemaElementAsString(null, AcctSchemaElementType.UserElementString5, this.acctSchema, m_doc, m_docLine).orElse(null);
		this.userElementString6 = computeAcctSchemaElementAsString(null, AcctSchemaElementType.UserElementString6, this.acctSchema, m_doc, m_docLine).orElse(null);
		this.userElementString7 = computeAcctSchemaElementAsString(null, AcctSchemaElementType.UserElementString7, this.acctSchema, m_doc, m_docLine).orElse(null);

	}   // FactLine

	@Nullable
	@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull" })
	private static ActivityId computeActivityId(
			final @Nullable Optional<ActivityId> activityId,
			final @NonNull Doc<?> doc,
			final @Nullable DocLine<?> docLine,
			final @Nullable MAccount account)
	{
		if (activityId != null)
		{
			return activityId.orElse(null);
		}

		return CoalesceUtil.coalesceSuppliers(
				() -> docLine != null ? docLine.getActivityId() : null,
				doc::getActivityId,
				() -> account != null ? ActivityId.ofRepoIdOrNull(account.getC_Activity_ID()) : null
		);
	}

	@Nullable
	@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull" })
	private static OrderId computeSalesOrderId(
			@Nullable Optional<OrderId> salesOrderId,
			@NonNull final Doc<?> doc,
			@Nullable final DocLine<?> docLine,
			@Nullable final MAccount account)
	{
		if (salesOrderId != null)
		{
			return salesOrderId.orElse(null);
		}

		return CoalesceUtil.coalesceSuppliers(
				() -> docLine != null ? docLine.getSalesOrderId() : null,
				doc::getSalesOrderId,
				() -> account != null ? OrderId.ofRepoIdOrNull(account.getC_OrderSO_ID()) : null
		);
	}

	private static ElementValueId computeAccountId(@Nullable ElementValueId accountId, @Nullable MAccount account)
	{
		if (accountId != null)
		{
			if (account != null && accountId.getRepoId() != account.getAccount_ID())
			{
				throw new AdempiereException("AccountId and Account shall match: " + accountId + ", " + account);
			}
			return accountId;
		}
		else if (account != null)
		{
			return ElementValueId.ofRepoId(account.getAccount_ID());
		}
		else
		{
			throw new AdempiereException("At least one of AccountId and Account shall be provided");
		}
	}

	private static ClientId computeClientId(@Nullable ClientId clientId, @NonNull Doc<?> doc)
	{
		if (clientId != null && clientId.isRegular())
		{
			return clientId;
		}

		final ClientId docClientId = doc.getClientId();
		if (!docClientId.isRegular())
		{
			throw new AdempiereException("Document shall have a regular AD_Client_ID"); // shall not happen
		}
		return docClientId;
	}

	private static OrgId computeOrgIdEffective(
			@Nullable final OrgId orgId,
			final Integer locatorRepoId,
			@NonNull final Doc<?> doc,
			@Nullable final DocLine<?> docLine,
			@Nullable final MAccount account)
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
		final boolean isGLJournal = DocBaseType.equals(DocBaseType.GLJournal, doc.getDocBaseType());
		{
			final OrgId computedOrgId = isGLJournal && account != null
					? OrgId.ofRepoIdOrAny(account.getAD_Org_ID())
					: doc.getOrgId();
			if (computedOrgId.isRegular())
			{
				return computedOrgId;
			}
		}
		// Prio 4 - get from account - if not GL
		{
			final OrgId computedOrgId = !isGLJournal && account != null
					? OrgId.ofRepoIdOrAny(account.getAD_Org_ID())
					: doc.getOrgId();
			if (computedOrgId.isRegular())
			{
				return computedOrgId;
			}
		}

		return OrgId.ANY; // shall not happen
	}

	@NonNull
	@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull" })
	private static String computeDescription(
			@Nullable Optional<String> description,
			@NonNull final Doc<?> doc,
			@Nullable final DocLine<?> docLine,
			@Nullable final String additionalDescription)
	{
		final StringBuilder result = new StringBuilder(doc.getDocumentNo());
		if (description != null)
		{
			result.append(description.orElse(""));
		}
		else
		{
			if (docLine != null)
			{
				result.append(" #").append(docLine.getLine());
				if (docLine.getDescription() != null)
				{
					result.append(" (").append(docLine.getDescription()).append(")");
				}
				else if (doc.getDescription() != null && !doc.getDescription().isEmpty())
				{
					result.append(" (").append(doc.getDescription()).append(")");
				}
			}
			else if (doc.getDescription() != null && !doc.getDescription().isEmpty())
			{
				result.append(" (").append(doc.getDescription()).append(")");
			}
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
			@Nullable final MAccount account)
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> docLine != null ? docLine.getC_SalesRegion_ID().orElse(null) : null,
				() -> doc.getC_SalesRegion_ID().orElse(null),
				() -> account != null ? account.getSalesRegionId() : null
		);
	}

	@Nullable
	@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull" })
	private static ProductId computeProductId(
			@Nullable final Optional<ProductId> productId,
			@NonNull final Doc<?> doc,
			@Nullable final DocLine<?> docLine,
			@Nullable final MAccount account)
	{
		if (productId != null)
		{
			return productId.orElse(null);
		}

		return CoalesceUtil.coalesceSuppliers(
				() -> docLine != null ? docLine.getProductId() : null,
				doc::getProductId,
				() -> account != null ? ProductId.ofRepoIdOrNull(account.getM_Product_ID()) : null
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Create Reversal (negate DR/CR) of the line
	 *
	 * @param description new description
	 * @return reversal line
	 */
	public FactLine reverse(final String description)
	{
<<<<<<< HEAD
		final FactLine reversal = new FactLine(getAD_Table_ID(), getRecord_ID(), getLine_ID());
		reversal.setClientOrg(this);    // needs to be set explicitly
		reversal.setDocumentInfo(m_doc, m_docLine);
		reversal.setAccount(acctSchema, m_acct);
		reversal.setPostingType(getPostingType());
		//
		reversal.setAmtSource(getCurrencyId(), getAmtSourceDr().negate(), getAmtSourceCr().negate());
		reversal.setQty(getQty().negate());
=======
		final FactLine reversal = FactLine.builder()
				.services(services)
				.doc(m_doc)
				.docLine(m_docLine)
				.docRecordRef(this.docRecordRef)
				.Line_ID(this.Line_ID)
				.postingType(this.postingType)
				.acctSchema(acctSchema)
				.accountId(accountId)
				.accountConceptualName(accountConceptualName)
				.qty(this.qty.negate())
				.clientId(AD_Client_ID)
				.orgId(orgId)
				.build();
		//
		reversal.setAmtSource(this.currencyId, this.amtSourceDr.negate(), this.amtSourceCr.negate());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		final FactLine accrual = new FactLine(getAD_Table_ID(), getRecord_ID(), getLine_ID());
		accrual.setClientOrg(this);    // needs to be set explicitly
		accrual.setDocumentInfo(m_doc, m_docLine);
		accrual.setAccount(acctSchema, m_acct);
		accrual.setPostingType(getPostingType());
=======
		final FactLine accrual = FactLine.builder()
				.services(services)
				.doc(m_doc)
				.docLine(m_docLine)
				.docRecordRef(docRecordRef)
				.Line_ID(this.Line_ID)
				.postingType(this.postingType)
				.acctSchema(acctSchema)
				.accountId(accountId)
				.accountConceptualName(accountConceptualName)
				.clientId(AD_Client_ID)
				.orgId(orgId)
				.build();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		//
		accrual.setAmtSource(getCurrencyId(), getAmtSourceCr(), getAmtSourceDr());
		accrual.convert();
		accrual.setDescription(description);
<<<<<<< HEAD
		return accrual;
	}    // reverse

	public void setAccount(@NonNull final AcctSchema acctSchema, @NonNull final AccountId accountId)
	{
		final MAccount account = Services.get(IAccountDAO.class).getById(getCtx(), accountId);
		setAccount(acctSchema, account);
	}

	public void setAccount(@NonNull final AcctSchema acctSchema, @NonNull final MAccount acct)
	{
		this.acctSchema = acctSchema;
		super.setC_AcctSchema_ID(acctSchema.getId().getRepoId());
		//
		m_acct = acct;
		if (getAD_Client_ID() <= 0)
		{
			setAD_Client_ID(m_acct.getAD_Client_ID());
		}
		setAccount_ID(m_acct.getAccount_ID());
		setC_SubAcct_ID(m_acct.getC_SubAcct_ID());

		// User Defined References
		final AcctSchemaElement ud1 = acctSchema.getSchemaElementByType(AcctSchemaElementType.UserElement1);
		if (ud1 != null)
		{
			final String ColumnName1 = ud1.getDisplayColumnName();
			if (ColumnName1 != null)
			{
				int ID1 = 0;
				if (m_docLine != null)
				{
					ID1 = m_docLine.getValue(ColumnName1);
				}
				if (ID1 == 0)
				{
					if (m_doc == null)
					{
						throw new IllegalArgumentException("Document not set yet");
					}
					ID1 = m_doc.getValueAsIntOrZero(ColumnName1);
				}
				if (ID1 != 0)
				{
					setUserElement1_ID(ID1);
				}
			}
		}
		final AcctSchemaElement ud2 = acctSchema.getSchemaElementByType(AcctSchemaElementType.UserElement2);
		if (ud2 != null)
		{
			final String ColumnName2 = ud2.getDisplayColumnName();
			if (ColumnName2 != null)
			{
				int ID2 = 0;
				if (m_docLine != null)
				{
					ID2 = m_docLine.getValue(ColumnName2);
				}
				if (ID2 == 0)
				{
					if (m_doc == null)
					{
						throw new IllegalArgumentException("Document not set yet");
					}
					ID2 = m_doc.getValueAsIntOrZero(ColumnName2);
				}
				if (ID2 != 0)
				{
					setUserElement2_ID(ID2);
				}
			}
		}

		updateUserElementStrings();

	}   // setAccount

	private void updateUserElementStrings()
	{
		updateUserElementString(AcctSchemaElementType.UserElementString1);
		updateUserElementString(AcctSchemaElementType.UserElementString2);
		updateUserElementString(AcctSchemaElementType.UserElementString3);
		updateUserElementString(AcctSchemaElementType.UserElementString4);
		updateUserElementString(AcctSchemaElementType.UserElementString5);
		updateUserElementString(AcctSchemaElementType.UserElementString6);
		updateUserElementString(AcctSchemaElementType.UserElementString7);
	}

	private void updateUserElementString(final AcctSchemaElementType stringElementType)
	{
		final AcctSchemaElement userElmentStringElement = acctSchema.getSchemaElementByType(stringElementType);
		if (userElmentStringElement != null)
		{
			final String userElementStringColumnname = userElmentStringElement.getDisplayColumnName();
			if (userElementStringColumnname != null)
			{
				String userElementString = null;

				if (m_docLine != null)
				{
					userElementString = m_docLine.getValueAsString(userElementStringColumnname);

				}
				if (userElementString == null)
				{
					if (m_doc == null)
					{
						throw new IllegalArgumentException("Document not set yet");
					}
					userElementString = m_doc.getValueAsString(userElementStringColumnname);
				}
				if (userElementString != null)
				{
					set_Value(userElementStringColumnname, userElementString);
				}
			}
		}
	}

	AcctSchema getAcctSchema()
	{
		return acctSchema;
	}

	CurrencyId getAcctCurrencyId() {return getAcctSchema().getCurrencyId();}
=======

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

	@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull" })
	private static Optional<String> computeAcctSchemaElementAsString(
			@Nullable Optional<String> providedValue,
			@NonNull final AcctSchemaElementType acctSchemaElementType,
			@NonNull final AcctSchema acctSchema,
			@NonNull final Doc<?> doc,
			@Nullable final DocLine<?> docLine)
	{
		if (providedValue != null)
		{
			return providedValue;
		}

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

	private static Optional<Instant> computeAcctSchemaElementAsDate(
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
		LocalDateAndOrgId value = null;
		if (docLine != null)
		{
			value = docLine.getValueAsLocalDateOrNull(columnName);
		}
		if (value == null)
		{
			value = doc.getValueAsLocalDateOrNull(columnName);
		}

		return Optional.ofNullable(value)
				.map(date -> date.toInstant(doc.getServices()::getTimeZone));
	}


	public CurrencyId getAcctCurrencyId()
	{
		return getAcctSchema().getCurrencyId();
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	private void assertAcctCurrency(@NonNull final Money amt)
	{
		amt.assertCurrencyId(getAcctCurrencyId());
	}

<<<<<<< HEAD
	/**
	 * Always throw {@link UnsupportedOperationException}. Please use {@link #setAccount(AcctSchema, MAccount)}.
	 */
	@Override
	@Deprecated
	public void setC_AcctSchema_ID(final int C_AcctSchema_ID)
	{
		throw new UnsupportedOperationException("Please use setAccount()");
	}

	public boolean isZeroAmtSource()
	{
		return getAmtSourceDr().signum() == 0 && getAmtSourceCr().signum() == 0;
	}
=======
	public boolean isZeroAmtSource() {return getAmtSourceDr().signum() == 0 && getAmtSourceCr().signum() == 0;}

	public boolean isZeroAmtSourceAndQty() {return isZeroAmtSource() && isZeroQty();}

	public boolean isZeroQty() {return qty == null || qty.isZero();}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	public void setAmtSource(@Nullable Money amtSourceDr, @Nullable Money amtSourceCr)
	{
		final CurrencyId currencyId = Money.getCommonCurrencyIdOfAll(amtSourceDr, amtSourceCr);
		setAmtSource(currencyId,
				amtSourceDr != null ? amtSourceDr.toBigDecimal() : BigDecimal.ZERO,
				amtSourceCr != null ? amtSourceCr.toBigDecimal() : BigDecimal.ZERO);
	}

<<<<<<< HEAD
	public void setAmtSource(final CurrencyId currencyId, @Nullable BigDecimal AmtSourceDr, @Nullable BigDecimal AmtSourceCr)
=======
	private void setAmtSource(final CurrencyId currencyId, @Nullable BigDecimal AmtSourceDr, @Nullable BigDecimal AmtSourceCr)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
				? Services.get(ICurrencyDAO.class).getStdPrecision(currencyId)
=======
				? services.getCurrencyStandardPrecision(currencyId)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				: ICurrencyDAO.DEFAULT_PRECISION;
		setAmtSourceDr(roundAmountToPrecision("AmtSourceDr", AmtSourceDr, precision));
		setAmtSourceCr(roundAmountToPrecision("AmtSourceCr", AmtSourceCr, precision));
	}   // setAmtSource

	public void setAmtAcct(@NonNull final Balance balance)
	{
		setAmtAcct(balance.getDebit(), balance.getCredit());
	}

	/**
	 * Set Accounted Amounts (alternative: call convert)
	 */
	public void setAmtAcct(@Nullable final Money amtAcctDr, @Nullable final Money amtAcctCr)
	{
		if (amtAcctDr != null)
		{
			assertAcctCurrency(amtAcctDr);
		}
		if (amtAcctCr != null)
		{
			assertAcctCurrency(amtAcctCr);
		}

		setAmtAcct(
				amtAcctDr != null ? amtAcctDr.toBigDecimal() : null,
				amtAcctCr != null ? amtAcctCr.toBigDecimal() : null);
	}

	/**
	 * Set Accounted Amounts (alternative: call convert)
	 */
<<<<<<< HEAD
	public void setAmtAcct(BigDecimal AmtAcctDr, BigDecimal AmtAcctCr)
=======
	void setAmtAcct(BigDecimal AmtAcctDr, BigDecimal AmtAcctCr)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
	/**
	 * Negate the DR and CR source and accounted amounts.
	 */
	public void negateDrAndCrAmounts()
	{
		final BigDecimal amtSourceDr = getAmtSourceDr();
		final BigDecimal amtSourceCr = getAmtSourceCr();
		final BigDecimal amtAcctDr = getAmtAcctDr();
		final BigDecimal amtAcctCr = getAmtAcctCr();

		setAmtSourceDr(amtSourceDr.negate());
		setAmtSourceCr(amtSourceCr.negate());
		setAmtAcctDr(amtAcctDr.negate());
		setAmtAcctCr(amtAcctCr.negate());
	}

	/**
	 * Set Accounted Amounts rounded by currency
	 *
	 * @param currencyId currency
	 * @param AmtAcctDr  acct amount dr
	 * @param AmtAcctCr  acct amount cr
	 */
	public void setAmtAcct(final CurrencyId currencyId, final BigDecimal AmtAcctDr, final BigDecimal AmtAcctCr)
	{
		final CurrencyPrecision precision = currencyId != null
				? Services.get(ICurrencyDAO.class).getStdPrecision(currencyId)
				: ICurrencyDAO.DEFAULT_PRECISION;
		setAmtAcctDr(roundAmountToPrecision("AmtAcctDr", AmtAcctDr, precision));
		setAmtAcctCr(roundAmountToPrecision("AmtAcctCr", AmtAcctCr, precision));
	}   // setAmtAcct

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
						.setDocument(getDoc())
						.setDocLine(getDocLine())
=======
						.setDocument(this.m_doc)
						.setDocLine(this.m_docLine)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
						.setFactLine(this)
						.setParameter("Rounding error", amtRoundingError)
						.setParameter("Rounding error (tolerated)", errorMarginTolerated);
				log.debug(ex.getLocalizedMessage(), ex);
			}
		}

		return amtRounded;
	}

<<<<<<< HEAD
	/**
	 * Set Document Info
	 *
	 * @param doc     document
	 * @param docLine doc line
	 */
	protected void setDocumentInfo(final Doc<?> doc, final DocLine<?> docLine)
	{
		m_doc = doc;
		m_docLine = docLine;

		// reset
		setAD_Org_ID(OrgId.ANY.getRepoId());
		setC_SalesRegion_ID(0);

		// Client
		if (getAD_Client_ID() <= 0)
		{
			setAD_Client_ID(m_doc.getClientId().getRepoId());
		}

		// Date Trx
		setDateTrx(m_doc.getDateDoc());
		if (m_docLine != null && m_docLine.getDateDoc() != null)
		{
			setDateTrx(m_docLine.getDateDoc());
		}

		// Date Acct
		setDateAcct(m_doc.getDateAcct());
		if (m_docLine != null && m_docLine.getDateAcct() != null)
		{
			setDateAcct(m_docLine.getDateAcct());
		}

		// Period
		if (m_docLine != null && m_docLine.getC_Period_ID() > 0)
		{
			setC_Period_ID(m_docLine.getC_Period_ID());
		}
		else
		{
			setC_Period_ID(m_doc.getC_Period_ID());
		}

		// Tax
		if (m_docLine != null)
		{
			setC_Tax_ID(TaxId.toRepoId(m_docLine.getTaxId()));
		}

		// Document infos
		setDocumentNo(m_doc.getDocumentNo());
		setC_DocType_ID(m_doc.getC_DocType_ID());
		setDocBaseType(m_doc.getDocumentType());
		setDocStatus(m_doc.getDocStatus());

		// Description
		final StringBuilder description = new StringBuilder(m_doc.getDocumentNo());
		if (m_docLine != null)
		{
			description.append(" #").append(m_docLine.getLine());
			if (m_docLine.getDescription() != null)
			{
				description.append(" (").append(m_docLine.getDescription()).append(")");
			}
			else if (m_doc.getDescription() != null && m_doc.getDescription().length() > 0)
			{
				description.append(" (").append(m_doc.getDescription()).append(")");
			}
		}
		else if (m_doc.getDescription() != null && m_doc.getDescription().length() > 0)
		{
			description.append(" (").append(m_doc.getDescription()).append(")");
		}
		setDescription(description.toString());

		// Journal Info
		setGL_Budget_ID(m_doc.getGL_Budget_ID());
		setGL_Category_ID(m_doc.getGL_Category_ID());

		// Product
		if (m_docLine != null)
		{
			setM_Product_ID(m_docLine.getProductId());
		}
		if (getM_Product_ID() == 0)
		{
			setM_Product_ID(m_doc.getProductId());
		}

		// UOM
		if (m_docLine != null)
		{
			setC_UOM_ID(m_docLine.getC_UOM_ID());
		}

		// Qty
		if (get_Value("Qty") == null)    // not previously set
		{
			if (m_docLine != null && m_docLine.getQty() != null)
			{
				setQty(m_docLine.getQty());
			}
		}

		// Loc From (maybe set earlier)
		if (getC_LocFrom_ID() <= 0 && m_docLine != null)
		{
			setC_LocFrom_ID(m_docLine.getLocationFromId());
		}
		if (getC_LocFrom_ID() <= 0)
		{
			setC_LocFrom_ID(m_doc.getLocationFromId());
		}

		// Loc To (maybe set earlier)
		if (getC_LocTo_ID() <= 0 && m_docLine != null)
		{
			setC_LocTo_ID(m_docLine.getLocationToId());
		}
		if (getC_LocTo_ID() <= 0)
		{
			setC_LocTo_ID(m_doc.getLocationToId());
		}

		// BPartner
		if (m_docLine != null)
		{
			setC_BPartner_ID(m_docLine.getBPartnerId());
		}
		if (getC_BPartner_ID() <= 0)
		{
			setC_BPartner_ID(m_doc.getBPartnerId());
		}

		// Sales Region from BPLocation/Sales Rep
		// NOTE: it will be set on save or on first call of getC_SalesRegion_ID() method.

		// Trx Org
		if (m_docLine != null)
		{
			setAD_OrgTrx_ID(m_docLine.getOrgTrxId());
		}
		if (getAD_OrgTrx_ID() <= 0)
		{
			setAD_OrgTrx_ID(m_doc.getOrgTrxId());
		}

		// Project
		if (m_docLine != null)
		{
			setC_Project_ID(m_docLine.getC_Project_ID());
		}
		if (getC_Project_ID() <= 0)
		{
			setC_Project_ID(m_doc.getC_Project_ID());
		}

		// Campaign
		if (m_docLine != null)
		{
			setC_Campaign_ID(m_docLine.getC_Campaign_ID());
		}
		if (getC_Campaign_ID() <= 0)
		{
			setC_Campaign_ID(m_doc.getC_Campaign_ID());
		}

		// Activity
		if (m_docLine != null)
		{
			setC_Activity_ID(m_docLine.getActivityId());
		}
		if (getC_Activity_ID() <= 0)
		{
			setC_Activity_ID(m_doc.getActivityId());
		}

		// User List 1
		if (m_docLine != null)
		{
			setUser1_ID(m_docLine.getUser1_ID());
		}
		if (getUser1_ID() <= 0)
		{
			setUser1_ID(m_doc.getUser1_ID());
		}

		// User List 2
		if (m_docLine != null)
		{
			setUser2_ID(m_docLine.getUser2_ID());
		}
		if (getUser2_ID() <= 0)
		{
			setUser2_ID(m_doc.getUser2_ID());
			// References in setAccount
		}
	}   // setDocumentInfo

	private void setC_LocTo_ID(final LocationId locationToId)
	{
		setC_LocTo_ID(LocationId.toRepoId(locationToId));
	}

	private void setC_LocFrom_ID(final LocationId locationFromId)
	{
		super.setC_LocFrom_ID(LocationId.toRepoId(locationFromId));
	}

	private void setDateTrx(final LocalDate dateTrx)
	{
		super.setDateTrx(TimeUtil.asTimestamp(dateTrx));
	}

	private void setDateAcct(final LocalDate dateAcct)
	{
		super.setDateAcct(TimeUtil.asTimestamp(dateAcct));
	}

	public Doc<?> getDoc()
	{
		return m_doc;
	}

	/**
	 * Get Document Line
	 *
	 * @return doc line
	 */
=======
	public Money getAmtSourceDrAsMoney() {return Money.of(getAmtSourceDr(), getCurrencyId());}

	public Money getAmtSourceCrAsMoney() {return Money.of(getAmtSourceCr(), getCurrencyId());}

	public Money getAmtAcctDrAsMoney() {return Money.of(getAmtAcctDr(), getAcctCurrencyId());}

	public Money getAmtAcctCrAsMoney() {return Money.of(getAmtAcctCr(), getAcctCurrencyId());}

	public Doc<?> getDoc() {return m_doc;}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public DocLine<?> getDocLine()
	{
		return m_docLine;
	}    // getDocLine

<<<<<<< HEAD
	/**
	 * Set Description
	 *
	 * @param description description
	 */
	public void addDescription(final String description)
	{
		final String original = getDescription();
		if (original == null || original.trim().length() == 0)
		{
			super.setDescription(description);
		}
		else
		{
			super.setDescription(original + " - " + description);
		}
	}    // addDescription

	/**
	 * Set Warehouse Locator.
	 * - will overwrite Organization -
	 *
	 * @param M_Locator_ID locator
	 */
	@Override
	public void setM_Locator_ID(final int M_Locator_ID)
	{
		super.setM_Locator_ID(M_Locator_ID);
		setAD_Org_ID(0);    // reset
	}   // setM_Locator_ID

	/**************************************************************************
	 * Set Location
	 *
	 * @param C_Location_ID location
	 * @param isFrom from
	 */
	public void setLocation(final int C_Location_ID, final boolean isFrom)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (isFrom)
		{
			setC_LocFrom_ID(C_Location_ID);
		}
		else
		{
			setC_LocTo_ID(C_Location_ID);
		}
<<<<<<< HEAD
	}   // setLocator

	/**
	 * Set Location from Locator
	 *
	 * @param M_Locator_ID locator
	 * @param isFrom       from
	 */
	public void setLocationFromLocator(final int M_Locator_ID, final boolean isFrom)
	{
		if (M_Locator_ID == 0)
		{
			return;
		}
		int C_Location_ID = 0;
		final String sql = "SELECT w.C_Location_ID FROM M_Warehouse w, M_Locator l "
				+ "WHERE w.M_Warehouse_ID=l.M_Warehouse_ID AND l.M_Locator_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, M_Locator_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				C_Location_ID = rs.getInt(1);
			}
		}
		catch (final SQLException e)
		{
			log.error(sql, e);
			return;
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		if (C_Location_ID != 0)
		{
			setLocation(C_Location_ID, isFrom);
		}
	}   // setLocationFromLocator

	/**
	 * Set Location from Busoness Partner Location
	 *
	 * @param C_BPartner_Location_ID bp location
	 * @param isFrom                 from
	 */
	public void setLocationFromBPartner(final int C_BPartner_Location_ID, final boolean isFrom)
	{
		if (C_BPartner_Location_ID == 0)
		{
			return;
		}
		int C_Location_ID = 0;
		final String sql = "SELECT C_Location_ID FROM C_BPartner_Location WHERE C_BPartner_Location_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, C_BPartner_Location_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				C_Location_ID = rs.getInt(1);
			}
		}
		catch (final SQLException e)
		{
			log.error(sql, e);
			return;
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		if (C_Location_ID != 0)
		{
			setLocation(C_Location_ID, isFrom);
		}
	}   // setLocationFromBPartner

	/**
	 * Set Location from Organization
	 *
	 * @param AD_Org_ID org
	 * @param isFrom    from
	 */
	public void setLocationFromOrg(final int AD_Org_ID, final boolean isFrom)
	{
		if (AD_Org_ID == 0)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return;
		}

<<<<<<< HEAD
		// 03711 using OrgBP_Location_ID to the the C_Location. Note that we have removed AD_OrgInfo.C_Location_ID
		int OrgBP_Location_ID = 0;
		final String sql = "SELECT OrgBP_Location_ID FROM AD_OrgInfo WHERE AD_Org_ID=?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, AD_Org_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				OrgBP_Location_ID = rs.getInt(1);
			}
		}
		catch (final SQLException e)
		{
			log.error(sql, e);
			return;
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		if (OrgBP_Location_ID > 0)
		{
			// 03711 using OrgBP_Location_ID to the the C_Location
			final I_C_BPartner_Location bpLoc = InterfaceWrapperHelper.create(getCtx(), OrgBP_Location_ID, I_C_BPartner_Location.class, get_TrxName());
			setLocation(bpLoc.getC_Location_ID(), isFrom);
		}
	}   // setLocationFromOrg

	public Balance getSourceBalance()
	{
		return Balance.of(getCurrencyId(), getAmtSourceDr(), getAmtSourceCr());
=======
		getServices().getLocationId(orgId)
				.ifPresent(locationId -> setLocation(locationId, isFrom));
	}

	public Balance getSourceBalance()
	{
		return Balance.of(this.currencyId, this.amtSourceDr, this.amtSourceCr);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	/**
	 * @return true if DR source balance
	 */
	public boolean isDrSourceBalance()
	{
		return getSourceBalance().isDebit();
	}

	public Balance getAcctBalance()
	{
		return Balance.of(getAcctCurrencyId(), getAmtAcctDr(), getAmtAcctCr());
	}

	/**
<<<<<<< HEAD
	 * @return true if the given fact line is booked on same DR/CR side as this line
	 */
	public boolean isSameAmtSourceDrCrSideAs(final FactLine factLine)
	{
		final boolean thisDR = getAmtAcctDr().signum() != 0;
		final boolean otherDR = factLine != null && factLine.getAmtAcctDr().signum() != 0;
		if (thisDR != otherDR)
		{
			return false;
		}

		final boolean thisCR = getAmtAcctCr().signum() != 0;
		final boolean otherCR = factLine != null && factLine.getAmtAcctCr().signum() != 0;
		if (thisCR != otherCR)
		{
			return false;
		}

		return true;
	}

	/**
	 * @return AmtSourceDr/AmtAcctDr or AmtSourceCr/AmtAcctCr, which one is not ZERO
	 * @throws IllegalStateException if both of them are not ZERO
	 */
	public AmountSourceAndAcct getAmtSourceAndAcctDrOrCr()
=======
	 * @return AmtSourceDr/AmtAcctDr or AmtSourceCr/AmtAcctCr, which one is not ZERO
	 * @throws IllegalStateException if both of them are not ZERO
	 */
	AmountSourceAndAcct getAmtSourceAndAcctDrOrCr()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		else if (amtAcctDrSign == 0 && amtAcctCrSign == 0)
=======
		else if (amtAcctDrSign == 0 /*&& amtAcctCrSign == 0*/)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	 * Is Account on Balance Sheet
	 *
	 * @return true if account is a balance sheet account
	 */
	public boolean isBalanceSheet()
	{
		return m_acct.isBalanceSheet();
	}    // isBalanceSheet

	/**
	 * Currect Accounting Amount.
=======
	 * @return true if account is a balance sheet account
	 */
	public boolean isBalanceSheet() {return services.getElementValueById(accountId).isBalanceSheet();}

	/**
	 * Correct Accounting Amount.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <pre>
	 *  Example:    1       -1      1       -1
	 *  Old         100/0   100/0   0/100   0/100
	 *  New         99/0    101/0   0/99    0/101
	 * </pre>
<<<<<<< HEAD
	 *
	 * @param deltaAmount delta amount
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		if (getC_Currency_ID() <= 0)
		{
			setC_Currency_ID(acctCurrencyId.getRepoId());
=======
		if (getCurrencyId() == null)
		{
			setCurrencyId(acctCurrencyId);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
	private CurrencyRate getCurrencyRate(final CurrencyId currencyId, final CurrencyId acctCurrencyId)
	{
		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
		final CurrencyConversionContext conversionCtx = getCurrencyConversionCtx();
		return currencyConversionBL.getCurrencyRate(conversionCtx, currencyId, acctCurrencyId);
	}

=======
	public CurrencyRate getCurrencyRate(final CurrencyId currencyFromId, final CurrencyId currencyToId)
	{
		final CurrencyConversionContext conversionCtx = getCurrencyConversionCtx();
		return services.getCurrencyRate(conversionCtx, currencyFromId, currencyToId);
	}

	public CurrencyRate getCurrencyRateFromDocumentToAcctCurrency() {return getCurrencyRate(getCurrencyId(), getAcctCurrencyId());}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
		// Get Conversion Type from Line or Header
		CurrencyConversionTypeId conversionTypeId = null;
		OrgId orgId = OrgId.ANY;
		if (m_docLine != null)            // get from line
		{
			conversionTypeId = m_docLine.getCurrencyConversionTypeId();
			orgId = m_docLine.getOrgId();
		}
		if (conversionTypeId == null)    // get from header
		{
			Check.assumeNotNull(m_doc, "m_doc not null");
			conversionTypeId = m_doc.getCurrencyConversionTypeId();
			if (orgId == null || orgId.isAny())
			{
				orgId = m_doc.getOrgId();
			}
		}

		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
		return currencyConversionBL.createCurrencyConversionContext(
				TimeUtil.asLocalDate(getDateAcct()),
				conversionTypeId,
				m_doc.getClientId(),
				orgId);
	}

	/**
	 * Get Account
	 *
	 * @return account
	 */
	public MAccount getAccount()
	{
		return m_acct;
	}    // getAccount

	@Override
	public String toString()
	{
		return "FactLine=[" + getAD_Table_ID() + ":" + getRecord_ID()
				+ "," + m_acct
				+ ",Cur=" + getC_Currency_ID()
				+ ", DR=" + getAmtSourceDr() + "|" + getAmtAcctDr()
				+ ", CR=" + getAmtSourceCr() + "|" + getAmtAcctCr()
				+ ", Record/Line=" + getRecord_ID() + (getLine_ID() > 0 ? "/" + getLine_ID() : "")
				+ "]";
	}

	public OrgId getOrgId() {return OrgId.ofRepoIdOrAny(getAD_Org_ID());}

	/**
	 * Get AD_Org_ID (balancing segment).
	 * (if not set directly - from document line, document, account, locator)
	 * <p>
	 * Note that Locator needs to be set before - otherwise segment balancing might produce the wrong results
	 *
	 * @return AD_Org_ID
	 */
	@Override
	public int getAD_Org_ID()
	{
		if (super.getAD_Org_ID() > 0)      // set earlier
		{
			return super.getAD_Org_ID();
		}

		// Prio 1 - get from locator - if exist
		final int locatorId = getM_Locator_ID();
		if (locatorId > 0)
		{
			final OrgId locatorOrgId = Services.get(IWarehouseDAO.class).retrieveOrgIdByLocatorId(locatorId);
			if (locatorOrgId != null)
			{
				setAD_Org_ID(locatorOrgId);
			}
		}
		// Prio 2 - get from doc line - if exists (document context overwrites)
		if (m_docLine != null && super.getAD_Org_ID() <= 0)
		{
			setAD_Org_ID(m_docLine.getOrgId());
		}
		// Prio 3 - get from doc - if not GL
		if (m_doc != null && super.getAD_Org_ID() <= 0)
		{
			if (Doc.DOCTYPE_GLJournal.equals(m_doc.getDocumentType()))
			{
				setAD_Org_ID(m_acct.getAD_Org_ID()); // inter-company GL
			}
			else
			{
				setAD_Org_ID(m_doc.getOrgId());
			}
		}
		// Prio 4 - get from account - if not GL
		if (m_doc != null && super.getAD_Org_ID() == 0)
		{
			if (Doc.DOCTYPE_GLJournal.equals(m_doc.getDocumentType()))
			{
				setAD_Org_ID(m_doc.getOrgId());
			}
			else
			{
				setAD_Org_ID(m_acct.getAD_Org_ID());
			}
		}
		return super.getAD_Org_ID();
	}   // setAD_Org_ID

	/**
	 * Get/derive Sales Region
	 *
	 * @return Sales Region
	 */
	@Override
	public int getC_SalesRegion_ID()
	{
		if (super.getC_SalesRegion_ID() > 0)
		{
			return super.getC_SalesRegion_ID();
		}
		//
		if (m_docLine != null)
		{
			setC_SalesRegion_ID(m_docLine.getC_SalesRegion_ID());
		}
		if (m_doc != null)
		{
			if (super.getC_SalesRegion_ID() == 0)
			{
				setC_SalesRegion_ID(m_doc.getC_SalesRegion_ID());
			}
			if (super.getC_SalesRegion_ID() == 0 && m_doc.getBP_C_SalesRegion_ID() > 0)
			{
				setC_SalesRegion_ID(m_doc.getBP_C_SalesRegion_ID());
			}
			// derive SalesRegion if AcctSegment
			if (super.getC_SalesRegion_ID() == 0
					&& m_doc.getC_BPartner_Location_ID() != 0
					&& m_doc.getBP_C_SalesRegion_ID() == -1)    // never tried
			// && m_acctSchema.isAcctSchemaElement(MAcctSchemaElement.ELEMENTTYPE_SalesRegion))
			{
				String sql = "SELECT COALESCE(C_SalesRegion_ID,0) FROM C_BPartner_Location WHERE C_BPartner_Location_ID=?";
				setC_SalesRegion_ID(DB.getSQLValue(null, sql, m_doc.getC_BPartner_Location_ID()));
				if (super.getC_SalesRegion_ID() != 0)        // save in VO
				{
					m_doc.setBP_C_SalesRegion_ID(super.getC_SalesRegion_ID());
					log.debug("C_SalesRegion_ID=" + super.getC_SalesRegion_ID() + " (from BPL)");
				}
				else
				// From Sales Rep of Document -> Sales Region
				{
					final UserId salesRepId = m_doc.getSalesRepId();
					if (salesRepId != null)
					{
						sql = "SELECT COALESCE(MAX(C_SalesRegion_ID),0) FROM C_SalesRegion WHERE SalesRep_ID=?";
						setC_SalesRegion_ID(DB.getSQLValueEx(ITrx.TRXNAME_None, sql, salesRepId));
					}

					if (super.getC_SalesRegion_ID() != 0)        // save in VO
					{
						m_doc.setBP_C_SalesRegion_ID(super.getC_SalesRegion_ID());
						log.debug("C_SalesRegion_ID=" + super.getC_SalesRegion_ID() + " (from SR)");
					}
					else
					{
						m_doc.setBP_C_SalesRegion_ID(-2);    // don't try again
					}
				}
			}
			if (m_acct != null && super.getC_SalesRegion_ID() == 0)
			{
				setC_SalesRegion_ID(m_acct.getC_SalesRegion_ID());
			}
		}
		//
		// log.debug("C_SalesRegion_ID=" + super.getC_SalesRegion_ID()
		// + ", C_BPartner_Location_ID=" + m_docVO.C_BPartner_Location_ID
		// + ", BP_C_SalesRegion_ID=" + m_docVO.BP_C_SalesRegion_ID
		// + ", SR=" + m_acctSchema.isAcctSchemaElement(MAcctSchemaElement.ELEMENTTYPE_SalesRegion));
		return super.getC_SalesRegion_ID();
	}    // getC_SalesRegion_ID

	/**
	 * Before Save
	 *
	 * @param newRecord new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (newRecord)
		{
			log.debug(toString());
			//
			getAD_Org_ID();
			getC_SalesRegion_ID();
			// Set Default Account Info
			if (getM_Product_ID() == 0)
			{
				setM_Product_ID(m_acct.getM_Product_ID());
			}
			if (getC_LocFrom_ID() == 0)
			{
				setC_LocFrom_ID(m_acct.getC_LocFrom_ID());
			}
			if (getC_LocTo_ID() == 0)
			{
				setC_LocTo_ID(m_acct.getC_LocTo_ID());
			}
			if (getC_BPartner_ID() == 0)
			{
				setC_BPartner_ID(m_acct.getC_BPartner_ID());
			}
			if (getAD_OrgTrx_ID() == 0)
			{
				setAD_OrgTrx_ID(m_acct.getAD_OrgTrx_ID());
			}
			if (getC_Project_ID() == 0)
			{
				setC_Project_ID(m_acct.getC_Project_ID());
			}
			if (getC_Campaign_ID() == 0)
			{
				setC_Campaign_ID(m_acct.getC_Campaign_ID());
			}
			if (getC_Activity_ID() == 0)
			{
				setC_Activity_ID(m_acct.getC_Activity_ID());
			}
			if (getUser1_ID() == 0)
			{
				setUser1_ID(m_acct.getUser1_ID());
			}
			if (getUser2_ID() == 0)
			{
				setUser2_ID(m_acct.getUser2_ID());
			}

			setVATCodeIfApplies();

			//
			// Revenue Recognition for AR Invoices
			if (m_doc.getDocumentType().equals(Doc.DOCTYPE_ARInvoice)
					&& m_docLine != null
					&& m_docLine.getC_RevenueRecognition_ID() > 0)
			{
				setAccount_ID(createRevenueRecognition(
						m_docLine.getC_RevenueRecognition_ID(),
						m_docLine.get_ID(),
						toAccountDimension()));
			}
		}
		return true;
	}    // beforeSave

	private AccountDimension toAccountDimension()
	{
		return AccountDimension.builder()
				.setAcctSchemaId(getAcctSchemaId())
				.setAD_Client_ID(getAD_Client_ID())
				.setAD_Org_ID(getAD_Org_ID())
				.setC_ElementValue_ID(getAccount_ID())
				.setC_SubAcct_ID(getC_SubAcct_ID())
				.setM_Product_ID(getM_Product_ID())
				.setC_BPartner_ID(getC_BPartner_ID())
				.setAD_OrgTrx_ID(getAD_OrgTrx_ID())
				.setC_LocFrom_ID(getC_LocFrom_ID())
				.setC_LocTo_ID(getC_LocTo_ID())
				.setC_SalesRegion_ID(getC_SalesRegion_ID())
				.setC_Project_ID(getC_Project_ID())
				.setC_Campaign_ID(getC_Campaign_ID())
				.setC_Activity_ID(getC_Activity_ID())
				.setUser1_ID(getUser1_ID())
				.setUser2_ID(getUser2_ID())
				.setUserElement1_ID(getUserElement1_ID())
				.setUserElement2_ID(getUserElement2_ID())
				.setUserElementString1(getUserElementString1())
				.setUserElementString2(getUserElementString2())
				.setUserElementString3(getUserElementString3())
				.setUserElementString4(getUserElementString4())
				.setUserElementString5(getUserElementString5())
				.setUserElementString6(getUserElementString6())
				.setUserElementString7(getUserElementString7())
				.build();
	}

	/**
	 * Create Revenue recognition plan and return Unearned Revenue account to be used instead of Revenue Account. If not found, it returns the revenue account.
	 *
	 * @return Account_ID for Unearned Revenue or Revenue Account if not found
	 */
	private int createRevenueRecognition(
			final int C_RevenueRecognition_ID,
			final int C_InvoiceLine_ID,
			final AccountDimension accountDimension)
	{
		// get VC for P_Revenue (from Product)
		final MAccount revenue = MAccount.get(getCtx(), accountDimension);
		if (revenue == null || revenue.get_ID() <= 0)
		{
			log.error("Revenue_Acct not found");
			return accountDimension.getC_ElementValue_ID();
		}
		final AccountId productRevenueAcctId = AccountId.ofRepoId(revenue.getC_ValidCombination_ID());

		// get Unearned Revenue Acct from BPartner Group
		AccountId unearnedRevenueAcctId = null;
		int new_Account_ID = 0;
		final String sql = "SELECT ga.UnearnedRevenue_Acct, vc.Account_ID "
				+ "FROM C_BP_Group_Acct ga, C_BPartner p, C_ValidCombination vc "
				+ "WHERE ga.C_BP_Group_ID=p.C_BP_Group_ID"
				+ " AND ga.UnearnedRevenue_Acct=vc.C_ValidCombination_ID"
				+ " AND ga.C_AcctSchema_ID=? AND p.C_BPartner_ID=?";
		final Object[] sqlParams = new Object[] { accountDimension.getAcctSchemaId(), accountDimension.getC_BPartner_ID() };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				unearnedRevenueAcctId = AccountId.ofRepoId(rs.getInt(1));
				new_Account_ID = rs.getInt(2);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		if (new_Account_ID <= 0)
		{
			log.error("UnearnedRevenue_Acct not found");
			return accountDimension.getC_ElementValue_ID();
		}

		final I_C_RevenueRecognition_Plan plan = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_RevenueRecognition_Plan.class);
		plan.setC_RevenueRecognition_ID(C_RevenueRecognition_ID);
		plan.setC_AcctSchema_ID(accountDimension.getAcctSchemaId().getRepoId());
		plan.setC_InvoiceLine_ID(C_InvoiceLine_ID);
		plan.setUnEarnedRevenue_Acct(unearnedRevenueAcctId.getRepoId());
		plan.setP_Revenue_Acct(productRevenueAcctId.getRepoId());
		plan.setC_Currency_ID(getCurrencyId().getRepoId());
		plan.setRecognizedAmt(BigDecimal.ZERO);
		plan.setTotalAmt(getAcctBalance().toBigDecimal());
		InterfaceWrapperHelper.save(plan);

		return new_Account_ID;
	}

=======
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

	@Override
	public String toString()
	{
		final String accountString = services.getElementValueById(accountId).toShortString();
		String sb = "FactLine=[" + this.docRecordRef
				+ "," + this.accountConceptualName + "/" + accountString
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
				.setC_ElementValue_ID(ElementValueId.toRepoId(this.accountId))
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
				// .setSalesOrderId(OrderId.toRepoId(this.C_OrderSO_ID))
				.build();
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	/**************************************************************************
	 * Update Line with reversed Original Amount in Accounting Currency.
	 * Also copies original dimensions like Project, etc.
	 * Called from Doc_MatchInv
	 *
<<<<<<< HEAD
	 * @param AD_Table_ID table
	 * @param Record_ID record
	 * @param Line_ID line
	 * @param multiplier targetQty/documentQty
	 * @return true if success
	 */
	public boolean updateReverseLine(
			final int AD_Table_ID,
=======
	 * @return true if success
	 */
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean updateReverseLine(
			final String tableName,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			final int Record_ID,
			final int Line_ID,
			final BigDecimal multiplier)
	{
<<<<<<< HEAD
		final IQueryBuilder<I_Fact_Acct> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fact_Acct.class)
				.addEqualsFilter(I_Fact_Acct.COLUMN_C_AcctSchema_ID, getC_AcctSchema_ID())
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Table_ID, AD_Table_ID)
				.addEqualsFilter(I_Fact_Acct.COLUMN_Record_ID, Record_ID)
				.addEqualsFilter(I_Fact_Acct.COLUMN_Line_ID, Line_ID)
				.addEqualsFilter(I_Fact_Acct.COLUMN_Account_ID, m_acct.getAccount_ID());
		if (I_M_Movement.Table_ID == AD_Table_ID)
		{
			queryBuilder.addEqualsFilter(I_Fact_Acct.COLUMN_M_Locator_ID, getM_Locator_ID());
=======
		final AdTableId adTableId = TableIdsCache.instance.getTableId(tableName).orElseThrow(() -> new AdempiereException("No AD_Table_ID found for " + tableName));

		final IQueryBuilder<I_Fact_Acct> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fact_Acct.class)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_C_AcctSchema_ID, getAcctSchemaId())
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, Record_ID)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Line_ID, Line_ID)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Account_ID, getAccountId());
		if (I_M_Movement.Table_Name.equals(tableName))
		{
			queryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_M_Locator_ID, getM_Locator_ID());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		final I_Fact_Acct fact = queryBuilder.create().firstOnly(I_Fact_Acct.class);
		if (fact != null)
		{
			final CurrencyId currencyId = CurrencyId.ofRepoId(fact.getC_Currency_ID());
			// Accounted Amounts - reverse
			final BigDecimal dr = fact.getAmtAcctDr();
			final BigDecimal cr = fact.getAmtAcctCr();
<<<<<<< HEAD
			// setAmtAcctDr (cr.multiply(multiplier));
			// setAmtAcctCr (dr.multiply(multiplier));
			setAmtAcct(
					currencyId,
					cr.multiply(multiplier),
					dr.multiply(multiplier));
			//
			// Bayu Sistematika - Source Amounts
=======

			setAmtAcct(
					roundAmountToPrecision("AmtAcctDr", cr.multiply(multiplier), getAcctSchema().getStandardPrecision()),
					roundAmountToPrecision("AmtAcctCr", dr.multiply(multiplier), getAcctSchema().getStandardPrecision()));

			//
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			// Fixing source amounts
			final BigDecimal drSourceAmt = fact.getAmtSourceDr();
			final BigDecimal crSourceAmt = fact.getAmtSourceCr();
			setAmtSource(
					currencyId,
					crSourceAmt.multiply(multiplier),
					drSourceAmt.multiply(multiplier));
<<<<<<< HEAD
			// end Bayu Sistematika
			//
			log.debug(new StringBuilder("(Table=").append(AD_Table_ID)
					.append(",Record_ID=").append(Record_ID)
					.append(",Line=").append(Record_ID)
					.append(", Account=").append(m_acct)
					.append(",dr=").append(dr).append(",cr=").append(cr)
					.append(") - DR=").append(getAmtSourceDr()).append("|").append(getAmtAcctDr())
					.append(", CR=").append(getAmtSourceCr()).append("|").append(getAmtAcctCr())
					.toString());
			// Dimensions
			setAD_OrgTrx_ID(fact.getAD_OrgTrx_ID());
			setC_Project_ID(fact.getC_Project_ID());
			setC_Activity_ID(fact.getC_Activity_ID());
			setC_Campaign_ID(fact.getC_Campaign_ID());
			setC_SalesRegion_ID(fact.getC_SalesRegion_ID());
			setC_LocFrom_ID(fact.getC_LocFrom_ID());
			setC_LocTo_ID(fact.getC_LocTo_ID());
			setM_Product_ID(fact.getM_Product_ID());
			setM_Locator_ID(fact.getM_Locator_ID());
			setUser1_ID(fact.getUser1_ID());
			setUser2_ID(fact.getUser2_ID());
			setC_UOM_ID(fact.getC_UOM_ID());
			setC_Tax_ID(fact.getC_Tax_ID());
			// Org for cross charge
			setAD_Org_ID(fact.getAD_Org_ID());
=======

			// Dimensions
			setAD_OrgTrx_ID(OrgId.ofRepoIdOrAny(fact.getAD_OrgTrx_ID()));
			this.C_Project_ID = ProjectId.ofRepoIdOrNull(fact.getC_Project_ID());
			this.C_Activity_ID = ActivityId.ofRepoIdOrNull(fact.getC_Activity_ID());
			this.C_Campaign_ID = Math.max(fact.getC_Campaign_ID(), 0);
			this.C_SalesRegion_ID = SalesRegionId.ofRepoIdOrNull(fact.getC_SalesRegion_ID());
			setC_LocFrom_ID(LocationId.ofRepoIdOrNull(fact.getC_LocFrom_ID()));
			setC_LocTo_ID(LocationId.ofRepoIdOrNull(fact.getC_LocTo_ID()));
			this.M_Product_ID = ProductId.ofRepoIdOrNull(fact.getM_Product_ID());
			this.M_Locator_ID = fact.getM_Locator_ID();
			this.User1_ID = fact.getUser1_ID();
			this.User2_ID = fact.getUser2_ID();
			//setC_UOM_ID(fact.getC_UOM_ID());
			this.taxId = TaxId.ofRepoIdOrNull(fact.getC_Tax_ID());
			this.vatCode = fact.getVATCode();
			this.orgId = OrgId.ofRepoIdOrAny(fact.getAD_Org_ID());
			this.C_OrderSO_ID = OrderId.ofRepoIdOrNull(fact.getC_OrderSO_ID());
			this.C_BPartner2_ID = BPartnerId.ofRepoIdOrNull(fact.getC_BPartner2_ID());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
				log.info(new StringBuilder("Not Found (try later) ")
						.append(",C_AcctSchema_ID=").append(getC_AcctSchema_ID())
						.append(", AD_Table_ID=").append(AD_Table_ID)
						.append(",Record_ID=").append(Record_ID)
						.append(",Line_ID=").append(Line_ID)
						.append(", Account_ID=").append(m_acct.getAccount_ID()).toString());
=======
				log.info("Not Found (try later) "
						+ ",C_AcctSchema_ID=" + this.acctSchema
						+ ", adTableId=" + adTableId
						+ ",Record_ID=" + Record_ID
						+ ",Line_ID=" + Line_ID
						+ ", Account_ID=" + getAccountId());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			}

			return false; // not updated
		}
	}   // updateReverseLine

<<<<<<< HEAD
	private void setVATCodeIfApplies()
	{
		final int taxId = getC_Tax_ID();
		if (taxId <= 0)
=======
	public AcctSchemaId getAcctSchemaId() {return getAcctSchema().getId();}

	public void setAD_Org_ID(@NonNull final OrgId orgId) {this.orgId = orgId;}

	public void setAD_Org_ID(final int orgId) {setAD_Org_ID(OrgId.ofRepoIdOrAny(orgId));}

	public void setAD_OrgTrx_ID(final OrgId orgId) {this.AD_OrgTrx_ID = orgId;}

	public void setFromDimension(@NonNull final Dimension dimension)
	{
		this.C_Project_ID = dimension.getProjectId();
		this.C_Campaign_ID = dimension.getCampaignId();
		this.C_Activity_ID = dimension.getActivityId();
		this.C_OrderSO_ID = dimension.getSalesOrderId();
		this.M_Product_ID = dimension.getProductId();
		this.C_BPartner2_ID = dimension.getBpartnerId2();
		this.User1_ID = dimension.getUser1_ID();
		this.User2_ID = dimension.getUser2_ID();
		setUserElement1_ID(dimension.getUserElement1Id());
		setUserElement2_ID(dimension.getUserElement2Id());
		setUserElementNumber1(getUserElementNumber1());
		setUserElementNumber2(getUserElementNumber2());
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
			Check.assume(ClientId.equals(getAD_Client_ID(), ClientId.ofRepoIdOrNull(dim.getAD_Client_ID())), "Fact_Acct and dimension shall have the same AD_Client_ID");
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Organization))
		{
			setAD_Org_ID(OrgId.ofRepoIdOrAny(dim.getAD_Org_ID()));
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Account))
		{
			Check.assumeEquals(this.accountId, dim.getC_ElementValue_ID(), "Account_ID");
		}
		if (dim.isSegmentValueSet(AcctSegmentType.SubAccount))
		{
			Check.assumeEquals(this.C_SubAcct_ID, dim.getC_SubAcct_ID(), "C_SubAcct_ID");
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Product))
		{
			this.M_Product_ID = ProductId.ofRepoIdOrNull(dim.getM_Product_ID());
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
			this.C_Project_ID = ProjectId.ofRepoIdOrNull(dim.getC_Project_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Campaign))
		{
			this.C_Campaign_ID = Math.max(dim.getC_Campaign_ID(), 0);
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Activity))
		{
			this.C_Activity_ID = ActivityId.ofRepoIdOrNull(dim.getC_Activity_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.SalesOrder))
		{
			this.C_OrderSO_ID = OrderId.ofRepoIdOrNull(dim.getSalesOrderId());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserList1))
		{
			this.User1_ID = dim.getUser1_ID();
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserList2))
		{
			this.User2_ID = dim.getUser2_ID();
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return;
		}

<<<<<<< HEAD
		//
		// Get context
		final boolean isSOTrx;
		final DocLine<?> docLine = getDocLine();
		if (docLine != null)
		{
			isSOTrx = docLine.isSOTrx();
		}
		else
		{
			final Doc<?> doc = getDoc();
			isSOTrx = doc.isSOTrx();
		}

		final IVATCodeDAO vatCodeDAO = Services.get(IVATCodeDAO.class);
		final VATCode vatCode = vatCodeDAO.findVATCode(VATCodeMatchingRequest.builder()
				.setC_AcctSchema_ID(getC_AcctSchema_ID())
				.setC_Tax_ID(taxId)
				.setIsSOTrx(isSOTrx)
				.setDate(getDateAcct())
				.build());

		setVATCode(vatCode.getCode());
	}

	public void setQty(@NonNull final Quantity quantity)
	{
		setQty(quantity.toBigDecimal());
		setC_UOM_ID(quantity.getUomId().getRepoId());
	}

	public AcctSchemaId getAcctSchemaId()
	{
		return AcctSchemaId.ofRepoIdOrNull(getC_AcctSchema_ID());
	}

	public void setAD_Org_ID(final OrgId orgId)
	{
		super.setAD_Org_ID(OrgId.toRepoId(orgId));
	}

	public void setAD_OrgTrx_ID(final OrgId orgTrxId)
	{
		super.setAD_OrgTrx_ID(OrgId.toRepoId(orgTrxId));
	}

	public void setM_Product_ID(final ProductId productId)
	{
		super.setM_Product_ID(ProductId.toRepoId(productId));
	}

	public void setC_Activity_ID(final ActivityId activityId)
	{
		super.setC_Activity_ID(ActivityId.toRepoId(activityId));
	}

	public CurrencyId getCurrencyId()
	{
		return CurrencyId.ofRepoId(getC_Currency_ID());
	}

	public void setCurrencyId(final CurrencyId currencyId)
	{
		setC_Currency_ID(CurrencyId.toRepoId(currencyId));
	}

	public void setC_BPartner_ID(final BPartnerId bpartnerId)
	{
		super.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
	}

	public void setPostingType(@NonNull final PostingType postingType)
	{
		super.setPostingType(postingType.getCode());
	}

}    // FactLine
=======
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
			updateCurrencyRate();
		}
	}

	public void updateCurrencyRate()
	{
		setCurrencyRate(computeCurrencyRate());
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

	public void updateFrom(@NonNull FactAcctChanges changes)
	{
		setAmtSource(changes.getAmtSourceDr(), changes.getAmtSourceCr());
		setAmtAcct(changes.getAmtAcctDr(), changes.getAmtAcctCr());
		updateCurrencyRate();

		if (changes.getAccountId() != null)
		{
			this.accountId = changes.getAccountId();
		}

		setTaxIdAndUpdateVatCode(changes.getTaxId());
		setDescription(changes.getDescription());
		this.M_Product_ID = changes.getProductId();
		this.userElementString1 = changes.getUserElementString1();
		this.C_OrderSO_ID = changes.getSalesOrderId();
		this.C_Activity_ID = changes.getActivityId();

		this.appliedUserChanges = changes;
	}

}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
