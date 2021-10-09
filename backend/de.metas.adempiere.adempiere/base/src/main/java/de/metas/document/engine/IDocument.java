package de.metas.document.engine;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Properties;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import de.metas.util.Services;

import javax.annotation.Nullable;

/**
 * Document Interface.
 * <p>
 * Important: if you want to create new documents, then please don't subclass auto-generated {@code X_*} classes (such as was done with {@code MOrder} or {@code MInvoice}),
 * but rather implement {@link DocumentHandler}.
 *
 * @author based on initial version of Jorg Janke
 * @author metas-dev <dev@metasfresh.com>
 */
public interface IDocument
{
	static IDocument cast(final Object documentObj)
	{
		return (IDocument)documentObj;
	}

	String ACTION_Complete = "CO";
	String ACTION_WaitComplete = "WC";
	String ACTION_Approve = "AP";
	String ACTION_Reject = "RJ";
	String ACTION_Post = "PO";
	String ACTION_UnPost = "UP"; // NOTE: this action is not in database
	String ACTION_Void = "VO";
	String ACTION_Close = "CL";
	String ACTION_UnClose = "UC";
	String ACTION_Reverse_Correct = "RC";
	String ACTION_Reverse_Accrual = "RA";
	String ACTION_ReActivate = "RE";
	String ACTION_None = "--";
	String ACTION_Prepare = "PR";
	String ACTION_Unlock = "XL";
	String ACTION_Invalidate = "IN";

	String STATUS_Drafted = "DR";
	String STATUS_Completed = "CO";
	String STATUS_Approved = "AP";
	String STATUS_Invalid = "IN";
	String STATUS_NotApproved = "NA";
	String STATUS_Voided = "VO";
	String STATUS_Reversed = "RE";
	String STATUS_Closed = "CL";
	String STATUS_Unknown = "??";
	String STATUS_InProgress = "IP";
	String STATUS_WaitingPayment = "WP";
	String STATUS_WaitingConfirmation = "WC";

	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Constant value for the Reversal_ID column. Note that we don't call it {@code COLUMNNAME_Reversal_ID} to avoid a compiler error saying
	 *
	 * <pre>
	 * reference to COLUMNNAME_Reversal_ID is ambiguous
	 * </pre>
	 *
	 * in (legacy) sub classes of this class.
	 *
	 */
	String Reversal_ID = "Reversal_ID";

	@Nullable
	String getProcessMsg();

	/** @return true if success */
	default boolean processIt(final String docAction)
	{
		return Services.get(IDocumentBL.class).processIt(this, docAction);
	}

	/** @return true if success */
	boolean unlockIt();

	/** @return true if success */
	boolean invalidateIt();

	/** @return new status (In Progress or Invalid) */
	String prepareIt();

	/** @return true if success */
	boolean approveIt();

	/** @return true if success */
	boolean rejectIt();

	/** @return new status (Complete, In Progress, Invalid, Waiting ..) */
	String completeIt();

	/** @return true if success */
	boolean voidIt();

	/** @return true if success */
	boolean closeIt();

	default void unCloseIt()
	{
		throw new UnsupportedOperationException();
	}

	/** @return true if success */
	boolean reverseCorrectIt();

	/** @return true if success */
	boolean reverseAccrualIt();

	/** @return true if success */
	boolean reActivateIt();

	File createPDF();

	/** @return Summary of Document */
	String getSummary();

	/** @return Document No */
	String getDocumentNo();

	/** @return Type and Document No */
	String getDocumentInfo();

	int getDoc_User_ID();

	int getC_Currency_ID();

	BigDecimal getApprovalAmt();

	int getAD_Client_ID();

	int getAD_Org_ID();

	boolean isActive();

	void setDocStatus(String newStatus);

	String getDocStatus();

	String getDocAction();

	LocalDate getDocumentDate();

	Properties getCtx();

	int get_ID();

	int get_Table_ID();

	Logger get_Logger();

	/** @return true if saved */
	boolean save();

	String get_TrxName();

	void set_TrxName(String trxName);

	default TableRecordReference toTableRecordReference()
	{
		return TableRecordReference.of(get_Table_ID(), get_ID());
	}

	/** 
	 * We use this constant in {@link org.adempiere.ad.wrapper.POJOWrapper}. 
	 * Please keep it in sync with {@link #getDocumentModel()}. 
	 */
	String METHOD_NAME_getDocumentModel = "getDocumentModel";

	default Object getDocumentModel()
	{
		return this;
	}
}
