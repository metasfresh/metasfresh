/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.IntFunction;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.logging.LoggingHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.MAccount;
import org.compiere.model.MNote;
import org.compiere.model.MPeriod;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.X_C_DocType;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnable2;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaGeneralLedger;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.doc.AcctDocContext;
import de.metas.acct.doc.AcctDocRequiredServicesFacade;
import de.metas.acct.doc.PostingException;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountAcct;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyDAO;
import de.metas.currency.exceptions.NoCurrencyRateFoundException;
import de.metas.document.engine.IDocument;
import de.metas.error.AdIssueId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.lang.SOTrx;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.lang.CoalesceUtil;
import de.metas.util.lang.RepoIdAware;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

/**
 * Posting Document Root.
 *
 * <pre>
 *  Table               Base Document Types (C_DocType.DocBaseType & AD_Reference_ID=183)
 *      Class           AD_Table_ID
 *  ------------------  ------------------------------
 *  C_Invoice:          ARI, ARC, ARF, API, APC
 *      Doc_Invoice     318 - has C_DocType_ID
 *
 *  C_Payment:          ARP, APP
 *      Doc_Payment     335 - has C_DocType_ID
 *
 *  C_Order:            SOO, POO,  POR (Requisition)
 *      Doc_Order       259 - has C_DocType_ID
 *
 *  M_InOut:            MMS, MMR
 *      Doc_InOut       319 - DocType derived
 *
 *  M_Inventory:        MMI
 *      Doc_Inventory   321 - DocType fixed
 *
 *  M_Movement:         MMM
 *      Doc_Movement    323 - DocType fixed
 *
 *  M_Production:       MMP
 *      Doc_Production  325 - DocType fixed
 *
 * M_Production:        MMO
 *      Doc_CostCollector  330 - DocType fixed
 *
 *  C_BankStatement:    CMB
 *      Doc_Bank        392 - DocType fixed
 *
 *  C_Cash:             CMC
 *      Doc_Cash        407 - DocType fixed
 *
 *  C_Allocation:       CMA
 *      Doc_Allocation  390 - DocType fixed
 *
 *  GL_Journal:         GLJ
 *      Doc_GLJournal   224 = has C_DocType_ID
 *
 *  Matching Invoice    MXI
 *      M_MatchInv      472 - DocType fixed
 *
 *  Matching PO         MXP
 *      M_MatchPO       473 - DocType fixed
 *
 * Project Issue		PJI
 * 	C_ProjectIssue	623 - DocType fixed
 *
 * </pre>
 *
 * Also see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>FR [ 2520591 ] Support multiples calendar for Org
 * @version $Id: Doc.java,v 1.6 2006/07/30 00:53:33 jjanke Exp $
 */
public abstract class Doc<DocLineType extends DocLine<?>>
{
	private final String SYSCONFIG_CREATE_NOTE_ON_ERROR = "org.compiere.acct.Doc.createNoteOnPostError";
	protected static final AdMessageKey MSG_NoAccountFound = AdMessageKey.of("Doc_NoAccountFound_Error");

	@Getter(AccessLevel.PROTECTED)
	protected final AcctDocRequiredServicesFacade services;

	/** AR Invoices - ARI */
	public static final String DOCTYPE_ARInvoice = X_C_DocType.DOCBASETYPE_ARInvoice;
	/** AR Credit Memo */
	public static final String DOCTYPE_ARCredit = "ARC";
	/** AR Receipt */
	public static final String DOCTYPE_ARReceipt = "ARR";
	/** AR ProForma */
	public static final String DOCTYPE_ARProForma = "ARF";
	/** AP Invoices */
	public static final String DOCTYPE_APInvoice = "API";
	/** AP Credit Memo */
	public static final String DOCTYPE_APCredit = "APC";
	/** AP Payment */
	public static final String DOCTYPE_APPayment = "APP";
	/** CashManagement Bank Statement */
	public static final String DOCTYPE_BankStatement = "CMB";
	/** CashManagement Cash Journals */
	public static final String DOCTYPE_CashJournal = "CMC";
	/** CashManagement Allocations */
	public static final String DOCTYPE_Allocation = "CMA";
	/** Material Shipment */
	public static final String DOCTYPE_MatShipment = "MMS";
	/** Material Receipt */
	public static final String DOCTYPE_MatReceipt = "MMR";
	/** Material Inventory */
	public static final String DOCTYPE_MatInventory = "MMI";
	/** Material Movement */
	public static final String DOCTYPE_MatMovement = "MMM";
	/** Material Production */
	public static final String DOCTYPE_MatProduction = "MMP";
	/** Match Invoice */
	public static final String DOCTYPE_MatMatchInv = "MXI";
	/** Match PO */
	public static final String DOCTYPE_MatMatchPO = "MXP";
	/** GL Journal */
	public static final String DOCTYPE_GLJournal = "GLJ";
	/** Purchase Order */
	public static final String DOCTYPE_POrder = "POO";
	/** Sales Order */
	public static final String DOCTYPE_SOrder = "SOO";
	/** Project Issue */
	public static final String DOCTYPE_ProjectIssue = "PJI";
	/** Purchase Requisition */
	public static final String DOCTYPE_PurchaseRequisition = "POR";

	/** Log per Document */
	private static final Logger log = LogManager.getLogger(Doc.class);

	/**
	 * @param ctx construction parameters
	 */
	/* package */ Doc(final AcctDocContext ctx)
	{
		this(ctx, (String)null); // defaultDocBaseType=null
	}

	/**
	 * @param ctx construction parameters
	 * @param defaultDocBaseType suggested DocBaseType to be used
	 */
	protected Doc(@NonNull final AcctDocContext ctx, final String defaultDocBaseType)
	{
		services = ctx.getServices();
		acctSchemas = ctx.getAcctSchemas();

		//
		// Document model
		final Object documentModel = ctx.getDocumentModel();
		p_po = InterfaceWrapperHelper.getPO(documentModel);
		Check.assumeNotNull(p_po, "p_po not null");

		// IMPORTANT: to make sure events like FactAcctListenersService.fireAfterUnpost will use the thread inherited trx
		p_po.set_TrxName(ITrx.TRXNAME_ThreadInherited);

		// DocStatus
		{
			final int index = p_po.get_ColumnIndex("DocStatus");
			if (index >= 0)
			{
				m_DocStatus = (String)p_po.get_Value(index);
			}
			else
			{
				m_DocStatus = null; // no DocStatus (e.g. M_MatchInv etc)
			}
		}

		// Document Type
		setDocumentType(defaultDocBaseType);
	}   // Doc

