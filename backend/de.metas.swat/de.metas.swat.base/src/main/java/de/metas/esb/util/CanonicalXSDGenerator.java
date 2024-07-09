/**
 *
 */
package de.metas.esb.util;

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

import de.metas.ad_reference.ADRefListItem;
import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.ReferenceId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.RPL_Constants;
import org.adempiere.util.LegacyAdapters;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Reference;
import org.compiere.model.I_EXP_Format;
import org.compiere.model.I_EXP_FormatLine;
import org.compiere.model.MColumn;
import org.compiere.model.MEXPFormat;
import org.compiere.model.MRefTable;
import org.compiere.model.MReplicationStrategy;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_AD_ReplicationTable;
import org.compiere.model.X_EXP_FormatLine;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Helper tool that generates XSD Schema for Canonical Messages
 *
 * @author tsa
 *
 */
public class CanonicalXSDGenerator
{
	// services
	private static final Logger logger = LogManager.getLogger(CanonicalXSDGenerator.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final ADReferenceService adReferenceService = ADReferenceService.get();

	private static final String XSD_TYPE_YES_NO = "YES-NO";
	private static final String XSD_ENUMERATION = "xsd:enumeration";
	private static final String XSD_RESTRICTION = "xsd:restriction";
	private static final String XSD_SIMPLE_TYPE = "xsd:simpleType";
	private static final String XSD_COMPLEX_TYPE = "xsd:complexType";
	private static final String XSD_ELEMENT = "xsd:element";
	private static final String XSD_MAX_OCCURS = "maxOccurs";
	private static final String XSD_MIN_OCCURS = "minOccurs";
	private static final String XSD_NAME = "name";
	private static final String XSD_TYPE = "type";

	private static final AdMessageKey ERROR_ESB_AD_REFERENCE_VALUE_NON_ASCII_3P = AdMessageKey.of("ESB_AD_Reference_Value_Non_Ascii");
	private static final AdMessageKey ERROR_ESB_AD_REFERENCE_VALUE_MISSING_3P = AdMessageKey.of("ESB_AD_Reference_Value_Missing");

	public static String NAME_TypeSuffix = "Type";
	public static String JAXB_AttributeSuffix = "Attr";
	public static String JAXB_Version = "2.0";

	@Nullable
	private Document document = null;
	@Nullable
	private Element rootElement = null;

	private final List<Element> complexTypes = new ArrayList<>();

	// 02775: exporting AD_Reference_Values of List references as XSD enums
	private final Map<String, Element> enumTypes = new HashMap<>();

	private final Map<String, Element> refTypes = new HashMap<>();

	private boolean oneFilePerFormat = false;
	private String targetDirectory = null;

	public void setOneFilePerFormat(final boolean oneFilePerFormat)
	{
		this.oneFilePerFormat = oneFilePerFormat;
	}

	public boolean isOneFilePerFormat()
	{
		return oneFilePerFormat;
	}

	public void setTargetDirectory(final String targetDirectory)
	{
		this.targetDirectory = targetDirectory;
	}

	public String getTargetDirectory()
	{
		return targetDirectory;
	}

	private Document createNewDocument()
	{
		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder documentBuilder;
		try
		{
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		}
		catch (final ParserConfigurationException e)
		{
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}

		return documentBuilder.newDocument();
	}

	public void addEXPFormat(final I_EXP_Format format)
	{
		final MEXPFormat formatPO = LegacyAdapters.convertToPO(format);
		addEXPFormat(formatPO);
	}

	public void addEXPFormat(final MEXPFormat format)
	{
		if (document == null)
		{
			document = createNewDocument();
			rootElement = document.createElement("xsd:schema");
			rootElement.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
			rootElement.setAttribute("xmlns:jaxb", "http://java.sun.com/xml/ns/jaxb");
			rootElement.setAttribute("jaxb:version", JAXB_Version);
			document.appendChild(rootElement);

			createDefaultAttributes();
		}

		createComplexType(format, false);

		for (final Element e : complexTypes)
		{
			rootElement.appendChild(e);
		}
		//
		// Create Top Elements
		for (final Element cte : complexTypes)
		{
			String name = cte.getAttribute(XSD_NAME);
			if (Check.isEmpty(name))
			{
				continue;
			}

			if (name.endsWith(NAME_TypeSuffix))
			{
				name = name.substring(0, name.length() - NAME_TypeSuffix.length());
			}

			if (!name.equals(format.getValue()))
			{
				continue;
			}

			final Element e = document.createElement(XSD_ELEMENT);
			e.setAttribute(XSD_NAME, name);
			e.setAttribute(XSD_TYPE, name + NAME_TypeSuffix);
			rootElement.appendChild(e);
		}

		if (isOneFilePerFormat())
		{
			final File file = new File(targetDirectory, format.getValue() + ".xsd");
			saveToFile(file.getAbsolutePath());
			reset();
		}
	}

	private void reset()
	{
		document = null;
		rootElement = null;
		complexTypes.clear();
	}

	private void createDefaultAttributes()
	{
		createXSDAttribute(rootElement, RPL_Constants.XML_ATTR_AD_Client_Value, "xsd:string", null);

		createXSDAttribute(rootElement, RPL_Constants.XML_ATTR_SEQUENCE_NO, "xsd:integer", null); // 02596

		createXSDAttribute(rootElement, RPL_Constants.XML_ATTR_REPLICATION_TrxName, "xsd:string", null); // 06231

		createXSDAttribute(rootElement, RPL_Constants.XML_ATTR_AD_SESSION_ID, "xsd:integer", null); // 03132

		final HashMap<String, String> map = new HashMap<>();
		map.put(Integer.toString(ModelValidator.TYPE_AFTER_CHANGE), "AfterChange");
		map.put(Integer.toString(ModelValidator.TYPE_BEFORE_DELETE), "BeforeDelete");
		map.put(Integer.toString(ModelValidator.TYPE_BEFORE_DELETE_REPLICATION), "BeforeDeleteReplication");

		createXSDEnumeration("ReplicationEventEnum", map);
		createXSDAttribute(rootElement, RPL_Constants.XML_ATTR_REPLICATION_EVENT, "ReplicationEventEnum", null);

		map.clear();
		map.put(Integer.toString(MReplicationStrategy.REPLICATION_DOCUMENT), "Document");
		map.put(Integer.toString(MReplicationStrategy.REPLICATION_TABLE), "Table");
		createXSDEnumeration("ReplicationModeEnum", map);
		createXSDAttribute(rootElement, RPL_Constants.XML_ATTR_REPLICATION_MODE, "ReplicationModeEnum", null);

		createXSDEnumeration("ReplicationTypeEnum", X_AD_ReplicationTable.REPLICATIONTYPE_AD_Reference_ID);
		createXSDAttribute(rootElement, RPL_Constants.XML_ATTR_REPLICATION_TYPE, "ReplicationTypeEnum", null);
	}

	private Element createXSDEnumeration(
			final String name,
			final int AD_Reference_ID)
	{
		Check.assume(toJavaName(name).equals(name),
				"'name' param '" + name + "' equals '" + toJavaName(name) + "'");

		final List<ADRefListItem> listValues = new ArrayList<>(adReferenceService.retrieveListItems(ReferenceId.ofRepoId(AD_Reference_ID)));
		if (listValues.isEmpty())
		{
			return null;
		}

		final HashMap<String, String> map = new HashMap<>(listValues.size());
		// final ValueNamePair[] list = MRefList.getList(ctx, AD_Reference_ID, false);

		for (final ADRefListItem listValue : listValues)
		{
			final String value = listValue.getValue();
			final String valueName = listValue.getValueName(); // 08456: Take valueName instead of name when generating enumerations (Names can be equal!)

			map.put(
					toJavaName(value),
					toJavaName(valueName)
					);
		}
		return createXSDEnumeration(name, map);
	}

	private Element createXSDEnumeration(final String enumTypeName, final Map<String, String> map)
	{
		if (map.size() == 0)
		{
			return null;
		}

		Check.assume(toJavaName(enumTypeName).equals(enumTypeName),
				"'enumTypeName' param '" + enumTypeName + "' equals '" + toJavaName(enumTypeName) + "'");

		if (enumTypes.containsKey(enumTypeName))
		{
			return enumTypes.get(enumTypeName);
		}

		final Element e = document.createElement(XSD_SIMPLE_TYPE);
		e.setAttribute(XSD_NAME, enumTypeName);

		final Element e2 = document.createElement(XSD_RESTRICTION);
		e2.setAttribute("base", "xsd:string");
		e.appendChild(e2);

		for (final Entry<String, String> entry : map.entrySet())
		{
			final String enumValue = entry.getKey();

			Check.assume(toJavaName(enumValue).equals(enumValue),
					"enumValue '" + enumValue + "' equals '" + toJavaName(enumValue) + "'");

			final Element e3 = document.createElement(XSD_ENUMERATION);
			e3.setAttribute("value", enumValue);
			e2.appendChild(e3);

			// Annotate

			final String typesafeEnumMember = entry.getValue();
			Check.assume(toJavaName(typesafeEnumMember).equals(typesafeEnumMember),
					"typesafeEnumMember '" + typesafeEnumMember + "' equals '" + toJavaName(typesafeEnumMember) + "'");

			final Element annotation = createJAXBAnnotation(e3);
			final Element eName = document.createElement("jaxb:typesafeEnumMember");

			eName.setAttribute(XSD_NAME, typesafeEnumMember);
			annotation.appendChild(eName);
		}

		rootElement.appendChild(e);
		enumTypes.put(enumTypeName, e);

		return e;
	}

	private void createComplexType(final MEXPFormat format, final boolean included)
	{
		// Check if already created:
		for (final Element e : complexTypes)
		{
			final String name = format.getValue() + NAME_TypeSuffix;
			if (name.equals(e.getAttribute(XSD_NAME)))
			{
				return;
			}
		}

		// final Element formatElement = document.createElement("xsd:element");
		// formatElement.setAttribute("name", format.getValue());

		final Element formatComplexTypeElement = document.createElement(XSD_COMPLEX_TYPE);
		formatComplexTypeElement.setAttribute(XSD_NAME, format.getValue() + NAME_TypeSuffix);
		// formatElement.appendChild(formatComplexTypeElement);

		final Element formatSeqElement = document.createElement("xsd:sequence");
		formatComplexTypeElement.appendChild(formatSeqElement);

		if (!included)
		{
			createXSDAttribute(formatComplexTypeElement, RPL_Constants.XML_ATTR_AD_Client_Value, null, null);
			createXSDAttribute(formatComplexTypeElement, RPL_Constants.XML_ATTR_REPLICATION_EVENT, null, null);
			createXSDAttribute(formatComplexTypeElement, RPL_Constants.XML_ATTR_REPLICATION_MODE, null, null);
			createXSDAttribute(formatComplexTypeElement, RPL_Constants.XML_ATTR_REPLICATION_TYPE, null, null);
			createXSDAttribute(formatComplexTypeElement, RPL_Constants.XML_ATTR_Version, "xsd:string", format.getVersion());
			createXSDAttribute(formatComplexTypeElement, RPL_Constants.XML_ATTR_SEQUENCE_NO, null, null); // 02596
			createXSDAttribute(formatComplexTypeElement, RPL_Constants.XML_ATTR_REPLICATION_TrxName, null, null); // 06231
			createXSDAttribute(formatComplexTypeElement, RPL_Constants.XML_ATTR_AD_SESSION_ID, null, null); // 03132
		}

		for (final I_EXP_FormatLine line : format.getFormatLinesOrderedBy(I_EXP_FormatLine.COLUMNNAME_Position + "," + I_EXP_FormatLine.COLUMNNAME_EXP_FormatLine_ID))
		{
			if (!line.isActive())
			{
				continue;
			}

			if (X_EXP_FormatLine.TYPE_XMLElement.equals(line.getType()))
			{
				final int displayType = getDisplayType(line, DisplayType.String);
				final I_AD_Column column = line.getAD_Column();

				final Element e = document.createElement(XSD_ELEMENT);
				e.setAttribute(XSD_NAME, line.getValue());
				if(!line.isMandatory())
				{
					e.setAttribute(XSD_MIN_OCCURS, "0");
				}

				if ((displayType == DisplayType.List
						|| displayType == DisplayType.Button) // 05231 (al): encountered case with button on EDI_ExportStatus
						&& column != null && column.getAD_Reference_Value_ID() > 0)
				{
					checkReferenceValue(column);

					// 02775
					// Notes:
					// We don't want the XSD file to contain non-ascii chars, because it might lead to trouble, e.g.
					// when the file is used to create binding code
					// AD_Reference.Name is unique (DB unique index), but we can't guarantee the same for the name after
					// it has been stripped of non-ascii chars
					// When choosing an AD_Reference.Name it is very easy to omit non-ascii chars

					final I_AD_Reference refValue = column.getAD_Reference_Value();

					if (!mkAsciiOnly(refValue.getName()).equals(refValue.getName()))
					{
						final String msg = msgBL.getMsg(Env.getCtx(), ERROR_ESB_AD_REFERENCE_VALUE_NON_ASCII_3P, new Object[] {
								column.getColumnName(),
								Services.get(IADTableDAO.class).retrieveTableName(column.getAD_Table_ID()),
								msgBL.translate(Env.getCtx(), I_AD_Column.COLUMNNAME_AD_Reference_Value_ID) });
						throw new AdempiereException(msg);
					}

					final String typeName = toJavaName(refValue.getName()) + "Enum";

					createXSDEnumeration(typeName, column.getAD_Reference_Value_ID());
					e.setAttribute(XSD_TYPE, typeName);
				}
				else
				{
					setXSDTypeByReference(e, column, displayType);
				}
				formatSeqElement.appendChild(e);
			}
			else if (X_EXP_FormatLine.TYPE_XMLAttribute.equals(line.getType()))
			{
				final int displayType = getDisplayType(line, DisplayType.String);
				final I_AD_Column column = line.getAD_Column();

				final Element e = document.createElement("xsd:attribute");
				e.setAttribute(XSD_NAME, line.getValue());
				e.setAttribute("use", "required");
				setXSDTypeByReference(e, column, displayType);
				formatSeqElement.appendChild(e);
			}
			// else if (MEXPFormatLine.TYPE_EmbeddedEXPFormat.equals(line.getType()))
			// {
			// throw new RuntimeException("Type "+line.getType()+" not implemented");
			// }
			else if (X_EXP_FormatLine.TYPE_EmbeddedEXPFormat.equals(line.getType())
					|| X_EXP_FormatLine.TYPE_ReferencedEXPFormat.equals(line.getType()))
			{
				MEXPFormat embededFormat = (MEXPFormat)line.getEXP_EmbeddedFormat();
				if (embededFormat == null || embededFormat.getEXP_Format_ID() <= 0)
				{
					embededFormat = findEXPFormatForColumn(line);
				}
				if (embededFormat == null || embededFormat.getEXP_Format_ID() <= 0)
				{
					// throw new RuntimeException("No EXP Format found for "+line);
				}
				//
				if (embededFormat != null)
				{
					createComplexType(embededFormat, true);
					//
					final Element e = document.createElement(XSD_ELEMENT);
					e.setAttribute(XSD_NAME, line.getValue());
					e.setAttribute(XSD_TYPE, embededFormat.getValue() + NAME_TypeSuffix);
					e.setAttribute(XSD_MIN_OCCURS, "0");
					if (!X_EXP_FormatLine.TYPE_ReferencedEXPFormat.equals(line.getType()))
					{
						e.setAttribute(XSD_MAX_OCCURS, "unbounded");
					}
					formatSeqElement.appendChild(e);
				}
			}
			else
			{
				throw new RuntimeException("@NotSupported@ @Type@=" + line.getType());
			}
		}

		complexTypes.add(formatComplexTypeElement);
		logger.info("Added format: " + format.getName());
	}

	private void checkReferenceValue(final I_AD_Column column)
	{
		if (column.getAD_Reference_Value_ID() <= 0)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(column);
			throw new AdempiereException(ERROR_ESB_AD_REFERENCE_VALUE_MISSING_3P,
					msgBL.translate(ctx, I_AD_Column.COLUMNNAME_AD_Reference_Value_ID),
					column.getColumnName(),
					Services.get(IADTableDAO.class).retrieveTableName(column.getAD_Table_ID()));
		}
	}

