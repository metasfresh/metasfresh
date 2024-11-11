package de.metas.fresh.product;

import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import de.metas.util.InSetPredicate;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaArticle;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaBillableTherapy;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaPackagingUnit;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaTherapy;
import lombok.NonNull;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Acct;
import org.compiere.model.I_M_Product_Allergen;
import org.compiere.model.I_M_Product_Nutrition;
import org.springframework.stereotype.Component;

@Component
public class M_Product_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	@Override
	public String getTableName() {return I_M_Product.Table_Name;}

	@Override
	public @NonNull InSetPredicate<String> getChildTableNames()
	{
		return InSetPredicate.only(
				I_M_Product_Acct.Table_Name,
				I_M_Product_AlbertaArticle.Table_Name,
				I_M_Product_AlbertaBillableTherapy.Table_Name,
				I_M_Product_AlbertaTherapy.Table_Name,
				I_M_Product_AlbertaPackagingUnit.Table_Name,
				I_M_Product_Allergen.Table_Name,
				I_M_Product_Nutrition.Table_Name);
	}
}
