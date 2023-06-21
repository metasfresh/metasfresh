package org.compiere.acct;

import de.metas.acct.Account;
import de.metas.acct.GLCategoryId;
import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.PostingType;
import de.metas.acct.api.impl.AcctSegmentType;
import de.metas.acct.doc.AcctDocRequiredServicesFacade;
import de.metas.acct.doc.PostingException;
import de.metas.acct.vatcode.VATCode;
import de.metas.acct.vatcode.VATCodeMatchingRequest;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRate;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.dimension.Dimension;
import de.metas.document.engine.DocStatus;
import de.metas.location.LocationId;
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
import de.metas.sectionCode.SectionCodeId;
import de.metas.tax.api.TaxId;
import de.metas.user.UserId;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_RevenueRecognition_Plan;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_M_Movement;
import org.compiere.model.MAccount;
import org.compiere.model.X_Fact_Acct;
import org.compiere.util.DB;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Accounting Fact Entry.
 *
 * @author Jorg Janke
 * @version $Id: FactLine.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 * <p>
 * Contributor(s):
 * Chris Farley: Fix Bug [ 1657372 ] M_MatchInv records can not be balanced
 * Carlos Ruiz - globalqss: Add setAmtAcct method rounded by Currency
 * Armen Rizal, Goodwill Consulting
 * <li>BF [ 1745154 ] Cost in Reversing Material Related Docs Bayu Sistematika -
 * <li>BF [ 2213252 ] Matching Inv-Receipt generated unproperly value for src
 * amt Teo Sarca
 * <li>FR [ 2819081 ] FactLine.getDocLine should be public
 */
public final class FactLine extends X_Fact_Acct
{
	private MAccount m_acct = null;
	private AcctSchema acctSchema = null;
	private Doc<?> m_doc = null;
	private DocLine<?> m_docLine = null;
	private CurrencyConversionContext currencyConversionCtx = null;
	@Getter(AccessLevel.PACKAGE)
	@NonNull
	private final AcctDocRequiredServicesFacade services;

	FactLine(
			@NonNull final AcctDocRequiredServicesFacade services,
			final int AD_Table_ID,
			final int Record_ID)
	{
		this(services, AD_Table_ID, Record_ID, 0);
	}

