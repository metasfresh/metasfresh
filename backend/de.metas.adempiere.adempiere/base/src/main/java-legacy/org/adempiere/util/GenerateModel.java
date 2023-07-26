/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 * Contributor(s): Carlos Ruiz - globalqss *
 * Teo Sarca *
 * Trifon Trifonov *
 *****************************************************************************/
package org.adempiere.util;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.ad.persistence.modelgen.ModelClassGenerator;
import org.adempiere.ad.persistence.modelgen.ModelInterfaceGenerator;
import org.adempiere.ad.persistence.modelgen.TableAndColumnInfoRepository;
import org.adempiere.ad.persistence.modelgen.TableInfo;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.tools.AdempiereToolsHelper;
import org.compiere.util.DB;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Generate Model Classes extending PO.
 * Base class for CMP interface - will be extended to create byte code directly
 *
 * @author Jorg Janke
 * @version $Id: GenerateModel.java,v 1.42 2005/05/08 15:16:56 jjanke Exp $
 * 
 * @author Teo Sarca, teo.sarca@gmail.com
 *         <li>BF [ 3020640 ] GenerateModel is failing when we provide a list of tables
 *         https://sourceforge.net/tracker/?func=detail&aid=3020640&group_id=176962&atid=879332
 */
public class GenerateModel
{

	/** Logger */
	private static Logger log = LogManager.getLogger(GenerateModel.class);

