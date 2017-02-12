package de.metas.adempiere.tools;

import org.adempiere.ad.persistence.modelgen.ADRefListGenerator;
import org.adempiere.ad.persistence.modelgen.ListInfo;
import org.adempiere.ad.persistence.modelgen.TableAndColumnInfoRepository;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.tools.AdempiereToolsHelper;
import org.adempiere.util.Check;
import org.compiere.util.Ini;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Helper CLI tool to generate java constants code for a given AD_Reference_ID.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class AD_Ref_List_GenerateJavaConstants
{
	public static void main(final String[] args)
	{
		//
		// Start ADempiere
		AdempiereToolsHelper.getInstance().startupMinimal();
		LogManager.setLevel(Level.DEBUG);
		Ini.setProperty(Ini.P_LOGMIGRATIONSCRIPT, false); // metas: don't log migration scripts

		final TableAndColumnInfoRepository repository = new TableAndColumnInfoRepository();

		//
		// Get AD_Reference_ID parameter
		if (args.length < 1)
		{
			throw new AdempiereException("Provide AD_Reference_ID parameter");
		}
		final String adReferenceIdStr = args[0];
		Check.assumeNotEmpty(adReferenceIdStr, "Valid AD_Reference_ID parameter: {}", adReferenceIdStr);
		final int adReferenceId = Integer.parseInt(adReferenceIdStr.trim());

		//
		// Get the AD_Reference list info
		final ListInfo listInfo = repository.getListInfo(adReferenceId).orNull();
		if (listInfo == null)
		{
			throw new AdempiereException("No list info found for AD_Reference_ID=" + adReferenceId);
		}

		//
		// Generate the Java code
		final String javacode = ADRefListGenerator.newInstance()
				.setColumnName("MyColumnName")
				.setListInfo(listInfo)
				.generateConstants();

		//
		// Output the result
		System.out.println("Generated Java code:");
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println(javacode);
		System.out.println("--------------------------------------------------------------------------------------------");
	}
}
