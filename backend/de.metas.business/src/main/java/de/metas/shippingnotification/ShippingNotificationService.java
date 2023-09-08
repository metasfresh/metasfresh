package de.metas.shippingnotification;

import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ShippingNotificationService
{
	private final ShipperNotificationRepository shipperNotificationRepository;

	public ShippingNotificationService(
			@NonNull final ShipperNotificationRepository shipperNotificationRepository)
	{
		this.shipperNotificationRepository = shipperNotificationRepository;
	}

	public boolean isCompletedDocument(@NonNull final ShippingNotificationId shippingNotificationId)
	{
		return shipperNotificationRepository.getById(shippingNotificationId).getDocStatus().isCompleted();
	}
}
