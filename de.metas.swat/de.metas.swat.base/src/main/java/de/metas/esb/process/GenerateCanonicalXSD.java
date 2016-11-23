/**
 * 
 */
package de.metas.esb.process;

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


import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.persistence.EntityTypesCache;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.process.rpl.api.IReplicationAccessContext;
import org.adempiere.process.rpl.api.impl.ReplicationAccessContext;
import org.adempiere.process.rpl.exp.ExportHelper;
import org.adempiere.tools.AdempiereToolsHelper;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.model.MEXPFormat;
import org.compiere.model.MReplicationStrategy;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_AD_ReplicationTable;
import org.compiere.util.Env;
import org.w3c.dom.Document;

import ch.qos.logback.classic.Level;
import de.metas.esb.interfaces.I_EXP_Format;
import de.metas.esb.util.CanonicalXSDGenerator;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * @author tsa
 * 
 */
public class GenerateCanonicalXSD extends JavaProcess
{
	private static final String PARAM_Target_Directory = "Target_Directory";
	private String p_Target_Directory = null;
	
	private static final String PARAM_EntityType = "EntityType";
	private String p_EntityType = null;
	private boolean p_FilterBy_AD_Client_ID = true;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParameters())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (PARAM_Target_Directory.equals(name))
				p_Target_Directory = (String)para.getParameter();
			else if (PARAM_EntityType.equals(name))
				p_EntityType = (String)para.getParameter();
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (p_Target_Directory == null)
			throw new FillMandatoryException(PARAM_Target_Directory);
		if (p_EntityType == null)
			throw new FillMandatoryException(PARAM_EntityType);

		final IQueryBuilder<I_EXP_Format> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_EXP_Format.class, this);

		if (p_FilterBy_AD_Client_ID)
		{
			queryBuilder.addInArrayFilter(I_EXP_Format.COLUMNNAME_AD_Client_ID, 0, getAD_Client_ID());
		}
		if (p_EntityType != null)
		{
			queryBuilder.addEqualsFilter(I_EXP_Format.COLUMNNAME_EntityType, p_EntityType);
		}

		queryBuilder.orderBy()
				.addColumn(I_EXP_Format.COLUMNNAME_EXP_Format_ID) // to have a predictible order
		;

		final List<I_EXP_Format> expFormats = queryBuilder.create().list();

		final CanonicalXSDGenerator engine = new CanonicalXSDGenerator();
		for (final I_EXP_Format format : expFormats)
		{
			if (!format.isActive())
				continue;
			engine.addEXPFormat(format);
			// metas-ts: commenting out for now, because taskes very long when there are many export formats and it never helped me so far (mainly because it doesn't export embedded formats)
			// createTestModel(format);
		}
		createTestModelsIndex(expFormats);

		final String schemaFile = getSchemaFile().getAbsolutePath();
		engine.saveToFile(schemaFile);
		addLog("Created schema file: " + schemaFile);

		return "OK";
	}

	@SuppressWarnings("unused")
	private void createTestModel(final I_EXP_Format exportFormat)
	{
		try
		{
			createTestModel0(exportFormat);
		}
		catch (Exception e)
		{
			final StringBuilder msg = new StringBuilder();
			if (e.getMessage() != null)
				msg.append(e.getMessage());
			if (msg.length() > 0)
				msg.append("; ");
			msg.append(exportFormat.toString());
			throw new AdempiereException(msg.toString(), e);
		}
	}

	private void createTestModel0(final I_EXP_Format exportFormat) throws Exception
	{
		final File directory = getTestModelDirectory();
		final File file = new File(directory, getTestModelFileName(exportFormat));

		final ExportHelper expHelper = new ExportHelper(getCtx(), getAD_Client_ID());
		final IReplicationAccessContext racCtx = new ReplicationAccessContext(10, false); // TODO hardcoded

		final Document doc = expHelper.exportRecord(
				(MEXPFormat)LegacyAdapters.convertToPO(exportFormat),
				"",
				MReplicationStrategy.REPLICATION_TABLE,
				X_AD_ReplicationTable.REPLICATIONTYPE_Merge,
				ModelValidator.TYPE_AFTER_CHANGE,
				racCtx);
		if (doc == null)
		{
			addLog("Not creating test XML for format '"
					+ exportFormat.getName() + "', because with AD_Client_ID=" + getAD_Client_ID() + "the system can't access any '"
					+ exportFormat.getAD_Table().getName() + "'-record");
			return;
		}
		// Save the document to the disk file
		final TransformerFactory tranFactory = TransformerFactory.newInstance();

		final Transformer aTransformer = tranFactory.newTransformer();
		aTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
		aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
		final Source src = new DOMSource(doc);

		// =================================== Write to String
		final Writer writer = new StringWriter();
		final Result dest2 = new StreamResult(writer);
		aTransformer.transform(src, dest2);
		// =================================== Write to Disk
		try
		{
			final Result dest = new StreamResult(file);
			aTransformer.transform(src, dest);
			writer.flush();
			writer.close();

		}
		catch (TransformerException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		addLog("Created test model: " + file);
	}

	private void createTestModelsIndex(List<I_EXP_Format> formats)
	{
		final StringBuilder sb = new StringBuilder();
		for (final I_EXP_Format format : formats)
		{
			sb.append(getTestModelFileName(format)).append("\n");
		}
		//
		final File file = new File(getTestModelDirectory(), "testModels.idx");
		FileWriter writer = null;
		try
		{
			writer = new FileWriter(file);
			writer.append(sb.toString());
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			if (writer != null)
			{
				try
				{
					writer.close();
				}
				catch (Exception e)
				{
				}
				writer = null;
			}
		}
		addLog("Created test model index: " + file);
	}

	private String getSchemaPackageDirectory()
	{
		final String modelPackage = EntityTypesCache.instance.getModelPackage(p_EntityType);
		Check.assumeNotEmpty(modelPackage, "Entity type " + p_EntityType + " has a ModelPackage set");

		final int idx = modelPackage.indexOf(".model");
		return modelPackage.substring(0, idx).replace(".", "/");
	}

	public File getSchemaFile()
	{
		// final String schemaPackageDir = getSchemaPackageDirectory();
		final String schemaDir = p_Target_Directory + "/src/main/xsd";
		final File dir = new File(schemaDir);

		dir.mkdirs();
		return new File(dir, "schema.xsd");
	}

	public String getTestModelFileName(final I_EXP_Format format)
	{
		return format.getValue() + ".xml";
	}

	public File getTestModelDirectory()
	{
		final String schemaPackageDir = getSchemaPackageDirectory();
		final String schemaDir = p_Target_Directory + "/src/test/resources/" + schemaPackageDir + "/schema";
		final File dir = new File(schemaDir);
		dir.mkdirs();

		return dir;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		if (Check.isEmpty(System.getProperty("PropertyFile"), true))
		{
			throw new AdempiereException("Please set the PropertyFile");
		}

		final String outputFolder = System.getProperty("OutputFolder");
		if (Check.isEmpty(outputFolder, true))
		{
			throw new AdempiereException("Please set the OutputFolder");
		}

		final String entityType = System.getProperty(PARAM_EntityType);
		if (Check.isEmpty(entityType, true))
		{
			throw new AdempiereException("Please set the EntityType");
		}

		LogManager.initialize(true); // just to make sure we are using the client side settings

		AdempiereToolsHelper.getInstance().startupMinimal();

		final ProcessInfo pi = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setTitle("GenerateCanonicalXSD")
				.setAD_Process_ID(-1) // N/A
				.setClassname(GenerateCanonicalXSD.class.getName())
				.addParameter(PARAM_Target_Directory, outputFolder)
				.addParameter(PARAM_EntityType, entityType)
				.build();

		final GenerateCanonicalXSD process = new GenerateCanonicalXSD();
		process.p_FilterBy_AD_Client_ID = false;

		LogManager.setLevel(Level.INFO);
		process.startProcess(pi, ITrx.TRX_None);

		//
		// CanonicalXSDGenerator.validateXML(new File("d:/tmp/C_BPartner.xml"), proc.getSchemaFile());
		// CanonicalXSDGenerator.validateXML(new File("d:/tmp/clientPartners.xml"), proc.getSchemaFile());
	}
}
