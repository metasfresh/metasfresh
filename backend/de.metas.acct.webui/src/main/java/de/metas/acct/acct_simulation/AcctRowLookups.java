package de.metas.acct.acct_simulation;

import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.edi.model.I_C_Order;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.tax.api.TaxId;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_SectionCode;
import org.compiere.util.Evaluatees;

import javax.annotation.Nullable;

class AcctRowLookups
{
	private final LookupDataSource postingSignLookup;
	private final LookupDataSource elementValueLookup;
	private final LookupDataSource taxLookup;
	private final LookupDataSource sectionCodeLookup;
	private final LookupDataSource productLookup;
	private final LookupDataSource orderLookup;
	private final LookupDataSource activityLookup;

	public AcctRowLookups(@NonNull final LookupDataSourceFactory lookupDataSourceFactory)
	{
		this.postingSignLookup = lookupDataSourceFactory.listByAD_Reference_Value_ID(PostingSign.AD_REFERENCE_ID);
		this.elementValueLookup = lookupDataSourceFactory.searchInTableLookup(I_C_ElementValue.Table_Name);
		this.taxLookup = lookupDataSourceFactory.searchInTableLookup(I_C_Tax.Table_Name);
		this.sectionCodeLookup = lookupDataSourceFactory.searchInTableLookup(I_M_SectionCode.Table_Name);
		this.productLookup = lookupDataSourceFactory.searchInTableLookup(I_M_Product.Table_Name);
		this.orderLookup = lookupDataSourceFactory.searchInTableLookup(I_C_Order.Table_Name);
		this.activityLookup = lookupDataSourceFactory.searchInTableLookup(I_C_Activity.Table_Name);
	}

	public LookupValue lookupElementValue(@Nullable final ElementValueId id) {return elementValueLookup.findById(id);}

	public LookupValue lookupTax(@Nullable final TaxId id) {return taxLookup.findById(id);}

	public LookupValue lookupSectionCode(@Nullable final SectionCodeId id) {return sectionCodeLookup.findById(id);}

	public LookupValue lookupProduct(@Nullable final ProductId id) {return productLookup.findById(id);}

	public LookupValue lookupOrder(@Nullable final OrderId id) {return orderLookup.findById(id);}

	public LookupValue lookupActivity(@Nullable final ActivityId id) {return activityLookup.findById(id);}

	public LookupValuesPage getFieldTypeahead(final String fieldName, final String query)
	{
		return getLookupDataSource(fieldName).findEntities(Evaluatees.empty(), query);
	}

	public LookupValuesList getFieldDropdown(final String fieldName)
	{
		return getLookupDataSource(fieldName).findEntities(Evaluatees.empty(), 20).getValues();
	}

	private LookupDataSource getLookupDataSource(@NonNull final String fieldName)
	{
		if (AcctRow.FIELDNAME_PostingSign.equals(fieldName))
		{
			return postingSignLookup;
		}
		else if (AcctRow.FIELDNAME_Account_ID.equals(fieldName))
		{
			return elementValueLookup;
		}
		else if (AcctRow.FIELDNAME_C_Tax_ID.equals(fieldName))
		{
			return taxLookup;
		}
		else if (AcctRow.FIELDNAME_M_SectionCode_ID.equals(fieldName))
		{
			return sectionCodeLookup;
		}
		else if (AcctRow.FIELDNAME_M_Product_ID.equals(fieldName))
		{
			return productLookup;
		}
		else if (AcctRow.FIELDNAME_C_OrderSO_ID.equals(fieldName))
		{
			return orderLookup;
		}
		else if (AcctRow.FIELDNAME_C_Activity_ID.equals(fieldName))
		{
			return activityLookup;
		}
		else
		{
			throw new AdempiereException("Field " + fieldName + " does not exist or it's not a lookup field");
		}
	}
}
