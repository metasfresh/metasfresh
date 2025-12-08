package de.metas.sales_region;

import de.metas.user.UserId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SalesRegionService
{
	private final SalesRegionRepository salesRegionRepository;

	public SalesRegionService(final SalesRegionRepository salesRegionRepository) {this.salesRegionRepository = salesRegionRepository;}

	public SalesRegion getById(final SalesRegionId salesRegionId) {return salesRegionRepository.getById(salesRegionId);}

	public Optional<SalesRegion> getBySalesRepId(final UserId salesRepId)
	{
		return salesRegionRepository.getBySalesRepId(salesRepId);
	}
}
