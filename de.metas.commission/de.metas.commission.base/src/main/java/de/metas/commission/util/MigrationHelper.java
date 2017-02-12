/**
 * 
 */
package de.metas.commission.util;

/*
 * #%L
 * de.metas.commission.base
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


import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Constants;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_RelationType;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MDocType;
import org.compiere.model.MOrg;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.model.X_AD_SysConfig;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.document.IDocumentPA;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;

/**
 * @author tsa
 * 
 */
public class MigrationHelper
{
	private static final int AD_REFERENCE_Reltype_Invoice = 540192;

	private static final int AD_Reference_ID_C_InvoiceLine = 540194; // C_InvoiceLine Commission Calc

	// private static final int AD_WINDOW_ID_COMM_CALC = 540035;
	//
	// private static final String ENTITY_TYPE = "de.metas.swat";

	public static final String DE_METAS_COMMISSION_VERSION = "de.metas.commission.Version";

	private final Logger log = LogManager.getLogger(getClass());

	private final Properties ctx;
	private final String trxName;
	private boolean isTest;

	private JavaProcess logger;

	public MigrationHelper(final Properties ctx, final String trxName)
	{
		this.ctx = ctx;
		this.trxName = trxName;
	}

	public void setIsTest(final boolean isTest)
	{
		this.isTest = isTest;
	}

	public boolean isTest()
	{
		return isTest;
	}

	public void setLogger(final JavaProcess logger)
	{
		this.logger = logger;
	}

	public void migrate()
	{
		final int version = getVersion();
		if (version < 1)
		{
			migrate_v1();
			setVersion(1);
		}
	}

	public Properties getCtx()
	{
		return ctx;
	}

	public String getTrxName()
	{
		return trxName;
	}

	public int getVersion()
	{
		final I_AD_SysConfig cfg = getSysConfigVersion();
		String value = cfg.getValue();
		if (Check.isEmpty(value, true))
		{
			return 0;
		}
		value = value.trim();
		int version = 0;
		try
		{
			version = Integer.parseInt(value);
		}
		catch (final NumberFormatException e)
		{
			log.warn("Cannot parse int for " + value + ". Considering zero", e);
			version = 0;
		}

		return version;
	}

	public void setVersion(final int version)
	{
		final I_AD_SysConfig cfg = getSysConfigVersion();
		cfg.setValue("" + version);
		InterfaceWrapperHelper.save(cfg);
	}

	private I_AD_SysConfig getSysConfigVersion()
	{
		final String whereClause = I_AD_SysConfig.COLUMNNAME_Name + "=?"
				+ " AND " + I_AD_SysConfig.COLUMNNAME_AD_Client_ID + "=?"
				+ " AND " + I_AD_SysConfig.COLUMNNAME_AD_Org_ID + "=?";
		I_AD_SysConfig cfg = new Query(getCtx(), I_AD_SysConfig.Table_Name, whereClause, getTrxName())
				.setParameters(MigrationHelper.DE_METAS_COMMISSION_VERSION, Env.getAD_Client_ID(getCtx()), 0)
				.firstOnly(I_AD_SysConfig.class);
		if (cfg == null)
		{
			cfg = new MSysConfig(getCtx(), 0, getTrxName());
			cfg.setAD_Org_ID(0);
			cfg.setConfigurationLevel(X_AD_SysConfig.CONFIGURATIONLEVEL_Client);
			cfg.setName(MigrationHelper.DE_METAS_COMMISSION_VERSION);
			cfg.setValue("" + 0);
			cfg.setIsActive(true);
			InterfaceWrapperHelper.save(cfg);
		}
		return cfg;
	}

	private void log(final String msg)
	{
		if (logger != null)
		{
			logger.addLog(msg);
		}
		else
		{
			log.info(msg);
		}
	}

	private void commit()
	{
		if (isTest())
		{
			return;
		}
		final Trx trx = Trx.get(getTrxName(), false);
		if (trx == null)
		{
			return;
		}
		trx.commit();
	}

