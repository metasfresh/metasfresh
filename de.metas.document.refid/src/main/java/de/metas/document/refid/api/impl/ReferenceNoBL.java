package de.metas.document.refid.api.impl;

/*
 * #%L
 * de.metas.document.refid
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.document.refid.api.IReferenceNoBL;
import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.api.IReferenceNoGeneratorInstance;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.document.refid.model.I_C_ReferenceNo_Type_Table;
import de.metas.document.refid.spi.IReferenceNoGenerator;

public class ReferenceNoBL implements IReferenceNoBL
{
	private final transient CLogger logger = CLogger.getCLogger(getClass());

	@Override
	public void linkReferenceNo(final PO po, final IReferenceNoGeneratorInstance instance)
	{
		final IReferenceNoDAO dao = Services.get(IReferenceNoDAO.class);

		final Properties localCtx = Env.deriveCtx(po.getCtx());
		Env.setContext(localCtx, "#AD_Client_ID", po.getAD_Client_ID());
		Env.setContext(localCtx, "#AD_Org_ID", po.getAD_Org_ID());

		final String trxName = po.get_TrxName();
		final int tableId = po.get_Table_ID();
		final int recordId = po.get_ID();

		final String referenceNoStr = instance.generateReferenceNo(po);
		if (IReferenceNoGenerator.REFERENCENO_None == referenceNoStr)
		{
			logger.log(Level.FINE, "Instance {0} did not generate any referenceNo for '{1}'. Skip.", new Object[] { instance, po });
			return;
		}

		final I_C_ReferenceNo referenceNo = dao.getCreateReferenceNo(localCtx, instance.getType(), referenceNoStr, trxName);
		dao.getCreateReferenceNoDoc(referenceNo, tableId, recordId);

		
		// 04153 : mark the reference numbers with 'referenceNoStr' created by the system with isManual = N
		if(referenceNo != null)
		{
			referenceNo.setIsManual(false);
			InterfaceWrapperHelper.save(referenceNo); // make sure the flag is saved
		}

		if (logger.isLoggable(Level.FINE))
		{
			logger.fine("Created reference " + referenceNoStr + " for " + po);
		}
	}

	@Override
	public void unlinkReferenceNo(final PO po, final IReferenceNoGeneratorInstance instance)
	{
		final IReferenceNoDAO dao = Services.get(IReferenceNoDAO.class);

		final Properties ctx = po.getCtx();
		final String trxName = po.get_TrxName();
		final int tableId = po.get_Table_ID();
		final int recordId = po.get_ID();
		final int referenceNoTypeId = instance.getType().getC_ReferenceNo_Type_ID();

		final List<I_C_ReferenceNo_Doc> assignments = dao.retrieveDocAssignments(ctx, referenceNoTypeId, tableId, recordId, trxName);
		dao.removeDocAssignments(assignments);
	}

	@Override
	// @Cached(cacheName = I_C_ReferenceNo_Type_Table.Table_Name + "_IReferenceNoGeneratorInstance")
	public List<IReferenceNoGeneratorInstance> getReferenceNoGeneratorInstances(final Properties ctx)
	{
		final IReferenceNoDAO dao = Services.get(IReferenceNoDAO.class);

		final List<IReferenceNoGeneratorInstance> result = new ArrayList<IReferenceNoGeneratorInstance>();
		for (I_C_ReferenceNo_Type type : dao.retrieveReferenceNoTypes(ctx))
		{
			final IReferenceNoGeneratorInstance generatorInstance = getReferenceNoGeneratorInstance(ctx, type);
			result.add(generatorInstance);
		}

		return Collections.unmodifiableList(result);
	}

	@Override
	public IReferenceNoGeneratorInstance getReferenceNoGeneratorInstance(final Properties ctx, final I_C_ReferenceNo_Type type)
	{
		final IReferenceNoDAO dao = Services.get(IReferenceNoDAO.class);

		//
		// Build-up AD_Table_IDs list
		final List<Integer> assignedTableIds = new ArrayList<Integer>();
		for (I_C_ReferenceNo_Type_Table assignment : dao.retrieveTableAssignments(type))
		{
			if (!assignment.isActive())
			{
				continue;
			}

			final int tableId = assignment.getAD_Table_ID();
			if (!assignedTableIds.contains(tableId))
			{
				assignedTableIds.add(tableId);
			}
		}
		if (assignedTableIds.isEmpty())
		{
			logger.info("No assigned documents found for reference type " + type + ". Skip.");
			return null;
		}

		final IReferenceNoGenerator generator = getReferenceNoGeneratorOrNull(type.getClassname());
		if (generator == null)
		{
			// No generator found or there were logged errors => SKIP
			return null;
		}

		final IReferenceNoGeneratorInstance generatorInstance = new ReferenceNoGeneratorInstance(type, assignedTableIds, generator);
		return generatorInstance;
	}

	private IReferenceNoGenerator getReferenceNoGeneratorOrNull(String classname)
	{
		try
		{
			return Util.getInstance(IReferenceNoGenerator.class, classname);
		}
		catch (Exception e)
		{
			logger.log(Level.SEVERE, "Error loading referenceNo generator '" + classname + "'. Ignored.", e);
			return null;
		}
	}

	@Override
	public void linkOnSameReferenceNo(final Object fromModel, final Object toModel)
	{
		if (fromModel == null)
		{
			logger.log(Level.FINE, "fromModel is null. Skip.");
			return;
		}

		if (toModel == null)
		{
			logger.log(Level.FINE, "toModel is null. Skip.");
			return;
		}

		// We use ctx and trxName from "toModel", because that one was produced now
		final Properties ctx = InterfaceWrapperHelper.getCtx(toModel);
		final String trxName = InterfaceWrapperHelper.getTrxName(toModel);

		final String fromTableName = InterfaceWrapperHelper.getModelTableName(fromModel);
		final int fromRecordId = InterfaceWrapperHelper.getId(fromModel);
		if (fromRecordId <= 0)
		{
			logger.log(Level.WARNING, "fromModel {0} was not saved yet or does not support simple primary key. Skip.", fromModel);
			return;
		}

		final String toTableName = InterfaceWrapperHelper.getModelTableName(toModel);
		final int toTableId = MTable.getTable_ID(toTableName);
		final int toRecordId = InterfaceWrapperHelper.getId(toModel);
		if (toRecordId <= 0)
		{
			logger.log(Level.WARNING, "toModel {0} was not saved yet or does not support simple primary key. Skip.", toModel);
			return;
		}

		final IReferenceNoDAO dao = Services.get(IReferenceNoDAO.class);
		final List<I_C_ReferenceNo_Doc> fromAssignments = dao.retrieveDocAssignments(ctx,
				-1, // referenceNoTypeId - return all assignments
				MTable.getTable_ID(fromTableName), // tableId
				fromRecordId,
				trxName);

		for (final I_C_ReferenceNo_Doc fromAssignment : fromAssignments)
		{
			final I_C_ReferenceNo referenceNo = fromAssignment.getC_ReferenceNo();
			dao.getCreateReferenceNoDoc(referenceNo, toTableId, toRecordId);

			logger.log(Level.INFO, "Linked {0} to {1}", new Object[] { toModel, referenceNo });
		}

	}
}
