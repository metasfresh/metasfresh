package org.compiere.acct;

import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.AcctSchemaId;
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

	/**
	 * Create Reversal (negate DR/CR) of the line
	 *
	 * @param description new description
	 * @return reversal line
	 */
	public FactLine reverse(final String description)
	{
		final FactLine reversal = new FactLine(getAD_Table_ID(), getRecord_ID(), getLine_ID());
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
		final FactLine accrual = new FactLine(getAD_Table_ID(), getRecord_ID(), getLine_ID());
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

		setCurrencyId(currencyId);

		// Currency Precision
		final CurrencyPrecision precision = currencyId != null
				? Services.get(ICurrencyDAO.class).getStdPrecision(currencyId)
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
		{
			return;
		}

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
		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
		final CurrencyConversionContext conversionCtx = getCurrencyConversionCtx();
		return currencyConversionBL.getCurrencyRate(conversionCtx, currencyId, acctCurrencyId);
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
	public boolean updateReverseLine(
			final int AD_Table_ID,
			final int Record_ID,
			final int Line_ID,
			final BigDecimal multiplier)
	{
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
		}

		final I_Fact_Acct fact = queryBuilder.create().firstOnly(I_Fact_Acct.class);
		if (fact != null)
		{
			final CurrencyId currencyId = CurrencyId.ofRepoId(fact.getC_Currency_ID());
			// Accounted Amounts - reverse
			final BigDecimal dr = fact.getAmtAcctDr();
			final BigDecimal cr = fact.getAmtAcctCr();
			// setAmtAcctDr (cr.multiply(multiplier));
			// setAmtAcctCr (dr.multiply(multiplier));
			setAmtAcct(
					currencyId,
					cr.multiply(multiplier),
					dr.multiply(multiplier));
			//
			// Bayu Sistematika - Source Amounts
			// Fixing source amounts
			final BigDecimal drSourceAmt = fact.getAmtSourceDr();
			final BigDecimal crSourceAmt = fact.getAmtSourceCr();
			setAmtSource(
					currencyId,
					crSourceAmt.multiply(multiplier),
					drSourceAmt.multiply(multiplier));
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
				log.info(new StringBuilder("Not Found (try later) ")
						.append(",C_AcctSchema_ID=").append(getC_AcctSchema_ID())
						.append(", AD_Table_ID=").append(AD_Table_ID)
						.append(",Record_ID=").append(Record_ID)
						.append(",Line_ID=").append(Line_ID)
						.append(", Account_ID=").append(m_acct.getAccount_ID()).toString());
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
