package de.metas.document.refid.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;

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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.document.refid.api.IReferenceNoBL;
import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.api.IReferenceNoGeneratorInstance;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.document.refid.model.I_C_ReferenceNo_Type_Table;
import de.metas.document.refid.spi.IReferenceNoGenerator;
import de.metas.logging.LogManager;
import de.metas.util.Services;

public class ReferenceNoBL implements IReferenceNoBL
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public void linkReferenceNo(final PO po, final IReferenceNoGeneratorInstance instance)
	{
		final IReferenceNoDAO dao = Services.get(IReferenceNoDAO.class);

		final IContextAware contextAware = getContextAware(po);


		final String referenceNoStr = instance.generateReferenceNo(po);
		if (IReferenceNoGenerator.REFERENCENO_None == referenceNoStr)
		{
			logger.debug("Instance {} did not generate any referenceNo for '{}'. Skip.", new Object[] { instance, po });
			return;
		}

		final I_C_ReferenceNo referenceNo = dao.getCreateReferenceNo(instance.getType(), referenceNoStr, contextAware);
		dao.getCreateReferenceNoDoc(referenceNo, TableRecordReference.of(po));

		// 04153 : mark the reference numbers with 'referenceNoStr' created by the system with isManual = N
		if(referenceNo != null)
		{
			referenceNo.setIsManual(false);
			InterfaceWrapperHelper.save(referenceNo); // make sure the flag is saved
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("Created reference " + referenceNoStr + " for " + po);
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

		final List<I_C_ReferenceNo_Doc> assignments = dao.retrieveAllDocAssignments(ctx, referenceNoTypeId, tableId, recordId, trxName);
		assignments.forEach(InterfaceWrapperHelper::delete);
	}

	@Override
	// @Cached(cacheName = I_C_ReferenceNo_Type_Table.Table_Name + "_IReferenceNoGeneratorInstance")
	public List<IReferenceNoGeneratorInstance> getReferenceNoGeneratorInstances(final Properties ctx)
	{
		final IReferenceNoDAO dao = Services.get(IReferenceNoDAO.class);

		final List<IReferenceNoGeneratorInstance> result = new ArrayList<>();
		for (final I_C_ReferenceNo_Type type : dao.retrieveReferenceNoTypes())
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
		final List<Integer> assignedTableIds = new ArrayList<>();
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
			logger.error("Error loading referenceNo generator '" + classname + "'. Ignored.", e);
			return null;
		}
	}

	@Override
	public void linkOnSameReferenceNo(final Object fromModel, final Object toModel)
	{
		if (fromModel == null)
		{
			logger.debug("fromModel is null. Skip.");
			return;
		}

		if (toModel == null)
		{
			logger.debug("toModel is null. Skip.");
			return;
		}

		// We use ctx and trxName from "toModel", because that one was produced now
		final Properties ctx = InterfaceWrapperHelper.getCtx(toModel);
		final String trxName = InterfaceWrapperHelper.getTrxName(toModel);

		final String fromTableName = InterfaceWrapperHelper.getModelTableName(fromModel);
		final int fromRecordId = InterfaceWrapperHelper.getId(fromModel);
		if (fromRecordId <= 0)
		{
			logger.warn("fromModel {} was not saved yet or does not support simple primary key. Skip.", fromModel);
			return;
		}

		final int toRecordId = InterfaceWrapperHelper.getId(toModel);
		if (toRecordId <= 0)
		{
			logger.warn("toModel {} was not saved yet or does not support simple primary key. Skip.", toModel);
			return;
		}

		final IReferenceNoDAO dao = Services.get(IReferenceNoDAO.class);
		final List<I_C_ReferenceNo_Doc> fromAssignments = dao.retrieveAllDocAssignments(ctx,
				-1, // referenceNoTypeId - return all assignments
				MTable.getTable_ID(fromTableName), // tableId
				fromRecordId,
				trxName);

		for (final I_C_ReferenceNo_Doc fromAssignment : fromAssignments)
		{
			final I_C_ReferenceNo referenceNo = fromAssignment.getC_ReferenceNo();
			dao.getCreateReferenceNoDoc(referenceNo, TableRecordReference.of(toModel));

			logger.info("Linked {} to {}", new Object[] { toModel, referenceNo });
		}

	}
}