	/** Accounting Schemas */
	private final ImmutableList<AcctSchema> acctSchemas;
	/** The Document */
	private final PO p_po;
	/** Document Type */
	private String m_DocumentType = null;
	/** Document Status */
	private final String m_DocStatus;
	/** Document No */
	private String m_DocumentNo = null;
	/** Description */
	private String m_Description = null;
	/** GL Category */
	private int m_GL_Category_ID = 0;
	/** GL Period */
	private MPeriod m_period = null;
	/** Period ID */
	private int m_C_Period_ID = 0;
	private final LocationId locationFromId = null;
	private final LocationId locationToId = null;
	private LocalDate _dateAcct = null;
	private LocalDate _dateDoc = null;
	/** Is (Source) Multi-Currency Document - i.e. the document has different currencies (if true, the document will not be source balanced) */
	private boolean m_MultiCurrency = false;
	/** BP Sales Region */
	private int m_BP_C_SalesRegion_ID = -1;
	private Optional<BPartnerId> _bpartnerId; // lazy

	/** Bank Account */
	private Optional<BankAccountId> _bankAccountId = null; // lazy
	private BankAccount bankAccount = null;
	/** Cach Book */
	private int m_C_CashBook_ID = -1;

	private Optional<CurrencyId> _currencyId; // lazy
	private CurrencyPrecision _currencyPrecision; // lazy

	/** Contained Doc Lines */
	private List<DocLineType> docLines;

	public final String get_TableName()
	{
		return getPO().get_TableName();
	}

	protected final int get_Table_ID()
	{
		return getPO().get_Table_ID();
	}

	/**
	 * @return record id
	 */
	public final int get_ID()
	{
		return getPO().get_ID();
	}

	/**
	 * Get Persistent Object
	 *
	 * @return po
	 */
	private final PO getPO()
	{
		return p_po;
	}	// getPO

	protected final <T> T getModel(final Class<T> modelClass)
	{
		return InterfaceWrapperHelper.create(getPO(), modelClass);
	}

	protected final void setDocLines(final List<DocLineType> docLines)
	{
		this.docLines = docLines;
	}

	protected final List<DocLineType> getDocLines()
	{
		return docLines;
	}

	/**
	 * Post Document.
	 *
	 * <pre>
	 *  - try to lock document (Processed='Y' (AND Processing='N' AND Posted='N'))
	 * 		- if not ok - return false
	 *          - postlogic (for all Accounting Schema)
	 *              - create Fact lines
	 *          - postCommit
	 *              - commits Fact lines and Document & sets Processing = 'N'
	 *              - if error - create Note
	 * </pre>
	 *
	 * @param force if true ignore that locked
	 * @param repost if true ignore that already posted
	 * @return null if posted error otherwise
	 */
	public final void post(final boolean force, final boolean repost)
	{
		lock(force, repost);

		//
		// Do the actual posting
		services.runInThreadInheritedTrx(new TrxRunnable2()
		{
			PostingException postingException;

			@Override
			public void run(final String localTrxName_NOTUSED)
			{
				post0(force, repost);
			}

			@Override
			public boolean doCatch(final Throwable e)
			{
				final PostingException postingException = newPostingException(e);
				this.postingException = postingException;

				final boolean createNote = services.getSysConfigBooleanValue(SYSCONFIG_CREATE_NOTE_ON_ERROR);
				if (createNote)
				{
					createErrorNote(postingException);
				}
				LoggingHelper.log(log, postingException.getLogLevel(), postingException.getLocalizedMessage(), postingException);

				throw postingException;
			}

			@Override
			public void doFinally()
			{
				//
				// Unlock (in parent transaction)
				unlock(postingException);
			}
		});
	}

	private final void post0(final boolean force, final boolean repost)
	{
		//
		// Validate document's DocStatus
		if (m_DocStatus == null)
		{
			// This is a valid case (e.g. M_MatchInv, M_MatchPO)
		}
		else if (m_DocStatus.equals(IDocument.STATUS_Completed)
				|| m_DocStatus.equals(IDocument.STATUS_Closed)
				|| m_DocStatus.equals(IDocument.STATUS_Voided)
				|| m_DocStatus.equals(IDocument.STATUS_Reversed))
		{
			// This is THE valid case
		}
		else
		{
			final String errmsg = "Invalid DocStatus='" + m_DocStatus + "' for DocumentNo=" + getDocumentNo();
			throw newPostingException()
					.setPreserveDocumentPostedStatus()
					.setDetailMessage(errmsg);
		}

		//
		// Validate document's AD_Client_ID
		if (!getClientId().equals(acctSchemas.get(0).getClientId()))
		{
			final String errmsg = "AD_Client_ID Conflict - Document=" + getClientId()
					+ ", AcctSchema=" + acctSchemas.get(0).getClientId();
			throw newPostingException()
					.setPreserveDocumentPostedStatus()
					.setDetailMessage(errmsg);
		}

		//
		// Load document details
		try
		{
			loadDocumentDetails();
		}
		catch (final Exception ex)
		{
			throw newPostingException(ex)
					.setPreserveDocumentPostedStatus();
		}

		//
		// Delete existing Accounting
		if (repost)
		{
			if (isPosted() && !isPeriodOpen())	// already posted - don't delete if period closed
			{
				throw newPostingException()
						.setPreserveDocumentPostedStatus()
						.setDetailMessage("@PeriodClosed@");
			}

			// delete existing accounting records
			deleteAcct();
		}
		else if (isPosted())
		{
			throw newPostingException()
					.setPreserveDocumentPostedStatus()
					.setDetailMessage("@AlreadyPosted@");
		}

		//
		// Create Fact per AcctSchema
		final List<Fact> facts = new ArrayList<>();
		for (final AcctSchema acctSchema : acctSchemas)
		{
			if (isSkipPosting(acctSchema))
			{
				continue;
			}

			// post
			final List<Fact> factsForAcctSchema = postLogic(acctSchema);
			facts.addAll(factsForAcctSchema);
		}

		//
		// Fire event: BEFORE_POST
		services.fireBeforePostEvent(getPO());

		//
		// Save facts
		for (final Fact fact : facts)
		{
			fact.save();
		}

		//
		// Fire event: AFTER_POST
		services.fireAfterPostEvent(getPO());
		// Execute after document posted code
		afterPost();

		//
		// Dispose facts
		for (Fact fact : facts)
		{
			fact.dispose();
		}
	}

	private boolean isSkipPosting(final AcctSchema acctSchema)
	{
		// if acct schema has "only" org, skip
		boolean skip = false;
		if (acctSchema.isPostOnlyForSomeOrgs())
		{
			// Header Level Org
			skip = acctSchema.isDisallowPostingForOrg(getOrgId());
			// Line Level Org
			final List<DocLineType> docLines = getDocLines();
			if (docLines != null)
			{
				for (int line = 0; skip && line < docLines.size(); line++)
				{
					skip = acctSchema.isDisallowPostingForOrg(docLines.get(line).getOrgId());
					if (!skip)
					{
						break;
					}
				}
			}
		}
		return skip;
	}

