package de.metas.shippingnotification.acct;

import com.google.common.collect.ImmutableMap;
import de.metas.acct.doc.AcctDocProviderTemplate;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import org.springframework.stereotype.Component;

@Component
public class ShippingNotificationAcctDocProvider extends AcctDocProviderTemplate
{
	protected ShippingNotificationAcctDocProvider()
	{
		super(ImmutableMap.<String, AcctDocFactory>builder()
				.put(I_M_Shipping_Notification.Table_Name, Doc_ShippingNotification::new)
				.build());
	}
}
