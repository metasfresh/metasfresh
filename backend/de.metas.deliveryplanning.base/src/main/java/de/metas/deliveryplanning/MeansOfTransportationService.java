package de.metas.deliveryplanning;

import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class MeansOfTransportationService
{
	private final MeansOfTransportationRepository meansOfTransportationRepository;

	public MeansOfTransportationService(final MeansOfTransportationRepository meansOfTransportationRepository) {this.meansOfTransportationRepository = meansOfTransportationRepository;}

	public MeansOfTransportation getById(@NonNull MeansOfTransportationId id)
	{
		return meansOfTransportationRepository.getById(id);
	}

}