	/**
	 * Delete Accounting
	 *
	 * @return number of records deleted
	 */
	private final int deleteAcct()
	{
		final Object documentPO = getPO();
		return services.deleteFactAcctByDocumentModel(documentPO);
	}	// deleteAcct

	private final List<Fact> postLogic(final AcctSchema acctSchema)
	{
		// rejectUnbalanced
		final AcctSchemaGeneralLedger acctSchemaGL = acctSchema.getGeneralLedger();
		if (!acctSchemaGL.isSuspenseBalancing() && !isBalanced())
		{
			throw newPostingException()
					.setAcctSchema(acctSchema)
					.setPostingStatus(PostingStatus.NotBalanced);
		}

		// rejectUnconvertible
		checkConvertible(acctSchema);

		// rejectPeriodClosed
		if (!isPeriodOpen())
		{
			throw newPostingException()
					.setAcctSchema(acctSchema)
					.setPostingStatus(PostingStatus.PeriodClosed);
		}

		//
		// Create facts for accounting schema
		final List<Fact> facts = createFacts(acctSchema);
		if (facts == null)
		{
			throw newPostingException()
					.setAcctSchema(acctSchema)
					.setPostingStatus(PostingStatus.Error)
					.setDetailMessage("No facts");
		}

		//
		// Process facts: validate, GL distribution, balance etc
		for (final Fact fact : facts)
		{
			processFacts(fact);
		}

		return facts;
	}   // postLogic

	private void processFacts(final Fact fact)
	{
		if (fact == null)
		{
			throw newPostingException()
					// .setAcctSchema(acctSchema)
					.setPostingStatus(PostingStatus.Error)
					.setDetailMessage("No fact");
		}

		final AcctSchema acctSchema = fact.getAcctSchema();

		// check accounts
		final BooleanWithReason checkAccountsResult = fact.checkAccounts();
		if (checkAccountsResult.isFalse())
		{
			throw newPostingException()
					.setAcctSchema(acctSchema)
					.setPostingStatus(PostingStatus.InvalidAccount)
					.setDetailMessage(checkAccountsResult.getReason())
					.setFact(fact);
		}

		// distribute
		try
		{
			fact.distribute();
		}
		catch (final Exception e)
		{
			throw newPostingException(e)
					.setAcctSchema(acctSchema)
					.setPostingStatus(PostingStatus.Error)
					.setFact(fact)
					.setDetailMessage("Fact distribution error: " + e.getLocalizedMessage());
		}

		// Balance source amounts
		if (fact.isSingleCurrency() && !fact.isSourceBalanced())
		{
			fact.balanceSource();
			if (!fact.isSourceBalanced())
			{
				throw newPostingException()
						.setAcctSchema(acctSchema)
						.setPostingStatus(PostingStatus.NotBalanced)
						.setFact(fact)
						.setDetailMessage("Source amounts not balanced");
			}
		}

		// balanceSegments
		if (!fact.isSegmentBalanced())
		{
			fact.balanceSegments();
			if (!fact.isSegmentBalanced())
			{
				throw newPostingException()
						.setAcctSchema(acctSchema)
						.setPostingStatus(PostingStatus.NotBalanced)
						.setFact(fact)
						.setDetailMessage("Segment not balanced");
			}
		}

		// balanceAccounting
		if (!fact.isAcctBalanced())
		{
			fact.balanceAccounting();
			if (!fact.isAcctBalanced())
			{
				throw newPostingException()
						.setAcctSchema(acctSchema)
						.setPostingStatus(PostingStatus.NotBalanced)
						.setFact(fact)
						.setDetailMessage("Accountable amounts not balanced");
			}
		}
	}

	/**
	 * Lock document
	 *
	 * @param force force posting
	 * @param repost true if is document re-posting; i.e. it will assume the document was not already posted
	 * @throws PostingException
	 */
	private final void lock(final boolean force, final boolean repost)
	{
		final String tableName = get_TableName();
		final int recordId = get_ID();

		final StringBuilder sql = new StringBuilder("UPDATE ");
		sql.append(tableName).append(" SET Processing='Y' WHERE ")
				.append(tableName).append("_ID=").append(recordId)
				.append(" AND Processed='Y' AND IsActive='Y'");
		if (!force)
		{
			sql.append(" AND (Processing='N' OR Processing IS NULL)");
		}
		if (!repost)
		{
			sql.append(" AND Posted='N'");
		}

		final int updatedCount = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (updatedCount != 1)
		{
			final PO po = getPO();
			final String errmsg = force ? "Cannot Lock - ReSubmit" : "Cannot Lock - ReSubmit or RePost with Force";
			throw newPostingException()
					.setDetailMessage(errmsg)
					.addDetailMessage("Hint: it could be that for some reason, the document remained locked (i.e. Processing=Y), so you could unlock it to fix the issue.")
					.setParameter("Processing", po.get_Value("Processing"))
					.setParameter("Processed", po.get_Value("Processed"))
					.setParameter("IsActive", po.get_Value("IsActive"))
					.setParameter("Posted", po.get_Value("Posted"))
					.setParameter("SQL", sql.toString())
					.setPostingStatus(PostingStatus.NotPosted)
					.setPreserveDocumentPostedStatus();
		}
	}

	private final void unlock(final PostingException exception)
	{
		final String tableName = get_TableName();
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		final String keyColumnName = poInfo.getKeyColumnName();
		final int recordId = get_ID();

		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(tableName).append(" SET ");

		//
		// Processing (i.e. unlock it)
		sql.append("Processing='N'");

		//
		// Posted
		final boolean updatePostedStatus = exception != null && !exception.isPreserveDocumentPostedStatus();
		if (exception == null)
		{
			sql.append(", Posted=").append(DB.TO_STRING(PostingStatus.Posted.getStatusCode()));
		}
		else if (updatePostedStatus)
		{
			final PostingStatus postingStatus = exception.getPostingStatus(PostingStatus.Error);
			sql.append(", Posted=").append(DB.TO_STRING(postingStatus.getStatusCode()));
		}

		//
		// PostingError_Issue_ID
		final String COLUMNNAME_PostingError_Issue_ID = "PostingError_Issue_ID";
		boolean hasPostingIssueColumn = poInfo.hasColumnName(COLUMNNAME_PostingError_Issue_ID);
		if (hasPostingIssueColumn)
		{
			final AdIssueId postingErrorIssueId = exception != null
					? services.createIssue(exception)
					: null;

			final AdIssueId previousPostingErrorIssueId = AdIssueId.ofRepoIdOrNull(getPO().get_ValueAsInt(COLUMNNAME_PostingError_Issue_ID));
			if (previousPostingErrorIssueId != null
					&& !previousPostingErrorIssueId.equals(postingErrorIssueId))
			{
				services.markIssueDeprecated(previousPostingErrorIssueId);
			}

			if (postingErrorIssueId != null)
			{

				sql.append(", ").append(COLUMNNAME_PostingError_Issue_ID).append("=").append(postingErrorIssueId.getRepoId());
			}
			else
			{
				sql.append(", ").append(COLUMNNAME_PostingError_Issue_ID).append(" = NULL ");
			}
		}

		sql.append("\n WHERE ").append(keyColumnName).append("=").append(recordId);

		final int updateCount = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		fireDocumentChanged();

		if (updateCount != 1)
		{
			throw newPostingException()
					.setDetailMessage("Unable to unlock");
		}
	}

