package org.compiere.model;

import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.api.ITrxManager;

import com.google.common.collect.ImmutableMap;

import javax.annotation.Nullable;

/**
 * Model Validator
 *
 * @author Jorg Janke
 * @version $Id: ModelValidator.java,v 1.2 2006/07/30 00:58:18 jjanke Exp $
 *
 *          2007/02/26 laydasalasc - globalqss - Add new timings for all before/after events on documents
 */
public interface ModelValidator
{
	/** Model Change Type New */
	int TYPE_BEFORE_NEW = 1;			// teo_sarca [ 1675490 ]
	int TYPE_AFTER_NEW = 4;			// teo_sarca [ 1675490 ]
	int TYPE_AFTER_NEW_REPLICATION = 7;	// @Trifon
	/** Model Change Type Change */
	int TYPE_BEFORE_CHANGE = 2;		// teo_sarca [ 1675490 ]
	int TYPE_AFTER_CHANGE = 5;			// teo_sarca [ 1675490 ]
	int TYPE_AFTER_CHANGE_REPLICATION = 8; // @Trifon
	/** Model Change Type Delete */
	int TYPE_BEFORE_DELETE = 3;		// teo_sarca [ 1675490 ]
	int TYPE_AFTER_DELETE = 6;			// teo_sarca [ 1675490 ]
	int TYPE_BEFORE_DELETE_REPLICATION = 9; // @Trifon

	/**
	 * When saving a PO, in case we create a local transaction, fire first TYPE_BEFORE_SAVE_TRX event we trigger the event only if was not started in other place, like in a process for example.
	 *
	 * Task 02380
	 */
	int TYPE_BEFORE_SAVE_TRX = 1000 + 1; // metas: tsa: 02380

	/**
	 * <ul>
	 * <li>Subsequent processing takes place after the po has basically been saved validated and saved</li>
	 * <li>This processing can take place immediately after the actual processing, but also later on (e.g. batch-processing on the server)</li>
	 * <li>If the model validator's modelChange() method throws an exception or returns a string, an AD_Issue is created</li>
	 * </ul>
	 *
	 * @deprecated if you want the code invoked right after the PO is saved, then please use {@link ITrxManager#getTrxListenerManagerOrAutoCommit(String)} to get a
	 *             {@link ITrxListenerManager} and then call {@link ITrxListenerManager#registerListener(ITrxListenerManager.RegisterListenerRequest)} to
	 *             register a listener with an <code>afterCommit()</code> implementation.
	 *             <p>
	 *             If you want the code invoked later, then please use async.
	 */
	@Deprecated int TYPE_SUBSEQUENT = 10; // metas-ts 0176

	// Correlation between constant events and list of event script model validators
	String[] tableEventValidators = new String[] {
			"", // 0
			X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_TableBeforeNew,    // TYPE_BEFORE_NEW = 1
			X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_TableBeforeChange, // TYPE_BEFORE_CHANGE = 2
			X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_TableBeforeDelete, // TYPE_BEFORE_DELETE = 3
			X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_TableAfterNew,     // TYPE_AFTER_NEW = 4
			X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_TableAfterChange,  // TYPE_AFTER_CHANGE = 5
			X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_TableAfterDelete,   // TYPE_AFTER_DELETE = 6
			X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_TableAfterNewReplication,     // TYPE_AFTER_NEW_REPLICATION = 7
			X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_TableAfterChangeReplication,  // TYPE_AFTER_CHANGE_REPLICATION = 8
			X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_TableBeforeDeleteReplication,   // TYPE_BEFORE_DELETE_REPLICATION = 9
			X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_TableSubsequentProcessing   // TYPE_SUBSEQUENT = 10
	};

	int DOCTIMING_Offset = 1000000; // NOTE: we are offsetting the TIMINGS because we don't want to collide with TYPE_* values
	/** Called before document is prepared */
	int TIMING_BEFORE_PREPARE = DOCTIMING_Offset + 1;
	/** Called before document is void */
	int TIMING_BEFORE_VOID = DOCTIMING_Offset + 2;
	/** Called before document is close */
	int TIMING_BEFORE_CLOSE = DOCTIMING_Offset + 3;
	/** Called before document is reactivate */
	int TIMING_BEFORE_REACTIVATE = DOCTIMING_Offset + 4;
	/** Called before document is reversecorrect */
	int TIMING_BEFORE_REVERSECORRECT = DOCTIMING_Offset + 5;
	/** Called before document is reverseaccrual */
	int TIMING_BEFORE_REVERSEACCRUAL = DOCTIMING_Offset + 6;
	/** Called before document is completed */
	int TIMING_BEFORE_COMPLETE = DOCTIMING_Offset + 7;
	/** Called after document is prepared */
	int TIMING_AFTER_PREPARE = DOCTIMING_Offset + 8;
	/** Called after document is completed */
	int TIMING_AFTER_COMPLETE = DOCTIMING_Offset + 9;
	/** Called after document is void */
	int TIMING_AFTER_VOID = DOCTIMING_Offset + 10;
	/** Called after document is closed */
	int TIMING_AFTER_CLOSE = DOCTIMING_Offset + 11;
	/** Called after document is reactivated */
	int TIMING_AFTER_REACTIVATE = DOCTIMING_Offset + 12;
	/** Called after document is reverse-correct */
	int TIMING_AFTER_REVERSECORRECT = DOCTIMING_Offset + 13;
	/** Called after document is reverse-accrual */
	int TIMING_AFTER_REVERSEACCRUAL = DOCTIMING_Offset + 14;

