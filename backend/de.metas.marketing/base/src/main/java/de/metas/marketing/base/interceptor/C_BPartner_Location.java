package de.metas.marketing.base.interceptor;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Set;

@Interceptor(I_C_BPartner_Location.class)
@Component
public class C_BPartner_Location
{
	private final ContactPersonRepository contactPersonRepository;

	public C_BPartner_Location(
			@NonNull final ContactPersonRepository contactPersonRepository)
	{
		this.contactPersonRepository = contactPersonRepository;
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_BPartner_Location.COLUMNNAME_C_Location_ID)
	public void updateContactPersonC_Location(I_C_BPartner_Location bpLocation)
	{
		final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoId(bpLocation.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
		final Set<ContactPerson> contactPersons = contactPersonRepository.getByBPartnerLocationId(bpLocationId);
		contactPersons.forEach(contactPerson -> contactPersonRepository.updateBPartnerLocation(contactPerson, bpLocationId));
	}
}