	private Element createXSDAttribute(@Nullable final Element parent, final String name, @Nullable final String type, @Nullable final String fixed)
	{
		final Element e = document.createElement("xsd:attribute");
		if (type != null)
		{
			e.setAttribute(XSD_NAME, name);
			e.setAttribute(XSD_TYPE, type);
			if (fixed != null)
			{
				e.setAttribute("fixed", fixed);
			}
			createJAXBAnnotation(e, name + JAXB_AttributeSuffix);
		}
		else
		{
			e.setAttribute("ref", name);
		}

		if (parent != null)
		{
			parent.appendChild(e);
		}
		return e;
	}

	private Element createJAXBAnnotation(final Element parent)
	{
		return createJAXBAnnotation(parent, null, null);
	}

	private Element createJAXBAnnotation(final Element parent, final String propertyName)
	{
		return createJAXBAnnotation(parent, "jaxb:property", propertyName);
	}

	private Element createJAXBAnnotation(final Element parent, final String annotationName, final String value)
	{
		final Element e = document.createElement("xsd:annotation");
		parent.appendChild(e);
		final Element e2 = document.createElement("xsd:appinfo");
		e.appendChild(e2);

		if (annotationName != null && value != null)
		{
			final Element e3 = document.createElement(annotationName);
			e3.setAttribute(XSD_NAME, toJavaName(value));
			e2.appendChild(e3);
		}

		return e2;
	}