	private void migrate_v1()
	{
		final int clientId = Env.getAD_Client_ID(getCtx());

		final List<MDocType> existingTypes =
				new Query(getCtx(), I_C_DocType.Table_Name, I_C_DocType.COLUMNNAME_AD_Client_ID + "=? AND " + I_C_DocType.COLUMNNAME_DocBaseType + "=?", getTrxName())
						.setParameters(clientId, Constants.DOCBASETYPE_AEInvoice)
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.list();

		final IDocumentPA docPA = Services.get(IDocumentPA.class);

		for (final MDocType existingType : existingTypes)
		{
			existingType.setDocSubType(CommissionConstants.COMMISSON_INVOICE_DOCSUBTYPE_CALC);
			existingType.saveEx();

			final MOrg currentOrg = MOrg.get(getCtx(), existingType.getAD_Org_ID());

			final String newDocTypeName = "Provision-Korrektur (" + currentOrg.getName() + ")";

			final I_C_DocType newDocType = docPA.createDocType(ctx, CommissionConstants.ENTITY_TYPE, newDocTypeName, newDocTypeName,
					Constants.DOCBASETYPE_AEInvoice, CommissionConstants.COMMISSON_INVOICE_DOCSUBTYPE_CORRECTION,
					0, 0, 0,
					1000018, // TODO: hardcoded GL_Category_ID
					trxName);
			newDocType.setAD_Org_ID(existingType.getAD_Org_ID());
			newDocType.setIsDocNoControlled(true);
			newDocType.setDocNoSequence_ID(existingType.getDocNoSequence_ID());
			newDocType.setDocumentCopies(existingType.getDocumentCopies());
			newDocType.setGL_Category_ID(existingType.getGL_Category_ID());
			InterfaceWrapperHelper.save(newDocType);

			log("Created doctype " + newDocType.getName());
		}

		// first adding an explicit type with very simple references, to record connections between c_invoicelines
		final I_AD_RelationType calcLine2corrLine = InterfaceWrapperHelper.create(ctx,  I_AD_RelationType.class, trxName);
		calcLine2corrLine.setName("Prov.-Abrechnung <=> Korrektur (Positionsebene)");
		calcLine2corrLine.setIsDirected(false);
		calcLine2corrLine.setAD_Reference_Source_ID(MigrationHelper.AD_Reference_ID_C_InvoiceLine);
		calcLine2corrLine.setAD_Reference_Target_ID(MigrationHelper.AD_Reference_ID_C_InvoiceLine);
		//calcLine2corrLine.setIsExplicit(true);
		calcLine2corrLine.setInternalName("com_calcline2corrline");
		calcLine2corrLine.setRole_Source("ComCalc");
		calcLine2corrLine.setRole_Target("ComCorr");
		InterfaceWrapperHelper.save(calcLine2corrLine);

		log("Created relation type '" + calcLine2corrLine.getName() + "' (internal name='" + calcLine2corrLine.getInternalName() + "')");

		final I_AD_RelationType calc2corr = InterfaceWrapperHelper.create(ctx,  I_AD_RelationType.class, trxName);
		calc2corr.setName("Prov.-Abrechnung <=> Korrektur (Belegebene)");
		calc2corr.setIsDirected(false);
		calc2corr.setAD_Reference_Source_ID(MigrationHelper.AD_REFERENCE_Reltype_Invoice);
		calc2corr.setAD_Reference_Target_ID(MigrationHelper.AD_REFERENCE_Reltype_Invoice);
		calc2corr.setInternalName("com_calc2corr");

		calc2corr.setRole_Source("ComCalc");
		calc2corr.setRole_Target("ComCorr");
		InterfaceWrapperHelper.save(calc2corr);

		log("Created relation type '" + calc2corr.getName() + "' (internal name='" + calc2corr.getInternalName() + "')");
	}

	// private MReference mkRef(final String docSubType, final String refName)
	// {
	// final int invoiceTableId = MTable.getTable_ID(I_C_Invoice.Table_Name);
	//
	// final MReference ref = new MReference(ctx, 0, trxName);
	// ref.setAD_Org_ID(0);
	// ref.setEntityType(ENTITY_TYPE);
	// ref.setName(refName);
	// ref.setValidationType(X_AD_Reference.VALIDATIONTYPE_TableValidation);
	// ref.saveEx();
	//
	// final MRefTable refTable = new MRefTable(ctx, 0, trxName);
	// refTable.setAD_Org_ID(0);
	// refTable.setAD_Reference_ID(ref.get_ID());
	// refTable.setAD_Table_ID(invoiceTableId);
	// refTable.setEntityType(ENTITY_TYPE);
	//
	// final int colC_InvoiceLine_ID = MColumn.getColumn_ID(I_C_InvoiceLine.Table_Name, I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID);
	// refTable.setAD_Key(colC_InvoiceLine_ID);
	// refTable.setAD_Display(colC_InvoiceLine_ID);
	//
	// refTable.setAD_Window_ID(AD_WINDOW_ID_COMM_CALC);
	//
	// // refTable.setWhereClause("C_Invoice.IsSOTrx='N' AND C_Invoice.C_DocType_ID IN (select C_DocType_ID from C_DocType where "
	// // +
	// // "DocBaseType='" + Constants.DOCBASETYPE_AEInvoice
	// // + "' AND DocSubType='" + docSubType
	// // + "' AND AD_Org_ID=@AD_Org_ID@) ");
	//
	// refTable.saveEx();
	//
	// return ref;
	// }
}
