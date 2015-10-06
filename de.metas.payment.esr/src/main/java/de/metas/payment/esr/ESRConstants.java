package de.metas.payment.esr;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.payment.esr.spi.impl.WithNextInvoiceESRActionHandler;

/**
 * Constant values for ESR.
 * 
 * @author RC
 *
 */
public final class ESRConstants
{
	public final static String ENTITYTYPE = "de.metas.payment.esr";
	private final static String SYSCONFIG_Enabled = "de.metas.payment.esr.Enabled"; // i.e. <ENTITYTYPE>.Enabled

	public final static String DOCUMENT_REFID_ReferenceNo_Type_ReferenceNumber = "ESRReferenceNumber";

	public final static String DOCUMENT_REFID_ReferenceNo_Type_InvoiceReferenceNumber = "InvoiceReference";

	public static final String ESRTRXTYPE_Payment = "995";

	public static final String ESRTRXTYPE_Receipt = "999";

	public static final List<String> ESRTRXTYPES_Control = Arrays.asList(ESRTRXTYPE_Payment, ESRTRXTYPE_Receipt);

	public static final String ESRTRXTYPE_CREDIT_MEMO_LAST_DIGIT = "2";

	public static final String ESRTRXTYPE_REVERSE_LAST_DIGIT = "5";

	public static final String ESRTRXTYPE_CORRECTION_LAST_DIGIT = "8";

	/**
	 * <code>AD_Message</code> value for the error message to be used when an ESR line has no selected action.
	 */
	public static final String ERR_ESR_LINE_WITH_NO_PAYMENT_ACTION = "de.metas.payment.esr.LineWithNoPaymentAction";

	public static final String SYSCONFIG_PreventDuplicateImportFiles = "de.metas.payment.esr.PreventDuplicateImportFiles";

	/**
	 * Y/N parameter. If <code>false</code>, then <code>C_Payment.IsAutoAllocateAvailableAmt</code> is <b>only</b> set to <code>Y</code> if an ESR-Line is handled by the
	 * {@link WithNextInvoiceESRActionHandler}.
	 * 
	 * @task http://dewiki908/mediawiki/index.php/09167_System_generiert_manchmal_generiert_falsche_ESR_%28105649640837%29
	 */
	public static final String SYSCONFIG_EAGER_PAYMENT_ALLOCATION = "de.metas.payment.esr.AlwaysAutoAllocateAvailablePayAmount";

	public static final String ASK_PreventDuplicateImportFiles = "de.metas.payment.esr.PreventDuplicatesWarning";

	public static final String WARN_PreventDuplicateImportFilesEntirely = "de.metas.payment.esr.PreventDuplicatesEtirely";

	public static final String ESR_DIFF_INV_PARTNER = "ESR_Diff_Inv_partner";

	public static final String ESR_DIFF_PAYMENT_PARTNER = "ESR_Diff_Payment_partner";

	private ESRConstants()
	{
	}

	/**
	 * String is required to compute the check digit. Check out the ESR specification for details. A german specification (current version 01.07) can be found here:<br>
	 * http://www.tkb.ch/fk/produkte_dienste/zv/zv_klassisch/besr_handbuch.htm
	 * 
	 * @see #calculateCheckDigit(String, int, int)
	 */
	public static int[] CHECK_String = { 0, 9, 4, 6, 8, 2, 7, 1, 3, 5 };

	public static final String C_Async_Batch_InternalName = "ESRWizard";

	/**
	 * @return true if ESR module is enabled
	 */
	public static final boolean isEnabled(final Properties ctx)
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_Enabled,
				true, // defaultValue
				Env.getAD_Client_ID(ctx) // AD_Client_ID
				);
	}

	/**
	 * Sets (and persist in database) if the ESR module shall be enabled.
	 */
	public static final void setEnabled(final Properties ctx, final boolean enabled)
	{
		final int AD_Org_ID = Env.CTXVALUE_AD_Org_ID_System; // all orgs
		Services.get(ISysConfigBL.class).setValue(SYSCONFIG_Enabled, enabled, AD_Org_ID);
	}

}
