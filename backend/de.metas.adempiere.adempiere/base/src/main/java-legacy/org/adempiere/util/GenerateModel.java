package org.adempiere.util;

import ch.qos.logback.classic.Level;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.persistence.modelgen.ModelClassGenerator;
import org.adempiere.ad.persistence.modelgen.ModelInterfaceGenerator;
import org.adempiere.ad.persistence.modelgen.TableAndColumnInfoRepository;
import org.adempiere.ad.persistence.modelgen.TableInfo;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.tools.AdempiereToolsHelper;
import org.compiere.util.DB;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Generate Model Classes extending PO.
 * Base class for CMP interface - will be extended to create byte code directly
 *
 * @author Jorg Janke
 * @author Teo Sarca, teo.sarca@gmail.com
 * <li>BF [ 3020640 ] GenerateModel is failing when we provide a list of tables
 * https://sourceforge.net/tracker/?func=detail&aid=3020640&group_id=176962&atid=879332
 * @version $Id: GenerateModel.java,v 1.42 2005/05/08 15:16:56 jjanke Exp $
 */
public class GenerateModel
{

	/**
	 * Logger
	 */
	private static final Logger log = LogManager.getLogger(GenerateModel.class);

	public static String getModelPackage(int AD_Table_ID)
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

	public static String getModelDirectory(String srcDirectory, String packageName)
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
	 * <p>
	 * Accepts following paramters
	 * <ul>
	 * <li>Parameter 1: Output directory
	 * <li>Parameter 2: Package Name
	 * <li>Parameter 3: EntityTypes
	 * <li>Parameter 4: Table Name (like)
	 * </ul>
	 */
	public static void main(String[] args) throws Exception
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
		log.info("Select SQL: {}", sql);

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

			if (count <= 0)
			{
				throw new AdempiereException("No models were generated.");
			}
		}
		finally
		{
			DB.close(rs, pstmt);
			log.info("Generated = {}", count);
		}
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
		String entityType = "'U','A'";    // User, Application
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
		String tableLike;
		tableLike = "'%'";    // All tables
		// tableLike = "'AD_OrgInfo', 'AD_Role', 'C_CashLine', 'C_Currency', 'C_Invoice', 'C_Order', 'C_Payment', 'M_InventoryLine', 'M_PriceList', 'M_Product', 'U_POSTerminal'"; // Only specific tables
		if (args.length > 3)
		{
			tableLike = args[3];
		}

		return Config.builder()
				.directory(directory)
				.packageName(packageName)
				.entityTypePatterns(parseStringPatterns(entityType))
				.tableNamePatterns(parseStringPatterns(tableLike))
				.build();
	}

	private static ImmutableSet<StringPattern> parseStringPatterns(@Nullable final String string)
	{
		if (string == null
				|| string.trim().isEmpty()
				|| string.trim().equals("-"))
		{
			return ImmutableSet.of();
		}

		return Splitter.on(",")
				.trimResults()
				.omitEmptyStrings()
				.splitToStream(string)
				.map(StringPattern::of)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static String toSql(@NonNull final String columnName, ImmutableSet<StringPattern> tableNamePatterns)
	{
		if (tableNamePatterns.isEmpty())
		{
			return null;
		}

		final StringBuilder sql = new StringBuilder();
		for (final StringPattern tableNamePattern : tableNamePatterns)
		{
			if (sql.length() > 0)
			{
				sql.append(" OR ");
			}

			sql.append(columnName)
					.append(tableNamePattern.isWithWildcard() ? " LIKE " : "=")
					.append(toSqlQuotedString(tableNamePattern.getPattern()));
		}

		return sql.toString();
	}

	private static String toSqlQuotedString(@Nullable final String string)
	{
		if (string != null
				&& string.length() > 2
				&& string.startsWith("'")
				&& string.endsWith("'"))
		{
			return string;
		}
		else
		{
			return DB.TO_STRING(string);
		}
	}

	private static String createTableSql(@NonNull final Config config)
	{
		final String packageName = config.getPackageName();

		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT AD_Table_ID FROM AD_Table WHERE IsActive='Y'");

		if(packageName.equals("org.compiere.model"))
		{
			sql.append("\n AND (IsView='N' OR TableName IN ('RV_WarehousePrice','RV_BPartner')");
		}

		//
		// EntityType
		final String sqlEntityTypeFiter = toSql("EntityType", config.getEntityTypePatterns());
		if (sqlEntityTypeFiter != null && !sqlEntityTypeFiter.isEmpty())
		{
			sql.append("\n AND (").append(sqlEntityTypeFiter).append(")");
		}

		//
		// Table LIKE
		final String sqlTableNameFilter = toSql("TableName", config.getTableNamePatterns());
		if (sqlTableNameFilter != null && !sqlTableNameFilter.isEmpty())
		{
			sql.append("\n AND (").append(sqlTableNameFilter).append(")");
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
		ImmutableSet<StringPattern> entityTypePatterns;

		@NonNull
		ImmutableSet<StringPattern> tableNamePatterns;
	}

	@Value(staticConstructor = "of")
	private static class StringPattern
	{
		@NonNull String pattern;

		public boolean isWithWildcard() {return pattern.contains("%");}

		@Override
		public String toString() {return isWithWildcard() ? "LIKE " + pattern : pattern;}
	}
}
