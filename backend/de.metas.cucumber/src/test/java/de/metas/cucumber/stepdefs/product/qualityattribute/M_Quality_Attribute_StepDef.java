/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.product.qualityattribute;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Quality_Attribute;
import org.compiere.util.DB;

import java.util.List;

public class M_Quality_Attribute_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Product_StepDefData productTable;

	public M_Quality_Attribute_StepDef(@NonNull final M_Product_StepDefData productTable)
	{
		this.productTable = productTable;
	}

	@And("^validate created M_Quality_Attributes for product: (.*)$")
	public void validate_M_Quality_Attribute(@NonNull final String productIdentifier, @NonNull final DataTable dataTable)
	{
		final I_M_Product product = productTable.get(productIdentifier);

		final List<I_M_Quality_Attribute> qualityAttributeList = queryBL.createQueryBuilder(I_M_Quality_Attribute.class)
				.addEqualsFilter(I_M_Quality_Attribute.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.addNotNull(I_M_Quality_Attribute.COLUMNNAME_QualityAttribute)
				.orderBy(I_M_Quality_Attribute.COLUMNNAME_QualityAttribute)
				.create()
				.list();

		final SoftAssertions softly = new SoftAssertions();

		for (int idx = 0; idx < dataTable.asMaps().size(); idx++)
		{
			final String qualityAttribute = DataTableUtil.extractStringOrNullForColumnName(dataTable.asMaps().get(idx), "OPT." + I_M_Quality_Attribute.COLUMNNAME_QualityAttribute);

			if (Check.isNotBlank(qualityAttribute))
			{
				softly.assertThat(qualityAttributeList.get(idx).getQualityAttribute()).as(I_M_Quality_Attribute.COLUMNNAME_QualityAttribute).isEqualTo(qualityAttribute);
			}
		}

		softly.assertAll();
	}

	@And("no M_Quality_Attribute data is found")
	public void reset_data()
	{
		DB.executeUpdateEx("TRUNCATE TABLE M_Quality_Attribute cascade", ITrx.TRXNAME_None);
	}
}
