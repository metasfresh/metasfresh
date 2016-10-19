package org.adempiere.ad.process;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.security.asp.IASPFiltersFactory;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldVO;
import org.compiere.model.I_AD_PInstance_Para;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.Lookup;
import org.compiere.model.Null;
import org.compiere.process.ProcessClassInfo;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

public class ProcessParameterPanelModel
{
	private static final Logger log = LogManager.getLogger(ProcessParameterPanelModel.class);

	public static final String FIELDSEPARATOR_TEXT = " - ";

	public static interface IDisplayValueProvider
	{
		String getDisplayValue(GridField gridField);
	}

	private final Properties ctx;
	private final int windowNo;
	private final int tabNo;
	private final int processId;
	private final ProcessInfo processInfo;
	private final ProcessClassInfo processClassInfo;
	private final List<GridField> gridFields = new ArrayList<GridField>();
	private final List<GridField> gridFieldsTo = new ArrayList<GridField>();
	private final List<GridField> gridFieldsAll = new ArrayList<GridField>();

	private IDisplayValueProvider displayValueProvider = null;
	private final ISvrProcessDefaultParametersProvider defaultsProvider;


	/**
	 * Dynamic generated Parameter panel.
	 *
	 * @param parentCtx parent context
	 * @param pi process info, used ONLY to get AD_Process_ID, WindowNo and TabNo; no reference is stored to it
	 */
	public ProcessParameterPanelModel(final Properties parentCtx, final ProcessInfo pi)
	{
		super();

		// NOTE: we are using same WindowNo/TabNo as the calling window/tab but we will create a shadow context just to not alter the current context
		this.ctx = Env.deriveCtx(parentCtx);
		this.windowNo = pi.getWindowNo();
		this.tabNo = pi.getTabNo();
		this.processId = pi.getAD_Process_ID();

		this.defaultsProvider = createSvrProcessDefaultParametersProviderOrNull(pi.getClassName());

		Check.assumeNotNull(pi, "pi not null");
		this.processInfo = pi;
		this.processClassInfo = pi.getProcessClassInfo();

		createFields();
	}

	private static final ISvrProcessDefaultParametersProvider createSvrProcessDefaultParametersProviderOrNull(final String classname)
	{
		if (Check.isEmpty(classname, true))
		{
			return null;
		}

		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null)
			{
				classLoader = ProcessParameterPanelModel.class.getClassLoader();
			}

			final Class<?> processClass = classLoader.loadClass(classname);
			if (!ISvrProcessDefaultParametersProvider.class.isAssignableFrom(processClass))
			{
				return null;
			}

