package de.metas.relation.grid;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.I_AD_RelationType;
import org.compiere.model.MRefTable;
import org.compiere.model.MReference;
import org.compiere.model.MRelationType;
import org.compiere.model.MTable;
import org.compiere.model.X_AD_Reference;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.compiere.util.TrxRunnable;
import org.compiere.util.Util;

import de.metas.ordercandidate.OrderCandidate_Constants;

public class CtrlRelationTarget
{

	// private static final Logger logger = CLogMgt.getLogger(CtrlRelationTarget.class);

	private final IViewRelationTarget view;

	private final ModelRelationTarget model;

	public CtrlRelationTarget(final ModelRelationTarget model, final IViewRelationTarget view)
	{
		this.view = view;
		this.model = model;

		view.addCancelButtonListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.closeWindow();
			}
		});
		view.addOKButtonListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				okButtonPressed();
			}
		});
		view.addWhereClauseChangedListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(final PropertyChangeEvent evt)
			{
				assert "whereClause".equals(evt.getPropertyName());
				final String targetWhereClause = (String)evt.getNewValue();
				model.setTargetWhereClause(targetWhereClause);
			}
		});
	}

	private void okButtonPressed()
	{
		final Properties ctx = Env.getCtx();
		final String entityType = OrderCandidate_Constants.ENTITY_TYPE;

		try
		{
			Trx.run(new TrxRunnable()
			{
				@Override
				public void run(final String trxName)
			{
				createOrUpdateRelationType(ctx, entityType, trxName);
			}
			});
		}
		catch (final RuntimeException rte)
		{
			view.showError(rte.getMessage());
		}
		view.closeWindow();
	}

	private void createOrUpdateRelationType(final Properties ctx, final String entityType, final String trxName)
	{
		final I_AD_RelationType retrievedRelType = MRelationType.retrieveForInternalName(ctx, model.getRelationTypeInternalName(), null);

		final I_AD_RelationType relType;

		final MReference refSource;
		final I_AD_Ref_Table refTableSource;

		final MReference refTarget;
		final I_AD_Ref_Table refTableTarget;

		final int orgId = 0;

		if (retrievedRelType == null)
		{
			relType = new MRelationType(ctx, 0, trxName);
			relType.setInternalName(model.getRelationTypeInternalName());

			refSource = new MReference(ctx, 0, trxName);
			refSource.setAD_Org_ID(orgId);
			refSource.setEntityType(entityType);
			refSource.setName(mkNameOfSourceRef(ctx));
			refSource.setValidationType(X_AD_Reference.VALIDATIONTYPE_TableValidation);
			refSource.saveEx();
			relType.setAD_Reference_Source_ID(refSource.get_ID());

			refTableSource = new MRefTable(ctx, 0, trxName);
			refTableSource.setAD_Org_ID(orgId);
			refTableSource.setAD_Reference_ID(refSource.get_ID());
			refTableSource.setEntityType(entityType);

			refTarget = new MReference(ctx, 0, trxName);
			refTarget.setAD_Org_ID(orgId);
			refTarget.setEntityType(entityType);
			refTarget.setName(mkNameOfTargetRef(ctx));
			refTarget.setValidationType(X_AD_Reference.VALIDATIONTYPE_TableValidation);
			refTarget.saveEx();
			relType.setAD_Reference_Target_ID(refTarget.get_ID());

			refTableTarget = new MRefTable(ctx, 0, trxName);
			refTableTarget.setAD_Org_ID(orgId);
			refTableTarget.setAD_Reference_ID(refTarget.get_ID());
			refTableTarget.setEntityType(entityType);
		}
		else
		{
			relType = retrievedRelType;
			refSource = (MReference)relType.getAD_Reference_Source();
			refTableSource = MReference.retrieveRefTable(ctx, refSource.get_ID(), trxName);

			refTarget = (MReference)relType.getAD_Reference_Target();
			refTableTarget = MReference.retrieveRefTable(ctx, refTarget.get_ID(), trxName);

		}
		relType.setName(model.getRelationTypeName());
		relType.setIsDirected(model.isRelationTypeDirected());
		InterfaceWrapperHelper.save(relType);

		// source reference
		refTableSource.setAD_Table_ID(model.getAdTableSourceId());
		refTableSource.setAD_Window_ID(model.getAdWindowSourceId());

		final MTable tableSource = MTable.get(ctx, model.getAdTableSourceId());
		final String[] keyColumnsSource = tableSource.getKeyColumns();
		assert keyColumnsSource.length == 1;

		final int keyColumnSourceId = tableSource.getColumn(keyColumnsSource[0]).get_ID();
		refTableSource.setAD_Key(keyColumnSourceId);
		refTableSource.setAD_Display(keyColumnSourceId);

		refTableSource.setWhereClause(keyColumnsSource[0] + "=" + model.getRecordSourceId());
		InterfaceWrapperHelper.save(refTableSource);

		// target reference
		refTableTarget.setAD_Table_ID(model.getAdTableTargetId());
		refTableTarget.setAD_Window_ID(model.getAdWindowTargetId());

		final MTable tableTarget = MTable.get(ctx, model.getAdTableTargetId());
		final String[] keyColumnsTarget = tableTarget.getKeyColumns();
		assert keyColumnsTarget.length == 1;

		final int keyColumnTargetId = tableTarget.getColumn(keyColumnsTarget[0]).get_ID();
		refTableTarget.setAD_Key(keyColumnTargetId);
		refTableTarget.setAD_Display(keyColumnTargetId);

		final String targetWhereClauseToUse;
		if (Util.isEmpty(model.getTargetWhereClause()))
		{
			targetWhereClauseToUse = "1=1";
		}
		else
		{
			targetWhereClauseToUse = model.getTargetWhereClause();
		}
		refTableTarget.setWhereClause(targetWhereClauseToUse);
		InterfaceWrapperHelper.save(refTableTarget);
	}

	private String mkNameOfSourceRef(final Properties ctx)
	{
		return "RelType_" + MTable.getTableName(ctx, model.getAdTableSourceId()) + "_" + model.getRecordSourceId();
	}

	private String mkNameOfTargetRef(final Properties ctx)
	{
		return "RelType_" + MTable.getTableName(ctx, model.getAdTableTargetId()) + "_" + model.getRecordSourceId();
	}
}
