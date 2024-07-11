package de.metas.handlingunits.picking.job.repository;

public class MockedPickingJobLoaderSupportingServicesFactory implements PickingJobLoaderSupportingServicesFactory
{
	@Override
	public PickingJobLoaderSupportingServices createLoaderSupportingServices()
	{
		return new MockedPickingJobLoaderSupportingServices();
	}
}