			final ISvrProcessDefaultParametersProvider defaultsProvider = (ISvrProcessDefaultParametersProvider)processClass.newInstance();
			return defaultsProvider;

		}
		catch (Throwable e)
		{
			log.error("Failed instantiating class: " + classname, e);
			return null;
		}
	}

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
		// ASP
		// NOTE: if you are going to change the "p." alias for AD_Process_Para, pls check the ASPFilters implementation.
		final String ASPFilter = Services.get(IASPFiltersFactory.class)
				.getASPFiltersForClient(Env.getAD_Client_ID(ctx))
				.getSQLWhereClause(I_AD_Process_Para.class);

		//
		final String sql;
		if (Env.isBaseLanguage(ctx, "AD_Process_Para"))
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
					+ ASPFilter + " ORDER BY SeqNo";
		else
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
					+ ASPFilter + " ORDER BY SeqNo";

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
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	public int getFieldCount()
	{
		return gridFields.size();
	}

	public GridField getField(int index)
	{
		return gridFields.get(index);
	}

	public GridField getFieldTo(int index)
	{
		return gridFieldsTo.get(index);
	}

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

	public void setDefaultValues()
	{
		final int size = getFieldCount();
		for (int index = 0; index < size; index++)
		{
			final GridField field = getField(index);
			setDefaultValue(field);

			final GridField fieldTo = getFieldTo(index);
			if (fieldTo != null)
			{
				setDefaultValue(fieldTo);
			}
		}
	}

	private void setDefaultValue(final GridField gridField)
	{
		Object defaultValue = null;
		if (defaultsProvider != null)
		{
			final String parameterName = gridField.getColumnName();
			try
			{
				defaultValue = defaultsProvider.getParameterDefaultValue(gridField);
			}
			catch (Exception e)
			{
				// ignore the error, but log it
				log.error("Failed retrieving the parameters default value from defaults provider: ParameterName=" + parameterName + ", Provider=" + defaultsProvider, e);
			}
		}
		if (defaultValue == null)
		{
			defaultValue = gridField.getDefault();
		}

		setFieldValue(gridField, defaultValue == Null.NULL ? null : defaultValue);
	}

	public void setDisplayValueProvider(final IDisplayValueProvider displayValueProvider)
	{
		this.displayValueProvider = displayValueProvider;
	}

	private String getDisplayValue(final GridField gridField)
	{
		if (displayValueProvider == null)
		{
			return null;
		}

		return displayValueProvider.getDisplayValue(gridField);
	}

	/**
	 * Create Field. - creates Fields and adds it to m_mFields list - creates Editor and adds it to m_vEditors list Handles Ranges by adding additional mField/vEditor.
	 * <p>
	 * mFields are used for default value and mandatory checking; vEditors are used to retrieve the value (no data binding)
	 *
	 * @param rs result set
	 */
	private void createField(final ResultSet rs)
	{
		// Create Field
		final GridFieldVO gridFieldVO = GridFieldVO.createParameter(ctx, windowNo, tabNo, rs);
		final boolean mandatoryOverride = processClassInfo.isParameterMandatory(gridFieldVO.getColumnName());
		if (mandatoryOverride)
		{
			gridFieldVO.setMandatory(true);
		}
		final GridField gridField = new GridField(gridFieldVO);
		gridFields.add(gridField);                      // add to Fields
		gridFieldsAll.add(gridField);

		//
		final GridField gridFieldTo;
		if (gridFieldVO.isRange)
		{
			final GridFieldVO gridFieldToVO = GridFieldVO.createParameterTo(gridFieldVO);
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
		gridFieldsTo.add(gridFieldTo);
		if (gridFieldTo != null)
		{
			gridFieldsAll.add(gridFieldTo);
		}
	}	// createField

	/**
	 * Notify the model that an editor value was changed.
	 *
	 * NOTE: this is used to do View to Model binding
	 *
	 * @param columnName
	 * @param valueNew
	 * @param displayValueNew
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
	private final Set<String> notifyValueChanged_CurrentColumnNames = new HashSet<String>();

	public void setFieldValue(GridField gridField, Object valueNew)
	{
		gridField.setValue(valueNew, true); // inserting=true(always)
	}

	/**
	 * Validate given <code>gridField</code> when <code>changedColumnName</code> was changed.
	 *
	 * @param gridField
	 * @param changedColumnName
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
				//gridField.validateValue();
				setFieldValue(gridField, null);
				// FIXME instead of reseting the value it would be better to reset only if the value is not valid anymore
			}
		}
	}

	public void validate()
	{
		final Set<String> missingMandatoryFields = new HashSet<String>();

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
			}   // range field
		}   // field loop

		if (!missingMandatoryFields.isEmpty())
		{
			throw new FillMandatoryException(true, missingMandatoryFields);
		}
	}

	public void saveParameters(final int adPInstanceId)
	{
		validate();
		Services.get(ITrxManager.class).run(new TrxRunnable()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				saveParameters(adPInstanceId, localTrxName);
			}
		});
	}

	private void saveParameters(final int adPInstanceId, final String trxName)
	{
		final int fieldCount = getFieldCount();
		for (int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++)
		{
			saveParameter(adPInstanceId, fieldIndex, trxName);
		}	// for every parameter
	}

	private void saveParameter(final int adPInstanceId, final int fieldIndex, final String trxName)
	{
		final GridField gridField = getField(fieldIndex);
		final String columnName = gridField.getColumnName();
		final Object value = gridField.getValue();
		final String displayValue = getDisplayValue(gridField);

		final GridField gridFieldTo = getFieldTo(fieldIndex);
		final Object valueTo;
		final String displayValueTo;
		if (gridFieldTo != null)
		{
			valueTo = gridFieldTo.getValue();
			displayValueTo = getDisplayValue(gridFieldTo);
		}
		else
		{
			valueTo = null;
			displayValueTo = null;
		}

		if (value == null && valueTo == null)
		{
			return;
		}

		final I_AD_PInstance_Para para = InterfaceWrapperHelper.create(ctx, I_AD_PInstance_Para.class, ITrx.TRXNAME_None);
		para.setAD_PInstance_ID(adPInstanceId);
		para.setSeqNo(fieldIndex);
		para.setParameterName(columnName);

		final boolean hasValueTo = valueTo != null;

		// Date
		if (value instanceof Timestamp || valueTo instanceof Timestamp)
		{
			para.setP_Date((Timestamp)value);
			if (hasValueTo)
			{
				para.setP_Date_To((Timestamp)valueTo);
			}
		}
		// Integer
		else if (value instanceof Integer || valueTo instanceof Integer)
		{
			if (value != null)
			{
				final Integer ii = (Integer)value;
				final BigDecimal valueBD = BigDecimal.valueOf(ii);
				para.setP_Number(valueBD);
			}
			if (hasValueTo)
			{
				final Integer ii = (Integer)valueTo;
				final BigDecimal valueBD = BigDecimal.valueOf(ii);
				para.setP_Number_To(valueBD);
			}
		}
		// BigDecimal
		else if (value instanceof BigDecimal || valueTo instanceof BigDecimal)
		{
			para.setP_Number((BigDecimal)value);
			if (hasValueTo)
			{
				para.setP_Number_To((BigDecimal)valueTo);
			}
		}
		// Boolean
		else if (value instanceof Boolean)
		{
			final Boolean valueBool = (Boolean)value;
			final String valueStr = valueBool.booleanValue() ? "Y" : "N";
			para.setP_String(valueStr);
			// Setting fieldTo does not make sense
		}
		// String
		else
		{
			if (value != null)
			{
				para.setP_String(value.toString());
			}
			if (hasValueTo)
			{
				para.setP_String_To(valueTo.toString());
			}
		}

		// Info
		para.setInfo(displayValue);
		para.setInfo_To(displayValueTo);
		//
		InterfaceWrapperHelper.save(para);
		log.debug(para.toString());
	}
}
