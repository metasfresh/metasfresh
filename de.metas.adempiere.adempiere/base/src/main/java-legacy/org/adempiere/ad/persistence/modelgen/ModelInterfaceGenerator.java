/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                           *
 * http://www.adempiere.org                                            *
 *                                                                     *
 * Copyright (C) Trifon Trifonov.                                      *
 * Copyright (C) Contributors                                          *
 *                                                                     *
 * This program is free software, you can redistribute it and/or       *
 * modify it under the terms of the GNU General Public License         *
 * as published by the Free Software Foundation, either version 2      *
 * of the License, or (at your option) any later version.              *
 *                                                                     *
 * This program is distributed in the hope that it will be useful,     *
 * but WITHOUT ANY WARRANTY, without even the implied warranty of      *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
 * GNU General Public License for more details.                        *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with this program, if not, write to the Free Software         *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
 * MA 02110-1301, USA.                                                 *
 *                                                                     *
 * Contributors:                                                       *
 * - Trifon Trifonov (trifonnt@users.sourceforge.net)                  *
 * - Teo Sarca (teo.sarca@arhipac.ro)                                  *
 *                                                                     *
 * Sponsors:                                                           *
 * - Company (http://www.d3-soft.com)                                  *
 * - ARHIPAC (http://www.arhipac.ro)                                   *
 **********************************************************************/

package org.adempiere.ad.persistence.modelgen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.ad.persistence.EntityTypesCache;
import org.adempiere.ad.security.TableAccessLevel;
import org.adempiere.model.ModelColumn;
import org.adempiere.util.Check;
import org.adempiere.util.ClassnameScanner;
import org.adempiere.util.Services;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.service.IColumnBL;

import de.metas.logging.LogManager;

/**
 * @author Trifon Trifonov
 * @version $Id$
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL <li>BF [ 1781629 ] Don't use Env.NL in model class/interface generators <li>FR [ 1781630 ] Generated class/interfaces have a lot of unused imports <li>BF [
 *         1781632 ] Generated class/interfaces should be UTF-8 <li>better formating of generated source <li>BF [ 1787833 ] ModelInterfaceGenerator: don't write timestamp <li>FR [ 1803309 ] Model
 *         generator: generate get method for Search cols <li>BF [ 1817768 ] Isolate hardcoded table direct columns https://sourceforge.net/tracker/?func=detail&atid=879332&aid=1817768&group_id=176962
 *         <li>FR [ 2343096 ] Model Generator: Improve Reference Class Detection <li>BF [ 2528434 ] ModelInterfaceGenerator: generate getters for common fields <li>-- <li>FR [ 2848449 ]
 *         ModelClassGenerator: Implement model getters https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2848449&group_id=176962
 * @author Teo Sarca, teo.sarca@gmail.com <li>FR [ 3020635 ] Model Generator should use FQ class names https://sourceforge.net/tracker/?func=detail&aid=3020635&group_id=176962&atid=879335
 * @author Victor Perez, e-Evolution <li>FR [ 1785001 ] Using ModelPackage of EntityType to Generate Model Class
 */
public class ModelInterfaceGenerator
{
	private String packageName = "";

	public static final String NL = "\n";

	/** File Header */
	public static final String COPY = "";
	// NOTE: we are not appending ANY license to generated files because we assume this will be done automatically by a maven plugin.

	/** Logger */
	private static final transient Logger log = LogManager.getLogger(ModelInterfaceGenerator.class);

	private static final String DEPRECATED_MSG_SetterForVirtualColumn = "Please don't use it because this is a virtual column";
	private static final String DEPRECATED_MSG_GetterForLazyLoadingColumn = "Please don't use it because this is a lazy loading column and it might affect the performances";

	public ModelInterfaceGenerator(final TableInfo tableInfo, String directory, String packageName)
	{
		super();

		// this.repository = repository;
		this.packageName = packageName;

		// create column access methods
		final StringBuilder mandatory = new StringBuilder();
		final StringBuilder sb = createColumns(tableInfo, mandatory);

		// Header
		String tableName = createHeader(tableInfo, sb, mandatory);

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

	/**
	 * Add Header info to buffer
	 * 
	 * @param AD_Table_ID table
	 * @param sb buffer
	 * @param mandatory init call for mandatory columns
	 * @param packageName package name
	 * @return class name
	 */
	private String createHeader(final TableInfo tableInfo, final StringBuilder sb, final StringBuilder mandatory)
	{
		final String tableName = tableInfo.getTableName();
		final TableAccessLevel accessLevel = tableInfo.getAccessLevel();

		//
		String className = "I_" + tableName;
		//
		StringBuilder start = new StringBuilder()
				.append(COPY)
				.append("package ").append(packageName).append(";").append(NL);

		// if (!packageName.equals("org.compiere.model")) {
		// addImportClass("org.compiere.model.*");
		// }
		// addImportClass(java.math.BigDecimal.class);
		// addImportClass(org.compiere.util.KeyNamePair.class);

		createImports(start);
		// Interface
		start.append("/** Generated Interface for ").append(tableName).append("\n")
				.append(" *  @author Adempiere (generated) \n")
				// .append(" *  @version ").append(Adempiere.MAIN_VERSION).append(NL) //.append(" - ").append(s_run).append("\n") // metas: don't generate it because it is changing on each rollout
				.append(" */\n")
				.append("@SuppressWarnings(\"javadoc\")\n") // metas
				.append("public interface ").append(className).append(" {").append("\n")

				.append("    /** TableName=").append(tableName).append(" */\n")
				.append("    public static final String Table_Name = \"").append(tableName).append("\";\n")

				.append("    /** AD_Table_ID=").append(tableInfo.getAD_Table_ID()).append(" */\n")
				.append(isGenerateLegacy() ? "" : "//") // metas
				.append("    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);\n")

				// .append("    protected KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);\n")
				.append(isGenerateLegacy() ? "" : "//") // metas
				.append("    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);\n") // INFO - Should this be here???

				.append("    /** AccessLevel = ").append(accessLevel.getDescription()).append("\n")
				.append("     */\n")
				// .append("    protected BigDecimal AccessLevel = new BigDecimal(").append(accessLevel).append(");\n")
				.append(isGenerateLegacy() ? "" : "//") // metas
				.append("    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(").append(accessLevel.getAccessLevelInt()).append(");\n") // INFO - Should this be here???

				.append("    /** Load Meta Data */\n")
		// .append("    protected POInfo initPO (Properties ctx);")
		// .append("    POInfo initPO (Properties ctx);") // INFO - Should this be here???
		;

		StringBuilder end = new StringBuilder("}");
		//
		sb.insert(0, start);
		sb.append(end);

		return className;
	}

	/**
	 * Create Column access methods
	 * 
	 * @param AD_Table_ID table
	 * @param mandatory init call for mandatory columns
	 * @return set/get method
	 */
	private StringBuilder createColumns(final TableInfo tableInfo, final StringBuilder mandatory)
	{
		final StringBuilder sb = new StringBuilder();

		final List<ColumnInfo> columnInfos = tableInfo.getColumnInfos();
		for (final ColumnInfo columnInfo : columnInfos)
		{
			sb.append(createColumnMethods(mandatory, columnInfo));
		}

		return sb;
	}

	private final String createColumnConstants(final String tableName, final String columnName, final String referenceClassName)
	{
		final String modelClassname = "I_" + tableName;

		final StringBuilder sb = new StringBuilder();

		//
		// Add: COLUMN_ColumnName = new ModelColumn...
		{
			// e.g. ModelColumn<I_C_Invoice, I_C_BPartner>
			final StringBuilder modelColumnClassname = new StringBuilder()
					.append(ModelColumn.class.getName())
					.append("<")
					.append(modelClassname)
					.append(", ").append(referenceClassName == null ? "Object" : referenceClassName)
					.append(">");

			sb.append("    /** Column definition for ").append(columnName).append(" */\n")
					.append("    public static final ").append(modelColumnClassname).append(" COLUMN_").append(columnName)
					//
					// e.g. new ModelColumn<I_C_Invoice, I_C_BPartner>
					.append(" = new ").append(modelColumnClassname)
					//
					// e.g. (I_C_Invoice.class, columnName, I_C_BPartner.class);
					.append("(")
					.append(modelClassname).append(".class")
					.append(", \"").append(columnName).append("\"")
					.append(", ").append(referenceClassName == null ? "null" : referenceClassName + ".class")
					.append(");");
			;
		}

		//
		// Add: COLUMNNAME_ColumnName = ...
		sb.append("    /** Column name ").append(columnName).append(" */\n")
				.append("    public static final String COLUMNNAME_").append(columnName)
				.append(" = \"").append(columnName).append("\";");

		return sb.toString();
	}

	/**
	 * Create set/get methods for column
	 * 
	 * @param mandatory init call for mandatory columns
	 * @param columnInfo column info
	 * @return set/get method
	 */
	private String createColumnMethods(final StringBuilder mandatory, final ColumnInfo columnInfo)
	{
		final Class<?> clazz = getClass(columnInfo);
		final String dataType = getDataTypeName(clazz, columnInfo.getDisplayType());

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
			
			// Create Java Comment
			generateJavaComment(sb, columnInfo, "Set", deprecatedSetter);
			appendDeprecatedIfNotNull(sb, deprecatedSetter);
			sb.append("\tpublic void set").append(columnInfo.getColumnName()).append(" (")
					.append(dataType).append(" ").append(columnInfo.getColumnName()).append(");");
		}

		//
		// Getter
		{
			String deprecatedGetter = null;
			if (columnInfo.isVirtualColumn() && columnInfo.isLazyLoading())
			{
				deprecatedGetter = DEPRECATED_MSG_GetterForLazyLoadingColumn;
			}
			
			generateJavaComment(sb, columnInfo, "Get", deprecatedGetter);
			appendDeprecatedIfNotNull(sb, deprecatedGetter);
			sb.append("\tpublic ").append(dataType);
			if (clazz.equals(Boolean.class))
			{
				sb.append(" is");
				if (columnInfo.getColumnName().toLowerCase().startsWith("is"))
					sb.append(columnInfo.getColumnName().substring(2));
				else
					sb.append(columnInfo.getColumnName());
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
		final String referenceClassName = getReferenceClassName(columnInfo);
		if (isGenerateModelGetter(columnInfo.getColumnName()) && DisplayType.isID(columnInfo.getDisplayType()) && !columnInfo.isKey())
		{
			final String fieldName = getFieldName(columnInfo.getColumnName());
			//
			if (fieldName != null && referenceClassName != null)
			{
				//
				// Model getter
				{
					String deprecatedGetter = null;
					if (columnInfo.isVirtualColumn() && columnInfo.isLazyLoading())
					{
						deprecatedGetter = DEPRECATED_MSG_GetterForLazyLoadingColumn;
					}
					sb.append("\n");
					appendDeprecatedIfNotNull(sb, deprecatedGetter);
					sb.append("\tpublic " + referenceClassName + " get").append(fieldName).append("();");
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
					
					sb.append("\n");
					appendDeprecatedIfNotNull(sb, deprecatedSetter);
					sb.append("\tpublic void set" + fieldName + "(" + referenceClassName + " " + fieldName + ");");
				}
			}
		}

		//
		// Handle AD_Table_ID/Record_ID generic model reference
		if (!Check.isEmpty(columnInfo.getTableIdColumnName(), true))
		{
			final String fieldName = getFieldName(columnInfo.getColumnName());
			sb.append("\n").append("\tpublic <ReferenceType> ReferenceType get").append(fieldName).append("(Class<ReferenceType> referenceType);");

			sb.append("\n").append("\tpublic <ReferenceType> void set").append(fieldName).append("(ReferenceType referenceModel);");
		}

		//
		// Column related constants (e.g. COLUMNNAME_)
		sb.append("\n");
		sb.append(createColumnConstants(columnInfo.getTableName(), columnInfo.getColumnName(), referenceClassName));

		return sb.toString();
	}

	// ****** Set/Get Comment ******
	private void generateJavaComment(
			final StringBuilder result //
			, final ColumnInfo columnInfo //
			, final String startOfComment //
			, final String deprecated //
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
	
	private void appendDeprecatedIfNotNull(final StringBuilder result, final String deprecated)
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
	private void writeToFile(StringBuilder sb, String fileName)
	{
		try
		{
			File out = new File(fileName);
			// metas: begin: make sure directory exists
			out.getParentFile().mkdirs();
			// metas: end
			Writer fw = new OutputStreamWriter(new FileOutputStream(out, false), "UTF-8");
			for (int i = 0; i < sb.length(); i++)
			{
				char c = sb.charAt(i);
				// after
				if (c == ';' || c == '}')
				{
					fw.write(c);
					if (sb.substring(i + 1).startsWith("//"))
						fw.write('\t');
					else
						fw.write(NL);
				}
				// before & after
				else if (c == '{')
				{
					fw.write(NL);
					fw.write(c);
					fw.write(NL);
				}
				else
					fw.write(c);
			}
			fw.flush();
			fw.close();
			float size = out.length();
			size /= 1024;
			log.info(out.getAbsolutePath() + " - " + size + " kB");
		}
		catch (Exception ex)
		{
			log.error(fileName, ex);
			throw new RuntimeException(ex);
		}
	}

	/** Import classes */
	private Collection<String> s_importClasses = new TreeSet<String>();

	/**
	 * Add class name to class import list
	 * 
	 * @param className
	 */
	private void addImportClass(String className)
	{
		if (className == null
				|| (className.startsWith("java.lang.") && !className.startsWith("java.lang.reflect."))
				|| className.startsWith(packageName + "."))
			return;
		for (String name : s_importClasses)
		{
			if (className.equals(name))
				return;
		}
		if (className.equals("byte[]"))
		{
			log.warn("Invalid type - " + className);
			return;
		}
		s_importClasses.add(className);
	}

	/**
	 * Add class to class import list
	 * 
	 * @param cl
	 */
	@SuppressWarnings("unused")
	private void addImportClass(Class<?> cl)
	{
		if (cl.isArray())
		{
			cl = cl.getComponentType();
		}
		if (cl.isPrimitive())
			return;
		addImportClass(cl.getCanonicalName());
	}

	/**
	 * Generate java imports
	 * 
	 * @param sb
	 */
	private void createImports(StringBuilder sb)
	{
		for (String name : s_importClasses)
		{
			sb.append("import ").append(name).append(";"); // .append(NL);
		}
		sb.append(NL);
	}

	/**
	 * Get class for given display type and reference
	 * 
	 * @param displayType
	 * @param AD_Reference_ID
	 * @return class
	 */
	public static Class<?> getClass(final ColumnInfo columnInfo)
	{
		final int displayType = columnInfo.getDisplayType();
		final int AD_Reference_ID = columnInfo.getAD_Reference_ID();
		return getClass(columnInfo, displayType, AD_Reference_ID);
	}

	private static Class<?> getClass(final ColumnInfo columnInfo, int displayType, int AD_Reference_ID)
	{
		final String columnName = columnInfo.getColumnName();

		// Handle Posted
		// TODO: hardcoded
		if (columnName.equalsIgnoreCase("Posted")
				|| columnName.equalsIgnoreCase("Processed")
				|| columnName.equalsIgnoreCase("Processing"))
		{
			return Boolean.class;
		}
		// Record_ID
		// TODO: hardcoded
		else if (Services.get(IColumnBL.class).isRecordColumnName(columnName))
		{
			return Integer.class;
		}
		// Reference Table
		else if ((DisplayType.Table == displayType || DisplayType.Search == displayType)
				&& AD_Reference_ID > 0)
		{
			final Optional<TableReferenceInfo> tableReferenceInfoOrNull = columnInfo.getTableReferenceInfo();
			if (tableReferenceInfoOrNull.isPresent())
			{
				final TableReferenceInfo tableReferenceInfo = tableReferenceInfoOrNull.get();
				displayType = tableReferenceInfo.getRefDisplayType();
				AD_Reference_ID = tableReferenceInfo.getKeyReferenceValueId();
			}
			else
			{
				throw new IllegalStateException("Not found AD_Ref_Table/AD_Column for " + columnInfo);
			}
			//
			return getClass(columnInfo, displayType, AD_Reference_ID); // recursive call with new parameters
		}
		else
		{
			return DisplayType.getClass(displayType, true);
		}
	}

	public static String getDataTypeName(Class<?> cl, int displayType)
	{
		String dataType = cl.getName();
		dataType = dataType.substring(dataType.lastIndexOf('.') + 1);
		if (dataType.equals("Boolean"))
		{
			dataType = "boolean";
		}
		else if (dataType.equals("Integer"))
		{
			dataType = "int";
		}
		else if (displayType == DisplayType.Binary || displayType == DisplayType.Image)
		{
			dataType = "byte[]";
		}
		else
		{
			dataType = cl.getName(); // metas: always use FQ names
		}
		return dataType;
	}

	/**
	 * @param columnName
	 * @return true if a setter method should be generated
	 */
	public static boolean isGenerateSetter(String columnName)
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
	 * @param columnName
	 * @return true if a model getter method (method that is returning referenced PO) should be generated
	 */
	public static boolean isGenerateModelGetter(String columnName)
	{
		return true
				// && !"AD_Client_ID".equals(columnName)
				// && !"AD_Org_ID".equals(columnName)
				&& !"CreatedBy".equals(columnName)
				&& !"UpdatedBy".equals(columnName);
	}

	/**
	 * 
	 * @param AD_Table_ID
	 * @param toEntityType
	 * @return true if a model getter method (method that is returning referenced PO) should be generated
	 */
	public static boolean isGenerateModelGetterForEntity(int AD_Table_ID, String toEntityType)
	{
		final String fromEntityType = DB.getSQLValueString(null, "SELECT EntityType FROM AD_Table where AD_Table_ID=?", AD_Table_ID);
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
	 * @param entityType
	 * @return
	 */
	public static final String getModelPackage(final String entityType)
	{
		if ("D".equals(entityType))
			return "org.compiere.model";

		return EntityTypesCache.instance.getModelPackage(entityType);
	}

	private static String getModelPackageForClassName(final String referenceClassName)
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

		final String packageName = classNameFQ.substring(0, idx);
		return packageName;
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

	public static String getFieldName(String columnName)
	{
		String fieldName;
		if (columnName.endsWith("_ID_To"))
			fieldName = columnName.substring(0, columnName.length() - 6) + "_To";
		else
			fieldName = columnName.substring(0, columnName.length() - 3);
		return fieldName;
	}

	public static String getReferenceClassName(final ColumnInfo columnInfo)
	{
		final int AD_Table_ID = columnInfo.getAD_Table_ID();
		final String columnName = columnInfo.getColumnName();
		final int displayType = columnInfo.getDisplayType();
		final int AD_Reference_ID = columnInfo.getAD_Reference_ID();

		String referenceClassName = null;
		//
		if (displayType == DisplayType.TableDir
				|| (displayType == DisplayType.Search && AD_Reference_ID == 0))
		{
			String refTableName = MQuery.getZoomTableName(columnName); // teo_sarca: BF [ 1817768 ] Isolate hardcoded table direct columns
			referenceClassName = "I_" + refTableName;

			MTable table = MTable.get(Env.getCtx(), refTableName);
			if (table != null)
			{
				String entityType = table.getEntityType();
				String modelpackage = getModelPackage(entityType);
				if (modelpackage == null)
				{
					modelpackage = getModelPackageForClassName(referenceClassName);
				}
				if (modelpackage != null && !modelpackage.isEmpty())
				{
					referenceClassName = modelpackage + "." + referenceClassName;
				}
				if (!isGenerateModelGetterForEntity(AD_Table_ID, entityType))
				{
					referenceClassName = null;
				}
			}
			else
			{
				throw new RuntimeException("No table found for refTableName=" + refTableName
						+ "; Method params: AD_Table_ID=" + AD_Table_ID + "; columnName=" + columnName + "; displayType=" + displayType + "; AD_Reference_ID=" + AD_Reference_ID);
			}
		}
		else if (displayType == DisplayType.Table
				|| (displayType == DisplayType.Search && AD_Reference_ID > 0))
		{
			// TODO: HARDCODED: do not generate model getter for GL_DistributionLine.Account_ID
			if (AD_Table_ID == 707 && columnName.equals("Account_ID"))
				return null;
			//

			final Optional<TableReferenceInfo> tableReferenceInfoOrNull = columnInfo.getTableReferenceInfo();
			if (tableReferenceInfoOrNull.isPresent())
			{
				final TableReferenceInfo tableReferenceInfo = tableReferenceInfoOrNull.get();
				final String refTableName = tableReferenceInfo.getRefTableName();
				final String entityType = tableReferenceInfo.getEntityType();
				final int refDisplayType = tableReferenceInfo.getRefDisplayType();
				final boolean refIsKey = tableReferenceInfo.isKey();
				if (refDisplayType == DisplayType.ID || refIsKey)
				{
					referenceClassName = "I_" + refTableName;
					String modelpackage = getModelPackage(entityType);
					if (modelpackage == null)
					{
						modelpackage = getModelPackageForClassName(referenceClassName);
					}
					if (modelpackage != null && !modelpackage.isEmpty())
					{
						referenceClassName = modelpackage + "." + referenceClassName;
					}
					if (!isGenerateModelGetterForEntity(AD_Table_ID, entityType))
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
		else
		{
			// TODO - Handle other types
			// sb.append("\tpublic I_"+columnName+" getI_").append(columnName).append("(){return null; };");
		}
		//
		return referenceClassName;
	}

	/**
	 * String representation
	 * 
	 * @return string representation
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("[").append("]");
		return sb.toString();
	}

	public static final String PROPERTY_Legacy = "generate.legacy";

	public static final boolean isGenerateLegacy()
	{
		return "true".equals(System.getProperty(PROPERTY_Legacy));
	}
}