	FactLine(
			@NonNull final AcctDocRequiredServicesFacade services,
			final int AD_Table_ID,
			final int Record_ID,
			final int Line_ID)
	{
		super(Env.getCtx(), 0, ITrx.TRXNAME_ThreadInherited);
		this.services = services;
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
	 * Create Reversal (negate DR/CR) of the line
	 *
	 * @param description new description
	 * @return reversal line
	 */
	public FactLine reverse(final String description)
	{
		final FactLine reversal = new FactLine(services, getAD_Table_ID(), getRecord_ID(), getLine_ID());
		reversal.setClientOrg(this);    // needs to be set explicitly
		reversal.setDocumentInfo(m_doc, m_docLine);
		reversal.setAccount(acctSchema, m_acct);
		reversal.setPostingType(getPostingType());
		//
		reversal.setAmtSource(getCurrencyId(), getAmtSourceDr().negate(), getAmtSourceCr().negate());
		reversal.setQty(getQty().negate());
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
		final FactLine accrual = new FactLine(services, getAD_Table_ID(), getRecord_ID(), getLine_ID());
		accrual.setClientOrg(this);    // needs to be set explicitly
		accrual.setDocumentInfo(m_doc, m_docLine);
		accrual.setAccount(acctSchema, m_acct);
		accrual.setPostingType(getPostingType());
		//
		accrual.setAmtSource(getCurrencyId(), getAmtSourceCr(), getAmtSourceDr());
		accrual.convert();
		accrual.setDescription(description);
		return accrual;
	}    // reverse

	public void setAccount(@NonNull final AcctSchema acctSchema, @NonNull final Account account)
	{
		final MAccount m_account = services.getAccountById(account.getAccountId());
		setAccount(acctSchema, m_account);
		setAccountConceptualName(account.getAccountConceptualName());
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
		updateUserElementDates();

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

	private void updateUserElementDates()
	{
		updateUserElementDate(AcctSchemaElementType.UserElementDate1);
		updateUserElementDate(AcctSchemaElementType.UserElementDate2);
	}

	private void updateUserElementDate(@NonNull final AcctSchemaElementType dateElementType)
	{
		final AcctSchemaElement userElementDateElement = acctSchema.getSchemaElementByType(dateElementType);
		if (userElementDateElement != null)
		{
			final String userElementDateColumnName = userElementDateElement.getDisplayColumnName();
			LocalDate userElementLocalDate = null;

			if (m_docLine != null)
			{
				userElementLocalDate = m_docLine.getValueAsLocalDateOrNull(userElementDateColumnName);

			}
			
			if (userElementLocalDate == null)
			{
				if (m_doc == null)
				{
					throw new IllegalArgumentException("Document not set yet");
				}
				userElementLocalDate = m_doc.getValueAsLocalDateOrNull(userElementDateColumnName);
			}
			
			if (userElementLocalDate != null)
			{
				set_Value(userElementDateColumnName, TimeUtil.asTimestamp(userElementLocalDate));
			}
		}
	}

	AcctSchema getAcctSchema()
	{
		return acctSchema;
	}

	@NonNull
	private CurrencyId getAcctCurrencyId()
	{
		return getAcctSchema().getCurrencyId();
	}

	private void assertAcctCurrency(@NonNull final Money amt)
	{
		amt.assertCurrencyId(getAcctCurrencyId());
	}

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

	public void setAmtSource(@Nullable Money amtSourceDr, @Nullable Money amtSourceCr)
	{
		final CurrencyId currencyId = Money.getCommonCurrencyIdOfAll(amtSourceDr, amtSourceCr);
		setAmtSource(currencyId,
				amtSourceDr != null ? amtSourceDr.toBigDecimal() : BigDecimal.ZERO,
				amtSourceCr != null ? amtSourceCr.toBigDecimal() : BigDecimal.ZERO);
	}

	public void setAmtSource(final CurrencyId currencyId, @Nullable BigDecimal AmtSourceDr, @Nullable BigDecimal AmtSourceCr)
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

		setC_Currency_ID(CurrencyId.toRepoId(currencyId));

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
	public void setAmtAcct(@NonNull final Money amtAcctDr, @NonNull final Money amtAcctCr)
	{
		assertAcctCurrency(amtAcctDr);
		assertAcctCurrency(amtAcctCr);
		setAmtAcct(amtAcctDr.toBigDecimal(), amtAcctCr.toBigDecimal());
	}

	/**
	 * Set Accounted Amounts (alternative: call convert)
	 */
	public void setAmtAcct(BigDecimal AmtAcctDr, BigDecimal AmtAcctCr)
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
						.setDocument(getDoc())
						.setDocLine(getDocLine())
						.setFactLine(this)
						.setParameter("Rounding error", amtRoundingError)
						.setParameter("Rounding error (tolerated)", errorMarginTolerated);
				log.debug(ex.getLocalizedMessage(), ex);
			}
		}

