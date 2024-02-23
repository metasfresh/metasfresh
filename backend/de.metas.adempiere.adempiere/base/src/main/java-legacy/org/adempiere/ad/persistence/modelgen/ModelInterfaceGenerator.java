package org.adempiere.ad.persistence.modelgen;

import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.persistence.EntityTypesCache;
import org.adempiere.model.ModelColumn;
import org.adempiere.util.ClassnameScanner;
import org.compiere.model.MQuery;
import org.compiere.util.DisplayType;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Trifon Trifonov
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <li>BF [ 1781629 ] Don't use Env.NL in model class/interface generators
 * <li>FR [ 1781630 ] Generated class/interfaces have a lot of unused imports
 * <li>BF [
 * 1781632 ] Generated class/interfaces should be UTF-8
 * <li>better formating of generated source
 * <li>BF [ 1787833 ] ModelInterfaceGenerator: don't write timestamp
 * <li>FR [ 1803309 ] Model
 * generator: generate get method for Search cols
 * <li>BF [ 1817768 ] Isolate hardcoded table direct columns https://sourceforge.net/tracker/?func=detail&atid=879332&aid=1817768&group_id=176962
 * <li>FR [ 2343096 ] Model Generator: Improve Reference Class Detection
 * <li>BF [ 2528434 ] ModelInterfaceGenerator: generate getters for common fields
 * <li>--
 * <li>FR [ 2848449 ]
 * ModelClassGenerator: Implement model getters https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2848449&group_id=176962
 * @author Teo Sarca, teo.sarca@gmail.com
 * <li>FR [ 3020635 ] Model Generator should use FQ class names https://sourceforge.net/tracker/?func=detail&aid=3020635&group_id=176962&atid=879335
 * @author Victor Perez, e-Evolution
 * <li>FR [ 1785001 ] Using ModelPackage of EntityType to Generate Model Class
 * @version $Id$
 */
public class ModelInterfaceGenerator
{
	public static final String NL = "\n";

	/**
	 * File Header
	 */
	// NOTE: we are not appending ANY license to generated files because we assume this will be done automatically by a maven plugin.
	public static final String COPY = "";

	public static final String PROPERTY_Legacy = "generate.legacy";

	private static final transient Logger log = LogManager.getLogger(ModelInterfaceGenerator.class);

	private static final String DEPRECATED_MSG_SetterForVirtualColumn = "Please don't use it because this is a virtual column";
	private static final String DEPRECATED_MSG_GetterForLazyLoadingColumn = "Please don't use it because this is a lazy loading column and it might affect the performances";

	private static final ImmutableSet<String> SKIP_InterfaceModelGettersForColumnNames = ImmutableSet.<String>builder()
			.add("AD_Client_ID")
			.add("AD_Org_ID")
			.add("CreatedBy")
			.add("UpdatedBy")
			.build();