	/** Called after document is un-posted */
	int TIMING_AFTER_UNPOST = DOCTIMING_Offset + 16;

	/** Called before document is posted */
	int TIMING_BEFORE_POST = DOCTIMING_Offset + 17;
	/** Called after document is posted */
	int TIMING_AFTER_POST = DOCTIMING_Offset + 18;

	/** Called before document is un-closed */
	int TIMING_BEFORE_UNCLOSE = DOCTIMING_Offset + 19;
	/** Called after document is un-closed */
	int TIMING_AFTER_UNCLOSE = DOCTIMING_Offset + 20;

	// Correlation between constant events and list of event script model validators
	ImmutableMap<DocTimingType, String> documentEventValidators = ImmutableMap.<DocTimingType, String> builder()
			//.put(0, "") // legacy
			.put(DocTimingType.BEFORE_PREPARE, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentBeforePrepare)
			.put(DocTimingType.BEFORE_VOID, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentBeforeVoid)
			.put(DocTimingType.BEFORE_CLOSE, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentBeforeClose)
			.put(DocTimingType.BEFORE_UNCLOSE, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentBeforeUnClose)
			.put(DocTimingType.BEFORE_REACTIVATE, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentBeforeReactivate)
			.put(DocTimingType.BEFORE_REVERSECORRECT, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentBeforeReverseCorrect)
			.put(DocTimingType.BEFORE_REVERSEACCRUAL, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentBeforeReverseAccrual)
			.put(DocTimingType.BEFORE_COMPLETE, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentBeforeComplete)
			.put(DocTimingType.AFTER_PREPARE, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentAfterPrepare)
			.put(DocTimingType.AFTER_COMPLETE, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentAfterComplete)
			.put(DocTimingType.AFTER_VOID, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentAfterVoid)
			.put(DocTimingType.AFTER_CLOSE, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentAfterClose)
			.put(DocTimingType.AFTER_UNCLOSE, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentAfterUnClose)
			.put(DocTimingType.AFTER_REACTIVATE, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentAfterReactivate)
			.put(DocTimingType.AFTER_REVERSECORRECT, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentAfterReverseCorrect)
			.put(DocTimingType.AFTER_REVERSEACCRUAL, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentAfterReverseAccrual)
			.put(DocTimingType.BEFORE_POST, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentBeforePost)
			.put(DocTimingType.AFTER_POST, X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentAfterPost)
			.build();

	/**
	 * Initialize Validation
	 *
	 * @param engine validation engine
	 * @param client client
	 */
	void initialize(ModelValidationEngine engine, @Nullable MClient client);

	/**
	 * Get Client to be monitored
	 *
	 * @return AD_Client_ID
	 */
	int getAD_Client_ID();

	/**
	 * User logged in
	 * Called before preferences are set
	 *
	 * @param AD_Org_ID org
	 * @param AD_Role_ID role
	 * @param AD_User_ID user
	 * @return error message or null
	 */
	@Nullable
	String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID);

	/**
	 * Model Change of a monitored Table.
	 * Called after PO.beforeSave/PO.beforeDelete
	 * when you called addModelChange for the table
	 *
	 * @param po persistent object
	 * @param type TYPE_
	 * @return error message or null
	 * @exception Exception if the recipient wishes the change to be not accept.
	 */
	@Nullable
	String modelChange(PO po, int type) throws Exception;

	/**
	 * Validate Document.
	 * Called as first step of DocAction.prepareIt
	 * or at the end of DocAction.completeIt
	 * when you called addDocValidate for the table.
	 * Note that totals, etc. may not be correct before the prepare stage.
	 *
	 * @param po persistent object
	 * @param timing see TIMING_ constants
	 * @return error message or null -
	 *         if not null, the document will be marked as Invalid.
	 */
	@Nullable
	String docValidate(PO po, int timing) throws Exception;

}	// ModelValidator
