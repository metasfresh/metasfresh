/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 * Contributor(s): Carlos Ruiz - globalqss                                    *
 *                 Teo Sarca - www.arhipac.ro                                 *
 *                 Trifon Trifonov                                            *
 *****************************************************************************/
package org.adempiere.ad.persistence.modelgen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;

/**
 * Generate Model Classes extending PO.
 * Base class for CMP interface - will be extended to create byte code directly
 *
 * @author Jorg Janke
 * @version $Id: GenerateModel.java,v 1.42 2005/05/08 15:16:56 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL <li>BF [ 1781629 ] Don't use Env.NL in model class/interface generators <li>FR [ 1781630 ] Generated class/interfaces have a lot of unused imports <li>BF [
 *         1781632 ] Generated class/interfaces should be UTF-8 <li>FR [ xxxxxxx ] better formating of generated source <li>FR [ 1787876 ] ModelClassGenerator: list constants should be ordered <li>FR
 *         [ 1803309 ] Model generator: generate get method for Search cols <li>FR [ 1990848 ] Generated Models: remove hardcoded field length <li>FR [ 2343096 ] Model Generator: Improve Reference
 *         Class Detection <li>BF [ 2780468 ] ModelClassGenerator: not generating methods for Created* <li>-- <li>FR [ 2848449 ] ModelClassGenerator: Implement model getters
 *         https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2848449&group_id=176962
 * @author Victor Perez, e-Evolution <li>FR [ 1785001 ] Using ModelPackage of EntityType to Generate Model Class
 */
public class ModelClassGenerator
{
	private static final Set<String> COLUMNNAMES_STANDARD = ImmutableSet.of("AD_Client_ID", "AD_Org_ID", "IsActive", "Created", "CreatedBy", "Updated", "UpdatedBy");

	/**
	 * Generate PO Class
	 * 
	 * @param AD_Table_ID table id
	 * @param directory directory
	 * @param packageName package name
	 */
	public ModelClassGenerator(final TableInfo tableInfo, String directory, String packageName)
	{
		super();

		this.packageName = packageName;

		// create column access methods
		StringBuilder mandatory = new StringBuilder();
		StringBuilder sb = createColumns(tableInfo, mandatory);

		// Header
		String tableName = createHeader(tableInfo, sb, mandatory, packageName);

		// Save
		if (!directory.endsWith(File.separator))
			directory += File.separator;

		writeToFile(sb, directory + tableName + ".java");
	}

	public static final String NL = "\n";

	/** Logger */
	private static Logger log = LogManager.getLogger(ModelClassGenerator.class);

	/** Package Name */
	private String packageName = "";

