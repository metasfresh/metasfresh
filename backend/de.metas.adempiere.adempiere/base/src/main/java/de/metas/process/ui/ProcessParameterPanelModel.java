<<<<<<< HEAD
package de.metas.process.ui;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

=======
/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.process.ui;

import de.metas.logging.LogManager;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.ProcessClassInfo;
import de.metas.process.ProcessDefaultParametersUpdater;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.Check;
import lombok.Getter;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldVO;
import org.compiere.model.Lookup;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

<<<<<<< HEAD
import de.metas.logging.LogManager;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.ProcessClassInfo;
import de.metas.process.ProcessDefaultParametersUpdater;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.Check;
=======
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

public class ProcessParameterPanelModel
{
	private static final Logger log = LogManager.getLogger(ProcessParameterPanelModel.class);

	public static final String FIELDSEPARATOR_TEXT = " - ";

	@FunctionalInterface
<<<<<<< HEAD
	public static interface IDisplayValueProvider
=======
	public interface IDisplayValueProvider
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		String getDisplayValue(GridField gridField);
	}

<<<<<<< HEAD
	private final Properties ctx;
	private final int windowNo;
	private final int tabNo;
	private final int processId;
	private final ProcessClassInfo processClassInfo;
	private final List<GridField> gridFields = new ArrayList<GridField>();
	private final List<GridField> gridFieldsTo = new ArrayList<GridField>();
	private final List<GridField> gridFieldsAll = new ArrayList<GridField>();
=======
	@Getter private final Properties ctx;
	@Getter private final int windowNo;
	@Getter private final int tabNo;
	private final int processId;
	private final ProcessClassInfo processClassInfo;
	private final List<GridField> gridFields = new ArrayList<>();
	private final List<GridField> gridFieldsTo = new ArrayList<>();
	private final List<GridField> gridFieldsAll = new ArrayList<>();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	private final ProcessDefaultParametersUpdater defaultParametersUpdater;

	/** Display values provider (never null) */
	private IDisplayValueProvider displayValueProvider = (gridField) -> null;

	/**
	 * Dynamic generated Parameter panel.
	 *
	 * @param parentCtx parent context
	 * @param pi process info, used ONLY to get AD_Process_ID, WindowNo and TabNo; no reference is stored to it
	 */
	public ProcessParameterPanelModel(final Properties parentCtx, final ProcessInfo pi)
	{
		super();
		Check.assumeNotNull(pi, "pi not null");

		// NOTE: we are using same WindowNo/TabNo as the calling window/tab but we will create a shadow context just to not alter the current context
		ctx = Env.deriveCtx(parentCtx);
		windowNo = pi.getWindowNo();
		tabNo = pi.getTabNo();
		processId = pi.getAdProcessId().getRepoId();

		defaultParametersUpdater = ProcessDefaultParametersUpdater.newInstance()
				.addDefaultParametersProvider(pi)
				.addDefaultParametersProvider(parameter -> GridField.extractFrom(parameter).getDefault())
				.onDefaultValue((parameter, value) -> setFieldValue(GridField.extractFrom(parameter), value));

		processClassInfo = pi.getProcessClassInfo();

		createFields();
	}

<<<<<<< HEAD
	public Properties getCtx()
	{
		return ctx;
	}

	public int getWindowNo()
	{
		return windowNo;
	}

	public int getTabNo()
	{
		return tabNo;
	}

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public int getAD_Process_ID()
	{
		return processId;
	}

	public void dispose()
	{
		gridFields.clear();
		gridFieldsTo.clear();
		gridFieldsAll.clear();
	}   // dispose

	private void createFields()
	{
		//
		final String sql;
		if (Env.isBaseLanguage(ctx, "AD_Process_Para"))
		{
			sql = "SELECT p.Name, p.Description, p.Help, "
					+ "p.AD_Reference_ID, p.AD_Process_Para_ID, "
					+ "p.FieldLength, p.IsMandatory, p.IsRange, p.ColumnName, "
					+ "p.DefaultValue, p.DefaultValue2, p.VFormat, p.ValueMin, p.ValueMax, "
					+ "p.SeqNo, p.AD_Reference_Value_ID, p.AD_Val_Rule_ID, p.ReadOnlyLogic, p.DisplayLogic "
					+ ", p.IsEncrypted " // metas: tsa: US745
					+ ", p.isAutoComplete" // 05887
					+ ", p.EntityType as FieldEntityType"
					+ " FROM AD_Process_Para p"
					+ " WHERE p.AD_Process_ID=?"		// 1
					+ " AND p.IsActive='Y' "
					+ " ORDER BY SeqNo";
		}
		else
		{
			sql = "SELECT t.Name, t.Description, t.Help, "
					+ "p.AD_Reference_ID, p.AD_Process_Para_ID, "
					+ "p.FieldLength, p.IsMandatory, p.IsRange, p.ColumnName, "
					+ "p.DefaultValue, p.DefaultValue2, p.VFormat, p.ValueMin, p.ValueMax, "
					+ "p.SeqNo, p.AD_Reference_Value_ID, p.AD_Val_Rule_ID, p.ReadOnlyLogic, p.DisplayLogic "
					+ ", p.IsEncrypted " // metas: tsa: US745
					+ ", p.isAutoComplete" // 05887
					+ ", p.EntityType as FieldEntityType"
					+ " FROM AD_Process_Para p"
					+ " INNER JOIN AD_Process_Para_Trl t ON (p.AD_Process_Para_ID=t.AD_Process_Para_ID)"
					+ "WHERE p.AD_Process_ID=?"		// 1
					+ " AND t.AD_Language='" + Env.getAD_Language(ctx) + "'"
					+ " AND p.IsActive='Y' "
					+ " ORDER BY SeqNo";
		}

		// Create Fields
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, getAD_Process_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				createField(rs);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
<<<<<<< HEAD
			rs = null;
			pstmt = null;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
	}

	public int getFieldCount()
	{
		return gridFields.size();
	}

	public GridField getField(final int index)
	{
		return gridFields.get(index);
	}

	public GridField getFieldTo(final int index)
	{
		return gridFieldsTo.get(index);
	}

