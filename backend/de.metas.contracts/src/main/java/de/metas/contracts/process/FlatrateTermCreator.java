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
import de.metas.common.util.CoalesceUtil;
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

	/** 
	 * If not given, then the {@link #conditions}'s orgId is used.
	 */
	@Nullable
	OrgId orgId;

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
	 * create terms for all the BPartners iterated from the subclass
	 */
	public ImmutableList<I_C_Flatrate_Term> createTermsForBPartners()
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final ImmutableList.Builder<I_C_Flatrate_Term> flatrateTermsCollector = ImmutableList.builder();

		for (final I_C_BPartner partner : bPartners)
		{
			try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(partner))
			{
				trxManager.runInThreadInheritedTrx(new TrxRunnableAdapter()
				{
					@Override
					public void run(final String localTrxName)
					{
						createTerm(partner, flatrateTermsCollector);
						Loggables.addLog("@Processed@ @C_BPartner_ID@:" + partner.getValue() + "_" + partner.getName());
						logger.debug("Created contract(s) for {}", partner);
					}
				});
			}
		}

		return flatrateTermsCollector.build();
	}

	private void createTerm(@NonNull final I_C_BPartner partner, @NonNull final ImmutableList.Builder<I_C_Flatrate_Term> flatrateTermCollector)
	{
		final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

		final IContextAware context = PlainContextAware.newWithThreadInheritedTrx(ctx);

		final OrgId orgIdToUse = CoalesceUtil.coalesceSuppliersNotNull(
				() -> this.orgId,
				() -> OrgId.ofRepoId(conditions.getAD_Org_ID()));
		
		for (final I_M_Product product : products)
		{
			final CreateFlatrateTermRequest createFlatrateTermRequest = CreateFlatrateTermRequest.builder()
				.orgId(orgIdToUse)
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
	private ProductAndCategoryId createProductAndCategoryId(@Nullable final I_M_Product productRecord)
	{
		if (productRecord == null)
		{
			return null;
		}
		return ProductAndCategoryId.of(productRecord.getM_Product_ID(), productRecord.getM_Product_Category_ID());
	}
}