	private static final ImmutableSet<String> SKIP_ModelGettersAndSettersForReferencedClassNames = ImmutableSet.<String>builder()
			.add("org.compiere.model.I_AD_Client")
			.add("org.compiere.model.I_AD_Org")
			//
			.add("org.compiere.model.I_C_BPartner")
			.add("org.compiere.model.I_C_BPartner_Location")
			.add("org.compiere.model.I_AD_User")
			.add("org.compiere.model.I_C_Greeting")
			//
			.add("org.compiere.model.I_C_UOM")
			.add("org.compiere.model.I_M_Product")
			.add("org.compiere.model.I_M_Product_Category")
			//
			.add("org.compiere.model.I_M_PricingSystem")
			.add("org.compiere.model.I_M_PriceList")
			.add("org.compiere.model.I_M_PriceList_Version")
			//
			.add("org.compiere.model.I_M_Warehouse")
			.add("org.compiere.model.I_M_Locator")
			//
			.add("org.compiere.model.I_C_TaxCategory")
			.add("org.compiere.model.I_C_Tax")
			//
			.add("org.compiere.model.I_C_PaymentTerm")
			//
			.add("org.compiere.model.I_C_Currency")
			.add("org.compiere.model.I_C_ConversionType")
			//
			.add("org.compiere.model.I_C_DocType")
			.add("org.compiere.model.I_M_Attribute")
			.add("org.compiere.model.I_M_AttributeValue")
			//
			.add("de.metas.handlingunits.model.I_M_HU_PI_Attribute")
			.add("de.metas.handlingunits.model.I_M_HU_PI_Item_Product")
			//
			.add("org.compiere.model.I_C_Project")
			//
			.add("org.compiere.model.I_AD_Note")
			.add("org.compiere.model.I_AD_Table")
			.add("org.compiere.model.I_AD_Message")
			.add("org.compiere.model.I_AD_Issue")
			.add("org.compiere.model.I_C_Activity")
			.add("org.compiere.model.I_C_Charge")
			//
			.add("org.compiere.model.I_C_Payment")
			.add("org.compiere.model.I_C_BankStatement")
			.add("org.compiere.model.I_C_BankStatementLine")
			.add("de.metas.banking.model.I_C_BankStatementLine_Ref")
			.add("org.compiere.model.I_C_BP_BankAccount")
			.add("org.compiere.model.I_C_Bank")
			//
			.add("org.compiere.model.I_AD_WF_Activity")
			.add("org.compiere.model.I_AD_WF_ActivityResult")
			.add("org.compiere.model.I_AD_WF_Block")
			.add("org.compiere.model.I_AD_WF_EventAudit")
			.add("org.compiere.model.I_AD_WF_NextCondition")
			.add("org.compiere.model.I_AD_Workflow")
			.add("org.compiere.model.I_AD_WF_Node")
			.add("org.compiere.model.I_AD_WF_Node_Para")
			.add("org.compiere.model.I_AD_WF_Node_Template")
			.add("org.compiere.model.I_AD_WF_NodeNext")
			.add("org.compiere.model.I_AD_WF_Process")
			.add("org.compiere.model.I_AD_WF_ProcessData")
			.add("org.compiere.model.I_AD_WF_Responsible")
			//
			.add("org.compiere.model.I_C_Element")
			//
			.add("org.eevolution.model.I_PP_Product_Planning")
			//
			.build();

	private final TableAndColumnInfoRepository repository;
	private final String packageName;

	public ModelInterfaceGenerator(
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
		final String tableName = createHeader(tableInfo, sb);

		// Save
		if (directory.endsWith("/") || directory.endsWith("\\"))
		{
		}
		else
		{
			directory = directory + "/";
		}
		writeToFile(sb, directory + tableName + ".java");
	}

	private String createHeader(
			@NonNull final TableInfo tableInfo,
			@NonNull final StringBuilder sb)
	{
		final String tableName = tableInfo.getTableName();

		//
		final String className = "I_" + tableName;
		//
		final StringBuilder start = new StringBuilder()
				.append(COPY)
				.append("package ").append(packageName).append(";").append(NL);

		createImports(start);
		// Interface
		start.append("/** Generated Interface for ").append(tableName).append("\n")
				.append(" *  @author metasfresh (generated) \n")
				.append(" */\n")
				//.append("@SuppressWarnings(\"javadoc\")\n")  // commented out because it gives warnings in intelliJ
				.append("@SuppressWarnings(\"unused\")\n")
				.append("public interface ").append(className).append(" {").append("\n")

				.append("\tString Table_Name = \"").append(tableName).append("\";\n");

		//
		// AD_Table_ID
		start
				.append(isGenerateLegacy() ? "" : "//") // metas
				.append("\t/** AD_Table_ID=").append(tableInfo.getAdTableId()).append(" */\n")
				.append(isGenerateLegacy() ? "" : "//") // metas
				.append("\tint Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);\n");

		sb.insert(0, start);
		sb.append("}");

		return className;
	}

	/**
	 * Create Column access methods
	 *
	 * @return set/get method
	 */
	private StringBuilder createColumns(
			final TableInfo tableInfo)
	{
		final StringBuilder sb = new StringBuilder();

		final List<ColumnInfo> columnInfos = tableInfo.getColumnInfos();
		for (final ColumnInfo columnInfo : columnInfos)
		{
			if (columnInfo.isRestAPICustomColumn())
			{
				continue;
			}
			sb.append(createColumnMethods(columnInfo));
		}

		return sb;
	}