<<<<<<< HEAD
	/**
	 * Get field index by columnName
	 *
	 * @param columnName
	 * @return field index or -1
	 */
	public int getFieldIndex(final String columnName)
	{
		for (int i = 0; i < gridFields.size(); i++)
		{
			if (gridFields.get(i).getColumnName().equals(columnName))
			{
				return i;
			}
		}
		return -1;
	}

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public void setDefaultValues()
	{
		final int size = getFieldCount();
		if (size <= 0)
		{
			return;
		}
		
		for (int index = 0; index < size; index++)
		{
			final IProcessDefaultParameter field = getField(index);
			defaultParametersUpdater.updateDefaultValue(field);

			final IProcessDefaultParameter fieldTo = getFieldTo(index);
			if (fieldTo != null)
			{
				defaultParametersUpdater.updateDefaultValue(fieldTo);
			}
		}
	}

	public void setDisplayValueProvider(final IDisplayValueProvider displayValueProvider)
	{
		Check.assumeNotNull(displayValueProvider, "Parameter displayValueProvider is not null");
		this.displayValueProvider = displayValueProvider;
	}

	/**
	 * Create Field. - creates Fields and adds it to m_mFields list - creates Editor and adds it to m_vEditors list Handles Ranges by adding additional mField/vEditor.
	 * <p>
	 * mFields are used for default value and mandatory checking; vEditors are used to retrieve the value (no data binding)
	 *
	 * @param rs result set
	 */
<<<<<<< HEAD
	private void createField(final ResultSet rs)
=======
	private void createField(final ResultSet rs) throws SQLException
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		// Parameter field
		final GridFieldVO gridFieldVO = GridFieldVO.createParameter(ctx, windowNo, tabNo, rs);
		{
			final boolean mandatoryOverride = processClassInfo.isParameterMandatory(gridFieldVO.getColumnName(), false); // parameterTo=false
			if (mandatoryOverride)
			{
				gridFieldVO.setMandatory(true);
			}
			final GridField gridField = new GridField(gridFieldVO);
			gridFields.add(gridField);                      // add to Fields
			gridFieldsAll.add(gridField);
		}

		//
		// Parameter field To
		{
			final GridField gridFieldTo;
			if (gridFieldVO.isRange)
			{
				final GridFieldVO gridFieldToVO = GridFieldVO.createParameterTo(gridFieldVO);
				final boolean mandatoryOverride = processClassInfo.isParameterMandatory(gridFieldVO.getColumnName(), true); // parameterTo=true
				if (mandatoryOverride)
				{
					gridFieldToVO.setMandatory(true);
				}
				gridFieldTo = new GridField(gridFieldToVO);
			}
			else
			{
				gridFieldTo = null;
			}
			gridFieldsTo.add(gridFieldTo); // add it even if null, to have the save "index" as in gridFields list
			if (gridFieldTo != null)
			{
				gridFieldsAll.add(gridFieldTo);
			}
		}
	}	// createField

	/**
	 * Notify the model that an editor value was changed.
<<<<<<< HEAD
	 *
	 * NOTE: this is used to do View to Model binding
	 *
	 * @param columnName
	 * @param valueNew
	 * @param displayValueNew
=======
	 * NOTE: this is used to do View to Model binding
	 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * @param changedField optional {@link GridField} that changed
	 */
	public void notifyValueChanged(final String columnName, final Object valueNew, final GridField changedField)
	{
		if (changedField == null)
		{
			return;
		}

		// Make sure is not already notifying (i.e. avoid endless recursion)
		if (!notifyValueChanged_CurrentColumnNames.add(columnName))
		{
			return;
		}

		try
		{
			setFieldValue(changedField, valueNew);

			for (final GridField gridField : gridFieldsAll)
			{
				if (gridField == null)
				{
					continue;
				}

				if (gridField == changedField)
				{
					continue;
				}
				else
				{
					validateField(gridField, columnName);
				}
			}

			// TODO: future processCallout (changedField);

			// setContext(columnName, valueNew);
		}
		finally
		{
			notifyValueChanged_CurrentColumnNames.remove(columnName);
		}
	}

	/** A list of column names which are notifying in progress */