	private static String toJavaName(final String name)
	{
		if (name == null)
		{
			return "";
		}

		return mkAsciiOnly(name)
				.trim()
				.replace(" ", "_")
				.replace("-", "_")
				.replace('.', '_');
	}

	/**
	 * Returns a string created from the given input, but without any non-ascii characters. If the given string is
	 * empty, then an empty string is returned.
	 *
	 * It is assumed that
	 * <ul>
	 * <li>the given input is not null</li>
	 * <li>after stripping non-ascii chars from the given (a non-empty!) input there is at least one ascii character
	 * left to output</li>
	 * </ul>
	 *
	 */
	private static String mkAsciiOnly(final String input)
	{
		Check.assume(input != null, "Input string is not null");

		if("".equals(input))
		{
			return "";
		}

		final String output = input.replaceAll("[^\\p{ASCII}]", "");

		Check.assume(!"".equals(output), "Input string '" + input + "' has at least one ascii-character");
        return output;
	}


	private MEXPFormat findEXPFormatForColumn(final I_EXP_FormatLine line)
	{
		// I_AD_Column column = line.getAD_Column();
		// String refTableName = MQuery.getZoomTableName(column.getColumnName());
		// MEXPFormat.getFormatByAD_Client_IDAD_Table_IDAndVersion(
		// ctx,
		// AD_Client_ID,
		// AD_Table_ID,
		// version,
		// trxName);
		// TODO Auto-generated method stub
		return null;
	}

