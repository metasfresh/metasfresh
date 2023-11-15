package de.metas.shippingnotification;

import lombok.NonNull;

public interface ShippingNotificationListener
{
	default void onAfterComplete(@NonNull ShippingNotification shippingNotification) {}

	default void onAfterReverse(@NonNull ShippingNotification reversal, @NonNull ShippingNotification original) {}
}