		return amtRounded;
	}

	/**
	 * Set Document Info
	 *
	 * @param doc     document
	 * @param docLine doc line
	 */
	void setDocumentInfo(final Doc<?> doc, final DocLine<?> docLine)
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
		if (m_docLine != null)
		{
			setDateTrx(m_docLine.getDateDocAsTimestamp());
		}
		else
		{
			setDateTrx(m_doc.getDateDocAsTimestamp());
		}

		// Date Acct
		if (m_docLine != null)
		{
			setDateAcct(m_docLine.getDateAcctAsTimestamp());
		}
		else
		{
			setDateAcct(m_doc.getDateAcctAsTimestamp());
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
		setDocBaseType(m_doc.getDocBaseType().getCode());

		final DocStatus docStatus = m_doc.getDocStatus();
		setDocStatus(docStatus != null ? docStatus.getCode() : null);

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
		if (m_docLine != null && (m_docLine.getBPartnerId() != null || m_docLine.getBPartnerLocationId() != null))
		{
			setBPartnerIdAndLocation(m_docLine.getBPartnerId(), m_docLine.getBPartnerLocationId());
		}
		else
		{
			setBPartnerIdAndLocation(m_doc.getBPartnerId(), m_doc.getBPartnerLocationId());
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

		// Order
		if (m_docLine != null)
		{
			setC_OrderSO_ID(m_docLine.getSalesOrderId());
		}
		if (getC_OrderSO_ID() <= 0)
		{
			setC_OrderSO_ID(m_doc.getSalesOrderId());
		}

		// SectionCode
		if (m_docLine != null)
		{
			setM_SectionCode_ID(m_docLine.getSectionCodeId());
		}
		if (getM_SectionCode_ID() <= 0)
		{
			setM_SectionCode_ID(m_doc.getSectionCodeId());
		}

		// C_BPartner_ID2
		setC_BPartner2_ID(CoalesceUtil.coalesceSuppliers(
				() -> (m_docLine != null) ? m_docLine.getBPartnerId2() : null,
				() -> (m_doc != null) ? m_doc.getBPartnerId2() : null
		));

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

	public Doc<?> getDoc()
	{
		return m_doc;
	}

	/**
	 * Get Document Line
	 *
	 * @return doc line
	 */
	public DocLine<?> getDocLine()
	{
		return m_docLine;
	}    // getDocLine

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
	{
		if (isFrom)
		{
			setC_LocFrom_ID(C_Location_ID);
		}
		else
		{
			setC_LocTo_ID(C_Location_ID);
		}
	}   // setLocator

	public void setLocation(@Nullable final LocationId C_Location_ID, final boolean isFrom)
	{
		setLocation(LocationId.toRepoId(C_Location_ID), isFrom);
	}

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
		return Balance.of(getCurrencyId(), getAmtSourceDr(), getAmtSourceCr());
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
		return thisCR == otherCR;
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
		else if (amtAcctDrSign == 0 && amtAcctCrSign == 0)
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
	 *
	 * <pre>
	 *  Example:    1       -1      1       -1
	 *  Old         100/0   100/0   0/100   0/100
	 *  New         99/0    101/0   0/99    0/101
	 * </pre>
	 *
	 * @param deltaAmount delta amount
	 */
	public void currencyCorrect(final Money deltaAmount)
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
		if (getC_Currency_ID() <= 0)
		{
			setC_Currency_ID(acctCurrencyId.getRepoId());
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

	private CurrencyRate getCurrencyRate(final CurrencyId currencyId, final CurrencyId acctCurrencyId)
	{
		final CurrencyConversionContext conversionCtx = getCurrencyConversionCtx();
		return services.getCurrencyRate(conversionCtx, currencyId, acctCurrencyId);
	}

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

		// Get Conversion Type from Line or Header
		LocalDateAndOrgId convDate;
		CurrencyConversionTypeId conversionTypeId = null;
		if (m_docLine != null)            // get from line
		{
			convDate = m_docLine.getDateAcct();
			conversionTypeId = m_docLine.getCurrencyConversionTypeId();
		}
		else
		{
			convDate = m_doc.getDateAcct();
		}

		if (conversionTypeId == null)    // get from header
		{
			conversionTypeId = m_doc.getCurrencyConversionTypeId();
		}

		return services.createCurrencyConversionContext(
				convDate,
				conversionTypeId,
				m_doc.getClientId());
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
		String sb = "FactLine=[" + getAD_Table_ID() + ":" + getRecord_ID()
				+ "," + getAccountConceptualName() + "/" + (m_acct != null ? m_acct.getCombination() : "?")
				+ ",Cur=" + getC_Currency_ID()
				+ ", DR=" + getAmtSourceDr() + "|" + getAmtAcctDr()
				+ ", CR=" + getAmtSourceCr() + "|" + getAmtAcctCr()
				+ ", Record/Line=" + getRecord_ID() + (getLine_ID() > 0 ? "/" + getLine_ID() : "");
		final BigDecimal currencyRate = getCurrencyRate();
		if (currencyRate != null && currencyRate.signum() != 0 && currencyRate.compareTo(BigDecimal.ONE) != 0)
		{
			sb = sb + ", currencyRate=" + currencyRate;
		}
		sb = sb + "]";
		return sb;
	}

	public OrgId getOrgId()
	{
		return OrgId.ofRepoIdOrAny(getAD_Org_ID());
	}

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
			final OrgId locatorOrgId = services.getOrgIdByLocatorRepoId(locatorId);
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
			if (DocBaseType.GLJournal.equals(m_doc.getDocBaseType()))
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
			if (DocBaseType.GLJournal.equals(m_doc.getDocBaseType()))
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
			if (getC_OrderSO_ID() <= 0)
			{
				setC_OrderSO_ID(m_acct.getC_OrderSO_ID());
			}
			if (getM_SectionCode_ID() <= 0)
			{
				setM_SectionCode_ID(m_acct.getM_SectionCode_ID());
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
			if (DocBaseType.ARInvoice.equals(m_doc.getDocBaseType())
					&& m_docLine != null
					&& m_docLine.getC_RevenueRecognition_ID() > 0)
			{
				setAccount_ID(createRevenueRecognition(
						m_docLine.getC_RevenueRecognition_ID(),
						m_docLine.get_ID(),
						toAccountDimension()));
			}
		}

		updateCurrencyRateIfNotSet();

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
				.setSalesOrderId(getC_OrderSO_ID())
				.setM_SectionCode_ID(getM_SectionCode_ID())
				.setUserElementDate1(TimeUtil.asInstant(getUserElementDate1()))
				.setUserElementDate2(TimeUtil.asInstant(getUserElementDate2()))
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

	/**************************************************************************
	 * Update Line with reversed Original Amount in Accounting Currency.
	 * Also copies original dimensions like Project, etc.
	 * Called from Doc_MatchInv
	 *
	 * @param AD_Table_ID table
	 * @param Record_ID record
	 * @param Line_ID line
	 * @param multiplier targetQty/documentQty
	 * @return true if success
	 */
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean updateReverseLine(
			final int AD_Table_ID,
			final int Record_ID,
			final int Line_ID,
			final BigDecimal multiplier)
	{
		final IQueryBuilder<I_Fact_Acct> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fact_Acct.class)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_C_AcctSchema_ID, getC_AcctSchema_ID())
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, AD_Table_ID)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, Record_ID)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Line_ID, Line_ID)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Account_ID, m_acct.getAccount_ID());
		if (I_M_Movement.Table_ID == AD_Table_ID)
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
			setAD_Org_ID(fact.getAD_Org_ID());
			setC_OrderSO_ID(fact.getC_OrderSO_ID());
			setM_SectionCode_ID(fact.getM_SectionCode_ID());
			setC_BPartner2_ID(fact.getC_BPartner2_ID());

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
						+ ",C_AcctSchema_ID=" + getC_AcctSchema_ID()
						+ ", AD_Table_ID=" + AD_Table_ID
						+ ",Record_ID=" + Record_ID
						+ ",Line_ID=" + Line_ID
						+ ", Account_ID=" + m_acct.getAccount_ID());
			}

			return false; // not updated
		}
	}   // updateReverseLine

	private void setVATCodeIfApplies()
	{
		final int taxId = getC_Tax_ID();
		if (taxId <= 0)
		{
			return;
		}

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

		setVATCode(services.findVATCode(VATCodeMatchingRequest.builder()
						.setC_AcctSchema_ID(getC_AcctSchema_ID())
						.setC_Tax_ID(taxId)
						.setIsSOTrx(isSOTrx)
						.setDate(getDateAcct())
						.build())
				.map(VATCode::getCode)
				.orElse(null));
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

	public void setAD_OrgTrx_ID(@Nullable final OrgId orgTrxId)
	{
		super.setAD_OrgTrx_ID(OrgId.toRepoId(orgTrxId));
	}

	public void setM_Product_ID(@Nullable final ProductId productId)
	{
		super.setM_Product_ID(ProductId.toRepoId(productId));
	}

	public void setC_Activity_ID(@Nullable final ActivityId activityId)
	{
		super.setC_Activity_ID(ActivityId.toRepoId(activityId));
	}

	public CurrencyId getCurrencyId()
	{
		return CurrencyId.ofRepoId(getC_Currency_ID());
	}

	public void setC_BPartner2_ID(@Nullable final BPartnerId bpartnerId)
	{
		super.setC_BPartner2_ID(BPartnerId.toRepoId(bpartnerId));
	}

	public void setC_Project_ID(@Nullable final ProjectId projectId)
	{
		super.setC_Project_ID(ProjectId.toRepoId(projectId));
	}

	public void setPostingType(@NonNull final PostingType postingType)
	{
		super.setPostingType(postingType.getCode());
	}

	public void setC_Tax_ID(@Nullable final TaxId taxId)
	{
		super.setC_Tax_ID(TaxId.toRepoId(taxId));
	}

	public void setM_SectionCode_ID(@Nullable final SectionCodeId sectionCodeId)
	{
		super.setM_SectionCode_ID(SectionCodeId.toRepoId(sectionCodeId));
	}

	public void setC_OrderSO_ID(@Nullable OrderId orderId)
	{
		super.setC_OrderSO_ID(OrderId.toRepoId(orderId));
	}

	public void setC_DocType_ID(@Nullable DocTypeId docTypeId)
	{
		setC_DocType_ID(DocTypeId.toRepoId(docTypeId));
	}

	public void setGL_Category_ID(@Nullable GLCategoryId glCategoryId)
	{
		setGL_Category_ID(GLCategoryId.toRepoId(glCategoryId));
	}

	public void setFromDimension(@NonNull final Dimension dimension)
	{
		setC_Project_ID(dimension.getProjectId());
		setC_Campaign_ID(dimension.getCampaignId());
		setC_Activity_ID(dimension.getActivityId());
		setC_OrderSO_ID(dimension.getSalesOrderId());
		setM_SectionCode_ID(dimension.getSectionCodeId());
		setM_Product_ID(dimension.getProductId());
		setC_BPartner2_ID(dimension.getBpartnerId2());
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
			setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			setC_BPartner_Location_ID(bPartnerLocationId.getRepoId());
		}
		else if (bPartnerId != null)
		{
			setC_BPartner_ID(bPartnerId.getRepoId());
			setC_BPartner_Location_ID(-1);
		}
		else
		{
			setC_BPartner_ID(-1);
			setC_BPartner_Location_ID(-1);
		}
	}

	public void setBPartnerId(@Nullable final BPartnerId bPartnerId)
	{
		final BPartnerId currentBPartnerId = BPartnerId.ofRepoIdOrNull(getC_BPartner_ID());

		if (BPartnerId.equals(currentBPartnerId, bPartnerId))
		{
			return;
		}

		setC_BPartner_ID(BPartnerId.toRepoId(bPartnerId));
		setC_BPartner_Location_ID(-1);
	}

	public void updateFromDimension(final AccountDimension dim)
	{
		if (dim.getAcctSchemaId() != null)
		{
			super.setC_AcctSchema_ID(dim.getAcctSchemaId().getRepoId());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Client))
		{
			// fa.setAD_Client_ID(dim.getAD_Client_ID());
			de.metas.util.Check.assume(getAD_Client_ID() == dim.getAD_Client_ID(), "Fact_Acct and dimension shall have the same AD_Client_ID");
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Organization))
		{
			setAD_Org_ID(dim.getAD_Org_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Account))
		{
			setAccount_ID(dim.getC_ElementValue_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.SubAccount))
		{
			setC_SubAcct_ID(dim.getC_SubAcct_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Product))
		{
			setM_Product_ID(dim.getM_Product_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.BPartner))
		{
			setBPartnerId(BPartnerId.ofRepoIdOrNull(dim.getC_BPartner_ID()));
		}
		if (dim.isSegmentValueSet(AcctSegmentType.OrgTrx))
		{
			setAD_OrgTrx_ID(dim.getAD_OrgTrx_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.LocationFrom))
		{
			setC_LocFrom_ID(dim.getC_LocFrom_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.LocationTo))
		{
			setC_LocTo_ID(dim.getC_LocTo_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.SalesRegion))
		{
			setC_SalesRegion_ID(dim.getC_SalesRegion_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Project))
		{
			setC_Project_ID(dim.getC_Project_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Campaign))
		{
			setC_Campaign_ID(dim.getC_Campaign_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Activity))
		{
			setC_Activity_ID(dim.getC_Activity_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.SalesOrder))
		{
			setC_OrderSO_ID(dim.getSalesOrderId());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.SectionCode))
		{
			setM_SectionCode_ID(dim.getM_SectionCode_ID());
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

		return amtAcct
				.divide(amtSource, schemaCurrencyPrecision.toInt(), schemaCurrencyPrecision.getRoundingMode());
	}

	private void updateCurrencyRateIfNotSet()
	{
		if (getCurrencyRate().signum() == 0 || getCurrencyRate().compareTo(BigDecimal.ONE) == 0)
		{
			setCurrencyRate(computeCurrencyRate());
		}
	}
}    // FactLine