	private void fireDocumentChanged()
	{
		services.fireDocumentChanged(get_TableName(), get_ID());
	}

	/**************************************************************************
	 * Load Document Type and GL Info. Set p_DocumentType and p_GL_Category_ID
	 *
	 * @return document type (i.e. DocBaseType)
	 */
	protected final String getDocumentType()
	{
		if (m_DocumentType == null)
		{
			setDocumentType(null);
		}
		return m_DocumentType;
	}   // getDocumentType

	/**
	 * Load Document Type and GL Info. Set p_DocumentType and p_GL_Category_ID
	 *
	 * @param docBaseType optional document base type to be used.
	 */
	private final void setDocumentType(final String docBaseType)
	{
		if (docBaseType != null)
		{
			m_DocumentType = docBaseType;
		}

		// No Document Type defined
		if (m_DocumentType == null && getC_DocType_ID() > 0)
		{
			final String sql = "SELECT DocBaseType, GL_Category_ID FROM C_DocType WHERE C_DocType_ID=?";
			PreparedStatement pstmt = null;
			ResultSet rsDT = null;
			try
			{
				pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				pstmt.setInt(1, getC_DocType_ID());
				rsDT = pstmt.executeQuery();
				if (rsDT.next())
				{
					m_DocumentType = rsDT.getString(1);
					m_GL_Category_ID = rsDT.getInt(2);
				}
			}
			catch (final SQLException e)
			{
				log.error(sql, e);
			}
			finally
			{
				DB.close(rsDT, pstmt);
				rsDT = null;
				pstmt = null;
			}
		}
		if (m_DocumentType == null)
		{
			log.error("No DocBaseType for C_DocType_ID=" + getC_DocType_ID() + ", DocumentNo=" + getDocumentNo());
		}

		// We have a document Type, but no GL info - search for DocType
		if (m_GL_Category_ID <= 0)
		{
			final String sql = "SELECT GL_Category_ID FROM C_DocType WHERE AD_Client_ID=? AND DocBaseType=?";
			PreparedStatement pstmt = null;
			ResultSet rsDT = null;
			try
			{
				pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				pstmt.setInt(1, getClientId().getRepoId());
				pstmt.setString(2, m_DocumentType);
				rsDT = pstmt.executeQuery();
				if (rsDT.next())
				{
					m_GL_Category_ID = rsDT.getInt(1);
				}
			}
			catch (final SQLException e)
			{
				log.error(sql, e);
			}
			finally
			{
				DB.close(rsDT, pstmt);
			}
		}

		// Still no GL_Category - get Default GL Category
		if (m_GL_Category_ID <= 0)
		{
			final String sql = "SELECT GL_Category_ID FROM GL_Category "
					+ "WHERE AD_Client_ID=? "
					+ "ORDER BY IsDefault DESC";
			PreparedStatement pstmt = null;
			ResultSet rsDT = null;
			try
			{
				pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				pstmt.setInt(1, getClientId().getRepoId());
				rsDT = pstmt.executeQuery();
				if (rsDT.next())
				{
					m_GL_Category_ID = rsDT.getInt(1);
				}
				rsDT.close();
				pstmt.close();
			}
			catch (final SQLException e)
			{
				log.error(sql, e);
			}
			finally
			{
				DB.close(rsDT, pstmt);
			}
		}
		//
		if (m_GL_Category_ID <= 0)
		{
			log.error("No default GL_Category - " + toString());
		}

		if (m_DocumentType == null)
		{
			throw new IllegalStateException("Document Type not found");
		}
	}	// setDocumentType

	/**************************************************************************
	 * Is the Source Document Balanced
	 *
	 * @return true if (source) balanced
	 */
	private final boolean isBalanced()
	{
		// Multi-Currency documents are source balanced by definition
		if (isMultiCurrency())
		{
			return true;
		}
		//
		final boolean retValue = getBalance().signum() == 0;
		if (retValue)
		{
			log.debug("Yes - {}", this);
		}
		else
		{
			log.warn("NO - {}", this);
		}
		return retValue;
	}	// isBalanced

	/**
	 * Makes sure the document is convertible from it's currency to accounting currency.
	 */
	private final void checkConvertible(final AcctSchema acctSchema)
	{
		// No Currency in document
		final CurrencyId docCurrencyId = getCurrencyId();
		if (docCurrencyId == null)
		{
			return;
		}

		// Get All Currencies
		final Set<CurrencyId> currencyIds = new HashSet<>();
		currencyIds.add(docCurrencyId);

		final List<DocLineType> docLines = getDocLines();
		if (docLines != null)
		{
			for (final DocLineType docLine : docLines)
			{
				final CurrencyId currencyId = docLine.getCurrencyId();
				if (currencyId != null)
				{
					currencyIds.add(currencyId);
				}
			}
		}

		// Check
		final CurrencyId acctCurrencyId = acctSchema.getCurrencyId();
		for (final CurrencyId currencyId : currencyIds)
		{
			if (CurrencyId.equals(currencyId, acctCurrencyId))
			{
				continue;
			}

			final CurrencyConversionContext conversionCtx = services.createCurrencyConversionContext(
					getDateAcct(),
					getCurrencyConversionTypeId(),
					getClientId(),
					getOrgId());
			try
			{
				services.getCurrencyRate(conversionCtx, currencyId, acctCurrencyId);
			}
			catch (final NoCurrencyRateFoundException e)
			{
				throw newPostingException(e)
						.setAcctSchema(acctSchema)
						.setPostingStatus(PostingStatus.NotConvertible);
			}
		}
	}

	/**
	 * Calculate Period from DateAcct. m_C_Period_ID is set to -1 of not open to 0 if not found
	 */
	private final void setPeriod()
	{
		if (m_period != null)
		{
			return;
		}

		// Period defined in GL Journal (e.g. adjustment period)
		final int periodId = getValueAsIntOrZero("C_Period_ID");
		if (periodId > 0)
		{
			m_period = MPeriod.get(Env.getCtx(), periodId);
		}
		if (m_period == null)
		{
			m_period = MPeriod.get(Env.getCtx(), TimeUtil.asTimestamp(getDateAcct()), getOrgId().getRepoId());
		}

		// Is Period Open?
		if (m_period != null
				&& m_period.isOpen(getDocumentType(), TimeUtil.asTimestamp(getDateAcct()), getOrgId().getRepoId()))
		{
			m_C_Period_ID = m_period.getC_Period_ID();
		}
		else
		{
			m_C_Period_ID = -1;
		}
	}

