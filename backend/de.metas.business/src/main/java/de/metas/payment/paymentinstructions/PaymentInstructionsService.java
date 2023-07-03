package de.metas.payment.paymentinstructions;

import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class PaymentInstructionsService
{
	private final PaymentInstructionsRepository paymentInstructionsRepository;

	public PaymentInstructionsService(final PaymentInstructionsRepository paymentInstructionsRepository) {this.paymentInstructionsRepository = paymentInstructionsRepository;}

	public PaymentInstructions getById(@NonNull PaymentInstructionsId id) {return paymentInstructionsRepository.getById(id);}
}
