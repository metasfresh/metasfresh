package de.metas.shippingnotification.acct;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.doc.AcctDocContext;
import de.metas.acct.doc.AcctDocRequiredServicesFacade;
import de.metas.acct.doc.IAcctDocProvider;
import de.metas.shippingnotification.ShippingNotificationId;
import de.metas.shippingnotification.ShippingNotificationService;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.Doc;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Doc_ShippingNotification_Factory implements IAcctDocProvider
{
	private final ShippingNotificationService shippingNotificationService;

	@Override
	public Set<String> getDocTableNames()
	{
		return ImmutableSet.of(I_M_Shipping_Notification.Table_Name);
	}

	@Override
	public Doc<?> getOrNull(final AcctDocRequiredServicesFacade services, final List<AcctSchema> acctSchemas, final TableRecordReference documentRef)
	{
		final ShippingNotificationId shippingNotificationId = documentRef.getIdAssumingTableName(I_M_Shipping_Notification.Table_Name, ShippingNotificationId::ofRepoId);

		return new Doc_ShippingNotification(
				shippingNotificationService,
				AcctDocContext.builder()
						.services(services)
						.acctSchemas(acctSchemas)
						.documentModel(shippingNotificationService.getRecordById(shippingNotificationId))
						.build());
	}
}
