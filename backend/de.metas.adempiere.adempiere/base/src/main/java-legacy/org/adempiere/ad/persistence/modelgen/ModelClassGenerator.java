package org.adempiere.ad.persistence.modelgen;

import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ModelClassGenerator
{
	private static final Logger log = LogManager.getLogger(ModelClassGenerator.class);
	private static final String NL = "\n";
	private static final String PLACEHOLDER_serialVersionUID = "[*serialVersionUID*]";
	private static final Set<String> COLUMNNAMES_STANDARD = ImmutableSet.of("AD_Client_ID", "AD_Org_ID", "IsActive", "Created", "CreatedBy", "Updated", "UpdatedBy");

	private final TableAndColumnInfoRepository repository;
	private final String packageName;

	public ModelClassGenerator(
			@NonNull final TableAndColumnInfoRepository repository,
			@NonNull final TableInfo tableInfo,
			String directory,
			final String packageName)
	{
		this.repository = repository;
		this.packageName = packageName;

		// create column access methods
		final StringBuilder sb = createColumns(tableInfo);

		// Header
		final String tableName = createHeader(tableInfo, sb, packageName);

		// Save
		if (!directory.endsWith(File.separator))
		{
			directory += File.separator;
		}

		writeToFile(sb, directory + tableName + ".java");
	}

	/**
	 * Add Header info to buffer
	 *
	 * @return class name
	 */
	private String createHeader(
			@NonNull final TableInfo tableInfo,
			@NonNull final StringBuilder sb,
			@NonNull final String packageName)
	{
		final String tableName = tableInfo.getTableName();

		//
		final String keyColumn = tableName + "_ID";
		final String className = "X_" + tableName;
		//
		final StringBuilder start = new StringBuilder()
				.append(ModelInterfaceGenerator.COPY)
				.append("// Generated Model - DO NOT CHANGE").append(NL)
				.append("package ").append(packageName).append(";").append(NL)
				.append(NL);

		addImportClass(java.util.Properties.class);
		addImportClass(java.sql.ResultSet.class);
		addImportClass(javax.annotation.Nullable.class);
		createImports(start);
		// Class
		start.append("/** Generated Model for ").append(tableName).append(NL)
				.append(" *  @author metasfresh (generated) ").append(NL)
				.append(" */").append(NL)
				//.append("@SuppressWarnings(\"javadoc\")").append(NL) // commented out because it gives warnings in intelliJ
				.append("public class ").append(className)
				.append(" extends org.compiere.model.PO")
				.append(" implements I_").append(tableName)
				.append(", org.compiere.model.I_Persistent ")
				.append(NL)
				.append("{").append(NL)

				// serialVersionUID
				.append(NL)
				.append("\tprivate static final long serialVersionUID = ")
				.append(PLACEHOLDER_serialVersionUID) // generate serialVersionUID on save
				.append("L;").append(NL)

				// Standard Constructor
				.append(NL)
				.append("    /** Standard Constructor */").append(NL)
				.append("    public ").append(className).append(" (final Properties ctx, final int ").append(keyColumn).append(", @Nullable final String trxName)").append(NL)
				.append("    {").append(NL)
				.append("      super (ctx, ").append(keyColumn).append(", trxName);").append(NL)
				.append("    }").append(NL)
				// Constructor End

				// Load Constructor
				.append(NL)
				.append("    /** Load Constructor */").append(NL)
				.append("    public ").append(className).append(" (final Properties ctx, final ResultSet rs, @Nullable final String trxName)").append(NL)
				.append("    {").append(NL)
				.append("      super (ctx, rs, trxName);").append(NL)
				.append("    }").append(NL)
				// Load Constructor End
				.append(NL);
		if (ModelInterfaceGenerator.isGenerateLegacy())
		{
			start
					.append("    /** AccessLevel").append(NL)
					.append("      * @return ").append(tableInfo.getAccessLevel().getDescription()).append(NL)
					.append("      */").append(NL)
					.append("    @Override").append(NL)
					.append("    protected int get_AccessLevel()").append(NL)
					.append("    {").append(NL)
					.append("      return accessLevel.intValue();").append(NL)
					.append("    }").append(NL);
		}

		// initPO
		start.append(NL)
				.append("\t/** Load Meta Data */").append(NL)
				.append("\t@Override").append(NL)
				.append("\tprotected org.compiere.model.POInfo initPO(final Properties ctx)").append(NL)
				.append("\t{").append(NL)
				.append("\t\treturn org.compiere.model.POInfo.getPOInfo(Table_Name);").append(NL)
				.append("\t}").append(NL);

		//
		sb.insert(0, start);
		sb.append("}");

		return className;
	}

	/**
	 * Create Column access methods
	 *
	 * @return set/get method
	 */
	private StringBuilder createColumns(final TableInfo tableInfo)
	{
		final StringBuilder sb = new StringBuilder();

		boolean isKeyNamePairCreated = false; // true if the method "getKeyNamePair" is already generated
		final List<ColumnInfo> columnInfos = tableInfo.getColumnInfos();
		for (final ColumnInfo columnInfo : columnInfos)
		{
			// Skip standard columns because for those we already have methods in org.compiere.model.PO
			if (COLUMNNAMES_STANDARD.contains(columnInfo.getColumnName()))
			{
				continue;
			}

			// Create record info KeyNamePair
			if (columnInfo.getSeqNo() == 1 && columnInfo.isIdentifier())
			{
				if (!isKeyNamePairCreated)
				{
					sb.append(createKeyNamePair(columnInfo.getColumnName(), columnInfo.getDisplayType()));
					isKeyNamePairCreated = true;
				}
				else
				{
					throw new RuntimeException("More than one primary identifier found: " + columnInfo);
				}
			}

			sb.append(createColumnMethods(columnInfo));
		}

		return sb;
	}    // createColumns

	/**
	 * Create set/get methods for column
	 *
	 * @return set/get methods (java code)
	 */
	private String createColumnMethods(@NonNull final ColumnInfo columnInfo)
	{
		final DataTypeInfo dataTypeInfo = DataTypeInfo.ofColumnInfo(columnInfo);
		final String columnName = columnInfo.getColumnName();

		// Set ********
		String setValue = "\t\tset_Value";
		if (columnInfo.isEncrypted())
		{
			setValue = "\t\tset_ValueE";
		}
		// Handle isUpdateable
		if (!columnInfo.isUpdatable())
		{
			setValue = "\t\tset_ValueNoCheck";
			if (columnInfo.isEncrypted())
			{
				setValue = "\t\tset_ValueNoCheckE";
			}
		}

		final StringBuilder sb = new StringBuilder();

		// TODO - New functionality
		// 1) Must understand which class to reference
		if (DisplayType.isID(dataTypeInfo.getDisplayType()) && !columnInfo.isKey())
		{
			final String fieldName = ModelInterfaceGenerator.getFieldName(columnName);
			final String referenceClassName = ModelInterfaceGenerator.getReferenceClassName(columnInfo, repository);
			//
			if (ModelInterfaceGenerator.isGenerateModelGetterOrSetterForReferencedClassName(referenceClassName))
			{
				//
				// Model Getter
				sb.append(NL)
						.append("\t@Override").append(NL)
						.append("\tpublic ").append(referenceClassName).append(" get").append(fieldName).append("()").append(NL)
						.append("\t{").append(NL)
						.append("\t\treturn get_ValueAsPO(COLUMNNAME_").append(columnName).append(", ").append(referenceClassName).append(".class);").append(NL)
						.append("\t}").append(NL);

				//
				// Model Setter
				sb.append(NL)
						.append("\t@Override").append(NL)
						.append("\tpublic void set").append(fieldName).append("(final ").append(referenceClassName).append(" ").append(fieldName).append(")").append(NL)
						.append("\t{").append(NL)
						.append("\t\tset_ValueFromPO(COLUMNNAME_").append(columnName).append(", ").append(referenceClassName).append(".class, ").append(fieldName).append(");").append(NL)
						.append("\t}").append(NL);
			}
		}

		//
		// Handle AD_Table_ID/Record_ID generic model reference
		if (!Check.isEmpty(columnInfo.getTableIdColumnName(), true))
		{
			final String fieldName = ModelInterfaceGenerator.getFieldName(columnName);
			Check.assume("Record".equals(fieldName), "Generic reference field name shall be 'Record'");

			sb.append(NL)
					.append("\t@Override").append(NL)
					.append("\tpublic <RecordType> RecordType get").append(fieldName).append("(final Class<RecordType> recordType)").append(NL)
					.append("\t{").append(NL)
					.append("\t\t return getReferencedRecord(recordType);").append(NL)
					.append("\t}").append(NL);

			sb.append(NL)
					.append("\t@Override").append(NL)
					.append("\tpublic <RecordType> void set").append(fieldName).append("(final RecordType record)").append(NL)
					.append("\t{").append(NL)
					.append("\t\t setReferencedRecord(record);").append(NL)
					.append("\t}").append(NL);

			sb.append(NL);
		}

		//
		// Setter
		sb.append(NL);
		sb.append("\t@Override").append(NL);
		sb.append("\tpublic void set").append(columnName).append(" (final ").append(dataTypeInfo.getJavaCode()).append(" ").append(columnName).append(")").append(NL)
				.append("\t{").append(NL);
		// List Validation
		if (columnInfo.getAdReferenceId() > 0
				&& dataTypeInfo.isString()
				&& columnInfo.getListInfo().isPresent())
		{
			final String staticVar = ADRefListGenerator.newInstance()
					.setColumnName(columnInfo.getColumnName())
					.setListInfo(columnInfo.getListInfo().get())
					.generateConstants();
			sb.insert(0, staticVar);
		}
		// setValue ("ColumnName", xx);
		if (columnInfo.isVirtualColumn())
		{
			sb.append("\t\tthrow new IllegalArgumentException (\"").append(columnName).append(" is virtual column\");");
		}
		// Integer
		else if (dataTypeInfo.isInteger())
		{
			if (columnName.endsWith("_ID"))
			{
				final int firstValidId = InterfaceWrapperHelper.getFirstValidIdByColumnName(columnName);
				// set _ID to null if < 0 for special column or < 1 for others
				sb.append("\t\tif (").append(columnName).append(" < ").append(firstValidId).append(") ").append(NL)
						.append("\t").append(setValue).append(" (").append("COLUMNNAME_").append(columnName).append(", null);").append(NL)
						.append("\t\telse ").append(NL).append("\t");
			}
			sb.append(setValue).append(" (").append("COLUMNNAME_").append(columnName).append(", ").append(columnName).append(");").append(NL);
		}
		// Boolean
		else if (dataTypeInfo.isBoolean())
		{
			sb.append(setValue).append(" (").append("COLUMNNAME_").append(columnName).append(", ").append(columnName).append(");").append(NL);
		}
		else
		{
			sb.append(setValue).append(" (").append("COLUMNNAME_").append(columnName).append(", ").append(columnName).append(");").append(NL);
		}
		sb.append("\t}").append(NL);

		//
		// Getter
		sb.append(NL);
		sb.append("\t@Override").append(NL);
		sb.append("\tpublic ").append(dataTypeInfo.getJavaCode());
		if (dataTypeInfo.isBoolean())
		{
			sb.append(" is");
			if (columnName.toLowerCase().startsWith("is"))
			{
				sb.append(columnName.substring(2));
			}
			else
			{
				sb.append(columnName);
			}
		}
		else
		{
			sb.append(" get").append(columnName);
		}
		sb.append("() ").append(NL)
				.append("\t{").append(NL)
				.append("\t\t");
		if (dataTypeInfo.isInteger())
		{
			sb.append("return get_ValueAsInt(").append("COLUMNNAME_").append(columnName).append(");").append(NL);
		}
		else if (dataTypeInfo.isBigDecimal())
		{
			Check.assumeEquals(dataTypeInfo.getNullableValueGetter(), NullableType.NON_NULL, "BigDecimal returning methods shall always return non null: {}", dataTypeInfo);

			sb.append("final BigDecimal bd = get_ValueAsBigDecimal(").append("COLUMNNAME_").append(columnName).append(");").append(NL);
			sb.append("\t\treturn bd != null ? bd : BigDecimal.ZERO;").append(NL);
			addImportClass(java.math.BigDecimal.class);
		}
		else if (dataTypeInfo.isBoolean())
		{
			sb.append("return get_ValueAsBoolean(").append("COLUMNNAME_").append(columnName).append(");").append(NL);
		}
		else if (dataTypeInfo.isTimestamp())
		{
			sb.append("return get_ValueAsTimestamp(").append("COLUMNNAME_").append(columnName).append(");").append(NL);
		}
		else if (dataTypeInfo.isObject())
		{
			final String getValue = columnInfo.isEncrypted() ? "get_ValueE" : "get_Value";
			sb.append("\t\treturn ").append(getValue).append("(").append("COLUMNNAME_").append(columnName).append(");").append(NL);
		}
		else if (dataTypeInfo.isString())
		{
			sb.append("return get_ValueAsString(").append("COLUMNNAME_").append(columnName).append(");").append(NL);
		}
		else
		{
			final String getValue = columnInfo.isEncrypted() ? "get_ValueE" : "get_Value";
			sb.append("return (").append(dataTypeInfo.getJavaCode()).append(")").append(getValue).append("(").append("COLUMNNAME_").append(columnName).append(");").append(NL);
		}
		sb.append("\t}").append(NL);
		//
		return sb.toString();
	}

	/**
	 * Create getKeyNamePair() method with first identifier
	 *
	 * @param columnName  name
	 * @param displayType int
	 * @return method code
	 */
	private StringBuilder createKeyNamePair(
			final String columnName,
			final int displayType)
	{
		if (!ModelInterfaceGenerator.isGenerateLegacy())
		{
			return new StringBuilder();
		}

		String method = "get" + columnName + "()";
		if (displayType != DisplayType.String)
		{
			method = "String.valueOf(" + method + ")";
		}

		return new StringBuilder(NL)
				.append("    /** Get Record ID/ColumnName").append(NL)
				.append("        @return ID/ColumnName pair").append(NL)
				.append("      */").append(NL)
				.append("    public org.compiere.util.KeyNamePair getKeyNamePair() ").append(NL)
				.append("    {").append(NL)
				.append("        return org.compiere.util.KeyNamePair.of(get_ID(), ").append(method).append(");").append(NL)
				.append("    }").append(NL);
	}    // createKeyNamePair

	/**************************************************************************
	 * Write to file
	 *
	 * @param sb string buffer
	 * @param fileName file name
	 */
	private void writeToFile(
			StringBuilder sb,
			final String fileName)
	{
		// Generate serial number
		{
			String s = sb.toString();
			final int hash = s.hashCode();
			s = s.replace(PLACEHOLDER_serialVersionUID, String.valueOf(hash));
			sb = new StringBuilder(s);
			System.out.println("" + fileName + ": hash=" + hash);
		}

		try
		{
			final File out = new File(fileName);
			final Writer fw = new OutputStreamWriter(new FileOutputStream(out, false), StandardCharsets.UTF_8);
			for (int i = 0; i < sb.length(); i++)
			{
				final char c = sb.charAt(i);
				// after
				if (c == ';' || c == '}')
				{
					fw.write(c);
				}
				// before & after
				else if (c == '{')
				{
					// fw.write(NL);
					fw.write(c);
					// fw.write(NL);
				}
				else
				{
					fw.write(c);
				}
			}
			fw.flush();
			fw.close();
			float size = out.length();
			size /= 1024;
			log.info(out.getAbsolutePath() + " - " + size + " kB");
		}
		catch (final Exception ex)
		{
			log.error(fileName, ex);
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Import classes
	 */
	private final Collection<String> s_importClasses = new TreeSet<>();

	/**
	 * Add class name to class import list
	 */
	private void addImportClass(final String className)
	{
		if (className == null
				|| (className.startsWith("java.lang.") && !className.startsWith("java.lang.reflect."))
				|| className.startsWith(packageName + "."))
		{
			return;
		}
		for (final String name : s_importClasses)
		{
			if (className.equals(name))
			{
				return;
			}
		}
		s_importClasses.add(className);
	}

	/**
	 * Add class to class import list
	 */
	private void addImportClass(Class<?> cl)
	{
		if (cl.isArray())
		{
			cl = cl.getComponentType();
		}
		if (cl.isPrimitive())
		{
			return;
		}
		addImportClass(cl.getCanonicalName());
	}

	/**
	 * Generate java imports
	 */
	private void createImports(final StringBuilder sb)
	{
		for (final String name : s_importClasses)
		{
			sb.append("import ").append(name).append(";").append(NL);
		}
		sb.append(NL);
	}
}
