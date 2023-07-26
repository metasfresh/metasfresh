package de.metas.marketing.base.interceptor;

import java.util.Set;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;

@Interceptor(I_C_BPartner_Location.class)
@Component
public class C_BPartner_Location
{

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_BPartner_Location.COLUMNNAME_C_Location_ID)
	public void updateContactPersonC_Location(I_C_BPartner_Location bpLocation)
	{
		final ContactPersonRepository contactrepo = Adempiere.getBean(ContactPersonRepository.class);

		final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoId(BPartnerId.ofRepoId(bpLocation.getC_BPartner_ID()), bpLocation.getC_BPartner_Location_ID());
		final Set<ContactPerson> contactPersons = contactrepo.getByBPartnerLocationId(bpLocationId);
		contactPersons.stream()
				.forEach(contactPerson -> contactrepo.updateBPartnerLocation(contactPerson, bpLocationId));
		}
}