	private String createColumnConstants(
			@NonNull final String tableName,
			@NonNull final String columnName,
			@Nullable final String referenceClassName)
	{
		final String modelClassname = "I_" + tableName;

		final StringBuilder sb = new StringBuilder();

		//
		// Add: COLUMN_ColumnName = new ModelColumn...
		if (!SKIP_ModelGettersAndSettersForReferencedClassNames.contains(referenceClassName))
		{
			addImportClass(ModelColumn.class);

			// e.g. ModelColumn<I_C_Invoice, I_C_BPartner>
			final String modelColumnClassname = ModelColumn.class.getSimpleName() + "<" + modelClassname + ", " + (referenceClassName == null ? "Object" : referenceClassName) + ">";

			sb.append("\t").append(modelColumnClassname).append(" COLUMN_").append(columnName)
					//
					// e.g. new ModelColumn<I_C_Invoice, I_C_BPartner>
					.append(" = new ").append(ModelColumn.class.getSimpleName()).append("<>")
					//
					// e.g. (I_C_Invoice.class, columnName, I_C_BPartner.class);
					.append("(")
					.append(modelClassname).append(".class")
					.append(", \"").append(columnName).append("\"")
					.append(", ").append(referenceClassName == null ? "null" : referenceClassName + ".class")
					.append(");");
		}

		//
		// Add: COLUMNNAME_ColumnName = ...
		sb.append("\tString COLUMNNAME_").append(columnName)
				.append(" = \"").append(columnName).append("\";");

		return sb.toString();
	}

	/**
	 * Create set/get methods for column
	 *
	 * @return set/get method
	 */
	@NonNull
	private String createColumnMethods(@NonNull final ColumnInfo columnInfo)
	{
		final DataTypeInfo dataTypeInfo = DataTypeInfo.ofColumnInfo(columnInfo);

		final StringBuilder sb = new StringBuilder();

		//
		// Setter
		if (isGenerateSetter(columnInfo.getColumnName()))
		{
			String deprecatedSetter = null;
			if (columnInfo.isVirtualColumn())
			{
				deprecatedSetter = DEPRECATED_MSG_SetterForVirtualColumn;
			}

			final NullableType nullableType = dataTypeInfo.getNullableValueSetter();
			addImportClasses(nullableType.getClassesToImport());
			addImportClass(dataTypeInfo);

			generateJavaComment(sb, columnInfo, "Set", deprecatedSetter);
			appendDeprecatedIfNotNull(sb, deprecatedSetter);
			sb.append("\tvoid set").append(columnInfo.getColumnName()).append(" (")
					.append(nullableType.getJavaCode())
					.append(dataTypeInfo.getJavaCode()).append(" ").append(columnInfo.getColumnName()).append(");");
		}

		//
		// Getter
		{
			String deprecatedGetter = null;
			if (columnInfo.isVirtualColumn() && columnInfo.isLazyLoading())
			{
				deprecatedGetter = DEPRECATED_MSG_GetterForLazyLoadingColumn;
			}

			final NullableType nullableType = dataTypeInfo.getNullableValueGetter();
			addImportClasses(nullableType.getClassesToImport());
			addImportClass(dataTypeInfo);

			generateJavaComment(sb, columnInfo, "Get", deprecatedGetter);
			appendDeprecatedIfNotNull(sb, deprecatedGetter);
			sb.append("\t")
					.append(nullableType.getJavaCode())
					.append(dataTypeInfo.getJavaCode());
			if (dataTypeInfo.isBoolean())
			{
				sb.append(" is");
				if (columnInfo.getColumnName().toLowerCase().startsWith("is"))
				{
					sb.append(columnInfo.getColumnName().substring(2));
				}
				else
				{
					sb.append(columnInfo.getColumnName());
				}
			}
			else
			{
				sb.append(" get").append(columnInfo.getColumnName());
			}
			sb.append("();");
		}
		//

		//
		// Model Getter & Setter
		final String referenceClassName = getReferenceClassName(columnInfo, repository);
		if (isGenerateInterfaceModelGetterForColumnName(columnInfo.getColumnName())
				&& isGenerateModelGetterOrSetterForReferencedClassName(referenceClassName)
				&& DisplayType.isID(columnInfo.getDisplayType())
				&& !columnInfo.isKey())
		{
			final String fieldName = getFieldName(columnInfo.getColumnName());

			//
			// Model getter
			{
				String deprecatedGetter = null;
				if (columnInfo.isVirtualColumn() && columnInfo.isLazyLoading())
				{
					deprecatedGetter = DEPRECATED_MSG_GetterForLazyLoadingColumn;
				}

				final NullableType nullableType = dataTypeInfo.getNullableValueObject();
				addImportClasses(nullableType.getClassesToImport());

				sb.append("\n");
				appendDeprecatedIfNotNull(sb, deprecatedGetter);
				sb.append("\t")
						.append(nullableType.getJavaCode())
						.append(referenceClassName).append(" get").append(fieldName).append("();");
			}

			//
			// Model setter
			if (isGenerateSetter(columnInfo.getColumnName()))
			{
				String deprecatedSetter = null;
				if (columnInfo.isVirtualColumn())
				{
					deprecatedSetter = DEPRECATED_MSG_SetterForVirtualColumn;
				}

				final NullableType nullableType = dataTypeInfo.getNullableValueObject();
				addImportClasses(nullableType.getClassesToImport());

				sb.append("\n");
				appendDeprecatedIfNotNull(sb, deprecatedSetter);
				sb.append("\tvoid set").append(fieldName).append("(")
						.append(nullableType.getJavaCode())
						.append(referenceClassName).append(" ").append(fieldName)
						.append(");");
			}
		}

		//
		// Handle AD_Table_ID/Record_ID generic model reference
		if (!Check.isEmpty(columnInfo.getTableIdColumnName(), true))
		{
			final String fieldName = getFieldName(columnInfo.getColumnName());
			sb.append("\n").append("\t<ReferenceType> ReferenceType get").append(fieldName).append("(Class<ReferenceType> referenceType);");

			sb.append("\n").append("\t<ReferenceType> void set").append(fieldName).append("(ReferenceType referenceModel);");
		}

		//
		// Column related constants (e.g. COLUMNNAME_)
		sb.append("\n");
		sb.append(createColumnConstants(columnInfo.getTableName(), columnInfo.getColumnName(), referenceClassName));

		return sb.toString();
	}