	/**
	 * Add Header info to buffer
	 * 
	 * @param AD_Table_ID table
	 * @param sb buffer
	 * @param mandatory init call for mandatory columns
	 * @param packageName package name
	 * @return class name
	 */
	private String createHeader(final TableInfo tableInfo, StringBuilder sb, StringBuilder mandatory, String packageName)
	{
		final String tableName = tableInfo.getTableName();

		//
		String keyColumn = tableName + "_ID";
		String className = "X_" + tableName;
		//
		StringBuilder start = new StringBuilder()
				.append(ModelInterfaceGenerator.COPY)
				.append("/** Generated Model - DO NOT CHANGE */").append(NL)
				.append("package " + packageName + ";").append(NL)
				.append(NL);

		addImportClass(java.util.Properties.class);
		addImportClass(java.sql.ResultSet.class);
		// if (!packageName.equals("org.compiere.model"))
		// addImportClass("org.compiere.model.*");
		createImports(start);
		// Class
		start.append("/** Generated Model for ").append(tableName).append(NL)
				.append(" *  @author Adempiere (generated) ").append(NL)
				// .append(" *  @version ").append(Adempiere.MAIN_VERSION).append(" - $Id$ */").append(NL) // metas: don't generate it because it is changing on each rollout
				.append(" */").append(NL)
				.append("@SuppressWarnings(\"javadoc\")").append(NL) // metas
				.append("public class ").append(className)
				.append(" extends org.compiere.model.PO")
				.append(" implements I_").append(tableName)
				.append(", org.compiere.model.I_Persistent ")
				.append(NL)
				.append("{").append(NL)

				// serialVersionUID
				.append(NL)
				.append("\t/**").append(NL)
				.append("\t *").append(NL)
				.append("\t */").append(NL)
				.append("\tprivate static final long serialVersionUID = ")
				.append(PLACEHOLDER_serialVersionUID)// metas: generate serialVersionUID on save
				// .append(String.format("%1$tY%1$tm%1$td", new Timestamp(System.currentTimeMillis()))) // metas: commented
				.append("L;").append(NL)
				// .append("\tprivate static final long serialVersionUID = 1L;").append(NL)

				// Standard Constructor
				.append(NL)
				.append("    /** Standard Constructor */").append(NL)
				.append("    public ").append(className).append(" (Properties ctx, int ").append(keyColumn).append(", String trxName)").append(NL)
				.append("    {").append(NL)
				.append("      super (ctx, ").append(keyColumn).append(", trxName);").append(NL)
				.append("      /** if (").append(keyColumn).append(" == 0)").append(NL)
				.append("        {").append(NL)
				.append(mandatory) // .append(NL)
				.append("        } */").append(NL)
				.append("    }").append(NL)
				// Constructor End

				// Load Constructor
				.append(NL)
				.append("    /** Load Constructor */").append(NL)
				.append("    public ").append(className).append(" (Properties ctx, ResultSet rs, String trxName)").append(NL)
				.append("    {").append(NL)
				.append("      super (ctx, rs, trxName);").append(NL)
				.append("    }").append(NL)
				// Load Constructor End

				// TableName
				// .append(NL)
				// .append("    /** TableName=").append(tableName).append(" */").append(NL)
				// .append("    public static final String Table_Name = \"").append(tableName).append("\";").append(NL)

				// AD_Table_ID
				// .append(NL)
				// .append("    /** AD_Table_ID=").append(AD_Table_ID).append(" */").append(NL)
				// .append("    public static final int Table_ID = MTable.getTable_ID(Table_Name);").append(NL)

				// KeyNamePair
				// .append(NL)
				// .append("    protected static KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);").append(NL)

				// accessLevel
				// .append(NL)
				// .append("    protected BigDecimal accessLevel = BigDecimal.valueOf(").append(accessLevel).append(");").append(NL)
				.append(NL);
		if (ModelInterfaceGenerator.isGenerateLegacy()) // metas
		{
			start
					.append("    /** AccessLevel").append(NL)
					.append("      * @return ").append(tableInfo.getAccessLevel().getDescription()).append(NL)
					.append("      */").append(NL)
					.append("    @Override").append(NL) // metas
					.append("    protected int get_AccessLevel()").append(NL)
					.append("    {").append(NL)
					.append("      return accessLevel.intValue();").append(NL)
					.append("    }").append(NL);
		}

		// initPO
		start.append(NL)
				.append("    /** Load Meta Data */")
				.append(NL)
				.append("    @Override")
				.append(NL)
				// metas
				.append("    protected org.compiere.model.POInfo initPO (Properties ctx)")
				.append(NL)
				.append("    {")
				.append(NL)
				.append("      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, " + (ModelInterfaceGenerator.isGenerateLegacy() ? "Table_ID" : "Table_Name")
						+ ", get_TrxName());").append(NL)
				.append("      return poi;").append(NL)
				.append("    }").append(NL)
		// initPO

		// toString()
		// NOTE: don't generate toString() but better use PO.toString() because it's better
		// .append(NL)
		// .append("    @Override").append(NL) // metas
		// .append("    public String toString()").append(NL)
		// .append("    {").append(NL)
		// .append("      StringBuilder sb = new StringBuilder (\"").append(className).append("[\")").append(NL)
		// .append("        .append(get_ID()).append(\"]\");").append(NL)
		// .append("      return sb.toString();").append(NL)
		// .append("    }").append(NL)
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

			sb.append(createColumnMethods(mandatory, columnInfo));
		}