<<<<<<< HEAD
	private final Set<String> notifyValueChanged_CurrentColumnNames = new HashSet<String>();
=======
	private final Set<String> notifyValueChanged_CurrentColumnNames = new HashSet<>();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/* package */void setFieldValue(final GridField gridField, final Object valueNew)
	{
		gridField.setValue(valueNew, true); // inserting=true(always)
	}

	/**
	 * Validate given <code>gridField</code> when <code>changedColumnName</code> was changed.
<<<<<<< HEAD
	 *
	 * @param gridField
	 * @param changedColumnName
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 */
	private void validateField(final GridField gridField, final String changedColumnName)
	{
		final List<String> dependentColumnNames = gridField.getDependentOn();
		if (!dependentColumnNames.contains(changedColumnName))
		{
			// field is not depending on changed column name => nothing to validate
			return;
		}

		if (gridField.getLookup() != null)
		{
			final Lookup mLookup = gridField.getLookup();

			// NOTE: we need to do this validation again because "dependentColumnNames" contains all dependencies (DisplayLogic, ReadOnlyLogic, Lookup validation etc)
			// and not only the lookup dependencies
			if (mLookup.getParameters().contains(changedColumnName))
			{
				log.debug(changedColumnName + " changed - " + gridField.getColumnName() + " set to null");

				// Invalidate current selection
				// gridField.validateValue();
				setFieldValue(gridField, null);
				// FIXME instead of reseting the value it would be better to reset only if the value is not valid anymore
			}
		}
	}

	private void validate()
	{
<<<<<<< HEAD
		final Set<String> missingMandatoryFields = new HashSet<String>();
=======
		final Set<String> missingMandatoryFields = new HashSet<>();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		final int fieldCount = getFieldCount();
		for (int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++)
		{
			final GridField gridField = getField(fieldIndex);
			if (!gridField.validateValue())
			{
				missingMandatoryFields.add(gridField.getHeader());
			}

			// Check for Range
			final GridField gridFieldTo = getFieldTo(fieldIndex);
			if (gridFieldTo != null && !gridFieldTo.validateValue())
			{
				missingMandatoryFields.add(gridField.getHeader());
			}    // range field
		}    // field loop

		if (!missingMandatoryFields.isEmpty())
		{
			throw new FillMandatoryException(true, missingMandatoryFields);
		}
	}

	public List<ProcessInfoParameter> createProcessInfoParameters()
	{
		validate();

		final List<ProcessInfoParameter> params = new ArrayList<>();
		final int fieldCount = getFieldCount();
		for (int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++)
		{
			final ProcessInfoParameter param = createProcessInfoParameter(fieldIndex);
			if (param == null)
			{
				continue;
			}
			params.add(param);
		} 	// for every parameter

		return params;
	}

	private ProcessInfoParameter createProcessInfoParameter(final int fieldIndex)
	{
		final GridField gridField = getField(fieldIndex);
		final String columnName = gridField.getColumnName();
		Object value = gridField.getValue();
		final String displayValue = displayValueProvider.getDisplayValue(gridField);

		final GridField gridFieldTo = getFieldTo(fieldIndex);
		Object valueTo;
		final String displayValueTo;
		if (gridFieldTo != null)
		{
			valueTo = gridFieldTo.getValue();
			displayValueTo = displayValueProvider.getDisplayValue(gridFieldTo);
		}
		else
		{
			valueTo = null;
			displayValueTo = null;
		}

		if (value == null && valueTo == null)
		{
			return null;
		}

		//
		// FIXME: legacy: convert Boolean to String because some of the JavaProcess implementations are checking boolean parametes as:
		// boolean value = "Y".equals(ProcessInfoParameter.getParameter());
		if (value instanceof Boolean)
		{
			value = DisplayType.toBooleanString((Boolean)value);
		}
		if (valueTo instanceof Boolean)
		{
			valueTo = DisplayType.toBooleanString((Boolean)valueTo);
		}

		return new ProcessInfoParameter(columnName, value, valueTo, displayValue, displayValueTo);
	}
}