	private void generateJavaComment(
			@NonNull final StringBuilder result,
			@NonNull final ColumnInfo columnInfo,
			@NonNull final String startOfComment,
			@Nullable final String deprecated
	)
	{
		final String propertyName = columnInfo.getName();

		result.append("\n")
				.append("\t/**")
				.append("\n\t * ").append(startOfComment).append(" ").append(propertyName).append(".");

		//
		// Description
		final String description = columnInfo.getDescription();
		if (description != null && description.length() > 0)
		{
			result.append("\n\t * ").append(description.trim());
		}

		result.append("\n\t *");

		//
		// Type
		result.append("\n\t * <br>Type: ").append(DisplayType.getDescription(columnInfo.getDisplayType()));

		//
		// Mandatory:
		result.append("\n\t * <br>Mandatory: ").append(columnInfo.isMandatory());

		//
		// Virtual Column:
		result.append("\n\t * <br>Virtual Column: ").append(columnInfo.isVirtualColumn());
		if (columnInfo.isLazyLoading())
		{
			result.append(" (lazy loading)");
		}

		if (deprecated != null)
		{
			result.append("\n\t * @deprecated ").append(deprecated);
		}

		//
		result.append("\n\t */\n");
	}

	private void appendDeprecatedIfNotNull(
			@NonNull final StringBuilder result,
			@Nullable final String deprecated)
	{
		if (deprecated == null)
		{
			return;
		}

		result.append("\t@Deprecated\n");
	}