	protected final int getC_Period_ID()
	{
		if (m_period == null)
		{
			setPeriod();
		}
		return m_C_Period_ID;
	}	// getC_Period_ID

	/**
	 * Is Period Open
	 *
	 * @return true if period is open
	 */
	private final boolean isPeriodOpen()
	{
		setPeriod();
		final boolean open = m_C_Period_ID > 0;
		if (open)
		{
			log.debug("Yes - " + toString());
		}
		else
		{
			log.warn("NO - " + toString());
		}
		return open;
	}	// isPeriodOpen

	/*************************************************************************/

	/** Amount Type - Invoice - Gross */
	public static final int AMTTYPE_Gross = 0;
	/** Amount Type - Invoice - Net */
	public static final int AMTTYPE_Net = 1;
	/** Amount Type - Invoice - Charge */
	public static final int AMTTYPE_Charge = 2;

	/** Source Amounts (may not all be used) */
	private final BigDecimal[] m_Amounts = new BigDecimal[] { BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO };

	/**
	 * Get the Amount (loaded in loadDocumentDetails)
	 *
	 * @param AmtType see AMTTYPE_*
	 * @return Amount
	 */
	protected final BigDecimal getAmount(final int AmtType)
	{
		if (AmtType < 0 || AmtType >= m_Amounts.length)
		{
			return null;
		}
		return m_Amounts[AmtType];
	}	// getAmount

	/**
	 * Set the Amount
	 *
	 * @param AmtType see AMTTYPE_*
	 * @param amt Amount
	 */
	protected final void setAmount(final int AmtType, final BigDecimal amt)
	{
		if (AmtType < 0 || AmtType >= m_Amounts.length)
		{
			return;
		}
		if (amt == null)
		{
			m_Amounts[AmtType] = BigDecimal.ZERO;
		}
		else
		{
			m_Amounts[AmtType] = amt;
		}
	}	// setAmount

	/**
	 * Get Amount with index 0
	 *
	 * @return Amount (primary document amount)
	 */
	protected final BigDecimal getAmount()
	{
		return m_Amounts[0];
	}   // getAmount

	@Nullable
	protected final AccountId getValidCombinationId(
			@NonNull final AccountType acctType,
			@NonNull final AcctSchema acctSchema)
	{
		final AcctSchemaId acctSchemaId = acctSchema.getId();

		final String sql;
		final List<Object> sqlParams;

		/** Account Type - Invoice */
		if (acctType == AccountType.Charge)	// see getChargeAccount in DocLine
		{
			final int cmp = getAmount(AMTTYPE_Charge).compareTo(BigDecimal.ZERO);
			if (cmp == 0)
			{
				return null;
			}
			else if (cmp < 0)
			{
				sql = "SELECT CH_Expense_Acct FROM C_Charge_Acct WHERE C_Charge_ID=? AND C_AcctSchema_ID=?";
			}
			else
			{
				sql = "SELECT CH_Revenue_Acct FROM C_Charge_Acct WHERE C_Charge_ID=? AND C_AcctSchema_ID=?";
			}
			sqlParams = Arrays.asList(getC_Charge_ID(), acctSchemaId);
		}
		else if (acctType == AccountType.V_Liability)
		{
			sql = "SELECT V_Liability_Acct FROM C_BP_Vendor_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getBPartnerId(), acctSchemaId);
		}
		else if (acctType == AccountType.V_Liability_Services)
		{
			sql = "SELECT V_Liability_Services_Acct FROM C_BP_Vendor_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getBPartnerId(), acctSchemaId);
		}
		else if (acctType == AccountType.C_Receivable)
		{
			sql = "SELECT C_Receivable_Acct FROM C_BP_Customer_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getBPartnerId(), acctSchemaId);
		}
		else if (acctType == AccountType.C_Receivable_Services)
		{
			sql = "SELECT C_Receivable_Services_Acct FROM C_BP_Customer_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getBPartnerId(), acctSchemaId);
		}
		else if (acctType == AccountType.V_Prepayment)
		{
			// metas: changed per Mark request: don't use prepayment account:
			log.warn("V_Prepayment account shall not be used", new Exception());
			sql = "SELECT V_Prepayment_Acct FROM C_BP_Vendor_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getBPartnerId(), acctSchemaId);
		}
		else if (acctType == AccountType.C_Prepayment)
		{
			// metas: changed per Mark request: don't use prepayment account:
			log.warn("C_Prepayment account shall not be used", new Exception());
			sql = "SELECT C_Prepayment_Acct FROM C_BP_Customer_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getBPartnerId(), acctSchemaId);
		}

		/** Account Type - Payment */
		else if (acctType == AccountType.UnallocatedCash)
		{
			return getBankAccountAcct(acctSchemaId).getUnallocatedCashAcct();
		}
		else if (acctType == AccountType.BankInTransit)
		{
			return getBankAccountAcct(acctSchemaId).getBankInTransitAcct();
		}
		else if (acctType == AccountType.PaymentSelect)
		{
			return getBankAccountAcct(acctSchemaId).getPaymentSelectAcct();
		}
		else if (acctType == AccountType.PayBankFee)
		{
			return getBankAccountAcct(acctSchemaId).getPaymentBankFeeAcct();
		}

		/** Account Type - Allocation */
		else if (acctType == AccountType.DiscountExp)
		{
			sql = "SELECT a.PayDiscount_Exp_Acct FROM C_BP_Group_Acct a, C_BPartner bp "
					+ "WHERE a.C_BP_Group_ID=bp.C_BP_Group_ID AND bp.C_BPartner_ID=? AND a.C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getBPartnerId(), acctSchemaId);
		}
		else if (acctType == AccountType.DiscountRev)
		{
			sql = "SELECT PayDiscount_Rev_Acct FROM C_BP_Group_Acct a, C_BPartner bp "
					+ "WHERE a.C_BP_Group_ID=bp.C_BP_Group_ID AND bp.C_BPartner_ID=? AND a.C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getBPartnerId(), acctSchemaId);
		}
		else if (acctType == AccountType.WriteOff)
		{
			sql = "SELECT WriteOff_Acct FROM C_BP_Group_Acct a, C_BPartner bp "
					+ "WHERE a.C_BP_Group_ID=bp.C_BP_Group_ID AND bp.C_BPartner_ID=? AND a.C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getBPartnerId(), acctSchemaId);
		}

		/** Account Type - Bank Statement */
		else if (acctType == AccountType.BankAsset)
		{
			return getBankAccountAcct(acctSchemaId).getBankAssetAcct();
		}
		else if (acctType == AccountType.InterestRev)
		{
			return getBankAccountAcct(acctSchemaId).getInterestRevenueAcct();
		}
		else if (acctType == AccountType.InterestExp)
		{
			return getBankAccountAcct(acctSchemaId).getInterestExpenseAcct();
		}

		/** Account Type - Cash */
		else if (acctType == AccountType.CashAsset)
		{
			sql = "SELECT CB_Asset_Acct FROM C_CashBook_Acct WHERE C_CashBook_ID=? AND C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getC_CashBook_ID(), acctSchemaId);
		}
		else if (acctType == AccountType.CashTransfer)
		{
			sql = "SELECT CB_CashTransfer_Acct FROM C_CashBook_Acct WHERE C_CashBook_ID=? AND C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getC_CashBook_ID(), acctSchemaId);
		}
		else if (acctType == AccountType.CashExpense)
		{
			sql = "SELECT CB_Expense_Acct FROM C_CashBook_Acct WHERE C_CashBook_ID=? AND C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getC_CashBook_ID(), acctSchemaId);
		}
		else if (acctType == AccountType.CashReceipt)
		{
			sql = "SELECT CB_Receipt_Acct FROM C_CashBook_Acct WHERE C_CashBook_ID=? AND C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getC_CashBook_ID(), acctSchemaId);
		}
		else if (acctType == AccountType.CashDifference)
		{
			sql = "SELECT CB_Differences_Acct FROM C_CashBook_Acct WHERE C_CashBook_ID=? AND C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getC_CashBook_ID(), acctSchemaId);
		}

		/** Inventory Accounts */
		else if (acctType == AccountType.InvDifferences)
		{
			sql = "SELECT W_Differences_Acct FROM M_Warehouse_Acct WHERE M_Warehouse_ID=? AND C_AcctSchema_ID=?";
			// "SELECT W_Inventory_Acct, W_Revaluation_Acct, W_InvActualAdjust_Acct FROM M_Warehouse_Acct WHERE M_Warehouse_ID=? AND C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getWarehouseId(), acctSchemaId);
		}
		else if (acctType == AccountType.NotInvoicedReceipts)
		{
			sql = "SELECT NotInvoicedReceipts_Acct FROM C_BP_Group_Acct a, C_BPartner bp "
					+ "WHERE a.C_BP_Group_ID=bp.C_BP_Group_ID AND bp.C_BPartner_ID=? AND a.C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getBPartnerId(), acctSchemaId);
		}

		/** Project Accounts */
		else if (acctType == AccountType.ProjectAsset)
		{
			sql = "SELECT PJ_Asset_Acct FROM C_Project_Acct WHERE C_Project_ID=? AND C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getC_Project_ID(), acctSchemaId);
		}
		else if (acctType == AccountType.ProjectWIP)
		{
			sql = "SELECT PJ_WIP_Acct FROM C_Project_Acct WHERE C_Project_ID=? AND C_AcctSchema_ID=?";
			sqlParams = Arrays.asList(getC_Project_ID(), acctSchemaId);
		}

		/** GL Accounts */
		else if (acctType == AccountType.PPVOffset)
		{
			return acctSchema.getGeneralLedger().getPurchasePriceVarianceOffsetAcctId();
		}
		else
		{
			throw newPostingException()
					.setAcctSchema(acctSchema)
					.setDetailMessage("Unknown AcctType=" + acctType);
		}

		// Get Acct
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final AccountId accountId = AccountId.ofRepoIdOrNull(rs.getInt(1));
				if (accountId == null)
				{
					log.warn("account ID not set for: account Type=" + acctType + ", Record=" + get_ID() + ", SQL=" + sql + ", sqlParams=" + sqlParams);
				}

				return accountId;
			}
			else
			{
				throw new AdempiereException(MSG_NoAccountFound, get_ID(), acctType)
						.markAsUserValidationError()
						.setParameter("sql", sql)
						.setParameter("sqlParams", sqlParams)
						.appendParametersToMessage();

			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}	// getAccount_ID