	/**
	 * String representation
	 * 
	 * @return string representation
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("GenerateModel[").append("]");
		return sb.toString();
	}

	public static final String getModelPackage(int AD_Table_ID)
	{
		String modelPackage = DB.getSQLValueStringEx(null,
				"SELECT et.ModelPackage FROM AD_Table t"
						+ " INNER JOIN AD_EntityType et ON (et.EntityType=t.EntityType)"
						+ " WHERE t.AD_Table_ID=?",
				AD_Table_ID);
		if (Check.isEmpty(modelPackage, true))
		{
			modelPackage = "org.compiere.model";
		}
		return modelPackage;
	}

	public static final String getModelDirectory(String srcDirectory, String packageName)
	{
		File directoryFile;
		if (Check.isEmpty(srcDirectory, true) || "-".equals(srcDirectory.trim()))
		{
			directoryFile = new File("./src/main/java-gen/");
		}
		else
		{
			directoryFile = new File(srcDirectory);
		}

		if (!directoryFile.isDirectory() || !directoryFile.canWrite())
		{
			throw new RuntimeException("Directory not exists or is not writable: " + directoryFile);
		}

		final String packagePart = packageName.replace(".", "/");
		if (!directoryFile.getAbsolutePath().endsWith(packagePart))
		{
			directoryFile = new File(directoryFile, packageName.replace(".", "/"));
		}
		// directoryFile.mkdirs();
		return directoryFile.getAbsolutePath() + File.separator;
	}

	/**
	 * Generates Interfacees and Classes for models
	 * 
	 * Accepts following paramters
	 * <ul>
	 * <li>Parameter 1: Output directory
	 * <li>Parameter 2: Package Name
	 * <li>Parameter 3: EntityTypes
	 * <li>Parameter 4: Table Name (like)
	 * </ul>
	 */
	public static void main(String[] args)
	{
		AdempiereToolsHelper.getInstance().startupMinimal();

		LogManager.setLevel(Level.DEBUG);
		Ini.setProperty(Ini.P_LOGMIGRATIONSCRIPT, false); // metas: don't log migration scripts
		log.info("Generate Model");
		log.info("----------------------------------");

		final Config config = getConfig(args);
		log.info("Config: {}", config);

		//
		//
		log.info("----------------------------------");
		final TableAndColumnInfoRepository repository = new TableAndColumnInfoRepository();
		final String sql = createTableSql(config);
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final int adTableId = rs.getInt(1);

				final String packageNameFinal;
				final String directoryFinal;
				if (config.getPackageName().equals("-"))
				{
					packageNameFinal = getModelPackage(adTableId);
					directoryFinal = getModelDirectory(config.getDirectory(), packageNameFinal);
				}
				else
				{
					packageNameFinal = config.getPackageName();
					directoryFinal = config.getDirectory();
				}

				final TableInfo tableInfo = repository.getTableInfo(adTableId);
				new ModelInterfaceGenerator(repository, tableInfo, directoryFinal, packageNameFinal);
				new ModelClassGenerator(repository, tableInfo, directoryFinal, packageNameFinal);
				count++;
			}
		}
		catch (Exception e)
		{
			log.error(sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (count <= 0)
		{
			throw new AdempiereException("No models were generated."
					+ "\n SQL: " + sql);
		}

		log.info("Generated = {}", count);
	}

	private static Config getConfig(String[] args)
	{
		//
		// Parameter 1: Output directory
		String directory = null;
		if (args.length > 0)
		{
			final String directoryArg = args[0];
			if ("-".equals(directoryArg))
			{
				directory = directoryArg;
			}
			else
			{
				directory = new File(directoryArg).getAbsolutePath();
			}
		}
		if (directory == null || directory.length() == 0)
		{
			System.err.println("No Output Directory");
			System.exit(1);
		}

		//
		// Parameter 2: Package Name
		String packageName = "compiere.model";
		if (args.length > 1)
			packageName = args[1];
		if (packageName == null || packageName.length() == 0)
		{
			System.err.println("No package");
			System.exit(1);
		}

		//
		// Parameter 3: EntityTypes
		String entityType = "'U','A'";	// User, Application
		if (args.length > 2)
		{
			entityType = args[2];
		}
		if (entityType == null || entityType.length() == 0)
		{
			System.err.println("No EntityType");
			System.exit(1);
		}

		//
		// Parameter 4: Table Name (like)
		String tableLike = null;
		tableLike = "'%'";	// All tables
		// tableLike = "'AD_OrgInfo', 'AD_Role', 'C_CashLine', 'C_Currency', 'C_Invoice', 'C_Order', 'C_Payment', 'M_InventoryLine', 'M_PriceList', 'M_Product', 'U_POSTerminal'"; // Only specific tables
		if (args.length > 3)
		{
			tableLike = args[3];
		}

		return Config.builder()
				.directory(directory)
				.packageName(packageName)
				.entityType(entityType)
				.tableLike(tableLike)
				.build();
	}

	private static String createTableSql(@NonNull final Config config)
	{
		final String packageName = config.getPackageName();
		final String entityType = config.getEntityType();
		final String tableLike = config.getTableLike();

		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT AD_Table_ID FROM AD_Table WHERE IsActive='Y'");

		sql.append("\n AND ("
				+ "TableName IN ('RV_WarehousePrice','RV_BPartner')"	// special views
				+ (packageName.equals("org.compiere.model") ? " OR IsView='N' " : " OR 1=1")
				+ ")");
		//
		// EntityType
		if (entityType.indexOf("%") >= 0)
		{
			sql.append("\n AND EntityType LIKE ").append(entityType);
		}
		else
		{
			sql.append("\n AND EntityType IN (").append(entityType).append(")");
		}

		//
		// Table LIKE
		// (Autodetect if we need to use IN or LIKE clause - teo_sarca [ 3020640 ])
		final boolean excludeTrlTables;
		if (tableLike.indexOf(",") == -1)
		{
			if (tableLike.indexOf("%") == -1)
			{
				sql.append("\n AND TableName=").append(tableLike);
				excludeTrlTables = false;
			}
			else
			{
				sql.append("\n AND TableName LIKE ").append(tableLike);
				excludeTrlTables = true;
			}
		}
		else
		{
			sql.append("\n AND TableName IN (").append(tableLike).append(")"); // only specific tables
			excludeTrlTables = false;
		}
		//
		if (excludeTrlTables)
		{
			sql.append("\n AND TableName NOT LIKE '%_Trl' ");
		}

		//
		// ORDER BY
		sql.append("\n ORDER BY TableName");

		//
		return sql.toString();
	}

	@Value
	@Builder
	private static class Config
	{
		@NonNull
		String directory;

		@NonNull
		String packageName;

		@NonNull
		String entityType;

		@NonNull
		String tableLike;
	}
}