	/*
	 * Write to file
	 *
	 * @param sb string buffer
	 *
	 * @param fileName file name
	 */
	private void writeToFile(
			final StringBuilder sb,
			final String fileName)
	{
		try
		{
			final File out = new File(fileName);
			out.getParentFile().mkdirs(); // make sure directory exists

			final Writer fw = new OutputStreamWriter(new FileOutputStream(out, false), StandardCharsets.UTF_8);
			for (int i = 0; i < sb.length(); i++)
			{
				final char c = sb.charAt(i);
				// after
				if (c == ';' || c == '}')
				{
					fw.write(c);
					if (sb.substring(i + 1).startsWith("//"))
					{
						fw.write('\t');
					}
					else
					{
						fw.write(NL);
					}
				}
				// before & after
				else if (c == '{')
				{
					fw.write(NL);
					fw.write(c);
					fw.write(NL);
				}
				else
				{
					fw.write(c);
				}
			}
			fw.flush();
			fw.close();

			log.info("Wrote {} ({} bytes)", out.getAbsolutePath(), out.length());
		}
		catch (final Exception ex)
		{
			throw new RuntimeException("Failed saving " + fileName, ex);
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
		if (className.equals("byte[]"))
		{
			log.warn("Invalid type: {}", className);
			return;
		}
		s_importClasses.add(className);
	}

	private void addImportClasses(@Nullable final Collection<Class<?>> classes)
	{
		if (classes == null || classes.isEmpty())
		{
			return;
		}

		for (final Class<?> clazz : classes)
		{
			if (clazz == null)
			{
				continue;
			}

			addImportClass(clazz);
		}
	}

	private void addImportClass(@NonNull final Class<?> clazz)
	{
		Class<?> actualClass = clazz;
		if (actualClass.isArray())
		{
			actualClass = actualClass.getComponentType();
		}
		if (actualClass.isPrimitive())
		{
			return;
		}

		addImportClass(actualClass.getCanonicalName());
	}

	private void addImportClass(@NonNull final DataTypeInfo dataTypeInfo)
	{
		if (dataTypeInfo.isJavaCodeFullyQualified())
		{
			return;
		}
		if (dataTypeInfo.isPrimitiveType())
		{
			return;
		}

		addImportClass(dataTypeInfo.getTypeClass());
	}

	/**
	 * Generate java imports
	 */
	private void createImports(final StringBuilder sb)
	{
		for (final String name : s_importClasses)
		{
			sb.append("import ").append(name).append(";"); // .append(NL);
		}
		sb.append(NL);
	}

	/**
	 * @return true if a setter method should be generated
	 */
	public static boolean isGenerateSetter(final String columnName)
	{
		return !"AD_Client_ID".equals(columnName)
				// && !"AD_Org_ID".equals(columnName)
				// && !"IsActive".equals(columnName)
				&& !"Created".equals(columnName)
				&& !"CreatedBy".equals(columnName)
				&& !"Updated".equals(columnName)
				&& !"UpdatedBy".equals(columnName);
	}

	/**
	 * @return true if a model getter method (method that is returning referenced PO) should be generated
	 */
	private static boolean isGenerateInterfaceModelGetterForColumnName(@NonNull final String columnName)
	{
		return !SKIP_InterfaceModelGettersForColumnNames.contains(columnName);
	}

	static boolean isGenerateModelGetterOrSetterForReferencedClassName(@Nullable final String referencedClassnameFQ)
	{
		if (referencedClassnameFQ == null)
		{
			return false;
		}

		return !SKIP_ModelGettersAndSettersForReferencedClassNames.contains(referencedClassnameFQ);
	}

	/**
	 * @return true if a model getter method (method that is returning referenced PO) should be generated
	 */
	private static boolean isGenerateModelGetterForEntity(
			final int AD_Table_ID,
			final String toEntityType,
			@NonNull final TableAndColumnInfoRepository repository)
	{
		final TableInfo tableInfo = repository.getTableInfo(AD_Table_ID);
		final String fromEntityType = tableInfo.getEntityType();
		final boolean fromEntity_SystemMaintained = EntityTypesCache.instance.isSystemMaintained(fromEntityType);
		final boolean toEntity_SystemMaintained = EntityTypesCache.instance.isSystemMaintained(toEntityType);
		return
				// Same entities
				fromEntityType.equals(toEntityType)
						// Both are system entities
						|| (fromEntity_SystemMaintained && toEntity_SystemMaintained)
						// Not Sys Entity referencing a Sys Entity
						|| (!fromEntity_SystemMaintained && toEntity_SystemMaintained);
	}

	/**
	 * Get EntityType Model Package.
	 *
	 * @author Victor Perez - [ 1785001 ] Using ModelPackage of EntityType to Generate Model Class
	 */
	public static String getModelPackage(final String entityType)
	{
		if ("D".equals(entityType))
		{
			return "org.compiere.model";
		}

		return EntityTypesCache.instance.getModelPackage(entityType);
	}

	@Nullable
	private static String getModelPackageForClassName(@NonNull final String referenceClassName)
	{
		Check.assumeNotEmpty(referenceClassName, "referenceClassName not empty");

		final Set<String> result = ImmutableSet.copyOf(getReflections()
				.getStore()
				.get(ClassnameScanner.class.getSimpleName(), referenceClassName));

		if (result.isEmpty())
		{
			return null;
		}
		else if (result.size() > 1)
		{
			log.warn("More then one classnames were found for " + referenceClassName + ": " + result);
			return null;
		}

		final String classNameFQ = result.iterator().next();
		final int idx = classNameFQ.lastIndexOf(".");
		if (idx <= 0)
		{
			return "";
		}

		return classNameFQ.substring(0, idx);
	}

	private static Reflections getReflections()
	{
		if (reflections == null)
		{
			reflections = new Reflections(new ConfigurationBuilder()
					.setScanners(new ClassnameScanner())
					.addUrls(ClasspathHelper.forClassLoader()));
		}
		return reflections;
	}

	private static Reflections reflections = null;

	@NonNull
	static String getFieldName(@NonNull final String columnName)
	{
		final String fieldName;
		if (columnName.endsWith("_ID_To"))
		{
			fieldName = columnName.substring(0, columnName.length() - 6) + "_To";
		}
		else
		{
			fieldName = columnName.substring(0, columnName.length() - 3);
		}
		return fieldName;
	}

	@Nullable
	static String getReferenceClassName(
			@NonNull final ColumnInfo columnInfo,
			@NonNull final TableAndColumnInfoRepository repository)
	{
		final int columnTableId = columnInfo.getAdTableId();
		final String columnName = columnInfo.getColumnName();
		final int displayType = columnInfo.getDisplayType();
		final int AD_Reference_ID = columnInfo.getAdReferenceId();

		String referenceClassName = null;
		//
		if (displayType == DisplayType.TableDir
				|| (displayType == DisplayType.Search && AD_Reference_ID <= 0))
		{
			final String refTableName = MQuery.getZoomTableName(columnName); // teo_sarca: BF [ 1817768 ] Isolate hardcoded table direct columns
			referenceClassName = "I_" + refTableName;

			final TableInfo refTable = repository.getTableInfo(refTableName);
			if (refTable != null)
			{
				final String refEntityType = refTable.getEntityType();
				String modelpackage = getModelPackage(refEntityType);
				if (modelpackage == null)
				{
					modelpackage = getModelPackageForClassName(referenceClassName);
				}
				if (modelpackage != null && !modelpackage.isEmpty())
				{
					referenceClassName = modelpackage + "." + referenceClassName;
				}
				if (!isGenerateModelGetterForEntity(columnTableId, refEntityType, repository))
				{
					referenceClassName = null;
				}
			}
			else
			{
				throw new RuntimeException("No table found for refTableName=" + refTableName
						+ "; Method params: AD_Table_ID=" + columnTableId + "; columnName=" + columnName + "; displayType=" + displayType + "; AD_Reference_ID=" + AD_Reference_ID);
			}
		}
		else if (displayType == DisplayType.Table
				|| (displayType == DisplayType.Search && AD_Reference_ID > 0))
		{
			// TODO: HARDCODED: do not generate model getter for GL_DistributionLine.Account_ID
			if (columnTableId == 707 && columnName.equals("Account_ID"))
			{
				return null;
				//
			}

			final TableReferenceInfo tableReferenceInfo = columnInfo.getTableReferenceInfo().orElse(null);
			if (tableReferenceInfo != null)
			{
				final String refTableName = tableReferenceInfo.getRefTableName();
				final String refTableEntityType = tableReferenceInfo.getEntityType();
				final int refDisplayType = tableReferenceInfo.getRefDisplayType();
				final boolean refIsKey = tableReferenceInfo.isKey();
				if (refDisplayType == DisplayType.ID || refIsKey)
				{
					referenceClassName = "I_" + refTableName;
					String modelpackage = getModelPackage(refTableEntityType);
					if (modelpackage == null)
					{
						modelpackage = getModelPackageForClassName(referenceClassName);
					}
					if (modelpackage != null && !modelpackage.isEmpty())
					{
						referenceClassName = modelpackage + "." + referenceClassName;
					}
					if (!isGenerateModelGetterForEntity(columnTableId, refTableEntityType, repository))
					{
						referenceClassName = null;
					}
				}
			}
		}
		else if (displayType == DisplayType.Location)
		{
			referenceClassName = "org.compiere.model.I_C_Location";
		}
		else if (displayType == DisplayType.Locator)
		{
			referenceClassName = "org.compiere.model.I_M_Locator";
		}
		else if (displayType == DisplayType.Account)
		{
			referenceClassName = "org.compiere.model.I_C_ValidCombination";
		}
		else if (displayType == DisplayType.PAttribute)
		{
			referenceClassName = "org.compiere.model.I_M_AttributeSetInstance";
		}
		else if (displayType == DisplayType.Image)
		{
			referenceClassName = "org.compiere.model.I_AD_Image";
		}

		return referenceClassName;
	}

	public static boolean isGenerateLegacy()
	{
		return "true".equals(System.getProperty(PROPERTY_Legacy));
	}
}
