package de.metas.process.ui;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.security.asp.IASPFiltersFactory;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldVO;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.Lookup;
import org.compiere.model.Null;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.ProcessClassInfo;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;

public class ProcessParameterPanelModel
{
	private static final Logger log = LogManager.getLogger(ProcessParameterPanelModel.class);

	public static final String FIELDSEPARATOR_TEXT = " - ";

	@FunctionalInterface
	public static interface IDisplayValueProvider
	{
		String getDisplayValue(GridField gridField);
	}

	private final Properties ctx;
	private final int windowNo;
	private final int tabNo;
	private final int processId;
	private final ProcessClassInfo processClassInfo;
	private final List<GridField> gridFields = new ArrayList<GridField>();
	private final List<GridField> gridFieldsTo = new ArrayList<GridField>();
	private final List<GridField> gridFieldsAll = new ArrayList<GridField>();

	private static final IProcessDefaultParametersProvider NULL_DefaultPrametersProvider = (parameterName) -> null;
	/** Default values provider (never null) */
	private final IProcessDefaultParametersProvider defaultsProvider;

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
		this.ctx = Env.deriveCtx(parentCtx);
		this.windowNo = pi.getWindowNo();
		this.tabNo = pi.getTabNo();
		this.processId = pi.getAD_Process_ID();

		this.defaultsProvider = createProcessDefaultParametersProvider(pi.getClassName());
		this.processClassInfo = pi.getProcessClassInfo();

		createFields();
	}

	private static final IProcessDefaultParametersProvider createProcessDefaultParametersProvider(final String classname)
	{
		if (Check.isEmpty(classname, true))
		{
			return NULL_DefaultPrametersProvider;
		}

		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null)
			{
				classLoader = ProcessParameterPanelModel.class.getClassLoader();
			}

			final Class<?> processClass = classLoader.loadClass(classname);
			if (!IProcessDefaultParametersProvider.class.isAssignableFrom(processClass))
			{
				return NULL_DefaultPrametersProvider;
			}

			final IProcessDefaultParametersProvider defaultsProvider = (IProcessDefaultParametersProvider)processClass.newInstance();
			return defaultsProvider;

		}
		catch (Throwable e)
		{
			log.error("Failed instantiating class: {}", classname, e);
			return NULL_DefaultPrametersProvider;
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
			catch (final Exception e)
			{
				// ignore the error, but log it
				log.error("Failed retrieving the parameters default value from defaults provider: ParameterName={}, Provider={}", parameterName, defaultsProvider, e);
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

	private void validate()
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

	public List<ProcessInfoParameter> createProcessInfoParameters()
	{
		validate();

		final List<ProcessInfoParameter> params = new ArrayList<>();
		final int fieldCount = getFieldCount();
		for (int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++)
		{
			final ProcessInfoParameter param = createProcessInfoParameter(fieldIndex);
			if(param == null)
			{
				continue;
			}
			params.add(param);
		}	// for every parameter
		
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
		if(value instanceof Boolean)
		{
			value = DisplayType.toBooleanString((Boolean)value);
			}
		if(valueTo instanceof Boolean)
		{
			valueTo = DisplayType.toBooleanString((Boolean)valueTo);
			}

		return new ProcessInfoParameter(columnName, value, valueTo, displayValue, displayValueTo);
	}
}
