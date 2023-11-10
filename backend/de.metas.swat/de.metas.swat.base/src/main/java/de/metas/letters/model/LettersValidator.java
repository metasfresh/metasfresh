package de.metas.letters.model;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.interfaces.I_M_Movement;
import de.metas.letters.api.ITextTemplateBL;
import de.metas.letters.api.impl.TextTemplateBL;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_Cash;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_DunningLevel;
import org.compiere.model.I_C_DunningRunEntry;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_C_ProjectIssue;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_MatchPO;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.MClient;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.Util.ArrayKey;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_HR_Process;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;

/**
 * @author teo_sarca
 *
 */
public class LettersValidator implements ModelValidator
{
	private int m_AD_Client_ID = -1;

	private static final Map<ArrayKey, Set<MADBoilerPlateVar>> s_cacheVars = new HashMap<>();

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
			m_AD_Client_ID = client.getAD_Client_ID();

		Services.registerService(ITextTemplateBL.class, TextTemplateBL.get());

		engine.addModelChange(I_C_DunningRunEntry.Table_Name, this);
		//
		final TreeSet<String> tableNames = new TreeSet<>();
		for (final MADBoilerPlateVarEval timing : MADBoilerPlateVarEval.getAll(Env.getCtx()))
		{
			final I_C_DocType dt = timing.getC_DocType();
			if (dt == null || dt.getC_DocType_ID() <= 0)
				continue;
			final String tableName = getTableNameByDocBaseType(dt.getDocBaseType());
			if (tableName == null)
				continue;

			final ArrayKey key = new ArrayKey(timing.getAD_Client_ID(), tableName, dt.getC_DocType_ID(), timing.getEvalTime());
			Set<MADBoilerPlateVar> list = s_cacheVars.get(key);
			if (list == null)
			{
				list = new TreeSet<>();
			}
			final MADBoilerPlateVar var = MADBoilerPlateVar.get(timing.getCtx(), timing.getAD_BoilerPlate_Var_ID());
			list.add(var);
			s_cacheVars.put(key, list);
			tableNames.add(tableName);
		}
		//
		for (final String tableName : tableNames)
		{
			engine.addDocValidate(tableName, this);
		}
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		if (I_C_DunningRunEntry.Table_Name.equals(po.get_TableName())
				&& TYPE_BEFORE_NEW == type)
		{
			final I_C_DunningRunEntry dre = InterfaceWrapperHelper.create(po, I_C_DunningRunEntry.class);
			setDunningRunEntryNote(dre);
		}
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		final Set<MADBoilerPlateVar> vars = getVars(po, timing);
		if (vars.isEmpty())
			return null;
		//
		for (final MColumn c : MTable.get(po.getCtx(), po.get_Table_ID()).getColumns(false))
		{
			final I_AD_Column column = InterfaceWrapperHelper.create(c, I_AD_Column.class);
			if (column.isAdvancedText())
			{
				parseField(po, column.getColumnName(), vars);
			}
		}
		//
		return null;
	}

	private static final Map<String, String> s_mapDocBaseTypeToTableName = new HashMap<>(20);
	static
	{
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_GLJournal, I_GL_Journal.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_GLDocument, I_GL_Journal.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_APInvoice, I_C_Invoice.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_APPayment, I_C_Payment.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_ARInvoice, I_C_Invoice.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_ARReceipt, I_C_Payment.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_SalesOrder, I_C_Order.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_ARProFormaInvoice, I_C_Invoice.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_MaterialDelivery, I_M_InOut.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_MaterialReceipt, I_M_InOut.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_MaterialMovement, I_M_Movement.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_PurchaseOrder, I_C_Order.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_PurchaseRequisition, I_M_Requisition.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory, I_M_Inventory.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_APCreditMemo, I_C_Invoice.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_ARCreditMemo, I_C_Invoice.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_BankStatement, I_C_BankStatement.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_CashJournal, I_C_Cash.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_PaymentAllocation, I_C_AllocationHdr.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_MatchInvoice, I_M_MatchInv.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_MatchPO, I_M_MatchPO.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_ProjectIssue, I_C_ProjectIssue.Table_Name);
		s_mapDocBaseTypeToTableName.put(PPOrderDocBaseType.MAINTENANCE_ORDER.getCode(), I_PP_Order.Table_Name);
		s_mapDocBaseTypeToTableName.put(PPOrderDocBaseType.MANUFACTURING_ORDER.getCode(), I_PP_Order.Table_Name);
		s_mapDocBaseTypeToTableName.put(PPOrderDocBaseType.QUALITY_ORDER.getCode(), I_PP_Order.Table_Name);
		s_mapDocBaseTypeToTableName.put(PPOrderDocBaseType.REPAIR_ORDER.getCode(), I_PP_Order.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_Payroll, I_HR_Process.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_DistributionOrder, I_DD_Order.Table_Name);
		s_mapDocBaseTypeToTableName.put(X_C_DocType.DOCBASETYPE_ManufacturingCostCollector, I_PP_Cost_Collector.Table_Name);
	}

	private static String getTableNameByDocBaseType(final String docBaseType)
	{
		return s_mapDocBaseTypeToTableName.get(docBaseType);
	}

	@Nullable
	private static String getEvalTime(final int timing)
	{
		if (TIMING_AFTER_PREPARE == timing)
			return MADBoilerPlateVarEval.EVALTIME_DocumentAfterPrepare;
		else if (TIMING_AFTER_COMPLETE == timing)
			return MADBoilerPlateVarEval.EVALTIME_DocumentAfterComplete;
		else if (TIMING_AFTER_VOID == timing)
			return MADBoilerPlateVarEval.EVALTIME_DocumentAfterVoid;
		else if (TIMING_AFTER_CLOSE == timing)
			return MADBoilerPlateVarEval.EVALTIME_DocumentAfterClose;
		else if (TIMING_AFTER_REACTIVATE == timing)
			return MADBoilerPlateVarEval.EVALTIME_DocumentAfterReactivate;
		else if (TIMING_AFTER_REVERSECORRECT == timing)
			return MADBoilerPlateVarEval.EVALTIME_DocumentAfterReverseCorrect;
		else if (TIMING_AFTER_REVERSEACCRUAL == timing)
			return MADBoilerPlateVarEval.EVALTIME_DocumentAfterReverseAccrual;
		else if (TIMING_AFTER_POST == timing)
			return MADBoilerPlateVarEval.EVALTIME_DocumentAfterPost;
		else
			return null;
	}

	private static Set<MADBoilerPlateVar> getVars(final PO po, final int timing)
	{
		final int AD_Client_ID = Env.getAD_Client_ID(po.getCtx());
		final String tableName = po.get_TableName();
		final int C_DocType_ID = getC_DocType_ID(po);
		final String evalTime = getEvalTime(timing);
		final ArrayKey key = new ArrayKey(AD_Client_ID, tableName, C_DocType_ID, evalTime);

		Set<MADBoilerPlateVar> vars = s_cacheVars.get(key);
		if (vars == null)
			vars = new TreeSet<>();
		return vars;
	}

	private static int getC_DocType_ID(final PO po)
	{
		int index = po.get_ColumnIndex("C_DocType_ID");
		if (index != -1)
		{
			Integer ii = (Integer)po.get_Value(index);
			// DocType does not exist - get DocTypeTarget
			if (ii != null && ii <= 0)
			{
				index = po.get_ColumnIndex("C_DocTypeTarget_ID");
				if (index > 0)
					ii = (Integer)po.get_Value(index);
			}
			if (ii != null)
				return ii;
		}
		return -1;
	}

	private static void parseField(final PO po, final String columnName, final Collection<MADBoilerPlateVar> vars)
	{
		final String text = po.get_ValueAsString(columnName);
		if (text == null || Check.isBlank(text))
			return;
		//
		final BoilerPlateContext attributes = BoilerPlateContext.builder()
				.setSourceDocumentFromObject(po)
				.build();
		//
		final Matcher m = MADBoilerPlate.NameTagPattern.matcher(text);
		final StringBuffer sb = new StringBuffer();
		while (m.find())
		{
			final String refName = MADBoilerPlate.getTagName(m);
			//
			MADBoilerPlateVar var = null;
			for (final MADBoilerPlateVar v : vars)
			{
				if (refName.equals(v.getValue().trim()))
				{
					var = v;
					break;
				}
			}
			//
			final String replacement;
			if (var != null)
			{
				replacement = MADBoilerPlate.getPlainText(var.evaluate(attributes));
			}
			else
			{
				replacement = m.group();
			}
			m.appendReplacement(sb, replacement);
		}
		m.appendTail(sb);

		final String textParsed = sb.toString();
		po.set_ValueOfColumn(columnName, textParsed);
	}

	private static void setDunningRunEntryNote(final I_C_DunningRunEntry dre)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(dre);
		final String trxName = InterfaceWrapperHelper.getTrxName(dre);

		final I_C_DunningLevel dl = dre.getC_DunningLevel();
		final I_C_BPartner bp = Services.get(IBPartnerDAO.class).getById(dre.getC_BPartner_ID());
		final String adLanguage = bp.getAD_Language();
		final String text;
		if (adLanguage != null)
		{
			text = InterfaceWrapperHelper.getPO(dl).get_Translation(I_C_DunningLevel.COLUMNNAME_Note, adLanguage);
		}
		else
		{
			text = dl.getNote();
		}
		final boolean isEmbeded = true;

		final BoilerPlateContext attributes = BoilerPlateContext.builder()
				.setSourceDocumentFromObject(dre)
				.build();

		final String textParsed = MADBoilerPlate.parseText(ctx, text, isEmbeded, attributes, trxName);

		dre.setNote(textParsed);
	}
}
