/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.contracts.process;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.FlatrateTermRequest.CreateFlatrateTermRequest;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.organization.OrgId;
import de.metas.product.ProductAndCategoryId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

@Builder
@Value
public class FlatrateTermCreator
{
	private static final Logger logger = LogManager.getLogger(FlatrateTermCreator.class);

	Properties ctx;

	Timestamp startDate;
	Timestamp endDate;
	I_C_Flatrate_Conditions conditions;
	I_AD_User userInCharge;

	@Singular
	List<I_M_Product> products;

	Iterable<I_C_BPartner> bPartners;

	boolean isSimulation;

	boolean isCompleteDocument;

	/**
	 * Creates terms for all the BPartners, each of them in its own transaction. A failing partner does not stop the run;
	 * its failure is only logged.
	 * Use {@link #createTerms()} to get the failures too.
	 */
	public ImmutableList<I_C_Flatrate_Term> createTermsForBPartners()
	{
		return createTerms().getTerms();
	}

	/**
	 * Creates terms for all the BPartners, each of them in its own transaction.
	 * A failing partner is rolled back, logged and collected in the result; the remaining partners are still processed.
	 */
	public CreationResult createTerms()
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final ImmutableList.Builder<I_C_Flatrate_Term> flatrateTermsCollector = ImmutableList.builder();
		final ImmutableList.Builder<PartnerFailure> failuresCollector = ImmutableList.builder();

		for (final I_C_BPartner partner : bPartners)
		{
			try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(partner))
			{
				// create each term in its own transaction
				trxManager.runInNewTrx(new TrxRunnableAdapter()
				{
					@Override
					public void run(final String localTrxName)
					{
						createTerm(partner, flatrateTermsCollector);
						Loggables.addLog("@Processed@ @C_BPartner_ID@:" + partner.getValue() + "_" + partner.getName());
						logger.debug("Created contract(s) for {}", partner);
					}

					// Swallowing the exception here keeps one failing partner from blocking the others (the run can cover 10000 partners).
					// The failure is logged and collected, so that the caller can report it at the end.
					@Override
					public boolean doCatch(final Throwable ex)
					{
						final String reason = ex.getLocalizedMessage();
						Loggables.addLog("@Error@ @C_BPartner_ID@:" + partner.getValue() + "_" + partner.getName() + ": " + reason);
						logger.debug("Failed creating contract for {}", partner, ex);
						failuresCollector.add(PartnerFailure.of(partner.getValue() + "_" + partner.getName(), reason));
						return true; // rollback
					}
				});
			}
		}

		return new CreationResult(flatrateTermsCollector.build(), failuresCollector.build());
	}

	@Value(staticConstructor = "of")
	public static class PartnerFailure
	{
		@NonNull String partner;
		@Nullable String reason;
	}

	@Value
	public static class CreationResult
	{
		@NonNull ImmutableList<I_C_Flatrate_Term> terms;
		@NonNull ImmutableList<PartnerFailure> failures;
	}

	private void createTerm(@NonNull final I_C_BPartner partner, @NonNull final ImmutableList.Builder<I_C_Flatrate_Term> flatrateTermCollector)
	{
		final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

		final IContextAware context = PlainContextAware.newWithThreadInheritedTrx(ctx);

		for (final I_M_Product product : products)
		{
			final CreateFlatrateTermRequest createFlatrateTermRequest = CreateFlatrateTermRequest.builder()
					.orgId(OrgId.ofRepoId(conditions.getAD_Org_ID()))
					.context(context)
					.bPartner(partner)
					.conditions(conditions)
					.startDate(startDate)
					.endDate(endDate)
					.userInCharge(userInCharge)
					.productAndCategoryId(createProductAndCategoryId(product))
					.isSimulation(isSimulation)
					.completeIt(isCompleteDocument)
					.build();

			flatrateTermCollector.add(flatrateBL.createTerm(createFlatrateTermRequest));
		}
	}

	@Nullable
	public ProductAndCategoryId createProductAndCategoryId(@Nullable final I_M_Product productRecord)
	{
		if (productRecord == null)
		{
			return null;
		}
		return ProductAndCategoryId.of(productRecord.getM_Product_ID(), productRecord.getM_Product_Category_ID());
	}
}