		return sb;
	}	// createColumns

	/**
	 * Create set/get methods for column
	 * 
	 * @param mandatory init call for mandatory columns
	 * @param columnName column name
	 * @param isUpdateable updateable
	 * @param isMandatory mandatory
	 * @param displayType display type
	 * @param AD_Reference_ID validation reference
	 * @param fieldLength int
	 * @param defaultValue default value
	 * @param ValueMin String
	 * @param ValueMax String
	 * @param VFormat String
	 * @param Callout String
	 * @param Name String
	 * @param Description String
	 * @param virtualColumn virtual column
	 * @param IsEncrypted stored encrypted
	 * @return set/get method
	 */
	private String createColumnMethods(final StringBuilder mandatory, final ColumnInfo columnInfo)
	{
		final Class<?> clazz = ModelInterfaceGenerator.getClass(columnInfo);
		final int displayType = columnInfo.getDisplayType();
		final String dataType = ModelInterfaceGenerator.getDataTypeName(clazz, displayType);
		final String columnName = columnInfo.getColumnName();

		String defaultValue = columnInfo.getDefaultValue();
		if (defaultValue == null)
			defaultValue = "";

		// int fieldLength = columnInfo.getFieldLength();
		// if (DisplayType.isLOB(displayType)) // No length check for LOBs
		// fieldLength = 0;

		// Set ********
		String setValue = "\t\tset_Value";
		if (columnInfo.isEncrypted())
			setValue = "\t\tset_ValueE";
		// Handle isUpdateable
		if (!columnInfo.isUpdateable())
		{
			setValue = "\t\tset_ValueNoCheck";
			if (columnInfo.isEncrypted())
				setValue = "\t\tset_ValueNoCheckE";
		}

		StringBuilder sb = new StringBuilder();

		// TODO - New functionality
		// 1) Must understand which class to reference
		if (DisplayType.isID(displayType) && !columnInfo.isKey())
		{
			String fieldName = ModelInterfaceGenerator.getFieldName(columnName);
			String referenceClassName = ModelInterfaceGenerator.getReferenceClassName(columnInfo);
			//
			if (fieldName != null && referenceClassName != null)
			{
				sb.append(NL)
						.append("\t@Override").append(NL) // metas
						.append("\tpublic " + referenceClassName + " get").append(fieldName).append("() throws RuntimeException").append(NL)
						.append("\t{").append(NL)
						// .append("\t\treturn ("+referenceClassName+")MTable.get(getCtx(), "+referenceClassName+".Table_Name)").append(NL)
						// .append("\t\t\t.getPO(get"+columnName+"(), get_TrxName());")
						.append("\t\treturn get_ValueAsPO(COLUMNNAME_" + columnName + ", " + referenceClassName + ".class);").append(NL) // metas: new model getter
						/**/
						.append("\t}").append(NL);

				// metas: begin: model setter
				sb.append(NL)
						.append("\t@Override").append(NL) // metas
						.append("\tpublic void set" + fieldName + "(" + referenceClassName + " " + fieldName + ")").append(NL)
						.append("\t{").append(NL)
						.append("\t\tset_ValueFromPO(COLUMNNAME_" + columnName + ", " + referenceClassName + ".class, " + fieldName + ");").append(NL)
						.append("\t}").append(NL);
				// metas: end
				// Add imports:
				// addImportClass(clazz);
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

		// Create Java Comment
		generateJavaSetComment(columnName, columnInfo.getName(), columnInfo.getDescription(), sb);

		// public void setColumn (xxx variable)
		sb.append("\t@Override").append(NL); // metas
		sb.append("\tpublic void set").append(columnName).append(" (").append(dataType).append(" ").append(columnName).append(")").append(NL)
				.append("\t{").append(NL);
		// List Validation
		if (columnInfo.getAD_Reference_ID() > 0 && String.class == clazz && columnInfo.getListInfo().isPresent())
		{
			sb.append("\n");
			
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
		else if (clazz.equals(Integer.class))
		{
			if (columnName.endsWith("_ID"))
			{
				final int firstValidId = InterfaceWrapperHelper.getFirstValidIdByColumnName(columnName);
				// set _ID to null if < 0 for special column or < 1 for others
				sb.append("\t\tif (").append(columnName).append(" < ").append(firstValidId).append(") ").append(NL)
						.append("\t").append(setValue).append(" (").append("COLUMNNAME_").append(columnName).append(", null);").append(NL)
						.append("\t\telse ").append(NL).append("\t");
			}
			sb.append(setValue).append(" (").append("COLUMNNAME_").append(columnName).append(", Integer.valueOf(").append(columnName).append("));").append(NL);
		}
		// Boolean
		else if (clazz.equals(Boolean.class))
			sb.append(setValue).append(" (").append("COLUMNNAME_").append(columnName).append(", Boolean.valueOf(").append(columnName).append("));").append(NL);
		else
		{
			sb.append(setValue).append(" (").append("COLUMNNAME_").append(columnName).append(", ")
					.append(columnName).append(");").append(NL);
		}
		sb.append("\t}").append(NL);

		// Mandatory call in constructor
		if (columnInfo.isMandatory())
		{
			mandatory.append("\t\t\tset").append(columnName).append(" (");
			if (clazz.equals(Integer.class))
				mandatory.append("0");
			else if (clazz.equals(Boolean.class))
			{
				if (defaultValue.indexOf('Y') != -1)
					mandatory.append(true);
				else
					mandatory.append("false");
			}
			else if (clazz.equals(BigDecimal.class))
				mandatory.append("Env.ZERO");
			else if (clazz.equals(Timestamp.class))
				mandatory.append("new Timestamp( System.currentTimeMillis() )");
			else
				mandatory.append("null");
			mandatory.append(");").append(NL);
			if (defaultValue.length() > 0)
				mandatory.append("// ").append(defaultValue).append(NL);
		}

		// ****** Get Comment ******
		generateJavaGetComment(columnInfo.getName(), columnInfo.getDescription(), sb);

		// Get ********
		String getValue = "get_Value";
		if (columnInfo.isEncrypted())
			getValue = "get_ValueE";

		sb.append("\t@Override").append(NL); // metas
		sb.append("\tpublic ").append(dataType);
		if (clazz.equals(Boolean.class))
		{
			sb.append(" is");
			if (columnName.toLowerCase().startsWith("is"))
				sb.append(columnName.substring(2));
			else
				sb.append(columnName);
		}
		else
		{
			sb.append(" get").append(columnName);
		}
		sb.append(" () ").append(NL)
				.append("\t{").append(NL)
				.append("\t\t");
		if (clazz.equals(Integer.class))
		{
			sb.append("Integer ii = (Integer)").append(getValue).append("(").append("COLUMNNAME_").append(columnName).append(");").append(NL)
					.append("\t\tif (ii == null)").append(NL)
					.append("\t\t\t return 0;").append(NL)
					.append("\t\treturn ii.intValue();").append(NL);
		}
		else if (clazz.equals(BigDecimal.class))
		{
			sb.append("BigDecimal bd = (BigDecimal)").append(getValue).append("(").append("COLUMNNAME_").append(columnName).append(");").append(NL)
					.append("\t\tif (bd == null)").append(NL)
					.append("\t\t\t return Env.ZERO;").append(NL)
					.append("\t\treturn bd;").append(NL);
			addImportClass(java.math.BigDecimal.class);
			addImportClass(org.compiere.util.Env.class);
		}
		else if (clazz.equals(Boolean.class))
		{
			sb.append("Object oo = ").append(getValue).append("(").append("COLUMNNAME_").append(columnName).append(");").append(NL)
					.append("\t\tif (oo != null) ").append(NL)
					.append("\t\t{").append(NL)
					.append("\t\t\t if (oo instanceof Boolean) ").append(NL)
					.append("\t\t\t\t return ((Boolean)oo).booleanValue(); ").append(NL)
					.append("\t\t\treturn \"Y\".equals(oo);").append(NL)
					.append("\t\t}").append(NL)
					.append("\t\treturn false;").append(NL);
		}
		else if (dataType.equals("Object"))
		{
			sb.append("\t\treturn ").append(getValue)
					.append("(").append("COLUMNNAME_").append(columnName).append(");").append(NL);
		}
		else
		{
			sb.append("return (").append(dataType).append(")").append(getValue)
					.append("(").append("COLUMNNAME_").append(columnName).append(");").append(NL);
			// addImportClass(clazz);
		}
		sb.append("\t}").append(NL);
		//
		return sb.toString();
	}	// createColumnMethods

	// ****** Set Comment ******
	public void generateJavaSetComment(String columnName, String propertyName, String description, StringBuilder result)
	{

		result.append(NL)
				.append("\t/** Set ").append(propertyName).append(".").append(NL)
				.append("\t\t@param ").append(columnName).append(" ");
		if (description != null && description.length() > 0)
		{
			result.append(NL)
					.append("\t\t").append(description).append(NL);
		}
		else
		{
			result.append(propertyName);
		}
		result.append("\t  */").append(NL);
	}

	// ****** Get Comment ******
	public void generateJavaGetComment(String propertyName, String description, StringBuilder result)
	{

		result.append(NL)
				.append("\t/** Get ").append(propertyName);
		if (description != null && description.length() > 0)
		{
			result.append(".").append(NL)
					.append("\t\t@return ").append(description).append(NL);
		}
		else
		{
			result.append(".\n\t\t@return ").append(propertyName);
		}
		result.append("\t  */").append(NL);
	}

	/**
	 * Create getKeyNamePair() method with first identifier
	 *
	 * @param columnName name
	 *            * @param displayType int
	 * @return method code
	 */
	private StringBuilder createKeyNamePair(String columnName, int displayType)
	{
		if (!ModelInterfaceGenerator.isGenerateLegacy())
		{
			return new StringBuilder();
		}

		String method = "get" + columnName + "()";
		if (displayType != DisplayType.String)
			method = "String.valueOf(" + method + ")";

		StringBuilder sb = new StringBuilder(NL)
				.append("    /** Get Record ID/ColumnName").append(NL)
				.append("        @return ID/ColumnName pair").append(NL)
				.append("      */").append(NL)
				.append("    public org.compiere.util.KeyNamePair getKeyNamePair() ").append(NL)
				.append("    {").append(NL)
				.append("        return new org.compiere.util.KeyNamePair(get_ID(), ").append(method).append(");").append(NL)
				.append("    }").append(NL);
		// addImportClass(org.compiere.util.KeyNamePair.class);
		return sb;
	}	// createKeyNamePair

	/**************************************************************************
	 * Write to file
	 * 
	 * @param sb string buffer
	 * @param fileName file name
	 */
	private void writeToFile(StringBuilder sb, String fileName)
	{
		// metas: begin: generate serial number
		{
			String s = sb.toString();
			int hash = s.hashCode();
			s = s.replace(PLACEHOLDER_serialVersionUID, String.valueOf(hash));
			sb = new StringBuilder(s);
			System.out.println("" + fileName + ": hash=" + hash);
		}
		// metas: end
		try
		{
			File out = new File(fileName);
			Writer fw = new OutputStreamWriter(new FileOutputStream(out, false), "UTF-8");
			for (int i = 0; i < sb.length(); i++)
			{
				char c = sb.charAt(i);
				// after
				if (c == ';' || c == '}')
				{
					fw.write(c);
					if (sb.substring(i + 1).startsWith("//"))
					{
						// fw.write('\t');
					}
					else
					{
						// fw.write(NL);
					}
				}
				// before & after
				else if (c == '{')
				{
					// fw.write(NL);
					fw.write(c);
					// fw.write(NL);
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
		s_importClasses.add(className);
	}

	/**
	 * Add class to class import list
	 * 
	 * @param cl
	 */
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
			sb.append("import ").append(name).append(";").append(NL);
		}
		sb.append(NL);
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

	// metas
	private static final String PLACEHOLDER_serialVersionUID = "[*serialVersionUID*]";
}
