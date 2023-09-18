package de.metas.acct.accounts;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueService;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Campaign;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_SalesRegion;
import org.compiere.model.I_C_SubAcct;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_SectionCode;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.HashMap;

@RequiredArgsConstructor
class ValidCombinationDescriptionUpdater
{
	private static final Logger log = LogManager.getLogger(ValidCombinationDescriptionUpdater.class);
	private final IAcctSchemaDAO acctSchemaDAO;
	private final IOrgDAO orgDAO;
	private final ElementValueService elementValueService;

	private static final String SEGMENT_COMBINATION_NA = "_";
	private static final String SEGMENT_DESCRIPTION_NA = "_";

	private final HashMap<ElementValueId, ElementValue> elementValues = new HashMap<>();
	private final HashMap<OrgId, I_AD_Org> orgs = new HashMap<>();

	public void update(final I_C_ValidCombination account)
	{
		final StringBuilder combination = new StringBuilder();
		final StringBuilder description = new StringBuilder();
		boolean fullyQualified = true;

		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(account.getC_AcctSchema_ID());
		final AcctSchema acctSchema = acctSchemaDAO.getById(acctSchemaId);
		final String separator = acctSchema.getValidCombinationOptions().getSeparator();

		//
		for (final AcctSchemaElement element : acctSchema.getSchemaElements())
		{
			// Skip those elements which are not displayed in editor (07546)
			if (!element.isDisplayedInEditor())
			{
				continue;
			}

			String segmentCombination = SEGMENT_COMBINATION_NA;        // not defined
			String segmentDescription = SEGMENT_DESCRIPTION_NA;

			final AcctSchemaElementType elementType = element.getElementType();
			Check.assumeNotNull(elementType, "elementType not null"); // shall not happen

			if (AcctSchemaElementType.Organization.equals(elementType))
			{
				final OrgId orgId = OrgId.ofRepoIdOrAny(account.getAD_Org_ID());
				if (orgId.isRegular())
				{
					final I_AD_Org org = getOrgById(orgId);
					segmentCombination = org.getValue();
					segmentDescription = org.getName();
				}
				else
				{
					segmentCombination = "*";
					segmentDescription = "*";
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.Account.equals(elementType))
			{
				final ElementValue elementValue = getElementValueById(ElementValueId.ofRepoIdOrNull(account.getAccount_ID()));
				if (elementValue != null)
				{
					segmentCombination = elementValue.getValue();
					segmentDescription = elementValue.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Account");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.SubAccount.equals(elementType))
			{
				final I_C_SubAcct sa = account.getC_SubAcct();
				if (sa != null)
				{
					segmentCombination = sa.getValue();
					segmentDescription = sa.getName();
				}
			}
			else if (AcctSchemaElementType.Product.equals(elementType))
			{
				final I_M_Product product = account.getM_Product();
				if (product != null)
				{
					segmentCombination = product.getValue();
					segmentDescription = product.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Product");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.BPartner.equals(elementType))
			{
				I_C_BPartner partner = account.getC_BPartner();
				if (partner != null)
				{
					segmentCombination = partner.getValue();
					segmentDescription = partner.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Business Partner");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.OrgTrx.equals(elementType))
			{
				final OrgId orgTrxId = OrgId.ofRepoIdOrAny(account.getAD_OrgTrx_ID());
				if (orgTrxId.isRegular())
				{
					I_AD_Org org = getOrgById(orgTrxId);
					segmentCombination = org.getValue();
					segmentDescription = org.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Trx Org");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.LocationFrom.equals(elementType))
			{
				final I_C_Location loc = account.getC_LocFrom();
				if (loc != null)
				{
					segmentCombination = loc.getPostal();
					segmentDescription = loc.getCity();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Location From");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.LocationTo.equals(elementType))
			{
				final I_C_Location loc = account.getC_LocTo();
				if (loc != null)
				{
					segmentCombination = loc.getPostal();
					segmentDescription = loc.getCity();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Location To");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.SalesRegion.equals(elementType))
			{
				final I_C_SalesRegion salesRegion = account.getC_SalesRegion();
				if (salesRegion != null)
				{
					segmentCombination = salesRegion.getValue();
					segmentDescription = salesRegion.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: SalesRegion");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.Project.equals(elementType))
			{
				// if (account.getC_Project_ID() > 0)
				// {
				// 	// final I_C_Project project = account.getC_Project();
				// 	// segmentCombination = project.getValue();
				// 	// segmentDescription = project.getName();
				// }
				// else
				if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Project");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.Campaign.equals(elementType))
			{
				final I_C_Campaign campaign = account.getC_Campaign();
				if (campaign != null)
				{
					segmentCombination = campaign.getValue();
					segmentDescription = campaign.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Campaign");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.Activity.equals(elementType))
			{
				final I_C_Activity activity = account.getC_Activity();
				if (activity != null)
				{
					segmentCombination = activity.getValue();
					segmentDescription = activity.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Campaign");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.SalesOrder.equals(elementType))
			{
				final I_C_Order salesOrder = account.getC_OrderSO();
				if (salesOrder != null)
				{
					segmentCombination = salesOrder.getDocumentNo();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: C_Order_ID");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.SectionCode.equals(elementType))
			{
				final I_M_SectionCode sectionCode = account.getM_SectionCode();
				if (sectionCode != null)
				{
					segmentCombination = sectionCode.getValue();
					segmentDescription = sectionCode.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: C_Order_ID");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.UserList1.equals(elementType))
			{
				final I_C_ElementValue ev = account.getUser1();
				if (ev != null)
				{
					segmentCombination = ev.getValue();
					segmentDescription = ev.getName();
				}
			}
			else if (AcctSchemaElementType.UserList2.equals(elementType))
			{
				final I_C_ElementValue ev = account.getUser2();
				if (ev != null)
				{
					segmentCombination = ev.getValue();
					segmentDescription = ev.getName();
				}
			}
			// TODO: implement
			// else if (AcctSchemaElementType.UserElement1.equals(elementType))
			// {
			// 	// if (acct.getUserElement1_ID() > 0)
			// 	// {
			// 	// }
			// }
			// else if (AcctSchemaElementType.UserElement2.equals(elementType))
			// {
			// 	// if (acct.getUserElement2_ID() > 0)
			// 	// {
			// 	// }
			// }

			//
			// Append segment combination and description
			if (!combination.isEmpty())
			{
				combination.append(separator);
				description.append(separator);
			}
			combination.append(segmentCombination);
			description.append(segmentDescription);
		}

		//
		// Set Values
		account.setCombination(combination.toString());
		account.setDescription(description.toString());
		if (fullyQualified != account.isFullyQualified())
		{
			account.setIsFullyQualified(fullyQualified);
		}
	}

	@Nullable
	private ElementValue getElementValueById(@Nullable final ElementValueId elementValueId)
	{
		if (elementValueId == null)
		{
			return null;
		}
		return elementValues.computeIfAbsent(elementValueId, elementValueService::getById);
	}

	private I_AD_Org getOrgById(@NonNull final OrgId orgId)
	{
		return orgs.computeIfAbsent(orgId, orgDAO::getById);
	}

}