	public void saveToFile(final String filename)
	{
		FileWriter writer = null;
		try
		{
			writer = new FileWriter(filename);
			saveToWriter0(writer);

			logger.info("Wrote schema file: " + filename);
		}
		catch (final Exception e)
		{
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}
		finally
		{
			if (writer != null)
			{
				try
				{
					writer.close();
				}
				catch (final IOException e)
				{
					throw new RuntimeException(e.getLocalizedMessage(), e);
				}
			}
			writer = null;
		}
	}

	public String saveToString()
	{
		final Writer writer = new StringWriter();
		saveToWriter(writer);
		return writer.toString();
	}

	public void saveToWriter(final Writer writer)
	{
		try
		{
			saveToWriter0(writer);
		}
		catch (final Exception e)
		{
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}
	}

	private void saveToWriter0(final Writer writer) throws Exception
	{
		final TransformerFactory tranFactory = TransformerFactory.newInstance();
		// String jVersion = System.getProperty("java.version");
		// if (jVersion.startsWith("1.5.0"))
		// tranFactory.setAttribute("indent-number", Integer.valueOf(1));

		final Transformer aTransformer = tranFactory.newTransformer();
		aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
		final Source src = new DOMSource(document);

		final Result dest2 = new StreamResult(writer);
		aTransformer.transform(src, dest2);
	}

