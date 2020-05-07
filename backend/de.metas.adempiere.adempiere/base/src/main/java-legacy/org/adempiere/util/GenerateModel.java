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
 *                 Teo Sarca                                                  *
 *                 Trifon Trifonov                                            *
 *****************************************************************************/
package org.adempiere.util;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.ad.persistence.modelgen.ModelClassGenerator;
import org.adempiere.ad.persistence.modelgen.ModelInterfaceGenerator;
import org.adempiere.ad.persistence.modelgen.TableAndColumnInfoRepository;
import org.adempiere.ad.persistence.modelgen.TableInfo;
import org.adempiere.tools.AdempiereToolsHelper;
import org.compiere.util.DB;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;

/**
 *  Generate Model Classes extending PO.
 *  Base class for CMP interface - will be extended to create byte code directly
 *
 *  @author Jorg Janke
 *  @version $Id: GenerateModel.java,v 1.42 2005/05/08 15:16:56 jjanke Exp $
 *  
 *  @author Teo Sarca, teo.sarca@gmail.com
 *  		<li>BF [ 3020640 ] GenerateModel is failing when we provide a list of tables
 *  			https://sourceforge.net/tracker/?func=detail&aid=3020640&group_id=176962&atid=879332
 */
public class GenerateModel
{
	
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(GenerateModel.class);
	
	/**
	 * 	String representation
	 * 	@return string representation
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
				+" INNER JOIN AD_EntityType et ON (et.EntityType=t.EntityType)"
				+" WHERE t.AD_Table_ID=?",
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
//		directoryFile.mkdirs();
		return directoryFile.getAbsolutePath()+File.separator;
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
	public static void main (String[] args)
	{		
		AdempiereToolsHelper.getInstance().startupMinimal();
		
		LogManager.setLevel(Level.DEBUG);
		Ini.setProperty(Ini.P_LOGMIGRATIONSCRIPT, false); // metas: don't log migration scripts
		log.info("Generate Model");
		log.info("----------------------------------");
		
		//
		// Parameter 1: Output directory
		String directory = null;
		if (args.length > 0)
		{
			final String directoryArg = args[0];
			if("-".equals(directoryArg))
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
		log.info("Output Directory: " + directory);

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
		log.info("Package: " + packageName);
		
		//
		// Parameter 3: EntityTypes
		String entityType = "'U','A'";	//	User, Application
		if (args.length > 2)
			entityType = args[2]; 
		if (entityType == null || entityType.length() == 0)
		{
			System.err.println("No EntityType");
			System.exit(1);
		}
		log.info("Entity Types: " + entityType);
		
		//
		// Parameter 4: Table Name (like) 
		String tableLike = null;
		tableLike = "'%'";	//	All tables
		// tableLike = "'AD_OrgInfo', 'AD_Role', 'C_CashLine', 'C_Currency', 'C_Invoice', 'C_Order', 'C_Payment', 'M_InventoryLine', 'M_PriceList', 'M_Product', 'U_POSTerminal'";	//	Only specific tables
		if (args.length > 3)
		{
			tableLike = args[3];
		}
		log.info("Table Like: " + tableLike);

		//
		//
		final StringBuilder sql = new StringBuilder();
		log.info("----------------------------------");
		//	complete sql
		sql.insert(0, "SELECT AD_Table_ID "
			+ "FROM AD_Table "
			+ "WHERE (TableName IN ('RV_WarehousePrice','RV_BPartner')"	//	special views
			+ (packageName.equals("org.compiere.model") ? " OR IsView='N' ": " OR 1=1") // teo_sarca
			+ ")" // teo_sarca
			//+ " OR IsView='N')" // TODO: teo_sarca: commented
			+ " AND IsActive = 'Y' AND TableName NOT LIKE '%_Trl' ");
		//
		if (entityType.indexOf("%") >= 0)
			sql.append(" AND EntityType LIKE ").append(entityType);
		else
			sql.append(" AND EntityType IN (").append(entityType).append(")");
		//
		// Autodetect if we need to use IN or LIKE clause - teo_sarca [ 3020640 ]
		if (tableLike.indexOf(",") == -1)
			sql.append(" AND TableName LIKE ").append(tableLike);
		else
			sql.append(" AND TableName IN (").append(tableLike).append(")"); // only specific tables

		sql.append(" ORDER BY TableName");
		
		final TableAndColumnInfoRepository repository = new TableAndColumnInfoRepository();

		//
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final int adTableId = rs.getInt(1);
				
				final String packageNameFinal;
				final String directoryFinal;
				if (packageName.equals("-"))
				{
					packageNameFinal = getModelPackage(adTableId);
					directoryFinal = getModelDirectory(directory, packageNameFinal);
				}
				else
				{
					packageNameFinal = packageName;
					directoryFinal = directory;
				}

				final TableInfo tableInfo = repository.getTableInfo(adTableId);
				new ModelInterfaceGenerator(tableInfo, directoryFinal, packageNameFinal);
				new ModelClassGenerator(tableInfo, directoryFinal, packageNameFinal);
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
			rs = null; pstmt = null;
		}
		log.info("Generated = " + count);
	}

}