	private BankAccountAcct getBankAccountAcct(@NonNull final AcctSchemaId acctSchemaId)
	{
		final BankAccountId bankAccountId = getBPBankAccountId();
		if (bankAccountId == null)
		{
			throw newPostingException()
					.addDetailMessage("Bank Account is not set");
		}

		return services.getBankAccountAcct(bankAccountId, acctSchemaId);
	}

	/**
	 * Get the account for Accounting Schema
	 *
	 * @param acctType see AccountType.*
	 * @param acctSchema accounting schema
	 * @return Account or <code>null</code>
	 */
	protected final MAccount getAccount(final AccountType acctType, final AcctSchema acctSchema)
	{
		final AccountId accountId = getValidCombinationId(acctType, acctSchema);
		if (accountId == null)
		{
			return null;
		}

		return services.getAccountById(accountId);
	}	// getAccount

	protected final MAccount getRealizedGainAcct(final AcctSchema as)
	{
		return services.getAccountById(as.getDefaultAccounts().getRealizedGainAcctId());
	}

	protected final MAccount getRealizedLossAcct(final AcctSchema as)
	{
		return services.getAccountById(as.getDefaultAccounts().getRealizedLossAcctId());
	}

	@Override
	public String toString()
	{
		return getPO().toString();
	}

	protected final ClientId getClientId()
	{
		return ClientId.ofRepoId(getPO().getAD_Client_ID());
	}

	protected final OrgId getOrgId()
	{
		return OrgId.ofRepoId(getPO().getAD_Org_ID());
	}

	protected String getDocumentNo()
	{
		if (m_DocumentNo == null)
		{
			m_DocumentNo = CoalesceUtil.coalesceSuppliers(
					() -> getValueAsString("DocumentNo"),
					() -> getValueAsString("Name"),
					() -> {
						throw new AdempiereException("DocumentNo not found");
					});
		}
		return m_DocumentNo;
	}

	protected final String getDocStatus()
	{
		return m_DocStatus;
	}

	protected final String getDescription()
	{
		if (m_Description == null)
		{
			m_Description = getValueAsString("Description");
			if (m_Description == null)
			{
				m_Description = "";
			}
		}
		return m_Description;
	}

	protected final CurrencyId getCurrencyId()
	{
		if (_currencyId == null)
		{
			_currencyId = getValueAsOptionalId("C_Currency_ID", CurrencyId::ofRepoIdOrNull);
		}

		return _currencyId.orElse(null);
	}

	protected final void setC_Currency_ID(final CurrencyId currencyId)
	{
		_currencyId = Optional.ofNullable(currencyId);
		_currencyPrecision = null;
	}

	protected final void setNoCurrency()
	{
		final CurrencyId currencyId = null;
		setC_Currency_ID(currencyId);
	}

	protected final boolean isMultiCurrency()
	{
		return m_MultiCurrency;
	}

	protected final void setIsMultiCurrency(final boolean mc)
	{
		m_MultiCurrency = mc;
	}

	protected final CurrencyConversionTypeId getCurrencyConversionTypeId()
	{
		return CurrencyConversionTypeId.ofRepoIdOrNull(getValueAsIntOrZero("C_ConversionType_ID"));
	}