	/**
	 *
	 * @param e
	 * @param column
	 * @param displayType
	 */
	private void setXSDTypeByReference(final Element e, final I_AD_Column column, final int displayType)
	{
		Check.assume(displayType > 0, "DisplayType > 0");

		final String xsdType;

		if (DisplayType.isText(displayType))
		{
			xsdType = "xsd:string";
		}
		else if (displayType == DisplayType.Integer) // note that Integer is stored as BD
		{
			xsdType = "xsd:integer";
		}
		else if (DisplayType.isID(displayType))
		{
			if (displayType == DisplayType.Table
					|| displayType == DisplayType.Search && column.getAD_Reference_Value_ID() > 0)
			{
				checkReferenceValue(column);

				final Properties ctx = InterfaceWrapperHelper.getCtx(column);
				final MRefTable refTable = MRefTable.get(ctx, column.getAD_Reference_Value_ID());

				final MColumn referencedCol = MColumn.get(ctx, refTable.getAD_Key());
				if (DisplayType.isText(referencedCol.getAD_Reference_ID()))
				{
					xsdType = "xsd:string";
				}
				else
				{
					xsdType = "xsd:integer";
				}
			}
			else
			{
				xsdType = "xsd:integer";
			}
		}

		else if (DisplayType.isNumeric(displayType))
		{
			xsdType = "xsd:decimal";
		}
		else if (displayType == DisplayType.Date)
		{
			// xsdType ="xsd:date";
			xsdType = "xsd:date";
		}
		else if (displayType == DisplayType.DateTime)
		{
			xsdType = "xsd:dateTime";
		}
		else if (displayType == DisplayType.Time)
		{
			xsdType = "xsd:time";
		}
		else if (displayType == DisplayType.YesNo)
		{
			xsdType = XSD_TYPE_YES_NO; // 02585
			ensureYesNoTypeExists();
		}
		else if (displayType == DisplayType.Button)
		{
			final String columnName = column.getColumnName();
			if (columnName.endsWith("_ID"))
			{
				xsdType = "xsd:integer";
			}
			else
			{
				xsdType = "xsd:string";
			}
		}
		else if (DisplayType.isLOB(displayType)) // CLOB is String
		{
			xsdType = "xsd:base64Binary";
		}
		else
		{
			throw new AdempiereException("Unexpected AD_Reference_ID '" + displayType + "' for AD_Column_ID=" + column.getAD_Column_ID());
		}
		//
		e.setAttribute(XSD_TYPE, xsdType);

	}

