/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.material.dispo;

import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.logging.LogManager;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Purchase_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_StockChange_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Transaction_Detail;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

/**
 * Step definitions for running the {@code MD_Candidate_Rebuild} process.
 * <p>
 * Steps:
 * <ul>
 *   <li>{@code When the MD_Candidate_Rebuild process is run with IsBackupBeforeDelete=Y/N}</li>
 *   <li>{@code When all MD_Candidates for product {identifier} are deleted}</li>
 * </ul>
 */
@RequiredArgsConstructor
public class MD_Candidate_Rebuild_StepDef
{
	@NonNull private static final Logger logger = LogManager.getLogger(MD_Candidate_Rebuild_StepDef.class);

	@NonNull private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final M_Product_StepDefData productTable;

	/**
	 * Runs the MD_Candidate_Rebuild AD_Process with the given backup parameter.
	 *
	 * @param isBackupBeforeDelete "Y" or "N"
	 */
	@When("the MD_Candidate_Rebuild process is run with IsBackupBeforeDelete={string}")
	public void run_md_candidate_rebuild(@NonNull final String isBackupBeforeDelete)
	{
		final AdProcessId processId = processDAO.retrieveProcessIdByValue("MD_Candidate_Rebuild");
		logger.info("Running MD_Candidate_Rebuild (AD_Process_ID={}, IsBackupBeforeDelete={})", processId, isBackupBeforeDelete);

		final boolean backup = "Y".equals(isBackupBeforeDelete);

		ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.addParameter("IsBackupBeforeDelete", backup)
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();

		logger.info("MD_Candidate_Rebuild completed successfully");
	}

	/**
	 * Deletes all MD_Candidate records (and their detail records) for the given product identifier.
	 * This simulates the condition where source documents exist but no MD_Candidates are present,
	 * allowing tests to verify that the rebuild process creates missing candidates (INSERT path / Pass B).
	 *
	 * @param productIdentifier identifier referencing a previously created M_Product
	 */
	@When("all MD_Candidates for product {string} are deleted")
	public void delete_all_md_candidates_for_product(@NonNull final String productIdentifier)
	{
		final I_M_Product product = productTable.get(StepDefDataIdentifier.ofString(productIdentifier));
		final int productId = product.getM_Product_ID();

		// Collect candidate IDs for this product
		final java.util.List<Integer> candidateIds = queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addEqualsFilter(I_MD_Candidate.COLUMNNAME_M_Product_ID, productId)
				.create()
				.listIds();

		if (candidateIds.isEmpty())
		{
			logger.info("No MD_Candidates found for product {} (M_Product_ID={})", productIdentifier, productId);
			return;
		}

		// Delete child detail records first (FK constraints)
		deleteDetailRecords(I_MD_Candidate_Demand_Detail.class, I_MD_Candidate_Demand_Detail.COLUMNNAME_MD_Candidate_ID, candidateIds);
		deleteDetailRecords(I_MD_Candidate_Purchase_Detail.class, I_MD_Candidate_Purchase_Detail.COLUMNNAME_MD_Candidate_ID, candidateIds);
		deleteDetailRecords(I_MD_Candidate_Prod_Detail.class, I_MD_Candidate_Prod_Detail.COLUMNNAME_MD_Candidate_ID, candidateIds);
		deleteDetailRecords(I_MD_Candidate_Dist_Detail.class, I_MD_Candidate_Dist_Detail.COLUMNNAME_MD_Candidate_ID, candidateIds);
		deleteDetailRecords(I_MD_Candidate_Transaction_Detail.class, I_MD_Candidate_Transaction_Detail.COLUMNNAME_MD_Candidate_ID, candidateIds);
		deleteDetailRecords(I_MD_Candidate_StockChange_Detail.class, I_MD_Candidate_StockChange_Detail.COLUMNNAME_MD_Candidate_ID, candidateIds);

		// Delete the candidates themselves
		final int deletedCount = queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addEqualsFilter(I_MD_Candidate.COLUMNNAME_M_Product_ID, productId)
				.create()
				.delete();

		logger.info("Deleted {} MD_Candidates (and their details) for product {} (M_Product_ID={})", deletedCount, productIdentifier, productId);
	}

	private <T> void deleteDetailRecords(
			@NonNull final Class<T> modelClass,
			@NonNull final String candidateIdColumnName,
			@NonNull final java.util.List<Integer> candidateIds)
	{
		if (candidateIds.isEmpty())
		{
			return;
		}

		final int deleted = queryBL.createQueryBuilder(modelClass)
				.addInArrayFilter(candidateIdColumnName, candidateIds)
				.create()
				.delete();

		if (deleted > 0)
		{
			logger.info("Deleted {} {} records", deleted, modelClass.getSimpleName());
		}
	}
}