	public final int getStdPrecision()
	{
		if (_currencyPrecision != null)
		{
			return _currencyPrecision.toInt();
		}

		final CurrencyId currencyId = getCurrencyId();
		if (currencyId == null)
		{
			return ICurrencyDAO.DEFAULT_PRECISION.toInt();
		}

		_currencyPrecision = services.getCurrencyStandardPrecision(currencyId);
		return _currencyPrecision.toInt();
	}

	protected final int getGL_Category_ID()
	{
		return m_GL_Category_ID;
	}

	protected final int getGL_Budget_ID()
	{
		return getValueAsIntOrZero("GL_Budget_ID");
	}

	protected final LocalDate getDateAcct()
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> _dateAcct,
				() -> getValueAsLocalDateOrNull("DateAcct"),
				() -> {
					throw new AdempiereException("No DateAcct");
				});
	}

	protected final void setDateAcct(final Timestamp dateAcct)
	{
		setDateAcct(TimeUtil.asLocalDate(dateAcct));
	}

	protected final void setDateAcct(final LocalDate dateAcct)
	{
		_dateAcct = dateAcct;
	}

	protected final LocalDate getDateDoc()
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> _dateDoc,
				() -> getValueAsLocalDateOrNull("DateDoc"),
				() -> getValueAsLocalDateOrNull("MovementDate"),
				() -> {
					throw new AdempiereException("No DateDoc");
				});
	}

	protected final void setDateDoc(final Timestamp dateDoc)
	{
		setDateDoc(TimeUtil.asLocalDate(dateDoc));
	}

	protected final void setDateDoc(final LocalDate dateDoc)
	{
		_dateDoc = dateDoc;
	}

	private final boolean isPosted()
	{
		final Boolean posted = getValueAsBoolean("Posted", null);
		if (posted == null)
		{
			throw new AdempiereException("Posted column is missing or it's null");
		}
		return posted;
	}

	public final boolean isSOTrx()
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> getValueAsBoolean("IsSOTrx", null),
				() -> getValueAsBoolean("IsReceipt", null),
				() -> SOTrx.PURCHASE.toBoolean());
	}

	protected final int getC_DocType_ID()
	{
		final int docTypeId = getValueAsIntOrZero("C_DocType_ID");
		if (docTypeId > 0)
		{
			return docTypeId;
		}

		// fallback
		final int docTypeTargetId = getValueAsIntOrZero("C_DocTypeTarget_ID");
		return docTypeTargetId;
	}

	protected final int getC_Charge_ID()
	{
		return getValueAsIntOrZero("C_Charge_ID");
	}

	protected final UserId getSalesRepId()
	{
		return getValueAsIdOrNull("SalesRep_ID", UserId::ofRepoIdOrNull);
	}

	/**
	 * Get C_BP_BankAccount_ID if it was previously set using {@link #setBPBankAccountId(BankAccountId)}, or attempts to get it from our <code>p_po</code> (document record).
	 */
	final BankAccountId getBPBankAccountId()
	{
		Optional<BankAccountId> bankAccountId = _bankAccountId;
		if (bankAccountId == null)
		{
			bankAccountId = _bankAccountId = getValueAsOptionalId(
					I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID,
					BankAccountId::ofRepoIdOrNull);
		}

		return bankAccountId.orElse(null);
	}

	final void setBPBankAccountId(@Nullable final BankAccountId bankAccountId)
	{
		_bankAccountId = Optional.ofNullable(bankAccountId);
	}

	@Nullable
	protected final BankAccount getBankAccount()
	{
		final BankAccountId bankAccountId = getBPBankAccountId();
		if (bankAccountId == null)
		{
			return null;
		}
		if (bankAccount == null || !BankAccountId.equals(bankAccount.getId(), bankAccountId))
		{
			bankAccount = services.getBankAccountById(bankAccountId);
		}
		return bankAccount;
	}

	protected final int getC_CashBook_ID()
	{
		if (m_C_CashBook_ID == -1)
		{
			m_C_CashBook_ID = getValueAsIntOrZero("C_CashBook_ID");
			if (m_C_CashBook_ID <= 0)
			{
				m_C_CashBook_ID = 0;
			}
		}
		return m_C_CashBook_ID;
	}

	protected final void setC_CashBook_ID(final int C_CashBook_ID)
	{
		m_C_CashBook_ID = C_CashBook_ID;
	}

	protected final WarehouseId getWarehouseId()
	{
		return getValueAsIdOrNull("M_Warehouse_ID", WarehouseId::ofRepoIdOrNull);
	}

	/**
	 * Get C_BPartner_ID
	 *
	 * @return BPartner
	 */
	protected final BPartnerId getBPartnerId()
	{
		if (_bpartnerId == null)
		{
			_bpartnerId = getValueAsOptionalId("C_BPartner_ID", BPartnerId::ofRepoIdOrNull);
		}
		return _bpartnerId.orElse(null);
	}

	protected final void setBPartnerId(final BPartnerId bpartnerId)
	{
		_bpartnerId = Optional.ofNullable(bpartnerId);
	}

	protected final int getC_BPartner_Location_ID()
	{
		return getValueAsIntOrZero("C_BPartner_Location_ID");
	}

	protected final int getC_Project_ID()
	{
		return getValueAsIntOrZero("C_Project_ID");
	}

	protected final int getC_SalesRegion_ID()
	{
		return getValueAsIntOrZero("C_SalesRegion_ID");
	}

	protected final int getBP_C_SalesRegion_ID()
	{
		if (m_BP_C_SalesRegion_ID == -1)
		{
			m_BP_C_SalesRegion_ID = getC_SalesRegion_ID();
			if (m_BP_C_SalesRegion_ID <= 0)
			{
				m_BP_C_SalesRegion_ID = 0;
			}
		}
		return m_BP_C_SalesRegion_ID;
	}

	/**
	 * Set BPartner's C_SalesRegion_ID
	 */
	protected final void setBP_C_SalesRegion_ID(final int C_SalesRegion_ID)
	{
		m_BP_C_SalesRegion_ID = C_SalesRegion_ID;
	}

	protected final ActivityId getActivityId()
	{
		return ActivityId.ofRepoIdOrNull(getValueAsIntOrZero("C_Activity_ID"));
	}

	protected final int getC_Campaign_ID()
	{
		return getValueAsIntOrZero("C_Campaign_ID");
	}

	protected final ProductId getProductId()
	{
		return getValueAsIdOrNull("M_Product_ID", ProductId::ofRepoIdOrNull);
	}

	protected final OrgId getOrgTrxId()
	{
		return getValueAsIdOrNull("AD_OrgTrx_ID", OrgId::ofRepoIdOrNull);
	}

	protected final LocationId getLocationFromId()
	{
		return locationFromId;
	}

	protected final LocationId getLocationToId()
	{
		return locationToId;
	}

	protected final int getUser1_ID()
	{
		return getValueAsIntOrZero("User1_ID");
	}

	protected final int getUser2_ID()
	{
		return getValueAsIntOrZero("User2_ID");
	}

	protected final int getValueAsIntOrZero(final String ColumnName)
	{
		final PO po = getPO();
		final int index = po.get_ColumnIndex(ColumnName);
		if (index != -1)
		{
			final Integer ii = (Integer)po.get_Value(index);
			if (ii != null)
			{
				return ii.intValue();
			}
		}
		return 0;
	}

	private final <T extends RepoIdAware> T getValueAsIdOrNull(final String columnName, final IntFunction<T> idOrNullMapper)
	{
		final PO po = getPO();
		final int index = po.get_ColumnIndex(columnName);
		if (index < 0)
		{
			return null;
		}

		final Object valueObj = po.get_Value(index);
		final Integer valueInt = NumberUtils.asInteger(valueObj, null);
		if (valueInt == null)
		{
			return null;
		}

		final T id = idOrNullMapper.apply(valueInt);
		return id;
	}

	private final <T extends RepoIdAware> Optional<T> getValueAsOptionalId(final String columnName, final IntFunction<T> idOrNullMapper)
	{
		final T id = getValueAsIdOrNull(columnName, idOrNullMapper);
		return Optional.ofNullable(id);
	}

	private final LocalDate getValueAsLocalDateOrNull(final String columnName)
	{
		final PO po = getPO();
		final int index = po.get_ColumnIndex(columnName);
		if (index != -1)
		{
			return TimeUtil.asLocalDate(po.get_Value(index));
		}

		return null;
	}

	private final Boolean getValueAsBoolean(final String columnName, final Boolean defaultValue)
	{
		final PO po = getPO();
		final int index = po.get_ColumnIndex(columnName);
		if (index != -1)
		{
			final Object valueObj = po.get_Value(index);
			return DisplayType.toBoolean(valueObj, defaultValue);
		}

		return defaultValue;
	}

	private final String getValueAsString(final String columnName)
	{
		final PO po = getPO();
		final int index = po.get_ColumnIndex(columnName);
		if (index != -1)
		{
			final Object valueObj = po.get_Value(index);
			return valueObj != null ? valueObj.toString() : null;
		}

		return null;
	}

	/**
	 * Load Document Details
	 */
	protected abstract void loadDocumentDetails();

	/**
	 * Get Source Currency Balance - subtracts line (and tax) amounts from total - no rounding
	 *
	 * @return positive amount, if total header is bigger than lines
	 */
	protected abstract BigDecimal getBalance();

	/**
	 * Create Facts (the accounting logic)
	 *
	 * @param as accounting schema
	 * @return Facts
	 */
	protected abstract List<Fact> createFacts(final AcctSchema as);

	/**
	 * Method called after everything was Posted and saved to database, right before committing.
	 */
	protected void afterPost()
	{
		// nothing on this level
	}

	protected final PostingException newPostingException()
	{
		final Throwable e = null;
		return newPostingException(e);
	}

	protected final PostingException newPostingException(final Throwable e)
	{
		final PostingException postingException;
		if (e == null)
		{
			postingException = new PostingException(this);
		}
		else if (e instanceof PostingException)
		{
			postingException = (PostingException)e;
		}
		else
		{
			postingException = new PostingException(this, e)
					.setPostingStatus(PostingStatus.Error);
		}

		if (isPosted())
		{
			postingException.setPreserveDocumentPostedStatus();
		}

		return postingException;
	}

	private final void createErrorNote(final PostingException ex)
	{
		DB.saveConstraints();
		try
		{
			DB.getConstraints().setOnlyAllowedTrxNamePrefixes(false).incMaxTrx(1);

			final PostingStatus postingStatus = ex.getPostingStatus(PostingStatus.Error);
			final AdMessageKey AD_MessageValue = postingStatus.getAD_Message();
			final PO po = getPO();
			final int AD_User_ID = po.getUpdatedBy();
			final Properties ctx = Env.getCtx();
			final String adLanguage = Env.getAD_Language(ctx);

			final MNote note = new MNote(
					ctx,
					AD_MessageValue.toAD_Message(),
					AD_User_ID,
					getClientId().getRepoId(),
					getOrgId().getRepoId(),
					ITrx.TRXNAME_None);
			note.setRecord(po.get_Table_ID(), po.get_ID());
			note.setReference(toString());	// Document

			final StringBuilder text = new StringBuilder();
			text.append(services.translate(AD_MessageValue).translate(adLanguage));
			final String p_Error = ex.getDetailMessage().translate(adLanguage);
			if (!Check.isEmpty(p_Error, true))
			{
				text.append(" (").append(p_Error).append(")");
			}

			text.append(" - ").append(getClass().getSimpleName());
			final boolean loaded = getDocLines() != null;
			if (loaded)
			{
				text.append(" (").append(getDocumentType())
						.append(" - DocumentNo=").append(getDocumentNo())
						.append(", DateAcct=").append(getDateAcct())
						.append(", Amount=").append(getAmount())
						.append(", Sta=").append(postingStatus)
						.append(" - PeriodOpen=").append(isPeriodOpen())
						.append(", Balanced=").append(isBalanced())
						.append(")");
			}
			note.setTextMsg(text.toString());
			note.save();
			// p_Error = Text.toString();
		}
		catch (final Exception noteEx)
		{
			log.warn("Failed to create the error note. Skipped", noteEx);
		}
		finally
		{
			DB.restoreConstraints();
		}
	}

	/**
	 * Post immediate given list of documents.
	 *
	 * IMPORTANT: This method won't fail if any of the documents's posting is failing, because we don't want to prevent the main document posting because of this.
	 */
	protected final <ID extends RepoIdAware> void postDependingDocuments(
			final String tableName,
			final Collection<ID> documentIds)
	{
		if (documentIds == null || documentIds.isEmpty())
		{
			return; // nothing to do
		}

		// task 08643: the list of documentModels might originate from a bag (i.e. InArrayFilter does not filter at all when given an empty set of values).
		// so we assume that if there are >=200 items, it's that bug and there are not really that many documentModels.
		// Note: we fixed the issue in the method's current callers (Doc_InOut and Doc_Invoice).
		if (documentIds.size() >= 200)
		{
			final PostingException ex = newPostingException()
					.setDocument(this)
					.setDetailMessage("There are too many depending document models to post. This might be a problem in filtering (legacy-bug in InArrayFilter).");
			log.warn("Got to many depending documents to post. Skip posting depending documents.", ex);
			return;
		}

		final ClientId clientId = getClientId();
		for (final ID documentId : documentIds)
		{
			final TableRecordReference documentRef = TableRecordReference.of(tableName, documentId);
			services.postImmediateNoFail(documentRef, clientId);
		}
	}
}   // Doc