	private void ensureYesNoTypeExists()
	{
		if (refTypes.containsKey(XSD_TYPE_YES_NO))
		{
			return; // nothing to do
		}

		// 02585
		final Element e = document.createElement(XSD_SIMPLE_TYPE);
		e.setAttribute(XSD_NAME, XSD_TYPE_YES_NO);

		final Element e2 = document.createElement(XSD_RESTRICTION);
		e2.setAttribute("base", "xsd:string");
		e.appendChild(e2);

		final Element e3 = document.createElement("xsd:pattern");
		e3.setAttribute("value", "[YN]");
		e2.appendChild(e3);

		rootElement.appendChild(e);

		refTypes.put(XSD_TYPE_YES_NO, e);
	}

	public static void validateXML(final File xmlFile, final File schemaFile) throws SAXException, IOException
	{
		System.out.println("Validating:");
		System.out.println("Schema: " + schemaFile);
		System.out.println("Test XML File: " + xmlFile);

		final SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		final Schema schema = factory.newSchema(schemaFile);
		final Validator validator = schema.newValidator();
		final Source source = new StreamSource(xmlFile);
		validator.validate(source);

		System.out.println("File " + xmlFile + " is valid");
	}

	/**
	 *
	 * @param line
	 * @param defaultDisplayType
	 * @return
	 */
	private int getDisplayType(final I_EXP_FormatLine line, final int defaultDisplayType)
	{
		if (line.getAD_Reference_Override_ID() > 0)
		{
			return line.getAD_Reference_Override_ID();
		}

		final I_AD_Column column = line.getAD_Column();
		if (column == null || column.getAD_Column_ID() <= 0)
		{
			return defaultDisplayType;
		}

		final int displayType = column.getAD_Reference_ID();
		if (displayType <= 0)
		{
			return defaultDisplayType;
		}

		return displayType;
	}
}
